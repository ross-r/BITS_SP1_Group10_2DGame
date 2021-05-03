package turd.game.graphics;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL2.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_info_from_memory;
import static org.lwjgl.stb.STBImage.stbi_is_hdr_from_memory;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.NativeType;

//import sun.nio.ch.IOUtil;
import turd.game.Window;

// TODO: Singleton.
public class Graphics {

//	//byte buffer for the image rendering
//    private final ByteBuffer image;
//	
//	//image height and width
//    private final int iIWidth;
//    private final int iIHeight;
//    private final int iIComp;
//    //private final String imageName;
	
	
	// NanoVG handle.
	// This must be passed to all NanoVG function calls.
	private long vg;
	
	private NVGColor color;
	
	private Window window;
	private FontRenderer fontRenderer;
	
	//public Graphics(Window window, int flags, String imageName)
	
	//original constructor
	public Graphics(Window window, int flags) {
		this.vg = nvgCreate(flags);
		this.window = window;
		this.fontRenderer = new FontRenderer(window);
		
		if (this.vg == NULL) {
			throw new RuntimeException("Could not create nanovg.");
        }
	
		this.color = NVGColor.create();
	}
	
	
//	//Graphics constructor mixed with image demo constructor for testing purposes
//    public Graphics(Window window, int flags, String imagePath) {
//		this.vg = nvgCreate(flags);
//		this.window = window;
//		this.fontRenderer = new FontRenderer(window);
//		
//		if (this.vg == NULL) {
//			throw new RuntimeException("Could not create nanovg.");
//        }
//	
//		this.color = NVGColor.create();
//        ByteBuffer imageBuffer;
//        try {
//            imageBuffer = IOUtil.ioResourceToByteBuffer(imagePath, 8 * 1024);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        try (MemoryStack stack = stackPush()) {
//            IntBuffer iIWidth    = stack.mallocInt(1);
//            IntBuffer iIHeight    = stack.mallocInt(1);
//            IntBuffer iIComp = stack.mallocInt(1);
//
//            // Use info to read image metadata without decoding the entire image.
//            // We don't need this for this demo, just testing the API.
//            if (!stbi_info_from_memory(imageBuffer, iIWidth, iIHeight, iIComp)) {
//                throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
//            } else {
//                System.out.println("OK with reason: " + stbi_failure_reason());
//            }
//
//            System.out.println("Image width: " + iIWidth.get(0));
//            System.out.println("Image height: " + iIHeight.get(0));
//            System.out.println("Image components: " + iIComp.get(0));
//            System.out.println("Image HDR: " + stbi_is_hdr_from_memory(imageBuffer));
//
//            // Decode the image
//            image = stbi_load_from_memory(imageBuffer, iIWidth, iIHeight, iIComp, 0);
//            if (image == null) {
//                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
//            }
//
//            this.iIWidth = iIWidth.get(0);
//            this.iIHeight = iIHeight.get(0);
//            this.iIComp = iIComp.get(0);
//        }
//    }
	
	public void terminate() {
		nvgDelete(vg);
	}

	public void beginFrame() {
		this.fontRenderer.setGLStates();
		
		nvgBeginFrame(vg, window.getScaledWidth(), window.getScaledHeight(), window.getPixelRatio());
	}
	
	public void endFrame() {
		nvgEndFrame(vg);
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
		//Set colour for lines
		nvgStrokeColor(vg, color);
	}
	
	//leo attempting stbi load
	//createImage makes a new object of Image
	public void createImage(java.nio.ByteBuffer imageName, int[] x, int[] y, int[] channels_in_file,
            int desired_channels) {
		//new Image("dfa.jpg").run();		
	}		
	
	public void createPlayerTexture() {
//		String imagePath = "player.png";
		String imagePath = "dfa.jpg";
		new Image(imagePath).run();
	}
	
//	nvglCreateImageFromHandleGL2()
	
	
//	 public static int nvgCreateImageMem(@NativeType("NVGcontext *") long ctx, int imageFlags, @NativeType("unsigned char *") ByteBuffer data) {
//	        if (CHECKS) {
//	            check(ctx);
//	        }
//	        return nnvgCreateImageMem(ctx, imageFlags, memAddress(data), data.remaining());
//	    }
	
	
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
	
	public void drawFilledCircle(float cx, float cy, float r) {
		nvgBeginPath(vg);
		nvgCircle(vg, cx, cy, r);
		nvgFillColor(vg, color);
		nvgFill(vg);
	}
	
	public void drawLine(int iX1, int iY1, int iX2, int iY2) {
		nvgBeginPath(vg);
		nvgMoveTo(vg, iX1, iY1);
		nvgLineTo(vg, iX2, iY2);
		nvgStroke(vg);
		nvgFill(vg);
	}
	
	
	public long vg() {
		return this.vg;
	}
}
