package main.com.otakuhangman.gui.screens;

import main.com.otakuhangman.gui.Screen;
import main.com.otakuhangman.core.EndChallengeReason;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class EndChallenge extends Screen implements ActionListener {

    // --- CONFIGURATION & COLORS ---
    private final Color COLOR_BG = new Color(5, 5, 5);
    private final Color COLOR_SCANLINE = new Color(0, 243, 255, 10);

    // Theme Colors (Matches CSS variables)
    private final Color THEME_GREEN = new Color(0, 255, 157); // #00ff9d
    private final Color THEME_CYAN = new Color(0, 243, 255);  // #00f3ff
    private final Color THEME_RED = new Color(255, 0, 85);    // #ff0055
    private final Color THEME_ORANGE = new Color(255, 184, 0);// #ffb800

    private Color currentThemeColor;
    private Color currentGlowColor;

    // --- STATE DATA ---
    private final EndChallengeReason reason;
    private final int totalScore;
    private final int pointsGained;
    private final String rank;
    private final String word;
    private final boolean isWon;

    // --- ANIMATION VARIABLES ---
    private final Timer timer;
    private float timeTicks = 0; // Global time tracker
    private float scanlineY = 0; // Vertical position of scanline
    private final Random random = new Random();

    // Fonts
    private Font fontTitle, fontMain, fontMono, fontSmall;

    public EndChallenge(EndChallengeReason reason, int totalScore, int pointsGained, String rank, String word) {
        this.reason = reason;
        this.totalScore = totalScore;
        this.pointsGained = pointsGained;
        this.rank = rank;
        this.word = word;
        this.isWon = (reason == EndChallengeReason.WON);

        // 1. Setup Theme based on result
        setupTheme();

        // 2. Setup Fonts (Fallbacks included)
        try {
            fontTitle = new Font("JetBrains Mono", Font.BOLD, 20);
            fontMain = new Font("JetBrains Mono", Font.BOLD, 60);
            fontMono = new Font("JetBrains Mono", Font.PLAIN, 14);
            fontSmall = new Font("JetBrains Mono", Font.BOLD, 12);
        } catch (Exception e) {
            fontTitle = new Font("Monospaced", Font.BOLD, 24);
            fontMain = new Font("Monospaced", Font.BOLD, 60);
            fontMono = new Font("Monospaced", Font.PLAIN, 24);
            fontSmall = new Font("Monospaced", Font.BOLD, 10);
        }

        // 3. Start Animation Loop (60 FPS)
        timer = new Timer(16, this);
        timer.start();

        // Hide standard layout manager since we are custom painting
        setLayout(null);
        setFocusable(true);
        requestFocusInWindow();
    }

    private void setupTheme() {
        if (isWon) {
            currentThemeColor = THEME_GREEN;
            currentGlowColor = new Color(0, 255, 157, 100);
        } else {
            switch (reason) {
                case TIME_UP -> {
                    currentThemeColor = THEME_RED;
                    currentGlowColor = new Color(255, 0, 85, 100);
                }
                case ATTEMPS_LIMIT -> {
                    currentThemeColor = THEME_ORANGE;
                    currentGlowColor = new Color(255, 184, 0, 100);
                }
                default -> {
                    currentThemeColor = new Color(139, 0, 0); // Dark Red
                    currentGlowColor = new Color(139, 0, 0, 100);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Increment Animation Ticks
        timeTicks += 0.05f;

        // Move Scanline
        scanlineY += 2.0f;
        if (scanlineY > getHeight()) scanlineY = -100;

        // Force redraw
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // High Quality Rendering Settings
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // 1. Draw Background
        g2.setColor(COLOR_BG);
        g2.fillRect(0, 0, w, h);

        // 2. Calculate Floating Animation (Sine Wave)
        // CSS: animate-float { transform: translateY(-10px); }
        int floatOffset = (int) (Math.sin(timeTicks) * 8);

        // 3. Define Card Geometry
        int cardW = Math.min(950, w - 80);
        int cardH = 550;
        int cardX = (w - cardW) / 2;
        int cardY = (h - cardH) / 2 + floatOffset;

        // 4. Draw Glow (Outer Glow CSS effect)
        drawGlow(g2, cardX, cardY, cardW, cardH, currentThemeColor);

        // 5. Draw Card Background (Semi-transparent black)
        g2.setColor(new Color(10, 10, 10, 240));
        g2.fillRect(cardX, cardY, cardW, cardH);

        // 6. Draw Card Borders & Corners
        g2.setColor(currentThemeColor);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRect(cardX, cardY, cardW, cardH);

        // Draw Decorative Corners (The little squares from the CSS)
        int cornerSize = 14;
        g2.fillRect(cardX - 2, cardY - 2, cornerSize, cornerSize); // TL
        g2.fillRect(cardX + cardW - cornerSize + 2, cardY - 2, cornerSize, cornerSize); // TR
        g2.fillRect(cardX - 2, cardY + cardH - cornerSize + 2, cornerSize, cornerSize); // BL
        g2.fillRect(cardX + cardW - cornerSize + 2, cardY + cardH - cornerSize + 2, cornerSize, cornerSize); // BR

        // 7. Render Content Inside the Card
        drawCardContent(g2, cardX, cardY, cardW, cardH);

        // 8. Draw Scanlines (Overlay)
        drawScanlines(g2, w, h);
    }

    private void drawCardContent(Graphics2D g2, int x, int y, int w, int h) {
        int centerX = x + (w / 2);
        int currentY = y + 80;

        // --- HEADER: MISSION STATUS ---
        // logic: If won, standard text. If lost, append the reason nicely formatted.
        String statusText;
        if (isWon) {
            statusText = "MISSION ACCOMPLISHED";
        } else {
            // Converts "TIME_UP" to "TIME UP" and "ERROR_LIMIT" to "ERROR LIMIT"
            String reasonText = reason.toString().replace("_", " ");
            statusText = "MISSION FAILED: " + reasonText;
        }

        g2.setFont(fontTitle);
        // Pulse opacity for header
        int alphaPulse = (int) (150 + Math.sin(timeTicks * 2) * 100);
        alphaPulse = Math.max(0, Math.min(255, alphaPulse));
        g2.setColor(new Color(currentThemeColor.getRed(), currentThemeColor.getGreen(), currentThemeColor.getBlue(), alphaPulse));
        drawCenteredString(g2, statusText, centerX, currentY);

        // --- MAIN TITLE (GLITCH EFFECT) ---
        currentY += 80;
        String mainTitle = isWon ? "PARABÉNS!" : "GAME OVER";
        g2.setFont(fontMain);

        // Glitch Logic (5% chance)
        if (random.nextInt(100) > 95) {
            g2.setColor(Color.RED);
            drawCenteredString(g2, mainTitle, centerX - 3, currentY);
            g2.setColor(Color.CYAN);
            drawCenteredString(g2, mainTitle, centerX + 3, currentY);
        } else {
            g2.setColor(new Color(currentThemeColor.getRed(), currentThemeColor.getGreen(), currentThemeColor.getBlue(), 50));
            drawCenteredString(g2, mainTitle, centerX, currentY + 2); // Shadow
        }
        g2.setColor(Color.WHITE);
        drawCenteredString(g2, mainTitle, centerX, currentY);

        // --- SUBTITLE & WORD REVEAL ---
        currentY += 60;
        g2.setFont(fontMono);
        g2.setColor(Color.GRAY);
        String subMsg = isWon ? "Você adivinhou a palavra:" : "A palavra era:";
        drawCenteredString(g2, subMsg, centerX, currentY);

        currentY += 60;
        g2.setFont(fontMain.deriveFont(60f));
        g2.setColor(currentThemeColor);
        drawCenteredString(g2, word.toUpperCase(), centerX, currentY);

        // --- STATS ROW (Score | Rank | Bonus) ---
        currentY += 80;

        // Draw Separator Line
        g2.setColor(new Color(50, 50, 50));
        g2.drawLine(centerX - 250, currentY - 30, centerX + 250, currentY - 30);

        // Define spacing for 3 items
        int statSpacing = 220;

        // 1. LEFT: TOTAL SCORE
        drawStat(g2, "TOTAL SCORE", String.format("%,d", totalScore), centerX - statSpacing, currentY, Color.WHITE);

        // 2. CENTER: RANK (New Element)
        // We use the current theme color for the Rank to make it pop
        drawStat(g2, "RANK", rank, centerX, currentY, currentThemeColor);

        // 3. RIGHT: BONUS
        // Added "+" manually before the formatted number
        drawStat(g2, "BONUS", "+" + String.format("%,d", pointsGained), centerX + statSpacing, currentY, isWon ? THEME_ORANGE : THEME_RED);

        // --- FOOTER (Blinking Cursor) ---
        currentY += 80;
        if ((int)(timeTicks * 2) % 2 == 0) {
            g2.setColor(new Color(20, 20, 20));
            g2.fillRect(centerX - 150, currentY - 20, 300, 30);

            g2.setColor(Color.WHITE);
            g2.setFont(fontSmall);
            drawCenteredString(g2, "PRESS [ENTER] TO CONTINUE_", centerX, currentY);
        }
    }

    private void drawStat(Graphics2D g2, String label, String value, int x, int y, Color valColor) {
        g2.setFont(fontSmall);
        g2.setColor(Color.GRAY);
        drawCenteredString(g2, label.toUpperCase(), x, y);

        g2.setFont(fontMono.deriveFont(Font.BOLD, 24f));
        g2.setColor(valColor);
        drawCenteredString(g2, value, x, y + 25);
    }

    // --- HELPER: Draw Neon Glow (Simulates CSS Box Shadow) ---
    private void drawGlow(Graphics2D g2, int x, int y, int w, int h, Color c) {
        // Draw multiple rects with decreasing opacity to simulate blur
        for (int i = 0; i < 15; i++) {
            float alpha = 1.0f - (i / 15.0f);
            alpha = alpha * 0.15f; // Reduce overall intensity
            g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(255 * alpha)));
            g2.setStroke(new BasicStroke(2f + (i * 1.5f))); // Increase stroke width outwards
            g2.drawRect(x, y, w, h);
        }
    }

    // --- HELPER: Scanlines (CRT Effect) ---
    private void drawScanlines(Graphics2D g2, int w, int h) {
        // 1. Moving Scanline
        g2.setColor(COLOR_SCANLINE);
        g2.fillRect(0, (int)scanlineY, w, 50); // A 50px tall bar moving down

        // 2. Static lines (every 2 pixels)
        g2.setColor(new Color(0, 0, 0, 50));
        for (int i = 0; i < h; i += 4) {
            g2.drawLine(0, i, w, i);
        }
    }

    private void drawCenteredString(Graphics2D g2, String text, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int drawX = x - (fm.stringWidth(text) / 2);
        g2.drawString(text, drawX, y);
    }

    // Stop timer when screen is destroyed
    @Override
    public void onExit() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    @Override
    public void onEnter() {
        // Reset animations if needed
        timeTicks = 0;
        scanlineY = 0;
        if (!timer.isRunning()) timer.start();
    }
}