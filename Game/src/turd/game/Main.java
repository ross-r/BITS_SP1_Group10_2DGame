package turd.game;

import org.lwjgl.*;
import org.lwjgl.glfw.GLFW;

import turd.game.input.KeyboardInput;

// We have to use NanoVG OpenGL 2 for Mac users as OpenGL 3 is not supported.
// NanoVG uses shader version 150 in OpenGL 3 context which cannot compile under Mac.
import static org.lwjgl.nanovg.NanoVGGL2.*;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

	private final int WINDOW_WIDTH = 1280;
	private final int WINDOW_HEIGHT = 720;
	
	private Window window;
	private Graphics graphics;
	
	double iPlayerX = 128;
	double iPlayerY = 128;
	int iPlayerW = 64;
	int iPlayerH = 64;
		
	public void start() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		// Create our window.
		window = new Window("Trash Unit Response Droid (T.U.R.D)", WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// Create graphics.
		graphics = new Graphics(window, NVG_ANTIALIAS);
		
		window.render(() -> {
			
			graphics.beginFrame();

			// Draw FPS/Render time stats.
			graphics.setColor(255.f, 255.f, 255.f, 255.f);
			graphics.drawString(String.format("Trash Unit Response Droid (T.U.R.D)\nFPS: %.0f (%.3f m/s)", window.getFps(), window.getFrameTime() * 1000.0), 2, 2, 1.f);
			
			//
			graphics.setColor(0.f, 255.f, 255.f, 127.f);
			graphics.drawFilledRect((int)iPlayerX, (int)iPlayerY, iPlayerW, iPlayerH);
			//
			
			// TODO: Ross - Player class.
			{
				// Make a PoC player that can move.
				// This is just a rectangle, I'll expand it into GameObjects/Player classes later.
				
				// We want our movement speed to be constant on different frame times.
				// If someone is running the game at 144 frames per second
				// this code will be executed much more than someone at 60 frames per second
				// thus they will effectively move faster.
				// 
				// Choose a constant speed for all frame rates.
	
				// Disable V-SYNC for testing (this will make your fps very very high)
				// 0 = V-SYNC OFF
				// 1 = V-SYNC ON
				glfwSwapInterval(1);
				
				// Our constant move speed that we want.
				// We can use this to boost player move speed if we pick up objects, etc...
				double moveSpeed = 500;
				
				// Check which movement keys are pressed.
				boolean bInMoveLeft = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_A);
				boolean bInMoveRight = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_D);
				boolean bInMoveUp = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_W);
				boolean bInMoveDown = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_S);
				
				// Setup some movement vectors.
				float flSideMove = bInMoveLeft ? -1.f : bInMoveRight ? 1.f : 0.f;
				float flUpMove = bInMoveUp ? -1.f : bInMoveDown ? 1.f : 0.f;
				
				// Make sure we want movement to happen.
				if (flSideMove != 0.f || flUpMove != 0.f) {
				
					// Compute the movement direction based on the pressed keys.
					// This is an angle in radians.
					// Use Math.toDegrees(flDirection) to convert from radians to degrees.
					double flDirection = Math.atan2(-flSideMove, flUpMove);
					
					// Compute the direction from the angle.
					double flDirectionX = Math.sin(-flDirection);
					double flDirectionY = Math.cos(flDirection);
					
					//System.out.printf("sidemove: %f, upmove: %f, direction: %f (%f) (x: %f, y: %f)\n", flSideMove, flUpMove, flDirection, Math.toDegrees(flDirection), flDirectionX, flDirectionY);
					
					// Finally adjust our players position.
					iPlayerX += (moveSpeed * window.getFrameTime()) * flDirectionX;
					iPlayerY += (moveSpeed * window.getFrameTime()) * flDirectionY;
				}
			}
			
			graphics.endFrame();
			
		}, 0.3F, 0.3F, 0.32F, 1.F);
		
		// Terminate the window and cleanup NanoVG context.
		window.terminate();
		graphics.terminate();
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
