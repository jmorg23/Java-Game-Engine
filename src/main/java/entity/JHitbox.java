package entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class JHitbox {


    public int x;
    public int y;
    public int width, height;


    public abstract boolean Collides(JHitbox hb);
    public abstract void draw(Graphics2D g2);


}
