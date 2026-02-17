package main.com.otakuhangman.controller;

import main.com.otakuhangman.core.EndChallengeReason;

public record ChallengeResolution(
        boolean won,
        String word,
        EndChallengeReason endReason,
        int pointsEarned,
        String playerStatus
) {
}
