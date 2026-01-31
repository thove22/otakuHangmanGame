package main.com.otakuhangman.gui.utils;

public final class AsciiArt {
    private AsciiArt(){}

    public static  String TITLE(){
        return """     
                ░█████╗░████████╗░█████╗░██╗░░██╗██╗░░░██╗  ██╗░░██╗░█████╗░███╗░░██╗░██████╗░███╗░░░███╗░█████╗░███╗░░██╗
                ██╔══██╗╚══██╔══╝██╔══██╗██║░██╔╝██║░░░██║  ██║░░██║██╔══██╗████╗░██║██╔════╝░████╗░████║██╔══██╗████╗░██║
                ██║░░██║░░░██║░░░███████║█████═╝░██║░░░██║  ███████║███████║██╔██╗██║██║░░██╗░██╔████╔██║███████║██╔██╗██║
                ██║░░██║░░░██║░░░██╔══██║██╔═██╗░██║░░░██║  ██╔══██║██╔══██║██║╚████║██║░░╚██╗██║╚██╔╝██║██╔══██║██║╚████║
                ╚█████╔╝░░░██║░░░██║░░██║██║░╚██╗╚██████╔╝  ██║░░██║██║░░██║██║░╚███║╚██████╔╝██║░╚═╝░██║██║░░██║██║░╚███║
                ░╚════╝░░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝░╚═════╝░  ╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚══╝░╚═════╝░╚═╝░░░░░╚═╝╚═╝░░╚═╝╚═╝░░╚══╝
                """;
    }
    public static String PAINT(){
        return """
                .   .       .           .        .         .           .           .     .    *      .      *       *   .     \\'/                    
                  .      .      .          .           .     .         .        .       .          ___     *   *     .   .  -= * =-                                                             
                  .      .      *           .       .          .                       .     ~*  .'`  _\\     .    *  .        /.\\            
                                 .       .   . *       * *       .       .      .               /  (o/   *     .    . .     .   *   .            
                  .       ____     .      . .     .     .     *        *    .        *    *    |     _\\     *  *  .    *     *     .           
                         <WW>>>         .        .      .    .       .       .          . .     \\  '==. * *    .      .   *     (  .             
                 .   .  /WWWI; \\  .       .    .  ____               .            ."   .   *     '.____\\   ~*   .    .  *    *    .
                  *    /WWWWII; \\=====;    .     /WI; \\   *    .        /\\_             .     .     *       *      *   *   )     .
                  .   /WWWWWII;..      \\_  . ___/WI;:. \\     .        _/M; \\    .   .         .      .         .    .   .     .  .
                     /WWWWWIIIIi;..      \\__/WWWIIII:.. \\____ .   .  /MMI:  \\   * .        .          .      *  .  .  .     *     .              
                 . _/WWWWWIIIi;;;:...:   ;\\WWWWWWIIIII;.     \\     /MMWII;   \\    .  .     .                /.\\
                  /WWWWWIWIiii;;;.:.. :   ;\\WWWWWIII;;;::     \\___/MMWIIII;   \\              .             /'.'\\    .    .     .   . 
                 /WWWWWIIIIiii;;::.... :   ;|WWWWWWII;;::.:      :;IMWIIIII;:   \\___     *                /.''.'\\         .   .     .
                /WWWWWWWWWIIIIIWIIii;;::;..;\\WWWWWWIII;;;:::...    ;IMIII;;     ::  \\     .              /.'.'  .\\  .       .   .    
                WWWWWWWWWIIIIIIIIIii;;::.;..;\\WWWWWWWWIIIII;;..  :;IMIII;:::     :    \\           "'""\""/'.''.'  .\\""'"'"     . 
                WWWWWWWWWWWWWIIIIIIii;;::..;..;\\WWWWWWWWIIII;::; :::::::::.....::       \\            thx ^^^[_]^^^ .      .    .   . 
                %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX%%%%%%%%%%%%%%
                %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX%%%%%%%%%%%%%%%%
                %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX%%%%%%%%%%%%%%%%%%%
                %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX%%%%%%%%%%%%%%%%%%%%%%
                """;
    }



    public static String drawBoxAscii(String content){
        String[] lines = content.split("\n");
        int maxLen = 0;

        for (String line : lines) {
            maxLen = Math.max(maxLen, line.length());
        }
        StringBuilder sb = new StringBuilder();
        String edge = "+" + "-".repeat(maxLen + 4) + "+";
        sb.append(edge).append("\n");
        for (String line : lines){
            int padding = maxLen - line.length();
            sb.append("|  ").append(line).append(" ".repeat(padding)).append("  |\n");
        }
        sb.append(edge);
        return sb.toString();
    }

    public static String MENUTITLE(){
        return """
                 _____ _____ ___   _   ___   _    _   _   ___   _   _ _____ ___  ___  ___   _   _\s
                |  _  |_   _/ _ \\ | | / / | | |  | | | | / _ \\ | \\ | |  __ \\|  \\/  | / _ \\ | \\ | |
                | | | | | |/ /_\\ \\| |/ /| | | |  | |_| |/ /_\\ \\|  \\| | |  \\/| .  . |/ /_\\ \\|  \\| |
                | | | | | ||  _  ||    \\| | | |  |  _  ||  _  || . ` | | __ | |\\/| ||  _  || . ` |
                \\ \\_/ / | || | | || |\\  \\ |_| |  | | | || | | || |\\  | |_\\ \\| |  | || | | || |\\  |
                 \\___/  \\_/\\_| |_/\\_| \\_/\\___/   \\_| |_/\\_| |_/\\_| \\_/\\____/\\_|  |_/\\_| |_/\\_| \\_/
                
                """;
    }

}
