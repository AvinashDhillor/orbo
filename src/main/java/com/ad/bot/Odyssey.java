package com.ad.bot;

import com.ad.Config;
import com.ad.bot.actions.Scroll;
import com.ad.utils.Logger;

import javafx.concurrent.Task;

public class Odyssey extends Task<String> {

    Scroll scroll;
    private int limit;
    private final int increaseBy;

    public Odyssey(int limit, int increaseBy) {
        this.limit = limit;
        scroll = new Scroll(limit);
        this.increaseBy = increaseBy;
    }

    final private long scrollSleep = Config.shortTimeSleep(), reloadSleep = Config.longTimeSleep();

    @Override
    public String call() throws Exception {
        while (true) {
            int n = scroll.by(increaseBy);
            updateMessage("scroll:" + n);
            Logger.log("Scrolling to " + n + " 👇");
            if (n >= limit) {
                updateMessage("reload:");
                Logger.log("Reloading 🔃");
                Logger.log("Going to Sleep for " + (reloadSleep / 60000) + " minutes 💤");
                Thread.sleep(reloadSleep);
                updateMessage("reload:");
            }
            Thread.sleep(scrollSleep);
        }
    }
}
