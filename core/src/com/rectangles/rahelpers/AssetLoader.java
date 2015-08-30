package com.rectangles.rahelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/** Class that loads assets of the game
 * @author Kostas Datsos
 */
public class AssetLoader {
	/** Game preferences */
	public static Preferences prefs;
	
	/** Loads game preferences */
	public static void load() {	
		/** Create (or retrieve existing) preferences file */
		prefs = Gdx.app.getPreferences("Rectangles");
		/** If no high score */
		if (!prefs.contains("highScore")) {
		    prefs.putInteger("highScore", 0);
		    prefs.flush();
		}		
	}
	
	/** Sets high score to score */
	public static void setHighScore(int score) {
	    prefs.putInteger("highScore", score);
	    prefs.flush();
	}

	/** Retrieves the current high score */
	public static int getHighScore() {
	    return prefs.getInteger("highScore");
	}
}
