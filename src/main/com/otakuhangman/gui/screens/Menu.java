package main.com.otakuhangman.gui.screens;
import main.com.otakuhangman.gui.Screen;
import main.com.otakuhangman.gui.utils.AsciiArt;
import main.com.otakuhangman.gui.utils.Star;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Menu extends Screen {
    private final Color HOVER_COLOR = new  Color(70, 100, 255);
    private final Color DEFAULT_COLOR = Color.WHITE;
    private List<Star> starField;
    private Timer animationTimer;
    private final int STAR_COUNT = 150;

    public Menu() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();

        starField = new ArrayList<>();
        for (int i = 0; i < STAR_COUNT; i++) {
            starField.add(new Star(1200, 800));
        }
        JTextArea titleArea = new JTextArea(AsciiArt.MENUTITLE());
        titleArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        titleArea.setForeground(DEFAULT_COLOR);
        titleArea.setOpaque(false);
        titleArea.setEditable(false);
        titleArea.setFocusable(false);
        titleArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 50, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleArea, gbc);
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.add(createMenuItem("NEW GAME", 1));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        menuPanel.add(createMenuItem("CONTINUE", 2));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(createMenuItem("INSTRUCTIONS", 3));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(createMenuItem("QUIT", 4));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(menuPanel, gbc);
        animationTimer = new Timer(16, e -> {
            for (Star star : starField) {
                star.update(getWidth(), getHeight());
            }
            repaint();
        });
    }
    private JLabel createMenuItem(String text, int choice){
        JLabel item = new JLabel(text);
        item.setFont(new Font("Monospaced", Font.BOLD, 32));
        item.setForeground(DEFAULT_COLOR);
        item.setAlignmentX(Component.CENTER_ALIGNMENT);
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setForeground(HOVER_COLOR);
                item.setText("> " + text + " <");
            }
            public void mouseExited(MouseEvent e) {
                item.setForeground(DEFAULT_COLOR);
                item.setText(text);
            }
            public void mouseClicked(MouseEvent e) {
                handleSelection(choice);
            }
        });
        return item;
    }
    private void handleSelection(int choice){
        switch (choice){
            case 1 -> System.out.println("Starting New Game...");
            case 4 -> System.exit(0);
            default -> System.out.println("Option " + choice + " selected.");
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(200, 200, 200, 150));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Star star : starField) {
            g2d.fillOval((int)star.x, (int)star.y, star.size, star.size);
        }
    }

    public void onEnter() {
        this.requestFocusInWindow();
        animationTimer.start();
    }
    public void onExit() {
        animationTimer.stop();
    }
}
