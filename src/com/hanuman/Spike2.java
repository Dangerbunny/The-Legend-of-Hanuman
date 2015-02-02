package com.hanuman;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Spike2 extends Deadly
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Spike2()
    {
    	super(14); // 14 is the Spike2 tile's image index
    	velX = 0;
    	velY = 0;
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.SPIKE2;
    }

    public void setRect(int k, int j)
    {
    	super.setRect(k, j);
    	//rect.y+=17;
    	rect.height = 43;

    }
    public void setRect(double x, double y)
    {
    	super.setRect(x, y);
    	//rect.y+=17;
    	rect.height = 43;
    }
    public void draw(SpriteBatch batch, Camera c)
    {
    	batch.draw(sprite, (int)(rect.x - c.xOffset), (int)rect.y - c.yOffset, (int)rect.width, (int)60);
    	//g.drawImage(sprite, (int)(rect.x - c.xOffset), (int)rect.y);
    }
}