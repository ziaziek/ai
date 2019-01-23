package com.pncomp.ai.animalsguesser;

import com.pncomp.ai.BinaryNode;
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;
import java.io.IOException;
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
        try{
        while(!"N".equalsIgnoreCase(continueAnswer)){
            learn(tree);
            System.out.println("Continue? (Y/N)");
            continueAnswer=s.next();
            ix=0;
        }
        } catch(IOException ex){
            ex.printStackTrace();
        }

    }

    protected void learn(DecisionTree<BinaryNode> tree) throws IOException {
        TreeNode n = tree.getRootNode();
        boolean found=false;
        while(ix<questions.length && !found){
            System.out.println(n);
            if(n.isLeafNode() && n.findAttribute("Animal") !=null){
                System.out.println("Myślę, że jest to "+ n.findAttribute("Animal"));
                found=true;
            } else {
                System.out.println(questions[(int)n.findAttribute("Q")]);
                final String answer=s.next();
                if(ix==questions.length-1){
                    n.setAttribute("Animal", answer);
                } else {
                    TreeNode aNode= n.getChildren().stream().filter(x -> answer.equals(x.findAttribute("A"))).findFirst().orElse(null);
                    if(aNode!=null){
                        n=aNode;
                    }
                    else {
                        aNode = buildNode(answer);
                        n.addChild(aNode);
                        n = aNode;
                    }
                }
            }

            ix++;
        }
    }

    private TreeNode buildNode(final String answer){
        TreeNode n = new BinaryNode();
        n.setAttribute("A", answer);
        n.setAttribute("Q", ix+1);
        return n;
    }

}
