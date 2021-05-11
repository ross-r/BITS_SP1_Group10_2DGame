package turd.game.platform;

import turd.game.objects.ObjectList;

public class FallingPlat extends Platform {

	private static final int PLATFORM_WIDTH = 64;
	private static final int PLATFORM_HEIGHT = 64;
	
	public FallingPlat(int x, int y) { // pass in paramiters 
		super();
		
		this.aabb.init(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		
		this.type = PLATFORM_TYPE.FALLING_PLATFORMS;
		
		this.sImage = "res/long_platform.png";
		
		// red
		this.r = 140.f;
		this.g = 140.f;
		this.b = 85.f;
		this.a = 255.f;
		
		// The game object class has x/y/w/h defined locally.
		// We can set that instead of making variables.
		
		// Register this platform.
		ObjectList.getInstance().registerStaticObject(this);
	}
}

