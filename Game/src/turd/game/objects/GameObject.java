package turd.game.objects;

import turd.game.Graphics;
import turd.game.Window;

public abstract class GameObject {
	
	private int x;
	private int y;
	private int width;
	private int height;
	
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

	/*
	 * Checks if two game objects will collide via box intersection.
	 */
	public boolean willCollide(GameObject other) {
		if((this.y + this.height) <= other.getY()) {
			return false;
		}

		if(this.getY() >= (other.getY() + other.getHeight())) {
			return false;
		}
		
		if((this.x + this.width) <= other.getX()) {
			return false;
		}
		
		if(this.getX() >= (other.getX() + other.getWidth())) {
			return false;
		}

		return true;
	}
	
	/*
	 * Getters / Setters.
	 */
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int w) {
		this.width = w;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int h) {
		this.height = h;
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setBounds(int w, int h) {
		this.width = w;
		this.height = h;
	}
}
