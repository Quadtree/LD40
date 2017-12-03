package info.quadtree.ld40;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import info.quadtree.ld40.info.quadtree.ld40.actor.*;
import info.quadtree.ld40.info.quadtree.ld40.level.BaseLevel;

import java.util.ArrayList;
import java.util.List;

public class GameState implements ContactListener {
    public World world;

    public List<Actor> actors = new ArrayList<Actor>();
    List<Actor> actorAddQueue = new ArrayList<Actor>();

    long msDone;

    float gameTime = 0;

    public PlayerTruck pc;

    public Integer finalScore = null;

    public HighScores highScores = null;

    BitmapFont defaultFont = new BitmapFont();

    public BaseLevel baseLevel;

    public float getTimeBonus(){
        float secondsOvertime = Math.max(gameTime - 30, 0);

        float bonus = 0.5f * (float)Math.pow(0.5f, secondsOvertime / 30);

        return bonus;
    }

    public GameState(BaseLevel baseLevel){
        this.baseLevel = baseLevel;
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

        if (finalScore == null) gameTime += 0.016f;
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

        for (Actor a : actors) a.renderMidground();

        final float groundBlockWidth = 64 / 30f;
        for (int i=-1000;i<1000;++i){
            LD40.s.batch.draw(LD40.s.getSprite("ground1"), groundBlockWidth*i, - groundBlockWidth + 1f, groundBlockWidth, groundBlockWidth);

        }

        for (int i=0;i<actors.size();++i){
            if (actors.get(i).keep())
                actors.get(i).render();
        }

        LD40.s.batch.end();

        LD40.s.batch.setProjectionMatrix(uiCam.combined);
        LD40.s.batch.begin();

        defaultFont.setColor(Color.WHITE);
        defaultFont.draw(LD40.s.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 768 - 40);

        if (finalScore == null) {
            defaultFont.setColor(Color.WHITE);
            defaultFont.draw(LD40.s.batch, baseLevel.getName(), 20, 768 - 20);
            defaultFont.draw(LD40.s.batch, "Time: " + Util.formatFloat(gameTime), 20, 768 - 60);
            defaultFont.draw(LD40.s.batch, "Time Bonus: +" + (int) (getTimeBonus() * 100) + "%", 20, 768 - 80);
        } else {
            int y = 700;
            defaultFont.setColor(Color.WHITE);
            defaultFont.draw(LD40.s.batch, baseLevel.getName(), 450, y -= 20);
            defaultFont.draw(LD40.s.batch, "Time: " + Util.formatFloat(gameTime), 450, y -= 20);
            defaultFont.draw(LD40.s.batch, "Time Bonus: +" + (int) (getTimeBonus() * 100) + "%", 450, y -= 20);
            defaultFont.draw(LD40.s.batch, "Score: " + finalScore, 450, y -= 20);

            if (highScores != null) {
                y -= 20;
                defaultFont.draw(LD40.s.batch, "High Score Table", 450, y -= 20);

                for (HighScoreEntry ent : highScores.HighScores){
                    defaultFont.setColor(ent.Score.equals(finalScore) ? Color.GREEN : Color.LIGHT_GRAY);
                    defaultFont.draw(LD40.s.batch, "" + ent.Score, 450, y -= 20);
                }

            }
        }

        LD40.s.batch.end();

        //Box2DDebugRenderer dbg = new Box2DDebugRenderer();
        //dbg.render(world, cam.combined);
    }

    public void dispose(){
        world.dispose();
        world = null;
    }

    public void init() {
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(this);

        // the truck will be 4m long
        // since it will be about 120 pixels long, that means 30px=1m
        // so the screen is 25.6m tall

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(1000, 1, new Vector2(0, 0), 0);
        groundBody.setUserData(this);

        groundBody.createFixture(ps, 0);

        msDone = System.currentTimeMillis();

        pc = new PlayerTruck(baseLevel.playerHasTrailer());

        addActor(pc);

        cam.setToOrtho(false, 25.6f * (1024f / 768f), 25.6f);
        cam.position.x = pc.getPosition().x;
        cam.position.y = 25.6f / 2;

        backgroundCam.setToOrtho(false, 25.6f * (1024f / 768f), 25.6f);
        backgroundCam.position.x = 25.6f * (1024f / 768f) / 2;
        backgroundCam.position.y = 25.6f / 2;

        uiCam.setToOrtho(false, 1024, 768);
        uiCam.update();

        baseLevel.init();

        for (int i=0;i<30;++i){
            world.step(0.1f, 1, 1);
        }
    }

    @Override
    public void beginContact(Contact contact) {

        Object ud1 = contact.getFixtureA().getBody().getUserData();
        Object ud2 = contact.getFixtureB().getBody().getUserData();

        if (
                (ud1 == this || ud2 == this) &&
                (ud1 instanceof Cargo || ud2 instanceof Cargo)
                ){
            if (ud1 instanceof Cargo){
                ((Cargo) ud1).destroy();
            }
            if (ud2 instanceof Cargo){
                ((Cargo) ud2).destroy();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
