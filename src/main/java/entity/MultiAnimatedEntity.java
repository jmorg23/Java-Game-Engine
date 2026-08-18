package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import display.GamePanel;

public abstract class MultiAnimatedEntity extends AnimatedEntity {

    private ArrayList<BufferedImage> images = new ArrayList<>();
    private int currentAnimation = 0;

    public MultiAnimatedEntity(int x, int y, GamePanel panel, int individualframew, int individualframeh,
            ArrayList<BufferedImage> images) {
        super(x, y, images.get(0), panel, individualframew, individualframeh);
        this.images = images;

    }

    public MultiAnimatedEntity(int x, int y, ArrayList<GamePanel> panels, int individualframew, int individualframeh,
            ArrayList<BufferedImage> images) {
        super(x, y, images.get(0), panels, individualframew, individualframeh);
        this.images = images;

    }

    public int getCurrentAnimaiton() {
        return currentAnimation;
    }

    public void changeAnimation(int index) {

        if (index < images.size() && index >= 0) {
            System.out.println("changingto: " + index);
            currentAnimation = index;
            image = images.get(index);
            framex = 0;
            framey = 0;

            iw = image.getWidth();
            ih = image.getHeight();
            frameWidth = image.getWidth() / width;
            frameHeight = image.getHeight() / height;

            shadowImage = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D sg = shadowImage.createGraphics();
            sg.drawImage(image, 0, 0, null);
            sg.setComposite(AlphaComposite.SrcIn);
            sg.setColor(Color.BLACK);
            sg.fillRect(0, 0, image.getWidth(), image.getHeight());
            sg.dispose();

            
        }
    }

}
