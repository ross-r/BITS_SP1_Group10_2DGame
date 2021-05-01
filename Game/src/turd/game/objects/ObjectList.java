package turd.game.objects;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import turd.game.Camera;
import turd.game.Window;
import turd.game.entities.AI;
import turd.game.entities.Player;
import turd.game.graphics.Graphics;

public class ObjectList {
	private static ObjectList instance = null;
	
	// All entities will be registered to this linked list.
	// Entities are described as interactable objects, such as Players, AI, Loot, etc...
	private LinkedList<GameObject> entities = new LinkedList<GameObject>();
	
	// All objects will be registered to this linked list.
	// Objects are described as static, non-interactable objects, such as collision boxes
	// world objects, etc...
	private LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	private Camera camera;
	
	private ObjectList() {
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
		this.entities.add(new Player());
		
		// Initialize the camrea object once a player is created.
		// Assume that only one player will ever be created.
		if ( this.camera == null ) {
			this.camera = new Camera( (Player) this.entities.getLast() );
		}
		
		return (Player) this.entities.getLast();
	
	}
	
	public AI createAI() {
		this.entities.add(new AI());
		return (AI) this.entities.getLast();
	}

	public void render(Window window, Graphics g) {
		g.beginFrame();
		
		// Project the camera matrix into NanoVG's graphic states.
		camera.project(window, g);
		
		// Render regular objects first as these are usually world objects and we want our entities to be on top.
		for(GameObject object : objects) {	
			object.render(window, g);
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

	public List<StaticObject> getStaticObjects() {
		// https://stackoverflow.com/a/33570034
		return objects.stream().map(n -> (StaticObject)n).collect(Collectors.toList());
	}
}
