package main.com.otakuhangman.gui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
public class ScreenManager {
    private final JPanel root;
    private final CardLayout cardLayout;
    private final Map<String, Screen> screens;
    private Screen currentScreen;

    public ScreenManager(){
        this.cardLayout = new CardLayout();
        this.root = new JPanel(cardLayout);
        this.screens = new LinkedHashMap<>();
    }

    public void register(String id, Screen screen){
        screens.put(id, screen);
        root.add(screen, id);
    }
    public void show(String id){
        Screen nextScreen  = screens.get(id);
        if(nextScreen == null){
            throw new IllegalArgumentException("Unknown screen id: " + id);
        }

        if (currentScreen != null){
            currentScreen.onExit();
        }
        cardLayout.show(root,id);
        currentScreen = nextScreen;
        currentScreen.onEnter();
    }
    public JPanel getRoot() {
        return root;
    }
}
