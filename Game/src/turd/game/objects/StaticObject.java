package turd.game.objects;

import turd.game.Graphics;
import turd.game.Window;

public class StaticObject extends GameObject {

	@Override
	public void render(Window window, Graphics g) {
		g.setColor(200.f, 200.f, 20.f, 200.f);
		g.drawFilledRect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void update(Window window, GAMEOBJ_UPDATE_TYPE updateType) {
		
	}

}
