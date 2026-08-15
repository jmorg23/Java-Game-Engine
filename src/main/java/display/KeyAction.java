package display;

import java.util.ArrayList;

public class KeyAction {

    // If any key code is true then all actions will be executed
    private ArrayList<Integer> keyCodes = new ArrayList<>();
    private ArrayList<Runnable> actions = new ArrayList<>();
    public boolean active = true;
    public boolean combineKeys = false;


    public ArrayList<Integer> getKeyCodes() {
        return keyCodes;
    }
    public ArrayList<Runnable> getActions() {
        return actions;
    }
    
    public KeyAction(ArrayList<Integer> keyCodes, ArrayList<Runnable> actions) {
        this.keyCodes = keyCodes;
        this.actions = actions;
    }
    public KeyAction(int keyCode, Runnable action) {
        this.keyCodes.add(keyCode);
        this.actions.add(action);
    }
    public KeyAction(ArrayList<Integer> keyCodes, Runnable action) {
        this.keyCodes = keyCodes;
        this.actions.add(action);
    }
    public KeyAction(int keyCode, ArrayList<Runnable> actions) {
        this.keyCodes.add(keyCode);
        this.actions = actions;
    }

    public void addKeyAction(Runnable a) {
        actions.add(a);
    }

}
