package turd.game.physics;

// Implementation for axis aligned bounding boxes.
// These are boxes that are aligned on a specific axis, i.e; they cannot rotate.
// All physic components should implement this class to implement bounding boxes.
// Our bounding boxes only need to perform operations for 2D boxes.

public final class AABB {
	
	// Against typical Java standards, avoiding getters/setters for simplicity during operations.
	// This is more to my C/C++ roots and just makes it all easier to read code wise when it's fully implemented.
	public Vec2 p0;
	public Vec2 p1;
	
	public AABB() {
		p0 = new Vec2(0.f, 0.f);
		p1 = new Vec2(0.f, 0.f);
	}
	
	public void init(float x, float y, float x1, float y1) {
		p0.x = x;
		p0.y = y;
		p1.x = x1;
		p1.y = y1;
	}
	
	public boolean isPointInside(Vec2 p) {
		return ( p.x >= p0.x && p.x <= p0.x + p1.x ) &&
				( p.y >= p0.y && p.y <= p0.y + p1.y );
	}
	
	public boolean collides(AABB aabb) {
		return ( p0.x <= ( aabb.p0.x + aabb.p1.x ) && ( p0.x + p1.x ) >= aabb.p0.x ) &&
				( p0.y <= ( aabb.p0.y + aabb.p1.y ) && ( p0.y + p1.y ) >= aabb.p0.y );
	}
	
	// Assume that the user has called the method 'collides' to validate collision.
	public AABB intersection(AABB aabb) {
		float x = p0.x > aabb.p0.x ? p0.x : aabb.p0.x;
		float y = p0.y > aabb.p0.y ? p0.y : aabb.p0.y;
		
		float x1 = ( p0.x + p1.x ) < ( aabb.p0.x + aabb.p1.x ) ? ( p0.x + p1.x ) - x : ( aabb.p0.x + aabb.p1.x ) - x;
		float y1 = ( p0.y + p1.y ) < ( aabb.p0.y + aabb.p1.y ) ? ( p0.y + p1.y ) - y : ( aabb.p0.y + aabb.p1.y ) - y;
		
		AABB out = new AABB();
		out.init(x, y, x1, y1);
		return out;
	}
	
	public float getCenterX() {
		return p0.x + ( p1.x / 2.f );
	}
	
	public float getCenterY() {
		return p0.y + ( p1.y / 2.f );
	}
}
