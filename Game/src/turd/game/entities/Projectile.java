package turd.game.entities;


import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.objects.GameObject;
import turd.game.physics.Physics;


public class Projectile extends GameObject{
	
	private int iDestinationX;
	private int iDestinationY;
	
	private int iOriginX;
	private int iOriginY;
	
	private float flProjectileSpeed;
	
	private final int PLAYER_BOUNDS = 16;
	
	Physics physics;

	public Projectile() {
		// TODO Auto-generated constructor stub
		super();
		
		this.physics = new Physics(this);
		
		flProjectileSpeed = 20;
		
		//System.out.println("Projectile Created");
	}

	@Override
	public void render(Window window, Graphics g) {		
		
		g.setColor(255.f, 255.f, 255.f, 255.f);
		g.drawFilledRect((int)aabb.p0.x, (int)aabb.p0.y, (int)aabb.p1.x, (int)aabb.p1.y);
		
	}
	
	@Override
	public void tick(Window w) {
		
		
		float flOldX = this.aabb.p0.x;
		float flOldY = this.aabb.p0.y;
		
		
		//Math for getting angle to move along
		float flxDirection = 0;
		float flyDirection = 0;
		
		flxDirection = (float)(Math.sin(Math.toRadians(getRotation())) * this.flProjectileSpeed);
		flyDirection = (float)(Math.cos(Math.toRadians(getRotation())) * -this.flProjectileSpeed);
		
		this.physics.move(this.aabb.p0.x + flxDirection, this.aabb.p0.y + flyDirection);
		
		
		if(flOldX == this.aabb.p0.x && flOldY == this.aabb.p0.y) {
			this.flProjectileSpeed = 0;
			//System.out.println("Collsion Deleted");
		}
		
		//Deleteing projectile if it moves off screen
		if(this.iDestinationX >= 0) {
			if(this.aabb.p0.x >= w.getWidth()) {
				this.flProjectileSpeed = 0;
				//System.out.println(" Right Horizontal Delte");
			}
		}
		if(this.iDestinationY >= 0) {
			
			if(this.aabb.p0.y >= w.getHeight() ) {
				
				this.flProjectileSpeed = 0;
				//System.out.println("down Vert Delte");
			}
		}
		if(this.iDestinationX <= 0) {
			if(this.aabb.p0.x <= -w.getWidth()) {
				this.flProjectileSpeed = 0;
				//System.out.println(" Left Horizontal Delte");
			}
		}
		if(this.iDestinationY <= 0) {
			if(this.aabb.p0.y <= -w.getHeight()){
				this.flProjectileSpeed = 0;
				//System.out.println(" up Horizontal Delte");
			}
		}
		
		
		
		
	}	
	
	public void initialise(int iStartX, int iStartY, int iDesX, int iDesY) {
		
		float flLength = 0;
		float flXExtended = 0;
		float flYExtended = 0;
		
		
		this.aabb.init(iStartX, iStartY, PLAYER_BOUNDS, PLAYER_BOUNDS);
		
		
		iStartX = this.iOriginX;
		iStartY = this.iOriginY;
		
		System.out.println("X1: " + iStartX + " Y1: " + iStartY + " X2: " + iDesX +  " Y2: " + iDesY);
		this.iDestinationX = iDesX;
		this.iDestinationY = iDesY;
		
		//Trig to extend the line out to keep going
		flLength = (float)Math.sqrt(Math.pow(this.iOriginX - this.iDestinationX, 2) + Math.pow(this.iOriginY - this.iDestinationY, 2));
		flXExtended =(float)this.iDestinationX + ((this.iDestinationX - this.iOriginX))/ flLength * 1000;
		flYExtended = (float)this.iDestinationY + ((this.iDestinationY - this.iOriginY)) / flLength * 1000;
		
		this.iDestinationX = (int)flXExtended;
		this.iDestinationY = (int)flYExtended;
		
		System.out.println("X1: " + this.iOriginX + " Y1: " + this.iOriginY + " X2: " + this.iDestinationX  +  " Y2: " + this.iDestinationY);
		
	}
	
	public float getProjectileSpeed() {
		return this.flProjectileSpeed;
	}
	
	
	
	public float getRotation() {
		float flTempRotation = 0;
		//getting the angle of the triangle
		flTempRotation = (float)Math.toDegrees(Math.atan2(this.iDestinationY - this.aabb.p0.y, this.iDestinationX - this.aabb.p0.x)) + 90;
		
		return flTempRotation;
	}
}
