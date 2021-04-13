package turd.game.audio;

import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

//Generates sources for playing buffered sounds
public class AudioSource {

	private final int iSourceID;

	// AudioSource constructor. Takes parameters to set whether source loops or
	// plays once.
	public AudioSource(boolean loop, boolean relative) {

		this.iSourceID = alGenSources();

		if (loop) {
			alSourcei(iSourceID, AL_LOOPING, AL_TRUE);
		}

		if (relative) {
			alSourcei(iSourceID, AL_SOURCE_RELATIVE, AL_TRUE);
		}
	}

	// Sets buffer to source
	public void setBuffer(int bufferID) {

		stop();

		alSourcei(iSourceID, AL_BUFFER, bufferID);
	}

	// Sets playing position of source
	public void setPosition(int x, int y, int z) {

		alSource3f(iSourceID, AL_POSITION, x, y, z);

	}

	// Sets gain (volume) of source
	public void setGain(float gain) {

		alSourcef(iSourceID, AL_GAIN, gain);

	}

	// Plays source
	public void play() {

		alSourcePlay(iSourceID);

	}

	// Returns if source is currently playing
	public boolean isPlaying() {

		return alGetSourcei(iSourceID, AL_SOURCE_STATE) == AL_PLAYING;

	}

	// Pauses a source
	public void pause() {

		alSourcePause(iSourceID);

	}

	// Stops source
	public void stop() {

		alSourceStop(iSourceID);

	}

	// Deletes a source.
	public void cleanUp() {

		stop();

		alDeleteSources(iSourceID);

	}

	// Returns source ID
	public int getSourceID() {

		return this.iSourceID;

	}
}
