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

public class SoundManager {

	private long device;
	
	private long context;
	
	private SoundListener listener;
	
	private final List<SoundBuffer> buffers;
	
	private final Map <String, SoundSource> soundSourceMap;
	
	private final Matrix4f cameraMatrix;
	
	private SoundBuffer laserBuffer;
	
	private SoundSource laserSource;
	
	private Vector3f position = new Vector3f(0, 0, 0);
	
	public SoundManager() throws Exception {
		
		init();
		
		buffers = new ArrayList<>();
		
		soundSourceMap = new HashMap<>();
		
		cameraMatrix = new Matrix4f();
		
		addSoundsToList();
		
		createListener(position);
		
		createSources();
		
		System.out.println(soundSourceMap);
		
		Scanner in = new Scanner(System.in);
		String entry = in.nextLine();
		
		while (entry != "q") {
			
			if (entry == "p") {
				
				System.out.println(soundSourceMap);
				
				System.out.println("Enter name of sound to play: ");
				
				entry = in.next();
				
				playSource(entry);
				
			}
		}
		
		laserBuffer.cleanUp();
	}
	
	
	public void addSoundsToList() throws Exception {
		
		laserBuffer = new SoundBuffer("/Laser.wav");
		
		buffers.add(laserBuffer);
	}
	
	public void createListener(Vector3f position) {
		
		listener = new SoundListener();
		
		listener.setPosition(position);
		
	}
	
	public void createSources() {
		
		laserSource = new SoundSource(true, false);
	}
	
	public void addSourcesToMap() {
		
		soundSourceMap.put("laser", laserSource);
		
	}
	
	public void playSource(String sourceName) {
		
		soundSourceMap.get(sourceName).play();
		
	}
	
	public void init() throws Exception {
		
		this.device = alcOpenDevice((ByteBuffer) null);
		
		if (device == 0) {
			
			throw new IllegalStateException("Failed to open the default OpenAL device.");
			
		}
		
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		
		this.context = alcCreateContext(device, (IntBuffer) null);
		
		if (context == 0) {
			
			throw new IllegalStateException("Failed to create OpenAL context.");
			
		}
		
		alcMakeContextCurrent(context);
		
		AL.createCapabilities(deviceCaps);
		
	}
	
	public static void main(String[] args) {
		
		try {
			SoundManager manager = new SoundManager();
		} catch (Exception e) {
			System.out.println("Error: Unable to generate sound controller.");
			e.printStackTrace();
		}
	}
}
