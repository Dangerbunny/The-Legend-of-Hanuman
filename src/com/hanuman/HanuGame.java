package com.hanuman;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.tools.imagepacker.TexturePacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class HanuGame extends InputAdapter
  implements ApplicationListener
{
  Level level = null;
  Texture backgroundImage;
  Texture editorGrid;
  Sound jumpSound;
  Sound gemSound1;
  Rectangle viewPortRect;
  TileEditor editor = null;
  boolean leftKey;
  boolean rightKey;
  SpriteBatch batch;
  static BitmapFont namefont;
  static BitmapFont editorFont;
  TexturePacker packer;
  TextureAtlas atlas;
  static List<TextureAtlas.AtlasRegion> regions = new ArrayList();
  TextureAtlas.AtlasRegion[] levelBackgrounds = new TextureAtlas.AtlasRegion[4];
  ArrayList<Sprite>[] cutsceneImages;
  ArrayList<Music> sceneMusic;
  ArrayList<Float>[] sceneTimers;
  public static boolean inLevel;
  public static boolean lookingAtSign = false;
  public Music hupClip;

  public void create()
  {
    Gdx.input.setInputProcessor(this);
    this.viewPortRect = new Rectangle(0.0F, 0.0F, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.batch = new SpriteBatch();
    TexturePacker.Settings settings = new TexturePacker.Settings();
    settings.padding = 2;
    settings.maxWidth = 2048;
    settings.maxHeight = 1024;
    settings.incremental = true;
    TexturePacker.process(settings, "assets", "texturePacks");

    this.atlas = new TextureAtlas(Gdx.files.internal("texturePacks/pack"));

    namefont = new BitmapFont(Gdx.files.internal("fonts/Ravi.fnt"), Gdx.files.internal("fonts/Ravi.png"), false);
    editorFont = new BitmapFont(Gdx.files.internal("fonts/editorFont.fnt"), Gdx.files.internal("fonts/editorFont.png"), false);
    loadImages("");
  }

  private void loadImages(String add)
  {
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("canon"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("Gunman"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("GunmanIdle"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("SwdEnemyIdle"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("demon"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("demonPowerful"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("bullet2"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("MonkeySheetSmallScale"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("MediumRavanaSheet"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("fish3"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("ledge"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("MovingLedge"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("ground"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("spikeImg"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("spikeImg2"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("launch"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("sticky"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("Gate"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("OrangeSpriteSheet"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("BanannaSpriteSheet"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("StrawberrySpriteSheet"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("TomatoSpriteSheet"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("AppleSpriteSheet"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("key"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("checkpoint"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("EditorGrid"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("ElementsGrid"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("Signpost"))));
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("SignUpClose")))); //28
    regions.add(new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("FalseTileOverlay")))); //29


    this.levelBackgrounds[0] = new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("platBackground")));
    this.levelBackgrounds[1] = new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("platBackground")));
    this.levelBackgrounds[2] = new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("forestbg")));
    this.levelBackgrounds[3] = new TextureAtlas.AtlasRegion(this.atlas.findRegion(new String("coastbg")));
  }

  private void loadCutsceneContent()
  {
    this.cutsceneImages[0].add(new Sprite(this.atlas.findRegion(new String("Name of the First Cutscene Image"))));
    this.cutsceneImages[0].add(new Sprite(this.atlas.findRegion(new String("Name of the Second Cutscene Image"))));

    this.sceneMusic.add(Gdx.audio.newMusic(new FileHandle(new File("MusicFileName1"))));

    this.sceneTimers[0].add(Float.valueOf(0.0F));
    this.sceneTimers[0].add(Float.valueOf(1.2F));

    for (int i = 0; i < 4; i++)
    {
      Level.scenes[i] = new Cutscene(i, (Music)this.sceneMusic.get(i), this.cutsceneImages[i], this.sceneTimers[i], false);
    }
  }

  void sop(String s) {
    System.out.println(s);
  }

  private void checkColl() throws IOException, ClassNotFoundException
  {
    for (Tile[] tile : this.level.mapTiles)
    {
      for (Tile t : tile)
      {
        if (!(t instanceof MovingLedge))
          continue;
        MovingLedge m = (MovingLedge)t;
        AugmentedRectangle next = new AugmentedRectangle((int)(m.rect.x + m.velX), (int)(m.rect.y + m.velY), (int)m.rect.width, (int)m.rect.height);
        if ((m.rect.left >= t.rect.right) && (next.left <= t.rect.right))
        {
          m.moveLeft = false;
          m.setCounter(0);
        }
        else if ((m.rect.right <= t.rect.left) && (next.right >= t.rect.left))
        {
          m.moveLeft = true;
          m.setCounter(0);
        }
        m.update();
      }

    }

    for (Unit[] unit : this.level.units)
    {
      for (Unit u : unit)
      {
        if (u != null)
        {
          if ((u instanceof Player))
          {
            Player p = (Player)u;
            p.update(this.level.mapWidth, this.level.mapTiles, this.leftKey, this.rightKey);
          }
          else
          {
            u.update(this.level.mapWidth, this.level.mapTiles);
          }

          AugmentedRectangle next = new AugmentedRectangle((int)(u.rect.x + u.velX), (int)(u.rect.y + u.velY), (int)u.rect.width, (int)u.rect.height);
          for (Tile[] tile : this.level.mapTiles)
          {
            for (Tile t : tile)
            {
              if (t == null)
                continue;
        	  if (((u instanceof Player)) && (u.isHaxin))
              {
                return;
              }
              if (((u instanceof Player)) && ((t instanceof Deadly)) && (u.rect.intersects(t.rect)))
                u.alive = false;
              if ((u instanceof Cannon))
              {
                Cannon c = (Cannon)u;
                if (c.bullet.rect.intersects(t.rect))
                {
                  c.bullet.alive = false;
                }
              }
              else if ((u instanceof Gunman))
              {
                Gunman c = (Gunman)u;
                if (c.bullet.rect.intersects(t.rect))
                {
                  c.bullet.alive = false;
                }
              }
              if ((!next.intersects(t.rect)) || ((t instanceof Deadly)))
                continue;
              if ((u.rect.left >= t.rect.right) && (next.left <= t.rect.right))
              {
                u.velX = t.velX;
                u.rect.x = t.rect.right;
                if ((u instanceof PatrollerEnemy))
                {
                  u.movingLeft = false;
                  u.nextLeft = false;
                }
                if ((u instanceof FollowerEnemy))
                {
                  if ((u instanceof Gunman))
                  {
                    u.movingLeft = false;
                  }
                  else if (!(u instanceof Boss))
                  {
                    if (!u.isJumping) {
                     // u.Jump();
                    }
                  }
                }
              }
              if ((u.rect.right <= t.rect.left) && (next.right >= t.rect.left))
              {
                u.velX = t.velX;

                u.rect.x = (t.rect.left - u.rect.width);
                if ((u instanceof PatrollerEnemy))
                {
                  u.movingRight = false;
                  u.nextLeft = true;
                }
                if ((u instanceof FollowerEnemy))
                {
                  if ((u instanceof Gunman))
                  {
                    u.movingRight = false;
                  }
                  else if (!(u instanceof Boss))
                  {
                    if (!u.isJumping)
                      u.Jump();
                  }
                }
              }
              if ((u.rect.bottom >= t.rect.top) && (next.bottom <= t.rect.top) && (this.level.mapTiles[(int)(t.rect.x / 90.0D)][((int)(t.rect.y / 60.0D) + 1)] == null))
              {
                u.velY = 0.0D;
                u.isJumping = false;
                u.rect.y = t.rect.top;
                if ((t instanceof MovingLedge))
                {
                  break;
                }
              }
              if ((u.rect.top <= t.rect.bottom) && (next.top >= t.rect.bottom) && (this.level.mapTiles[(int)(t.rect.x / 90.0D)][((int)(t.rect.y / 60.0D) - 1)] == null))
              {
                u.velY = 0.0D;
                u.rect.y = (t.rect.bottom - u.rect.height);
                if ((t instanceof MovingLedge))
                {
                  break;
                }
              }
              if ((u.rect.left <= t.rect.right) && (u.rect.right >= t.rect.right) && ((t instanceof MovingLedge)))
              {
                u.velX = t.velX;
                u.rect.x = t.rect.right;
                for (Tile[] tileArr : this.level.mapTiles)
                {
                  for (Tile newTile : tileArr)
                  {
                    if (newTile == null)
                      continue;
                    if (!u.rect.intersects(newTile.rect))
                      continue;
                    if ((u.rect.right < newTile.rect.left) || (newTile.equals(t)))
                      continue;
                    u.alive = false;
                  }

                }

              }

              if ((u.rect.right < t.rect.left) || (u.rect.left > t.rect.left) || (!(t instanceof MovingLedge)))
                continue;
              u.velX = t.velX;
              u.rect.x = (t.rect.left - u.rect.width);
              for (Tile[] tileArr : this.level.mapTiles)
              {
                for (Tile newTile : tileArr)
                {
                  if (newTile == null)
                    continue;
                  if (!u.rect.intersects(newTile.rect))
                    continue;
                  if ((u.rect.left > t.rect.right) || (newTile.equals(t)))
                    continue;
                  u.alive = false;
                }

              }
            }

          }

          if ((u instanceof Player))
          {
            for (Unit[] uni : this.level.units)
            {
              for (Unit e : uni)
              {
                if ((!(e instanceof Enemy)) || (!e.alive))
                  continue;
                if (next.intersects(e.rect))
                {
                  u.alive = false;
                }
                if ((e instanceof Cannon))
                {
                  if (next.intersects(((Cannon)e).bullet.rect))
                    u.alive = false;
                }
                if (!(e instanceof Gunman))
                  continue;
                if (next.intersects(((Gunman)e).bullet.rect)) {
                  u.alive = false;
                }
              }
            }

            for (int i = 0; i < this.level.mapWidth; i++)
            {
              for (int k = 0; k < this.level.mapTiles[0].length; k++)
              {
                if (this.level.collects[i][k] == null)
                  continue;
                if ((this.level.collects[i][k] instanceof Text))
                {
                  ((Text)this.level.collects[i][k]).beingViewed = false;
                }
                
                if (!u.rect.intersects(this.level.collects[i][k].rect)) {
                  continue;
                }
                this.level.score += this.level.collects[i][k].pointValue;
                if(level.collects[i][k] instanceof FalseLedge)
                {
                	break;
                }
                if ((this.level.collects[i][k] instanceof Key))
                {
                  this.level.keys += 1;
                }
                if ((this.level.collects[i][k] instanceof Text))
                {
                  ((Text)this.level.collects[i][k]).beingViewed = true;
                  break;
                }

                if ((this.level.collects[i][k] instanceof Gate))
                {
                  if (this.level.levelNumber == 21)
                  {
                    loadImages("Glitch");
                  }
                  else if (this.level.levelNumber == 22)
                  {
                    loadImages("");
                  }

                  this.level.nextLevel();
                  return;
                }

                if ((this.level.collects[i][k] instanceof Checkpoint))
                {
                  if(u instanceof Player)
                  {
                	  u.rect.x = this.level.collects[i][k].rect.x;
                	  u.rect.y = this.level.collects[i][k].rect.y;
                  }
                  this.level.collects[i][k] = null;
                  this.level.saveToCurrentCheckpoint();
                  this.level.checkpointActivated = true;
                }
                if (!(this.level.collects[i][k] instanceof Gate)) {
                  this.level.collects[i][k] = null;
                }
              }
            }
          }

        }

        this.level.cam.update();
      }
    }
  }

  public boolean keyDown(int e)
  {
    int thing = e;
    if ((this.level == null) && (this.editor == null))
    {
      if (thing == 131)
      {
        System.exit(1);
      }
      if (thing == 8)
      {
    	Cannon.dontDrawBullets = false;
        Gunman.dontDrawBullets = false;
        FalseLedge.inEditor = false;
        Gdx.graphics.setDisplayMode(901, 600, false);
        inLevel = true;
        try {
          this.level = new Level(this.viewPortRect, this.gemSound1);
        }
        catch (IOException e1) {
          e1.printStackTrace();
        }
        catch (ClassNotFoundException e1) {
          e1.printStackTrace();
        }
      }
      else if (thing == 9)
      {
        int width = 0; int height = 0;

        inLevel = false;
        try
        {
          BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
          System.out.print("1 for new, 2 for existing: ");
          String st = in.readLine();
          if (st.equals("1"))
          {
            System.out.print("Please enter the width of the map to be created : ");
            width = Integer.parseInt(in.readLine());
            System.out.print("Please enter the height of the map to be created : ");
            height = Integer.parseInt(in.readLine());
          }
          this.editor = new TileEditor(this.viewPortRect, this.editorGrid, width, height, this.gemSound1, namefont);

          if (st.equals("2")) 
          {
          System.out.print("Enter level number to be edited: ");
          this.editor.edit(Integer.parseInt(in.readLine()));
          //this.editor.quickFixEdit();
          }
          Cannon.dontDrawBullets = true;
          Gunman.dontDrawBullets = true;
          FalseLedge.inEditor = true;
        }
        catch (IOException localIOException1)
        {
        }

      }

    }
    else if (this.level != null)
    {
      Player p = null;
      for (int i = 0; i < this.level.units.length; i++)
      {
        for (int k = 0; k < this.level.units[0].length; k++)
        {
          if (!(this.level.units[i][k] instanceof Player))
            continue;
          p = (Player)this.level.units[i][k];
          break;
        }
      }

      if (thing != 0)
      {
        if (p.alive)
        {
          if (thing == 21)
          {
            this.leftKey = true;
          }
          else if (thing == 22)
          {
            this.rightKey = true;
          }

          if (thing == 39)
          {
            p.alive = false;
          }
          if (thing == 43)
          {
            p.isHaxin = true;
          }
          if (thing == 44)
          {
            p.isHaxin = false;
          }
          if (thing == 49) {
            try
            {
              this.level.nextLevel();
            }
            catch (IOException e1) {
              e1.printStackTrace();
            }
            catch (ClassNotFoundException e1) {
              e1.printStackTrace();
            }
          }

          if ((!p.isHaxin) && (thing == 62))
          {
            if (!p.isJumping)
            {
              p.Jump();
            }
          }
          if ((p.isHaxin) && (thing == 20))
          {
            p.velY = -15.0D;
          }
          else if ((p.isHaxin) && (thing == 19))
          {
            p.velY = 15.0D;
          }
        }
        else
        {
          if (thing == 62) {
            try
            {
              this.level.reset();
            }
            catch (IOException e1) {
              e1.printStackTrace();
            }
            catch (ClassNotFoundException e1) {
              e1.printStackTrace();
            }
          }
          if (thing == 131)
          {
            this.level.disposeTempMapFile();
            this.level = null;
            Gdx.graphics.setDisplayMode(900, 600, false);
            //namefont.setScale(1.0F);
          }
        }
      }
    }
    else if (this.editor != null)
    {
      if (thing == 67)
      {
        try
        {
          this.editor.save();
        }
        catch (IOException Ex)
        {
          System.out.println(Ex);
        }
        this.editor = null;
      }
      if (thing == 131)
      {
        Cannon.dontDrawBullets = false;
        Gunman.dontDrawBullets = false;
        this.editor = null;
        Gdx.graphics.setDisplayMode(900, 600, false);
      }
      if (thing == 48)
      {
        this.editor.showMiniMap();
      }
      if (thing == 42)
      {
        try
        {
          BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
          System.out.print("Enter map name: ");
          this.editor.mapName = in.readLine();
        }
        catch (IOException Ex)
        {
          System.out.println(Ex);
        }
      }
      if (thing == 59)
      {
        this.editor.deleting = true;
      }

      if ((thing == 22) && (this.editor.cam.xOffset < this.editor.width * 90 * 0.6666666666666666D - 600.0D))
      {
        Camera tmp824_821 = this.editor.cam; tmp824_821.xOffset = (int)(tmp824_821.xOffset + 60.0D);
      }
      if (thing == 21)
      {
        Camera tmp850_847 = this.editor.cam; tmp850_847.xOffset = (int)(tmp850_847.xOffset - 60.0D);
      }
      if ((thing == 19) && (this.editor.cam.yOffset < this.editor.height * 60 - 600))
      {
        this.editor.cam.yOffset += 60;
      }
      if (thing == 20)
      {
        this.editor.cam.yOffset -= 60;
      }
    }
    label936: return false;
  }

  public boolean keyTyped(char e) {
    return false;
  }

  public boolean keyUp(int e) {
    int thing = e;
    if (this.level != null)
    {
      Player p = null;
      for (int i = 0; i < this.level.units.length; i++)
      {
        for (int k = 0; k < this.level.units[0].length; k++)
        {
          if (!(this.level.units[i][k] instanceof Player))
            continue;
          p = (Player)this.level.units[i][k];
          break;
        }
      }

      if (thing != 0)
      {
        if (thing == 21)
        {
          this.leftKey = false;
        }

        if (thing == 22)
        {
          this.rightKey = false;
        }

        if ((p.isHaxin) && (thing == 20))
        {
          p.velY = 0.0D;
        }
        else if ((p.isHaxin) && (thing == 19))
        {
          p.velY = 0.0D;
        }
      }
    }
    if (this.editor != null)
    {
      if (thing == 59)
      {
        this.editor.deleting = false;
      }
      if (thing == 48)
      {
        this.editor.closeMiniMap();
      }
    }
    return false;
  }

  public boolean touchDown(int x, int y, int pointer, int button) {
    if (this.editor != null)
    {
      if (button == 0)
      {
        if (!this.editor.deleting)
        {
          this.editor.checkActive(new Point(x, (y - 600) * -1));
          this.editor.setActivePos(new Point(x, (y - 600) * -1));
        }
        else
        {
          this.editor.deleteTile(new Point(x, (y - 600) * -1));
        }
      }
      else if (button == 1)
      {
        this.editor.flipElementOrientationOReditText(new Point(x, (y - 600) * -1));
      }
    }
    return false;
  }

  public boolean touchUp(int x, int y, int pointer, int button) {
    return false;
  }

  public boolean touchDragged(int x, int y, int pointer) {
    if (this.editor != null)
    {
      if (!this.editor.deleting)
      {
        this.editor.checkActive(new Point(x, (y - 600) * -1));
        this.editor.setActivePos(new Point(x, (y - 600) * -1));
      }
      else
      {
        this.editor.deleteTile(new Point(x, (y - 600) * -1));
      }
    }
    return false;
  }

  public void dispose()
  {
  }

  public void pause()
  {
  }

  public void render()
  {
    this.batch.begin();
    if (this.level != null)
    {
      if (!this.level.sceneMode)
      {
        try {
          checkColl();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
        if (this.level.levelNumber <= 3) {
          this.batch.draw(this.levelBackgrounds[this.level.levelNumber], -this.level.cam.mapXOffset, 0.0F);
        }
        else {
          this.batch.draw(this.levelBackgrounds[0], -this.level.cam.mapXOffset, 0.0F);
        }
      }
      this.level.draw(this.batch);
    }
    else if (this.editor != null)
    {
      Gdx.gl.glClear(16384);
      this.editor.draw(this.batch);
      editorFont.draw(this.batch, "Viewing X tiles: " + (int)(this.editor.cam.xOffset / 60.0D) + 
        "-" + 
        (int)((this.editor.cam.xOffset + 600.0D) / 60.0D) + " /" + this.editor.width, 
        650.0F, 50.0F);
      editorFont.draw(this.batch, "Viewing Y tiles: " + this.editor.cam.yOffset / 60 + "-" + (this.editor.cam.yOffset + 600) / 60 + " /" + this.editor.height, 650.0F, 25.0F);
    }
    else
    {
      this.batch.draw(this.levelBackgrounds[0], 0.0F, 0.0F);
      namefont.draw(this.batch, "Press One to play game.", 195.0F, this.viewPortRect.height / 2.0F + 100.0F);
      namefont.draw(this.batch, "Press Two to access the Editor", 125.0F, this.viewPortRect.height / 2.0F);
    }
    this.batch.end();
  }

  public void resize(int arg0, int arg1)
  {
  }

  public void resume()
  {
  }
}