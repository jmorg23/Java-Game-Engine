package util.Sounds;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SoundPlayerRef {

    // public static void main(String[] args) {
    //     try {
    //         Sounds.setRes();
    //         Sounds.HOME_1.play();
    //         Thread.sleep(5000);
    //         Sounds.HOME_1.stop();
    //         Thread.sleep(5000);
    //         Sounds.HOME_1.play();
    //         Thread.sleep(5000);


    //     } catch (FileNotFoundException | JavaLayerException | InterruptedException e) {

    //         e.printStackTrace();
    //     }

    // }

    public enum Sounds {
        HOME_1();

        Sounds() {


        }

        private Thread myThread;
        private AdvancedPlayer myPlayer;
        //private PlaybackListener list;

        private static String[] songs = new String[] {
                "/mus/My Song 11.mp3"
        };
        // private FileInputStream myInStream;

        public static void setRes() throws FileNotFoundException, JavaLayerException {
            int ind = 0;
            for (Sounds sound : Sounds.values()) {
                sound.myPlayer = new AdvancedPlayer(new FileInputStream(songs[ind]));
              //  sound.list = sound.myPlayer.getPlayBackListener();
            }

        }

        public void play() {
            new Thread(()->{
            try {

              //  System.out.println("a");
                    myPlayer.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();


        }

        public boolean isPlaying() {
            return myThread.isAlive();
        }

        public void stop() {

            myPlayer.close();
        }

        public void reset() {
            myPlayer.stop();
        }

    }

}
