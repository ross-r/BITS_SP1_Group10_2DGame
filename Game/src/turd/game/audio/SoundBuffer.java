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

public class SoundBuffer {

	private final int bufferID;
	private ByteBuffer vorbis = null;
	private ShortBuffer wav = null;
	
	public SoundBuffer(String file) throws Exception {
		this.bufferID = alGenBuffers();
		try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
			wav = readVorbis(file, 32 * 1024, info);
			
			AL10.alBufferData(AL_BUFFER, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, wav, info.sample_rate());
		}
	}
	
	public int getBufferID() {
		return this.bufferID;
	}
	
	public void cleanUp() {
		AL10.alDeleteBuffers(this.bufferID);
		if (wav != null) {
			MemoryUtil.memFree(wav);
		}
	}
	
	private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) throws Exception {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			vorbis = Utils.ioResourceToByteBuffer(resource, bufferSize);
			IntBuffer error = stack.mallocInt(1);
			long decoder = stb_vorbis_open_memory(vorbis, error, null);

			if (decoder == NULL) {
				throw new RuntimeException("Failed to open Ogg. Vorbis file. Error: " + error.get(0));
			}
			
			stb_vorbis_get_info(decoder, info);
			
			int channels = info.channels();
			
			int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);
			
			wav = MemoryUtil.memAllocShort(lengthSamples);
			
			wav.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, wav) * channels);
			stb_vorbis_close(decoder);
			
			return wav;
		}
	}
}
