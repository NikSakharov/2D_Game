package com.thecherno.rain.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {

    private String path;
    public final int SIZE;
    public int[] pixels;

    public static SpriteSheet tiles = new SpriteSheet("src\\res\\textures\\sheets\\spritesheet.png", 256);
    public static SpriteSheet spawn_level = new SpriteSheet("src\\res\\textures\\sheets\\spawn_level.png", 256);
    public static SpriteSheet projectile_wizard = new SpriteSheet("src\\res\\textures\\sheets\\projectiles\\wizard.png", 48);

    public SpriteSheet(String path, int size) {
        this.path = path;
        SIZE = size;
        pixels = new int[SIZE*SIZE];
        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0,0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
