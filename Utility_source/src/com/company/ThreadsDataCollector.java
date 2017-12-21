package com.company;
import org.jetbrains.annotations.Contract;

/**
 * Class ThreadsDataCollector and its static properties and methods are shared between
 * all DownloadTask threads and collect results of every task execution
 */

public class ThreadsDataCollector {
    // Private fields
    private static long downloadTimeSum;
    private static double averageSpeedSum;
    private static int downloadedFilesCount;
    private static long totalFilesSize;

    // Public Set properties (Setters)
    public static void setTotalDownloadTime(long downloadTime) {
        synchronized (ThreadsDataCollector.class) {
            downloadTimeSum += downloadTime;
        }
    }

    public static void setAverageSpeedSum(double averageSpeed) {
        synchronized (ThreadsDataCollector.class) {
            averageSpeedSum += averageSpeed;
        }
    }

    public static void setDownloadedFilesCount(int filesCount) {
        synchronized (ThreadsDataCollector.class) {
            downloadedFilesCount += filesCount;
        }
    }

    public static void setTotalFilesSize(long fileSize) {
        synchronized (ThreadsDataCollector.class) {
            totalFilesSize += fileSize;
        }
    }

    // Public Get properties (Getters)
    @Contract(pure = true)
    public static long getDownloadTimeSum() {
        return downloadTimeSum;
    }

    @Contract(pure = true)
    public static double getAverageSpeedSum() {
        return averageSpeedSum;
    }

    @Contract(pure = true)
    public static int getDownloadedFilesCount() {
        return downloadedFilesCount;
    }

    @Contract(pure = true)
    public static long getTotalFilesSize() {
        return totalFilesSize;
    }
}

