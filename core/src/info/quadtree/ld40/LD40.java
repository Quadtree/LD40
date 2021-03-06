package info.quadtree.ld40;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import info.quadtree.ld40.info.quadtree.ld40.level.*;

import java.util.HashMap;
import java.util.Map;

public class LD40 extends ApplicationAdapter {
	public SpriteBatch batch;
	public Texture img;

	TextureAtlas atlas;

	public GameState cgs;

	public BaseLevel levelToLoad = null;
	public boolean unloadCurrentLevel = false;

	public static LD40 s;

	Stage uiStage;

	Map<String, Sprite> spritePool = new HashMap<String, Sprite>();

	public Label mainMenuMessage;

	final static public boolean DEBUG_MODE = false;

	public Sprite getSprite(String name){
		if (!spritePool.containsKey(name)) spritePool.put(name, atlas.createSprite(name));

		if (!spritePool.containsKey(name) || spritePool.get(name) == null) throw new RuntimeException("Cannot find sprite " + name);

		return spritePool.get(name);
	}

	TextButton[] baseLevelButtons;
	
	@Override
	public void create () {
		for (int i=0;i<3;++i) Util.loadSound("Crush" + i);
		for (int i=0;i<4;++i) Util.loadSound("Thud" + i);

		s = this;
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		atlas = new TextureAtlas(Gdx.files.internal("default.atlas"));

		uiStage = new Stage();

        Label.LabelStyle titleLabelSkin = new Label.LabelStyle();
        titleLabelSkin.font = new BitmapFont(Gdx.files.internal("elec-90.fnt"));

        Label.LabelStyle labelSkin = new Label.LabelStyle();
        labelSkin.font = new BitmapFont(Gdx.files.internal("elec-24.fnt"));

        mainMenuMessage = new Label("", labelSkin);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("elec-24.fnt"));
        textButtonStyle.up = new NinePatchDrawable(new NinePatch(atlas.createSprite("panel1"), 6, 6, 6, 6));

		Table mainMenuTable = new Table();
		mainMenuTable.setFillParent(true);

		Label titleLabel = new Label("Oversize Load", titleLabelSkin);
		mainMenuTable.add(titleLabel).pad(40);
        mainMenuTable.row();

		Table levelSelectTable = new Table();

		BaseLevel[] levels = new BaseLevel[]{new Level1(), new Level2(), new Level3(), new Level4(), new Level5()};

        baseLevelButtons = new TextButton[levels.length];
        int i = 0;

		for (final BaseLevel bl : levels){
            TextButton tb = new TextButton("Play " + bl.getName(), textButtonStyle);

            tb.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    levelToLoad = bl;
                }
            });

            levelSelectTable.add(tb).width(200).pad(10);
            levelSelectTable.row();
            tb.setUserObject(bl);

            baseLevelButtons[i] = tb;
            i++;
        }

        determineButtonVisibility();

        mainMenuTable.add(levelSelectTable);
        mainMenuTable.row();

        mainMenuTable.add(new Label("Made by Quadtree for Ludum Dare 40", labelSkin)).expand().right().bottom().pad(10);
        mainMenuTable.row();

        Image img = new Image(new SpriteDrawable(getSprite("sky")));
        img.setFillParent(true);
        uiStage.addActor(img);

		uiStage.addActor(mainMenuTable);

		uiStage.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent){
					if (((InputEvent) event).getKeyCode() == Input.Keys.T && ((InputEvent) event).getType() == InputEvent.Type.keyDown){
						Util.takeScreenshot();
					}
					if (((InputEvent) event).getKeyCode() == Input.Keys.M && ((InputEvent) event).getType() == InputEvent.Type.keyDown){
						Util.toggleMainMusic();
					}
				}
				return false;
			}
		});

		mainMusic = Gdx.audio.newMusic(Gdx.files.internal("harmonicas.ogg"));
		mainMusic.setVolume(0.3f);
		mainMusic.setLooping(true);
		mainMusic.play();
	}

	public Music mainMusic;

    protected void determineButtonVisibility() {
        boolean isFirst = true;
        for (final TextButton tb : baseLevelButtons){
            tb.setVisible(Util.getPrefs().getBoolean(((BaseLevel)(tb.getUserObject())).getName(), false) || isFirst);
            isFirst = false;
        }
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (levelToLoad != null || unloadCurrentLevel){
            unloadCurrentLevel = false;
			if (cgs != null){
				cgs.dispose();
				cgs = null;
			}

			if (levelToLoad != null) {
			    Util.getPrefs().putBoolean(levelToLoad.getName(), true);
                Util.getPrefs().flush();
                cgs = new GameState(levelToLoad);
                cgs.init();
                levelToLoad = null;
            }
		}

		if (cgs != null) {
			cgs.render();
		} else {
		    getSprite("sky").setColor(Color.WHITE);
            determineButtonVisibility();
		    Gdx.input.setInputProcessor(uiStage);
			uiStage.act();
			uiStage.draw();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
