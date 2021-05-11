package turd.game.platform;

import turd.game.objects.ObjectList;

public class Spikes extends Platform {

	private static final int PLATFORM_WIDTH = 128;
	private static final int PLATFORM_HEIGHT = 64;
	
	public Spikes(int x, int y) { // pass in paramiters 
		super();
		
		this.aabb.init(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		
		this.type = PLATFORM_TYPE.SPIKES;
		
		this.sImage = "res/long_platform.png";
		
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
}
