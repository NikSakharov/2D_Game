package com.thecherno.game.level.tile;

import com.thecherno.game.graphics.Screen;
import com.thecherno.game.graphics.Sprite;

public class FlowerTile extends Tile {
    public FlowerTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }
}
