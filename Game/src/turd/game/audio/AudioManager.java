package turd.game.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.lwjgl.openal.ALC10.*;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class AudioManager {

	private long lDevice;

	private long lContext;

	private AudioListener alListener;

	private final List<AudioBuffer> liBuffers;

	private final Map<String, AudioSource> mpSoundSourceMap;

	private final Matrix4f mxCameraMatrix;

	private AudioBuffer abLaserBuffer;

	private AudioSource asLaserSource;

	private Vector3f vrPosition = new Vector3f(0, 0, 0);

	public AudioManager() throws Exception {

		init();

		liBuffers = new ArrayList<>();

		mpSoundSourceMap = new HashMap<>();

		mxCameraMatrix = new Matrix4f();

		addSoundsToList();

		createListener(vrPosition);

		createSources();

		System.out.println(mpSoundSourceMap);

		Scanner in = new Scanner(System.in);
		String entry = in.nextLine();

		while (entry != "q") {

			if (entry == "p") {

				System.out.println(mpSoundSourceMap);

				System.out.println("Enter name of sound to play: ");

				entry = in.next();

				playSource(entry);

			}
		}

		abLaserBuffer.cleanUp();
		asLaserSource.cleanUp();
	}

	public void addSoundsToList() throws Exception {
<<<<<<< HEAD

		abLaserBuffer = new AudioBuffer("Sweep.wav");

=======
		
		abLaserBuffer = new AudioBuffer("footsteps.ogg");
		
>>>>>>> 70f92bb01329801f90ff8fc9972f5767101cb8c7
		liBuffers.add(abLaserBuffer);
	}

	public void createListener(Vector3f position) {

		alListener = new AudioListener();

		alListener.setPosition(position);

	}

	public void createSources() {

		asLaserSource = new AudioSource(true, false);
	}

	public void addSourcesToMap() {

		mpSoundSourceMap.put("sweep", asLaserSource);

	}

	public void playSource(String sourceName) {

		mpSoundSourceMap.get(sourceName).play();

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
<<<<<<< HEAD

=======
>>>>>>> 70f92bb01329801f90ff8fc9972f5767101cb8c7
		try {
			AudioManager manager = new AudioManager();
			manager.playSource("sweep");
		} catch (Exception e) {
			System.out.println("Error: Unable to generate sound controller.");
			e.printStackTrace();
		}
	}
}
