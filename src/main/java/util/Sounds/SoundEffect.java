package util.Sounds;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect {
  

        // private AudioInputStream audioStream;
        private String filePath;
        private byte[] soundData;
        private AudioFormat audioFormat;
        private Clip fastClip;
        private float volume = 6.0f; // Default volume (0.0f = full volume)
        private int myID = -1;
        private static int lastID = -1;

        SoundEffect(String soundFile) {
            myID = lastID;
            lastID++;
            this.filePath = soundFile;

            try {
                loadSound();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Preload the audio file into memory
        public void loadSound() throws Exception {
            try (BufferedInputStream bufferedStream = new BufferedInputStream(
                    SoundPlayer.class.getResourceAsStream(filePath));) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedStream);

                // AudioFormat.Encoding encoding = audioStream.getFormat().getEncoding();
                // if (encoding != AudioFormat.Encoding.PCM_SIGNED) {
                audioStream = convertToCompatibleFormat(audioStream);
                // }

                audioFormat = audioStream.getFormat();
                soundData = audioStream.readAllBytes(); // Load the sound data into memory
                fastClip = getCompatibleClip();

                // Open the Clip with a ByteArrayInputStream of the sound data
                AudioInputStream stream = new AudioInputStream(
                        new ByteArrayInputStream(soundData),
                        audioFormat,
                        soundData.length / audioFormat.getFrameSize());
                fastClip.open(stream);
            }
        }

        // Play the sound, allowing for overlapping playback
        // Play the sound with overlapping capability
        public void playSound() {
            try {
                if (soundData == null || audioFormat == null) {
                    System.err.println("Sound not loaded. Call loadSound() first.");
                    return;
                }

                new Thread(() -> {
                    try {
                        // Create a new Clip instance for each playback
                        Clip clip = getCompatibleClip();

                        // Open the Clip with a ByteArrayInputStream of the sound data
                        AudioInputStream stream = new AudioInputStream(
                                new ByteArrayInputStream(soundData),
                                audioFormat,
                                soundData.length / audioFormat.getFrameSize());
                        clip.open(stream);
                        setVolume(clip);
                        clip.start();

                        // Cleanup after playback
                        clip.addLineListener(event -> {
                            if (event.getType() == LineEvent.Type.STOP) {
                                clip.close();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        // ExceptionHandler.handle(e);

                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        public void stopSound() {
            try {
                fastClip.stop();
            } catch (Exception e) {

            }
        }

        public void playSoundFast() {
            // if (fastClip == null) {
            // System.err.println("Sound not loaded or fastClip not initialized.");
            // return;
            // }
            try {
                if (fastClip.isRunning()) {
                    fastClip.stop(); // Stop current playback if running
                }
                setVolume(fastClip);
                fastClip.setFramePosition(0); // Rewind to the beginning
                fastClip.start(); // Play the sound
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        public void playAndFinishSoundFast() {
            // if (fastClip == null) {
            // System.err.println("Sound not loaded or fastClip not initialized.");
            // return;
            // }
            try {
                if (fastClip.isRunning()) {
                    return;
                } else {
                    fastClip.stop(); // Stop current playback if running
                    fastClip.setFramePosition(0); // Rewind to the beginning
                    setVolume(fastClip);
                    fastClip.start(); // Play the sound

                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        private void setVolume(Clip clip) {
            try {
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float clampedVolume = Math.max(gainControl.getMinimum(),
                            Math.min(volume, gainControl.getMaximum()));
                    gainControl.setValue(clampedVolume);

                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

   

        // Finds a working audio mixer that supports Clips
        private static Clip getCompatibleClip() {
            Mixer.Info[] mixers = AudioSystem.getMixerInfo();
            for (Mixer.Info mixerInfo : mixers) {
                try {
                    Mixer mixer = AudioSystem.getMixer(mixerInfo);
                    if (mixer.isLineSupported(new Line.Info(Clip.class))) {
                        System.out.println("Using Mixer: " + mixerInfo.getName());
                        return (Clip) mixer.getLine(new Line.Info(Clip.class));
                    }
                } catch (Exception e) {
                    System.out.println("Mixer " + mixerInfo.getName() + " does not support Clip.");
                }
            }
            return null; // No compatible mixer found
        }

        // Converts any WAV file to a guaranteed supported format
        private static AudioInputStream convertToCompatibleFormat(AudioInputStream audioStream)
                throws UnsupportedAudioFileException, IOException {
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100, // Standard sample rate
                    16, // Bit depth
                    baseFormat.getChannels(), // Preserve original channels
                    baseFormat.getChannels() * 2, // Frame size (16-bit per channel)
                    44100, // Frame rate
                    false // Little-endian
            );

            return AudioSystem.getAudioInputStream(targetFormat, audioStream);
        }

        public void setVolume(float volume) {
            this.volume = volume;
        }
    
}
