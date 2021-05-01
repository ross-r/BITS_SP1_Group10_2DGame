package turd.game.input;

import org.lwjgl.glfw.GLFW;

import turd.game.GameState;
import turd.game.Window;

public class KeyboardInput {
	private static KeyboardInput instance = null;
	
	private static final int MAX_KEYS = GLFW.GLFW_KEY_LAST + 1;

	private int keyStates[] = new int[MAX_KEYS];
	
	// Singleton so don't expose a constructor.
	private KeyboardInput() {}
	
    public static KeyboardInput getInstance() {
    	if(instance == null) {
    		instance = new KeyboardInput();
    	}
    	
    	return instance;
    }

	public void updateKeys(Window window, int key, int scancode, int action, int mods) {
		//System.out.printf("key: %d, action: %d, mods: %d\n", key, action, mods);
		
		// Key is out of our accepted range.
		if(key < 0 || key >= MAX_KEYS) {
			return;
		}
		
		this.keyStates[key] = action;
		
    	// Toggle paused game state.
    	if (isKeyClicked(GLFW.GLFW_KEY_ESCAPE)) {
    		GameState.getInstance().setPaused(!GameState.getInstance().isPaused());
    	}
		
		// Update all entities/objects.
		//ObjectList.getInstance().update(window, GAMEOBJ_UPDATE_TYPE.KEYBOARD);
	}
	
	public boolean isKeyClicked(int key) {
		return this.keyStates[key] == 1;
	}
	
	public boolean isKeyDown(int key) {
		return isKeyClicked(key) || this.keyStates[key] == 2;
	}

	public boolean isKeyHeld(int key) {
		return this.keyStates[key] == 2;
	}
	
	public boolean isKeyUp(int key) {
		return this.keyStates[key] == 0;
	}
}
