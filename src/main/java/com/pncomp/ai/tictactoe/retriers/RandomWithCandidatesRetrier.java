package com.pncomp.ai.tictactoe.retriers;

import com.pncomp.ai.TreeNode;
import com.pncomp.ai.tictactoe.TicTacToeNode;

import java.util.List;

public class RandomWithCandidatesRetrier extends RandomRetrier {

    public List<TreeNode> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<TreeNode> candidates) {
        this.candidates = candidates;
    }

    private List<TreeNode> candidates;

    public RandomWithCandidatesRetrier(final int boardLength){
        super(boardLength);
    }

    @Override
    public int newPosition() throws Exception {
        if(candidates==null){
            throw new Exception("Candidates are empty or not set!");
        }
        int position;
        if (!candidates.isEmpty()) {
            System.out.println("Selecting a winning candidate.");
            position = findRandomCandidate(candidates);
        } else {
            System.out.println("No winning candidate found. Making a random move.");
            position = makeRandomMove(boardLength);
        }
        return position;
    }

    private int findRandomCandidate(List<TreeNode> candidates) {

        return ((TicTacToeNode)candidates.get(random.nextInt(candidates.size()))).getMove()[0];

    }
}
