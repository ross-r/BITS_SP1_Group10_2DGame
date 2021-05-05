package turd.game.entities;


import turd.game.Window;
import turd.game.graphics.Graphics;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.physics.Physics;



public class Projectile extends GameObject{
	
	private int iDestinationX;
	private int iDestinationY;
	
	private int iOriginX;
	private int iOriginY;
	
	private float flProjectileSpeed;
	
	private final int PLAYER_BOUNDS = 16;
	
	private int iTimeonScreen;
	
	Physics physics;

	public Projectile() {
		// TODO Auto-generated constructor stub
		super();
		
		this.physics = new Physics(this);
		
		flProjectileSpeed = 20;
		
		this.iTimeonScreen = 0;
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
		
		//Moving box
		this.physics.move(this.aabb.p0.x + flxDirection, this.aabb.p0.y + flyDirection);
		
		//If it collides delete
		if(flOldX == this.aabb.p0.x && flOldY == this.aabb.p0.y) {
			this.flProjectileSpeed = 0;
			System.out.println("Collsion Deleted");
			
		}
		
		//If it times out or goes off screen delete
		if(this.iTimeonScreen == 2000) {
			this.flProjectileSpeed = 0;
			System.out.println("Projectile Time Deleted");
		}
		//add to time counter
		this.iTimeonScreen = this.iTimeonScreen + (int)this.flProjectileSpeed;
		
		
		
		
	}	
	
	public void initialise(int iStartX, int iStartY, int iDesX, int iDesY) {
		
		float flLength = 0;
		float flXExtended = 0;
		float flYExtended = 0;
		
		//Making the box
		this.aabb.init(iStartX - (PLAYER_BOUNDS / 2), iStartY - (PLAYER_BOUNDS / 2), PLAYER_BOUNDS, PLAYER_BOUNDS);
		
		//Setting origin
		iStartX = this.iOriginX;
		iStartY = this.iOriginY;
		
		//System.out.println("X1: " + iStartX + " Y1: " + iStartY + " X2: " + iDesX +  " Y2: " + iDesY);
		this.iDestinationX = iDesX;
		this.iDestinationY = iDesY;
		
		//System.out.println(getRotation());
		//Trig to extend the line out to keep going
		flLength = (float)Math.sqrt(Math.pow(this.iOriginX - this.iDestinationX, 2) + Math.pow(this.iOriginY - this.iDestinationY, 2));
		
		flLength = flLength * 10.0f;
		
		flXExtended = (float)(flLength * Math.sin(Math.toRadians(getRotation())));
		flYExtended = (float)(Math.cos(Math.toRadians(getRotation())) * -flLength);
		//flXExtended =(float)this.iDestinationX + ((this.iDestinationX - this.iOriginX))/ flLength * 400;
		//flYExtended = (float)this.iDestinationY + ((this.iDestinationY - this.iOriginY)) / flLength * 400;
		//flXExtended = (float)(this.iDestinationX + 500 * Math.sin(getRotation()));
		//flYExtended = (float)(this.iDestinationY + 500 * Math.cos(getRotation()));
		
		//Setting extended line
		this.iDestinationX = (int)flXExtended;
		this.iDestinationY = (int)flYExtended;
		
		//System.out.println("X1: " + this.iOriginX + " Y1: " + this.iOriginY + " X2: " + this.iDestinationX  +  " Y2: " + this.iDestinationY);
		//System.out.println(getRotation());
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
