package main.com.otakuhangman.core;

public enum ScoreBase {
   NO_ERROR(0, 100),
   ONE_ERROR(1, 80),
   TWO_ERRORS(2, 60),
   THREE_ERRORS(3, 40),
   FOUR_ERRORS(4, 20),
   FIVE_ERRORS(5,10),
    SIX_ERRORS(6, 0);

   private final int errors;
   private final int score;

   ScoreBase(int errors, int score){
       this.errors = errors;
       this.score = score;
   }
    public int getErrors() {
        return errors;
    }
    public int getScore(){
       return score;
    }
}
