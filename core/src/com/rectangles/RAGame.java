package com.rectangles;

import com.badlogic.gdx.Game;
import com.rectangles.rahelpers.AssetLoader;
import com.rectangles.screens.GameScreen;

public class RAGame extends Game {
	@Override
	public void create () {
		AssetLoader.load();
		setScreen(new GameScreen());
	}
}
