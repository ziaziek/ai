package com.pncomp.ai.tictactoe;

import com.google.common.eventbus.EventBus;
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.Settings;
import com.pncomp.ai.TreeNode;
import com.pncomp.ai.io.DecisionTreeFileWriter;
import com.pncomp.ai.io.DecisionTreeWriter;
import com.pncomp.ai.tictactoe.events.EventBusFactory;
import com.pncomp.ai.tictactoe.events.NewGameEvent;
import com.pncomp.ai.tictactoe.retriers.RandomRetrier;
import com.pncomp.ai.tictactoe.retriers.RandomWithCandidatesRetrier;
import com.pncomp.ai.tictactoe.retriers.Retrier;

import java.io.IOException;
import java.util.Arrays;

public class GameRunner {

    private DecisionTreeBuilder builder;

    private final PlayerInput playerInput;
    private final LearnSettings learnSettings;
    private final Settings settings;
    private DecisionTreeWriter decisionTreeWriter= new DecisionTreeFileWriter();
    private int gamesPlayed=0;
    private long startTime, saveTime=System.currentTimeMillis();
    private EventBus eventBus;

    public GameRunner(PlayerInput scanner, LearnSettings learnSettings, Settings settings) {
        this(scanner, null, learnSettings, settings);
    }

    public GameRunner(final PlayerInput scanner, final DecisionTreeBuilder decisionTreeBuilder, final LearnSettings learnSettings, final Settings settings){
        this.playerInput = scanner;
        this.learnSettings=learnSettings;
        this.settings=settings;
        if (decisionTreeBuilder==null){
            builder = buildDecisionTreeBuilder();
        } else {
            builder = decisionTreeBuilder;
        }
        builder.setSettings(settings);
    }

    public GameRunner(final PlayerInput scanner, final DecisionTreeBuilder decisionTreeBuilder, final LearnSettings learnSettings, final Settings settings, final String eventBusName){
        this(scanner, decisionTreeBuilder, learnSettings, settings);
        eventBus = EventBusFactory.buildEventBus(eventBusName);
    }

    public void run() throws Exception {
        if(eventBus==null){
            eventBus=EventBusFactory.getEventBus();
        }
        startTime = System.currentTimeMillis();
        eventBus.register(builder);
        eventBus.register(playerInput);
        boolean playOn=true;
        while(playOn){
            eventBus.post(new NewGameEvent());
            GameManager manager = new GameManager().withSettings(settings);
            manager.setEventBusName(eventBus.identifier());
            playGame(builder,  manager);
            if(!settings.isLearnSelf() || settings.isVerbose())
                System.out.println("Nowa gra? (Y/N)");
            playOn="y".equalsIgnoreCase(readInput(manager, learnSettings, true));
        }
        if(!settings.isLearnSelf() || settings.isVerbose())
            System.out.println("Thank you.");
    }

    private String readInput(GameManager manager, LearnSettings learnSettings, final boolean playOnDecision) {
        if(!playOnDecision){
            return playerInput.readInput();
        } else{
            if(manager!=null && settings.isLearnSelf() && learnSettings!=null){
                //decide if the another game should be played
                gamesPlayed++;
//                double p = (double)(builder.getDecisionTree().countAllNodes(builder.getDecisionTree().getRootNode()))/(double)(manager.getMaxDecisionTreeNodes());
//                if(settings.isVerbose())
//                    System.out.println("Current percentage: "+ p+", number of all nodes:" + manager.getMaxDecisionTreeNodes());
                if((learnSettings.getSecondsToFinish()==0 || (System.currentTimeMillis()-startTime)<learnSettings.getSecondsToFinish()*1000)){
                    return "y";
                } else {
                    //decisionTreeWriter.save(Settings.DECISION_TREE_FILE_NAME+ eventBus.identifier(), builder.getDecisionTree().getRootNode()); //This has to go to main method
                    return "n";
                }
            }
            byte[] b = new byte[1];
            try {
                System.in.read(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new String(b);
        }
    }

    private DecisionTreeBuilder buildDecisionTreeBuilder(){
        return new DecisionTreeBuilder(new DecisionTree(new TicTacToeNode()));
    }

    private void playGame(DecisionTreeBuilder builder, GameManager gm) throws Exception {
        RandomRetrier randomRetrier = new RandomRetrier(gm.getBoard().size());
        RandomWithCandidatesRetrier randomWithCandidatesRetrier = new RandomWithCandidatesRetrier(gm.getBoard().size());
        randomRetrier.setSettings(settings);
        randomWithCandidatesRetrier.setSettings(settings);
        while(!gm.isGameOver()){
            long currentTime = System.currentTimeMillis();
            if(currentTime-saveTime > settings.getSaveIntervalMillis()){
                saveTime=currentTime;
                System.out.println("Saving temp file. Current nodes number: "+ builder.getDecisionTree().countAllNodes(
                        builder.getDecisionTree().getRootNode()
                ));
                decisionTreeWriter.save(settings.TEMPORARY_FILE_NAME+ eventBus.identifier(), builder.getDecisionTree().getRootNode());
            }
            int cp = gm.getCurrentPlayer();
            if(!settings.isLearnSelf()){
                gm.printOutBoard();
            }

            while (cp == gm.getCurrentPlayer() && !gm.isGameOver()) {
                if(!settings.isLearnSelf())
                    System.out.println("Make a move (enter coordinates): ");
                playerMakesMove(readInput(null, learnSettings, false), gm);
            }

            if (!gm.isGameOver()) {
                computerMakesMove(builder, gm, randomRetrier, randomWithCandidatesRetrier);
            }

        }
        if(!settings.isLearnSelf()){
            gm.printOutBoard();
        }
    }

    private void computerMakesMove(DecisionTreeBuilder builder, GameManager gm, RandomRetrier randomRetrier, RandomWithCandidatesRetrier randomWithCandidatesRetrier) throws Exception {
        if(settings.isVerbose()){
            System.out.println("Computer, make a move.");
        }
        TreeNode playerMoveNode=builder.getCurrentNode();
        Retrier currentRetrier;
        if (playerMoveNode!=null) {
            if(settings.isVerbose()){
                System.out.println("Found move in my decision tree.");
            }
            randomWithCandidatesRetrier.setCandidates(LogicHelper.findWinningCandidates((TicTacToeNode) playerMoveNode));
            currentRetrier=randomWithCandidatesRetrier;
        } else {
            if(settings.isVerbose()){
                System.out.println("I don't know this move. Building a new path in my decision tree.");
            }
            currentRetrier=randomRetrier;
        }
        gm.tryPlacingSymbol(gm.getCurrentPlayer(), currentRetrier);
    }

    private void playerMakesMove(final String coordinates, GameManager gm) {
        int currentSymbol;
        int[] coords= getUserCoordinates(coordinates);
        currentSymbol=gm.getCurrentPlayerSymbol();
        gm.placeSymbol(currentSymbol, coords[0], coords[1]);
        if(settings.isVerbose()){
            System.out.println("Current player:" + gm.getCurrentPlayer());
        }
    }

    private int[] getUserCoordinates(final String coords) {
        String[] coordinates = coords.split(",");
        if(!settings.isLearnSelf())
            Arrays.stream(coordinates).forEach(System.out::println);
        return new int[] {Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
    }
}
