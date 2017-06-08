package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import static com.mygdx.game.Enemy.State.ALIVE;
import static com.mygdx.game.Enemy.State.EXPLODING;

/**
 * Created by itjs3 on 03/06/2017.
 */
public class Enemy {
    Sprite sprite;
    private int health = 1;
    long lastTick = 0;
    public static final int width = 42;
    public static final int height = 50;
    Texture explodeTexture;
    boolean flashOff = false;
    boolean destroyed = false;
    final static long explodeFor = 500;

    static enum State {
        ALIVE,
        EXPLODING
    }
    long explodeAt = 0;
    private State state = ALIVE;

    public Enemy(Sprite sprite, float startX, float startY, Texture explodeTexture) {
        this.sprite = sprite;
        sprite.setSize(width,height);
        sprite.setPosition(startX, startY);
        lastTick = TimeUtils.millis();
        this.explodeTexture = explodeTexture;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public Rectangle getBoundingRect() {
        return sprite.getBoundingRectangle();
    }

    public void onCollideBullet() {

        if (state.equals(ALIVE)) {
            flashOff = true;
            health--;
            if (health == 0) {
                state = EXPLODING;
                explodeAt = TimeUtils.millis();
            }
        }
    }

    public void render(SpriteBatch batch) {
        if(state.equals(ALIVE)) {
            if(!flashOff)
                sprite.draw(batch);
            flashOff = false;
        } else if(state.equals(EXPLODING)) {
            if(TimeUtils.timeSinceMillis(explodeAt) > explodeFor) {
                destroyed = true;
            }
            batch.draw(explodeTexture, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
