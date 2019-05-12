package com.thecherno.game.level.tile.spawn_level;

import com.thecherno.game.graphics.Screen;
import com.thecherno.game.graphics.Sprite;
import com.thecherno.game.level.tile.Tile;

public class SpawnGrassTile extends Tile {

    public SpawnGrassTile (Sprite sprite){
        super(sprite);
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }
}
