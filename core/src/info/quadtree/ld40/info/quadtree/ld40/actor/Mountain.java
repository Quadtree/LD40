package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;

public class Mountain extends Actor {
    Vector2 pos;
    float size;

    public Mountain(Vector2 pos, float size) {
        this.pos = pos.cpy();
        this.size = size;
    }

    @Override
    public void backgroundRender() {
        super.backgroundRender();

        LD40.s.batch.draw(LD40.s.getSprite("mountain2"), pos.x, pos.y, 512f / 30f * size, 512f / 30f * size);
    }
}
