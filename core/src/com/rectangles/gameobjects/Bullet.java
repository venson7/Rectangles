package com.rectangles.gameobjects;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.AtomicQueue;
import com.rectangles.gameworld.GameWorld;
import com.rectangles.gameworld.GameWorld.GameState;

import static com.badlogic.gdx.math.MathUtils.random;

/** Class that contains the enemy (bullet)
 * @author Kostas Datsos
 */
public class Bullet {
	
	/* Enum defining starting position (side) of bullet */
	enum startPosition {
		top, bottom, left, right
	}
	
	/** Acceleration coefficient */
	public static double acceleration = 1.08;

	/** Game Objects
	 * 	hero refers to the main character (rectangle)
	 * 	world refers to game logic class */
	GameWorld world;
	Hero hero;

	/** Position and velocity of bullet */
	Vector2 position, velocity;
	
	/** Starting position of bullet (side) */
	startPosition start;
	
	/* Size and color and speed of bullet */
	float width, height;
	float red, green, blue, speed;
	
	/** Displacement used to calculate starting position
	 * of the bullet */
	final float Xdisplacement = Gdx.graphics.getWidth()/96 
			,Ydisplacement = Gdx.graphics.getHeight()/54; 

	/** Initial speed of bullet, calculated 
	 * relatively to screen width */
	public static float initSpeed = Gdx.graphics.getWidth()/5.4f;

	/** Rectangle used for collision detection */
	Rectangle rect;
	
	/** Queue used to determine starting point (side)
	 * 	of the bullet */
	private static AtomicQueue<startPosition> positionqueue =
			new AtomicQueue<startPosition>(9){
				{
					put(startPosition.top);
					put(startPosition.bottom);
					put(startPosition.left);
					put(startPosition.right);
				}
	};

	public Bullet(Hero hero, GameWorld world) {
		this.world = world;
		rect = new Rectangle(0, 0, 0, 0);
		speed = initSpeed;
	}

	/** Getting reference of hero */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/** Changes position of the bullet when outside screen.
	 * 	New position is determined by the queue, hero's 
	 * 	position and "random" margined displacement */
	public synchronized void reposition() {
		/** Used for margined random displacement */
		Random random = new Random();

		/** Initial position after displacent */
		int positionX = 0,positionY = 0;
		
		/** Drawing starting position (side) */
		start = positionqueue.poll();
		
		switch (start) {
		case top:
			if (world.getCurrentState() == GameState.RUNNING) {
				/** Determines x position randomly within 
				 * [hero's width - Xdisplacement,hero's width - Xdisplacement] */
				positionX = (int) (hero.getX() - Xdisplacement + random(hero.getWidth() + Xdisplacement));
			} else {
				/** X position of bullet can be the whole width (no hero present) */
				positionX = random.nextInt(Gdx.graphics.getWidth());
			}
			/** Starts from the top of the screen */
			positionY = 0;
			/** Sets velocity vector accordingly */
			velocity = new Vector2(0, speed);
			/** Width is determined according to screen width,
			 * 	Height is determined according to width */
			width = Gdx.graphics.getWidth()/96f;
			height = width*2.5f;
			break;
		case bottom:
			if (world.getCurrentState() == GameState.RUNNING) {
				positionX = (int) (hero.getX() - Xdisplacement + random(hero.getWidth() + Xdisplacement));
			} else {
				positionX = random.nextInt(Gdx.graphics.getWidth());
			}
			positionY = Gdx.graphics.getHeight();
			velocity = new Vector2(0, -speed);
			width = Gdx.graphics.getWidth()/96f;
			height = width*2.5f;
			break;
		case left:
			positionX = 0;
			if (world.getCurrentState() == GameState.RUNNING) {
				positionY = (int) (hero.getY() + Ydisplacement  + random(hero.getHeight() + Ydisplacement));
			} else {
				positionY = random.nextInt(Gdx.graphics.getHeight());
			}
			velocity = new Vector2(speed, 0);
			width = Gdx.graphics.getWidth()/38.4f;
			height = width/2.5f;
			break;
		case right:
			positionX = Gdx.graphics.getWidth();
			if (world.getCurrentState() == GameState.RUNNING) {
				positionY = (int) (hero.getY() + Ydisplacement  + random(hero.getHeight() + Ydisplacement));
			} else {
				positionY = random.nextInt(Gdx.graphics.getHeight());
			}
			velocity = new Vector2(-speed, 0);
			width = Gdx.graphics.getWidth()/38.4f;
			height = width/2.5f;
			break;
		}
		/** Creating random color for the bullet */
		red = random.nextInt(255) + 20;
		green = random.nextInt(255) + 20;
		blue = random.nextInt(255) + 20;
		/** Setting starting position */
		position = new Vector2(positionX, positionY);
		/** Setting rectangle for collision detection */
		rect = new Rectangle(position.x, position.y, width, height);
	}

	/* Updates bullet's position and detects if it reached
	 * out of the bounds of screen */
	public void update(float delta) {
		/** if bullet out of screen */
		if (getX() < -Xdisplacement || getX() > Gdx.graphics.getWidth() 
				|| getY() < -Ydisplacement || getY() > Gdx.graphics.getHeight()) {
			/** return queue token */
			positionqueue.put(start);
			/** move bullet to new position */
			reposition();
		}
		/** Adding velocity to the position of the bullet */
		position.add(velocity.cpy().scl(delta));
		/** Moving rectangle */
		rect.setPosition(position);
	}
	
	/** Populates starting position queue with a set
	 * 	of all the sides of screen */
	public static void populateQueue(){
		positionqueue.put(startPosition.top);
		positionqueue.put(startPosition.bottom);
		positionqueue.put(startPosition.left);
		positionqueue.put(startPosition.right);
	}

	/** Accessors */
	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRed() {
		return red;
	}

	public float getGreen() {
		return green;
	}

	public float getBlue() {
		return blue;
	}

	public Rectangle getRect() {
		return rect;
	}

	public float getVelocityX() {
		return velocity.x;
	}

	public float getVelocityY() {
		return velocity.y;
	}

	public void setVelocityX(float x) {
		this.velocity.x = x;
	}

	public void setVelocityY(float y) {
		this.velocity.y = y;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
