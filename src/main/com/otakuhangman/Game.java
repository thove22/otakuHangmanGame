package main.com.otakuhangman;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

public class Game {
    private Player currentPlayer;
    private List<Level> levels;
    private int currentLevelIndex;
    private boolean isPlaying;

    public Player getCurrentPlayer() {return currentPlayer;}
    public void setCurrentPlayer(Player currentPlayer) {this.currentPlayer = currentPlayer;}
    public List<Level> getLevels() {return levels;}
    public void setLevels(List<Level> levels) {this.levels = levels;}
    public int getCurrentLevelIndex() {return currentLevelIndex;}
    public void setCurrentLevelIndex(int currentLevelIndex) {this.currentLevelIndex = currentLevelIndex;}
    public boolean isPlaying() {return isPlaying;}
    public void setPlaying(boolean playing) {isPlaying = playing;}

    public  void start(){
        int userChoice  = displayMenu();
        handleMenuChoice(userChoice);
    }

    private int displayMenu() {
        try (BufferedReader br =  new BufferedReader(new InputStreamReader(System.in))){
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
              //    showInstrutions();
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
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String name = getValidPlayerName(br);
            currentPlayer = new Player(name);
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
            if (name.matches("^[a-zA-Z0-9_\\-\\s]+$")){
                System.out.println("Use apenas letras, números, espaços, hífen ou underscore");
                continue;
            }
            return name;
        }
    }

    void playCurrentLevel(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            currentPlayer.setCurrentLevelScore(0);
            Level currentLevel = levels.get(currentLevelIndex);
            List<Challenge> challenges = currentLevel.getChallenges();
            int totalChallenges = challenges.size();
            for (int i = 0 ; i < totalChallenges ; i++){
                Challenge challenge = challenges.get(i);
                System.out.println("Desafio " + (i + 1) + " de " + totalChallenges);
                System.out.println(challenge.getHint());
                System.out.println(challenge.getStatus());

                while (!challenge.isComplete()){
                    char letter = getTriedLetter(br);
                    boolean correct =  challenge.tryLetter(letter);
                    System.out.println(correct ? "Tentativa Correcta" : "Tentativa Incorecta" );

                    if (!correct){
                        //drawHangMan(challenge.getCurrentErrors());
                    }
                }
                System.out.println(challenge.isWon() ? "\nPARABÉNS! Você adivinhou: " + challenge.getWord()
                        : "\n💀 FORCA COMPLETA! A palavra era: " + challenge.getWord());
                int errors = challenge.getCurrentErrors();
                int pointEarned = currentPlayer.processChallengesResult(errors);

                System.out.println(currentPlayer.getPlayerStatus());
                System.out.println("\n Resultados do Jogador: ");
                System.out.println("Pontos: " + pointEarned);
                System.out.println("\nPressione Enter para continuar...");
                br.readLine();
            }
            currentPlayer.advanceToNextLevel();
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
            }
            char letter =  input.charAt(0);

            if (!Character.isLetter(letter) && !Character.isDigit(letter)){
                System.out.println("Use apenas letras ou numeros");
                continue;
            }
            return letter;
        }
    }
    void exitGame(){
        System.out.println("Obriagdo por Jogar o Otaku HangMan, Sayonara minna-san!ᕦ(ò_óˇ)ᕤ");
        System.exit(0);
    }
}