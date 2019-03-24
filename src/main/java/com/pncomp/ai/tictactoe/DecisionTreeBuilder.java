package com.pncomp.ai.tictactoe;

import com.google.common.eventbus.Subscribe;
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
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
        TicTacToeNode tn = (TicTacToeNode) buildNewNode(event.getState().getSymbol(), event.getState().getPosition());
        tn.setResult(true);
        currentNode.addChild(tn);
    }

    @Subscribe
    public void handleSymbolPlaced(GameEvent event){
        //check if the tree needs adding a new node
        TreeNode potentialNode = buildNewNode(event.getState().getSymbol(), event.getState().getPosition());
        System.out.println("Checking if a new tree node is required.");
        BoardState state = event.getState();
        if (currentNode.isLeafNode()) {
            System.out.println("This is a leaf node, so I need to add a new node");
            TreeNode node = buildNewNode(state.getSymbol(), state.getPosition());
            currentNode.addChild(node);
            currentNode=(TicTacToeNode)node;
        } else {
            Optional tnOptional = currentNode.getChildren().stream().filter(x -> x.isLike(potentialNode)).findAny();
            if(tnOptional.isPresent()){
                currentNode=(TicTacToeNode)tnOptional.get();
            } else {
                currentNode.addChild(potentialNode);
                currentNode=(TicTacToeNode)potentialNode;
            }
        }
    }

    @Subscribe
    public void handleNewGame(NewGameEvent event){
        System.out.println("New game. Resetting.");
        this.currentNode= (TicTacToeNode) this.getDecisionTree().getRootNode();
    }

    /*
        Return true if none of the children represents the given board state.
     */
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
