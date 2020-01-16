package com.pncomp.ai.tictactoe;

public class CoordinatesException extends Exception {

    @Override
    public String getMessage() {
        String message = "Coordinates do not fit the size of the board.";
        return message;
    }

}
