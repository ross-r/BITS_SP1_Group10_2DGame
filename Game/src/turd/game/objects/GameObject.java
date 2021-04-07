package turd.game.objects;

import turd.game.Graphics;
import turd.game.Window;

public abstract class GameObject {
	
	// Can be expanded to include further update methods.
	// At the moment this will be used to update specific
	// data during mouse/keyboard callbacks exposed by GLFW.
	public enum GAMEOBJ_UPDATE_TYPE {
		
		// Indicates the update function is invoked from keyboard callbacks.
		KEYBOARD,
		
		// Indicates the update function is invoked from mouse callbacks.
		MOUSE,
		
		// Indicates the update function is invoked from the render callback, i.e, the current 'frame'.
		FRAME
	}
	
	/*
	 * Invoked during the Windows main render routine.
	 * Draw any graphics at this stage.
	 */
	public abstract void render(Window window, Graphics g);
	
	/*
	 * General update method invoked from multiple classes.
	 * 
	 * Refer to the GAMEOBJ_UPDATE_TYPE enum to see which type to check against.
	 */
	public abstract void update(Window window, GAMEOBJ_UPDATE_TYPE updateType);
}
