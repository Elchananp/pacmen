package main;

public class Main {
    public static void main(String[] args) {
        GamePanel gamePanel = new GamePanel(); // יצירת לוח המשחק
        new GameWindow(gamePanel); //         GameWindow gameWindow = new GameWindow(gamePanel); // יצירת החלון עם לוח המשחק

        gamePanel.startGameThread(); // הפעלת לולאת המשחק


    }

}
