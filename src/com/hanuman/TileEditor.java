package com.hanuman;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class TileEditor implements Serializable
{
	public static enum Elem {LEDGE,GROUND,MOVELEDGE,GATE,GEM,PLAYER,
							 FOLL,PAT,SPIKE, SPIKE2, GUNMAN, BOSS, 
							 BULLET, KEY, CANNON, LAUNCH, FISH,STICKY, CHECK, TEXT, FALSELEDGE};
	private static final long serialVersionUID = 1L;
	int width, height;
	float miniViewWidthScale, miniViewHeightScale;
	Texture grid;
	Element[][] map, shallowMapCopy;
	Element[] elements;
	Camera cam, cam2;
	Elem active;
	boolean deleting = false, showMiniMap = false, flipping = false;
	String mapName = "";
	String nextMapName = "";
	//ArrayList<String> textObjectContents;
	BitmapFont textFont;
	Text helpText;

    public TileEditor(Rectangle viewPortRect, Texture grid, int width, int height, Sound gemSound1, BitmapFont font)
    {
    	helpText = new Text(font, "HAI!", 27);
    	//helpText.setRect(450,300);
    	miniViewWidthScale = 1f/(width/10f);
    	miniViewHeightScale = 1f/(height/10f);
    	elements = new Element[21];
    	map = new Element[width][height];
		elements[0] = new Ledge();
		elements[1] = new Ground();
		elements[2] = new MovingLedge(2);
		elements[3] = new Gate(gemSound1);
		elements[4] = new Gem(gemSound1, 1);
		elements[5] = new Player(20);
		elements[6]= new FollowerEnemy(1);
	    elements[7] = new PatrollerEnemy(1);
	    elements[8] = new Spike();
	    elements[9] = new Spike2();
	    elements[10] = new Gunman(1);
	    //elements[11] = new Bullet(1,true);
	    elements[11] = new Key(gemSound1);
	    elements[12] = new Cannon(1);
	    elements[13] = new Launch();
	    elements[14] = new Boss(20);
	    elements[15] = new fish(1);
	    elements[16] = new Sticky();
	    elements[17] = new Checkpoint(gemSound1);
	    elements[18] = new Text(font, "Text");
	    elements[19] = new FalseLedge();
	    elements[20] = helpText;
	    textFont = font;

	    this.grid = grid;
	    cam = new Camera(width*90, height*60);
    	cam2 = new Camera(width*90, height*60);
    	this.width = width;
    	this.height = height;
    	for(int i = 0; i < elements.length; i++)
    	{
    		if(i!=elements.length-1)
    			elements[i].setRect(900 + 90*(i / 8), 540-60.0*(i % 8));
    		else
    			elements[i].setRect(1260.00, 540.00);
    		elements[i].applyElementTransform();
    	}
    	//elements[20].setRect(900+90, 540);
		//elements[20].applyElementTransform();
    	
    }

    public void quickFixEdit()
    {
    	miniViewWidthScale = 1f/(width/10f);
    	miniViewHeightScale = 1f/(height/10f);
    }
    public void edit(int levelNumber) throws IOException
    {
    	FileInputStream fis = null;
    	ObjectInputStream obs = null;
    	try 
    	{
			fis = new FileInputStream("Maps/Level" + levelNumber + "Map");
			obs = new ObjectInputStream(fis);
			map = (Element[][])obs.readObject();
			for(Element[] ele : map)
	    	{
	    		for(Element e : ele)
	    		{
	    			if(e!=null)
	    			e.applyEditorScalar();
	    		}
	    	}
			width = map.length;
			height = map[0].length;
			miniViewWidthScale = 1f/(width/10f);
	    	miniViewHeightScale = 1f/(height/10f);
		} 
    	
    	catch (ClassNotFoundException ex) 
    	{
			ex.printStackTrace();
		}
    	
        cam = new Camera(width*90, height*60);
        cam2 = new Camera(width*90, height*60);
        
    }
    private void openMap(String str)
    {
        for(int k = 0; k < map[0].length; k++)
        {
            for(int i = 0; i < map.length; i++)
            {
                char ind = str.charAt(i+k*width);
                if(ind == '=')
                {
                    map[i][(k-(height-1))*-1] = new Ledge();
                }
                else if(ind == '.')
                {
                    map[i][(k-(height-1))*-1] = new Ground();
                }
                else if (ind == '+')
                {
                    MovingLedge e = (MovingLedge)elements[2];
                    map[i][(k-(height-1))*-1] = new MovingLedge(e.getMoveLength());
                }
                else if (ind == 's')
                {
                    map[i][(k-(height-1))*-1] = new Spike();
                }
                else if(ind == 'p')
                {
                    PatrollerEnemy e = (PatrollerEnemy)elements[7];
                    map[i][(k-(height-1))*-1] = new PatrollerEnemy(e.health);
                }
                else if (ind == 'B')
                {
                	Boss  e = (Boss)elements[15];
                    map[i][(k-(height-1))*-1] = new Boss(e.health);
                }
                else if(ind == 'f')
                {
                    FollowerEnemy  e= (FollowerEnemy)elements[6];
                    map[i][(k-(height-1))*-1] = new FollowerEnemy(e.health);
                }
                else if(ind == 'y')
                {
                    Player e = (Player)elements[5];
                    map[i][(k-(height-1))*-1] = new Player(e.health);
                }
                else if (ind == '-')
                {
                    map[i][(k-(height-1))*-1] = null;
                }
                else if(ind == 'g')
                {
                    Gem e = (Gem)elements[4];
                    map[i][(k-(height-1))*-1] = new Gem(e.pickupSound, 1);
                }
                else if(ind == 'e')
                {
                    Gate e = (Gate)elements[3];
                    map[i][(k-(height-1))*-1] = new Gate(e.pickupSound);
                }
    			else if(ind == 'd')
    			{
    				map[i][(k-(height-1))*-1] = new Spike2();
    			}
    			else if(ind == 'q')
    			{
    				Gunman  e = (Gunman)elements[10];
    				map[i][(k-(height-1))*-1] = new Gunman(e.health);
    			}
    			else if(ind == 'c')
    			{
    				Cannon  e = (Cannon)elements[13];
    				map[i][(k-(height-1))*-1] = new Cannon(e.health);

    			}
    			else if(ind == 'b')
    			{
    				Bullet  e = (Bullet)elements[11];
    				map[i][(k-(height-1))*-1] = new Bullet(e.health, true);
    			}

    			else if(ind == 'k')
    			{
    				Key  e = (Key)elements[12];
    				map[i][(k-(height-1))*-1] = new Key(e.pickupSound);
    			}
    			else if(ind == 'n')
    			{
    				Checkpoint  e = (Checkpoint)elements[18];
    				map[i][(k-(height-1))*-1] = new Checkpoint(e.pickupSound);
    			}
    			else if(ind == 'l')
    			{
    				map[i][(k-(height-1))*-1] = new Launch();
    			}
    			else if(ind == 'z')
    			{
    				map[i][(k-(height-1))*-1] = new fish(1);
    			}
    			else if(ind == 't')
    			{
    				map[i][(k-(height-1))*-1] = new Sticky();
    			}
    			else if(ind == 'T')
    			{
    				//map[i][(k-(height-1))*-1] = new Text(textFont, "Text");
    			}
                if(map[i][(k-(height-1))*-1] != null)
                {
                	map[i][(k-(height-1))*-1].setRect((double)90*i, (double)60*(k-(height-1))*-1);
                    map[i][(k-(height-1))*-1].applyEditorScalar();
                }	
            }
        }
		//Gdx.graphics.setDisplayMode(1200,600,false);
    }
    public void save() throws IOException
    {
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    System.out.print("Please enter the level number of the map you wish to create : ");
	    int levelNumber = Integer.parseInt(in.readLine());
    	FileOutputStream fos = null;
    	ObjectOutputStream obs = null;
    	for(Element[] ele : map)
    	{
    		for(Element e : ele)
    		{
    			if(e!=null)
    			e.undoEditorScalar();
    		}
    	}
    	try
    	{
    		fos = new FileOutputStream("Maps/Level" + levelNumber + "map");
    		obs = new ObjectOutputStream(fos);
    		obs.writeObject(map);
    		obs.close();
    	}
    	catch (IOException ex)
    	{
    		
    	}
    }
    public void checkActive(Point p)
    {
    	for(Element ele : elements)
		{
			if(ele.getRect().contains(p))
			{
				active = ele.getType();
				break;
			}
		}
    }
    public void setActivePos(Point p)
    {
    	if(width>10 && !showMiniMap)
    	{
	    	if(p.getX() < 900*Constants.editorWidthScalar)
	    	{
	    		int x =(int)(p.getX()/(90*Constants.editorWidthScalar))+(int)(cam.xOffset/(90*Constants.editorWidthScalar));
	    		int y = (int)(p.getY()/60)+(int)(cam.yOffset/60);
	    		if(active != null)
	    			setSelected(x,y);

	    	}
    	}
    	else
    	{
    		if(p.getX() < width*90*Constants.editorWidthScalar && !showMiniMap)
	    	{
	    		int x =(int)(p.getX()/(90*Constants.editorWidthScalar))+(int)(cam.xOffset/(90*Constants.editorWidthScalar));
	    		int y = (int)(p.getY()/60)+(int)(cam.yOffset/60);
	    		if(active != null && map[x][y] == null)
	    			setSelected(x,y);
    		}
    	}
    }
    private void setSelected(int x, int y)
    {
		switch(active)
		{
			case LEDGE:
			{
				map[x][y] = new Ledge();
				break;
			}
			case GROUND:
			{
				map[x][y] = new Ground();
				break;
			}
			case FALSELEDGE:
			{
				map[x][y] = new FalseLedge();
				break;
			}
				case SPIKE:
			{
				map[x][y] = new Spike();
				break;
			}
			case SPIKE2:
			{
				map[x][y] = new Spike2();
				break;
			}
			case MOVELEDGE:
			{
				MovingLedge e = (MovingLedge)elements[2];
				map[x][y] = new MovingLedge(e.getMoveLength());
				break;
			}
			case GATE:
			{
				Gate e = (Gate)elements[3];
				map[x][y] = new Gate(e.pickupSound);
				break;
			}
			case GEM:
			{
				Gem e = (Gem)elements[4];
				map[x][y] = new Gem(e.pickupSound, 1);
				break;
			}
			case PLAYER:
			{
				Player e = (Player)elements[5];
				map[x][y] = new Player(e.health);
				break;
			}
			case FOLL:
			{
				FollowerEnemy  e= (FollowerEnemy)elements[6];
				map[x][y] = new FollowerEnemy(e.health);
				map[x][y].setRect((double)90*x, (double)60*y);
				break;
			}
			case GUNMAN:
			{
				Gunman  e = (Gunman)elements[10];
				map[x][y] = new Gunman(e.health, new Bullet(1,e.facingLeft));
				break;
			}
			case BOSS:
			{
				Boss e = (Boss)elements[14];
				map[x][y] = new Boss(e.health);
				break;
			}
			case CANNON:
			{
				Cannon  e = (Cannon)elements[12];
				map[x][y] = new Cannon(e.health, new Bullet(1, e.facingLeft));
				break;
			}
			/*case BULLET:
			{
				Bullet  e = (Bullet)elements[11];
				map[x][y] = new Bullet(e.health, true);
				break;
			}*/
			case PAT:
			{
				PatrollerEnemy e = (PatrollerEnemy)elements[7];
				map[x][y] = new PatrollerEnemy(e.health);
				break;
			}
			case KEY:
			{
				Key  e = (Key)elements[11];
				map[x][y] = new Key(e.pickupSound);
				break;
			}
			case CHECK:
			{
				Checkpoint  e = (Checkpoint)elements[17];
				map[x][y] = new Checkpoint(e.pickupSound);
				break;
			}
			case LAUNCH:
			{
				map[x][y] = new Launch();
				break;
			}
			case STICKY:
			{
				map[x][y] = new Sticky();
				break;
			}
			case FISH:
			{
				map[x][y] = new fish(1);
				break;
			}
			case TEXT:
			{
				map[x][y] = new Text(textFont, "Text");
				break;
			}
		}
		map[x][y].setRect((double)90*x, (double)60*y);
		map[x][y].applyEditorScalar();

    }
    public void deleteTile(Point p)
    {
    	if(p.getX() < width*(90*Constants.editorWidthScalar))
    	{
    		int x =(int)(p.getX()/(90*Constants.editorWidthScalar))+(int)(cam.xOffset/(90*Constants.editorWidthScalar));
    		int y = (int)(p.getY()/60)+(int)(cam.yOffset/60);
			if(map[x][y] != null)
			{
    			if(map[x][y].getRect().contains((double)90*x*Constants.editorWidthScalar, (double)60*y))
    			{
    				map[x][y] = null;
    			}
			}
    	}
	}
    public void showMiniMap()
    {
    	shallowMapCopy = map;
    	if(!showMiniMap)
    	{
    		for(int i = 0; i < map.length; i++)
    		{
    			for(int k = 0; k < map[i].length; k++)
    			{
    				if(map[i][k] != null)
    				{
    					shallowMapCopy[i][k].getRect().x = map[i][k].getRect().x * miniViewWidthScale;
    					shallowMapCopy[i][k].getRect().y = map[i][k].getRect().y * miniViewHeightScale;
    					shallowMapCopy[i][k].getRect().width = map[i][k].getRect().width * miniViewWidthScale;
    					shallowMapCopy[i][k].getRect().height = map[i][k].getRect().height * miniViewHeightScale;
    				}
    			}
    		}
    	}
    	showMiniMap = true;
    }
    public void closeMiniMap()
    {
    	showMiniMap = false;
    	for(int i = 0; i < map.length; i++)
		{
			for(int k = 0; k < map[i].length; k++)
			{
				if(map[i][k] != null)
				{
					shallowMapCopy[i][k].getRect().x = map[i][k].getRect().x / miniViewWidthScale;
					shallowMapCopy[i][k].getRect().y = map[i][k].getRect().y / miniViewHeightScale;
					shallowMapCopy[i][k].getRect().width = map[i][k].getRect().width / miniViewWidthScale;
					shallowMapCopy[i][k].getRect().height = map[i][k].getRect().height / miniViewHeightScale;
				}
			}
		}
    }


	public void draw(SpriteBatch batch)
	{
		
		cam.update();
		
		for(int i = 0; i < 10; i++)
		{
			batch.draw(HanuGame.regions.get(25), (int)(i*90*Constants.editorWidthScalar), 0, (int)(90*Constants.editorWidthScalar), 600);
		}
	
		for(int i = 10; i < 14; i++)
		{
			batch.draw(HanuGame.regions.get(26), (int)(i*90*Constants.editorWidthScalar), 60, (int)(90*Constants.editorWidthScalar), 540);
		}
		
		for(Element e: elements)
		{
			e.draw(batch, cam2);
		}
		
		if(!showMiniMap)
		{
			for(Element[] eArray : map)
			{
				for(Element e : eArray)
				{
					if(e != null && e.getRect().x - cam.xOffset < 900*Constants.editorWidthScalar && e.getRect().y - cam.yOffset < 600)
					{
						e.draw(batch, cam);
					}
				}
			}
		}
		else
		{
			for(Element[] eArray : shallowMapCopy)
			{
				for(Element e : eArray)
				{
					if(e != null)
					{
						e.draw(batch);
					}
				}
			}
		}
		//helpText.draw(batch);
	}

	public void flipElementOrientationOReditText(Point p) 
	{
		int x =(int)(p.getX()/(90*Constants.editorWidthScalar))+(int)(cam.xOffset/(90*Constants.editorWidthScalar));
		int y = (int)(p.getY()/60)+(int)(cam.yOffset/60);
		if(map[x][y] != null && map[x][y] instanceof Unit)
		{
			((Unit)map[x][y]).flipOrientation();
		}
		else if(map[x][y] != null && map[x][y] instanceof Text)
		{
			((Text) map[x][y]).editMessage();
		}
	}

}