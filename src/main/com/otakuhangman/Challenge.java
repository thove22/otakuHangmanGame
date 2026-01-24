package main.com.otakuhangman;

import java.util.*;
import java.util.stream.Collectors;

public class Challenge {
    private String word;
    private String hint;
    private String category;
    private Set<Character> triedLetters;
    private int currentErrors;
    private boolean[] discoveredPositions;
    private final int MAX_ERRORS = 6;
    private int attemps;
    private int maxAttemps;
    private String normalizedWord;


    public Challenge(String word, String hint, String category){
        this.word = word;
        this.category = category;
        this.hint = hint;
        this.attemps = 0;
        this.normalizedWord = word.toUpperCase();
        this.discoveredPositions = new boolean[normalizedWord.length()];
        this.triedLetters = new HashSet<>();
        this.currentErrors = 0;
        this.maxAttemps = normalizedWord.length() + 2;
    }

    public String getWord() {return word;}

    public int getMaxAttemps() {
        return maxAttemps;
    }

    public int getAttemps() {
        return attemps;
    }

    public void setWord(String word) {this.word = word;}

    public String getHint() {return hint;}

    public void setHint(String hint) {this.hint = hint;}

    public Set<Character> getTriedLetters() {
        return triedLetters;
    }

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}

    String getMaskedWord(){
        char[] display = new char[normalizedWord.length()];

        for (int i = 0; i < normalizedWord.length(); i++){
            char currentChar = normalizedWord.charAt(i);

            if (!Character.isLetter(currentChar) || discoveredPositions[i]){
                display[i] = currentChar;
            }else {
                display[i] ='_';
            }
        }
        return new String(display);
    }

    boolean tryLetter(char letter){
        attemps++;
        char upperLetter = Character.toUpperCase(letter);

        if (triedLetters.contains(upperLetter)){
            return false;
        }

        triedLetters.add(upperLetter);
        boolean found = false;

        for(int i = 0; i < normalizedWord.length(); i++){
            if (normalizedWord.charAt(i) == upperLetter){
                discoveredPositions[i] = true;
                found = true;
            }
        }
        if (!found){
            currentErrors++;
        }
        return found;
    }
    String getTriedLettersString(){
        if(triedLetters.isEmpty()){
            return "Nenhuma letra tentada";
        }
        List<Character> sorted = new ArrayList<>(triedLetters);
        Collections.sort(sorted);

        return sorted.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }

    boolean isWon(){
        for(int i = 0; i < normalizedWord.length(); i++){
        if(Character.isLetter(normalizedWord.charAt(i)) &&!discoveredPositions[i]){
            return false;
        }
        }
        return true;
    }
    boolean isLost(){
        return currentErrors >= MAX_ERRORS || attemps >= maxAttemps;
    }

    boolean isMaxAttempsReached(){
        return attemps >= maxAttemps;
    }
    boolean isComplete(){
        return isWon() || isLost();
    }
    public int getCurrentErrors(){
        return currentErrors;
    }


    void reset(){
        this.triedLetters.clear();
        this.currentErrors = 0;

        for (int i = 0 ; i < discoveredPositions.length; i++){
            discoveredPositions[i] = false;
        }
    }
    String getStatus(){
        return  "=========================================\n"+
                "Palavra: " + getMaskedWord() + "\n"+
                "Letras Tentadas: " + triedLetters + "\n" +
                "Erros: " + currentErrors + " / " + MAX_ERRORS + "\n"+
                "Tentativas: " + attemps + " / " + maxAttemps + "\n"+
                "=========================================";
    }
    @Override
    public String toString() {
        return "Challenge{" +
                "maskedWord='" + getMaskedWord() + '\'' +
                ", triedLetters=" + triedLetters +
                ", errors=" + currentErrors + "/" + MAX_ERRORS +
                '}';
    }

}
