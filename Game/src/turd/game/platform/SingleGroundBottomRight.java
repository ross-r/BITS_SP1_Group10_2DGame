package turd.game.platform;

import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.graphics.Texture;
import turd.game.graphics.TextureManager;
import turd.game.objects.ObjectList;

public class SingleGroundBottomRight extends Platform {

	private static final int PLATFORM_WIDTH = 100;
	private static final int PLATFORM_HEIGHT = 100;
	
	private Texture texture;
	
	public SingleGroundBottomRight(int x, int y) {
		super();
		
		this.aabb.init(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		
		this.type = PLATFORM_TYPE.GROUND_BOTTOM_RIGHT;
		
		this.texture = TextureManager.get("Ground_Bottom_Right.png");
		
		this.r = 255.f;
		this.g = 0.f;
		this.b = 0.f;
		this.a = 255.f;
		
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