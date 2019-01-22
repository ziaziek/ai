package com.pncomp.ai;

import static org.junit.Assert.*;
import org.junit.Test;

public class BinaryNodeTests {

    @Test
    public void binaryNodeTests(){
        BinaryNode node = new BinaryNode();
        assertNull(node.getParent());
        BinaryNode node1 = new BinaryNode(node);
        assertEquals(1, node.getChildren().size());
        assertTrue(node.getChildren().contains(node1));
        BinaryNode node2 = new BinaryNode(node);
        assertEquals(2, node.getChildren().size());
        BinaryNode node3 = new BinaryNode(node);
        assertEquals(2, node.getChildren().size());
        assertFalse(node.addChild(node3));
    }
}
