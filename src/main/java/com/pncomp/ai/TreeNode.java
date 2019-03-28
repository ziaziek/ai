package com.pncomp.ai;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@XmlRootElement(name="node")
public class TreeNode {

    @XmlElement
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @XmlElementRef
    public Set<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(Set<TreeNode> children) {
        this.children = children;
    }

    private Map<String, Object> attributes = new HashMap<>();

    protected Set<TreeNode> children = new HashSet<>();

    @XmlTransient
    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent){
            this.parent=parent;
    }

    @XmlTransient
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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        getAttributes().forEach((x,y)-> {sb.append(x); sb.append(": "); sb.append(y.toString()); sb.append("\n");});
        return sb.toString();
    }

    public boolean isLike(Object obj) {
        if(obj==null || ! (obj instanceof TreeNode)){
            return false;
        }
        TreeNode tn = (TreeNode) obj;
         return hasSameAttributes(tn);
    }

    private boolean hasSameAttributes(TreeNode tn){
        for(String s: attributes.keySet()){
            if(!attributes.get(s).equals(tn.findAttribute(s))){
                return false;
            }
        }
        return true;
    }
}
