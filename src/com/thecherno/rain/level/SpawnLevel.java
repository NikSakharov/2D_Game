package com.thecherno.rain.level;

import com.thecherno.rain.entity.mob.Dummy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpawnLevel extends Level {

    public SpawnLevel(String path) {
        super(path);
    }

    protected void loadLevel (String path) {
        try{
            BufferedImage image = ImageIO.read(new File(path));
            int w = width = image.getWidth();
            int h = height = image.getHeight();
            tiles = new int[w * h];
            image.getRGB(0,0, w, h, tiles, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception! Could not load level file");
        }
        add(new Dummy(21,55));
    }

    protected void generateLevel() {
    }
}
