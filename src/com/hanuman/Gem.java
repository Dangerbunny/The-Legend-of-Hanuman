package com.hanuman;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Gem extends Collectible
{

    Random rand;
	private static final long serialVersionUID = 1L;
	public Gem(Sound clip, int points)
    {
    	super(18, clip, points); 
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.GEM;
    }
    private void setRandomFruit()
    {
    	rand = new Random();
    	int randInt = rand.nextInt(5)+18;
    	sprite = new Sprite(HanuGame.regions.get(randInt));
    }
    protected void setSprite(int imageIndex)
	{
		setRandomFruit();
	}
    public void setRect(int k, int j)
    {
    	final int rectWidth = 54;
    	final int rectHeight = 54;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    	TextureRegion[][] TempRegions = sprite.split(56, 100);
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
    	final int rectWidth = 54;
    	final int rectHeight = 54;
    	rect = new AugmentedRectangle((int)x, (int)y, rectWidth, rectHeight);
    	TextureRegion[][] TempRegions = sprite.split(56, 100);
    	regions = new Sprite[TempRegions.length][TempRegions[0].length];
    	for(int i = 0; i < TempRegions.length; i++)
    	{
    		for(int l = 0; l < TempRegions[0].length; l++)
    		{
    			regions[i][l] = new Sprite(TempRegions[i][l]);
    		}
    	}
    }
    public void draw(SpriteBatch batch, Camera cam)
    {
    	//System.out.println(rect + " " + cam.xOffset + " " + cam.yOffset);
    	//g.drawImage(sprite, (int)rect.x - cam.xOffset, (int)rect.y, (int)rect.x + (int)rect.width - cam.xOffset, (int)rect.y + (int)rect.height, (int)rect.width*imageCount, 0, (int)(rect.width*imageCount + rect.width), sprite.getHeight(iObs), iObs);
		batch.draw(regions[0][imageCount], (int)(rect.x - cam.xOffset), (int)rect.y - (int)cam.yOffset, (int)rect.width, (int)rect.height);
    	
		counterCount++;
		if(counterCount == 5)
		{
			imageCount++;
			counterCount = 0;
		}
		if(imageCount >= 8)
		{
			imageCount = 0;
		}
    }
    public void draw(SpriteBatch batch)
    {
		batch.draw(regions[0][imageCount], (int)(rect.x), (int)rect.y, (int)rect.width, (int)rect.height);
		counterCount++;
		if(counterCount == 5)
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