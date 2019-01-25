package com.pncomp.ai.tictactoe;

import static org.junit.Assert.*;
import org.junit.Test;

public class GameManagerTests {

    @Test
    public void initTest(){
        GameManager gm = new GameManager();
        assertEquals(9, gm.getBoard().size());
        gm = new GameManager(5);
        assertEquals(25, gm.getBoard().size());
    }

    @Test
    public void placingTests(){
        GameManager gm = new GameManager();
        assertEquals(0, gm.getCurrentPlayer());
        gm.placeSymbol(1, 0, 0);
        assertEquals(1, gm.getCurrentPlayer());
        assertEquals(1, gm.getBoard().get(0).intValue());
        gm.placeSymbol(-1, 1, 0);
        assertEquals(0, gm.getCurrentPlayer());
        assertEquals(-1, gm.getBoard().get(1).intValue());
        gm.placeSymbol(1, 0,0);
        assertEquals(0, gm.getCurrentPlayer());
        assertEquals(1, gm.getBoard().get(0).intValue());
        assertEquals(-1, gm.getBoard().get(1).intValue());
    }

    @Test
    public void winningRightDiagonal(){
        GameManager gm = new GameManager();
        gm.placeSymbol(GameManager.TIC, 1,1);
        gm.placeSymbol(GameManager.TAC, 0,0);
        gm.placeSymbol(GameManager.TIC, 0, 2);
        gm.placeSymbol(GameManager.TAC, 0, 1);
        gm.placeSymbol(GameManager.TIC, 2, 0);
        assertTrue(gm.isGameOver());
        assertEquals(0, gm.getCurrentPlayer());
    }

    @Test
    public void winningLeftDiagonalTest(){
        GameManager gm = new GameManager();
        gm.placeSymbol(GameManager.TIC, 1,1);
        gm.placeSymbol(GameManager.TAC, 1,0);
        gm.placeSymbol(GameManager.TIC, 0, 0);
        gm.placeSymbol(GameManager.TAC, 0, 1);
        gm.placeSymbol(GameManager.TIC, 2, 2);
        assertTrue(gm.isGameOver());
        assertEquals(0, gm.getCurrentPlayer());
    }

    @Test
    public void winnigRowTest(){
        GameManager gm = new GameManager();
        gm.placeSymbol(GameManager.TIC, 0,1);
        gm.placeSymbol(GameManager.TAC, 1,0);
        gm.placeSymbol(GameManager.TIC, 1, 1);
        gm.placeSymbol(GameManager.TAC, 0, 0);
        gm.placeSymbol(GameManager.TIC, 2, 1);
        assertTrue(gm.isGameOver());
        assertEquals(0, gm.getCurrentPlayer());
    }

    @Test
    public void winningColumnTest(){
        GameManager gm = new GameManager();
        gm.placeSymbol(GameManager.TIC, 2,1);
        gm.placeSymbol(GameManager.TAC, 0,1);
        gm.placeSymbol(GameManager.TIC, 1, 1);
        gm.placeSymbol(GameManager.TAC, 0, 0);
        gm.placeSymbol(GameManager.TIC, 2, 2);
        gm.placeSymbol(GameManager.TAC, 0, 2);
        assertTrue(gm.isGameOver());
        assertEquals(1, gm.getCurrentPlayer());
    }
}
