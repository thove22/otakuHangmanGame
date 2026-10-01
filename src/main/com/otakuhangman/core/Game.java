package main.com.otakuhangman.core;
import main.com.otakuhangman.core.AttemptResult;

import java.io.*;
import java.util.List;

public class Game {
    private Player currentPlayer;
    private List<Level> levels;
    private int currentLevelIndex;
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public  void start(){
        int userChoice  = displayMenu();
        handleMenuChoice(userChoice);
    }

    private int displayMenu() {
        try {
            int choice = 0;
            do{
                System.out.println("≡≡≡ O T A K U - H A N G M A N ≡≡≡");
                System.out.println("1.Novo Jogo");
                System.out.println("2.Continuar");
                System.out.println("3.Como Jogar");
                System.out.println("4.Sair");
                System.out.print("Escolha uma opcao: ");
                try{
                    choice = Integer.parseInt(br.readLine());
                    if (choice < 1 || choice > 4){
                        System.out.println("Opcao Invalida!!!");
                    }
                }catch (NumberFormatException e){
                    System.out.println("Entrada Invalida! Por favor, digite apenas numeros.");
                    choice = 0;
                }
            }while (choice < 1 || choice > 4);

            return choice;

        }catch (IOException e){
            System.out.println("Erro de Leitura");
            exitGame();
            return  0;
        }
    }

    private  void handleMenuChoice(int userChoice){
        switch (userChoice){
            case 1:
                 startNewGame();
                  break;
            case 2:
               //   continueGame();
                  break;
            case 3:
                 showInstrutions();
                  break;
            case 4:
                   exitGame();
                   break;
            default:
                    System.out.println("Opcao Invalida!!!");
        }
    }
    void startNewGame(){
        try {
            String name = getValidPlayerName(br);
            currentPlayer = new Player(name);
            levels = GameData.createLevels();
            currentLevelIndex = 0;
            playCurrentLevel();
        } catch (IOException e) {
            System.out.println("Erro de Entrada: " + e.getMessage());
        }
    }
    String getValidPlayerName(BufferedReader br) throws IOException{
        while (true){
            System.out.println("Insira seu nome de jogador: ");
            String name = br.readLine().trim();

            if(name.isEmpty()){
                return "Player_" + (int)(Math.random() * 1000);
            }
            if (name.length() < 2 || name.length() > 20){
                System.out.println(" O Nome deve ter entre 2 e 20 caracteres.");
                continue;
            }
            if (!name.matches("^[a-zA-Z0-9_\\-\\s]+$")){
                System.out.println("Use apenas letras, números, espaços, hífen ou underscore");
                continue;
            }
            return name;
        }
    }

    void playCurrentLevel(){
        try {
            currentPlayer.setCurrentLevelScore(0);
            Level currentLevel = levels.get(currentLevelIndex);
            List<Challenge> challenges = currentLevel.getChallenges();
            int totalChallenges = challenges.size();

            for (int i = 0 ; i < totalChallenges ; i++){
                Challenge challenge = challenges.get(i);
                challenge.startTimer();
                challenge.reset();
                System.out.println("Desafio " + (i + 1) + " de " + totalChallenges);
                System.out.println(challenge.getHint());

                while (!challenge.isComplete()){
                    if (challenge.isTimeUp()){
                        currentPlayer.processChallengesResult(6);
                        drawHangMan(6);
                        break;
                    }

                    System.out.println(challenge.getStatus());
                    char letter = getTriedLetter(br);
                    AttemptResult result = challenge.tryLetter(letter);
                    switch (result){
                        case CORRECT -> {
                            System.out.println("Tentativa Correta!");
                        }
                        case WRONG -> {
                            System.out.println("Tentativa Incorreta!");
                            drawHangMan(challenge.getCurrentErrors());
                        }
                        case ORDER_MISTAKE -> {
                            System.out.println("Ordem Incorreta!");
                            drawHangMan(challenge.getCurrentErrors());
                        }
                        case REPEATED -> {
                            System.out.println("Letra já Tentada!");
                        }
                        case TIME_UP -> {
                            System.out.println("Tempo Esgotado!");
                        }
                        case CHALLENGE_OVER -> {
                            System.out.println("Desafio já terminado!");
                        }
                    }

                    System.out.println("Letras Tentadas: " + challenge.getTriedLettersString());
                    System.out.println("Tentativas: " + challenge.getAttemps() + " / " + challenge.getMaxAttemps());
                    if (challenge.isLost()){
                        break;
                    }
                }
                if (challenge.isWon()){
                    System.out.println("PARABÉNS! Você adivinhou: " + challenge.getWord());
                }else {
                    if (challenge.isMaxAttempsReached()){
                        System.out.println("Numero de Tentativas Excedido! A palavra era: " + challenge.getWord());
                    }else if(challenge.isTimeUp()) {
                        System.out.println("Tempo Esgotado! A palavra era: " + challenge.getWord());
                    }else {
                        System.out.println("💀FORCA COMPLETA! A palavra era: " + challenge.getWord());
                    }
                }
                EndChallengeReason endReason = challenge.getEndReason();

                int pointEarned = switch (endReason){
                    case WON -> currentPlayer.processChallengesResult(challenge.getCurrentErrors());
                    case TIME_UP -> currentPlayer.processChallengesResult(6);
                    case ERROR_LIMIT -> currentPlayer.processChallengesResult(6);
                    case ATTEMPS_LIMIT -> currentPlayer.processChallengesResult(6);
                };

                System.out.println(currentPlayer.getPlayerStatus());
                System.out.println("\n Resultados do Jogador: ");
                System.out.println("Pontos: " + pointEarned);
                System.out.println("\nPressione Enter para continuar...");
                br.readLine();
            }
            checkLevelCompletion(currentLevel);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    char getTriedLetter(BufferedReader br) throws IOException{
        while (true){
            System.out.println("Insira uma letra: ");
            String input = br.readLine();
            if (input.isEmpty()){
                System.out.println("Entrada vazia. Insira uma letra: .");
                continue;
            }
            if (input.length() != 1){
                System.out.println("Insira apenas uma letra.");
                continue;
            }
            char letter =  input.charAt(0);

            if (!Character.isLetter(letter) && !Character.isDigit(letter)){
                System.out.println("Use apenas letras ou numeros");
                continue;
            }
            return letter;
        }
    }
    void checkLevelCompletion(Level level){
        System.out.println("=============Nivel Finalizado=============");
        System.out.println(currentPlayer.getPlayerStatus());
        System.out.println("Score do Nivel: " + currentPlayer.getCurrentLevelScore());
        System.out.println("Desafios Concluidos: " + currentPlayer.getCompletedChallenges()  + " / "+
                level.getChallenges().size());

        boolean passed = level.canAdvanceToNextLevel(currentPlayer);
        if (passed){
            System.out.println("Parabéns! Você passou de nível!");
            currentLevelIndex++;
            currentPlayer.advanceToNextLevel();
        }else {
            if (isForgivingLevel(level)){
                System.out.println("Você ainda não atingiu os requisitos mas este é um nível de aprendizado." +
                        "\n Você pode continuar!");
                System.out.println(printLevelRequeriments(level));
                currentLevelIndex++;
                currentPlayer.advanceToNextLevel();
            }else {
                System.out.println("Você não atingiu os requisitos para avançar.");
                System.out.println(printLevelRequeriments(level));
                System.out.println("Você precisará tentar novamente." +
                        "\n Este nível exige domínio!!!!");
                level.resetLevel();
                playCurrentLevel();
            }

        }
        System.out.println("=============================================");

    }

    boolean isForgivingLevel(Level level){
        return level.getLevelNumber() <= 3;
    }
    void drawHangMan(int errors){
        if (errors < 0 || errors > 6){
            throw new IllegalArgumentException("Número de erros inválido: " + errors);
        }
        System.out.println(HangmanArt.STAGES[errors]);
    }

    String printLevelRequeriments(Level level){
        return "Requisitos: " + "\n" +
                "-- Score mínimo: " + level.getRequiredScoreToPass() + "\n" +
                "--  Desafios mínimos: " + level.getRequiredChallengesToPass() + "\n";
    }

    void showInstrutions(){
        System.out.println("\n=========== COMO JOGAR ===========");
        System.out.println("Bem-vindo ao Otaku Hangman!");
        System.out.println();
        System.out.println("Objetivo:");
        System.out.println("- Adivinhar a palavra secreta, uma letra por vez.");
        System.out.println();
        System.out.println("Regras:");
        System.out.println("- Insira apenas UMA letra por tentativa.");
        System.out.println("- Letras repetidas não contam como nova tentativa.");
        System.out.println("- Cada erro adiciona uma parte ao boneco da forca.");
        System.out.println("- Com 6 erros, o desafio é perdido.");
        System.out.println();
        System.out.println("Progressão:");
        System.out.println("- Cada nível possui vários desafios.");
        System.out.println("- Você precisa atingir o score mínimo para avançar.");
        System.out.println("- Nos primeiros níveis, você pode avançar mesmo falhando.");
        System.out.println("- A partir de níveis mais altos, será preciso dominar o jogo!");
        System.out.println();
        System.out.println("Boa sorte e divirta-se! (⌐■_■)");
        System.out.println("=================================\n");
    }
    void exitGame(){
        System.out.println("Obriagdo por Jogar o Otaku HangMan, Sayonara minna-san!ᕦ(ò_óˇ)ᕤ");
        System.exit(0);
    }
}