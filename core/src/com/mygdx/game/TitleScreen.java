package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Timer;

import static com.badlogic.gdx.Gdx.graphics;

/**
 * Created by jaron on 01/06/2017.
 */
public class TitleScreen implements Screen, ScreenChanger {
    private SpriteBatch batch;
    private Texture backgroundImg;
    private Texture pressStartImg;
    private OrthographicCamera cam;
    private final PublicSpaceEvaders game;

    private boolean showStartImg = true;
    private final Timer fadeTimer = new Timer();

    FadeOutEffect fadeOutEffect;


    public void flashStartImg() {
        showStartImg = !showStartImg;
    }



    public void changeScreen() {
        switchScreen(game, new IntroScreen(game));
    }

    public void switchScreen(final PublicSpaceEvaders game, final Screen newScreen){
        fadeOutEffect.start(0, 0.033f, 0.05f);
    }


    public TitleScreen(final PublicSpaceEvaders game) {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1280, 720);

        this.game = game;
        batch = new SpriteBatch();
        backgroundImg = new Texture("public_space_evaders_title.png");
        pressStartImg = new Texture("press_start.png");
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                flashStartImg();
            }
        }, 1.0f, 1.0f);
        Gdx.input.setInputProcessor(new MenuInputProcessor(this));

        fadeOutEffect = new FadeOutEffect(new VoidCallback() {
            public void callback() {
                game.setScreen(new IntroScreen(game));
            }
        });
    }

    @Override
    public void show() {

    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, fadeOutEffect.getOpacity());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        batch.setColor(1,1,1, fadeOutEffect.getOpacity());
        batch.begin();
        batch.draw(backgroundImg, 0, 0, cam.viewportWidth, cam.viewportHeight);
        if(showStartImg) {
            batch.draw(pressStartImg, cam.viewportWidth/2 - pressStartImg.getWidth() / 2, 0 + pressStartImg.getHeight()/2 - 30);
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
        backgroundImg.dispose();
        pressStartImg.dispose();
        batch.dispose();
    }


}
