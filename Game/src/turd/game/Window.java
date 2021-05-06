package turd.game;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import turd.game.input.KeyboardInput;
import turd.game.input.MouseInput;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Window {

	// How many nano-seconds are in a second.
	public static final double NS_PER_SECOND = 1000000.0;
	
	// How many ticks are in a second.
	public static final double NS_PER_TICK = NS_PER_SECOND / 60.0;
	
	// Conversion metric for milliseconds to nanoseconds.
	public static final double MS_TO_NS = 1e+6;
	
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
	
	// The frame time between rendering cycles.
	private double flLastFrameTime;
	private double flFrameTime;
	
	// The time remaining once all tick divisions have been calculated per frame.
	private double flTickRemainder;

	private double flMouseX;
	private double flMouseY;
	
	//
	// Frame metrics.
	//
	private double flLastSecond;
	private int iFrames;
	private int iTicks;
	
	// Amount of frames per second. 
	private int iFps;
	
	// Amount of ticks per second.
	private int iTps;
	
	public Window(String title, int w, int h) {
		this.sTitle = title;
		this.iWidth = w;
		this.iHeight = h;
		this.flMouseX = 0.f;
		this.flMouseY = 0.f;
		this.iTicks = 0;
		this.flTickRemainder = 0.0;
		
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

		if (Platform.get() == Platform.MACOSX) {
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		}
		
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
		glfwWindowHint(GLFW_SAMPLES, iMSAAQuality);
		
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
			KeyboardInput.getInstance().updateKeys(this, key, scancode, action, mods);
		});
		
		// Setup a size callback.
		glfwSetWindowSizeCallback(hHandle, (window, width, height) -> {
			this.iWidth = width;
			this.iHeight = height;
			
			updateWindowSizes();
		});
		
		//See if mouse is clicked
		
		
		glfwSetMouseButtonCallback(hHandle, (window, button, action, mods) -> {
			MouseInput.getInstance().updateMouse(this, button, action, mods);
		});
		
		// Setup a cursor position callback.
		// This will notify us of where our mouse is within the winow.
		glfwSetCursorPosCallback(hHandle, (window, x, y) -> {
			
			
			this.flMouseX = x;
			this.flMouseY = y;
			
		});
	}
	
	public void loop(Runnable render, Runnable tick) {
		if (this.hHandle == NULL) {
			throw new RuntimeException("Invalid window handle in Window::render");
		}
		
		glfwSetTime(0);
		
		while ( !glfwWindowShouldClose(hHandle) ) {

			// If you're confused about the multiplication being performed here, it's just
			// converting milliseconds into nanoseconds.
			// 
			// Using nanoseconds for timing is easier when it comes to implementing ticks per second.
			double now = ( glfwGetTime() * MS_TO_NS );
			
			flFrameTime = now - flLastFrameTime;
			flLastFrameTime = now;
			
			// Update frame metrics once per second.
			if (now - flLastSecond > NS_PER_SECOND) {
				iFps = iFrames;
				iTps = iTicks;
				iFrames = 0;
				iTicks = 0;
				flLastSecond = now;
			}
			
			double flTickTime = flFrameTime + flTickRemainder;
			
			// While the frame time exceeds the ticks per second time
			// we have tick updates remaining, since frame time and tick time
			// cannot be perfectly in sync, that is, frame time is not always
			// divisible by the factor of our ticks per second, we will have a remainder,
			// so we take into account this remainder to allow for lost ticks on a previous
			// frame to be executed on the next.
			while (flTickTime > NS_PER_TICK) {
				iTicks++;

				if(tick != null) {
					tick.run();
				}
				
				flTickTime -= NS_PER_TICK;
			}
			
			// Our tick remainder is simply what ever is left once the loop condition exits out.
			flTickRemainder = Math.max(flTickTime, 0.0);
			
			// TODO: update function here for key input updates etc.
			
			// Set the clear color.
			glClearColor(0.f, 0.f, 0.f, 0.f);
			
			// Clear a couple buffers that NanoVG and other libraries may touch.
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

			// Make sure to update the viewport for window resizing.
			glViewport(0, 0, this.iFrameBufferWidth, this.iFrameBufferHeight);
			
			// If we have a render callback execute it now.
			if (render != null) {
				iFrames++;
				render.run();
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

	public double getFrameTime() {
		return flFrameTime;
	}

	public double getMouseX() {
		return flMouseX;
	}

	public double getMouseY() {
		return flMouseY;
	}
	
	public int getFps() {
		return iFps;
	}
	
	public int getTicks() {
		return iTps;
	}
}
