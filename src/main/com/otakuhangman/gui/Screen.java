package main.com.otakuhangman.gui;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel {

    public Screen(){
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    public abstract void onEnter();
    public abstract void onExit();

}
