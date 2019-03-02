package com.pncomp.ai.tictactoe;


public class GameOverEvent extends GameEvent {



    public int getWinningPlayer() {
        return winningPlayer;
    }

    private int winningPlayer;

    public GameOverEvent(Object sender, BoardState state,int winningPlayer) {
        super(sender, state);
        this.winningPlayer=winningPlayer;
    }

}
