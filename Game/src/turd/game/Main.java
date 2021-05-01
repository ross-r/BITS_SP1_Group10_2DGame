package turd.game;

// We have to use NanoVG OpenGL 2 for Mac users as OpenGL 3 is not supported.
// NanoVG uses shader version 150 in OpenGL 3 context which cannot compile under Mac.
import static org.lwjgl.nanovg.NanoVGGL2.*;

import org.lwjgl.*;
import turd.game.audio.Audio;
import turd.game.graphics.Graphics;
import turd.game.objects.ObjectList;
import turd.game.platform.LongPlatform;
import turd.game.platform.SmallSquare;

public class Main {

	private final int WINDOW_WIDTH = 1280;
	private final int WINDOW_HEIGHT = 720;

	private Window window;
	private Graphics graphics;

	private Audio audio;
	
	private Runnable _render = new Runnable() {

		@Override
		public void run() {
			render();
		}
		
	};
	
	private Runnable _tick = new Runnable() {

		@Override
		public void run() {
			tick();
		}
		
	};
	
	private void renderHUD() {
		graphics.beginFrame();
		
		// Draw FPS/Render time stats.
		graphics.setColor(255.f, 255.f, 255.f, 255.f);
		graphics.drawString(String.format("Trash Unit Response Droid (T.U.R.D)\nFPS: %d\nTPS: %d",
				window.getFps(), window.getTicks()), 2, 2, 1.f);

		// ----------------------- PAUSE MENU HUD
		if (GameState.getInstance().isPaused()) {
			
			// Draw a darker background that is on top of everything else.
			graphics.setColor(0.f, 0.f, 0.f, 127.f);
			graphics.drawFilledRect(0, 0, window.getWidth(), window.getHeight());

			// Draw text indicating the game is paused.
			graphics.setColor(255.f, 255.f, 255.f, 255.f);
			graphics.drawString("GAME PAUSED", 2, 2, 16.f);
		}
		// ----------------------- PAUSE MENU HUD
		
		graphics.endFrame();
	}
	
	public void render() {
		GameState.getInstance().update(window);
		
		// We need to separate things into multiple frames so that translations don't mess stuff up.
		// The first frame will be our "hud".
		
		// Render all objects.
		ObjectList.getInstance().render(window, graphics);
		
		// Render HUD (this will overlap the world)
		renderHUD();
	}
	
	public void tick() {
		// If the game is paused then don't process any ticks.
		if (GameState.getInstance().isPaused()) {
			return;
		}
		
		ObjectList.getInstance().tick(window);
	}
	
	public void start() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		// Create our window.
		window = new Window("Trash Unit Response Droid (T.U.R.D)", WINDOW_WIDTH, WINDOW_HEIGHT);

		// Create graphics.
		graphics = new Graphics(window, NVG_ANTIALIAS);

		// Create audio.
		audio = new Audio();

		// If you would like to disable the camera projection do so here.
		// This may be useful when placing around more objects.
		//GameState.getInstance().setUseCamera(false);
		
		// If you want to move the camera manually you can enter the position here.
		// You can place this function call inside of tick(...) or render(...) methods
		// and feed in the cursor x/y from window.getMouseX/Y methods to make the camera
		// follow the mouse - as an example.
		//GameState.getInstance().setCameraPos(1000.f, 100.f);
		
		// Create our player.
		ObjectList.getInstance().createPlayer();

		// (Length , Height)
		// Window size
		// 720
		// 1280

		new LongPlatform(0, 660);
		new LongPlatform(300, 560);
		new LongPlatform(600, 460);
		new SmallSquare(0, 200);
		
		//audio.play("laser");

		window.loop(_render, _tick);

		// Terminate the window and cleanup NanoVG context.
		window.terminate();
		graphics.terminate();
		audio.terminate();
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
