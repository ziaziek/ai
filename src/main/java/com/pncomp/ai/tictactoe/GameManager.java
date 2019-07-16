package com.pncomp.ai.tictactoe;

import com.pncomp.ai.Settings;
import com.pncomp.ai.tictactoe.events.EventBusFactory;
import com.pncomp.ai.tictactoe.events.GameEvent;
import com.pncomp.ai.tictactoe.events.GameOverEvent;
import com.pncomp.ai.tictactoe.retriers.Retrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager {

    public static final int DEFAULT_BOARD_SIZE=3;

    public String getEventBusName() {
        return eventBusName;
    }

    private Settings settings;

    public void setEventBusName(String eventBusName) {
        this.eventBusName = eventBusName;
    }

    private String eventBusName=null;

    public long getMaxDecisionTreeNodes() {
        return maxDecisionTreeNodes;
    }

    private final long maxDecisionTreeNodes;

    public List<Integer> getBoard() {
        List<Integer> r = new ArrayList<>();
        for(int p: board){
            r.add(p);
        }
        return Collections.unmodifiableList(r);
    }

    protected int[] board;

    public boolean isGameOver() {
        return gameOver;
    }

    private boolean gameOver;

    public static final int TIC = -1;
    public static final int TAC = 1;

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    //TODO: may be better to create a player class which would hold the symbol. Some better associaten between the player and symbol
    public int getCurrentPlayerSymbol(){
        return getCurrentPlayer()==0? TIC:TAC;
    }

    private int currentPlayer;

    public int getWinningPlayer() {
        return winningPlayer;
    }

    private int winningPlayer = -1;
    private int size=DEFAULT_BOARD_SIZE;
    private int nPlayers=2;

    public GameManager(Settings settings){
        this(settings, DEFAULT_BOARD_SIZE);
    }

    public GameManager(Settings settings, final int size){
        initBoard(size);
        this.size=size;
        this.maxDecisionTreeNodes=countMaxAllAvailableNodes(size);
        this.settings=settings;
    }

    private void initBoard(int size){
        board = new int[size*size];
    }

    public boolean placeSymbol(int symbol, int place){
        if(canPlaceSymbol(place)){
            board[place]=symbol;
            if (!boardWillBeFull() && !isGameWon(symbol, place)) {
                nextPlayer(symbol, place);
                LogicHelper.getFreePlaces().remove(LogicHelper.getFreePlaces().indexOf(place));
            } else {
                doGameOver(symbol, place);
            }
            return true;
        } else {
            return false;
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
            b.append("\n");
            b.append("-+-+-\n");
        }
        System.out.println(b.toString());
    }

    private void doGameOver(int symbol, int place) {
        gameOver=true;
        if(!settings.isLearnSelf()){
            System.out.println("Game over.");
        }
        EventBusFactory.getEventBus(eventBusName).post(new GameOverEvent(this, new BoardState(place, symbol, getBoard()), currentPlayer));
    }

    private void nextPlayer(int symbol, int place) {
        EventBusFactory.getEventBus(eventBusName).post(new GameEvent(this, new BoardState(place, symbol, getBoard()), currentPlayer));
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

    public int convertCoordinates(int x, int y) throws CoordinatesException {
        return CoordinatesConverter.convertCoordinates(x, y, size);
    }


    private boolean isGameWon(final int symbol, final int place) {
        boolean p = sameSymbolInRow(symbol, place) || sameSymbolInColumn(symbol, place) || sameSymbolDiagonal(symbol, place);
        if (p) {
            winningPlayer = currentPlayer;
            if(!settings.isLearnSelf()){
                System.out.println("Player " + winningPlayer + " wins.");
            }
        }
        return p;
    }

    private boolean sameSymbolDiagonal(int symbol, int place) {
        //Done for main diagonals only. May need to be reworked for larger boards.
            int ix=0;
        boolean p = true, q = true;
        while (ix < size) {
                try {
                    p = p && board[convertCoordinates(ix, ix)] == symbol;
                    q = q && board[convertCoordinates(ix, size - ix - 1)] == symbol;
                } catch (CoordinatesException e) {
                    e.printStackTrace();
                    p=true;
                    q=true;
                }
                ix++;
            }
        return p || q;
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
            int crc = byRow?convertCoordinates(ix, rc): convertCoordinates(rc, ix);
            p = board[crc] != symbol;
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

    private boolean boardWillBeFull() {
        return getBoard().stream().filter(x -> x != 0).count() == size * size;
    }

    public boolean isDraw() {
        return isGameOver() && boardWillBeFull() && winningPlayer == -1;
    }

    public void tryPlacingSymbol(final int player, final Retrier retrier) throws Exception {
        while (player == getCurrentPlayer() && !isGameOver()) {
            placeSymbol(getCurrentPlayerSymbol(), retrier.newPosition());
        }
    }

    private long countMaxAllAvailableNodes(int boardSize){
        //for 3 it's above 900 000
        return 900000;
    }
}
