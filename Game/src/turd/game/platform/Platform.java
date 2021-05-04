package turd.game.platform;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.objects.GameObject;
import turd.game.objects.StaticObject;

// Use StaticObject instead of GameObject for when we register the platform
// into the Object List.
public class Platform extends StaticObject {
	
	public enum PLATFORM_TYPE {
		
		// platforms horizontal
		LONG,
		MEDIUM,
		SHORT,
		HAZARD,
		SMALLSQ,
		MEDIUMSQ,
		// platforms vertical
		LONG_VERT,
		MED_VERT,
		// hazards
		SPIKES,
		PIT,
		FALLING_PLATFORMS,
	
	}
	
	// Indicates the type of platform this class should handle.
	protected PLATFORM_TYPE type;
	
	protected String sImage;
 	
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
