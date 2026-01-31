package main.com.otakuhangman.gui.screens;
import main.com.otakuhangman.gui.Screen;
import main.com.otakuhangman.gui.utils.AsciiArt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OnBoarding extends Screen {
    private JTextArea textDisplay;
    private  int charIndex = 0;
    private Timer typewriterTimer;


    public OnBoarding(){
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        textDisplay = new JTextArea();
        textDisplay.setEditable(false);
        textDisplay.setFocusable(false);
        textDisplay.setBackground(Color.BLACK);
        textDisplay.setForeground(new Color(220, 220, 220));
        textDisplay.setFont(new Font("Monospaced", Font.BOLD, 16));
        textDisplay.setMargin(new Insets(20, 20, 20, 20));
        add(textDisplay, new GridBagConstraints());
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String fullText = AsciiArt.ONBOARDING_TEXT();
               if(charIndex < fullText.length()){
                   typewriterTimer.stop();
                   charIndex = fullText.length();
                   textDisplay.setText(AsciiArt.drawBoxAscii(fullText));
               }else {
                   System.out.println("Entering the Game...");
               }
            }
        });
        typewriterTimer = new Timer(30, e -> {
            String fullText = AsciiArt.ONBOARDING_TEXT();
            if (charIndex < fullText.length()) {
                textDisplay.append(String.valueOf(AsciiArt.ONBOARDING_TEXT().charAt(charIndex)));
                charIndex++;
            } else {
                typewriterTimer.stop();
            }
        });
    }
    public void onEnter() {
        this.requestFocusInWindow();
        textDisplay.setText("");

        charIndex = 0;
        typewriterTimer.start();
    }
    public void onExit() {
        typewriterTimer.stop();
    }
}
