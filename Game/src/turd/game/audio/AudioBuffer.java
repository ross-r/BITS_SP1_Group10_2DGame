package turd.game.audio;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.openal.AL10.*;

import org.lwjgl.stb.STBVorbisInfo;
import static org.lwjgl.stb.STBVorbis.*;

import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.system.MemoryUtil.*;

// Creates buffers to handle .wav I/O.
public class AudioBuffer {
	
	/*
	static void checkALError() {
		int err = alGetError();
		if (err != AL_NO_ERROR) {
			throw new RuntimeException(String.format("%d - %s", err, alGetString(err)));
		}
	}
	*/
	
	protected final int iBufferID;
	protected final ShortBuffer vorbisBuffer;	
	protected final int iChannels;
	protected final int iSampleRate;
   
	protected ByteBuffer resourceBuffer;
	
	public AudioBuffer(String file) throws Exception {
		this.resourceBuffer = null;
		
		this.iBufferID = alGenBuffers();
		
		// Load the OGG file into a buffer in memory and store off some important data.
		try(STBVorbisInfo info = STBVorbisInfo.malloc()) {
			// This buffer needs to stay loaded for the audio to play.
			// Only free it when the application is shutting down.
			this.vorbisBuffer = readVorbis(file, 32 * 1024, info);
		   
			// Once the .ogg file has been loaded and stb_vorbis has parsed it's information we can free the buffer.
			if(this.resourceBuffer != null) {
				MemoryUtil.memFree(this.resourceBuffer);
			}
			
			iChannels = info.channels();
			iSampleRate = info.sample_rate();
		}
	}
	
	public void load() {
		alBufferData(this.iBufferID, iChannels == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, this.vorbisBuffer, iSampleRate);	
	}
	
	// Deletes buffers to prevent open I/O.
	public void bufferCleanUp() {
		AL10.alDeleteBuffers(this.iBufferID);
		if (this.vorbisBuffer != null) {
			MemoryUtil.memFree(this.vorbisBuffer);
		}
	}

	// Reads .ogg files and decodes using STBVorbis.
	private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) throws Exception {
		try {
			this.resourceBuffer = ioResourceToByteBuffer(resource, bufferSize);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		if (this.resourceBuffer == null) {
			throw new RuntimeException("Resource Buffer failed allocation.");
		}
		
		IntBuffer error = BufferUtils.createIntBuffer(1);
		long decoder = stb_vorbis_open_memory(this.resourceBuffer, error, null);
		if (decoder == NULL) {
			throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
		}
		
		stb_vorbis_get_info(decoder, info);
		
		int channels = info.channels();
		
		ShortBuffer pcm = BufferUtils.createShortBuffer(stb_vorbis_stream_length_in_samples(decoder) * channels);
		 
		stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm);
		
		stb_vorbis_close(decoder);
		
		return pcm;
	}
	
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {

		ByteBuffer bbBuffer;

		Path path = Paths.get(resource);

		if (Files.isReadable(path)) {
			try (SeekableByteChannel fc = Files.newByteChannel(path)) {
				bbBuffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
				while (fc.read(bbBuffer) != -1);
			}
		} else {
			try (InputStream source = AudioBuffer.class.getResourceAsStream(resource);
					ReadableByteChannel rbc = Channels.newChannel(source)) {

				bbBuffer = createByteBuffer(bufferSize);

				while (true) {
					int bytes = rbc.read(bbBuffer);
					if (bytes == -1) {
						break;
					}
					if (bbBuffer.remaining() == 0) {
						bbBuffer = resizeBuffer(bbBuffer, bbBuffer.capacity() * 2);
					}
				}
			}
		}

		bbBuffer.flip();
		return bbBuffer;
	}
	
	//Resize a byte buffer.
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {

		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);

		buffer.flip();

		newBuffer.put(buffer);
		return newBuffer;

	}
}
