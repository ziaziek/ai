package com.pncomp.ai.tictactoe.retriers;

import java.util.List;

public interface Retrier {
    int newPosition() throws Exception;

    int newPosition(List<Integer> freePlaces) throws Exception;
}
