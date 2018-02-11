package de.android.ayrathairullin.rover.levels;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.boontaran.games.StageGame;

import de.android.ayrathairullin.rover.Rover;
import de.android.ayrathairullin.rover.controls.CButton;
import de.android.ayrathairullin.rover.controls.Joystick;
import de.android.ayrathairullin.rover.player.Player;

public class Level extends StageGame{
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
        }
    }

    private void build() {

    }

    public void addChild(Actor actor) {
        this.stage.addActor(actor);
    }

    public void addChild(Actor actor, float x, float y) {
        this.addChild(actor);
        actor.setX(x);
        actor.setY(y);
    }
}
