package display.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import display.GamePanel;
import entity.Ground;
import util.Imaging;

public class Test extends GamePanel {

    public Test() {
        // setBackgroundImage("/im/background.jpg");
        Ground g = new Ground(0, 500, Imaging.loadImage("/im/test.jpg"), this);

        ArrayList<BufferedImage> playerImages = new ArrayList<>();

        playerImages.add(Imaging.loadImage("/im/idle.png"));
        playerImages.add(Imaging.loadImage("/im/running.png"));
        playerImages.add(Imaging.loadImage("/im/runandjump.png"));

        Player myPlayer = new Player(300, 0, this, 300, 300, playerImages);
        // myPlayer.setBounce(true);
        // Ball b = new Ball(100, 0, Imaging.loadImage("/im/ball.jpg"), this);

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void gainFocus() {

    }

    @Override
    public void loseFocus() {

    }

}
