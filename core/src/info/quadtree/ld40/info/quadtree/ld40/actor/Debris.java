package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.Util;

public class Debris extends Actor {
    int life = 300;

    Body body;

    public Debris(Vector2 pos) {
        body = Util.createBodyOfType(BodyDef.BodyType.DynamicBody);
        body.setAngularDamping(3);

        FixtureDef fd = new FixtureDef();

        CircleShape cs = new CircleShape();
        cs.setRadius(0.15f);

        fd.shape = cs;
        fd.density = 0.1f;

        body.createFixture(fd);

        body.setTransform(pos.cpy(), MathUtils.random(0f, 6f));
        body.setLinearVelocity(MathUtils.random(-15, 15), MathUtils.random(-15, 15));
        body.setAngularVelocity(MathUtils.random(-15f, 15f));
    }

    @Override
    public boolean keep() {
        return super.keep() && body != null;
    }

    @Override
    public void update() {
        super.update();

        life--;

        if (life <= 0 && body != null){
            LD40.s.cgs.world.destroyBody(body);
            body = null;
        }
    }

    @Override
    public void render() {
        super.render();

        Util.drawOnBody(body, "debris", 16 / 30f, 16 / 30f);
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition().cpy();
    }
}
