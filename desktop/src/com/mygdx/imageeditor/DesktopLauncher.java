package com.mygdx.imageeditor;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.imageeditor.ImageEditor;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static int WindowWidth = 584;
	public static int WindowHeight = 480;
	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("ImageEditor");
		config.setWindowedMode(WindowWidth, WindowHeight);
		System.out.println("Project made by Elijah Webb");
		new Lwjgl3Application(new ImageEditor(), config);
	}
}
