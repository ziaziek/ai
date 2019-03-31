package com.pncomp.ai.tictactoe;

import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;
import com.pncomp.ai.tictactoe.events.EventBusFactory;
import com.pncomp.ai.tictactoe.events.NewGameEvent;
import com.pncomp.ai.tictactoe.retriers.RandomRetrier;
import com.pncomp.ai.tictactoe.retriers.RandomWithCandidatesRetrier;
import com.pncomp.ai.tictactoe.retriers.Retrier;

import java.util.Arrays;
import java.util.Scanner;

public class GameRunner {

    private DecisionTreeBuilder builder;

    private final Scanner scanner;

    public GameRunner(Scanner scanner) {
        this(scanner, null);
    }

    public GameRunner(final Scanner scanner, final DecisionTreeBuilder decisionTreeBuilder){
        this.scanner = scanner;
        if (decisionTreeBuilder==null){
            builder = buildDecisionTreeBuilder();
        } else {
            builder = decisionTreeBuilder;
        }
    }

    public void run() throws Exception {
        EventBusFactory.getEventBus().register(builder);
        boolean playOn=true;
        while(playOn){
            EventBusFactory.getEventBus().post(new NewGameEvent());
            playGame(builder, scanner, new GameManager());
            System.out.println("Nowa gra? (Y/N)");
            playOn="y".equalsIgnoreCase(scanner.next());
        }
        System.out.printf("Thank you.");
    }

    private DecisionTreeBuilder buildDecisionTreeBuilder(){
        return new DecisionTreeBuilder(new DecisionTree(new TicTacToeNode()));
    }

    private void playGame(DecisionTreeBuilder builder, Scanner scanner, GameManager gm) throws Exception {
        RandomRetrier randomRetrier = new RandomRetrier(gm);
        RandomWithCandidatesRetrier randomWithCandidatesRetrier = new RandomWithCandidatesRetrier(gm);
        while(!gm.isGameOver()){
            int cp = gm.getCurrentPlayer();
            gm.printOutBoard();

            while (cp == gm.getCurrentPlayer() && !gm.isGameOver()) {
                playerMakesMove(scanner, gm);
            }

            if (!gm.isGameOver()) {
                computerMakesMove(builder, gm, randomRetrier, randomWithCandidatesRetrier);
            }

        }
        gm.printOutBoard();
    }

    private void computerMakesMove(DecisionTreeBuilder builder, GameManager gm, RandomRetrier randomRetrier, RandomWithCandidatesRetrier randomWithCandidatesRetrier) throws Exception {
        System.out.println("Computer, make a move.");
        TreeNode playerMoveNode=builder.getCurrentNode();
        Retrier currentRetrier;
        if (playerMoveNode!=null) {
            System.out.println("Found move in my decision tree.");
            randomWithCandidatesRetrier.setCandidates(LogicHelper.findWinningCandidates((TicTacToeNode) playerMoveNode));
            currentRetrier=randomWithCandidatesRetrier;
        } else {
            System.out.println("I don't know this move. Building a new path in my decision tree.");
            currentRetrier=randomRetrier;
        }
        gm.tryPlacingSymbol(gm.getCurrentPlayer(), currentRetrier);
    }

    private void playerMakesMove(Scanner scanner, GameManager gm) {
        int currentSymbol;
        System.out.println("Make a move (enter coordinates): ");
        int[] coords= getUserCoordinates(scanner);
        currentSymbol=gm.getCurrentPlayerSymbol();
        gm.placeSymbol(currentSymbol, coords[0], coords[1]);
        System.out.println("Current player:" + gm.getCurrentPlayer());
    }

    private int[] getUserCoordinates(Scanner scanner) {
        String[] coordinates = scanner.next().split(",");
        Arrays.stream(coordinates).forEach(System.out::println);
        return new int[] {Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
    }
}
