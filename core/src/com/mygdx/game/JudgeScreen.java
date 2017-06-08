package com.mygdx.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by jaron on 08/06/17.
 */

public class JudgeScreen implements Screen{
    Texture ruddShout;
    Texture corbynLaugh;
    Texture ruddLaugh;
    Texture crowdLaugh;
    Texture message;
    Sound track;

    long time;

    OrthographicCamera cam = new OrthographicCamera();
    SpriteBatch batch;
    public JudgeScreen() {
        cam.setToOrtho(false, 1280, 720);
        ruddShout = new Texture(Gdx.files.internal("RUDDSHOUT.jpg"));
        corbynLaugh = new Texture(Gdx.files.internal("corblol.png"));
        ruddLaugh = new Texture(Gdx.files.internal("RUDDLAUGH.png"));
        crowdLaugh = new Texture(Gdx.files.internal("corbyn-rally.jpg"));
        track = Gdx.audio.newSound(Gdx.files.internal("judge_us_on_our_record_LOL.ogg"));
        message = new Texture(Gdx.files.internal("end_screen.png"));

        time = TimeUtils.millis();
        batch = new SpriteBatch();

        track.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        if(TimeUtils.timeSinceMillis(time) < 3000) {
            batch.draw(ruddShout, 0,0, 1280,720);
        } else if(TimeUtils.timeSinceMillis(time) > 3000) {
            batch.draw(corbynLaugh,700,350);
            if(TimeUtils.timeSinceMillis(time) > 4000) {
                batch.draw(ruddLaugh, 400, 400);
            }
            if(TimeUtils.timeSinceMillis(time) > 6000) {
                batch.draw(crowdLaugh, 200, 310);
            }
        }

        if(TimeUtils.timeSinceMillis(time) > 13000) {
            batch.draw(message,0,0,1280,720);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
