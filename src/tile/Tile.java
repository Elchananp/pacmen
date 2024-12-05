package tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean isBlocked = false;
    public boolean isBlockedForPinky = false;
    public int score = 0;
    public String type;

    public void draw(Graphics2D g2, int x, int y, int tileSize) {
        if (image != null) {
            // גודל התמונה קטן יותר מגודל האריח
            int imageWidth = tileSize; // מתאים לגודל האריח
            int imageHeight = tileSize;

            // מיקום התמונה במרכז האריח
            int imageX = x + (tileSize - imageWidth) / 2;
            int imageY = y + (tileSize - imageHeight) / 2;

            // ציור התמונה
            // אם אין תמונה, צייר צבע בסיסי בהתאם לסוג האריח
            g2.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);
        } else {
            switch (type) {
                case "empty" -> {
//                    g2.setColor(Color.BLACK);
//                    g2.fillRect(x, y, tileSize, tileSize);
                }
                case "point" -> {
                    g2.setColor(Color.YELLOW);
                    int padding = tileSize / 4;
                    g2.fillOval(x + padding, y + padding, tileSize / 2, tileSize / 2);
                }
                case "wall" -> {
                    g2.setColor(Color.GRAY);
                    g2.fillRect(x, y, tileSize, tileSize);
                }
                case "tunnel" -> {
//                    g2.setColor(Color.BLACK);
//                    g2.fillRect(x, y, tileSize, tileSize);
                }
                case "border" -> {
                    g2.setColor(Color.red);
                    g2.fillRect(x, y, tileSize, tileSize);
                }
                default -> {
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillRect(x, y, tileSize, tileSize);
                }
            }
        }
    }
}
