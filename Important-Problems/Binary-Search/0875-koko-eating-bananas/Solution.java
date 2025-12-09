
## Solution.java

```java
/**
 * 875. Koko Eating Bananas
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Koko loves to eat bananas. There are n piles of bananas, the i-th pile has piles[i] bananas.
 * The guards will come back in h hours. Koko can decide her eating speed k (bananas per hour).
 * Each hour, she chooses some pile and eats k bananas from it. If the pile has less than k bananas,
 * she eats all of them and won't eat any more bananas that hour.
 * 
 * Return the minimum integer k such that she can eat all the bananas within h hours.
 * 
 * Key Insights:
 * 1. This is a binary search problem on the possible eating speeds k
 * 2. Search space: k from 1 to max(piles) (she never needs to eat faster than largest pile)
 * 3. For each k, calculate total hours needed = sum(ceil(pile / k) for all piles
 * 4. Find the smallest k such that total hours <= h
 * 5. Use ceiling division: hours = (pile + k - 1) / k
 * 
 * Approach (Binary Search on Answer):
 * 1. Initialize left = 1, right = max(piles)
 * 2. While left <= right, calculate mid
 * 3. Check if k = mid can finish bananas in h hours
 * 4. If yes, search for smaller k (right = mid - 1)
 * 5. If no, search for larger k (left = mid + 1)
 * 6. Return left when search ends
 * 
 * Time Complexity: O(n log m) where n = piles.length, m = max(piles)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Binary Search on Eating Speed - RECOMMENDED
     * O(n log m) time, O(1) space - Optimal solution
     */
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = getMaxPile(piles);
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canEatAll(piles, h, mid)) {
                // Can finish with speed mid, try smaller speed
                right = mid - 1;
            } else {
                // Cannot finish with speed mid, need larger speed
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    /**
     * Helper: Check if Koko can eat all bananas with given speed k within h hours
     */
    private boolean canEatAll(int[] piles, int h, int k) {
        long totalHours = 0; // Use long to prevent integer overflow
        
        for (int pile : piles) {
            // Ceiling division: (pile + k - 1) / k
            totalHours += (pile + k - 1) / k;
            
            // Early exit if already exceeds h
            if (totalHours > h) {
                return false;
            }
        }
        
        return totalHours <= h;
    }
    
    /**
     * Helper: Get maximum pile size
     */
    private int getMaxPile(int[] piles) {
        int max = 0;
        for (int pile : piles) {
            max = Math.max(max, pile);
        }
        return max;
    }
    
    /**
     * Approach 2: Binary Search with Optimized Range
     * Same complexity but with better initial bounds
     */
    public int minEatingSpeedOptimized(int[] piles, int h) {
        // Calculate theoretical bounds
        long totalBananas = 0;
        int maxPile = 0;
        for (int pile : piles) {
            totalBananas += pile;
            maxPile = Math.max(maxPile, pile);
        }
        
        // Lower bound: at least need to eat ceil(total/h) bananas per hour
        int left = Math.max(1, (int) Math.ceil((double) totalBananas / h));
        // Upper bound: never need more than max pile
        int right = maxPile;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canEatAll(piles, h, mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    /**
     * Approach 3: Binary Search with Double for Feasibility Check
     * Uses double division, might have floating point issues
     */
    public int minEatingSpeedDouble(int[] piles, int h) {
        int left = 1;
        int right = getMaxPile(piles);
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canEatAllDouble(piles, h, mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private boolean canEatAllDouble(int[] piles, int h, int k) {
        double totalHours = 0;
        
        for (int pile : piles) {
            totalHours += Math.ceil((double) pile / k);
            
            if (totalHours > h) {
                return false;
            }
        }
        
        return totalHours <= h;
    }
    
    /**
     * Approach 4: Linear Search (Not Recommended - for comparison only)
     * O(n * m) time - Too slow for large constraints
     */
    public int minEatingSpeedLinear(int[] piles, int h) {
        int maxPile = getMaxPile(piles);
        
        for (int k = 1; k <= maxPile; k++) {
            if (canEatAll(piles, h, k)) {
                return k;
            }
        }
        
        return maxPile;
    }
    
    /**
     * Approach 5: Binary Search with Separate Hours Calculation
     * More modular approach
     */
    public int minEatingSpeedModular(int[] piles, int h) {
        int left = 1;
        int right = getMaxPile(piles);
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (calculateTotalHours(piles, mid) <= h) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private long calculateTotalHours(int[] piles, int k) {
        long totalHours = 0;
        for (int pile : piles) {
            totalHours += (pile + k - 1) / k;
        }
        return totalHours;
    }
    
    /**
     * Approach 6: Binary Search with Early Optimization
     * Stops early when optimal k is found
     */
    public int minEatingSpeedEarlyOpt(int[] piles, int h) {
        int maxPile = getMaxPile(piles);
        
        // If hours equal piles, must eat at least max pile
        if (h == piles.length) {
            return maxPile;
        }
        
        int left = 1;
        int right = maxPile;
        int result = maxPile;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canEatAll(piles, h, mid)) {
                result = mid; // Found a valid speed, try to find smaller
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    private void visualizeBinarySearch(int[] piles, int h) {
        System.out.println("\nBinary Search Visualization:");
        System.out.println("Piles: " + Arrays.toString(piles));
        System.out.println("Hours available: " + h);
        System.out.println("Max pile: " + getMaxPile(piles));
        System.out.println("Search range: 1 to " + getMaxPile(piles));
        
        int left = 1;
        int right = getMaxPile(piles);
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Mid | Total Hours | Feasible? | Action");
        System.out.println("-----|------|-------|-----|-------------|-----------|--------");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long totalHours = calculateTotalHours(piles, mid);
            boolean feasible = totalHours <= h;
            String action;
            
            if (feasible) {
                action = "Feasible, search LEFT for smaller k";
                right = mid - 1;
            } else {
                action = "Not feasible, search RIGHT for larger k";
                left = mid + 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %11d | %9s | %s%n", 
                            step, left, right, mid, totalHours, feasible ? "YES" : "NO", action);
            step++;
        }
        
        System.out.println("\nFinal Result: k = " + left);
        System.out.println("Verification: Total hours with k=" + left + ": " + 
                         calculateTotalHours(piles, left));
    }
    
    /**
     * Helper method to show hours calculation for different speeds
     */
    private void showSpeedAnalysis(int[] piles, int h) {
        System.out.println("\nSpeed Analysis:");
        System.out.println("Piles: " + Arrays.toString(piles));
        System.out.println("Hours available: " + h);
        System.out.println("\nSpeed (k) | Hours Needed | Feasible?");
        System.out.println("----------|-------------|----------");
        
        int maxPile = getMaxPile(piles);
        for (int k = 1; k <= Math.min(maxPile, 20); k++) {
            long hoursNeeded = calculateTotalHours(piles, k);
            boolean feasible = hoursNeeded <= h;
            System.out.printf("%9d | %11d | %8s%n", k, hoursNeeded, feasible ? "YES" : "NO");
        }
        
        // Show some key speeds
        List<Integer> keySpeeds = new ArrayList<>();
        keySpeeds.add(1);
        keySpeeds.add(maxPile / 4);
        keySpeeds.add(maxPile / 2);
        keySpeeds.add(maxPile * 3 / 4);
        keySpeeds.add(maxPile);
        
        for (int k : keySpeeds) {
            if (k > 20) {
                long hoursNeeded = calculateTotalHours(piles, k);
                boolean feasible = hoursNeeded <= h;
                System.out.printf("%9d | %11d | %8s%n", k, hoursNeeded, feasible ? "YES" : "NO");
            }
        }
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] piles, int h) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Piles: " + Arrays.toString(piles));
        System.out.println("Hours: " + h);
        System.out.println("Array size: " + piles.length);
        System.out.println("Max pile: " + getMaxPile(piles));
        System.out.println("=================================");
        
        long startTime, endTime;
        int result;
        
        // Standard Binary Search
        startTime = System.nanoTime();
        result = minEatingSpeed(piles, h);
        endTime = System.nanoTime();
        System.out.printf("Standard Binary: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Optimized Range
        startTime = System.nanoTime();
        result = minEatingSpeedOptimized(piles, h);
        endTime = System.nanoTime();
        System.out.printf("Optimized Range: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Modular Approach
        startTime = System.nanoTime();
        result = minEatingSpeedModular(piles, h);
        endTime = System.nanoTime();
        System.out.printf("Modular:         %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Early Optimization
        startTime = System.nanoTime();
        result = minEatingSpeedEarlyOpt(piles, h);
        endTime = System.nanoTime();
        System.out.printf("Early Optimized: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Linear Search (only for small cases)
        if (getMaxPile(piles) <= 1000) {
            startTime = System.nanoTime();
            result = minEatingSpeedLinear(piles, h);
            endTime = System.nanoTime();
            System.out.printf("Linear Search:   %8d ns, Result: %d%n", (endTime - startTime), result);
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Koko Eating Bananas:");
        System.out.println("=============================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int[] piles1 = {3, 6, 7, 11};
        int h1 = 8;
        int result1 = solution.minEatingSpeed(piles1, h1);
        System.out.println("Result: " + result1 + " - " + (result1 == 4 ? "PASSED" : "FAILED"));
        solution.visualizeBinarySearch(piles1, h1);
        
        // Test case 2: Another example
        System.out.println("\nTest 2: Tight constraint");
        int[] piles2 = {30, 11, 23, 4, 20};
        int h2 = 5;
        int result2 = solution.minEatingSpeed(piles2, h2);
        System.out.println("Result: " + result2 + " - " + (result2 == 30 ? "PASSED" : "FAILED"));
        
        // Test case 3: More relaxed constraint
        System.out.println("\nTest 3: Relaxed constraint");
        int[] piles3 = {30, 11, 23, 4, 20};
        int h3 = 6;
        int result3 = solution.minEatingSpeed(piles3, h3);
        System.out.println("Result: " + result3 + " - " + (result3 == 23 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single pile
        System.out.println("\nTest 4: Single pile");
        int[] piles4 = {100};
        int h4 = 1;
        int result4 = solution.minEatingSpeed(piles4, h4);
        System.out.println("Result: " + result4 + " - " + (result4 == 100 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single pile with more time
        System.out.println("\nTest 5: Single pile with more time");
        int[] piles5 = {100};
        int h5 = 100;
        int result5 = solution.minEatingSpeed(piles5, h5);
        System.out.println("Result: " + result5 + " - " + (result5 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 6: All piles same size
        System.out.println("\nTest 6: All piles same size");
        int[] piles6 = {5, 5, 5, 5, 5};
        int h6 = 5;
        int result6 = solution.minEatingSpeed(piles6, h6);
        System.out.println("Result: " + result6 + " - " + (result6 == 5 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large numbers
        System.out.println("\nTest 7: Large numbers");
        int[] piles7 = {1000000000, 1000000000, 1000000000};
        int h7 = 3;
        int result7 = solution.minEatingSpeed(piles7, h7);
        System.out.println("Result: " + result7 + " - " + (result7 == 1000000000 ? "PASSED" : "FAILED"));
        
        // Test case 8: Many piles with small numbers
        System.out.println("\nTest 8: Many small piles");
        int[] piles8 = new int[10000];
        Arrays.fill(piles8, 1);
        int h8 = 10000;
        int result8 = solution.minEatingSpeed(piles8, h8);
        System.out.println("Result: " + result8 + " - " + (result8 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 9: Edge case - minimum constraints
        System.out.println("\nTest 9: Minimum constraints");
        int[] piles9 = {1};
        int h9 = 1;
        int result9 = solution.minEatingSpeed(piles9, h9);
        System.out.println("Result: " + result9 + " - " + (result9 == 1 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 10: Performance Comparison - Small Input");
        solution.compareApproaches(piles1, h1);
        
        System.out.println("\nTest 11: Performance Comparison - Medium Input");
        int[] piles11 = new int[1000];
        Random rand = new Random(42);
        for (int i = 0; i < piles11.length; i++) {
            piles11[i] = rand.nextInt(1000) + 1;
        }
        solution.compareApproaches(piles11, 1000);
        
        // Speed analysis visualization
        System.out.println("\nTest 12: Speed Analysis");
        solution.showSpeedAnalysis(piles1, h1);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("This is a classic 'binary search on answer' problem.");
        System.out.println("We're searching for the minimum k that satisfies a condition.");
        System.out.println("The condition: total hours to eat all bananas ≤ h");
        
        System.out.println("\nWhy binary search works:");
        System.out.println("1. If k works, all larger k will also work (monotonic property)");
        System.out.println("2. If k doesn't work, all smaller k won't work");
        System.out.println("3. This allows us to use binary search to find the minimum valid k");
        
        System.out.println("\nHours Calculation:");
        System.out.println("For a pile with p bananas and eating speed k:");
        System.out.println("  Hours needed = ceil(p / k)");
        System.out.println("We can compute this without floating point as: (p + k - 1) / k");
        
        System.out.println("\nExample: pile = 7, k = 3");
        System.out.println("  (7 + 3 - 1) / 3 = 9 / 3 = 3 hours");
        System.out.println("  Actually: hour1: 3, hour2: 3, hour3: 1 → total 3 hours");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Binary Search on Answer (RECOMMENDED):");
        System.out.println("   Time: O(n log m) - n piles, m = max(pile)");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Search space: k from 1 to max(piles)");
        System.out.println("     - For each k, calculate total hours needed");
        System.out.println("     - Use binary search to find minimum valid k");
        System.out.println("   Pros:");
        System.out.println("     - Optimal for large constraints");
        System.out.println("     - Simple and elegant");
        System.out.println("     - Handles worst-case scenarios well");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of 'binary search on answer'");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Binary Search with Optimized Range:");
        System.out.println("   Time: O(n log m) - Same as standard");
        System.out.println("   Space: O(1) - Same as standard");
        System.out.println("   How it works:");
        System.out.println("     - Better initial bounds: left = ceil(total/h), right = max(pile)");
        System.out.println("     - Reduces search space significantly");
        System.out.println("   Pros:");
        System.out.println("     - Fewer iterations in practice");
        System.out.println("     - More efficient for certain inputs");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: When you want optimal performance");
        
        System.out.println("\n3. Linear Search (NOT RECOMMENDED):");
        System.out.println("   Time: O(n * m) - Try every k from 1 to max(pile)");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Try k = 1, 2, 3, ..., max(pile)");
        System.out.println("     - Return first k that satisfies time constraint");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(n*m) is too slow for large inputs");
        System.out.println("     - Fails for max(pile) = 10^9");
        System.out.println("   Best for: Very small inputs, educational purposes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Minimum possible k: ceil(total_bananas / h)");
        System.out.println("2. Maximum possible k: max(piles)");
        System.out.println("3. Hours calculation: sum(ceil(pile / k)) for all piles");
        System.out.println("4. Ceiling division without float: (a + b - 1) / b");
        System.out.println("5. For n=10^4, m=10^9: binary search takes ~30 iterations");
        System.out.println("6. Linear search would take 10^9 iterations (impossible)");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Recognize this as a 'binary search on answer' problem");
        System.out.println("2. Explain the search space and monotonic property");
        System.out.println("3. Implement the feasibility function carefully");
        System.out.println("4. Use integer ceiling division to avoid floating point");
        System.out.println("5. Discuss the time complexity O(n log m)");
        System.out.println("6. Handle edge cases: single pile, large numbers");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
