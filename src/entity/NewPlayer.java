//package entity;
//
//import main.GamePanel;
//import main.KeyHandler;
//import tile.Tile;
//import tile.TileManager;
//import javax.imageio.ImageIO;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Objects;
//
//
//public class NewPlayer extends Entity {
//    GamePanel gamePanel;
//    KeyHandler keyHandler;
//    TileManager tileManager;
//    private Image image; // משתנה התמונה במחלקת NewPlayer בלבד
//    public String direction = "def";
//    public String nextStep;
//    public int score = 0;
//    private BufferedImage upImage, downImage, leftImage, rightImage, defImage;
//
//
////
//
//    public NewPlayer(GamePanel gamePanel, KeyHandler keyHandler, TileManager tileManager) {
//        this.gamePanel = gamePanel;
//        this.keyHandler = keyHandler;
//        this.tileManager = tileManager; // השמה כדי להימנע מ-NullPointerException
//        setDefaultValue();
//    }
//
//
//    public void setDefaultValue() {
//        this.x = 25; // מיקום התחלתי
//        this.y = 175;
//        this.speed = 5;
//        this.life = 3;
//    }
//
//    public void update() {
//        chooseDirection();
//        hasPath(nextStep);
////        moveNewPlayer();
//    }
//
//    public void test(){
//        int x = tileManager.getMap()[0][1];
//        System.out.println(tileManager.tile[x].type);
//    }
//
//
//
//
//
//    private void chooseDirection() {
//        if (keyHandler.upPressed) {
//            resetKeys();
//            printNextPoint();
//            nextStep = "up";
//        } else if (keyHandler.downPressed) {
//            resetKeys();
//            printNextPoint();
//            nextStep = "down";
//        } else if (keyHandler.leftPressed) {
//            resetKeys();
//            printNextPoint();
//            nextStep = "left";
//        } else if (keyHandler.rightPressed) {
//            resetKeys();
//            printNextPoint();
//            nextStep  = "right";
//
//        }
////        testGetIndex();
//    }
//
//   public void hasPath(String nextStep){
//        if (!isBlocked(nextStep)){
//            direction = nextStep;
//            moveNewPlayer();
//        }
//   }
//
//
//
//
//    private void moveNewPlayer() {
//        switch (direction) {
//            case "up" -> {
//                if (isBlocked("up")) {
//                    y -= speed;
//                }
////                else if (!isBlocked("up")){
////                    System.out.println("blocked");
////                }
//            }
//            case "down" -> {
//                if (isBlocked("down")) {
//                    y += speed;
//                }
////                else if (!isBlocked("down")){
////                    System.out.println("blocked");
////                }
//            }
//            case "left" -> {
//                if (isBlocked("left")) {
//                    x -= speed;
//                }
////                else if (!isBlocked("left")){
////                    System.out.println("blocked");
////                }
//            }
//            case "right" -> {
//                if (isBlocked("right")) {
//                    x += speed;
//                }
////                else if (!isBlocked("right")){
////                    System.out.println("blocked");
////                }
//            }
//        }
////        setMap();
//        tileAction();
//
//    }
//
//    public void tileAction() {
//        int[] num = getNextTileIndex(direction);
//        int tileNum = tileManager.getMap()[num[1]][num[0]];
//        Tile tile = tileManager.tile[tileNum];
//
//        switch (tile.type) {
//            case "point" -> {
//                score += tile.score;
//                setMap(num);
//            }
//            case "tunnel" -> {
//                actionTunnel(num[0], num[1]);
//
//            }
////            case "wall" -> {
////            }
//            default -> {
//                // אריח רגיל
//            }
//        }
//    }
//
//
//    public void actionTunnel(int tileX, int tileY){
//        int tileSize = gamePanel.tileSize;
//
//        if (tileY == 0) {
//            this.x = (gamePanel.screenWidth - tileSize );
//        } else if (tileY == gamePanel.maxScreenCol - 1) {
//            // אם השחקן בקצה הימני של המסך, מעבירים אותו לקצה השמאלי
//            this.x = 0;
//        }
//        System.out.println(gamePanel.screenWidth - tileSize );
//    }
//
//
//    public void resetKeys(){
//        keyHandler.upPressed = false;
//        keyHandler.downPressed = false;
//        keyHandler.rightPressed = false;
//        keyHandler.leftPressed = false;
//    }
//
//    public boolean isBlocked(String nextStep){
//        int indexMap = testGetIndex(nextStep);
//        if (indexMap >= 0){
//            return tileManager.tile[indexMap].isBlocked;
//        }
//        return true;
//    }
//
//
//
//
//
//    public void setMap(int [] num) {
//
//        tileManager.getMap()[num[1]][num[0]] = 0; // שינוי הערך לאריח רגיל
//    }
//
//    public int[] getNextTileIndex(String direction) {
//        int tileSize = gamePanel.tileSize;
//        int nextRow = y / tileSize;
//        int nextCol = x / tileSize;
//
//        switch (direction) {
//            case "up" -> nextRow = (y - speed) / tileSize;
//            case "down" -> nextRow = (y + gamePanel.tileSize - 1 + speed) / tileSize;
//            case "left" -> nextCol = (x - speed) / tileSize;
//            case "right" -> nextCol = (x + gamePanel.tileSize - 1 + speed) / tileSize;
//        }
//
//        return new int[]{nextRow, nextCol}; // מחזיר מערך עם השורה והעמודה
//    }
////    public addScore(){
////
////    }
//
//
//    public void printNextPoint(){
//        System.out.println(Arrays.toString(getNextTileIndex(direction)));
//    }
//
//
//
//
//    public void getNewPlayerImages() {
//        try {
//            // טוען את התמונות לכל כיוון
//            upImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-whole-down.png ")));
//            downImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-open-down.png")));
//            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-open-left.png")));
//            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-open-right.png")));
//            defImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-whole-down.png ")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void draw(Graphics g) {
//        getNewPlayerImages();
//        BufferedImage image = null;
//        // בוחרים את התמונה המתאימה על פי כיוון התנועה
//        switch (direction) {
//            case "up" -> image = upImage ;
//            case "down" -> image = downImage ;
//            case "left" -> image = leftImage ;
//            case "right" -> image = rightImage ;
//            case "def" -> image = defImage; // צבע ברירת מחדל
////            case "def" -> g.setColor(Color.WHITE); // צבע ברירת מחדל
//
//        }
//        g.drawImage(image, x, y, gamePanel.tileSize - 5, gamePanel.tileSize - 5, null);
//    }
//
//
//    public int testGetIndex(String direction) {
//        int row, col;
//        int tileSize = gamePanel.tileSize;
//
//        if (direction.equals("up")) {
//            row = (y - speed) / tileSize;
////            int numLeft = tileManager.getMap()[x / tileSize][row];
////            int numRight = tileManager.getMap()[(x + tileSize - 1) / tileSize][row];
//
////        System.out.println("Tile Left: " + tileManager.tile[numLeft].type);
////        System.out.println("Tile Right: " + tileManager.tile[numRight].type);
//            if ((x + tileSize - 1) == (x / tileSize)) {
//                return tileManager.getMap()[(x + tileSize - 1) / tileSize][row];
//            }
//
////            if (numLeft != numRight) {
////                System.out.println("Mismatch! Left and Right tiles are different.");
////            }
//        } else if (direction.equals("down")) {
//            row = (y + tileSize + speed) / tileSize;
////            int numLeft = tileManager.getMap()[x / tileSize][row];
//            if ((x / tileSize) == (x + tileSize - 1)) {
//                return tileManager.getMap()[(x + tileSize - 1) / tileSize][row];
//
//
//            }
//
//
//        } else if (direction.equals("right")) {
//            col = (x + tileSize + speed) / tileSize;
////            int numTop = tileManager.getMap()[col][y / tileSize];
////            int numBottom = tileManager.getMap()[col][(y + tileSize - 1) / tileSize];
//            if ((y + tileSize - 1) == (y / tileSize)) {
//                return tileManager.getMap()[col][(y + tileSize - 1) / tileSize];
//            }
//
//
//        } else if (direction.equals("left")) {
//            col = (x - speed) / tileSize;
////            int numTop = tileManager.getMap()[col][y / tileSize];
////            int numBottom = tileManager.getMap()[col][(y + tileSize - 1) / tileSize];
//            if ((y / tileSize) == (y + tileSize - 1)) {
//                return tileManager.getMap()[col][(y + tileSize - 1) / tileSize];
//            }
//
//
//        }
//        return -1;
//    }
//
//}
//
////    public boolean isBlocked(String direction) {
////        int tileSize = gamePanel.tileSize;
////        int leftCol = x / tileSize;
////        int rightCol = (x + tileSize - 1) / tileSize;
////        int topRow = y / tileSize;
////        int bottomRow = (y + tileSize - 1) / tileSize;
////
////        switch (direction) {
////            case "up" -> {
////                int newTopRow = (y - speed) / tileSize;
////                return newTopRow >= 0 &&
////                        (tileManager.tile[tileManager.getMap()[leftCol][newTopRow]].isBlocked ||
////                                tileManager.tile[tileManager.getMap()[rightCol][newTopRow]].isBlocked);
////            }
////            case "down" -> {
////                int newBottomRow = ((y + tileSize - 1 + speed) / tileSize);
////                return newBottomRow < gamePanel.maxScreenRow &&
////                        (tileManager.tile[tileManager.getMap()[leftCol][newBottomRow]].isBlocked ||
////                                tileManager.tile[tileManager.getMap()[rightCol][newBottomRow]].isBlocked);
////            }
////            case "left" -> {
////                int newLeftCol = (x - speed) / tileSize;
////                return newLeftCol >= 0 &&
////                        (tileManager.tile[tileManager.getMap()[newLeftCol][topRow]].isBlocked ||
////                                tileManager.tile[tileManager.getMap()[newLeftCol][bottomRow]].isBlocked);
////            }
////            case "right" -> {
////                int newRightCol = ((x + tileSize - 1 + speed) / tileSize);
////                return newRightCol < gamePanel.maxScreenCol &&
////                        (tileManager.tile[tileManager.getMap()[newRightCol][topRow]].isBlocked ||
////                                tileManager.tile[tileManager.getMap()[newRightCol][bottomRow]].isBlocked);
////            }
////            default -> {
////                return false;
////            }
////        }
////    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////package entity;
////
////import main.GamePanel;
////import main.KeyHandler;
////import tile.Tile;
////import tile.TileManager;
////import javax.imageio.ImageIO;
////
//
////import java.awt.*;
//
////import java.awt.image.BufferedImage;
////import java.io.File;
////import java.io.IOException;
////import java.util.Arrays;
////import java.util.Objects;
////
////
////public class NewPlayer extends Entity {
////    GamePanel gamePanel;
////    KeyHandler keyHandler;
////    TileManager tileManager;
////    private Image image; // משתנה התמונה במחלקת NewPlayer בלבד
////    public String direction = "def";
////    public String nextStep;
////    public int score = 0;
////    private BufferedImage upImage, downImage, leftImage, rightImage, defImage;
////
////
//////
////
////    public NewPlayer(GamePanel gamePanel, KeyHandler keyHandler, TileManager tileManager) {
////        this.gamePanel = gamePanel;
////        this.keyHandler = keyHandler;
////        this.tileManager = tileManager; // השמה כדי להימנע מ-NullPointerException
////        setDefaultValue();
////    }
////
////
////    public void setDefaultValue() {
////        this.x = 25; // מיקום התחלתי
////        this.y = 175;
////        this.speed = 5;
////        this.life = 3;
////    }
////
////    public void update() {
////        chooseDirection();
////        moveNewPlayer();
////    }
////
////
////    private void chooseDirection() {
////        if (keyHandler.upPressed) {
////            resetKeys();
////            printNextPoint();
////            direction = "up";
////        } else if (keyHandler.downPressed) {
////            resetKeys();
////            printNextPoint();
////            direction = "down";
////        } else if (keyHandler.leftPressed) {
////            resetKeys();
////            printNextPoint();
////            direction = "left";
////        } else if (keyHandler.rightPressed) {
////            resetKeys();
////            printNextPoint();
////            direction = "right";
////
////        }
////        testGetIndex();
////    }
////
////
////
////    private void moveNewPlayer() {
////        switch (direction) {
////            case "up" -> {
////                if (isBlocked("up")) {
////                    y -= speed;
////                }
//////                else if (!isBlocked("up")){
//////                    System.out.println("blocked");
//////                }
////            }
////            case "down" -> {
////                if (isBlocked("down")) {
////                    y += speed;
////                }
//////                else if (!isBlocked("down")){
//////                    System.out.println("blocked");
//////                }
////            }
////            case "left" -> {
////                if (isBlocked("left")) {
////                    x -= speed;
////                }
//////                else if (!isBlocked("left")){
//////                    System.out.println("blocked");
//////                }
////            }
////            case "right" -> {
////                if (isBlocked("right")) {
////                    x += speed;
////                }
//////                else if (!isBlocked("right")){
//////                    System.out.println("blocked");
//////                }
////            }
////        }
//////        setMap();
////        tileAction();
////
////    }
////
////    public void tileAction() {
////        int[] num = getNextTileIndex(direction);
////        int tileNum = tileManager.getMap()[num[1]][num[0]];
////        Tile tile = tileManager.tile[tileNum];
////
////        switch (tile.type) {
////            case "point" -> {
////                score += tile.score;
////                setMap(num);
////            }
////            case "tunnel" -> {
////                actionTunnel(num[0], num[1]);
////
////            }
//////            case "wall" -> {
//////            }
////            default -> {
////                // אריח רגיל
////            }
////        }
////    }
////
////
////    public void actionTunnel(int tileX, int tileY){
////        int tileSize = gamePanel.tileSize;
////
////        if (tileY == 0) {
////            this.x = (gamePanel.screenWidth - tileSize );
////        } else if (tileY == gamePanel.maxScreenCol - 1) {
////            // אם השחקן בקצה הימני של המסך, מעבירים אותו לקצה השמאלי
////            this.x = 0;
////        }
////        System.out.println(gamePanel.screenWidth - tileSize );
////    }
////
////
////    public void resetKeys(){
////        keyHandler.upPressed = false;
////        keyHandler.downPressed = false;
////        keyHandler.rightPressed = false;
////        keyHandler.leftPressed = false;
////    }
////
////    public boolean isBlocked(){
////        if (testGetIndex() >= 0){
////            return tileManager.tile[testGetIndex()].isBlocked;
////        }
////        return false;
////    }
////
//////    public boolean isBlocked(String direction) {
//////        int tileSize = gamePanel.tileSize;
//////        int leftCol = x / tileSize;
//////        int rightCol = (x + tileSize - 1) / tileSize;
//////        int topRow = y / tileSize;
//////        int bottomRow = (y + tileSize - 1) / tileSize;
//////
//////        switch (direction) {
//////            case "up" -> {
//////                int newTopRow = (y - speed) / tileSize;
//////                return newTopRow >= 0 &&
//////                        (tileManager.tile[tileManager.getMap()[leftCol][newTopRow]].isBlocked ||
//////                                tileManager.tile[tileManager.getMap()[rightCol][newTopRow]].isBlocked);
//////            }
//////            case "down" -> {
//////                int newBottomRow = ((y + tileSize - 1 + speed) / tileSize);
//////                return newBottomRow < gamePanel.maxScreenRow &&
//////                        (tileManager.tile[tileManager.getMap()[leftCol][newBottomRow]].isBlocked ||
//////                                tileManager.tile[tileManager.getMap()[rightCol][newBottomRow]].isBlocked);
//////            }
//////            case "left" -> {
//////                int newLeftCol = (x - speed) / tileSize;
//////                return newLeftCol >= 0 &&
//////                        (tileManager.tile[tileManager.getMap()[newLeftCol][topRow]].isBlocked ||
//////                                tileManager.tile[tileManager.getMap()[newLeftCol][bottomRow]].isBlocked);
//////            }
//////            case "right" -> {
//////                int newRightCol = ((x + tileSize - 1 + speed) / tileSize);
//////                return newRightCol < gamePanel.maxScreenCol &&
//////                        (tileManager.tile[tileManager.getMap()[newRightCol][topRow]].isBlocked ||
//////                                tileManager.tile[tileManager.getMap()[newRightCol][bottomRow]].isBlocked);
//////            }
//////            default -> {
//////                return false;
//////            }
//////        }
//////    }
////
////
////
////    public void setMap(int [] num) {
////
////        tileManager.getMap()[num[1]][num[0]] = 0; // שינוי הערך לאריח רגיל
////    }
////
////    public int[] getNextTileIndex(String direction) {
////        int tileSize = gamePanel.tileSize;
////        int nextRow = y / tileSize;
////        int nextCol = x / tileSize;
////
////        switch (direction) {
////            case "up" -> nextRow = (y - speed) / tileSize;
////            case "down" -> nextRow = (y + gamePanel.tileSize - 1 + speed) / tileSize;
////            case "left" -> nextCol = (x - speed) / tileSize;
////            case "right" -> nextCol = (x + gamePanel.tileSize - 1 + speed) / tileSize;
////        }
////
////        return new int[]{nextRow, nextCol}; // מחזיר מערך עם השורה והעמודה
////    }
//////    public addScore(){
//////
//////    }
////
////
////    public void printNextPoint(){
////        System.out.println(Arrays.toString(getNextTileIndex(direction)));
////    }
////
////
////
////
////    public void getNewPlayerImages() {
////        try {
////            // טוען את התמונות לכל כיוון
////            upImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-whole-down.png ")));
////            downImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-open-down.png")));
////            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-open-left.png")));
////            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-open-right.png")));
////            defImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/pacman-whole-down.png ")));
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
////
////    @Override
////    public void draw(Graphics g) {
////        getNewPlayerImages();
////        BufferedImage image = null;
////        // בוחרים את התמונה המתאימה על פי כיוון התנועה
////        switch (direction) {
////            case "up" -> image = upImage ;
////            case "down" -> image = downImage ;
////            case "left" -> image = leftImage ;
////            case "right" -> image = rightImage ;
////            case "def" -> image = defImage; // צבע ברירת מחדל
//////            case "def" -> g.setColor(Color.WHITE); // צבע ברירת מחדל
////
////        }
////        g.drawImage(image, x, y, gamePanel.tileSize - 5, gamePanel.tileSize - 5, null);
////    }
////
////
////    public int testGetIndex() {
////        int row, col;
////        int tileSize = gamePanel.tileSize;
////
////        if (direction.equals("up")) {
////            row = (y - speed) / tileSize;
//////            int numLeft = tileManager.getMap()[x / tileSize][row];
//////            int numRight = tileManager.getMap()[(x + tileSize - 1) / tileSize][row];
////
//////        System.out.println("Tile Left: " + tileManager.tile[numLeft].type);
//////        System.out.println("Tile Right: " + tileManager.tile[numRight].type);
////            if ((x + tileSize - 1) == (x / tileSize)) {
////                return tileManager.getMap()[(x + tileSize - 1) / tileSize][row];
////            }
////
//////            if (numLeft != numRight) {
//////                System.out.println("Mismatch! Left and Right tiles are different.");
//////            }
////        } else if (direction.equals("down")) {
////            row = (y + tileSize + speed) / tileSize;
//////            int numLeft = tileManager.getMap()[x / tileSize][row];
////            if ((x / tileSize) == (x + tileSize - 1)) {
////                return tileManager.getMap()[(x + tileSize - 1) / tileSize][row];
////
////
////            }
////
////
////        } else if (direction.equals("right")) {
////            col = (x + tileSize + speed) / tileSize;
//////            int numTop = tileManager.getMap()[col][y / tileSize];
//////            int numBottom = tileManager.getMap()[col][(y + tileSize - 1) / tileSize];
////            if ((y + tileSize - 1) == (y / tileSize)) {
////                return tileManager.getMap()[col][(y + tileSize - 1) / tileSize];
////            }
////
////
////        } else if (direction.equals("left")) {
////            col = (x - speed) / tileSize;
//////            int numTop = tileManager.getMap()[col][y / tileSize];
//////            int numBottom = tileManager.getMap()[col][(y + tileSize - 1) / tileSize];
////            if ((y / tileSize) == (y + tileSize - 1)) {
////                return tileManager.getMap()[col][(y + tileSize - 1) / tileSize];
////            }
////
////
////        }
////        return -1;
////    }
////
////}
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
