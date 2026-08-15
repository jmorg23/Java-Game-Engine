package display;

import java.util.ArrayList;
import java.util.HashSet;

import debug.DebugValue;
import debug.Debugger;
import debug.Logger;
import entity.GameEntity;
import savor.Game;
import util.GlobalTick;
import util.Imaging;
import util.button.CustomButton;
import util.button.SimpleButton;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public abstract class GamePanel implements KeyListener {

    private static ArrayList<GamePanel> panels = new ArrayList<>();
    public int myIndex;
    protected static HashSet<Integer> pressedKeys = new HashSet<>();
    protected static HashSet<Integer> releasedKeys = new HashSet<>();

    private static Camera gameCam = new Camera();
    private static Panel mainPanel;
    private ArrayList<SimpleButton> frontButtons = new ArrayList<>();
    public BufferedImage backgroundImage;
    private ArrayList<KeyAction> pressedKeyActions = new ArrayList<>();
    private ArrayList<KeyAction> releasedKeyActions = new ArrayList<>();

    private ArrayList<GameEntity> entities = new ArrayList<>();
    public static final int LAYERS = 5;

    public void addEntity(GameEntity entity) {
        entities.add(entity);

    }

    public void setBackgroundImage(String path) {
        backgroundImage = Imaging.scaleToScreen(Imaging.loadImage(path), ControlPanel.FRAME_W, ControlPanel.FRAME_H);
    }

    public void addButton(SimpleButton button) {
        frontButtons.add(button);
        mainPanel.addMouseListener(button);
        mainPanel.addMouseMotionListener(button);
    }

    public void removeButton(SimpleButton button) {
        frontButtons.remove(button);
        mainPanel.removeMouseListener(button);
        mainPanel.removeMouseMotionListener(button);
    }

    public Camera getCamera() {
        return gameCam;
    }

    static {
        mainPanel = new Panel();

    }

    public static Panel getMainPanel() {
        return mainPanel;
    }

    private static GamePanel currentPanel;

    private static int curDebugger = 0;

    public GamePanel() {
        myIndex = panels.size();
        panels.add(this);

        addReleasedKeyAction(KeyEvent.VK_Y, () -> {
            for (GameEntity e : entities) {
                e.getDebugger().active = false;

            }
            entities.get(curDebugger).getDebugger().active = true;

            ControlPanel.debugMode = !ControlPanel.debugMode;
        });

        addReleasedKeyAction(KeyEvent.VK_OPEN_BRACKET, () -> {
            curDebugger--;
            for (GameEntity e : entities) {
                e.getDebugger().active = false;

            }
            if (0 > curDebugger) {
                curDebugger = entities.size() - 1;

                // curDebugger--;
            }
            entities.get(curDebugger).getDebugger().active = true;

        });
        addReleasedKeyAction(KeyEvent.VK_CLOSE_BRACKET, () -> {
            curDebugger++;

            for (GameEntity e : entities) {
                e.getDebugger().active = false;

            }
            if (entities.size() <= curDebugger) {
                curDebugger = 0;

            }
            entities.get(curDebugger).getDebugger().active = true;

        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = gameCam.setGraphics(g);
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, ControlPanel.FRAME_W, ControlPanel.FRAME_H, null);
        }

        draw(g2);

        for (GameEntity entity : entities) {
            entity.drawEntity(g2);
            if (ControlPanel.debugMode) {

                entity.getDebugger().draw(g2);
            }
        }

        for (SimpleButton button : frontButtons) {
            button.draw(g2);
        }

        if (ControlPanel.debugMode) {
            // debugger.draw(g2);
        }

    }

    public void checkCollisions() {

        for (int i = 0; i < LAYERS; i++) {
            checkLayer(i);
        }

    }

    private void checkLayer(int l) {
        ArrayList<GameEntity> collidingEntities = new ArrayList<>();

        for (GameEntity ge : entities) {
            if (ge.getLayers().contains(l)) {
                collidingEntities.add(ge);

            }

        }
        for (GameEntity ge : entities) {

            if (ge.getMasks().contains(l)) {
                for (GameEntity g : collidingEntities)
                    if (!ge.equals(g)) {

                        if (ge.getBounds().intersects(g.getBounds()))
                            ge.collidesWith(g, l);
                    }
            }
        }
    }

    private void checkWillCollide() {

    }

    public abstract void update();

    public abstract void draw(Graphics2D g2);

    public abstract void gainFocus();

    public abstract void loseFocus();

    public static void changePanel(int index) {
        if (currentPanel != null) {
            currentPanel.loseFocus();
            mainPanel.removeKeyListener(currentPanel);
        }

        currentPanel = panels.get(index);

        pressedKeys.clear();
        releasedKeys.clear();

        mainPanel.addKeyListener(currentPanel);
        currentPanel.gainFocus();

        mainPanel.setGamePanel(currentPanel);

        GlobalTick.setTarget(() -> {

            currentPanel.updatePanel();
            mainPanel.render();

        });
        GlobalTick.startTick();
    }

    public void addExclusivePressedKeyAction(int keyCode, Runnable action) {
        addExclusivePressedKeyAction(new KeyAction(keyCode, action));
    }

    public void addExclusivePressedKeyAction(KeyAction action) {
        // pressedKeyActions.clear();
        action.addKeyAction(() -> {
            action.active = false;
        });
        releasedKeyActions.add(new KeyAction(action.getKeyCodes(), (() -> {
            action.active = true;

        })));
        pressedKeyActions.add(action);

    }

    public void addCombinedPressedKeyAction(int keyCode, Runnable action) {
        addCombinedPressedKeyAction(new KeyAction(keyCode, action));
    }

    public void addCombinedPressedKeyAction(KeyAction action) {
        // pressedKeyActions.clear();
        action.combineKeys = true;
        action.addKeyAction(() -> {
            action.active = false;
        });
        releasedKeyActions.add(new KeyAction(action.getKeyCodes(), (() -> {
            action.active = true;

        })));
        pressedKeyActions.add(action);

    }

    public void addPressedKeyAction(int keyCode, Runnable action) {
        pressedKeyActions.add(new KeyAction(keyCode, action));
    }

    public void addReleasedKeyAction(int keyCode, Runnable action) {
        releasedKeyActions.add(new KeyAction(keyCode, action));
    }

    public void addPressedKeyAction(KeyAction action) {
        pressedKeyActions.add(action);
    }

    public void addReleasedKeyAction(KeyAction action) {
        releasedKeyActions.add(action);
    }

    private void sendKeyStrokes() {

        handlePressedKeys();

        for (Integer keyCode : releasedKeys) {
            keyReleased(keyCode);
        }
    }

    private void keyReleased(int keyCode) {
        for (KeyAction action : releasedKeyActions) {

            if (action.getKeyCodes().contains(keyCode)) {
                for (Runnable r : action.getActions()) {

                    r.run();
                }
            }
        }

    }

    private void handlePressedKeys() {

        for (KeyAction action : pressedKeyActions) {

            if (action.active)
                if (action.combineKeys) {

                    boolean allPressed = true;

                    for (Integer code : action.getKeyCodes()) {
                        if (!pressedKeys.contains(code)) {
                            allPressed = false;
                            break;
                        }
                    }

                    if (allPressed) {
                        action.active = false;

                        for (Runnable r : action.getActions()) {
                            r.run();
                        }
                    }
                } else {
                    for (Integer i : action.getKeyCodes())

                        if (pressedKeys.contains(i)) {

                            for (Runnable r : action.getActions()) {
                                r.run();
                            }
                            break;
                        }
                }
        }

    }

    private void updatePanel() {

        sendKeyStrokes();
        releasedKeys.clear();
        // pressedKeys.clear();

        for (GameEntity entity : entities) {
            entity.updateEntity();
        }
        checkCollisions();

        update();

    }

    protected void addButton(CustomButton button) {
        mainPanel.addMouseListener(button.getMyAction());
        mainPanel.addMouseMotionListener(button.getMyAction());
    }

    protected void removeButton(CustomButton button) {
        mainPanel.removeMouseListener(button.getMyAction());
        mainPanel.removeMouseMotionListener(button.getMyAction());
    }

    public void addMouseListeners(MouseListener m) {
        mainPanel.addMouseListener(m);
    }

    public void addMouseMotionListeners(MouseMotionListener m) {
        mainPanel.addMouseMotionListener(m);
    }

    public void removeMouseListeners(MouseListener m) {
        mainPanel.removeMouseListener(m);
    }

    public void removeMouseMotionListeners(MouseMotionListener m) {
        mainPanel.removeMouseMotionListener(m);
    }

    @Override
    public final void keyReleased(KeyEvent e) {

        pressedKeys.removeIf(i -> i == e.getKeyCode());
        releasedKeys.add((Integer) e.getKeyCode());
    }

    @Override
    public final void keyPressed(KeyEvent e) {

        pressedKeys.add(e.getKeyCode());

    }

    @Override
    public final void keyTyped(KeyEvent e) {

    }

}
