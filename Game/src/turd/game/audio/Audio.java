package turd.game.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import turd.game.entities.Player;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.joml.Vector3f;

public class Audio {

	private long lDevice;

	private long lContext;
	
	private AudioListener alListener;
	
	private Map <String, AudioSource> soundMap = new HashMap<>();

	private Vector3f vrListenerPosition = new Vector3f(0, 0, 0);
	
	private static Audio instance;

	public Audio(){

		//Initialize audio class.
		try {
			init();
		} catch (Exception e) {
			System.out.println("Error: Could not initialize audio device.");
			e.printStackTrace();
		}

		//Create listener.
		createListener(vrListenerPosition);
		
		//Create sounds using buffers and store in sources.
		try {
			createSounds();
		} catch (Exception e) {
			System.out.println("Error: Could not create audio library.");
			e.printStackTrace();
		}
	}

	//Generate listener with neutral values
	public void createListener(Vector3f position) {

		alListener = new AudioListener(position);
		
		alListener.setOrientation(position, position);

	}
	
	
	//Create all sounds for game and store in sources.
	public void createSounds() throws Exception {
		
		//Create Player Sounds
		AudioSource playerJump = new AudioSource("playerJump.ogg", false, false, -50);
		AudioSource playerRevUp = new AudioSource("playerRevUp.ogg", false, false, 20);
		AudioSource playerMove = new AudioSource("playerMove.ogg", true, false, 20);
		AudioSource playerShoot = new AudioSource("playerShoot.ogg", false, false, 1);
		AudioSource playerPickUp = new AudioSource("playerPickUp.ogg", false, false, 1);
		AudioSource playerDamage = new AudioSource("playerDamage.ogg", false, false, 1);
		soundMap.put("playerJump", playerJump);	
		soundMap.put("playerRevUp", playerRevUp);
		soundMap.put("playerMove", playerMove);
		soundMap.put("playerShoot", playerShoot);
		soundMap.put("playerPickUp", playerPickUp);
		soundMap.put("playerDamage", playerDamage);
		
		//Create Enemy Sounds
		AudioSource enemyDamage = new AudioSource("enemyDamage.ogg", false, false, 1);
		AudioSource enemyMove = new AudioSource("enemyMove.ogg", false, false, 1);
		soundMap.put("enemyDamage", enemyDamage);
		soundMap.put("enemyMove", enemyMove);
		
		//Create game sounds
		AudioSource pausePlay = new AudioSource("pausePlay.ogg", false, false, 1);
		soundMap.put("pausePlay", pausePlay);
		
	}
	
	//Terminates open audio devices used to handle audio file processing.
	public void terminate() {
		
		Iterator <String> it = soundMap.keySet().iterator();
		
		//Iterate through audio devices and close open buffer/sources etc.
		while (it.hasNext()) {
			String temp = (String)it.next();
			soundMap.get(temp).cleanUp();
		}
		
		//Close memory device.
		alcCloseDevice(lDevice);
		
		//Destroy memory context.
		alcDestroyContext(lContext);
	}
	
	//Plays sounds. Gets them from a Map using string as the key.
	public void play(String name) {
		soundMap.get(name).play();
	}
	
	//Stops sounds. Gets them from a Map using string as the key.
	public void stop(String name) {
		soundMap.get(name).stop();
	}
	
	public static Audio getInstance() {
		if (instance == null) {
			instance = new Audio();
		}
		return instance;
	}
	
	public void playerMovePlay() {
		if (!soundMap.get("playerMove").isPlaying()) {
			play("playerMove");
		}
	}
	
	public boolean getPlaying(String key) {
		if (soundMap.get(key).isPlaying()) {
			return true;
		}
		return false;
	}

	//Initializes the audio devices. Opens a device that manages memory and sets the context for audio management.
	public void init() throws Exception {

		//Open memory handling device.
		this.lDevice = alcOpenDevice((ByteBuffer) null);

		if (lDevice == NULL) {

			throw new IllegalStateException("Failed to open the default OpenAL device.");

		}

		//Generate ALC capabilities.
		ALCCapabilities deviceCaps = ALC.createCapabilities(lDevice);

		//Create context for memory management.
		this.lContext = alcCreateContext(lDevice, (IntBuffer) null);

		if (lContext == NULL) {

			throw new IllegalStateException("Failed to create OpenAL context.");

		}

		//Set context to current context of program.
		alcMakeContextCurrent(lContext);

		//Load capabilities into device capabilities.
		AL.createCapabilities(deviceCaps);
	}
}
