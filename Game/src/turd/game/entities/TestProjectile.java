package turd.game.entities;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.graphics.Texture;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.physics.Physics;
import turd.game.physics.Vec2;

public class TestProjectile extends GameObject {

	private final int BOUNDS = 16;
	
	private Vec2 direction;
	private Vec2 velocity;
	private boolean bInitialized;
	
	private Physics physics;
	
	private int iTicksAirBorne;
	private int iMaxTicksAirborne;
	
	private Texture texture;
	
	// An empty constructor so that the object can be registered into the object list at creation of Player class.
	// This is done as at creation we don't know the position, direction or velocity that this object should have.
	public TestProjectile() {
		super();
	
		// Gotta have something here.
		this.aabb.init(0.f, 0.f, 0.f, 0.f);
		
		this.physics = new Physics(this);
		
		// How many ticks this entity has been air borne, an indication to remove the entity after some duration.
		this.iTicksAirBorne = 0;
		
		// Convert seconds into game ticks to decide how many seconds the projectile should remain air-borne before 'detonating'.
		// 60 game-ticks in 1 second.
		this.iMaxTicksAirborne = 2 * 60;
		
		this.bInitialized = false;
		
		// Select one of the hud_scrap textures randomly.
		final int rand = ( int ) ( ( Math.random() * ( 7 - 1 ) ) + 1 );
		this.texture = new Texture( Graphics.nvgHandle(), String.format( "hud_scrap%d.png", rand ) );
	}
	
	public boolean initialize(Vec2 position, Vec2 direction, Vec2 velocity) {
		this.direction = direction;
		this.velocity = velocity;
		
		this.aabb.init(position.x, position.y, BOUNDS, BOUNDS);
		
		// Make sure this projectile does not spawn in an invalid position.
		if( this.physics.doesObjectCollideWithWorld() ) {
			this.destroy( false );
			return false;
		}
		
		this.bInitialized = true;
		return true;
	}
	
	public boolean isInitialized() {
		return this.bInitialized;
	}
	
	// This method doesn't de-register from the object list.
	public void destroy(boolean bCalledByPhysics) {
		this.bInitialized = false;
		this.iTicksAirBorne = 0;
		
		// If this is the case then the projectile has hit another entity.
		// - Commented out as I don't know if we want this behaviour, I would assume if the projectile damages something,
		// we would want the projectile to be 'consumed', maybe it could drop a scrap object that gives a smaller portion of 'ammo'?
		//if( bCalledByPhysics ) {
		//	ObjectList.getInstance().registerQueuedObject( new Scrap( ( int ) this.aabb.p0.x, ( int ) this.aabb.p0.y ) );
		//}

		this.aabb.init(0.f, 0.f, 0.f, 0.f);
	}
	
	@Override
	public void render(Window window, Graphics g) {
		final int x = (int)aabb.p0.x;
		final int y = (int)aabb.p0.y;
		final int w = (int)aabb.p1.x;
		final int h = (int)aabb.p1.y;
		
		if( !this.bInitialized ) {
			return;
		}
		
		this.texture.render(x - w / 2, y - h / 2, 255.f);
		
		//g.setColor(255.f, 0.f, 0.f, 255.f);
		//g.drawFilledRect(x - w / 2, y - h / 2, w, h);
	}

	@Override
	public void tick(Window w) {
		if( !this.bInitialized ) {
			return;
		}
		
		++this.iTicksAirBorne;
		if( this.iTicksAirBorne > this.iMaxTicksAirborne ) {
			this.destroy(false);
			return;
		}
		
		// If this entity is on the ground delete it.
		// - Commented out as I don't think projectiles should have gravity applied to them.
		/*if( !this.physics.gravity() ) {
			System.out.println("ground collided");
			this.destroy();
			return;
		}*/
		
		this.physics.applyForce(this.direction, this.velocity);
	}

	@Override
	public void onCollision(GameObject object) {
		ObjectList.getInstance().registerQueuedObject( new Scrap( ( int ) this.aabb.p0.x, ( int ) this.aabb.p0.y ) );
		this.destroy(false);
	}
}
