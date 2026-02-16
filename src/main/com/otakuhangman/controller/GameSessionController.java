package main.com.otakuhangman.controller;
import main.com.otakuhangman.core.AttemptResult;
import main.com.otakuhangman.core.Challenge;
import main.com.otakuhangman.core.GameData;
import main.com.otakuhangman.core.Level;
import main.com.otakuhangman.core.Player;
import java.util.List;

public class GameSessionController {
    private Player currentPlayer;
    private List<Level> levels;
    private int currentLevelIndex;
    private int currentChallengeIndex;
    private Challenge currentChallenge;


    public void startNewGame(String playerName){
        String validName = sanitizePlayerName(playerName);
        levels = GameData.createLevels();
        currentLevelIndex = 0;
        currentChallengeIndex = 0;

        currentChallenge = getCurrentLevel().getChallenges().get(currentChallengeIndex);
        currentChallenge.reset();
        currentChallenge.startTimer();
    }
    public AttemptResult submitGuess(char letter){
        ensureSessionStarted();
        return currentChallenge.tryLetter(letter);
    }

    public ChallengeViewState getCurrentChallengeStatus(){
        ensureSessionStarted();
        return new ChallengeViewState(
                currentChallenge.getHint(),
                currentChallenge.getMaskedWord(),
                currentChallenge.getTriedLettersString(),
                currentChallenge.getCurrentErrors(),
                currentChallenge.getAttemps(),
                currentChallenge.getMaxAttemps(),
                currentChallenge.getRemainingSeconds(),
                currentChallenge.isComplete()
        );
    }
    public Player getCurrentPlayer() {
        ensureSessionStarted();
        return currentPlayer;
    }

    private Level getCurrentLevel() {
        if (levels == null || levels.isEmpty()) {
            throw new IllegalStateException("No levels loaded.");
        }
        return levels.get(currentLevelIndex);
    }

    private String sanitizePlayerName(String playerName) {
        if (playerName == null || playerName.trim().isEmpty()) {
            return "Player_" + (int) (Math.random() * 1000);
        }

        String trimmedName = playerName.trim();
        if (trimmedName.length() < 2 || trimmedName.length() > 20) {
            throw new IllegalArgumentException("Player name must have 2 to 20 characters.");
        }

        if (!trimmedName.matches("^[a-zA-Z0-9_\\-\\s]+$")) {
            throw new IllegalArgumentException("Player name has invalid characters.");
        }

        return trimmedName;
    }

    private void ensureSessionStarted() {
        if (currentPlayer == null || currentChallenge == null) {
            throw new IllegalStateException("Session not started. Call startNewGame first.");
        }
    }

}
