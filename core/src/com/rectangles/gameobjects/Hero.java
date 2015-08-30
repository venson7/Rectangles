package com.rectangles.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.rectangles.gameworld.GameWorld;
import com.rectangles.gameworld.GameWorld.GameState;

/** Class that contains the main hero of the game
 * @author Kostas Datsos
 */
public class Hero {

	/** Position and relative center of the Hero */
	private Vector2 position, center;
	
	/** Reference to GameWorld class */
	GameWorld world;
	
	/** Reference to bullets (enemies) */
	private Bullet bullet1, bullet2, bullet3, bullet4;
	
	/** Rectangles that enclose game objects
	 * 	recthero encloses hero,
	 * 	rect1,rect2,rect3,rect4 enclose bullets */
	Rectangle recthero, rect1, rect2, rect3, rect4;
	
	/** Dimensions of hero */
	private float width,height;

	public Hero(GameWorld world,float x, float y, float width, float height) {
		this.world = world;
		/** Getting dimensions of hero */
		this.width = width;
		this.height = height;
		/** Getting position of Hero */
		position = new Vector2(x, y);
		center = new Vector2(0,0);
		/** Creating and positioning enclosing rectangle */
		recthero = new Rectangle(position.x,position.y,width,height);
		/** Getting reference of bullets */
		bullet1 = world.getBullet1();
		bullet2 = world.getBullet2();
		bullet3 = world.getBullet3();
		bullet4 = world.getBullet4();
	}

	/* Updates hero's position 
	 * and detects possible collision */
	public void update(float delta) {

		/** Updates hero's position */
		recthero.setPosition(position);
		/** Getting reference of bullet's rectangles */
		rect1 = bullet1.getRect();
		rect2 = bullet2.getRect();
		rect3 = bullet3.getRect();
		rect4 = bullet4.getRect();
		/** Colision detection */
		if ((recthero.overlaps(rect1)) || (recthero.overlaps(rect2))
				|| (recthero.overlaps(rect3))
				|| (recthero.overlaps(rect4))) {
			/** Change state if collision */
			world.setCurrentState(GameState.GAMEOVER);
		}
		

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


	public void setX(float x) {
		position.x = x;
	}

	public void setY(float y) {
		position.y = y;
	}

	public void setCenterY(float y) {
		center.y = y;
	}
	
	public float getCenterY(){
		return center.y;
	}
	
	public void setCenterX(float x) {
		center.x = x;
	}
	
	public float getCenterX(){
		return center.x;
	}

	public Rectangle getRect() {
		return recthero;
	}	
	
	public Vector2 getCenter() {
		return center;
	}

	public void setCenter(Vector2 center) {
		this.center = center;
	}
}
