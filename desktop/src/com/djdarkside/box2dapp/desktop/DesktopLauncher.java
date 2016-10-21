package com.djdarkside.box2dapp.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.djdarkside.box2dapp.Application;
import com.djdarkside.box2dapp.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.V_WIDTH;
		config.height = Constants.V_HEIGHT;
		config.title = Constants.TITLE;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		new LwjglApplication(new Application(), config);
	}
}
