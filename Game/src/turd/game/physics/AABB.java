package turd.game.physics;

import org.joml.Intersectionf;

// Implementation for axis aligned bounding boxes.
// These are boxes that are aligned on a specific axis, i.e; they cannot rotate.
// All physic components should implement this class to implement bounding boxes.
// Our bounding boxes only need to perform operations for 2D boxes.

import org.joml.Vector2f;
import org.joml.Vector3f;

import turd.game.objects.GameObject;

public final class AABB {
	
	// Against typical Java standards, avoiding getters/setters for simplicity during operations.
	// This is more to my C/C++ roots and just makes it all easier to read code wise when it's fully implemented.
	public Vector2f p0;
	public Vector2f p1;
	
	public boolean bCollided;
		
	public AABB() {
		p0 = new Vector2f();
		p1 = new Vector2f();
		bCollided = false;
	}
	
	public Vector2f extent() {
		return p0.add(p1, new Vector2f());
	}
	
	public void init(float x, float y, float x1, float y1) {
		p0.x = x;
		p0.y = y;
		p1.x = x1;
		p1.y = y1;
	}
	
	public boolean isPointInside(Vector2f p) {
		return ( p.x >= p0.x && p.x <= p0.x + p1.x ) &&
				( p.y >= p0.y && p.y <= p0.y + p1.y );
	}
	
	public boolean collides(AABB aabb) {
		bCollided = Intersectionf.testAabAab(
				new Vector3f( p0.x, p0.y, 0.f ), new Vector3f( p0.x + p1.x, p0.y + p1.y, 0.f ), 
				new Vector3f( aabb.p0.x, aabb.p0.y, 0.f ), new Vector3f( aabb.p0.x + aabb.p1.x, aabb.p0.y + aabb.p1.y, 0.f ));
		
		return bCollided;
	}
	
	// Assume that the user has called the method 'collides' to validate collision.
	public void resolveCollision(GameObject object0, GameObject object1) {
		if( !bCollided ) {
			return;
		}
		
		AABB aabb = object1.aabb;

		// Compute depth of x/y collision.
		float flOverlapX = 0.f;
		float flOverlapY = 0.f;

		//
		// x coordinate collision resolution testing.
		//
		
		float b = Math.max( p0.x, aabb.p0.x );
		float c = Math.min( extent().x, aabb.extent().x );
		
		float x1 = p0.x + ( p1.x / 2.f );
		float x2 = aabb.p0.x + ( aabb.p1.x / 2.f );

		boolean left = x1 < x2;
		boolean right = x1 > x2;
		
		float x3 = Math.max( ( c - b ), ( b - c ) );
		float x4 = x3 - p1.x;
		
		if( left ) {
			flOverlapX = x4;
		}
		
		if( right ) {
			flOverlapX = -x4;
		}

		//System.out.printf("%b %b (%f %f) %f - (%f %f %f %f)\n", left, right, b, c, flOverlapX, x1, x2, x3, x4);
		
		//
		// y coordinate collision resolution testing.
		//
		
		// Check if the first box (this) is inside of the other box (aabb)
		boolean test1 = p0.y > aabb.p0.y && p0.y < ( aabb.p0.y + aabb.p1.y );
		
		// Check if the bottom of the first box (this) is penetrating into the other box (aabb)
		// but make sure it is still within the bounds.
		boolean test2 = ( p0.y + p1.y ) > aabb.p0.y && ( p0.y + p1.y ) < ( aabb.p0.y + aabb.p1.y );
		
		// If both conditions are met, we done messed up.
		if( !( test1 && test2 ) ) {
			if( test1 ) {
				
				// Calculate the depth from the bottom of the other box - how far in our box has moved 'upwards' into the other.
				flOverlapY = -( ( aabb.p0.y + aabb.p1.y ) - p0.y );
			}
			else if( test2 ) {
				
				// Calculate the depth from the bottom of our box - how far our box has moved 'downwards' into the other.
				flOverlapY = -( aabb.p0.y - ( p0.y + p1.y ) );
			}
		}
		
		System.out.printf("%f %f\n", flOverlapX, flOverlapY);
		
		// Fix up any overlapping positions.
		Vector2f overlap = new Vector2f( flOverlapX, flOverlapY );
		this.p0.sub( overlap );
	}
	
	public float getCenterX() {
		return p0.x + ( p1.x / 2.f );
	}
	
	public float getCenterY() {
		return p0.y + ( p1.y / 2.f );
	}
}
