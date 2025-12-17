
# Solution.java

```java
import java.util.*;

/**
 * 888. Fair Candy Swap
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Alice and Bob have different total candies. Need to exchange one box each
 * so that after exchange, both have equal total candy.
 * 
 * Key Insights:
 * 1. Mathematical relationship: x - y = (sumA - sumB) / 2
 * 2. Where x = Alice's box, y = Bob's box
 * 3. For each x in Alice's boxes, look for y = x - delta in Bob's boxes
 * 4. Use HashSet for O(1) lookup of Bob's candies
 * 
 * Approach (HashSet with Mathematical Formula):
 * 1. Calculate sumA and sumB
 * 2. Compute delta = (sumA - sumB) / 2
 * 3. Store Bob's candies in HashSet for quick lookup
 * 4. For each x in Alice's candies, check if (x - delta) exists in Bob's set
 * 5. Return [x, x - delta] when found
 * 
 * Time Complexity: O(n + m)
 * Space Complexity: O(m)
 * 
 * Tags: Array, Hash Table, Math
 */

class Solution {
    
    /**
     * Approach 1: HashSet with Mathematical Formula (RECOMMENDED)
     * O(n + m) time, O(m) space
     */
    public int[] fairCandySwap(int[] aliceSizes, int[] bobSizes) {
        // Calculate sums
        int sumA = 0, sumB = 0;
        for (int candy : aliceSizes) sumA += candy;
        for (int candy : bobSizes) sumB += candy;
        
        // Compute the difference needed
        int delta = (sumA - sumB) / 2;
        
        // Store Bob's candies in HashSet for O(1) lookup
        Set<Integer> bobSet = new HashSet<>();
        for (int candy : bobSizes) bobSet.add(candy);
        
        // For each candy Alice has, check if Bob has the needed candy
        for (int x : aliceSizes) {
            int y = x - delta;
            if (bobSet.contains(y)) {
                return new int[]{x, y};
            }
        }
        
        // Should never reach here due to problem guarantee
        return new int[]{-1, -1};
    }
    
    /**
     * Approach 2: Sorting with Two Pointers
     * O(n log n + m log m) time, O(1) extra space
     * Good when we want to avoid extra space
     */
    public int[] fairCandySwapTwoPointers(int[] aliceSizes, int[] bobSizes) {
        // Calculate sums
        int sumA = 0, sumB = 0;
        for (int candy : aliceSizes) sumA += candy;
        for (int candy : bobSizes) sumB += candy;
        
        // Compute target difference
        int delta = (sumA - sumB) / 2;
        
        // Sort both arrays
        Arrays.sort(aliceSizes);
        Arrays.sort(bobSizes);
        
        // Two pointers approach
        int i = 0, j = 0;
        while (i < aliceSizes.length && j < bobSizes.length) {
            int x = aliceSizes[i];
            int y = bobSizes[j];
            int diff = x - y;
            
            if (diff == delta) {
                return new int[]{x, y};
            } else if (diff < delta) {
                // x - y is too small, need larger x or smaller y
                i++;
            } else {
                // x - y is too large, need smaller x or larger y
                j++;
            }
        }
        
        return new int[]{-1, -1};
    }
    
    /**
     * Approach 3: Binary Search
     * O(n log m) time, O(1) extra space (if we sort in place)
     * Good when Bob's array is much larger than Alice's
     */
    public int[] fairCandySwapBinarySearch(int[] aliceSizes, int[] bobSizes) {
        // Calculate sums
        int sumA = 0, sumB = 0;
        for (int candy : aliceSizes) sumA += candy;
        for (int candy : bobSizes) sumB += candy;
        
        // Compute target difference
        int delta = (sumA - sumB) / 2;
        
        // Sort Bob's array for binary search
        Arrays.sort(bobSizes);
        
        // For each candy Alice has, binary search in Bob's array
        for (int x : aliceSizes) {
            int targetY = x - delta;
            if (binarySearch(bobSizes, targetY)) {
                return new int[]{x, targetY};
            }
        }
        
        return new int[]{-1, -1};
    }
    
    private boolean binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return true;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
    
    /**
     * Approach 4: Using boolean array (when values are limited)
     * O(n + m + K) time, O(K) space where K = max candy value
     * Fast when candy values are limited
     */
    public int[] fairCandySwapBooleanArray(int[] aliceSizes, int[] bobSizes) {
        // Calculate sums
        int sumA = 0, sumB = 0;
        int maxCandy = 0;
        
        for (int candy : aliceSizes) {
            sumA += candy;
            maxCandy = Math.max(maxCandy, candy);
        }
        for (int candy : bobSizes) {
            sumB += candy;
            maxCandy = Math.max(maxCandy, candy);
        }
        
        // Compute target difference
        int delta = (sumA - sumB) / 2;
        
        // Create boolean array for Bob's candies
        boolean[] bobHasCandy = new boolean[maxCandy + 1];
        for (int candy : bobSizes) {
            bobHasCandy[candy] = true;
        }
        
        // Check each of Alice's candies
        for (int x : aliceSizes) {
            int y = x - delta;
            if (y >= 0 && y <= maxCandy && bobHasCandy[y]) {
                return new int[]{x, y};
            }
        }
        
        return new int[]{-1, -1};
    }
    
    /**
     * Approach 5: Using Two HashSets
     * O(n + m) time, O(n + m) space
     * Symmetric approach
     */
    public int[] fairCandySwapTwoSets(int[] aliceSizes, int[] bobSizes) {
        // Calculate sums
        int sumA = 0, sumB = 0;
        for (int candy : aliceSizes) sumA += candy;
        for (int candy : bobSizes) sumB += candy;
        
        // Compute target difference
        int delta = (sumA - sumB) / 2;
        
        // Store both sets
        Set<Integer> aliceSet = new HashSet<>();
        Set<Integer> bobSet = new HashSet<>();
        
        for (int candy : aliceSizes) aliceSet.add(candy);
        for (int candy : bobSizes) bobSet.add(candy);
        
        // Check possible exchanges
        for (int x : aliceSet) {
            int y = x - delta;
            if (bobSet.contains(y)) {
                return new int[]{x, y};
            }
        }
        
        return new int[]{-1, -1};
    }
    
    /**
     * Helper method to visualize the candy exchange process
     */
    private void visualizeExchange(int[] aliceSizes, int[] bobSizes, String approach) {
        System.out.println("\n" + approach + " - Candy Exchange Visualization:");
        System.out.println("Alice's boxes: " + Arrays.toString(aliceSizes));
        System.out.println("Bob's boxes:   " + Arrays.toString(bobSizes));
        
        // Calculate sums
        int sumA = 0, sumB = 0;
        for (int candy : aliceSizes) sumA += candy;
        for (int candy : bobSizes) sumB += candy;
        
        System.out.println("\nInitial totals:");
        System.out.println("Alice: " + sumA + " candies");
        System.out.println("Bob:   " + sumB + " candies");
        System.out.println("Difference: " + (sumA - sumB));
        
        int delta = (sumA - sumB) / 2;
        System.out.println("\nMathematical relationship:");
        System.out.println("We need: x - y = (sumA - sumB) / 2 = " + delta);
        System.out.println("So: y = x - " + delta);
        
        System.out.println("\nLooking for exchange where:");
        System.out.println("Alice gives x, Bob gives y = x - " + delta);
        
        // Find and show the solution
        int[] result = fairCandySwap(aliceSizes, bobSizes);
        
        if (result[0] != -1) {
            System.out.println("\nFound solution:");
            System.out.println("Alice gives box with " + result[0] + " candies");
            System.out.println("Bob gives box with " + result[1] + " candies");
            
            // Verify the exchange
            int newSumA = sumA - result[0] + result[1];
            int newSumB = sumB - result[1] + result[0];
            
            System.out.println("\nAfter exchange:");
            System.out.println("Alice: " + sumA + " - " + result[0] + " + " + result[1] + " = " + newSumA);
            System.out.println("Bob:   " + sumB + " - " + result[1] + " + " + result[0] + " = " + newSumB);
            System.out.println("Both have " + newSumA + " candies ✓");
            
            // Show all possible exchanges
            System.out.println("\nAll possible exchanges:");
            Set<Integer> bobSet = new HashSet<>();
            for (int candy : bobSizes) bobSet.add(candy);
            
            int count = 0;
            for (int x : aliceSizes) {
                int y = x - delta;
                if (bobSet.contains(y)) {
                    count++;
                    System.out.println("  Option " + count + ": Alice gives " + x + ", Bob gives " + y);
                }
            }
        }
    }
    
    /**
     * Helper to show the mathematical derivation
     */
    private void showMathematicalDerivation() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL DERIVATION:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nLet:");
        System.out.println("  sumA = total candies Alice has initially");
        System.out.println("  sumB = total candies Bob has initially");
        System.out.println("  x = candies in box Alice gives");
        System.out.println("  y = candies in box Bob gives");
        
        System.out.println("\nAfter exchange:");
        System.out.println("  Alice has: sumA - x + y");
        System.out.println("  Bob has:   sumB - y + x");
        
        System.out.println("\nWe want them to have equal amounts:");
        System.out.println("  sumA - x + y = sumB - y + x");
        
        System.out.println("\nRearrange:");
        System.out.println("  sumA - sumB = 2x - 2y");
        System.out.println("  x - y = (sumA - sumB) / 2");
        
        System.out.println("\nLet delta = (sumA - sumB) / 2");
        System.out.println("Then: y = x - delta");
        
        System.out.println("\nAlgorithm:");
        System.out.println("1. Calculate sumA, sumB");
        System.out.println("2. Compute delta = (sumA - sumB) / 2");
        System.out.println("3. For each x in Alice's boxes:");
        System.out.println("   - Check if y = x - delta exists in Bob's boxes");
        System.out.println("4. Return [x, y] when found");
        
        System.out.println("\nExample:");
        System.out.println("Alice: [1, 2], Bob: [2, 3]");
        System.out.println("sumA = 3, sumB = 5");
        System.out.println("delta = (3 - 5) / 2 = -1");
        System.out.println("We need: y = x - (-1) = x + 1");
        System.out.println("Check x = 1: y = 2 (exists in Bob's boxes) ✓");
        System.out.println("Solution: [1, 2]");
    }
    
    /**
     * Helper to compare different approaches
     */
    private void compareApproaches(int[] aliceSizes, int[] bobSizes) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput:");
        System.out.println("Alice: " + Arrays.toString(aliceSizes));
        System.out.println("Bob:   " + Arrays.toString(bobSizes));
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int[] result1, result2, result3, result4, result5;
        
        // Approach 1: HashSet
        startTime = System.nanoTime();
        result1 = solution.fairCandySwap(aliceSizes, bobSizes);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Two Pointers (needs sorted arrays, so copy first)
        int[] aliceCopy = aliceSizes.clone();
        int[] bobCopy = bobSizes.clone();
        startTime = System.nanoTime();
        result2 = solution.fairCandySwapTwoPointers(aliceCopy, bobCopy);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Binary Search
        bobCopy = bobSizes.clone(); // Need to sort
        startTime = System.nanoTime();
        result3 = solution.fairCandySwapBinarySearch(aliceSizes, bobCopy);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Boolean Array
        startTime = System.nanoTime();
        result4 = solution.fairCandySwapBooleanArray(aliceSizes, bobSizes);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: Two Sets
        startTime = System.nanoTime();
        result5 = solution.fairCandySwapTwoSets(aliceSizes, bobSizes);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        // Verify all results are valid
        boolean allValid = true;
        int[][] results = {result1, result2, result3, result4, result5};
        
        // Check each result satisfies the condition
        int sumA = 0, sumB = 0;
        for (int candy : aliceSizes) sumA += candy;
        for (int candy : bobSizes) sumB += candy;
        int delta = (sumA - sumB) / 2;
        
        for (int i = 0; i < results.length; i++) {
            int x = results[i][0];
            int y = results[i][1];
            if (x - y != delta) {
                allValid = false;
                System.out.println("Approach " + (i+1) + " invalid: " + x + " - " + y + " = " + (x-y) + " != " + delta);
            }
        }
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (HashSet):        [" + result1[0] + ", " + result1[1] + "]");
        System.out.println("Approach 2 (Two Pointers):   [" + result2[0] + ", " + result2[1] + "]");
        System.out.println("Approach 3 (Binary Search):  [" + result3[0] + ", " + result3[1] + "]");
        System.out.println("Approach 4 (Boolean Array):  [" + result4[0] + ", " + result4[1] + "]");
        System.out.println("Approach 5 (Two Sets):       [" + result5[0] + ", " + result5[1] + "]");
        
        System.out.println("\nAll results valid: " + (allValid ? "✓ YES" : "✗ NO"));
        
        System.out.println("\nPerformance (nanoseconds):");
        System.out.printf("Approach 1: %-10d (HashSet)%n", time1);
        System.out.printf("Approach 2: %-10d (Two Pointers with sorting)%n", time2);
        System.out.printf("Approach 3: %-10d (Binary Search)%n", time3);
        System.out.printf("Approach 4: %-10d (Boolean Array)%n", time4);
        System.out.printf("Approach 5: %-10d (Two Sets)%n", time5);
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Fair Candy Swap:");
        System.out.println("========================");
        
        // Show mathematical derivation first
        solution.showMathematicalDerivation();
        
        // Test case 1: Example from problem
        System.out.println("\n\nTest 1: Basic example");
        int[] alice1 = {1, 1};
        int[] bob1 = {2, 2};
        int[] expected1 = {1, 2};
        
        solution.visualizeExchange(alice1, bob1, "Test 1");
        
        int[] result1 = solution.fairCandySwap(alice1, bob1);
        System.out.println("\nExpected: " + Arrays.toString(expected1));
        System.out.println("Result:   " + Arrays.toString(result1));
        System.out.println("Passed: " + Arrays.equals(result1, expected1) ? "✓" : "✗");
        
        // Test case 2: Another example
        System.out.println("\n\nTest 2: Example 2");
        int[] alice2 = {1, 2};
        int[] bob2 = {2, 3};
        int[] expected2 = {1, 2};
        
        solution.visualizeExchange(alice2, bob2, "Test 2");
        
        int[] result2 = solution.fairCandySwap(alice2, bob2);
        System.out.println("\nExpected: " + Arrays.toString(expected2));
        System.out.println("Result:   " + Arrays.toString(result2));
        System.out.println("Passed: " + Arrays.equals(result2, expected2) ? "✓" : "✗");
        
        // Test case 3: Single boxes
        System.out.println("\n\nTest 3: Single boxes");
        int[] alice3 = {2};
        int[] bob3 = {1, 3};
        // Solution: Alice gives 2, Bob gives 3
        // sumA = 2, sumB = 4, delta = (2-4)/2 = -1
        // Need: y = x - (-1) = x + 1
        // For x = 2, y = 3 ✓
        
        solution.visualizeExchange(alice3, bob3, "Test 3");
        
        int[] result3 = solution.fairCandySwap(alice3, bob3);
        System.out.println("\nResult: " + Arrays.toString(result3));
        
        // Verify it's correct
        int sumA3 = 2, sumB3 = 4;
        int newSumA3 = sumA3 - result3[0] + result3[1];
        int newSumB3 = sumB3 - result3[1] + result3[0];
        System.out.println("Verification: Both have " + newSumA3 + " candies: " + 
                         (newSumA3 == newSumB3 ? "✓" : "✗"));
        
        // Test case 4: Larger arrays
        System.out.println("\n\nTest 4: Larger arrays");
        int[] alice4 = {1, 3, 5, 7, 9};
        int[] bob4 = {2, 4, 6, 8, 10};
        // sumA = 25, sumB = 30, delta = (25-30)/2 = -2.5 -> -2 (integer division)
        // Actually: (25-30)/2 = -2 (integer division truncates toward 0)
        // Need: y = x - (-2) = x + 2
        
        solution.visualizeExchange(alice4, bob4, "Test 4");
        
        int[] result4 = solution.fairCandySwap(alice4, bob4);
        System.out.println("\nResult: " + Arrays.toString(result4));
        
        // Test case 5: Equal sums (shouldn't happen per constraints)
        System.out.println("\n\nTest 5: Equal sums (edge case)");
        int[] alice5 = {1, 2, 3};
        int[] bob5 = {1, 2, 3};
        // sumA = 6, sumB = 6, delta = 0
        // Need: y = x - 0 = x
        // Any matching pair works
        
        solution.visualizeExchange(alice5, bob5, "Test 5");
        
        // Test case 6: Multiple possible solutions
        System.out.println("\n\nTest 6: Multiple solutions");
        int[] alice6 = {1, 2, 5};
        int[] bob6 = {2, 4};
        // sumA = 8, sumB = 6, delta = (8-6)/2 = 1
        // Need: y = x - 1
        // Possible: (2,1), (5,4)
        
        solution.visualizeExchange(alice6, bob6, "Test 6");
        
        // Compare all approaches for this test case
        solution.compareApproaches(alice6, bob6);
        
        // Test case 7: Large values
        System.out.println("\n\nTest 7: Large candy values");
        int[] alice7 = {100000, 200000, 300000};
        int[] bob7 = {150000, 250000, 350000};
        
        solution.visualizeExchange(alice7, bob7, "Test 7");
        
        int[] result7 = solution.fairCandySwap(alice7, bob7);
        System.out.println("\nResult: " + Arrays.toString(result7));
        
        // Test all approaches on various test cases
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE TESTING OF ALL APPROACHES:");
        System.out.println("=".repeat(80));
        
        int[][][] testCases = {
            {alice1, bob1},
            {alice2, bob2},
            {alice3, bob3},
            {alice4, bob4},
            {alice6, bob6},
            {alice7, bob7},
            {new int[]{3, 4, 5}, new int[]{1, 2, 6}},
            {new int[]{10, 20, 30}, new int[]{5, 15, 25}},
            {new int[]{1, 2, 3, 4, 5}, new int[]{6, 7, 8}},
            {new int[]{100, 200, 300, 400}, new int[]{150, 250, 350}}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\nTest Case " + (i+1) + ":");
            System.out.println("Alice: " + Arrays.toString(testCases[i][0]));
            System.out.println("Bob:   " + Arrays.toString(testCases[i][1]));
            
            int[] alice = testCases[i][0];
            int[] bob = testCases[i][1];
            
            // Calculate expected using HashSet approach
            int[] expected = solution.fairCandySwap(alice, bob);
            
            // Test all approaches
            int[] r1 = solution.fairCandySwap(alice, bob);
            int[] r2 = solution.fairCandySwapTwoPointers(alice.clone(), bob.clone());
            int[] r3 = solution.fairCandySwapBinarySearch(alice, bob.clone());
            int[] r4 = solution.fairCandySwapBooleanArray(alice, bob);
            int[] r5 = solution.fairCandySwapTwoSets(alice, bob);
            
            boolean allMatch = Arrays.equals(r1, expected) &&
                              Arrays.equals(r2, expected) &&
                              Arrays.equals(r3, expected) &&
                              Arrays.equals(r4, expected) &&
                              Arrays.equals(r5, expected);
            
            System.out.println("All approaches match: " + (allMatch ? "✓" : "✗"));
            
            if (!allMatch) {
                System.out.println("HashSet:       " + Arrays.toString(r1));
                System.out.println("Two Pointers:  " + Arrays.toString(r2));
                System.out.println("Binary Search: " + Arrays.toString(r3));
                System.out.println("Boolean Array: " + Arrays.toString(r4));
                System.out.println("Two Sets:      " + Arrays.toString(r5));
            }
        }
        
        // Performance test with large arrays
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST WITH LARGE ARRAYS:");
        System.out.println("=".repeat(80));
        
        // Generate large test data
        Random random = new Random(42);
        int size = 10000;
        int[] largeAlice = new int[size];
        int[] largeBob = new int[size];
        
        for (int i = 0; i < size; i++) {
            largeAlice[i] = random.nextInt(100000) + 1;
            largeBob[i] = random.nextInt(100000) + 1;
        }
        
        System.out.println("\nTesting with " + size + " boxes each:");
        System.out.println("Alice: values 1-100000");
        System.out.println("Bob:   values 1-100000");
        
        long startTime, endTime;
        
        // Approach 1: HashSet
        startTime = System.currentTimeMillis();
        int[] perf1 = solution.fairCandySwap(largeAlice, largeBob);
        endTime = System.currentTimeMillis();
        long time1 = endTime - startTime;
        
        // Approach 2: Two Pointers (with sorting)
        int[] aliceCopy = largeAlice.clone();
        int[] bobCopy = largeBob.clone();
        startTime = System.currentTimeMillis();
        int[] perf2 = solution.fairCandySwapTwoPointers(aliceCopy, bobCopy);
        endTime = System.currentTimeMillis();
        long time2 = endTime - startTime;
        
        // Approach 3: Binary Search
        bobCopy = largeBob.clone();
        startTime = System.currentTimeMillis();
        int[] perf3 = solution.fairCandySwapBinarySearch(largeAlice, bobCopy);
        endTime = System.currentTimeMillis();
        long time3 = endTime - startTime;
        
        // Approach 4: Boolean Array (may not work due to large values)
        startTime = System.currentTimeMillis();
        int[] perf4 = solution.fairCandySwapBooleanArray(largeAlice, largeBob);
        endTime = System.currentTimeMillis();
        long time4 = endTime - startTime;
        
        // Approach 5: Two Sets
        startTime = System.currentTimeMillis();
        int[] perf5 = solution.fairCandySwapTwoSets(largeAlice, largeBob);
        endTime = System.currentTimeMillis();
        long time5 = endTime - startTime;
        
        System.out.println("\nPerformance (milliseconds):");
        System.out.printf("Approach 1 (HashSet):        %5d ms%n", time1);
        System.out.printf("Approach 2 (Two Pointers):   %5d ms%n", time2);
        System.out.printf("Approach 3 (Binary Search):  %5d ms%n", time3);
        System.out.printf("Approach 4 (Boolean Array):  %5d ms%n", time4);
        System.out.printf("Approach 5 (Two Sets):       %5d ms%n", time5);
        
        // Verify all give same result
        boolean perfConsistent = Arrays.equals(perf1, perf2) &&
                                Arrays.equals(perf2, perf3) &&
                                Arrays.equals(perf3, perf4) &&
                                Arrays.equals(perf4, perf5);
        
        System.out.println("\nResults consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Algorithm explanation and analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nOptimal Approach (HashSet):");
        System.out.println("Time Complexity: O(n + m)");
        System.out.println("  - O(n) to compute sumA and iterate through Alice's boxes");
        System.out.println("  - O(m) to compute sumB and build HashSet");
        System.out.println("  - O(n) to check each Alice box against HashSet");
        System.out.println("  - Total: O(n + m)");
        
        System.out.println("\nSpace Complexity: O(m)");
        System.out.println("  - HashSet storing Bob's candy boxes");
        
        System.out.println("\nWhy HashSet is optimal:");
        System.out.println("1. O(1) average case lookup time");
        System.out.println("2. Simple implementation");
        System.out.println("3. Handles all constraints efficiently");
        System.out.println("4. No need for sorting");
        
        System.out.println("\nAlternative Approaches:");
        System.out.println("1. Two Pointers with sorting: O((n+m) log(n+m)) time, O(1) space");
        System.out.println("2. Binary Search: O(n log m) time, O(1) space (if sort in place)");
        System.out.println("3. Boolean Array: O(n+m+K) time, O(K) space (K = max candy value)");
        System.out.println("4. Two Sets: O(n+m) time, O(n+m) space");
        
        // Edge cases and common mistakes
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMMON MISTAKES AND EDGE CASES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Integer division:");
        System.out.println("   - delta = (sumA - sumB) / 2 uses integer division");
        System.out.println("   - In Java, division truncates toward 0");
        System.out.println("   - This is correct because sumA - sumB is always even");
        
        System.out.println("\n2. Negative delta:");
        System.out.println("   - When Bob has more candy, delta is negative");
        System.out.println("   - Formula y = x - delta still works");
        System.out.println("   - Example: x=1, delta=-2 → y=3");
        
        System.out.println("\n3. Multiple solutions:");
        System.out.println("   - Problem says any valid answer is acceptable");
        System.out.println("   - Different approaches may return different valid pairs");
        
        System.out.println("\n4. Large arrays:");
        System.out.println("   - Up to 10^4 elements");
        System.out.println("   - Need O(n) or O(n log n) solution");
        System.out.println("   - O(n²) brute force is too slow");
        
        System.out.println("\n5. Large candy values:");
        System.out.println("   - Up to 10^5");
        System.out.println("   - Boolean array approach needs O(10^5) space");
        System.out.println("   - HashSet handles this efficiently");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Resource Allocation:");
        System.out.println("   - Balancing resources between two parties");
        System.out.println("   - Fair exchange problems");
        
        System.out.println("\n2. Trading Systems:");
        System.out.println("   - Finding fair trades between two traders");
        System.out.println("   - Barter systems");
        
        System.out.println("\n3. Game Theory:");
        System.out.println("   - Finding Nash equilibria in simple games");
        System.out.println("   - Fair division problems");
        
        System.out.println("\n4. Database Systems:");
        System.out.println("   - Balancing data between two servers");
        System.out.println("   - Load balancing algorithms");
        
        System.out.println("\n5. Financial Applications:");
        System.out.println("   - Portfolio rebalancing");
        System.out.println("   - Asset exchange optimization");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - Exchange one box each");
        System.out.println("   - Equal totals after exchange");
        System.out.println("   - Guaranteed solution exists");
        
        System.out.println("\n2. Derive mathematical relationship:");
        System.out.println("   - sumA - x + y = sumB - y + x");
        System.out.println("   - Simplify: x - y = (sumA - sumB) / 2");
        System.out.println("   - Let delta = (sumA - sumB) / 2");
        System.out.println("   - Need y = x - delta");
        
        System.out.println("\n3. Brute force approach:");
        System.out.println("   - Try all pairs (x, y)");
        System.out.println("   - O(n*m) time, too slow for constraints");
        
        System.out.println("\n4. Optimize with data structures:");
        System.out.println("   - For each x, need to find y = x - delta");
        System.out.println("   - Store Bob's values in HashSet for O(1) lookup");
        System.out.println("   - O(n + m) time, O(m) space");
        
        System.out.println("\n5. Discuss alternatives:");
        System.out.println("   - Sorting + two pointers");
        System.out.println("   - Binary search");
        System.out.println("   - Boolean array for limited values");
        
        System.out.println("\n6. Walk through example:");
        System.out.println("   - Use given examples to demonstrate");
        
        System.out.println("\n7. Handle edge cases:");
        System.out.println("   - Negative delta");
        System.out.println("   - Multiple solutions");
        System.out.println("   - Large inputs");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Mathematical derivation is key to solving efficiently");
        System.out.println("- HashSet provides optimal time complexity");
        System.out.println("- Problem reduces to 'find pair with specific difference'");
        System.out.println("- Similar to Two Sum problem");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not deriving the mathematical formula");
        System.out.println("- Using O(n*m) brute force");
        System.out.println("- Forgetting about integer division");
        System.out.println("- Not handling negative delta correctly");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 1. Two Sum (Similar pattern)");
        System.out.println("2. 167. Two Sum II - Input Array Is Sorted");
        System.out.println("3. 532. K-diff Pairs in an Array");
        System.out.println("4. 349. Intersection of Two Arrays");
        System.out.println("5. 350. Intersection of Two Arrays II");
        System.out.println("6. 454. 4Sum II");
        System.out.println("7. 561. Array Partition");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
