package com.hanuman;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class fish extends PatrollerEnemy
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public fish(int hp)
    {
    	super(hp, 9);
    	movingLeft = true;
    	gravity = false; //set to false, in combination with overriding the check methods allows fish to fly
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.FISH;
    }
    protected void checkBotLeft(Tile[][] mapTiles) //These are overridden from PatrollerEnemy so that the fish can fly
    {
    }
    protected void checkBotRight(Tile[][] mapTiles)
    {
    }

    public void setRect(int k, int j)
    {
    	final int rectWidth = 90;
    	final int rectHeight = 56;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    	TextureRegion[][] tRightRegis = spriteRight.split(90, 70);
    	RightRegis = new Sprite[tRightRegis.length][tRightRegis[0].length];
    	for(int i = 0; i < tRightRegis.length; i++)
    	{
    		for(int l = 0; l < tRightRegis[0].length; l++)
    		{
    			RightRegis[i][l] = new Sprite(tRightRegis[i][l]);
    		}
    	}
    	TextureRegion[][] tIdleRegis = idleRight.split(90, 70);
    	idleRightregis = new Sprite[tIdleRegis.length][tIdleRegis[0].length];
    	for(int i = 0; i < tIdleRegis.length; i++)
    	{
    		for(int l = 0; l < tIdleRegis[0].length; l++)
    		{
    			idleRightregis[i][l] = new Sprite(tIdleRegis[i][l]);
    		}
    	}
    }
    public void setRect(double x, double y)
    {
    	final int rectWidth = 90;
    	final int rectHeight = 56;
    	rect = new AugmentedRectangle((int)x, (int)y, rectWidth, rectHeight);
    	TextureRegion[][] tRightRegis = spriteRight.split(90, 70);
    	RightRegis = new Sprite[tRightRegis.length][tRightRegis[0].length];
    	for(int i = 0; i < tRightRegis.length; i++)
    	{
    		for(int l = 0; l < tRightRegis[0].length; l++)
    		{
    			RightRegis[i][l] = new Sprite(tRightRegis[i][l]);
    		}
    	}
    	TextureRegion[][] tIdleRegis = idleRight.split(90, 70);
    	idleRightregis = new Sprite[tIdleRegis.length][tIdleRegis[0].length];
    	for(int i = 0; i < tIdleRegis.length; i++)
    	{
    		for(int l = 0; l < tIdleRegis[0].length; l++)
    		{
    			idleRightregis[i][l] = new Sprite(tIdleRegis[i][l]);
    		}
    	}
    }

}