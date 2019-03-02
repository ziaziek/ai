package com.pncomp.ai.tictactoe;

public class GameEvent {

    public Object getSender() {
        return sender;
    }

    public BoardState getState() {
        return state;
    }

    private Object sender;
    private BoardState state;

    public GameEvent(Object sender, BoardState state) {
        this.sender = sender;
        this.state = state;
    }
}
