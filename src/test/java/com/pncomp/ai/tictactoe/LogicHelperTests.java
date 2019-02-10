package com.pncomp.ai.tictactoe;

import com.pncomp.ai.DecisionTree;
import org.junit.Test;
import static org.junit.Assert.*;

public class LogicHelperTests {

    private final DecisionTree dt = buildDecisionTree();
    private TicTacToeNode firstMove;

    @Test
    public void isMoveNoneFoundTest(){
        assertFalse(LogicHelper.isMoveNodeFound(0, GameManager.TIC, (TicTacToeNode) dt.getRootNode()));
    }

    @Test
    public void winningCandidatesTest(){
        assertFalse(LogicHelper.findWinningCandidates(firstMove).isEmpty());
    }


    private DecisionTree buildDecisionTree() {
        TicTacToeNode root = new TicTacToeNode();
        DecisionTree<TicTacToeNode> tree = new DecisionTree<>(root);

        //game
        firstMove = buildNode(root, 2, GameManager.TIC);
        TicTacToeNode secondMove = buildNode(firstMove, 4, GameManager.TAC);
        TicTacToeNode thirdMove = buildNode(secondMove, 1, GameManager.TIC);
        TicTacToeNode fourthMove = buildNode(thirdMove, 0, GameManager.TAC);
        TicTacToeNode fifthMove = buildNode(fourthMove, 5, GameManager.TIC);
        TicTacToeNode sixthMove = buildNode(fifthMove, 8, GameManager.TAC);
        sixthMove.setResult(true);
        return tree;
    }

    private TicTacToeNode buildNode(final TicTacToeNode parent, final int position, final int symbol){
        TicTacToeNode node = new TicTacToeNode(parent);
        node.setMove(position, symbol);
        return node;
    }
}
