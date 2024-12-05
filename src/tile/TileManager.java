package tile;

import entity.Player;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;



public class TileManager {

    GamePanel gamePanel;
    public Tile[] tile;
    int[][] mapTileNum;
    Player player;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
//        this.player = player;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenRow][gamePanel.maxScreenCol];
        getTileImage();
        loadMap();
    }

    public void loadMap() {
        try (InputStream inputStream = getClass().getResourceAsStream("/maps/map4.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            for (int row = 0; row < gamePanel.maxScreenRow; row++) {
                // קורא שורה אחת
                String line = bufferedReader.readLine();
                // בודק שהשורה קיימת
                if (line == null) break;

                // מפצל את השורה לפי רווחים
                // מייצר מערך סטרינגים ובהמשך עובר עליו ומייצר מערך אחר שבו כל ערך מוחלף למספר
                String[] numbers = line.split(" ");
                for (int col = 0; col < gamePanel.maxScreenCol; col++) {
                    // ממיר כל ערך למספר ושומר במערך
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[row][col] = num;
                    if (tile[mapTileNum[row][col]].type.equals("point")) {
                        gamePanel.amountOfSmallPoints++;
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void setBlockPinky() {
//        int tileSize = gamePanel.tileSize;
//        int row = player.getPointInPixel()[1];// y
//        int col = player.getPointInPixel()[0]; // x
//        switch (player.direction) {
//            case "up" -> {
//                row = row + tileSize + player.speed / tileSize;
//                col = col / tileSize;
//                tile[getMap()[row][col]].isBlockedForPinky = false;
//            }
//            case "down" -> {
//                row = row - player.speed / tileSize;
//                col = col / tileSize;
//                tile[getMap()[row][col]].isBlockedForPinky = false;
//            }
//            case "left" -> {
//                row = row / tileSize;
//                col = col + tileSize + player.speed / tileSize;
//                tile[getMap()[row][col]].isBlockedForPinky = false;
//
//            }
//            case "right" -> {
//              row = row / tileSize;
//                col = col - player.speed / tileSize;
//                tile[getMap()[row][col]].isBlockedForPinky = false;
//            }
//        }
//
//        tile[getMap()[player.getPointOnMap()[0]][player.getPointOnMap()[1]]].isBlockedForPinky = true;
//    }

//    public void setBlockPinky() {
//        int tileSize = gamePanel.tileSize;
//        int row = player.getPointInPixel()[1];// y
//        int col = player.getPointInPixel()[0]; // x
//        switch (player.direction) {
//            case "up" -> {
//                row = row + tileSize + player.speed / tileSize;
//                col = col / tileSize;
//                getMap()[row][col] = 5 ;
//            }
//            case "down" -> {
//                row = row - player.speed / tileSize;
//                col = col / tileSize;
//                getMap()[row][col] = 5 ;
//            }
//            case "left" -> {
//                row = row / tileSize;
//                col = col + tileSize + player.speed / tileSize;
//                getMap()[row][col] = 5 ;
//
//            }
//            case "right" -> {
//                row = row / tileSize;
//                col = col - player.speed / tileSize;
//                getMap()[row][col] = 5 ;
//            }
//        }
//
//        getMap()[player.getPointOnMap()[0]][player.getPointOnMap()[1]] = 0;
//    }


    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].type = "empty";
            tile[0].isBlocked = false;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/wall.png")));
            tile[1].type = "wall";
            tile[1].isBlocked = true;


            tile[2] = new Tile();
            tile[2].type = "point";
            tile[2].isBlocked = false;
            tile[2].score = 1;

            tile[3] = new Tile();
//            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[3].type = "tunnel";
            tile[3].isBlocked = true;


            tile[4] = new Tile();
            tile[4].type = "test";
            tile[4].isBlocked = true;

            tile[5] = new Tile();
            tile[5].type = "border";
            tile[5].isBlocked = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void setMap() {
//        int[] num = player.getNextTileIndex(player.direction);
//        int currentTileNum = mapTileNum[num[1]][num[0]]; // שינוי לעמודה ושורה לפי אינדקס
//        if (currentTileNum == 2) {
//            // פעולה עבור איסוף נקודה
//            player.score += tile[currentTileNum].score;
//            mapTileNum[num[1]][num[0]] = 0; // שינוי הערך לאריח רגיל
//        }
//    }

    private BufferedImage createYellowCircleImage(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.YELLOW); // צבע העיגול
        int padding = size / 4; // מרווח לעיגול הקטן
        g2.fillOval(padding, padding, size / 2, size / 2); // יצירת עיגול
        g2.dispose();
        return image;
    }



    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int x = 0, y = 0;
        for (int row = 0; row < gamePanel.maxScreenRow; row++) {
            for (int col = 0; col < gamePanel.maxScreenCol; col++) {
                int tileNum = mapTileNum[row][col];
                Tile currentTile = tile[tileNum];

                // קריאה למתודת draw של Tile
                currentTile.draw(g2, x, y, gamePanel.tileSize);

                x += gamePanel.tileSize;
            }
            x = 0;
            y += gamePanel.tileSize;
        }
    }

    public int[][] getMap(){
        return mapTileNum;
    }



}

















//package tile;
//
//import java.awt.*;
//
//public class GameBoard {
//    private Tile[][] tiles; // מערך האריחים
//    private int tileSize;
//
//    public GameBoard(int rows, int cols, int tileSize) {
//        this.tileSize = tileSize;
//        tiles = new Tile[rows][cols];
//        initializeTiles();
//    }
//
//    private void initializeTiles() {
//        for (int row = 0; row < tiles.length; row++) {
//            for (int col = 0; col < tiles[0].length; col++) {
//                boolean isSolid = (row == 0 || col == 0 || row == tiles.length - 1 || col == tiles[0].length - 1); // רק דוגמה
//                tiles[row][col] = new Tile(col * tileSize, row * tileSize, tileSize,);
//            }
//        }
//    }
//
//    public void draw(Graphics g) {
//        for (Tile[] row : tiles) {
//            for (Tile tile : row) {
//                tile.draw(g);
//            }
//        }

//    }
//}
