package turd.game.entities;

import org.lwjgl.glfw.GLFW;

import turd.game.GameState;
import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.input.KeyboardInput;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.objects.StaticObject;
import turd.game.physics.Physics;

public class Player extends GameObject {
	private final double DIRECTION_DOWN = 270.f;
	private final double DIRECTION_UP = 90.f;

	private final int PLAYER_BOUNDS = 64;
	
	Physics physics;
	
	// Movement related properties.
	private float flSideMove;
	private float flUpMove;
	
	private boolean bInMoveLeft;
	private boolean bInMoveRight;
	private boolean bInMoveUp;
	private boolean bInMoveDown;
	
	private double flDirection;
	private double flRotation;
	
	private int x;
	private int y;
	
	public Player() {
		super();
		
		this.flSideMove = 0.f;
		this.flUpMove = 0.f;
		
		this.bInMoveLeft = false;
		this.bInMoveRight = false;
		this.bInMoveUp = false;
		this.bInMoveDown = false;
		
		this.flDirection = 0.0;
		this.flRotation = 0.0;
		
		this.aabb.init(0.f, 0.f, PLAYER_BOUNDS, PLAYER_BOUNDS);
	}

	private void input() {
		// Update movement keys.
		bInMoveLeft = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_A);
		bInMoveRight = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_D);
		bInMoveUp = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_W);
		bInMoveDown = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_S);
		
		// Update movement directions.
		flSideMove = bInMoveLeft ? -1.f : bInMoveRight ? 1.f : 0.f;
		flUpMove = bInMoveUp ? -1.f : bInMoveDown ? 1.f : 0.f;
	}

	@Override
	public void render(Window window, Graphics g) {		
		g.setColor(255.f, 255.f, 255.f, 127.f);
		g.drawFilledRect(x, y, PLAYER_BOUNDS, PLAYER_BOUNDS);
		//g.createPlayer();
	}
	
	@Override
	public void tick(Window w) {
		input();
		
		//
		
		// Update aabb (x,y is player position).
		this.aabb.init(x, y, PLAYER_BOUNDS, PLAYER_BOUNDS);
	}
	
}
