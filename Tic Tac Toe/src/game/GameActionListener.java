package game;

import players.GamePlayerAi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameActionListener implements ActionListener {
    private int row;
    private int col;
    private GameButton button;

    public GameActionListener(int row, int col, GameButton button) {
        this.row = row;
        this.col = col;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();
        if (board.isTurnable(col,row)){
            updateByPlayersData(board);
            if (board.checkWin()){
                board.getGame().showMessage("Winner is Player !");
                board.emptyField();
            } else {
                if (board.isFull()){
                    board.getGame().showMessage("Draw");
                    board.emptyField();
                } else {
                    board.getGame().passTurn();
                    GamePlayerAi.updateByAiData(board);
                }
            }
        } else{
            board.getGame().showMessage("Wrong move");
        }

    }

    private void updateByPlayersData(GameBoard board) {
        board.updateGameField(col,row);
        button.setText(Character.toString(board.getGame().getCurrentGamePlayer().getPlayerSign()));
    }


}
