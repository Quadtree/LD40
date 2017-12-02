package info.quadtree.ld40;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Util {
    public static Body createBodyOfType(BodyDef.BodyType type){
        BodyDef bd = new BodyDef();
        bd.type = type;

        assert(LD40.s != null);

        return LD40.s.cgs.world.createBody(bd);
    }

    public static void drawOnBody(Body body, String spriteName, float sizeX, float sizeY){
        Sprite sp = LD40.s.getSprite(spriteName);

        sp.setCenter(sizeX / 2, sizeY / 2);
        sp.setX(body.getPosition().x - sizeX / 2);
        sp.setY(body.getPosition().y - sizeY / 2);
        sp.setSize(sizeX, sizeY);
        sp.draw(LD40.s.batch);
    }
}
