package main.com.otakuhangman.gui;

import main.com.otakuhangman.core.AttemptResult;
import main.com.otakuhangman.core.Challenge;
import main.com.otakuhangman.core.Level;
import main.com.otakuhangman.core.Player;

public interface GameGui {
    void showMenu();
    void showChallenge(Challenge challenge);
    void showAttempResult(AttemptResult result);
    void showLevelResult(Player player, Level level);
    void showGameOver(Player player);
}
