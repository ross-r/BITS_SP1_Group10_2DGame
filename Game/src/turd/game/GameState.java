package turd.game;

public class GameState {
	private static GameState instance = null;

    public static GameState getInstance() {
    	if(instance == null) {
    		instance = new GameState();
    	}
    	
    	return instance;
    }

    private boolean bIsPaused;
    private boolean bUseCamera;
    private boolean bIsPlayerAlive;
    
    // Allows for the camera coordinates to be overriden.
    private boolean bOverrideCamera;
    private float flCameraX;
    private float flCameraY;
    
    // Allows for the camera to be zoomed in/out.
    private float flCameraFOV;
    
    private double flFrameTime;
    
    private int iTotalTicks;
    
    public GameState() {
    	this.bIsPaused = false;
    	this.bUseCamera = true;
    	
    	this.bOverrideCamera = false;
    	this.flCameraX = 0.f;
    	this.flCameraY = 0.f;
    	
    	this.setCameraFOV(90.f);
    	
    	this.flFrameTime = 0.f;
    	this.iTotalTicks = 0;
    	
    	// Assume the player is alive by default.
    	this.bIsPlayerAlive = true;
    }
    
    public void update(Window window) {
    	this.flFrameTime = window.getFrameTime();
    }
    
    public boolean isPaused() {
    	return this.bIsPaused;
    }
    
    public boolean isUsingCamera() {
    	return bUseCamera;
    }
    
    public void setPaused(boolean bIsPaused) {
    	this.bIsPaused = bIsPaused;
    }
    
    public void setUseCamera(boolean bUseCamera) {
    	this.bUseCamera = bUseCamera;
    }
    
    public double getFrameTime() {
    	return this.bIsPaused ? 0.f : this.flFrameTime;
    }

	public boolean isCameraOverridden() {
		return bOverrideCamera;
	}

	public void setCameraOverride(boolean bOverrideCamera) {
		this.bOverrideCamera = bOverrideCamera;
	}
	
	public void setCameraPos(float x, float y) {
		this.bOverrideCamera = true;
		this.flCameraX = x;
		this.flCameraY = y;
	}

	public float getOverridenCameraX() {
		return flCameraX;
	}

	public float getOverridenCameraY() {
		return flCameraY;
	}

	public float getCameraFOV() {
		return flCameraFOV;
	}

	public void setCameraFOV(float flCameraFOV) {
		this.flCameraFOV = flCameraFOV;
	}

	public void incrementTicks() {
		++this.iTotalTicks;
	}
	
	public int getCurrentTick() {
		return this.iTotalTicks;
	}

	public boolean isIsPlayerAlive() {
		return bIsPlayerAlive;
	}

	public void setPlayerDead() {
		this.bIsPlayerAlive = false;
	}
	
	public boolean isGameRunning() {
		return !this.bIsPaused && this.bIsPlayerAlive;
	}
}
