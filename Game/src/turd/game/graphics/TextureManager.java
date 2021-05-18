package turd.game.graphics;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {

	private static TextureManager instance = null;
	
	public static TextureManager getInstance() {
		if( instance == null ) {
			instance = new TextureManager();
		}
		
		return instance;
	}
	
	private Map< String, Texture > textureMap;
	
	public TextureManager() {
		this.textureMap = new HashMap< String, Texture >();
	}
	
	public boolean hasTexture( String filename ) {
		return this.textureMap.containsKey( filename );
	}
	
	public void setTexture( String filename, Texture texture ) {
		this.textureMap.put(filename, texture);
	}
	
	public void createTexture( String filename ) {
		this.setTexture( filename, new Texture( Graphics.nvgHandle(), filename ) );
	}
	
	public Texture getTexture( String filename ) {
		return this.textureMap.get( filename );
	}
	
	// Use this to create textures from now on.
	public static Texture get( String filename ) {
		if( getInstance().hasTexture( filename ) ) {
			return getInstance().getTexture( filename );
		}
		
		// We need to create the texture.
		getInstance().createTexture( filename );
		
		// This will only output once for each texture.
		//System.out.println("-> created texture " + filename);
		
		// Now we can obtain it.
		return getInstance().getTexture( filename );
	}
}
