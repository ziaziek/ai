package com.pncomp.ai.tictactoe;

import com.google.common.eventbus.Subscribe;
import com.pncomp.ai.tictactoe.events.NewGameEvent;

public class InitialPositionAutoPlayer extends AutoPlayer {


    public int getInitialPositionField() {
        return initialPositionField;
    }

    private final int initialPositionField;
    private boolean firstMove;

    public InitialPositionAutoPlayer(final int boardSize, int positionField, final String eventBusName){
        super(boardSize);
        this.initialPositionField=positionField;
    }

    @Override
    public String readInput(){
        if(firstMove){
            firstMove = false;
            return getConvertedPosition(CoordinatesConverter.convertPositionToCoordinates(initialPositionField, boardSize));
        } else {
            return super.readInput();
        }
    }

    @Subscribe
    public void restartPlayerToFirstMove(NewGameEvent event){
        firstMove=true;
    }
}
