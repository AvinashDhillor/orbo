package com.ad.utils;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

public class Logger extends Task<String> {
    static List<String> logs = new ArrayList<>();
    private volatile int prevLen = 0;

    public static void log(String log) {
        logs.add(log);
    }

    @Override
    protected String call() throws Exception {
        while (true) {
            if (prevLen < logs.size()) {
                updateMessage(logs.get(prevLen++));
            } else {
                Thread.sleep(1000);
            }
        }
    }

}
