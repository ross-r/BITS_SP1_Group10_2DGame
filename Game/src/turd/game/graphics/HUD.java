package turd.game.graphics;

import static org.lwjgl.nanovg.NanoVGGL2.nvgDelete;

import turd.game.Window;
import turd.game.entities.Player;
import turd.game.objects.ObjectList;

//would this need to be called only when health/ammo or upgrade is changed?
//or would it need to be called every frame as the "screen" moves
public class HUD {

	// Scrap is health and ammunition
	Texture texScrap1;
	Texture texScrap2;
	Texture texScrap3;
	Texture texScrap4;
	Texture texScrap5;
	Texture texScrap6;
	Texture texScrap7;
	Texture texSpeedUp;
	Texture texScrap[];
	Texture texUpSpeed;
	
	// the window width and height
	int iWidth;
	int iHeight;

	public HUD(Window window, Graphics g) {
		//these textures will be updated to the picked up scrap instead of default colours
		texScrap1 = new Texture(Graphics.nvgHandle(), "hud_scrap1.png");
		texScrap2 = new Texture(Graphics.nvgHandle(), "hud_scrap2.png");
		texScrap3 = new Texture(Graphics.nvgHandle(), "hud_scrap3.png");
		texScrap4 = new Texture(Graphics.nvgHandle(), "hud_scrap4.png");
		texScrap5 = new Texture(Graphics.nvgHandle(), "hud_scrap5.png");
		texScrap6 = new Texture(Graphics.nvgHandle(), "hud_scrap6.png");
		texScrap7 = new Texture(Graphics.nvgHandle(), "hud_scrap7.png");
		texSpeedUp = new Texture(Graphics.nvgHandle(), "hud_upgrade_speed.png");
		texUpSpeed = new Texture(Graphics.nvgHandle(), "player-upgrade-speed.png");
		texScrap = new Texture[7];
		texScrap[0] = texScrap1;
		texScrap[1] = texScrap2;
		texScrap[2] = texScrap3;
		texScrap[3] = texScrap4;
		texScrap[4] = texScrap5;
		texScrap[5] = texScrap6;
		texScrap[6] = texScrap7;
		
	}

	public void render(Window window, Graphics g) {
		Player player = ObjectList.getInstance().getPlayer();
		if( player == null ) {
			return;
		}
		
		//choose the initial position on the screen
		//top left with an offset of 3% from corner in both directions
		iWidth = window.getScaledWidth();
		iHeight = window.getScaledHeight();
		
		//create a position for initial scrap icon
		int scrap_x = 30% iWidth;
		int scrap_y = 30% iHeight;
		int offset = 0;
		
		for (int i = 0; i < player.getScrapValue(); i++) {
			offset = offset + (60% iWidth);
			texScrap[i].render(scrap_x + offset, scrap_y, 255.f);
		}
		
		int offset2 = 12;
		for (int i = 0; i < player.getTotalUpgrades(); i++) {	
			offset2 = offset2 + (60% iWidth);
			if (player.getPlayerSpeedUp())
			texUpSpeed.render(scrap_x + offset2, scrap_y, 255.f);
		}
		
//		switch (Player.getScrapValue()) {
//		
//		case 1:
//			texScrap.render(scrap_x, scrap_y, 255.f);
//		case 2:
//			texScrap.render(scrap_x + (30% iWidth), scrap_y + (30% iWidth), 255.f);
		}

	}

//	public void terminate() {
//		nvgDelete(nvgHandle);
//	}


