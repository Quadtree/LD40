package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;

public class EndFlag extends Flag {
    public EndFlag(Vector2 pos) {
        super(pos);
    }

    @Override
    protected Color getColor() {
        return Color.GREEN;
    }

    @Override
    public void update() {
        super.update();

        if (LD40.s.cgs.pc.getPosition().x - LD40.s.cgs.pc.getLengthBehind() > pos.x){
            LD40.s.cgs.finalScore = 1f;
        }
    }
}
