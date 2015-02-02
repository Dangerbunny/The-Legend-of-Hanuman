package com.hanuman;
import java.awt.geom.Rectangle2D;

public class PatrollerEnemy extends Enemy{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int wait = 0;
    public PatrollerEnemy(int hp)
    {
    	super(hp, Constants.patSpeed, 4, 3);
    	movingLeft = true;
    }
    public PatrollerEnemy(int hp,int imgIndex) //this constructor is used for the fish subclass
    {
    	super(hp, Constants.fishSpeed, imgIndex, imgIndex);
    	movingLeft = true;
    }
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.PAT;
    }
    protected void checkBotLeft(Tile[][] mapTiles)
    {
    	Rectangle2D.Double check = new Rectangle2D.Double(rect.left - rect.width, rect.bottom -1, rect.width, 1);
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
		movingLeft = false;
		nextLeft = false;
    }
    protected void checkBotRight(Tile[][] mapTiles)
    {
    	Rectangle2D.Double check = new Rectangle2D.Double(rect.right, rect.bottom -1, rect.width, 1);
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
		movingRight = false;
		nextLeft = true;
    }
    public void act(Tile[][] mapTiles)
    {
    	if(movingLeft)
    	{
    	 	checkBotLeft(mapTiles);
    	}
    	else if(movingRight)
    	{
    		checkBotRight(mapTiles);
    	}
    	else
    	{
    		wait++;
    	}
    	if(wait == 60)
    	{
    		wait = 0;
    		if(nextLeft)
    		{
    			movingLeft = true;
    		}
    		else if (!nextLeft)
    		{
    			movingRight = true;
    		}
    	}
    	
    }

    public void update(int mapSize, Tile[][] mapTiles)
    {
    	act(mapTiles);
    	super.update(mapSize, mapTiles);
    }
}