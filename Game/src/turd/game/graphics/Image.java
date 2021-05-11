 package turd.game.graphics;


import static java.lang.Math.*; //for science ;)
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBImageResize.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryStack;

import turd.game.Window;

public class Image {
	//byte buffer for the image rendering
  private final ByteBuffer image;
	
	//image height and width
  private int iIWidth;
  private int iIHeight;
  private final int iIComp;
  
  private int iTexID;
  
  private int scale;
	
	public Image (String imagePath) {
      ByteBuffer imageBuffer;
      try {
          imageBuffer = IOUtil.ioResourceToByteBuffer(imagePath, 10000 * 1024);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }

      try (MemoryStack stack = stackPush()) {
          IntBuffer iIWidth  = stack.mallocInt(1);
          IntBuffer iIHeight = stack.mallocInt(1);
          IntBuffer iIComp   = stack.mallocInt(1);

          // Unused in demo
          if (!stbi_info_from_memory(imageBuffer, iIWidth, iIHeight, iIComp)) {
              throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
          } else {
              System.out.println("OK with reason: " + stbi_failure_reason());
          }

//          System.out.println("Image width: " + iIWidth.get(0));
//          System.out.println("Image height: " + iIHeight.get(0));
//          System.out.println("Image components: " + iIComp.get(0));
//          System.out.println("Image HDR: " + stbi_is_hdr_from_memory(imageBuffer));

          // Decode the image
          image = stbi_load_from_memory(imageBuffer, iIWidth, iIHeight, iIComp, 0);
          if (image == null) {
              throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
          }

          this.iIWidth = iIWidth.get(0);
          this.iIHeight = iIHeight.get(0);
          this.iIComp = iIComp.get(0);
      }
      
      this.createTexture();
  }
	
    //requires a google
    private void premultiplyAlpha() {
        int stride = iIWidth * 4;
        for (int y = 0; y < iIHeight; y++) {
            for (int x = 0; x < iIWidth; x++) {
                int i = y * stride + x * 4;

                float alpha = (image.get(i + 3) & 0xFF) / 255.0f;
                image.put(i + 0, (byte)round(((image.get(i + 0) & 0xFF) * alpha)));
                image.put(i + 1, (byte)round(((image.get(i + 1) & 0xFF) * alpha)));
                image.put(i + 2, (byte)round(((image.get(i + 2) & 0xFF) * alpha)));
            }
        }
    }
    
    public int createTexture() {
    	//glGenTextures returns n texture names in textures. There is no guarantee that the names form a contiguous set of integers; however, it is guaranteed that none of the returned names was in use immediately before the call to glGenTextures.
        //The generated textures have no dimensionality; they assume the dimensionality of the texture target to which they are first bound (see glBindTexture).
        //Texture names returned by a call to glGenTextures are not returned by subsequent calls, unless they are first deleted with glDeleteTextures.
        this.iTexID = glGenTextures();
        //glBindTexture lets you create or use a named texture. Calling glBindTexture with target set to GL_TEXTURE_2D and texture set to the name of the new texture binds the texture name to the target. When a texture is bound to a target, the previous binding for that target is automatically broken.
        
        glBindTexture(GL_TEXTURE_2D, this.iTexID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        //this checks if this image is transparent or not, then chooses the appropriate format
        int format;
        if (iIComp == 3) {
            if ((iIWidth & 3) != 0) {
            	//GL_UNPACK_ALIGNMENT
            	//Specifies the alignment requirements for the start of each pixel row in memory. The allowable values are 1 (byte-alignment), 2 (rows aligned to even-numbered bytes), 4 (word-alignment), and 8 (rows start on double-word boundaries).
                glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (iIWidth & 1));
            }
            format = GL_RGB;
        } else {
            premultiplyAlpha();

            glEnable(GL_BLEND);
            glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

            format = GL_RGBA;
            
            System.out.println("texture is rgba format");
        }

        glTexImage2D(GL_TEXTURE_2D, 0, format, iIWidth, iIHeight, 0, format, GL_UNSIGNED_BYTE, image);

        ByteBuffer input_pixels = image;
        int        input_w      = iIWidth;
        int        input_h      = iIHeight;
        int        mipmapLevel  = 0;
        
        while (1 < input_w || 1 < input_h) {
            int output_w = Math.max(1, input_w >> 1);
            int output_h = Math.max(1, input_h >> 1);

            ByteBuffer output_pixels = memAlloc(output_w * output_h * iIComp);
            stbir_resize_uint8_generic(
                input_pixels, input_w, input_h, input_w * iIComp,
                output_pixels, output_w, output_h, output_w * iIComp,
                iIComp, iIComp == 4 ? 3 : STBIR_ALPHA_CHANNEL_NONE, STBIR_FLAG_ALPHA_PREMULTIPLIED,
                STBIR_EDGE_CLAMP,
                STBIR_FILTER_MITCHELL,
                STBIR_COLORSPACE_SRGB
            );

            if (mipmapLevel == 0) {
                stbi_image_free(image);
            } else {
                memFree(input_pixels);
            }

            glTexImage2D(GL_TEXTURE_2D, ++mipmapLevel, format, output_w, output_h, 0, format, GL_UNSIGNED_BYTE, output_pixels);

            input_pixels = output_pixels;
            input_w = output_w;
            input_h = output_h;
        }
        
        if (mipmapLevel == 0) {
            stbi_image_free(image);
        } else {
            memFree(input_pixels);
        }

        return this.iTexID;
    }
    
    public void render( int x, int y, int iWidth, int iHeight ) {
        float scaleFactor = 1.0f;

        glPushMatrix();
        
        glEnable(GL_TEXTURE_2D);
        
        glBindTexture(GL_TEXTURE_2D, this.iTexID);
        
        glTranslatef(x, y, 0.0f);
        
        //glScalef(scaleFactor, scaleFactor, 1f);
        //glTranslatef(-iHeight * 0.5f, -iIHeight * 0.5f, 0.0f);

        glBegin(GL_QUADS);
        {
            glTexCoord2f(0.0f, 0.0f);
            glVertex2f(0.0f, 0.0f);

            glTexCoord2f(1.0f, 0.0f);
            glVertex2f(iWidth, 0.0f);

            glTexCoord2f(1.0f, 1.0f);
            glVertex2f(iWidth, iHeight);

            glTexCoord2f(0.0f, 1.0f);
            glVertex2f(0.0f, iHeight);
        }
        glEnd();

        glDisable(GL_TEXTURE_2D);
        
        glPopMatrix();
    }
}
