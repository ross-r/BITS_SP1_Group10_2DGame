package turd.game.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.joml.Vector3f;

public class Audio {

	private long lDevice;

	private long lContext;
	
	private AudioListener alListener;
	
	private Map <String, AudioSource> soundMap = new HashMap<>();

	private Vector3f vrListenerPosition = new Vector3f(0, 0, 0);

	public Audio(){

		try {
			init();
		} catch (Exception e) {
			System.out.println("Error: Could not initialize audio device.");
			e.printStackTrace();
		}

		createListener(vrListenerPosition);
		
		try {
			createSounds();
		} catch (Exception e) {
			System.out.println("Error: Could not create audio library.");
			e.printStackTrace();
		}
		
		Scanner in = new Scanner(System.in);
		
		String entry;
		
		do {
			System.out.println("Enter sound to play \n 1 = Laser \n 2 = footsteps \n 3 = Jump \n q = quit. ");
			System.out.println("Enter: ");
			
			entry = in.next();
			switch(entry) {
			case "1":
				play("laser");
			break;
			case "2":
				play("footsteps");
			break;
			case "3":
				play("jump");
			break;
			case "q":
			return;
			}
		} while (entry != "q");
		
		terminate();
	}

	public void createListener(Vector3f position) {

		alListener = new AudioListener(position);
		
		alListener.setOrientation(position, position);

	}
	
	public void createSounds() throws Exception {
		
		AudioSource footsteps = new AudioSource("footsteps.ogg", false, false, 1);
		AudioSource laser = new AudioSource("Laser.ogg", false, false, 1);
		AudioSource jump = new AudioSource("jump.ogg", false, false, 20);
		soundMap.put("footsteps", footsteps);
		soundMap.put("laser", laser);
		soundMap.put("jump", jump);		
	}
	
	public void terminate() {
		
		Iterator <String> it = soundMap.keySet().iterator();
		
		while (it.hasNext()) {
			String temp = (String)it.next();
			soundMap.get(temp).cleanUp();
		}
		
		alcCloseDevice(lDevice);
		alcDestroyContext(lContext);
	}
	
	public void play(String name) {
		soundMap.get(name).play();
	}

	public void init() throws Exception {

		this.lDevice = alcOpenDevice((ByteBuffer) null);

		if (lDevice == NULL) {

			throw new IllegalStateException("Failed to open the default OpenAL device.");

		}

		ALCCapabilities deviceCaps = ALC.createCapabilities(lDevice);

		this.lContext = alcCreateContext(lDevice, (IntBuffer) null);

		if (lContext == NULL) {

			throw new IllegalStateException("Failed to create OpenAL context.");

		}

		alcMakeContextCurrent(lContext);

		AL.createCapabilities(deviceCaps);
	}
	
	public static void main(String args[]) {
		new Audio();
	}
}
