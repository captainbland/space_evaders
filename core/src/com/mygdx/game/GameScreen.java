package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jaron on 01/06/2017.
 */
public class GameScreen implements Screen, InputProcessor {

    PublicSpaceEvaders game;
    SpriteBatch gameBatch;
    SpriteBatch hudBatch;
    Sprite player;
    Texture playerTexture;
    Texture mayTexture;
    Texture huntTexture;
    Texture bojoTexture;
    Texture explodeTexture;
    Texture corbynLasersTexture;
    Texture hammondTexture;
    Texture backgroundTexture;
    Texture shieldTexture;
    Texture enemyProjectileTexture;
    Sound laser;
    Sound explosion;
    BitmapFont hudFont;
    OrthographicCamera cam;
    OrthographicCamera hudCam;
    StandardEnemyGroupController enemyController;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<PlayerProjectile> playerProjectiles = new ArrayList<PlayerProjectile>();
    ArrayList<EnemyProjectile> enemyProjectiles = new ArrayList<EnemyProjectile>();

    private ArrayList<Enemy> mays = new ArrayList<Enemy>();
    Random random = new Random();

    Shield shieldLeft;
    Shield shieldMid;
    Shield shieldRight;

    ArrayList<Shield> shields = new ArrayList<Shield>();
    long shakeStart = 0;
    final long shakeFor = 750;
    final int shakeDist = 6;

    long fireDelay = 500;
    long lastFire = 0;

    long lastEnemyProjectile = TimeUtils.millis();
    long enemyProjectileDelay = 500;
    int score = 0;

    public GameScreen(PublicSpaceEvaders game) {
        this.game = game;

        playerTexture = new Texture(Gdx.files.internal("corbynhead.png"));
        mayTexture = new Texture(Gdx.files.internal("mayhead_1.png"));
        huntTexture = new Texture(Gdx.files.internal("hunthead.png"));
        hammondTexture = new Texture(Gdx.files.internal("hammond.png"));
        bojoTexture = new Texture(Gdx.files.internal("bojohead.png"));
        corbynLasersTexture = new Texture(Gdx.files.internal("corbyn_laser.png"));
        explodeTexture = new Texture(Gdx.files.internal("explode.png"));
        backgroundTexture = new Texture(Gdx.files.internal("Westminster.jpg"));
        shieldTexture = new Texture(Gdx.files.internal("shieldblock.png"));
        enemyProjectileTexture = new Texture(Gdx.files.internal("omvomvom.png"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("Explosion.mp3"));
        laser = Gdx.audio.newSound(Gdx.files.internal("laser.mp3"));

        hudFont = new BitmapFont(Gdx.files.internal("fonts/space_invaders.fnt"), Gdx.files.internal("fonts/space_invaders.png"), false);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1280, 720);
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, 1280, 720);
        player = new Sprite(playerTexture);
        player.setSize(75, 90);
        player.setPosition(cam.viewportWidth/2 - player.getWidth() / 2, 0);
        gameBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        Gdx.input.setInputProcessor(this);

        generateEnemiesWave1();
        generateShields();
    }

    public int scoreToLab() {
        return 25 + (int)((float) score / 4);
    }

    public int scoreToCon() {
        return 50 - (int)((float) score / 6);
    }

    public void generateShields() {
        shieldLeft = new Shield(shieldTexture, 200);
        shieldMid = new Shield(shieldTexture, cam.viewportWidth/2 - 50);
        shieldRight = new Shield(shieldTexture, cam.viewportWidth - 250);
        shields.add(shieldLeft);
        shields.add(shieldMid);
        shields.add(shieldRight);
    }

    public void generateEnemiesWave1() {

        final int cols = 12;
        final int enemyHSpacing = 60;
        final int enemyVSpaing = 60;
        for(int i = 0; i < cols; i++) {
            Enemy may = new Enemy(new Sprite(mayTexture),
                    i * enemyHSpacing + (cam.viewportWidth / 2 - enemyHSpacing*(cols/2)),
                    600+enemyVSpaing, explodeTexture);
            enemies.add(may);
            mays.add(may);
            Enemy bojo = new Enemy(new Sprite(bojoTexture),
                    i * enemyHSpacing + (cam.viewportWidth / 2 - enemyHSpacing*(cols/2)),
                    600, explodeTexture);
            Enemy bojo2 = new Enemy(new Sprite(bojoTexture),
                    i * enemyHSpacing + (cam.viewportWidth / 2 - enemyHSpacing*(cols/2)),
                    600-enemyVSpaing, explodeTexture);
            enemies.add(bojo);
            enemies.add(bojo2);
            Enemy hunt = new Enemy(new Sprite(huntTexture),
                    i * enemyHSpacing + (cam.viewportWidth / 2 - enemyHSpacing*(cols/2)),
                    600-enemyVSpaing*2, explodeTexture);
            Enemy hunt2 = new Enemy(new Sprite(huntTexture),
                    i * enemyHSpacing + (cam.viewportWidth / 2 - enemyHSpacing*(cols/2)),
                    600-enemyVSpaing*3, explodeTexture);
            enemies.add(hunt);
            enemies.add(hunt2);
            Enemy hammond = new Enemy(new Sprite(hammondTexture),
                    i * enemyHSpacing + (cam.viewportWidth / 2 - enemyHSpacing*(cols/2)),
                    600-enemyVSpaing*4, explodeTexture);
            Enemy hammond2 = new Enemy(new Sprite(hammondTexture),
                    i * enemyHSpacing + (cam.viewportWidth / 2 - enemyHSpacing*(cols/2)),
                    600-enemyVSpaing*5, explodeTexture);
            enemies.add(hammond);
            enemies.add(hammond2);

        }


        enemyController = new StandardEnemyGroupController(enemies,
                100, cam.viewportWidth - 100,
                cam.viewportWidth / 2 - enemyHSpacing * cols/2, cam.viewportWidth / 2 + enemyHSpacing * cols/2);
    }

    @Override
    public void show() {

    }

    public void spawnPlayerProjectile() {
        PlayerProjectile projectile = new PlayerProjectile(corbynLasersTexture, player.getX() + player.getWidth() / 2,0);
        playerProjectiles.add(projectile);
    }

    public void update(float delta) {

        if(enemies.size() == 0) {
            game.setScreen(new JudgeScreen());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.translateX(-300.0f * delta);
        }

        if(score < 0) {
            game.setScreen(new GameScreen(game));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.translateX(300.0f * delta);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            if(TimeUtils.timeSinceMillis(lastFire) > fireDelay) {
                spawnPlayerProjectile();
                lastFire = TimeUtils.millis();
                laser.play();
            }
        }


        if(TimeUtils.timeSinceMillis(lastEnemyProjectile) > enemyProjectileDelay && enemies.size() > 8) {
            int fireFrom = random.nextInt(enemies.size());
            float x = enemies.get(fireFrom).getSprite().getX();
            float y = enemies.get(fireFrom).getSprite().getY();
            EnemyProjectile newProjectile = new EnemyProjectile(enemyProjectileTexture, x, y);
            lastEnemyProjectile = TimeUtils.millis();
            enemyProjectiles.add(newProjectile);
        }

        for(EnemyProjectile ep: enemyProjectiles) {
            ep.update(delta);
        }

        enemyController.update(delta);
        for(PlayerProjectile pp : playerProjectiles) {
            pp.update(delta);

            //now check for collisions
            for(Enemy enemy : enemies) {
                if(enemy.getSprite().getBoundingRectangle().overlaps(pp.getCollisionRect())) {
                    enemy.onCollideBullet();
                    pp.onCollideEnemy();
                    shakeStart = TimeUtils.millis();
                    score++;
                    explosion.play();
                    break;
                }
            }
        }

        //shields
        for(Shield shield: shields) {
            for(PlayerProjectile pp: playerProjectiles) {
                if(shield.checkCollisions(pp.getCollisionRect())) {
                    pp.onCollideEnemy();
                }
            }

            for(Enemy enemy: enemies) {
                shield.checkCollisions(enemy.getBoundingRect());
            }

            for(EnemyProjectile ep: enemyProjectiles) {
                if(shield.checkCollisions(ep.getBoundingRect())) {
                    ep.onCollide();
                }
            }
        }

        for(Enemy enemy: enemies) {
            if(enemy.getBoundingRect().overlaps(player.getBoundingRectangle())) {
                score--;
                enemy.destroyed = true;
            }
        }

        for(EnemyProjectile ep: enemyProjectiles) {
            if(ep.getBoundingRect().overlaps(player.getBoundingRectangle())) {
                ep.onCollide();
                score--;
            }
        }
        //cleanup
        for(int i = playerProjectiles.size() - 1; i >= 0; i--) {
            if(playerProjectiles.get(i).isDestroyed()) {
                playerProjectiles.remove(i);
            }
        }

        for(int i = enemies.size() - 1; i >= 0; i--) {
            if(enemies.get(i).isDestroyed()) {
                enemies.remove(i);
            }
        }

        for(int i = enemyProjectiles.size() - 1; i >= 0; i--) {
            if(enemyProjectiles.get(i).isDestroyed()) {
                enemyProjectiles.remove(i);
            }
        }

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector3 camPos = cam.position.cpy();
        if( TimeUtils.timeSinceMillis(shakeStart) < shakeFor) {
            cam.translate(random.nextInt(shakeDist) - shakeDist/2, random.nextInt(shakeDist) - shakeDist / 2);
        }

        cam.update();
        gameBatch.setProjectionMatrix(cam.combined);
        gameBatch.begin();
        gameBatch.draw(backgroundTexture,0,0);

        player.draw(gameBatch);
        for(Enemy enemy: enemies) {
            enemy.render(gameBatch);
        }

        for(PlayerProjectile pp: playerProjectiles) {
            pp.getSprite().draw(gameBatch);
        }

        for(EnemyProjectile ep: enemyProjectiles) {
            ep.render(gameBatch);
        }

        shieldLeft.render(gameBatch);
        shieldMid.render(gameBatch);
        shieldRight.render(gameBatch);

        gameBatch.end();
        cam.position.x = camPos.x;
        cam.position.y = camPos.y;

        hudBatch.setProjectionMatrix(hudCam.combined);
        hudCam.update();
        hudBatch.begin();
        hudFont.setColor(0,0,0,1);
        hudFont.draw(hudBatch, "Polls: ", 22, 700-1);
        hudFont.draw(hudBatch, "Labour: " + scoreToLab(), 22, 620-1);
        hudFont.draw(hudBatch, "Tories: " + scoreToCon(), 22, 660-1);

        hudFont.setColor(1,1,1,1);
        hudFont.draw(hudBatch, "Polls: ", 20, 700);
        hudFont.setColor(1,.3f,.3f,1);
        hudFont.draw(hudBatch, "Labour: " + scoreToLab(), 20, 620);
        hudFont.setColor(.3f,.3f,1,1);
        hudFont.draw(hudBatch, "Tories: " + scoreToCon(), 20, 660);
        hudBatch.end();

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
        gameBatch.dispose();
        playerTexture.dispose();
         mayTexture.dispose();
         huntTexture.dispose();
         bojoTexture.dispose();
         explodeTexture.dispose();
         corbynLasersTexture.dispose();
         hammondTexture.dispose();
         backgroundTexture.dispose();
         shieldTexture.dispose();
         enemyProjectileTexture.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
