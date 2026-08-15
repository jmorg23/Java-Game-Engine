package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import debug.DebugValue;
import display.GamePanel;

public class Ground extends GameEntity {

    public Ground(int x, int y, BufferedImage image, ArrayList<GamePanel> panels) {
        super(x, y, image, panels);
        layers.add(0);
        objectID = GROUND;
        affectedByGravity = false;
    }

    public Ground(int x, int y, BufferedImage image, GamePanel panel) {
        super(x, y, image, panel);
        layers.add(0);
        objectID = GROUND;
        affectedByGravity = false;

    }

    @Override
    public void setClassResources() {

    }

    
    @Override
    public ArrayList<DebugValue> debugValues() {
        ArrayList<DebugValue> values = new ArrayList<>();
        values.add(new DebugValue("", "", (Graphics2D g2) -> {
            g2.draw(getBounds());
        }));

        return values;    
    
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void update() {

    }

    @Override
    public void unloadClassResources() {

    }

}
