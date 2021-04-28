package turd.game.platform;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.objects.GameObject;
import turd.game.objects.StaticObject;

// Use StaticObject instead of GameObject for when we register the platform
// into the Object List.
public class Platform extends StaticObject {
	
	public enum PLATFORM_TYPE {
		LONG,
		MEDIUM,
		SHORT,
		HAZARD,
		SMALLSQ,
		MEDIUMSQ,
	}
	
	// Indicates the type of platform this class should handle.
	protected PLATFORM_TYPE type;
	
	protected String sImage;
 	
	protected float r, g, b, a;
	
	@Override
	public void render(Window window, Graphics g) {
		
		// Check platform type, render different image.
		
		g.setColor(this.r, this.g, this.b, this.a);
		g.drawFilledRect(getX(), getY(), getWidth(), getHeight());
		
	}

	@Override
	public void update(Window window, GAMEOBJ_UPDATE_TYPE updateType) {
		
		
	}

}
