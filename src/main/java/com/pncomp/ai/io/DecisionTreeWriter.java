package com.pncomp.ai.io;
import com.pncomp.ai.TreeNode;

public interface DecisionTreeWriter {

    void save(TreeNode treeNode);

    void save(String filename, TreeNode treeNode);
}
