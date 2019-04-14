package com.pncomp.ai.tictactoe;

public class LearnSettings {

    public double getPercentageOfNodes() {
        return percentageOfNodes;
    }

    public void setPercentageOfNodes(double percentageOfNodes) {
        this.percentageOfNodes = percentageOfNodes;
    }

    public long getSecondsToFinish() {
        return secondsToFinish;
    }

    public void setSecondsToFinish(long secondsToFinish) {
        this.secondsToFinish = secondsToFinish;
    }

    private double percentageOfNodes;
    private long secondsToFinish;

}
