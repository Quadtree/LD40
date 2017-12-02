package info.quadtree.ld40.info.quadtree.ld40.level;

import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.info.quadtree.ld40.actor.Cargo;
import info.quadtree.ld40.info.quadtree.ld40.actor.EndFlag;
import info.quadtree.ld40.info.quadtree.ld40.actor.Mountain;
import info.quadtree.ld40.info.quadtree.ld40.actor.StartFlag;

public class Level1 extends BaseLevel {
    @Override
    public void init() {
        super.init();

        LD40.s.cgs.addActor(new Cargo("panel1", 20, 20, 1, 1));
        LD40.s.cgs.addActor(new Cargo("panel1", 21, 20, 1, 1));
        LD40.s.cgs.addActor(new Cargo("panel1", 22, 20, 1, 1));
        LD40.s.cgs.addActor(new Cargo("panel1", 23, 20, 1, 1));

        LD40.s.cgs.addActor(new Mountain(new Vector2(0,0), 0.6f));
        LD40.s.cgs.addActor(new Mountain(new Vector2(20,0), 0.95f));
        LD40.s.cgs.addActor(new Mountain(new Vector2(45,0), 0.8f));

        LD40.s.cgs.addActor(new StartFlag(new Vector2(0,1.5f)));
        LD40.s.cgs.addActor(new EndFlag(new Vector2(40f,1.5f)));
    }

    @Override
    public String getName() {
        return "Level 1";
    }
}
