package com.pncomp.ai.tictactoe;


import com.pncomp.ai.tictactoe.retriers.RandomRetrier;
import com.pncomp.ai.tictactoe.retriers.Retrier;

public class AutoPlayer implements PlayerInput{

    private final Retrier retrier;
    protected final int boardSize;

    public AutoPlayer(final int boardSize) {
        this.retrier = new RandomRetrier(boardSize * boardSize);
        this.boardSize=boardSize;
    }

    @Override
    public String readInput() {
        try {
            int[] p = CoordinatesConverter.convertPositionToCoordinates(retrier.newPosition(), boardSize);
            return  getConvertedPosition(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getConvertedPosition(int[] p){
        String pstr = "";
        for ( int i = 0 ; i< p.length; i++){
            pstr+=p[i]+",";
        }
        return pstr.substring(0, pstr.length()-1);
    }
}
