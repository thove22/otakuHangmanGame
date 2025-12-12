package main.com.otakuhangman;
import java.io.*;

public class Game {

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
                //  startNewGame();
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

    private void exitGame(){
        System.out.println("Obriagdo por Jogar o Otaku HangMan, Sayonara minna-san!ᕦ(ò_óˇ)ᕤ");
        System.exit(0);
    }
}