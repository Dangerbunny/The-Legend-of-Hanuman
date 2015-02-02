package com.hanuman;

import java.awt.geom.Rectangle2D;

public class AugmentedRectangle extends Rectangle2D.Double
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double left, right, top, bottom;
	public AugmentedRectangle()
	{
	}
    public AugmentedRectangle(int x, int y, int width, int height)
    {
    	super(x, y, width, height);
    	left = x;
    	right = x+width;
    	top = y+height;
    	bottom = y;
    }
}