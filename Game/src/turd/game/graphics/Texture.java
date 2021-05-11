package turd.game.graphics;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.nanovg.NanoVG.*;

public class Texture {
    // NanoVG handle shared from Graphics class.
    private final long vg;

    private final ByteBuffer image;

    private final int iWidth;
    private final int iHeight;
    private final int iTexID;

    public Texture(long vg, String imagePath) {
        this.vg = vg;

        ByteBuffer pathBuffer;
        try {
            pathBuffer = IOUtil.ioResourceToByteBuffer(imagePath, 32 * 32);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            // Parse the image info and obtain the width, height and channels.
            if (!stbi_info_from_memory(pathBuffer, w, h, c)) {
                throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
            }

            // Since we are loading RGBA via nvgCreateImageRGBA we need our byte buffer to have enough
            // size to support 4 channels, even if our image isn't 4 channels.
            //
            // i.e; width * height * channels = required size
            //
            // An example of this would be 12 * 12 * 3 = 49152
            // Which may not be enough data to fill in the remaining channel, we would instead need
            // 128 * 128 * 4 = 65536 bytes of data.
            final int iDesiredChannels = 4;

            // Load the image buffer.
            this.image = stbi_load_from_memory(pathBuffer, w, h, c, iDesiredChannels);
            if (this.image == null) {
                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
            }
            
            // Store off some variables we'll use later.
            this.iWidth = w.get();
            this.iHeight = h.get();
        }

        // Let NanoVG create the image from the RGBA buffer we have read via STB image library.
        this.iTexID = nvgCreateImageRGBA(this.vg, this.iWidth, this.iHeight, 0, this.image);
    }
    
    public void terminate() {
    	if(this.image == null) {
    		return;
    	}
    	
    	stbi_image_free(this.image);
    }

    // Draws the texture with a fixed width and height.
    public void render(int x, int y, int w, int h, float alpha) {
        nvgBeginPath(vg);

        // NanoVG parameter fields for nvgImagePattern.
        float ox = (float) x;
        float oy = (float) y;
        float ex = (float) w;
        float ey = (float) h;

        // As done by NanoVG example.
        float angle = (float) Math.toRadians(0.f);
        float _alpha = alpha / 255.f;

        NVGPaint paint = nvgImagePattern(this.vg, ox, oy, ex, ey, angle, this.iTexID, _alpha, NVGPaint.create());

        nvgRect(vg, x, y, w, h);
        nvgFillPaint(vg, paint);
        nvgFill(vg);
    }

    // Draws the texture with the textures width and height.
    public void render(int x, int y, float alpha) {
        render(x, y, this.iWidth, this.iHeight, alpha);
    }

    public int getWidth() {
        return this.iWidth;
    }

    public int getHeight() {
        return this.iHeight;
    }

    // Failed attempt to animate the background image :(
    
    boolean bHasPreviousFrame = false;

    byte previous0 = 0;
    byte previous1 = 0;
    byte previous2 = 0;

    int x = 0;
    public void animate(int w, int h) {
        /*if(!KeyboardInput.getInstance().isKeyClicked(GLFW.GLFW_KEY_1)) {
			return;
		}*/

        for (int o = 0; o < 128; o++) {

            if (x + 1 > w) {
                x = 0;
            }

            System.out.printf("%d\n", x);

            int stride = this.iWidth * 4;
            for (int y = 0; y < this.iHeight; y++) {

                int i = y * stride + x * 4;

                int x1 = (x + 1);

                int j = y * stride + x1 * 4;

                byte next0 = image.get(j + 0);
                byte next1 = image.get(j + 1);
                byte next2 = image.get(j + 2);

                if( bHasPreviousFrame ) {
                    image.put(i + 0, previous0);
                    image.put(i + 1, previous1);
                    image.put(i + 2, previous2);
                }
                
                image.put(i + 0, next0);
                image.put(i + 1, next1);
                image.put(i + 2, next2);

                previous0 = next0;
                previous1 = next1;
                previous2 = next2;
                bHasPreviousFrame = true;

            }

            x++;
        }

        nvgUpdateImage(this.vg, this.iTexID, this.image);
    }
}