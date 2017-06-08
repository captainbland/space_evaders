package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by jaron on 06/06/17.
 */

public class Shield {
    private ArrayList<Sprite> blocks = new ArrayList<Sprite>();
    final int width = 32;
    final int height= 16;
    final int yPos = 150;

    public Shield(Texture shieldTexture, float x) {
        for(int i = 0; i < 3; i++) {
            Sprite block1 = new Sprite(shieldTexture);
            Sprite block2 = new Sprite(shieldTexture);
            Sprite block3 = new Sprite(shieldTexture);
            block1.setSize(width,height);
            block2.setSize(width, height);
            block3.setSize(width, height);
            block1.setPosition(x, yPos + height*i);
            block2.setPosition(x + width, yPos + height*i);
            block3.setPosition(x + width*2, yPos + height*i);
            blocks.add(block1);
            blocks.add(block2);
            blocks.add(block3);
        }
    }

    public boolean checkCollisions(Rectangle checkFor) {
        Sprite remove = null;
        for(Sprite sprite: blocks) {
            if(sprite.getBoundingRectangle().overlaps(checkFor)) {
                remove = sprite;
                break;
            }
        }
        if(remove != null) {
            blocks.remove(remove);
            return true;
        }
        return false;
    }

    public void render(SpriteBatch batch) {
        for(Sprite sprite: blocks) {
            sprite.draw(batch);
        }
    }
}
