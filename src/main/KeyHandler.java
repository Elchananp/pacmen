package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean spacePressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

//    @Override
//    public void keyPressed(KeyEvent e) {
//        int keyCode = e.getKeyCode();
//
//        if (keyCode == KeyEvent.VK_UP) {
//            resetKeys();
//            upPressed = true;
//        }
//        if (keyCode == KeyEvent.VK_DOWN) {
//            resetKeys();
//            downPressed = true;
//        }
//        if (keyCode == KeyEvent.VK_LEFT) {
//            resetKeys();
//            leftPressed = true;
//        }
//        if (keyCode == KeyEvent.VK_RIGHT) {
//            resetKeys();
//            rightPressed = true;
//        }
//        if (keyCode == KeyEvent.VK_M) {
//            resetKeys();
//    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {


            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_M) {
            resetKeys();
        }if (keyCode == KeyEvent.VK_SPACE){
            spacePressed = true;
        }

//    @Override
//    public void keyReleased(KeyEvent e) {
//
//    }

//    @Override
//    public void keyReleased(KeyEvent e) {
//        int keyCode = e.getKeyCode();
//
//        if (keyCode == KeyEvent.VK_UP) {
//            upPressed = false;
//        }
//        if (keyCode == KeyEvent.VK_DOWN) {
//            downPressed = false;
//        }
//        if (keyCode == KeyEvent.VK_LEFT) {
//            leftPressed = false;
//        }
//        if (keyCode == KeyEvent.VK_RIGHT) {
//            rightPressed = false;
//        }
//    }

//    @Override
//    public void keyReleased(KeyEvent e){
//            int keyCode = e.getKeyCode();

//        if (keyCode == KeyEvent.VK_UP) {
//            upPressed = false;
//            downPressed = true;
//        }
//        if (keyCode == KeyEvent.VK_M) {
//            resetKeys();


//        }
//        if (keyCode == KeyEvent.VK_DOWN) {
//            downPressed = false;
//        }
//        if (keyCode == KeyEvent.VK_LEFT) {
//            leftPressed = false;
//        }
//        if (keyCode == KeyEvent.VK_RIGHT) {
//            rightPressed = false;
//        }
        }

    @Override
    public void keyReleased(KeyEvent e) {
//        int keyCode = e.getKeyCode();
//
//        if (keyCode == KeyEvent.VK_SPACE){
//            spacePressed = false;
//        }

    }
    public void resetKeys(){
        upPressed = false;
        downPressed = false;
        rightPressed = false;
        leftPressed = false;
    }

}


