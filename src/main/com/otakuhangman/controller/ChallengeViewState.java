package main.com.otakuhangman.controller;

import java.util.List;

public record ChallengeViewState(
        String hint,
        String maskedWord,
        List<Character> triedLetters,
        int currentErrors,
        int attempts,
        int maxAttempts,
        long remainingSeconds,
        boolean complete
) {

}
