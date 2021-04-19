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

	private AudioSource asFootsteps;
	
	private AudioListener alListener;

	private Vector3f vrListenerPosition = new Vector3f(0, 0, 0);
	
	private Vector3f vrSourcePosition = new Vector3f(0, 0, 0);

	public AudioManager() throws Exception {

		init();

		createListener(vrListenerPosition);

		//
		asFootsteps = new AudioSource("footsteps.ogg", false, false);
		asFootsteps.setPosition(new Vector3f(0,0,0));
		asFootsteps.setGain(10);
		asFootsteps.play();
		//
		
		int i = 5;
		while(i > 0) {
			System.out.printf("playing: %b\n", asFootsteps.isPlaying());
			
			Thread.sleep(1000);
			i--;
		}
		
		//asFootsteps.cleanUp();
	}

	public void createListener(Vector3f position) {

		alListener = new AudioListener(position);
		
		alListener.setOrientation(position, position);

	}
	
	public void cleanup() {
		asFootsteps.cleanUp();
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
