package com.rectangles.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Disposable;
import com.rectangles.gameobjects.Hero;
import com.rectangles.gameobjects.Bullet;
import com.rectangles.gameworld.GameWorld.GameState;
import com.rectangles.rahelpers.AssetLoader;
import com.rectangles.rahelpers.BackgroundHandler;

public class GameRenderer implements Disposable {
	/** Game Objects
	 *  world refers to game logic class
	 *  bullets refer to enemies
	 * 	hero refers to the main character (rectangle)
	 * 	background refers to background handler class */
	private GameWorld myWorld;
	private Bullet bullet1, bullet2, bullet3, bullet4;
	private Hero hero;
	private BackgroundHandler background;
	/** Graphics related objects */
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	private BitmapFont font; 
	private GlyphLayout glyphLayout;
	/** Strings used for rendering text */
	String string1, string2,string3;
	/** Used to determine output size of text after scaling */
	private float w,h;

	public GameRenderer(GameWorld world, BackgroundHandler background) {
		/** Get objects */
		myWorld = world;
		this.background = background;
		getGameObjects();
		/** Create orthographic camera and set to ortho */
		cam = new OrthographicCamera();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		/** Create batcher and shaperenderer */
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		/** Load font */
		font = new BitmapFont(Gdx.files.internal("data/calibri.fnt"), true);
		/** Initiate glyphLayout */
		glyphLayout = new GlyphLayout();
	}

	/** Gets reference of game objects */
	private void getGameObjects() {
		hero = myWorld.getHero();
		bullet1 = myWorld.getBullet1();
		bullet2 = myWorld.getBullet2();
		bullet3 = myWorld.getBullet3();
		bullet4 = myWorld.getBullet4();
	}

	/** Renderes graphics. called on each frame */
	public void render(float runTime) {
		Gdx.gl.glClearColor(background.getRed(), background.getGreen(), background.getBlue(), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/** When game has not started */
		if (myWorld.getCurrentState() == GameState.READY) {	
			/** Make shapes filled */
			shapeRenderer.begin(ShapeType.Filled);
			/** Get colors and render bullets */
			shapeRenderer.setColor(bullet1.getRed() / 255f, 
					bullet1.getGreen() / 255f, bullet1.getBlue() / 255f, 1);
			shapeRenderer.rect(bullet1.getX(), bullet1.getY(), bullet1.getWidth(),
					bullet1.getHeight());
			shapeRenderer.setColor(bullet2.getRed() / 255f,
					bullet2.getGreen() / 255f, bullet2.getBlue() / 255f, 1);
			shapeRenderer.rect(bullet2.getX(), bullet2.getY(), bullet2.getWidth(),
					bullet2.getHeight());
			shapeRenderer.setColor(bullet3.getRed() / 255f,
					bullet3.getGreen() / 255f, bullet3.getBlue() / 255f, 0.9f);
			shapeRenderer.rect(bullet3.getX(), bullet3.getY(), bullet3.getWidth(),
					bullet3.getHeight());
			shapeRenderer.setColor(bullet4.getRed() / 255f,
					bullet4.getGreen() / 255f, bullet4.getBlue() / 255f, 0.8f);
			shapeRenderer.rect(bullet4.getX(), bullet4.getY(), bullet4.getWidth(),
					bullet4.getHeight());
			shapeRenderer.end();
			/** Create string messages */
			string1 = "Welcome to Rect-Angles!";
			string2 = "Tap Screen to start...";
			/** Scale font according to resolution */
			font.getData().setScale(3 * Gdx.graphics.getWidth()/1920f, 3 * Gdx.graphics.getHeight()/1080f);
			/** Initiate batcher for rendering text */
			batcher.begin();
			/** Calculate size of text to determine it's position */
			glyphLayout.setText(font,string1);
			w = glyphLayout.width;
			h = glyphLayout.height;
			/** Render text */
			font.draw(batcher, string1, (Gdx.graphics.getWidth() - w)/2, Gdx.graphics.getHeight()/3.6f);
			/** Calculate size of text to determine it's position */
			glyphLayout.setText(font,string2);
			w = glyphLayout.width;
			/** Render text */
			font.draw(batcher, string2, (Gdx.graphics.getWidth() - w)/2, Gdx.graphics.getHeight()/3.3f + h);
			batcher.end();	
		/** Game is running */
		} else if (myWorld.getCurrentState() == GameState.RUNNING) {
			renderEnvironment();
		/** Current state is GAMEOVER */
		} else {
			/** Create string messages */
			string1 = "You Lost!";
			string2 = "Your Score is " + Integer.toString((int)myWorld.score.getScore());
			string3 = "High Score is " + Integer.toString(AssetLoader.getHighScore());
			/** Scale font according to resolution */
			font.getData().setScale(3 * Gdx.graphics.getWidth()/1920f, 3 * Gdx.graphics.getHeight()/1080f);
			/** Initiate batcher for rendering text */
			batcher.begin();
			/** Calculate size of text to determine it's position */
			glyphLayout.setText(font,string1);
			w = glyphLayout.width;
			h = glyphLayout.height;
			/** Render text */
			font.draw(batcher, string1, (Gdx.graphics.getWidth() - w)/2, Gdx.graphics.getHeight()/3.6f);
			/** Calculate size of text to determine it's position */
			glyphLayout.setText(font,string2);
			w = glyphLayout.width;
			/** Render text */
			font.draw(batcher, string2, (Gdx.graphics.getWidth() - w)/2, Gdx.graphics.getHeight()/3.3f + h);
			/** Calculate size of text to determine it's position */
			glyphLayout.setText(font,string3);
			w = glyphLayout.width;
			/** Render text */
			font.draw(batcher, string3, (Gdx.graphics.getWidth() - w)/2, Gdx.graphics.getHeight()/3f + 2*h);
			batcher.end();
		}
	}

	/** Called by render when current state is RUNNING */
	public void renderEnvironment() {
		/** Make shapes filled */
		shapeRenderer.begin(ShapeType.Filled);
		/** Set color and render hero */
		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		shapeRenderer.rect(hero.getX(), hero.getY(), hero.getWidth(),
				hero.getHeight());
		/** Get colors and render bullets */
		shapeRenderer.setColor(bullet1.getRed() / 255f,
				bullet1.getGreen() / 255f,bullet1.getBlue() / 255f, 1);
		shapeRenderer.rect(bullet1.getX(), bullet1.getY(), bullet1.getWidth(),
				bullet1.getHeight());
		shapeRenderer.setColor(bullet2.getRed() / 255f,
				bullet2.getGreen() / 255f, bullet2.getBlue() / 255f, 1);
		shapeRenderer.rect(bullet2.getX(), bullet2.getY(), bullet2.getWidth(),
				bullet2.getHeight());
		shapeRenderer.setColor(bullet3.getRed() / 255f,
				bullet3.getGreen() / 255f, bullet3.getBlue() / 255f, 0.9f);
		shapeRenderer.rect(bullet3.getX(), bullet3.getY(), bullet3.getWidth(),
				bullet3.getHeight());
		shapeRenderer.setColor(bullet4.getRed() / 255f,
				bullet4.getGreen() / 255f, bullet4.getBlue() / 255f, 0.8f);
		shapeRenderer.rect(bullet4.getX(), bullet4.getY(), bullet4.getWidth(),
				bullet4.getHeight());
		shapeRenderer.end();
		batcher.begin();
		/** Render score */
		font.draw(batcher, Integer.toString((int)myWorld.score.getScore()), Gdx.graphics.getWidth()/64f, Gdx.graphics.getHeight()/36f);
		batcher.end();
	}

	@Override
	/** Disposes disposable resources */
	public void dispose() {
		batcher.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}
