package turd.game.audio;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.ByteBuffer;

import org.lwjgl.openal.AL10;
import static org.lwjgl.openal.AL10.*;

import org.lwjgl.stb.STBVorbisInfo;
import static org.lwjgl.stb.STBVorbis.*;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.system.MemoryUtil.*;

//Creates buffers to handle .wav I/O.
public class AudioBuffer {

	private final int iBufferID;
	private ByteBuffer bbVorbis = null;
	private ShortBuffer sbWav = null;
	
	//Initializes a buffer. Encodes data using STBVorbis.
	public AudioBuffer(String file) throws Exception {
		this.iBufferID = alGenBuffers();
		try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
			sbWav = readVorbis(file, 32 * 1024, info);
			
			AL10.alBufferData(AL_BUFFER, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, sbWav, info.sample_rate());
		}
	}
	
	//Returns buffer ID.
	public int getBufferID() {
		return this.iBufferID;
	}
	
	//Deletes buffers to prevent open I/O.
	public void cleanUp() {
		AL10.alDeleteBuffers(this.iBufferID);
		if (sbWav != null) {
			MemoryUtil.memFree(sbWav);
		}
	}
	

	//Reads .wav files and decodes using STBVorbis.
	private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) throws Exception {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			bbVorbis = AudioUtils.ioResourceToByteBuffer(resource, bufferSize);
			
			IntBuffer ibError = stack.mallocInt(1);
			long lDecoder = stb_vorbis_open_memory(bbVorbis, ibError, null);

			if (lDecoder == NULL) {
				throw new RuntimeException("Failed to open Ogg. Vorbis file. Error: " + ibError.get(0));
			}
			
			stb_vorbis_get_info(lDecoder, info);
			
			int iChannels = info.channels();
			
			int iLengthSamples = stb_vorbis_stream_length_in_samples(lDecoder);
			
			sbWav = MemoryUtil.memAllocShort(iLengthSamples);
			
			sbWav.limit(stb_vorbis_get_samples_short_interleaved(lDecoder, iChannels, sbWav) * iChannels);
			stb_vorbis_close(lDecoder);
			
			return sbWav;
		}
	}
}
