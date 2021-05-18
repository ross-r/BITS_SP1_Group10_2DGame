package turd.game.platform;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.graphics.Texture;
import turd.game.graphics.TextureManager;
import turd.game.objects.ObjectList;

public class GroundFillEdgeMedium extends Platform {

	private static final int PLATFORM_WIDTH = 500;
	private static final int PLATFORM_HEIGHT = 100;
	
	private Texture texLeft;
	private Texture texRight;
	private Texture texMid;
	
	public GroundFillEdgeMedium(int x, int y) {
		super();
		
		this.aabb.init(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		
		this.type = PLATFORM_TYPE.GROUND_FILL_EDGE_MEDIUM;
		
		this.texLeft = TextureManager.get("Ground_Mid_Left.png");
		this.texRight = TextureManager.get("Ground_Mid_Right.png");
		this.texMid = TextureManager.get("Ground_Mid_Mid.png");
		
		// red
		this.r = 255.f;
		this.g = 0.f;
		this.b = 0.f;
		this.a = 255.f;
		
		// Register this platform.
		ObjectList.getInstance().registerStaticObject(this);
	}
	
	private void drawRepeatedTexture(Texture texture, int x, int y, int width, int height) {
		
		// Check if the texture can be repeated evenly within the supplied width.
		if(width % texture.getWidth() != 0) {
			
			// For debug purposes.
			System.out.printf("texture cannot be repeated evenly, width is %d and texture width is %d - remainder: %d\n",
					width, texture.getWidth(), width % texture.getWidth());
			
			return;
		}
		
		// Calculate how many iterations it will take to project the texture over the entire object.
		final int iterations = width / texture.getWidth();
		
		// Apply a scissor rect (clip rect) so that we don't need to manually fix when the texture repeats
		// too far due to width/width not being equally divisible or w/e.
		// NOTE - Ross (11/5): This should no longer be needed since we make sure the texture can be repeated evenly.
		//nvgScissor(Graphics.nvgHandle(), aabb.p0.x, aabb.p0.y, aabb.p1.x, aabb.p1.y);
		
		for( int i = 0; i < iterations; i++ ) {
			texture.render(x, y, texture.getWidth(), height, 255.f);
			x += texture.getWidth();
		}
		
		// NOTE - Ross (11/5): This should no longer be needed since we make sure the texture can be repeated evenly.
		//nvgResetScissor(Graphics.nvgHandle());
	}
	
	@Override
	public void render(Window window, Graphics g) {
		final int x = (int)aabb.p0.x;
		final int y = (int)aabb.p0.y;
		final int w = (int)aabb.p1.x;
		final int h = (int)aabb.p1.y;
		
		// Draw the left platform first.
		this.texLeft.render(x, y, 255.f);
		
		// Now draw the right platform.
		this.texRight.render(x + (w - this.texRight.getWidth()), y, 255.f);
		
		// Work out how many times we need to repeat the center platform to fill in the gap.
		final int iRepeatWidth = w - ( this.texLeft.getWidth() + this.texRight.getWidth() );
		
		// Draw the mid platform texture and repeat it to fill out the remaining gap.
		drawRepeatedTexture(this.texMid, x + this.texLeft.getWidth(), y, iRepeatWidth, h);
	}
	
}