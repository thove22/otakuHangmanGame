package main.com.otakuhangman;

import java.util.List;

public final class GameData {
    private GameData(){}

    static List<Level> createLevels(){
        return List.of(
                createLevel1(),
                createLevel2(),
                createLevel3(),
                createLevel4(),
                createLevel5(),
                createLevel6(),
                createLevel7(),
                createLevel8(),
                createLevel9(),
                createLevel10()
        );
    }
    private static Level createLevel1(){
        List<Challenge> challenges = List.of(
                new Challenge("NARUTO", "Ninja loiro de Konoha", "Anime", true),
                new Challenge("GOKU", "Saiyajin criado na Terra", "Anime", false),
                new Challenge("Luffy", "Capitão dos Chapéus de Palha", "Anime", false)
        );
        return  new Level(1,challenges);
    }
    private static Level createLevel2(){
        List<Challenge> challenges = List.of(
                new Challenge("DEATHNOTE", "Caderno mortal", "Anime", false),
                new Challenge("BLEACH", "Shinigamis e Hollows", "Anime", false),
                new Challenge("ATTACKONTITAN", "Titãs", "Anime", false)

        );
        return  new Level(2,challenges);
    }
    private static Level createLevel3(){
        List<Challenge> challenges = List.of(
                new Challenge("DEATHNOTE", "Caderno mortal", "Anime", false),
                new Challenge("BLEACH", "Shinigamis e Hollows", "Anime", false),
                new Challenge("ATTACKONTITAN", "Titãs", "Anime", false)

        );
        return  new Level(3,challenges);
    }
    private static Level createLevel4(){
        List<Challenge> challenges = List.of(
                new Challenge("DEATHNOTE", "Caderno mortal", "Anime", false),
                new Challenge("BLEACH", "Shinigamis e Hollows", "Anime", false),
                new Challenge("ATTACKONTITAN", "Titãs", "Anime", false)

        );
        return  new Level(4,challenges);
    }
    private static Level createLevel5(){
        List<Challenge> challenges = List.of(
                new Challenge("DEATHNOTE", "Caderno mortal", "Anime", false),
                new Challenge("BLEACH", "Shinigamis e Hollows", "Anime", false),
                new Challenge("ATTACKONTITAN", "Titãs", "Anime", false)

        );
        return  new Level(5,challenges);
    }
    private static Level createLevel6(){
        List<Challenge> challenges = List.of(
                new Challenge("DEATHNOTE", "Caderno mortal", "Anime", false),
                new Challenge("BLEACH", "Shinigamis e Hollows", "Anime", false),
                new Challenge("ATTACKONTITAN", "Titãs", "Anime", false)

        );
        return  new Level(6,challenges);
    }
    private static Level createLevel7(){
        List<Challenge> challenges = List.of(
                new Challenge("DEATHNOTE", "Caderno mortal", "Anime", false),
                new Challenge("BLEACH", "Shinigamis e Hollows", "Anime", false),
                new Challenge("ATTACKONTITAN", "Titãs", "Anime", false)

        );
        return  new Level(7,challenges);
    }
    private static Level createLevel8(){
        List<Challenge> challenges = List.of(
                new Challenge("DEATHNOTE", "Caderno mortal", "Anime", false),
                new Challenge("BLEACH", "Shinigamis e Hollows", "Anime", false),
                new Challenge("ATTACKONTITAN", "Titãs", "Anime", false)

        );
        return  new Level(8,challenges);
    }
    private static Level createLevel9(){
        List<Challenge> challenges = List.of(
                new Challenge("DEATHNOTE", "Caderno mortal", "Anime", false),
                new Challenge("BLEACH", "Shinigamis e Hollows", "Anime", false),
                new Challenge("ATTACKONTITAN", "Titãs", "Anime", false)

        );
        return  new Level(9,challenges);
    }
    private static Level createLevel10(){
        List<Challenge> challenges = List.of(
                new Challenge("DEATHNOTE", "Caderno mortal", "Anime", false),
                new Challenge("BLEACH", "Shinigamis e Hollows", "Anime", false),
                new Challenge("ATTACKONTITAN", "Titãs", "Anime", false)

        );
        return  new Level(10,challenges);
    }
}
