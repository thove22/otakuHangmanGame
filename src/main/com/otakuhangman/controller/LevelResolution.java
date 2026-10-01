package main.com.otakuhangman.controller;

public record LevelResolution(
        LevelProgressState state,
        int levelNumber,
        int levelScore,
        int completedChallenges,
        int totalChallenges,
        int requiredScore,
        String requirements
) {
}
