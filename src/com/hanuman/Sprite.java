package com.hanuman;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite extends com.badlogic.gdx.graphics.g2d.Sprite implements Serializable
{

	private static final long serialVersionUID = 1L;

	public Sprite(TextureRegion loadedTexture)
	{
		super(loadedTexture);
	}
	private void writeObject(ObjectOutputStream out) throws IOException
    {
    	out.defaultWriteObject();
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
    	in.defaultReadObject();
    	//sprite = new Sprite()
    }

}