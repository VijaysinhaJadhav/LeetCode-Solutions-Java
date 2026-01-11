
# Solution.java

```java
import java.util.*;

/**
 * 1353. Maximum Number of Events That Can Be Attended
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given array of events [start, end], attend at most one event per day.
 * Can attend event i on any day d where start <= d <= end.
 * Return maximum number of events that can be attended.
 * 
 * Key Insights:
 * 1. Greedy approach: Always attend event that ends earliest
 * 2. Process days from min to max day
 * 3. Use min-heap to track available events by end day
 * 4. Sort events by start day for efficient processing
 */
class Solution {
    
    /**
     * Approach 1: Greedy with Min-Heap (Recommended)
     * Time: O(n log n), Space: O(n)
     */
    public int maxEvents(int[][] events) {
        // Sort events by start day
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Min-heap to store end days of available events
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        int maxDay = 0;
        for (int[] event : events) {
            maxDay = Math.max(maxDay, event[1]);
        }
        
        int eventIndex = 0;
        int attendedEvents = 0;
        
        // Process each day from 1 to maxDay
        for (int day = 1; day <= maxDay; day++) {
            // Add all events that start on this day to the heap
            while (eventIndex < events.length && events[eventIndex][0] == day) {
                minHeap.offer(events[eventIndex][1]);
                eventIndex++;
            }
            
            // Remove events that have already ended (end day < current day)
            while (!minHeap.isEmpty() && minHeap.peek() < day) {
                minHeap.poll();
            }
            
            // Attend the event that ends earliest
            if (!minHeap.isEmpty()) {
                minHeap.poll();
                attendedEvents++;
            }
        }
        
        return attendedEvents;
    }
    
    /**
     * Approach 2: Optimized Greedy - Process only days when events are available
     * Time: O(n log n), Space: O(n)
     * More efficient when events span many days
     */
    public int maxEventsOptimized(int[][] events) {
        if (events == null || events.length == 0) return 0;
        
        // Sort events by start day
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int n = events.length;
        int i = 0;
        int currentDay = 0;
        int result = 0;
        
        while (i < n || !minHeap.isEmpty()) {
            // If no events available, jump to next event's start day
            if (minHeap.isEmpty()) {
                currentDay = events[i][0];
            }
            
            // Add all events starting on or before current day
            while (i < n && events[i][0] <= currentDay) {
                minHeap.offer(events[i][1]);
                i++;
            }
            
            // Attend the earliest ending event
            minHeap.poll();
            result++;
            currentDay++;
            
            // Remove events that have already ended
            while (!minHeap.isEmpty() && minHeap.peek() < currentDay) {
                minHeap.poll();
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Greedy with Event Objects for clarity
     * Time: O(n log n), Space: O(n)
     */
    public int maxEventsWithObjects(int[][] events) {
        List<Event> eventList = new ArrayList<>();
        for (int[] e : events) {
            eventList.add(new Event(e[0], e[1]));
        }
        
        // Sort events by start day
        Collections.sort(eventList, (a, b) -> Integer.compare(a.start, b.start));
        
        PriorityQueue<Integer> endDays = new PriorityQueue<>();
        int eventIndex = 0;
        int attended = 0;
        
        // Find max day
        int maxDay = 0;
        for (Event e : eventList) {
            maxDay = Math.max(maxDay, e.end);
        }
        
        for (int day = 1; day <= maxDay; day++) {
            // Add events starting today
            while (eventIndex < eventList.size() && eventList.get(eventIndex).start == day) {
                endDays.offer(eventList.get(eventIndex).end);
                eventIndex++;
            }
            
            // Remove expired events
            while (!endDays.isEmpty() && endDays.peek() < day) {
                endDays.poll();
            }
            
            // Attend one event today
            if (!endDays.isEmpty()) {
                endDays.poll();
                attended++;
            }
            
            // If we've processed all events and heap is empty, we can stop
            if (eventIndex == eventList.size() && endDays.isEmpty()) {
                break;
            }
        }
        
        return attended;
    }
    
    /**
     * Approach 4: Alternative - Sort by end day first
     * Time: O(n log n), Space: O(n)
     * Not as efficient but shows different perspective
     */
    public int maxEventsAlternative(int[][] events) {
        // Sort events by end day
        Arrays.sort(events, (a, b) -> Integer.compare(a[1], b[1]));
        
        // Track which days are used
        boolean[] used = new boolean[100001]; // Max constraint is 10^5
        int count = 0;
        
        for (int[] event : events) {
            for (int day = event[0]; day <= event[1]; day++) {
                if (!used[day]) {
                    used[day] = true;
                    count++;
                    break;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 5: Union-Find for day assignment (Advanced)
     * Time: O(n log n), Space: O(n)
     * Uses union-find to find next available day efficiently
     */
    public int maxEventsUnionFind(int[][] events) {
        // Sort events by end day
        Arrays.sort(events, (a, b) -> Integer.compare(a[1], b[1]));
        
        // Find max day
        int maxDay = 0;
        for (int[] event : events) {
            maxDay = Math.max(maxDay, event[1]);
        }
        
        // Union-Find to track next available day
        int[] parent = new int[maxDay + 2];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
        
        int count = 0;
        for (int[] event : events) {
            int day = find(parent, event[0]);
            if (day <= event[1]) {
                count++;
                parent[day] = day + 1;
            }
        }
        
        return count;
    }
    
    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }
    
    /**
     * Helper class for Approach 3
     */
    class Event {
        int start;
        int end;
        
        Event(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    
    /**
     * Helper: Visualize events and greedy selection
     */
    public void visualizeEvents(int[][] events) {
        System.out.println("\nEvent Visualization:");
        System.out.println("Events: " + Arrays.deepToString(events));
        
        // Sort events for display
        int[][] sortedEvents = events.clone();
        Arrays.sort(sortedEvents, (a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        
        System.out.println("\nSorted events (by start day):");
        for (int[] event : sortedEvents) {
            System.out.printf("[%d, %d] ", event[0], event[1]);
        }
        System.out.println();
        
        // Find min and max days
        int minDay = Integer.MAX_VALUE;
        int maxDay = 0;
        for (int[] event : events) {
            minDay = Math.min(minDay, event[0]);
            maxDay = Math.max(maxDay, event[1]);
        }
        
        System.out.println("\nDay-by-day processing:");
        int result = maxEvents(events);
        System.out.println("Maximum events that can be attended: " + result);
    }
    
    /**
     * Helper: Show step-by-step greedy selection
     */
    public void showGreedySteps(int[][] events) {
        System.out.println("\nGreedy Step-by-Step:");
        
        int[][] sortedEvents = events.clone();
        Arrays.sort(sortedEvents, (a, b) -> Integer.compare(a[0], b[0]));
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int eventIndex = 0;
        int attended = 0;
        
        int maxDay = 0;
        for (int[] event : events) {
            maxDay = Math.max(maxDay, event[1]);
        }
        
        for (int day = 1; day <= maxDay; day++) {
            System.out.printf("\nDay %d:", day);
            
            // Add events starting today
            List<Integer> addedToday = new ArrayList<>();
            while (eventIndex < sortedEvents.length && sortedEvents[eventIndex][0] == day) {
                minHeap.offer(sortedEvents[eventIndex][1]);
                addedToday.add(sortedEvents[eventIndex][1]);
                eventIndex++;
            }
            
            if (!addedToday.isEmpty()) {
                System.out.print(" Added events ending on: " + addedToday);
            }
            
            // Remove expired events
            List<Integer> removedToday = new ArrayList<>();
            while (!minHeap.isEmpty() && minHeap.peek() < day) {
                removedToday.add(minHeap.poll());
            }
            
            if (!removedToday.isEmpty()) {
                System.out.print(" Removed expired events: " + removedToday);
            }
            
            // Attend an event
            if (!minHeap.isEmpty()) {
                int attendedEventEnd = minHeap.poll();
                attended++;
                System.out.print(" → Attended event ending on day " + attendedEventEnd);
                System.out.print(" (Total: " + attended + ")");
            } else {
                System.out.print(" → No event to attend");
            }
            
            System.out.print(" Heap: " + new ArrayList<>(minHeap));
        }
        
        System.out.println("\n\nTotal events attended: " + attended);
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(int[][] events) {
        System.out.println("\nComparing Approaches:");
        System.out.println("Events: " + Arrays.deepToString(events));
        
        long startTime, endTime;
        int result1 = 0, result2 = 0, result3 = 0, result4 = 0, result5 = 0;
        
        // Approach 1
        startTime = System.nanoTime();
        result1 = maxEvents(events);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2
        startTime = System.nanoTime();
        result2 = maxEventsOptimized(events);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3
        startTime = System.nanoTime();
        result3 = maxEventsWithObjects(events);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4 (only for small input due to O(n*range))
        if (events.length <= 1000) {
            startTime = System.nanoTime();
            result4 = maxEventsAlternative(events);
            endTime = System.nanoTime();
            long time4 = endTime - startTime;
        }
        
        // Approach 5
        startTime = System.nanoTime();
        result5 = maxEventsUnionFind(events);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (Greedy+Heap): " + result1);
        System.out.println("Approach 2 (Optimized):   " + result2);
        System.out.println("Approach 3 (Objects):     " + result3);
        if (events.length <= 1000) {
            System.out.println("Approach 4 (Day Array):   " + result4);
        }
        System.out.println("Approach 5 (Union-Find):  " + result5);
        
        boolean allEqual = (result1 == result2) && (result2 == result3) && (result3 == result5);
        if (events.length <= 1000) {
            allEqual = allEqual && (result3 == result4);
        }
        
        System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Greedy+Heap:   %-10d (Recommended for interviews)%n", time1);
        System.out.printf("Optimized:     %-10d (Skips empty days)%n", time2);
        System.out.printf("Objects:       %-10d (More readable)%n", time3);
        if (events.length <= 1000) {
            System.out.printf("Day Array:     %-10d (Only for small inputs)%n", time4);
        }
        System.out.printf("Union-Find:    %-10d (Advanced approach)%n", time5);
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCase(int n, int maxRange) {
        Random rand = new Random();
        int[][] events = new int[n][2];
        
        for (int i = 0; i < n; i++) {
            int start = rand.nextInt(maxRange) + 1;
            int end = start + rand.nextInt(Math.min(10, maxRange - start + 1));
            events[i][0] = start;
            events[i][1] = end;
        }
        
        return events;
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        
        // Test 1: Single event
        System.out.println("\n1. Single event:");
        int[][] events1 = {{1, 5}};
        System.out.println("Events: " + Arrays.deepToString(events1));
        System.out.println("Result: " + maxEvents(events1) + " (expected: 1)");
        
        // Test 2: All events on same day
        System.out.println("\n2. All events on same day:");
        int[][] events2 = {{1, 1}, {1, 1}, {1, 1}};
        System.out.println("Events: " + Arrays.deepToString(events2));
        System.out.println("Result: " + maxEvents(events2) + " (expected: 1)");
        
        // Test 3: Non-overlapping events
        System.out.println("\n3. Non-overlapping events:");
        int[][] events3 = {{1, 1}, {2, 2}, {3, 3}, {4, 4}};
        System.out.println("Events: " + Arrays.deepToString(events3));
        System.out.println("Result: " + maxEvents(events3) + " (expected: 4)");
        
        // Test 4: Fully overlapping events
        System.out.println("\n4. Fully overlapping events:");
        int[][] events4 = {{1, 5}, {1, 5}, {1, 5}};
        System.out.println("Events: " + Arrays.deepToString(events4));
        System.out.println("Result: " + maxEvents(events4) + " (expected: 5)");
        
        // Test 5: Example from problem
        System.out.println("\n5. Example 1 from problem:");
        int[][] events5 = {{1, 2}, {2, 3}, {3, 4}};
        System.out.println("Events: " + Arrays.deepToString(events5));
        System.out.println("Result: " + maxEvents(events5) + " (expected: 3)");
        
        // Test 6: Example 2 from problem
        System.out.println("\n6. Example 2 from problem:");
        int[][] events6 = {{1, 2}, {2, 3}, {3, 4}, {1, 2}};
        System.out.println("Events: " + Arrays.deepToString(events6));
        System.out.println("Result: " + maxEvents(events6) + " (expected: 4)");
        
        // Test 7: Large range
        System.out.println("\n7. Large range events:");
        int[][] events7 = {{1, 100000}, {2, 99999}, {3, 99998}};
        System.out.println("Events: " + Arrays.deepToString(events7));
        System.out.println("Result: " + maxEvents(events7) + " (expected: 3)");
    }
    
    /**
     * Helper: Explain greedy strategy
     */
    public void explainGreedyStrategy() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EXPLANATION OF GREEDY STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nWhy greedy works for this problem:");
        System.out.println("1. At any day, we want to attend an event");
        System.out.println("2. If we have multiple choices, which one should we attend?");
        System.out.println("3. Intuition: Attend the event that ends earliest");
        System.out.println("   - It will expire sooner");
        System.out.println("   - Leaves more days for other events");
        
        System.out.println("\nProof sketch:");
        System.out.println("1. Let OPT be optimal solution");
        System.out.println("2. Let GREEDY be our greedy solution");
        System.out.println("3. We can transform OPT to GREEDY without decreasing count");
        System.out.println("4. Whenever OPT and GREEDY differ, swap events");
        System.out.println("5. Greedy choice (earliest end) is always safe");
        
        System.out.println("\nAlgorithm steps:");
        System.out.println("1. Sort events by start day");
        System.out.println("2. Use min-heap to store end days of available events");
        System.out.println("3. For each day from 1 to maxDay:");
        System.out.println("   a. Add events starting today to heap");
        System.out.println("   b. Remove events that have already ended");
        System.out.println("   c. If heap not empty, attend event (pop from heap)");
        
        System.out.println("\nExample:");
        System.out.println("Events: [[1,2], [2,3], [3,4], [1,2]]");
        System.out.println("Day 1: Add [1,2], [1,2] → Heap: [2, 2] → Attend one → Remaining: [2]");
        System.out.println("Day 2: Add [2,3] → Heap: [2, 3] → Attend one → Remaining: [3]");
        System.out.println("Day 3: Add [3,4] → Heap: [3, 4] → Attend one → Remaining: [4]");
        System.out.println("Day 4: Heap: [4] → Attend one");
        System.out.println("Total: 4 events attended ✓");
    }
    
    /**
     * Main method with comprehensive tests
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Maximum Number of Events That Can Be Attended");
        System.out.println("=============================================");
        
        // Explain strategy
        solution.explainGreedyStrategy();
        
        // Test edge cases
        solution.testEdgeCases();
        
        // Example tests
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EXAMPLE TESTS:");
        System.out.println("=".repeat(80));
        
        // Example 1
        System.out.println("\nExample 1:");
        int[][] example1 = {{1, 2}, {2, 3}, {3, 4}};
        solution.visualizeEvents(example1);
        solution.showGreedySteps(example1);
        solution.compareApproaches(example1);
        
        // Example 2
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Example 2:");
        int[][] example2 = {{1, 2}, {2, 3}, {3, 4}, {1, 2}};
        solution.visualizeEvents(example2);
        solution.showGreedySteps(example2);
        solution.compareApproaches(example2);
        
        // Generated test cases
        System.out.println("\n" + "=".repeat(80));
        System.out.println("GENERATED TEST CASES:");
        System.out.println("=".repeat(80));
        
        // Small test
        System.out.println("\nSmall test (10 events):");
        int[][] smallTest = solution.generateTestCase(10, 20);
        solution.compareApproaches(smallTest);
        
        // Medium test
        System.out.println("\nMedium test (100 events):");
        int[][] mediumTest = solution.generateTestCase(100, 100);
        solution.compareApproaches(mediumTest);
        
        // Large test (skip Approach 4 which is O(n*range))
        System.out.println("\nLarge test (1000 events):");
        int[][] largeTest = solution.generateTestCase(1000, 1000);
        long startTime = System.currentTimeMillis();
        int result = solution.maxEvents(largeTest);
        long endTime = System.currentTimeMillis();
        System.out.println("Result: " + result);
        System.out.println("Time: " + (endTime - startTime) + "ms");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE COMPARISON:");
        System.out.println("=".repeat(80));
        
        int[] sizes = {100, 500, 1000, 5000, 10000};
        for (int size : sizes) {
            System.out.println("\nTesting with " + size + " events:");
            int[][] testEvents = solution.generateTestCase(size, size * 2);
            
            startTime = System.currentTimeMillis();
            int r1 = solution.maxEvents(testEvents);
            endTime = System.currentTimeMillis();
            System.out.printf("Greedy+Heap: %dms, Result: %d%n", endTime - startTime, r1);
            
            startTime = System.currentTimeMillis();
            int r2 = solution.maxEventsOptimized(testEvents);
            endTime = System.currentTimeMillis();
            System.out.printf("Optimized:   %dms, Result: %d%n", endTime - startTime, r2);
            
            if (r1 != r2) {
                System.out.println("ERROR: Results don't match!");
            }
        }
        
        // Key takeaways
        System.out.println("\n" + "=".repeat(80));
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Greedy Strategy:");
        System.out.println("   - Always attend event that ends earliest");
        System.out.println("   - This maximizes future opportunities");
        
        System.out.println("\n2. Data Structures:");
        System.out.println("   - Sort events by start day: O(n log n)");
        System.out.println("   - Min-heap for end days: O(n log n)");
        
        System.out.println("\n3. Time Complexity:");
        System.out.println("   - Overall: O(n log n)");
        System.out.println("   - Sorting: O(n log n)");
        System.out.println("   - Heap operations: O(n log n)");
        
        System.out.println("\n4. Space Complexity:");
        System.out.println("   - O(n) for heap and sorting");
        
        System.out.println("\n5. For Interviews:");
        System.out.println("   - Explain why greedy works (earliest end first)");
        System.out.println("   - Mention alternative approaches");
        System.out.println("   - Handle edge cases (empty input, single event, etc.)");
        System.out.println("   - Discuss time/space complexity");
        
        System.out.println("\n6. Common Mistakes:");
        System.out.println("   - Forgetting to remove expired events from heap");
        System.out.println("   - Not sorting events by start day");
        System.out.println("   - Processing all days instead of event days");
        
        // Additional visualization
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ADDITIONAL VISUALIZATION:");
        System.out.println("=".repeat(80));
        
        int[][] complexExample = {
            {1, 3}, {1, 1}, {2, 2}, {2, 3}, 
            {3, 3}, {4, 4}, {5, 5}, {1, 5}
        };
        
        System.out.println("\nComplex example:");
        solution.visualizeEvents(complexExample);
        solution.showGreedySteps(complexExample);
    }
}
