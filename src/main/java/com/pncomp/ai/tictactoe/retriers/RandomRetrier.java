package com.pncomp.ai.tictactoe.retriers;

import com.pncomp.ai.Settings;
import com.pncomp.ai.tictactoe.GameManager;

import java.util.Random;

public class RandomRetrier implements Retrier {

    protected Random random = new Random();

    protected final int boardLength;

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    protected Settings settings;

    public RandomRetrier(int boardLength){
        this.boardLength=boardLength;
    }

    @Override
    public int newPosition() throws Exception {
        return makeRandomMove(boardLength);
    }

    protected int makeRandomMove(int boardSize) {
        return  random.nextInt(boardSize);
    }
}
