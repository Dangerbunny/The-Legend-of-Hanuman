package com.hanuman;


public class Launch extends Tile
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int jumpValue = 32;
    public Launch()
    {
    	super(15); //15 is the Launch image index
    	velX = 0;
    	velY = 0;
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.LAUNCH;
    }
    public void effect(Unit unit1)
    {
    	unit1.Jump(jumpValue);

    }
}