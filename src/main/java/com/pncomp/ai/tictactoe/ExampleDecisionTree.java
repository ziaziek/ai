package com.pncomp.ai.tictactoe;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;
import com.pncomp.ai.tictactoe.events.EventBusFactory;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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
        GameManager gm = new GameManager();
        gm.setEventBusName("example");
        DecisionTreeBuilder builder = new DecisionTreeBuilder(this);
        EventBusFactory.getEventBus("example").register(builder);
        gm.placeSymbol(-1, 1, 1);
        gm.placeSymbol(1, 0,0);
        gm.placeSymbol(-1, 0,1);
        gm.placeSymbol(1, 2,0);
        gm.placeSymbol(-1, 2, 2);
        gm.placeSymbol(1, 1,0);
    }
}
