package main.com.otakuhangman;

import javax.crypto.spec.PSource;
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
    private boolean ordered;
    private int currentIndex;
    private long startTimeNs;
    private long timeLimitNs;


    public Challenge(String word, String hint, String category, boolean ordered){
        this.word = word;
        this.category = category;
        this.ordered = ordered;
        this.hint = hint;
        this.attemps = 0;
        this.currentIndex = 0;
        this.normalizedWord = word.toUpperCase();
        this.discoveredPositions = new boolean[normalizedWord.length()];
        this.triedLetters = new HashSet<>();
        this.currentErrors = 0;
        this.maxAttemps = normalizedWord.length() + 2;
        this.timeLimitNs = 30L * 1_000_000_000L;
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

    public boolean isOrdered() {
        return ordered;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    String getMaskedWord(){
     StringBuilder sb = new StringBuilder();

        for (int i = 0; i < normalizedWord.length(); i++){
            char currentChar = normalizedWord.charAt(i);

            if (!Character.isLetter(currentChar) || discoveredPositions[i]){
                sb.append(currentChar);
            }else {
                sb.append("_");
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    AttemptResult tryLetter(char letter){
        if (isTimeUp()){
            return AttemptResult.TIME_UP;
        }
        attemps++;

        char upperLetter = Character.toUpperCase(letter);

        if (triedLetters.contains(upperLetter)){
            return AttemptResult.REPEATED;
        }

        triedLetters.add(upperLetter);

        if (!ordered){
         return   tryNormalLetter(upperLetter);
        }else {
         return  tryOrderedLetter(upperLetter);
        }

    }

    AttemptResult tryNormalLetter(char letter){
        boolean found = false;

        for(int i = 0; i < normalizedWord.length(); i++){
            if (normalizedWord.charAt(i) == letter){
                discoveredPositions[i] = true;
                found = true;
            }
        }
        if (!found){
            currentErrors++;
            return AttemptResult.WRONG;
        }
        return AttemptResult.CORRECT;
    }

    AttemptResult tryOrderedLetter(char letter){
        if(currentIndex >= normalizedWord.length()){
            return AttemptResult.WRONG;
        }
        char expected = normalizedWord.charAt(currentIndex);

        if (letter == expected){
            discoveredPositions[currentIndex] = true;
            currentIndex++;
            return AttemptResult.CORRECT;
        }else {
            currentErrors++;
            return AttemptResult.ORDER_MISTAKE;
        }
    }

     boolean isTimeUp(){
        return (System.nanoTime() - startTimeNs) >= timeLimitNs;
     }

     long remaingSeconds(){
        long remaingNs = timeLimitNs - (System.nanoTime() - startTimeNs);
        return Math.max(0, remaingNs / 1_000_000_000L);
     }
     void startTimer(){
         this.startTimeNs = System.nanoTime();
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
        return currentErrors >= MAX_ERRORS || attemps >= maxAttemps || isTimeUp();
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
        this.attemps = 0;
        this.currentIndex = 0;
        this.startTimeNs = System.nanoTime();
        for (int i = 0 ; i < discoveredPositions.length; i++){
            discoveredPositions[i] = false;
        }
    }
    String getStatus(){
        return  "====================================================\n"+
                (ordered ? "DESAFIO ORDENADO": " ") + "\n" +
                "Palavra: " + getMaskedWord() + "\n"+
                (ordered ? "Posição atual: " + currentIndex +  " / " + normalizedWord.length() + "\n" : " ") +
                "Letras Tentadas: " + triedLetters + "\n" +
                "Erros: " + currentErrors + " / " + MAX_ERRORS + "\n"+
                "Tentativas: " + attemps + " / " + maxAttemps + "\n"+
                "Tempo Restante: " + remaingSeconds() + "s " + "\n" +
                "=====================================================";
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
