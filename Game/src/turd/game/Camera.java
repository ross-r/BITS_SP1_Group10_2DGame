package turd.game;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.nanovg.NanoVG;

import turd.game.entities.Player;
import turd.game.graphics.Graphics;

public class Camera {
	private Player player;

	public Camera(Player player) {
		this.player = player;
	}

	private Matrix4f setupViewMatrix(float flCameraX, float flCameraY) {
		return new Matrix4f()
				.perspective((float) Math.toRadians(GameState.getInstance().getCameraFOV()), 1.f, 0.01f, 100.f)
				.ortho(-1.f, 1.f, -1.f, 1.f, 0.01f, 100.f).lookAt(new Vector3f(flCameraX, flCameraY, 1.f), // camera
																											// position
						new Vector3f(flCameraX, flCameraY, 0.f), // target position
						new Vector3f(0.f, 1.f, 0.f) // camera direction
				);
	}

	public void project(Window window, Graphics g) {

		// The camera is completely disabled.
		if (!GameState.getInstance().isUsingCamera() && !GameState.getInstance().isCameraOverridden()) {
			return;
		}

		// Camera object initialised with null player.
		if (this.player == null) {
			return;
		}

		// Link the camera's x and y position to the players.

		// If the camera is overriden we need to draw with the desired camera position.
		if (GameState.getInstance().isCameraOverridden()) {

			// Compute the view matrix for the desired position.
			Matrix4f viewMatrix = setupViewMatrix(GameState.getInstance().getOverridenCameraX(),
					GameState.getInstance().getOverridenCameraY());

			// Give NanoVG the matrix we would like to use for transformations.
			NanoVG.nvgTransform(Graphics.nvgHandle(), viewMatrix.m00(), viewMatrix.m01(), viewMatrix.m10(), viewMatrix.m11(),
					viewMatrix.m30(), viewMatrix.m31());

		} else {

			// Compute the center of the players axis aligned bounding box and use this for
			// the camera position.
			final float flCenterX = this.player.aabb.p0.x + (this.player.aabb.p1.x / 2.f);
			final float flCenterY = this.player.aabb.p0.y + (this.player.aabb.p1.y / 2.f);

			// Make the camera position be centered around the player and in the center of
			// the window.
			final float flCameraX = flCenterX - (window.getWidth() / 2);
			final float flCameraY = flCenterY - (window.getHeight() / 2);
			
			// Compute the view matrix for the desired position.
			Matrix4f viewMatrix = setupViewMatrix(flCameraX, flCameraY);
			
			// Give NanoVG the matrix we would like to use for transformations.
			NanoVG.nvgTransform(Graphics.nvgHandle(), viewMatrix.m00(), viewMatrix.m01(), viewMatrix.m10(), viewMatrix.m11(),
					viewMatrix.m30(), viewMatrix.m31());
		}
	}
}
