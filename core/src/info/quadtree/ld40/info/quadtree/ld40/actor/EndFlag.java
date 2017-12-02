package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class EndFlag extends Flag {
    public EndFlag(Vector2 pos) {
        super(pos);
    }

    @Override
    protected Color getColor() {
        return Color.GREEN;
    }
}
