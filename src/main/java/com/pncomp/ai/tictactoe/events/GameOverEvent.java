package com.pncomp.ai.tictactoe.events;


import com.pncomp.ai.tictactoe.BoardState;
import com.pncomp.ai.tictactoe.events.GameEvent;

public class GameOverEvent extends GameEvent {



    public int getWinningPlayer() {
        return winningPlayer;
    }

    private int winningPlayer;

    public GameOverEvent(Object sender, BoardState state, int winningPlayer) {
        super(sender, state, winningPlayer);
        this.winningPlayer=winningPlayer;
    }

}
