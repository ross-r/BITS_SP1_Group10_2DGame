package turd.game.entities;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import turd.game.Constants;
import turd.game.GameState;
import turd.game.MathUtils;
import turd.game.Window;
import turd.game.audio.Audio;
//import turd.game.audio.Audio;
import turd.game.graphics.Graphics;
import turd.game.graphics.Texture;
import turd.game.graphics.TextureManager;
import turd.game.input.KeyboardInput;
import turd.game.input.MouseInput;
import turd.game.objects.GameObject;
import turd.game.objects.ObjectList;
import turd.game.physics.Physics;
import turd.game.platform.HazardSpikes;

public class Player extends GameObject {
	
	private Physics physics;
	
	//
	// Movement related properties
	//
	private float flSideMove;
	private float flUpMove;

	private boolean bInMoveLeft;
	private boolean bInMoveRight;
	private boolean bInMoveSpeed;
	private boolean bInJump;
	private boolean bOnGround;

	private float flMoveSpeed;
	private float flMoveSpeedBonusMultiplier;
	private float flJumpTime;

	private int iFallTicks;
	
	//
	// Animations
	//
	private int iAnimateTicks; //leo - used for temporarily animating wheels
	private int iAnimateTickTimer;
	private int iDamageTakenStart;
	private int iDamageTakenFadeOutStart;
	private int iDamageTakenFadeEnd;
	private float flDamageOverlayAlpha;
	private boolean jumping = false;
	private final int iEmptyScrapValue = 2;
	private final int iHalfScrapValue = 5;
	private int iLastInput;
	
	//
	// Ammo
	//
	private int iScrapValue; // used for counting projectiles

	//
	// Textures
	//
	Texture texture;
	// player moving left/right
	Texture texLeftE1, texLeftE2, texRightE1, texRightE2, texLeftH1, texLeftH2, texRightH1, texRightH2, texLeftF1,
			texLeftF2, texRightF1, texRightF2;
	// player jumping left/right pre-jump
	Texture texLeftE1J1, texLeftE2J1, texRightE1J1, texRightE2J1, texLeftH1J1, texLeftH2J1, texRightH1J1, texRightH2J1,
			texLeftF1J1, texLeftF2J1, texRightF1J1, texRightF2J1;
	// player jumping left/right mid-jump
	Texture texLeftE1J2, texLeftE2J2, texRightE1J2, texRightE2J2, texLeftH1J2, texLeftH2J2, texRightH1J2, texRightH2J2,
			texLeftF1J2, texLeftF2J2, texRightF1J2, texRightF2J2;
	// player jumping left/right post-jump
	Texture texLeftE1J3, texLeftE2J3, texRightE1J3, texRightE2J3, texLeftH1J3, texLeftH2J3, texRightH1J3, texRightH2J3,
			texLeftF1J3, texLeftF2J3, texRightF1J3, texRightF2J3;
	// player attack left/right mid-attack
	Texture texLeftE1A1, texLeftE2A1, texRightE1A1, texRightE2A1, texLeftH1A1, texLeftH2A1, texRightH1A1, texRightH2A1,
			texLeftF1A1, texLeftF2A1, texRightF1A1, texRightF2A1;
	// player attack left/right fin-attack
	Texture texLeftE1A2, texLeftE2A2, texRightE1A2, texRightE2A2, texLeftH1A2, texLeftH2A2, texRightH1A2, texRightH2A2,
			texLeftF1A2, texLeftF2A2, texRightF1A2, texRightF2A2;

	//
	// Projectiles
	//
	private TestProjectile testProjectiles[];
	private int iProjectileCooldown;

	//
	// Health, damage stuff, etc...
	//
	private int iHealth;

	//
	// Upgrades
	//
	private boolean PlayerSpeedUp;
	private boolean PlayerJumpBoost;
	
	public Player() {
		super();

		// The player should have max health by default.
		this.iHealth = Constants.PLAYER_MAX_HEALTH;
		
		// Initialize physics for this entity.
		this.physics = new Physics(this);

		this.flSideMove = 0.f;
		this.flUpMove = 0.f;

		this.bInMoveLeft = false;
		this.bInMoveRight = false;

		this.flMoveSpeed = 1.f;
		this.flMoveSpeedBonusMultiplier = 1.f;
		this.flJumpTime = 0.f;

		// Align the players bounding box so that x + w / 2 will be the center position of the aabb.
		// (applies to y + h / 2 too)
		//
		// this is used so that the mouse position can be centered relative to the players centered aabb.
		this.aabb.init(-( Constants.PLAYER_BOUNDS / 2 ), -( Constants.PLAYER_BOUNDS / 2 ), Constants.PLAYER_BOUNDS, Constants.PLAYER_BOUNDS);

		// Create textures.
		// Create textures.
		// player walking left/right
		texLeftE1 = TextureManager.get("Player-Stand-LeftEmpty.png");
		texLeftE2 = TextureManager.get("Player-Stand-LeftEmpty-ALT.png");
		texRightE1 = TextureManager.get("Player-Stand-RightEmpty.png");
		texRightE2 = TextureManager.get("Player-Stand-RightEmpty-ALT.png");
		texLeftH1 = TextureManager.get("Player-Stand-LeftHalf.png");
		texLeftH2 = TextureManager.get("Player-Stand-LeftHalf-ALT.png");
		texRightH1 = TextureManager.get("Player-Stand-RightHalf.png");
		texRightH2 = TextureManager.get("Player-Stand-RightHalf-ALT.png");
		texLeftF1 = TextureManager.get("Player-Stand-LeftFull.png");
		texLeftF2 = TextureManager.get("Player-Stand-LeftFull-ALT.png");
		texRightF1 = TextureManager.get("Player-Stand-RightFull.png");
		texRightF2 = TextureManager.get("Player-Stand-RightFull-ALT.png");
		// player jumping left/right pre-jump
		texLeftE1J1 = TextureManager.get("Player-PreJump-LeftEmpty.png");
		texLeftE2J1 = TextureManager.get("Player-PreJump-LeftEmpty-ALT.png");
		texRightE1J1 = TextureManager.get("Player-PreJump-RightEmpty.png");
		texRightE2J1 = TextureManager.get("Player-PreJump-RightEmpty-ALT.png");
		texLeftH1J1 = TextureManager.get("Player-PreJump-LeftHalf.png");
		texLeftH2J1 = TextureManager.get("Player-PreJump-LeftHalf-ALT.png");
		texRightH1J1 = TextureManager.get("Player-PreJump-RightHalf.png");
		texRightH2J1 = TextureManager.get("Player-PreJump-RightHalf-ALT.png");
		texLeftF1J1 = TextureManager.get("Player-PreJump-LeftFull.png");
		texLeftF2J1 = TextureManager.get("Player-PreJump-LeftFull-ALT.png");
		texRightF1J1 = TextureManager.get("Player-PreJump-RightFull.png");
		texRightF2J1 = TextureManager.get("Player-PreJump-RightFull-ALT.png");
		// player jumping left/right mid-jump
		texLeftE1J2 = TextureManager.get("Player-MidJump-LeftEmpty.png");
		texLeftE2J2 = TextureManager.get("Player-MidJump-LeftEmpty-ALT.png");
		texRightE1J2 = TextureManager.get("Player-MidJump-RightEmpty.png");
		texRightE2J2 = TextureManager.get("Player-MidJump-RightEmpty-ALT.png");
		texLeftH1J2 = TextureManager.get("Player-MidJump-LeftHalf.png");
		texLeftH2J2 = TextureManager.get("Player-MidJump-LeftHalf-ALT.png");
		texRightH1J2 = TextureManager.get("Player-MidJump-RightHalf.png");
		texRightH2J2 = TextureManager.get("Player-MidJump-RightHalf-ALT.png");
		texLeftF1J2 = TextureManager.get("Player-MidJump-LeftFull.png");
		texLeftF2J2 = TextureManager.get("Player-MidJump-LeftFull-ALT.png");
		texRightF1J2 = TextureManager.get("Player-MidJump-RightFull.png");
		texRightF2J2 = TextureManager.get("Player-MidJump-RightFull-ALT.png");
		// player jumping left/right post-jump
		texLeftE1J3 = TextureManager.get("Player-PostJump-LeftEmpty.png");
		texLeftE2J3 = TextureManager.get("Player-PostJump-LeftEmpty-ALT.png");
		texRightE1J3 = TextureManager.get("Player-PostJump-RightEmpty.png");
		texRightE2J3 = TextureManager.get("Player-PostJump-RightEmpty-ALT.png");
		texLeftH1J3 = TextureManager.get("Player-PostJump-LeftHalf.png");
		texLeftH2J3 = TextureManager.get("Player-PostJump-LeftHalf-ALT.png");
		texRightH1J3 = TextureManager.get("Player-PostJump-RightHalf.png");
		texRightH2J3 = TextureManager.get("Player-PostJump-RightHalf-ALT.png");
		texLeftF1J3 = TextureManager.get("Player-PostJump-LeftFull.png");
		texLeftF2J3 = TextureManager.get("Player-PostJump-LeftFull-ALT.png");
		texRightF1J3 = TextureManager.get("Player-PostJump-RightFull.png");
		texRightF2J3 = TextureManager.get("Player-PostJump-RightFull-ALT.png");
		// player attack left/right mid-attack
		texLeftE1A1 = TextureManager.get("Player-MidAttack-LeftEmpty.png");
		texLeftE2A1 = TextureManager.get("Player-MidAttack-LeftEmpty-ALT.png");
		texRightE1A1 = TextureManager.get("Player-MidAttack-RightEmpty.png");
		texRightE2A1 = TextureManager.get("Player-MidAttack-RightEmpty-ALT.png");
		texLeftH1A1 = TextureManager.get("Player-MidAttack-LeftHalf.png");
		texLeftH2A1 = TextureManager.get("Player-MidAttack-LeftHalf-ALT.png");
		texRightH1A1 = TextureManager.get("Player-MidAttack-RightHalf.png");
		texRightH2A1 = TextureManager.get("Player-MidAttack-RightHalf-ALT.png");
		texLeftF1A1 = TextureManager.get("Player-MidAttack-LeftFull.png");
		texLeftF2A1 = TextureManager.get("Player-MidAttack-LeftFull-ALT.png");
		texRightF1A1 = TextureManager.get("Player-MidAttack-RightFull.png");
		texRightF2A1 = TextureManager.get("Player-MidAttack-RightFull-ALT.png");
		// player attack left/right fin-attack
		texLeftE1A2 = TextureManager.get("Player-FinAttack-LeftEmpty.png");
		texLeftE2A2 = TextureManager.get("Player-FinAttack-LeftEmpty-ALT.png");
		texRightE1A2 = TextureManager.get("Player-FinAttack-RightEmpty.png");
		texRightE2A2 = TextureManager.get("Player-FinAttack-RightEmpty-ALT.png");
		texLeftH1A2 = TextureManager.get("Player-FinAttack-LeftHalf.png");
		texLeftH2A2 = TextureManager.get("Player-FinAttack-LeftHalf-ALT.png");
		texRightH1A2 = TextureManager.get("Player-FinAttack-RightHalf.png");
		texRightH2A2 = TextureManager.get("Player-FinAttack-RightHalf-ALT.png");
		texLeftF1A2 = TextureManager.get("Player-FinAttack-LeftFull.png");
		texLeftF2A2 = TextureManager.get("Player-FinAttack-LeftFull-ALT.png");
		texRightF1A2 = TextureManager.get("Player-FinAttack-RightFull.png");
		texRightF2A2 = TextureManager.get("Player-FinAttack-RightFull-ALT.png");
		//texture used for player graphic at all times
		//this texture references the above textures when it needs to
		texture = this.texRightF1;
		// health/ammo value (graphics)
		iScrapValue = 7;
		// saves last left/right keyboard input (graphics)
		iLastInput = 1;
	
		//temporary health/ammo value
		iScrapValue = 7;
		
		this.iProjectileCooldown = 0;
	}
	
	// Called from ObjectList method 'createPlayer'
	// use this to register additional game objects, prevents exceptions being thrown.
	public void initialize() {
		this.testProjectiles = new TestProjectile[ Constants.MAX_PROJECTILES ];
		
		for( int i = 0; i < Constants.MAX_PROJECTILES; i++ ) {
			this.testProjectiles[ i ] = (TestProjectile) ObjectList.getInstance().createEntityObject(new TestProjectile());
		}
	}

	private void input() {
		// Update movement keys.
		bInMoveLeft = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_A);
		bInMoveRight = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_D);

		//bInMoveUp = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_W);
		//bInMoveDown = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_S);

		bInMoveSpeed = KeyboardInput.getInstance().isKeyDown(GLFW.GLFW_KEY_J);
		bInJump = KeyboardInput.getInstance().isKeyClicked(GLFW.GLFW_KEY_SPACE);

		// Update movement directions.
		flSideMove = bInMoveLeft ? -1.f : bInMoveRight ? 1.f : 0.f;
		flUpMove = 0.f;//bInMoveUp ? -1.f : bInMoveDown ? 1.f : 0.f;

		if ( this.bOnGround && bInJump && flJumpTime <= 0.f ) {
			jump( 1.f );
		}
	}

	private void drawTrail(Texture texture) {
		final int x = (int)aabb.p0.x;
		final int y = (int)aabb.p0.y;
		final int w = (int)aabb.p1.x;
		final int h = (int)aabb.p1.y;
		
		if( this.bInMoveSpeed || this.flMoveSpeedBonusMultiplier > 2.F ) {
			final int trail = 5;

			// trail - 1 because the last trail is 0 opacity.
			for( int i = 0; i < trail - 1; i++ ) {
				int newX = x;
				int newY = y;

				float flUpMove = Math.max(this.flUpMove, -1.F);
				if( !this.bOnGround && this.flUpMove == 0.f ) {

					// Assume that gravity is pushing the player down.
					flUpMove = 1.f;
				}

				int offsetX = ( ( i + 1 ) * ( w / 4 ) ) * ( int )this.flSideMove;
				int offsetY = ( ( i + 1 ) * ( h / 4 ) ) * ( int )flUpMove;

				newX += offsetX * -1;
				newY += offsetY * -1;

				float alpha = 255.f - (( i + 1 ) * (255.f/trail));

				texture.render(newX, newY, w, h, alpha);
			}
		}
	}
	
	@Override
	public void render(Window window, Graphics g) {
		final int x = (int)aabb.p0.x;
		final int y = (int)aabb.p0.y;
		final int w = (int)aabb.p1.x;
		final int h = (int)aabb.p1.y;

		// sets the last input so when the player stops their left/right
		// keyboard input, the animations still update correctly
		if (bInMoveLeft) {
			iLastInput = 0;
		} else if (bInMoveRight) {
			iLastInput = 1;
		}
		
		//render the player graphic
				if (iLastInput == 0) {								// LEFT
					if (iEmptyScrapValue >= iScrapValue) { 			// EMPTY
						// on ground
						if (bOnGround && !bInJump) {
							if (jumping == true) {
								this.texture = this.texLeftE1J3;	// landing (jump)
								jumping = false;
							}
							// walk animation
							if (bInMoveLeft) {
								if (iAnimateTicks == 0) {
									this.texture = this.texLeftE1;	// walk 1
								} else if (iAnimateTicks == 1) {
									this.texture = this.texLeftE2;	// walk 2
								}
							}
						}
						// start-jump
						if (bOnGround && bInJump) {
							this.texture = this.texLeftE1J1; 		// pre-jump (jump)
						}
						// mid-jump
						if (flJumpTime > 0.f) {
							this.texture = this.texLeftE1J2;		// mid-jump (jump)
							// jump texture
							jumping = true;
						}
						// falling
						if ((flJumpTime <= 0.f) && !bOnGround) {
							this.texture = this.texLeftE1;			// falling (walk)
							// un-iterated walk texture
							jumping = true;
						}
						// attack
						if (MouseInput.getInstance().getMouseClicked()) {
							// mid attack texture
							this.texture = this.texLeftE1A1;		// mid-atk (attack)
							// final attack
							this.texture = this.texLeftE1A2;		// fin-atk (attack)
						}
					}
				}

				if (iLastInput == 1) {								// RIGHT
					if (iEmptyScrapValue >= iScrapValue) {			// EMPTY
						// on ground
						if (bOnGround && !bInJump) {
							if (jumping == true) {
								this.texture = this.texRightE1J3; 	// landing (jump)
								jumping = false;
							}
							// walk animation
							if (bInMoveRight) {
								if (iAnimateTicks == 0) {
									this.texture = this.texRightE1; // walk 1
								} else if (iAnimateTicks == 1) {
									this.texture = this.texRightE2; // walk 2
								}
							}
						}
						// start-jump
						if (bOnGround && bInJump) {
							this.texture = this.texRightE1J1; 		// pre-jump (jump)
						}
						// mid-jump
						if (flJumpTime > 0.f) {
							this.texture = this.texRightE1J2;		// mid-jump (jump)
							// jump texture
							jumping = true;
						}
						// falling
						if ((flJumpTime <= 0.f) && !bOnGround) {
							this.texture = this.texRightE1;			// falling (walk)
							// un-iterating walk texture
							jumping = true;
						}
						// attack
						if (MouseInput.getInstance().getMouseClicked()) {
							// mid attack texture
							this.texture = this.texRightE1A1;		// mid-atk (attack)
							// final attack
							this.texture = this.texRightE1A2;		// fin-atk (attack)
						}
					}
				}
				if (iLastInput == 0) { 														// LEFT
					if (iHalfScrapValue >= iScrapValue && iEmptyScrapValue < iScrapValue) { // HALF
						// on ground
						if (bOnGround && !bInJump) {
							if (jumping == true) {
								this.texture = this.texLeftH1J3; 	// landing (jump)
								jumping = false;
							}
							// walk animation
							if (bInMoveLeft) {
								if (iAnimateTicks == 0) {
									this.texture = this.texLeftH1;	// walk 1
								} else if (iAnimateTicks == 1) {
									this.texture = this.texLeftH2;	// walk 2
								}
							}
						}
						// start-jump
						if (bOnGround && bInJump) {
							this.texture = this.texLeftH1J1; 		// pre-jump (jump)
						}
						// mid-jump
						if (flJumpTime > 0.f) {
							this.texture = this.texLeftH1J2;		// mid-jump (jump)
							// jump texture
							jumping = true;
						}
						// falling
						if ((flJumpTime <= 0.f) && !bOnGround) {
							this.texture = this.texLeftH1;			// falling (walk)
							// un-iterating walk texture
							jumping = true;
						}
						// attack
						if (MouseInput.getInstance().getMouseClicked()) {
							// mid attack texture
							this.texture = this.texLeftH1A1;		// mid-atk (attack)
							// final attack
							this.texture = this.texLeftH1A2;		// fin-atk (attack)
						}
					}
				}

				if (iLastInput == 1) { 														// RIGHT
					if (iHalfScrapValue >= iScrapValue && iEmptyScrapValue < iScrapValue) {	// HALF
						// on ground
						if (bOnGround && !bInJump) {
							if (jumping == true) {
								this.texture = this.texRightH1J3; 	// landing (jump)
								jumping = false;
							}
							// walk animation
							if (bInMoveRight) {
								if (iAnimateTicks == 0) {
									this.texture = this.texRightH1;	// walk 1
								} else if (iAnimateTicks == 1) {
									this.texture = this.texRightH2;	// walk 2
								}
							}
						}
						// start-jump
						if (bOnGround && bInJump) {
							this.texture = this.texRightH1J1; 		// pre-jump (jump)
						}
						// mid-jump
						if (flJumpTime > 0.f) {
							this.texture = this.texRightH1J2;		// mid-jump (jump)
							// jump texture
							jumping = true;
						}
						// falling
						if ((flJumpTime <= 0.f) && !bOnGround) {
							this.texture = this.texRightH1;			// falling (walk)
							// un-iterating walk texture
							jumping = true;
						}
						// attack
						if (MouseInput.getInstance().getMouseClicked()) {
							// mid attack texture
							this.texture = this.texRightH1A1;		// mid-atk (attack)
							// final attack
							this.texture = this.texRightH1A2;		// fin-atk (attack)
						}
					}
				}
				if (iLastInput == 0) { 								// LEFT
					if (iScrapValue >= (Constants.PLAYER_MAX_SCRAP_VALUE - 1)) {		// FULL
						// on ground
						if (bOnGround && !bInJump) {
							if (jumping == true) {
								this.texture = this.texLeftF1J3; 	// landing (jump)
								jumping = false;
							}
							// walk animation
							if (bInMoveLeft) {
								if (iAnimateTicks == 0) {
									this.texture = this.texLeftF1;	// walk 1
								} else if (iAnimateTicks == 1) {
									this.texture = this.texLeftF2;	// walk 2
								}
							}
						}
						// start-jump
						if (bOnGround && bInJump) {
							this.texture = this.texLeftF1J1;		// pre-jump (jump)
						}
						// mid-jump
						if (flJumpTime > 0.f) {
							this.texture = this.texLeftF1J2;		// mid-jump (jump)
							// jump texture
							jumping = true;
						}
						// falling
						if ((flJumpTime <= 0.f) && !bOnGround) {
							this.texture = this.texLeftF1;			// falling (walk)
							// un-iterating walk texture
							jumping = true;
						}
						// attack
						if (MouseInput.getInstance().getMouseClicked()) {
							// mid attack texture
							this.texture = this.texLeftF1A1;		// mid-atk (attack)
							// final attack
							this.texture = this.texLeftF1A2;		// fin-atk (attack)
						}
					}
				}

				if (iLastInput == 1) { 								// RIGHT
					if (iScrapValue >= (Constants.PLAYER_MAX_SCRAP_VALUE - 1)) {		// FULL
						// on ground
						if (bOnGround && !bInJump) {
							if (jumping == true) {
								this.texture = this.texRightF1J3;	// landing (jump)
								jumping = false;
							}
							// walk animation
							if (bInMoveRight) {
								if (iAnimateTicks == 0) {
									this.texture = this.texRightF1;	// walk 1
								} else if (iAnimateTicks == 1) {
									this.texture = this.texRightF2;	// walk 2
								}
							}
						}
						// start-jump
						if (bOnGround && bInJump) {
							this.texture = this.texRightF1J1;		// pre-jump (jump)
						}
						// mid-jump
						if (flJumpTime > 0.f) {
							this.texture = this.texRightF1J2;		// mid-jump (jump)
							// jump texture
							jumping = true;
						}
						// falling
						if ((flJumpTime <= 0.f) && !bOnGround) {
							this.texture = this.texRightF1;			// falling (walk)
							// un-iterating walk texture
							jumping = true;
						}
						// attack
						if (MouseInput.getInstance().getMouseClicked()) {
							// mid attack texture
							this.texture = this.texRightF1A1;		// mid-atk (attack)
							// final attack
							this.texture = this.texRightF1A2;		// fin-atk (attack)
						}
					}
				}
		
//				//TEST
//				if (texture == texRightF1J2) {
//					System.out.print("midjump");
//				}
		
//				if (bInMoveLeft || bInMoveRight)
//					Audio.getInstance().playerMovePlay();
//				else
//					Audio.getInstance().stop("playerMove");
		
		
		//OLD TEXTURES, LEFT HERE TEMPORARILY
//		if (bInMoveLeft) {
//			Audio.getInstance().playerMovePlay();
//			if (this.flJumpTime > 0.f) {
//				texture = texPlayerJumpLeft;
//			} else {
//				if (iAnimateTicks == 1) {
//					texture = texPlayerLeft1;
//				} else if (iAnimateTicks == 0) {
//					texture = texPlayerLeft2;
//				}
//			}
//		} else if (bInMoveRight) {
//			Audio.getInstance().playerMovePlay();
//			if (this.flJumpTime > 0.f) {
//				texture = texPlayerJumpRight;
//			} else {
//				if (iAnimateTicks == 1) {
//					texture = texPlayerRight1;
//				} else if (iAnimateTicks == 0) {
//					texture = texPlayerRight2;
//				}
//			}
//		} else {
//			Audio.getInstance().stop("playerMove");
//			//texture = texPlayerIdle;
//		}
		
		drawTrail(texture);
		
		texture.render(x, y, w, h, 255.f);

		// Draw a full-screen overlay when we take damage.
		final int iCurrentTick = GameState.getInstance().getCurrentTick();
		if( iCurrentTick >= this.iDamageTakenStart && iCurrentTick <= this.iDamageTakenFadeEnd ) {
			
			// Save the current render state.
			g.save();
			
			// Prevent the camera's projection matrix from messing this up.
			g.resetTransform();
						
			// Fade the opacity in until the fade out tick is met.
			if( iCurrentTick < this.iDamageTakenFadeOutStart ) {
				
				final int iDeltaTicks = this.iDamageTakenFadeOutStart - this.iDamageTakenStart;
				
				flDamageOverlayAlpha += ( ( ( float )iDeltaTicks / 60.f ) * ( 255.f / 8.f ) );
				
			}

			// Assume fade-out has started.
			else {
				
				final int iDeltaTicks = this.iDamageTakenFadeEnd - this.iDamageTakenFadeOutStart;
				flDamageOverlayAlpha -= ( ( ( float )iDeltaTicks / 60.f ) * ( 255.f / 8.f ) );
			}


			g.setColor(255.f, 0.f, 0.f, flDamageOverlayAlpha);
			g.drawFilledRect(0, 0, window.getWidth(), window.getHeight());
			
			// Restore the render state that was saved so that we don't purge the transformation matrix for render calls
			// that may happen after our player is drawn.
			g.restore();
		}
	}

	@Override
	public void tick(Window window) {
		iAnimateTickTimer++;
		
		// Decides the frequency at which the wheels animation changes.
		final int iAnimationMod = this.bInMoveSpeed ? 2 : 4;
		if( iAnimateTickTimer % iAnimationMod == 0 ) {
			iAnimateTicks++;
			if( iAnimateTicks == 2 ) {
				iAnimateTicks = 0;
			}
			
			iAnimateTickTimer = 0;
		}
		
		input();
		
		this.bOnGround = !this.physics.gravity();
		
		if( !this.bOnGround ) {
			iFallTicks++;
		}
		else {
			iFallTicks = 0;
		}

		// If we are falling for more than 4 seconds (60 ticks per second)
		// reset our position so we don't stay off-screen.
		if( iFallTicks >= 60*4 ) {
			//this.aabb.p0.x = 0;
			//this.aabb.p0.y = 0;
			iFallTicks = 0;
		}

		this.flMoveSpeed = this.bInMoveSpeed ? ( Constants.PLAYER_MOVE_SPEED * Constants.PLAYER_MOVE_SPEED_MULTIPLIER ) : Constants.PLAYER_MOVE_SPEED;

		// Calculate movement speed penality based on the amount of scrap the player has.
		// The more scrap they have the slower they will move.
		if( this.iScrapValue >= 0 ) {
			float y = Constants.PLAYER_MAX_SCRAP_VALUE - ( Constants.PLAYER_MAX_SCRAP_VALUE - this.iScrapValue );
			float z = 0.8f + ( 2.5f / Math.max( 1.f, y ) );
			
			this.flMoveSpeedBonusMultiplier = Math.max( 1.f , z );
		}
		
		// 'flMoveSpeedBonusMultiplier' is the variable we would write to when we want to gain a bonus to movement speed outside of the
		// default movement speed limitations (i.e; move speed, jump speed, jump height) - this value is a multiplier so it scales,
		// a value of 1 will have no change, < 1 will slow us, > 1 will make us faster.
		this.flMoveSpeed *= this.flMoveSpeedBonusMultiplier;

		//this.flJumpSpeed = Constants.PLAYER_JUMP_SPEED_MULTIPLIER;

		if ( this.flJumpTime > 0.f ) {
			this.flJumpTime -= ( 1.f / 60.f );

			// Negative value here since we want to go upwards.
			this.flUpMove = -2.f;

			// Negate regular movement speed and multiply by desired jump speed.
			this.flUpMove /= this.flMoveSpeed;
			this.flUpMove *= ( ( this.flMoveSpeed / this.flMoveSpeedBonusMultiplier ) * Constants.PLAYER_JUMP_SPEED_MULTIPLIER );
		}

		// This is really bad lol
		// The reason these calls are separated is because both x and y are checked in collision and if ONE of them fails
		// both are ignored for the current tick, if they are separate calls then this *issue* is *avoided*.
		this.physics.move( this.aabb.p0.x + ( this.flSideMove * this.flMoveSpeed ), this.aabb.p0.y );
		boolean bCollidedY = this.physics.move( this.aabb.p0.x, this.aabb.p0.y + + ( this.flUpMove * this.flMoveSpeed ) );
		if( bCollidedY && MathUtils.isObjectAbovePlayer(this, this.physics.getCollidedObject()) ) {
			this.flJumpTime = 0.f;
		}
		
		//
		//
		//
		
		this.iProjectileCooldown--;
		if( MouseInput.getInstance().getMouseClicked() ) {
//			if (!Audio.getInstance().getPlaying("playerShoot"))
//			Audio.getInstance().play("playerShoot");
			this.shoot(window);
		}
	}
	
	private void shoot(Window window) {

		// Do we have ammo to use?
		if( this.iScrapValue == 0 ) {
			return;
		}
		
		if( this.iProjectileCooldown > 0 ) {
			return;
		}
		
		// Figure out which projectile we should fire.
		int iProjectileIndex = -1;
		for( int i = 0; i < Constants.MAX_PROJECTILES; i++ ) {
			if( this.testProjectiles[ i ].isInitialized() ) {
				continue;
			}
			
			iProjectileIndex = i;
			break;
		}
		
		// No projectiles found to fire.
		if( iProjectileIndex == -1 ) {
			return;
		}
	
		TestProjectile testProjectile = this.testProjectiles[ iProjectileIndex ];
		
		Vector2f direction = MathUtils.calcDirFromGameObjectToMouse(window, this);
		
		// Compute center coordinates of our aabb.
		final float flCenterX = this.aabb.p0.x + ( this.aabb.p1.x / 2 );
		final float flCenterY = this.aabb.p0.y + ( this.aabb.p1.y / 2 );
		
		// Compute the position and move it out of the players bounding box slightly.
		// This prevents the projectile getting stuck on the entity shooting it.
		Vector2f position = new Vector2f( 
			flCenterX + ( direction.x * Constants.PLAYER_BOUNDS ), 
			flCenterY + ( direction.y * Constants.PLAYER_BOUNDS ) 
		);
		
		Vector2f velocity = new Vector2f( 10.f, 10.f );
		
		// Attempt to initialize the projectile.
		if( !testProjectile.initialize( position, direction, velocity ) ) {
			
			// If this fails it means the projectile would spawn in an invalid position.
			return;
		}
		
		// TODO: It would be a good idea to add a delay between projectile shots
		// this delay could be affected by some of our EVF's (such as speed alter, etc..)
	
		// Since we have shot a projectile, consume ammo.
		this.iScrapValue--;
		
		this.iProjectileCooldown = MathUtils.convertMillisecondsToGameTicks( 1000 );
	}
	
	private void jump( float flJumpStrength ) {
		// How many ticks we should jump for.
		// We multiply by a fixed value here to keep the jump height consistent even when we are moving faster.
		final float flJumpTicks = 48.f * ( bInMoveSpeed ? 0.5f : 1.f );
		this.flJumpTime = ( ( 1.f / 60.f ) * Math.round( flJumpTicks ) ) * flJumpStrength;
		
		if (!Audio.getInstance().getPlaying("playerJump")) {
			Audio.getInstance().play("playerJump");
		}
	}
	
	private void dropScrap() {
		if( this.iScrapValue == 0 ) {
			return;
		}
		
		--this.iScrapValue;

		Vector2f direction = new Vector2f( 
			MathUtils.randomInRange( -1.f, 1.f ),
			-1.f
		);
		
		// Compute center coordinates of our aabb.
		final float flCenterX = this.aabb.p0.x + ( this.aabb.p1.x / 2 );
		final float flCenterY = this.aabb.p0.y + ( this.aabb.p1.y / 2 );
		
		// Compute the position and move it out of the players bounding box slightly.
		// This prevents the projectile getting stuck on the entity shooting it.
		Vector2f position = new Vector2f( 
			flCenterX + ( direction.x * Constants.PLAYER_BOUNDS ), 
			flCenterY + ( direction.y * Constants.PLAYER_BOUNDS ) 
		);
		
		Vector2f velocity = new Vector2f( 2.f, 0.f );
		
		ObjectList.getInstance().registerQueuedObject( new Scrap( position, direction, velocity ) );
	}
	
	private void onTakeDamage( GameObject object ) {		
		final boolean bDamagedBySpikes = object instanceof HazardSpikes;
		
		// If we're inside of the bit we want to take a small amount of damage and initiate a jump.
		if( bDamagedBySpikes ) {
			jump( 0.5f );
		}
		
		// We can apply a multiplier for things like damage resistance and so on.
		//this.iHealth -= Constants.PROJECTILE_BASE_DAMAGE;
		
		// Reset damage overlay alpha.
		this.flDamageOverlayAlpha = 0.f;
		
		// Since we have taken some damage, I want to have an overlay to indicate it
		// this will be rather crappy, but it'll be a red full-screen overlay that fades out.
		this.iDamageTakenStart = GameState.getInstance().getCurrentTick();
		
		// Begin to fade out the overlay 500ms after initial damage taken.
		this.iDamageTakenFadeOutStart = this.iDamageTakenStart + MathUtils.convertMillisecondsToGameTicks(500);
		
		// And finally, end the overlay 250ms after the fade begins.
		this.iDamageTakenFadeEnd = this.iDamageTakenFadeOutStart + MathUtils.convertMillisecondsToGameTicks(500);
		
		// If we took damage drop a piece of scrap in a random direction.
		if( !bDamagedBySpikes ) {
			dropScrap();
		}
		
		// Keep health in increments of 10s.
		this.iHealth = this.iScrapValue * 10;
		if( this.iHealth <= 0 ) {
			
			// We can allow for a 'lives' system so we can let the player restart the level and try again.
			GameState.getInstance().setPlayerDead();
		
		}
	}
	
	@Override
	public void onCollision( GameObject object ) {
		
		// Only these objects can damage our player.
		if( !( object instanceof TestProjectile || object instanceof HazardSpikes ) ) {
			return;
		}
		
		onTakeDamage( object );
	}
	
	//players health/ammunition value (scrap)
	public int getScrapValue() {
		return iScrapValue;
	}
	
	public boolean collectScrap() {
		if( this.iScrapValue == Constants.PLAYER_MAX_SCRAP_VALUE ) {
			// Can't collect scrap as we have max already.
			return false;
		}
		
		++this.iScrapValue;
		
//		Audio.getInstance().play("playerPickUp");
		
		return true;
		
	}
	
	public void setScrapValue(int iScrapValue) {
		if( iScrapValue > Constants.PLAYER_MAX_SCRAP_VALUE ) {
			this.iScrapValue = Constants.PLAYER_MAX_SCRAP_VALUE;
			return;
		}
		
		this.iScrapValue = iScrapValue;
	}
	
	public void incrementScrapValue() {
		if( this.iScrapValue == Constants.PLAYER_MAX_SCRAP_VALUE ) {
			return;
		}
		
		++this.iScrapValue;
	}
	
	public boolean hasMaxScrap() {
		return this.iScrapValue == Constants.PLAYER_MAX_SCRAP_VALUE;
	}

	//for HUD
	public int getTotalUpgrades() {
		return 1;
	}
	public boolean getPlayerSpeedUp() {
		return true;
	}
	
	
}
