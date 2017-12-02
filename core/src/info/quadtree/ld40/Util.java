package info.quadtree.ld40;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
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
        /*Sprite sp = LD40.s.getSprite(spriteName);

        sp.setCenter(0.5f, 0.5f);
        sp.setScale(sizeX, sizeY);
        sp.setX(body.getPosition().x - sizeX / 2);
        sp.setY(body.getPosition().y - sizeY / 2);
        sp.setSize(1, 1);
        sp.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sp.draw(LD40.s.batch);*/

        LD40.s.batch.draw(LD40.s.getSprite(spriteName), body.getPosition().x, body.getPosition().y, 0.5f, 0.5f, 1, 1, sizeX, sizeY, body.getAngle() * MathUtils.radiansToDegrees);
    }
}
