package com.pncomp.ai.tictactoe;

import com.google.common.eventbus.Subscribe;
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

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
        node.setMove(position, gm.getCurrentPlayerSymbol());
        return node;
    }

    public TreeNode buildNewNode(int symbol, int position) {

        TicTacToeNode node = new TicTacToeNode();
        node.setMove(position, symbol);
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
        TreeNode potentialNode = buildNewNode(event.getPlayer(), event.getState().getPosition());
        System.out.println("Checking if a new tree node is required.");
        BoardState state = event.getState();
        if (currentNode.isLeafNode()) {
            System.out.printf("This is a leaf node, so I need to add a new node");
            currentNode.addChild(buildNewNode(state.getSymbol(), state.getPosition()));
        } else if (nomatch(currentNode.getChildren(), state)) {
            System.out.println("Current node has children, but not like this. Adding a new child.");
            currentNode.addChild(potentialNode);
        }
    }

    @Subscribe
    public void handleNewGame(NewGameEvent event){
        this.currentNode= (TicTacToeNode) this.getDecisionTree().getRootNode();
    }

    protected boolean nomatch(@NotNull Set<TreeNode> children, final BoardState state) {
        for(TreeNode tn : children){
            if(tn instanceof TicTacToeNode){
                TicTacToeNode tnn = (TicTacToeNode)tn;
                int[] move = tnn.getMove();
                if(move[0]==state.getPosition() && move[1]==state.getSymbol()){
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

}
