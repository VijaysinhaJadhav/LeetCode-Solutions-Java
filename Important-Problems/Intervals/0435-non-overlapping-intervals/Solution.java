
## Problems/Arrays-Hashing/0435-non-overlapping-intervals/Solution.java

```java
/**
 * 435. Non-overlapping Intervals
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of intervals where intervals[i] = [starti, endi], 
 * return the minimum number of intervals you need to remove to make 
 * the rest of the intervals non-overlapping.
 * 
 * Key Insights:
 * 1. Equivalent to finding maximum number of non-overlapping intervals
 * 2. Minimum removals = Total intervals - Maximum non-overlapping intervals
 * 3. Greedy approach: sort by end time, always pick interval that ends earliest
 * 4. Two intervals overlap if current.start < previous.end
 * 
 * Approach (Greedy - Sort by End Time):
 * 1. Sort intervals by end time
 * 2. Initialize count of non-overlapping intervals to 1 (first interval)
 * 3. For each subsequent interval:
 *    - If it doesn't overlap with last picked interval, add to count
 *    - Otherwise, skip it (implicitly removed)
 * 4. Return total intervals - count
 * 
 * Time Complexity: O(n log n) due to sorting
 * Space Complexity: O(log n) for sorting algorithm
 * 
 * Tags: Array, Dynamic Programming, Greedy, Sorting, Intervals
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Greedy - Sort by End Time - RECOMMENDED
     * O(n log n) time, O(log n) space
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }
        
        // Sort intervals by end time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        
        int nonOverlapCount = 1; // Count of non-overlapping intervals we can keep
        int lastEnd = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            
            // If current interval doesn't overlap with last kept interval
            if (current[0] >= lastEnd) {
                nonOverlapCount++;
                lastEnd = current[1];
            }
            // Else: skip this interval (it would be removed)
        }
        
        return intervals.length - nonOverlapCount;
    }
    
    /**
     * Approach 2: Greedy - Sort by Start Time
     * O(n log n) time, O(log n) space - Alternative greedy approach
     */
    public int eraseOverlapIntervalsSortByStart(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        int removals = 0;
        int lastEnd = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            
            if (current[0] < lastEnd) {
                // Overlap found, we need to remove one interval
                removals++;
                // Remove the interval that ends later (keep the one that ends earlier)
                lastEnd = Math.min(lastEnd, current[1]);
            } else {
                // No overlap, update lastEnd to current interval's end
                lastEnd = current[1];
            }
        }
        
        return removals;
    }
    
    /**
     * Approach 3: Dynamic Programming - Longest Increasing Subsequence style
     * O(n^2) time, O(n) space - Not optimal but demonstrates DP approach
     */
    public int eraseOverlapIntervalsDP(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        int n = intervals.length;
        int[] dp = new int[n]; // dp[i] = max non-overlapping intervals ending at i
        Arrays.fill(dp, 1);
        
        int maxNonOverlap = 1;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If intervals don't overlap
                if (intervals[j][1] <= intervals[i][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxNonOverlap = Math.max(maxNonOverlap, dp[i]);
        }
        
        return n - maxNonOverlap;
    }
    
    /**
     * Approach 4: Greedy with explicit tracking
     * O(n log n) time, O(log n) space - More explicit version
     */
    public int eraseOverlapIntervalsExplicit(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        
        List<int[]> selected = new ArrayList<>();
        selected.add(intervals[0]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] last = selected.get(selected.size() - 1);
            int[] current = intervals[i];
            
            if (current[0] >= last[1]) {
                selected.add(current);
            }
        }
        
        return intervals.length - selected.size();
    }
    
    /**
     * Approach 5: Using Priority Queue (Max Heap for DP optimization)
     * O(n log n) time, O(n) space - For educational purposes
     */
    public int eraseOverlapIntervalsHeap(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }
        
        // Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Min heap to track end times of selected intervals
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.offer(intervals[0][1]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            
            // If current interval starts after the earliest ending interval
            if (current[0] >= heap.peek()) {
                heap.poll(); // Remove the ended interval
            }
            heap.offer(current[1]);
        }
        
        return intervals.length - heap.size();
    }
    
    /**
     * Helper method to check if two intervals overlap
     */
    private boolean overlaps(int[] a, int[] b) {
        return Math.max(a[0], b[0]) < Math.min(a[1], b[1]);
    }
    
    /**
     * Helper method to visualize the greedy selection process
     */
    private void visualizeGreedy(int[][] intervals) {
        System.out.println("\nNon-overlapping Intervals Visualization:");
        System.out.println("Original intervals: " + Arrays.deepToString(intervals));
        
        // Sort by end time
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[1], b[1]));
        System.out.println("Sorted by end time: " + Arrays.deepToString(sorted));
        
        List<int[]> selected = new ArrayList<>();
        selected.add(sorted[0]);
        int lastEnd = sorted[0][1];
        
        System.out.println("\nGreedy Selection Process:");
        System.out.println("Step | Current Interval | Last End | Action");
        System.out.println("-----|------------------|----------|--------");
        
        for (int i = 1; i < sorted.length; i++) {
            int[] current = sorted[i];
            String action;
            
            if (current[0] >= lastEnd) {
                action = "SELECT - No overlap";
                selected.add(current);
                lastEnd = current[1];
            } else {
                action = "REMOVE - Overlaps with previous";
            }
            
            System.out.printf("%4d | [%2d, %2d] | %8d | %s%n",
                            i, current[0], current[1], lastEnd, action);
        }
        
        System.out.println("\nSelected non-overlapping intervals: " + selected.size());
        System.out.println("Intervals to remove: " + (intervals.length - selected.size()));
        System.out.println("Selected intervals: " + selected.toString());
    }
    
    /**
     * Helper method to compare different approaches
     */
    private void compareApproaches(int[][] intervals) {
        System.out.println("\nComparison of Different Approaches:");
        System.out.println("Input: " + Arrays.deepToString(intervals));
        
        int result1 = eraseOverlapIntervals(intervals);
        int result2 = eraseOverlapIntervalsSortByStart(intervals);
        int result3 = eraseOverlapIntervalsDP(intervals);
        int result4 = eraseOverlapIntervalsExplicit(intervals);
        
        System.out.println("Greedy (end time): " + result1 + " removals");
        System.out.println("Greedy (start time): " + result2 + " removals");
        System.out.println("Dynamic Programming: " + result3 + " removals");
        System.out.println("Explicit tracking: " + result4 + " removals");
        
        boolean allEqual = (result1 == result2) && (result1 == result3) && (result1 == result4);
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Non-overlapping Intervals Solution:");
        System.out.println("============================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[][] intervals1 = {{1,2},{2,3},{3,4},{1,3}};
        int expected1 = 1;
        
        long startTime = System.nanoTime();
        int result1a = solution.eraseOverlapIntervals(intervals1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.eraseOverlapIntervalsSortByStart(intervals1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.eraseOverlapIntervalsDP(intervals1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = (result1a == expected1);
        boolean test1b = (result1b == expected1);
        boolean test1c = (result1c == expected1);
        
        System.out.println("Greedy (end): " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Greedy (start): " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("DP: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeGreedy(intervals1);
        
        // Test case 2: Multiple duplicates
        System.out.println("\nTest 2: Multiple duplicates");
        int[][] intervals2 = {{1,2},{1,2},{1,2}};
        int expected2 = 2;
        
        int result2a = solution.eraseOverlapIntervals(intervals2);
        System.out.println("Multiple duplicates: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: No overlaps
        System.out.println("\nTest 3: No overlaps");
        int[][] intervals3 = {{1,2},{2,3}};
        int expected3 = 0;
        
        int result3a = solution.eraseOverlapIntervals(intervals3);
        System.out.println("No overlaps: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single interval
        System.out.println("\nTest 4: Single interval");
        int[][] intervals4 = {{1,2}};
        int expected4 = 0;
        
        int result4a = solution.eraseOverlapIntervals(intervals4);
        System.out.println("Single interval: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Complex overlaps
        System.out.println("\nTest 5: Complex overlaps");
        int[][] intervals5 = {{1,100},{11,22},{1,11},{2,12}};
        int result5a = solution.eraseOverlapIntervals(intervals5);
        System.out.println("Complex overlaps: " + result5a + " removals");
        
        // Test case 6: Empty input
        System.out.println("\nTest 6: Empty input");
        int[][] intervals6 = {};
        int expected6 = 0;
        
        int result6a = solution.eraseOverlapIntervals(intervals6);
        System.out.println("Empty input: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: All intervals overlap
        System.out.println("\nTest 7: All intervals overlap");
        int[][] intervals7 = {{1,4},{2,3},{3,5},{4,6}};
        int result7a = solution.eraseOverlapIntervals(intervals7);
        System.out.println("All overlap: " + result7a + " removals");
        
        // Test case 8: Large gaps
        System.out.println("\nTest 8: Large gaps");
        int[][] intervals8 = {{1,2},{10,11},{20,21},{30,31}};
        int expected8 = 0;
        
        int result8a = solution.eraseOverlapIntervals(intervals8);
        System.out.println("Large gaps: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Greedy (end): " + time1a + " ns");
        System.out.println("  Greedy (start): " + time1b + " ns");
        System.out.println("  DP: " + time1c + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[][] largeIntervals = new int[10000][2];
        Random random = new Random(42);
        for (int i = 0; i < 10000; i++) {
            int start = random.nextInt(100000);
            int end = start + random.nextInt(1000) + 1;
            largeIntervals[i] = new int[]{start, end};
        }
        
        startTime = System.nanoTime();
        int result10a = solution.eraseOverlapIntervals(largeIntervals);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10b = solution.eraseOverlapIntervalsSortByStart(largeIntervals);
        long time10b = System.nanoTime() - startTime;
        
        // Skip DP for large input as it's too slow
        
        System.out.println("Large input (10,000 intervals):");
        System.out.println("  Greedy (end): " + time10a + " ns, Removals: " + result10a);
        System.out.println("  Greedy (start): " + time10b + " ns, Removals: " + result10b);
        
        // Compare approaches
        solution.compareApproaches(intervals1);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("GREEDY ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The problem is equivalent to finding the maximum number");
        System.out.println("of non-overlapping intervals (interval scheduling problem).");
        System.out.println("Minimum removals = Total intervals - Maximum non-overlapping intervals");
        
        System.out.println("\nWhy sort by end time?");
        System.out.println("1. We want to maximize the number of intervals we can keep");
        System.out.println("2. By picking the interval that ends earliest, we leave");
        System.out.println("   more room for subsequent intervals");
        System.out.println("3. This greedy choice leads to the optimal solution");
        
        System.out.println("\nOverlap Condition:");
        System.out.println("Two intervals [a,b] and [c,d] overlap if:");
        System.out.println("  max(a,c) < min(b,d)");
        System.out.println("In our algorithm, we check: current.start < previous.end");
        
        System.out.println("\nVisual Example:");
        System.out.println("Input: [[1,2], [2,3], [3,4], [1,3]]");
        System.out.println("Sorted by end: [[1,2], [2,3], [1,3], [3,4]]");
        System.out.println("Process:");
        System.out.println("  Select [1,2] (ends at 2)");
        System.out.println("  [2,3] starts at 2 >= 2 -> SELECT");
        System.out.println("  [1,3] starts at 1 < 3 -> REMOVE");
        System.out.println("  [3,4] starts at 3 >= 3 -> SELECT");
        System.out.println("Selected: 3 intervals, Removed: 1 interval");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Greedy - Sort by End Time (RECOMMENDED):");
        System.out.println("   Time: O(n log n) - Dominated by sorting");
        System.out.println("   Space: O(log n) - For sorting algorithm");
        System.out.println("   How it works:");
        System.out.println("     - Sort intervals by end time");
        System.out.println("     - Always pick the next interval that ends earliest");
        System.out.println("     - Skip intervals that overlap with last picked");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n log n) time complexity");
        System.out.println("     - Simple and elegant");
        System.out.println("     - Proven to be optimal for interval scheduling");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of greedy proof");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Greedy - Sort by Start Time:");
        System.out.println("   Time: O(n log n) - Sorting + linear scan");
        System.out.println("   Space: O(log n) - For sorting algorithm");
        System.out.println("   How it works:");
        System.out.println("     - Sort intervals by start time");
        System.out.println("     - When overlap occurs, remove the interval that ends later");
        System.out.println("     - Keep the interval that ends earlier");
        System.out.println("   Pros:");
        System.out.println("     - Also O(n log n) time complexity");
        System.out.println("     - Different perspective on the problem");
        System.out.println("   Cons:");
        System.out.println("     - Slightly less intuitive");
        System.out.println("   Best for: Alternative implementation");
        
        System.out.println("\n3. Dynamic Programming:");
        System.out.println("   Time: O(n^2) - Nested loops for LIS-style DP");
        System.out.println("   Space: O(n) - DP array storage");
        System.out.println("   How it works:");
        System.out.println("     - Sort intervals by start time");
        System.out.println("     - For each interval, find max non-overlapping before it");
        System.out.println("     - Use DP to build solution incrementally");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates DP thinking");
        System.out.println("     - More intuitive for some problems");
        System.out.println("   Cons:");
        System.out.println("     - O(n^2) too slow for large inputs");
        System.out.println("     - Uses more space");
        System.out.println("   Best for: Learning DP applications");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL PROOF OF GREEDY OPTIMALITY:");
        System.out.println("1. Let S be the set of intervals selected by greedy algorithm");
        System.out.println("2. Let O be any optimal solution");
        System.out.println("3. We can transform O into S without decreasing the count");
        System.out.println("4. The first interval where O and S differ:");
        System.out.println("   - Greedy picks the interval that ends earliest");
        System.out.println("   - We can replace O's choice with S's choice");
        System.out.println("5. This preserves optimality, so |S| = |O|");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Greedy (end time) - it's the expected solution");
        System.out.println("2. Explain the equivalence: min removals = total - max non-overlapping");
        System.out.println("3. Justify why sorting by end time is optimal");
        System.out.println("4. Draw examples to illustrate the greedy choice");
        System.out.println("5. Handle edge cases: empty, single interval, no overlaps");
        System.out.println("6. Discuss time/space complexity");
        System.out.println("7. Mention alternative approaches if time permits");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
