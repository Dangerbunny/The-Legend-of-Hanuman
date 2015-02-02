package com.hanuman;

import com.badlogic.gdx.backends.lwjgl.LwjglApplet;

public class BrowserStarter extends LwjglApplet
{
	private static final long serialVersionUID = 1L;
	public BrowserStarter() 
	{
		super(new HanuGame(), false);
	}
}
