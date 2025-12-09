
## Problems/Arrays-Hashing/0252-meeting-rooms/Solution.java

```java
/**
 * 252. Meeting Rooms
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given an array of meeting time intervals where intervals[i] = [starti, endi], 
 * determine if a person could attend all meetings.
 * 
 * Key Insights:
 * 1. Check if any two meetings overlap
 * 2. After sorting by start time, only need to check adjacent intervals
 * 3. Two intervals overlap if current.start < previous.end
 * 4. If no overlaps found, return true; otherwise false
 * 
 * Approach (Sort and Check Adjacent Intervals):
 * 1. Sort intervals by start time
 * 2. For each interval from 1 to n-1:
 *    - Check if it starts before previous interval ends
 *    - If yes, return false (overlap found)
 * 3. Return true if no overlaps found
 * 
 * Time Complexity: O(n log n) due to sorting
 * Space Complexity: O(log n) for sorting algorithm
 * 
 * Tags: Array, Sorting, Intervals
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Sort and Check Adjacent Intervals - RECOMMENDED
     * O(n log n) time, O(log n) space
     */
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Check for overlaps between consecutive intervals
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false; // Overlap found
            }
        }
        
        return true; // No overlaps found
    }
    
    /**
     * Approach 2: Sort with Custom Comparator
     * O(n log n) time, O(log n) space - Alternative implementation
     */
    public boolean canAttendMeetingsComparator(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        // Using explicit comparator
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Approach 3: Brute Force - Compare All Pairs
     * O(n^2) time, O(1) space - Simple but inefficient
     */
    public boolean canAttendMeetingsBruteForce(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1; j < intervals.length; j++) {
                if (overlaps(intervals[i], intervals[j])) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Approach 4: Using Priority Queue (Min Heap)
     * O(n log n) time, O(n) space - Demonstrates heap usage
     */
    public boolean canAttendMeetingsHeap(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        // Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Min heap to track end times
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.offer(intervals[0][1]);
        
        for (int i = 1; i < intervals.length; i++) {
            // If current meeting starts after the earliest ending meeting
            if (intervals[i][0] >= heap.peek()) {
                heap.poll(); // Remove the ended meeting
            }
            heap.offer(intervals[i][1]);
            
            // If heap size > 1, it means we have overlapping meetings
            if (heap.size() > 1) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Approach 5: In-place Check without Extra Space
     * O(n log n) time, O(1) extra space - Modifies input
     */
    public boolean canAttendMeetingsInPlace(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        // Sort in-place
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Helper method to check if two intervals overlap
     */
    private boolean overlaps(int[] a, int[] b) {
        return Math.max(a[0], b[0]) < Math.min(a[1], b[1]);
    }
    
    /**
     * Helper method to visualize the meeting schedule
     */
    private void visualizeMeetings(int[][] intervals) {
        System.out.println("\nMeeting Rooms Visualization:");
        System.out.println("Original meetings: " + Arrays.deepToString(intervals));
        
        if (intervals == null || intervals.length == 0) {
            System.out.println("No meetings scheduled - CAN ATTEND ALL");
            return;
        }
        
        // Sort intervals
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.println("Sorted by start time: " + Arrays.deepToString(sorted));
        
        System.out.println("\nMeeting Schedule Check:");
        System.out.println("Meeting | Start | End | Overlaps with Previous?");
        System.out.println("--------|-------|-----|-----------------------");
        
        boolean canAttendAll = true;
        
        for (int i = 0; i < sorted.length; i++) {
            String overlapStatus = "N/A";
            
            if (i > 0) {
                boolean overlaps = sorted[i][0] < sorted[i - 1][1];
                overlapStatus = overlaps ? "YES - CONFLICT" : "No";
                if (overlaps) {
                    canAttendAll = false;
                }
            }
            
            System.out.printf("%7d | %5d | %3d | %s%n",
                            i + 1, sorted[i][0], sorted[i][1], overlapStatus);
        }
        
        System.out.println("\nResult: " + (canAttendAll ? "CAN attend all meetings" : "CANNOT attend all meetings"));
    }
    
    /**
     * Helper method to create a visual timeline
     */
    private void printTimeline(int[][] intervals) {
        System.out.println("\nMeeting Timeline:");
        
        if (intervals == null || intervals.length == 0) {
            System.out.println("No meetings");
            return;
        }
        
        // Find time range
        int minTime = Integer.MAX_VALUE;
        int maxTime = Integer.MIN_VALUE;
        for (int[] interval : intervals) {
            minTime = Math.min(minTime, interval[0]);
            maxTime = Math.max(maxTime, interval[1]);
        }
        
        // Sort intervals
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Print timeline
        for (int[] interval : sorted) {
            System.out.printf("[%2d-%2d]: ", interval[0], interval[1]);
            for (int time = minTime; time <= maxTime; time++) {
                if (time >= interval[0] && time < interval[1]) {
                    System.out.print("█");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        
        // Check for overlaps
        boolean hasOverlap = false;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] < sorted[i - 1][1]) {
                hasOverlap = true;
                System.out.printf("OVERLAP: Meeting %d [%d-%d] overlaps with Meeting %d [%d-%d]%n",
                                i, sorted[i][0], sorted[i][1], i - 1, sorted[i - 1][0], sorted[i - 1][1]);
            }
        }
        
        if (!hasOverlap) {
            System.out.println("No overlaps detected - All meetings can be attended");
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Meeting Rooms Solution:");
        System.out.println("===============================");
        
        // Test case 1: Overlapping meetings (should return false)
        System.out.println("\nTest 1: Overlapping meetings");
        int[][] intervals1 = {{0,30},{5,10},{15,20}};
        boolean expected1 = false;
        
        long startTime = System.nanoTime();
        boolean result1a = solution.canAttendMeetings(intervals1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.canAttendMeetingsComparator(intervals1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1c = solution.canAttendMeetingsBruteForce(intervals1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = (result1a == expected1);
        boolean test1b = (result1b == expected1);
        boolean test1c = (result1c == expected1);
        
        System.out.println("Sort & Check: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Comparator: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Brute Force: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the meetings
        solution.visualizeMeetings(intervals1);
        solution.printTimeline(intervals1);
        
        // Test case 2: Non-overlapping meetings (should return true)
        System.out.println("\nTest 2: Non-overlapping meetings");
        int[][] intervals2 = {{7,10},{2,4}};
        boolean expected2 = true;
        
        boolean result2a = solution.canAttendMeetings(intervals2);
        System.out.println("Non-overlapping: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        solution.visualizeMeetings(intervals2);
        
        // Test case 3: Single meeting
        System.out.println("\nTest 3: Single meeting");
        int[][] intervals3 = {{1,2}};
        boolean expected3 = true;
        
        boolean result3a = solution.canAttendMeetings(intervals3);
        System.out.println("Single meeting: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Empty input
        System.out.println("\nTest 4: Empty input");
        int[][] intervals4 = {};
        boolean expected4 = true;
        
        boolean result4a = solution.canAttendMeetings(intervals4);
        System.out.println("Empty input: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Meetings that touch but don't overlap
        System.out.println("\nTest 5: Meetings that touch");
        int[][] intervals5 = {{1,2},{2,3},{3,4}};
        boolean expected5 = true;
        
        boolean result5a = solution.canAttendMeetings(intervals5);
        System.out.println("Touching meetings: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        solution.visualizeMeetings(intervals5);
        
        // Test case 6: Multiple overlaps
        System.out.println("\nTest 6: Multiple overlaps");
        int[][] intervals6 = {{1,5},{2,3},{4,6},{7,8}};
        boolean expected6 = false;
        
        boolean result6a = solution.canAttendMeetings(intervals6);
        System.out.println("Multiple overlaps: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large meeting that contains others
        System.out.println("\nTest 7: Large containing meeting");
        int[][] intervals7 = {{1,10},{2,3},{4,5},{6,7}};
        boolean expected7 = false;
        
        boolean result7a = solution.canAttendMeetings(intervals7);
        System.out.println("Large containing: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Back-to-back meetings
        System.out.println("\nTest 8: Back-to-back meetings");
        int[][] intervals8 = {{1,2},{2,3},{3,4},{4,5}};
        boolean expected8 = true;
        
        boolean result8a = solution.canAttendMeetings(intervals8);
        System.out.println("Back-to-back: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Overlapping meetings performance:");
        System.out.println("  Sort & Check: " + time1a + " ns");
        System.out.println("  Comparator: " + time1b + " ns");
        System.out.println("  Brute Force: " + time1c + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[][] largeIntervals = new int[10000][2];
        Random random = new Random(42);
        for (int i = 0; i < 10000; i++) {
            int start = random.nextInt(100000);
            int end = start + random.nextInt(10) + 1;
            largeIntervals[i] = new int[]{start, end};
        }
        
        startTime = System.nanoTime();
        boolean result10a = solution.canAttendMeetings(largeIntervals);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result10b = solution.canAttendMeetingsBruteForce(largeIntervals);
        long time10b = System.nanoTime() - startTime;
        
        System.out.println("Large input (10,000 meetings):");
        System.out.println("  Sort & Check: " + time10a + " ns, Result: " + result10a);
        System.out.println("  Brute Force: " + time10b + " ns, Result: " + result10b);
        
        // Compare all approaches for consistency
        System.out.println("\nTest 11: Consistency Check");
        int[][] testIntervals = {{1,3},{2,4},{5,7}};
        boolean r1 = solution.canAttendMeetings(testIntervals);
        boolean r2 = solution.canAttendMeetingsComparator(testIntervals);
        boolean r3 = solution.canAttendMeetingsBruteForce(testIntervals);
        boolean r4 = solution.canAttendMeetingsHeap(testIntervals);
        boolean r5 = solution.canAttendMeetingsInPlace(testIntervals);
        
        System.out.println("All approaches consistent: " + 
                         (r1 == r2 && r1 == r3 && r1 == r4 && r1 == r5));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("After sorting meetings by start time, we only need to");
        System.out.println("check if each meeting starts before the previous meeting ends.");
        System.out.println("If meetings[i].start < meetings[i-1].end, then there's an overlap.");
        
        System.out.println("\nWhy this works:");
        System.out.println("1. Sorting by start time arranges meetings in chronological order");
        System.out.println("2. If meeting i doesn't overlap with meeting i-1,");
        System.out.println("   it cannot overlap with any meeting before i-1");
        System.out.println("3. This reduces the problem to checking only adjacent meetings");
        
        System.out.println("\nOverlap Condition:");
        System.out.println("Two meetings [a,b] and [c,d] overlap if:");
        System.out.println("  max(a,c) < min(b,d)");
        System.out.println("After sorting (a <= c), this simplifies to: c < b");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Sort and Check Adjacent (RECOMMENDED):");
        System.out.println("   Time: O(n log n) - Dominated by sorting");
        System.out.println("   Space: O(log n) - For sorting algorithm");
        System.out.println("   How it works:");
        System.out.println("     - Sort intervals by start time");
        System.out.println("     - Check if each interval starts before previous ends");
        System.out.println("     - Return false if any overlap found, else true");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity for this problem");
        System.out.println("     - Simple and elegant");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Requires sorting first");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Brute Force - Compare All Pairs:");
        System.out.println("   Time: O(n^2) - Compare every pair of intervals");
        System.out.println("   Space: O(1) - No extra space needed");
        System.out.println("   How it works:");
        System.out.println("     - For each interval, check against all others");
        System.out.println("     - Use overlap condition: max(a,c) < min(b,d)");
        System.out.println("     - Return false if any overlap found");
        System.out.println("   Pros:");
        System.out.println("     - Simple to implement");
        System.out.println("     - No sorting required");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(n^2) too slow for large inputs");
        System.out.println("     - Inefficient for n > 1000");
        System.out.println("   Best for: Small inputs, educational purposes");
        
        System.out.println("\n3. Priority Queue (Min Heap):");
        System.out.println("   Time: O(n log n) - Sorting + heap operations");
        System.out.println("   Space: O(n) - Heap storage");
        System.out.println("   How it works:");
        System.out.println("     - Sort intervals by start time");
        System.out.println("     - Use min heap to track end times");
        System.out.println("     - If heap size > 1, overlapping meetings exist");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates heap usage");
        System.out.println("     - Prepares for Meeting Rooms II problem");
        System.out.println("   Cons:");
        System.out.println("     - More complex than necessary");
        System.out.println("     - Uses extra space");
        System.out.println("   Best for: Learning heap applications");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND NOTES:");
        System.out.println("1. Empty input: Return true (no meetings to conflict)");
        System.out.println("2. Single meeting: Return true");
        System.out.println("3. Meetings that touch: [1,2] and [2,3] don't overlap");
        System.out.println("4. Meetings with same start time: Always overlap");
        System.out.println("5. One meeting contained within another: Overlap");
        System.out.println("6. Large input: Use O(n log n) approach, not O(n^2)");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Sort and Check - it's the expected solution");
        System.out.println("2. Explain why sorting enables checking only adjacent intervals");
        System.out.println("3. Clearly state the overlap condition");
        System.out.println("4. Handle edge cases: empty, single meeting, touching meetings");
        System.out.println("5. Discuss time/space complexity");
        System.out.println("6. Mention brute force as alternative but explain why it's inefficient");
        System.out.println("7. If time permits, discuss related problem (Meeting Rooms II)");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
