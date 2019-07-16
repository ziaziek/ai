package com.pncomp.ai.tictactoe;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogicHelper {

    public static List<Integer> getFreePlaces() {
        return freePlaces;
    }

    private static List<Integer> freePlaces = new ArrayList<>();

    public static TreeNode isMoveNodeFound(int position, int symbol, TicTacToeNode node){
        List<TreeNode> foundMovesList = node.getChildren().stream().filter(x-> (int)x.findAttribute(TicTacToeNode.ATTR_SYMBOL)==symbol
                && (int)x.findAttribute(TicTacToeNode.ATTR_MOVE)==position).collect(Collectors.toList());
        if(foundMovesList!=null && !foundMovesList.isEmpty()){
            return foundMovesList.get(0);
        } else {
            return null;
        }
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
            if(hasWinningLeaf(tn, tn.getMove()[1])){
                candidates.add(n);
            }
        }
        return candidates;
    }

    public static void initFreePlaces(final List<Integer> board){

        List<Integer> r = new ArrayList<>();
        for(int i=0; i< board.size(); i++){
            if(board.get(i)==null || board.get(i)==0){
                r.add(i);
            }
        }
        freePlaces=r;
    }

    private static boolean hasWinningLeaf(TicTacToeNode n, int symbol) {

        return new DecisionTree<>(n).findAllLeaves().stream().anyMatch(x -> symbol == x.getMove()[1]
                && x.getResult());

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
