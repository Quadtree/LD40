package info.quadtree.ld40.info.quadtree.ld40.level;

import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.info.quadtree.ld40.actor.*;

public class Level1 extends BaseLevel {
    @Override
    public void init() {
        super.init();

        for (int i=20;i<getLevelLength();i += 7){
            if (i < 80)
                LD40.s.cgs.addActor(new Cargo("panel1", i, 10, 1, 1));
            else
                LD40.s.cgs.addActor(new LongCargo(i, 20));
        }

        generateRandomMountains(1);
    }

    @Override
    public String getName() {
        return "Level 1";
    }

    @Override
    public float getLevelLength() {
        return 130f;
    }

    @Override
    public BaseLevel makeNextLevel() {
        return new Level2();
    }
}
