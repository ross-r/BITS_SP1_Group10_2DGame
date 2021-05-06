package turd.game.physics;

import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.objects.StaticObject;
import turd.game.platform.SmallSquare;

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
	
	public void move(float flNewX, float flNewY) {
		float x = this.gameObject.aabb.p0.x;
		float y = this.gameObject.aabb.p0.y;
		
		this.gameObject.aabb.p0.x = flNewX;
		this.gameObject.aabb.p0.y = flNewY;
				
		// Perform collision detection on all other static objects.
		for (StaticObject staticObject : ObjectList.getInstance().getStaticObjects()) {
			
			if (this.gameObject.collides(staticObject)) {
				
				// Stops movement.
				this.gameObject.aabb.p0.x = x;
				this.gameObject.aabb.p0.y = y;
			}
		}
	}
}
