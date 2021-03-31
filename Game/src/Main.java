import org.lwjgl.*;
import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL2.*;

import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

	private final int WINDOW_WIDTH = 1280;
	private final int WINDOW_HEIGHT = 720;
	
	private Window window;
	
	// Temporary NanoVG stuff.
	private long vg;
	private NVGColor color;

	public void start() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		// Create our window.
		window = new Window("Trash Unit Response Droid (T.U.R.D)", WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// Now that we have the window created we can initialize NanoVG.
		// TODO: This would be in its own class, something like Graphics.setup();
		vg = nvgCreate(NVG_ANTIALIAS);
		if (vg == NULL) {
			throw new RuntimeException("Could not create nanovg.");
        }
		
		// Initialize color instance.
		// This allocates 4 bytes in memory.
		color = NVGColor.create();
		
		window.render(() -> {
			
			// Begin the frame, setup NanoVG for rendering and set opengl states and buffers.
			// TODO: @Ross, set device pixel ratio properly.
			nvgBeginFrame(vg, window.getScaledWidth(), window.getScaledHeight(), window.getPixelRatio());
			
			// Render some stuff.
			color.r(255.F / 255.F);
			color.g(0.F / 255.F);
			color.b(0.F / 255.F);
			color.a(127.F / 255.F);
			
			nvgBeginPath(vg);
			nvgRoundedRect(vg, 0, 0, 80, 80, 9.F);
			nvgFillColor(vg, color);
			nvgFill(vg);
			
			// End the frame, this will set some opengl states and clear buffers.
			nvgEndFrame(vg);
			
		}, 0.3F, 0.3F, 0.32F, 1.F);
		
		// Terminate the window and cleanup NanoVG context.
		window.terminate();
		nvgDelete(vg);
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
