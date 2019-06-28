package com.pncomp.ai.io;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.Settings;
import com.pncomp.ai.TreeNode;
import com.pncomp.ai.tictactoe.TicTacToeNode;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class DecisionTreeFileReader<E extends TreeNode> implements DecisionTreeReader {

    @Override
    public E read() {
        try {
            JAXBContext context = JAXBContext.newInstance(TicTacToeNode.class);
            Unmarshaller unmasrshaller = context.createUnmarshaller();
            E root = (E)(unmasrshaller.unmarshal(new File(Settings.DECISION_TREE_FILE_NAME)));
            return root;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

}
