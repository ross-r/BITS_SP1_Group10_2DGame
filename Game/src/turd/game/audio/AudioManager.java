package turd.game.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.joml.Vector3f;

public class AudioManager {

	private long lDevice;

	private long lContext;
	
	private AudioListener alListener;
	
	private Map <String, AudioSource> soundMap = new HashMap<>();

	private Vector3f vrListenerPosition = new Vector3f(0, 0, 0);

	public AudioManager() throws Exception {

		init();

		createListener(vrListenerPosition);
		
		createSounds();

		int i = 5;
		while(i > 0) {
			
			play("laser");
			System.out.printf("playing: %b\n", soundMap.get("footsteps").isPlaying());
			Thread.sleep(1000);
			i--;
		}
		
		cleanUp();
	}

	public void createListener(Vector3f position) {

		alListener = new AudioListener(position);
		
		alListener.setOrientation(position, position);

	}
	
	public void createSounds() throws Exception {
		
		AudioSource footsteps = new AudioSource("footsteps.ogg", false, false, 1);
		AudioSource laser = new AudioSource("Laser.ogg", false, false, 1);
		soundMap.put("footsteps", footsteps);
		soundMap.put("laser", laser);
		
	}
	
	public void cleanUp() {
		
		Iterator <String> it = soundMap.keySet().iterator();
		
		while (it.hasNext()) {
			String temp = (String)it.next();
			soundMap.get(temp).cleanUp();
		}
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

	public static void main(String[] args) {
		try {
			
			AudioManager manager = new AudioManager();
			
		} catch (Exception e) {
			
			System.out.println("Error: Unable to generate sound controller.");
			e.printStackTrace();
			
		}
	}
}
