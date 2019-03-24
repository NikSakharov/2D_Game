package com.thecherno.rain.entity;

import com.thecherno.rain.graphics.Screen;
import com.thecherno.rain.graphics.Sprite;
import com.thecherno.rain.level.Level;

import java.util.Random;

public class Entity {

    protected int x, y;
    protected Sprite sprite;
    private boolean removed = false;
    protected Level level;
    protected final Random random = new Random();

    public void update() {
    }

    public void render(Screen screen) {
        if(sprite != null) screen.renderSprite( x, y, sprite, true);
    }

    public void remove() {
        //Remove from level
        removed = true;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void init (Level level) {
        this.level = level;
    }
}
