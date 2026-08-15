package debug;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

public class Debugger {


    private ArrayList<DebugValue> debugValues = new ArrayList<>();
    public boolean printDebug = false;
    public boolean active = false;

    public Debugger() {

    }
    public void clear(){
        debugValues.clear();
    }

    
    public  void addDebugValue(String name, String value) {
        debugValues.add(new DebugValue(name, value));
    }

    public void addDebugValue( DebugValue dv) {
        debugValues.add(dv);
    }

    public void printDebugValues() {
        if (printDebug) {
            System.out.println("Debug Values:");
            for (
            DebugValue value : debugValues) {
                System.out.println(value.toString());
            }
        }
    }

    

    public void draw(java.awt.Graphics2D g) {


        g.setColor(Color.RED);
        if (active) {

            printDebugValues();
            g.setFont(new Font("Arial", Font.PLAIN, 40));
                        g.drawString("Values:", 100, 50);

            for (int i = 0; i < debugValues.size(); i++) {
                DebugValue value = debugValues.get(i);
                g.setColor(Color.RED);

                g.drawString(value.toString(), 100, 100 + i * 50);
                g.setColor(Color.BLUE);

                if (value.getPaint() != null) {

                    value.getPaint().paint(g);
                }
            }
        } else {
            for (int i = 0; i < debugValues.size(); i++) {
                DebugValue value = debugValues.get(i);

                if (value.getPaint() != null) {
                    value.getPaint().paint(g);
                }
            }
        }
        g.setColor(Color.RED);

    }

}
