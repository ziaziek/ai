package com.pncomp.ai;

import java.util.Set;

public class BinaryNode extends TreeNode {


    public BinaryNode(){}

    public BinaryNode(BinaryNode node){
        super(node);
    }

    @Override
    public void setChildren(Set<TreeNode> children) {
        if(children!=null && children.size()<3){
            super.setChildren(children);
        } else {
            throw new UnsupportedOperationException("Children set can have 2 elements maximum.");
        }
    }

    @Override
    public boolean addChild(TreeNode tn) {
        return getChildren().size()<2 && super.addChild(tn);
    }
}
