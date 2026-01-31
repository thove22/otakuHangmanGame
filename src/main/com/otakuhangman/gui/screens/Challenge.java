package main.com.otakuhangman.gui.screens;
import main.com.otakuhangman.gui.Screen;
import main.com.otakuhangman.core.HangmanArt;
import main.com.otakuhangman.gui.utils.AsciiArt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Challenge extends Screen {
    private JTextArea gallowsArea;
    private JTextArea hintArea;
    private JTextArea wordStatusArea;
    private JLabel hudLabel;
    private JLabel feedbackLabel;
    private JLabel triedLettersLabel;
    private JLabel attemptsLabel;
    private JTextField inputField;

    public Challenge(){
        setLayout(new OverlayLayout(this));
        setBackground(Color.BLACK);
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        initializeZones(contentPanel);
        add(contentPanel);

        JTextArea borderBackground = new JTextArea();
        borderBackground.setEditable(false);
        borderBackground.setFocusable(false);
        borderBackground.setBackground(Color.BLACK);
        borderBackground.setForeground(new Color(200, 200, 200));
        borderBackground.setFont(new Font("Monospaced", Font.BOLD, 14));
        StringBuilder emptyContent = new StringBuilder();
        for(int i=0; i<28; i++) emptyContent.append(" ".repeat(95)).append("\n");
        borderBackground.setText(AsciiArt.drawBoxAscii(emptyContent.toString()));
        add(borderBackground);
    }
    public void initializeZones(JPanel contentPanel){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(15, 30, 15, 30);
        hudLabel = new JLabel("LEVEL: 01/05 | CHALLENGE: 01/10 | SCORE: 00000 | TIME: 60s");
        hudLabel.setForeground(Color.WHITE);
        hudLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        hudLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        contentPanel.add(hudLabel, gbc);

        JPanel zoneB = new JPanel(new GridBagLayout());
        zoneB.setOpaque(false);
        gbc.gridy = 1; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(zoneB, gbc);

        gallowsArea = new JTextArea(AsciiArt.getGallows(0)); // Start empty
        gallowsArea.setEditable(false);
        gallowsArea.setFocusable(false);
        gallowsArea.setBackground(Color.BLACK);
        gallowsArea.setForeground(Color.WHITE);
        gallowsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        GridBagConstraints gbcB = new GridBagConstraints();
        gbcB.gridx = 0; gbcB.gridy = 0; gbcB.anchor = GridBagConstraints.CENTER;
        gbcB.insets = new Insets(0, 0, 0, 40); // Spacing between gallows and word
        zoneB.add(gallowsArea, gbcB);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        gbcB.gridx = 1; gbcB.weightx = 1.0; gbcB.insets = new Insets(0, 0, 0, 0);
        zoneB.add(rightPanel, gbcB);

        hintArea = new JTextArea("HINT: A popular Japanese animation style.");
        hintArea.setEditable(false);
        hintArea.setFocusable(false);
        hintArea.setBackground(Color.BLACK);
        hintArea.setForeground(Color.CYAN); // Cyan for hints
        hintArea.setFont(new Font("Monospaced", Font.ITALIC, 16));
        hintArea.setLineWrap(true);
        hintArea.setWrapStyleWord(true);
        hintArea.setPreferredSize(new Dimension(400, 60));
        GridBagConstraints gbcR = new GridBagConstraints();
        gbcR.gridx = 0; gbcR.gridy = 0; gbcR.insets = new Insets(0, 0, 30, 0);
        gbcR.anchor = GridBagConstraints.CENTER;
        rightPanel.add(hintArea, gbcR);

        wordStatusArea = new JTextArea("_ N _ M _"); // Placeholder
        wordStatusArea.setEditable(false);
        wordStatusArea.setFocusable(false);
        wordStatusArea.setBackground(Color.BLACK);
        wordStatusArea.setForeground(Color.WHITE);
        wordStatusArea.setFont(new Font("Monospaced", Font.BOLD, 32)); // Big font for the word
        gbcR.gridy = 1;
        rightPanel.add(wordStatusArea, gbcR);

        feedbackLabel = new JLabel(" "); // Initial empty state
        feedbackLabel.setForeground(Color.WHITE);
        feedbackLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2; gbc.weighty = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(feedbackLabel, gbc);

        JPanel zoneD = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        zoneD.setOpaque(false);
        gbc.gridy = 3;
        contentPanel.add(zoneD, gbc);

        triedLettersLabel = new JLabel("Tried: [ None ]");
        triedLettersLabel.setForeground(Color.GRAY);
        triedLettersLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        zoneD.add(triedLettersLabel);

        attemptsLabel = new JLabel("Attempts: 0/6");
        attemptsLabel.setForeground(Color.GRAY);
        attemptsLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        zoneD.add(attemptsLabel);

        inputField = new JTextField(1);
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        inputField.setFont(new Font("Monospaced", Font.BOLD, 20));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setPreferredSize(new Dimension(40, 30));
        zoneD.add(inputField);


    }
    public void updateHUD(int level, int challenge, int score, int time){
        hudLabel.setText(String.format("LEVEL: %02d/05 | CHALLENGE: %02d/10 | SCORE: %05d | TIME: %ds", level, challenge, score, time));

        hudLabel.setForeground(time <= 10 ? Color.RED : Color.WHITE);
    }
    public void updateGallows(int errors) {
        gallowsArea.setText(AsciiArt.getGallows(errors));
    }

    public void updateWordStatus(String status) {
        wordStatusArea.setText(status);
    }

    public void updateHint(String hint) {
        hintArea.setText("HINT: " + hint);
    }

    public void setFeedback(String message, Color color) {
        feedbackLabel.setText(message);
        feedbackLabel.setForeground(color);
    }

    public void updateStats(String triedLetters, int attempts, int maxAttempts) {
        triedLettersLabel.setText("Tried: [ " + (triedLetters.isEmpty() ? "None" : triedLetters) + " ]");
        attemptsLabel.setText("Attempts: " + attempts + "/" + maxAttempts);
    }

    public JTextField getInputField() {
        return inputField;
    }

    @Override
    public void onEnter() {
        inputField.requestFocusInWindow();
    }

    @Override
    public void onExit() {

    }

}
