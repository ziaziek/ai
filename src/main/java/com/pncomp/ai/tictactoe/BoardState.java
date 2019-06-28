package com.pncomp.ai.tictactoe;

import java.util.List;

public class BoardState {

    private int position;

    public int getPosition() {
        return position;
    }

    public int getSymbol() {
        return symbol;
    }

    private int symbol;

    public List<Integer> getBoard() {
        return board;
    }

    private List<Integer> board;

    public BoardState(int position, int symbol, final List<Integer> board) {
        this.position = position;
        this.symbol = symbol;
        this.board=board;
    }
}
