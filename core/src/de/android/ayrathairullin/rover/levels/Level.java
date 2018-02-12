package de.android.ayrathairullin.rover.levels;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.boontaran.games.StageGame;

import de.android.ayrathairullin.rover.Rover;
import de.android.ayrathairullin.rover.Setting;
import de.android.ayrathairullin.rover.controls.CButton;
import de.android.ayrathairullin.rover.controls.Joystick;
import de.android.ayrathairullin.rover.controls.JumpGauge;
import de.android.ayrathairullin.rover.player.Player;
import de.android.ayrathairullin.rover.player.UserData;

public class Level extends StageGame {
    public static final float WORLD_SCALE = 30;
    public static final int ON_RESTART = 1;
    public static final int ON_QUIT = 2;
    public static final int ON_COMPLETED = 3;
    public static final int ON_FAILED = 4;
    public static final int ON_PAUSED = 5;
    public static final int ON_RESUME = 6;

    private static final int PLAY = 1;
    private static final int LEVEL_FAILED = 2;
    private static final int LEVEL_COMPLETED = 3;
    private static final int PAUSED = 4;

    private String directory;
    private int state = 1;
    private int mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, levelWidth, levelHeight;
    private Player player;
    private Body finish;
    private boolean moveFrontKey, moveBackKey;
    private Image pleaseWait;
    private Joystick joystick;
    private CButton jumpBackBtn, jumpForwardBtn;
    private String musicName;
    private boolean musicHasLoaded;
    private static final float LAND_RESTITUTION = .5f;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Array<Body> bodies = new Array<Body>();
    private boolean hasBeenBuilt = false;
    private TiledMap map;
    private JumpGauge jumpGauge;

    public Level(String directory) {
        this.directory = directory;
        pleaseWait = new Image(Rover.atlas.findRegion("please_wait"));
        addOverlayChild(pleaseWait);
        centerActorXY(pleaseWait);
        delayCall("build_level", .2f);
    }

    @Override
    protected void onDelayCall(String code) {
        if (code.equals("build_level")) {
            build();
        } else if (code.equals("resumeLevel2")) {
            resumeLevel2();
        }
    }

    private void setBackground(String region) {
        clearBackground();
        Image bg = new Image(Rover.atlas.findRegion(region));
        addBackground(bg, true, false);
    }

    private void build() {
        hasBeenBuilt = true;
        world = new World(new Vector2(0, -Setting.GRAVITY), true);
        world.setContactListener(contactListener);
        debugRenderer = new Box2DDebugRenderer();
        // TODO loadMap();
        if (player == null) {
            throw new Error("player not defined");
        }
        if (finish == null) {
            throw new Error("finish not defined");
        }
        // TODO addRectangleLand();
        int count = 60;
        while (count-- > 0) {
            world.step(1f / 60, 10, 10);
        }
        jumpGauge = new JumpGauge();
        addOverlayChild(jumpGauge);
        joystick = new Joystick(mmToPx(10));
        addOverlayChild(joystick);
        joystick.setPosition(15, 15);
        jumpBackBtn = new CButton(
                new Image(Rover.atlas.findRegion("jump1")),
                new Image(Rover.atlas.findRegion("jump1_down")),
                mmToPx(10)
        );
        addOverlayChild(jumpBackBtn);
        jumpForwardBtn = new CButton(
                new Image(Rover.atlas.findRegion("jump2")),
                new Image(Rover.atlas.findRegion("jump2_down")),
                mmToPx(10)
        );
        addOverlayChild(jumpForwardBtn);
        jumpForwardBtn.setPosition(getWidth() - jumpForwardBtn.getWidth() - 15, 15);
        jumpBackBtn.setPosition(jumpForwardBtn.getX() - jumpBackBtn.getWidth() - 15, 15);
        jumpBackBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == PLAY) {
                    if (player.isTouchedGround()) {
                        jumpGauge.start();
                        return true;
                    }
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                float jumpValue = jumpGauge.getValue();
                player.jumpBack(jumpValue);
            }
        });
        jumpForwardBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == PLAY) {
                    if (player.isTouchedGround()) {
                        jumpGauge.start();
                        return true;
                    }
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                float jumpValue = jumpGauge.getValue();
                player.jumpForward(jumpValue);
            }
        });
        setBackground("level_bg");
        world.getBodies(bodies);
        updateCamera();
    }

    protected void quitLevel() {
        call(ON_QUIT);
    }

    public void setMusic(String name) {
        musicName = name;
        Rover.media.addMusic(name);
    }

    public String getMusicName() {
        return musicName;
    }

    @Override
    public void dispose() {
        if (musicName != null && musicHasLoaded) {
            Rover.media.stopMusic(musicName);
            Rover.media.removeMusic(musicName);
        }
        if (world != null) {
            world.dispose();
        }
        map.dispose();
        super.dispose();
    }

    private ContactListener contactListener = new ContactListener() {
        @Override
        public void beginContact(Contact contact) {
            Body bodyA = contact.getFixtureA().getBody();
            Body bodyB = contact.getFixtureB().getBody();
            if (bodyA == player.astronaut) {
                playerTouch(bodyA);
            }
        }

        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    };

    private void resumeLevel2() {

    }

    private void updateCamera() {

    }

    public void addChild(Actor actor) {
        this.stage.addActor(actor);
    }

    public void addChild(Actor actor, float x, float y) {
        this.addChild(actor);
        actor.setX(x);
        actor.setY(y);
    }

    protected void playerTouch(Body body) {
        UserData data = (UserData)body.getUserData();
    }
}
