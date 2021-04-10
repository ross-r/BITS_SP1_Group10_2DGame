package turd.game.audio;

import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

//Sets the listener for audio sources. Only one listener per compilation.
public class AudioListener {

	//Initializes listener with neutral values for 3D space.
	public AudioListener() {
		
		this(new Vector3f(0, 0, 0));
		
	}
	
	//Initializes listener with specific locations in 3D space, and neutral velocity of audio input in 3D space.
	public AudioListener(Vector3f position) {
		
		alListener3f(AL_POSITION, position.x, position.y, position.z);
		alListener3f(AL_VELOCITY, 0, 0, 0);
		
	}
	
	//Sets velocity of audio input.
	public void setSpeed(Vector3f speed) {
		
		alListener3f(AL_VELOCITY, speed.x, speed.y, speed.z);
		
	}
	
	//Set position of listener in 3D space.
	public void setPosition(Vector3f position) {
		
		alListener3f(AL_POSITION, position.x, position.y, position.z);
		
	}
	
	//Set orientation of listener in 3D space. Takes two Vectors to indicate direction, and location.
	public void setOrientation(Vector3f at, Vector3f up) {
		
		float[] data = new float[6];
		
		data[0] = at.x;
		
		data[1] = at.y;
		
		data[2] = at.z;
		
		data[3] = up.x;
		
		data[4] = up.y;
		
		data[5] = up.z;
		
		alListenerfv(AL_ORIENTATION, data);
		
	}
	
	
}
