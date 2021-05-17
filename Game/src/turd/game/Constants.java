package turd.game;

public class Constants {
	
	public static final int		PLAYER_BOUNDS = 64;
	public static final float	PLAYER_MOVE_SPEED = 4.F;
	public static final float	PLAYER_MOVE_SPEED_MULTIPLIER = 3.F; // ( PLAYER_MOVE_SPEED * x )
	public static final float	PLAYER_JUMP_SPEED_MULTIPLIER = 0.5F; // ( PLAYER_MOVE_SPEED * x )
	public static final int		PLAYER_MAX_SCRAP_VALUE = 7;
	public static final int		PLAYER_MAX_HEALTH = 100;
	
	public static final int 	ENEMY_MAX_HEALTH = 100;
	
	public static final int		PROJECTILE_BASE_DAMAGE = 10;
	public static final int 	MAX_PROJECTILES = 25; // Maximum amount of projectile objects that should be created.
}
