package display;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import display.game.Test;
import util.GlobalTick;
import util.Sounds.SoundPlayer;
import util.button.SimpleButton;


public class ControlPanel extends GamePanel {

    public static final int FRAME_W = 1000, FRAME_H = 1000;
    public static final int FPS = 30;
    private static final double GRAVITY = 9.8;
    public static final double GRAVITY_EFFECT = GRAVITY / FPS;

    public static boolean debugMode = false;
    private static JFrame frame = new JFrame("NAME");


    private static SimpleButton button1;

    private LevelSelect levelSelect = new LevelSelect();

    private Test t = new Test();
    public ControlPanel() {

        GlobalTick.setTick(FPS);
        initGraphics();
        changePanel(myIndex);
        setBackgroundImage("/im/background.jpg");
        
        button1 = new SimpleButton(() -> {

            // GamePanel.changePanel(levelSelect.myIndex);
                        GamePanel.changePanel(t.myIndex);


        });
        button1.setText("Button 1");
        button1.setTranslation(FRAME_W / 2 - 50, FRAME_H / 2 - 25);
        addButton(button1);
    
    }

    public static void main(String[] args) {
        new SoundPlayer();
        SoundPlayer.INTRO.playSound();
        SwingUtilities.invokeLater(() -> {
            new ControlPanel();
        });

    }

    public void initGraphics() {
        frame.setSize(FRAME_W, FRAME_H);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(GamePanel.getMainPanel());
        frame.setVisible(true);
        GamePanel.getMainPanel().initBufferStrategy();

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
