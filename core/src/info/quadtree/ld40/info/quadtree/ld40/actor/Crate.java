package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.graphics.Color;

public class Crate extends Cargo {
    public Crate(float x, float y) {
        super("crate", x, y, 1, 1);
    }

    @Override
    public Color getDebrisColor() {
        return new Color(0xa07b33ff);
    }

    @Override
    public float getDensity() {
        return 0.5f;
    }
}
