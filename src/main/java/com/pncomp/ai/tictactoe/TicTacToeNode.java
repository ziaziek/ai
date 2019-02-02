package com.pncomp.ai.tictactoe;
import com.pncomp.ai.TreeNode;

public class TicTacToeNode extends TreeNode {

    public static final String ATTR_MOVE= "move";
    public static final String ATTR_SYMBOL = "symbol";
    public static final String ATTR_RESULT = "result";

    public void setMove(int m, int symbol){
        setAttribute(ATTR_MOVE, m);
        setAttribute(ATTR_SYMBOL, symbol);
    }

    public void setResult(boolean win){
        setAttribute(ATTR_RESULT, win);
    }

    public boolean getResult(){
        return (boolean) findAttribute(ATTR_RESULT);
    }

    public int[] getMove(){
        return new int[]{(int) findAttribute(ATTR_MOVE), (int) findAttribute(ATTR_SYMBOL)};
    }
}
