package players;

import game.GameBoard;

public abstract class GamePlayer {
    private char playerSign;
    private boolean realPlayer;
    private GameBoard board;

    public GamePlayer() {
    }

    public GamePlayer(char playerSign, boolean realPlayer,GameBoard board){
        this.playerSign = playerSign;
        this.realPlayer = realPlayer;
        this.board = board;
    }

    public boolean isRealPlayer(){
        return this.realPlayer;
    }

    public char getPlayerSign() {
        return this.playerSign;
    }

    public GameBoard getBoard() {
        return board;
    }
}
