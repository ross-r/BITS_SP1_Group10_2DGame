package turd.game.platform;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.graphics.Texture;
import turd.game.graphics.TextureManager;
import turd.game.objects.ObjectList;

public class HazardSpikes extends Platform {

	private static final int PLATFORM_WIDTH = 100;
	private static final int PLATFORM_HEIGHT = 100;
	
	private Texture texture;
	
	public HazardSpikes(int x, int y) { // pass in paramiters 
		super();
		
		this.aabb.init(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		
		this.type = PLATFORM_TYPE.SPIKES;
		
		this.texture = TextureManager.get("Hazard_Spikes.png");
		
		// blue
		this.r = 0.f;
		this.g = 0.f;
		this.b = 255.f;
		this.a = 255.f;
		
		// The game object class has x/y/w/h defined locally.
		// We can set that instead of making variables.
		
		// Register this platform.
		ObjectList.getInstance().registerStaticObject(this);
	}
	
	@Override
	public void render(Window window, Graphics g) {
		final int x = (int)aabb.p0.x;
		final int y = (int)aabb.p0.y;
		final int w = (int)aabb.p1.x;
		final int h = (int)aabb.p1.y;
		
		this.texture.render(x, y, w, h, 255.f);
	}
}
