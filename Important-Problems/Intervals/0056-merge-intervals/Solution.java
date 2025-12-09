
## Problems/Arrays-Hashing/0056-merge-intervals/Solution.java

```java
/**
 * 56. Merge Intervals
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of intervals where intervals[i] = [starti, endi], 
 * merge all overlapping intervals, and return an array of the non-overlapping 
 * intervals that cover all the intervals in the input.
 * 
 * Key Insights:
 * 1. Sort intervals by start time to make merging easier
 * 2. Two intervals overlap if current.start <= previous.end
 * 3. When merging, take min(start) and max(end) of overlapping intervals
 * 4. Process intervals in sorted order and merge greedily
 * 
 * Approach (Sort and Linear Merge):
 * 1. Sort intervals by start time
 * 2. Initialize result with first interval
 * 3. For each subsequent interval:
 *    - If it overlaps with last interval in result, merge them
 *    - Otherwise, add it to result
 * 
 * Time Complexity: O(n log n) due to sorting
 * Space Complexity: O(n) for result, O(log n) for sorting algorithm
 * 
 * Tags: Array, Sorting, Intervals
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Sort and Linear Merge - RECOMMENDED
     * O(n log n) time, O(n) space
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        List<int[]> result = new ArrayList<>();
        result.add(intervals[0]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] lastInterval = result.get(result.size() - 1);
            int[] currentInterval = intervals[i];
            
            // Check if current interval overlaps with last interval in result
            if (currentInterval[0] <= lastInterval[1]) {
                // Merge intervals by updating the end time
                lastInterval[1] = Math.max(lastInterval[1], currentInterval[1]);
            } else {
                // No overlap, add current interval to result
                result.add(currentInterval);
            }
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Approach 2: Sort and Merge with While Loop
     * O(n log n) time, O(n) space - Alternative implementation
     */
    public int[][] mergeWhileLoop(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        List<int[]> result = new ArrayList<>();
        int i = 0;
        
        while (i < intervals.length) {
            int start = intervals[i][0];
            int end = intervals[i][1];
            
            // Merge all overlapping intervals
            while (i + 1 < intervals.length && intervals[i + 1][0] <= end) {
                end = Math.max(end, intervals[i + 1][1]);
                i++;
            }
            
            result.add(new int[]{start, end});
            i++;
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Approach 3: Using Stack
     * O(n log n) time, O(n) space - Demonstrates stack usage
     */
    public int[][] mergeWithStack(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        Stack<int[]> stack = new Stack<>();
        stack.push(intervals[0]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] top = stack.peek();
            int[] current = intervals[i];
            
            if (current[0] <= top[1]) {
                // Merge intervals
                top[1] = Math.max(top[1], current[1]);
            } else {
                stack.push(current);
            }
        }
        
        return stack.toArray(new int[stack.size()][]);
    }
    
    /**
     * Approach 4: In-place Merge (Optimized Space)
     * O(n log n) time, O(1) extra space (modifies input)
     */
    public int[][] mergeInPlace(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        int writeIndex = 0; // Position to write the next merged interval
        
        for (int i = 1; i < intervals.length; i++) {
            int[] lastInterval = intervals[writeIndex];
            int[] currentInterval = intervals[i];
            
            if (currentInterval[0] <= lastInterval[1]) {
                // Merge with the last interval
                lastInterval[1] = Math.max(lastInterval[1], currentInterval[1]);
            } else {
                // Move to next position and copy current interval
                writeIndex++;
                intervals[writeIndex] = currentInterval;
            }
        }
        
        // Return only the merged intervals (first writeIndex + 1 elements)
        return Arrays.copyOf(intervals, writeIndex + 1);
    }
    
    /**
     * Approach 5: Using Linked List
     * O(n log n) time, O(n) space - Demonstrates alternative data structure
     */
    public int[][] mergeWithLinkedList(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        LinkedList<int[]> merged = new LinkedList<>();
        for (int[] interval : intervals) {
            if (merged.isEmpty() || merged.getLast()[1] < interval[0]) {
                merged.add(interval);
            } else {
                merged.getLast()[1] = Math.max(merged.getLast()[1], interval[1]);
            }
        }
        
        return merged.toArray(new int[merged.size()][]);
    }
    
    /**
     * Helper method to check if two intervals overlap
     */
    private boolean overlaps(int[] a, int[] b) {
        return Math.max(a[0], b[0]) <= Math.min(a[1], b[1]);
    }
    
    /**
     * Helper method to visualize the merging process
     */
    private void visualizeMerge(int[][] intervals) {
        System.out.println("\nMerge Intervals Visualization:");
        System.out.println("Original intervals: " + Arrays.deepToString(intervals));
        
        // Sort intervals
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.println("Sorted intervals: " + Arrays.deepToString(sorted));
        
        List<int[]> result = new ArrayList<>();
        result.add(sorted[0]);
        
        System.out.println("\nMerging Process:");
        System.out.println("Step | Current Interval | Last in Result | Action");
        System.out.println("-----|------------------|----------------|--------");
        
        for (int i = 1; i < sorted.length; i++) {
            int[] last = result.get(result.size() - 1);
            int[] current = sorted[i];
            String action;
            
            if (current[0] <= last[1]) {
                // Merge
                int newEnd = Math.max(last[1], current[1]);
                action = "Merge: [" + last[0] + ", " + newEnd + "]";
                last[1] = newEnd;
            } else {
                // Add new
                action = "Add new interval";
                result.add(current);
            }
            
            System.out.printf("%4d | [%2d, %2d] | [%2d, %2d] | %s%n",
                            i, current[0], current[1], last[0], last[1], action);
        }
        
        System.out.println("\nFinal result: " + result.toString());
    }
    
    /**
     * Helper method to print intervals in a more readable format
     */
    private void printIntervals(int[][] intervals, String title) {
        System.out.println(title + ":");
        for (int[] interval : intervals) {
            System.out.println("  [" + interval[0] + ", " + interval[1] + "]");
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Merge Intervals Solution:");
        System.out.println("=================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[][] intervals1 = {{1,3},{2,6},{8,10},{15,18}};
        int[][] expected1 = {{1,6},{8,10},{15,18}};
        
        long startTime = System.nanoTime();
        int[][] result1a = solution.merge(intervals1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result1b = solution.mergeWhileLoop(intervals1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result1c = solution.mergeWithStack(intervals1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result1d = solution.mergeInPlace(intervals1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = Arrays.deepEquals(result1a, expected1);
        boolean test1b = Arrays.deepEquals(result1b, expected1);
        boolean test1c = Arrays.deepEquals(result1c, expected1);
        boolean test1d = Arrays.deepEquals(result1d, expected1);
        
        System.out.println("Sort & Merge: " + Arrays.deepToString(result1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("While Loop: " + Arrays.deepToString(result1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Stack: " + Arrays.deepToString(result1c) + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("In-place: " + Arrays.deepToString(result1d) + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeMerge(intervals1);
        
        // Test case 2: Adjacent intervals
        System.out.println("\nTest 2: Adjacent intervals");
        int[][] intervals2 = {{1,4},{4,5}};
        int[][] expected2 = {{1,5}};
        
        int[][] result2a = solution.merge(intervals2);
        System.out.println("Adjacent intervals: " + Arrays.deepToString(result2a) + " - " + 
                         (Arrays.deepEquals(result2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Single interval
        System.out.println("\nTest 3: Single interval");
        int[][] intervals3 = {{1,3}};
        int[][] expected3 = {{1,3}};
        
        int[][] result3a = solution.merge(intervals3);
        System.out.println("Single interval: " + Arrays.deepToString(result3a) + " - " + 
                         (Arrays.deepEquals(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Empty input
        System.out.println("\nTest 4: Empty input");
        int[][] intervals4 = {};
        int[][] expected4 = {};
        
        int[][] result4a = solution.merge(intervals4);
        System.out.println("Empty input: " + Arrays.deepToString(result4a) + " - " + 
                         (Arrays.deepEquals(result4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Complete overlap
        System.out.println("\nTest 5: Complete overlap");
        int[][] intervals5 = {{1,4},{2,3}};
        int[][] expected5 = {{1,4}};
        
        int[][] result5a = solution.merge(intervals5);
        System.out.println("Complete overlap: " + Arrays.deepToString(result5a) + " - " + 
                         (Arrays.deepEquals(result5a, expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Multiple merges
        System.out.println("\nTest 6: Multiple merges");
        int[][] intervals6 = {{1,3},{2,6},{8,10},{15,18},{16,17}};
        int[][] expected6 = {{1,6},{8,10},{15,18}};
        
        int[][] result6a = solution.merge(intervals6);
        System.out.println("Multiple merges: " + Arrays.deepToString(result6a) + " - " + 
                         (Arrays.deepEquals(result6a, expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Unsorted intervals
        System.out.println("\nTest 7: Unsorted intervals");
        int[][] intervals7 = {{15,18},{1,3},{8,10},{2,6}};
        int[][] expected7 = {{1,6},{8,10},{15,18}};
        
        int[][] result7a = solution.merge(intervals7);
        System.out.println("Unsorted intervals: " + Arrays.deepToString(result7a) + " - " + 
                         (Arrays.deepEquals(result7a, expected7) ? "PASSED" : "FAILED"));
        
        // Test case 8: All intervals merged into one
        System.out.println("\nTest 8: All intervals merged into one");
        int[][] intervals8 = {{1,4},{0,4}};
        int[][] expected8 = {{0,4}};
        
        int[][] result8a = solution.merge(intervals8);
        System.out.println("All merged: " + Arrays.deepToString(result8a) + " - " + 
                         (Arrays.deepEquals(result8a, expected8) ? "PASSED" : "FAILED"));
        
        // Test case 9: Complex example
        System.out.println("\nTest 9: Complex example");
        int[][] intervals9 = {{2,3},{4,5},{6,7},{8,9},{1,10}};
        int[][] expected9 = {{1,10}};
        
        int[][] result9a = solution.merge(intervals9);
        System.out.println("Complex: " + Arrays.deepToString(result9a) + " - " + 
                         (Arrays.deepEquals(result9a, expected9) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 10: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Sort & Merge: " + time1a + " ns");
        System.out.println("  While Loop: " + time1b + " ns");
        System.out.println("  Stack: " + time1c + " ns");
        System.out.println("  In-place: " + time1d + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 11: Large input performance");
        int[][] largeIntervals = new int[10000][2];
        Random random = new Random(42);
        for (int i = 0; i < 10000; i++) {
            int start = random.nextInt(10000);
            int end = start + random.nextInt(100);
            largeIntervals[i] = new int[]{start, end};
        }
        
        startTime = System.nanoTime();
        int[][] result11a = solution.merge(largeIntervals);
        long time11a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result11b = solution.mergeWhileLoop(largeIntervals);
        long time11b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[][] result11c = solution.mergeInPlace(largeIntervals);
        long time11c = System.nanoTime() - startTime;
        
        System.out.println("Large input (10,000 intervals):");
        System.out.println("  Sort & Merge: " + time11a + " ns, Result size: " + result11a.length);
        System.out.println("  While Loop: " + time11b + " ns, Result size: " + result11b.length);
        System.out.println("  In-place: " + time11c + " ns, Result size: " + result11c.length);
        
        // Verify all approaches produce the same result
        boolean allEqual = Arrays.deepEquals(result11a, result11b) && 
                          Arrays.deepEquals(result11a, result11c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SORT AND LINEAR MERGE EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("By sorting intervals by start time, we can merge in a single pass.");
        System.out.println("Two intervals [a,b] and [c,d] overlap if: a <= d AND c <= b");
        System.out.println("When they overlap, we merge by taking min(a,c) and max(b,d)");
        
        System.out.println("\nWhy sorting works:");
        System.out.println("1. After sorting, intervals are processed in increasing start order");
        System.out.println("2. This ensures that if interval i doesn't overlap with i-1,");
        System.out.println("   it cannot overlap with any interval before i-1");
        System.out.println("3. We only need to compare with the last merged interval");
        
        System.out.println("\nVisual Example:");
        System.out.println("Input: [[1,3], [2,6], [8,10], [15,18]]");
        System.out.println("Sorted: [[1,3], [2,6], [8,10], [15,18]]");
        System.out.println("Process:");
        System.out.println("  Start with [1,3]");
        System.out.println("  [2,6] overlaps with [1,3] -> merge to [1,6]");
        System.out.println("  [8,10] no overlap -> add");
        System.out.println("  [15,18] no overlap -> add");
        System.out.println("Result: [[1,6], [8,10], [15,18]]");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Sort and Linear Merge (RECOMMENDED):");
        System.out.println("   Time: O(n log n) - Dominated by sorting");
        System.out.println("   Space: O(n) - For result list, O(log n) for sorting");
        System.out.println("   How it works:");
        System.out.println("     - Sort intervals by start time");
        System.out.println("     - Initialize result with first interval");
        System.out.println("     - For each interval, merge if overlaps with last in result");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity for this problem");
        System.out.println("     - Clear and intuitive logic");
        System.out.println("     - Handles all edge cases well");
        System.out.println("   Cons:");
        System.out.println("     - Requires sorting first");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. While Loop Variant:");
        System.out.println("   Time: O(n log n) - Same as main approach");
        System.out.println("   Space: O(n) - For result list");
        System.out.println("   How it works:");
        System.out.println("     - Sort intervals by start time");
        System.out.println("     - Use while loop to merge all consecutive overlaps");
        System.out.println("   Pros:");
        System.out.println("     - Alternative implementation style");
        System.out.println("     - May be more intuitive for some");
        System.out.println("   Cons:");
        System.out.println("     - Same complexity as main approach");
        System.out.println("   Best for: Alternative implementation practice");
        
        System.out.println("\n3. Stack Approach:");
        System.out.println("   Time: O(n log n) - Sorting + stack operations");
        System.out.println("   Space: O(n) - Stack storage");
        System.out.println("   How it works:");
        System.out.println("     - Sort intervals by start time");
        System.out.println("     - Use stack to track merged intervals");
        System.out.println("     - Merge by updating top of stack");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates stack usage");
        System.out.println("     - Clear separation of merged and unmerged");
        System.out.println("   Cons:");
        System.out.println("     - Extra space for stack");
        System.out.println("     - More complex than array list");
        System.out.println("   Best for: Learning stack applications");
        
        System.out.println("\n4. In-place Merge:");
        System.out.println("   Time: O(n log n) - Sorting + in-place merge");
        System.out.println("   Space: O(1) extra space - Modifies input array");
        System.out.println("   How it works:");
        System.out.println("     - Sort intervals by start time");
        System.out.println("     - Use write index to track position for next interval");
        System.out.println("     - Merge in-place by updating array elements");
        System.out.println("   Pros:");
        System.out.println("     - Minimal extra space usage");
        System.out.println("     - Efficient memory usage");
        System.out.println("   Cons:");
        System.out.println("     - Modifies input array (may not be desired)");
        System.out.println("     - More complex index management");
        System.out.println("   Best for: When space optimization is critical");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Overlap condition: Two intervals [a,b] and [c,d] overlap if:");
        System.out.println("   max(a,c) <= min(b,d)");
        System.out.println("2. After sorting by start time, we only need to check:");
        System.out.println("   current.start <= previous.end");
        System.out.println("3. The greedy approach works because:");
        System.out.println("   - We always merge with the latest possible interval");
        System.out.println("   - Sorting ensures no interval before can overlap with current");
        System.out.println("4. Time complexity is dominated by O(n log n) sorting");
        System.out.println("5. This is optimal for comparison-based algorithms");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Sort and Linear Merge - it's the standard solution");
        System.out.println("2. Explain why sorting is necessary and how it enables linear merge");
        System.out.println("3. Clearly state the overlap condition");
        System.out.println("4. Draw examples to visualize the merging process");
        System.out.println("5. Handle edge cases: empty input, single interval, no overlaps");
        System.out.println("6. Discuss time/space complexity trade-offs");
        System.out.println("7. Mention that O(n log n) is optimal for comparison-based approach");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
