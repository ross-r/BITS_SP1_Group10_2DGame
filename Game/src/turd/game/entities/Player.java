package turd.game.entities;

import org.lwjgl.glfw.GLFW;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.input.KeyboardInput;
import turd.game.input.MouseInput;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.physics.Physics;
import turd.game.entities.Projectile;
import turd.game.entities.Scrap;

public class Player extends GameObject {
	private final double DIRECTION_DOWN = 270.f;
	private final double DIRECTION_UP = 20.f;

	private final int PLAYER_BOUNDS = 64;
	
	Scrap tempScrap;
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
	
	private boolean bOnGround;
	
	private float flJumpTime;
	
	private double flDirection;
	private double flRotation;
	
	private int x;
	private int y;
	private static int MAX_AMMO = 10;
	private int iCurrentAmmo = 0;
	
	Projectile projectile;
	
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
		
		this.iCurrentAmmo = 1;
		
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
		flUpMove = 0.f;//bInMoveUp ? -1.f : bInMoveDown ? 1.f : 0.f;
		
		if ( this.bOnGround && bInJump && flJumpTime <= 0.f ) {
			// How many ticks we should jump for.
			final int iNumJumpTicks = 20;
			flJumpTime = ( 1.f / 60.f ) * iNumJumpTicks;
		}
	}

	@Override
	public void render(Window window, Graphics g) {		
		g.setColor(255.f, 255.f, 255.f, 255.f);
		g.drawFilledRect((int)aabb.p0.x, (int)aabb.p0.y, (int)aabb.p1.x, (int)aabb.p1.y);
		
		//Draws aiming line from centre of player to mouse position
		g.drawLine((int)((aabb.p0.x) + (aabb.p1.x / 2)), (int)((aabb.p0.y) + (aabb.p1.y / 2)), MouseInput.getInstance().getXPosition(window, this), (int)MouseInput.getInstance().getYPosition(window, this));
		//g.createPlayerTexture();
		
		if(projectile != null) {
			projectile.render(window, g);
		}
		if(this.tempScrap != null) {
			this.tempScrap.render(window, g);
		}
	}
	
	@Override
	public void tick(Window w) {
		input();
		
		// 'physics.gravity()' returns false when a collision has happened.
		this.bOnGround = !this.physics.gravity();
		
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
		
		if(MouseInput.getInstance().getMouseClicked() == true) {
			if(projectile == null && this.iCurrentAmmo != 0) {
				projectile = new Projectile();
				
				System.out.println("Player:" + (this.aabb.p0.x - (this.aabb.p1.x /2) + " " + ( this.aabb.p0.y - (this.aabb.p1.y / 2))));
				
				projectile.initialise((int)((aabb.p0.x) + (aabb.p1.x / 2)),(int)((aabb.p0.y) + (aabb.p1.y / 2)),
						(int)MouseInput.getInstance().getXPosition(w, this), (int)MouseInput.getInstance().getYPosition(w, this));
				this.iCurrentAmmo -= 1;
			}
		}
		
		//delete projectile if it stopped
		if(this.projectile != null) {
			
			
			if(this.projectile.getProjectileSpeed() == 0) {
				System.out.println("Projectile Deleted");
				this.tempScrap = new Scrap((int)this.projectile.aabb.p0.x, (int)this.projectile.aabb.p0.y);
				//ObjectList.getInstance().createScrap((int)this.projectile.aabb.p0.x, (int)this.projectile.aabb.p0.y);
				projectile = null;
			}
			else {
				projectile.tick(w);
			}
		}
		
		
		  if(this.tempScrap != null) { this.tempScrap.tick(w);
		  
		  if(this.tempScrap.pickedUp(this)) {
			  
			  if(this.iCurrentAmmo != MAX_AMMO) {
				  this.iCurrentAmmo += 1;
			  }
			  
		  }
		  if(this.tempScrap.getScrapSpeed() == 0) {
		  System.out.println("Scrap Deleted"); this.tempScrap = null; } }
		 
		
	}	
}
