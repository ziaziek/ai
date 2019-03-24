package com.pncomp.ai;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BinaryTree extends DecisionTree<BinaryNode> {

    public BinaryTree(){
        super();
    }

    public BinaryTree(BinaryNode root){
        super(root);
    }
}
