package com.hanuman;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Powerup extends Collectible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static enum Type {FIRE, JUMP, INVULN}
	Type type;
	
	public Powerup(int imageIndex) {
		super(imageIndex);
		Random rand = new Random();
		switch(rand.nextInt(2)){
			case 0:{
				type = Type.FIRE;
			}
			case 1:{
				type = Type.JUMP;
			}
			case 2:{
				type = Type.INVULN;
			}
		}
	}

	public abstract void performAction();
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRect(int k, int mapSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRect(double x, double y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(SpriteBatch batch, Camera c) {
		// TODO Auto-generated method stub

	}

}
