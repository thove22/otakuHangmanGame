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
                new Challenge("ALABASTA", "Reino arenoso do crocodilo traidor", "Shonen", false),
                new Challenge("MIGI", "O melhor amigo que nasceu na sua mão direita e quer te estudar", "Seinen", false),
                new Challenge("KNIGHTMAREFRAME", "robôs que dão drift e andam de patins no campo de batalha", "Mecha", false),
                new Challenge("BEHELIT", "Ovo facial que ativa o modo eclipse e vira banquete demoníaco", "Seinen", false),
                new Challenge("TRUCKKUN", "O maior serial killer de protagonistas da história dos isekai", "Isekai", false),
                new Challenge("ZETTAIRYOUIKI", "A distância sagrada e absoluta entre a meia alta e a saia", "Anime Trope", false),
                new Challenge("THETRUTH", "Entidade branca que rouba seu corpo por equivalência", "Shounen", false)
        );
        return  new Level(4,challenges);
    }
    private static Level createLevel5(){
        List<Challenge> challenges = List.of(
                new Challenge("ZOLTRAAK", "A magia de ataque 'comum' que era o terror dos demônios há 80 anos", "ISekai", false),
                new Challenge("SKYPEIA", "Ilha nas nuvens onde Deus toca tambor elétrico", "Shonen", false),
                new Challenge("MEGUMIN", "Archmage que só sabe um feitiço e desmaia depois",
                        "Isekai", false),
                new Challenge("MALEVOLENTKITCHEN", "A expansão de domínio do cozinheiro que fatia geral no centro de Shibuya",
                        "Shonen", false),
                new Challenge("AINZ", "O esqueleto que finge ter um plano genial enquanto morre de ansiedade por dentro",
                        "Isekai", false),
                new Challenge("VINLAND", "Terra prometida sem brigas sonho do ex-anão viking raivoso que virou o maior filósofo paz e amor das fazendas",
                        "Seinen", false),
                new Challenge("REIGEN", "Mestre fake que 'exorciza' com sal e papo furado, mas salva o dia com vibe de chefe preguiçoso",
                        "Shonen", false)
        );
        return  new Level(5,challenges);
    }
    private static Level createLevel6(){
        List<Challenge> challenges = List.of(
                new Challenge("HIMMEL", "O herói que morreu no ep 1 mas deixou uma estátua em cada esquina", "Seinen", false),
                new Challenge("AQUA", "A deusa inútil que serve como purificador de água e geradora de dívidas", "Isekai", false),
                new Challenge("HITOGAMI", "A divindade que te dá dicas num vazio branco e sorri como um político", "Isekai", false),
                new Challenge("KENZOTENMA", "O neurocirurgião que aprendeu que nem toda vida merece ser salva", "Seinen", false),
                new Challenge("DEBRIS", "O lixo espacial que os garis do futuro precisam recolher pra evitar desastres orbitais", "Sci-Fi", false),
                new Challenge("CAPSULECORP", "Empresa onde literalmente tudo cabe no bolso", "Shonen", false),
                new Challenge("RYUZAKI", "Pseudônimo do detetive viciado em açúcar", "Seinen", false)

        );
        return  new Level(6,challenges);
    }
    private static Level createLevel7(){
        List<Challenge> challenges = List.of(
                new Challenge("ONIZUKA", "Professor, 22 anos, virgem, faixa preta e fã de dar suplex em diretor", "Classic", false),
                new Challenge("SPEEDWAGON", "A verdadeira 'best waifu' que narra a luta e financia a porradaria geracional", "Seinen", false),
                new Challenge("LAIN", "A garota que confunde cabo de internet com cordão umbilical existencial", "Sci-Fi", false),
                new Challenge("BOOGIEPOP", "A shinigami que surge quando o mundo está desequilibrado e só aparece na timeline certa", "Sci-Fi", false),
                new Challenge("HAIBANE", "Anjos cinzentos que brotam de casulos e pagam pecados esquecidos em paredes velhas", "Fantasy", false),
                new Challenge("GINTOKI", "Samurai que troca a espada por madeira e faz bico pra pagar aluguel", "Sci-Fi", false),
                new Challenge("KAGUYASAMA", "A deusa da lua que decide brincar de 'quem se declara primeiro' num colégio de elite", "Romance", false)
        );
        return  new Level(7,challenges);
    }
    private static Level createLevel8(){
        List<Challenge> challenges = List.of(
                new Challenge("BONDREWD", "O 'Melhor Pai do Ano' que transforma crianças em lancheiras de bateria", "Dark Fantasy", false),
                new Challenge("GRIFFITH", "Líder carismático que troca amigos por upgrade demoníaco e castelo, fanbase ainda defende 'ele só queria voar", "Seinen", false),
                new Challenge("WIRED", "ede cibernética vintage onde egos se dissolvem em delírios coletivos e todo mundo vira deus online sem logout", "Sci-Fi", false),
                new Challenge("MAKISHIMA", "O filósofo criminoso que hackeia mentes pra provar que livre arbítrio não existe", "SCI-FI", false),
                new Challenge("LELOUCH", "O príncipe exilado que virou terrorista com olho hipnótico pra salvar a irmã", "Mecha", false),
                new Challenge("MERUEM", "Rei formiga overpowered que vira filósofo softie jogando Gungi com cega e descobrindo feels humanos inesperados", "Shonen", false),
                new Challenge("HORIMIYA", "A popular escondendo que é doméstica e o emo escondendo piercing e tattoo", "Romance", false)
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
