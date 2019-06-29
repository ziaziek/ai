package com.pncomp.ai.tictactoe.retriers;

import com.google.common.eventbus.Subscribe;
import com.pncomp.ai.Settings;
import com.pncomp.ai.tictactoe.GameManager;
import com.pncomp.ai.tictactoe.LogicHelper;
import com.pncomp.ai.tictactoe.events.GameEvent;
import com.pncomp.ai.tictactoe.events.NewGameEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        Integer p = LogicHelper.getFreePlaces().get(random.nextInt(LogicHelper.getFreePlaces().size()));
        return p;
    }
}
