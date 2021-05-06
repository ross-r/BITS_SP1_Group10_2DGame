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
        	 pathBuffer = IOUtil.ioResourceToByteBuffer(imagePath, 1024 * 1024);
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

             // Load the image buffer.
             image = stbi_load_from_memory(pathBuffer, w, h, c, 0);
             if (image == null) {
                 throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
             }

             // Store off some variables we'll use later.
             this.iWidth = w.get();
             this.iHeight = h.get();
         }

         // Let NanoVG create the image from the RGBA buffer we have read via STB image library.
         this.iTexID = nvgCreateImageRGBA( this.vg, this.iWidth, this.iHeight, 0, this.image );
     }

     // Draws the texture with a fixed width and height.
     public void render( int x, int y, int w, int h, float alpha ) {
		nvgBeginPath(vg);
		
		// NanoVG parameter fields for nvgImagePattern.
		float ox = ( float )x;
		float oy = ( float )y;
		float ex = ( float )w;
		float ey = ( float )h;
		
		// As done by NanoVG example.
		float angle = ( float )Math.toRadians( 0.f );
		float _alpha = alpha / 255.f;
		
		NVGPaint paint = nvgImagePattern( this.vg, ox, oy, ex, ey, angle, this.iTexID, _alpha, NVGPaint.create() );
		
		nvgRect(vg, x, y, w, h);
		nvgFillPaint(vg, paint);
		nvgFill(vg);
     }
     
     // Draws the texture with the textures width and height.
     public void render( int x, int y, float alpha ) {
    	 render( x, y, this.iWidth, this.iHeight, alpha );
     }
     
     public int getWidth() {
    	 return this.iWidth;
     }
     
     public int getHeight() {
    	 return this.iHeight;
     }
 }