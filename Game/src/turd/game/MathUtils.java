package turd.game;

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
}
