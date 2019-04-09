package com.pncomp.ai.tictactoe;


import com.pncomp.ai.tictactoe.retriers.RandomRetrier;
import com.pncomp.ai.tictactoe.retriers.Retrier;

public class AutoPlayer implements PlayerInput{

    private final Retrier retrier;
    private final GameManager gameManager;
    private final int boardSize;

    public AutoPlayer(final GameManager gameManager) {
        this.retrier = new RandomRetrier(gameManager);
        this.gameManager=gameManager;
        boardSize = (int) Math.sqrt(gameManager.getBoard().size());
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
