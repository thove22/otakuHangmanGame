package main.com.otakuhangman;

public final class HangmanArt {
    private HangmanArt(){
    }
    public static  final String[] STAGES = {
            """
         +---+
         |   |
             |
             |
             |
             |
        =======
        """,
            """
         +---+
         |   |
         O   |
             |
             |
             |
        =======
        """,
            """
         +---+
         |   |
         O   |
         |   |
             |
             |
        =======
        """,
            """
         +---+
         |   |
         O   |
        /|   |
             |
             |
        =======
        """,
            """
         +---+
         |   |
         O   |
        /|\\  |
             |
             |
        =======
        """,
            """
         +---+
         |   |
         O   |
        /|\\  |
        /    |
             |
        =======
        """,
            """
         +---+
         |   |
         O   |
        /|\\  |
        / \\  |
             |
        =======
        """
    };
}
