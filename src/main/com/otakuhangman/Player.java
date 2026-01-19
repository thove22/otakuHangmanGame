package main.com.otakuhangman;

public class Player {
    private String name;
    private int currentLevelScore;
    private int currentStreak;
    private int totalPoints;
    private int currentLevel;
    private int completedChallenges;
    private Rank rank;

    public  Player(String name){
        this(name, 1, 0, 0, 0, 0, Rank.OTAKU_INICIANTE);
    }

    public Player(String name,int currentLevelScore, int currentStreak , int totalPoints,
                  int currentLevel, int completedChallenges, Rank rank){
        this.name = name;
        this.currentLevelScore = currentLevelScore;
        this.currentStreak = currentStreak;
        this.totalPoints = totalPoints;
        this.completedChallenges = completedChallenges;
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
    public int getCompletedChallenges() {return completedChallenges;}
    public void setCompletedChallenges(int completedChalenges) {this.completedChallenges = completedChallenges;}
    public Rank getRank() {return rank;}
    public void setRank(Rank rank) {this.rank = rank;}
    public int getCurrentLevel(){return this.currentLevel;}

    int processChallenge(int errors){
        int points = 0;

        if (errors < 0 || errors > 6){
            throw new IllegalArgumentException(
                    "Numero de Erros Invalidos: " + errors + ". O valor deve estar entre 0 e 6"
            );
        }

        if (errors == 6){
            currentStreak = 0;
            return 0;
        }

        points = ScoreCalculator.calculateFinalPoints(errors , currentStreak);
        currentLevelScore += points;
        completedChallenges++;
        totalPoints += points;

        if(errors == 0){
            currentStreak++;
        }else {
            currentStreak  = 0;
        }
        updateRank();
        return points;
    }
     void updateRank(){
        Rank newRank = determineRankFromPoints(totalPoints);

        if(this.rank != newRank){
            this.rank = newRank;
            notifyRankup(newRank);
        }
    }

     Rank determineRankFromPoints(int points){
        if (points >= Rank.OTAKU_NUTELLA.getRequiredPoints()) return  Rank.OTAKU_NUTELLA;
        if (points >= Rank.MID_OTAKU.getRequiredPoints()) return Rank.MID_OTAKU;
        if (points >= Rank.ADVANCED_OTAKU.getRequiredPoints()) return Rank.ADVANCED_OTAKU;
        if (points >= Rank.GOD_OTAKU.getRequiredPoints()) return Rank.GOD_OTAKU;

        return Rank.OTAKU_INICIANTE;
    }
    void advanceToNextLevel(){
        currentLevel++;
        currentLevelScore = 0;
        currentStreak = 0;
        completedChallenges = 0;
    }

    void notifyRankup(Rank newRank){
        String message  = switch (newRank){
            case OTAKU_INICIANTE -> "Hajimemashite! Você é um Otaku Iniciante! (◕‿◕✿)";
            case OTAKU_NUTELLA -> "Yatta! Você subiu para Otaku Nutella! Continue assim! (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧";
            case MID_OTAKU -> "Sugoi! Mid Otaku desbloqueado! Seu poder está crescendo! ᕙ(⇀‸↼‶)ᕗ";
            case ADVANCED_OTAKU -> "Kakkoii! Advanced Otaku alcançado! Você tá evoluindo! (•̀ᴗ•́)و ̑̑";
            case GOD_OTAKU -> "MASAKA! God Otaku conquistado! Você é imbatível! ୧(﹒︠ᴗ﹒︡)୨";
        };
        System.out.println(message);
    }
}
