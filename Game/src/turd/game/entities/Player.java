package turd.game.entities;

import org.lwjgl.glfw.GLFW;

import turd.game.MathUtils;
import turd.game.Window;
import turd.game.audio.Audio;
import turd.game.graphics.Graphics;
import turd.game.graphics.Texture;
import turd.game.input.KeyboardInput;
import turd.game.input.MouseInput;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.physics.Physics;
import turd.game.physics.Vec2;

public class Player extends GameObject {
	private final int PLAYER_BOUNDS = 64;

	// Movement variables.
	private final float PLAYER_MOVE_SPEED = 4.F;
	private final float PLAYER_MOVE_SPEED_MULTIPLIER = 3.F; // ( PLAYER_MOVE_SPEED * x )
	private final float PLAYER_JUMP_SPEED_MULTIPLIER = 0.5F; // ( PLAYER_MOVE_SPEED * x )

	private final int MAX_SCRAP_VALUE = 7;
	
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
	private int iAnimateTicks; //leo - used for temporarily animating wheels
	private int iAnimateTickTimer;
	
	private int iScrapValue; //leo - used for temporarily counting projectiles

	Projectile projectile;

	Texture texPlayerIdle;
	Texture texPlayerLeft1;
	Texture texPlayerLeft2;
	Texture texPlayerRight1;
	Texture texPlayerRight2;
	Texture texPlayerJumpLeft;
	Texture texPlayerJumpRight;
	Texture texture;
	private final int MAX_PROJECTILES = 40;
	private TestProjectile testProjectiles[];
	private int iProjectileCooldown;

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

		// Align the players bounding box so that x + w / 2 will be the center position of the aabb.
		// (applies to y + h / 2 too)
		//
		// this is used so that the mouse position can be centered relative to the players centered aabb.
		this.aabb.init(-( PLAYER_BOUNDS / 2 ), -( PLAYER_BOUNDS / 2 ), PLAYER_BOUNDS, PLAYER_BOUNDS);

		// Create textures.
		texPlayerIdle = new Texture( Graphics.nvgHandle(), "player_idle.png" );
		texPlayerLeft1 = new Texture( Graphics.nvgHandle(), "player_left1.png");
		texPlayerLeft2 = new Texture( Graphics.nvgHandle(), "player_left2.png");
		texPlayerRight1 = new Texture( Graphics.nvgHandle(), "player_right1.png");
		texPlayerRight2 = new Texture( Graphics.nvgHandle(), "player_right2.png");
		texPlayerJumpLeft = new Texture( Graphics.nvgHandle(), "player_jump_left.png");
		texPlayerJumpRight = new Texture( Graphics.nvgHandle(), "player_jump_right.png");
		texture = this.texPlayerIdle;
	
		//temporary health/ammo value
		iScrapValue = 7;
		
		this.iProjectileCooldown = 0;
	}
	
	// Called from ObjectList method 'createPlayer'
	// use this to register additional game objects, prevents exceptions being thrown.
	public void initialize() {
		this.testProjectiles = new TestProjectile[ this.MAX_PROJECTILES ];
		
		for( int i = 0; i < this.MAX_PROJECTILES; i++ ) {
			this.testProjectiles[ i ] = (TestProjectile) ObjectList.getInstance().createEntityObject(new TestProjectile());
		}
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

	private void drawTrail(Texture texture) {
		final int x = (int)aabb.p0.x;
		final int y = (int)aabb.p0.y;
		final int w = (int)aabb.p1.x;
		final int h = (int)aabb.p1.y;
		
		if( this.bInMoveSpeed || this.flMoveSpeedBonusMultiplier != 1.F ) {
			final int trail = 5;

			// trail - 1 because the last trail is 0 opacity.
			for( int i = 0; i < trail - 1; i++ ) {
				int newX = x;
				int newY = y;

				float flUpMove = Math.max(this.flUpMove, -1.F);
				if( !this.bOnGround && this.flUpMove == 0.f ) {

					// Assume that gravity is pushing the player down.
					flUpMove = 1.f;
				}

				int offsetX = ( ( i + 1 ) * ( w / 4 ) ) * ( int )this.flSideMove;
				int offsetY = ( ( i + 1 ) * ( h / 4 ) ) * ( int )flUpMove;

				newX += offsetX * -1;
				newY += offsetY * -1;

				float alpha = 255.f - (( i + 1 ) * (255.f/trail));

				texture.render(newX, newY, w, h, alpha);
			}
		}
	}
	
	@Override
	public void render(Window window, Graphics g) {
		final int x = (int)aabb.p0.x;
		final int y = (int)aabb.p0.y;
		final int w = (int)aabb.p1.x;
		final int h = (int)aabb.p1.y;
		
		if (bInMoveLeft) {
			Audio.getInstance().playerMovePlay();
			if (this.flJumpTime > 0.f) {
				texture = texPlayerJumpLeft;
			} else {
				if (iAnimateTicks == 1) {
					texture = texPlayerLeft1;
				} else if (iAnimateTicks == 0) {
					texture = texPlayerLeft2;
				}
			}
		} else if (bInMoveRight) {
			Audio.getInstance().playerMovePlay();
			if (this.flJumpTime > 0.f) {
				texture = texPlayerJumpRight;
			} else {
				if (iAnimateTicks == 1) {
					texture = texPlayerRight1;
				} else if (iAnimateTicks == 0) {
					texture = texPlayerRight2;
				}
			}
		} else {
			Audio.getInstance().stop("playerMove");
			//texture = texPlayerIdle;
		}
		
		drawTrail(texture);
		
		texture.render(x, y, w, h, 255.f);

		// Draws aiming line from centre of player to mouse position
		g.setColor(255.f, 255.f, 255.f, 255.f);
		//g.drawLine(x + w / 2, y + h / 2, MouseInput.getInstance().getXPosition(window, this), (int)MouseInput.getInstance().getYPosition(window, this));

		if(projectile != null) {
			projectile.render(window, g);
		}

		if(this.tempScrap != null) {
			this.tempScrap.render(window, g);
		}

	}

	@Override
	public void tick(Window window) {
		iAnimateTickTimer++;
		
		// Decides the frequency at which the wheels animation changes.
		final int iAnimationMod = this.bInMoveSpeed ? 2 : 4;
		if( iAnimateTickTimer % iAnimationMod == 0 ) {
			iAnimateTicks++;
			if( iAnimateTicks == 2 ) {
				iAnimateTicks = 0;
			}
			
			iAnimateTickTimer = 0;
		}
		
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
			//this.aabb.p0.x = 0;
			//this.aabb.p0.y = 0;
			iFallTicks = 0;
		}

		this.flMoveSpeed = this.bInMoveSpeed ? ( PLAYER_MOVE_SPEED * PLAYER_MOVE_SPEED_MULTIPLIER ) : PLAYER_MOVE_SPEED;

		// 'flMoveSpeedBonusMultiplier' is the variable we would write to when we want to gain a bonus to movement speed outside of the
		// default movement speed limitations (i.e; move speed, jump speed, jump height) - this value is a multiplier so it scales,
		// a value of 1 will have no change, < 1 will slow us, > 1 will make us faster.
		this.flMoveSpeed *= this.flMoveSpeedBonusMultiplier;

		this.flJumpSpeed = this.bInMoveSpeed ? PLAYER_JUMP_SPEED_MULTIPLIER : 1.f;

		if ( this.flJumpTime > 0.f ) {
			
			if (!Audio.getInstance().getPlaying("playerJump")) {
				Audio.getInstance().play("playerJump");
			}
		
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

		//
		//
		//
		
		this.iProjectileCooldown--;
		if( MouseInput.getInstance().getMouseClicked() ) {
			if (!Audio.getInstance().getPlaying("playerShoot"))
			Audio.getInstance().play("playerShoot");
			this.shoot(window);
		}
		
		/*
		if(MouseInput.getInstance().getMouseClicked() == true) {
			if(projectile == null) {
				System.out.println("Projectile");
				projectile = new Projectile();

				System.out.println("Player:" + (this.aabb.p0.x - (this.aabb.p1.x /2) + " " + ( this.aabb.p0.y - (this.aabb.p1.y / 2))));
				projectile.initialise((int)(this.aabb.p0.x - (this.aabb.p1.x /2)),(int)( this.aabb.p0.y - (this.aabb.p1.y / 2)),
						(int)MouseInput.getInstance().getXPosition(w, this), (int)MouseInput.getInstance().getYPosition(w, this));
			
				iScrapValue --; //leo, temporarily de-incrementing how many scrap the player has
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
		*/
	}
	
	private void shoot(Window window) {

		// Do we have ammo to use?
		if( this.iScrapValue == 0 ) {
			return;
		}
		
		if( this.iProjectileCooldown > 0 ) {
			return;
		}
		
		// Figure out which projectile we should fire.
		int iProjectileIndex = -1;
		for( int i = 0; i < this.MAX_PROJECTILES; i++ ) {
			if( this.testProjectiles[ i ].isInitialized() ) {
				continue;
			}
			
			iProjectileIndex = i;
			break;
		}
		
		// No projectiles found to fire.
		if( iProjectileIndex == -1 ) {
			return;
		}
	
		TestProjectile testProjectile = this.testProjectiles[ iProjectileIndex ];
						
		Vec2 direction = MathUtils.calcDirFromGameObjectToMouse(window, this);
		
		// Compute center coordinates of our aabb.
		final float flCenterX = this.aabb.p0.x + ( this.aabb.p1.x / 2 );
		final float flCenterY = this.aabb.p0.y + ( this.aabb.p1.y / 2 );
		
		// Compute the position and move it out of the players bounding box slightly.
		// This prevents the projectile getting stuck on the entity shooting it.
		Vec2 position = new Vec2( 
			flCenterX + ( direction.x * PLAYER_BOUNDS ), 
			flCenterY + ( direction.y * PLAYER_BOUNDS ) 
		);
		
		Vec2 velocity = new Vec2( 10.f, 10.f );
		
		testProjectile.initialize(position, direction, velocity);
		
		// TODO: It would be a good idea to add a delay between projectile shots
		// this delay could be affected by some of our EVF's (such as speed alter, etc..)
	
		// Since we have shot a projectile, consume ammo.
		this.iScrapValue--;
		
		this.iProjectileCooldown = MathUtils.convertMillisecondsToGameTicks( 1000 );
	}
	
	@Override
	public void onCollision(GameObject object) {
		if( object instanceof TestProjectile ) {
			
			//System.out.println("Player has had a projectile collide with their aabb - object: " + object);
		}
	}
	
	//players health/ammunition value (scrap)
	public int getScrapValue() {
		return iScrapValue;
	}
	
	public boolean collectScrap() {
		if( this.iScrapValue == MAX_SCRAP_VALUE ) {
			// Can't collect scrap as we have max already.
			return false;
		}
		
		++this.iScrapValue;
		
		Audio.getInstance().play("playerPickUp");
		
		return true;
		
	}
	
	public void setScrapValue(int iScrapValue) {
		if( iScrapValue > MAX_SCRAP_VALUE ) {
			this.iScrapValue = MAX_SCRAP_VALUE;
			return;
		}
		
		this.iScrapValue = iScrapValue;
	}
	
	public void incrementScrapValue() {
		if( this.iScrapValue == MAX_SCRAP_VALUE ) {
			return;
		}
		
		++this.iScrapValue;
	}
	
	public boolean hasMaxScrap() {
		return this.iScrapValue == MAX_SCRAP_VALUE;
	}
}
