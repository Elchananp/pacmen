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
import java.util.Arrays;
import java.util.Stack;

public class Clyde extends Entity {
    BufferedImage image;
    TileManager tileManager;
    GamePanel gamePanel;
    Bfs bfs;
    Player player;

    boolean exit = false;
    String[] directions = {"up", "down", "left", "right"};
    Random random = new Random();
    int randomInt;
    String nextStep;
    String direction = "def";
    boolean debug = true; // משתנה לשליטה בהדפסות הבדיקות
    int[] point;
    public boolean inHome = true;

//    Stack<String> pathStack = new Stack<>();

    public Clyde(GamePanel gamePanel, TileManager tileManager, Bfs bfs, Player player) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        this.player = player;
        this.bfs = bfs;
        setDefaultValue();
        getImage();
    }

    @Override
    public void update() {
        if (!inHome){
        if (!exit) {
            exit();
            System.out.println("in home false");
//            getPath();
        }else {
            getPath();
            chooseDirection();
            changeDirection(nextStep);
            move();
//            col();
        }}
//        exit = true;
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
        this.x = 13 * gamePanel.tileSize;
        this.y = 15 * gamePanel.tileSize;
//        this.x = 12 * gamePanel.tileSize;
//        this.y = 15 * gamePanel.tileSize;
        this.speed = 3;
        this.life = 1;
    }

    public void getPath() {
        int tileSize = gamePanel.tileSize;
        int[] playerPosition = {player.y / tileSize, player.x / tileSize};
        int[] clydePosition = {y / tileSize, x / tileSize};

        System.out.println("Player Position: " + Arrays.toString(playerPosition));
        System.out.println("Clyde Position: " + Arrays.toString(clydePosition));
//        System.out.println("Current Tile Map:");
//        debugMap(tileManager.getMap());
//        Stack<String> pathStack = new Stack<>();


        bfs.findShortestPath(tileManager.getMap(), clydePosition, playerPosition);
//        System.out.println("Shortest path: " );
//        while (!bfs.pathStack.isEmpty()){
//            System.out.println(Arrays.toString(bfs.pathStack.pop()));
//        }
    }

//    public void exit() {
//        System.out.println("exit is running");
//        if (debug) debugExit();
//
//        if (x < 325) {
//            x += speed;
//        } else if (y > 250) {
//            y -= speed;
//        } else {
//            exit = true;
//            nextStep = "right";
//        }
//    }


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


    public boolean hasPath(String nextStep) {
        alignToTile();
        int index = getNumOfTile(nextStep);
        Tile currentTile = null;

        if (!nextStep.equals("def") && index >= 0) {
            currentTile = tileManager.tile[index];

            // בדיקת חסימה
            if (currentTile != null && !currentTile.isBlocked) {
                return true;
            }
        }
        return false;
    }


    @Override
//    public void changeDirection(String nextStep) {
//        if (debug) debugChangeDirection(nextStep); // קריאה לפונקציית העזר
//        if (hasPath(nextStep)) {
//            direction = nextStep;
//            System.out.println("Direction changed to: " + direction);
//        }
//    }

    public void changeDirection(String nextStep) {
//        System.out.println("changeDirection start, next step: " + nextStep);
        alignToTile();
        int[] index = getNumOfTileIndex(nextStep);
//        int index = getNumOfTile(nextStep);
        Tile cureentTile = null;
        System.out.println("index: " + Arrays.toString(index) + " " + "point: " + Arrays.toString(point));
        if (Arrays.equals(index, point)){
//            System.out.println("changeDirection");
            direction = nextStep;
            bfs.pathStack.pop();
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

        if (debug) {
            System.out.println("Aligning to tile. Position adjusted to: x = " + x + ", y = " + y);
        }
    }

    public int getNumOfTile(String nextPoint) {
        int[] index = getNumOfTileIndex(nextPoint);
        if (debug) debugGetNumOfTile(nextPoint, index); // קריאה לפונקציית העזר

        if (index[0] >= 0 && index[1] >= 0) {
            return tileManager.getMap()[index[0]][index[1]];
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
//    public void moveAlongPath() {
//        if (!bfs.pathStack.isEmpty()) {
//            // קבלת הצעד הבא מהמחסנית
//            String nextStep = bfs.pathStack.pop();
//
//            // שינויים בכיוון השחקן לפי הצעד הבא
//            changeDirection(nextStep);
//
//            // הזזת השחקן לכיוון החדש
//            move();
//        } else {
//            System.out.println("No path left in the stack.");
//        }
//    }


    @Override
    public void chooseDirection() {
        int newY = y / gamePanel.tileSize;
        int newX = x / gamePanel.tileSize;
       Stack<int[]> pathStack =  bfs.pathStack;
        if (pathStack.isEmpty()) {
            System.out.println("pathStack.isEmpty()");
            return;
        }

             point = bfs.pathStack.peek();
        if (point[0] < newY ){
            nextStep = "up";
            changeDirection(nextStep);
        } else if (point[0] > newY) {
            nextStep = "down";
            changeDirection(nextStep);
        } else if (point[1] > newX) {
            nextStep = "right";
            changeDirection(nextStep);
        } else if (point[1] < newX) {
            nextStep = "left";
            changeDirection(nextStep);
        }else {
            nextStep = "def";
//            direction = "def";
        }
        System.out.println("nextStep from chooseDirection: " + nextStep);

//      nextStep = decideDirection(new int[]{y / gamePanel.tileSize, x / gamePanel.tileSize}, nextPoint);
//      changeDirection(nextStep);
    }
//    public String decideDirection(int[] currentPoint, int[] nextPoint) {
//        int deltaX = nextPoint[0] - currentPoint[0];  // חישוב ההבדל בציר X
//        int deltaY = nextPoint[1] - currentPoint[1];  // חישוב ההבדל בציר Y
//
//        if (deltaX > 0) {
//            return "right";  // אם הצעד הבא הוא ימינה
//        } else if (deltaX < 0) {
//            return "left";  // אם הצעד הבא הוא שמאלה
//        } else if (deltaY > 0) {
//            return "down";  // אם הצעד הבא הוא למטה
//        } else if (deltaY < 0) {
//            return "up";  // אם הצעד הבא הוא למעלה
//        }
//
//        return "def";  // במקרה שאין שינוי, יוחזר ברירת מחדל
//    }


    public void getImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/ghost-clyde-down.png")));
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
        System.out.println("Getting Tile Index. Next Point: " + nextPoint);
    }

    private void debugMap(int[][] map) {
        for (int[] row : map) {
            for (int tile : row) {
                System.out.print(tile + " ");
            }
            System.out.println();
        }
    }
    public int[] getPoint(){
       return new int[] {y / gamePanel.tileSize, x / gamePanel.tileSize };
    }

//    public void col(){
//        if(Arrays.equals(player.getPoint(), getPoint())){
//            System.out.println("Collision");
//        }
//    }
}
