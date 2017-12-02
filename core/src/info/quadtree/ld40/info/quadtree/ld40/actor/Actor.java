package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.math.Vector2;

public abstract class Actor {
    public boolean keep(){ return true; }
    public void update(){}
    public void render(){}
    public void backgroundRender(){}

    public Vector2 getPosition(){ return new Vector2(0,0); }
}
