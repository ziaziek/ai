package com.pncomp.ai.tictactoe;

public class CoordinatesConverter {

    public static int convertCoordinates(int x, int y,int size) throws CoordinatesException {
        if(x<size && y<size){
            return x + y* size;
        } else {
            throw new CoordinatesException();
        }
    }

    public static int[] convertPositionToCoordinates(int position, int size){

        int[] pos = new int[2];
        pos[1]=position/size;
        pos[0]=position%size;
        return pos;
    }
}
