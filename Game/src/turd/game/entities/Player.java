package turd.game.entities;

import org.lwjgl.glfw.GLFW;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.input.KeyboardInput;
import turd.game.objects.GameObject;
import turd.game.physics.Physics;

public class Player extends GameObject {
	private final double DIRECTION_DOWN = 270.f;
	private final double DIRECTION_UP = 90.f;

	private final int PLAYER_BOUNDS = 64;
	
	Physics physics;
	
	// Movement related properties.
	private float flSideMove;
	private float flUpMove;
	
	private boolean bInMoveLeft;
	private boolean bInMoveRight;
	private boolean bInMoveUp;
	private boolean bInMoveDown;
	private boolean bInMoveSpeed;
	private boolean bInJump;
	
	private float flJumpTime;
	
	private double flDirection;
	private double flRotation;
	
	private int x;
	private int y;
	
	public Player() {
		super();
		
		// Initialize physics for this entity.
		this.physics = new Physics(this);
		
		this.flSideMove = 0.f;
		this.flUpMove = 0.f;
		
		this.bInMoveLeft = false;
		this.bInMoveRight = false;
		this.bInMoveUp = false;
		this.bInMoveDown = false;
		
		this.flDirection = 0.0;
		this.flRotation = 0.0;
		
		this.aabb.init(0.f, 0.f, PLAYER_BOUNDS, PLAYER_BOUNDS);
	}

	private void input() {
		// Update movement keys.
		bInMoveLeft = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_A);
		bInMoveRight = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_D);
		bInMoveUp = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_W);
		bInMoveDown = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_S);
		bInMoveSpeed = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_J);
		bInJump = KeyboardInput.getInstance().isKeyClicked(GLFW.GLFW_KEY_SPACE);
		
		// Update movement directions.
		flSideMove = bInMoveLeft ? -1.f : bInMoveRight ? 1.f : 0.f;
		flUpMove = bInMoveUp ? -1.f : bInMoveDown ? 1.f : 0.f;
		
		if ( bInJump && flJumpTime <= 0.f ) {
			// How many ticks we should jump for.
			final int iNumJumpTicks = 20;
			flJumpTime = ( 1.f / 60.f ) * iNumJumpTicks;
		}
	}

	@Override
	public void render(Window window, Graphics g) {		
		g.setColor(255.f, 255.f, 255.f, 255.f);
		g.drawFilledRect((int)aabb.p0.x, (int)aabb.p0.y, (int)aabb.p1.x, (int)aabb.p1.y);
		//g.createPlayerTexture();
	}
	
	@Override
	public void tick(Window w) {
		input();
		
		this.physics.gravity();

		float PLAYER_SPEED = bInMoveSpeed ? 12.f : 4.f;
		float PLAYER_JUMP_SPEED = bInMoveSpeed ? 8.f : 4.f;
		
		if ( flJumpTime > 0.f ) {
			flJumpTime -= ( 1.f / 60.f );
			
			// Negative value here since we want to go upwards.
			flUpMove = -2.f;
			
			// Negate regular movement speed and multiply by desired jump speed.
			flUpMove /= PLAYER_SPEED;
			flUpMove *= PLAYER_JUMP_SPEED;
		}
		
		// This is really bad lol
		// The reason these calls are separated is because both x and y are checked in collision and if ONE of them fails
		// both are ignored for the current tick, if they are separate calls then this *issue* is *avoided*.
		this.physics.move(this.aabb.p0.x + ( flSideMove * PLAYER_SPEED ), this.aabb.p0.y);
		this.physics.move(this.aabb.p0.x, this.aabb.p0.y + ( flUpMove * PLAYER_SPEED ));
	}	
}
