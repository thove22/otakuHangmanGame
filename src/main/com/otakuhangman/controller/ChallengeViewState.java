package main.com.otakuhangman.controller;

public record ChallengeViewState(
        String hint,
        String maskedWord,
        String triedLetters,
        int currentErrors,
        int attempts,
        int maxAttempts,
        long remainingSeconds,
        boolean complete
) {

}
