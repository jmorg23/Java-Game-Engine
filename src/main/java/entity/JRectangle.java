package entity;

import java.awt.Graphics2D;

public class JRectangle extends Hitbox {

    public JRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public boolean collides(Hitbox hb) {

        if (hb instanceof JCircle) {

            double nearestX = Math.max(hb.x, Math.min(this.x, hb.x + hb.width));
            double nearestY = Math.max(hb.y, Math.min(this.y, hb.y + hb.height));
            double dx = this.x - nearestX;
            double dy = this.y - nearestY;
            return (dx * dx + dy * dy) < (((JCircle) hb).radius * ((JCircle) hb).radius);

        } else {

            int var2 = this.width;
            int var3 = this.height;
            int var4 = hb.width;
            int var5 = hb.height;
            if (var4 > 0 && var5 > 0 && var2 > 0 && var3 > 0) {
                int var6 = this.x;
                int var7 = this.y;
                int var8 = hb.x;
                int var9 = hb.y;
                var4 += var8;
                var5 += var9;
                var2 += var6;
                var3 += var7;
                return (var4 < var8 || var4 > var6) && (var5 < var9 || var5 > var7) && (var2 < var6 || var2 > var8)
                        && (var3 < var7 || var3 > var9);
            } else {
                return false;
            }

        }

    }

    public JRectangle intersects(JRectangle hb) {
        int left = Math.max(this.x, hb.x);
        int top = Math.max(this.y, hb.y);
        int right = Math.min(this.x + this.width, hb.x + hb.width);
        int bottom = Math.min(this.y + this.height, hb.y + hb.height);

        if (right > left && bottom > top) {
            return new JRectangle(left, top, right - left, bottom - top);
        }

        return null;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawRect(x, y, width, height);
    }
}
