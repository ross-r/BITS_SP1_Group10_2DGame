package turd.game.objects;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import turd.game.Camera;
import turd.game.Window;
import turd.game.entities.Enemy;
import turd.game.entities.Player;
import turd.game.graphics.Graphics;
import turd.game.physics.Vec2;
import turd.game.entities.Scrap;

public class ObjectList {
	private static ObjectList instance = null;
	
	// All entities will be registered to this linked list.
	// Entities are described as interactable objects, such as Players, AI, Loot, etc...
	private LinkedList<GameObject> entities = new LinkedList<GameObject>();
	
	// All objects will be registered to this linked list.
	// Objects are described as static, non-interactable objects, such as collision boxes
	// world objects, etc...
	private LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	private Player player;
	private Camera camera;
	
	private ObjectList() {
		this.player = null;
		this.camera = null;
	}
	
	public static ObjectList getInstance() {
		if(instance == null) {
			instance = new ObjectList();
		}
		
		return instance;
	}
	
	//public StaticObject createStaticObject() {
	//	this.objects.add(new StaticObject());
	//	return (StaticObject) this.objects.getLast();
	//}
	
	public void registerStaticObject(StaticObject object) {
		this.objects.add(object);
	}
	
	public Player createPlayer() {
		if( this.player != null ) {
			throw new RuntimeException("A player entity has already been created. Only 1 player is allowed.");
		}
		
		this.entities.add(new Player());
		
		this.player = ( Player )this.entities.getLast();
		
		// Initialize the camrea object once a player is created.
		// Assume that only one player will ever be created.
		if ( this.camera == null ) {
			this.camera = new Camera( this.player );
		}
		
		this.player.initialize();
		return this.player;
	}
	
	public GameObject createEntityObject(GameObject object) {
		this.entities.add(object);
		return this.entities.getLast();
	}
	
	public Enemy createEnemy(float x, float y) {
		this.entities.add(new Enemy(new Vec2(x, y)));
		Enemy enemy = (Enemy) this.entities.getLast();
		enemy.initialize();
		return enemy;
	}
	
	public Scrap createScrap(int iX, int iY){
		
		if(this.entities.add(new Scrap(iX, iY))) {
			System.out.println("Created right");
			System.out.println(this.entities.size());
			System.out.println(this.entities);
		}
		
		return (Scrap) this.entities.getLast();
	}
	
	public void render(Window window, Graphics g) {
		g.beginFrame();
		
		// Project the camera matrix into NanoVG's graphic states.
		camera.project(window, g);
		
		// Render regular objects first as these are usually world objects and we want our entities to be on top.
		for(GameObject object : objects) {	
			object.render(window, g);
			
			// Draw the AABB of all objects.
			g.setColor(0.F, 255.F, 255.F, 127.F);
			g.drawRect((int)object.aabb.p0.x, (int)object.aabb.p0.y, (int)object.aabb.p1.x, (int)object.aabb.p1.y);
		}

		// Now render our entities on top of the non-interactable objects.
		for(GameObject entity : entities) {
			entity.render(window, g);
		}

		g.endFrame();
	}
	
	public void tick(Window window) {
		for(GameObject object : objects) {
			object.tick(window);
		}
		
		for(GameObject entity : entities) {
			entity.tick(window);
		}
	}

	public List<GameObject> getEntities() {
		// https://stackoverflow.com/a/33570034
		return entities.stream().map(n -> (GameObject)n).collect(Collectors.toList());
	}
	
	public List<StaticObject> getStaticObjects() {
		// https://stackoverflow.com/a/33570034
		return objects.stream().map(n -> (StaticObject)n).collect(Collectors.toList());
	}
	
	public Player getPlayer() {
		if( this.player == null ) {
			throw new RuntimeException("ObjectList method 'getPlayer' called before a player has been created.");
		}
		
		return this.player;
	}
}
