package info.quadtree.ld40.info.quadtree.ld40.level;

import com.badlogic.gdx.math.Vector2;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.info.quadtree.ld40.actor.EndFlag;
import info.quadtree.ld40.info.quadtree.ld40.actor.Mountain;
import info.quadtree.ld40.info.quadtree.ld40.actor.StartFlag;

import java.util.Random;

public abstract class BaseLevel {
    public void init(){


    }
    public String getName(){ return "BaseLevel"; }

    void generateRandomMountains(int seed){
        float levelLength = getLevelLength();
        Random rand = new Random(seed);

        int num = rand.nextInt((int)levelLength / 6);

        for (int i=0;i<num;++i) {
            LD40.s.cgs.addActor(new Mountain(new Vector2(rand.nextFloat() * levelLength, 0), rand.nextFloat()));
        }
    }

    public float getLevelLength(){ return 40; }

    public boolean playerHasTrailer(){ return false; }

    public abstract BaseLevel makeNextLevel();
}
