package main;

import entity.*;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // גודל מקורי של כל משבצת (pixel)
    final int originalTileSize = 5; // נניח שכל משבצת בגודל 16x16 פיקסלים במקור
    final int scale = 5; // קנה מידה להגדלת המשבצות

    public final int tileSize = originalTileSize * scale;  // גודל סופי של משבצת לאחר קנה מידה (48x48 פיקסלים)

    // מספר עמודות ושורות במסך המשחק
    public final int maxScreenCol = 27;
    public final int maxScreenRow = 30;

    // חישוב גודל המסך בהתאם למספר המשבצות
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public int amountOfSmallPoints;
    private double counterMillis;
    public boolean isRunning;


    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager;
    Player player;
    Inky inky;
    Clyde clyde;
    Pinky pinky;
    public Bfs bfs;
    CollisionManager collisionManager;



    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // גודל הפאנל בהתאם לגודל המשבצות ולמספרן
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
        this.requestFocusInWindow(); // מבטיח שהפאנל יתפוס פוקוס לקלט מהמשתמש

        tileManager = new TileManager(this);
        player = new Player(this, keyHandler, tileManager);
        inky = new Inky(this, tileManager);
        bfs = new Bfs(this, tileManager);
        clyde = new Clyde(this, tileManager, bfs, player);
        pinky = new Pinky(this,tileManager,bfs, player);
        collisionManager = new CollisionManager(this, player, clyde, inky, pinky);


        isRunning = true;

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }

//    public void stopGame(){
//        if (keyHandler.spacePressed){
//            System.out.println("keyHandler.spacePressed" + keyHandler.spacePressed);
//            isRunning = !isRunning;
//            keyHandler.spacePressed = false;
//        }
public void stopGame() {
    if (keyHandler.spacePressed) {
        System.out.println("SPACE pressed, toggling isRunning");
        isRunning = !isRunning;
        keyHandler.spacePressed = false; // איפוס המקש
        System.out.println("isRunning = " + isRunning);
    }

    }



    @Override
    public void run() {
        while (true) {
            if (isRunning) {
                player.update();
                inky.update();
                clyde.update();
                pinky.update();
                collisionManager.update();
                repaint();
            }
            stopGame();


            try {
                Thread.sleep(16); // 60 FPS
//                 counterMillis += 16;
//                 setTimer();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

//    public void run() {
//        while (gameThread != null) {
//            player.update();
//            inky.update();
////            System.out.println(player.score);
//            repaint();
//
//            try {
//                Thread.sleep(16); // כיוון התדירות של לולאת המשחק ל-60 FPS (1000ms / 60)
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tileManager.draw(g);
        player.draw(g);
        inky.draw(g);
        clyde.draw(g);
        pinky.draw(g);

        // ציור אלמנטים של המשחק כאן
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + player.score, 10, 20);
    }


}
