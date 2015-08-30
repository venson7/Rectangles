package com.rectangles.rahelpers;

import static com.badlogic.gdx.math.MathUtils.random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

/** Class that handles the background color
 * 	of the game. Color changes over time.
 * 	@author Kostas Datsos
 */
public class BackgroundHandler {
	/** Enum that specifies the state of the color
	 * 	ASCENTING: In this state color becomes 
	 * 	brighter over time
	 * 	DESCENTING: In this state color becomes
	 * 	darker over time */
	private enum colortate {
		ASCENTING, DESCENTING;
	}
	/** Color variables
	 * 	color defines the initial random color
	 * 	colorInit is used as a copy of color
	 * 	colorFinal defines the brightest state of random color
	 * 	step describes the addition that color receives in every frame
	 */
	Color color, colorInit, colorFinal, step;
	/** variable of color state */
	private colortate state;

	public BackgroundHandler() {
		/** Initializing color */
		color = new Color();
		colorInit = new Color();
		colorFinal = new Color();
		step = new Color();
		/** Randomize color */
		initColorValues();
		/** set ASCENDING as initial state */
		state = colortate.ASCENTING;
	}

	/** randomizes color and calculates step */
	private void initColorValues() {
		/** randomizing initial color (darkest) */
		color.r = random(0.08f);
		color.g = random(0.08f);
		color.b = random(0.08f);
		/** Creating copy of initial color */
		colorInit = color.cpy();
		/** Randomizing final color (brightest) */
		colorFinal.r = (random(0.12f) + 0.08f);
		colorFinal.g = (random(0.12f) + 0.08f);
		colorFinal.b = (random(0.12f) + 0.08f);
		/** Calculating step */
		step.r = (colorFinal.r - color.r) / 120f;
		step.g = (colorFinal.g - color.g) / 120f;
		step.b = (colorFinal.b - color.b) / 120f; 
	}

	/** Updates color state
	 * 	called before every frame */
	public void update() {
		/** When color becomes brighter */
		if (state == colortate.ASCENTING) {	
			/** Adding step to color, scaled according to framerate */
			color.add(step.cpy().mul(Gdx.graphics.getDeltaTime()/0.017f));
			/** When color reaches final value */
			if (color.r > colorFinal.r) {
				/** Changing state to DESCENTING */
				state = colortate.DESCENTING;
			}
		/** When color becomes darker */
		} else {
			/** Subtracting step from color, scaled according to framerate */
			color.sub(step.cpy().mul(Gdx.graphics.getDeltaTime()/0.017f));
			/** When color reaches initial value */
			if (color.r < colorInit.r) {
				/** Changing state to ASCENTING */
				state = colortate.ASCENTING;
				/** Create new random color */
				initColorValues();
			}
		}
	}

	/** Accessors */
	public float getRed() {
		return color.r;
	}

	public float getGreen() {
		return color.g;
	}

	public float getBlue() {
		return color.b;
	}
}
