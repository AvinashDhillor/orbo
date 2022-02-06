package com.ad.bot.actions;

public class Scroll {
    private int start;
    private int limit;

    public Scroll(int limit) {
        start = 0;
        this.limit = limit;
    }

    public int by(int increaseBy) {
        int tmp = start;
        start += increaseBy;
        if (tmp > limit)
            start = 0;
        return tmp;
    }
}
