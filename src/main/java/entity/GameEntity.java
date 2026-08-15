package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import debug.DebugValue;
import debug.Debugger;
import debug.HasDebug;
import display.ControlPanel;
import display.GamePanel;
import util.Imaging;

public abstract class GameEntity implements HasDebug {

    protected double x, y;
    private double scalex = 1, scaley = 1;
    private double rotation = 0;

    protected void rotate(double deg) {
        rotation += Math.toRadians(deg);
    }

    protected void setRot(double deg) {
        rotation = Math.toRadians(deg);

    }

    protected BufferedImage image;
    protected int width, height;
    protected AffineTransform transform = new AffineTransform();
    protected Rectangle bounds;
    protected boolean affectedByGravity = true;
    protected double velocityX = 0;
    protected double velocityY = 0;
    public boolean visible = true;

    public int objectID = 0;
    protected ArrayList<Integer> layers = new ArrayList<>();
    protected ArrayList<Integer> masks = new ArrayList<>();

    protected boolean grounded = false;
    private boolean resSet = false;

    private boolean bounce = false;
    private static final double BOUNCE_HEIGHT = 80;
    private double accelerationX, accelerationY;
    private double angularVelocity = 0;
    protected boolean circular = false;

    protected Debugger debugger = new Debugger();

    public Debugger getDebugger() {
        return debugger;
    }

    public void setBounce(boolean bounce) {
        this.bounce = bounce;
    }

    protected void setScale(double x, double y) {
        scalex = x;
        scaley = y;
    }

    protected void addScale(double x, double y) {
        scalex += x;
        scaley += y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public final void unloadResources() {
        if (resSet) {
            resSet = false;
            unloadClassResources();
        }
    }

    public final void setResources() {
        if (!resSet) {
            resSet = true;
            setClassResources();
        }
    }

    public static final int GROUND = 0, COLLECTABLE = 1, ACTIVATABLE = 2, WALL = 3, HURTBOX = 4, HITBOX = 5,
            MOVING_PLATFORM = 6;

    public ArrayList<Integer> getLayers() {
        return layers;
    }

    public ArrayList<Integer> getMasks() {
        return masks;
    }

    protected BufferedImage loadImage(String path) {
        return Imaging.loadImage(path);
    }

    @Override
    public final void getDebugValues() {

        debugger.clear();
        debugger.addDebugValue(new DebugValue("X: ", x + ""));
        debugger.addDebugValue(new DebugValue("Y: ", y + ""));
        debugger.addDebugValue(new DebugValue("Velocity X: ", velocityX + ""));
        debugger.addDebugValue(new DebugValue("Velocity Y: ", velocityY + ""));
        debugger.addDebugValue(new DebugValue("Bounds: ", bounds.toString()));

        debugger.addDebugValue(new DebugValue("", "", (Graphics2D g2) -> {
            g2.draw(getBounds());
        }));

        // ArrayList<DebugValue> debugValues = debugValues();
        // if (debugValues != null) {
        //     // .addAll(debugValues());
        // }

        
    }

    public GameEntity(int x, int y, BufferedImage image, GamePanel panel) {

        this.x = x;
        this.y = y;
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        bounds = new Rectangle(x, y, (int) (width * scalex), (int) (height * scaley));
        panel.addEntity(this);
    }

    public GameEntity(int x, int y, BufferedImage image, ArrayList<GamePanel> panels) {

        this.x = x;
        this.y = y;
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        bounds = new Rectangle(x, y, (int) (width * scalex), (int) (height * scaley));
        for (GamePanel p : panels) {
            p.addEntity(this);
        }
    }

    private double beforeVelY = 0, beforeVelX = 0;

    public void updateEntity() {

        if (ControlPanel.debugMode) {
           getDebugValues();
        }
        if (affectedByGravity) {
            velocityY = velocityY + ControlPanel.GRAVITY_EFFECT;
        }
        if (Math.abs(velocityY) > ControlPanel.GRAVITY_EFFECT) {
            grounded = false;
        }
        if (Math.abs(velocityY + beforeVelY) < Math.abs(velocityY)) {
            accelerationY = velocityY;
        } else {
            accelerationY = velocityY - beforeVelY;
        }
        if (Math.abs(velocityX + beforeVelX) < Math.abs(velocityX)) {
            accelerationX = velocityX;
        } else {
            accelerationX = velocityX - beforeVelX;
        }

        beforeVelY = velocityY;

        y += velocityY;
        x += velocityX;
        rotation += angularVelocity;

        bounds.setBounds((int) x, (int) y, (int) (width * scalex), (int) (height * scaley));
        transform.setToTranslation(x, y);
        transform.translate(bounds.width / 2, bounds.height / 2);
        transform.rotate(rotation);
        transform.translate(-bounds.width / 2, -bounds.height / 2);
        transform.scale(scalex, scaley);

        update();

    }

    public void drawEntity(Graphics2D g2) {
        if (!visible) {
            return;
        }
        if (image != null) {
            g2.drawImage(image, transform, null);
        } else {
            g2.fill(bounds);
        }
        draw(g2);

    }

    public void collidesWith(GameEntity ge, int layer) {

        switch (layer) {
            case GROUND:

                if (bounce && velocityY > 1) {
                    double i = stopColliding(ge.getBounds(), 1);

                    velocityY += ControlPanel.GRAVITY_EFFECT * (1 - velocityY / i);

                    y--;
                    bounce(ge.getBounds(), 1);
                } else {
                    stopColliding(ge.getBounds(), 1);
                    velocityY = 0;
                    grounded = true;
                }
                break;

            default:
                break;
        }

    }

    // dir 1 go up, 2 go down, 3 go right 4 go left
    private void bounce(Rectangle b, double dir) {

        double velPercent = BOUNCE_HEIGHT / 100;
        velocityY = (-velocityY) * velPercent;

        if (circular)
            angularVelocity = velocityX / 25.0;

    }

    // dir 1 go up, 2 go down, 3 go right 4 go left
    private double stopColliding(Rectangle b, int dir) {
        double i = 0;
        switch (dir) {
            case 1:
                while (bounds.intersects(b)) {
                    i++;
                    bounds.y -= 1;

                }
                y = ++bounds.y;
                break;

            default:
                break;
        }
        return velocityY - i;
    }

    public abstract void setClassResources();

    public abstract void unloadClassResources();

    public abstract ArrayList<DebugValue> debugValues();

    public abstract void draw(Graphics2D g2);

    public abstract void update();

}
