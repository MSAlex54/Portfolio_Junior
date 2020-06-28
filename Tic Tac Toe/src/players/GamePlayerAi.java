package players;

import game.GameBoard;

public class GamePlayerAi extends GamePlayer {
    private char playerSign;
    private boolean realPlayer;
    private static int [][]gameFieldRate;

    public GamePlayerAi (GameBoard board){
        super('O', false,board);
    }

    public static void updateByAiData(GameBoard board) {
        int col, row, cell;

//        //random AI
//        Random rnd = new Random();
//        do {
//            col = rnd.nextInt(GameBoard.getDimension());
//            row = rnd.nextInt(GameBoard.getDimension());
//        } while (!board.isTurnable(col,row));
//      //end of logic

        //Clever AI
        //rate cells
        gameFieldRate = new int[board.getDimension()][board.getDimension()];
        for (int rw = 0; rw < board.getDimension(); rw++){
            for (int cl = 0; cl < board.getDimension(); cl++){
                int rate;
                if (rw ==1& cl ==1){
                    rate = 4;
                } else if (rw == 1 || cl ==1){
                    rate = 2;
                } else {
                    rate = 3;
                }
                gameFieldRate[rw][cl] = rate;
            }
        }
        cell = getAiMove(board);
        row = cell / board.getDimension();
        col = cell % board.getDimension();
        //end of logic

        board.updateGameField(col,row);

        int cellIndex = board.getDimension()*row + col;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentGamePlayer().getPlayerSign()));

        if (board.checkWin()){
            board.getGame().showMessage("SkyNet has won!");
        } else {
            board.getGame().passTurn();
        }
    }

    private static int getAiMove (GameBoard board){
        int result = 0;
        int max = 0;

        //check for preWin situation if AI almost win, cell will rate +100
        //if player almost win? cell will rate +50
        for (int i = 0; i < board.getDimension(); i++) {
            for (int j = 0; j < board.getDimension(); j++) {
                int second = 0;
                if (!(j+1 ==board.getDimension())){
                    second = j+1;
                }
                int checkPar = 0;
                switch (j){
                    case 0:
                        checkPar = 2;
                        break;
                    case 1:
                        checkPar = 0;
                        break;
                    case 2:
                        checkPar = 1;
                        break;
                }
                //check columns for preWin situation
                if (((!board.isTurnable(i,j)) & ( board.getGameField()[j][i] == board.getGameField()[second][i]))& board.isTurnable(i,checkPar)){
                    gameFieldRate[checkPar][i] += board.getGameField()[j][i] == board.getGame().getCurrentGamePlayer().getPlayerSign() ? 100 : 50;
                }
                //check rows for preWin situation
                if (((!board.isTurnable(j,i)) & (board.getGameField()[i][j] == board.getGameField()[i][second])) & board.isTurnable(checkPar,i)){
                    gameFieldRate[i][checkPar] += board.getGameField()[i][j] == board.getGame().getCurrentGamePlayer().getPlayerSign() ? 100 : 50;
                }
            }
            int second = 0;
            if (!(i+1 ==board.getDimension())){
                second = i+1;
            }
            int checkPar = 0;
            switch (i){
                case 0:
                    checkPar = 2;
                    break;
                case 1:
                    checkPar = 0;
                    break;
                case 2:
                    checkPar = 1;
                    break;
            }
            //check main diagonal for preWin situation
            if (((!board.isTurnable(i,i)) & (board.getGameField()[i][i] == board.getGameField()[second][second]))){
                gameFieldRate[checkPar][checkPar] += board.getGameField()[i][i] == board.getGame().getCurrentGamePlayer().getPlayerSign() ? 100 : 50;
            }
            //check second diagonal for preWin situation
            int inv = 2-i;
            int invSecond = 2 - second;
            if (((!board.isTurnable(i,inv)) & (board.getGameField()[inv][i] == board.getGameField()[invSecond][second]))){
                gameFieldRate[2-checkPar][checkPar] += board.getGameField()[inv][i] == board.getGame().getCurrentGamePlayer().getPlayerSign() ? 100 : 50;
            }
        }

        //check cells rating for the best move (not perfect)
        for (int rw = 0; rw < board.getDimension(); rw++) {
            for (int cl = 0; cl < board.getDimension(); cl++) {
                if (!board.isTurnable(cl,rw)){
                    for (int i = -1; i <=1 ; i++) {
                        for (int j = -1; j<=1; j++) {
                            int checkRow = rw + i;
                            int checkCol = cl + j;
                            if (checkCol >= 0 && checkCol <= 2 && checkRow >= 0 && checkRow <= 2) {
                                if (board.isTurnable(checkCol, checkRow)) {
                                    //rating up for nearest Players (+1) and AI (+2) signs
                                    gameFieldRate[checkRow][checkCol] += board.getGameField()[rw][cl] == board.getGame().getCurrentGamePlayer().getPlayerSign() ? 2 : 1;
                                    if (max < gameFieldRate[checkRow][checkCol]){
                                        max = gameFieldRate[checkRow][checkCol];
                                        result = board.getDimension()*checkRow + checkCol;
                                    } else if (max == gameFieldRate[checkRow][checkCol]) { //if several cells have same rating - randomize chose
                                        int a = (int) (Math.random()*1);
                                        if (a==1) {
                                            max = gameFieldRate[checkRow][checkCol];
                                            result = board.getDimension() * checkRow + checkCol;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
