package com.pncomp.ai;

import java.util.*;

public class TreeNode {

    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public Set<TreeNode> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public void setChildren(Set<TreeNode> children) {
        this.children = children;
    }

    private Map<String, Object> attributes = new HashMap<>();

    protected Set<TreeNode> children = new HashSet<>();

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent){
            this.parent=parent;
    }

    private TreeNode parent;

    /**
     * TreeNode constructor
     * @param parentNode if provided, the node will be added to its children.
     */
    public TreeNode(TreeNode parentNode){
        if(parentNode!=null){
            parentNode.addChild(this);
        }
    }

    public TreeNode(){};

    public boolean isLeafNode(){
        return children ==null || children.isEmpty();
    }

    public boolean addChild(TreeNode tn){
        if(tn!=null){
            tn.setParent(this);
            children.add(tn);
            return true;
        } else {
            return false;
        }
    }

    public Object findAttribute(final String attributeName){
        return attributes.get(attributeName);
    }

    public void setAttribute(final String attributeName, final Object value){
        attributes.put(attributeName, value);
    }
}
