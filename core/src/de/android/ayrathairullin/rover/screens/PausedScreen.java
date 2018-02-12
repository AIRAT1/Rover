package de.android.ayrathairullin.rover.screens;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.boontaran.MessageEvent;

import de.android.ayrathairullin.rover.Rover;

public class PausedScreen extends Group{
    public static final int ON_RESUME = 1;
    public static final int ON_QUIT = 2;

    private Image title;
    private ImageButton resume, quit;
    private float w, h;

    public PausedScreen(float w, float h) {
        this.w = w;
        this.h = h;
        title = new Image(Rover.atlas.findRegion("paused"));
        title.setX((w = title.getWidth()) / 2);
        title.setY(h);
        addActor(title);
        resume = new ImageButton(new TextureRegionDrawable(Rover.atlas.findRegion("play_btn")),
                new TextureRegionDrawable(Rover.atlas.findRegion("play_btn_down")));
        addActor(resume);
        resume.setX(w / 2 - resume.getWidth() - 30);
        resume.setY((h - resume.getHeight()) / 2 - 60);
        resume.setColor(1, 1, 1, 0);
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new MessageEvent(ON_RESUME));
            }
        });
    }
}
