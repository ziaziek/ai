package com.pncomp.ai;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;


public class DecisionTree<T extends TreeNode> {

    @XmlElement(name="node")
    public T getRootNode() {
        return rootNode;
    }

    private T rootNode;

    public DecisionTree(){};

    public DecisionTree(T root){
        this.rootNode=root;
    }

    public List<T> findAllLeaves(){
        List<T> myLeaves=new ArrayList<>();
        leaves(rootNode, myLeaves);
        return myLeaves;
    }

    private void leaves(TreeNode localRoot, List<T> leaves){
        if(!localRoot.isLeafNode()){
            for(TreeNode node: localRoot.getChildren()){
                leaves(node, leaves);
            }
        } else {
            leaves.add((T)localRoot);
        }
    }

    public int countAllNodes(TreeNode n){
        Integer p = 0;
        p = countNodes(n, p);
        return p;
    }

    private int countNodes(TreeNode n, Integer c){
        c++;
        if(!n.isLeafNode()){
            for(TreeNode tn : n.getChildren()){
                c+=countNodes(tn, 0);
            }
        }
        return  c;
    }
}
