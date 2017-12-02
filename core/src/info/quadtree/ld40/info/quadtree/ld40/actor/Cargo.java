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

    boolean markForDestroy = false;
    boolean destroyed = false;

    public Cargo(String spriteName, float x, float y, float width, float height){
        this.spriteName = spriteName;
        this.width = width;
        this.height = height;

        body = Util.createBodyOfType(BodyDef.BodyType.KinematicBody);
        body.setTransform(x, y, 0);
        body.setUserData(this);

        addFixtures();

        LD40.s.cgs.addActor(new CargoPole(new Vector2(x + width / 2, y + width / 2)));
    }

    public void destroy(){
        markForDestroy = true;
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
        return !destroyed;
    }

    @Override
    public void update() {
        super.update();

        for (Actor a : LD40.s.cgs.actors){
            if (a instanceof PlayerTruck){
                if (Math.abs(a.getPosition().x - this.getPosition().x) < 5){
                    body.setType(BodyDef.BodyType.DynamicBody);
                }
            }
        }

        if (markForDestroy && !destroyed){
            LD40.s.cgs.world.destroyBody(body);
            body = null;
            destroyed = true;
        }
    }

    @Override
    public void render() {
        super.render();

        if (body != null) Util.drawOnBody(body, spriteName, width, height);
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition().cpy();
    }
}
