package main.com.otakuhangman.controller;
import main.com.otakuhangman.core.*;

import java.util.List;

public class GameSessionController {
    private Player currentPlayer;
    private List<Level> levels;
    private int currentLevelIndex;
    private int currentChallengeIndex;
    private Challenge currentChallenge;


    public void startNewGame(String playerName){
        String validName = sanitizePlayerName(playerName);
        currentPlayer = new Player(validName);
        levels = GameData.createLevels();
        currentLevelIndex = 0;
        prepareChallengeForCurrentLevl();
    }

    public void prepareChallengeForCurrentLevl(){
        currentChallengeIndex = 0;
        currentChallenge = getCurrentLevel().getChallenges().get(currentChallengeIndex);
        currentChallenge.reset();
        currentChallenge.startTimer();
    }

    public AttemptResult submitGuess(char letter){
        ensureSessionStarted();
        return currentChallenge.tryLetter(letter);
    }

    public boolean isCurrentChallengeComplete(){
        ensureSessionStarted();
        return  currentChallenge.isComplete();
    }
    public ChallengeResolution resolveCurrentChallenge(){
        ensureSessionStarted();

        if(!currentChallenge.isComplete()){
           throw  new IllegalStateException("Current Challenge is not complete yet");
        }
        EndChallengeReason endReason = currentChallenge.getEndReason();

        int pointsEarned = switch (endReason){
            case WON -> currentPlayer.processChallengesResult(currentChallenge.getCurrentErrors());
            case TIME_UP , ATTEMPS_LIMIT , ERROR_LIMIT -> currentPlayer.processChallengesResult(6);
        };

        return new ChallengeResolution(endReason == EndChallengeReason.WON,
                currentChallenge.getWord(),
                endReason,
                pointsEarned,
                currentPlayer.getPlayerStatus());
    }
    public boolean advanceNextChallenge(){
        ensureSessionStarted();
        int totalChallenges = getCurrentLevel().getChallenges().size();
        if (currentChallengeIndex + 1 >= totalChallenges){
            return false;
        }
        currentChallengeIndex++;
        currentChallenge = getCurrentLevel().getChallenges().get(currentChallengeIndex);
        currentChallenge.reset();
        currentChallenge.startTimer();
        return true;
    }

    public ChallengeViewState getCurrentChallengeState(){
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
    public LevelResolution resolveCurrentLevel(){
        ensureSessionStarted();

        Level currentLevel = getCurrentLevel();
        boolean passed = currentLevel.canAdvanceToNextLevel(currentPlayer);
        String requeriments = printLevelRequirements(currentLevel);

        LevelProgressState state;

        if (passed){
            currentLevelIndex++;
            currentPlayer.advanceToNextLevel();
            prepareChallengeForCurrentLevl();
            state = LevelProgressState.ADVANCED;
        }else if(isForgivingLevel(currentLevel)){
            currentLevelIndex++;
            currentPlayer.advanceToNextLevel();
            state = LevelProgressState.ISFORGIVING;
        }else {
            currentLevel.resetLevel();
            prepareChallengeForCurrentLevl();
            state = LevelProgressState.RETRY;
        }

        return new LevelResolution(
                state,
                currentLevel.getLevelNumber(),
                currentPlayer.getCurrentLevelScore(),
                currentPlayer.getCompletedChallenges(),
                currentLevel.getChallenges().size(),
                requeriments
        );
    }

    public Player getCurrentPlayer() {
        ensureSessionStarted();
        return currentPlayer;
    }
    public int getCurrentLevelIndex(){
        ensureSessionStarted();
        return currentLevelIndex;
    }
    public int getCurrentChallengeIndex() {
        ensureSessionStarted();
        return currentChallengeIndex;
    }
    private Level getCurrentLevel() {
        if (levels == null || levels.isEmpty()) {
            throw new IllegalStateException("No levels loaded.");
        }
        return levels.get(currentLevelIndex);
    }
    private boolean isForgivingLevel(Level level) {
        return level.getLevelNumber() >= 4;
    }
    private String printLevelRequirements(Level level) {
        return "Requisitos: " + "\n" +
                "-- Score mínimo: " + level.getRequiredScoreToPass() + "\n" +
                "--  Desafios mínimos: " + level.getRequiredChallengesToPass() + "\n";
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
