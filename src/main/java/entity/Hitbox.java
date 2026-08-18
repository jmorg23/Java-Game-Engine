package entity;

import java.awt.Graphics2D;


public abstract class Hitbox {


    public int x;
    public int y;
    public int width, height;

    public Hitbox(int x, int y, int width, int height){
        this.x = x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    public Hitbox(){
        
    }


    public abstract boolean collides(Hitbox hb);
    public abstract void draw(Graphics2D g2);


}
