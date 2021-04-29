package turd.game.audio;

import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

//Generates sources for playing buffered sounds
public class AudioSource extends AudioBuffer {

	private final int iSourceID;

	// AudioSource constructor. Takes parameters to set whether source loops or
	// plays once.
	public AudioSource(String file, boolean loop, boolean relative, float gain) throws Exception {
		
		// Call AudioBuffer constructor.
		// Step 1 - Generate Buffer ID.
		super(file);
		
		// Step 2 - Generate Source ID.
		this.iSourceID = alGenSources();

		// Step 3 - Load audio file buffer into OpenAL buffer that was generated.
		this.load();
		
		// Set some default properties.
		alSourcei(iSourceID, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
		alSourcei(iSourceID, AL_SOURCE_RELATIVE, relative ? AL_TRUE : AL_FALSE);
		alSourcef(iSourceID, AL_GAIN, gain);
	}

	// Sets playing position of source
	public void setPosition(Vector3f position) {
		alSource3f(iSourceID, AL_POSITION, position.x, position.y, position.z);
	}

	// Sets gain (volume) of source
	public void setGain(float gain) {
		alSourcef(iSourceID, AL_GAIN, gain);
	}

	// Plays source
	public void play() {
		alSourcei(this.iSourceID, AL_BUFFER, this.iBufferID);
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

		bufferCleanUp();
		
		stop();

		alDeleteSources(iSourceID);

	}

	// Returns source ID
	public int getSourceID() {

		return this.iSourceID;

	}
}
