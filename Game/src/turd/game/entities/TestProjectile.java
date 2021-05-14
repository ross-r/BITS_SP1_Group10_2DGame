package turd.game.entities;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.objects.GameObject;
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
	}
	
	public void initialize(Vec2 position, Vec2 direction, Vec2 velocity) {
		this.direction = direction;
		this.velocity = velocity;
		
		this.aabb.init(position.x, position.y, BOUNDS, BOUNDS);
		
		this.bInitialized = true;
	}
	
	public boolean isInitialized() {
		return this.bInitialized;
	}
	
	// This method doesn't de-register from the object list.
	public void destroy(boolean bCalledByPhysics) {
		this.bInitialized = false;
		this.iTicksAirBorne = 0;
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
		
		g.setColor(255.f, 0.f, 0.f, 255.f);
		g.drawFilledRect(x - w / 2, y - h / 2, w, h);
	}

	@Override
	public void tick(Window w) {
		if( !this.bInitialized ) {
			return;
		}
		
		++this.iTicksAirBorne;
		if( this.iTicksAirBorne > this.iMaxTicksAirborne ) {
			System.out.println("destroyed");
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

}
