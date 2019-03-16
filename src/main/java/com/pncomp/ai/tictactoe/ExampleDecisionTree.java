package com.pncomp.ai.tictactoe;

import com.google.common.eventbus.EventBus;
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;

public class ExampleDecisionTree extends DecisionTree {

    public ExampleDecisionTree(){
        throw new UnsupportedOperationException("This tree should be constructed with a given tree node.");
    }

    public ExampleDecisionTree(TreeNode root) {
        super(root);
        EventBusFactory.buildEventBus("example");
        fillRootNode();
    }

    private void fillRootNode() {
        TicTacToeNode node = (TicTacToeNode) getRootNode();
        GameManager gm = new GameManager();
        gm.setEventBusName("example");
        DecisionTreeBuilder builder = new DecisionTreeBuilder(this);
        EventBusFactory.getEventBus("example").register(builder);
        gm.placeSymbol(-1, 1, 1);
        gm.placeSymbol(1, 0,1);
        gm.placeSymbol(-1, 0,0);
        gm.placeSymbol(1, 0,2);
        gm.placeSymbol(-1, 2, 2);
    }
}
