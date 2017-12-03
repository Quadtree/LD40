package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import info.quadtree.ld40.Util;
import javafx.scene.Scene;

public class HillChunk extends Scenery {
    int chunks;

    public HillChunk(Vector2 pos, int chunks, float angle) {
        super();
        this.chunks = chunks;
        createBody(pos);

        body.setTransform(pos, MathUtils.degreesToRadians * angle);
    }

    @Override
    protected Vector2 getSize() {
        return new Vector2(chunks * 64 / 30f, 800f / 30f);
    }

    @Override
    protected String getSpriteName() {
        return "ground_tall";
    }

    @Override
    protected Shape createShape() {
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(getSize().x / 2, getSize().y / 2);

        return ps;
    }

    @Override
    public void render() {
        for (int i=0;i<chunks;++i){
            Util.drawOnBody(body, getSpriteName(), 64 / 30f, getSize().y, -getSize().x / 2 + (i + 0.5f) * 64f / 30f, 0, 0);
        }
    }
}
