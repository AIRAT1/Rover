package de.android.ayrathairullin.rover.controls;


import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import de.android.ayrathairullin.rover.Rover;

public class JumpGauge extends Group{
    private Array<Image> offs, ons;
    private final float TIME = .5f;
    private float time = 0;
    private boolean counting = false;

    public JumpGauge() {
        setTouchable(Touchable.disabled);
        Image box;
        offs = new Array<Image>();
        ons = new Array<Image>();
        NinePatch patch = new NinePatch(Rover.atlas.findRegion("jump_gauge_off"), 3, 3, 3, 3);
        int i;
        int w;
        for (i = 0; i < 5; i++) {
            w = (i + 3) * 4;
            box = new Image(patch);
            box.setWidth(w);
            box.setHeight(8);
            box.setY(- box.getWidth() / 2);
        }
    }
}
