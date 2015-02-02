package com.hanuman;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FalseLedge extends Collectible 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean ledge;
	Sprite ledgeSprite, groundSprite;
	public static boolean inEditor;

	public FalseLedge() 
	{
		super(29);
		//setSprite(29);
		ledge = true;
	}
	protected void setSprite(int index)
	{
		ledgeSprite = new Sprite(HanuGame.regions.get(10));
		groundSprite = new Sprite(HanuGame.regions.get(12));
		super.setSprite(index);
	}
	private void writeObject(ObjectOutputStream out) throws IOException
    {
    	out.defaultWriteObject();
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
    	in.defaultReadObject();
    	setSprite(personalImgIndex);
    }
	public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.FALSELEDGE;
    }
	public void draw(SpriteBatch batch, Camera c)
    {
		if(ledge)
			batch.draw(ledgeSprite, (int)(rect.x - c.xOffset), (int)rect.y - c.yOffset, (int)rect.width, (int)rect.height);
		else
			batch.draw(groundSprite, (int)(rect.x - c.xOffset), (int)rect.y - c.yOffset, (int)rect.width, (int)rect.height);
		if(inEditor)
		{
			batch.draw(sprite, (int)(rect.x - c.xOffset), (int)rect.y - c.yOffset, (int)rect.width, (int)rect.height);
		}
    }
	@Override
	public void draw(SpriteBatch batch) 
	{
		if(ledge)
			batch.draw(ledgeSprite, (int)(rect.x), (int)rect.y, (int)rect.width, (int)rect.height);
		else
			batch.draw(groundSprite, (int)(rect.x), (int)rect.y, (int)rect.width, (int)rect.height);
		if(inEditor)
		{
			batch.draw(sprite, (int)(rect.x), (int)rect.y, (int)rect.width, (int)rect.height);
		}
		
	}
	public void setRect(int k, int j)
    {
    	final int rectWidth = 90;
    	final int rectHeight = 60;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    }
    public void setRect(double x, double y)
    {
    	final int rectWidth = 90;
    	final int rectHeight = 60;
    	rect = new AugmentedRectangle((int)x, (int)y, rectWidth, rectHeight);
    }

}
