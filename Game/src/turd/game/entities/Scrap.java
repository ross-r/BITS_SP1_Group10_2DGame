package turd.game.entities;


import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.objects.GameObject;
import turd.game.physics.Physics;


public class Scrap extends GameObject {

	Physics physics;
	
	private final int SCRAP_BOUNDS = 16;
	
	private int iInAirTimer;
	private boolean bInAir;
	
	private float flScrapSpeed;
	
	public Scrap(int iX, int iY) {
		
		super();
		
		this.physics = new Physics(this);
		
		//this is used to make sure scrap hasnt fallen off screen.
		this.iInAirTimer = 0;
		this.bInAir = false;
		
		flScrapSpeed = 20;
		initialise(iX,iY);
	}
	
	public void initialise(int iStartX, int iStartY) {
		
		this.aabb.init(iStartX, iStartY, SCRAP_BOUNDS, SCRAP_BOUNDS);
		
		 System.out.println("Scrap Created");
	}
	
	@Override
	public void render(Window window, Graphics g) {	
		
		System.out.println("Scrap Rendered");
		g.setColor(255.f, 255.f, 255.f, 255.f);
		g.drawFilledRect((int)aabb.p0.x, (int)aabb.p0.y, (int)aabb.p1.x, (int)aabb.p1.y);
		
	}
	
	@Override
	public void tick(Window w) {
		
		System.out.println("Scrap Tick");
		// 'physics.gravity()' returns false when a collision has happened.
		this.bInAir = this.physics.gravity();
		
		if(this.bInAir) {
			//Check if its in the air and add to a timer incase it falls off screen
			this.physics.move(this.aabb.p0.x, this.aabb.p0.y + flScrapSpeed);
			this.iInAirTimer = this.iInAirTimer + 20;
		}
		
		if(!this.bInAir) {
			//Reset timer once landed
			this.iInAirTimer = 0;
		}
		
		this.physics.gravity();
		
		if(this.iInAirTimer == 200) {
			this.flScrapSpeed = 0;
		}
		
	}
	
	public void setScrapSpeed(int iSpeed) {
		this.flScrapSpeed = iSpeed;
	}

	
}
