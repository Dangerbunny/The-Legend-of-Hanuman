package com.hanuman;


public class Sticky extends Tile 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Sticky()
    {
    	super(16); //16 is the Sticky tile's image index
    	velX = 0;
    	velY = 0;
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.STICKY;
    }
    public void effect(Unit unit1)
    {
    	unit1.movingLeft = false;
    	unit1.movingRight = false;
    	unit1.velX = 0;

    }
}