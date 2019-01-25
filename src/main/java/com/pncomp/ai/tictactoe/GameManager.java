package com.pncomp.ai.tictactoe;

public class GameManager {

    private int[] board;

    public static final int TIC = 1;
    public static final int TAC = -1;
    private int currentPlayer;
    private int size=3;

    public GameManager(){
        board = new int[9];
    }

    public GameManager(final int size){
        board = new int[size*size];
        this.size=size;
    }

    private boolean canPlaceSymbol(final int place){
        return board[place]==0;
    }

    private boolean canPlaceSymbol(final int x, final int y) throws CoordinatesException {
        return canPlaceSymbol(convertCoordinates(x,y));
    }

    private int convertCoordinates(int x, int y) throws CoordinatesException {
        if(x<size && y<size){
            return x + y* board.length;
        } else {
            throw new CoordinatesException();
        }
    }
    
    private boolean isGameOver(final int symbol, final int place){
        return sameSymbolInRow(symbol, place) || sameSymbolInColumn(symbol, place) || sameSymbolDiagonal(symbol, place);
    }

    private boolean sameSymbolDiagonal(int symbol, int place) {
        //Done for main diagonals only. May need to be reworked for larger boards.
        if(place%size==place/size){
            int ix=0;
            boolean p=false;
            while(ix<size*size && !p){
                p=board[ix]!=symbol;
                ix+=size;
            }
            return p;
        } else {
            return false;
        }
    }

    private boolean sameSymbolInColumn(int symbol, int place) {
        try {
            return isWinCriteria(symbol,place%size , false);
        } catch (CoordinatesException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isWinCriteria(int symbol, int rc, boolean byRow) throws CoordinatesException {
        int ix=0;
        boolean p=false;
        while (ix < size && !p) {
            p = board[byRow?convertCoordinates(ix, rc): convertCoordinates(rc, ix)] != symbol;
            ix++;
        }
        return p;
    }

    private boolean sameSymbolInRow(int symbol, int place) {
        try {
            return isWinCriteria(symbol, place/size, true);
        } catch (CoordinatesException e) {
            e.printStackTrace();
        }
        return false;
    }
}
