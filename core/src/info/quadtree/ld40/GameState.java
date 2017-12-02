package info.quadtree.ld40;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import info.quadtree.ld40.info.quadtree.ld40.actor.*;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    public World world;

    public List<Actor> actors = new ArrayList<Actor>();
    List<Actor> actorAddQueue = new ArrayList<Actor>();

    long msDone;

    float gameTime = 0;

    PlayerTruck pc;

    BitmapFont defaultFont = new BitmapFont();

    float getTimeBonus(){
        float secondsOvertime = Math.max(gameTime - 30, 0);

        float bonus = 0.5f * (float)Math.pow(0.5f, secondsOvertime / 30);

        return bonus;
    }

    public GameState(){

    }

    public void addActor(Actor a){
        actorAddQueue.add(a);
    }

    public void update(){
        actors.addAll(actorAddQueue);
        actorAddQueue.clear();

        for (int i=0;i<actors.size();++i){
            if (actors.get(i).keep())
                actors.get(i).update();
            else
                actors.remove(i--);
        }

        world.step(0.016f, 4, 4);

        msDone += 16;
        gameTime += 0.016f;
    }

    OrthographicCamera cam = new OrthographicCamera();
    OrthographicCamera backgroundCam = new OrthographicCamera();
    OrthographicCamera uiCam = new OrthographicCamera();

    public void render(){
        int updates = 0;

        while(updates < 1 && System.currentTimeMillis() > msDone){
            update();
            updates++;
        }

        cam.translate((pc.getPosition().x - cam.position.x) / 20f, 0);
        cam.update();

        backgroundCam.position.x = cam.position.x / 5f;
        backgroundCam.update();

        LD40.s.batch.setProjectionMatrix(backgroundCam.combined);
        LD40.s.batch.begin();

        LD40.s.batch.draw(LD40.s.getSprite("sky"), -1000, 0, 5000, 25.6f);

        for (Actor a : actors) a.backgroundRender();
        LD40.s.batch.end();


        LD40.s.batch.setProjectionMatrix(cam.combined);
        LD40.s.batch.begin();

        final float groundBlockWidth = 64 / 30f;
        for (int i=-1000;i<1000;++i){
            LD40.s.batch.draw(LD40.s.getSprite("ground1"), groundBlockWidth*i, - groundBlockWidth + 1.5f, groundBlockWidth, groundBlockWidth);

        }

        for (int i=0;i<actors.size();++i){
            if (actors.get(i).keep())
                actors.get(i).render();
        }

        LD40.s.batch.end();

        LD40.s.batch.setProjectionMatrix(uiCam.combined);
        LD40.s.batch.begin();

        defaultFont.draw(LD40.s.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 768 - 20);
        defaultFont.draw(LD40.s.batch, String.format("Time: %.1f", gameTime), 20, 768 - 40);
        defaultFont.draw(LD40.s.batch, String.format("Time Bonus: +%d%%", (int)(getTimeBonus() * 100)), 20, 768 - 60);

        LD40.s.batch.end();
    }

    public void dispose(){
        world.dispose();
        world = null;
    }

    public void init() {
        world = new World(new Vector2(0, -9.8f), true);

        // the truck will be 4m long
        // since it will be about 120 pixels long, that means 30px=1m
        // so the screen is 25.6m tall

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(1000, 1, new Vector2(0, 0), 0);

        groundBody.createFixture(ps, 0);

        msDone = System.currentTimeMillis();

        pc = new PlayerTruck();

        addActor(pc);
        addActor(new Cargo("panel1", 20, 20, 1, 1));
        addActor(new Cargo("panel1", 21, 20, 1, 1));
        addActor(new Cargo("panel1", 22, 20, 1, 1));
        addActor(new Cargo("panel1", 23, 20, 1, 1));

        cam.setToOrtho(false, 25.6f * (1024f / 768f), 25.6f);
        cam.position.x = 25.6f * (1024f / 768f) / 2;
        cam.position.y = 25.6f / 2;

        backgroundCam.setToOrtho(false, 25.6f * (1024f / 768f), 25.6f);
        backgroundCam.position.x = 25.6f * (1024f / 768f) / 2;
        backgroundCam.position.y = 25.6f / 2;

        uiCam.setToOrtho(false, 1024, 768);
        uiCam.update();

        addActor(new Mountain(new Vector2(0,0), 0.6f));
        addActor(new Mountain(new Vector2(20,0), 0.95f));
        addActor(new Mountain(new Vector2(45,0), 0.8f));

        addActor(new StartFlag(new Vector2(0,1.5f)));
        addActor(new EndFlag(new Vector2(150f,1.5f)));
    }
}
