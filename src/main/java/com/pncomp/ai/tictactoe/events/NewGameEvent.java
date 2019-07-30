package com.pncomp.ai.tictactoe.events;

public class NewGameEvent {

    public int getBoardSize() {
        return boardSize;
    }

    private int boardSize;

    public NewGameEvent(int boardSize){
        this.boardSize=boardSize;
    }
}
