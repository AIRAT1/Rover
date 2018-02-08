package de.android.ayrathairullin.rover.media;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.android.ayrathairullin.rover.Rover;

public class LevelIcon extends Group{
    private int id;
    private Label label;
    private Image lockImg, bg, bgDown, hiliteImg;
    private boolean isHilited = false;
    private boolean alphaUp = false;

    public LevelIcon(int id) {
        this.id = id;
        hiliteImg = new Image(Rover.atlas.findRegion("level_icon_hilite"));
        addActor(hiliteImg);
        hiliteImg.setVisible(false);
        hiliteImg.setX(getWidth() - hiliteImg.getWidth() / 2);
        hiliteImg.setY(getHeight() - hiliteImg.getHeight() / 2);
        bg = new Image(Rover.atlas.findRegion("level_icon_bg"));
        addActor(bg);
        setSize(bg.getWidth(), bg.getHeight());
        bgDown = new Image(Rover.atlas.findRegion("level_icon_bg_down"));
        addActor(bgDown);
        bgDown.setX(bg.getX() + (bg.getWidth() - bgDown.getWidth() / 2));
        bgDown.setY(bg.getY() + (bg.getHeight() - bgDown.getHeight() / 2));
    }
}
