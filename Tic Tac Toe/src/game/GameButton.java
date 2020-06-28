package game;

import javax.swing.*;

public class GameButton extends JButton {
    private int buttonIndex;
    private GameBoard board;

    public GameButton (int buttonIndex, GameBoard currentGameBoard){
        this.buttonIndex = buttonIndex;
        this.board = currentGameBoard;

        int rowNum = buttonIndex / GameBoard.dimension;
        int colNum = buttonIndex % GameBoard.dimension;

        setSize(GameBoard.cellSize - 5,GameBoard.cellSize - 5);
        addActionListener(new GameActionListener(rowNum,colNum,this));
    }
    public GameBoard getBoard(){
        return board;
    }
}
