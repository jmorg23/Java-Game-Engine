package display;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.GameEntity;

public class LoadingScreen extends GamePanel {

    private static int myInd = -999;

    public static void load(ArrayList<GameEntity> entities, int nextPanel) {

        load(entities, nextPanel, 1);
    }

    public static void load(Runnable r, int nextPanel) {
        load(r, nextPanel, 1);
    }

    public static void load(ArrayList<GameEntity> entities, int nextPanel, int delay) {
        if (myInd != -999)
            myInd = new LoadingScreen().myIndex;

        try {

            GamePanel.changePanel(myInd);
            new Thread(() -> {

                for (GameEntity e : entities) {
                    e.setResources();
                }
                GamePanel.changePanel(nextPanel);

            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load(Runnable r, int nextPanel, int delay) {
        if (myInd != -999)
            myInd = new LoadingScreen().myIndex;

        try {

            GamePanel.changePanel(myInd);
            new Thread(() -> {

                r.run();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GamePanel.changePanel(nextPanel);
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
