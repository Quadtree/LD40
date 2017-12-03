package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import info.quadtree.ld40.LD40;

public class HelpMessage extends Actor {

    private Vector2 pos;

    static BitmapFont font = null;

    String text;

    public HelpMessage(Vector2 pos, String text) {
        this.pos = pos.cpy();
        this.text = text;
    }



    @Override
    public void renderUi() {
        super.render();

        if (font == null) font = new BitmapFont(Gdx.files.internal("elec-16.fnt"));

        Vector3 screenPos = LD40.s.cgs.cam.project(new Vector3(pos.x, pos.y, 0));

        font.draw(LD40.s.batch, text, screenPos.x, screenPos.y);
    }

    @Override
    public Vector2 getPosition() {
        return super.getPosition();
    }
}
