package turd.game;

import org.joml.Vector2f;

import turd.game.entities.Player;
import turd.game.objects.GameObject;

public class MathUtils {
	public static float calcAngle( int x, int y, int x1, int y1 ) {
		double theta = Math.atan2( y1 - y, x1 - x );
		
		// This makes it so that if we did (y = 0, y1 = -1, x = 0, x1 = 0)
		// the output angle would be 0.0, as the direction computed is NORTH.
		// if we were to feed in y1 = 1 then the direction is SOUTH which will be 180.0
		theta += Math.PI / 2.0;
		
		// The output angle is ranged from [0, 360]
		double angle = Math.toDegrees( theta );
		
		// Make sure the angle is 'normalized' between the range [0, 360]
		angle = ( angle % 360.0 );
		
		// Convert the angle into the range [-180, 180]
		if( angle > 180.0 ) {
			angle -= 360.0;
		}
		
		return (float) (theta);
	}
	
	public static float calcDirection( int x, int y, int x1, int y1 ) {
		double theta = Math.atan2( -( x1 - x ), y1 - y );
		return ( float ) theta;
	}
	
	public static float calcDirection( float x, float y, float x1, float y1 ) {
		double theta = Math.atan2( -( x1 - x ), y1 - y );
		return ( float ) theta;
	}
	
	// returned values are in radians.
	public static Vector2f calcDirFromGameObjectToMouse(Window window, GameObject object) {
		// Compute center coordinates of our aabb.
		final float flCenterX = object.aabb.p0.x + ( object.aabb.p1.x / 2 );
		final float flCenterY = object.aabb.p0.y + ( object.aabb.p1.y / 2 );
		
		// Work out the mouse coordinates in relation to the center of the screen specifically,
		// i.e; if the mouse is in the center the coordinates should be [0, 0]
		// if the mouse is off to the left it should be [-30, 0] where 30 is arbitrarily chosen for this example.
		final float flMouseX = flCenterX + ( float ) window.getMouseX() - ( window.getWidth() / 2 );
		final float flMouseY = flCenterY + ( float ) window.getMouseY() - ( window.getHeight() / 2 );
		
		float flDirection = MathUtils.calcDirection(flCenterX, flCenterY, flMouseX, flMouseY);
		
		float flDirectionX = (float) Math.sin(-flDirection);
		float flDirectionY = (float) Math.cos(flDirection);
		
		return new Vector2f(flDirectionX, flDirectionY);
	}
	
	public static Vector2f calcDirBetweenGameObjects(GameObject object0, GameObject object1) {
		// Compute center coordinates of our aabb.
		final float flCenterX = object0.aabb.p0.x + ( object0.aabb.p1.x / 2 );
		final float flCenterY = object0.aabb.p0.y + ( object0.aabb.p1.y / 2 );
		
		// Compute center coordinates of target aabb.
		final float flTargetX = object1.aabb.p0.x + ( object1.aabb.p1.x / 2 );
		final float flTargetY = object1.aabb.p0.y + ( object1.aabb.p1.y / 2 );
		
		float flDirection = MathUtils.calcDirection(flCenterX, flCenterY, flTargetX, flTargetY);
		
		float flDirectionX = (float) Math.sin(-flDirection);
		float flDirectionY = (float) Math.cos(flDirection);
		
		return new Vector2f(flDirectionX, flDirectionY);
	}
	
	// Expected input is milliseconds.
	// i.e; 2800 = 2.8 seconds
	public static int convertMillisecondsToGameTicks(double milliseconds) {
		
		// The ratio to convert 1 millisecond into a second ( 1ms = 0.001s )
		final double MS_TO_SECOND = 0.001;
		
		// How many milliseconds are in a second.
		final double MS_IN_SECOND = 1000;
		
		// How many game ticks happen in a second.
		final int GAME_TICKS = 60;
		
		return ( int )( ( milliseconds / MS_IN_SECOND ) / MS_TO_SECOND ) / GAME_TICKS;
	}
	
	public static float randomInRange( float flMin, float flMax ) {
		return ( float ) ( Math.random() * ( flMax - flMin ) ) + flMin;
	}
	
	public static boolean isObjectAbovePlayer(Player player, GameObject object) {
		if( object == null ) {
			return false;
		}
		
		// Calculate the direction but don't worry about the x coordinate
		// including the x coordinate here would produce an angle that is between
		// x and y and y would only be usable if we were directly under something
		// and centered perfectly in the middle of the object.
		final float flCenterY = player.aabb.p0.y + ( player.aabb.p1.y / 2 );
		final float flTargetY = object.aabb.p0.y + ( object.aabb.p1.y / 2 );
		final float flDirection = MathUtils.calcDirection(0, flCenterY, 0, flTargetY);
		final float flAngle = ( float ) ( Math.toDegrees( Math.cos( flDirection ) ) * Math.PI );
		
		// -180 would indicate that something is perfectly above us.
		return flAngle == -180.f;
	}
}
