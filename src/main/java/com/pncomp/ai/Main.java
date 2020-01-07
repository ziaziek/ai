package com.pncomp.ai;

import com.pncomp.ai.io.DecisionTreeFileReader;
import com.pncomp.ai.io.DecisionTreeFileWriter;
import com.pncomp.ai.tictactoe.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Settings settings = new Settings();
        settings.setLearnSelf(args.length>0 && args[0].equalsIgnoreCase("-learnself"));
        settings.setVerbose(args.length>1 && args[1].equalsIgnoreCase("-verbose"));
        LearnSettings learnSettings= new LearnSettings();
        learnSettings.setPercentageOfNodes(80);
        learnSettings.setSecondsToFinish(7200);
        DecisionTree tree = null;
        if(settings.isLearnSelf()){
            tree = new DecisionTree(new TicTacToeNode());
            new GameRunner(new AutoPlayer(GameManager.DEFAULT_BOARD_SIZE),
                    new DecisionTreeBuilder(
                            tree), learnSettings, settings).run();
        } else {
            tree = new DecisionTree(new DecisionTreeFileReader().read());
        }
        new DecisionTreeFileWriter().save(tree.getRootNode());
    }

    static class SystemInPlayer implements PlayerInput{

        private Scanner scanner = new Scanner(System.in);

        @Override
        public String readInput(){
            return scanner.next();
        }
    }
}


