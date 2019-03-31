package com.pncomp.ai.io;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.Settings;
import com.pncomp.ai.TreeNode;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class DecisionTreeFileWriter implements DecisionTreeWriter {

    @Override
    public void save(TreeNode node) {
        try{
            JAXBContext context = JAXBContext.newInstance(node.getClass());
            Marshaller marshaller=context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(node, new File(Settings.DECISION_TREE_FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
