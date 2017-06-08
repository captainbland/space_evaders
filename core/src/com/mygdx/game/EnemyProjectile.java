package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by jaron on 06/06/17.
 */

public class EnemyProjectile {
    private Sprite sprite;
    private float speed = 300;
    boolean destroyed = false;
    public EnemyProjectile(Texture texture, float x, float y) {
        sprite = new Sprite(texture);
        sprite.setSize(32,32);
        sprite.setPosition(x, y);
    }

    public void update(float delta) {
        sprite.translateY(-speed * delta);
    }

    public Rectangle getBoundingRect() {
        Rectangle spriteRect = sprite.getBoundingRectangle();
        spriteRect.x = spriteRect.x + 4;
        spriteRect.y = spriteRect.y + 4;
        spriteRect.height = spriteRect.height - 8;
        spriteRect.height = spriteRect.width - 8;
        return spriteRect;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void onCollide() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
