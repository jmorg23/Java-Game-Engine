package display;

import java.awt.Graphics2D;
import java.awt.Graphics;

import java.awt.geom.AffineTransform;

public class Camera {
    public AffineTransform transform = new AffineTransform();
    public Graphics2D graphics;


    public Graphics2D setGraphics(Graphics g){
        graphics = (Graphics2D) g;
        graphics.transform(transform);
        
        return graphics;
    }
    public void zoom(double amt){
        transform.scale(amt, amt);
        
    }
    public void translate(double x, double y){
        transform.translate(x, y);
        
    }
    public void moveTo(double x, double y){
        transform.setToTranslation(x, y);
        


    }
    public void setScale(double amt){

        transform.setToScale(amt, amt);


    }
}
