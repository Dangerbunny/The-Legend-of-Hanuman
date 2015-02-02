package com.hanuman;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public abstract class Tile implements Element, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Sprite sprite;
	AugmentedRectangle rect = new AugmentedRectangle();
	int velX, velY, personalImgIndex;

	public Tile(int imageIndex)
    {
		personalImgIndex = imageIndex;
    	setSprite(personalImgIndex);
    }
	protected void setSprite(int index)
	{
		sprite = new Sprite(HanuGame.regions.get(index));
	}
    private void writeObject(ObjectOutputStream out) throws IOException
    {
    	out.defaultWriteObject();
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
    	in.defaultReadObject();
    	setSprite(personalImgIndex);
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
    public AugmentedRectangle getRect()
	{
		return rect;
	}
    public TileEditor.Elem getType()
    {
    	return null;
    }
    public void setRect(int k, int j)
    {
    	final int rectWidth = 90;
    	final int rectHeight = 60;
    	rect = new AugmentedRectangle(k*90, j*60, rectWidth, rectHeight);
    }
    public void setRect(double x, double y)
    {
    	final int rectWidth = 90;
    	final int rectHeight = 60;
    	rect = new AugmentedRectangle((int)x, (int)y, rectWidth, rectHeight);
    }
    public void effect(Unit unit1)
    {
    }
    public void draw(SpriteBatch batch, Camera c)
    {
    	batch.draw(sprite, (int)(rect.x - c.xOffset), (int)rect.y - c.yOffset, (int)rect.width, (int)rect.height);
    	//g.drawImage(sprite, (int)(rect.x - c.xOffset), (int)rect.y);
    }
    public void draw(SpriteBatch batch)
    {
    	batch.draw(sprite, (int)(rect.x), (int)rect.y, (int)rect.width, (int)rect.height);
    }

}