package util.Sounds;

public class SoundPlayer {



public SoundPlayer() {
    }
    public static final int INTRO_ID = -1;
    public static final Song INTRO = new Song("/mus/intro.mp3");
    public static final SoundEffect SE_CLICK = new SoundEffect("/sounds/click.wav");
    public static final SoundEffect SE_SELECT = new SoundEffect("/sounds/select.wav");

    public static void loadSongs(int id) {
        try {
            switch (id) {
                case -1:
                    INTRO.preloadSound();
                    break;
                case 0:
                    // Songs.LEVEL_SELECT.preloadSound();
                    break;
                case 1:
                    // Songs.LELVEL_1_MAIN.preloadSound();
                    break;
                case 2:
                    // Songs.LELVEL_2_MAIN.preloadSound();
                    break;
                case 3:
                    // Songs.LELVEL_3_MAIN.preloadSound();
                    break;
                case 4:
                    // Songs.LELVEL_4_MAIN.preloadSound();
                    break;
                case 5:
                    // Songs.LELVEL_5_MAIN.preloadSound();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error loading song: " + id);
            // ExceptionHandler.handle(e);
        }
    }

    public static void unloadSongs(int id) {
        switch (id) {
            case -1:
                INTRO.unloadSound();
                break;
            case 1:
                // Songs.LELVEL_1_MAIN.unloadSound();
                break;
            case 2:
                // Songs.LELVEL_2_MAIN.unloadSound();
                break;
            case 3:
                // Songs.LELVEL_3_MAIN.unloadSound();
                break;
            case 4:
                // Songs.LELVEL_4_MAIN.unloadSound();
                break;
            case 5:
                // Songs.LELVEL_5_MAIN.unloadSound();
                break;
        }
    }

    public static void unloadAllSongs() {

        INTRO.unloadSound();

    }

    public static void stopAllSongs() {

        INTRO.stopSound();

    }

}