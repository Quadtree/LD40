package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import info.quadtree.ld40.HighScoreEntry;
import info.quadtree.ld40.HighScores;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.Util;

import java.util.ArrayList;
import java.util.List;

public class EndFlag extends Flag {
    final static String HIGH_SCORE_SERVER_DOMAIN = "https://quadtree.info";

    public EndFlag(Vector2 pos) {
        super(pos);
    }

    @Override
    float invisibileWallOffset() {
        return 30f;
    }

    @Override
    protected Color getColor() {
        return Color.GREEN;
    }

    @Override
    public void update() {
        super.update();

        if (LD40.s.cgs.pc.getPosition().x - LD40.s.cgs.pc.getLengthBehind() > pos.x && LD40.s.cgs.finalScore == null){

            float totalScore = 0;

            for (Actor a : LD40.s.cgs.actors){
                if (a instanceof Cargo){
                    if (a.getPosition().x >= pos.x){
                        totalScore += ((Cargo) a).getScore();
                    }
                }
            }

            LD40.s.cgs.finalScore = (int)(totalScore * (1 + LD40.s.cgs.getTimeBonus()));
            Util.log( "Level is done final score is " + LD40.s.cgs.finalScore);

            Net.HttpRequest req = new Net.HttpRequest(Net.HttpMethods.GET);
            req.setUrl(HIGH_SCORE_SERVER_DOMAIN + "/ld/ld40/high_score.php?l=" + LD40.s.cgs.baseLevel.getName().replace(" ", "%20") + "&s=" + LD40.s.cgs.finalScore);

            Gdx.net.sendHttpRequest(req, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    try {
                        Json js = new Json();
                        HighScores highScores = js.fromJson(HighScores.class, httpResponse.getResultAsString());

                        Util.log("" + httpResponse.getStatus().getStatusCode());
                        Util.log("" + highScores);

                        LD40.s.cgs.highScores = highScores;
                    }catch (RuntimeException t){
                        Util.log( "Error processing response from server (RE): " + t);
                        throw t;
                    } catch (Throwable t){
                        Util.log( "Error processing response from server: " + t);
                    }
                }

                @Override
                public void failed(Throwable t) {

                }

                @Override
                public void cancelled() {

                }
            });
        }
    }
}
