package com.haulmont.testtask.data.services.util;

public class Statistic {

    private int countPlan;
    private int countDone;
    private int countClientOk;

    public Statistic() {
    }

    public Statistic(int countPlan, int countDone, int countClientOk) {
        this.countPlan = countPlan;
        this.countDone = countDone;
        this.countClientOk = countClientOk;
    }

    public int getCountPlan() {
        return countPlan;
    }

    public void setCountPlan(int countPlan) {
        this.countPlan = countPlan;
    }

    public int getCountDone() {
        return countDone;
    }

    public void setCountDone(int countDone) {
        this.countDone = countDone;
    }

    public int getCountClientOk() {
        return countClientOk;
    }

    public void setCountClientOk(int countClientOk) {
        this.countClientOk = countClientOk;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "countPlan=" + countPlan +
                ", countDone=" + countDone +
                ", countClientOk=" + countClientOk +
                '}';
    }
}
