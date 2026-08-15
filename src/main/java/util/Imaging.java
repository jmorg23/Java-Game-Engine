package util;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class Imaging {


    public static BufferedImage loadImage(String path) {
        try {
            return javax.imageio.ImageIO.read(Imaging.class.getResourceAsStream(path));
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static BufferedImage scaleToScreen(BufferedImage image, int screenWidth, int screenHeight) {
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        // Calculate the scaling factor to maintain aspect ratio
        double scaleFactor = Math.min((double) screenWidth / originalWidth, (double) screenHeight / originalHeight);

        // Calculate the new dimensions
        int newWidth = (int) (originalWidth * scaleFactor);
        int newHeight = (int) (originalHeight * scaleFactor);

        // Create a new BufferedImage with the new dimensions
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, image.getType());

        // Draw the original image onto the scaled image
        java.awt.Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return scaledImage;
    }

    public static BufferedImage changeHue(BufferedImage image, float hueOffset) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage modifiedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                Color originalColor = new Color(rgb);
                float[] hsb = Color.RGBtoHSB(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(),
                        null);

                // Modify the hue component by the specified offset
                float modifiedHue = (hsb[0] + hueOffset / 360.0f) % 1.0f;
                if (modifiedHue < 0) {
                    modifiedHue += 1.0f;
                }

                // Convert modified HSB color back to RGB
                int modifiedRGB = Color.HSBtoRGB(modifiedHue, hsb[1], hsb[2]);

                // Set the pixel in the modified image
                modifiedImage.setRGB(x, y, modifiedRGB);
            }
        }

        return modifiedImage;
    }

    //the brightnessfactor is very sensitive having the range as 0-5 i think
    public static BufferedImage adjustBrightness(BufferedImage originalImage, double brightnessScale) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage adjustedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        float scaleFactor = (float) brightnessScale / 24;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = originalImage.getRGB(x, y);

                // Extracting the color components
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Adjusting brightness
                red = (int) (red * scaleFactor);
                green = (int) (green * scaleFactor);
                blue = (int) (blue * scaleFactor);

                // Clipping values to the valid range [0, 255]
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));

                // Creating the adjusted color
                int adjustedRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;

                adjustedImage.setRGB(x, y, adjustedRGB);
            }
        }

        return adjustedImage;
    }


}
