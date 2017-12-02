package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;

public class Flag extends Actor {
    Vector2 pos;

    public Flag(Vector2 pos) {
        this.pos = pos;
    }

    protected Color getColor(){
        return Color.WHITE;
    }

    @Override
    public void render() {
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
}
