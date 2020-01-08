package com.pncomp.ai.tictactoe;

public class CoordinatesException extends Exception {

    private String message = "Coordinates do not fit the size of the board.";

    @Override
    public String getMessage() {
        return message;
    }

}
