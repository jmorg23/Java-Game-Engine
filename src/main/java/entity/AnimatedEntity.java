package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import display.GamePanel;

public abstract class AnimatedEntity extends GameEntity {

    protected int frameWidth, frameHeight;
    protected int framex = 0;
    protected int framey = 0;

    protected int iw, ih;

    public AnimatedEntity(int x, int y, BufferedImage image, GamePanel panel, int framew, int frameh) {
        super(x, y, image, panel);

        width = framew;
        height = frameh;
        iw = image.getWidth();
        ih = image.getHeight();
        frameWidth = image.getWidth() / framew;
        frameHeight = image.getHeight() / frameh;

    }

    public AnimatedEntity(int x, int y, BufferedImage image, ArrayList<GamePanel> panels, int framew, int frameh) {
        super(x, y, image, panels);

        width = framew;
        height = frameh;
        iw = image.getWidth();
        ih = image.getHeight();
        frameWidth = image.getWidth() / framew;
        frameHeight = image.getHeight() / frameh;

    }

    @Override
    public void updateEntity() {

        framex++;
        if (framex >= frameWidth) {

            if (framey >= frameHeight - 1) {
                framex = 0;
                framey = 0;
            } else {
                framex = 0;
                framey++;
            }
        }

        drawImage = image.getSubimage(framex * width, framey * height, width,
                height);

        drawShadowImage = shadowImage.getSubimage(framex * height, framey * height, width,
                height);

        super.updateEntity();

    }

}
