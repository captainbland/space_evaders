package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by itjs3 on 04/06/2017.
 */
public class PlayerProjectile {
    Texture gunTexture;
    final int regionWidth = 128;
    final int regionHeight = 1020;
    final float spriteWidth = 18;
    final float spriteHeight = 180;
    final int numLasers = 4;
    Sprite projectileSprite;
    boolean destroyed = false;
    float speed = 300;

    public PlayerProjectile(Texture texture, float posX, float posY) {
        int chooseLaser = new Random().nextInt(numLasers);

        projectileSprite = new Sprite(texture, regionWidth * chooseLaser, 0, regionWidth, regionHeight);
        projectileSprite.setSize(spriteWidth, spriteHeight);
        projectileSprite.setPosition(posX, posY);
    }

    public Sprite getSprite() { return projectileSprite; }

    public void update(float delta) {
        projectileSprite.translateY(speed * delta);
    }

    public void onCollideEnemy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public Rectangle getCollisionRect() {
        Rectangle rect = new Rectangle(projectileSprite.getX(), projectileSprite.getY() - 20, spriteWidth, spriteHeight - 20);
        return rect;
    }
}
