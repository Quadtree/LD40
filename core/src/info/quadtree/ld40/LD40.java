package info.quadtree.ld40;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;
import java.util.Map;

public class LD40 extends ApplicationAdapter {
	public SpriteBatch batch;
	public Texture img;

	TextureAtlas atlas;

	public GameState cgs;



	public static LD40 s;

	Map<String, Sprite> spritePool = new HashMap<String, Sprite>();

	public Sprite getSprite(String name){
		if (!spritePool.containsKey(name)) spritePool.put(name, atlas.createSprite(name));

		return spritePool.get(name);
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		atlas = new TextureAtlas(Gdx.files.internal("default.atlas"));

		cgs = new GameState();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		cgs.render();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
