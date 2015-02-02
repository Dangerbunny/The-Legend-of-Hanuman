package com.hanuman;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Unit implements Element, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean alive = true;
	boolean gravity = true;
	int health, speed, personalMovingImgIndex, personalIdleImgIndex;
	double velX = 0, velY = 0;
	boolean isJumping, movingLeft, movingRight, sprinting;
	AugmentedRectangle rect = new AugmentedRectangle();
	int i = 0, count = 1;
	int idleCount = 1, idleI = 0;// these are used for the animation of the images, changing the image when i changes, and changing i every x amount of count changing.
	double sprintCount = 1;

	Sprite spriteRight;
	Sprite idleRight;
	boolean focused = false;
	boolean nextLeft; // used only for the patrollerEnemy
	static Texture[] unitImages;
	Sprite[][] RightRegis, idleRightregis;
	protected boolean facingRight = false;
	protected boolean facingLeft = true;
	public boolean isHaxin = false;

	/*public void editFieldInfo()
	{
		System.out.println("Choose a field to edit: 1) Direction Facing 2) Movement Speed");
		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();
		if (i == 1)
		{
			System.out.println("Facing left (1) or right (2)");
			int k = sc.nextInt();
			if(k == 1)
			{
				facingLeft = true;
				facingRight = false;
			}
			else if(k==2)
			{
				facingRight = true;
				facingLeft = false;
			}
		}
		else if (i==2)
		{
			System.out.println("Enter")
		}
		
	}*/
    public Unit(int hp, int speed, int spriteRightIndex, int idleRightIndex)
    {
    	health = hp; //health has nothing being done with it at the moment
    	this.speed = speed;
    	personalMovingImgIndex = spriteRightIndex;
    	personalIdleImgIndex = idleRightIndex;
    	setSprites(personalMovingImgIndex, personalIdleImgIndex);
    	
    	isJumping = false;
		
    }
    protected void setSprites(int indexMoving, int indexIdle)
	{
		spriteRight = new Sprite(HanuGame.regions.get(indexMoving));
		idleRight = new Sprite(HanuGame.regions.get(indexIdle));
	}
    private void writeObject(ObjectOutputStream out) throws IOException
    {
    	out.defaultWriteObject();
    	//out.writeInt(speed);
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
    	in.defaultReadObject();
    	setSprites(personalMovingImgIndex,personalIdleImgIndex);
    	if(!HanuGame.inLevel)
    		setRect(rect.x,rect.y);
    	else if(HanuGame.inLevel)
    		setRect((int)(rect.x/90),(int)(rect.y/60));
    }
    public void flipOrientation()
    {
    	facingRight = !facingRight;
    	facingLeft = !facingLeft;
    }
    public TileEditor.Elem getType()
    {
    	return null;
    }
    public AugmentedRectangle getRect()
	{
		return rect;
	}
    public void Jump()
	{
		velY = 20.0;
		isJumping = true;
	}
	public void Jump(int amount)
	{
		velY = amount;
		isJumping = true;
	}
	public void applyEditorScalar()
	{
		rect.width *= Constants.editorWidthScalar;
		rect.x *= Constants.editorWidthScalar;
	}
	public void undoEditorScalar()
	{
		rect.width /= Constants.editorWidthScalar;
		rect.x /= Constants.editorWidthScalar;
	}
	public void applyElementTransform() //used if this is to be an element to be selected from in the editor
	{
		rect.width = 90*Constants.editorWidthScalar;
		rect.x *= Constants.editorWidthScalar;
		rect.height = 60;
	}
	
	private void checkFall(Tile[][] mapTiles)
	{
		Rectangle2D.Double fallcheck = new Rectangle2D.Double(rect.x  , rect.y-1, rect.width, rect.height);
		for(Tile[] tile : mapTiles)
		{
			for(Tile t : tile)
			{
				if(t != null)
				{
					if(fallcheck.intersects(t.rect))
					{
						if(t instanceof MovingLedge)
						{
							velX += t.velX;
						}

						t.effect(this);

						return;
					}
				}
			}
		}
		isJumping = true;
	}
    public void update(int mapWidth, Tile[][] mapTiles)
	{
		rect.left = rect.x;
    	rect.right = rect.x + rect.width;
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
		if(isHaxin)
		{
			velX*=2;
		}

		/*if(sprintCount >= 10)
		{
			sprinting = false;
		}
		if(sprinting)
		{								No Sprinting for this game but we'll leave in support just in case
			velX *= 1.5;
			sprintCount+=.1;
		}
		if(!sprinting && sprintCount > 1)
		{
			sprintCount-=.05;
		}*/
		if(isJumping && !isHaxin && gravity)
		{
			velY-=Constants.gravity;
		}
		if(rect.x <= 0)
		{
			rect.x = 0;
		}
		if(rect.right >= (mapWidth)*90)
		{
			rect.x = (mapWidth)*90 - rect.width;
		}
		if(rect.y < 0 || health <= 0)
		{
			alive = false;
			movingLeft = false;
			movingRight = false;
		}
		if(rect.y > mapTiles[0].length*60)
		{
			rect.y = mapTiles[0].length*60;
		}
		if(!isHaxin)
		{
			checkFall(mapTiles);
		}
		rect.x += velX;
		rect.y += velY;
	}
    public void setRect(int k, int j) //this takes indices on a map and sets the rect from them (Used for in game)
    {
    	final int rectWidth = 70;
    	final int rectHeight = 70;
    	//rect = new AugmentedRectangle(k%(mapSize/10) * 90, k/(mapSize/10) * 60, rectWidth, rectHeight);
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);

    }
    public abstract void setRect(double x, double y); //this takes real world space coordinates (Used in editor)
    public void draw(SpriteBatch batch, Camera cam)
	{
    	//System.out.println(cam.yOffset);
		if(i >= 10)
		{
			i = 0;
		}
		if(idleI >= 10)
		{
			idleI = 0;
		}
		if(movingRight)
		{
			/*g.drawImage(spriteRight, (int)rect.x - cam.xOffset, (int)rect.y,
									 (int)rect.x + (int)rect.width - cam.xOffset, (int)rect.y + (int)rect.height,
									 (int)findRectWidth*i, 0, (int)findRectWidth + (int)findRectWidth*i, (int)spriteRight.getHeight());*/
			batch.draw(RightRegis[0][i], (int)rect.x - cam.xOffset, (int)rect.y - cam.yOffset, (int)rect.width, (int)rect.height);
			facingRight = true;
			facingLeft = false;
		}
		else if (movingLeft)
		{
			/*g.drawImage(spriteLeft, (int)rect.x - cam.xOffset, (int)rect.y,
									 (int)rect.x + (int)rect.width - cam.xOffset, (int)rect.y + (int)rect.height,
									 (int)findRectWidth*i, 0, (int)findRectWidth + (int)findRectWidth*i, (int)spriteLeft.getHeight());*/
			batch.draw(RightRegis[0][i],(int)rect.x+45 - cam.xOffset, (int)rect.y - cam.yOffset, (int)-rect.width, (int)rect.height);
			facingLeft = true;
			facingRight = false;
		}
		else
		{
			/*g.drawImage(currentIdle, (int)rect.x - cam.xOffset, (int)rect.y,
									 (int)rect.x + (int)rect.width - cam.xOffset, (int)rect.y + (int)rect.height,
									 (int)findRectWidth*i, 0, (int)findRectWidth + (int)findRectWidth*i, (int)currentIdle.getHeight());*/
			if(facingLeft)
				batch.draw(idleRightregis[0][idleI], (int)rect.x+45 - cam.xOffset, (int)rect.y - cam.yOffset, -(int)rect.width, (int)rect.height);
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
    	//System.out.println(cam.yOffset);
		if(i >= 10)
		{
			i = 0;
		}
		if(idleI >= 10)
		{
			idleI = 0;
		}
		if(movingRight)
		{
			/*g.drawImage(spriteRight, (int)rect.x - cam.xOffset, (int)rect.y,
									 (int)rect.x + (int)rect.width - cam.xOffset, (int)rect.y + (int)rect.height,
									 (int)findRectWidth*i, 0, (int)findRectWidth + (int)findRectWidth*i, (int)spriteRight.getHeight());*/
			batch.draw(RightRegis[0][i], (int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
			facingRight = true;
			facingLeft = false;
		}
		else if (movingLeft)
		{
			/*g.drawImage(spriteLeft, (int)rect.x - cam.xOffset, (int)rect.y,
									 (int)rect.x + (int)rect.width - cam.xOffset, (int)rect.y + (int)rect.height,
									 (int)findRectWidth*i, 0, (int)findRectWidth + (int)findRectWidth*i, (int)spriteLeft.getHeight());*/
			batch.draw(RightRegis[0][i],(int)rect.x+45, (int)rect.y, (int)-rect.width, (int)rect.height);
			facingLeft = true;
			facingRight = false;
		}
		else
		{
			/*g.drawImage(currentIdle, (int)rect.x - cam.xOffset, (int)rect.y,
									 (int)rect.x + (int)rect.width - cam.xOffset, (int)rect.y + (int)rect.height,
									 (int)findRectWidth*i, 0, (int)findRectWidth + (int)findRectWidth*i, (int)currentIdle.getHeight());*/
			if(facingLeft)
				batch.draw(idleRightregis[0][idleI], (int)rect.x+45, (int)rect.y, -(int)rect.width, (int)rect.height);
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