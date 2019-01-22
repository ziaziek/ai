package com.pncomp.ai.animalsguesser;

import com.pncomp.ai.BinaryNode;
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Guesser {

    private static String[] questions={"Czy to zwierzę lądowe?", "Czy to ssak?", "Czy to zwierzę domowe?", "Czy to zwierzę ma futro?",
    "Co to za zwierzę?"};
    private final int maxQuestionsIndex=3;
    Scanner s;
    private DecisionTree<BinaryNode> tree;

    private int ix=0;

    public Guesser(){
        BinaryNode root = new BinaryNode();
        root.setAttribute("Q", ix);
        tree=new DecisionTree(root);
        s = new Scanner(System.in);
        try{
        while(ix==0 || !"N".equalsIgnoreCase(s.next())){
            learn(tree);
            System.out.println("Continue? (Y/N");
            ix=0;
        }
        } catch(IOException ex){
            ex.printStackTrace();
        }

    }

    protected void learn(DecisionTree<BinaryNode> tree) throws IOException {
        TreeNode n = tree.getRootNode();
        boolean found=false;
        while(ix<maxQuestionsIndex && !found){
            System.out.println(questions[(int)n.findAttribute("Q")]);
            final String answer=s.next();
            TreeNode aNode= n.getChildren().stream().filter(x -> "T".equals(x.findAttribute("A"))).findFirst().orElse(null);
            if(aNode!=null){
                n=aNode;
                System.out.println("Found answer. Going further this path.");
            } else if(n.isLeafNode() && n.findAttribute("Animal") !=null){
                System.out.println("End of decision path. Giving answer.");
                System.out.println("Myślę, że jest to "+ n.findAttribute("Animal"));
                found=true;
            }
            else {
                System.out.println("No ansswer found on this path. Adding a new child.");
                aNode = buildNode(answer);
                n.addChild(aNode);
                n = aNode;
            }
            ix++;
        }
        if(!found){
            System.out.println("I didn't know this path. Adding final answer.");
            n.addChild(buildFinalNode("T", s.next()));
        }
    }

    private TreeNode buildNode(final String answer){
        TreeNode n = new BinaryNode();
        n.setAttribute("A", answer);
        n.setAttribute("Q", ix+1);
        return n;
    }

    private TreeNode buildFinalNode(final String answer, final String animal){
        TreeNode n = buildNode(answer);
        n.setAttribute("Animal", animal);
        return n;
    }
}
