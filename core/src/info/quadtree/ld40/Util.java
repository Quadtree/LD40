package info.quadtree.ld40;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;

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

    public static void drawOnBody(Body body, String spriteName, float sizeX, float sizeY, float offsetX, float offsetY, float angleOffsetDegrees){

        float rot = body.getAngle() * MathUtils.radiansToDegrees;

        Affine2 a2 = new Affine2();
        a2.idt();
        a2.rotate(rot);

        Vector2 offset = new Vector2(offsetX, offsetY);
        a2.applyTo(offset);

        float posX = body.getPosition().x + offset.x - 0.5f;
        float posY = body.getPosition().y + offset.y - 0.5f;

        LD40.s.batch.draw(LD40.s.getSprite(spriteName), posX, posY, 0.5f, 0.5f, 1, 1, sizeX, sizeY, rot + angleOffsetDegrees);
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

    public interface FloatFormatter {
        String formatFloat(float f);
        void log(String msg);
    }

    public static FloatFormatter defaultFloatFormatter = null;

    public static String formatFloat(float f){
        return defaultFloatFormatter.formatFloat(f);
    }

    public static void log(String msg){ defaultFloatFormatter.log(msg); }

    static HashMap<String, Sound> soundPool = new HashMap<String, Sound>();

    public static void playSound(String name, float volume){
        if (!soundPool.containsKey(name)){
            soundPool.put(name, Gdx.audio.newSound(Gdx.files.internal(name + ".wav")));
        }

        soundPool.get(name).play(volume);
    }

    public static Preferences getPrefs(){
        return Gdx.app.getPreferences("info.quadtree.ld40.xml");
    }
}
