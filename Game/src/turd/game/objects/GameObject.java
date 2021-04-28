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
}
