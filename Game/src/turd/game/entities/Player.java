package turd.game.entities;

import org.lwjgl.glfw.GLFW;

import turd.game.GameState;
import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.input.KeyboardInput;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.objects.StaticObject;

public class Player extends GameObject {
	private final double DIRECTION_DOWN = 270.f;
	private final double DIRECTION_UP = 90.f;
	
	private final int PLAYER_MOVE_SPEED = 200;
	private final int PLAYER_BOUNDS = 64;
	
	//
	private final float PLAYER_BASE_GRAVITY = 200.f;
	private final float PLAYER_JUMP_DECAY = 200.f;
	private final float PLAYER_JUMP_HEIGHT = 200.f;
	
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
	
	// Jumping.
	private boolean bInJump;
	private float flJumpImpulse;
	
	private double flDirection;
	private double flRotation;
	
	//
	private double flFrameTime;
	
	private double flSpeedModifier = 1.f;
	
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
		
		this.flFrameTime = 0.0;
	
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
			gravity();
			break;
		}
	}

	private void processKeyboardInputs(Window window) {
		// Update movement keys.
		bInMoveLeft = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_A);
		bInMoveRight = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_D);
		bInMoveUp = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_W);
		bInMoveDown = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_S);
		
		// Jumping.
		bInJump = KeyboardInput.getInstance().isKeyClicked(GLFW.GLFW_KEY_SPACE);
		if (bInJump) {
			
			// The jump impulse is the upwards momentum that will be given.
			// If this impulse is not depleted, then the player should not jump again.
			if (flJumpImpulse <= 0.f) {
				
				// Some bogus initial jump impulse for the time being.
				// We can modify this later to alter jump height based on other attributes.
				flJumpImpulse = 200.f;
			}
			
		}
		
		// Update movement directions.
		flSideMove = bInMoveLeft ? -1.f : bInMoveRight ? 1.f : 0.f;
		flUpMove = bInMoveUp ? -1.f : bInMoveDown ? 1.f : 0.f;
	}
	
	private void processFrame(Window window) {
		int iOldX = this.getX();
		double flOldX = flPosX;
		
		// Update frame time from the game state (this handles pausing for us)
		this.flFrameTime = GameState.getInstance().getFrameTime();
		
		// Make sure we want movement to happen.
		if(flSideMove == 0.f && flUpMove == 0.f) {
			return;
		}
		
		// Compute the movement direction based on the pressed keys.
		flDirection = Math.atan2(-flSideMove, flUpMove);
		flRotation = Math.toDegrees(flDirection);
		
		// Finally adjust our players position.
		flPosX += ((PLAYER_MOVE_SPEED * this.flSpeedModifier) * this.flFrameTime) * Math.sin(-flDirection);
		
		// Update GameObject position.
		this.setX((int)Math.floor(flPosX));
		
		// Now that the y coordinate was updated we can check for collisions.
		if (this.checkCollisions()) {
			
			// A collision has happened.
			// We need to restore our coordinates to the state prior to when we modified them.
			
			flPosX = flOldX;
			this.setX(iOldX);
			
			return;
		}
		
		this.setX((int)Math.floor(flPosX));
	}
	
	private boolean checkCollisions() {
		for(StaticObject obstacle : ObjectList.getInstance().getStaticObjects()) {
			if (willCollide(obstacle)) {
				return true;
			}
		}
		
		return false;
	}

	private void gravity() {
		int iOldY = this.getY();
		double flOldY = flPosY;
		
		// Check if we should be jumping.
		if (this.flJumpImpulse > 0.F) {
			
			// (PLAYER_MOVE_SPEED * this.flSpeedModifier)
			
			final double flJumpSpeedModifier = 2.f;
			final double flJumpHeightModifier = 1.f;			
			final double flJumpHeight = (PLAYER_JUMP_HEIGHT * this.flSpeedModifier) * flJumpHeightModifier;
			final double flJumpSpeed = flJumpSpeedModifier;
			
			flPosY += ((flJumpHeight * this.flFrameTime) * Math.cos(DIRECTION_UP)) * flJumpSpeedModifier;
			
			// Decay the jump impulse over some fixed time span.
			this.flJumpImpulse -= (PLAYER_JUMP_DECAY * this.flFrameTime);
		}
		else {
		
			// Apply gravity to the players y coordinate.
			// Continuously moves them downwards.
			flPosY += (PLAYER_BASE_GRAVITY * this.flFrameTime) * Math.cos(DIRECTION_DOWN);
		}
		
		// Update internal y coordinate since that is used in collision detections.
		this.setY((int)Math.floor(flPosY));
		
		// Now that the y coordinate was updated we can check for collisions.
		if (this.checkCollisions()) {
			
			// A collision has happened.
			// We need to restore our coordinates to the state prior to when we modified them.
			
			flPosY = flOldY;
			this.setY(iOldY);
			
			return;
		}
		
		this.setY((int)Math.floor(flPosY));
	}
}
