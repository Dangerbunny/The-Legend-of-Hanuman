package com.hanuman;


public class Ground extends Tile
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Ground()
    {
    	super(12);//12 is the index of the Ground Image
    	velX = 0;
    	velY = 0;
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.GROUND;
    }
}