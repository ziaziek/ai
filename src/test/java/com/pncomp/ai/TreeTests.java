package com.pncomp.ai;

import static org.junit.Assert.*;
import org.junit.Test;

public class TreeTests {

    @Test
    public void treeTest(){
        TreeNode tn = new TreeNode(null);
        DecisionTree dt = new DecisionTree(tn);
        assertFalse(dt.findAllLeaves().isEmpty());
        assertEquals(tn, dt.getRootNode());
        TreeNode nodeLevel1 = new TreeNode(tn);
        TreeNode newNodeLevel1 = new TreeNode(tn);
        assertEquals(2, dt.findAllLeaves().size());
        nodeLevel1.addChild(new TreeNode(null));
        assertEquals(2, dt.findAllLeaves().size());
    }

    @Test
    public  void  countLeavesTests(){
        TreeNode tn = new TreeNode(null);
        DecisionTree dt = new DecisionTree(tn);
        Integer i = dt.countNodes(dt.getRootNode(), 1);
        assertEquals(1, dt.countNodes(dt.getRootNode(), i));
        tn.addChild(new BinaryNode());
        assertEquals(2, dt.countNodes(dt.getRootNode(), i));
        tn.addChild(new TreeNode());
        assertEquals(3, dt.countNodes(dt.getRootNode(), i));
        TreeNode tn11 = new BinaryNode();
        tn.addChild(tn11);
        assertEquals(4, dt.countNodes(dt.getRootNode(), i));
        tn11.addChild(new BinaryNode());
        assertEquals(5, dt.countNodes(dt.getRootNode(), i));
    }
}
