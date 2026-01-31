package main.com.otakuhangman.gui.screens;

import main.com.otakuhangman.gui.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MenuScreen extends Screen {
    private final Color HOVER_COLOR = new  Color(70, 100, 255);
    private final Color DEFAULT_COLOR = Color.WHITE;
    private Point[] stars;
    public MenuScreen() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        generateStars(100);
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
        add(menuPanel, new GridBagConstraints());
    }
    private JLabel createMenuItem(String text, int choice){
        JLabel item = new JLabel(text);
        item.setFont(new Font("Monospaced", Font.BOLD, 28));
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
    private void generateStars(int count) {
        Random rand = new Random();
        stars = new Point[count];
        for (int i = 0; i < count; i++) {
            stars[i] = new Point(rand.nextInt(1200), rand.nextInt(800));
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        for (Point star : stars) {
            g.fillRect(star.x, star.y, 2, 2);
        }
    }

    public void onEnter() {
        this.requestFocusInWindow();
    }
    public void onExit() {
    }
}
