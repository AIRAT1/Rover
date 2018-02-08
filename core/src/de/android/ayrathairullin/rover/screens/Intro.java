package de.android.ayrathairullin.rover.screens;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.boontaran.games.StageGame;

import de.android.ayrathairullin.rover.Rover;

public class Intro extends StageGame{
    public static final int ON_PLAY = 1;
    public static final int ON_BACK = 2;

    private Image title;
    private ImageButton playBtn;

    public Intro() {
        Image bg = new Image(Rover.atlas.findRegion("intro_bg"));
        addBackground(bg, true, false);
        title = new Image(Rover.atlas.findRegion("title"));
        addChild(title);
        centerActorX(title);
        title.setY(getHeight());
        MoveByAction move = new MoveByAction();
        move.setAmount(0, - title.getHeight() * 1.5f);
        move.setDuration(.4f);
        move.setInterpolation(Interpolation.swingOut);
        move.setActor(title);
        title.addAction(Actions.delay(.5f, move));
        playBtn = new ImageButton(
                new TextureRegionDrawable(Rover.atlas.findRegion("play_btn")),
                new TextureRegionDrawable(Rover.atlas.findRegion("play_btn_down"))
        );
        addChild(playBtn);
        centerActorXY(playBtn);
        playBtn.moveBy(0, - 60);
        AlphaAction alphaAction = new AlphaAction();
        alphaAction.setActor(playBtn);
        alphaAction.setDuration(.9f);
        alphaAction.setAlpha(1);
        playBtn.setColor(1, 1, 1, 0);
        playBtn.addAction(Actions.delay(.8f, alphaAction));
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playBtn.clearActions();
                onClickPlay();
                Rover.media.playSound("click.ogg");
            }
        });
    }

    private void onClickPlay() {
        playBtn.setTouchable(Touchable.disabled);
        playBtn.addAction(Actions.alpha(0, .3f));
        title.addAction(Actions.moveTo(title.getX(), getHeight(), .5f, Interpolation.swingIn));
        delayCall("delay1", .7f);
    }

    public boolean keyUp(int keyCode) {
        if (keyCode == Keys.ESCAPE || keyCode == Keys.BACK) {
            call(ON_BACK);
            return true;
        }
        return super.keyUp(keyCode);
    }

    protected void onDelayCall(String code) {
        if (code.equals("delay1")) {
            call(ON_PLAY);
        }
    }
}
