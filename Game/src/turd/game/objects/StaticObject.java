package turd.game.objects;

import turd.game.Window;
import turd.game.graphics.Graphics;

public class StaticObject extends GameObject {

	@Override
	public void render(Window window, Graphics g) {
		g.setColor(255.f, 0.f, 0.f, 255.f);
		g.drawFilledRect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void update(Window window, GAMEOBJ_UPDATE_TYPE updateType) {
		
	}

}
