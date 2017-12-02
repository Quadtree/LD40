package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;

public class CargoPole extends Actor {
    private Vector2 pos;

    public CargoPole(Vector2 pos) {
        this.pos = pos.cpy();
    }

    @Override
    public void renderMidground() {
        super.renderMidground();

        LD40.s.batch.draw(LD40.s.getSprite("solidpole"), pos.x, -5, 0.15f, pos.y + 5);
    }
}
