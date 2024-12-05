package entity;

import main.GamePanel;
import tile.Tile;
import tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;


public class Inky extends Entity {
    BufferedImage image;
    TileManager tileManager;
    GamePanel gamePanel;
    boolean exit = false;
//    String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
    String[] directions = {"up", "down", "left", "right"};
    Random random = new Random();
    int randomInt;
    String nextStep;
    String direction = "def";
    boolean debug = false; // משתנה לשליטה בהדפסות הבדיקות
    public boolean inHome = true;

    public Inky(GamePanel gamePanel, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        setDefaultValue();
        getImage();

    }

    @Override
    public void update() {
        if (!inHome) {
            if (!exit) {
                exit(); // בצע את פעולת היציאה
            } else {
                chooseDirection();
                move();
            }
        }
    }


    @Override
    public void move() {
        int indexTile = getNumOfTile(direction);
        Tile[] tiles = tileManager.tile;

        if (debug) debugMove(indexTile); // קריאה לפונקציית העזר

        if (direction.equals("up") && !tiles[indexTile].isBlocked) {
            y -= speed;

        } else if (direction.equals("down") && !tiles[indexTile].isBlocked) {
            y += speed;

        } else if (direction.equals("right") && !tiles[indexTile].isBlocked) {
            x += speed;

        } else if (direction.equals("left") && !tiles[indexTile].isBlocked) {
            x -= speed;
        }
    }

    @Override
    public void setDefaultValue() {
        this.x = 275;
        this.y = 375;
        this.speed = 3;
        this.life = 1;
    }

    public void exit() {
        if (debug) debugExit(); // קריאה לפונקציית העזר

        if (x < 325) {
            x += speed;
        } else if (y > 250) {
            y -= speed;
        } else {
            exit = true; // ציין שהיציאה הושלמה
            nextStep = "right";
        }
    }

    public boolean hasPath(String nextStep){
        alignToTile();
        int index = getNumOfTile(nextStep);
        Tile currentTile = null;

        if (!nextStep.equals("def") && index >= 0) {
            currentTile = tileManager.tile[index];

            return !currentTile.isBlocked;
        }
        return false;
    }

    @Override
    public void changeDirection(String nextStep) {
//        if (debug) debugChangeDirection(nextStep); // קריאה לפונקציית העזר
            if (hasPath(nextStep)){
                direction = nextStep;
//                System.out.println("direction: " + direction);
            }

    }

    public void alignToTile() {
        int tileSize = gamePanel.tileSize;
        int gapX = x % tileSize;
        int gapY = y % tileSize;

        if (gapX > 0 && gapX < speed) {
            x -= gapX;
        } else if (gapX > tileSize - speed) {
            x += (tileSize - gapX);
        }

        if (gapY > 0 && gapY < speed) {
            y -= gapY;
        } else if (gapY > tileSize - speed) {
            y += (tileSize - gapY);
        }
    }

    public int getNumOfTile(String nextPoint) {
        int[] index = getNumOfTileIndex(nextPoint);
        if (debug) debugGetNumOfTile(nextPoint, index); // קריאה לפונקציית העזר

        if (index[0] >= 0 && index[1] >= 0) {
            return tileManager.getMap()[(index[0])][(index[1])];
        }
        return -1;
    }

    public int[] getNumOfTileIndex(String nextPoint) {
        int row = -1, col = -1;
        int tileSize = gamePanel.tileSize;

        if (debug) debugGetNumOfTileIndex(nextPoint); // קריאה לפונקציית העזר

        switch (nextPoint) {
            case "up" -> {
                row = (y - speed) / tileSize;
                int xLeft = x / tileSize;
                int xRight = (x + tileSize - 1) / tileSize;
                if (xRight == xLeft) col = xLeft;
            }
            case "down" -> {
                row = (y + tileSize - 1 + speed) / tileSize;
                int xLeft = x / tileSize;
                int xRight = (x + tileSize - 1) / tileSize;
                if (xRight == xLeft) col = xLeft;
            }
            case "left" -> {
                col = (x - speed) / tileSize;
                int yUp = y / tileSize;
                int yDown = (y + tileSize - 1) / tileSize;
                if (yUp == yDown) row = yUp;
            }
            case "right" -> {
                col = (x + tileSize - 1 + speed) / tileSize;
                int yUp = y / tileSize;
                int yDown = (y + tileSize - 1) / tileSize;
                if (yUp == yDown) row = yUp;
            }
        }

        if (row >= 0 && row < gamePanel.maxScreenRow && col >= 0 && col < gamePanel.maxScreenCol) {
            return new int[]{row, col};
        }

        return new int[]{-1, -1};
    }

    @Override
    public void chooseDirection() {
        if ((direction.equals("up") || direction.equals("down")) && (hasPath("left") || hasPath("right"))){
        randomInt = random.nextInt(directions.length);
        nextStep = directions[randomInt];
        changeDirection(nextStep);
//        System.out.println(nextStep);
        } else if ((direction.equals("right") || direction.equals("left")) && (hasPath("up") || hasPath("down"))) {
            randomInt = random.nextInt(directions.length);
            nextStep = directions[randomInt];
            changeDirection(nextStep);
//            System.out.println(nextStep);
        } else if (!hasPath(direction)) {
            randomInt = random.nextInt(directions.length);
            nextStep = directions[randomInt];
            changeDirection(nextStep);
//            System.out.println(nextStep);
        }

    }

    public void getImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/ghost-inky-down.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    // פונקציות עזר להדפסות
    private void debugMove(int indexTile) {
        System.out.println("Moving. Direction: " + direction + ", Tile Index: " + indexTile);
    }

    private void debugExit() {
        System.out.println("Exiting. Current Position: x = " + x + ", y = " + y);
    }

    private void debugChangeDirection(String nextStep) {
        System.out.println("Changing Direction. Next Step: " + nextStep + ", Current Direction: " + direction);
    }

    private void debugGetNumOfTile(String nextPoint, int[] index) {
        System.out.println("Getting Tile Number. Next Point: " + nextPoint + ", Index: " + index[0] + ", " + index[1]);
    }

    private void debugGetNumOfTileIndex(String nextPoint) {
        System.out.println("Getting Tile Index. Direction: " + nextPoint + ", Current Position: x = " + x + ", y = " + y);
    }

    public int[] getPoint(){
        return new int[] {y / gamePanel.tileSize, x / gamePanel.tileSize };
    }
}








