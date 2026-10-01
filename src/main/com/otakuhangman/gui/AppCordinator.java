package main.com.otakuhangman.gui;

import main.com.otakuhangman.controller.ChallengeResolution;
import main.com.otakuhangman.controller.GameSessionController;
import main.com.otakuhangman.controller.LevelProgressState;
import main.com.otakuhangman.controller.LevelResolution;
import main.com.otakuhangman.core.Player;
import main.com.otakuhangman.core.Rank;
import main.com.otakuhangman.gui.screens.Challenge;
import main.com.otakuhangman.gui.screens.EndChallenge;
import main.com.otakuhangman.gui.screens.Intro;
import main.com.otakuhangman.gui.screens.LevelResult;
import main.com.otakuhangman.gui.screens.Menu;
import main.com.otakuhangman.gui.screens.NameEntry;
import main.com.otakuhangman.gui.screens.OnBoarding;
import main.com.otakuhangman.gui.screens.RankAchieved;
import javax.swing.*;

public class AppCordinator {
    private static final String INTRO = "intro";
    private static final String MENU = "menu";
    private static final String ONBOARDING = "onboarding";
    private static final String NAME_ENTRY = "nameEntry";
    private static final String CHALLENGE = "challenge";
    private static final String END_CHALLENGE = "endChallenge";
    private static final String RANK_ACHIEVED = "rankAchieved";
    private static final String LEVEL_RESULT = "levelResult";

    private static final String INSTRUCTIONS = """
            Objetivo:
            - Adivinhar a palavra secreta, uma letra por vez.

            Regras:
            - Insira apenas UMA letra por tentativa e pressione ENTER.
            - Letras repetidas não contam como nova tentativa.
            - Cada erro adiciona uma parte ao boneco da forca.
            - Com 6 erros, ou sem tentativas, o desafio é perdido.
            - Você tem 30 segundos por desafio.

            Progressão:
            - Cada nível possui vários desafios.
            - Você precisa atingir o score mínimo para avançar.
            - Nos níveis 1 a 3, você pode avançar mesmo falhando.
            - A partir do nível 4, será preciso dominar o jogo!

            Boa sorte e divirta-se! (⌐■_■)""";

    private final ScreenManager screenManager;
    private final GameSessionController gameSessionController;

    private EndChallenge endChallenge;
    private RankAchieved rankAchieved;
    private LevelResult levelResult;
    private Rank rankBeforeChallenge;
    private LevelProgressState lastLevelState;

    public AppCordinator(){
        this.screenManager = new ScreenManager();
        this.gameSessionController = new GameSessionController();
    }

    public JPanel build(){
        Intro intro = new Intro(() -> screenManager.show(ONBOARDING));
        OnBoarding onBoarding = new OnBoarding(() -> screenManager.show(MENU));
        Challenge challenge = new Challenge(gameSessionController, this::onChallengeFinished);
        endChallenge = new EndChallenge(this::afterEndChallenge);
        rankAchieved = new RankAchieved(this::goToNextChallenge);
        levelResult = new LevelResult(this::afterLevelResult);

        Menu menu = new Menu(new Menu.MenuSelectionHandler() {
            @Override
            public void onNewGame() {
                screenManager.show(NAME_ENTRY);
            }
            @Override
            public void onInstructions() {
                JOptionPane.showMessageDialog(screenManager.getRoot(), INSTRUCTIONS,
                        "Como Jogar", JOptionPane.INFORMATION_MESSAGE);
            }
            @Override
            public void onContinue() {
                JOptionPane.showMessageDialog(screenManager.getRoot(), "Continue is not available yet.");
            }

            @Override
            public void onQuit() {
                System.exit(0);
            }
        });

        NameEntry nameEntry = new NameEntry(playerName -> {
            try {
                gameSessionController.startNewGame(playerName);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(screenManager.getRoot(),
                        "Nome inválido: use 2 a 20 letras, números, espaços, hífen ou underscore.");
                return;
            }
            rankBeforeChallenge = gameSessionController.getCurrentPlayer().getRank();
            screenManager.show(CHALLENGE);
        });
        screenManager.register(INTRO, intro);
        screenManager.register(MENU, menu);
        screenManager.register(ONBOARDING, onBoarding);
        screenManager.register(NAME_ENTRY, nameEntry);
        screenManager.register(CHALLENGE, challenge);
        screenManager.register(END_CHALLENGE, endChallenge);
        screenManager.register(RANK_ACHIEVED, rankAchieved);
        screenManager.register(LEVEL_RESULT, levelResult);
        screenManager.show(INTRO);
        return screenManager.getRoot();
    }

    private void onChallengeFinished(ChallengeResolution resolution){
        Player player = gameSessionController.getCurrentPlayer();
        endChallenge.showResult(resolution.endReason(), player.getTotalPoints(), resolution.pointsEarned(),
                formatRank(player.getRank()), resolution.word());
        screenManager.show(END_CHALLENGE);
    }

    private void afterEndChallenge(){
        Rank currentRank = gameSessionController.getCurrentPlayer().getRank();
        if (currentRank != rankBeforeChallenge){
            rankBeforeChallenge = currentRank;
            rankAchieved.setRank(currentRank);
            screenManager.show(RANK_ACHIEVED);
        }else {
            goToNextChallenge();
        }
    }

    private void goToNextChallenge(){
        if (gameSessionController.advanceNextChallenge()){
            screenManager.show(CHALLENGE);
            return;
        }
        LevelResolution resolution = gameSessionController.resolveCurrentLevel();
        lastLevelState = resolution.state();
        levelResult.showResult(resolution, formatRank(gameSessionController.getCurrentPlayer().getRank()));
        screenManager.show(LEVEL_RESULT);
    }

    private void afterLevelResult(){
        if (lastLevelState == LevelProgressState.GAME_COMPLETED){
            screenManager.show(MENU);
        }else {
            screenManager.show(CHALLENGE);
        }
    }

    private static String formatRank(Rank rank){
        return rank.name().replace('_', ' ');
    }
}
