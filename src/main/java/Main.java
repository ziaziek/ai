
import com.pncomp.ai.DecisionTree;
import com.pncomp.ai.io.DecisionTreeFileReader;
import com.pncomp.ai.tictactoe.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        LearnSettings learnSettings= new LearnSettings();
        learnSettings.setPercentageOfNodes(80);
        new GameRunner(new AutoPlayer(GameManager.DEFAULT_BOARD_SIZE),
                new DecisionTreeBuilder(
                        new DecisionTree(new DecisionTreeFileReader<TicTacToeNode>().read())), learnSettings).run();
    }

    static class SystemInPlayer implements PlayerInput{

        private Scanner scanner = new Scanner(System.in);

        @Override
        public String readInput(){
            return scanner.next();
        }
    }
}


