package util.C;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class YouTubeToMP3_GUI extends JFrame {

    private JTextField urlField;
    private JTextArea outputArea;
    private JButton downloadButton;

    public YouTubeToMP3_GUI() {
        setTitle("YouTube to MP3 Downloader");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        urlField = new JTextField();
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        downloadButton = new JButton("Download MP3");

        downloadButton.addActionListener((ActionEvent e) -> startDownload());

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(new JLabel("YouTube URL:"), BorderLayout.WEST);
        topPanel.add(urlField, BorderLayout.CENTER);
        topPanel.add(downloadButton, BorderLayout.EAST);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void startDownload() {
        String url = urlField.getText().trim();
        if (!url.startsWith("http")) {
            outputArea.append("Invalid URL. Please enter a valid YouTube link.\n");
            return;
        }

        downloadButton.setEnabled(false);
        outputArea.append("Starting download...\n");

        new Thread(() -> {
            try {
                File outputDir = new File("downloads");
                if (!outputDir.exists())
                    outputDir.mkdir();

                String ffmpegPath = "C:\\Users\\Jmorg23\\Downloads\\ffmpeg-master-latest-win64-gpl-shared\\ffmpeg-master-latest-win64-gpl-shared\\bin"; // <- change this

                String[] command = {
                        "C:\\Users\\Jmorg23\\Downloads\\yt-dlp",
                        "-x",
                        "--audio-format", "mp3",
                        "--ffmpeg-location", ffmpegPath,
                        "-o", "downloads/%(title)s.%(ext)s",
                        url
                };

                ProcessBuilder builder = new ProcessBuilder(command);
                builder.redirectErrorStream(true);
                Process process = builder.start();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String finalLine = line;
                        SwingUtilities.invokeLater(() -> outputArea.append(finalLine + "\n"));
                    }
                }

                int exitCode = process.waitFor();
                SwingUtilities.invokeLater(() -> {
                    if (exitCode == 0) {
                        outputArea.append("✅ Download complete! Check the 'downloads' folder.\n");
                    } else {
                        outputArea.append("❌ Download failed with exit code: " + exitCode + "\n");
                    }
                    downloadButton.setEnabled(true);
                });

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    outputArea.append("Error: " + ex.getMessage() + "\n");
                    downloadButton.setEnabled(true);
                });
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new YouTubeToMP3_GUI().setVisible(true));
    }
}
