package com.pncomp.ai;

import static org.junit.Assert.*;
import com.pncomp.ai.animalsguesser.Guesser;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

public class IterativeLearningTest {

    private static final String piesDelfin = "t\nt\nt\nn\npies\nCzy szczeka?\ny\nn\nt\nn\nn\ndelfin\nCzy szybko pływa?\nn";

    private Guesser dt;

    @Before
    public void setUp(){
        dt = new Guesser(new Scanner(piesDelfin));
    }

    private BinaryNode buildNode(int ix, String answer, String animal){
        BinaryNode tn = new BinaryNode();
        tn.setAttribute("Q", ix);
        tn.setAttribute("A", answer);
        tn.setAttribute("Animal", animal);
        return tn;
    }

    @Test
    public void testGuesser(){
        DecisionTree tree = dt.run();
        assertNotNull(tree);
        assertEquals(11, tree.countAllNodes(tree.getRootNode()));
        assertEquals(2, tree.findAllLeaves().size());
        assertEquals(1, tree.findAllLeaves().stream().filter(x->((BinaryNode)x).findAttribute("Animal").equals("delfin")).count());
        assertEquals(1, tree.findAllLeaves().stream().filter(x->((BinaryNode)x).findAttribute("Animal").equals("pies")).count());
    }
}
