package display.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import debug.DebugValue;

import display.GamePanel;
import display.KeyAction;

import entity.MultiAnimatedEntity;

public class Player extends MultiAnimatedEntity {

    private double jumpSpeed = -8;
    private double speed = 15;

    public Player(int x, int y, ArrayList<GamePanel> panels,
            int frameW, int frameH, ArrayList<BufferedImage> images) {

        super(x, y, panels, frameW, frameH, images);

        setScale(0.5, 0.5);
        masks.add(GROUND);

    }

    public Player(int x, int y, GamePanel panel, int frameW,
            int frameH, ArrayList<BufferedImage> images) {
        super(x, y, panel, frameW, frameH, images);
        setScale(0.5, 0.5);

        masks.add(GROUND);
        panel.addExclusivePressedKeyAction(KeyEvent.VK_SPACE, () -> {
            if (grounded)
                velocityY = jumpSpeed;

            changeAnimation(2);

        });
        ArrayList<Integer> keysForLeft = new ArrayList<>();
        keysForLeft.add(KeyEvent.VK_LEFT);
        keysForLeft.add(KeyEvent.VK_A);

        ArrayList<Integer> keysForRight = new ArrayList<>();
        keysForRight.add(KeyEvent.VK_RIGHT);
        keysForRight.add(KeyEvent.VK_D);

        ArrayList<Integer> keysForRightKick = new ArrayList<>();
        keysForRightKick.add(KeyEvent.VK_D);
        keysForRightKick.add(KeyEvent.VK_K);

        panel.addPressedKeyAction(new KeyAction(keysForLeft, () -> {
                this.velocityX = -speed;
            

            if (grounded && getCurrentAnimaiton() != 1)
                changeAnimation(1);
        }));
        panel.addPressedKeyAction(new KeyAction(keysForRight, () -> {
                this.velocityX = speed;
            

            if (grounded && getCurrentAnimaiton() != 1)
                changeAnimation(1);

        }));


        panel.addCombinedPressedKeyAction(new KeyAction(keysForRightKick,
                () -> {
                    rotate(5);
                }));

    }

    @Override
    public void setClassResources() {

    }

    @Override
    public void unloadClassResources() {

    }

    @Override
    public ArrayList<DebugValue> debugValues() {
        ArrayList<DebugValue> vals = new ArrayList<>();
        vals.add(new DebugValue("Grounded: ", grounded + ""));

        return vals;
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void update() {
        if (grounded && velocityX == 0 && getCurrentAnimaiton() != 0) {
            changeAnimation(0);
        }
        // else if(grounded && velocityX!=0&& getCurrentAnimaiton() != 0){

        // }

    }

}
