package main.com.otakuhangman;

public class Player {
    private String name;
    private int currentLevelScore;
    private int currentStreak;
    private int totalPoints;
    private int currentLevel;
    private int completedChalenges;
    private Rank rank;

    public  Player(String name){
        this(name, 1, 0, 0, 0, 0, Rank.OTAKU_INICIANTE);
    }

    public Player(String name,int currentLevelScore, int currentStreak , int totalPoints,
                  int currentLevel, int completedChalenges, Rank rank){

        this.name = name;
        this.currentLevelScore = currentLevelScore;
        this.currentStreak = currentStreak;
        this.totalPoints = totalPoints;
        this.completedChalenges = completedChalenges;
        this.rank = rank;
        this.currentLevel = currentLevel;
    }

    public String getName() {return name;}

    public int getCurrentLevelScore() {return currentLevelScore;}

    public void setCurrentLevelScore(int currentLevelScore) {this.currentLevelScore = currentLevelScore;}

    public int getCurrentStreak() {return currentStreak;}

    public void setCurrentStreak(int currentStreak) {this.currentStreak = currentStreak;}

    public int getTotalPoints() {return totalPoints;}

    public void setTotalPoints(int totalPoints) {this.totalPoints = totalPoints;}

    public void setCurrentLevel(int currentLevel) {this.currentLevel = currentLevel;}

    public int getCompletedChalenges() {return completedChalenges;}

    public void setCompletedChalenges(int completedChalenges) {this.completedChalenges = completedChalenges;}

    public Rank getRank() {return rank;}

    public void setRank(Rank rank) {this.rank = rank;}

    public int getCurrentLevel(){
        return this.currentLevel;
    }


}
