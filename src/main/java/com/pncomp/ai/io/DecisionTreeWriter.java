package com.pncomp.ai.io;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;

import javax.xml.bind.JAXBException;

public interface DecisionTreeWriter {

    void save(TreeNode treeNode) throws JAXBException;
}
