package turd.game.entities;

import turd.game.Constants;
import turd.game.MathUtils;
import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.physics.Physics;
import turd.game.physics.Vec2;

public class Enemy extends GameObject {
	
	private Physics physics;
	
	private TestProjectile testProjectiles[];
	private int iProjectileCooldown;
	
	private Player player;
	
	private int iHealth;
	
	public Enemy(Vec2 position) {
		super();

		this.physics = new Physics(this);
		
		// Align the players bounding box so that x + w / 2 will be the center position of the aabb.
		// (applies to y + h / 2 too)
		//
		// this is used so that the mouse position can be centered relative to the players centered aabb.
		this.aabb.init(position.x - ( Constants.PLAYER_BOUNDS / 2 ), position.y - ( Constants.PLAYER_BOUNDS / 2 ), Constants.PLAYER_BOUNDS, Constants.PLAYER_BOUNDS);
		
		// The player entity is always the enemies target.
		this.player = ObjectList.getInstance().getPlayer();
		
		this.iProjectileCooldown = 0;
	
		// 20 health.
		this.iHealth = Constants.PLAYER_MAX_HEALTH / 5;
	}
	
	// Called from ObjectList method 'createPlayer'
	// use this to register additional game objects, prevents exceptions being thrown.
	public void initialize() {
		this.testProjectiles = new TestProjectile[ Constants.MAX_PROJECTILES ];
		
		for( int i = 0; i < Constants.MAX_PROJECTILES; i++ ) {
			this.testProjectiles[ i ] = (TestProjectile) ObjectList.getInstance().createEntityObject(new TestProjectile());
		}
	}
	
	@Override
	public void render(Window window, Graphics g) {
		final int x = (int)aabb.p0.x;
		final int y = (int)aabb.p0.y;
		final int w = (int)aabb.p1.x;
		final int h = (int)aabb.p1.y;
		
		g.setColor(255.f, 0.f, 255.f, 255.f);
		g.drawFilledRect(x, y, w, h);
	}

	@Override
	public void tick(Window w) {
		this.physics.gravity();
		
		if( this.iProjectileCooldown > 0 ) {
			this.iProjectileCooldown--;
		}
		
		// TODO: Make it so the enemy moves around, aka a 'roaming' mode.
		
		// Calculate the distance to the player and don't attack if they're too far away.
		float flDeltaX = this.aabb.getCenterX() - this.player.aabb.getCenterX();
		float flDeltaY = this.aabb.getCenterY() - this.player.aabb.getCenterY();
		
		// Calculate the root of the magnitude which is the 'distance' between each object.
		float flLengthSqr = ( float ) Math.sqrt( ( flDeltaX * flDeltaX ) + ( flDeltaY * flDeltaY ) );
		if( flLengthSqr > 512.f ) {
			return;
		}

		// Figure out which projectile we should fire.
		int iProjectileIndex = -1;
		for( int i = 0; i < Constants.MAX_PROJECTILES; i++ ) {
			if( this.testProjectiles[ i ].isInitialized() ) {
				continue;
			}
			
			iProjectileIndex = i;
			break;
		}
		
		// If we have a projectile we can shoot.
		if( iProjectileIndex >= 0 && iProjectileIndex < Constants.MAX_PROJECTILES ) {
					
			TestProjectile testProjectile = this.testProjectiles[ iProjectileIndex ];
			
			if( this.iProjectileCooldown == 0 ) {
				
				Vec2 direction = MathUtils.calcDirBetweenGameObjects(this, this.player);
				
				// Compute center coordinates of our aabb.
				final float flCenterX = this.aabb.p0.x + ( this.aabb.p1.x / 2 );
				final float flCenterY = this.aabb.p0.y + ( this.aabb.p1.y / 2 );
				
				// Compute the position and move it out of the players bounding box slightly.
				// This prevents the projectile getting stuck on the entity shooting it.
				Vec2 position = new Vec2( 
					flCenterX + ( direction.x * Constants.PLAYER_BOUNDS ), 
					flCenterY + ( direction.y * Constants.PLAYER_BOUNDS ) 
				);
				
				Vec2 velocity = new Vec2( 10.f, 10.f );
				
				testProjectile.initialize(position, direction, velocity);
				
				this.iProjectileCooldown = MathUtils.convertMillisecondsToGameTicks( 1000 );
			}
			
		}
		
	}

	private void onTakeDamage() {
		if( this.iHealth <= 0 ) {
			return;
		}
		
		// We can apply a multiplier for things like damage resistance and so on.
		this.iHealth -= Constants.PROJECTILE_BASE_DAMAGE;
		
		// If we are going to die to this projectile, spawn some scrap on the ground as a reward to the player.
		if( this.iHealth <= 0 ) {
			
			final int iMinScrap = 3;
			final int iMaxScrap = 10;
			final int iRandScrapCount = ( int ) MathUtils.randomInRange( iMinScrap, iMaxScrap );
			
			for( int i = 0; i < iRandScrapCount; i++ ) {
				
				float flScrapX = this.aabb.p0.x;
				float flScrapY = this.aabb.p0.y;
				
				// Offset the x, y coordinates randomly to make it appear the scrap 'explodes' out of the enemy.
				flScrapX += MathUtils.randomInRange( -30.f, 30.f );
				flScrapY -= MathUtils.randomInRange( 20.f, 	50.f );
				
				ObjectList.getInstance().registerQueuedObject( new Scrap( ( int )flScrapX, ( int )flScrapY ) );
			}
		}
	}
	
	@Override
	public void onCollision(GameObject object) {
		if( object instanceof TestProjectile ) {
			onTakeDamage();
		}
	}
	
	@Override
	public boolean shouldDelete() {
		return this.iHealth <= 0;
	}
}
