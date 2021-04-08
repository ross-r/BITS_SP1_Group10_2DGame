package turd.game.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.openal.ALC10.*;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import org.joml.Matrix4f;

public class SoundManager {

	private long device;
	
	private long context;
	
	private SoundListener listener;
	
	private final List<SoundBuffer> soundBufferList;
	
	private final Map <String, SoundSource> soundSourceMap;
	
	private final Matrix4f cameraMatrix;
	
	public SoundManager() {
		
		soundBufferList = new ArrayList<>();
		
		soundSourceMap = new HashMap<>();
		
		cameraMatrix = new Matrix4f();
		
	}
	
	public void init() throws Exception {
		
		this.device = alcOpenDevice((ByteBuffer) null);
		
		if (device == NULL) {
			
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
	
	public void addBuffers() {
		
		soundBufferList.add("laser.wav");
		
	}
	
	public void addToBufferList(SoundBuffer buffer) {
		
		soundBufferList.add(buffer);
		
	}
	
	public void playAudio(String bufferName) {

		for (int i = 0; i < ((CharSequence) soundBufferList).length(); i++) {
			
			if (soundBufferList.contains(bufferName)) {
				
			}
		}
	}
	
	
}
