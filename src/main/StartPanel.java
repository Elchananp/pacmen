//package main;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class StartPanel extends JPanel {
//
//    private JButton startButton; // כפתור התחלת המשחק
//    private JButton loadButton; // כפתור שחזור משחק
//
//    public StartPanel(GameWindow gameWindow) {
//        this.setLayout(new GridBagLayout());
//        this.setBackground(Color.BLACK);
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//
//        // יצירת כפתור "Start"
//        startButton = new JButton("Start Game");
//        startButton.setFont(new Font("Arial", Font.BOLD, 20));
//        startButton.setBackground(Color.GREEN);
//        startButton.setForeground(Color.BLACK);
//
//        // יצירת כפתור "Load"
//        loadButton = new JButton("Load Game");
//        loadButton.setFont(new Font("Arial", Font.BOLD, 20));
//        loadButton.setBackground(Color.BLUE);
//        loadButton.setForeground(Color.WHITE);
//
//        // הוספת הכפתורים
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        this.add(startButton, gbc);
//
//        gbc.gridy = 1;
//        this.add(loadButton, gbc);
//
//        // פעולה ללחיצה על "Start Game"
//        startButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                gameWindow.startGame(); // הפעלת המשחק
//            }
//        });
//
//        // פעולה ללחיצה על "Load Game"
//        loadButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                gameWindow.loadGame(); // שיחזור משחק
//            }
//        });
//    }
//}
