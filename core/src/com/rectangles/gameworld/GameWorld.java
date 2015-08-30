package com.rectangles.gameworld;

import com.badlogic.gdx.Gdx;
import com.rectangles.gameobjects.Hero;
import com.rectangles.rahelpers.AssetLoader;
import com.rectangles.rahelpers.BackgroundHandler;
import com.rectangles.rahelpers.ScoreHandler;
import com.rectangles.gameobjects.Bullet;

/** Class that contains game logic
 * This class updates every game object,
 * calls the renderer class on each frame,
 * determines the game state
 * @author Kostas Datsos
 */
public class GameWorld {

	/**Enum that characterizes the game state 
	 * READY: Initial state, waiting for key pressing
	 * RUNNING: Main state, objects are moving
	 * GAMEOVER: Game over state, pressing any key changes to RUNNING */
	public enum GameState {
		READY, RUNNING, GAMEOVER
	}
	
	/**Game Objects
	 * hero refers to the main character (rectangle)
	 * bullets refer to enemies (bullets
	 */
	private Hero hero;
	private Bullet bullet1, bullet2, bullet3, bullet4;
	
	/**Handlers
	 * background handles manages background color over time
	 * score handler manages score over time
	 */
	private BackgroundHandler background;
	public ScoreHandler score;

	/*game state variable */
	private GameState currentState;

	public GameWorld(BackgroundHandler background) {
		
		/** Initial state */
		currentState = GameState.READY;
		this.background = background;
		AssetLoader.load();
		score = new ScoreHandler();
		bullet1 = new Bullet(hero,GameWorld.this);
		bullet2 = new Bullet(hero,GameWorld.this);
		bullet3 = new Bullet(hero,GameWorld.this);
		bullet4 = new Bullet(hero,GameWorld.this);
		/* Positioning hero to center of screen, making it square shaped
		 * (Same Width and Height, scaled according to Width)*/
		hero = new Hero(GameWorld.this, Gdx.graphics.getWidth()/2f,
				Gdx.graphics.getHeight()/2f, 
				Gdx.graphics.getWidth()/12.8f ,
				Gdx.graphics.getWidth()/12.8f);
		
		/* Passing reference of hero to bullets */
		bullet1.setHero(hero);
		bullet2.setHero(hero);
		bullet3.setHero(hero);
		bullet4.setHero(hero);
		bulletsReposition();
	}

	/* Called before rendering every frame,
	 * updates every object according to
	 * game state */
	public void update(float delta) {
		
		/* updated on all game states */
		background.update();
		
		if (currentState == GameState.RUNNING) {
			/* when acceleration interval is reached
			 * (see update method in ScoreHandler) */
			if(score.update()){
				accelerateBullets();
			}
			hero.update(delta);
			bulletsUpdate(delta);		
		}else if (currentState == GameState.READY){
			score.reset();
			bulletsUpdate(delta);
		}else{
			/** if score is higher than high score */
			if (score.getScore() > AssetLoader.getHighScore()){
				AssetLoader.setHighScore((int)score.getScore());
			}
			setBulletsInitSpeed();
		}
	}
	
	/* Accelerates bullets according to acceleration coefficient */
	public void accelerateBullets(){
		bullet1.setSpeed((float) (bullet1.getSpeed()*Bullet.acceleration));
		bullet2.setSpeed((float) (bullet2.getSpeed()*Bullet.acceleration));
		bullet3.setSpeed((float) (bullet3.getSpeed()*Bullet.acceleration));
		bullet4.setSpeed((float) (bullet4.getSpeed()*Bullet.acceleration));
	}
	
	/* Updates bullets position */
	public void bulletsUpdate(float delta){
		bullet1.update(delta);
		bullet2.update(delta);
		bullet3.update(delta);
		bullet4.update(delta);
	}
	
	/* Resets bullets speed */
	public void setBulletsInitSpeed(){
		bullet1.setSpeed(Bullet.initSpeed);
		bullet2.setSpeed(Bullet.initSpeed);
		bullet3.setSpeed(Bullet.initSpeed);
		bullet4.setSpeed(Bullet.initSpeed);
	}
	
	/* Moves bullets to initial state and resets their speed */
	public void bulletsReposition(){
		bullet1.reposition();
		bullet2.reposition();
		bullet3.reposition();
		bullet4.reposition();
		setBulletsInitSpeed();
	}

	/* Accessors */
	public Hero getHero() {
		return hero;
	}

	public Bullet getBullet1() {
		return bullet1;
	}

	public Bullet getBullet2() {
		return bullet2;
	}

	public Bullet getBullet3() {
		return bullet3;
	}

	public Bullet getBullet4() {
		return bullet4;
	}

	public GameState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}
}
