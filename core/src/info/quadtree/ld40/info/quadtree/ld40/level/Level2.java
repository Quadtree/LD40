package info.quadtree.ld40.info.quadtree.ld40.level;

import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.info.quadtree.ld40.actor.*;

import java.util.Random;

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

        Random rand = new Random(388394);

        for (int i=80;i<130;i += rand.nextInt(10)){
            LD40.s.cgs.addActor(new Rock(new Vector2(i, 1)));
        }

        generateRandomMountains(2);
    }

    @Override
    public String getName() {
        return "Level 2";
    }

    @Override
    public float getLevelLength() {
        return 150f;
    }
}
