package com.pncomp.ai;

public class Settings {

    public static final String DECISION_TREE_FILE_NAME="dt.xml";
    public static final String TEMPORARY_FILE_NAME = "dt_temp";

    public int getSaveIntervalMillis() {
        return saveIntervalMillis;
    }

    public void setSaveIntervalMillis(int saveIntervalMillis) {
        this.saveIntervalMillis = saveIntervalMillis;
    }

    private int saveIntervalMillis = 120000;

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
