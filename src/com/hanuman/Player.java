package com.hanuman;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Player extends Unit
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Player(int hp)
    {
    	super(hp, Constants.playerSpeed, 7, 1); //7 and 1 are the indices of the player moving image and idle image respectively
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.PLAYER;
    }
    public void setRect(int k, int j)
    {
    	final int rectWidth = 50;
    	final int rectHeight = 50;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    	TextureRegion[][] tRightRegis = spriteRight.split(95, 95);
    	RightRegis = new Sprite[tRightRegis.length][tRightRegis[0].length];
    	for(int i = 0; i < tRightRegis.length; i++)
    	{
    		for(int l = 0; l < tRightRegis[0].length; l++)
    		{
    			RightRegis[i][l] = new Sprite(tRightRegis[i][l]);
    		}
    	}
    	TextureRegion[][] tIdleRegis = idleRight.split(60, 60);
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
    	final int rectWidth = 60;
    	final int rectHeight = 60;
    	rect = new AugmentedRectangle((int)x, (int)y, rectWidth, rectHeight);
    	TextureRegion[][] tRightRegis = spriteRight.split(95, 95);
    	RightRegis = new Sprite[tRightRegis.length][tRightRegis[0].length];
    	for(int i = 0; i < tRightRegis.length; i++)
    	{
    		for(int l = 0; l < tRightRegis[0].length; l++)
    		{
    			RightRegis[i][l] = new Sprite(tRightRegis[i][l]);
    		}
    	}
    	TextureRegion[][] tIdleRegis = idleRight.split(60, 60);
    	idleRightregis = new Sprite[tIdleRegis.length][tIdleRegis[0].length];
    	for(int i = 0; i < tIdleRegis.length; i++)
    	{
    		for(int l = 0; l < tIdleRegis[0].length; l++)
    		{
    			idleRightregis[i][l] = new Sprite(tIdleRegis[i][l]);
    		}
    	}
    }
    public void update(int mapSize, Tile[][] mapTiles,boolean leftKey, boolean rightKey )
    {
    	if(leftKey)
    	{
    		movingLeft = true;
    	}
    	else
    	{
    		movingLeft = false;
    	}
    	if(rightKey)
    	{
    		movingRight = true;
    	}
    	else
    	{
    		movingRight = false;
    	}
    	if(rect.y> (mapTiles[0].length-1)*60)
    	{
    		rect.y = (mapTiles[0].length-1)*60;
    	}
    	super.update(mapSize, mapTiles);

    }
    public void draw(SpriteBatch batch, Camera cam)
	{
		if(i >= 8)
		{
			i = 0;
		}
		if(idleI >= 10)
		{
			idleI = 0;
		}
		if(movingRight)
		{
			batch.draw(RightRegis[0][i], (int)rect.x+45 - cam.xOffset, (int)rect.y - cam.yOffset, (int)-rect.width, (int)rect.height);
			facingRight = true;
			facingLeft = false;
		}
		else if (movingLeft)
		{
			batch.draw(RightRegis[0][i],(int)rect.x - cam.xOffset, (int)rect.y - cam.yOffset, (int)rect.width, (int)rect.height);
			facingLeft = true;
			facingRight = false;
		}
		else
		{
			if(facingLeft)
				batch.draw(idleRightregis[0][idleI], (int)rect.x - cam.xOffset, (int)rect.y - cam.yOffset, (int)rect.width, (int)rect.height);
			else if (facingRight)
				batch.draw(idleRightregis[0][idleI], (int)rect.x+45 - cam.xOffset, (int)rect.y - cam.yOffset, -(int)rect.width, (int)rect.height);

		}
		if(movingLeft || movingRight)
		{
			idleI = 0;
			count++;
			if(count % 6 == 0)
			{
				i++;
			}
		}
		else
		{
			i = 0;
			idleCount++;
			if(idleCount % 8 == 0)
			{
				idleI++;
			}
		}
	}
}