package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import display.ControlPanel;
import display.GamePanel;

public abstract class AnimatedEntity extends GameEntity {

    protected int frameWidth, frameHeight;
    protected int framex = 0;
    protected int framey = 0;

    public AnimatedEntity(int x, int y, BufferedImage image, GamePanel panel, int framew, int frameh) {
        super(x, y, image, panel);
        this.frameWidth = framew;
        this.frameHeight = frameh;

    }

    public AnimatedEntity(int x, int y, BufferedImage image, ArrayList<GamePanel> panels, int framew, int frameh) {
        super(x, y, image, panels);
        this.frameWidth = framew;
        this.frameHeight = frameh;

    }

    @Override
    public void updateEntity() {
        if(!visible) {
            return;
        }
        if (affectedByGravity) {
            velocityY = velocityY - ControlPanel.GRAVITY_EFFECT;
        }
        framex++;
        if (framex * frameWidth >= image.getWidth()) {
            if (framey * frameHeight >= image.getHeight()) {
                framex = 0;
                framey = 0;
            } else {
                framex = 0;
                framey++;
            }
        }


        bounds.setBounds((int)x, (int)y, width, height);
        transform.setToTranslation(x, y);
        transform.scale(1, 1);

        update();

        y -= velocityY;
        x += velocityX;
    }

    @Override
    public final void drawEntity(Graphics2D g2) {
        if (!visible) {
            return;
        }



        g2.drawImage(image.getSubimage(framex * (width/frameWidth), framey * (height/frameHeight), frameWidth, frameHeight), transform,
                null);

        draw(g2);
    }

}
