package com.test.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.test.game.Khartoosha;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=Khartoosha.Gheight;
		config.width=Khartoosha.Gwidth;
		config.title=Khartoosha.title;
		new LwjglApplication(new Khartoosha(), config);
	}
}
