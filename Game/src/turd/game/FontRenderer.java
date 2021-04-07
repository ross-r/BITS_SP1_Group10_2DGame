package turd.game;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.stb.STBEasyFont.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class FontRenderer {

	static final int MEM_1KB 	= 0x400;
	static final int MEM_4KB 	= MEM_1KB * 4;
	static final int MEM_8KB 	= MEM_1KB * 8;
	static final int MEM_16KB 	= MEM_1KB * 16;
	
	// This is the buffer that stb_easy_font_print will project quads into for rendering.
	// This buffer needs to have an appropriate size to fit enough quads for any given string
	// length.
	private ByteBuffer buffer;
	
	// Reference needed for window width/height.
	private Window window;
	
	// Used to translate text positions.
	private int iTranslateX;
	private int iTranslateY;
	
	// Used to color the string.
	private float flRed;
	private float flGreen;
	private float flBlue;
	private float flAlpha;
	
	public FontRenderer(Window window) {
		this.window = window;
		
		// 16KB should be enough.
		this.buffer = memAlloc(MEM_16KB);
		
		this.iTranslateX = 0;
		this.iTranslateY = 0;
		
		this.flRed = 1.f;
		this.flGreen = 1.f;
		this.flBlue = 1.f;
		this.flAlpha = 1.f;
	}
	
	public void setGLStates() {
		glEnableClientState(GL_VERTEX_ARRAY);
		glVertexPointer(2, GL_FLOAT, 16, buffer);
	}
	
	public void drawString(String text, int x, int y, float scale) {
		glPushMatrix();
		
		// Allow transparency.
		glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		// Set up projection used by STBEasyFont.
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, window.getWidth(), window.getHeight(), 0.0, -1.0, 1.0);
		glMatrixMode(GL_MODELVIEW);
		
		int quads = stb_easy_font_print(x, y, text, null, buffer);
		
		// Set scale.
		glScalef(scale, scale, 1.f);
		
		// Set translation.
		// Relative to original coordinates.
		glTranslatef(x + this.iTranslateX, y + this.iTranslateY, 0);
		
		// Set color.
		glColor4f(this.flRed, this.flGreen, this.flBlue, this.flAlpha);
		
        glDrawArrays(GL_QUADS, 0, quads * 4);
        
        glPopMatrix();
	}

	public void setColor(float r, float g, float b, float a) {
		this.flRed = r;
		this.flGreen = g;
		this.flBlue = b;
		this.flAlpha = a;
	}
	
	public void translate(int x, int y) {
		iTranslateX = x;
		iTranslateY = y;
	}
	
}
