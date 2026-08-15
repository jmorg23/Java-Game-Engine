package debug;

public class DebugValue {
    private String name;
    private String value;
    private PaintMethod drawAlso;

    public PaintMethod getPaint(){
        return drawAlso;
    }
    public DebugValue(String name, String value) {
        this.name = name;
        this.value = value;
    }
    public DebugValue(String name, String value, PaintMethod da) {
        this.name = name;
        this.value = value;
        drawAlso = da;
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }
}
