package com.pncomp.ai.io;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.Settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class DecisionTreeFileWriter implements DecisionTreeWriter {

    @Override
    public void save(DecisionTree decisionTree) {
        try{
            JAXBContext context = JAXBContext.newInstance(decisionTree.getClass());
            Marshaller marshaller=context.createMarshaller();
            marshaller.marshal(decisionTree.getRootNode(), new File(Settings.DECISION_TREE_FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
