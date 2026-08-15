package display.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import debug.DebugValue;

import display.GamePanel;
import display.KeyAction;
import entity.GameEntity;

public class Player extends GameEntity {

    private int jumpSpeed = -5;
    private int speed = 15;

    public Player(int x, int y, BufferedImage image, ArrayList<GamePanel> panels) {
        super(x, y, image, panels);
        setScale(0.5, 0.5);
        masks.add(GROUND);

    }

    public Player(int x, int y, BufferedImage image, GamePanel panel) {
        super(x, y, image, panel);
        setScale(0.5, 0.5);

        masks.add(GROUND);
        panel.addExclusivePressedKeyAction(KeyEvent.VK_SPACE, ()->{
            if(grounded)
            velocityY=jumpSpeed;
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

    
        panel.addPressedKeyAction(new KeyAction(keysForLeft, ()->{this.x-=speed;}));
        panel.addPressedKeyAction(new KeyAction(keysForRight, ()->{this.x+=speed;}));
        panel.addCombinedPressedKeyAction(new KeyAction(keysForRightKick, ()->{rotate(5);}));

        
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
        vals.add(new DebugValue("Grounded: ", grounded+""));

        return vals;
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void update() {

    }

}
