package info.quadtree.ld40.info.quadtree.ld40.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import info.quadtree.ld40.LD40;
import info.quadtree.ld40.Util;

import java.util.HashSet;
import java.util.Set;

public class PlayerTruck extends Actor implements InputProcessor {
    public static final float BED_ENDS_HEIGHT = 2f;
    public static final int BED_ENDS_OFFSET_ANGLE = 20;
    Body chassis;
    Body frontWheel;
    Body rearWheel;
    Body rearWheel2;

    Body bed;
    Body trailerChassis;

    Body trailerRearWheel;
    Body trailerFrontWheel;

    RevoluteJoint bedJoint;

    final static float BED_LENGTH = 5f;
    final static float BED_HEIGHT = 0.25f;
    final static float BED_ENDS_EXTRA_SPREAD = 0.2f;

    boolean accelLeft;
    boolean accelRight;

    boolean liftBed;
    boolean lowerBed;

    public float getLengthBehind(){
        return trailerChassis == null ? 5f : 15f;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) accelLeft = true;
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) accelRight = true;

        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) liftBed = true;
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) lowerBed = true;

        if (keycode == Input.Keys.R){
            LD40.s.levelToLoad = LD40.s.cgs.baseLevel;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) accelLeft = false;
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) accelRight = false;

        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) liftBed = false;
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) lowerBed = false;

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

    public PlayerTruck(boolean hasTrailer){
        chassis = Util.createBodyOfType(BodyDef.BodyType.DynamicBody);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(3, 0.4f);

        chassis.createFixture(ps, 1);
        float startHeight = LD40.s.cgs.getTerrainHeightAt(10) + 15;
        Util.log("StartHeight=" + startHeight);
        chassis.setTransform(10,startHeight, 0);

        rearWheel = createWheel(-3f, -0.5f, chassis);
        rearWheel2 = createWheel(-1.3f, -0.5f, chassis);
        frontWheel = createWheel(3f, -0.5f, chassis);

        // Create BED
        this.bed = createBed();
        RevoluteJointDef rjd = new RevoluteJointDef();
        rjd.bodyA = chassis;
        rjd.bodyB = bed;
        rjd.localAnchorA.x = -1.85f;
        rjd.localAnchorA.y = 1f;
        rjd.enableLimit = true;
        rjd.collideConnected = false;
        bedJoint = (RevoluteJoint)LD40.s.cgs.world.createJoint(rjd);

        bed.setTransform(chassis.getPosition().cpy().add(new Vector2(0,5)), 0);

        if (hasTrailer) {
            // Create TRAILER
            trailerChassis = createBed();
            rjd = new RevoluteJointDef();
            rjd.bodyA = chassis;
            rjd.bodyB = trailerChassis;
            rjd.localAnchorA.x = -3f;
            rjd.localAnchorA.y = 0f;
            rjd.localAnchorB.x = 7f;
            rjd.localAnchorB.y = 0f;
            rjd.collideConnected = true;
            LD40.s.cgs.world.createJoint(rjd);

            trailerChassis.setTransform(chassis.getPosition().cpy().add(-10, 0), 0);

            trailerRearWheel = createWheel(-3f, -0.5f, trailerChassis);
            trailerFrontWheel = createWheel(3f, -0.5f, trailerChassis);
        }

        // 45f / 30f, 60f / 30f, 2.3f, 1.4f, 0);

        PolygonShape cabPs = new PolygonShape();
        cabPs.setAsBox(45f / 30f / 2, 60f / 30f / 2, new Vector2(2.3f, 1.4f), 0);
        chassis.createFixture(cabPs, 1);


        Gdx.input.setInputProcessor(this);
    }

    private Body createBed() {
        PolygonShape ps;
        Body bed = Util.createBodyOfType(BodyDef.BodyType.DynamicBody);


        FixtureDef fd = new FixtureDef();
        ps = new PolygonShape();
        ps.setAsBox(BED_LENGTH / 2, BED_HEIGHT / 2);
        fd.shape = ps;
        fd.density = 1;
        fd.friction = 2;
        bed.createFixture(fd);

        fd = new FixtureDef();
        ps = new PolygonShape();
        ps.setAsBox(BED_ENDS_HEIGHT / 2, BED_HEIGHT / 2, new Vector2(BED_LENGTH / 2 + BED_ENDS_EXTRA_SPREAD, BED_ENDS_HEIGHT / 2), (90 - BED_ENDS_OFFSET_ANGLE) * MathUtils.degreesToRadians);
        fd.shape = ps;
        fd.density = 1;
        fd.friction = 2;
        bed.createFixture(fd);

        fd = new FixtureDef();
        ps = new PolygonShape();
        ps.setAsBox(BED_ENDS_HEIGHT / 2, BED_HEIGHT / 2, new Vector2(-BED_LENGTH / 2 - BED_ENDS_EXTRA_SPREAD, BED_ENDS_HEIGHT / 2), (90 + BED_ENDS_OFFSET_ANGLE) * MathUtils.degreesToRadians);
        fd.shape = ps;
        fd.density = 1;
        fd.friction = 2;
        bed.createFixture(fd);

        return bed;
    }

    Body createWheel(float relX, float relY, Body chassis){
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

        if (LD40.s.cgs.finalScore != null){
            accelLeft = false;
            accelRight = false;
        }

        float accel = 0;
        float bedMovement = 0;

        try {
            for (Controller con : Controllers.getControllers()) {
                //System.err.println("0: " + con.getAxis(0));
                //System.err.println(con.getAxis(1));

                accel = con.getAxis(1);
                bedMovement = -con.getAxis(2);
            }
        } catch (Throwable t){}

        if (accelLeft && !accelRight) accel = -1;
        if (accelRight && !accelLeft) accel = 1;

        final float enginePower = 10f;

        //frontWheel.applyAngularImpulse(-enginePower * accel, true);
        //rearWheel.applyAngularImpulse(-enginePower * accel, true);

        frontWheel.setAngularVelocity(-enginePower * accel);
        rearWheel.setAngularVelocity(-enginePower * accel);
        rearWheel2.setAngularVelocity(-enginePower * accel);



        if (liftBed && !lowerBed) bedMovement = 1;
        if (!liftBed && lowerBed) bedMovement = -1;

        float bedCenter = (bedJoint.getUpperLimit() + bedJoint.getLowerLimit()) / 2;

        bedCenter += bedMovement * 0.01f;
        bedCenter = MathUtils.clamp(bedCenter, -0.18f, .18f);

        bedJoint.setLimits(bedCenter - 0.01f, bedCenter + 0.01f);


    }

    @Override
    public void render() {
        super.render();

        Util.drawOnBody(chassis,"panel1", 0.25f, .8f, -1.85f, 0.5f, 0);
        Util.drawOnBody(chassis,"panel1", 6f, .8f);
        Util.drawOnBody(chassis,"cab", 45f / 30f, 60f / 30f, 2.3f, 1.4f, 0);

        drawBed(bed);
        if (trailerChassis != null){
            drawBed(trailerChassis);

            Util.drawOnBody(trailerChassis,"panel1", 5f, 0.25f, 5f, 0f, 0);

            Util.drawOnBody(trailerRearWheel,"wheel1", 1.6f, 1.6f);
            Util.drawOnBody(trailerFrontWheel,"wheel1", 1.6f, 1.6f);
        }

        Util.drawOnBody(rearWheel,"wheel1", 1.6f, 1.6f);
        Util.drawOnBody(rearWheel2,"wheel1", 1.6f, 1.6f);
        Util.drawOnBody(frontWheel,"wheel1", 1.6f, 1.6f);
    }

    private void drawBed(Body bed) {
        Util.drawOnBody(bed,"panel1", BED_LENGTH, BED_HEIGHT);
        Util.drawOnBody(bed,"panel1", BED_ENDS_HEIGHT, BED_HEIGHT, BED_LENGTH / 2 + BED_ENDS_EXTRA_SPREAD, BED_ENDS_HEIGHT / 2, 90 - BED_ENDS_OFFSET_ANGLE);
        Util.drawOnBody(bed,"panel1", BED_ENDS_HEIGHT, BED_HEIGHT, -BED_LENGTH / 2 - BED_ENDS_EXTRA_SPREAD, BED_ENDS_HEIGHT / 2, 90 + BED_ENDS_OFFSET_ANGLE);
    }

    @Override
    public Vector2 getPosition() {
        return chassis.getPosition().cpy();
    }
}
