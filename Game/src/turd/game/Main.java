package turd.game;

import org.lwjgl.*;

import turd.game.entities.Player;
import turd.game.graphics.Graphics;
import turd.game.objects.ObjectList;
import turd.game.objects.StaticObject;

// We have to use NanoVG OpenGL 2 for Mac users as OpenGL 3 is not supported.
// NanoVG uses shader version 150 in OpenGL 3 context which cannot compile under Mac.
import static org.lwjgl.nanovg.NanoVGGL2.*;

public class Main {

	private final int WINDOW_WIDTH = 1280;
	private final int WINDOW_HEIGHT = 720;

	private Window window;
	private Graphics graphics;

	public void start() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		// Create our window.
		window = new Window("Trash Unit Response Droid (T.U.R.D)", WINDOW_WIDTH, WINDOW_HEIGHT);

		// Create graphics.
		graphics = new Graphics(window, NVG_ANTIALIAS);

		// Create our player.
		ObjectList.getInstance().createPlayer();

		// Setup an obstacle that the player cannot pass through.

		StaticObject obstacle0 = ObjectList.getInstance().createStaticObject();
		obstacle0.setPos(0, 660);
		obstacle0.setBounds(1280, 60);

		StaticObject obstacle1 = ObjectList.getInstance().createStaticObject();
		obstacle1.setPos(0, 260);
		obstacle1.setBounds(60, 400);

		StaticObject obstacle2 = ObjectList.getInstance().createStaticObject();
		obstacle2.setPos(700, 460);
		obstacle2.setBounds(300, 200);

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
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
