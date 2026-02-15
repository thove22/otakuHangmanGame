package main.com.otakuhangman.gui.screens;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import main.com.otakuhangman.gui.Screen;
import main.com.otakuhangman.gui.utils.AsciiArt;


public class LevelResult extends Screen {
    // --- Colors ---
    private static final Color BG_COLOR = new Color(13, 13, 13);
    private static final Color CYAN = new Color(0, 190, 190);
    private static final Color GREEN = new Color(50, 180, 50);
    private static final Color RED = new Color(180, 50, 50);
    private static final Color YELLOW = new Color(200, 180, 50);
    private static final Color BORDER_DARK = new Color(50, 50, 50);
    private static final Color TEXT_DIM = new Color(90, 90, 90);
    private static final Color PANEL_BG = new Color(21, 21, 21);

    // --- State Variables ---
    private boolean isWin;
    private int score;
    private int requiredScore;
    private String rank;
    private int completedChallenges = 7;
    private int totalChallenges = 7;

    // --- Animation & Utility ---
    private float timeTicks = 0;
    private Timer timer;
    private Random random = new Random();
    private Runnable onEnterPressed;

    // --- Fonts ---
    private Font fontMono;
    private Font fontTitle;
    private Font fontStats;


    public LevelResult(boolean isWin, int score, int requiredScore, String rank, Runnable onEnterPressed) {
        this.isWin = isWin;
        this.score = score;
        this.requiredScore = requiredScore;
        this.rank = rank;
        this.onEnterPressed = onEnterPressed;

        setBackground(BG_COLOR);
        setFocusable(true);

//        // Load fonts (Fallback to Monospaced if JetBrains isn't installed)
//        fontMono = new Font("Monospaced", Font.PLAIN, 12);
//        fontTitle = new Font("Monospaced", Font.BOLD, 60);
//        fontStats = new Font("Monospaced", Font.BOLD, 24);
        try {
            fontTitle= new Font("JetBrains Mono", Font.BOLD, 60);
            fontMono = new Font("Monospaced", Font.PLAIN, 24);
            fontStats = new Font("Monospaced", Font.BOLD, 18);
        } catch (Exception e) {
            fontTitle = new Font("Monospaced", Font.BOLD, 60);
            fontMono = new Font("Monospaced", Font.PLAIN, 24);
            fontStats = new Font("Monospaced", Font.BOLD, 10);
        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (onEnterPressed != null) onEnterPressed.run();
                }
            }
        });

        // Setup the 60 FPS Animation loop (but don't start it yet)
        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeTicks += 0.1f;
                repaint();
            }
        });
    }

    // ==========================================
    // SCREEN LIFECYCLE METHODS
    // ==========================================

    @Override
    public void onEnter() {
        // Start animations and request keyboard focus when screen becomes active
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
        requestFocusInWindow();
    }

    @Override
    public void onExit() {
        // Stop animations to save CPU/Memory when navigating away
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    // ==========================================
    // RENDERING LOGIC
    // ==========================================

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;

        Color themeColor = isWin ? CYAN : RED;

        // --- 1. HEADER ---
        int currentY = 120;


        g2.setFont(fontTitle);
        String mainTitle = isWin ? "PARABÉNS!" : "NÍVEL NÃO COMPLETADO";

        // Glitch / Glow Effect
        if (isWin) {
            g2.setColor(new Color(GREEN.getRed(), GREEN.getGreen(), GREEN.getBlue(), 50));
            drawCenteredString(g2, mainTitle, centerX, currentY + 2);
            drawCenteredString(g2, mainTitle, centerX, currentY - 2);

            if (random.nextInt(100) > 92) {
                g2.setColor(RED);
                drawCenteredString(g2, mainTitle, centerX - 4, currentY);
                g2.setColor(CYAN);
                drawCenteredString(g2, mainTitle, centerX + 4, currentY);
            }
            g2.setColor(CYAN);
        } else {
            int pulse = (int) (180 + Math.sin(timeTicks) * 75);
            g2.setColor(new Color(180, 50, 50, pulse));
            drawCenteredString(g2, mainTitle, centerX, currentY + 1);
            g2.setColor(RED);
        }
        drawCenteredString(g2, mainTitle, centerX, currentY);

        // Decorator Line
        currentY += 30;
        g2.setColor(new Color(themeColor.getRed(), themeColor.getGreen(), themeColor.getBlue(), 180));
        g2.drawLine(centerX - 60, currentY, centerX + 60, currentY);

        // --- 2. MAIN RPG BOX ---
        currentY += 50;
        int boxW = 860;
        int boxH = 420;
        int boxX = centerX - (boxW / 2);

        g2.setColor(BORDER_DARK);
        g2.drawRect(boxX, currentY, boxW, boxH);

        Stroke oldStroke = g2.getStroke();
        float[] dash = {4f, 4f};
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        g2.drawRect(boxX + 4, currentY + 4, boxW - 8, boxH - 8);
        g2.setStroke(oldStroke);

        drawCornerDecorations(g2, boxX, currentY, boxW, boxH, themeColor);

        // --- 3. LEFT CONTENT (ASCII ART) ---
        int leftCenterX = boxX + 150;
        int asciiY = currentY + 60;

        g2.setFont(fontMono.deriveFont(Font.BOLD, 18f));
        g2.setColor(CYAN);

   // Retrieve and split the string from your new AsciiArt file
        String asciiData = isWin ? AsciiArt.WINASCII() : AsciiArt.LOSSASCII();
        String[] asciiLines = asciiData.split("\n");

        for (String line : asciiLines) {
            // Avoid drawing empty lines if they exist at the end of your text block
            if (!line.trim().isEmpty() || line.length() > 0) {
                drawCenteredString(g2, line, leftCenterX, asciiY);
                asciiY += 24;
            }
        }



        // --- 4. RIGHT CONTENT (STATS GRID) ---
        int rightStartX = boxX + 300;
        int statsY = currentY + 40;
        int cellW = 230;
        int cellH = 85;

        drawStatCell(g2, rightStartX, statsY, cellW, cellH, "CHALLENGES CLEARED",
                String.format("%02d", completedChallenges), "/" + totalChallenges, Color.WHITE, TEXT_DIM);

        String rankSub = isWin ? "" : "";
        Color rankColor = isWin ? YELLOW : TEXT_DIM;
        drawStatCell(g2, rightStartX + cellW + 15, statsY, cellW, cellH, "RANK", rank, rankSub, rankColor, TEXT_DIM);

        int scoreCellW = (cellW * 2) + 15;
        int scoreCellY = statsY + cellH + 15;

        g2.setColor(PANEL_BG);
        g2.setFont(fontMono.deriveFont(14f));
        g2.fillRect(rightStartX, scoreCellY, scoreCellW, cellH);
        g2.setColor(BORDER_DARK);
        g2.drawRect(rightStartX, scoreCellY, scoreCellW, cellH);

        if (!isWin) {
            g2.setColor(new Color(RED.getRed(), RED.getGreen(), RED.getBlue(), 80));
            int barWidth = (int) (scoreCellW * ((double) score / requiredScore));
            g2.fillRect(rightStartX, scoreCellY + cellH - 4, Math.min(barWidth, scoreCellW), 4);
        }

        g2.setFont(fontMono.deriveFont(14f));
        g2.setColor(TEXT_DIM);
        g2.drawString("LEVEL SCORE", rightStartX + 15, scoreCellY + 20);
        g2.setFont(fontStats);
        g2.setColor(isWin ? CYAN : RED);
        g2.drawString(String.format("%,d", score) + " PTS", rightStartX + 15, scoreCellY + 50);

        if (!isWin) {
            g2.setFont(fontMono.deriveFont(14f));
            g2.setColor(YELLOW);
            String reqStr = "REQUIREMENT";
            int reqW = g2.getFontMetrics().stringWidth(reqStr);
            g2.drawString(reqStr, rightStartX + scoreCellW - reqW - 15, scoreCellY + 20);

            g2.setFont(fontStats.deriveFont(20f));
            g2.setColor(TEXT_DIM);
            String reqVal = String.format("%,d", requiredScore) + " PTS";
            int reqValW = g2.getFontMetrics().stringWidth(reqVal);
            g2.drawString(reqVal, rightStartX + scoreCellW - reqValW - 15, scoreCellY + 48);
        }

        int msgY = scoreCellY + cellH + 40;
        g2.setColor(isWin ? GREEN : YELLOW);
        g2.fillRect(rightStartX, msgY, 3, 40);

        g2.setFont(fontMono.deriveFont(15f));
        if (isWin) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawString("Você venceu! ", rightStartX + 15, msgY + 25);
            g2.setColor(GREEN);
            g2.drawString("Próximo nível desbloqueado!", rightStartX + 145, msgY + 25);
        } else {
            g2.setColor(YELLOW);
            g2.drawString("Você não atingiu a pontuação necessária para prosseguir.", rightStartX + 8, msgY + 15);
            g2.setFont(fontMono.deriveFont(15f));
            g2.setColor(TEXT_DIM);
            g2.drawString("Você terá de repetir o nível para atingir a pontuação.", rightStartX + 10, msgY + 35);
        }

        // --- 5. FOOTER (ARROW & ENTER BOX) ---
        int footerY = currentY + boxH + 50;

        int arrowOffset = (int) (Math.sin(timeTicks * 2) * 8);
        g2.setFont(fontTitle.deriveFont(24f));
        g2.setColor(themeColor);
        drawCenteredString(g2, "↓", centerX, footerY + arrowOffset);

        footerY += 40;
        int btnW = 380;
        int btnH = 45;
        int btnX = centerX - (btnW / 2);

        g2.setColor(new Color(themeColor.getRed(), themeColor.getGreen(), themeColor.getBlue(), 30));
        g2.fillRect(btnX - 2, footerY - 2, btnW + 4, btnH + 4);

        g2.setColor(BG_COLOR);
        g2.fillRect(btnX, footerY, btnW, btnH);
        g2.setColor(themeColor);
        g2.drawRect(btnX, footerY, btnW, btnH);

        if ((int)(timeTicks * 2) % 2 == 0) {
            g2.fillRect(btnX + 15, footerY + 18, 8, 8);
            g2.fillRect(btnX + btnW - 23, footerY + 18, 8, 8);
        }

        g2.setFont(fontMono.deriveFont(Font.BOLD, 13f));
        String prompt = isWin ? "PRESSIONE [ENTER] PARA CONTINUAR" : "PRESSIONE [ENTER] PARA TENTAR NOVAMENTE";
        drawCenteredString(g2, prompt, centerX, footerY + 28);

        drawScanlines(g2, width, height);
    }

    private void drawStatCell(Graphics2D g2, int x, int y, int w, int h, String title, String mainVal, String subVal, Color mainColor, Color subColor) {
        g2.setColor(PANEL_BG);
        g2.fillRect(x, y, w, h);
        g2.setColor(BORDER_DARK);
        g2.drawRect(x, y, w, h);

        g2.setFont(fontMono.deriveFont(14f));
        g2.setColor(TEXT_DIM);
        g2.drawString(title, x + 10, y + 20);

        g2.setFont(fontStats);
        g2.setColor(mainColor);
        g2.drawString(mainVal, x + 10, y + 50);

        int mainValWidth = g2.getFontMetrics().stringWidth(mainVal);
        g2.setFont(fontMono.deriveFont(14f));
        g2.setColor(subColor);
        g2.drawString(subVal, x + 10 + mainValWidth + 5, y + 50);
    }

    private void drawCornerDecorations(Graphics2D g2, int x, int y, int w, int h, Color color) {
        g2.setColor(color);
        int size = 10;
        g2.drawLine(x - 2, y - 2, x + size, y - 2);
        g2.drawLine(x - 2, y - 2, x - 2, y + size);
        g2.drawLine(x + w + 2, y - 2, x + w - size, y - 2);
        g2.drawLine(x + w + 2, y - 2, x + w + 2, y + size);
        g2.drawLine(x - 2, y + h + 2, x + size, y + h + 2);
        g2.drawLine(x - 2, y + h + 2, x - 2, y + h - size);
        g2.drawLine(x + w + 2, y + h + 2, x + w - size, y + h + 2);
        g2.drawLine(x + w + 2, y + h + 2, x + w + 2, y + h - size);
    }

    private void drawCenteredString(Graphics2D g2, String text, int x, int y) {
        FontMetrics metrics = g2.getFontMetrics();
        int width = metrics.stringWidth(text);
        g2.drawString(text, x - width / 2, y);
    }

    private void drawScanlines(Graphics2D g2, int width, int height) {
        g2.setColor(new Color(0, 0, 0, 20));
        for (int i = 0; i < height; i += 4) {
            g2.drawLine(0, i, width, i);
        }
    }
}
