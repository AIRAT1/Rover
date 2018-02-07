package de.android.ayrathairullin.rover.levels;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boontaran.games.StageGame;

public class Level extends StageGame{
    public static final float WORLD_SCALE = 30;

    private String directory;

    public Level(String directory) {
        this.directory = directory;
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
