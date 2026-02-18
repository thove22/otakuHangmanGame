package main.com.otakuhangman.gui;

import main.com.otakuhangman.controller.GameSessionController;
import main.com.otakuhangman.gui.screens.Challenge;
import main.com.otakuhangman.gui.screens.Intro;
import main.com.otakuhangman.gui.screens.Menu;
import main.com.otakuhangman.gui.screens.NameEntry;
import main.com.otakuhangman.gui.screens.OnBoarding;
import javax.swing.*;

public class AppCordinator {
    private static final String INTRO = "intro";
    private static final String MENU = "menu";
    private static final String ONBOARDING = "onboarding";
    private static final String NAME_ENTRY = "nameEntry";
    private static final String CHALLENGE = "challenge";

    private final ScreenManager screenManager;
    private final GameSessionController gameSessionController;

    public AppCordinator(){
        this.screenManager = new ScreenManager();
        this.gameSessionController = new GameSessionController();
    }

    public JPanel build(){
        Intro intro = new Intro(() -> screenManager.show(ONBOARDING));
        OnBoarding onBoarding = new OnBoarding(() -> screenManager.show(MENU));
        Challenge challenge = new Challenge();

        Menu menu = new Menu(new Menu.MenuSelectionHandler() {
            @Override
            public void onNewGame() {
                screenManager.show(NAME_ENTRY);
            }
            @Override
            public void onInstructions() {
                JOptionPane.showMessageDialog(screenManager.getRoot(), "Continue flow not implemented yet.");
            }
            @Override
            public void onContinue() {
                JOptionPane.showMessageDialog(screenManager.getRoot(), "Continue flow not implemented yet.");
            }

            @Override
            public void onQuit() {
                System.exit(0);
            }
        });

        NameEntry nameEntry = new NameEntry(playerName -> {
            gameSessionController.startNewGame(playerName);
            challenge.setController(gameSessionController);
            screenManager.show(CHALLENGE);
        });
        screenManager.register(INTRO, intro);
        screenManager.register(MENU, menu);
        screenManager.register(ONBOARDING, onBoarding);
        screenManager.register(NAME_ENTRY, nameEntry);
        screenManager.register(CHALLENGE, challenge);
        screenManager.show(INTRO);
        return screenManager.getRoot();
    }
}
