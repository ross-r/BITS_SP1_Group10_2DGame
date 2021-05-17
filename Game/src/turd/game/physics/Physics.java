package turd.game.physics;

import turd.game.entities.TestProjectile;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.objects.StaticObject;

public class Physics {

	public final float GRAVITY = 4.f;

	private GameObject gameObject;
	
	public Physics(GameObject obj) {
		this.gameObject = obj;
	}
	
	// Applies gravity to a point.
	public boolean gravity() {
		float y = this.gameObject.aabb.p0.y;
		
		this.gameObject.aabb.p0.y += GRAVITY;
		
		// Perform collision detection on all other static objects.
		for (StaticObject staticObject : ObjectList.getInstance().getStaticObjects()) {
		
			// if instance of ....
			
			
			if (this.gameObject.collides(staticObject)) {
				
				// SmallSquare extends Platform which extends StaticObject
				// calling instanceof on a variable will compare x against y (x instanceof y)
				// and validate the object is of a specific type.
				
				//if( staticObject instanceof SmallSquare ) {
				//	System.out.println("on small square");
				//}
				
				this.gameObject.aabb.p0.y = y;
				
				return false;
			}
		}
		
		return true;
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
	
	public void move(float flNewX, float flNewY) {
		float x = this.gameObject.aabb.p0.x;
		float y = this.gameObject.aabb.p0.y;
		
		this.gameObject.aabb.p0.x = flNewX;
		this.gameObject.aabb.p0.y = flNewY;
		
		this.handleEntityCollisions();
		
		// Perform collision detection on all other static objects.
		for (StaticObject staticObject : ObjectList.getInstance().getStaticObjects()) {
			
			if (this.gameObject.collides(staticObject)) {
				
				// Notify our object that it has collided with something.
				this.gameObject.onCollision(staticObject);
				
				// Stops movement.
				this.gameObject.aabb.p0.x = x;
				this.gameObject.aabb.p0.y = y;
			}
		}
	}

	public boolean applyForce(Vec2 direction, Vec2 velocity) {
		float x = this.gameObject.aabb.p0.x;
		float y = this.gameObject.aabb.p0.y;
		
		x += direction.x * velocity.x;
		y += direction.y * velocity.y;
		
		this.move(x, y);
		
		// Check if there was any movement.
		final float EPSILON = 0.01f;
		boolean moved = Math.abs(this.gameObject.aabb.p0.x - x) > EPSILON || 
				Math.abs(this.gameObject.aabb.p0.y - y) > EPSILON;
				
		return !moved;
	}
}
