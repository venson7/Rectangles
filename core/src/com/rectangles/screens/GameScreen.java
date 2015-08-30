package com.rectangles.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.rectangles.gameworld.GameRenderer;
import com.rectangles.gameworld.GameWorld;
import com.rectangles.rahelpers.BackgroundHandler;
import com.rectangles.rahelpers.InputHandler;

/** Class that handles game screen.
 * 	this game only has one screen
 * 	@author Kostas Datsos
 */
public class GameScreen implements Screen {
	/** Game Objects
	 * 	world refers to game logic class 
	 * 	renderer refers to the class that renders graphics
	 * 	background refers to the object that handles background */
	private GameWorld world;
	private GameRenderer renderer;
	private BackgroundHandler background;
	
	public GameScreen() {
		/** Creating new background class */
		background = new BackgroundHandler();
		/** Initializing world */
		world = new GameWorld(background);
		/** Initializing renderer */
		renderer = new GameRenderer(world, background);
		/** Creating new input handler */
		Gdx.input.setInputProcessor(new InputHandler(world.getHero(),world,renderer));
	}

	@Override
	/** updates world and renders graphics on every frame */
	public void render(float delta) {
		/** Update world */
		world.update(delta);
		/** Render environment */
		renderer.render(delta);
	}

	@Override
	/** Not implemented */
	public void resize(int width, int height) {}

	@Override
	/** Not implemented */
	public void show() {}

	@Override
	/** Not implemented */
	public void hide() {}

	@Override
	/** Not implemented */
	public void pause() {}

	@Override
	/** Not implemented */
	public void resume() {}

	@Override
	/** Disposes resources */
	public void dispose() {
		renderer.dispose();
	}
}
