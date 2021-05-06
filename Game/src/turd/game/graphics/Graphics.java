package turd.game.graphics;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL2.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.nanovg.NVGColor;

//import sun.nio.ch.IOUtil;
import turd.game.Window;

public class Graphics {

	// NanoVG handle.
	// This must be passed to all NanoVG function calls.
	// Since this needs to be used in all NanoVG function calls and the Graphics class
	// is not static or doesn't have a singleton function, this variable is now static so it can
	// be accessed from anywhere as it is needed.
	private static long nvgHandle;
	
	private NVGColor color;
	
	private Window window;
	private FontRenderer fontRenderer;
	
	public Graphics(Window window, int flags) {
		nvgHandle = nvgCreate(flags);
		
		this.window = window;
		this.fontRenderer = new FontRenderer(window);
		
		if (nvgHandle == NULL) {
			throw new RuntimeException("Could not create nanovg.");
        }
	
		this.color = NVGColor.create();
	}
	
	public void terminate() {
		nvgDelete(nvgHandle);
	}

	public void beginFrame() {
		this.fontRenderer.setGLStates();
		
		nvgBeginFrame(nvgHandle, window.getScaledWidth(), window.getScaledHeight(), window.getPixelRatio());
	}
	
	public void endFrame() {
		nvgEndFrame(nvgHandle);
	}
	
	public void drawString(String text, int x, int y) {
		this.fontRenderer.drawString(text, x, y, 1.f);
	}
	
	public void drawString(String text, int x, int y, float scale) {
		this.fontRenderer.drawString(text, x, y, scale);
	}
	
	public void setColor(float r, float g, float b, float a) {
		this.color.r(r / 255.f);
		this.color.g(g / 255.f);
		this.color.b(b / 255.f);
		this.color.a(a / 255.f);
		
		this.fontRenderer.setColor(this.color.r(), this.color.g(), this.color.b(), this.color.a());
		
		// Set colour for lines
		nvgStrokeColor(nvgHandle, this.color);
	}
	
	public void translate(int x, int y) {
		nvgTranslate(nvgHandle, x, y);
		
		// Update text translations.
		fontRenderer.translate(x, y);
	}
	
	public void rotate(float angle) {
		nvgRotate(nvgHandle, angle);
	}
	
	public void drawFilledRect(int x, int y, int width, int height) {
		nvgBeginPath(nvgHandle);
		nvgRect(nvgHandle, x, y, width, height);
		nvgFillColor(nvgHandle, color);
		nvgFill(nvgHandle);
	}
	
	public void drawRect(int x, int y, int width, int height) {
		nvgBeginPath(nvgHandle);
		nvgRect(nvgHandle, x, y, width, height);
		nvgStrokeColor(nvgHandle, color);
		nvgStroke(nvgHandle);
	}
	
	public void drawFilledCircle(float cx, float cy, float r) {
		nvgBeginPath(nvgHandle);
		nvgCircle(nvgHandle, cx, cy, r);
		nvgFillColor(nvgHandle, color);
		nvgFill(nvgHandle);
	}
	
	public void drawLine(int iX1, int iY1, int iX2, int iY2) {
		nvgBeginPath(nvgHandle);
		nvgMoveTo(nvgHandle, iX1, iY1);
		nvgLineTo(nvgHandle, iX2, iY2);
		nvgStroke(nvgHandle);
		nvgFill(nvgHandle);
	}
	
	
	public static long nvgHandle() {
		return nvgHandle;
	}
}
