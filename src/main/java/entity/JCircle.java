package entity;

import java.awt.Graphics2D;

public class JCircle extends Hitbox {

    public double radius;

    @Override
    public boolean collides(Hitbox hb) {

        if (hb instanceof JCircle) {
            double dist = Math.sqrt(Math.pow(x - hb.x, 2) + Math.pow(y - hb.y, 2));
            if (dist < radius + ((JCircle) hb).radius) {
                return true;
            }
        } else {
            double nearestX = Math.max(hb.x, Math.min(this.x, hb.x + hb.width));
            double nearestY = Math.max(hb.y, Math.min(this.y, hb.y + hb.height));
            double dx = this.x - nearestX;
            double dy = this.y - nearestY;
            return (dx * dx + dy * dy) < (this.radius * this.radius);

        }

        return false;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
    }

}
