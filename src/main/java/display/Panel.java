package display;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Panel extends Canvas {

    private static GamePanel currentPanel;

    public void setGamePanel(GamePanel pan) {
        currentPanel = pan;
    }

    public Panel() {
        setSize(ControlPanel.FRAME_W, ControlPanel.FRAME_H);
        setIgnoreRepaint(true); // We'll handle rendering manually
    }

    // Call this once after adding the canvas to the frame and making the frame
    // visible
    public void initBufferStrategy() {
        requestFocusInWindow();
        createBufferStrategy(2); // Double buffering
    }


    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // Clear screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        draw(g);

        g.dispose();
        bs.show(); // Show the drawn frame

    }

    public void draw(Graphics g) {
        currentPanel.paintComponent(g);
    }

    public BufferedImage getSnapshot() {
        BufferedImage snapshot = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = snapshot.createGraphics();

        // Copy exactly what your render method does here:
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Replace this with your actual drawing code
        draw(g2);

        g2.dispose();
        return snapshot;
    }

}
