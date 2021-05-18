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
	
	// A queue of game objects that are to be registered into a specific linked list.
	// The purpose of this is so we can 'add' objects while certain data is being processed
	// Java is particularly picky about having stuff added or removed to a list during iteration
	// (in most other languages this behaviour is fine, however, may cause undefined behaviour if done wrongly)
	private LinkedList<GameObject> queue = new LinkedList<GameObject>();
	
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
	
	public void registerQueuedObject(GameObject object) {
		this.queue.add(object);
	}
	
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
//			g.setColor(0.F, 255.F, 255.F, 127.F);
//			g.drawRect((int)object.aabb.p0.x, (int)object.aabb.p0.y, (int)object.aabb.p1.x, (int)object.aabb.p1.y);
		}

		// Now render our entities on top of the non-interactable objects.
		for(GameObject entity : entities) {
			entity.render(window, g);
		}

		g.endFrame();
	}
	
	public void tick(Window window) {
		//
		// Process any queued game objects and transfer them to the appropriate list.
		//
		// Reference: https://www.vogella.com/tutorials/JavaCollections/article.html
		// Feel free to read if you're interested in learning how this stuff works.
		//
		this.processQueue();
		
		for(GameObject object : objects) {
			object.tick(window);
		}
		
		for(GameObject entity : entities) {
			entity.tick(window);
		}
		
		//
		// Delete any objects which request it.
		//
		this.removeDeadObjects();
	}
	
	private void processQueue() {
		if( !this.queue.isEmpty() ) {
			
			// Firstly filter the queued game objects into two categories
			// 1) StaticObject
			// 2) GameObject
			//
			// StaticObject is considered to be world objects, i.e; platforms, things the player collides with.
			//
			// GameObject is generalized and can safely be assumed that no collision is desired, for this purpose
			// they are considered 'entities', though, they may also provide collisions.
			
			List<GameObject> staticObjects = this.queue.stream().filter(
				n -> ( n instanceof StaticObject )
			).map( n -> ( StaticObject )n ).collect(Collectors.toList());
			
			if( !staticObjects.isEmpty() ) {
				this.objects.addAll( staticObjects );
				
				// Make sure to remove these so that the below GameObject filter does not include any StaticObject classes.
				this.queue.removeAll( staticObjects );
			}
			
			List<GameObject> gameObjects = this.queue.stream().filter(
				n -> ( n instanceof GameObject )
			).map( n -> ( GameObject )n ).collect(Collectors.toList());
			
			if( !gameObjects.isEmpty() ) {
				this.entities.addAll( gameObjects );
				this.queue.removeAll( gameObjects );
			}
			
			// If we are at this point and the queue still has objects in it then something has gone wrong.
			if( !( this.queue.isEmpty() ) ) {
				System.out.println("The queue was not fully emptied into its respective linked lists.");
			}
		}
	}
	
	private void removeDeadObjects() {
		List<GameObject> deadObjects = this.objects.stream().filter(
			n -> ( ( ( GameObject ) n).shouldDelete() )
		).collect(Collectors.toList());
		
		this.objects.removeAll(deadObjects);
		
		List<GameObject> deadEntities = this.entities.stream().filter(
			n -> ( ( ( GameObject ) n).shouldDelete() )
		).collect(Collectors.toList());
			
		this.entities.removeAll(deadEntities);
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
