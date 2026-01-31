package main.com.otakuhangman.core;

import main.com.otakuhangman.gui.screens.Menu;
import main.com.otakuhangman.gui.screens.OnBoarding;

import javax.swing.*;

public class Main {
//    public static void main(String [] args){
//        Game game = new Game();
//        game.start();
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame frame = new JFrame("Otaku Hangman");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            OnBoarding intro = new OnBoarding();
            frame.add(intro);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            intro.onEnter();
        });
    }
}
