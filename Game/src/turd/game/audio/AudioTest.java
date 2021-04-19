package turd.game.audio;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.stb.STBVorbisInfo;

public class AudioTest {

	private long lDevice;
	private long lContext;
	
	private AudioListener listener;
	
    static void checkALError() {
        int err = alGetError();
        if (err != AL_NO_ERROR) {
            throw new RuntimeException(String.format("%d - %s", err, alGetString(err)));
        }
    }
	
	private int iBufferID;
	private int iSourceID;
	
	public void init() {		
		iBufferID = -1;
		iSourceID = -1;
		
		this.lDevice = alcOpenDevice((ByteBuffer) null);

		if (lDevice == 0) {

			throw new IllegalStateException("Failed to open the default OpenAL device.");

		}

		ALCCapabilities deviceCaps = ALC.createCapabilities(lDevice);

		this.lContext = alcCreateContext(lDevice, (IntBuffer) null);

		if (lContext == 0) {

			throw new IllegalStateException("Failed to create OpenAL context.");

		}

		alcMakeContextCurrent(lContext);

		AL.createCapabilities(deviceCaps);
	}

	public void load() throws Exception {
		listener = new AudioListener();

		this.iBufferID = alGenBuffers();
		checkALError();
		
		this.iSourceID = alGenSources();
		checkALError();
		
		// stb_vorbis only supports .ogg file types.
        try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
            ShortBuffer pcm = readVorbis("footsteps.ogg", 32 * 1024, info);

            //copy to buffer
            alBufferData(this.iBufferID, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
            checkALError();
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
		
		alSourcei(this.iSourceID, AL_BUFFER, this.iBufferID);
		checkALError();
		
		alSourcef(iSourceID, AL_GAIN, 10.f);
		checkALError();
		
		alSourcei(this.iSourceID, AL_LOOPING, AL_TRUE);
		checkALError();
		
		//alSource3f(this.iSourceID, AL_POSITION, position[0], position[1], position[2]);
		//checkALError();		
		
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
