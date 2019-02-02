package com.pncomp.ai.tictactoe;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LogicHelper {

    public static boolean isMoveNodeFound(int position, int symbol, TicTacToeNode node){
        return node.getChildren().stream().anyMatch(x-> (int)x.findAttribute(TicTacToeNode.ATTR_SYMBOL)==symbol
        && (int)x.findAttribute(TicTacToeNode.ATTR_MOVE)==position);
    }

    public static List<TreeNode> findWinningCandidates(TicTacToeNode node){
        List<TreeNode> candidates = new ArrayList<>();
        for(TreeNode n : node.getChildren()){
            if(hasWinningLeaf(n)){
                candidates.add(n);
            }
        }
        return candidates;
    }

    private static boolean hasWinningLeaf(TreeNode n) {
        return false;
    }
}
