package com.pncomp.ai.animalsguesser;

import com.pncomp.ai.BinaryNode;
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;

import java.util.Scanner;

public class Guesser {

    private static String[] questions={"Czy to zwierzę lądowe?", "Czy to ssak?", "Czy to zwierzę domowe?", "Czy to zwierzę ma futro?"};
    private static final String WHAT_ANIMAL_QUESTION="Co to za zwierzę?";
    private static final String DETAILING_QUESTION="Jakie pytanie odróżnia to zwierzę?";
    Scanner s;
    private DecisionTree<BinaryNode> tree;

    private int ix=0;

    public Guesser(final Scanner s){
        BinaryNode root = new BinaryNode();
        root.setAttribute("Q", questions[ix]);
        tree=new DecisionTree(root);
        this.s = s;
        s.useDelimiter("\n");
    }

    public DecisionTree run(){
        String continueAnswer="Y";
        while(!"N".equalsIgnoreCase(continueAnswer)){
            learnAndTeach(tree);
            System.out.println("Continue? (Y/N)");
            continueAnswer=s.next();
            ix=0;
        }
        return this.tree;
    }

    protected boolean reachedEndOfGuessPath(final TreeNode n){
        return n.isLeafNode() && n.findAttribute("Animal") !=null;
    }

    protected void learnAndTeach(DecisionTree<BinaryNode> tree) {
        TreeNode n = tree.getRootNode();
        while(!(reachedEndOfGuessPath(n) && isAnswerFound(n))){
            n = runGuessingCycle(n);
            ix++;
        }
    }

    protected TreeNode runGuessingCycle(TreeNode n) {
        System.out.println(n.findAttribute("Q"));
        final String answer=s.next();

            TreeNode aNode= n.getChildren().stream().filter(x -> answer.equalsIgnoreCase(x.findAttribute("A").toString())).findFirst().orElse(null);
            if(aNode!=null){
                n=aNode;
            }
            else {
                if(ix<questions.length-1) {
                    aNode = buildNode(answer, questions[ix + 1]);
                } else {
                    System.out.println(WHAT_ANIMAL_QUESTION);
                    final String animQ=s.next();
                    System.out.println(DETAILING_QUESTION);
                    final String detailingQ = s.next();
                    aNode = buildNode(answer, detailingQ);
                    n.addChild(aNode);
                    n=aNode;
                    aNode = buildNode("T", null);
                    aNode.setAttribute("Animal", animQ);
                }
                n.addChild(aNode);
                n = aNode;
            }
        return n;
    }

    protected boolean isAnswerFound(TreeNode n) {
        boolean found;
        System.out.println("Myślę, że jest to "+ n.findAttribute("Animal"));
        found=true;
        return found;
    }

    private TreeNode buildNode(final String answer, final String question){
        TreeNode n = new BinaryNode();
        n.setAttribute("A", answer);
        n.setAttribute("Q", question);
        return n;
    }

}
