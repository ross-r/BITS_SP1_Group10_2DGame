package turd.game.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.ALC10.*;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import org.joml.Vector3f;

public class AudioManager {

	private long lDevice;

	private long lContext;

	private AudioBuffer abFootstepsBuffer;

	private AudioSource asFootsteps;
	
	private AudioListener alListener;

	private Vector3f vrListenerPosition = new Vector3f(0, 0, 0);
	
	private Vector3f vrSourcePosition = new Vector3f(0, 0, 0);

	public AudioManager() throws Exception {

		init();

		addSoundsToList();

		createSources(vrSourcePosition);
		
		createListener(vrListenerPosition);
		
		for (int i = 0; i < 10; i++) {
			
			System.out.println("Sound has played " + (i + 1) + " times.");
			System.out.println(asFootsteps.getSourceID());
			asFootsteps.play();
		}
		
		abFootstepsBuffer.cleanUp();
		asFootsteps.cleanUp();
	}

	public void addSoundsToList() throws Exception {

		abFootstepsBuffer = new AudioBuffer("Laser.ogg");

	}

	public void createListener(Vector3f position) {

		alListener = new AudioListener(position);
		
		alListener.setOrientation(position, position);

	}

	public void createSources(Vector3f position) {

		asFootsteps = new AudioSource(false, true);
		
		asFootsteps.setPosition(position);
		
		asFootsteps.setGain(10);
		
	}
	
	public void addBufferToSources() {
		
		asFootsteps.setBuffer(abFootstepsBuffer.getBufferID());
		 
	}


	public void init() throws Exception {

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

	public static void main(String[] args) {
		try {
			AudioManager manager = new AudioManager();
		} catch (Exception e) {
			System.out.println("Error: Unable to generate sound controller.");
			e.printStackTrace();
		}
	}
}
