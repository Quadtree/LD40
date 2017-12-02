package info.quadtree.ld40;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public class Util {
    public static Body createBodyOfType(BodyDef.BodyType type){
        BodyDef bd = new BodyDef();
        bd.type = type;

        assert(LD40.s != null);

        return LD40.s.cgs.world.createBody(bd);
    }

    public static void drawOnBody(Body body, String spriteName, float sizeX, float sizeY){
        Util.drawOnBody(body, spriteName, sizeX, sizeY, 0,0,0);
    }

    public static void drawOnBody(Body body, String spriteName, float sizeX, float sizeY, float offsetX, float offsetY, float angleOffsetRadians){



        LD40.s.batch.draw(LD40.s.getSprite(spriteName), body.getPosition().x, body.getPosition().y, 0.5f, 0.5f, 1, 1, sizeX, sizeY, body.getAngle() * MathUtils.radiansToDegrees);
    }

    /*public static void drawOnFixtures(Body body, String spriteName){
        for (Fixture fx : body.getFixtureList()){
            Shape shp = fx.getShape();

            if (shp instanceof PolygonShape){
                shp.get
            }
            LD40.s.batch.draw(LD40.s.getSprite(spriteName), body.getPosition().x, body.getPosition().y, 0.5f, 0.5f, 1, 1, sizeX, sizeY, body.getAngle() * MathUtils.radiansToDegrees);
        }


    }*/
}
