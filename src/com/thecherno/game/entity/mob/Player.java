package com.thecherno.game.entity.mob;

import com.thecherno.game.Game;
import com.thecherno.game.entity.projectile.Projectile;
import com.thecherno.game.entity.projectile.WizardProjectile;
import com.thecherno.game.events.EventDispatcher;
import com.thecherno.game.events.EventListener;
import com.thecherno.game.events.types.MousePressedEvent;
import com.thecherno.game.events.types.MouseReleasedEvent;
import com.thecherno.game.graphics.AnimatedSprite;
import com.thecherno.game.graphics.Screen;
import com.thecherno.game.graphics.Sprite;
import com.thecherno.game.graphics.SpriteSheet;
import com.thecherno.game.graphics.ui.*;
import com.thecherno.game.input.Keyboard;
import com.thecherno.game.input.Mouse;
import com.thecherno.game.util.ImageUtils;
import com.thecherno.game.util.Vector2i;
import com.thecherno.game.events.Event;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Mob implements EventListener {

    private String name;
    private Keyboard input;
    private Sprite sprite;
    private boolean walking = false;

    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3 );
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3 );
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3 );
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3 );

    private AnimatedSprite animSprite = down;

    private int fireRate = 0;

    private UIManager ui;
    private UIProgressBar uiHealthBar;
    private UIButton button;

    private BufferedImage image;

    private boolean shooting = false;

    @Deprecated
    public Player(String name, Keyboard input) {
        this.name = name;
        this.input = input;
        sprite = Sprite.player_forward;


    }

    public Player(String name, int x, int y, Keyboard input) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.input = input;
        sprite = Sprite.player_forward;
        fireRate = WizardProjectile.FIRE_RATE;

        ui = Game.getUiManager();
        UIPanel panel = (UIPanel) new UIPanel(new Vector2i((300 - 80) * 3, 0), new Vector2i(80 * 3, 168 * 3)).setColor(0x4f4f4f);
        ui.addPanel(panel);
        UILabel nameLabel = new UILabel(new Vector2i(40,200), name);
        nameLabel.setColor(0xbbbbbb);
        nameLabel.setFont(new Font("Verdana", Font.PLAIN, 24));
        nameLabel.dropShadow = true;
        panel.addComponent(nameLabel);

        uiHealthBar = new UIProgressBar(new Vector2i(10, 215), new Vector2i(80 * 3 - 20, 20));
        uiHealthBar.setColor(0x6a6a6a);
        uiHealthBar.setForegroundColor(0xee3030);
        panel.addComponent(uiHealthBar);

        UILabel hpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2,16)), "HP");
        hpLabel.setColor(0xffffff);
        hpLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.addComponent(hpLabel);
        // Player default attributes
        health = 100;

        button = new UIButton(new Vector2i(10, 260), new Vector2i(100, 30), new UIActionListener() {
            public void perform() {
                System.out.println("Action performed");
            }
        });

        button.setText("Hello");
        panel.addComponent(button);

        try {
            image = ImageIO.read(new File("src\\res\\textures\\home.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        UIButton imageButton = new UIButton(new Vector2i(10, 360), image, new UIActionListener() {
            public void perform() {
                System.exit(0);
            }
        });
        imageButton.setButtonListener(new UIButtonListener(){
            public void entered(UIButton button) {
                button.setImage(ImageUtils.changeBrightness(image, -50));
            }

            public void exited(UIButton button){
                button.setImage(image);
            }

            public void pressed(UIButton button) {
                button.setImage(ImageUtils.changeBrightness(image,50));
            }

            public void released(UIButton button) {
                button.setImage(image);
            }
        });
        panel.addComponent(imageButton);
    }

    public String getName(){
        return name;
    }

    public void onEvent(Event event){
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent)e));
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent)e));
    }

    public void update(){
        if(walking) animSprite.update();
        else animSprite.setFrame(0);
        if(fireRate > 0) fireRate--;
        double xa = 0, ya = 0;
        double speed = 2;
        if(input.up) {
            animSprite = up;
            ya -= speed;
        }
        else if(input.down) {
            animSprite = down;
            ya += speed;
        }
        if(input.left) {
            animSprite = left;
            xa -= speed;
        }
        else if(input.right) {
            animSprite = right;
            xa += speed;
        }

        if(xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }
        clear();
        updateShooting();

        uiHealthBar.setProgress(health / 100.0);
    }

    private void updateShooting(){
        if(!shooting || fireRate > 0)
            return;

        double dx = Mouse.getX() - Game.getWindowWidth() / 2;
        double dy = Mouse.getY() - Game.getWindowHeight() / 2;
        double dir = Math.atan2(dy,dx);
        shoot(x,y,dir);
        fireRate = WizardProjectile.FIRE_RATE;
    }

    public boolean onMousePressed(MousePressedEvent e){
        if(Mouse.getX() > 660)
            return false;

        if (e.getButton() == MouseEvent.BUTTON1) {
            shooting = true;

            return true;
        }
        return false;
    }

    public boolean onMouseReleased(MouseReleasedEvent e){
        if (e.getButton() == MouseEvent.BUTTON1){
            shooting = false;
            return true;
        }
        return false;
    }

    private void clear() {
        for(int i = 0; i < level.getProjectiles().size(); i++) {
            Projectile p = level.getProjectiles().get(i);
            if (p.isRemoved()) level.getProjectiles().remove(i);
        }
    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob(x - 16, y - 16, sprite);
    }

}
