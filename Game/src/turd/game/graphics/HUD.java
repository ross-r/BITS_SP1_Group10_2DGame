package turd.game.graphics;

import static org.lwjgl.nanovg.NanoVGGL2.nvgDelete;

import turd.game.Window;

//would this need to be called only when health/ammo or upgrade is changed?
//or would it need to be called every frame as the "screen" moves
public class HUD {
	
	//Scrap is health and ammunition
	Texture texScrap;
	Texture texSpeedUp;
	
	//the window width and height
	int iWidth;
	int iHeight;

	public HUD(Window window, Graphics g) {		
		texScrap = new Texture(Graphics.nvgHandle(), "hud_scrap.png" );
		texSpeedUp = new Texture(Graphics.nvgHandle(), "hud_upgrade_speed.png" );
	}
	
	public void render(Window window, Graphics g) {
		//choose the initial position on the screen
		//top left with an offset of 3% from corner in both directions
		iWidth = window.getScaledWidth();
		iHeight = window.getScaledHeight();
		
		//create a position for initial scrap icon
		int scrap_x = 30% iWidth;
		int scrap_y = 30% iHeight;
		
		//place the icon
		//parse into the render of the texScrap object the int x int y and alpha
		texScrap.render(scrap_x, scrap_y, 255.f);
	}

//	public void terminate() {
//		nvgDelete(nvgHandle);
//	}
	
}
