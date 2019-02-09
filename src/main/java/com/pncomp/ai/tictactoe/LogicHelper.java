package com.pncomp.ai.tictactoe;

import com.pncomp.ai.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LogicHelper {

    public static boolean isMoveNodeFound(int position, int symbol, TicTacToeNode node){
        return node.getChildren().stream().anyMatch(x-> (int)x.findAttribute(TicTacToeNode.ATTR_SYMBOL)==symbol
        && (int)x.findAttribute(TicTacToeNode.ATTR_MOVE)==position);
    }

    /**
     * Finds out if this node has a winning branch and picks them up.
     * @param node
     * @return
     */
    public static List<TreeNode> findWinningCandidates(TicTacToeNode node){
        List<TreeNode> candidates = new ArrayList<>();
        for(TreeNode n : node.getChildren()){
            TicTacToeNode tn = (TicTacToeNode)n;
            if(hasWinningLeaf(tn, tn.getMove()[0])){
                candidates.add(n);
            }
        }
        return candidates;
    }

    private static boolean hasWinningLeaf(TicTacToeNode n, int symbol) {

        return findAllLeaves(n, new ArrayList<>()).stream().anyMatch(x -> symbol == x.getMove()[1]
                && Boolean.TRUE.equals(x.getResult()));

    }

    private static List<TicTacToeNode> findAllLeaves(TicTacToeNode n, List<TicTacToeNode> nodes) {
        for(TreeNode tn : n.getChildren()){
            if(tn.getChildren().isEmpty()){
                nodes.add((TicTacToeNode) tn);
            } else {
                findAllLeaves((TicTacToeNode) tn, nodes);
            }
        }
        return nodes;
    }
}
