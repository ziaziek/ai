package com.pncomp.ai.tictactoe;

import static org.junit.Assert.*;
import org.junit.Test;

public class GameManagerTests {


    @Test
    public void initTest(){
        GameManager gm = new GameManager();
        assertEquals(9, gm.getBoard().length);
        gm = new GameManager(5);
        assertEquals(25, gm.getBoard().length);
    }

    @Test
    public void placingTests(){
        GameManager gm = new GameManager();
        assertEquals(0, gm.getCurrentPlayer());
        gm.placeSymbol(1, 0, 0);
        assertEquals(1, gm.getCurrentPlayer());
        assertEquals(1, gm.getBoard()[0]);
        gm.placeSymbol(-1, 1, 0);
        assertEquals(0, gm.getCurrentPlayer());
        assertEquals(-1, gm.getBoard()[1]);
        gm.placeSymbol(1, 0,0);
        assertEquals(0, gm.getCurrentPlayer());
        assertEquals(1, gm.getBoard()[0]);
        assertEquals(-1, gm.getBoard()[1]);
    }
}
