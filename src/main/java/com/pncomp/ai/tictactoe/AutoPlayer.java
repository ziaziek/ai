package com.pncomp.ai.tictactoe;


import com.pncomp.ai.tictactoe.retriers.RandomRetrier;
import com.pncomp.ai.tictactoe.retriers.Retrier;


public class AutoPlayer implements PlayerInput{

    public Retrier getRetrier() {
        return retrier;
    }

    private final Retrier retrier;
    private final int boardSize;

    public AutoPlayer(final int boardSize) {
        this.retrier = new RandomRetrier(boardSize * boardSize);
        this.boardSize=boardSize;
    }


    @Override
    public String readInput() {
        try {
            int[] p = CoordinatesConverter.convertPositionToCoordinates(retrier.newPosition(), boardSize);
            String pstr = "";
            for ( int i = 0 ; i< p.length; i++){
                pstr+=p[i]+",";
            }
            return pstr.substring(0, pstr.length()-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
