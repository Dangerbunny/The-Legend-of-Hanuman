package com.hanuman;

import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Text extends Collectible
{
  private static final long serialVersionUID = 1L;
  static BitmapFont font;
  String message;
  public boolean beingViewed = false;

  public Text(BitmapFont font, String message)
  {
    super(27);
    this.font = font;
    this.message = message;
    
  }
  public Text(BitmapFont font, String message, int imageIndex)
  {
    super(imageIndex);
    this.font = font;
    this.message = message;
    
  }

  public static void setFont(BitmapFont newFont) {
    font = newFont;
    //font.setScale(0.75F);
  }

  public TileEditor.Elem getType() {
    return TileEditor.Elem.TEXT;
  }

  public String toString() {
    return this.message;
  }

  public void editMessage() {
    System.out.println("Current message: " + this.message);
    System.out.print("Please enter a new message: ");
    Scanner sc = new Scanner(System.in);
    this.message = sc.nextLine();
    System.out.println("New message: " + this.message);
  }

  public void displayMessage()
  {
  }

  public void setRect(int k, int j)
  {
    int rectWidth = 60;
    int rectHeight = 60;
    this.rect = new AugmentedRectangle(k * 90, j * 60, rectWidth, rectHeight);
    TextureRegion[][] TempRegions = this.sprite.split(408, 598);
    this.regions = new Sprite[TempRegions.length][TempRegions[0].length];
    for (int i = 0; i < TempRegions.length; i++)
    {
      for (int l = 0; l < TempRegions[0].length; l++)
      {
        this.regions[i][l] = new Sprite(TempRegions[i][l]);
      }
    }
  }

  public void setRect(double x, double y)
  {
    int rectWidth = 60;
    int rectHeight = 60;
    this.rect = new AugmentedRectangle((int)x, (int)y, rectWidth, rectHeight);
    TextureRegion[][] TempRegions = this.sprite.split(408, 598);
    this.regions = new Sprite[TempRegions.length][TempRegions[0].length];
    for (int i = 0; i < TempRegions.length; i++)
    {
      for (int l = 0; l < TempRegions[0].length; l++)
      {
        this.regions[i][l] = new Sprite(TempRegions[i][l]);
      }
    }
  }

  public void draw(SpriteBatch batch, Camera c)
  {
    batch.draw(this.regions[0][0], (int)this.rect.x - c.xOffset, (int)this.rect.y - c.yOffset, (int)this.rect.width, (int)this.rect.height);
    if (this.beingViewed)
    {
      batch.draw(HanuGame.regions.get(28), 100f, 50f);
      font.drawWrapped(batch, this.message, 120.0F, 450.0F, 660f);
    }
  }

  public void draw(SpriteBatch batch) {
    batch.draw(this.regions[0][0], (int)this.rect.x, (int)this.rect.y, (int)this.rect.width, (int)this.rect.height);
    if (this.beingViewed)
    {
        font.drawWrapped(batch, this.message, 300.0F, 585.0F, 300f);
    }
  }
}