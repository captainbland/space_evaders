package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by jaron on 02/06/2017.
 */
public class IntroScreen implements Screen, ScreenChanger {

    PublicSpaceEvaders game;
    OrthographicCamera cam;
    BitmapFont font;
    SpriteBatch batch;
    final static int fontSize = 28;
    int char_to = 0;
    String intro_text;
    Timer timer;
    private final FadeOutEffect fadeOutEffect;


    public IntroScreen(final PublicSpaceEvaders game) {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1280, 720);
        batch = new SpriteBatch();
        intro_text = Gdx.files.internal("intro_text.txt").readString();
        font = new BitmapFont(Gdx.files.internal("fonts/space_invaders.fnt"), Gdx.files.internal("fonts/space_invaders.png"), false);
        this.game = game;
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                revealChar();
            }
        }, 0.3f, 0.1f);

        Gdx.input.setInputProcessor(new MenuInputProcessor(this));

        fadeOutEffect = new FadeOutEffect(new VoidCallback() {

            @Override
            public void callback() {
                game.setScreen(new GameScreen(game));
            }
        });
    }

    private void revealChar() {
        char_to++;
        if(char_to >= intro_text.length()) {
            char_to = intro_text.length() - 1;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.setColor(1,1,1,fadeOutEffect.getOpacity());

        font.setColor(1,1,1,fadeOutEffect.getOpacity());
        font.draw(batch, intro_text.substring(0, char_to), 10,cam.viewportHeight - fontSize);
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
        font.dispose();
        batch.dispose();

    }

    @Override
    public void changeScreen() {
        timer.stop();
        fadeOutEffect.start(0, 0.033f, 0.05f);
    }
}
