package de.android.ayrathairullin.rover.controls;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.android.ayrathairullin.rover.Rover;

public class Joystick extends Group{
    private Image idle, right, left;
    private static final int IDLE = 0;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;
    private int direction;

    public Joystick(float minHeight) {
        idle = new Image(Rover.atlas.findRegion("joystick"));
        addActor(idle);
        float scale = 1;
        if (idle.getHeight() < minHeight) {
            scale = minHeight / idle.getHeight();
        }
        idle.setHeight(idle.getHeight() * scale);
        idle.setWidth(idle.getWidth() * scale);
        setSize(idle.getWidth(), idle.getHeight());

        right = new Image(Rover.atlas.findRegion("joystick_right"));
        right.setSize(getWidth(), getHeight());
        addActor(right);

        left = new Image(Rover.atlas.findRegion("joystick_left"));
        left.setSize(getWidth(), getHeight());
        addActor(left);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
}
