package info.quadtree.ld40.info.quadtree.ld40.level;

import info.quadtree.ld40.LD40;
import info.quadtree.ld40.info.quadtree.ld40.actor.Cargo;
import info.quadtree.ld40.info.quadtree.ld40.actor.LongCargo;

public class LevelFake extends BaseLevel {
    @Override
    public void init() {
        super.init();

        generateRandomMountains(999);
    }

    @Override
    public String getName() {
        return "Level Fake";
    }

    @Override
    float getLevelLength() {
        return 20f;
    }
}
