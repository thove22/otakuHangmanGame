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
                new Challenge("BUNGEEGUM", "Chiclete + borracha, genialidade hisokana", "Shonen", false),
                new Challenge("KAGEBUNSHIN", "Ctrl+C Ctrl+V versão ninja", "Shonen", false),
                new Challenge("GEARFOURTH", "Modo balão muscular do chapéu de palha", "Shonen", false),
                new Challenge("HINOKAMIKAGURA", "Dança do sol que salva o sen/cos", "Shonen", false),
                new Challenge("BANKAI", "Liberação final dos Shinigamis que acaba os créditos do episódio", "Shonen", false),
                new Challenge("PLUSULTRA", "Lema do herdeiro do One For All", "Shonen", false),
                new Challenge("GENKIDAMA", "Bola de ki mendigada do universo todo", "Shonen", false)
        );
        return  new Level(1,challenges);
    }
    private static Level createLevel2(){
        List<Challenge> challenges = List.of(
                new Challenge("BUSOSHOKU", "Armadura invisível pra bater em Logia", "shonen", false),
                new Challenge("HUMUNCULUS", "Pecado capital com forma humana", "shonen", false),
                new Challenge("BOOGIEWOOGIE", "Troca posições com palmas", "Shonen", false),
                new Challenge("HOUOUIN KYOUMA", "Mad scientist do El Psy Kongroo", "Shonen", false),
                new Challenge("KEKKEIGENKAI", "Herança OP de clã ninja chato", "Shonen", false),
                new Challenge("KAIOKEN", "Boost vermelho que assa o Saiyajin", "Shonen", false),
                new Challenge("KAMUI", "Jutsu que suga pro limbo com olho torto", "Shonen", false)
        );
        return  new Level(2,challenges);
    }
    private static Level createLevel3(){
        List<Challenge> challenges = List.of(
                new Challenge("TATAKAE", "Grito de guerra do titã que perdeu a cabeça (literalmente)", "Seinen/Shonen", false),
                new Challenge("THIRDIMPACT", "Apocalipse onde todo mundo vira suco de laranja coletivo", "Classic Mecha", false),
                new Challenge("UNLIMITEDBLADEWORKS", "O estoque infinito de espadas do arqueiro que não usa arco", "Fantasy", false),
                new Challenge("DRAGONSLAYER", "Uma chapa de ferro bruta demais pra ser chamada de espada", "Seinen", false),
                new Challenge("RETURNBYDEATH", "O checkpoint mais traumático e doloroso dos isekais", "Isekai", false),
                new Challenge("WAKUWAKU", "A onomatopeia da telepata que adora amendoim e caos", "Comedy", false),
                new Challenge("SERIOUSPUNCH", "Soco full power do careca que acaba com o hype do monstro em segundos", "Seinen", false)
        );
        return  new Level(3,challenges);
    }
    private static Level createLevel4(){
        List<Challenge> challenges = List.of(

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
