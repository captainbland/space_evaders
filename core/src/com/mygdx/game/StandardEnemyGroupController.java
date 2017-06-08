package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by itjs3 on 03/06/2017.
 */
public class StandardEnemyGroupController {
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private float leftScreenBound, rightScreenBound;
    private float leftEnemyBound, rightEnemyBound;

    private int direction = 1;
    private int speed = 20;
    private long lastTime;
    private int updateInterval = 750;
    private static final int fallBy = 60;
    public StandardEnemyGroupController(ArrayList<Enemy> enemies, float leftScreenBound, float rightScreenBound, float leftEnemyBound, float rightEnemyBound) {
        this.leftScreenBound = leftScreenBound;
        this.rightScreenBound = rightScreenBound;
        this.leftEnemyBound = leftEnemyBound;
        this.rightEnemyBound = rightEnemyBound;
        this.enemies = enemies;

        lastTime = TimeUtils.millis();
    }

    public void update(float delta) {
        if(TimeUtils.timeSinceMillis(lastTime) > updateInterval) {
            lastTime = TimeUtils.millis();
            leftEnemyBound += speed * direction;
            rightEnemyBound += speed * direction;
            System.out.println("rightEnemyBound " + rightEnemyBound);
            System.out.println("rightScreenBound " + rightScreenBound);
            for (Enemy enemy : enemies) {
                enemy.getSprite().translateX(speed * direction);
            }

            if(leftEnemyBound < leftScreenBound) {
                direction = 1;
                updateInterval -= 55;
                for(Enemy enemy : enemies) {
                    enemy.getSprite().translateY(-fallBy);
                }
            } else if (rightEnemyBound > rightScreenBound) {
                direction = -1;
                updateInterval -= 55;
                for(Enemy enemy: enemies) {
                    enemy.getSprite().translateY(-fallBy);
                }
            }
        }
    }
}
