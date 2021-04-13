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

	private final List<AudioBuffer> liBuffers;

	private final Map<String, AudioSource> mpSoundSourceMap;

	private AudioBuffer abFootstepsBuffer;

	private AudioSource asFootsteps;
	
	private AudioListener alListener;

	private Vector3f vrListenerPosition = new Vector3f(0, 0, 0);

	public AudioManager() throws Exception {

		init();

		liBuffers = new ArrayList<>();

		mpSoundSourceMap = new HashMap<>();

		addSoundsToList();

		createListener(vrListenerPosition);

		createSources();
		
		asFootsteps.play();
		abFootstepsBuffer.cleanUp();
		asFootsteps.cleanUp();
	}

	public void addSoundsToList() throws Exception {

		abFootstepsBuffer = new AudioBuffer("footsteps.ogg");

		liBuffers.add(abFootstepsBuffer);
	}

	public void createListener(Vector3f position) {

		alListener = new AudioListener();

		alListener.setPosition(position);

	}

	public void createSources() {

		asFootsteps = new AudioSource(false, true);
		
		asFootsteps.setPosition(0, 0, 0);
		asFootsteps.setGain(1);
	}

	public void addSourcesToMap() {

		mpSoundSourceMap.put("Footsteps", asFootsteps);

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
		try {
			AudioManager manager = new AudioManager();
		} catch (Exception e) {
			System.out.println("Error: Unable to generate sound controller.");
			e.printStackTrace();
		}
	}
}
