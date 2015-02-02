package com.hanuman;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Level implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String map = "";
	String mapName = "";
	Tile[][] mapTiles;
	Collectible[][] collects;
	Unit[][] units;
	Rectangle bounds;
	int mapWidth, mapHeight;
	int score = 0;
	int levelNumber = 1;
	int keys = 0;
	int totalKeys = 0;
	int globalScore = 0;
	//savePoint savy;
	static Cutscene[] scenes;
	public boolean sceneMode = false, checkpointActivated;

	static Tile ledge;
    static Tile ground;
    static Collectible gem;
	Sound gemSound1;
	Camera cam;
	
    public Level(Rectangle viewPortRect, Sound gemSound1) throws IOException, ClassNotFoundException
    {
    	bounds = viewPortRect;
		this.gemSound1 = gemSound1;
		checkpointActivated = false;
		openMap(levelNumber, null);
    }
    private void popMapTiles(Checkpoint lastCheckpoint) //Obsolete as of v1.6 with the addition of Serialization
	{
		mapTiles = new Tile[mapWidth][mapHeight];
		collects = new Collectible[mapWidth][mapHeight];
		units = new Unit[mapWidth][mapHeight];
		totalKeys = 0;

		for(int j = 0; j < mapHeight; j++)
    	{
			for (int k = 0; k < mapWidth; k++)
			{
	    		char ind = map.charAt(k+j*mapWidth);
	    		System.out.print(ind);
				if(ind == '=')
				{
					mapTiles[k][(j-(mapHeight-1))*-1] = new Ledge();
					//mapTiles[k].collision = mapTiles[k].collision.Ledge;
				}
				else if(ind == '.')
				{
					mapTiles[k][(j-(mapHeight-1))*-1] = new Ground();
					//mapTiles[k].collision = mapTiles[k].collision.Impassable;
				}
				else if (ind == '+')
				{
					mapTiles[k][(j-(mapHeight-1))*-1] = new MovingLedge(2);
				}
				else if(ind == 'p')
				{
					units[k][(j-(mapHeight-1))*-1] = new PatrollerEnemy(1);
				}
				else if(ind == 'b')
				{
					units[k][(j-(mapHeight-1))*-1] = new Bullet(1,true);
					Bullet b = (Bullet)units[k][(j-(mapHeight-1))*-1];
					b.setMapWidth(mapWidth);
				}
				else if(ind == 'f')
				{
					units[k][(j-(mapHeight-1))*-1] = new FollowerEnemy(1);
				}
				else if(ind == 'q')
				{
					units[k][(j-(mapHeight-1))*-1] = new Gunman(1, (Bullet)units[k-1][(j-(mapHeight-1))*-1]);
	
				}
				else if(ind == 'c')
				{
					units[k][(j-(mapHeight-1))*-1] = new Cannon(1, (Bullet)units[k-1][(j-(mapHeight-1))*-1]);
				}
				else if (ind == 'B')
				{
					units[k][(j-(mapHeight-1))*-1] = new Boss(20);
				}
				else if(ind == 'y')
				{
					//System.out.print("k: " + k + " j: " + j);
					if(lastCheckpoint == null)
					{
						units[k][(j-(mapHeight-1))*-1] = new Player(20);
						units[k][(j-(mapHeight-1))*-1].focused = true;
						cam = new Camera(units[k][(j-(mapHeight-1))*-1], (mapWidth)*90 - 450, (mapHeight)*60 - 300);	
					}
				}
				else if (ind == '-')
				{
					mapTiles[k][(j-(mapHeight-1))*-1] = null;
				}
				else if(ind == 'g')
				{
					collects[k][(j-(mapHeight-1))*-1] = new Gem(gemSound1, 1);
				}
				else if(ind == 'e')
				{
					collects[k][(j-(mapHeight-1))*-1] = new Gate(gemSound1);
				}
				else if(ind == 'k')
				{
					totalKeys++;
					collects[k][(j-(mapHeight-1))*-1] = new Key(gemSound1);
				}
	
				else if(ind == 'n')
				{
					collects[k][(j-(mapHeight-1))*-1] = new Checkpoint(gemSound1, k, (j-(mapHeight-1))*-1);
				}
	
				else if(ind == 's')
				{
					mapTiles[k][(j-(mapHeight-1))*-1] = new Spike();
				}
				else if(ind == 'd')
				{
					mapTiles[k][(j-(mapHeight-1))*-1] = new Spike2();
				}
				else if(ind == 'l')
				{
					mapTiles[k][(j-(mapHeight-1))*-1] = new Launch();
				}
				else if(ind == 't')
				{
					mapTiles[k][(j-(mapHeight-1))*-1] = new Sticky();
				}
				else if(ind == 'z')
				{
					units[k][(j-(mapHeight-1))*-1] = new fish(1);
				}
	
				if(mapTiles[k][(j-(mapHeight-1))*-1] != null)
	    		{
					mapTiles[k][(j-(mapHeight-1))*-1].setRect(k, (j-(mapHeight-1))*-1);
	    		}
	    		if(collects[k][(j-(mapHeight-1))*-1] != null)
	    		{
	    			collects[k][(j-(mapHeight-1))*-1].setRect(k, (j-(mapHeight-1))*-1);
	    		}
	    		if(units[k][(j-(mapHeight-1))*-1] != null)
	    		{
	    			units[k][(j-(mapHeight-1))*-1].setRect(k, (j-(mapHeight-1))*-1);
	    		}
			}
			System.out.println(" ");
    	}

    	if(lastCheckpoint != null)
		{
			units[lastCheckpoint.xValue][lastCheckpoint.yValue] = new Player(20);
			units[lastCheckpoint.xValue][lastCheckpoint.yValue].focused = true;
			cam = new Camera(units[lastCheckpoint.xValue][lastCheckpoint.yValue], (mapWidth)*90 - 450, mapHeight*60 - 300);
			units[lastCheckpoint.xValue][lastCheckpoint.yValue].setRect(lastCheckpoint.xValue, lastCheckpoint.yValue);
		}

    	for(Unit[] unit : units)
    	{
    		for(Unit u : unit)
    		{
    			if(u instanceof FollowerEnemy)
        		{
        			FollowerEnemy fe = (FollowerEnemy)u;
        			fe.findPlayer(units);
        		}
        		if(u instanceof Gunman)
        		{
        			Gunman fe = (Gunman)u;
        			fe.findPlayer(units);
        		}
    		}
    	}
    	//reverseCoordinates();
	}
    private void openMap(int floo, Checkpoint check) throws IOException, ClassNotFoundException
    {
    	if(checkpointActivated)
    	{
    		FileInputStream fis = null;
        	ObjectInputStream obs = null;
        	
    		fis = new FileInputStream("Maps/Level" + levelNumber + "MapTemp");
    		obs = new ObjectInputStream(fis);
    		Level tempLevel = (Level) obs.readObject();
    		units = tempLevel.units;
    		mapTiles = tempLevel.mapTiles;
    		collects = tempLevel.collects;
    		keys = tempLevel.keys;
    		cam = tempLevel.cam;
    		obs.close();
    		for(Tile[] til : mapTiles)
    		{
    			for(Tile t : til)
    			{
    				if (t instanceof MovingLedge)
    				{
    					((MovingLedge) t).randomizeInitTime();
    				}
    			}
    		}
    	}
    	else
    	{
    		FileInputStream fis = null;
        	ObjectInputStream obs = null;
        	fis = new FileInputStream("Maps/Level" + levelNumber + "Map");
    		obs = new ObjectInputStream(fis);
    		Element[][] tempMap = (Element[][]) obs.readObject();
    		for(Element[] ele : tempMap)
    		{
    			for(Element e : ele)
    			{
    				//System.out.print(e + ", ");
    			}
    		}
    		mapWidth = tempMap.length;
    		mapHeight = tempMap[0].length;
    		mapTiles = new Tile[mapWidth][mapHeight];
    		collects = new Collectible[mapWidth][mapHeight];
    		units = new Unit[mapWidth][mapHeight];
    		totalKeys = 0;

    		for (int i = 0; i < tempMap.length; i++)
    		{
    			for(int k = 0; k < tempMap[0].length; k++)
    			{
    				if(tempMap[i][k]!=null)
    				{
						Element tempE = tempMap[i][k];
						if(tempE instanceof Unit)
						{
							units[i][k] = (Unit)tempE;
							if(tempE instanceof Player)
							{
								Player p = (Player)units[i][k];
								p.focused = true;
								cam = new Camera(p, (mapWidth)*90 - 450, (mapHeight)*60 - 300);
								//units[i][k] = p;
							}
							else if(tempE instanceof Cannon)
							{
								Cannon c = (Cannon)tempE;
								double tempX = c.rect.x, tempY = c.rect.y;
								c = new Cannon(1, new Bullet(1, c.facingLeft), c.facingLeft);
								c.bullet.setMapWidth(mapWidth);
								c.setRect(tempX,tempY);
								units[i][k] = c;
							}
							else if(tempE instanceof Gunman)
							{
								Gunman g = (Gunman)tempE;
								double tempX = g.rect.x, tempY = g.rect.y;
								g = new Gunman(1, new Bullet(1,g.facingLeft));
								g.bullet.setMapWidth(mapWidth);
								g.setRect(tempX,tempY);
								units[i][k] = g;
							}
							else if (tempE instanceof Bullet)
							{
								Bullet b = (Bullet) tempE;
								b.setMapWidth(mapWidth);
								units[i][k] = b;
							}
						}
						else if(tempE instanceof Tile)
						{
							if(tempE instanceof MovingLedge)
							{
								((MovingLedge) tempE).randomizeInitTime();
							}
							if(tempE instanceof FalseLedge)
							{
								//tempE = new FalseLedge();
							}
							mapTiles[i][k] = (Tile)tempE;

						}
						else if(tempE instanceof Collectible)
						{
							collects[i][k] = (Collectible)tempE;
						}
    				}
    			}
    		}
    		for(Unit[] uni : units)
    		{
    			for(Unit u : uni)
    			{
    				if (u instanceof FollowerEnemy)
					{
						FollowerEnemy f = (FollowerEnemy)u;
						f.findPlayer(units);
					}
    			}
    		}
    		obs.close();
    	}
    	Text.setFont(HanuGame.namefont);
		/*String mapFile = "Maps/Maps.txt";
		File maps = new File(mapFile);
		Scanner sc = null;
		String mapString = "";
		try
		{
			sc = new Scanner (maps);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Trololo ur missing a file bro!");
		}
		while (sc.hasNextLine())
		{
			mapString += sc.nextLine();
		}
		int startIndex = mapString.indexOf("" + floo);
		int endIndex = mapString.indexOf("" + (floo+1));
		if(levelNumber < 10)
		{
			map = mapString.substring(startIndex+1, endIndex);
		}
		else
		{
			map = mapString.substring(startIndex+2, endIndex);
		}
		String[] ittyBits = map.split("`");
		mapName = ittyBits[0];
		map = ittyBits[1];
		//System.out.println("mapName: " + mapName);

		mapWidth = (map.indexOf('/') + 1);
		mapHeight = (map.length()+1)/mapWidth;
		map.replace('/','-');
		popMapTiles(check);
		*/
    }
    public void nextLevel() throws IOException, ClassNotFoundException
    {
    	disposeTempMapFile();
    	levelNumber += 1;
    	checkpointActivated = false;
    	openMap(levelNumber, null);
		globalScore = score;
		//savy = null;
    }
    public void disposeTempMapFile()
    {
    	File file = new File("Maps/Level" + levelNumber + "mapTemp");
    	file.delete();
    }
    public void reset() throws IOException, ClassNotFoundException
    {
		openMap(levelNumber, null);
    }
 
    public void startCutscene()
    {
    	sceneMode = true;
    	scenes[levelNumber].isActive = true;
    	scenes[levelNumber].playScene();
    }
    public void saveToCurrentCheckpoint() throws IOException
    {
    	FileOutputStream fos = null;
    	ObjectOutputStream obs = null;
    	fos = new FileOutputStream("Maps/Level" + levelNumber + "mapTemp");
    	obs = new ObjectOutputStream(fos);
    	obs.writeObject(this);
    	obs.close();
    	
    }
    private void writeObject(ObjectOutputStream obj) throws IOException
    {
    	obj.defaultWriteObject();
    }
    private void readObject(ObjectInputStream inp) throws IOException, ClassNotFoundException
    {
    	inp.defaultReadObject();
    }
    public void draw(SpriteBatch batch)
    {
    	if(!sceneMode)
    	{
    		for(Tile[] tile : mapTiles)
        	{
        		for (Tile t : tile)
        		{
        			if(t != null)
            		{
            			t.draw(batch, cam);
            		}
        		}
        	}
        	for(Collectible[] col: collects)
        	{
        		for (Collectible c : col)
        		{
        			if(c != null && !(c instanceof Text))
            		{
            			c.draw(batch, cam);
            		}
        		}
        	}
        	for(Unit[] unit : units)
        	{
        		for (Unit u : unit)
        		{
        			if(u != null && u.alive)
            		{
            			u.draw(batch, cam);
            		}
        		}
        	}
        	for(Collectible[] col: collects)
        	{
        		for (Collectible c : col)
        		{
        			if(c != null && c instanceof Text)
            		{
            			c.draw(batch, cam);
            		}
        		}
        	}
        	
    	}
    	else
    	{
    		for(Cutscene scene : scenes)
    		{
    			if(scene.isActive)
    			{
    				scene.draw(batch);
    			}
    			else if (!scene.isActive)
    			{
    				sceneMode = false;
    			}
    		}
    	}
    }
}
