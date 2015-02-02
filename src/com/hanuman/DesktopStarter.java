package com.hanuman;

import com.badlogic.gdx.backends.jogl.JoglApplication;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopStarter{
	public static void main(String[] args)
	{
		new JoglApplication(new HanuGame(), "Hanuman", 900, 600, false);
	}
	
}
