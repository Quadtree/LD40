package info.quadtree.ld40;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import info.quadtree.ld40.info.quadtree.ld40.actor.*;
import info.quadtree.ld40.info.quadtree.ld40.level.BaseLevel;

import java.util.ArrayList;
import java.util.List;

public class GameState implements ContactListener, InputProcessor {
    public World world;

    public List<Actor> actors = new ArrayList<Actor>();
    List<Actor> actorAddQueue = new ArrayList<Actor>();

    long msDone;

    float gameTime = 0;

    public PlayerTruck pc;

    public Integer finalScore = null;

    public HighScores highScores = null;

    BitmapFont defaultFont = null;

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

    public OrthographicCamera cam = new OrthographicCamera();
    OrthographicCamera backgroundCam = new OrthographicCamera();
    OrthographicCamera uiCam = new OrthographicCamera();

    public void render(){
        int updates = 0;

        while(updates < 1 && System.currentTimeMillis() > msDone){
            update();
            updates++;
        }

        cam.translate((pc.getPosition().x - cam.position.x) / 20f, 0);

        float camTrgY = getCamTrgY();

        cam.translate(0, (camTrgY - cam.position.y) / 20f);

        cam.update();

        backgroundCam.position.x = cam.position.x / 5f;
        backgroundCam.update();

        LD40.s.batch.setProjectionMatrix(uiCam.combined);
        LD40.s.batch.begin();

        Sprite sp = LD40.s.getSprite("sky");
        sp.setSize(1024, 768);
        sp.setPosition(0, 0);
        sp.setColor(new Color(baseLevel.getSkyBrightness(), baseLevel.getSkyBrightness(), baseLevel.getSkyBrightness(), 1f));
        sp.draw(LD40.s.batch);

        sp = LD40.s.getSprite("stars1");
        sp.setSize(1024, 768);
        sp.setPosition(0, 0);
        sp.setColor(new Color(1f, 1f, 1f, MathUtils.clamp((1 - baseLevel.getSkyBrightness()) * 2 - 0.5f, 0, 1)));
        sp.draw(LD40.s.batch);

        LD40.s.batch.end();

        LD40.s.batch.setProjectionMatrix(backgroundCam.combined);
        LD40.s.batch.begin();



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

        for (Actor a : actors) a.renderUi();

        float LINE_SPACING = 25;
        float TEXT_LEFT = 400;

        if (LD40.DEBUG_MODE) {
            defaultFont.setColor(Color.WHITE);
            defaultFont.draw(LD40.s.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 768 - LINE_SPACING * 2);
        }

        if (finalScore == null) {
            defaultFont.setColor(Color.WHITE);
            defaultFont.draw(LD40.s.batch, baseLevel.getName(), 20, 768 - LINE_SPACING);
            defaultFont.draw(LD40.s.batch, "Time: " + Util.formatFloat(gameTime), 20, 768 - LINE_SPACING*3);
            defaultFont.draw(LD40.s.batch, "Time Bonus: +" + (int) (getTimeBonus() * 100) + "%", 20, 768 - LINE_SPACING*4);
        } else {
            int y = 700;
            defaultFont.setColor(Color.WHITE);
            defaultFont.draw(LD40.s.batch, baseLevel.getName(), TEXT_LEFT, y -= LINE_SPACING);
            defaultFont.draw(LD40.s.batch, "Time: " + Util.formatFloat(gameTime), TEXT_LEFT, y -= LINE_SPACING);
            defaultFont.draw(LD40.s.batch, "Time Bonus: +" + (int) (getTimeBonus() * 100) + "%", TEXT_LEFT, y -= LINE_SPACING);
            defaultFont.draw(LD40.s.batch, "Score: " + finalScore, TEXT_LEFT, y -= LINE_SPACING);

            if (highScores != null) {
                y -= LINE_SPACING;
                defaultFont.draw(LD40.s.batch, "High Score Table", TEXT_LEFT, y -= LINE_SPACING);

                for (HighScoreEntry ent : highScores.HighScores){
                    defaultFont.setColor(ent.Score.equals(finalScore) ? Color.GREEN : Color.LIGHT_GRAY);
                    defaultFont.draw(LD40.s.batch, "" + ent.Score, TEXT_LEFT, y -= LINE_SPACING);
                }

            }

            y -= LINE_SPACING;
            defaultFont.setColor(Color.WHITE);
            if (baseLevel.makeNextLevel() != null) {
                defaultFont.draw(LD40.s.batch, "Press N for next level", TEXT_LEFT, y -= LINE_SPACING);
            } else {
                defaultFont.draw(LD40.s.batch, "The end! Press N to return to the main menu", TEXT_LEFT, y -= LINE_SPACING);
            }

            Gdx.input.setInputProcessor(this);
        }

        LD40.s.batch.end();

        //Box2DDebugRenderer dbg = new Box2DDebugRenderer();
        //dbg.render(world, cam.combined);
    }

    protected float getCamTrgY() {
        float camTrgY = 25.6f / 2;
        float pcY = pc.getPosition().y + 7f;
        if (pcY > 14f){
            camTrgY = pcY;
        }
        return camTrgY;
    }

    public void dispose(){
        world.dispose();
        world = null;
    }

    public void init() {
        defaultFont = new BitmapFont(Gdx.files.internal("elec-24.fnt"));

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

        baseLevel.init();

        LD40.s.cgs.addActor(new StartFlag(new Vector2(0,getLowTerrainHeightAt(0)-0.5f)));
        LD40.s.cgs.addActor(new EndFlag(new Vector2(baseLevel.getLevelLength(),getLowTerrainHeightAt(baseLevel.getLevelLength())-0.5f)));

        pc = new PlayerTruck(baseLevel.playerHasTrailer());
        addActor(pc);

        cam.setToOrtho(false, 25.6f * (1024f / 768f), 25.6f);


        backgroundCam.setToOrtho(false, 25.6f * (1024f / 768f), 25.6f);
        backgroundCam.position.x = 25.6f * (1024f / 768f) / 2;
        backgroundCam.position.y = 25.6f / 2;

        uiCam.setToOrtho(false, 1024, 768);
        uiCam.update();

        for (int i=0;i<30;++i){
            world.step(0.1f, 1, 1);
        }

        cam.position.x = pc.getPosition().x;
        cam.position.y = getCamTrgY();

        simStarted = true;
    }

    boolean simStarted = false;

    float height = 0;

    public float getLowTerrainHeightAt(float x){
        return Math.min(getTerrainHeightAt(x - 1), getTerrainHeightAt(x + 1));
    }

    public float getTerrainHeightAt(float x){
        world.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                height = point.y;

                return 0;
            }
        }, x, 10000, x, 0);

        return height;
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

        if (simStarted) {
            float massA = contact.getFixtureA().getBody().getMass();
            float massB = contact.getFixtureB().getBody().getMass();

            float relSpeed = contact.getFixtureA().getBody().getLinearVelocity().cpy().sub(contact.getFixtureB().getBody().getLinearVelocity()).len();

            if (massA >= 1 && massB >= 1 && relSpeed > 5)
                Util.playSound("Thump" + MathUtils.random(0, 2), MathUtils.clamp(relSpeed / 15, 0, 1));
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.N){
            LD40.s.levelToLoad = baseLevel.makeNextLevel();
            LD40.s.unloadCurrentLevel = true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
