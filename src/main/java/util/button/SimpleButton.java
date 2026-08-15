package util.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

public class SimpleButton implements MouseListener, MouseMotionListener {

    /*
     * Simple button class
     * Uses similar ideas to the Custom button with much more simplicity
     * No circles, No key listeners, No sounds, No moving by center
     * 
     * 
     */

    

    private String text;
    private BufferedImage image, hoverImage;
    private Runnable target;
    private Font myFont = new Font("Arial", Font.PLAIN, 25);
    private Color color = Color.black, hovColor = Color.CYAN;
    private Rectangle bounds = new Rectangle();

    private boolean selected = false;
    private boolean visible = true;

    public String getText() {
        return text;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getHoverImage() {
        return hoverImage;
    }

    public void setHoverImage(BufferedImage hoverImage) {
        this.hoverImage = hoverImage;
    }

    public Runnable getTarget() {
        return target;
    }

    public void setTarget(Runnable target) {
        this.target = target;
    }

    public Font getMyFont() {
        return myFont;
    }

    public void setMyFont(Font myFont) {
        this.myFont = myFont;
        setSize();
    } 

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isSelected() {
        return selected;
    }

    public SimpleButton(String text, Runnable target) {
        if (!text.equals("")) {
            this.text = text;
        }
        this.target = target;

    }

    public SimpleButton(Runnable target) {
        this.target = target;
    }

    public void setText(String t) {
        text = t;
        setSize();

    }

    private void setSize() {
        if (image == null) {

            FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
            Rectangle2D bounds2D = myFont.getStringBounds(text, frc);

            bounds.width = (int) Math.ceil(bounds2D.getWidth());
            bounds.height = (int) Math.ceil(bounds2D.getHeight());

        } else {
            bounds.width = image.getWidth();
            bounds.height = image.getHeight();
        }
        
    }

    public void setMainColor(Color b) {
        color = b;
    }

    public void setHoverColor(Color b) {
        hovColor = b;
    }

    public void setTranslation(int x, int y) {

        this.bounds.x = x;
        this.bounds.y = y;

    }

    public void translate(int x, int y) {
        this.bounds.x += x;
        this.bounds.y += y;
    }

    public void draw(Graphics2D g2) {
        if(visible)
        if (image == null) {
            if (selected) {
                g2.setColor(hovColor);
            } else {
                g2.setColor(color);
            }
            g2.setFont(myFont);
            g2.drawString(text, bounds.x, bounds.y+bounds.height);
        } else {
            if (selected && hoverImage != null) {
                g2.drawImage(hoverImage, bounds.x, bounds.y, null);
            } else {
                g2.drawImage(image, bounds.x, bounds.y, null);

            }
        }
        //g2.draw(bounds);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (visible) {
            if (bounds.contains(e.getPoint())) {
                selected = true;
            } else {
                selected = false;
            }
        } else {
            selected = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(visible)
        if (SwingUtilities.isLeftMouseButton(e))
            if (selected) {
                target.run();
            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

}
