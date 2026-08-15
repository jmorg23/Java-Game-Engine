package display.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import debug.DebugValue;
import display.GamePanel;
import entity.GameEntity;

public class Ball extends GameEntity {

    public Ball(int x, int y, BufferedImage image, GamePanel panel) {
        super(x, y, image, panel);
        // layers.add(0);
        masks.add(0);
        setScale(0.2, 0.2);
        setBounce(true);
        velocityX = 1;
        circular = true;
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
        vals.add(new DebugValue("Ball Vel Y: ", ""+velocityY));
        return vals;
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void update() {
    }
    
}
