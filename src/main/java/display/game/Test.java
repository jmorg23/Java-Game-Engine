package display.game;

import java.awt.Graphics2D;

import display.GamePanel;
import entity.Ground;
import util.Imaging;

public class Test extends GamePanel {



    public Test(){
        // setBackgroundImage("/im/background.jpg");
        
        Player myPlayer = new Player(300, 0, Imaging.loadImage("/im/playertest.png"), this);
       // myPlayer.setBounce(true);
        Ground g = new Ground(0, 800, Imaging.loadImage("/im/test.jpg"), this);
        Ball b = new Ball(100, 0, Imaging.loadImage("/im/ball.jpg"), this);
        
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
