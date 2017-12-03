package info.quadtree.ld40.info.quadtree.ld40.level;

import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.info.quadtree.ld40.actor.Crate;
import info.quadtree.ld40.info.quadtree.ld40.actor.HillChunk;
import info.quadtree.ld40.info.quadtree.ld40.actor.Pipe;
import info.quadtree.ld40.info.quadtree.ld40.actor.Rock;

import java.util.Random;

public class Level3 extends BaseLevel {
    @Override
    public void init() {
        super.init();

        /*for (int i=25;i<90;i += 25){
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
        }*/



        LD40.s.cgs.addActor(new HillChunk(new Vector2(60f, -6), 40, 10));
        LD40.s.cgs.addActor(new HillChunk(new Vector2(120f, 10), 60, 22));

        for (int i=25;i<130;i += 12){
            LD40.s.cgs.addActor(new Crate(i, LD40.s.cgs.getTerrainHeightAt(i)+15));
        }

        generateRandomMountains(3);
    }

    @Override
    public String getName() {
        return "Level 3";
    }

    @Override
    public float getLevelLength() {
        return 150f;
    }
}
