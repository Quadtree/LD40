package info.quadtree.ld40.info.quadtree.ld40.level;

import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.info.quadtree.ld40.actor.Crate;
import info.quadtree.ld40.info.quadtree.ld40.actor.Pipe;
import info.quadtree.ld40.info.quadtree.ld40.actor.Rock;

import java.util.Random;

public class Level4 extends BaseLevel {
    @Override
    public void init() {
        super.init();

        for (int i=170;i<200;i += 16){
            LD40.s.cgs.addActor(new Pipe(i, 20));
        }

        for (int i=20;i<180;i += 5){
            LD40.s.cgs.addActor(new Crate(i, 22));
        }

        Random rand = new Random(3945845);

        for (int i=40;i<170;i += rand.nextInt(30)){
            LD40.s.cgs.addActor(new Rock(new Vector2(i, 1)));
        }

        generateRandomMountains(4);
    }

    @Override
    public String getName() {
        return "Level 4";
    }

    @Override
    public float getLevelLength() {
        return 220f;
    }

    @Override
    public boolean playerHasTrailer() {
        return true;
    }
}
