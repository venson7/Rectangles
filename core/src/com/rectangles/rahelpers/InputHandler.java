package com.rectangles.rahelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.rectangles.gameobjects.Bullet;
import com.rectangles.gameobjects.Hero;
import com.rectangles.gameworld.GameRenderer;
import com.rectangles.gameworld.GameWorld;
import com.rectangles.gameworld.GameWorld.GameState;

/** Class that handles input from the user
 * @author Kostas Datsos
 */
public class InputHandler implements InputProcessor {
	/** Game Objects
	 * 	hero refers to the main character (rectangle)
	 * 	world refers to game logic class */
	private Hero hero;
	private GameWorld world;
	/** Is true when last touch occured within hero boundaries */
	private boolean isTouched;

	public InputHandler(Hero hero, GameWorld world, GameRenderer renderer) {
		this.hero = hero;
		this.world = world;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		/** When state of game is RUNNING */
		if (world.getCurrentState() == GameState.RUNNING) {
			/** If touch occured within hero's boundaries */
			if (screenX > hero.getX() - Gdx.graphics.getWidth()/40
					&& screenX < hero.getX() + hero.getWidth() + Gdx.graphics.getWidth()/40
					&& screenY > hero.getY() - Gdx.graphics.getHeight()/40
					&& screenY < hero.getY() + hero.getHeight() + Gdx.graphics.getHeight()/40 ) {
				/** Changing hero's relative center (touch position) */
				hero.setCenterX(screenX - hero.getX());
				hero.setCenterY(screenY - hero.getY());
				isTouched = true;
				return true;
			/** If touch occured outside hero's boundaries */
			} else {
				isTouched = false;
	
			}
		/** When state of the game is READY or GAMEOVER */
		} else {
			/** Populate bullet queue with all four sides of screen */
			Bullet.populateQueue();
			/** Move bullets to their initial position */
			world.bulletsReposition();
			/** Reset score */
			world.score.reset();
			/** Move hero to center of the screen */
			hero.setX(Gdx.graphics.getWidth()/2);
			hero.setY(Gdx.graphics.getHeight()/2);
			/** Change state to RUNNING */
			world.setCurrentState(GameState.RUNNING);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		/** When state of the game is RUNNING */
		if (world.getCurrentState() == GameState.RUNNING) {
			/** If touch occured within hero's boundaries */
			if (isTouched && screenX > 0 && screenX < Gdx.graphics.getWidth()
					&& screenY > 0 && screenY < Gdx.graphics.getHeight()) {
				/** Move hero to the current dragging position */
				hero.setX(screenX - hero.getCenterX());
				hero.setY(screenY - hero.getCenterY());
			}
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
