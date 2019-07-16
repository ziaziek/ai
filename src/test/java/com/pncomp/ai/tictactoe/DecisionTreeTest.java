package com.pncomp.ai.tictactoe;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;
import com.pncomp.ai.tictactoe.events.GameEvent;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DecisionTreeTest {

    private final List<Integer> board = Collections.emptyList();

    @Test
    public void nomatchTest(){
        TicTacToeNode rootNode = new TicTacToeNode();
        DecisionTree tree = new DecisionTree(rootNode);
        ExtDecisionTreeBuilder builder = new ExtDecisionTreeBuilder(tree);
        builder.handleSymbolPlaced(new GameEvent(this, new BoardState(4, -1, board), 0));
        assertFalse(rootNode.getChildren().isEmpty());
        assertTrue(builder.noMatch(rootNode.getChildren(), new BoardState(4, 1, board)));
        assertFalse(builder.noMatch(rootNode.getChildren(), new BoardState(4, -1, board)));
    }


    private class ExtDecisionTreeBuilder extends DecisionTreeBuilder{

        public ExtDecisionTreeBuilder(DecisionTree decisionTree) {
            super(decisionTree);
        }

        public boolean noMatch(Set<TreeNode> children, BoardState state){
            return super.nomatch(children, state);
        }

    }
}
