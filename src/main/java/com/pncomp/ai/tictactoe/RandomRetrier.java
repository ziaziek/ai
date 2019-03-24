package com.pncomp.ai.tictactoe;

import java.util.Random;

public class RandomRetrier implements Retrier {

    protected Random random = new Random();

    protected final GameManager gm;

    public RandomRetrier(GameManager gm){
        this.gm=gm;
    }
    @Override
    public int newPosition() throws Exception {
        return makeRandomMove(gm.getBoard().size());
    }

    protected int makeRandomMove(int boardSize) {
        return  random.nextInt(boardSize);
    }
}
