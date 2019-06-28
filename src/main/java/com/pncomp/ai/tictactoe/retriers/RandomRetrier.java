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

    private List<Integer> freePlaces;

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    protected Settings settings;

    public RandomRetrier(int boardLength, List<Integer> fp){
        this.boardLength=boardLength;
        freePlaces=fp;
    }

    @Override
    public int newPosition() throws Exception {
        return makeRandomMove(boardLength);
    }

    @Subscribe
    private void handleGameEvent(final GameEvent event){
        freePlaces= LogicHelper.getFreePlaces(event.getState().getBoard());
    }

    @Subscribe
    private void handleNewGame(final NewGameEvent event){
        freePlaces=new ArrayList<>();
        for(int i=0; i<event.getBoardSize(); i++){
            freePlaces.add(i);
        }
    }

    protected int makeRandomMove(int boardSize) {
        Integer p = freePlaces.get(random.nextInt(freePlaces.size()));
        freePlaces.remove(p);
        return p;
    }
}
