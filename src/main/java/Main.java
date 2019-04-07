
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.io.DecisionTreeFileReader;
import com.pncomp.ai.tictactoe.DecisionTreeBuilder;
import com.pncomp.ai.tictactoe.GameRunner;
import com.pncomp.ai.tictactoe.PlayerInput;
import com.pncomp.ai.tictactoe.TicTacToeNode;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        new GameRunner(new SystemInPlayer(),
                new DecisionTreeBuilder(
                        new DecisionTree(new DecisionTreeFileReader<TicTacToeNode>().read()))).run();
    }

    static class SystemInPlayer implements PlayerInput{

        private Scanner scanner = new Scanner(System.in);

        @Override
        public String readInput(){
            return scanner.next();
        }
    }
}


