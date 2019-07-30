package com.pncomp.ai.io;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.Settings;
import com.pncomp.ai.TreeNode;

import java.io.*;
import java.util.Map;

public class DecisionTreeFileWriter implements DecisionTreeWriter {

    @Override
    public void save(TreeNode node) {

        try(BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(Settings.DECISION_TREE_FILE_NAME))) {
            doSave(node, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doSave(TreeNode node, BufferedOutputStream outStream) {
        try {
        outStream.write((node.getId()+Settings.separator).getBytes());
        if(node.getParent()!=null){
            outStream.write((node.getParent().getId()+Settings.separator).getBytes());
        }
        writeAttributes(node, outStream);
         outStream.write( System.lineSeparator().getBytes());
        for(TreeNode n: node.getChildren()){
            doSave(n, outStream);
        }
        }
        catch (IOException e) {
                e.printStackTrace();
            }

    }

    private void writeAttributes(TreeNode node, BufferedOutputStream outStream) throws IOException {
        for (Map.Entry<String, Object> entry : node.getAttributes().entrySet()) {
            String x = entry.getKey();
            Object y = entry.getValue();
            outStream.write((x+"="+y.toString()+Settings.separator).getBytes());
        }
    }
}
