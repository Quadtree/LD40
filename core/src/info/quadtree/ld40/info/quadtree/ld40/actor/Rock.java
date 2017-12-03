package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class Rock extends Scenery {
    @Override
    protected Vector2 getSize() {
        return new Vector2(0.5f, 0.5f);
    }

    @Override
    protected String getSpriteName() {
        return "rock";
    }

    @Override
    protected Shape createShape() {
        CircleShape ret = new CircleShape();
        ret.setRadius(0.25f);

        return ret;
    }

    public Rock(Vector2 pos) {
        super(pos);
    }
}
