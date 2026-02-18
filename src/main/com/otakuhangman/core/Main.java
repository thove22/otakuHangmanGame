package main.com.otakuhangman.core;

import main.com.otakuhangman.gui.AppCordinator;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame frame = new JFrame("Otaku Hangman");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            AppCordinator coordinator = new AppCordinator();
            frame.setContentPane(coordinator.build());
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
