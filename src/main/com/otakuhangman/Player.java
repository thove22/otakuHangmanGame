package main.com.otakuhangman;

public class Player {
    private String name;
    private int score;
    private Rank rank;
    private int currentLevel;

    public Player(String name){
        this(name, 0, Rank.INICIANTE,0);
    }
    public Player(String name, int score, Rank rank, int currentLevel){
        this.name = name;
        this.score = score;
        this.rank = rank;
        this.currentLevel = currentLevel;
    }

    public String getName(){
        return this.name;
    }
    public int getScore(){
        return this.score;
    }
    public Rank getRank(){
        return this.rank;
    }
    public int getCurrentLevel(){return this.currentLevel;}

    public void setName(String name){
        this.name = name;
    }
    private void addPoints(int points){
        this.score += points;
        updateRank(score);
    }
    private void updateLevel(){

    }
    private void updateRank(int score){
        if(score < 300){
            this.rank = Rank.INICIANTE;
        }else if(score < 600){
            this.rank = Rank.NUTELLA;
        }else if(score < 1200){
            this.rank = Rank.MID_OTAKU;
        }else{
            this.rank = Rank.ADVANCED_OTAKU;
        }else{
            this.rank = Rank.GOD_OTAKU;
        }
    }
}
