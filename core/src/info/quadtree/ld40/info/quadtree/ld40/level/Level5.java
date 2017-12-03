package info.quadtree.ld40.info.quadtree.ld40.level;

import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.info.quadtree.ld40.actor.Crate;
import info.quadtree.ld40.info.quadtree.ld40.actor.HillChunk;
import info.quadtree.ld40.info.quadtree.ld40.actor.LongCargo;

public class Level5 extends BaseLevel {
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




        LD40.s.cgs.addActor(new HillChunk(new Vector2(-20f, 20), 60, -10));
        LD40.s.cgs.addActor(new HillChunk(new Vector2(79.9f, -6.35f), 40, -22));
        LD40.s.cgs.addActor(new HillChunk(new Vector2(160f, -11), 25, 10));

        for (int i=25;i<200;i += 12){
            LD40.s.cgs.addActor(new Crate(i, LD40.s.cgs.getTerrainHeightAt(i)+15));
        }
        for (int i=200;i<250;i += 12){
            LD40.s.cgs.addActor(new Crate(i, LD40.s.cgs.getTerrainHeightAt(i)+22));
        }
        for (int i=120;i<140;i += 9){
            LD40.s.cgs.addActor(new LongCargo(i, LD40.s.cgs.getTerrainHeightAt(i)+22));
        }

        generateRandomMountains(5);
    }

    @Override
    public String getName() {
        return "Level 5";
    }

    @Override
    public float getLevelLength() {
        return 250f;
    }

    @Override
    public boolean playerHasTrailer() {
        return true;
    }

    @Override
    public BaseLevel makeNextLevel() {
        return null;
    }
}
