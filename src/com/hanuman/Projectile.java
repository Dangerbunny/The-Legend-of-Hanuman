package com.hanuman;


abstract class Projectile extends Enemy 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Projectile(int hp, int speed, int sprite, int idleSprite)
	{
		super(hp,speed, sprite, idleSprite);
	}

	public void update(int mapSize, Tile[][] mapTiles)
	{
		act(mapTiles);
		rect.left = rect.x;
    	rect.right = rect.x+rect.width;
    	rect.top = rect.y + rect.height;
    	rect.bottom = rect.y;

		if(movingRight)
		{
			velX = speed;
		}
		else if(movingLeft)
		{
			velX = -speed;
		}
		else
		{
			velX = 0;
		}

		if(rect.x <= 0)
		{
			rect.x = 0;
		}
		if(rect.x + rect.width >= (mapSize)*90)
		{
			rect.x = (mapSize)*90 - rect.width;
		}
		if(health <= 0)
		{
			alive = false;
			movingLeft = false;
			movingRight = false;
		}
		rect.x += velX;
		rect.y += velY;
	}



}
