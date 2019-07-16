package com.pncomp.ai.tictactoe;

import com.pncomp.ai.Settings;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameManagerTests {

    private final Settings settings = new Settings();
    private final GameManager gm = new GameManager(settings);

    @Test
    public void initTest(){
        GameManager gm = new GameManager(settings);
        assertEquals(9, gm.getBoard().size());
        gm = new GameManager(settings,5);
        assertEquals(25, gm.getBoard().size());
    }

    @Test
    public void placingTests(){
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
        gm.placeSymbol(GameManager.TIC, 2,1);
        gm.placeSymbol(GameManager.TAC, 0,1);
        gm.placeSymbol(GameManager.TIC, 1, 1);
        gm.placeSymbol(GameManager.TAC, 0, 0);
        gm.placeSymbol(GameManager.TIC, 2, 2);
        gm.placeSymbol(GameManager.TAC, 0, 2);
        assertTrue(gm.isGameOver());
        assertEquals(1, gm.getCurrentPlayer());
    }

    @Test
    public void isDrawTest() {
        int[] board = new int[]{-1, 1, -1,
                -1, 1, -1,
                1, -1, 0};
        GameBoardManager bm = new GameBoardManager(settings);
        bm.setBoard(board);
        bm.placeSymbol(1, 2, 2);
        assertTrue(bm.isDraw());
        assertTrue(bm.isGameOver());
    }

    private class GameBoardManager extends GameManager {

        public GameBoardManager(Settings settings) {
            super(settings);
        }

        void setBoard(int[] boardArray) {
            board = boardArray;
        }
    }
}
