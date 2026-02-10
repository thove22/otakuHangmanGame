package main.com.otakuhangman.gui.screens;

import main.com.otakuhangman.gui.Screen;
import main.com.otakuhangman.core.HangmanArt;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Challenge extends Screen {
    // --- UI COLOR PALETTE ---
    private final Color COLOR_CYAN = new Color(0, 190, 190);
    private final Color COLOR_RED = new Color(180, 50, 50);
    private final Color COLOR_YELLOW = new Color(200, 180, 50);
    private final Color COLOR_BG = new Color(13, 13, 13);
    private final Color COLOR_GALLOWS_BG = new Color(13, 13, 13);
    private final Color COLOR_BORDER = new Color(50, 50, 50); // Dashed border color
    private final Color COLOR_TEXT_DIM = new Color(90, 90, 90);

    // --- COMPONENTS ---
    private JTextArea gallowsArea;
    private JLabel wordStatusLabel, hintLabel;
    private JLabel levelVal, challengeVal, scoreVal, timeVal;
    private JLabel feedbackBanner, attemptLettersVal, remainingText;
    private JTextField inputField;
    private JPanel feedbackPanel, triedLettersContainer;

    public Challenge() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BG);

        // Wrapper to handle the global outer margin
        JPanel mainWrapper = new JPanel(new GridBagLayout());
        mainWrapper.setBackground(COLOR_BG);
        mainWrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(mainWrapper, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // === ZONE A: HUD (Centralized & Dashed) ===
        gbc.gridy = 0; gbc.weighty = 0.05; // Reduced weight to remove white space
        mainWrapper.add(createBoxedHud(), gbc);

        // === ZONE B: GAMEPLAY (Vertical Separator & Bold Gallows) ===
        gbc.gridy = 1; gbc.weighty = 0.7; // Major focus here
        gbc.insets = new Insets(5, 0, 5, 0);
        mainWrapper.add(createGameplaySplit(), gbc);

        // === ZONE C: FEEDBACK (Layout Specific) ===
        gbc.gridy = 2; gbc.weighty = 0.1;
        mainWrapper.add(createFeedbackBanner(), gbc);

        // === ZONE D: INPUT & TRIED LETTERS (Dashed Box) ===
        gbc.gridy = 3; gbc.weighty = 0.15;
        mainWrapper.add(createInputStatsActive(), gbc);
    }

    private JPanel createBoxedHud() {
        JPanel p = new JPanel(new GridLayout(1, 4));
        p.setBackground(COLOR_BG);
        p.setBorder(new DashedBorder(COLOR_BORDER)); // Custom dashed ASCII border

        levelVal = createHudItem(p, "LEVEL STATUS", "LVL 01/05", COLOR_CYAN);
        challengeVal = createHudItem(p, "CURRENT CHALLENGE", "# 1 of 10", Color.WHITE);
        scoreVal = createHudItem(p, "TOTAL SCORE", "00500 PTS", COLOR_YELLOW);
        timeVal = createHudItem(p, "TIME REMAINING", "08s", COLOR_RED);
        return p;
    }

    private JLabel createHudItem(JPanel parent, String title, String value, Color vCol) {
        JPanel item = new JPanel(new GridBagLayout()); // GridBag for perfect centering
        item.setOpaque(false);
        GridBagConstraints ic = new GridBagConstraints();

        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(new Font("Monospaced", Font.PLAIN, 11));
        t.setForeground(COLOR_TEXT_DIM);

        JLabel v = new JLabel(value, SwingConstants.CENTER);
        v.setFont(new Font("Monospaced", Font.BOLD, 24));
        v.setForeground(vCol);

        ic.gridy = 0; item.add(t, ic);
        ic.gridy = 1; item.add(v, ic);

        parent.add(item);
        return v;
    }

    private JPanel createGameplaySplit() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(COLOR_BG);
        container.setBorder(new DashedBorder(COLOR_BORDER));
        GridBagConstraints g = new GridBagConstraints();

        // --- Left: Gallows ---
        JPanel gallowsPanel = new JPanel(new BorderLayout());
        gallowsPanel.setBackground(COLOR_GALLOWS_BG);
        // Vertical Separator
        gallowsPanel.setBorder(new MatteBorder(0, 0, 0, 1, COLOR_BORDER));

        gallowsArea = new JTextArea(HangmanArt.STAGES[0]);
        gallowsArea.setFont(new Font("Monospaced", Font.BOLD, 22)); // High presence
        gallowsArea.setForeground(COLOR_CYAN);
        gallowsArea.setOpaque(false);
        gallowsArea.setEditable(false);
        gallowsArea.setMargin(new Insets(20, 20, 20, 20));
        gallowsPanel.add(gallowsArea, BorderLayout.CENTER);

        g.gridx = 0; g.weightx = 0.4; g.fill = GridBagConstraints.BOTH;
        container.add(gallowsPanel, g);

        // --- Right: Hint & Word ---
        JPanel wordSection = new JPanel(new GridBagLayout());
        wordSection.setOpaque(false);
        GridBagConstraints wg = new GridBagConstraints();

        // Hint Box (Yellow Accent)
        JPanel hintBox = new JPanel(new BorderLayout());
        hintBox.setBackground(new Color(20, 20, 20));
        hintBox.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 4, 0, 0, COLOR_YELLOW),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        hintLabel = new JLabel("\"A powerful emotion that drives the protagonist...\"");
        hintLabel.setForeground(COLOR_TEXT_DIM);
        hintLabel.setFont(new Font("Monospaced", Font.ITALIC, 18));
        JLabel hintTag = new JLabel("[HINT] ");
        hintTag.setForeground(COLOR_YELLOW);
        hintTag.setFont(new Font("Monospaced", Font.BOLD, 18));
        hintBox.add(hintTag, BorderLayout.WEST);
        hintBox.add(hintLabel, BorderLayout.CENTER);

        wg.gridy = 0; wg.insets = new Insets(0, 20, 50, 20);
        wordSection.add(hintBox, wg);

        // Masked Word
        wordStatusLabel = new JLabel("O _ A _ U");
        wordStatusLabel.setFont(new Font("Monospaced", Font.BOLD, 32));
        wordStatusLabel.setForeground(Color.WHITE);
        wg.gridy = 1; wg.insets = new Insets(0, 20, 0, 20);
        wordSection.add(wordStatusLabel, wg);

        g.gridx = 1; g.weightx = 0.6;
        container.add(wordSection, g);

        return container;
    }

    private JPanel createFeedbackBanner() {
        feedbackPanel = new JPanel(new BorderLayout());
        feedbackPanel.setBackground(COLOR_BG);
        feedbackBanner = new JLabel("", SwingConstants.CENTER); // Starts empty per layout
        feedbackBanner.setFont(new Font("Monospaced", Font.BOLD, 22));
        feedbackPanel.add(feedbackBanner, BorderLayout.CENTER);
        return feedbackPanel;
    }

    private JPanel createInputStatsActive() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(COLOR_BG);
        container.setBorder(new DashedBorder(COLOR_BORDER));
        GridBagConstraints g = new GridBagConstraints();
        g.weightx = 1.0; g.fill = GridBagConstraints.BOTH;

        // 1. Left: Tried Letters
        JPanel triedWrap = new JPanel(new BorderLayout());
        triedWrap.setOpaque(false);
        triedWrap.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        JLabel triedTitle = new JLabel("TRIED LETTERS", SwingConstants.LEFT);
        triedTitle.setForeground(COLOR_TEXT_DIM);
        triedTitle.setFont(new Font("Monospaced", Font.PLAIN, 14));

        triedLettersContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        triedLettersContainer.setOpaque(false);
        // Placeholder boxes with slashes (Risqued)
        String[] demo = {"A", "E", "I", "X", "Z"};
        for(String s : demo) addTriedLetter(s);

        triedWrap.add(triedTitle, BorderLayout.NORTH);
        triedWrap.add(triedLettersContainer, BorderLayout.CENTER);
        g.gridx = 0; container.add(triedWrap, g);

        // 2. Center: Input
        JPanel inputContainer = new JPanel(new GridBagLayout());
        inputContainer.setOpaque(false);
        JLabel inputTitle = new JLabel("ENTER GUESS CHAR >", SwingConstants.CENTER);
        inputTitle.setForeground(COLOR_CYAN);
        inputTitle.setFont(new Font("Monospaced", Font.BOLD, 14));

        inputField = new JTextField(" ", 1); // Underscore placeholder
        inputField.setBackground(COLOR_BG);
        inputField.setForeground(Color.WHITE);
        inputField.setFont(new Font("Monospaced", Font.BOLD, 32));
        inputField.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 2));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setPreferredSize(new Dimension(90, 60));

        JLabel confirmText = new JLabel("Press [ENTER] to confirm", SwingConstants.CENTER);
        confirmText.setForeground(COLOR_TEXT_DIM);
        confirmText.setFont(new Font("Monospaced", Font.PLAIN, 14));

        GridBagConstraints ic = new GridBagConstraints();
        ic.gridy = 0; inputContainer.add(inputTitle, ic);
        ic.gridy = 1; ic.insets = new Insets(5,0,5,0); inputContainer.add(inputField, ic);
        ic.gridy = 2; inputContainer.add(confirmText, ic);

        g.gridx = 1; container.add(inputContainer, g);

        // 3. Right: Attempt Status
        JPanel lifePanel = new JPanel(new GridBagLayout());
        lifePanel.setOpaque(false);
        lifePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        GridBagConstraints lc = new GridBagConstraints();
        lc.anchor = GridBagConstraints.EAST;

        JLabel lpTitle = new JLabel("ATTEMPT STATUS", SwingConstants.RIGHT);
        lpTitle.setForeground(COLOR_TEXT_DIM);
        lpTitle.setFont(new Font("Monospaced", Font.PLAIN, 14));

        attemptLettersVal = new JLabel("X X X _ _ _", SwingConstants.RIGHT);
        attemptLettersVal.setFont(new Font("Monospaced", Font.BOLD, 24));
        attemptLettersVal.setForeground(COLOR_RED);

        remainingText = new JLabel("(3 / 6) REMAINING", SwingConstants.RIGHT);
        remainingText.setForeground(COLOR_RED);
        remainingText.setFont(new Font("Monospaced", Font.PLAIN, 12));

        lc.gridy = 0; lifePanel.add(lpTitle, lc);
        lc.gridy = 1; lifePanel.add(attemptLettersVal, lc);
        lc.gridy = 2; lifePanel.add(remainingText, lc);

        g.gridx = 2; container.add(lifePanel, g);

        return container;
    }

    private void addTriedLetter(String s) {
        JLabel letter = new JLabel(s, SwingConstants.CENTER);
        letter.setPreferredSize(new Dimension(35, 35));
        letter.setForeground(COLOR_TEXT_DIM);
        // Dashed box for letter + "Risqued" (strike-through) look
        letter.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        letter.setText("<html><strike>" + s + "</strike></html>");
        triedLettersContainer.add(letter);
    }

    // --- LOGIC METHODS ---
    public void setFeedback(String message, boolean isError) {
        if (isError) {
            feedbackPanel.setBackground(COLOR_RED);
            feedbackBanner.setText("△ [ ! ] " + message.toUpperCase() + " [ ! ] △");
            feedbackBanner.setForeground(Color.WHITE);
        } else {
            feedbackPanel.setBackground(COLOR_BG);
            feedbackBanner.setText(message);
            feedbackBanner.setForeground(COLOR_CYAN);
        }
        feedbackPanel.setOpaque(true);
    }

    @Override public void onEnter() { inputField.requestFocusInWindow(); }
    @Override public void onExit() {}

    // Inner class for the dashed ASCII-style border
    private static class DashedBorder extends AbstractBorder {
        private final Color color;
        public DashedBorder(Color color) { this.color = color; }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(color);
            float[] dash = {4f, 4f};
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dash, 0));
            g2.drawRect(x, y, width - 1, height - 1);
            g2.dispose();
        }
    }
}