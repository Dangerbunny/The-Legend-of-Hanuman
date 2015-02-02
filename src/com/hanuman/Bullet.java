package com.hanuman;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


class Bullet extends Projectile
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int mapWidth = 0;
	public Bullet(int hp, boolean left)
    {
    	super(hp, Constants.bulletSpeed, 6, 6);
    	movingLeft = left;
    	movingRight = !left;
    	gravity = false;
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.BULLET;
    }
	public void setRect(double x, double y)
    {
    	final int rectWidth = 40;
    	final int rectHeight = 20;
    	rect = new AugmentedRectangle((int)x , (int)y, rectWidth, rectHeight);
    	TextureRegion[][] tRightRegis = spriteRight.split(20, 10);
    	RightRegis = new Sprite[tRightRegis.length][tRightRegis[0].length];
    	for(int i = 0; i < tRightRegis.length; i++)
    	{
    		for(int l = 0; l < tRightRegis[0].length; l++)
    		{
    			RightRegis[i][l] = new Sprite(tRightRegis[i][l]);
    		}
    	}
    	TextureRegion[][] tIdleRegis = idleRight.split(20, 10);
    	idleRightregis = new Sprite[tIdleRegis.length][tIdleRegis[0].length];
    	for(int i = 0; i < tIdleRegis.length; i++)
    	{
    		for(int l = 0; l < tIdleRegis[0].length; l++)
    		{
    			idleRightregis[i][l] = new Sprite(tIdleRegis[i][l]);
    		}
    	}
    }
    public void setRect(int k, int j)
    {
    	final int rectWidth = 40;
    	final int rectHeight = 20;
    	rect = new AugmentedRectangle(k*90, j*60 + 30, rectWidth, rectHeight);
    	TextureRegion[][] tRightRegis = spriteRight.split(20, 10);
    	RightRegis = new Sprite[tRightRegis.length][tRightRegis[0].length];
    	for(int i = 0; i < tRightRegis.length; i++)
    	{
    		for(int l = 0; l < tRightRegis[0].length; l++)
    		{
    			RightRegis[i][l] = new Sprite(tRightRegis[i][l]);
    		}
    	}
    	TextureRegion[][] tIdleRegis = idleRight.split(20, 10);
    	idleRightregis = new Sprite[tIdleRegis.length][tIdleRegis[0].length];
    	for(int i = 0; i < tIdleRegis.length; i++)
    	{
    		for(int l = 0; l < tIdleRegis[0].length; l++)
    		{
    			idleRightregis[i][l] = new Sprite(tIdleRegis[i][l]);
    		}
    	}
    }
    public void setMapWidth(int mapW)
    {
    	mapWidth = mapW;
    }
    protected void checkBotLeft(Tile[][] mapTiles)
    {

    }
    protected void checkBotRight(Tile[][] mapTiles)
    {

    }
	public void act(Tile[][] mapTiles)
	{
		if(rect.x <=0  || (rect.right >= (mapWidth)*90))
		{
			this.alive = false;
		}
	}

	public void reset(Enemy shooter)
	{
		this.alive = true;
		if(shooter.facingLeft)
		{
			rect.y = shooter.rect.y + shooter.rect.height/2;
			rect.x = shooter.rect.x - rect.width - 1;
		}
		else if (shooter.facingRight)
		{
			rect.y = shooter.rect.y + shooter.rect.height/2;
			rect.x = shooter.rect.x + shooter.rect.width + 1;
		}
		
		this.movingLeft = shooter.facingLeft;
		this.movingRight = !this.movingLeft;
	}
}
