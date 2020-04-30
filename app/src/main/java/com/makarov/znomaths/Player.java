package com.makarov.znomaths;

public class Player {
    private int triesCount, record, rightAnswersCount;

    public Player() {
        triesCount = 3;
        rightAnswersCount = 0;
    }

    public void increase_triesCount() {
        triesCount++;
    }

    public void decrease_triesCount() {
        triesCount--;
    }

    public int get_triesCount() {
        return triesCount;
    }

    public void increase_rightAnswersCount() {
        rightAnswersCount++;
    }

    public int get_rightAnswersCount() {
        return rightAnswersCount;
    }

}
