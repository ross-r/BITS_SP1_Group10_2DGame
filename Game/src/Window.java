import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Window {

	// The handle to our window.
	private long hHandle;
	
	// The width and height supplied for window creation.
	private int iWidth;
	private int iHeight;
	
	// The internal width and height of the window, commonly referred as frame width/height.
	// The width and height here are used for frame buffers, that is, internal viewports and such for rendering.
	private int iFrameBufferWidth;
	private int iFrameBufferHeight;
	
	//
	private float flContentScaleX;
	private float flContentScaleY;
	
	// The window title.
	private String sTitle;
	
	// Default window properties that can be overloaded by a different constructor.
	// TODO: @Ross, add other constructor to initialize values.
	private int iResizable;
	private int iVisible;
	private int iMSAAQuality;
	
	public Window(String title, int w, int h) {
		this.sTitle = title;
		this.iWidth = w;
		this.iHeight = h;

		//
		// Default properties.
		//
		
		// Allow the window to be resized.
		iResizable = GLFW_TRUE;
		
		// Is this window visible?
		iVisible = GLFW_TRUE;
		
		// Enable Multisample anti-aliasing.
		// This value must be 2^n
		iMSAAQuality = 16;
		
		this.initAndCreateWindow();
	}
	
	private void updateWindowSizes() {
		try(MemoryStack stack = stackPush()) {
			
			// Get the frame buffer sizes.
			IntBuffer fw = stack.mallocInt(1);
			IntBuffer fh = stack.mallocInt(1);

			glfwGetFramebufferSize(hHandle, fw, fh);
			this.iFrameBufferWidth = fw.get();
			this.iFrameBufferHeight = fh.get();
			
			// Get the content scales.
			FloatBuffer sx = stack.mallocFloat(1);
			FloatBuffer sy = stack.mallocFloat(1);
			
			glfwGetWindowContentScale(hHandle, sx, sy);
			this.flContentScaleX = sx.get();
			this.flContentScaleY = sy.get();
		}
	}
	
	private void initAndCreateWindow() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();
		
		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		//
		// Configure GLFW
		//
		
		// optional, the current window hints are already the default
		glfwDefaultWindowHints();
		
		glfwWindowHint(GLFW_VISIBLE, iVisible);
		glfwWindowHint(GLFW_RESIZABLE, iResizable);
		
		/*
		 GLFW_SCALE_TO_MONITOR specified whether the window content area should be resized based on the monitor content scale of any monitor it is placed on. 
		 This includes the initial placement when the window is created. Possible values are GLFW_TRUE and GLFW_FALSE.
		 
		 This is for larger resolution monitors like 1440p, 4K and retina displays.
		 */
		glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE);
		
		// TODO: @Ross, temporarily disabled as Leo has window creation failures on macOS.
		// 				this may be the cause of those issues.
		//glfwWindowHint(GLFW_SAMPLES, iMSAAQuality);
		
		//
		// Window creation.
		//
		
		// Create the window.
		this.hHandle = glfwCreateWindow(iWidth, iHeight, sTitle, NULL, NULL);
		if(this.hHandle == NULL) {
			throw new RuntimeException("Failed to create GLFW window.");
		}
		
		// Update the window width and height and internal buffer sizes.
		updateWindowSizes();
		
		// Get the video mode for the primary display.
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		// Center the window on the primary monitor.
		glfwSetWindowPos(hHandle, (vidmode.width() - iWidth) / 2, (vidmode.height() - iHeight) / 2);	

		// Make the OpenGL context current.
		glfwMakeContextCurrent(hHandle);
		
		// Enable v-sync.
		// TODO: @Ross, make this optional.
		glfwSwapInterval(1);
		
		// Make the window visible.
		glfwShowWindow(hHandle);
		
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(hHandle, (window, key, scancode, action, mods) -> {
			
			// We'll use this to notify the input class of changes.
			inputCallback(window, key, scancode, action, mods);
		});
		
		// Setup a size callback.
		glfwSetWindowSizeCallback(hHandle, (window, width, height) -> {
			this.iWidth = width;
			this.iHeight = height;
			
			updateWindowSizes();
		});
	}
	
	public void render(Runnable callback, float r, float g, float b, float a) {
		
		if(this.hHandle == NULL) {
			throw new RuntimeException("Invalid window handle in Window::render");
		}
		
		// TODO: @Ross, render statistics (fps, frame time, etc..)
		
		while(!glfwWindowShouldClose(hHandle)) {
		
			// Set the clear color.
			glClearColor(r, g, b, a);
			
			// Clear a couple buffers that NanoVG and other libraries may touch.
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		
			// Make sure to update the viewport for window resizing.
			glViewport(0, 0, this.iFrameBufferWidth, this.iFrameBufferHeight);
			
			// If we have a render callback execute it now.
			if(callback != null) {
				callback.run();
			}
			
			glfwSwapBuffers(hHandle);
			
			// Poll for window events. The key callback above will only be invoked during this call.
			glfwPollEvents();
		}
	}
	
	public void terminate() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(hHandle);
		glfwDestroyWindow(hHandle);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	private void inputCallback(long window, int key, int scancode, int action, int mods) {
		// TODO: Implement input class.		
	}

	//
	// Getters/Setters
	//
	
	public int getWidth() {
		return iWidth;
	}
	
	public int getHeight() {
		return iHeight;
	}
	
	public int getScaledWidth() {
		return (int)(this.iFrameBufferWidth / this.flContentScaleX);
	}
	
	public int getScaledHeight() {
		return (int)(this.iFrameBufferHeight / this.flContentScaleY);
	}
	
	public int getPixelRatio() {
		int a = getScaledWidth();
		int b = getScaledHeight();
		return a > b ? a : b;
	}
}
