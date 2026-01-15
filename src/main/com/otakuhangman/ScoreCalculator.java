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
}
