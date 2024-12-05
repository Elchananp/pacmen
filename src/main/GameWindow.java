//package main;
//
//import javax.swing.*;
//
//public class GameWindow {
//    private JFrame window;
//    private GamePanel gamePanel;
////    private StartPanel startPanel;
//
//    public GameWindow(GamePanel gamePanel) {
//        this.gamePanel = gamePanel;
//
//        // יצירת חלון
//        window = new JFrame();
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setResizable(false);
//        window.setTitle("Pac-Man Game");
//
////        // יצירת פאנל התחלה
////        startPanel = new StartPanel(this);
////        window.add(startPanel);
//
//        // התאמת גודל החלון
//        window.pack();
//        window.setLocationRelativeTo(null);
//        window.setVisible(true);
//    }
//
//    // הפעלת המשחק
////    public void startGame() {
////        window.remove(startPanel); // הסרת פאנל ההתחלה
////        window.add(gamePanel); // הוספת פאנל המשחק
////        window.pack();
////        gamePanel.startGameThread(); // הפעלת לולאת המשחק
////    }
//    public void startGame() {
////        window.remove(startPanel); // הסרת פאנל ההתחלה
//        window.add(gamePanel); // הוספת פאנל המשחק
//        window.pack(); // התאמת גודל החלון לפאנל החדש
//        window.setLocationRelativeTo(null); // מיקום החלון במרכז המסך
//        gamePanel.startGameThread(); // הפעלת לולאת המשחק
//    }
//
//
//    // שיחזור משחק
//    public void loadGame() {
//        // כאן תוכל להוסיף לוגיקה לשיחזור משחק
//        System.out.println("Load Game selected.");
//        startGame();
//    }
//}


package main;

import entity.Player;

import javax.swing.*;

public class GameWindow {

    private JFrame window;
    Player player;

    public GameWindow(GamePanel gamePanel) {
        // יצירת חלון חדש
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Pac-Man Game");

        // הוספת לוח המשחק
        window.add(gamePanel);

        // התאמה אוטומטית לגודל של gamePanel
        window.pack();

        // מיקום החלון במרכז המסך
        window.setLocationRelativeTo(null);

        // הצגת החלון
        window.setVisible(true);
    }

    public JFrame getWindow() {
        return window;
    }
}
