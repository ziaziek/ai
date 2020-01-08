package com.pncomp.ai;

public class Settings {

    public static final String DECISION_TREE_FILE_NAME="dt.xml";
    public static final String TEMPORARY_FILE_NAME = "dt_temp.xml";

    public int getSaveInterval() {
        return saveInterval;
    }

    public void setSaveInterval(int saveInterval) {
        this.saveInterval = saveInterval;
    }

    private int saveInterval = 120000; //3 minutes by default

    public boolean isLearnSelf() {
        return learnSelf;
    }

    public void setLearnSelf(boolean learnSelf) {
        this.learnSelf = learnSelf;
    }

    private boolean learnSelf = true;

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    private boolean verbose= false;
}
