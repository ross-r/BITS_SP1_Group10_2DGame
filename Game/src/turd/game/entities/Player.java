package turd.game.entities;

import org.lwjgl.glfw.GLFW;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.graphics.Texture;
import turd.game.input.KeyboardInput;
import turd.game.input.MouseInput;
import turd.game.objects.GameObject;
import turd.game.physics.Physics;
import turd.game.entities.Projectile;

public class Player extends GameObject {
	private final int PLAYER_BOUNDS = 64;

	// Movement variables.
	private final float PLAYER_MOVE_SPEED = 4.F;
	private final float PLAYER_MOVE_SPEED_MULTIPLIER = 3.F; // ( PLAYER_MOVE_SPEED * x )
	private final float PLAYER_JUMP_SPEED_MULTIPLIER = 0.5F; // ( PLAYER_MOVE_SPEED * x )

	Scrap tempScrap;
	Physics physics;

	// Movement related properties.
	private float flSideMove;
	private float flUpMove;

	private boolean bInMoveLeft;
	private boolean bInMoveRight;
	private boolean bInMoveSpeed;
	private boolean bInJump;

	private boolean bOnGround;

	private float flMoveSpeed;
	private float flMoveSpeedBonusMultiplier;
	private float flJumpSpeed;
	private float flJumpTime;

	private int iFallTicks;

	Projectile projectile;

	Texture texPlayerIdle;

	public Player() {
		super();

		// Initialize physics for this entity.
		this.physics = new Physics(this);

		this.flSideMove = 0.f;
		this.flUpMove = 0.f;

		this.bInMoveLeft = false;
		this.bInMoveRight = false;

		this.flMoveSpeed = 1.f;
		this.flMoveSpeedBonusMultiplier = 1.f;
		this.flJumpTime = 0.f;

		this.aabb.init(0.f, 0.f, PLAYER_BOUNDS, PLAYER_BOUNDS);

		// Create textures.
		texPlayerIdle = new Texture( Graphics.nvgHandle(), "player.png" );
	}

	private void input() {
		// Update movement keys.
		bInMoveLeft = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_A);
		bInMoveRight = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_D);

		//bInMoveUp = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_W);
		//bInMoveDown = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_S);

		bInMoveSpeed = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_J);
		bInJump = KeyboardInput.getInstance().isKeyClicked(GLFW.GLFW_KEY_SPACE);

		// Update movement directions.
		flSideMove = bInMoveLeft ? -1.f : bInMoveRight ? 1.f : 0.f;
		flUpMove = 0.f;//bInMoveUp ? -1.f : bInMoveDown ? 1.f : 0.f;

		if ( this.bOnGround && bInJump && flJumpTime <= 0.f ) {
			// How many ticks we should jump for.
			// We multiply by a fixed value here to keep the jump height consistent even when we are moving faster.
			final float flJumpTicks = 48.f * ( bInMoveSpeed ? 0.5f : 1.f );

			flJumpTime = ( 1.f / 60.f ) * Math.round(flJumpTicks);
		}
	}

	@Override
	public void render(Window window, Graphics g) {
		final int x = (int)aabb.p0.x;
		final int y = (int)aabb.p0.y;
		final int w = (int)aabb.p1.x;
		final int h = (int)aabb.p1.y;

		if( this.bInMoveSpeed || this.flMoveSpeedBonusMultiplier != 1.F ) {
			final int trail = 5;

			// trail - 1 because the last trail is 0 opacity.
			for( int i = 0; i < trail - 1; i++ ) {
				int newX = (int)aabb.p0.x;
				int newY = (int)aabb.p0.y;

				float flUpMove = Math.max(this.flUpMove, -1.F);
				if( !this.bOnGround && this.flUpMove == 0.f ) {

					// Assume that gravity is pushing the player down.
					flUpMove = 1.f;
				}

				int offsetX = ( ( i + 1 ) * ( PLAYER_BOUNDS / 4 ) ) * ( int )this.flSideMove;
				int offsetY = ( ( i + 1 ) * ( PLAYER_BOUNDS / 4 ) ) * ( int )flUpMove;

				newX += offsetX * -1;
				newY += offsetY * -1;

				float alpha = 255.f - (( i + 1 ) * (255.f/trail));

				texPlayerIdle.render(newX, newY, w, h, alpha);
			}
		}

		texPlayerIdle.render(x, y, w, h, 255.f);

		// Draws aiming line from centre of player to mouse position
		g.setColor(255.f, 255.f, 255.f, 255.f);
		g.drawLine(x + w / 2, y + h / 2, MouseInput.getInstance().getXPosition(window, this), (int)MouseInput.getInstance().getYPosition(window, this));

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

		if( !this.bOnGround ) {
			iFallTicks++;
		}
		else {
			iFallTicks = 0;
		}

		// If we are falling for more than 4 seconds (60 ticks per second)
		// reset our position so we don't stay off-screen.
		if( iFallTicks >= 60*4 ) {
			this.aabb.p0.x = 0;
			this.aabb.p0.y = 0;
			iFallTicks = 0;
		}

		this.flMoveSpeed = this.bInMoveSpeed ? ( PLAYER_MOVE_SPEED * PLAYER_MOVE_SPEED_MULTIPLIER ) : PLAYER_MOVE_SPEED;

		// 'flMoveSpeedBonusMultiplier' is the variable we would write to when we want to gain a bonus to movement speed outside of the
		// default movement speed limitations (i.e; move speed, jump speed, jump height) - this value is a multiplier so it scales,
		// a value of 1 will have no change, < 1 will slow us, > 1 will make us faster.
		this.flMoveSpeed *= this.flMoveSpeedBonusMultiplier;

		this.flJumpSpeed = this.bInMoveSpeed ? PLAYER_JUMP_SPEED_MULTIPLIER : 1.f;

		if ( this.flJumpTime > 0.f ) {
			this.flJumpTime -= ( 1.f / 60.f );

			// Negative value here since we want to go upwards.
			this.flUpMove = -2.f;

			// Negate regular movement speed and multiply by desired jump speed.
			this.flUpMove /= this.flMoveSpeed;
			this.flUpMove *= ( this.flMoveSpeed * this.flJumpSpeed );
		}

		// This is really bad lol
		// The reason these calls are separated is because both x and y are checked in collision and if ONE of them fails
		// both are ignored for the current tick, if they are separate calls then this *issue* is *avoided*.
		this.physics.move(this.aabb.p0.x + ( this.flSideMove * this.flMoveSpeed ), this.aabb.p0.y);
		this.physics.move(this.aabb.p0.x, this.aabb.p0.y + ( this.flUpMove * this.flMoveSpeed ));

		if(MouseInput.getInstance().getMouseClicked() == true) {
			if(projectile == null) {
				System.out.println("Projectile");
				projectile = new Projectile();

				System.out.println("Player:" + (this.aabb.p0.x - (this.aabb.p1.x /2) + " " + ( this.aabb.p0.y - (this.aabb.p1.y / 2))));
				projectile.initialise((int)(this.aabb.p0.x - (this.aabb.p1.x /2)),(int)( this.aabb.p0.y - (this.aabb.p1.y / 2)),
						(int)MouseInput.getInstance().getXPosition(w, this), (int)MouseInput.getInstance().getYPosition(w, this));
			}
		}

		//delete projectile if it stopped
		if(this.projectile != null) {


			if(this.projectile.getProjectileSpeed() == 0) {
				System.out.println("Projectile Deleted");
				projectile = null;
			}
			else {
				projectile.tick(w);
			}
		}

	}
}
