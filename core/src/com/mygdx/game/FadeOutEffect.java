package com.mygdx.game;

import com.badlogic.gdx.utils.Timer;

/**
 * Created by itjs3 on 03/06/2017.
 */
public class FadeOutEffect {
    final private Timer fadeTimer = new Timer();
    private final VoidCallback onEnd;
    private float opacity = 1.0f;

    public FadeOutEffect(VoidCallback onEnd) {
        this.onEnd = onEnd;
    }

    public void fadeOut(float amt) {
        opacity -= amt;
        if(opacity <= 0) {
            opacity = 0;
            fadeTimer.stop();
            onEnd.callback();
        }
    }

    public void start(float delay, float interval, final float byAmt) {
        fadeTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                fadeOut(byAmt);
            }
        }, delay, interval);
    }

    public float getOpacity() {
        return opacity;
    }
}
