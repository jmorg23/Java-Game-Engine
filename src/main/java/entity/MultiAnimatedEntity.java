package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import display.GamePanel;

public abstract class MultiAnimatedEntity extends AnimatedEntity {

    private ArrayList<BufferedImage> images = new ArrayList<>();
    private int imageWidth, imageHeight;

    public MultiAnimatedEntity(int x, int y, BufferedImage image, GamePanel panel, int individualframew, int individualframeh, ArrayList<BufferedImage> images) {
        super(x, y, image, panel, 0, 0);
        this.images = images;
        image = images.get(0);
        imageWidth = individualframew;
        imageHeight = individualframeh;

        frameWidth = image.getWidth() / individualframew;
        frameHeight = image.getHeight() / individualframeh;
    
    }

    public MultiAnimatedEntity(int x, int y, BufferedImage image, ArrayList<GamePanel> panels, int individualframew, int individualframeh, ArrayList<BufferedImage> images) {
        super(x, y, image, panels, 0, 0);
        this.images = images;
        image = images.get(0);
        imageWidth = individualframew;
        imageHeight = individualframeh;

        frameWidth = image.getWidth() / individualframew;
        frameHeight = image.getHeight() / individualframeh;
    }
    
    public void changeAnimation(int index){
        if(index < images.size() && index >= 0){
            image = images.get(index);
            framex = 0;
            framey = 0;
            frameWidth = image.getWidth() / imageWidth;
            frameHeight = image.getHeight() / imageHeight;
        }
    }
    
    
    
}
