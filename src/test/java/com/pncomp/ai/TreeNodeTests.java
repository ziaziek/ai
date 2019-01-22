package com.pncomp.ai;

import static org.junit.Assert.*;
import org.junit.Test;

public class TreeNodeTests {

    @Test
    public void newSingleNodeTest(){
        TreeNode tn = new TreeNode(null);
        assertNotNull(tn);
        assertNotNull(tn.getAttributes());
        assertTrue(tn.getAttributes().isEmpty());
        assertTrue(tn.getChildren().isEmpty());
        assertTrue(tn.isLeafNode());
        assertNull(tn.getParent());
        TreeNode tn1 = new TreeNode();
        assertNull(tn1.getParent());
    }

    @Test
    public void multipleNodesTest(){
        TreeNode tn = new TreeNode(null);
        TreeNode tn1 = new TreeNode(tn);
        assertTrue(tn1.isLeafNode());
        assertFalse(tn.isLeafNode());
        assertNotNull(tn1.getParent());
        assertEquals(tn, tn1.getParent());
    }

    @Test
    public void attributesTest(){
        TreeNode tn = new TreeNode();
        tn.getAttributes().put("Answer", null);
        assertTrue(tn.getAttributes().containsKey("Answer"));
        tn.getAttributes().put("Question", "?");
        assertTrue(tn.getAttributes().containsKey("Question"));
        assertEquals(2, tn.getAttributes().size());
    }

    @Test
    public void nodeAttributeTest(){
        TreeNode tn = new TreeNode();
        tn.setAttribute("Q", "What?");
        assertEquals(1, tn.getAttributes().size());
        assertNotNull(tn.findAttribute("Q"));
        assertNull(tn.findAttribute("A"));
        tn.setAttribute("A", 1);
        assertEquals(2, tn.getAttributes().size());
        assertNotNull(tn.findAttribute("A"));
    }
}
