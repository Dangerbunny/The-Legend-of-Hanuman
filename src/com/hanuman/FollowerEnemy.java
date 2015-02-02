package com.hanuman;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class FollowerEnemy extends Enemy
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Unit target;
	static Random rand = new Random();
	ArrayList<Vector3> paths;

    public FollowerEnemy(int hp)
    {
    	super(hp, Constants.followerSpeed, 5,3);
    }
    public FollowerEnemy(int hp,int speed)
    {
    	super(hp, speed, 5, 3);
    }
	public FollowerEnemy(int hp, int speed, int moving, int idle)
	{
		super(hp, speed, moving, idle);
	}
    public TileEditor.Elem getType()
    {
    	return TileEditor.Elem.FOLL;
    }

    public void findPlayer(Unit[][] units)
    {
    	ArrayList<Player> players = new ArrayList<Player>();
    	for(Unit[] unit: units)
    	{
    		for(Unit u : unit)
    		{
    			if(u instanceof Player)
        		{
        			players.add((Player)u);
        		}
    		}
    	}
    	if(players.size() > 1)
    		target = players.get(rand.nextInt(players.size()));
    	else
    		target = players.get(0);
    }
    public void findPaths(Tile[][] mapTiles)
    {
    	int targX = (int) (target.rect.x/90);
    	int targY = (int) (target.rect.y/60);
    	int counter = 0;
    	Vector3 currentLocation = new Vector3((int)(rect.x/90+.5), (int)(rect.y/60+.5), 0);
    	ArrayList<Vector3> locationList = new ArrayList<Vector3>();
    	locationList.add(new Vector3(targX,targY, counter));
    	
		for(int i = 0; i < locationList.size(); i++)
		{
			int tempX = (int)locationList.get(i).x;
			int tempY = (int)locationList.get(i).y;
		
    		counter = (int) (locationList.get(i).z+1);
    		ArrayList<Vector3> miniList = new ArrayList<Vector3>();
    		if(tempY < mapTiles[0].length-1 && mapTiles[tempX][tempY+1] == null)
    			miniList.add(new Vector3(tempX,tempY+1,counter));
    		if(tempX < mapTiles.length-1 && mapTiles[tempX+1][tempY] == null)
    			miniList.add(new Vector3(tempX+1, tempY, counter));
    		if(tempY > 0 && mapTiles[tempX][tempY-1] == null)
    			miniList.add(new Vector3(tempX, tempY-1, counter));
    		if(tempX > 0 && mapTiles[tempX-1][tempY] == null)
    			miniList.add(new Vector3(tempX-1, tempY, counter));
    		for(int j = 0; j < miniList.size(); j++)
    		{
    			for(int k = 0; k < locationList.size(); k++)
    			{
    				if(miniList.get(j).x == locationList.get(k).x && miniList.get(j).y == locationList.get(k).y && miniList.get(j).z >= locationList.get(k).z)
        			{
        				miniList.remove(j);
        				j--;
        				break;
        			}
    			}
    		}
    		for(Vector3 vec : miniList)
    		{
    			
    			if(vec.x == currentLocation.x && vec.y == currentLocation.y)
    			{
    				paths = locationList;
    				return;
    			}
    			locationList.add(vec);
    		}
		}
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
		if(!isJumping && rect.bottom >= 600-60 && !(target.rect.y < rect.y))
		{
			Jump();
		}
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
		if(!isJumping && rect.bottom >= 600-60 && !(target.rect.y < rect.y))
		{
			Jump();
		}
    }
	public void act(Tile[][] mapTiles)
	{
		if(Math.abs(rect.x-target.rect.x) < 625 && Math.abs(rect.y-target.rect.y) < 450)
		{
			findPaths(mapTiles);
	    	Vector3 currentLoc = new Vector3((int)(rect.x/90 +.5), (int)(rect.y/60 +.5), 0);
			ArrayList<Vector3> adjacentMoves = new ArrayList<Vector3>();
			ArrayList<Vector3> possibleFalls = new ArrayList<Vector3>();
			int lowestMoveCount = 1000;
			for(Vector3 vec : paths)
			{
				if((Math.abs(currentLoc.x-vec.x)==1 && currentLoc.y == vec.y) || (Math.abs(currentLoc.y-vec.y)==1 && currentLoc.x == vec.x))
				{
					adjacentMoves.add(vec);
				}
				if(currentLoc.y-vec.y == 1 && Math.abs(currentLoc.x-vec.x)==1)
				{
					possibleFalls.add(vec);
					System.out.println(vec);
				}
			}
			for(Vector3 vec : adjacentMoves)
			{
				if(vec.z<lowestMoveCount)
				{
					lowestMoveCount = (int) vec.z;
					int directionX = (int) (vec.x-currentLoc.x);
					int directionY = (int) (vec.y-currentLoc.y);
					if(directionX<0)
					{
						movingLeft = true;
						movingRight = false;
					}
					else if(directionX>0)
					{
						movingRight = true;
						movingLeft = false;
					}
					
					if(directionY>0)
					{
						if(!isJumping)
						{
							Jump();
						}
					}
				}
			}
			boolean pathIndicatesJump = false;
			for(Vector3 fall : possibleFalls)
			{
				if(fall.z<lowestMoveCount)
				{
					pathIndicatesJump = false;
					break;
				}
				else if(fall.z >= lowestMoveCount)
				{
					pathIndicatesJump = true;
				}
			}
			if(pathIndicatesJump && !isJumping)
			{
				Jump();
			}
		}
		else
		{
			movingLeft = false;
			movingRight = false;
		}
		if(!target.alive)
		{
			movingLeft = false;
			movingRight = false;
		}
		
	}
	
	/*
	 * 
		if(target != null)
		{
			if(target.rect.x < this.rect.x && Math.abs(target.rect.x - this.rect.x) < 450 && Math.abs(target.rect.y - this.rect.y) < 300)
			{
				this.movingLeft = true;
				this.movingRight = false;
			}
			else if (target.rect.x > this.rect.x && Math.abs(target.rect.x - this.rect.x) < 450 && Math.abs(target.rect.y - this.rect.y) < 300)
			{
				this.movingRight = true;
				this.movingLeft = false;
			}
			if(movingRight && this.rect.x + this.velX >= target.rect.x)
			{
				this.rect.x = target.rect.x;
				this.movingLeft = false;
				this.movingRight = false;
			}
			if(movingLeft && this.rect.x - this.velX <= target.rect.x)
			{
				this.rect.x = target.rect.x;
				this.movingLeft = false;
				this.movingRight = false;
			}
		}
		if(movingLeft)
    	{
    	 	checkBotLeft(mapTiles);
    	}
    	else if(movingRight)
    	{
    		checkBotRight(mapTiles);
    	}
	}

	 * 
	 */
}