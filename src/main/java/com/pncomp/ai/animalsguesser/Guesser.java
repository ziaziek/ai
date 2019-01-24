package com.pncomp.ai.animalsguesser;

import com.pncomp.ai.BinaryNode;
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;
import java.util.Scanner;

public class Guesser {

    private static final String QUESTION_MARK = "Q";
    private static final String ANSWER_MARK = "A";
    private static final String ANIMAL_MARK="Animal";

    private static String[] questions={"Czy to zwierzę lądowe?", "Czy to ssak?", "Czy to zwierzę domowe?", "Czy to zwierzę ma futro?",
    "Co to za zwierzę?"};
    Scanner s;
    private DecisionTree<BinaryNode> tree;

    private int ix=0;

    public Guesser(){
        BinaryNode root = new BinaryNode();
        root.setAttribute(QUESTION_MARK, questions[ix]);
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
        return n.isLeafNode() && n.findAttribute(ANIMAL_MARK) !=null;
    }

    protected void learnAndTeach(DecisionTree<BinaryNode> tree) {
        TreeNode n = tree.getRootNode();
        while(!(reachedEndOfGuessPath(n) && isAnswerFound(n))){
            n = runGuessingCycle(n);
            ix++;
        }
    }

    protected TreeNode runGuessingCycle(TreeNode n) {
        System.out.println(n.findAttribute(QUESTION_MARK));
        final String answer=s.nextLine();
            TreeNode aNode= n.getChildren().stream().filter(x -> answer.equalsIgnoreCase(x.findAttribute("A").toString())).findFirst().orElse(null);
            if(aNode!=null){
                n=aNode;
            }
            else {
                if(ix<questions.length-1){
                    aNode = buildNode(answer, questions[ix+1]);
                    n.addChild(aNode);
                } else {
                    System.out.println("Jakie pytanie odróżnia to zwierze?");
                    final String question = s.nextLine();
                    aNode = new BinaryNode();
                    aNode.setAttribute(QUESTION_MARK, question);
                    aNode.setAttribute(ANSWER_MARK, "T");
                    TreeNode parent = n.getParent();
                    n.setParent(aNode);
                    aNode.setParent(parent);
                    n.setAttribute(ANIMAL_MARK, answer);
                    n.setAttribute(ANSWER_MARK, "T");
                }
                n = aNode;
            }
        return n;
    }

    protected boolean isAnswerFound(TreeNode n) {
        if(n.findAttribute(ANIMAL_MARK)!=null){
            System.out.println("Myślę, że jest to "+ n.findAttribute(ANIMAL_MARK));
            return true;
        } else {
            return false;
        }
    }

    private TreeNode buildNode(final String answer, final String question){
        TreeNode n = new BinaryNode();
        n.setAttribute(ANSWER_MARK, answer);
        n.setAttribute(QUESTION_MARK, question);
        return n;
    }

}
