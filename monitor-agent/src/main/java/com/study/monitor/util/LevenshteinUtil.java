package com.study.monitor.util;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class LevenshteinUtil {

    public static double calculateSimilarityScore(String logMessage1, String logMessage2) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int distance = levenshteinDistance.apply(logMessage1, logMessage2);
        int maxLength = Math.max(logMessage1.length(), logMessage2.length());
        return 1 - (double) distance / maxLength;
    }

    public static boolean isSimilarLog(String logMessage1, String logMessage2) {
        return calculateSimilarityScore(logMessage1, logMessage2) >= 0.8;
    }
}
