package debug;

public class Log {
    public boolean showTimestamp = true;
    public String message;

    public String log() {
        if (showTimestamp) {
            return "[" + System.currentTimeMillis() + "] " + message;
        } else {
            return message;
        }
    }

    public Log(String message) {
        this.message = message;
    }
}
