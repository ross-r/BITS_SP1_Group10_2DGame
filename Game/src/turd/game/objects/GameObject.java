package turd.game.objects;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.physics.AABB;

public abstract class GameObject {
	public AABB aabb;
		
	protected GameObject() {
		this.aabb = new AABB();
	}
	
	public abstract void render(Window w, Graphics g);
	
	public abstract void tick(Window w);
	
	public boolean collides(GameObject object) {
		return this.aabb.collides(object.aabb);
	}
	
	public void onCollision(GameObject object) {
		// Override this method in classes which wish to implement functionality upon collisions.
	}
	
	public boolean shouldDelete() {
		// Override this method in classes which wish to have deleted.
		return false;
	}
	
}
