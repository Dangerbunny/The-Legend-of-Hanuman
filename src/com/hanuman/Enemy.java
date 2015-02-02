package com.hanuman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Enemy extends Unit
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int idleCount = 1, idleI = 0;
    public Enemy(int hp, int speed, int spriteIndex, int idleIndex)
    {
    	super(hp, speed, spriteIndex, idleIndex);
    	
    }
    public void setRect(int k, int j)
    {
    	final int rectWidth = 60;
    	final int rectHeight = 60;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    	TextureRegion[][] tRightRegis = spriteRight.split(209, 218);
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
    	TextureRegion[][] tRightRegis = spriteRight.split(209, 218);
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
    public abstract void act(Tile[][] mapTiles);
    protected abstract void checkBotLeft(Tile[][] mapTiles);
    protected abstract void checkBotRight(Tile[][] mapTiles);
    public void update(int mapSize, Tile[][] mapTiles)
    {
    	act(mapTiles);
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
			batch.draw(RightRegis[0][i], (int)rect.x - cam.xOffset, (int)rect.y - cam.yOffset, (int)rect.width, (int)rect.height);
			facingRight = true;
			facingLeft = false;
		}
		else if (movingLeft)
		{
			batch.draw(RightRegis[0][i],(int)(rect.x+rect.width) - cam.xOffset, (int)rect.y - cam.yOffset, -(int)rect.width, (int)rect.height);
			facingLeft = true;
			facingRight = false;
		}
		else
		{
			if(facingLeft)
				batch.draw(idleRightregis[0][idleI], (int)(rect.x+rect.width) - cam.xOffset, (int)rect.y - cam.yOffset, -(int)rect.width, (int)rect.height);
			else if (facingRight)
				batch.draw(idleRightregis[0][idleI], (int)rect.x - cam.xOffset, (int)rect.y - cam.yOffset, (int)rect.width, (int)rect.height);

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
    public void draw(SpriteBatch batch)
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
			batch.draw(RightRegis[0][i], (int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
			facingRight = true;
			facingLeft = false;
		}
		else if (movingLeft)
		{
			batch.draw(RightRegis[0][i],(int)(rect.x+rect.width), (int)rect.y, -(int)rect.width, (int)rect.height);
			facingLeft = true;
			facingRight = false;
		}
		else
		{
			if(facingLeft)
				batch.draw(idleRightregis[0][idleI], (int)(rect.x+rect.width), (int)rect.y, -(int)rect.width, (int)rect.height);
			else if (facingRight)
				batch.draw(idleRightregis[0][idleI], (int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);

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