package com.hanuman;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Gunman extends FollowerEnemy
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Unit target;
	Bullet bullet;
	public static boolean dontDrawBullets = false;
	static Random rand = new Random();

    public Gunman(int hp,int speed, Bullet b)
    {
    	super(hp, speed, 1, 2);
    	bullet = b;
    }
    public Gunman(int hp, Bullet b)                             //WHY ARE THERE SOOOO MANY CONSTRUCTORZ!?!
    {
    	super(hp, Constants.gunManSpeed, 5, 2);
    	bullet = b;
    }
    public Gunman(int hp)
    {
    	super(hp, Constants.gunManSpeed, 5, 2);
    }
    public Gunman(int hp, int index)
    {
    	super(hp, 0, index, index);
    }
    public Gunman(int hp, int speed, int movingIndex, int idleIndex, Bullet b)
    {
    	super(hp,speed,movingIndex,idleIndex);
    	bullet = b;
    }
    public void flipOrientation()
    {
    	super.flipOrientation();
    	bullet.movingLeft = !bullet.movingLeft;
    	bullet.movingRight = !bullet.movingLeft;
    }
    public void setRect(int k, int j)
    {
    	final int rectWidth = 60;
    	final int rectHeight = 60;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    	
    	if(bullet!=null)
    	{
			if(facingLeft)
				bullet.setRect(rect.x-40, rect.y);
			else if(facingRight)
				bullet.setRect(rect.x+rectWidth+40, rect.y);
    	}
    	
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
    	
    	if(bullet!=null)
    	{
			if(facingLeft)
				bullet.setRect(rect.x-40, rect.y);
			else if(facingRight)
				bullet.setRect(rect.x+rectWidth+40, rect.y);
    	}
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
    public void update(int mapSize, Tile[][] mapTiles)
	{
		super.update(mapSize, mapTiles);
		bullet.update(mapSize, mapTiles);
	}
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.GUNMAN;
    }
    protected void checkBotLeft(Tile[][] mapTiles)
    {
    	Rectangle2D.Double check = new Rectangle2D.Double(rect.left - rect.width, rect.bottom, rect.width, 1);
		for(Tile[] tile : mapTiles)
		{
			for(Tile t : tile)
			{
				if(t != null)
				{
					if(check.intersects(t.rect))
					{
						return;
					}
				}
			}
		}
		if(!isJumping && rect.bottom >= 600-60)
		{
			//movingLeft = false;
		}
    }
    protected void checkBotRight(Tile[][] mapTiles)
    {
    	Rectangle2D.Double check = new Rectangle2D.Double(rect.right, rect.bottom, rect.width, 1);
		for(Tile[] tile : mapTiles)
		{
			for(Tile t : tile )
			if(t != null)
			{
				{
					if(check.intersects(t.rect))
					{
						return;
					}
				}
			}
		}
		if(!isJumping && rect.bottom >= 600-60)
		{
			//movingRight = false;
		}
    }
	public void act(Tile[][] mapTiles)
	{
		super.act(mapTiles);
    	if(!bullet.alive)
    	{
    		bullet.reset(this);
    	}
	}
	public void draw(SpriteBatch batch)
	{
		super.draw(batch);
		if(bullet!=null)
		bullet.draw(batch);
	}
	public void draw(SpriteBatch batch, Camera cam)
	{
		super.draw(batch,cam);
		if(bullet!=null && !dontDrawBullets)
			bullet.draw(batch,cam);
	}
}