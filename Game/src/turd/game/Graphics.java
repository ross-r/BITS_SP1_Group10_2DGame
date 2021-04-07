package turd.game;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL2.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.nanovg.NVGColor;

// TODO: Singleton.
public class Graphics {

	// NanoVG handle.
	// This must be passed to all NanoVG function calls.
	private long vg;
	
	private NVGColor color;
	
	private Window window;
	private FontRenderer fontRenderer;
	
	public Graphics(Window window, int flags) {
		this.vg = nvgCreate(flags);
		this.window = window;
		this.fontRenderer = new FontRenderer(window);
		
		if (this.vg == NULL) {
			throw new RuntimeException("Could not create nanovg.");
        }
	
		this.color = NVGColor.create();
	}
	
	public void terminate() {
		nvgDelete(vg);
	}

	public void beginFrame() {
		fontRenderer.setGLStates();
		
		nvgBeginFrame(vg, window.getScaledWidth(), window.getScaledHeight(), window.getPixelRatio());
	}
	
	public void endFrame() {
		nvgEndFrame(vg);
	}
	
	public void drawString(String text, int x, int y) {
		fontRenderer.drawString(text, x, y, 1.f);
	}
	
	public void drawString(String text, int x, int y, float scale) {
		fontRenderer.drawString(text, x, y, scale);
	}
	
	public void setColor(float r, float g, float b, float a) {
		this.color.r(r);
		this.color.g(g);
		this.color.b(b);
		this.color.a(a);
	}
	
	public void translate(int x, int y) {
		nvgTranslate(vg, x, y);
		
		// Update text translations.
		fontRenderer.translate(x, y);
	}
	
	public void rotate(float angle) {
		nvgRotate(vg, angle);
	}
	
	public void drawFilledRect(int x, int y, int width, int height) {
		nvgBeginPath(vg);
		nvgRect(vg, x, y, width, height);
		nvgFillColor(vg, color);
		nvgFill(vg);
	}
}
