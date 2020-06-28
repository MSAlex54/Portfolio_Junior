package players;

import game.GameBoard;

public class GamePlayerHuman extends GamePlayer {

    public GamePlayerHuman (GameBoard board){
        super(getCustomPlayerSign(), true, board);
    }

    private static char getCustomPlayerSign(){
        return 'X';
    }
}
