package main.com.otakuhangman;

import java.util.List;

public class Level {
   private int levelNumber;
   private List<Challenge> challenges;
   private int requiredScoreToPass;
   private int requiredChallengesToPass;
   private int currentChallengesIndex;
   private int completedChallenges;
   private boolean isLevelCompleted;

   public Level(int levelNumber, List<Challenge> challenges){
      this.levelNumber = levelNumber;
      this.challenges = challenges;
      this.currentChallengesIndex = 0;
      this.completedChallenges = 0;
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

   public int getCurrentChallengesIndex() {
      return currentChallengesIndex;
   }

   public int getCompletedChallenges() {
      return completedChallenges;
   }

   public boolean isLevelCompleted() {
      return isLevelCompleted;
   }

   void recordChallengeCompletion(){

   }
   void resetLevel(){
      currentChallengesIndex = 0;
      completedChallenges = 0;
      isLevelCompleted = false;

      for (Challenge challenge : challenges){
            challenge.reset();
      }
   }
   boolean canAdvanceToNextLevel(Player player ){
      return player.getCurrentLevelScore() >= requiredScoreToPass &&
              player.getCompletedChallenges() >= requiredChallengesToPass;
   }
   Challenge getCurrentChallenge(){
      if (currentChallengesIndex < 0 || currentChallengesIndex > challenges.size()){
            return null;
      }
      return challenges.get(currentChallengesIndex);
   }

   boolean hasNextChallenges(){
      return currentChallengesIndex < challenges.size() - 1;
   }

   void moveToNextChallenge(){
         Challenge current = getCurrentChallenge();

         if (current == null){
            return;
         }
         if(!current.isComplete()){
            return;
         }
         if (hasNextChallenges()){
            currentChallengesIndex++;
         }
   }

   double getProgressPercentage(){
      return((double) completedChallenges/challenges.size()) * 100;
   }

   boolean isLevelComplete(){
      return completedChallenges == challenges.size();
   }
   private int calcularMinimoDesafios() {
      return (int) Math.ceil(challenges.size() * 0.75);
   }

   private int calcularScoreMinimo() {
      int maxScore = calcularScoreMaximo();
      return (int) (maxScore * 0.50);
   }
   private int calcularScoreMaximo() {
      int total = challenges.size();
      return 370 + (total - 3) * 200;
   }
}
