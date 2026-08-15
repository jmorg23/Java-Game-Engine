package debug;

public class TimedLog {

    private Logger logger;
    private Log log;
    private long interval;
    private boolean running = false;

    public TimedLog(Logger logger, Log log, long interval) {
        this.logger = logger;
        this.interval = interval;
        this.log = log;

    }

    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                if(!running) break;
                logger.log(log.log());
            }
        }).start();
    }
    public void stop() {
        running = false;
    }

}
