package info.quadtree.ld40.info.quadtree.ld40.level;

import info.quadtree.ld40.LD40;
import info.quadtree.ld40.info.quadtree.ld40.actor.Cargo;
import info.quadtree.ld40.info.quadtree.ld40.actor.Crate;
import info.quadtree.ld40.info.quadtree.ld40.actor.LongCargo;
import info.quadtree.ld40.info.quadtree.ld40.actor.Pipe;

public class Level2 extends BaseLevel {
    @Override
    public void init() {
        super.init();

        for (int i=25;i<90;i += 25){
            LD40.s.cgs.addActor(new Crate(i, 18));
        }

        for (int i=20;i<90;i += 16){
            LD40.s.cgs.addActor(new Pipe(i, 20));
        }

        for (int i=100;i<130;i += 7){
            LD40.s.cgs.addActor(new Crate(i, 13));
        }

        generateRandomMountains(1);
    }

    @Override
    public String getName() {
        return "Level 2";
    }

    @Override
    float getLevelLength() {
        return 150f;
    }
}
