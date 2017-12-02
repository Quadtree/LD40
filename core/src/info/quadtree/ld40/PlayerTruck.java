package info.quadtree.ld40;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import javafx.scene.shape.Circle;

public class PlayerTruck extends Actor {
    Body chassis;
    Body frontWheel;
    Body rearWheel;

    public PlayerTruck(){
        chassis = Util.createBodyOfType(BodyDef.BodyType.DynamicBody);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(2, 0.4f);

        chassis.createFixture(ps, 1);
        chassis.setTransform(10,15, 0);

        rearWheel = createWheel(-2, -0.5f);
        frontWheel = createWheel(2, -0.5f);


    }

    Body createWheel(float relX, float relY){
        Body ret = Util.createBodyOfType(BodyDef.BodyType.DynamicBody);

        CircleShape cs = new CircleShape();
        cs.setRadius(.8f);
        ret.createFixture(cs, 1);

        RevoluteJointDef rjd = new RevoluteJointDef();
        rjd.bodyA = chassis;
        rjd.bodyB = ret;
        rjd.localAnchorA.x = -relX;
        rjd.localAnchorA.y = relY;
        rjd.localAnchorB.x = 0;
        rjd.localAnchorB.y = 0;
        rjd.collideConnected = false;
        Vector2 anchorPt = chassis.getPosition().cpy().add(new Vector2(relX, relY));

        System.out.println("Anchor Pt: " + anchorPt);

        LD40.s.cgs.world.createJoint(rjd);

        ret.setTransform(anchorPt, 0);

        return ret;
    }

    @Override
    public boolean keep() {
        return super.keep();
    }

    @Override
    public void update() {
        super.update();

        //frontWheel.applyTorque(-500, true);
        //rearWheel.applyTorque(-500, true);
        frontWheel.applyAngularImpulse(-0.08f, true);
        rearWheel.applyAngularImpulse(-0.08f, true);
    }

    @Override
    public void render() {
        super.render();

        Util.drawOnBody(chassis,"panel1", 4f, .8f);
        Util.drawOnBody(rearWheel,"wheel1", 1.6f, 1.6f);
        Util.drawOnBody(frontWheel,"wheel1", 1.6f, 1.6f);
    }
}
