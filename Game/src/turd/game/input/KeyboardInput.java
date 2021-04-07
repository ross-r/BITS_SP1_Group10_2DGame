package turd.game.input;

public class KeyboardInput {
	private static KeyboardInput instance = null;
	
	private static final int MAX_KEYS = 256;

	private int keyStates[] = new int[MAX_KEYS];
	
	// Singleton so don't expose a constructor.
	private KeyboardInput() {}
	
    public static KeyboardInput getInstance() {
    	if(instance == null) {
    		instance = new KeyboardInput();
    	}
    	
    	return instance;
    }

	public void updateKeys(long window, int key, int scancode, int action, int mods) {
		//System.out.printf("key: %d, action: %d, mods: %d\n", key, action, mods);
		
		// Key is out of our accepted range.
		if(key < 0 || key >= MAX_KEYS) {
			return;
		}
		
		this.keyStates[key] = action;
	}
	
	public boolean isKeyDown(int key) {
		return this.keyStates[key] == 1 || this.keyStates[key] == 2;
	}

	public boolean isKeyHeld(int key) {
		return this.keyStates[key] == 2;
	}
	
	public boolean isKeyUp(int key) {
		return this.keyStates[key] == 0;
	}
}
