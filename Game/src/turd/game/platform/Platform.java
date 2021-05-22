package turd.game.platform;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.objects.StaticObject;

// Use StaticObject instead of GameObject for when we register the platform
// into the Object List.
public class Platform extends StaticObject {
	
	public enum PLATFORM_TYPE {
		
		
		LONG_FLOATING_PLATFORM, 
		MEDIUM_FLOATING_PLATFORM, 
		SMALL_FLOATING_PLATFORM, 
		
		GROUND_FILL_LONG,
		GROUND_FILL_MEDIUM,
		GROUND_FILL_SMALL,
		
		GROUND_FILL_EDGE_LONG,
		GROUND_FILL_EDGE_MEDIUM,
		GROUND_FILL_EDGE_SMALL,
		
		GROUND_TOP_LONG,
		GROUND_TOP_MEDIUM,
		GROUND_TOP_SMALL,
		
		GROUND_TOP_EDGE_LONG,
		GROUND_TOP_EDGE_MEDIUM,
		GROUND_TOP_EDGE_SMALL,
		
		// single blocks
		
		GROUND_MID_RIGHT,
		GROUND_MID_LEFT,
		GROUND_MID_MID,
		GROUND_BOTTOM_LEFT,
		GROUND_BOTTOM_RIGHT,
		
		GROUND_BOTTOM_FARRIGHT, 
		GROUND_BOTTOM_FARLEFT, 
		
		GROUND_BOTTOM_RIGHT_SPIKESCONNECT,
		GROUND_BOTTOM_LEFT_SPIKESCONNECT,
		
		GROUND_TOP_LEFT,
		GROUND_TOP_MID,
		GROUND_TOP_RIGHT,
		
		GROUND_PLATFORM_EDGE_CONNECT_RIGHT,
		GROUND_PLATFORM_EDGE_CONNECT_LEFT,
		GROUND_WALL_TOP_LEFT,
		GROUND_WALL_TOP_RIGHT,
		
		GROUND_PLATFORM_RIGHT,
		GROUND_PLATFORM_LEFT,
		GROUND_PLATFORM_MID,
		
		GROUND_ROOF_MID,
		GROUND_ROOF_LEFT,
		GROUND_ROOF_RIGHT,
		
		GROUND_ROOF_MID_CONNECT_RIGHT,
		GROUND_ROOF_MID_CONNECT_LEFT,
		
		// hazards
		SPIKES,
		PIT,
		FALLING_PLATFORMS,
	
		// objects
		OBJECT_CAR01,
		OBJECT_CRANE,
	}
	
	// Indicates the type of platform this class should handle.
	protected PLATFORM_TYPE type;
	
	protected float r, g, b, a;
	
	@Override
	public void render(Window window, Graphics g) {
		
		// Check platform type, render different image.
		
		g.setColor(this.r, this.g, this.b, this.a);
		g.drawFilledRect((int)aabb.p0.x, (int)aabb.p0.y, (int)aabb.p1.x, (int)aabb.p1.y);
	}

	@Override
	public void tick(Window w) {
		
	}
}
