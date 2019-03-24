import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;
import com.pncomp.ai.tictactoe.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static DecisionTreeBuilder builder;

    public static void main(String[] args) throws Exception {
        builder = new DecisionTreeBuilder(new DecisionTree(new TicTacToeNode()));
        Scanner scanner = new Scanner(System.in);
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

    private static void playGame(DecisionTreeBuilder builder, Scanner scanner, GameManager gm) throws Exception {
        RandomRetrier randomRetrier = new RandomRetrier(gm);
        RandomWithCandidatesRetrier randomWithCandidatesRetrier = new RandomWithCandidatesRetrier(gm);
        while(!gm.isGameOver()){
            int cp = gm.getCurrentPlayer();
            gm.printOutBoard();
            int currentSymbol=0;
            //human makes a move
            while (cp == gm.getCurrentPlayer() && !gm.isGameOver()) {
                System.out.println("Make a move (enter coordinates): ");
                int[] coords= getUserCoordinates(scanner);
                    currentSymbol=gm.getCurrentPlayerSymbol();
                    gm.placeSymbol(currentSymbol, coords[0], coords[1]);
                    System.out.println("Current player:" + gm.getCurrentPlayer());
            }


            if (!gm.isGameOver()) {
                System.out.println("Computer, make a move.");
                //computer makes a move
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

        }
        gm.printOutBoard();
    }

    private static int[] getUserCoordinates(Scanner scanner) {
        String[] coordinates = scanner.next().split(",");
        Arrays.stream(coordinates).forEach(System.out::println);
        return new int[] {Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
    }



}


