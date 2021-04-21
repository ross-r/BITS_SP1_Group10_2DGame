package turd.game;

import org.lwjgl.glfw.GLFW;

import turd.game.input.KeyboardInput;

public class GameState {
	private static GameState instance = null;

    public static GameState getInstance() {
    	if(instance == null) {
    		instance = new GameState();
    	}
    	
    	return instance;
    }

    private boolean bIsPaused;
    private double flFrameTime;
    
    public GameState() {
    	bIsPaused = false;
    	flFrameTime = 0.f;
    }
    
    public void update(Window window) {
    	this.flFrameTime = window.getFrameTime();
    }
    
    public boolean isPaused() {
    	return this.bIsPaused;
    }
    
    public void setPaused(boolean bIsPaused) {
    	this.bIsPaused = bIsPaused;
    }
    
    public double getFrameTime() {
    	return this.bIsPaused ? 0.f : this.flFrameTime;
    }
}
