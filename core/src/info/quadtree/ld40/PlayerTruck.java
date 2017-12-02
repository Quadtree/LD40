package info.quadtree.ld40;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import javafx.scene.shape.Circle;

public class PlayerTruck extends Actor implements InputProcessor {
    Body chassis;
    Body frontWheel;
    Body rearWheel;
    Body rearWheel2;

    boolean accelLeft;
    boolean accelRight;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) accelLeft = true;
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) accelRight = true;

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) accelLeft = false;
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) accelRight = false;

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public PlayerTruck(){
        chassis = Util.createBodyOfType(BodyDef.BodyType.DynamicBody);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(3, 0.4f);

        chassis.createFixture(ps, 1);
        chassis.setTransform(10,15, 0);

        rearWheel = createWheel(-3f, -0.5f);
        rearWheel2 = createWheel(-1.3f, -0.5f);
        frontWheel = createWheel(3f, -0.5f);

        Gdx.input.setInputProcessor(this);
    }

    Body createWheel(float relX, float relY){
        Body ret = Util.createBodyOfType(BodyDef.BodyType.DynamicBody);

        CircleShape cs = new CircleShape();
        cs.setRadius(.8f);

        FixtureDef fd = new FixtureDef();
        fd.friction = 10;
        fd.shape = cs;
        fd.density = 1;

        ret.createFixture(fd);

        RevoluteJointDef rjd = new RevoluteJointDef();
        rjd.bodyA = chassis;
        rjd.bodyB = ret;
        rjd.localAnchorA.x = relX;
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

        float accel = 0;

        if (accelLeft && !accelRight) accel = -1;
        if (accelRight && !accelLeft) accel = 1;

        final float enginePower = 10f;

        //frontWheel.applyAngularImpulse(-enginePower * accel, true);
        //rearWheel.applyAngularImpulse(-enginePower * accel, true);

        frontWheel.setAngularVelocity(-enginePower * accel);
        rearWheel.setAngularVelocity(-enginePower * accel);
        rearWheel2.setAngularVelocity(-enginePower * accel);
    }

    @Override
    public void render() {
        super.render();

        Util.drawOnBody(chassis,"panel1", 6f, .8f);
        Util.drawOnBody(rearWheel,"wheel1", 1.6f, 1.6f);
        Util.drawOnBody(rearWheel2,"wheel1", 1.6f, 1.6f);
        Util.drawOnBody(frontWheel,"wheel1", 1.6f, 1.6f);
    }
}
