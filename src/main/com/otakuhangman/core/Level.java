package main.com.otakuhangman.core;

import java.util.List;

public class Level {
   private int levelNumber;
   private List<Challenge> challenges;
   private int requiredScoreToPass;
   private int requiredChallengesToPass;
   public Level(int levelNumber, List<Challenge> challenges){
      if(challenges == null || challenges.isEmpty()){
         throw new IllegalArgumentException("O level deve ter pelo menos um desafio");
      }
      this.levelNumber = levelNumber;
      this.challenges = challenges;
      requiredChallengesToPass =  calculateMinChallengesToPass();
      requiredScoreToPass = calculateMinScoreToPass()  ;
   }

   public int getLevelNumber() {
      return levelNumber;
   }

   public List<Challenge> getChallenges() {
      return challenges;
   }

   public int getRequiredScoreToPass() {
      return requiredScoreToPass;
   }

   public int getRequiredChallengesToPass() {
      return requiredChallengesToPass;
   }

   void resetLevel(){
      for (Challenge challenge : challenges){
            challenge.reset();
      }
   }
   boolean canAdvanceToNextLevel(Player player ){
      return player.getCurrentLevelScore() >= requiredScoreToPass &&
              player.getCompletedChallenges() >= requiredChallengesToPass;
   }

   private int calculateMinChallengesToPass() {
      return (int) Math.ceil(challenges.size() * 0.75);
   }

   private int calculateMinScoreToPass() {
      int maxScore = calculateMaxScore();
      return (int) (maxScore * 0.50);
   }
   private int calculateMaxScore() {
      int totalChallenges = challenges.size();

      if (totalChallenges <= 0) return  0;
      int score = 0;
      for(int i = 0; i < totalChallenges; i++){
         int challengeNumber = i + 1;

         if (challengeNumber == 1){
            score+=100;
         }else if(challengeNumber == 2){
            score+=120;
         } else if (challengeNumber == 3) {
            score+=150;
         }else{
            score+=200;
         }
      }
      return score;
   }

}
