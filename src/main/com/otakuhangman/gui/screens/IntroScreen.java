package main.com.otakuhangman.gui.screens;
import main.com.otakuhangman.gui.Screen;

import javax.swing.*;
import java.awt.*;

public class IntroScreen extends Screen {
    private JTextArea ascii;

    public IntroScreen(){
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        ascii = new JTextArea();
        ascii.setEditable(false);
        ascii.setFocusable(false);
        ascii.setBackground(Color.BLACK);
        ascii.setForeground(new Color(200, 200, 200));
        ascii.setFont(new Font("Monospaced", Font.BOLD, 16));
        String rawArt = "\n" +  AsciiArt.TITLE() + "\n" + AsciiArt.paint();
        String boxedArt = AsciiArt.drawBoxAscii(rawArt);
        String finalView = boxedArt + "\n" + "    [ PRESS ENTER TO CONTINUE ]";
        ascii.setText(finalView);
        ascii.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(ascii, new GridBagConstraints());

    }
    public void onEnter() {
        requestFocusInWindow();
    }
    public void onExit() {
    }


}
