package com.hanuman;

import java.util.Random;

public class MovingLedge extends Tile 
{
	/**
	 * 
	 */
	Random rand;
	int timeToWaitUponInit = 0;
	private static final long serialVersionUID = 1L;
	public boolean moveLeft = true;
	private int counter;
	private int moveLength;
    public MovingLedge(int moveLength)
    {
    	super(11); //11 is the Moving Ledge tile's image index
    	this.moveLength = moveLength;
    	velX = 0;
    	velY = 0;
    	rand = new Random();
    	timeToWaitUponInit = rand.nextInt(100);
    }
    public void setCounter(int newCount)
    {
    	counter = newCount;
    }
    public void randomizeInitTime()
    {
    	rand = new Random();
    	timeToWaitUponInit = rand.nextInt(100);
    }
    public void setRect(int k, int j)
    {
    	final int rectWidth = 90;
    	final int rectHeight = 60;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    }
    public int getMoveLength()
    {
    	return moveLength;
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.MOVELEDGE;
    }
    public void update()
    {
    	rect.left = rect.x;
    	rect.right = rect.x+rect.width;
    	rect.top = rect.y+rect.height;
    	rect.bottom = rect.y;
    	if(timeToWaitUponInit > 0)
    	{
    		timeToWaitUponInit--;
    	}
    	else
    	{
        	move();
    	}
    	
    }
    private void move()
    {
    	if(moveLeft)
    	{
    		//velY = 2;
    		velX = -3;
    		counter++;
    	}
    	else
    	{
    		//velY = -2;
    		velX = 3;
    		counter++;
    	}
    	if(counter == (moveLength*90)/3 && moveLeft)
    	{
    		counter = 0;
    		moveLeft = false;
    	}
    	else if (counter == (moveLength*90)/3 && !moveLeft)
    	{
    		counter = 0;
    		moveLeft = true;
    	}
    	rect.x += velX;
    	//rect.y += velY;
    }
	
}