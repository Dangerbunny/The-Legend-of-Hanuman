package com.hanuman;

import java.io.Serializable;

public class Camera implements Serializable
{
	private static final long serialVersionUID = 1L;
	public int xOffset, yOffset;
	public float mapXOffset;
	Unit u;
	int mapWidth, mapHeight;
	
    public Camera(Unit u, int mapWidth, int mapHeight)
    {
    	this.u = u;
    	xOffset = 0;
    	yOffset = 0;
    	mapXOffset = 0;
    	this.mapWidth = mapWidth;
    	this.mapHeight = mapHeight;
    }
    public Camera(int mapWidth, int mapHeight) //used for the editor (not following a unit)
    {
    	xOffset = 0;
    	yOffset = 0;
    	this.mapWidth = mapWidth;
    	this.mapHeight = mapHeight;
    }
    public void update()
    {
    	if(u != null)
    	{
    		xOffset = (int)u.rect.x - 450;
    		if(u.rect.y >= 300)
    			yOffset = (int)u.rect.y - 300;
    		else
    			yOffset = 0;
    	}
    	else
    	{
    		if(xOffset > mapWidth - 900)
    		{
    			xOffset = mapWidth - 900;
    		}
    		if(yOffset > mapHeight - 600)
    		{
    			yOffset = mapHeight - 600;
    		}
    	}
    	if(xOffset < 0)
    	{
    		xOffset = 0;
    	}
    	if(yOffset < 0)
    	{
    		yOffset = 0;
    	}
    	if(xOffset > mapWidth - 450)
    	{
    		xOffset = mapWidth - 450;
    	}
    	if(yOffset > mapHeight - 300)
    	{
    		yOffset = mapHeight - 300;
    	}  
		
		//System.out.println(mapXOffset);

    	//System.out.println("MapXOffset: " + mapxOffset/1700 + " xOffset: "+ (float)xOffset/mapWidth);	
    }
    public void updateBackgroundXOffset(int backgroundWidth)
    {
    	float ratio = (float)xOffset/mapWidth;
    	mapXOffset = (backgroundWidth*ratio);
    	System.out.println(mapXOffset + "/" + backgroundWidth + " " + xOffset + "/" + mapWidth);
    	if(mapXOffset > backgroundWidth-450)
    	{
    		mapXOffset = backgroundWidth-450;
    	}
    	if(mapXOffset < 0)
    	{
    		mapXOffset = 0;
    	}
    }
}