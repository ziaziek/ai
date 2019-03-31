package com.pncomp.ai.io;

import com.pncomp.ai.TreeNode;

public interface DecisionTreeReader<E extends TreeNode> {

    E read();
}
