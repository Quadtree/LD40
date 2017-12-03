package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
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

        LD40

        float vx = (pos.x - LD40.s.cgs.pc.getPosition().x) * 30f;
        float vy = (pos.y - LD40.s.cgs.pc.getPosition().y) * 30f;

        System.err.println(vx + " " + vy);

        font.draw(LD40.s.batch, text, vx, vy);
    }

    @Override
    public Vector2 getPosition() {
        return super.getPosition();
    }
}
