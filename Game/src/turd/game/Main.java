package turd.game;

// We have to use NanoVG OpenGL 2 for Mac users as OpenGL 3 is not supported.
// NanoVG uses shader version 150 in OpenGL 3 context which cannot compile under Mac.
import static org.lwjgl.nanovg.NanoVGGL2.*;

import org.lwjgl.*;
import org.lwjgl.glfw.GLFW;

import turd.game.audio.Audio;
import turd.game.graphics.Graphics;
import turd.game.graphics.HUD;
import turd.game.graphics.Texture;
import turd.game.input.KeyboardInput;
import turd.game.objects.ObjectList;
import turd.game.platform.FallingPlat;
import turd.game.platform.LongPlatform;
import turd.game.platform.MediumPlatform;
import turd.game.platform.Pit;
import turd.game.platform.SmallSquare;
import turd.game.platform.Spikes;

public class Main {

	private final int WINDOW_WIDTH = 1280;
	private final int WINDOW_HEIGHT = 720;

	private Window window;
	private Graphics graphics;

	private HUD hud;
	
	private Texture texBackground;
	
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
	
	private void renderHUD() {
		graphics.beginFrame();
		
		// Draw FPS/Render time stats.
		graphics.setColor(255.f, 255.f, 255.f, 255.f);
		graphics.drawString(String.format("Trash Unit Response Droid (T.U.R.D)\nFPS: %d\nTPS: %d",
				window.getFps(), window.getTicks()), 2, 2, 1.f);
		
		// ----------------------- PAUSE MENU HUD
		if (GameState.getInstance().isPaused()) {
			
			// Draw a darker background that is on top of everything else.
			graphics.setColor(0.f, 0.f, 0.f, 127.f);
			graphics.drawFilledRect(0, 0, window.getWidth(), window.getHeight());

			// Draw text indicating the game is paused.
			graphics.setColor(255.f, 255.f, 255.f, 255.f);
			graphics.drawString("GAME PAUSED", 2, 2, 8.f);
		}
		// ----------------------- PAUSE MENU HUD

		hud.render(window, graphics);
		graphics.endFrame();
	}
	
	public void render() {
		// Render the world background.
		// The world background will not move with the camera, it will remain static.
		graphics.beginFrame();
		texBackground.render(0, 0, window.getWidth(), window.getHeight(), 255.f);
		graphics.endFrame();
		
		GameState.getInstance().update(window);
		
		// We need to separate things into multiple frames so that translations don't mess stuff up.
		// The first frame will be our "hud".
		
		// Render all objects.
		ObjectList.getInstance().render(window, graphics);
		
		// Render HUD (this will overlap the world)
		renderHUD();
	}
	
	public void tick() {
		// If the game is paused then don't process any ticks.
		if (GameState.getInstance().isPaused()) {
			return;
		}
		
		// Animate the background texture and move it right every rendered frame to simulate it moving.
		// Failed attempt to animate the background image :(
		//texBackground.animate(window.getWidth(), window.getHeight());

		// Zoom the camera in/out.
		float flCameraFOV = GameState.getInstance().getCameraFOV();
		
		if ( KeyboardInput.getInstance().isKeyClicked(GLFW.GLFW_KEY_DOWN) ) {
			flCameraFOV += 1.F;
		}
		
		if ( KeyboardInput.getInstance().isKeyClicked(GLFW.GLFW_KEY_UP) ) {
			flCameraFOV -= 1.F;
		}
		
		GameState.getInstance().setCameraFOV(flCameraFOV);
		
		ObjectList.getInstance().tick(window);
	}
	
	public void start() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		// Create our window.
		window = new Window("Trash Unit Response Droid (T.U.R.D)", WINDOW_WIDTH, WINDOW_HEIGHT);

		// Create graphics.
		graphics = new Graphics(window, NVG_ANTIALIAS);
		
		//create hud.
		hud = new HUD(window, graphics);

		// If you would like to disable the camera projection do so here.
		// This may be useful when placing around more objects.
		//GameState.getInstance().setUseCamera(false);
		
		// If you want to move the camera manually you can enter the position here.
		// You can place this function call inside of tick(...) or render(...) methods
		// and feed in the cursor x/y from window.getMouseX/Y methods to make the camera
		// follow the mouse - as an example.
		//GameState.getInstance().setCameraPos(1000.f, 100.f);
		
		// Create our player.
		ObjectList.getInstance().createPlayer();
		ObjectList.getInstance().createEnemy(420.f, 20.f);
		
		Audio.getInstance().play("playerJump");

		// (Length , Height)
		// Window size
		// 720
		// 1280

		new LongPlatform(-80, 144);
		new SmallSquare(-80, 80);
		new SmallSquare(656, 80);
		new Spikes(720, 100);
		new LongPlatform(850, 144);
		new SmallSquare(850, 80);
		new SmallSquare(1820, 144);
		new MediumPlatform(2046, 144);
		new Pit(1650, 240);
		new FallingPlat(2450,144);
		new FallingPlat(2520,144);
		new FallingPlat(2590,144);
		new FallingPlat(2660,144);
		new FallingPlat(2730,144);
		new MediumPlatform(2800, 144);
		
		texBackground = new Texture(Graphics.nvgHandle(), "Background01.png");
		
		window.loop(_render, _tick);

		// Terminate the window and cleanup NanoVG context.
		window.terminate();
		graphics.terminate();
		
		//Terminate all audio devices.
		Audio.getInstance().terminate();

//LEO	HUD.terminate();
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
