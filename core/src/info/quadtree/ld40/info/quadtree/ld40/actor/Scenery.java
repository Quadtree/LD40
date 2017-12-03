package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import info.quadtree.ld40.Util;

public abstract class Scenery extends Actor {
    private Body body;

    protected abstract Vector2 getSize();
    protected abstract String getSpriteName();

    public Scenery(Vector2 pos) {
        body = Util.createBodyOfType(BodyDef.BodyType.StaticBody);

        FixtureDef fd = new FixtureDef();
        fd.shape = createShape();
        fd.density = 0;

        body.createFixture(fd);

        body.setTransform(pos.cpy(), 0);
    }

    protected abstract Shape createShape();

    @Override
    public Vector2 getPosition() {
        return body.getPosition().cpy();
    }

    @Override
    public void render() {
        super.render();

        Util.drawOnBody(body, getSpriteName(), getSize().x, getSize().y);
    }
}
