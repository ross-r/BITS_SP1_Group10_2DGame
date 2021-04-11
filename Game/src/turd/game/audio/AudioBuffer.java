package turd.game.audio;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
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
	private final int iSourceID;
	
	private ByteBuffer bbVorbis = null;
	private ShortBuffer sbWav = null;
	
    static void checkALError() {
        int err = alGetError();
        if (err != AL_NO_ERROR) {
            throw new RuntimeException(String.format("%d - %s", err, alGetString(err)));
        }
    }
	
	//Initializes a buffer. Encodes data using STBVorbis.
	public AudioBuffer(String file) throws Exception {
		this.iBufferID = alGenBuffers();
		checkALError();
		
		this.iSourceID = alGenSources();
		checkALError();
		
		// stb_vorbis only supports .ogg file types.
        try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
            ShortBuffer pcm = readVorbis(file, 32 * 1024, info);

            //copy to buffer
            alBufferData(this.iBufferID, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
            checkALError();
        }
        
        play();
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
	
	
	public void play() {
		float[] position = new float[] { 0.f, 0.f, 0.f };
		
		/*
		 * AL_POSITION works based off a matrix of sorts, image you have a cube
		 * the top left position of the cube represents x: -1, y: -1
		 * 
		 * 		NOTE: we are ignoring the z component here since we are working in 2D
		 * 
		 * the bottom right position of the cube represents x: 1, y: 1
		 * 
		 * to play audio from the center we would use x: 0, y: 0
		 * 
		 */
		
		alSourcei(this.iSourceID, AL_LOOPING, AL_TRUE);
		
		alSource3f(this.iSourceID, AL_POSITION, position[0], position[1], position[2]);
		
		alSourcei(this.iSourceID, AL_BUFFER, this.iBufferID);
		checkALError();
		
		// play source
		alSourcePlay(this.iSourceID);
		checkALError();
	}

	// Reads .wav files and decodes using STBVorbis.
	private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) throws Exception {
		ByteBuffer vorbis;
		try {
			vorbis = AudioUtils.ioResourceToByteBuffer(resource, bufferSize);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		IntBuffer error   = BufferUtils.createIntBuffer(1);
		long decoder = stb_vorbis_open_memory(vorbis, error, null);
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
}
