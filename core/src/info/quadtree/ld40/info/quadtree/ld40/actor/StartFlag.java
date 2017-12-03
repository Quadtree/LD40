package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class StartFlag extends Flag {
    public StartFlag(Vector2 pos) {
        super(pos);
    }

    @Override
    protected Color getColor() {
        return Color.RED;
    }

    @Override
    float invisibileWallOffset() {
        return -10f;
    }
}
