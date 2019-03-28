package com.pncomp.ai.io;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.Settings;
import com.pncomp.ai.TreeNode;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class DecisionTreeFileReader implements DecisionTreeReader {

    private Class<? extends DecisionTree> T;

    public DecisionTreeFileReader(Class<? extends DecisionTree> dtClass){
        T = dtClass;
    }

    @Override
    public DecisionTree read() {
        try {
            JAXBContext context = JAXBContext.newInstance(DecisionTree.class);
            Unmarshaller unmasrshaller = context.createUnmarshaller();
            TreeNode root = (TreeNode) unmasrshaller.unmarshal(new File(Settings.DECISION_TREE_FILE_NAME));
            return new DecisionTree(root);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
