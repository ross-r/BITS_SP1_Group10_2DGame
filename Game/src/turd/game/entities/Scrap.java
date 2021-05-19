package turd.game.entities;


import org.joml.Vector2f;

import turd.game.Constants;
import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.objects.GameObject;
import turd.game.physics.Physics;


public class Scrap extends GameObject {

	private Physics physics;
		
	private boolean bCollected;
	
	private Vector2f direction;
	private Vector2f velocity;
	
	public Scrap(Vector2f pos, Vector2f dir, Vector2f vel) {
		super();
		
		this.physics = new Physics(this);
		
		this.direction = dir;
		this.velocity = vel;
		
		this.bCollected = false;
		
		this.aabb.init(pos.x, pos.y, Constants.SCRAP_BOUNDS, Constants.SCRAP_BOUNDS);
	}
	
	@Override
	public void render(Window window, Graphics g) {	
		
		g.setColor(255.f, 255.f, 255.f, 255.f);
		g.drawFilledRect((int)aabb.p0.x, (int)aabb.p0.y, (int)aabb.p1.x, (int)aabb.p1.y);
		
	}
	
	@Override
	public void tick(Window w) {
		// 'physics.gravity()' returns false when a collision has happened.
		if( this.physics.gravity() ) {
			this.physics.applyForce( this.direction, this.velocity );
		}
	}
	
	@Override
	public void onCollision(GameObject object) {
		
		if( this.bCollected ) {
			return;
		}
		
		// Only the player can collect scrap.
		if( !( object instanceof Player ) ) {
			return;
		}
		
		// The player has 'collected' this piece of scrap.
		if ( ( ( Player ) object ).collectScrap() ) {
		
			// Mark this scrap as collected for deletion later.
			this.bCollected = true;
		}
	}
	
	@Override
	public boolean shouldDelete() {
		return this.bCollected;
	}
}
