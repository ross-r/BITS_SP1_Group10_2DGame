package turd.game;

import org.lwjgl.*;
import org.lwjgl.glfw.GLFW;

import turd.game.audio.Audio;
import turd.game.entities.Player;
import turd.game.graphics.Graphics;
import turd.game.input.KeyboardInput;
import turd.game.objects.ObjectList;
import turd.game.objects.StaticObject;

import turd.game.physics.AABB;
import turd.game.physics.Point;

// We have to use NanoVG OpenGL 2 for Mac users as OpenGL 3 is not supported.
// NanoVG uses shader version 150 in OpenGL 3 context which cannot compile under Mac.
import static org.lwjgl.nanovg.NanoVGGL2.*;

public class Main {

	private final int WINDOW_WIDTH = 1280;
	private final int WINDOW_HEIGHT = 720;

	private Window window;
	private Graphics graphics;
	private Audio audio;
	
	private Runnable _render = new Runnable() {

		@Override
		public void run() {
			render();
		}
		
	};
	
	private Runnable _tick = new Runnable() {

		@Override
		public void run() {
			tick();
		}
		
	};

	private AABB bbox;
	private AABB bbox1;
	
	private void test_aabb_gravity() {
		final float gravity = 4.f;
		
		float y = bbox1.p0.y;
		bbox1.p0.y += gravity;
		
		// cant move any further, set to last y coordinate.
		if (bbox1.collides(bbox)) {
			bbox1.p0.y = y;
		}
	}
	
	private void test_aabb() {
		// TODO: Test physics stuff
		
		test_aabb_gravity();
		
		// Try move down once per tick until collision happens.
		float x = bbox1.p0.x;
		float y = bbox1.p0.y;
		
		if (KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_A)) {
			bbox1.p0.x -= 4.f;
		}
		
		if (KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_D)) {
			bbox1.p0.x += 4.f;
		}
		
		if (KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_W)) {
			bbox1.p0.y -= 8.f;
		}
		
		// cant move any further, set to last y coordinate.
		if (bbox1.collides(bbox)) {
			bbox1.p0.x = x;
			bbox1.p0.y = y;
		}
	}
	
	private void render_aabb() {
		//bbox1.init((float)window.getMouseX(), (float)window.getMouseY(), 30.f, 30.f);
		
		graphics.setColor(0.f, 155.f, 60.f, 255.f);
		graphics.drawFilledRect((int)bbox.p0.x, (int)bbox.p0.y, (int)bbox.p1.x, (int)bbox.p1.y);
		
		graphics.setColor(255.f, 255.f, 255.f, 255.f);
		graphics.drawFilledRect((int)bbox1.p0.x, (int)bbox1.p0.y, (int)bbox1.p1.x, (int)bbox1.p1.y);
		
		/*
		Point cursor = new Point( (float)window.getMouseX(), (float)window.getMouseY() );
		if ( bbox.collides( bbox1 ) ) {
			
			AABB intersection = bbox.intersection(bbox1);
			
			graphics.setColor(255.f, 0.f, 0.f, 255.f);
			graphics.drawFilledRect((int)intersection.p0.x, (int)intersection.p0.y, (int)intersection.p1.x, (int)intersection.p1.y);

			graphics.setColor(0.f, 255.f, 0.f, 255.f);
			graphics.drawFilledRect((int)intersection.p0.x, (int)intersection.p0.y, 8, 8);
			
		}
		*/
	}
	
	public void render() {
		GameState.getInstance().update(window);
		
		graphics.beginFrame();

		// Draw FPS/Render time stats.
		graphics.setColor(255.f, 255.f, 255.f, 255.f);
		graphics.drawString(String.format("Trash Unit Response Droid (T.U.R.D)\nFPS: %d\nTPS: %d",
				window.getFps(), window.getTicks()), 2, 2, 1.f);

		// Render all objects.
		ObjectList.getInstance().render(window, graphics);

		/*
		 * START AABB TESTINIG
		 */
		render_aabb();
		/*
		 * END AABB TESTING
		 */
		
		// ----------------------- PAUSE MENU HUD
		if (GameState.getInstance().isPaused()) {
			
			// Draw a darker background that is on top of everything else.
			graphics.setColor(0.f, 0.f, 0.f, 127.f);
			graphics.drawFilledRect(0, 0, window.getWidth(), window.getHeight());
			
			// Draw text indicating the game is paused.
			graphics.setColor(255.f, 255.f, 255.f, 255.f);
			graphics.drawString("GAME PAUSED", 2, 2, 16.f);
			
		}
		// ----------------------- PAUSE MENU HUD
		
		graphics.endFrame();
	}
	
	public void tick() {
		// If the game is paused then don't process any ticks.
		if (GameState.getInstance().isPaused()) {
			return;
		}
		
		ObjectList.getInstance().tick(window);
		
		//System.out.println("tick");
		
		test_aabb();
	}
	
	public void start() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		// Create our window.
		window = new Window("Trash Unit Response Droid (T.U.R.D)", WINDOW_WIDTH, WINDOW_HEIGHT);

		// Create graphics.
		graphics = new Graphics(window, NVG_ANTIALIAS);
		
		// Create audio.
		audio = new Audio();

		// Create our player.
		ObjectList.getInstance().createPlayer();

		// Setup an obstacle that the player cannot pass through.
 
		/*
		StaticObject obstacle0 = ObjectList.getInstance().createStaticObject();
		obstacle0.setPos(0, 660);
		obstacle0.setBounds(1280, 60);

		StaticObject obstacle1 = ObjectList.getInstance().createStaticObject();
		obstacle1.setPos(0, 260);
		obstacle1.setBounds(60, 400);

		StaticObject obstacle2 = ObjectList.getInstance().createStaticObject();
		obstacle2.setPos(700, 460);
		obstacle2.setBounds(300, 200);
		*/
		
		bbox = new AABB();
		bbox1 = new AABB();
		
		bbox.init(40.f, 250.f, 600.f, 2.f);
		bbox1.init(55.f, 35.f, 30.f, 30.f);
		
		//audio.play("laser");

		window.loop(_render, _tick);

		// Terminate the window and cleanup NanoVG context.
		window.terminate();
		graphics.terminate();
		audio.terminate();
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
