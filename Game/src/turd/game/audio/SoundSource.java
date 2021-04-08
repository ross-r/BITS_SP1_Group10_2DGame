package turd.game.audio;

import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

public class SoundSource {
	
	private final int sourceID;
	
	public SoundSource(boolean loop, boolean relative) {
		this.sourceID = alGenSources();
		
		if (loop) {
			alSourcei(sourceID, AL_LOOPING, AL_TRUE);
		}
		
		if (relative) {
			alSourcei(sourceID, AL_SOURCE_RELATIVE, AL_TRUE);
		}
	}
	
	public void setBuffer(int bufferID) {
		
		stop();
		
		alSourcei(sourceID, AL_BUFFER, bufferID);
	}

	public void setPosition(Vector3f position) {
		
		alSource3f(sourceID, AL_POSITION, position.x, position.y, position.z);
		
	}
	
	public void setGain(float gain) {
		
		alSourcef(sourceID, AL_GAIN, gain);
	
	}

	public void play() {
		
		alSourcePlay(sourceID);
		
	}
	
	public boolean isPlaying() {
		
		return alGetSourcei(sourceID, AL_SOURCE_STATE) == AL_PLAYING;
		
	}
	
	public void pause() {
		
		alSourcePause(sourceID);
		
	}
	
	public void stop() {
		
		alSourceStop(sourceID);
		
	}
	
	public void cleanUp() {
		
		stop();
		
		alDeleteSources(sourceID);
		
	}
}
