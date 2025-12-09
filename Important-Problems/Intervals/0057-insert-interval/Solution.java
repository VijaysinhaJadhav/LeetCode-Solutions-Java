
## Problems/Arrays-Hashing/0057-insert-interval/Solution.java

```java
/**
 * 57. Insert Interval
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi] 
 * represent the start and end of the ith interval and intervals is sorted in ascending order by starti. 
 * You are also given an interval newInterval = [start, end] that represents the start and end of another interval.
 * Insert newInterval into intervals such that intervals is still sorted in ascending order by starti 
 * and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).
 * 
 * Key Insights:
 * 1. Intervals are already sorted, so we can process them in order
 * 2. Only intervals that overlap with newInterval need to be merged
 * 3. Three-phase approach: add non-overlapping before, merge overlapping, add non-overlapping after
 * 4. An interval overlaps if: interval.start <= newInterval.end AND interval.end >= newInterval.start
 * 
 * Approach (Three-phase linear scan):
 * 1. Add all intervals that end before new interval starts
 * 2. Merge all intervals that overlap with new interval
 * 3. Add all intervals that start after new interval ends
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(n) for result, O(1) extra space
 * 
 * Tags: Array, Intervals, Sorting
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Three-phase Linear Scan - RECOMMENDED
     * O(n) time, O(n) space (for result)
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int n = intervals.length;
        int i = 0;
        
        // Phase 1: Add all intervals that end before newInterval starts
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }
        
        // Phase 2: Merge all overlapping intervals
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        result.add(newInterval);
        
        // Phase 3: Add all intervals that start after newInterval ends
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Approach 2: Binary Search for Insertion Point
     * O(n) time, O(n) space - Binary search doesn't improve overall complexity
     */
    public int[][] insertBinarySearch(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        
        // Find insertion point using binary search
        int left = 0, right = intervals.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (intervals[mid][0] < newInterval[0]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        int insertPos = left;
        
        // Add all intervals before insertion point
        for (int i = 0; i < insertPos; i++) {
            result.add(intervals[i]);
        }
        
        // Add or merge new interval
        if (result.isEmpty() || result.get(result.size() - 1)[1] < newInterval[0]) {
            result.add(newInterval);
        } else {
            int[] last = result.get(result.size() - 1);
            last[1] = Math.max(last[1], newInterval[1]);
        }
        
        // Merge with remaining intervals
        for (int i = insertPos; i < intervals.length; i++) {
            int[] last = result.get(result.size() - 1);
            if (intervals[i][0] <= last[1]) {
                last[1] = Math.max(last[1], intervals[i][1]);
            } else {
                result.add(intervals[i]);
            }
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Approach 3: Direct Insertion and Merge
     * O(n) time, O(n) space - Simple but less efficient
     */
    public int[][] insertDirect(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        
        // Add all intervals and new interval
        for (int[] interval : intervals) {
            result.add(interval);
        }
        result.add(newInterval);
        
        // Sort intervals
        Collections.sort(result, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Merge intervals (similar to Merge Intervals problem)
        List<int[]> merged = new ArrayList<>();
        for (int[] interval : result) {
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
                merged.add(interval);
            } else {
                int[] last = merged.get(merged.size() - 1);
                last[1] = Math.max(last[1], interval[1]);
            }
        }
        
        return merged.toArray(new int[merged.size()][]);
    }
    
    /**
     * Approach 4: In-place Merge (Optimized)
     * O(n) time, O(1) extra space (modifies input)
     */
    public int[][] insertInPlace(int[][] intervals, int[] newInterval) {
        // This approach modifies the input array (not recommended for interviews)
        // but demonstrates how to minimize space usage
        
        int n = intervals.length;
        int i = 0;
        
        // Count how many intervals we need to merge
        int mergeCount = 0;
        while (i < n && intervals[i][1] < newInterval[0]) {
            i++;
        }
        
        int startPos = i;
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            mergeCount++;
            i++;
        }
        
        // Create result array with correct size
        int[][] result = new int[n - mergeCount + 1][2];
        
        // Copy non-overlapping before
        System.arraycopy(intervals, 0, result, 0, startPos);
        
        // Add merged interval
        result[startPos] = newInterval;
        
        // Copy non-overlapping after
        System.arraycopy(intervals, startPos + mergeCount, result, startPos + 1, n - startPos - mergeCount);
        
        return result;
    }
    
    /**
     * Helper method to check if two intervals overlap
     */
    private boolean overlaps(int[] a, int[] b) {
        return Math.max(a[0], b[0]) <= Math.min(a[1], b[1]);
    }
    
    /**
     * Helper method to visualize the insertion process
     */
    private void visualizeInsertion(int[][] intervals, int[] newInterval) {
        System.out.println("\nInsert Interval Visualization:");
        System.out.println("Original intervals: " + Arrays.deepToString(intervals));
        System.out.println("New interval: " + Arrays.toString(newInterval));
        
        List<int[]> result = new ArrayList<>();
        int n = intervals.length;
        int i = 0;
        
        System.out.println("\nPhase 1: Add intervals that end before new interval starts");
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            System.out.println("  Add: " + Arrays.toString(intervals[i]) + " (ends before new interval starts)");
            i++;
        }
        
        System.out.println("\nPhase 2: Merge overlapping intervals");
        int[] merged = newInterval.clone();
        boolean mergedAny = false;
        while (i < n && intervals[i][0] <= merged[1]) {
            merged[0] = Math.min(merged[0], intervals[i][0]);
            merged[1] = Math.max(merged[1], intervals[i][1]);
            System.out.println("  Merge with: " + Arrays.toString(intervals[i]) + 
                             " -> New merged: [" + merged[0] + ", " + merged[1] + "]");
            i++;
            mergedAny = true;
        }
        result.add(merged);
        if (!mergedAny) {
            System.out.println("  No overlaps found, add new interval as is");
        }
        
        System.out.println("\nPhase 3: Add remaining intervals");
        while (i < n) {
            result.add(intervals[i]);
            System.out.println("  Add: " + Arrays.toString(intervals[i]) + " (starts after new interval ends)");
            i++;
        }
        
        System.out.println("\nFinal result: " + result.toString());
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Insert Interval Solution:");
        System.out.println("=================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[][] intervals1 = {{1,3},{6,9}};
        int[] newInterval1 = {2,5};
        int[][] expected1 = {{1,5},{6,9}};
        
        long startTime = System.nanoTime();
        int[][] result1a = solution.insert(intervals1, newInterval1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result1b = solution.insertBinarySearch(intervals1, newInterval1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result1c = solution.insertDirect(intervals1, newInterval1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = Arrays.deepEquals(result1a, expected1);
        boolean test1b = Arrays.deepEquals(result1b, expected1);
        boolean test1c = Arrays.deepEquals(result1c, expected1);
        
        System.out.println("Three-phase: " + Arrays.deepToString(result1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Binary Search: " + Arrays.deepToString(result1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Direct: " + Arrays.deepToString(result1c) + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeInsertion(intervals1, newInterval1);
        
        // Test case 2: Multiple overlaps
        System.out.println("\nTest 2: Multiple overlaps");
        int[][] intervals2 = {{1,2},{3,5},{6,7},{8,10},{12,16}};
        int[] newInterval2 = {4,8};
        int[][] expected2 = {{1,2},{3,10},{12,16}};
        
        int[][] result2a = solution.insert(intervals2, newInterval2);
        System.out.println("Multiple overlaps: " + Arrays.deepToString(result2a) + " - " + 
                         (Arrays.deepEquals(result2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Empty intervals
        System.out.println("\nTest 3: Empty intervals");
        int[][] intervals3 = {};
        int[] newInterval3 = {5,7};
        int[][] expected3 = {{5,7}};
        
        int[][] result3a = solution.insert(intervals3, newInterval3);
        System.out.println("Empty intervals: " + Arrays.deepToString(result3a) + " - " + 
                         (Arrays.deepEquals(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: New interval at beginning
        System.out.println("\nTest 4: New interval at beginning");
        int[][] intervals4 = {{2,3},{5,7}};
        int[] newInterval4 = {0,1};
        int[][] expected4 = {{0,1},{2,3},{5,7}};
        
        int[][] result4a = solution.insert(intervals4, newInterval4);
        System.out.println("At beginning: " + Arrays.deepToString(result4a) + " - " + 
                         (Arrays.deepEquals(result4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: New interval at end
        System.out.println("\nTest 5: New interval at end");
        int[][] intervals5 = {{1,2},{3,4}};
        int[] newInterval5 = {5,6};
        int[][] expected5 = {{1,2},{3,4},{5,6}};
        
        int[][] result5a = solution.insert(intervals5, newInterval5);
        System.out.println("At end: " + Arrays.deepToString(result5a) + " - " + 
                         (Arrays.deepEquals(result5a, expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: New interval covers all
        System.out.println("\nTest 6: New interval covers all");
        int[][] intervals6 = {{1,3},{4,6},{7,9}};
        int[] newInterval6 = {0,10};
        int[][] expected6 = {{0,10}};
        
        int[][] result6a = solution.insert(intervals6, newInterval6);
        System.out.println("Covers all: " + Arrays.deepToString(result6a) + " - " + 
                         (Arrays.deepEquals(result6a, expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: No overlap
        System.out.println("\nTest 7: No overlap");
        int[][] intervals7 = {{1,2},{5,6}};
        int[] newInterval7 = {3,4};
        int[][] expected7 = {{1,2},{3,4},{5,6}};
        
        int[][] result7a = solution.insert(intervals7, newInterval7);
        System.out.println("No overlap: " + Arrays.deepToString(result7a) + " - " + 
                         (Arrays.deepEquals(result7a, expected7) ? "PASSED" : "FAILED"));
        
        // Test case 8: Single interval merge
        System.out.println("\nTest 8: Single interval merge");
        int[][] intervals8 = {{1,5}};
        int[] newInterval8 = {2,3};
        int[][] expected8 = {{1,5}};
        
        int[][] result8a = solution.insert(intervals8, newInterval8);
        System.out.println("Single merge: " + Arrays.deepToString(result8a) + " - " + 
                         (Arrays.deepEquals(result8a, expected8) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Three-phase: " + time1a + " ns");
        System.out.println("  Binary Search: " + time1b + " ns");
        System.out.println("  Direct: " + time1c + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[][] largeIntervals = new int[10000][2];
        for (int i = 0; i < 10000; i++) {
            largeIntervals[i][0] = i * 2;
            largeIntervals[i][1] = i * 2 + 1;
        }
        int[] largeNewInterval = {5000, 15000};
        
        startTime = System.nanoTime();
        int[][] result10a = solution.insert(largeIntervals, largeNewInterval);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result10b = solution.insertBinarySearch(largeIntervals, largeNewInterval);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result10c = solution.insertDirect(largeIntervals, largeNewInterval);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (10,000 intervals):");
        System.out.println("  Three-phase: " + time10a + " ns");
        System.out.println("  Binary Search: " + time10b + " ns");
        System.out.println("  Direct: " + time10c + " ns");
        
        // Verify all approaches produce the same result
        boolean allEqual = Arrays.deepEquals(result10a, result10b) && 
                          Arrays.deepEquals(result10a, result10c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("THREE-PHASE LINEAR SCAN EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Intervals are already sorted, so we can process them in three phases:");
        System.out.println("1. Add all intervals that end before new interval starts");
        System.out.println("2. Merge all intervals that overlap with new interval");
        System.out.println("3. Add all intervals that start after new interval ends");
        
        System.out.println("\nOverlap Condition:");
        System.out.println("Two intervals [a,b] and [c,d] overlap if:");
        System.out.println("  a <= d AND c <= b");
        System.out.println("Or equivalently: max(a,c) <= min(b,d)");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. Phase 1: These intervals cannot overlap with new interval");
        System.out.println("2. Phase 2: We merge by taking min(start) and max(end)");
        System.out.println("3. Phase 3: These intervals cannot overlap with merged interval");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Three-phase Linear Scan (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(n) - For result list (O(1) extra space)");
        System.out.println("   How it works:");
        System.out.println("     - Phase 1: Add intervals ending before new interval");
        System.out.println("     - Phase 2: Merge overlapping intervals with new interval");
        System.out.println("     - Phase 3: Add intervals starting after merged interval");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time complexity");
        System.out.println("     - Clear and intuitive logic");
        System.out.println("     - Handles all edge cases well");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of overlap condition");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Binary Search Approach:");
        System.out.println("   Time: O(n) - Binary search + linear merge");
        System.out.println("   Space: O(n) - For result list");
        System.out.println("   How it works:");
        System.out.println("     - Use binary search to find insertion point");
        System.out.println("     - Add intervals before insertion point");
        System.out.println("     - Merge new interval with overlapping intervals");
        System.out.println("     - Add remaining intervals");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates binary search knowledge");
        System.out.println("     - Efficient for finding insertion point");
        System.out.println("   Cons:");
        System.out.println("     - Doesn't improve overall O(n) complexity");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: When asked about binary search applications");
        
        System.out.println("\n3. Direct Insertion and Merge:");
        System.out.println("   Time: O(n log n) - Due to sorting");
        System.out.println("   Space: O(n) - For result list");
        System.out.println("   How it works:");
        System.out.println("     - Add new interval to the list");
        System.out.println("     - Sort all intervals");
        System.out.println("     - Merge overlapping intervals (like problem 56)");
        System.out.println("   Pros:");
        System.out.println("     - Simple and straightforward");
        System.out.println("     - Reuses Merge Intervals solution");
        System.out.println("   Cons:");
        System.out.println("     - O(n log n) not optimal");
        System.out.println("     - Doesn't leverage sorted input");
        System.out.println("   Best for: When simplicity is prioritized over performance");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Three-phase approach - it's the expected optimal solution");
        System.out.println("2. Clearly explain the three phases and overlap condition");
        System.out.println("3. Draw examples to visualize the merging process");
        System.out.println("4. Handle edge cases: empty intervals, no overlap, complete overlap");
        System.out.println("5. Discuss time/space complexity trade-offs");
        System.out.println("6. Mention alternative approaches if time permits");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
