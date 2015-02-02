package com.hanuman;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cutscene 
{
	int levelNumber;
	int imageNumber = 0;
	private float internalTimer = 0.0f;
	Music music;
	ArrayList<Sprite> sceneImages;
	ArrayList<Float> sceneTransitionTimers;
	public boolean isActive;
	
	public Cutscene(int levelNumber, Music music, ArrayList<Sprite> sceneImages, ArrayList<Float> sceneTransitionTimers,
			boolean isActive)
	{
		this.levelNumber = levelNumber;
		this.music = music;
		this.sceneImages = sceneImages;
		this.sceneTransitionTimers = sceneTransitionTimers;
		this.isActive = isActive;
	}
	public void playScene()
	{
		isActive = true;
		music.setLooping(true);
		music.play();
	}
	private void endScene()
	{
		isActive = false;
		music.stop();
		//music.dispose();
		
	}
	public void draw(SpriteBatch batch)
	{
		advanceToNextImage();
		sceneImages.get(imageNumber).draw(batch);
	}
	private void advanceToNextImage()
	{
		if(internalTimer >= sceneTransitionTimers.get(imageNumber))
		{
			if(imageNumber >= sceneImages.size())
				endScene();
			else
			{
				internalTimer = 0;
				imageNumber++;
			}
		}
	}
	private void writeObject(ObjectOutputStream obj) throws IOException
	{
		obj.defaultWriteObject();
	}
	private void readObject(ObjectInputStream inp) throws IOException, ClassNotFoundException
	{
		inp.defaultReadObject();
	}
}