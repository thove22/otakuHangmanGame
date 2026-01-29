package main.com.otakuhangman.gui.screens;

import main.com.otakuhangman.gui.Screen;

import javax.swing.*;
import java.awt.*;

public class IntroScreen extends Screen {
    private JTextField ascii;

    public IntroScreen(){
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        ascii = new JTextField();
        ascii.setEditable(false);
        ascii.setFocusable(false);
        ascii.setBackground(Color.BLACK);
        ascii.setForeground(Color.WHITE);
        ascii.setFont(new Font("Monospaced", Font.PLAIN, 14));
        ascii.setText(AsciiArt.TITLE());
        add(ascii,BorderLayout.CENTER);

    }
    public void onEnter() {
        requestFocusInWindow();
    }
    public void onExit() {
    }
}
