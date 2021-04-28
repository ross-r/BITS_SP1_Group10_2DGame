package turd.game;

//import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL2.*;
//import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.*;

//import turd.game.entities.Player;

import turd.game.audio.Audio;
import turd.game.entities.Player;

import turd.game.graphics.Graphics;
import turd.game.objects.ObjectList;
//import turd.game.objects.StaticObject;
import turd.game.platform.LongPlatform;
import turd.game.platform.SmallSquare;

public class Main {

	private final int WINDOW_WIDTH = 1280;
	private final int WINDOW_HEIGHT = 720;

	private Window window;
	private Graphics graphics;

	private Audio audio;

	public void start() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		// Create our window.
		window = new Window("Trash Unit Response Droid (T.U.R.D)", WINDOW_WIDTH, WINDOW_HEIGHT);

		// Create graphics.
		graphics = new Graphics(window, NVG_ANTIALIAS);

		// Create audio.
		audio = new Audio();

		// Create our player.
		ObjectList.getInstance().createPlayer();

		// Setup an obstacle that the player cannot pass through.

		// (Length , Height)
		// Window size
		// 720
		// 1280

		new LongPlatform(0, 660);
		new SmallSquare(0, 200);

		audio.play("laser");

		window.render(() -> {

			GameState.getInstance().update(window);

			graphics.beginFrame();

			// Draw FPS/Render time stats.
			graphics.setColor(255.f, 255.f, 255.f, 255.f);
			graphics.drawString(String.format("Trash Unit Response Droid (T.U.R.D)\nFPS: %.0f (%.3f m/s)",
					window.getFps(), window.getFrameTime() * 1000.0), 2, 2, 1.f);

			// Render all objects.
			ObjectList.getInstance().render(window, graphics);

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

		}, 0.3F, 0.3F, 0.32F, 1.F);

		// Terminate the window and cleanup NanoVG context.
		window.terminate();
		graphics.terminate();
		audio.terminate();
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
