package com.pncomp.ai;

import com.pncomp.ai.io.DecisionTreeFileReader;
import com.pncomp.ai.io.DecisionTreeFileWriter;
import com.pncomp.ai.tictactoe.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static final int NO_THREADS = 9; //this can be parametrised via configuration or entry parameters. Now it's the
    // number of fields, initial positions

    public static void main(String[] args) throws Exception {
        Settings settings = getSettings(args);
        LearnSettings learnSettings= getLearningSettings();
        DecisionTree tree = null;
        if(settings.isLearnSelf()){

            // For now, we remove continue option. This will need further investigation
//            if(Arrays.stream(args).anyMatch(x -> x.equals("-continue"))){
//                tree = new DecisionTree(new DecisionTreeFileReader().read(Settings.TEMPORARY_FILE_NAME));
//            } else {
                tree = new DecisionTree(new TicTacToeNode());
            //}
            ExecutorService executorService = Executors.newFixedThreadPool(NO_THREADS);
            List<Callable<DecisionTree>> tasks = new ArrayList<>();
            for(int i=0; i<NO_THREADS; i++){
                final int position = i;
                tasks.add(() -> {
                    final DecisionTree t = new DecisionTree(new TicTacToeNode());
                    String thid = "th" + position;
                    System.out.println("Starting "+thid);
                    PlayerInput player = new InitialPositionAutoPlayer(GameManager.DEFAULT_BOARD_SIZE, position, thid );
                    System.out.println("Created player.");
                    try {
                        new GameRunner(player,
                                new DecisionTreeBuilder(
                                        t), learnSettings, settings, thid).run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return t;
                });
            }
            List<Future<DecisionTree>> trees = executorService.invokeAll(tasks);
            executorService.shutdown();
            if(executorService.isShutdown()){
                new DecisionTreeFileWriter().save(assembleTree(trees).getRootNode());
            }
        } else {
            System.out.println("Reading in decision tree. Please wait ...");
            tree = new DecisionTree(new DecisionTreeFileReader().read());
            new GameRunner(new SystemInPlayer(),
                    new DecisionTreeBuilder(
                            tree), learnSettings, settings).run();
        }
    }

    private static LearnSettings getLearningSettings() {
        LearnSettings learnSettings = new LearnSettings();
        learnSettings.setPercentageOfNodes(80);
        learnSettings.setSecondsToFinish(3600);
        return learnSettings;
    }

    private static Settings getSettings(String args[]) {
        Settings settings = new Settings();
        settings.setLearnSelf(args.length>0 && args[0].equalsIgnoreCase("-learnself"));
        settings.setVerbose(args.length>1 && args[1].equalsIgnoreCase("-verbose"));
        settings.setSaveIntervalMillis(90000);
        return settings;
    }

    private static DecisionTree<TreeNode> assembleTree(List<Future<DecisionTree>> tasks) throws Exception {
        final DecisionTree tree = new DecisionTree(new TicTacToeNode());
        final TreeNode root = tree.getRootNode();
        for(Future task : tasks){
            root.addChild(((DecisionTree)task.get()).getRootNode().getChildren().stream().findFirst().get());
        }
        return tree;
    }

    static class SystemInPlayer implements PlayerInput{

        private Scanner scanner = new Scanner(System.in);

        @Override
        public String readInput(){
            return scanner.next();
        }
    }
}


