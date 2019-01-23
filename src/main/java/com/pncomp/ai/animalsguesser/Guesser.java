package com.pncomp.ai.animalsguesser;

import com.pncomp.ai.BinaryNode;
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;
import java.util.Scanner;

public class Guesser {

    private static String[] questions={"Czy to zwierzę lądowe?", "Czy to ssak?", "Czy to zwierzę domowe?", "Czy to zwierzę ma futro?",
    "Co to za zwierzę?"};
    Scanner s;
    private DecisionTree<BinaryNode> tree;

    private int ix=0;

    public Guesser(){
        BinaryNode root = new BinaryNode();
        root.setAttribute("Q", ix);
        tree=new DecisionTree(root);
        s = new Scanner(System.in);
        String continueAnswer="Y";
        while(!"N".equalsIgnoreCase(continueAnswer)){
            learnAndTeach(tree);
            System.out.println("Continue? (Y/N)");
            continueAnswer=s.next();
            ix=0;
        }

    }

    protected boolean reachedEndOfGuessPath(final TreeNode n){
        return n.isLeafNode() && n.findAttribute("Animal") !=null;
    }

    protected void learnAndTeach(DecisionTree<BinaryNode> tree) {
        TreeNode n = tree.getRootNode();
        while(ix<questions.length && !(reachedEndOfGuessPath(n) && isAnswerFound(n))){
            n = runGuessingCycle(n);
            ix++;
        }
    }

    protected TreeNode runGuessingCycle(TreeNode n) {
        System.out.println(questions[(int)n.findAttribute("Q")]);
        final String answer=s.next();
        if(ix==questions.length-1){
            n.setAttribute("Animal", answer);
        } else {
            TreeNode aNode= n.getChildren().stream().filter(x -> answer.equalsIgnoreCase(x.findAttribute("A").toString())).findFirst().orElse(null);
            if(aNode!=null){
                n=aNode;
            }
            else {
                aNode = buildNode(answer);
                n.addChild(aNode);
                n = aNode;
            }
    }
        return n;
    }

    protected boolean isAnswerFound(TreeNode n) {
        boolean found;
        System.out.println("Myślę, że jest to "+ n.findAttribute("Animal"));
        found=true;
        return found;
    }

    private TreeNode buildNode(final String answer){
        TreeNode n = new BinaryNode();
        n.setAttribute("A", answer);
        n.setAttribute("Q", ix+1);
        return n;
    }

}
