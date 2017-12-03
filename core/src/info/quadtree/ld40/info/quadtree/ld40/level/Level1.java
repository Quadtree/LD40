package info.quadtree.ld40.info.quadtree.ld40.level;

import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.info.quadtree.ld40.actor.*;

public class Level1 extends BaseLevel {
    @Override
    public void init() {
        super.init();

        LD40.s.cgs.addActor(new HelpMessage(new Vector2(10,7), "Use A and D or left and right arrow to move"));
        LD40.s.cgs.addActor(new HelpMessage(new Vector2(30,7), "Get as much cargo as possible to the end"));
        LD40.s.cgs.addActor(new HelpMessage(new Vector2(50,7), "Use W and S or up and down to tilt the bed"));
        LD40.s.cgs.addActor(new HelpMessage(new Vector2(80,7), "Small cargo is worth 10 each, big cargo is worth 40 each"));

        for (int i=20;i<getLevelLength();i += 7){
            if (i < 80)
                LD40.s.cgs.addActor(new Crate(i, 10));
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
