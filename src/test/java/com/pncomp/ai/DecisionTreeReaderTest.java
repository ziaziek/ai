package com.pncomp.ai;

import com.google.common.collect.Lists;
import com.pncomp.ai.io.DecisionTreeFileReader;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DecisionTreeReaderTest {

    private final DecisionTreeFileReader treeFileReader = new DecisionTreeFileReader(DecisionTree.class);

    @Test
    public void readTree(){
        DecisionTree tree = treeFileReader.read();
        assertNotNull(tree);
        assertNotNull(tree.getRootNode());
        assertTrue(tree.getRootNode().getChildren().size()==1);
        List<TreeNode> ltree = Lists.newLinkedList(tree.getRootNode().getChildren());
        assertTrue(ltree.size()==1);
    }
}
