package turd.game.entities;

import turd.game.MathUtils;
import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.input.MouseInput;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.physics.Physics;
import turd.game.physics.Vec2;

public class Enemy extends GameObject {
	
	private final int PLAYER_BOUNDS = 64;

	private final int MAX_PROJECTILES = 40;

	private Physics physics;
	
	private int iCurrentProjectileIndex;
	private TestProjectile testProjectiles[];
	private int iProjectileCooldown;
	
	private Player player;
	
	public Enemy(Vec2 position) {
		super();

		this.physics = new Physics(this);
		
		// Align the players bounding box so that x + w / 2 will be the center position of the aabb.
		// (applies to y + h / 2 too)
		//
		// this is used so that the mouse position can be centered relative to the players centered aabb.
		this.aabb.init(position.x - ( PLAYER_BOUNDS / 2 ), position.y - ( PLAYER_BOUNDS / 2 ), PLAYER_BOUNDS, PLAYER_BOUNDS);
		
		// The player entity is always the enemies target.
		this.player = ObjectList.getInstance().getPlayer();
		
		this.iCurrentProjectileIndex = 0;
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

		// Figure out which projectile we should fire.
		int iProjectileIndex = -1;
		for( int i = 0; i < this.MAX_PROJECTILES; i++ ) {
			if( this.testProjectiles[ i ].isInitialized() ) {
				continue;
			}
			
			iProjectileIndex = i;
			break;
		}
		
		// If we have a projectile we can shoot.
		if( iProjectileIndex >= 0 && iProjectileIndex < this.MAX_PROJECTILES ) {
		
			this.iCurrentProjectileIndex = iProjectileIndex;
			
			TestProjectile testProjectile = this.testProjectiles[ iProjectileIndex ];
			
			if( this.iProjectileCooldown == 0 ) {
				
				Vec2 direction = MathUtils.calcDirBetweenGameObjects(this, this.player);
				
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
				
				this.iProjectileCooldown = MathUtils.convertMillisecondsToGameTicks( 1000 );
			}
			
		}
		
	}

}
