package com.hanuman;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Gate extends Collectible
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Gate(Sound clip)
    {
    	super(17, clip, 0); //17 is the Checkpoint class's image index
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.GATE;
    }
    public AugmentedRectangle getRect()
    {
    	return rect;
    }
    public void setRect(int k, int j)
    {
    	final int rectWidth = 60;
    	final int rectHeight = 60;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    	TextureRegion[][] TempRegions = sprite.split(217,200);
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
    	final int rectWidth = 60;
    	final int rectHeight = 60;
    	rect = new AugmentedRectangle((int)x, (int)y, rectWidth, rectHeight);
    	TextureRegion[][] TempRegions = sprite.split(217,200);
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
    	batch.draw(regions[0][imageCount], (int)rect.x - c.xOffset, (int)rect.y - (int)c.yOffset, (int)rect.width, (int)rect.height);
    	counterCount++;
		if(counterCount == 9)
		{
			imageCount++;
			counterCount = 0;
		}
		if(imageCount >= 8)
		{
			imageCount = 0;
		}
	}
    public void draw(SpriteBatch batch) //this is exactly the same as Collectibles draw except it updates the animation slower
	{
		//g.drawImage(sprite, (int)rect.x - c.xOffset, (int)rect.y, (int)rect.x + (int)rect.width - c.xOffset, (int)rect.y + (int)rect.height, (int)rect.width*imageCount, 0, (int)(rect.width*imageCount + rect.width), sprite.getHeight(iObs), iObs);
    	batch.draw(regions[0][imageCount], (int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
    	counterCount++;
		if(counterCount == 9)
		{
			imageCount++;
			counterCount = 0;
		}
		if(imageCount >= 8)
		{
			imageCount = 0;
		}
	}
}