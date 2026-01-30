package main.com.otakuhangman.gui.screens;

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
    public static String paint(){
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


}
