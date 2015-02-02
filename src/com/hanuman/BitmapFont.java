package com.hanuman;

import java.io.Serializable;

import com.badlogic.gdx.files.FileHandle;

public class BitmapFont extends com.badlogic.gdx.graphics.g2d.BitmapFont
		implements Serializable {

	public BitmapFont(FileHandle internal, FileHandle internal2, boolean b) {
		super(internal,internal2,b);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
