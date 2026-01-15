package main.com.otakuhangman;

public class ScoreCalculator {

    public static int calculateBaseScore(int errors){

        if(errors < 0 || errors > 6 ){
            throw new IllegalArgumentException(
                    "Numero de Erros Invalidos: " + errors + ". O valor deve estar entre 0 e 6"
            );
        }

        for (ScoreBase scoreBase : ScoreBase.values()){
            if (scoreBase.getErrors() == errors){
                return scoreBase.getScore();
            }
        }

        throw new IllegalStateException("Score nao Encontrado para o Erro");
    }

    public static double getStreakMultiplier(int currentStreak){
        if(currentStreak < 0 ){
            throw new IllegalArgumentException(
                    "Current Streak:" + currentStreak + ". Streak negativa não faz sentido conceitualmente."
            );
        }
        switch (currentStreak){
            case 0:
            case 1:
                return 1.0;
            case 2:
                return 1.2;
            case 3:
                return 1.5;
            default:
                return 2.0;
        }
    }

    public static int calculateFinalPoints(int errors, int currentStreak){

      return (int) Math.round(getStreakMultiplier(currentStreak) * calculateBaseScore(errors));

    }

}
