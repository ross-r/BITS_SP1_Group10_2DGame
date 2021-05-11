package turd.game.platform;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.graphics.Texture;
import turd.game.objects.ObjectList;

import static org.lwjgl.nanovg.NanoVG.*;

public class LongPlatform extends Platform {

	// final = const
	// const means it cannot change after initialization (e.g, inline/constrcutor)
	// pixels sizing
	
	private static final int PLATFORM_WIDTH = 800;
	private static final int PLATFORM_HEIGHT = 100;
	
	private Texture texture;
	
	public LongPlatform(int x, int y) { // pass in paramiters 
		super();
		
		this.aabb.init(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		
		this.type = PLATFORM_TYPE.LONG;
		
		this.sImage = "platform_long.png";
		this.texture = new Texture(Graphics.nvgHandle(), this.sImage);
				
		// red
		this.r = 255.f;
		this.g = 0.f;
		this.b = 0.f;
		this.a = 255.f;
		
		// The game object class has x/y/w/h defined locally.
		// We can set that instead of making variables.
		
		// Register this platform.
		ObjectList.getInstance().registerStaticObject(this);
	}
	
	@Override
	public void render(Window window, Graphics g) {
		//g.setColor(this.r, this.g, this.b, this.a);
		//g.drawFilledRect((int)aabb.p0.x, (int)aabb.p0.y, (int)aabb.p1.x, (int)aabb.p1.y);
	
		// Calculate how many iterations it will take to project the texture over the entire object.
		final int width = (int)aabb.p1.x;
		int iterations = (int)Math.ceil((float)width / this.texture.getWidth());
		
		// Apply a scissor rect (clip rect) so that we don't need to manually fix when the texture repeats
		// too far due to width/width not being equally divisible or w/e.
		nvgScissor(Graphics.nvgHandle(), aabb.p0.x, aabb.p0.y, aabb.p1.x, aabb.p1.y);
		
		int iTexX = (int)aabb.p0.x;
		for( int i = 0; i < iterations; i++ ) {
			int iTexWidth = this.texture.getWidth();
			this.texture.render(iTexX, (int)aabb.p0.y, iTexWidth, (int)aabb.p1.y, 255.f);
			iTexX += this.texture.getWidth();
		}
		
		nvgResetScissor(Graphics.nvgHandle());
	}
	
}
