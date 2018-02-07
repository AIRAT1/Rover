package de.android.ayrathairullin.rover.player;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.ActorClip;

import de.android.ayrathairullin.rover.Rover;
import de.android.ayrathairullin.rover.Setting;
import de.android.ayrathairullin.rover.levels.Level;

public class Player extends ActorClip implements IBody{
    private Image roverImg, astronautImg, astronautFallImg, frontWheelImg, rearWheelImg;
    private Group frontWheelCont, rearWheelCont, astronautFallCont;
    public Body rover, frontWheel, rearWheel, astronaut;
    private Joint frontWheelJoint, rearWheelJoint, astroJoint;
    private World world;
    private boolean hasDestroyed = false;
    private boolean destroyOnNextUpdate = false;
    private boolean isTouchGround = true;
    private float jumpImpulse = Setting.JUMP_IMPULSE;
    private float jumpWait = 0;
    private Level level;

    public Player(Level level) {
        this.level = level;

        roverImg = new Image(Rover.atlas.findRegion("rover"));
        childs.addActor(roverImg);
        roverImg.setX(-roverImg.getWidth() / 2);
        roverImg.setY(- 15);

        astronautImg = new Image(Rover.atlas.findRegion("astronaut"));
        childs.addActor(astronautImg);
        astronautImg.setX(- 35);
        astronautImg.setY(20);

        astronautFallCont = new Group();
        astronautFallImg = new Image(Rover.atlas.findRegion("astronaut_fall"));
        astronautFallCont.addActor(astronautFallImg);
        astronautFallImg.setX(- astronautFallImg.getWidth() / 2);
        astronautFallImg.setY(- astronautFallImg.getHeight() / 2);
    }

    public void touchGround() {
        isTouchGround = true;
    }

    public boolean isTouchedGround() {
        if (jumpWait > 0) {
            return false;
        }
        return isTouchGround;
    }

    @Override
    public Body createBody(World world) {
        this.world = world;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.linearDamping = 0;

        frontWheelCont = new Group();
        frontWheelImg = new Image(Rover.atlas.findRegion("front_wheel"));
        frontWheelCont.addActor(frontWheelImg);
        frontWheelImg.setX(- frontWheelImg.getWidth() / 2);
        frontWheelImg.setY(- frontWheelImg.getHeight() / 2);
        getParent().addActor(frontWheelCont);
        UserData data = new UserData();
        data.actor = frontWheelCont;
        frontWheel.setUserData(data);
        RevoluteJointDef rDef = new RevoluteJointDef();
        rDef.initialize(rover, frontWheel, new Vector2(frontWheel.getPosition()));
        frontWheelJoint = world.createJoint(rDef);

        rearWheelCont = new Group();
        rearWheelImg = new Image(Rover.atlas.findRegion("rear_wheel"));
        rearWheelCont.addActor(rearWheelImg);
        rearWheelImg.setX(- rearWheelImg.getWidth() / 2);
        rearWheelImg.setY(- rearWheelImg.getHeight() / 2);
        getParent().addActor(rearWheelCont);
        data = new UserData();
        data.actor = rearWheelCont;
        rearWheel.setUserData(data);
        rDef.initialize(rover, rearWheel, new Vector2(rearWheel.getPosition()));
        rearWheelJoint = world.createJoint(rDef);

        return rover;
    }

    private Body createWheel(World world, float rad) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.linearDamping = 0;
        def.angularDamping = 1f;
        Body body = world.createBody(def);
        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(rad);
        fDef.shape = shape;
        fDef.restitution = .5f;
        fDef.friction = .4f;
        fDef.density = 1;
        body.createFixture(fDef);
        shape.dispose();

        return body;
    }
}
