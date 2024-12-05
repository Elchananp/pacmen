package entity;

import main.GamePanel;
import main.KeyHandler;
import tile.Tile;
import tile.TileManager;

import javax.imageio.ImageIO;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    TileManager tileManager;
    MoveRecord moveRecord;
    private Image image; // משתנה התמונה במחלקת Player בלבד
    public String direction = "def";
    public String nextStep = "def";
    public int score = 0;
    private BufferedImage upImage, downImage, leftImage, rightImage, defImage;
    int counterUp;
    //    List<Object> moves = new ArrayList<>();
    int counterSteps;
    private final Map<String, Integer> previousTiles = new HashMap<>(); // שמירת מצב קודם לפי קואורדינטות
    // רשימת האריחים הנוכחיים שבהם פקמן נמצא
    private Set<String> currentTiles = new HashSet<>();


    List<MoveRecord> moves = new ArrayList<>();
    public int life;
    public int speed;
    public int pointsEaten;



//

    public Player(GamePanel gamePanel, KeyHandler keyHandler, TileManager tileManager) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.tileManager = tileManager; // השמה כדי להימנע מ-NullPointerException
        setDefaultValue();
        getPlayerImages();
    }

    public void setDefaultValue() {
//        this.x = 25;
//        this.y = 25;
        this.x = 25 * gamePanel.tileSize;
        this.y = 27 * gamePanel.tileSize;
//        this.x = 9 * gamePanel.tileSize;
//        this.y = 10 * gamePanel.tileSize;
        this.speed = 3;
        this.life = 3;
    }

    public void getPlayerImages() {
        try {
            upImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-open-up.png")));
            downImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-open-down.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-open-left.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-open-right.png")));
            defImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-whole-down.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void recordMoves(String direction, int steps) {
        if (!direction.equals("def") && steps != 0) { // מתעלם ממצבים ללא תנועה
            moves.add(new MoveRecord(direction, steps));
        }
    }

    public void printMoves() {
        for (MoveRecord move : moves) {
            System.out.println(move);
        }
        System.out.println("moves.size(): " + moves.size());

    }


    @Override
    public void update() {
        chooseDirection();
        changeDirection(nextStep);
        move();
//        test();

    }

    @Override
//    public void move() {
//        int indexTile = getNumOfTile(direction);
//        Tile[] tiles = tileManager.tile;
//
//        if (direction.equals("up") && !tiles[indexTile].isBlocked) {
//            y -= speed;
//            counterSteps += speed;
//        } else if (direction.equals("down") && !tiles[indexTile].isBlocked) {
//            y += speed;
//            counterSteps += speed;
//        } else if (direction.equals("right") && !tiles[indexTile].isBlocked) {
//            x += speed;
//            counterSteps += speed;
//        } else if (direction.equals("left") && !tiles[indexTile].isBlocked) {
//            x -= speed;
//            counterSteps += speed;
//        }
//
//        // בדיקה אם השחקן השלים מעבר אריח מלא
//        if (isFullTileMove()) {
//            recordMoves(direction, counterSteps); // שמירת הצעד
//            counterSteps = 0; // איפוס מונה הצעדים לאחר שמירה
//            controlTileAction(); // עדכון פעולה
//        }
//    }


    public void move() {
        int indexTile = getNumOfTile(direction);
        Tile[] tiles = tileManager.tile;

        if (direction.equals("up") && !tiles[indexTile].isBlocked) {
            y -= speed;
            counterSteps += speed;
            controlTileAction();
            setBlockPinky();
        } else if (direction.equals("down") && !tiles[indexTile].isBlocked) {
            y += speed;
            counterSteps += speed;
            controlTileAction();
            setBlockPinky();
        } else if (direction.equals("right") && !tiles[indexTile].isBlocked) {
            x += speed;
            counterSteps += speed;
            controlTileAction();
            setBlockPinky();
        } else if (direction.equals("left") && !tiles[indexTile].isBlocked) {
            x -= speed;
            counterSteps += speed;
            controlTileAction();
            setBlockPinky();
        }
//        if (!direction.equals("def") && tiles[indexTile].isBlocked) direction = "def";
//        recordMoves(direction, counterSteps);
//        counterSteps = 0;

        if (isFullTileMove()) {
            recordMoves(direction, counterSteps); // שמירת הצעד
            counterSteps = 0; // איפוס מונה הצעדים לאחר שמירה
            direction = "def";
        }
    }

    public boolean isFullTileMove() {
        return x % gamePanel.tileSize == 0 && y % gamePanel.tileSize == 0;
    }


    public void test() {
        int x = tileManager.getMap()[1][1];
        System.out.println(tileManager.tile[x].type);
        System.out.println(Arrays.deepToString(tileManager.getMap()));
    }

    @Override
    public void chooseDirection() {
        if (keyHandler.upPressed) {
            resetKeys();
            nextStep = "up";
            changeDirection("up");
            printChoose();
        } else if (keyHandler.downPressed) {
            resetKeys();
            nextStep = "down";
            changeDirection("down");
            printChoose();
        } else if (keyHandler.leftPressed) {
            resetKeys();
            nextStep = "left";
            changeDirection("left");
            printChoose();
        } else if (keyHandler.rightPressed) {
            resetKeys();
            nextStep = "right";
            changeDirection("right");
            printChoose();
        } else if (keyHandler.spacePressed) {
            resetKeys();
            printMoves();

        }
//        changeDirection(nextStep);

    }

    // Aligns the player to the closest tile edge if slightly offset.
    public void alignToTile() {
        int tileSize = gamePanel.tileSize;

        // חשב את השאריות על ציר X ו-Y
        int gapX = x % tileSize;
        int gapY = y % tileSize;

        // אם השארית קטנה מ-speed, התאם את המיקום
        if (gapX > 0 && gapX < speed) {
            x -= gapX; // יישור שמאלה
        } else if (gapX > tileSize - speed) {
            x += (tileSize - gapX); // יישור ימינה
        }

        if (gapY > 0 && gapY < speed) {
            y -= gapY; // יישור למעלה
        } else if (gapY > tileSize - speed) {
            y += (tileSize - gapY); // יישור למטה
        }
    }

//    public void setBlockPinky() {
//        int tileSize = gamePanel.tileSize;
////        int row = getPointInPixel()[1];// y
////        int col = getPointInPixel()[0]; // x
//        switch (direction) {
//            case "up" -> {
////                row = (row + tileSize + speed - 1) / tileSize;
////                col = col / tileSize;
////                tileManager.getMap()[row][col] = 5;
//                tileManager.getMap()[getPointOnMap()[0][0]][getPointOnMap()[0][1]] = 0;// up
//                tileManager.getMap()[getPointOnMap()[1][0]][getPointOnMap()[1][1]] = 0;// down
//                tileManager.getMap()[getPointOnMap()[2][0]][getPointOnMap()[2][1]] = 0;// right
//                tileManager.getMap()[getPointOnMap()[3][0]][getPointOnMap()[3 ][1]] = 0;// left
//
//
//            }
////            case "down" -> {
////                System.out.println("setBlockPinky " + direction);
////                row = (row - speed + 1) / tileSize;
////                col = col / tileSize;
////                tileManager.getMap()[row][col] = 5;
////
////                //    row = (row + speed + 1) / tileSize; // משנה את המיקום של ה-row
//////                col = col / tileSize;  // לא משנים את ה-col
//////                tileManager.getMap()[row][col] = 5; // עדכון המפה עם הערך החדש
//////            }
////
////            }
////            case "left" -> {
////                row = row / tileSize;
////                col = (col + tileSize + speed - 1) / tileSize;
////                tileManager.getMap()[row][col] = 5;
////
////            }
////            case "right" -> {
////                row = row / tileSize;
////                col = (col - speed) / tileSize;
////                tileManager.getMap()[row][col] = 5;
////            }
////
//        }
//        tileManager.getMap()[getPointOnMap()[0][0]][getPointOnMap()[0][1]] = 5;
//        tileManager.getMap()[getPointOnMap()[1][0]][getPointOnMap()[1][1]] = 5;
//        tileManager.getMap()[getPointOnMap()[2][0]][getPointOnMap()[2][1]] = 5;
//        tileManager.getMap()[getPointOnMap()[3][0]][getPointOnMap()[3 ][1]] = 5;
//
//    }

    public void setBlockPinky() {
        int tileSize = gamePanel.tileSize;

        // שמירת האריחים הקודמים
        Set<String> previousTilesSet = new HashSet<>(currentTiles);

        // חישוב האריחים החדשים שבהם פקמן נמצא
        int topRow = (y + speed) / tileSize;
        int bottomRow = (y + tileSize - 1 - speed) / tileSize;
        int leftCol = (x + speed) / tileSize;
        int rightCol = (x + tileSize - 1 - speed) / tileSize;

        currentTiles.clear();
        for (int row = topRow; row <= bottomRow; row++) {
            for (int col = leftCol; col <= rightCol; col++) {
                String key = row + "," + col;
                currentTiles.add(key);

                // אם האריח לא נחסם בעבר, נשמור את מצבו הקודם ונחסום אותו
                if (!previousTiles.containsKey(key)) {
//                    previousTiles.put(key, tileManager.getMap()[row][col]);
                    previousTiles.put(key, tileManager.getMap()[row][col]); // שמירת הערך של האריח

                }

//                tileManager.getMap()[row][col] = 5; // חסימת האריח
                tileManager.tile[tileManager.getMap()[row][col]].isBlockedForPinky = true; // חסימת האריח
            }
        }

        // שחרור אריחים שאינם בטווח הנוכחי
        for (String key : previousTilesSet) {
            if (!currentTiles.contains(key)) {
                String[] parts = key.split(",");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);

                // שחזור האריח למצבו המקורי
//                tileManager.getMap()[row][col] = previousTiles.get(key);
                tileManager.tile[tileManager.getMap()[row][col]].isBlockedForPinky = false;

                previousTiles.remove(key);
            }
        }
    }
//    private void releaseOldTiles(int topRow, int bottomRow, int leftCol, int rightCol) {



//
//    public void setBlockPinky() {
//        int tileSize = gamePanel.tileSize;
//        int row = getPointInPixel()[1]; // y
//        int col = getPointInPixel()[0]; // x
//
//        switch (direction) {
//            case "up" -> {
//                // חישוב הפוך ל-up
//                row = (row - tileSize - speed) / tileSize; // משנה את המיקום של ה-row
//                col = col / tileSize;  // לא משנים את ה-col
//                tileManager.getMap()[row][col] = 5; // עדכון המפה עם הערך החדש
//            }
//            case "down" -> {
//                // חישוב הפוך ל-down
//                System.out.println("setBlockPinky " + direction);
//                row = (row + speed + 1) / tileSize; // משנה את המיקום של ה-row
//                col = col / tileSize;  // לא משנים את ה-col
//                tileManager.getMap()[row][col] = 5; // עדכון המפה עם הערך החדש
//            }
//            case "left" -> {
//                // חישוב הפוך ל-left
//                row = row / tileSize;  // לא משנים את ה-row
//                col = (col - tileSize - speed) / tileSize; // משנה את המיקום של ה-col
//                tileManager.getMap()[row][col] = 5; // עדכון המפה עם הערך החדש
//            }
//            case "right" -> {
//                // חישוב הפוך ל-right
//                row = row / tileSize;  // לא משנים את ה-row
//                col = (col + speed) / tileSize; // משנה את המיקום של ה-col
//                tileManager.getMap()[row][col] = 5; // עדכון המפה עם הערך החדש
//            }
//        }
//
//        // עדכון המפה של המיקום הקודם של Pinky
//        tileManager.getMap()[getPointOnMap()[0]][getPointOnMap()[1]] = 0;
//    }


    public void controlTileAction() {
        int[] index = getNumOfTileIndex(direction);
        int numTile = getNumOfTile(direction);
        Tile tile = tileManager.tile[numTile];


        switch (tile.type) {
            case "point" -> {
                score += tile.score;
                pointsEaten++;
                setMap(index, 0);
            }
            case "tunnel" -> {
                moveInTunnel(index[0], index[1]);

            }

            default -> {
                // אריח רגיל
            }
        }
    }

    public void setMap(int[] indexOfMAp, int num) {
        tileManager.getMap()[indexOfMAp[0]][indexOfMAp[1]] = num;

    }

    public void moveInTunnel(int tileX, int tileY) {
        int tileSize = gamePanel.tileSize;

        if (tileY == 0) {
            this.x = (gamePanel.screenWidth - tileSize);
        } else if (tileY == gamePanel.maxScreenCol - 1) {
            // אם השחקן בקצה הימני של המסך, מעבירים אותו לקצה השמאלי
            this.x = 0;
        }
//        System.out.println(gamePanel.screenWidth - tileSize);
    }


    @Override
    public void changeDirection(String nextStep) {
//        System.out.println("changeDirection start, next step: " + nextStep);
        alignToTile();
        int index = getNumOfTile(nextStep);
        Tile cureentTile = null;


        if (!nextStep.equals("def") && index >= 0) {
//            System.out.println("xxx");
            cureentTile = tileManager.tile[index];

            if (!cureentTile.isBlocked) {
//            System.out.println("yyy");
                direction = nextStep;

//            System.out.println("direction from changeDirection:  " + direction);
            }
        }
    }


    public void printChoose() {
//        System.out.println("printChoose start");
//        System.out.println("next step " + nextStep);
//        int index =
//        System.out.println(tileManager.tile[getNumOfTile(nextStep)].type);

    }



    public int[] getNumOfTileIndex(String nextPoint) {
        int row = -1, col = -1;
        int tileSize = gamePanel.tileSize;

//        System.out.println("Direction: " + nextPoint);
//        System.out.println("Current Position: x = " + x + ", y = " + y);

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

//        System.out.println("Calculated Position: row = " + row + ", col = " + col);

        // בדיקה אם האינדקסים חוקיים
        if (row >= 0 && row < gamePanel.maxScreenRow && col >= 0 && col < gamePanel.maxScreenCol) {
//            System.out.println("Valid Position: row = " + row + ", col = " + col);
            return new int[]{row, col};
        }

//        System.out.println("Invalid Position: Returning [-1, -1]");
        return new int[]{-1, -1};
    }

    public int getNumOfTile(String nextPoint) {
        int[] index = getNumOfTileIndex(nextPoint);
        if (index[0] >= 0 && index[1] >= 0) {
            return tileManager.getMap()[(index[0])][(index[1])];
        }
        return -1;
    }


    public void resetKeys() {
        keyHandler.upPressed = false;
        keyHandler.downPressed = false;
        keyHandler.rightPressed = false;
        keyHandler.leftPressed = false;
    }


    @Override
    public void draw(Graphics g) {
        BufferedImage image = null;
//        System.out.println("from draw :" + direction);
        switch (direction) {
            case "up" -> image = upImage;
            case "down" -> image = downImage;
            case "left" -> image = leftImage;
            case "right" -> image = rightImage;
            case "def" -> image = defImage;
//            case "def" -> g.setColor(Color.WHITE); // צבע ברירת מחדל

        }
        g.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    public int[][] getPointOnMap() {
        return new int[][] {
                {y / gamePanel.tileSize, x / gamePanel.tileSize},// up
                {(y + gamePanel.tileSize - 1) / gamePanel.tileSize, x / gamePanel.tileSize},// down
                {y / gamePanel.tileSize, (x + gamePanel.tileSize - 1) / gamePanel.tileSize},// right
                {(y + gamePanel.tileSize - 1) / gamePanel.tileSize, (x + gamePanel.tileSize - 1) / gamePanel.tileSize}// left
        };
    }

//    public int[][] getPointOnMap() {
//        return new int[][]{
//                {y / gamePanel.tileSize, x / gamePanel.tileSize}, // up
//                {(y + gamePanel.tileSize - 1) / gamePanel.tileSize, x / gamePanel.tileSize}, // down
//                {y / gamePanel.tileSize, (x + gamePanel.tileSize - 1) / gamePanel.tileSize}, // right
//                {(y + gamePanel.tileSize - 1) / gamePanel.tileSize, (x + gamePanel.tileSize - 1) / gamePanel.tileSize} // left
//        };
//    }



    public void setPoint(){
        this.x = 25 * gamePanel.tileSize;
        this.y = 27 * gamePanel.tileSize;
    }

    public int[] getPointInPixel(){
        return new int[] {y / gamePanel.tileSize, x / gamePanel.tileSize  };

    }

}




