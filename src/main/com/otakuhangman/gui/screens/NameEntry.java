package main.com.otakuhangman.gui.screens;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.com.otakuhangman.gui.Screen;
import main.com.otakuhangman.gui.utils.AsciiArt;

public class NameEntry extends Screen {
    private JTextField nameInput;
    private JTextArea textDisplay;

    public NameEntry(){
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        String nameAsk = " WHAT IS YOUR NAME, TRAVELER? ";
        textDisplay = new JTextArea(AsciiArt.drawBoxAscii(nameAsk));
        textDisplay.setEditable(false);
        textDisplay.setFocusable(false);
        textDisplay.setBackground(Color.BLACK);
        textDisplay.setForeground(new Color(70, 100, 255));
        textDisplay.setFont(new Font("Monospaced", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(textDisplay, gbc);
        nameInput = new JTextField(15);
        nameInput.setBackground(Color.BLACK);
        nameInput.setForeground(Color.WHITE);
        nameInput.setCaretColor(Color.WHITE);
        nameInput.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        nameInput.setFont(new Font("Monospaced", Font.BOLD, 24));
        nameInput.setHorizontalAlignment(JTextField.CENTER);
        nameInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameInput.getText().trim();
                if (!playerName.isEmpty()) {
                    proceedToGame(playerName);
                }
            }
        });
        gbc.gridy = 1;
        add(nameInput, gbc);
        JLabel footer = new JLabel("[ Press ENTER to confirm ]");
        footer.setForeground(Color.DARK_GRAY);
        footer.setFont(new Font("Monospaced", Font.ITALIC, 14));
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(footer, gbc);
    }
    private void proceedToGame(String name) {
        System.out.println("Welcome, " + name);

    }
    public void onEnter() {
        nameInput.requestFocusInWindow();
    }
    public void onExit() {}
}
