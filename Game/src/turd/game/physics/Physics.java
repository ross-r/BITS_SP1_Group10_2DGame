package turd.game.physics;

import turd.game.entities.TestProjectile;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.objects.StaticObject;

public class Physics {

	public final float GRAVITY = 4.f;

	private GameObject gameObject;
	private GameObject collidedObject;
	
	public Physics(GameObject obj) {
		this.gameObject = obj;
		this.collidedObject = null;
	}
	
	public boolean gravity() {
		Vec2 direction = new Vec2( 0.f, 1.f );
		Vec2 velocity = new Vec2( 0.f, GRAVITY );		
		return !applyForce( direction, velocity );
	}
	
	// Check if a projectile intersects with another entity (which is not a projectile)
	private void handleEntityCollisions() {
		for( GameObject entity : ObjectList.getInstance().getEntities() ) {
		
			// Skip self.
			if( entity.equals( this.gameObject ) ) {
				continue;
			}
			
			// Skip other projectiles.
			if( entity instanceof TestProjectile ) {
				continue;
			}
			
			if( this.gameObject.collides( entity ) ) {
				
				// Notify the entity that they have had a collision.
				entity.onCollision( this.gameObject );
				
				// Destroy the projectile once it has collided with an entity.
				if( this.gameObject instanceof TestProjectile ) {
					( ( TestProjectile )this.gameObject ).destroy(true);
				}
				
				// Don't de-register the game object from ObjectList here.
			}
		}
	}
	
	public boolean doesObjectCollideWithWorld() {
		// Perform collision detection on all other static objects.
		for (StaticObject staticObject : ObjectList.getInstance().getStaticObjects()) {
			
			if (this.gameObject.collides(staticObject)) {
				
				// Store the object that we collided with so we can check if it was above our player.
				this.collidedObject = staticObject;
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean move(float flNewX, float flNewY) {
		this.collidedObject = null;
		
		float x = this.gameObject.aabb.p0.x;
		float y = this.gameObject.aabb.p0.y;
		
		this.gameObject.aabb.p0.x = flNewX;
		this.gameObject.aabb.p0.y = flNewY;
		
		this.handleEntityCollisions();
		
		if( this.doesObjectCollideWithWorld() ) {
		
			// Notify our object that it has collided with something.
			this.gameObject.onCollision( this.collidedObject );
			
			// Stops movement.
			this.gameObject.aabb.p0.x = x;
			this.gameObject.aabb.p0.y = y;
			
			return true;
		}
		
		return false;
	}

	public boolean applyForce(Vec2 direction, Vec2 velocity) {
		float x = this.gameObject.aabb.p0.x;
		float y = this.gameObject.aabb.p0.y;
		
		x += direction.x * velocity.x;
		y += direction.y * velocity.y;
		
		boolean bCollided = this.move(x, y);
		
		// Check if there was any movement.
		final float EPSILON = 0.01f;
		boolean bMoved = Math.abs(this.gameObject.aabb.p0.x - x) > EPSILON || 
				Math.abs(this.gameObject.aabb.p0.y - y) > EPSILON;
				
		return bCollided && bMoved;
	}
	
	public GameObject getCollidedObject() {
		return this.collidedObject;
	}
}
