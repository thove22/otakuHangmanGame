package main.com.otakuhangman.gui.utils;

public class Star {
    double x, y, speed;
    int size;
    Star(int width, int height) {
        reset(width, height, true);
    }
    void reset(int width, int height, boolean randomY) {
        this.x = Math.random() * width;
        this.y = randomY ? Math.random() * height : -5;
        this.speed = 0.5 + Math.random() * 2.0;
        this.size = (int) (1 + Math.random() * 3);
    }
    void update(int width, int height) {
        y += speed;
        if (y > height) {
            reset(width, height, false);
        }
    }
}
