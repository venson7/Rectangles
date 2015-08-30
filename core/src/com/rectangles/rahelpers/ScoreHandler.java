package com.rectangles.rahelpers;

import com.badlogic.gdx.Gdx;

/** Class that handles score of the game
 * @author Kostas Datsos
 */
public class ScoreHandler {

	/** score keeps score from the begining of each game
	 * 	scoreCount resets every "accelerationInterval" */
	float score,scoreCount;
	
	/** specifies the interval between each acceleration of bullets,
	 * in millisecs * 100 */
	final int accelerationInterval = 100;

	public ScoreHandler() {
		reset();
	}
	
	/** resets variables (called before each new game) */
	public void reset(){
		score = 0.0f;
		scoreCount = 0;
	}
	
	/** updates score, called before rendering each frame
	 * returns true approx. every accelerationInterval */
	public boolean update(){
		/** adding time passed since last frame times 10 */
		score += Gdx.graphics.getDeltaTime() * 10;
		scoreCount += Gdx.graphics.getDeltaTime() * 10;
		/** If an interval is passed */
		if (scoreCount > accelerationInterval){
			scoreCount = 0;
			return true;
		}
		return false;
	}

	/** Accessors */
	public float getScore() {
		return score;
	}	
}
