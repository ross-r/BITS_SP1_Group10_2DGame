package turd.game.physics;

import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.objects.StaticObject;

public class Physics {

	public final float GRAVITY = 2.f;

	private GameObject gameObject;
	
	public Physics(GameObject obj) {
		this.gameObject = obj;
	}
	
	// Applies gravity to a point.
	public void gravity() {
		float y = this.gameObject.aabb.p0.y;
		
		this.gameObject.aabb.p0.y += GRAVITY;
		
		// Perform collision detection on all other static objects.
		for (StaticObject staticObject : ObjectList.getInstance().getStaticObjects()) {
			if (this.gameObject.collides(staticObject)) {
				this.gameObject.aabb.p0.y = y;
			}
		}
	}
	
	public void move(float flNewX, float flNewY) {
		float x = this.gameObject.aabb.p0.x;
		float y = this.gameObject.aabb.p0.y;
		
		this.gameObject.aabb.p0.x = flNewX;
		this.gameObject.aabb.p0.y = flNewY;
		
		// Perform collision detection on all other static objects.
		for (StaticObject staticObject : ObjectList.getInstance().getStaticObjects()) {
			if (this.gameObject.collides(staticObject)) {
				this.gameObject.aabb.p0.x = x;
				this.gameObject.aabb.p0.y = y;
			}
		}
	}
}
