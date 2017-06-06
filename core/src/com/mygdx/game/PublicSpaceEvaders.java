package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class PublicSpaceEvaders extends Game {
	
	@Override
	public void create () {
		this.setScreen(new TitleScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}
}
