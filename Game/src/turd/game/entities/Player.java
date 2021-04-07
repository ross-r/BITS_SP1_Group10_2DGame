package turd.game.entities;

import org.lwjgl.glfw.GLFW;

import turd.game.Graphics;
import turd.game.Window;
import turd.game.input.KeyboardInput;
import turd.game.objects.GameObject;

public class Player extends GameObject {
	private final int PLAYER_MOVE_SPEED = 500;
	private final int PLAYER_BOUNDS = 64;
	
	private double flPosX;
	private double flPosY;
	
	// TODO: Maybe these properties could be in a Collision class?
	// These properties define the player bounds (e.i; the width/height)
	// and can be used for collision detection based on the root position (flPosX, flPosY)
	private int iBoundsX;
	private int iBoundsY;
	
	// Movement related properties.
	private float flSideMove;
	private float flUpMove;
	
	private boolean bInMoveLeft;
	private boolean bInMoveRight;
	private boolean bInMoveUp;
	private boolean bInMoveDown;
	
	private double flDirection;
	private double flRotation;
	
	public Player() {
		this.flPosX = 0.0;
		this.flPosY = 0.0;
		
		this.iBoundsX = PLAYER_BOUNDS;
		this.iBoundsY = PLAYER_BOUNDS;
		
		this.flSideMove = 0.f;
		this.flUpMove = 0.f;
		
		this.bInMoveLeft = false;
		this.bInMoveRight = false;
		this.bInMoveUp = false;
		this.bInMoveDown = false;
		
		this.flDirection = 0.0;
		this.flRotation = 0.0;
	}
	
	@Override
	public void render(Window window, Graphics g) {
		int x = (int)Math.floor(flPosX);
		int y = (int)Math.floor(flPosY);
		
		g.setColor(255.f, 255.f, 255.f, 127.f);
		g.drawFilledRect(x, y, iBoundsY, iBoundsX);
	}

	@Override
	public void update(Window window, GAMEOBJ_UPDATE_TYPE updateType) {
		switch(updateType) {
		case KEYBOARD:
			processKeyboardInputs(window);
			break;
			
		case MOUSE:
			// TODO: Look direction (cast a ray to check collisions, etc..)
			break;
			
		case FRAME:
			processFrame(window);
			break;
		}
	}

	private void processKeyboardInputs(Window window) {
		// Update movement keys.
		bInMoveLeft = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_A);
		bInMoveRight = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_D);
		bInMoveUp = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_W);
		bInMoveDown = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_S);
		
		// Update movement directions.
		flSideMove = bInMoveLeft ? -1.f : bInMoveRight ? 1.f : 0.f;
		flUpMove = bInMoveUp ? -1.f : bInMoveDown ? 1.f : 0.f;
	}
	
	private void processFrame(Window window) {
		// Make sure we want movement to happen.
		if(flSideMove == 0.f && flUpMove == 0.f) {
			return;
		}
		
		// Compute the movement direction based on the pressed keys.
		flDirection = Math.atan2(-flSideMove, flUpMove);
		flRotation = Math.toDegrees(flDirection);
		
		// Finally adjust our players position.
		flPosX += (PLAYER_MOVE_SPEED * window.getFrameTime()) * Math.sin(-flDirection);
		flPosY += (PLAYER_MOVE_SPEED * window.getFrameTime()) * Math.cos(flDirection);
	}
}
