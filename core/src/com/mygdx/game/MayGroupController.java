package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by itjs3 on 06/06/2017.
 */
public class MayGroupController {

    ArrayList<Enemy> mays;
    float speed = 300;
    public MayGroupController(ArrayList<Enemy> mays) {
        this.mays = mays;
    }

    public void update(float delta) {
        double t = TimeUtils.millis();
        for(int i = 0; i < mays.size(); i++) {
            double this_t = t + i*100;
            double scale = 2 / (3 - Math.cos(2*this_t));
            double x = scale * Math.cos(this_t);
            double y = scale * Math.sin(2*this_t) / 2;

            y = 720 - y*100;
            x = x * 1280;

            mays.get(i).getSprite().setX((float)x);
            mays.get(i).getSprite().setY((float)y);


        }
    }
}
