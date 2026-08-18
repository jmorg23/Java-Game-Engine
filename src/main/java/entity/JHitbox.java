package entity;

import java.util.ArrayList;
import java.awt.Graphics2D;

public class JHitBox {
    private ArrayList<Hitbox> boxes = new ArrayList<>();

    public JHitBox() {

    }

    public JHitBox(Hitbox[] hbs) {
        for (Hitbox h : hbs) {
            boxes.add(h);
        }
    }

    public JHitBox(ArrayList<Hitbox> hbs) {
        boxes.addAll(hbs);
    }

    public ArrayList<Hitbox> getBoxes() {
        return boxes;
    }

    public void addBox(Hitbox hb) {
        boxes.add(hb);
    }

    public boolean collides(JHitBox jhb) {

        for (Hitbox hb : jhb.getBoxes()) {
            for (Hitbox h : boxes) {
                if (h.collides(hb)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Hitbox> collidingBoxes(JHitBox jhb) {
        ArrayList<Hitbox> collHit = new ArrayList<>();

        for (Hitbox hb : jhb.getBoxes()) {
            for (Hitbox h : boxes) {
                if (h.collides(hb)) {
                    collHit.add(h);
                }
            }
        }
        return collHit;
    }

    public void draw(Graphics2D g2) {
        for (Hitbox hb : boxes) {
            hb.draw(g2);
        }
    }

}
