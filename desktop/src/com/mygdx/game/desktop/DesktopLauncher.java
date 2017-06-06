package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.PublicSpaceEvaders;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1066;
		config.height = 600;
		config.title = "Public Space Evaders";

		config.foregroundFPS = 60;
		new LwjglApplication(new PublicSpaceEvaders(), config);
	}
}
