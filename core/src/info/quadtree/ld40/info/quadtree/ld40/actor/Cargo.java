package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.Util;

public class Cargo extends Actor {

    String spriteName;
    float width;
    float height;

    Body body;

    public Cargo(String spriteName, float x, float y, float width, float height){
        this.spriteName = spriteName;
        this.width = width;
        this.height = height;

        body = Util.createBodyOfType(BodyDef.BodyType.KinematicBody);
        body.setTransform(x, y, 0);

        addFixtures();
    }

    protected void addFixtures(){
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width / 2, height / 2);

        body.createFixture(ps, 1f);
    }

    public float getScore(){
        return 10;
    }

    @Override
    public boolean keep() {
        return super.keep();
    }

    @Override
    public void update() {
        super.update();

        for (Actor a : LD40.s.cgs.actors){
            if (a instanceof PlayerTruck){
                if (Math.abs(a.getPosition().x - this.getPosition().x) < 8){
                    body.setType(BodyDef.BodyType.DynamicBody);
                }
            }
        }
    }

    @Override
    public void render() {
        super.render();

        Util.drawOnBody(body, spriteName, width, height);
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition().cpy();
    }
}
