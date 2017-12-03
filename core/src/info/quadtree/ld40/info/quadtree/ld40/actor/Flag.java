package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.Util;

public abstract class Flag extends Actor {
    Vector2 pos;

    public Flag(Vector2 pos) {
        this.pos = pos;

        Body invisibleWall = Util.createBodyOfType(BodyDef.BodyType.StaticBody);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(0.25f, 10000f, new Vector2(pos.x + invisibileWallOffset(), 0), 0);
        invisibleWall.createFixture(ps, 0);
    }

    protected Color getColor(){
        return Color.WHITE;
    }

    @Override
    public void renderMidground() {
        super.render();

        Sprite sp = LD40.s.getSprite("flagpole");
        sp.setPosition(pos.x, pos.y);
        sp.setSize(64f / 30f, 64f / 30f);
        sp.setColor(Color.WHITE);
        sp.draw(LD40.s.batch);

        sp = LD40.s.getSprite("flag");
        sp.setPosition(pos.x, pos.y);
        sp.setSize(64f / 30f, 64f / 30f);
        sp.setColor(getColor());
        sp.draw(LD40.s.batch);
    }

    abstract float invisibileWallOffset();
}
