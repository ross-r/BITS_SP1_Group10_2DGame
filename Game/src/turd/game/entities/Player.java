package turd.game.entities;

import org.lwjgl.glfw.GLFW;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.input.KeyboardInput;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.objects.StaticObject;

public class Player extends GameObject {
	private final int PLAYER_MOVE_SPEED = 500;
	private final int PLAYER_BOUNDS = 64;
	
	// Absolute position that isn't restricted to pixels.
	private double flPosX;
	private double flPosY;
	
	private int iOldX;
	private int iOldY;

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
		super();
		
		this.flPosX = 0.0;  
		this.flPosY = 0.0;
				
		this.flSideMove = 0.f;
		this.flUpMove = 0.f;
		
		this.bInMoveLeft = false;
		this.bInMoveRight = false;
		this.bInMoveUp = false;
		this.bInMoveDown = false;
		
		this.flDirection = 0.0;
		this.flRotation = 0.0;
	
		this.setBounds(PLAYER_BOUNDS, PLAYER_BOUNDS);
	}
	
	@Override
	public void render(Window window, Graphics g) {
		g.setColor(255.f, 255.f, 255.f, 127.f);
		g.drawFilledRect(getX(), getY(), getWidth(), getHeight());
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
		iOldX = this.getX();
		iOldY = this.getY();
		
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
		
		// Update GameObject position.
		this.setPos((int)Math.floor(flPosX), (int)Math.floor(flPosY));
		
		// Check if the player will collide with other obstacles.
		for(StaticObject obstacle : ObjectList.getInstance().getStaticObjects()) {
			if(willCollide(obstacle)) {
				
				// Block the player from moving any further this frame.
				// Reset the players position to the previous frames.
				this.block();
			}
		}
	}

	private void block() {
		
		// TODO: Check which coordinate we should specifically block.
		// For example, if we are moving along the x axis and bump into an obstacle on the y axis
		// then if we hold movement keys such that the x and y axis both push into an obstacle
		// the player will become completely stuck, instead only the x axis should stop and still allow
		// the y axis to advance.
		
		flPosX = iOldX;
		flPosY = iOldY;
		this.setPos(iOldX, iOldY);
	}
}
