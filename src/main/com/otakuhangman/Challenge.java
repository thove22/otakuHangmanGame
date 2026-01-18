package main.com.otakuhangman;

import java.util.HashSet;
import java.util.Set;

public class Challenge {
    private String word;
    private String hint;
    private String category;
    private Set<Character> triedLetters;
    private int currentErrors;
    private boolean[] discoveredPositions;
    private final int MAX_ERRORS = 6;
    private String normalizedWord;


    public Challenge(String word, String hint, String category){
        this.word = word;
        this.category = category;
        this.hint = hint;

        this.normalizedWord = word.toUpperCase();
        this.discoveredPositions = new boolean[normalizedWord.length()];
        this.triedLetters = new HashSet<>();
        this.currentErrors = 0;
    }

    public String getWord() {return word;}

    public void setWord(String word) {this.word = word;}

    public String getHint() {return hint;}

    public void setHint(String hint) {this.hint = hint;}

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

    boolean isWon(){
        for(int i = 0; i < normalizedWord.length(); i++){
        if(Character.isLetter(normalizedWord.charAt(i)) &&!discoveredPositions[i]){
            return false;
        }
        }
        return false;
    }
    boolean isLost(){
        return currentErrors >= MAX_ERRORS;
    }
    boolean isComplete(){
        return isWon() || isLost();
    }
    public int getCurrentErrors(){
        return currentErrors;
    }


}
