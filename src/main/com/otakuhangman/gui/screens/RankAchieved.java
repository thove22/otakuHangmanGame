package main.com.otakuhangman.gui.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
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

    // --- Animations ---
    private Timer timer;
    private float timeTicks = 0;
    private final Random random = new Random();
    private int lightningTicks = 0;
    private Path2D currentLightning;

    // Speed Regulators
    private int rainTick = 0;
    private int typeTick = 0;

    // Typewriter
    private int typewriterIndex = 0;
    private int typeDelayTicks = 60; // Wait ~1 second before typing

    // Matrix Rain
    private static final int FONT_SIZE = 16;
    private int[] drops;
    private final String matrixChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$#@%&*";

    // Fonts
    private final Font fontMono = new Font("Monospaced", Font.PLAIN, 16);
    private final Font fontTitle = new Font(Font.SANS_SERIF, Font.BOLD, 65);

    public RankAchieved(Rank newRank, Runnable onEnterPressed) {
        this.newRank = newRank;
        this.onEnterPressed = onEnterPressed;

        this.fullMessage = getRankMessage(newRank);

        setBackground(BG_COLOR);
        setFocusable(true);

        // Initialize Matrix Rain (Assuming max width of ~1920)
        drops = new int[1920 / FONT_SIZE];
        for (int i = 0; i < drops.length; i++) drops[i] = random.nextInt(50);

        // 60 FPS Timer
        timer = new Timer(16, e -> {
            timeTicks += 0.05f;

            // --- ADD THIS LIGHTNING LOGIC ---
            if (lightningTicks > 0) {
                lightningTicks--;
            } else if (random.nextInt(450) == 0) { // ~1 in 450 chance per frame
                lightningTicks = 10 + random.nextInt(10); // Flash duration
                currentLightning = generateLightningPath();
            }
            // --------------------------------

            rainTick++;

            // Slowed down Typewriter logic
            if (typeDelayTicks > 0) {
                typeDelayTicks--;
            } else {
                typeTick++;
                // Types one character every 3 frames
                if (typewriterIndex < fullMessage.length() && typeTick % 3 == 0) {
                    typewriterIndex++;
                }
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

            // --- REPLACE YOUR COLOR LOGIC WITH THIS ---
            if (lightningTicks > 0 && random.nextBoolean()) {
                g2.setColor(Color.WHITE); // Glitch effect during thunder
            } else {
                float chance = random.nextFloat();
                if (chance > 0.98f) g2.setColor(new Color(YELLOW.getRed(), YELLOW.getGreen(), YELLOW.getBlue(), 80));
                else if (chance > 0.95f) g2.setColor(new Color(255, 255, 255, 80));
                else g2.setColor(new Color(CYAN.getRed(), CYAN.getGreen(), CYAN.getBlue(), 40));
            }
            // ------------------

            g2.drawString(String.valueOf(c), i * FONT_SIZE, drops[i] * FONT_SIZE);
        }
        if (lightningTicks > 0 && currentLightning != null) {
            // Full screen flash
            g2.setColor(new Color(255, 255, 255, 20 + (lightningTicks * 4)));
            g2.fillRect(0, 0, getWidth(), getHeight());

            // The Bolt
            g2.setStroke(new BasicStroke(2f + random.nextInt(3)));
            g2.setColor(CYAN);
            g2.draw(currentLightning);

            // Core of the bolt
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1f));
            g2.draw(currentLightning);
        }

        // 2. Top Status Bar
        g2.setFont(fontMono.deriveFont(Font.BOLD, 16f));
        g2.setColor(DIM);
        g2.drawString("[ SYSTEM_ALERT: RANK_UPGRADE_INITIATED ]", 40, 40);
        g2.setColor(CYAN);
        g2.drawString("Required Points: " + newRank.getRequiredPoints(), getWidth() - 240, 40);

        if ((int)(timeTicks * 3 ) % 6 == 4) { // Slowed down blink
            g2.setColor(YELLOW);
            g2.fillOval(20, 31, 10, 10);
        }

        // 3. Mahoraga Wheel Background
        int wheelY = cy - 120;
        drawMahoragaWheel(g2, cx, wheelY, timeTicks);

        // 4. Main Title (Glitch Effect)
        String title = newRank.name();
        g2.setFont(fontTitle);
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(title);
        int th = fm.getHeight();
        int tx = cx - tw / 2;
        int ty = wheelY + 90;

        GradientPaint grad = new GradientPaint(tx, ty, CYAN, tx + tw, ty, RED);
        // Glitch chance reduced to ~2% for a rarer, slower glitch effect
        if (random.nextInt(100) > 92) {
            g2.setColor(new Color(255, 0, 0, 180));
            g2.drawString(title, tx - (random.nextInt(8) - 4), ty + (random.nextInt(4) - 2));
            g2.setColor(new Color(0, 255, 255, 180));
            g2.drawString(title, tx + (random.nextInt(8) - 4), ty + (random.nextInt(4) - 2));

            for (int s = 0; s < 3; s++) { // Create 3 random slices
                int sliceY = random.nextInt(th);
                int sliceH = 5 + random.nextInt(15);
                int xOffset = random.nextInt(30) - 15;

                Shape oldClip = g2.getClip();
                // Set the clip to a thin horizontal strip
                g2.setClip(tx - 50, ty - th + sliceY, tw + 100, sliceH);

                // Draw the slice in a random "glitch" color
                g2.setColor(random.nextBoolean() ? CYAN : Color.WHITE);
                g2.drawString(title, tx + xOffset, ty);

                g2.setClip(oldClip);
            }

            // 3. Digital Noise (Small white/cyan rectangles)
            for (int n = 0; n < 5; n++) {
                g2.setColor(random.nextBoolean() ? CYAN : YELLOW);
                g2.fillRect(tx + random.nextInt(tw), ty - random.nextInt(th), random.nextInt(20), 2);
            }
        }
        g2.setPaint(grad);
        g2.drawString(title, tx, ty);

        g2.setFont(fontMono.deriveFont(Font.BOLD, 10f));
        g2.setColor(DIM);
        drawCenteredString(g2, " " + " ", cx, wheelY + 60);

        // 5. Typewriter Box
        int boxW = 600, boxH = 100;
        int boxX = cx - (boxW / 2);
        int boxY = wheelY + 210;

        g2.setColor(PANEL_BG);
        g2.fillRect(boxX, boxY, boxW, boxH);
        g2.setColor(BORDER);
        g2.drawRect(boxX, boxY, boxW, boxH);

        g2.setColor(CYAN);
        g2.drawLine(boxX, boxY, boxX + 10, boxY); g2.drawLine(boxX, boxY, boxX, boxY + 10);
        g2.drawLine(boxX + boxW, boxY + boxH, boxX + boxW - 10, boxY + boxH); g2.drawLine(boxX + boxW, boxY + boxH, boxX + boxW, boxY + boxH - 10);

        g2.setFont(fontMono.deriveFont(Font.BOLD, 20f));
        g2.setColor(Color.WHITE);
        String printedText = fullMessage.substring(0, typewriterIndex);
        drawCenteredString(g2, printedText, cx, boxY + 55);

        // Cursor blink slowed down
        if (typewriterIndex < fullMessage.length() || (int)(timeTicks * 4) % 6 == 0) {
            int textWidth = g2.getFontMetrics().stringWidth(printedText);
            g2.setColor(CYAN);
            g2.fillRect(cx + (textWidth / 2) + 2, boxY + 42, 8, 16);
        }

        // 6. Footer Diamond Badge
        int diamondY = boxY + boxH + 100;
        AffineTransform oldTx = g2.getTransform();
        g2.translate(cx, diamondY);
        g2.rotate(Math.PI / 4);

        int pulse = (int) (Math.sin(timeTicks * 0.1) * 2); // Slower pulse
        g2.setColor(new Color(CYAN.getRed(), CYAN.getGreen(), CYAN.getBlue(), 50));
        g2.fillRect(-30 - pulse, -30 - pulse, 60 + pulse*2, 60 + pulse*2);

        g2.setColor(PANEL_BG);
        g2.fillRect(-30, -30, 60, 60);
        g2.setColor(YELLOW);
        g2.drawRect(-30, -30, 60, 60);
        g2.setTransform(oldTx);

        g2.setColor(YELLOW);
        g2.setFont(fontTitle.deriveFont(32f));
        drawCenteredString(g2, "♔", cx, diamondY + 10);

        // 7. Enhanced "Press Enter" Section
        drawPressEnterSection(g2, cx, diamondY + 80, timeTicks);

        g2.setFont(fontMono.deriveFont(8f));
        g2.setColor(DIM);
        drawCenteredString(g2, "SESSION_ID: #8X92-QA", cx, diamondY + 120);
    }

    private void drawMahoragaWheel(Graphics2D g2, int cx, int cy, float time) {
        AffineTransform oldTx = g2.getTransform();
        g2.translate(cx, cy);

        // Scale factor to make it fit beautifully behind the text (Scale SVG coordinates up by 1.8x)
        g2.scale(1.8, 1.8);

        // Alpha to blend it into the background nicely
        Composite originalComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));

        Stroke solidThin = new BasicStroke(0.8f);
        Stroke solidMed = new BasicStroke(1.5f);
        Stroke solidThick = new BasicStroke(2.5f);
        Stroke dashedOuter = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{8f, 4f}, 0f);
        Stroke dashedMid = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{2f, 6f}, 0f);

        // --- LAYER 1: Outer Layer (Slowest, Forward) ---
        AffineTransform l1Tx = g2.getTransform();
        g2.rotate(time * 0.01); // Slow spin

        g2.setColor(DIM);
        g2.setStroke(dashedOuter);
        g2.drawOval(-82, -82, 164, 164); // Outer dash ring

        g2.setStroke(solidThin);
        // Cross lines
        g2.drawLine(0, -75, 0, 75);
        g2.drawLine(-75, 0, 75, 0);
        g2.drawLine(-53, -53, 53, 53);
        g2.drawLine(-53, 53, 53, -53);

        g2.setColor(YELLOW);
        g2.setStroke(solidMed);
        // 8 Perimeter Circles (Radius 7 at Distance 82)
        int[][] circlePoints = {{0,-82}, {0,82}, {-82,0}, {82,0}, {-58,-58}, {58,58}, {-58,58}, {58,-58}};
        for (int[] p : circlePoints) {
            g2.drawOval(p[0] - 7, p[1] - 7, 14, 14);
        }
        g2.setTransform(l1Tx);

        // --- LAYER 3: Inner Core (Fastest, Forward) ---
        AffineTransform l3Tx = g2.getTransform();
        g2.rotate(time * 0.01); // Fast spin

        g2.setColor(BORDER);
        g2.setStroke(solidThick);
        g2.drawOval(-40, -40, 80, 80);

        g2.setColor(YELLOW);
        g2.setStroke(solidThin);
        g2.drawOval(-34, -34, 68, 68);

        // Inner 8-point star polygon
        g2.setColor(new Color(CYAN.getRed(), CYAN.getGreen(), CYAN.getBlue(), 120)); // Semi-transparent fill
        Path2D star = new Path2D.Double();
        star.moveTo(0, -25);  star.lineTo(6, -6);
        star.lineTo(25, 0);   star.lineTo(6, 6);
        star.lineTo(0, 25);   star.lineTo(-6, 6);
        star.lineTo(-25, 0);  star.lineTo(-6, -6);
        star.closePath();
        g2.fill(star);

        g2.setTransform(l3Tx);

        // Reset composite and transform
        g2.setComposite(originalComposite);
        g2.setTransform(oldTx);
    }

    private Path2D generateLightningPath() {
        Path2D path = new Path2D.Double();
        int startX = random.nextInt(getWidth());
        path.moveTo(startX, 0);

        int currX = startX;
        int currY = 0;
        while (currY < getHeight()) {
            currX += random.nextInt(160) - 80; // Horizontal jaggedness
            currY += random.nextInt(100);      // Vertical progress
            path.lineTo(currX, currY);
        }
        return path;
    }

    private void drawPressEnterSection(Graphics2D g2, int cx, int cy, float time) {
        Font pressFont = fontMono.deriveFont(Font.BOLD, 18f);
        g2.setFont(pressFont);
        FontMetrics fm = g2.getFontMetrics();

        String s1 = "PRESSIONE ";
        String s2 = "[ENTER]";
        String s3 = " PARA CONTINUAR";

        int w1 = fm.stringWidth(s1);
        int w2 = fm.stringWidth(s2);
        int w3 = fm.stringWidth(s3);

        int totalWidth = w1 + w2 + w3;
        int startX = cx - (totalWidth / 2);

        // Draw Gray text
        g2.setColor(DIM);
        g2.drawString(s1, startX, cy);
        g2.drawString(s3, startX + w1 + w2, cy);

        // Draw Glowing/Pulsing Cyan Text
        // Calculate a sine wave pulse between ~100 and 255 for the Alpha channel
        int alpha = (int) (175 + 80 * Math.sin(timeTicks * 0.2));
        alpha = Math.max(0, Math.min(255, alpha)); // Ensure bounds

        Color pulsingCyan = new Color(CYAN.getRed(), CYAN.getGreen(), CYAN.getBlue(), alpha);
        g2.setColor(pulsingCyan);
        g2.drawString(s2, startX + w1, cy);

        // Underline effect underneath [ENTER]
        int lineY = cy + 6;
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(startX + w1, lineY, startX + w1 + w2, lineY);
    }

    private void drawCenteredString(Graphics2D g2, String t, int x, int y) {
        g2.drawString(t, x - g2.getFontMetrics().stringWidth(t) / 2, y);
    }

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
}