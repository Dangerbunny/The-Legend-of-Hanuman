package com.hanuman;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Boss extends FollowerEnemy
{
	int animationRow = 0;
	private static final long serialVersionUID = 1L;
	static final int bossImageIndex = 8;
    public Boss(int hp)
    {
    	super(hp, Constants.bossSpeed, bossImageIndex, bossImageIndex);
    }
    public void setRect(int k, int j)
    {
    	final int rectWidth = 200;
    	final int rectHeight = 200;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    	
    	TextureRegion[][] tRightRegis = spriteRight.split(180, 180);
    	RightRegis = new Sprite[tRightRegis.length][tRightRegis[0].length];
    	for(int i = 0; i < tRightRegis.length; i++)
    	{
    		for(int l = 0; l < tRightRegis[0].length; l++)
    		{
    			RightRegis[i][l] = new Sprite(tRightRegis[i][l]);
    		}
    	}
    	TextureRegion[][] tIdleRegis = idleRight.split(180, 180);
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
    	final int rectHeight = 120;
    	rect = new AugmentedRectangle((int)x, (int)y, rectWidth, rectHeight);
    	TextureRegion[][] tRightRegis = spriteRight.split(180, 180);
    	RightRegis = new Sprite[tRightRegis.length][tRightRegis[0].length];
    	for(int i = 0; i < tRightRegis.length; i++)
    	{
    		for(int l = 0; l < tRightRegis[0].length; l++)
    		{
    			RightRegis[i][l] = new Sprite(tRightRegis[i][l]);
    		}
    	}
    	TextureRegion[][] tIdleRegis = idleRight.split(180, 180);
    	idleRightregis = new Sprite[tIdleRegis.length][tIdleRegis[0].length];
    	for(int i = 0; i < tIdleRegis.length; i++)
    	{
    		for(int l = 0; l < tIdleRegis[0].length; l++)
    		{
    			idleRightregis[i][l] = new Sprite(tIdleRegis[i][l]);
    		}
    	}
    }
    protected void checkBotLeft(Tile[] mapTiles)
    {
    }
    protected void checkBotRight(Tile[] mapTiles)
    {
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.BOSS;
    }
    public void draw(SpriteBatch batch, Camera cam)
    {
    	if(i >= 4)
		{
			i = 0;
			animationRow++;
		}
		if(idleI >= 4)
		{
			idleI = 0;
			animationRow++;
		}
		if(animationRow > 1)
		{
			animationRow = 0;
		}
		if(movingRight)
		{
			batch.draw(RightRegis[animationRow][i], (int)rect.x+180 - cam.xOffset, (int)rect.y - cam.yOffset, (int)-rect.width, (int)rect.height);
			facingRight = true;
			facingLeft = false;
		}
		else if (movingLeft)
		{
			batch.draw(RightRegis[animationRow][i],(int)rect.x - cam.xOffset, (int)rect.y - cam.yOffset, (int)rect.width, (int)rect.height);
			facingLeft = true;
			facingRight = false;
		}
		else
		{
			if(facingLeft)
				batch.draw(idleRightregis[animationRow][idleI], (int)rect.x - cam.xOffset, (int)rect.y - cam.yOffset, (int)rect.width, (int)rect.height);
			else if (facingRight)
				batch.draw(idleRightregis[animationRow][idleI], (int)rect.x+180 - cam.xOffset, (int)rect.y - cam.yOffset, -(int)rect.width, (int)rect.height);

		}
		if(movingLeft || movingRight)
		{
			idleI = 0;
			count++;
			if(count % 8 == 0)
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