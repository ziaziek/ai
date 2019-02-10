package com.pncomp.ai.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager {

    public List<Integer> getBoard() {
        List<Integer> r = new ArrayList<>();
        for(int p: board){
            r.add(p);
        }
        return Collections.unmodifiableList(r);
    }

    private int[] board;

    public boolean isGameOver() {
        return gameOver;
    }

    private boolean gameOver;

    public static final int TIC = 1;
    public static final int TAC = -1;

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    private int currentPlayer;
    private int size=3;
    private int nPlayers=2;

    public GameManager(){
        board = new int[9];
    }

    public GameManager(final int size){
        board = new int[size*size];
        this.size=size;
    }

    public void placeSymbol(int symbol, int place){
        if(canPlaceSymbol(place)){
            board[place]=symbol;
            if(!isGameOver(symbol, place)){
                nextPlayer();
            } else {
                doGameOver();
            }
        }
    }

    public void printOutBoard(){
        final StringBuilder b = new StringBuilder();
        for(int i = 0; i<size; i++){
            for(int j=0; j<size; j++){
                try {
                    b.append(getBoard().get(convertCoordinates(i,j))).append("|");
                } catch (CoordinatesException e) {
                    e.printStackTrace();
                }
            }
            b.append("-+-+-");
        }
        System.out.println(b.toString());
    }

    private void doGameOver() {
        gameOver=true;
        System.out.println("Game over. Player "+currentPlayer+" wins.");
    }

    private void nextPlayer() {
        currentPlayer=(currentPlayer+1)%nPlayers;
    }

    public void placeSymbol(int symbol, int x, int y){
        try {
            placeSymbol(symbol, convertCoordinates(x,y));
        } catch (CoordinatesException e) {
            e.printStackTrace();
        }
    }

    private boolean canPlaceSymbol(final int place){
        return !gameOver && board[place]==0;
    }

    private boolean canPlaceSymbol(final int x, final int y) throws CoordinatesException {
        return canPlaceSymbol(convertCoordinates(x,y));
    }

    private int convertCoordinates(int x, int y) throws CoordinatesException {
        if(x<size && y<size){
            return x + y* size;
        } else {
            throw new CoordinatesException();
        }
    }
    
    private boolean isGameOver(final int symbol, final int place){
        return sameSymbolInRow(symbol, place) || sameSymbolInColumn(symbol, place) || sameSymbolDiagonal(symbol, place);
    }

    private boolean sameSymbolDiagonal(int symbol, int place) {
        //Done for main diagonals only. May need to be reworked for larger boards.
            int ix=0;
            boolean p=false, q = false;
            while(ix<size && !(p && q)){
                try {
                    p=board[convertCoordinates(ix, ix)]!=symbol;
                    q = board[convertCoordinates(ix, size-ix-1)]!=symbol;
                } catch (CoordinatesException e) {
                    e.printStackTrace();
                    p=true;
                    q=true;
                }
                ix++;
            }
            return !(p && q);
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
        return !p;
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
