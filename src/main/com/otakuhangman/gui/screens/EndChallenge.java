package main.com.otakuhangman.gui.screens;

import main.com.otakuhangman.gui.Screen;
import main.com.otakuhangman.core.EndChallengeReason;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class EndChallenge extends Screen {
    // Colors
    private final Color COLOR_WIN = new Color(0, 255, 150); // Vibrant Green
    private final Color COLOR_FAIL = new Color(255, 50, 50); // Aggressive Red
    private final Color COLOR_BG = Color.BLACK;
    private final Color COLOR_TEXT_DIM = new Color(150, 150, 150);

    public EndChallenge(EndChallengeReason reason, int totalScore, int pointsGained, String rank, String word) {
        setLayout(new GridBagLayout());
        setBackground(COLOR_BG);

        // Define Theme based on Reason
        boolean isWon = (reason == EndChallengeReason.WON);
        Color themeColor = isWon ? COLOR_WIN : COLOR_FAIL;
        String title = isWon ? "MISSION ACCOMPLISHED" : getFailureTitle(reason);
        String mainMsg = isWon ? "PARABÉNS !" : "VOCÊ FALHOU !";
        String subMsg = isWon ? "Você adivinhou a palavra:" : "A palavra era:";
        String asciiArt = isWon ? "  /\\ /\\ /\\ /\\ /\\ \n (  V I C T O R Y  )\n  \\/ \\/ \\/ \\/ \\/ "
                : "      _.-^^---....,,--_\n  _--                  --_\n <    S Y S T E M  O F F   >\n  \\._                _./\n     ```--. . , ; .--''' ";

        // 1. CENTRAL SQUARE (The Main Frame)
        JPanel centralPanel = new JPanel(new GridBagLayout());
        centralPanel.setBackground(new Color(15, 15, 15)); // Darker gray for depth
        centralPanel.setPreferredSize(new Dimension(650, 450));
        centralPanel.setBorder(new CornerSquareBorder(themeColor));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.CENTER;

        // Title: MISSION ACCOMPLISHED / FAILED
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblTitle.setForeground(themeColor);
        gbc.gridy = 0; centralPanel.add(lblTitle, gbc);

        // ASCII Art Area
        JTextArea artArea = new JTextArea(asciiArt);
        artArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        artArea.setForeground(themeColor);
        artArea.setOpaque(false);
        artArea.setEditable(false);
        gbc.gridy = 1; centralPanel.add(artArea, gbc);

        // Main Result: PARABENS / FAIL
        JLabel lblMain = new JLabel(mainMsg);
        lblMain.setFont(new Font("Monospaced", Font.BOLD, 48));
        lblMain.setForeground(Color.WHITE);
        gbc.gridy = 2; centralPanel.add(lblMain, gbc);

        // Word Reveal
        JPanel wordPanel = new JPanel(new FlowLayout());
        wordPanel.setOpaque(false);
        JLabel lblSub = new JLabel(subMsg);
        lblSub.setForeground(COLOR_TEXT_DIM);
        JLabel lblWord = new JLabel(word.toUpperCase());
        lblWord.setFont(new Font("Monospaced", Font.BOLD, 28));
        lblWord.setForeground(themeColor);
        wordPanel.add(lblSub);
        wordPanel.add(lblWord);
        gbc.gridy = 3; centralPanel.add(wordPanel, gbc);

        // Stats Row: Total Score | Pontos Ganhos | Rank
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setOpaque(false);
        addStatItem(statsPanel, "TOTAL SCORE", String.format("%,d", totalScore));
        addStatItem(statsPanel, "PONTOS GANHOS", "+" + pointsGained);
        addStatItem(statsPanel, "ACTUAL RANK", rank);
        gbc.gridy = 4; gbc.insets = new Insets(30, 0, 10, 0);
        centralPanel.add(statsPanel, gbc);

        // Footer: Press Enter
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(25, 25, 25));
        footer.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40)));
        JLabel lblEnter = new JLabel("Pressione [ENTER] para continuar...", SwingConstants.CENTER);
        lblEnter.setFont(new Font("Monospaced", Font.PLAIN, 12));
        lblEnter.setForeground(COLOR_TEXT_DIM);
        footer.add(lblEnter, BorderLayout.CENTER);
        gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        centralPanel.add(footer, gbc);

        add(centralPanel);
    }

    private void addStatItem(JPanel parent, String label, String val) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel l = new JLabel(label, SwingConstants.CENTER);
        l.setFont(new Font("Monospaced", Font.PLAIN, 10));
        l.setForeground(COLOR_TEXT_DIM);
        JLabel v = new JLabel(val, SwingConstants.CENTER);
        v.setFont(new Font("Monospaced", Font.BOLD, 20));
        v.setForeground(Color.WHITE);
        p.add(l, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);
        parent.add(p);
    }

    private String getFailureTitle(EndChallengeReason reason) {
        return switch (reason) {
            case TIME_UP -> "MISSION FAILED: TIMEOUT";
            case ERROR_LIMIT -> "MISSION FAILED: EXECUTION";
            case ATTEMPS_LIMIT -> "MISSION FAILED: RESOURCE DEPLETED";
            default -> "MISSION FAILED";
        };
    }

    // --- CUSTOM BORDER WITH CORNER SQUARES ---
    private static class CornerSquareBorder extends AbstractBorder {
        private final Color color;
        private final int squareSize = 8;

        public CornerSquareBorder(Color color) { this.color = color; }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));

            // Main Border Line
            g2.drawRect(x + 2, y + 2, width - 5, height - 5);

            // Corner Squares
            g2.fillRect(x, y, squareSize, squareSize); // Top-Left
            g2.fillRect(x + width - squareSize, y, squareSize, squareSize); // Top-Right
            g2.fillRect(x, y + height - squareSize, squareSize, squareSize); // Bottom-Left
            g2.fillRect(x + width - squareSize, y + height - squareSize, squareSize, squareSize); // Bottom-Right

            g2.dispose();
        }
    }

    @Override public void onEnter() {}
    @Override public void onExit() {}
}