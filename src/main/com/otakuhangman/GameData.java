package main.com.otakuhangman;

import java.util.List;

public final class GameData {
    private GameData(){}

    static List<Level> createLevels(){
        return List.of(
                createLevel1(),
                createLevel2()
        );
    }
    private static Level createLevel1(){
        List<Challenge> challenges = List.of(
                new Challenge("NARUTO", "Ninja loiro de Konoha", "Anime"),
                new Challenge("GOKU", "Saiyajin criado na Terra", "Anime"),
                new Challenge("Luffy", "Capitão dos Chapéus de Palha", "Anime")
        );
        return  new Level(1,challenges);
    }
    private static Level createLevel2(){
        List<Challenge> challenges = List.of(
                new Challenge("DEATHNOTE", "Caderno mortal", "Anime"),
                new Challenge("BLEACH", "Shinigamis e Hollows", "Anime"),
                new Challenge("ATTACKONTITAN", "Titãs", "Anime")
        );
        return  new Level(2,challenges);
    }
}
