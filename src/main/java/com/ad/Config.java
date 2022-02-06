package com.ad;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {
    public static int canvasWidth = 446;
    public static int canvasHeight = 428;
    public static String saveToDir;
    public static String URL = "https://linkedin.com";

    public static int Depth() {
        return (int) (Math.random() * (maxScrollDepth - minScrollDepth) + minScrollDepth);
    }

    public static int minScrollDepth = 30 * canvasHeight;

    public static int maxScrollDepth = 70 * canvasHeight;

    public static int canvasJump = (int) (canvasHeight * 0.90);

    public static int minShortTimeSleep = 5; // 4 seconds

    public static int maxShortTimeSleep = 7; // 5 seconds

    public static int minLongTimeSleep = 35; // 10 minutues

    public static int maxLongTimeSleep = 60; // 15 minutes;

    private static String[] defaultWords = { "hiring", "referral", "refer", "referrals", "DM", "opportunity", "2020",
            "job opening", "career", "recruiting", "hire", "interested", "opening", "looking for", "new positions",
            "recruiter" };

    public static ArrayList<String> lookupWords = new ArrayList<>(Arrays.asList(defaultWords));

    public static long longTimeSleep() {
        return (long) (Math.random() * (maxLongTimeSleep - minLongTimeSleep) + minLongTimeSleep) * 1000L * 60L;
    }

    public static long shortTimeSleep() {
        return (long) (Math.random() * (maxShortTimeSleep - minShortTimeSleep) + minShortTimeSleep) * 1000L;
    }

    public static void addToLookupWords(String s) {
        String[] tmp = s.split(",");
        lookupWords.clear();
        for (String i : tmp) {
            i = i.trim();
            if (i.length() != 0)
                lookupWords.add(i.trim());
        }
    }
}
