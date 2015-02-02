package com.hanuman;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
abstract class Deadly extends Tile
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Deadly(int imageIndex)
    {
    	super(imageIndex);
    	velX = 0;
    	velY = 0;
    }

}
