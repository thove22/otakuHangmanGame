package main.com.otakuhangman.gui.screens;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.Random;
import main.com.otakuhangman.gui.Screen;
import main.com.otakuhangman.core.Rank;

public class RankAchieved extends Screen {
    private static final Color BG_COLOR = new Color(13, 13, 13);
    private static final Color PANEL_BG = new Color(21, 21, 21, 200);
    private static final Color BORDER = new Color(50, 50, 50);
    private static final Color CYAN = new Color(0, 190, 190);
    private static final Color YELLOW = new Color(200, 180, 50);
    private static final Color RED = new Color(180, 50, 50);
    private static final Color DIM = new Color(90, 90, 90);

    // --- State & Logic ---
    private final Rank newRank;
    private final Runnable onEnterPressed;
    private final String fullMessage;
    private final String kanji;

    // --- Animations ---
    private Timer timer;
    private float timeTicks = 0;
    private final Random random = new Random();

    // Typewriter
    private int typewriterIndex = 0;
    private int typeDelayTicks = 60; // Wait ~1 second before typing

    // Matrix Rain
    private static final int FONT_SIZE = 14;
    private int[] drops;
    private final String matrixChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$#@%&*";

    // Fonts
    private final Font fontMono = new Font("Monospaced", Font.PLAIN, 14);
    private final Font fontTitle = new Font("Monospaced", Font.BOLD, 64);
    private final Font fontKanji = new Font("SansSerif", Font.BOLD, 180);

    public RankAchieved(Rank newRank, Runnable onEnterPressed) {
        this.newRank = newRank;
        this.onEnterPressed = onEnterPressed;

        this.fullMessage = getRankMessage(newRank);
        this.kanji = getRankKanji(newRank);

        setBackground(BG_COLOR);
        setFocusable(true);

        // Initialize Matrix Rain (Assuming max width of ~1920)
        drops = new int[1920 / FONT_SIZE];
        for (int i = 0; i < drops.length; i++) drops[i] = random.nextInt(50); // Random start positions

        // 60 FPS Timer
        timer = new Timer(16, e -> {
            timeTicks += 0.1f;
            updateMatrix();

            // Typewriter logic
            if (typeDelayTicks > 0) {
                typeDelayTicks--;
            } else if (typewriterIndex < fullMessage.length() && timeTicks % 2 < 1) { // Speed controller
                typewriterIndex++;
            }

            repaint();
        });

        // Enter Key Listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && onEnterPressed != null) {
                    onEnterPressed.run();
                }
            }
        });
    }

    @Override
    public void onEnter() {
        requestFocusInWindow();
        if (timer != null) timer.start();
    }

    @Override
    public void onExit() {
        if (timer != null) timer.stop();
    }

    private void updateMatrix() {
        for (int i = 0; i < drops.length; i++) {
            if (drops[i] * FONT_SIZE > getHeight() && random.nextFloat() > 0.95f) {
                drops[i] = 0;
            }
            drops[i]++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        // 1. Draw Background & Digital Rain
        g2.setColor(BG_COLOR);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setFont(fontMono);
        for (int i = 0; i < drops.length; i++) {
            char c = matrixChars.charAt(random.nextInt(matrixChars.length()));
            float chance = random.nextFloat();
            if (chance > 0.98f) g2.setColor(new Color(YELLOW.getRed(), YELLOW.getGreen(), YELLOW.getBlue(), 80));
            else if (chance > 0.95f) g2.setColor(new Color(255, 255, 255, 80));
            else g2.setColor(new Color(CYAN.getRed(), CYAN.getGreen(), CYAN.getBlue(), 40)); // Dim rain

            g2.drawString(String.valueOf(c), i * FONT_SIZE, drops[i] * FONT_SIZE);
        }

        // 2. Top Status Bar
        g2.setFont(fontMono.deriveFont(Font.BOLD, 12f));
        g2.setColor(DIM);
        g2.drawString("[ SYSTEM_ALERT: RANK_UPGRADE_INITIATED ]", 40, 40);
        g2.setColor(CYAN);
        g2.drawString("// V.5.0.1 //", getWidth() - 140, 40);

        // Blinking indicator
        if ((int)(timeTicks * 2) % 2 == 0) {
            g2.setColor(YELLOW);
            g2.fillOval(20, 31, 10, 10);
        }

        // 3. Center Kanji & Rotating Circles
        int kanjiY = cy - 80;

        AffineTransform oldTx = g2.getTransform();
        g2.setStroke(new BasicStroke(2f));

        // Inner Circle (Spins right)
        g2.translate(cx, kanjiY - 50);
        g2.rotate(timeTicks * 0.05);
        g2.setColor(new Color(CYAN.getRed(), CYAN.getGreen(), CYAN.getBlue(), 50));
        g2.drawOval(-120, -120, 240, 240);
        g2.setTransform(oldTx);

        // Outer Dashed Circle (Spins left)
        g2.translate(cx, kanjiY - 50);
        g2.rotate(-timeTicks * 0.03);
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{10f}, 0f));
        g2.setColor(new Color(YELLOW.getRed(), YELLOW.getGreen(), YELLOW.getBlue(), 30));
        g2.drawOval(-150, -150, 300, 300);
        g2.setTransform(oldTx);
        g2.setStroke(new BasicStroke(1f));

        // Draw Kanji
        g2.setFont(fontKanji);
        g2.setColor(new Color(255, 255, 255, 40)); // High transparency
        drawCenteredString(g2, kanji, cx, kanjiY);

        // 4. Main Title (Glitch Effect)
        String title = newRank.name();
        g2.setFont(fontTitle);
        if (random.nextInt(100) > 90) { // Glitch randomly
            g2.setColor(RED); drawCenteredString(g2, title, cx - 4, kanjiY + 30);
            g2.setColor(CYAN); drawCenteredString(g2, title, cx + 4, kanjiY + 30);
        }
        g2.setColor(CYAN);
        drawCenteredString(g2, title, cx, kanjiY + 30);

        g2.setFont(fontMono.deriveFont(Font.BOLD, 10f));
        g2.setColor(DIM);
        drawCenteredString(g2, "LEVEL " + newRank.getRequiredPoints() + " ACHIEVED", cx, kanjiY + 60);

        // 5. Typewriter Box
        int boxW = 600, boxH = 100;
        int boxX = cx - (boxW / 2);
        int boxY = kanjiY + 120;

        g2.setColor(PANEL_BG);
        g2.fillRect(boxX, boxY, boxW, boxH);
        g2.setColor(BORDER);
        g2.drawRect(boxX, boxY, boxW, boxH);

        // Corner accents
        g2.setColor(CYAN);
        g2.drawLine(boxX, boxY, boxX + 10, boxY); g2.drawLine(boxX, boxY, boxX, boxY + 10);
        g2.drawLine(boxX + boxW, boxY + boxH, boxX + boxW - 10, boxY + boxH); g2.drawLine(boxX + boxW, boxY + boxH, boxX + boxW, boxY + boxH - 10);

        // Typewriter Text
        g2.setFont(fontMono.deriveFont(Font.BOLD, 16f));
        g2.setColor(Color.WHITE);
        String printedText = fullMessage.substring(0, typewriterIndex);
        drawCenteredString(g2, printedText, cx, boxY + 55);

        // Cursor
        if (typewriterIndex < fullMessage.length() || (int)(timeTicks * 2) % 2 == 0) {
            int textWidth = g2.getFontMetrics().stringWidth(printedText);
            g2.setColor(CYAN);
            g2.fillRect(cx + (textWidth / 2) + 2, boxY + 42, 8, 16);
        }

        // 6. Footer Diamond Badge
        int diamondY = boxY + boxH + 60;
        g2.translate(cx, diamondY);
        g2.rotate(Math.PI / 4); // Rotate 45 degrees

        // Pulsing glow
        int pulse = (int) (Math.sin(timeTicks * 0.1) * 5);
        g2.setColor(new Color(CYAN.getRed(), CYAN.getGreen(), CYAN.getBlue(), 50));
        g2.fillRect(-30 - pulse, -30 - pulse, 60 + pulse*2, 60 + pulse*2);

        g2.setColor(PANEL_BG);
        g2.fillRect(-30, -30, 60, 60);
        g2.setColor(YELLOW);
        g2.drawRect(-30, -30, 60, 60);
        g2.setTransform(oldTx); // Reset rotation

        // Draw simple Crown inside Diamond (using ASCII for simplicity)
        g2.setColor(YELLOW);
        g2.setFont(fontTitle.deriveFont(32f));
        drawCenteredString(g2, "♔", cx, diamondY + 10);

        // 7. Footer text
        g2.setFont(fontMono.deriveFont(Font.BOLD, 12f));
        g2.setColor(DIM);
        drawCenteredString(g2, "PRESSIONE [ENTER] PARA CONTINUAR", cx, diamondY + 80);
        g2.setFont(fontMono.deriveFont(8f));
        drawCenteredString(g2, "SESSION_ID: #8X92-QA", cx, diamondY + 100);
    }

    private void drawCenteredString(Graphics2D g2, String t, int x, int y) {
        g2.drawString(t, x - g2.getFontMetrics().stringWidth(t) / 2, y);
    }

    // --- Helper Methods to adapt terminal logic ---

    private String getRankMessage(Rank r) {
        return switch (r) {
            case OTAKU_INICIANTE -> "Hajimemashite! Você é um Otaku Iniciante! (◕‿◕✿)";
            case OTAKU_NUTELLA -> "Yatta! Você subiu para Otaku Nutella! Continue assim!";
            case MID_OTAKU -> "Sugoi! Mid Otaku desbloqueado! Seu poder cresce!";
            case ADVANCED_OTAKU -> "Kakkoii! Advanced Otaku alcançado! Você evoluiu!";
            case GOD_OTAKU -> "MASAKA! God Otaku conquistado! Você é imbatível!";
            default -> "Rank atualizado!";
        };
    }

    private String getRankKanji(Rank r) {
        return switch (r) {
            case OTAKU_INICIANTE -> "初"; // Beginner
            case OTAKU_NUTELLA -> "甘";  // Sweet/Naive
            case MID_OTAKU -> "中";      // Middle
            case ADVANCED_OTAKU -> "高"; // High
            case GOD_OTAKU -> "神";      // God
            default -> "上";             // Up
        };
    }
}
