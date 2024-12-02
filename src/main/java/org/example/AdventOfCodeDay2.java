package org.example;
import java.io.*;
import java.util.*;

public class AdventOfCodeDay2 {
    public static void main(String[] args) {
        try {
            // Read the input file containing reports
            List<String> reports = readReportsFromFile("reports.txt");

            // Count the number of safe reports
            int safeCount = 0;
            for (String report : reports) {
                if (isSafeReport(report) || canBeSafeWithOneRemoval(report)) {
                    safeCount++;
                }
            }

            // Print the result
            System.out.println("Number of safe reports (with Problem Dampener): " + safeCount);
        } catch (IOException e) {
            System.err.println("Error reading reports: " + e.getMessage());
        }
    }

    /**
     * Reads reports from a file, one report per line.
     *
     * @param fileName the name of the file containing reports
     * @return a list of report strings
     * @throws IOException if an error occurs during file reading
     */
    private static List<String> readReportsFromFile(String fileName) throws IOException {
        List<String> reports = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                reports.add(line.trim());
            }
        }
        return reports;
    }

    /**
     * Determines if a report is safe based on the rules:
     * 1. Levels are either all increasing or all decreasing.
     * 2. Any two adjacent levels differ by at least 1 and at most 3.
     *
     * @param report the report string containing levels separated by spaces
     * @return true if the report is safe, false otherwise
     */
    private static boolean isSafeReport(String report) {
        String[] parts = report.split(" ");
        int[] levels = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();

        boolean increasing = true;
        boolean decreasing = true;

        for (int i = 1; i < levels.length; i++) {
            int diff = levels[i] - levels[i - 1];

            // Check if the difference is valid (between 1 and 3)
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            // Check if the report is consistently increasing or decreasing
            if (diff < 0) {
                increasing = false;
            } else if (diff > 0) {
                decreasing = false;
            }
        }

        // The report must be either all increasing or all decreasing
        return increasing || decreasing;
    }

    /**
     * Determines if a report can be made safe by removing a single level.
     *
     * @param report the report string containing levels separated by spaces
     * @return true if the report can be safe with one removal, false otherwise
     */
    private static boolean canBeSafeWithOneRemoval(String report) {
        String[] parts = report.split(" ");
        int[] levels = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();

        // Try removing each level one by one
        for (int i = 0; i < levels.length; i++) {
            // Create a new array without the i-th element
            int[] modifiedLevels = new int[levels.length - 1];
            for (int j = 0, k = 0; j < levels.length; j++) {
                if (j != i) {
                    modifiedLevels[k++] = levels[j];
                }
            }

            // Check if the modified report is safe
            if (isSafeReport(convertArrayToString(modifiedLevels))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Converts an array of integers to a space-separated string.
     *
     * @param array the array of integers
     * @return a space-separated string
     */
    private static String convertArrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int num : array) {
            sb.append(num).append(" ");
        }
        return sb.toString().trim();
    }
}
