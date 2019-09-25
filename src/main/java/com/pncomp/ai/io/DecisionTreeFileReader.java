package com.pncomp.ai.io;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.Settings;
import com.pncomp.ai.TreeNode;
import com.pncomp.ai.tictactoe.TicTacToeNode;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DecisionTreeFileReader<E extends TreeNode> implements DecisionTreeReader {

    @Override
    public E read() {

        DecisionTree tree = new DecisionTree();
        TreeNode currentParent=null;
        try {
            List<String> lines = Files.readAllLines(Paths.get(Settings.DECISION_TREE_FILE_NAME));
            Long parentId=0L;
            for(String s : lines){

                TreeNode n = new TreeNode();
                String[] attrs = s.split(Settings.separator);
                n.setId(Long.parseLong(attrs[0]));
                if(tree.getRootNode()==null){
                    tree = new DecisionTree(n);
                }
                if(attrs.length>1 && currentParent!=null){
                    if(Long.parseLong(attrs[1])!=parentId){
                        currentParent = tree.findNodeForId(tree.getRootNode(), Long.parseLong(attrs[1]));
                        parentId=currentParent.getId();
                    }
                    n.setParent(currentParent);
                    currentParent.addChild(n);
                }
                for(String p : attrs){
                    if(p.contains("=")){
                        String[] v = p.split("=");
                        if(v.length>1){
                            switch (v[0]){
                                case "result": n.getAttributes().put(v[0], Boolean.parseBoolean(v[1]));
                                break;
                                default: n.getAttributes().put(v[0], Integer.parseInt(v[1]));//decide how to solve the object type. Possibly need to introduce a schema file as well
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (E)tree.getRootNode();
    }

}
