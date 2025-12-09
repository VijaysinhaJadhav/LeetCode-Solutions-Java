
## Problems/Arrays-Hashing/0253-meeting-rooms-ii/Solution.java

```java
/**
 * 253. Meeting Rooms II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of meeting time intervals where intervals[i] = [starti, endi], 
 * return the minimum number of conference rooms required.
 * 
 * Key Insights:
 * 1. Find the maximum number of overlapping meetings at any time
 * 2. Min heap tracks end times of currently ongoing meetings
 * 3. When new meeting starts, check if any meeting has ended
 * 4. Sort by start time to process meetings in chronological order
 * 
 * Approach (Min Heap):
 * 1. Sort intervals by start time
 * 2. Use min heap to track end times of ongoing meetings
 * 3. For each meeting:
 *    - Remove all finished meetings from heap
 *    - Add current meeting's end time to heap
 *    - Update max rooms needed
 * 4. Return max rooms needed
 * 
 * Time Complexity: O(n log n) due to sorting and heap operations
 * Space Complexity: O(n) for the heap in worst case
 * 
 * Tags: Array, Two Pointers, Greedy, Sorting, Heap, Intervals
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Min Heap (Priority Queue) - RECOMMENDED
     * O(n log n) time, O(n) space
     */
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Min heap to track end times of ongoing meetings
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.offer(intervals[0][1]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            
            // If the earliest ending meeting has finished before current starts
            if (current[0] >= heap.peek()) {
                heap.poll(); // Free up the room
            }
            
            // Add current meeting's end time to heap
            heap.offer(current[1]);
        }
        
        // The heap size represents the maximum rooms needed
        return heap.size();
    }
    
    /**
     * Approach 2: Chronological Ordering (Two Pointers)
     * O(n log n) time, O(n) space - Alternative optimal approach
     */
    public int minMeetingRoomsChronological(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        int n = intervals.length;
        int[] startTimes = new int[n];
        int[] endTimes = new int[n];
        
        // Separate start and end times
        for (int i = 0; i < n; i++) {
            startTimes[i] = intervals[i][0];
            endTimes[i] = intervals[i][1];
        }
        
        // Sort both arrays
        Arrays.sort(startTimes);
        Arrays.sort(endTimes);
        
        int startPtr = 0, endPtr = 0;
        int usedRooms = 0;
        int maxRooms = 0;
        
        // Process all meetings
        while (startPtr < n) {
            // If current meeting starts before the earliest ending meeting ends
            if (startTimes[startPtr] < endTimes[endPtr]) {
                usedRooms++; // Need a new room
                startPtr++;
            } else {
                // A meeting ended, free up a room
                usedRooms--;
                endPtr++;
            }
            maxRooms = Math.max(maxRooms, usedRooms);
        }
        
        return maxRooms;
    }
    
    /**
     * Approach 3: Sweep Line Algorithm
     * O(n log n) time, O(n) space - Clear and intuitive
     */
    public int minMeetingRoomsSweepLine(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        List<int[]> events = new ArrayList<>();
        
        // Create events: start time (+1), end time (-1)
        for (int[] interval : intervals) {
            events.add(new int[]{interval[0], 1});   // Meeting starts
            events.add(new int[]{interval[1], -1});  // Meeting ends
        }
        
        // Sort events by time, if same time, end comes before start
        events.sort((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });
        
        int currentRooms = 0;
        int maxRooms = 0;
        
        for (int[] event : events) {
            currentRooms += event[1]; // Add 1 for start, subtract 1 for end
            maxRooms = Math.max(maxRooms, currentRooms);
        }
        
        return maxRooms;
    }
    
    /**
     * Approach 4: Brute Force with Timeline
     * O(n * T) time, O(T) space - Not efficient but educational
     */
    public int minMeetingRoomsBruteForce(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // Find the time range
        int minTime = Integer.MAX_VALUE;
        int maxTime = Integer.MIN_VALUE;
        for (int[] interval : intervals) {
            minTime = Math.min(minTime, interval[0]);
            maxTime = Math.max(maxTime, interval[1]);
        }
        
        // Create timeline array
        int[] timeline = new int[maxTime + 1];
        
        // Mark busy times
        for (int[] interval : intervals) {
            for (int time = interval[0]; time < interval[1]; time++) {
                timeline[time]++;
            }
        }
        
        // Find maximum overlap
        int maxRooms = 0;
        for (int count : timeline) {
            maxRooms = Math.max(maxRooms, count);
        }
        
        return maxRooms;
    }
    
    /**
     * Approach 5: Using TreeMap (Balanced BST)
     * O(n log n) time, O(n) space - Alternative implementation
     */
    public int minMeetingRoomsTreeMap(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        TreeMap<Integer, Integer> timeline = new TreeMap<>();
        
        // Use sweep line approach with TreeMap
        for (int[] interval : intervals) {
            timeline.put(interval[0], timeline.getOrDefault(interval[0], 0) + 1);
            timeline.put(interval[1], timeline.getOrDefault(interval[1], 0) - 1);
        }
        
        int currentRooms = 0;
        int maxRooms = 0;
        
        for (int count : timeline.values()) {
            currentRooms += count;
            maxRooms = Math.max(maxRooms, currentRooms);
        }
        
        return maxRooms;
    }
    
    /**
     * Helper method to visualize the room allocation process
     */
    private void visualizeRoomAllocation(int[][] intervals) {
        System.out.println("\nMeeting Rooms II Visualization:");
        System.out.println("Input meetings: " + Arrays.deepToString(intervals));
        
        if (intervals == null || intervals.length == 0) {
            System.out.println("No meetings - 0 rooms needed");
            return;
        }
        
        // Sort intervals by start time
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.println("Sorted by start time: " + Arrays.deepToString(sorted));
        
        // Simulate room allocation
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        List<List<int[]>> rooms = new ArrayList<>();
        rooms.add(new ArrayList<>());
        
        heap.offer(sorted[0][1]);
        rooms.get(0).add(sorted[0]);
        
        System.out.println("\nRoom Allocation Process:");
        System.out.println("Meeting | Start | End | Earliest End | Action | Rooms Needed");
        System.out.println("--------|-------|-----|--------------|--------|-------------");
        
        System.out.printf("%7d | %5d | %3d | %12s | %6s | %12d%n",
                        1, sorted[0][0], sorted[0][1], "N/A", "Room 1", 1);
        
        for (int i = 1; i < sorted.length; i++) {
            int[] current = sorted[i];
            String action;
            int roomsBefore = heap.size();
            
            if (current[0] >= heap.peek()) {
                heap.poll();
                action = "Reuse room";
            } else {
                action = "New room";
            }
            
            heap.offer(current[1]);
            int roomsAfter = heap.size();
            
            System.out.printf("%7d | %5d | %3d | %12d | %6s | %12d%n",
                            i + 1, current[0], current[1], heap.peek(), action, roomsAfter);
        }
        
        System.out.println("\nFinal result: " + heap.size() + " rooms needed");
    }
    
    /**
     * Helper method to create a visual timeline with room assignments
     */
    private void printRoomTimeline(int[][] intervals) {
        System.out.println("\nRoom Timeline Visualization:");
        
        if (intervals == null || intervals.length == 0) {
            System.out.println("No meetings");
            return;
        }
        
        // Sort intervals by start time
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Simulate room assignment
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        List<List<int[]>> roomAssignments = new ArrayList<>();
        
        for (int[] meeting : sorted) {
            boolean assigned = false;
            
            // Try to assign to existing room
            for (int i = 0; i < roomAssignments.size(); i++) {
                List<int[]> room = roomAssignments.get(i);
                int[] lastMeeting = room.get(room.size() - 1);
                
                if (meeting[0] >= lastMeeting[1]) {
                    room.add(meeting);
                    assigned = true;
                    break;
                }
            }
            
            // If no room available, create new room
            if (!assigned) {
                List<int[]> newRoom = new ArrayList<>();
                newRoom.add(meeting);
                roomAssignments.add(newRoom);
            }
        }
        
        // Print room assignments
        for (int i = 0; i < roomAssignments.size(); i++) {
            System.out.printf("Room %d: ", i + 1);
            for (int[] meeting : roomAssignments.get(i)) {
                System.out.printf("[%d-%d] ", meeting[0], meeting[1]);
            }
            System.out.println();
        }
        
        System.out.println("Total rooms needed: " + roomAssignments.size());
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Meeting Rooms II Solution:");
        System.out.println("===================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[][] intervals1 = {{0,30},{5,10},{15,20}};
        int expected1 = 2;
        
        long startTime = System.nanoTime();
        int result1a = solution.minMeetingRooms(intervals1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.minMeetingRoomsChronological(intervals1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.minMeetingRoomsSweepLine(intervals1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = (result1a == expected1);
        boolean test1b = (result1b == expected1);
        boolean test1c = (result1c == expected1);
        
        System.out.println("Min Heap: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Chronological: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Sweep Line: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeRoomAllocation(intervals1);
        solution.printRoomTimeline(intervals1);
        
        // Test case 2: Non-overlapping meetings
        System.out.println("\nTest 2: Non-overlapping meetings");
        int[][] intervals2 = {{7,10},{2,4}};
        int expected2 = 1;
        
        int result2a = solution.minMeetingRooms(intervals2);
        System.out.println("Non-overlapping: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single meeting
        System.out.println("\nTest 3: Single meeting");
        int[][] intervals3 = {{1,2}};
        int expected3 = 1;
        
        int result3a = solution.minMeetingRooms(intervals3);
        System.out.println("Single meeting: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Empty input
        System.out.println("\nTest 4: Empty input");
        int[][] intervals4 = {};
        int expected4 = 0;
        
        int result4a = solution.minMeetingRooms(intervals4);
        System.out.println("Empty input: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: All meetings overlap
        System.out.println("\nTest 5: All meetings overlap");
        int[][] intervals5 = {{1,5},{2,3},{4,6}};
        int expected5 = 2;
        
        int result5a = solution.minMeetingRooms(intervals5);
        System.out.println("All overlap: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Chain of meetings
        System.out.println("\nTest 6: Chain of meetings");
        int[][] intervals6 = {{1,2},{2,3},{3,4},{4,5}};
        int expected6 = 1;
        
        int result6a = solution.minMeetingRooms(intervals6);
        System.out.println("Chain meetings: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Complex overlapping pattern
        System.out.println("\nTest 7: Complex overlapping pattern");
        int[][] intervals7 = {{1,10},{2,3},{4,5},{6,7},{8,9}};
        int expected7 = 2;
        
        int result7a = solution.minMeetingRooms(intervals7);
        System.out.println("Complex pattern: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Large meeting with many small overlaps
        System.out.println("\nTest 8: Large meeting with overlaps");
        int[][] intervals8 = {{0,50},{10,20},{15,25},{20,30},{25,35}};
        int result8a = solution.minMeetingRooms(intervals8);
        System.out.println("Large with overlaps: " + result8a + " rooms");
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Min Heap: " + time1a + " ns");
        System.out.println("  Chronological: " + time1b + " ns");
        System.out.println("  Sweep Line: " + time1c + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[][] largeIntervals = new int[10000][2];
        Random random = new Random(42);
        for (int i = 0; i < 10000; i++) {
            int start = random.nextInt(100000);
            int end = start + random.nextInt(100) + 1;
            largeIntervals[i] = new int[]{start, end};
        }
        
        startTime = System.nanoTime();
        int result10a = solution.minMeetingRooms(largeIntervals);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10b = solution.minMeetingRoomsChronological(largeIntervals);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10c = solution.minMeetingRoomsSweepLine(largeIntervals);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (10,000 meetings):");
        System.out.println("  Min Heap: " + time10a + " ns, Rooms: " + result10a);
        System.out.println("  Chronological: " + time10b + " ns, Rooms: " + result10b);
        System.out.println("  Sweep Line: " + time10c + " ns, Rooms: " + result10c);
        
        // Compare all approaches for consistency
        System.out.println("\nTest 11: Consistency Check");
        int[][] testIntervals = {{1,3},{2,4},{5,7}};
        int r1 = solution.minMeetingRooms(testIntervals);
        int r2 = solution.minMeetingRoomsChronological(testIntervals);
        int r3 = solution.minMeetingRoomsSweepLine(testIntervals);
        int r4 = solution.minMeetingRoomsTreeMap(testIntervals);
        
        System.out.println("All approaches consistent: " + 
                         (r1 == r2 && r1 == r3 && r1 == r4));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MIN HEAP ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We need to track ongoing meetings and assign rooms greedily.");
        System.out.println("The min heap stores end times of currently ongoing meetings.");
        System.out.println("When a new meeting starts:");
        System.out.println("  - If earliest ending meeting has finished, reuse that room");
        System.out.println("  - Otherwise, allocate a new room");
        
        System.out.println("\nWhy Min Heap works:");
        System.out.println("1. We always know when the next meeting will end (heap.peek())");
        System.out.println("2. If new meeting starts after earliest ending meeting ends,");
        System.out.println("   we can reuse that room");
        System.out.println("3. The heap size represents current active rooms");
        System.out.println("4. Maximum heap size = minimum rooms needed");
        
        System.out.println("\nVisual Example:");
        System.out.println("Meetings: [[0,30], [5,10], [15,20]]");
        System.out.println("Process:");
        System.out.println("  Add [0,30] -> Heap: [30], Rooms: 1");
        System.out.println("  [5,10] starts at 5, earliest end is 30 -> need new room");
        System.out.println("  Add [5,10] -> Heap: [10,30], Rooms: 2");
        System.out.println("  [15,20] starts at 15, earliest end is 10 -> reuse room");
        System.out.println("  Heap becomes: [20,30], Rooms: 2");
        System.out.println("Result: 2 rooms needed");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Min Heap (Priority Queue) - RECOMMENDED:");
        System.out.println("   Time: O(n log n) - Sorting + heap operations");
        System.out.println("   Space: O(n) - Heap storage in worst case");
        System.out.println("   How it works:");
        System.out.println("     - Sort intervals by start time");
        System.out.println("     - Use min heap to track end times");
        System.out.println("     - For each meeting, check if room available");
        System.out.println("     - Heap size gives minimum rooms needed");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and elegant");
        System.out.println("     - Most commonly expected in interviews");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of heap data structure");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Chronological Ordering (Two Pointers):");
        System.out.println("   Time: O(n log n) - Sorting two arrays");
        System.out.println("   Space: O(n) - Two additional arrays");
        System.out.println("   How it works:");
        System.out.println("     - Separate start and end times into two arrays");
        System.out.println("     - Sort both arrays");
        System.out.println("     - Use two pointers to track starts and ends");
        System.out.println("     - Count active meetings as we process starts and ends");
        System.out.println("   Pros:");
        System.out.println("     - No heap operations, potentially faster");
        System.out.println("     - Clear two-pointer approach");
        System.out.println("   Cons:");
        System.out.println("     - Less intuitive than heap approach");
        System.out.println("     - Requires separate arrays");
        System.out.println("   Best for: Alternative optimal solution");
        
        System.out.println("\n3. Sweep Line Algorithm:");
        System.out.println("   Time: O(n log n) - Sorting events");
        System.out.println("   Space: O(n) - Event list storage");
        System.out.println("   How it works:");
        System.out.println("     - Create events: +1 for start, -1 for end");
        System.out.println("     - Sort events by time");
        System.out.println("     - Sweep through time, counting active meetings");
        System.out.println("   Pros:");
        System.out.println("     - Very clear and mathematical");
        System.out.println("     - Easy to extend to other sweep line problems");
        System.out.println("   Cons:");
        System.out.println("     - More complex event creation and sorting");
        System.out.println("   Best for: Learning sweep line technique");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. The problem is equivalent to finding the maximum clique");
        System.out.println("   in an interval graph");
        System.out.println("2. Minimum rooms = Maximum number of overlapping intervals");
        System.out.println("3. The greedy approach is optimal because:");
        System.out.println("   - We always assign to an available room if possible");
        System.out.println("   - This minimizes the number of new rooms needed");
        System.out.println("4. The solution can be seen as an online algorithm");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Min Heap approach - it's the expected solution");
        System.out.println("2. Explain the greedy room assignment strategy clearly");
        System.out.println("3. Draw a timeline to visualize the room allocation");
        System.out.println("4. Discuss time/space complexity in detail");
        System.out.println("5. Mention alternative approaches (chronological, sweep line)");
        System.out.println("6. Handle edge cases: empty input, single meeting, no overlaps");
        System.out.println("7. Connect to related problems (252. Meeting Rooms)");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
