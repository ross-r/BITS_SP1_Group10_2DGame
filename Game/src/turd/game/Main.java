package turd.game;

import org.lwjgl.*;

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
		
		window.render(() -> {
			
			graphics.beginFrame();

			// Draw FPS/Render time stats.
			graphics.setColor(255.f, 255.f, 255.f, 255.f);
			graphics.drawString(String.format("Trash Unit Response Droid (T.U.R.D)\nFPS: %.0f (%.3f m/s)", window.getFps(), window.getFrameTime() * 1000.0), 2, 2, 1.f);
			
			//
			graphics.setColor(0.f, 255.f, 255.f, 127.f);
			graphics.drawFilledRect(40, 40, 80, 80);
			//
						
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
