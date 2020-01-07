package com.pncomp.ai;

import com.google.common.collect.Lists;
import com.pncomp.ai.io.DecisionTreeFileReader;
import com.pncomp.ai.tictactoe.TicTacToeNode;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DecisionTreeReaderTest {

    private final DecisionTreeFileReader<TicTacToeNode> treeFileReader = new DecisionTreeFileReader();

    @Test
    @Ignore
    public void readTree(){
        DecisionTree<TreeNode> tree = new DecisionTree(treeFileReader.read());
        assertNotNull(tree);
        assertNotNull(tree.getRootNode());
        assertTrue(tree.getRootNode().getChildren().size()==1);
        List<TreeNode> ltree = Lists.newLinkedList(tree.getRootNode().getChildren());
        assertTrue(ltree.size()==1);
    }
}
