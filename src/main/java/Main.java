import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.TreeNode;
import com.pncomp.ai.tictactoe.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Random random = new Random();
    static DecisionTreeBuilder builder;

    public static void main(String[] args){
        builder = new DecisionTreeBuilder(new DecisionTree(new TicTacToeNode()));
        Scanner scanner = new Scanner(System.in);
        EventBusFactory.getEventBus().register(builder);
        boolean playOn=true;
        while(playOn){
            playGame(builder, scanner, new GameManager());
            System.out.println("Nowa gra? (Y/N)");
            playOn="y".equalsIgnoreCase(scanner.next());
        }
        System.out.printf("Thank you.");
    }

    private static void playGame(DecisionTreeBuilder builder, Scanner scanner, GameManager gm) {
        int cp = gm.getCurrentPlayer();
        while(!gm.isGameOver()){
            gm.printOutBoard();
            int position=-1;
            //human makes a move
            while (cp == gm.getCurrentPlayer() && !gm.isGameOver()) {
                System.out.println("Make a move (enter coordinates): ");
                int[] coords= getUserCoordinates(scanner);
                try {
                    position=gm.convertCoordinates(coords[0], coords[1]);
                    gm.placeSymbol(gm.getCurrentPlayerSymbol(), coords[0], coords[1]);
                    System.out.println("Current player:" + gm.getCurrentPlayer());
                } catch (CoordinatesException e) {
                    e.printStackTrace();
                }
            }


            if (!gm.isGameOver()) {
                System.out.println("Computer, make a move.");
                //computer makes a move
                if (LogicHelper.isMoveNodeFound(position, gm.getCurrentPlayer(), builder.getCurrentNode())) {
                    System.out.println("Found move in my decision tree.");
                    List<TreeNode> candidates = LogicHelper.findWinningCandidates(builder.getCurrentNode());
                    cp = gm.getCurrentPlayer();
                    while (cp == gm.getCurrentPlayer() || !gm.isGameOver()) {
                        if (!candidates.isEmpty()) {
                            System.out.println("Selecting a winning candidate.");
                            position = findRandomCandidate(candidates);
                        } else {
                            System.out.println("No winning candidate found. Making a random move.");
                            position = makeRandomMove(gm.getBoard().size());
                        }
                    }
                    gm.placeSymbol(gm.getCurrentPlayerSymbol(), position);
                } else {
                    System.out.println("I don't know this move. Building a new path in my decision tree.");
                    builder.getCurrentNode().addChild(builder.buildNewNode(cp, position));
                    position = makeRandomMove(gm.getBoard().size());
                    gm.placeSymbol(gm.getCurrentPlayerSymbol(), position);
                    builder.getCurrentNode().addChild(builder.buildNewNode(gm, position));
                }
            }

        }
        //add the last position as a result node, if it doesn't exist yet
        gm.printOutBoard();
    }

    private static int findRandomCandidate(List<TreeNode> candidates) {

        return ((TicTacToeNode)candidates.get(random.nextInt(candidates.size()))).getMove()[0];

    }

    private static int[] getUserCoordinates(Scanner scanner) {
        String[] coordinates = scanner.next().split(",");
        Arrays.stream(coordinates).forEach(System.out::println);
        return new int[] {Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])};
    }

    private static int makeRandomMove(int boardSize) {
        return  random.nextInt(boardSize);
    }

}


