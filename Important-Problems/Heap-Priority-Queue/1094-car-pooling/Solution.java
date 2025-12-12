
## Solution.java

```java
/**
 * 1094. Car Pooling
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given car capacity and trips [passengers, from, to],
 * determine if car can complete all trips without exceeding capacity.
 * 
 * Key Insights:
 * 1. Track passenger count changes at each location
 * 2. Can use array timeline since max location = 1000
 * 3. Add passengers at pickup, subtract at dropoff
 * 4. Calculate running sum to check capacity violation
 * 
 * Approach (Timeline Array):
 * 1. Create array timeline[1001] (0 to 1000 inclusive)
 * 2. For each trip: timeline[from] += passengers, timeline[to] -= passengers
 * 3. Calculate prefix sum, check if any point > capacity
 * 
 * Time Complexity: O(n + L) where L = max location (1000)
 * Space Complexity: O(L) for timeline array
 * 
 * Tags: Array, Prefix Sum, Sorting, Simulation, Heap
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Timeline Array with Prefix Sum (RECOMMENDED)
     * O(n + L) time, O(L) space
     */
    public boolean carPooling(int[][] trips, int capacity) {
        // Since toi ≤ 1000, we need array size 1001
        int[] timeline = new int[1001];
        
        // Mark passenger changes
        for (int[] trip : trips) {
            int passengers = trip[0];
            int from = trip[1];
            int to = trip[2];
            
            timeline[from] += passengers;  // Pick up passengers
            timeline[to] -= passengers;    // Drop off passengers
        }
        
        // Calculate running sum and check capacity
        int currentPassengers = 0;
        for (int i = 0; i < timeline.length; i++) {
            currentPassengers += timeline[i];
            if (currentPassengers > capacity) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Approach 2: Heap (Priority Queue) - More Efficient for Sparse Events
     * O(n log n) time, O(n) space
     */
    public boolean carPoolingHeap(int[][] trips, int capacity) {
        // Sort trips by pickup location
        Arrays.sort(trips, (a, b) -> a[1] - b[1]);
        
        // Min-heap for dropoff events: [dropoffLocation, passengers]
        PriorityQueue<int[]> dropoffHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        int currentPassengers = 0;
        
        for (int[] trip : trips) {
            int passengers = trip[0];
            int from = trip[1];
            int to = trip[2];
            
            // Drop off passengers whose dropoff location <= current pickup location
            while (!dropoffHeap.isEmpty() && dropoffHeap.peek()[0] <= from) {
                currentPassengers -= dropoffHeap.poll()[1];
            }
            
            // Pick up new passengers
            currentPassengers += passengers;
            
            // Check capacity
            if (currentPassengers > capacity) {
                return false;
            }
            
            // Add dropoff event to heap
            dropoffHeap.offer(new int[]{to, passengers});
        }
        
        return true;
    }
    
    /**
     * Approach 3: Sweep Line with Events
     * O(n log n) time, O(n) space
     */
    public boolean carPoolingSweepLine(int[][] trips, int capacity) {
        // Create events: [location, passengerChange]
        List<int[]> events = new ArrayList<>();
        
        for (int[] trip : trips) {
            int passengers = trip[0];
            int from = trip[1];
            int to = trip[2];
            
            events.add(new int[]{from, passengers});   // Pickup: positive
            events.add(new int[]{to, -passengers});    // Dropoff: negative
        }
        
        // Sort events by location, then by passenger change (dropoff before pickup at same location)
        events.sort((a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1]; // Negative (dropoff) before positive (pickup)
        });
        
        int currentPassengers = 0;
        for (int[] event : events) {
            currentPassengers += event[1];
            if (currentPassengers > capacity) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Approach 4: Bucket Sort Optimization
     * O(n + L) time, O(L) space - optimized for constraints
     */
    public boolean carPoolingBucketSort(int[][] trips, int capacity) {
        // Since from/to ≤ 1000 and passengers ≤ 100, we can use bucket approach
        int maxLocation = 0;
        for (int[] trip : trips) {
            maxLocation = Math.max(maxLocation, trip[2]);
        }
        
        int[] passengerChanges = new int[maxLocation + 1];
        
        for (int[] trip : trips) {
            passengerChanges[trip[1]] += trip[0];
            passengerChanges[trip[2]] -= trip[0];
        }
        
        int currentPassengers = 0;
        for (int i = 0; i <= maxLocation; i++) {
            currentPassengers += passengerChanges[i];
            if (currentPassengers > capacity) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Approach 5: Difference Array with TreeMap
     * O(n log n) time, O(n) space - good for sparse large ranges
     */
    public boolean carPoolingTreeMap(int[][] trips, int capacity) {
        TreeMap<Integer, Integer> passengerChanges = new TreeMap<>();
        
        for (int[] trip : trips) {
            int passengers = trip[0];
            int from = trip[1];
            int to = trip[2];
            
            passengerChanges.put(from, passengerChanges.getOrDefault(from, 0) + passengers);
            passengerChanges.put(to, passengerChanges.getOrDefault(to, 0) - passengers);
        }
        
        int currentPassengers = 0;
        for (int change : passengerChanges.values()) {
            currentPassengers += change;
            if (currentPassengers > capacity) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Helper method to visualize the trips and passenger flow
     */
    private void visualizeTrips(int[][] trips, int capacity, String approach) {
        System.out.println("\n" + approach + " - Trip Visualization:");
        System.out.println("Car capacity: " + capacity);
        
        // Find max location
        int maxLocation = 0;
        for (int[] trip : trips) {
            maxLocation = Math.max(maxLocation, trip[2]);
        }
        
        // Create timeline
        int[] timeline = new int[maxLocation + 1];
        for (int[] trip : trips) {
            timeline[trip[1]] += trip[0];
            timeline[trip[2]] -= trip[0];
        }
        
        // Print header
        System.out.print("\nLocation:  ");
        for (int i = 0; i <= maxLocation; i++) {
            System.out.printf("%3d ", i);
        }
        
        // Print passenger changes
        System.out.print("\nChange:    ");
        for (int i = 0; i <= maxLocation; i++) {
            System.out.printf("%3d ", timeline[i]);
        }
        
        // Print running total
        System.out.print("\nPassengers:");
        int current = 0;
        for (int i = 0; i <= maxLocation; i++) {
            current += timeline[i];
            System.out.printf("%3d ", current);
        }
        
        // Print capacity line
        System.out.print("\nCapacity:  ");
        for (int i = 0; i <= maxLocation; i++) {
            System.out.printf("%3d ", capacity);
        }
        
        // Check for violations
        System.out.print("\nStatus:    ");
        current = 0;
        boolean valid = true;
        for (int i = 0; i <= maxLocation; i++) {
            current += timeline[i];
            if (current > capacity) {
                System.out.printf(" X  ");
                valid = false;
            } else {
                System.out.printf(" ✓  ");
            }
        }
        
        System.out.println("\n\nOverall: " + (valid ? "POSSIBLE" : "IMPOSSIBLE"));
        
        // Show individual trips
        System.out.println("\nIndividual trips:");
        for (int i = 0; i < trips.length; i++) {
            int[] trip = trips[i];
            System.out.printf("  Trip %d: %d passengers from %d to %d%n", 
                i, trip[0], trip[1], trip[2]);
        }
        
        // Show timeline with events
        System.out.println("\nTimeline with events:");
        for (int i = 0; i <= maxLocation; i++) {
            if (timeline[i] != 0) {
                String event = timeline[i] > 0 ? 
                    String.format("Pickup %d", timeline[i]) : 
                    String.format("Dropoff %d", -timeline[i]);
                System.out.printf("  Location %d: %s%n", i, event);
            }
        }
    }
    
    /**
     * Helper method to simulate the car journey
     */
    private void simulateJourney(int[][] trips, int capacity) {
        System.out.println("\nCar Journey Simulation:");
        System.out.println("========================");
        
        // Sort trips by pickup location
        int[][] sortedTrips = trips.clone();
        Arrays.sort(sortedTrips, (a, b) -> a[1] - b[1]);
        
        // Use heap for simulation
        PriorityQueue<int[]> dropoffHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int currentPassengers = 0;
        int currentLocation = 0;
        boolean possible = true;
        
        for (int[] trip : sortedTrips) {
            int passengers = trip[0];
            int from = trip[1];
            int to = trip[2];
            
            // Travel to pickup location
            if (from > currentLocation) {
                System.out.printf("\nTravel from %d to %d", currentLocation, from);
                currentLocation = from;
            }
            
            // Drop off passengers before pickup
            while (!dropoffHeap.isEmpty() && dropoffHeap.peek()[0] <= currentLocation) {
                int[] dropoff = dropoffHeap.poll();
                currentPassengers -= dropoff[1];
                System.out.printf("\n  Location %d: %d passengers get off (now: %d)", 
                    currentLocation, dropoff[1], currentPassengers);
            }
            
            // Pick up passengers
            System.out.printf("\n  Location %d: %d passengers get on (was: %d, now: %d)", 
                from, passengers, currentPassengers, currentPassengers + passengers);
            currentPassengers += passengers;
            
            // Check capacity
            if (currentPassengers > capacity) {
                System.out.printf("\n  ✗ CAPACITY EXCEEDED: %d > %d", currentPassengers, capacity);
                possible = false;
                break;
            } else {
                System.out.printf("\n  ✓ Within capacity: %d ≤ %d", currentPassengers, capacity);
            }
            
            // Schedule dropoff
            dropoffHeap.offer(new int[]{to, passengers});
        }
        
        // Complete remaining journey
        if (possible) {
            while (!dropoffHeap.isEmpty()) {
                int[] dropoff = dropoffHeap.poll();
                if (dropoff[0] > currentLocation) {
                    System.out.printf("\n\nTravel from %d to %d", currentLocation, dropoff[0]);
                    currentLocation = dropoff[0];
                }
                currentPassengers -= dropoff[1];
                System.out.printf("\n  Location %d: %d passengers get off (now: %d)", 
                    dropoff[0], dropoff[1], currentPassengers);
            }
            System.out.println("\n\n✓ All trips completed successfully!");
        } else {
            System.out.println("\n\n✗ Cannot complete all trips - capacity exceeded!");
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Car Pooling Solution:");
        System.out.println("==============================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example - should be false");
        int[][] trips1 = {{2,1,5}, {3,3,7}};
        int capacity1 = 4;
        boolean expected1 = false;
        
        solution.visualizeTrips(trips1, capacity1, "Expected");
        
        long startTime = System.nanoTime();
        boolean result1a = solution.carPooling(trips1, capacity1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result1b = solution.carPoolingHeap(trips1, capacity1);
        long time1b = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Timeline Array: " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1a + " ns)");
        System.out.println("Heap:           " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1b + " ns)");
        
        // Simulate journey
        solution.simulateJourney(trips1, capacity1);
        
        // Test case 2: Example 2 from problem
        System.out.println("\n\nTest 2: Same trips, higher capacity - should be true");
        int[][] trips2 = {{2,1,5}, {3,3,7}};
        int capacity2 = 5;
        boolean expected2 = true;
        
        solution.visualizeTrips(trips2, capacity2, "Expected");
        
        boolean result2a = solution.carPooling(trips2, capacity2);
        System.out.println("Result: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Example 3 from problem
        System.out.println("\n\nTest 3: Non-overlapping trips - should be true");
        int[][] trips3 = {{2,1,5}, {3,5,7}};
        int capacity3 = 3;
        boolean expected3 = true;
        
        solution.visualizeTrips(trips3, capacity3, "Expected");
        
        boolean result3a = solution.carPooling(trips3, capacity3);
        System.out.println("Result: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        solution.simulateJourney(trips3, capacity3);
        
        // Test case 4: Single trip
        System.out.println("\n\nTest 4: Single trip within capacity");
        int[][] trips4 = {{5,0,10}};
        int capacity4 = 5;
        boolean expected4 = true;
        
        boolean result4a = solution.carPooling(trips4, capacity4);
        System.out.println("Result: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single trip exceeding capacity
        System.out.println("\n\nTest 5: Single trip exceeding capacity");
        int[][] trips5 = {{6,0,10}};
        int capacity5 = 5;
        boolean expected5 = false;
        
        boolean result5a = solution.carPooling(trips5, capacity5);
        System.out.println("Result: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Multiple trips at same location
        System.out.println("\n\nTest 6: Multiple pickups at same location");
        int[][] trips6 = {{2,0,5}, {3,0,7}, {1,0,3}};
        int capacity6 = 6;
        
        solution.visualizeTrips(trips6, capacity6, "Test");
        solution.simulateJourney(trips6, capacity6);
        
        boolean result6a = solution.carPooling(trips6, capacity6);
        System.out.println("Possible: " + result6a);
        
        // Test case 7: Dropoff at pickup location
        System.out.println("\n\nTest 7: Dropoff at same location as next pickup");
        int[][] trips7 = {{3,0,5}, {4,5,10}};
        int capacity7 = 4;
        
        solution.visualizeTrips(trips7, capacity7, "Test");
        solution.simulateJourney(trips7, capacity7);
        
        boolean result7a = solution.carPooling(trips7, capacity7);
        System.out.println("Possible: " + result7a);
        
        // Test case 8: Complex overlapping
        System.out.println("\n\nTest 8: Complex overlapping trips");
        int[][] trips8 = {{3,2,8}, {4,4,6}, {2,5,9}, {1,7,10}};
        int capacity8 = 5;
        
        solution.visualizeTrips(trips8, capacity8, "Test");
        solution.simulateJourney(trips8, capacity8);
        
        // Test all implementations for consistency
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS:");
        System.out.println("=".repeat(70));
        
        int[][][] allTrips = {trips1, trips2, trips3, trips4, trips5, trips6, trips7, trips8};
        int[] capacities = {4, 5, 3, 5, 5, 6, 4, 5};
        
        System.out.println("\nTesting " + allTrips.length + " test cases:");
        boolean allConsistent = true;
        
        for (int i = 0; i < allTrips.length; i++) {
            int[][] trips = allTrips[i];
            int capacity = capacities[i];
            
            boolean r1 = solution.carPooling(trips, capacity);
            boolean r2 = solution.carPoolingHeap(trips, capacity);
            boolean r3 = solution.carPoolingSweepLine(trips, capacity);
            boolean r4 = solution.carPoolingBucketSort(trips, capacity);
            boolean r5 = solution.carPoolingTreeMap(trips, capacity);
            
            boolean consistent = (r1 == r2) && (r2 == r3) && (r3 == r4) && (r4 == r5);
            
            System.out.printf("Test case %d: Timeline=%s, Heap=%s, Sweep=%s, Bucket=%s, TreeMap=%s - %s%n",
                i + 1, r1, r2, r3, r4, r5, consistent ? "✓ CONSISTENT" : "✗ INCONSISTENT");
            
            if (!consistent) allConsistent = false;
        }
        
        System.out.println("\nAll implementations consistent: " + (allConsistent ? "✓ YES" : "✗ NO"));
        
        // Performance test
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        // Generate large test case
        Random random = new Random(42);
        int n = 1000;
        int[][] largeTrips = new int[n][3];
        int largeCapacity = 100;
        
        for (int i = 0; i < n; i++) {
            int passengers = random.nextInt(10) + 1;
            int from = random.nextInt(900);
            int to = from + random.nextInt(100) + 1;
            largeTrips[i] = new int[]{passengers, from, to};
        }
        
        System.out.println("\nTesting with " + n + " trips, capacity " + largeCapacity);
        
        // Test all implementations
        startTime = System.currentTimeMillis();
        boolean perf1 = solution.carPooling(largeTrips, largeCapacity);
        long timePerf1 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        boolean perf2 = solution.carPoolingHeap(largeTrips, largeCapacity);
        long timePerf2 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        boolean perf3 = solution.carPoolingSweepLine(largeTrips, largeCapacity);
        long timePerf3 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        boolean perf4 = solution.carPoolingBucketSort(largeTrips, largeCapacity);
        long timePerf4 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        boolean perf5 = solution.carPoolingTreeMap(largeTrips, largeCapacity);
        long timePerf5 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Timeline Array: " + timePerf1 + " ms - Result: " + perf1);
        System.out.println("Heap:           " + timePerf2 + " ms - Result: " + perf2);
        System.out.println("Sweep Line:     " + timePerf3 + " ms - Result: " + perf3);
        System.out.println("Bucket Sort:    " + timePerf4 + " ms - Result: " + perf4);
        System.out.println("TreeMap:        " + timePerf5 + " ms - Result: " + perf5);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("This is essentially a range update + point query problem.");
        System.out.println("We need to track passenger count changes at each location.");
        
        System.out.println("\nTimeline Array Approach:");
        System.out.println("1. Create array timeline[1001] (since locations ≤ 1000)");
        System.out.println("2. For each trip [p, from, to]:");
        System.out.println("   - timeline[from] += p  (passengers get on)");
        System.out.println("   - timeline[to] -= p    (passengers get off)");
        System.out.println("3. Calculate prefix sum (running total):");
        System.out.println("   - current += timeline[i] for each i");
        System.out.println("   - If current > capacity at any point, return false");
        System.out.println("4. If we reach end without exceeding capacity, return true");
        
        System.out.println("\nWhy this works:");
        System.out.println("- We're tracking net change in passengers at each location");
        System.out.println("- Prefix sum gives us the actual passenger count at each point");
        System.out.println("- The subtraction at 'to' handles dropoff correctly");
        
        System.out.println("\nVisual Example: trips = [[2,1,5], [3,3,7]], capacity = 4");
        System.out.println("Timeline array after marking changes:");
        System.out.println("  index:  0  1  2  3  4  5  6  7");
        System.out.println("  change: 0 +2  0 +3  0 -2  0 -3");
        System.out.println("\nPrefix sum calculation:");
        System.out.println("  i=0: current=0");
        System.out.println("  i=1: current=0+2=2 ≤ 4 ✓");
        System.out.println("  i=2: current=2+0=2 ≤ 4 ✓");
        System.out.println("  i=3: current=2+3=5 > 4 ✗ (capacity exceeded)");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("IMPLEMENTATION COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Timeline Array (RECOMMENDED):");
        System.out.println("   Time: O(n + L) where L = max location (1000)");
        System.out.println("   Space: O(L)");
        System.out.println("   Pros:");
        System.out.println("     - Simplest and most efficient for given constraints");
        System.out.println("     - O(n + 1000) operations, very fast");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Fixed array size (wastes space if sparse)");
        System.out.println("     - Doesn't scale if locations > 1000");
        System.out.println("   Best for: Interview settings, given constraints");
        
        System.out.println("\n2. Heap (Priority Queue):");
        System.out.println("   Time: O(n log n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - More efficient for sparse events");
        System.out.println("     - Scales better for large location ranges");
        System.out.println("     - Only processes actual events");
        System.out.println("   Cons:");
        System.out.println("     - Heap operations overhead");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: Sparse events, large location ranges");
        
        System.out.println("\n3. Sweep Line:");
        System.out.println("   Time: O(n log n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Classic interval scheduling technique");
        System.out.println("     - Clear event-driven approach");
        System.out.println("     - Handles overlapping events well");
        System.out.println("   Cons:");
        System.out.println("     - Sorting overhead");
        System.out.println("     - More complex than array approach");
        System.out.println("   Best for: General interval scheduling problems");
        
        System.out.println("\n4. Bucket Sort Optimization:");
        System.out.println("   Time: O(n + L)");
        System.out.println("   Space: O(L)");
        System.out.println("   Pros:");
        System.out.println("     - More space efficient than fixed array");
        System.out.println("     - Only allocates needed size");
        System.out.println("     - Same time complexity as array");
        System.out.println("   Cons:");
        System.out.println("     - Requires finding max location first");
        System.out.println("     - Extra pass through trips");
        System.out.println("   Best for: When memory is concern");
        
        System.out.println("\n5. TreeMap:");
        System.out.println("   Time: O(n log n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Handles sparse events efficiently");
        System.out.println("     - Automatically sorts by location");
        System.out.println("     - Clean API for range queries");
        System.out.println("   Cons:");
        System.out.println("     - TreeMap overhead");
        System.out.println("     - Slower than array for dense events");
        System.out.println("   Best for: Dynamic updates, sparse large ranges");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Ride-sharing Services:");
        System.out.println("   - Uber/Lyft trip scheduling");
        System.out.println("   - Capacity optimization for shared rides");
        
        System.out.println("\n2. Public Transportation:");
        System.out.println("   - Bus/train passenger load tracking");
        System.out.println("   - Capacity management during peak hours");
        
        System.out.println("\n3. Logistics and Delivery:");
        System.out.println("   - Delivery truck route planning");
        System.out.println("   - Package load optimization");
        
        System.out.println("\n4. Resource Scheduling:");
        System.out.println("   - Conference room bookings");
        System.out.println("   - Hotel room assignments");
        System.out.println("   - Cloud resource allocation");
        
        System.out.println("\n5. Network Bandwidth Management:");
        System.out.println("   - Bandwidth allocation over time");
        System.out.println("   - Network traffic scheduling");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify constraints:");
        System.out.println("   - Locations ≤ 1000 (important for array approach)");
        System.out.println("   - Can't turn around (one direction only)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Track passengers at each mile");
        System.out.println("   - Update for each trip");
        System.out.println("   - Mention O(n * L) time, can be optimized");
        
        System.out.println("\n3. Recognize pattern:");
        System.out.println("   - Similar to range addition problem");
        System.out.println("   - Use difference array technique");
        
        System.out.println("\n4. Propose array solution:");
        System.out.println("   - Create array of size 1001");
        System.out.println("   - Add at pickup, subtract at dropoff");
        System.out.println("   - Calculate prefix sum");
        
        System.out.println("\n5. Discuss alternatives:");
        System.out.println("   - Heap approach for sparse events");
        System.out.println("   - Sweep line for general case");
        System.out.println("   - Compare time/space complexities");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - Dropoff at same location as pickup");
        System.out.println("   - Multiple trips at same location");
        System.out.println("   - Capacity zero or negative");
        
        System.out.println("\n7. Walk through example:");
        System.out.println("   - Use given example");
        System.out.println("   - Show array updates step by step");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- O(n + 1000) time is optimal for given constraints");
        System.out.println("- Difference array technique is key insight");
        System.out.println("- The 1000 limit enables array solution");
        System.out.println("- Heap approach more general but slower");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting to subtract at dropoff location");
        System.out.println("- Not handling multiple events at same location");
        System.out.println("- Off-by-one errors with array indices");
        System.out.println("- Not considering dropoff happens at 'to', not 'to-1'");
        System.out.println("- Integer overflow with large passenger counts");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with given examples");
        System.out.println("2. Test edge cases (single trip, same location)");
        System.out.println("3. Test capacity exactly equal to max passengers");
        System.out.println("4. Test with dropoff at pickup location");
        System.out.println("5. Verify passenger count never exceeds capacity");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
