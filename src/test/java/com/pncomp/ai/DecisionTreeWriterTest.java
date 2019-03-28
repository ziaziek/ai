package com.pncomp.ai;

import com.pncomp.ai.io.DecisionTreeFileWriter;
import org.junit.Test;

public class DecisionTreeWriterTest {

    private final DecisionTreeFileWriter fileWriter = new DecisionTreeFileWriter();

    @Test
    public void saveEmptyTree(){
        DecisionTree dt = new DecisionTree(new TreeNode());
        fileWriter.save(dt);
    }

    @Test
    public void saveTicTacToeTree(){
        DecisionTree edt = new DecisionTree(new TreeNode());
        TreeNode move = new TreeNode(edt.getRootNode());
        move.setAttribute("move", 4);
        move.setAttribute("symbol", -1);
        TreeNode move1 = new TreeNode(move);
        move1.setAttribute("move", 11);
        fileWriter.save(edt);
    }
}
