
## Solution.java

```java
/**
 * 1011. Capacity To Ship Packages Within D Days
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * A conveyor belt has packages that must be shipped within 'days' days.
 * The i-th package has weight weights[i]. Each day, we load the ship with packages
 * in the given order. We may not load more weight than the ship's capacity.
 * 
 * Return the least weight capacity of the ship that will result in all packages
 * being shipped within 'days' days.
 * 
 * Key Insights:
 * 1. This is a binary search problem on the possible ship capacities
 * 2. Search space: capacity from max(weights) to sum(weights)
 * 3. For each capacity, calculate days needed using greedy loading
 * 4. Find the smallest capacity such that days needed <= given days
 * 5. Packages must be loaded in order (important constraint)
 * 
 * Approach (Binary Search on Answer):
 * 1. Initialize left = max(weights), right = sum(weights)
 * 2. While left <= right, calculate mid
 * 3. Check if capacity = mid can ship all packages in <= days
 * 4. If yes, search for smaller capacity (right = mid - 1)
 * 5. If no, search for larger capacity (left = mid + 1)
 * 6. Return left when search ends
 * 
 * Time Complexity: O(n log s) where n = weights.length, s = sum(weights)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Binary Search on Capacity - RECOMMENDED
     * O(n log s) time, O(1) space - Optimal solution
     */
    public int shipWithinDays(int[] weights, int days) {
        // Calculate search bounds
        int maxWeight = 0;
        int totalWeight = 0;
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        int left = maxWeight;  // Minimum capacity must be at least the heaviest package
        int right = totalWeight; // Maximum capacity needed is total weight (ship all at once)
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canShipWithinDays(weights, days, mid)) {
                // Can ship with capacity mid, try smaller capacity
                right = mid - 1;
            } else {
                // Cannot ship with capacity mid, need larger capacity
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    /**
     * Helper: Check if we can ship all packages within given days using given capacity
     */
    private boolean canShipWithinDays(int[] weights, int days, int capacity) {
        int currentLoad = 0;
        int daysNeeded = 1; // Start with first day
        
        for (int weight : weights) {
            // If adding this package exceeds capacity, start new day
            if (currentLoad + weight > capacity) {
                daysNeeded++;
                currentLoad = 0;
                
                // If we've exceeded the allowed days, return false
                if (daysNeeded > days) {
                    return false;
                }
            }
            
            currentLoad += weight;
        }
        
        return daysNeeded <= days;
    }
    
    /**
     * Approach 2: Binary Search with Optimized Bounds
     * Same complexity but with better initial bounds
     */
    public int shipWithinDaysOptimized(int[] weights, int days) {
        int maxWeight = 0;
        int totalWeight = 0;
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        // If we have only 1 day, we need capacity = totalWeight
        if (days == 1) {
            return totalWeight;
        }
        
        // If we have as many days as packages, we need capacity = maxWeight
        if (days == weights.length) {
            return maxWeight;
        }
        
        // Theoretical lower bound: ceil(totalWeight / days)
        int left = Math.max(maxWeight, (totalWeight + days - 1) / days);
        int right = totalWeight;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canShipWithinDays(weights, days, mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    /**
     * Approach 3: Binary Search with Separate Days Calculation
     * More modular approach
     */
    public int shipWithinDaysModular(int[] weights, int days) {
        int maxWeight = 0;
        int totalWeight = 0;
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        int left = maxWeight;
        int right = totalWeight;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (calculateDaysNeeded(weights, mid) <= days) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private int calculateDaysNeeded(int[] weights, int capacity) {
        int days = 1;
        int currentLoad = 0;
        
        for (int weight : weights) {
            if (currentLoad + weight > capacity) {
                days++;
                currentLoad = 0;
            }
            currentLoad += weight;
        }
        
        return days;
    }
    
    /**
     * Approach 4: Binary Search with Early Exit
     * Stops early when optimal capacity is found
     */
    public int shipWithinDaysEarlyExit(int[] weights, int days) {
        int maxWeight = 0;
        int totalWeight = 0;
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        int left = maxWeight;
        int right = totalWeight;
        int result = totalWeight;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canShipWithinDays(weights, days, mid)) {
                result = mid; // Found a valid capacity, try to find smaller
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Linear Search (Not Recommended - for comparison only)
     * O(n * s) time - Too slow for large constraints
     */
    public int shipWithinDaysLinear(int[] weights, int days) {
        int maxWeight = 0;
        int totalWeight = 0;
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        for (int capacity = maxWeight; capacity <= totalWeight; capacity++) {
            if (canShipWithinDays(weights, days, capacity)) {
                return capacity;
            }
        }
        
        return totalWeight;
    }
    
    /**
     * Approach 6: Binary Search with Detailed Feasibility Check
     * Provides more debugging information
     */
    public int shipWithinDaysDetailed(int[] weights, int days) {
        int maxWeight = 0;
        int totalWeight = 0;
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        int left = maxWeight;
        int right = totalWeight;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            boolean canShip = canShipWithDetails(weights, days, mid);
            
            if (canShip) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private boolean canShipWithDetails(int[] weights, int days, int capacity) {
        int currentLoad = 0;
        int daysUsed = 1;
        List<List<Integer>> dailyLoads = new ArrayList<>();
        List<Integer> currentDay = new ArrayList<>();
        
        for (int weight : weights) {
            if (currentLoad + weight > capacity) {
                dailyLoads.add(new ArrayList<>(currentDay));
                daysUsed++;
                currentLoad = 0;
                currentDay.clear();
                
                if (daysUsed > days) {
                    return false;
                }
            }
            
            currentLoad += weight;
            currentDay.add(weight);
        }
        
        // Add the last day
        if (!currentDay.isEmpty()) {
            dailyLoads.add(new ArrayList<>(currentDay));
        }
        
        return daysUsed <= days;
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    private void visualizeBinarySearch(int[] weights, int days) {
        System.out.println("\nBinary Search Visualization:");
        System.out.println("Weights: " + Arrays.toString(weights));
        System.out.println("Days available: " + days);
        
        int maxWeight = 0;
        int totalWeight = 0;
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        System.out.println("Max weight: " + maxWeight);
        System.out.println("Total weight: " + totalWeight);
        System.out.println("Search range: " + maxWeight + " to " + totalWeight);
        
        int left = maxWeight;
        int right = totalWeight;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Mid | Days Needed | Feasible? | Action");
        System.out.println("-----|------|-------|-----|-------------|-----------|--------");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int daysNeeded = calculateDaysNeeded(weights, mid);
            boolean feasible = daysNeeded <= days;
            String action;
            
            if (feasible) {
                action = "Feasible, search LEFT for smaller capacity";
                right = mid - 1;
            } else {
                action = "Not feasible, search RIGHT for larger capacity";
                left = mid + 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %11d | %9s | %s%n", 
                            step, left, right, mid, daysNeeded, feasible ? "YES" : "NO", action);
            step++;
        }
        
        System.out.println("\nFinal Result: Capacity = " + left);
        System.out.println("Verification: Days needed with capacity=" + left + ": " + 
                         calculateDaysNeeded(weights, left));
    }
    
    /**
     * Helper method to show capacity analysis for different values
     */
    private void showCapacityAnalysis(int[] weights, int days) {
        System.out.println("\nCapacity Analysis:");
        System.out.println("Weights: " + Arrays.toString(weights));
        System.out.println("Days available: " + days);
        System.out.println("\nCapacity | Days Needed | Feasible? | Daily Loads");
        System.out.println("---------|-------------|-----------|------------");
        
        int maxWeight = 0;
        int totalWeight = 0;
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        // Show key capacities around the solution
        List<Integer> keyCapacities = new ArrayList<>();
        
        // Add capacities from maxWeight to slightly above expected solution
        for (int cap = maxWeight; cap <= Math.min(totalWeight, maxWeight + 10); cap++) {
            keyCapacities.add(cap);
        }
        
        // Add some capacities in the middle
        int midPoint = (maxWeight + totalWeight) / 2;
        for (int cap = Math.max(midPoint - 5, maxWeight + 11); 
             cap <= Math.min(midPoint + 5, totalWeight); cap++) {
            if (!keyCapacities.contains(cap)) {
                keyCapacities.add(cap);
            }
        }
        
        // Add capacities near totalWeight
        for (int cap = Math.max(totalWeight - 5, midPoint + 6); cap <= totalWeight; cap++) {
            if (!keyCapacities.contains(cap)) {
                keyCapacities.add(cap);
            }
        }
        
        Collections.sort(keyCapacities);
        
        for (int capacity : keyCapacities) {
            int daysNeeded = calculateDaysNeeded(weights, capacity);
            boolean feasible = daysNeeded <= days;
            String dailyLoads = getDailyLoadsString(weights, capacity);
            System.out.printf("%8d | %11d | %9s | %s%n", 
                            capacity, daysNeeded, feasible ? "YES" : "NO", dailyLoads);
        }
    }
    
    /**
     * Helper method to get string representation of daily loads
     */
    private String getDailyLoadsString(int[] weights, int capacity) {
        List<List<Integer>> dailyLoads = new ArrayList<>();
        int currentLoad = 0;
        List<Integer> currentDay = new ArrayList<>();
        
        for (int weight : weights) {
            if (currentLoad + weight > capacity) {
                dailyLoads.add(new ArrayList<>(currentDay));
                currentLoad = 0;
                currentDay.clear();
            }
            
            currentLoad += weight;
            currentDay.add(weight);
        }
        
        if (!currentDay.isEmpty()) {
            dailyLoads.add(new ArrayList<>(currentDay));
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dailyLoads.size(); i++) {
            if (i > 0) sb.append("; ");
            sb.append("Day ").append(i + 1).append(": ");
            int sum = 0;
            for (int w : dailyLoads.get(i)) {
                sb.append(w).append("+");
                sum += w;
            }
            sb.deleteCharAt(sb.length() - 1); // Remove last '+'
            sb.append("=").append(sum);
        }
        
        return sb.toString();
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] weights, int days) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Weights: " + Arrays.toString(weights));
        System.out.println("Days: " + days);
        System.out.println("Array size: " + weights.length);
        
        int maxWeight = 0;
        int totalWeight = 0;
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        System.out.println("Max weight: " + maxWeight + ", Total weight: " + totalWeight);
        System.out.println("=================================");
        
        long startTime, endTime;
        int result;
        
        // Standard Binary Search
        startTime = System.nanoTime();
        result = shipWithinDays(weights, days);
        endTime = System.nanoTime();
        System.out.printf("Standard Binary: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Optimized Bounds
        startTime = System.nanoTime();
        result = shipWithinDaysOptimized(weights, days);
        endTime = System.nanoTime();
        System.out.printf("Optimized Bounds:%8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Modular Approach
        startTime = System.nanoTime();
        result = shipWithinDaysModular(weights, days);
        endTime = System.nanoTime();
        System.out.printf("Modular:         %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Early Exit
        startTime = System.nanoTime();
        result = shipWithinDaysEarlyExit(weights, days);
        endTime = System.nanoTime();
        System.out.printf("Early Exit:      %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Linear Search (only for small cases)
        if (totalWeight - maxWeight <= 1000) {
            startTime = System.nanoTime();
            result = shipWithinDaysLinear(weights, days);
            endTime = System.nanoTime();
            System.out.printf("Linear Search:   %8d ns, Result: %d%n", (endTime - startTime), result);
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Capacity To Ship Packages Within D Days:");
        System.out.println("================================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int[] weights1 = {1,2,3,4,5,6,7,8,9,10};
        int days1 = 5;
        int result1 = solution.shipWithinDays(weights1, days1);
        System.out.println("Result: " + result1 + " - " + (result1 == 15 ? "PASSED" : "FAILED"));
        solution.visualizeBinarySearch(weights1, days1);
        
        // Test case 2: Another example
        System.out.println("\nTest 2: Tight constraint");
        int[] weights2 = {3,2,2,4,1,4};
        int days2 = 3;
        int result2 = solution.shipWithinDays(weights2, days2);
        System.out.println("Result: " + result2 + " - " + (result2 == 6 ? "PASSED" : "FAILED"));
        
        // Test case 3: More relaxed constraint
        System.out.println("\nTest 3: Relaxed constraint");
        int[] weights3 = {1,2,3,1,1};
        int days3 = 4;
        int result3 = solution.shipWithinDays(weights3, days3);
        System.out.println("Result: " + result3 + " - " + (result3 == 3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single package
        System.out.println("\nTest 4: Single package");
        int[] weights4 = {100};
        int days4 = 1;
        int result4 = solution.shipWithinDays(weights4, days4);
        System.out.println("Result: " + result4 + " - " + (result4 == 100 ? "PASSED" : "FAILED"));
        
        // Test case 5: All packages same weight
        System.out.println("\nTest 5: All packages same weight");
        int[] weights5 = {5, 5, 5, 5, 5};
        int days5 = 5;
        int result5 = solution.shipWithinDays(weights5, days5);
        System.out.println("Result: " + result5 + " - " + (result5 == 5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Minimum days (1 day)
        System.out.println("\nTest 6: Minimum days (1 day)");
        int[] weights6 = {1, 2, 3, 4, 5};
        int days6 = 1;
        int result6 = solution.shipWithinDays(weights6, days6);
        System.out.println("Result: " + result6 + " - " + (result6 == 15 ? "PASSED" : "FAILED"));
        
        // Test case 7: Maximum days (equal to packages)
        System.out.println("\nTest 7: Maximum days (equal to packages)");
        int[] weights7 = {1, 2, 3, 4, 5};
        int days7 = 5;
        int result7 = solution.shipWithinDays(weights7, days7);
        System.out.println("Result: " + result7 + " - " + (result7 == 5 ? "PASSED" : "FAILED"));
        
        // Test case 8: Large weights
        System.out.println("\nTest 8: Large weights");
        int[] weights8 = {500, 500, 500, 500, 500};
        int days8 = 5;
        int result8 = solution.shipWithinDays(weights8, days8);
        System.out.println("Result: " + result8 + " - " + (result8 == 500 ? "PASSED" : "FAILED"));
        
        // Test case 9: Many packages
        System.out.println("\nTest 9: Many packages");
        int[] weights9 = new int[1000];
        Arrays.fill(weights9, 1);
        int days9 = 10;
        int result9 = solution.shipWithinDays(weights9, days9);
        System.out.println("Result: " + result9 + " - " + (result9 == 100 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 10: Performance Comparison - Small Input");
        solution.compareApproaches(weights1, days1);
        
        System.out.println("\nTest 11: Performance Comparison - Medium Input");
        int[] weights11 = new int[1000];
        Random rand = new Random(42);
        for (int i = 0; i < weights11.length; i++) {
            weights11[i] = rand.nextInt(100) + 1;
        }
        solution.compareApproaches(weights11, 10);
        
        // Capacity analysis visualization
        System.out.println("\nTest 12: Capacity Analysis");
        solution.showCapacityAnalysis(weights1, days1);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("This is a classic 'binary search on answer' problem.");
        System.out.println("We're searching for the minimum capacity that satisfies a condition.");
        System.out.println("The condition: days needed to ship all packages ≤ given days");
        
        System.out.println("\nWhy binary search works:");
        System.out.println("1. If capacity C works, all larger capacities will also work");
        System.out.println("2. If capacity C doesn't work, all smaller capacities won't work");
        System.out.println("3. This monotonic property allows binary search");
        
        System.out.println("\nSearch Space Bounds:");
        System.out.println("Lower bound: max(weights) - ship must handle heaviest package");
        System.out.println("Upper bound: sum(weights) - ship could carry all packages at once");
        
        System.out.println("\nFeasibility Check:");
        System.out.println("For a given capacity, we simulate the shipping process:");
        System.out.println("1. Start with day 1, current load = 0");
        System.out.println("2. For each package, try to add it to current day");
        System.out.println("3. If adding exceeds capacity, start new day");
        System.out.println("4. Count total days needed");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Binary Search on Answer (RECOMMENDED):");
        System.out.println("   Time: O(n log s) - n packages, s = sum(weights)");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Search space: capacity from max(weights) to sum(weights)");
        System.out.println("     - For each capacity, calculate days needed using greedy approach");
        System.out.println("     - Use binary search to find minimum valid capacity");
        System.out.println("   Pros:");
        System.out.println("     - Optimal for large constraints");
        System.out.println("     - Simple and efficient");
        System.out.println("     - Handles worst-case scenarios well");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of 'binary search on answer'");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Binary Search with Optimized Bounds:");
        System.out.println("   Time: O(n log s) - Same as standard");
        System.out.println("   Space: O(1) - Same as standard");
        System.out.println("   How it works:");
        System.out.println("     - Better initial bounds considering days constraint");
        System.out.println("     - left = max(maxWeight, ceil(totalWeight/days))");
        System.out.println("     - Reduces search space significantly");
        System.out.println("   Pros:");
        System.out.println("     - Fewer iterations in practice");
        System.out.println("     - More efficient for certain inputs");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: When you want optimal performance");
        
        System.out.println("\n3. Linear Search (NOT RECOMMENDED):");
        System.out.println("   Time: O(n * s) - Try every capacity from max to sum");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Try capacity = max, max+1, ..., sum");
        System.out.println("     - Return first capacity that satisfies days constraint");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(n*s) is too slow for large inputs");
        System.out.println("     - Fails for sum(weights) = 25,000,000 (5*10^4 * 500)");
        System.out.println("   Best for: Very small inputs, educational purposes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Minimum possible capacity: max(weights)");
        System.out.println("2. Maximum possible capacity: sum(weights)");
        System.out.println("3. Theoretical lower bound: ceil(totalWeight / days)");
        System.out.println("4. For n=5*10^4, s=25,000,000: binary search takes ~25 iterations");
        System.out.println("5. Linear search would take 25,000,000 iterations (impossible)");
        System.out.println("6. The greedy loading algorithm is optimal for this problem");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Recognize this as a 'binary search on answer' problem");
        System.out.println("2. Explain the search space bounds and monotonic property");
        System.out.println("3. Implement the feasibility function with greedy loading");
        System.out.println("4. Mention the packages must be loaded in order (important!)");
        System.out.println("5. Discuss the time complexity O(n log s)");
        System.out.println("6. Handle edge cases: single package, minimum/maximum days");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
