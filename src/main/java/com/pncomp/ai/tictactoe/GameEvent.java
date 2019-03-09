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

    public int getPlayer() {
        return player;
    }

    private int player;

    public GameEvent(Object sender, BoardState state, int player) {
        this.sender = sender;
        this.state = state;
        this.player = player;
    }
}
