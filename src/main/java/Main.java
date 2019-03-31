
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.io.DecisionTreeFileReader;
import com.pncomp.ai.tictactoe.DecisionTreeBuilder;
import com.pncomp.ai.tictactoe.GameRunner;
import com.pncomp.ai.tictactoe.TicTacToeNode;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        new GameRunner(new Scanner(System.in),
                new DecisionTreeBuilder(
                        new DecisionTree(new DecisionTreeFileReader<TicTacToeNode>().read()))).run();
    }
}


