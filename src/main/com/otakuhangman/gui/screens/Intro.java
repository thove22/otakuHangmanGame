package main.com.otakuhangman.gui.screens;
import main.com.otakuhangman.gui.Screen;
import main.com.otakuhangman.gui.utils.AsciiArt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Intro extends Screen {
    private JTextArea ascii;

    public Intro(){
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        ascii = new JTextArea();
        ascii.setEditable(false);
        ascii.setFocusable(false);
        ascii.setBackground(Color.BLACK);
        ascii.setForeground(new Color(200, 200, 200));
        ascii.setFont(new Font("Monospaced", Font.BOLD, 16));
        String rawArt = "\n" +  AsciiArt.TITLE() + "\n" + AsciiArt.PAINT();
        String boxedArt = AsciiArt.drawBoxAscii(rawArt);
        String finalView = boxedArt + "\n" + "    [ PRESSIONE ENTER PARA CONTINUAR ]";
        ascii.setText(finalView);
        ascii.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(ascii, new GridBagConstraints());

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    System.out.println("Enter pressed! Moving to Character Selection...");
                    handleScreenTransition();
                }
            }
        });
    }
    private void handleScreenTransition(){

    }

    public void onEnter() {
       this.requestFocusInWindow();
    }
    public void onExit() {
    }


}
