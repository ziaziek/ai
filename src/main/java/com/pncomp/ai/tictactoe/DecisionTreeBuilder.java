package com.pncomp.ai.tictactoe;

import com.google.common.eventbus.Subscribe;
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;

public class DecisionTreeBuilder {

    public TicTacToeNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(TicTacToeNode currentNode) {
        this.currentNode = currentNode;
    }

    private TicTacToeNode currentNode;

    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    private final DecisionTree decisionTree;

    public DecisionTreeBuilder(DecisionTree decisionTree) {
        this.decisionTree = decisionTree;
        this.currentNode= (TicTacToeNode) decisionTree.getRootNode();
    }

    public TreeNode buildNewNode(GameManager gm, int position) {

        TicTacToeNode node = new TicTacToeNode();
        node.setMove(position, gm.getCurrentPlayer());
        return node;
    }

    public TreeNode buildNewNode(int playerNumber, int position) {

        TicTacToeNode node = new TicTacToeNode();
        node.setMove(position, playerNumber);
        return node;
    }

    @Subscribe
    public void handleWinningNode(GameOverEvent event){
        //add if this is actually needed
        TicTacToeNode tn = (TicTacToeNode) buildNewNode(event.getWinningPlayer(), event.getState().getPosition());
        tn.setResult(true);
        currentNode.addChild(tn);
    }

    @Subscribe
    public void handleSymbolPlaced(GameEvent event){
        //check if the tree needs adding a new node
    }
}
