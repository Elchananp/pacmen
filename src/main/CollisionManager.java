package main;


import entity.*;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

// המחלקה הזאת כרגע באמצע בנייה והיא אמורה בהמשך לנהל את המשחק והשם שלה אמור להשתנות בהתאם
public class CollisionManager {
    GamePanel gamePanel;
    Player player;
    Clyde clyde;
    Timer timer;
    Inky inky;
    Pinky pinky;


    public CollisionManager(GamePanel gamePanel, Player player, Clyde clyde, Inky inky, Pinky pinky) {
        this.gamePanel = gamePanel;
        this.player = player;
        this.clyde = clyde;
        this.inky = inky;
        this.pinky = pinky;
//        initTimer();
    }

    public void update() {
        checkCollision();
        takingOutGhostsByPoints();
    }

//    private void initTimer() {
//        // יצירת טיימר שמתחיל אחרי 3 שניות ומבצע את הקריאה כל 3 שניות
//        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() { 
//                // קריאה לפונקציה כל 3 שניות
//                takingOutGhostsByPoints();
//            }
//        }, 3000, 3000); // השהיה ראשונית של 3 שניות, ואז ריצה כל 3 שניות
//    }


    public void checkCollision() {
        if (Arrays.equals(player.getPointInPixel(), clyde.getPoint())) {
//            System.out.println("Collision");
//            System.out.println("player: " + Arrays.toString(player.getPoint()) + " " + "clyde: " + Arrays.toString(clyde.getPoint()));
            player.setPoint();
            player.life--;
        } else if (Arrays.equals(player.getPointInPixel(), inky.getPoint())) {
            player.setPoint();
            player.life--;
        }
    }

    public void takingOutGhostsByPoints() {
        if (gamePanel.amountOfSmallPoints / 4 == player.pointsEaten) {
            System.out.println("clyde.exit();");
            if (pinky.inHome) {
                pinky.inHome = false;
                player.pointsEaten = 0;
            } else if (inky.inHome) {
                inky.inHome = false;
                player.pointsEaten = 0;
            }else if (clyde.inHome) {
                clyde.inHome = false;
                player.pointsEaten = 0;
            }
        }
    }
}




//package main;
//
//
//import entity.Clyde;
//import entity.Player;
//
//import java.util.Arrays;
//// המחלקה הזאת כרגע באמצע בנייה והיא אמורה בהמשך לנהל את המשחק והשם שלה אמור להשתנות בהתאם
//public class CollisionManager {
//    GamePanel gamePanel;
//    Player player;
//    Clyde clyde;
//
//
//    public CollisionManager(GamePanel gamePanel, Player player, Clyde clyde) {
//        this.gamePanel = gamePanel;
//        this.player = player;
//        this.clyde = clyde;
//    }
//
//    public void update(){
//        checkCollision();
//        takingOutGhostsByPoints();
//    }
//
//
//
//    public void checkCollision(){
//        if(Arrays.equals(player.getPoint(), clyde.getPoint())){
//            System.out.println("Collision");
//            System.out.println("player: " + Arrays.toString(player.getPoint()) + " " + "clyde: " + Arrays.toString(clyde.getPoint()));
//            player.setPoint();
//        }
//    }
//    public void takingOutGhostsByPoints(){
//        if (gamePanel.amountOfSmallPoints / 4 == player.pointsEaten){
//            System.out.println("clyde.exit();");
//            if(clyde.inHome){
//                clyde.inHome = false;
////                clyde.exit();
//                player.pointsEaten = 0;
//            }
//
//        }
//    }
//}
//
