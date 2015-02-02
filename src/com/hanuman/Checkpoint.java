package com.hanuman;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Checkpoint extends Collectible
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int xValue,yValue;
	final static int pointVal = 0;
    public Checkpoint(Sound clip, int xval, int yval)
    {
    	super(24, clip, pointVal); //24 is the Checkpoint class's image index
    	xValue = xval;
    	yValue = yval;
    }
    public Checkpoint(Sound clip)
    {
    	super(24, clip, 0); //24 is the Checkpoint class's image index
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.CHECK;
    }
    public AugmentedRectangle getRect()
    {
    	return rect;
    }
    public void setRect(int k, int j)
    {
    	final int rectWidth = 90;
    	final int rectHeight = 45;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    	TextureRegion[][] TempRegions = sprite.split(90, 45);
    	regions = new Sprite[TempRegions.length][TempRegions[0].length];
    	for(int i = 0; i < TempRegions.length; i++)
    	{
    		for(int l = 0; l < TempRegions[0].length; l++)
    		{
    			regions[i][l] = new Sprite(TempRegions[i][l]);
    		}
    	}
    }
    public void setRect(double x, double y)
    {
    	final int rectWidth = 90;
    	final int rectHeight = 45;
    	rect = new AugmentedRectangle((int)x, (int)y, rectWidth, rectHeight);
    	TextureRegion[][] TempRegions = sprite.split(90, 45);
    	regions = new Sprite[TempRegions.length][TempRegions[0].length];
    	for(int i = 0; i < TempRegions.length; i++)
    	{
    		for(int l = 0; l < TempRegions[0].length; l++)
    		{
    			regions[i][l] = new Sprite(TempRegions[i][l]);
    		}
    	}
    }
    public void draw(SpriteBatch batch, Camera c) //this is exactly the same as Collectibles draw except it updates the animation slower
	{
		//g.drawImage(sprite, (int)rect.x - c.xOffset, (int)rect.y, (int)rect.x + (int)rect.width - c.xOffset, (int)rect.y + (int)rect.height, (int)rect.width*imageCount, 0, (int)(rect.width*imageCount + rect.width), sprite.getHeight(iObs), iObs);
    	batch.draw(regions[0][0], (int)rect.x - c.xOffset, (int)rect.y -(int)c.yOffset, (int)rect.width, (int)rect.height);

	}
    public void draw(SpriteBatch batch)
    {
    	batch.draw(regions[0][0], (int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
    }
}