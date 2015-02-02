package com.hanuman;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Spike extends Deadly
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Spike()
    {
    	super(13); //13 is the Spike tile's image index
    	velX = 0;
    	velY = 0;
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.SPIKE;
    }

    public void setRect(int k, int j)
    {
    	super.setRect(k, j);
    	rect.height = 43;
    }
    public void setRect(double x, double y)
    {
    	super.setRect(x, y);
    	rect.height = 43;
    }
    public void draw(SpriteBatch batch, Camera c)
    {
    	batch.draw(sprite, (int)(rect.x - c.xOffset), (int)rect.y - c.yOffset, (int)rect.width, (int)60);
    	//g.drawImage(sprite, (int)(rect.x - c.xOffset), (int)rect.y);
    }
}