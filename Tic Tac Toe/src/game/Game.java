package game;

import players.GamePlayer;
import players.GamePlayerAi;
import players.GamePlayerHuman;

import javax.swing.*;

public class Game {
    private GameBoard board;
    private GamePlayer[] gamePlayers = new GamePlayer[2];
    private int playersTurn = 0;

    public Game(){
        this.board = new GameBoard(this);
    }

    public void initGame(){
        gamePlayers[0] = new GamePlayerHuman(board);
        gamePlayers[1] = new GamePlayerAi(board);
    }

    public void passTurn(){
        playersTurn = playersTurn == 0 ? 1:0;
    }

    void newGame(){
        playersTurn = 0;
    }

    public GamePlayer getCurrentGamePlayer(){
        return gamePlayers[playersTurn];
    }

    public void showMessage(String messageText){
        JOptionPane.showMessageDialog(board,messageText);
    }
}
