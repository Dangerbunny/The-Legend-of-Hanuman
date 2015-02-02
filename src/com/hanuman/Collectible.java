package com.hanuman;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Collectible implements Element, Serializable
{
	private static final long serialVersionUID = 1L;
	Sprite sprite;
	Sprite[][] regions;
	Sound pickupSound;
	int pointValue;
	AugmentedRectangle rect;
	int imageCount = 0, counterCount = 0;
	int personalImgIndex;
	
    public Collectible(int imageIndex, Sound sound, int points)
    {
    	personalImgIndex = imageIndex;
    	setSprite(personalImgIndex);
    	pickupSound = sound;
    	pointValue = points;
    }
    public Collectible(int imageIndex)
    {
    	personalImgIndex = imageIndex;
    	setSprite(personalImgIndex);
    }
    protected void setSprite(int imageIndex)
	{
		sprite = new Sprite(HanuGame.regions.get(imageIndex));
	}
    private void writeObject(ObjectOutputStream out) throws IOException
    {
    	out.defaultWriteObject();
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
    	in.defaultReadObject();
    	setSprite(personalImgIndex);
    	if(!HanuGame.inLevel)
    		setRect(rect.x,rect.y);
    	else if(HanuGame.inLevel)
    		setRect((int)rect.x/90,(int)rect.y/60);
    }
    public TileEditor.Elem getType()
    {
    	return null;
    }
    public AugmentedRectangle getRect()
    {
    	return rect;
    }
    public void applyEditorScalar()
	{
		rect.width *= Constants.editorWidthScalar;
		rect.x *= Constants.editorWidthScalar;
	}
    public void undoEditorScalar()
	{
		rect.width /= Constants.editorWidthScalar;
		rect.x /= Constants.editorWidthScalar;
	}
    public void applyElementTransform() //used if this is to be an element to be selected from in the editor
	{
		rect.width = 90*Constants.editorWidthScalar;
		rect.x *= Constants.editorWidthScalar;
		rect.height = 60;
	}
	/*public void editFieldInfo()
	{
		System.out.println("Please enter a point value for this collectible: ");
		try {
			pointValue = System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
    public abstract void setRect(int k, int mapSize);
    public abstract void setRect(double x, double y);
	public abstract void draw(SpriteBatch batch, Camera c);

}