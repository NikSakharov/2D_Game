package com.thecherno.game.level.tile.spawn_level;

import com.thecherno.game.graphics.Screen;
import com.thecherno.game.graphics.Sprite;
import com.thecherno.game.level.tile.Tile;

public class SpawnWall1Tile extends Tile {

    public SpawnWall1Tile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }

    public boolean solid() {
        return true;
    }
}
