package com.pncomp.ai.io;

import com.pncomp.ai.DecisionTree;

import javax.xml.bind.JAXBException;

public interface DecisionTreeWriter {

    void save(DecisionTree decisionTree) throws JAXBException;
}
