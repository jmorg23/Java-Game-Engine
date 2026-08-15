package util.Sounds;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javazoom.jl.player.Player;

public class Song {
    
        private String filePath;
        private byte[] soundData;

        private Player myPlayer;
        private boolean loop = false;
        public int myID = -1;
        public static int lastID = -1;


        public Song(String filePath) {
            myID = lastID;
            this.filePath = filePath;
            lastID++;
            
            try {
                preloadSound();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public boolean isLooping() {
            return loop;
        }

        void preloadSound() throws Exception {
            try (InputStream fis = SoundPlayer.class.getResourceAsStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }

                soundData = baos.toByteArray();
            }
        }

        void unloadSound() {
            stopSound();
            soundData = null;
            myPlayer = null;
        }

        public void playSound() {
            if (soundData == null) {
                //System.err.println("Sound not loaded. Call preloadSound() first.");
                return;
            }

            new Thread(() -> {
                try (ByteArrayInputStream bais = new ByteArrayInputStream(soundData)) {
                    Player player = new Player(bais);
                    player.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        public void stopSound() {
            loop = false;
            if (myPlayer != null)
                myPlayer.close();
        }

        public void loopSound() {
            loop = true;
            new Thread(() -> {
                while (loop) {
                    try (ByteArrayInputStream bais = new ByteArrayInputStream(soundData)) {
                        myPlayer = new Player(bais);
                        myPlayer.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                        loop = false;
                    }
                }
            }).start();

        }

        public void unloopSound() {
            loop = false;

        }

        public void setVolume() {

        }





}
