package main.com.otakuhangman;

public enum Rank {
    OTAKU_INICIANTE(0),
    OTAKU_NUTELLA(1000),
    MID_OTAKU(3000),
    ADVANCED_OTAKU(5000),
    GOD_OTAKU(9500);

    private final int requiredPoints;

    Rank(int requiredPoints){
        this.requiredPoints = requiredPoints;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }
}
