package com.hanuman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Element
{
	AugmentedRectangle getRect();
    public void draw(SpriteBatch batch, Camera cam);
    public void draw(SpriteBatch batch);
    public void setRect(int k, int mapsize); //used for setting with the index k from the map
    public void setRect(double x, double y); //used for setting with specific game world coordinates
    public abstract TileEditor.Elem getType();
    public abstract void applyEditorScalar();
	public void undoEditorScalar();
    public abstract void applyElementTransform();
    //public void editFieldInfo();
}