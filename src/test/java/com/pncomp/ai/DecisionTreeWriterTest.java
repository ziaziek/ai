package com.pncomp.ai;

import com.pncomp.ai.io.DecisionTreeFileWriter;
import com.pncomp.ai.tictactoe.ExampleDecisionTree;
import com.pncomp.ai.tictactoe.TicTacToeNode;
import org.junit.Test;

public class DecisionTreeWriterTest {

    private final DecisionTreeFileWriter fileWriter = new DecisionTreeFileWriter();

    @Test
    public void saveEmptyTree(){
        TreeNode dt = new TreeNode();
        fileWriter.save(dt);
    }

    @Test
    public void saveTicTacToeTree(){
        TicTacToeNode root = (TicTacToeNode) new ExampleDecisionTree(new TicTacToeNode()).getRootNode();
        fileWriter.save(root);
    }
}
