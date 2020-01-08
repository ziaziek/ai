package com.pncomp.ai.tictactoe;

public class BoardState {

    private int position;

    public int getPosition() {
        return position;
    }

    public int getSymbol() {
        return symbol;
    }

    private int symbol;

    public BoardState(int position, int symbol) {
        this.position = position;
        this.symbol = symbol;
    }
}
