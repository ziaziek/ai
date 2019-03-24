package com.pncomp.ai.io;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.Settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;

public class DecisionTreeFileReader implements DecisionTreeReader {

    private Class<? extends DecisionTree> T;

    public DecisionTreeFileReader(Class<? extends DecisionTree> dtClass){
        T = dtClass;
    }

    @Override
    public DecisionTree read() {
        try {
            JAXBContext context = JAXBContext.newInstance(T);
            return (DecisionTree) context.createUnmarshaller().unmarshal(new File(Settings.DECISION_TREE_FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
