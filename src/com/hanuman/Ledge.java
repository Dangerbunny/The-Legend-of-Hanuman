package com.hanuman;


public class Ledge extends Tile 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Ledge()
    {
    	super(10); //10 is the Ledge tile's image index
    	velX = 0;
    	velY = 0;
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.LEDGE;
    }
}