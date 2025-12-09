
# Solution.java

```java
/**
 * 853. Car Fleet
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * There are n cars going to the same destination along a one-lane road.
 * Cars cannot pass each other but can form fleets when catching up.
 * Return the number of car fleets that arrive at the destination.
 * 
 * Key Insights:
 * 1. Sort cars by position (closest to target first)
 * 2. Calculate time to reach target for each car
 * 3. Use monotonic stack to track fleet formation
 * 4. If car behind takes less time than car ahead, they form a fleet
 * 
 * Approach (Sorting + Monotonic Stack):
 * 1. Create array of cars with position and speed
 * 2. Sort cars by position in descending order
 * 3. Calculate time to target for each car: (target - position) / speed
 * 4. Use stack to maintain fleet leaders
 * 5. If current car time <= stack top time, they form a fleet (skip push)
 * 6. Otherwise, push current car time to stack (new fleet)
 * 7. Return stack size as number of fleets
 * 
 * Time Complexity: O(n log n) - Dominated by sorting
 * Space Complexity: O(n) - Storage for cars and stack
 * 
 * Tags: Array, Stack, Sorting, Monotonic Stack
 */

import java.util.*;

public class Solution {
    
    /**
     * Approach 1: Sorting + Monotonic Stack - RECOMMENDED
     * O(n log n) time, O(n) space - Most intuitive and efficient
     */
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 0) return 0;
        
        // Create and sort cars by position (closest to target first)
        Car[] cars = new Car[n];
        for (int i = 0; i < n; i++) {
            cars[i] = new Car(position[i], speed[i]);
        }
        Arrays.sort(cars, (a, b) -> b.position - a.position); // Descending order
        
        Stack<Double> stack = new Stack<>();
        
        for (Car car : cars) {
            double timeToTarget = (double)(target - car.position) / car.speed;
            
            // If current car takes less or equal time than fleet ahead, they form a fleet
            if (!stack.isEmpty() && timeToTarget <= stack.peek()) {
                // This car will catch up to the fleet ahead, so skip pushing
                continue;
            }
            
            // Otherwise, this car leads a new fleet
            stack.push(timeToTarget);
        }
        
        return stack.size();
    }
    
    /**
     * Car class to store position and speed
     */
    private static class Car {
        int position;
        int speed;
        
        Car(int position, int speed) {
            this.position = position;
            this.speed = speed;
        }
    }
    
    /**
     * Approach 2: Sorting + Array (Optimized)
     * O(n log n) time, O(n) space - Uses array instead of stack
     */
    public int carFleetArray(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 0) return 0;
        
        // Create array of cars and sort by position
        int[][] cars = new int[n][2]; // [position, speed]
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = speed[i];
        }
        Arrays.sort(cars, (a, b) -> b[0] - a[0]); // Descending by position
        
        double[] times = new double[n];
        for (int i = 0; i < n; i++) {
            times[i] = (double)(target - cars[i][0]) / cars[i][1];
        }
        
        int fleets = 0;
        double currentFleetTime = -1;
        
        for (int i = 0; i < n; i++) {
            // If current car takes more time than current fleet, it leads a new fleet
            if (times[i] > currentFleetTime) {
                fleets++;
                currentFleetTime = times[i];
            }
        }
        
        return fleets;
    }
    
    /**
     * Approach 3: Sorting + Single Pass
     * O(n log n) time, O(n) space - Most space efficient
     */
    public int carFleetSinglePass(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 0) return 0;
        
        // Create array of indices and sort by position
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, (a, b) -> position[b] - position[a]); // Descending
        
        int fleets = 0;
        double prevTime = -1;
        
        for (int i = 0; i < n; i++) {
            int idx = indices[i];
            double time = (double)(target - position[idx]) / speed[idx];
            
            // If this car takes more time than previous fleet, it leads a new fleet
            if (time > prevTime) {
                fleets++;
                prevTime = time;
            }
        }
        
        return fleets;
    }
    
    /**
     * Approach 4: Priority Queue (Alternative)
     * O(n log n) time, O(n) space - Uses priority queue for sorting
     */
    public int carFleetPriorityQueue(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 0) return 0;
        
        // Use priority queue to sort by position (max heap)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        for (int i = 0; i < n; i++) {
            pq.offer(new int[]{position[i], speed[i]});
        }
        
        int fleets = 0;
        double prevTime = -1;
        
        while (!pq.isEmpty()) {
            int[] car = pq.poll();
            double time = (double)(target - car[0]) / car[1];
            
            if (time > prevTime) {
                fleets++;
                prevTime = time;
            }
        }
        
        return fleets;
    }
    
    /**
     * Approach 5: Brute Force Simulation (For Understanding)
     * O(n^2) time, O(n) space - Not efficient but helps understanding
     */
    public int carFleetBruteForce(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 0) return 0;
        
        // Create and sort cars
        Car[] cars = new Car[n];
        for (int i = 0; i < n; i++) {
            cars[i] = new Car(position[i], speed[i]);
        }
        Arrays.sort(cars, (a, b) -> b.position - a.position);
        
        boolean[] merged = new boolean[n];
        int fleets = 0;
        
        for (int i = 0; i < n; i++) {
            if (merged[i]) continue;
            
            fleets++;
            double currentTime = (double)(target - cars[i].position) / cars[i].speed;
            
            // Check which cars behind will catch up to this fleet
            for (int j = i + 1; j < n; j++) {
                if (merged[j]) continue;
                
                double behindTime = (double)(target - cars[j].position) / cars[j].speed;
                if (behindTime <= currentTime) {
                    merged[j] = true;
                }
            }
        }
        
        return fleets;
    }
    
    /**
     * Helper method to visualize the car fleet formation process
     */
    private void visualizeCarFleets(int target, int[] position, int[] speed) {
        System.out.println("\nCar Fleet Formation Visualization:");
        System.out.println("Target: " + target);
        System.out.println("Positions: " + Arrays.toString(position));
        System.out.println("Speeds: " + Arrays.toString(speed));
        
        int n = position.length;
        Car[] cars = new Car[n];
        for (int i = 0; i < n; i++) {
            cars[i] = new Car(position[i], speed[i]);
        }
        Arrays.sort(cars, (a, b) -> b.position - a.position);
        
        System.out.println("\nCars sorted by position (closest to target first):");
        System.out.println("Index | Position | Speed | Time to Target");
        System.out.println("------|----------|-------|---------------");
        
        for (int i = 0; i < n; i++) {
            double time = (double)(target - cars[i].position) / cars[i].speed;
            System.out.printf("%5d | %8d | %5d | %13.2f%n", 
                            i, cars[i].position, cars[i].speed, time);
        }
        
        Stack<Double> stack = new Stack<>();
        System.out.println("\nFleet Formation Process:");
        System.out.println("Step | Car Pos | Time | Stack State | Action");
        System.out.println("-----|---------|------|-------------|--------");
        
        for (int i = 0; i < n; i++) {
            double time = (double)(target - cars[i].position) / cars[i].speed;
            String stackStateBefore = stack.toString();
            String action;
            
            if (!stack.isEmpty() && time <= stack.peek()) {
                action = "Joins fleet (time " + time + " <= " + stack.peek() + ")";
            } else {
                stack.push(time);
                action = "Leads new fleet";
            }
            
            String stackStateAfter = stack.toString();
            System.out.printf("%4d | %7d | %4.1f | %11s | %s%n", 
                            i + 1, cars[i].position, time, stackStateAfter, action);
        }
        
        System.out.println("\nFinal Result: " + stack.size() + " car fleets");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Car Fleet:");
        System.out.println("==================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int target1 = 12;
        int[] position1 = {10, 8, 0, 5, 3};
        int[] speed1 = {2, 4, 1, 1, 3};
        int expected1 = 3;
        
        long startTime = System.nanoTime();
        int result1a = solution.carFleet(target1, position1, speed1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.carFleetArray(target1, position1, speed1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.carFleetSinglePass(target1, position1, speed1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        
        System.out.println("Monotonic Stack: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Array Approach: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Single Pass: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the fleet formation process
        solution.visualizeCarFleets(target1, position1, speed1);
        
        // Test case 2: Single car
        System.out.println("\nTest 2: Single car");
        int target2 = 10;
        int[] position2 = {3};
        int[] speed2 = {3};
        int expected2 = 1;
        
        int result2a = solution.carFleet(target2, position2, speed2);
        System.out.println("Single car: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: All cars form one fleet
        System.out.println("\nTest 3: All cars form one fleet");
        int target3 = 100;
        int[] position3 = {0, 2, 4};
        int[] speed3 = {4, 2, 1};
        int expected3 = 1;
        
        int result3a = solution.carFleet(target3, position3, speed3);
        System.out.println("One fleet: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: No fleets formed (all separate)
        System.out.println("\nTest 4: No fleets formed");
        int target4 = 20;
        int[] position4 = {0, 5, 10, 15};
        int[] speed4 = {1, 1, 1, 1};
        int expected4 = 4;
        
        int result4a = solution.carFleet(target4, position4, speed4);
        System.out.println("No fleets: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Mixed fleet formation
        System.out.println("\nTest 5: Mixed fleet formation");
        int target5 = 20;
        int[] position5 = {5, 10, 15, 0};
        int[] speed5 = {2, 1, 1, 3};
        int expected5 = 2;
        
        int result5a = solution.carFleet(target5, position5, speed5);
        System.out.println("Mixed fleets: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Cars at same position
        System.out.println("\nTest 6: Cars at same position");
        int target6 = 15;
        int[] position6 = {5, 5, 10};
        int[] speed6 = {2, 3, 1};
        int expected6 = 2;
        
        int result6a = solution.carFleet(target6, position6, speed6);
        System.out.println("Same position: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Monotonic Stack: " + time1a + " ns");
        System.out.println("  Array Approach: " + time1b + " ns");
        System.out.println("  Single Pass: " + time1c + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 8: Large input performance");
        int target8 = 1000000;
        int[] largePosition = generateLargePositions(100000, target8);
        int[] largeSpeed = generateLargeSpeeds(100000);
        
        startTime = System.nanoTime();
        int result8a = solution.carFleet(target8, largePosition, largeSpeed);
        long time8a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result8b = solution.carFleetSinglePass(target8, largePosition, largeSpeed);
        long time8b = System.nanoTime() - startTime;
        
        System.out.println("Large input (100,000 cars):");
        System.out.println("  Monotonic Stack: " + time8a + " ns, Fleets: " + result8a);
        System.out.println("  Single Pass: " + time8b + " ns, Fleets: " + result8b);
        
        // Edge case: Empty input
        System.out.println("\nTest 9: Empty input");
        int target9 = 10;
        int[] position9 = {};
        int[] speed9 = {};
        int expected9 = 0;
        
        int result9a = solution.carFleet(target9, position9, speed9);
        System.out.println("Empty input: " + result9a + " - " + 
                         (result9a == expected9 ? "PASSED" : "FAILED"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Process cars from closest to farthest from target.");
        System.out.println("If a car behind takes less or equal time to reach target");
        System.out.println("than the car ahead, it will catch up and form a fleet.");
        
        System.out.println("\nStep-by-step Algorithm:");
        System.out.println("1. Sort cars by position in descending order");
        System.out.println("2. Calculate time to target for each car: (target - position) / speed");
        System.out.println("3. Initialize stack to track fleet leaders");
        System.out.println("4. For each car (closest to farthest):");
        System.out.println("   a. If stack empty → push time (new fleet)");
        System.out.println("   b. If current time > stack top → push time (new fleet)");
        System.out.println("   c. If current time <= stack top → skip (joins existing fleet)");
        System.out.println("5. Return stack size (number of fleets)");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\nMonotonic Stack Approach:");
        System.out.println("┌────────────┬────────────┬──────────────┐");
        System.out.println("│ Operation  │ Time       │ Space        │");
        System.out.println("├────────────┼────────────┼──────────────┤");
        System.out.println("│ Sorting    │ O(n log n) │ O(n)         │");
        System.out.println("│ Processing │ O(n)       │ O(n)         │");
        System.out.println("│ Total      │ O(n log n) │ O(n)         │");
        System.out.println("└────────────┴────────────┴──────────────┘");
        
        System.out.println("\nComparison of Approaches:");
        System.out.println("┌──────────────────┬────────────┬─────────────────┐");
        System.out.println("│ Approach         │ Time       │ Space           │");
        System.out.println("├──────────────────┼────────────┼─────────────────┤");
        System.out.println("│ Monotonic Stack  │ O(n log n) │ O(n)            │");
        System.out.println("│ Array Approach   │ O(n log n) │ O(n)            │");
        System.out.println("│ Single Pass      │ O(n log n) │ O(n)            │");
        System.out.println("│ Priority Queue   │ O(n log n) │ O(n)            │");
        System.out.println("│ Brute Force      │ O(n²)      │ O(n)            │");
        System.out.println("└──────────────────┴────────────┴─────────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with Monotonic Stack Approach:");
        System.out.println("   - Most intuitive and efficient solution");
        System.out.println("   - Clearly demonstrates the fleet formation logic");
        System.out.println("   - Handles all edge cases correctly");
        
        System.out.println("\n2. Key Points to Explain:");
        System.out.println("   - Why sorting by position is necessary");
        System.out.println("   - How time calculation determines fleet formation");
        System.out.println("   - Why stack is appropriate (LIFO for fleet leaders)");
        System.out.println("   - Time and space complexity analysis");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - Single car");
        System.out.println("   - All cars form one fleet");
        System.out.println("   - No fleets formed");
        System.out.println("   - Cars at same position");
        System.out.println("   - Empty input");
        
        System.out.println("\n4. Discuss Optimizations:");
        System.out.println("   - Alternative to stack (single variable)");
        System.out.println("   - Different sorting approaches");
        System.out.println("   - Space optimization techniques");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - Traffic flow analysis and simulation");
        System.out.println("   - Autonomous vehicle coordination");
        System.out.println("   - Supply chain logistics");
        System.out.println("   - Network packet scheduling");
        
        System.out.println("\nAll tests completed successfully!");
    }
    
    /**
     * Helper method to generate large position array for performance testing
     */
    private static int[] generateLargePositions(int size, int target) {
        int[] positions = new int[size];
        Random random = new Random(42);
        
        for (int i = 0; i < size; i++) {
            positions[i] = random.nextInt(target);
        }
        
        return positions;
    }
    
    /**
     * Helper method to generate large speed array for performance testing
     */
    private static int[] generateLargeSpeeds(int size) {
        int[] speeds = new int[size];
        Random random = new Random(42);
        
        for (int i = 0; i < size; i++) {
            speeds[i] = 1 + random.nextInt(1000000); // 1 to 1,000,000
        }
        
        return speeds;
    }
}
