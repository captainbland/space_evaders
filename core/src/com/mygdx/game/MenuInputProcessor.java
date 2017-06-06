package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

/**
 * Created by jaron on 01/06/2017.
 */
public class MenuInputProcessor implements InputProcessor {

    private PublicSpaceEvaders game;
    private ScreenChanger screenChanger;

    public MenuInputProcessor(ScreenChanger screenChanger) {
       this.screenChanger = screenChanger;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(Input.Keys.SPACE == keycode || Input.Keys.ENTER == keycode) {
            screenChanger.changeScreen();
            System.out.println("Calling a change screen!");
            return true;
        }
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
        //game.setScreen(nextScreen);
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
