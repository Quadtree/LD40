package info.quadtree.ld40;

import com.badlogic.gdx.math.Vector2;

public abstract class Actor {
    public boolean keep(){ return true; }
    public void update(){}
    public void render(){}

    public abstract Vector2 getPosition();
}
