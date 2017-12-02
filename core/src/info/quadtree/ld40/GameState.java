package info.quadtree.ld40;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    public World world;

    List<Actor> actors = new ArrayList<Actor>();
    List<Actor> actorAddQueue = new ArrayList<Actor>();

    long msDone;

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

        msDone += 16;
    }

    public void render(){
        int updates = 0;

        while(updates < 16 && System.currentTimeMillis() > msDone){
            update();
            updates++;
        }

        for (int i=0;i<actors.size();++i){
            if (actors.get(i).keep())
                actors.get(i).render();
        }
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
        ps.setAsBox(1000, 1, new Vector2(0, 25.6f), 0);

        groundBody.createFixture(ps, 0);

        msDone = System.currentTimeMillis();

        addActor(new PlayerTruck());
    }
}
