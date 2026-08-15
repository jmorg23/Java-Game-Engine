package debug;

public class Logger {

    public static void log(String message) {
        System.out.println(new Log(message).log());
    }
    public void log(Log log) {
        System.out.println(log.log());
    }


    
}
