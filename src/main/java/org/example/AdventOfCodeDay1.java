package org.example;
import java.io.*;
import java.util.*;

public class AdventOfCodeDay1 {

    public static void main(String[] args) {
        try {
            // Read the left and right lists from files
            List<Integer> leftList = readListFromFile("left.txt");
            List<Integer> rightList = readListFromFile("right.txt");

            // Part I: Calculate total distance
            int totalDistance = calculateTotalDistance(leftList, rightList);
            System.out.println("Total Distance (Part I): " + totalDistance);

            // Part II: Calculate similarity score
            int similarityScore = calculateSimilarityScore(leftList, rightList);
            System.out.println("Similarity Score (Part II): " + similarityScore);

        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    // Method to read a list of integers from a file
    private static List<Integer> readListFromFile(String fileName) throws IOException {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(Integer.parseInt(line.trim()));
            }
        }
        return list;
    }

    // Part I: Calculate total distance
    private static int calculateTotalDistance(List<Integer> leftList, List<Integer> rightList) {
        // Sort both lists
        Collections.sort(leftList);
        Collections.sort(rightList);

        // Calculate the total distance
        int totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        return totalDistance;
    }

    // Part II: Calculate similarity score
    private static int calculateSimilarityScore(List<Integer> leftList, List<Integer> rightList) {
        // Create a map to count occurrences in the right list
        Map<Integer, Integer> rightListCounts = new HashMap<>();
        for (int num : rightList) {
            rightListCounts.put(num, rightListCounts.getOrDefault(num, 0) + 1);
        }

        // Calculate the similarity score
        int similarityScore = 0;
        for (int num : leftList) {
            similarityScore += num * rightListCounts.getOrDefault(num, 0);
        }
        return similarityScore;
    }
}
