package com.rectangles.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rectangles.RAGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Rect-Angles";
        config.width = 480;//Gdx.graphics.getWidth();//272;
        config.height = 320;//Gdx.graphics.getHeight();//408;
        new LwjglApplication(new RAGame(), config);
	}
}
