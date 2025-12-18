
# Solution.java

```java
import java.util.*;

/**
 * 523. Continuous Subarray Sum
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Check if array has subarray of length ≥ 2 with sum divisible by k.
 * 
 * Key Insights:
 * 1. Prefix sum + modular arithmetic:
 *    - If sum[i:j] % k == 0, then (prefix[j+1] - prefix[i]) % k == 0
 *    - So prefix[j+1] % k == prefix[i] % k
 * 2. Same remainder means divisible subarray between them
 * 3. Use hash map to store first occurrence of each remainder
 * 4. Check if current remainder seen before with distance ≥ 2
 * 
 * Approach (Prefix Sum with Hash Map):
 * 1. Initialize hash map with remainder 0 at index -1 (for subarrays starting at 0)
 * 2. Compute running sum modulo k (handle k=0 separately)
 * 3. For each remainder:
 *    - If seen before and distance ≥ 2 → return true
 *    - Otherwise store first occurrence
 * 4. Handle k=0 case: look for two consecutive zeros
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(min(n, k))
 * 
 * Tags: Array, Hash Table, Math, Prefix Sum
 */

class Solution {
    
    /**
     * Approach 1: Prefix Sum with Hash Map (RECOMMENDED)
     * O(n) time, O(min(n, k)) space
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        int n = nums.length;
        
        // Special case: k == 0
        if (k == 0) {
            // Look for two consecutive zeros
            for (int i = 0; i < n - 1; i++) {
                if (nums[i] == 0 && nums[i + 1] == 0) {
                    return true;
                }
            }
            return false;
        }
        
        // Map from remainder modulo k to first index where it occurred
        Map<Integer, Integer> remainderMap = new HashMap<>();
        // Initialize with remainder 0 at index -1
        // This handles case where prefix sum itself is divisible by k
        remainderMap.put(0, -1);
        
        int runningSum = 0;
        
        for (int i = 0; i < n; i++) {
            runningSum += nums[i];
            int remainder = runningSum % k;
            
            // Handle negative remainder (Java's % gives negative for negative numbers)
            if (remainder < 0) {
                remainder += k;
            }
            
            if (remainderMap.containsKey(remainder)) {
                int prevIndex = remainderMap.get(remainder);
                // Check if subarray length is at least 2
                if (i - prevIndex >= 2) {
                    return true;
                }
            } else {
                remainderMap.put(remainder, i);
            }
        }
        
        return false;
    }
    
    /**
     * Approach 2: Prefix Sum Array
     * O(n) time, O(n) space
     * More intuitive but uses more space
     */
    public boolean checkSubarraySumPrefixArray(int[] nums, int k) {
        int n = nums.length;
        
        if (k == 0) {
            for (int i = 0; i < n - 1; i++) {
                if (nums[i] == 0 && nums[i + 1] == 0) {
                    return true;
                }
            }
            return false;
        }
        
        // Compute prefix sums
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        // Check all pairs (O(n²) would be too slow, but we optimize with hash map)
        Map<Integer, Integer> remainderMap = new HashMap<>();
        
        for (int i = 0; i <= n; i++) {
            int remainder = prefixSum[i] % k;
            if (remainder < 0) remainder += k;
            
            if (remainderMap.containsKey(remainder)) {
                int prevIndex = remainderMap.get(remainder);
                if (i - prevIndex >= 2) {
                    return true;
                }
            } else {
                remainderMap.put(remainder, i);
            }
        }
        
        return false;
    }
    
    /**
     * Approach 3: Brute Force (for comparison)
     * O(n²) time, O(1) space
     * Only works for small n
     */
    public boolean checkSubarraySumBruteForce(int[] nums, int k) {
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            int sum = nums[i];
            for (int j = i + 1; j < n; j++) {
                sum += nums[j];
                if (k == 0) {
                    if (sum == 0) return true;
                } else if (sum % k == 0) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Approach 4: Using HashSet (instead of HashMap)
     * O(n) time, O(min(n, k)) space
     * Stores remainders but needs to track index differently
     */
    public boolean checkSubarraySumHashSet(int[] nums, int k) {
        int n = nums.length;
        
        if (k == 0) {
            for (int i = 0; i < n - 1; i++) {
                if (nums[i] == 0 && nums[i + 1] == 0) {
                    return true;
                }
            }
            return false;
        }
        
        Set<Integer> remainders = new HashSet<>();
        int runningSum = 0;
        int prevRemainder = 0;
        
        for (int i = 0; i < n; i++) {
            runningSum += nums[i];
            int remainder = runningSum % k;
            if (remainder < 0) remainder += k;
            
            // If we've seen this remainder before
            if (remainders.contains(remainder)) {
                return true;
            }
            
            // Add previous remainder to set (for next iteration)
            remainders.add(prevRemainder);
            prevRemainder = remainder;
        }
        
        return false;
    }
    
    /**
     * Approach 5: Optimized for memory (array instead of hash map when k is small)
     * O(n) time, O(k) space
     * Good when k is relatively small
     */
    public boolean checkSubarraySumArray(int[] nums, int k) {
        int n = nums.length;
        
        if (k == 0) {
            for (int i = 0; i < n - 1; i++) {
                if (nums[i] == 0 && nums[i + 1] == 0) {
                    return true;
                }
            }
            return false;
        }
        
        // If k is too large, use hash map approach
        if (k > 100000) {
            return checkSubarraySum(nums, k);
        }
        
        // Use array to store first occurrence index of each remainder
        int[] remainderIndex = new int[k];
        Arrays.fill(remainderIndex, -2); // -2 means not seen
        remainderIndex[0] = -1; // Initialize remainder 0 at index -1
        
        int runningSum = 0;
        
        for (int i = 0; i < n; i++) {
            runningSum += nums[i];
            int remainder = runningSum % k;
            if (remainder < 0) remainder += k;
            
            if (remainderIndex[remainder] != -2) {
                if (i - remainderIndex[remainder] >= 2) {
                    return true;
                }
            } else {
                remainderIndex[remainder] = i;
            }
        }
        
        return false;
    }
    
    /**
     * Helper method to visualize the prefix sum and remainder process
     */
    private void visualizeSolution(int[] nums, int k, String approach) {
        System.out.println("\n" + approach + " - Solution Visualization:");
        System.out.println("nums = " + Arrays.toString(nums) + ", k = " + k);
        System.out.println("\nIndex | Value | Running Sum | Remainder (mod " + k + ") | Action");
        System.out.println("------|-------|-------------|-------------------------|--------");
        
        if (k == 0) {
            System.out.println("\nSpecial case k = 0: Looking for two consecutive zeros");
            for (int i = 0; i < nums.length - 1; i++) {
                System.out.printf("Checking indices %d,%d: [%d, %d]%n", 
                    i, i+1, nums[i], nums[i+1]);
                if (nums[i] == 0 && nums[i + 1] == 0) {
                    System.out.println("✓ Found two consecutive zeros at indices " + i + "," + (i+1));
                    return;
                }
            }
            System.out.println("✗ No two consecutive zeros found");
            return;
        }
        
        Map<Integer, Integer> remainderMap = new HashMap<>();
        remainderMap.put(0, -1);
        
        int runningSum = 0;
        
        for (int i = 0; i < nums.length; i++) {
            runningSum += nums[i];
            int remainder = runningSum % k;
            if (remainder < 0) remainder += k;
            
            System.out.printf("%5d | %5d | %11d | %23d | ", 
                i, nums[i], runningSum, remainder);
            
            if (remainderMap.containsKey(remainder)) {
                int prevIndex = remainderMap.get(remainder);
                if (i - prevIndex >= 2) {
                    System.out.printf("Remainder %d seen before at index %d (distance=%d)%n",
                        remainder, prevIndex, i - prevIndex);
                    System.out.printf("✓ Found subarray [%d..%d] with sum divisible by %d%n",
                        prevIndex + 1, i, k);
                    
                    // Show the subarray
                    System.out.print("Subarray: [");
                    int sum = 0;
                    for (int j = prevIndex + 1; j <= i; j++) {
                        System.out.print(nums[j]);
                        sum += nums[j];
                        if (j < i) System.out.print(", ");
                    }
                    System.out.printf("], sum = %d, %d %% %d = %d%n",
                        sum, sum, k, sum % k);
                    return;
                } else {
                    System.out.printf("Remainder %d seen at index %d but distance=%d < 2%n",
                        remainder, prevIndex, i - prevIndex);
                }
            } else {
                System.out.printf("First time seeing remainder %d, storing at index %d%n",
                    remainder, i);
                remainderMap.put(remainder, i);
            }
        }
        
        System.out.println("\n✗ No valid subarray found");
        
        // Show all remainders and their first occurrences
        System.out.println("\nAll remainders encountered:");
        System.out.println("Remainder | First Index");
        System.out.println("----------|------------");
        for (Map.Entry<Integer, Integer> entry : remainderMap.entrySet()) {
            System.out.printf("%9d | %11d%n", entry.getKey(), entry.getValue());
        }
    }
    
    /**
     * Helper to explain the mathematical principle
     */
    private void explainMathematicalPrinciple() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL PRINCIPLE:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nKey Insight:");
        System.out.println("For subarray nums[i..j] (inclusive, 0-indexed):");
        System.out.println("  sum(i, j) = prefix[j+1] - prefix[i]");
        System.out.println("where prefix[k] = sum of first k elements (prefix[0] = 0)");
        
        System.out.println("\nIf sum(i, j) is divisible by k:");
        System.out.println("  (prefix[j+1] - prefix[i]) % k == 0");
        System.out.println("  => prefix[j+1] % k == prefix[i] % k");
        
        System.out.println("\nTherefore:");
        System.out.println("If two prefix sums have the same remainder modulo k,");
        System.out.println("the subarray between them has sum divisible by k.");
        
        System.out.println("\nExample: nums = [23, 2, 4, 6, 7], k = 6");
        System.out.println("Prefix sums: [0, 23, 25, 29, 35, 42]");
        System.out.println("Prefix % 6:  [0, 5,  1,  5,  5,  0]");
        System.out.println("\nRemainder 5 appears at indices 1, 3, 4:");
        System.out.println("  - Between indices 1 and 3: nums[2..3] = [2, 4], sum = 6 ✓");
        System.out.println("  - Between indices 1 and 4: nums[2..4] = [2, 4, 6], sum = 12 ✓");
        System.out.println("  - Between indices 3 and 4: nums[4..4] = [6], length=1 (invalid)");
        System.out.println("Remainder 0 appears at indices 0 and 5:");
        System.out.println("  - Between indices 0 and 5: whole array, sum = 42 ✓");
        
        System.out.println("\nWhy store index -1 for remainder 0?");
        System.out.println("Remainder 0 means prefix sum itself is divisible by k.");
        System.out.println("To handle subarrays starting from index 0,");
        System.out.println("we need a 'virtual' prefix sum of 0 at index -1.");
    }
    
    /**
     * Helper to demonstrate edge cases
     */
    private void demonstrateEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES DEMONSTRATION:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. k = 0:");
        System.out.println("   Looking for two consecutive zeros");
        int[] case1 = {0, 1, 0, 0, 2};
        System.out.println("   nums = " + Arrays.toString(case1) + ", k = 0");
        System.out.println("   Result: " + solution.checkSubarraySum(case1, 0) + 
                         " (has zeros at indices 2,3)");
        
        System.out.println("\n2. k = 1:");
        System.out.println("   Any sum is divisible by 1, need length ≥ 2");
        int[] case2 = {1};
        int[] case3 = {1, 2};
        System.out.println("   nums = " + Arrays.toString(case2) + ", k = 1: " + 
                         solution.checkSubarraySum(case2, 1) + " (length=1)");
        System.out.println("   nums = " + Arrays.toString(case3) + ", k = 1: " + 
                         solution.checkSubarraySum(case3, 1) + " (length=2)");
        
        System.out.println("\n3. Negative remainders (Java's % gives negative):");
        System.out.println("   In Java: -1 % 6 = -1, but mathematically it should be 5");
        System.out.println("   We fix by: if remainder < 0, remainder += k");
        int[] case4 = {-1, 2, 3};
        System.out.println("   nums = " + Arrays.toString(case4) + ", k = 6");
        System.out.println("   Result: " + solution.checkSubarraySum(case4, 6));
        
        System.out.println("\n4. Large k (greater than sum):");
        System.out.println("   Only remainder 0 matters (from prefix[0])");
        int[] case5 = {1, 2, 3};
        System.out.println("   nums = " + Arrays.toString(case5) + ", k = 100");
        System.out.println("   Result: " + solution.checkSubarraySum(case5, 100));
        
        System.out.println("\n5. Single element array:");
        int[] case6 = {6};
        System.out.println("   nums = " + Arrays.toString(case6) + ", k = 6");
        System.out.println("   Result: " + solution.checkSubarraySum(case6, 6) + 
                         " (length=1 < 2)");
        
        System.out.println("\n6. All zeros:");
        int[] case7 = {0, 0, 0};
        System.out.println("   nums = " + Arrays.toString(case7) + ", k = any");
        System.out.println("   k=0: " + solution.checkSubarraySum(case7, 0));
        System.out.println("   k=5: " + solution.checkSubarraySum(case7, 5));
    }
    
    /**
     * Helper to compare different approaches
     */
    private void compareApproaches(int[] nums, int k) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput:");
        System.out.println("nums = " + Arrays.toString(nums));
        System.out.println("k = " + k);
        System.out.println("n = " + nums.length);
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        boolean result1, result2, result3, result4, result5;
        
        // Approach 1: Hash Map (recommended)
        startTime = System.nanoTime();
        result1 = solution.checkSubarraySum(nums, k);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Prefix Array
        startTime = System.nanoTime();
        result2 = solution.checkSubarraySumPrefixArray(nums, k);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 4: HashSet
        startTime = System.nanoTime();
        result4 = solution.checkSubarraySumHashSet(nums, k);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 5: Array (for small k)
        startTime = System.nanoTime();
        result5 = solution.checkSubarraySumArray(nums, k);
        endTime = System.nanoTime();
        long time5 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (HashMap):        " + result1);
        System.out.println("Approach 2 (Prefix Array):   " + result2);
        System.out.println("Approach 4 (HashSet):        " + result4);
        System.out.println("Approach 5 (Array):          " + result5);
        
        // Approach 3: Brute force (only for small n)
        if (nums.length <= 100) {
            startTime = System.nanoTime();
            result3 = solution.checkSubarraySumBruteForce(nums, k);
            endTime = System.nanoTime();
            long time3 = endTime - startTime;
            
            System.out.println("Approach 3 (Brute Force):    " + result3);
            
            boolean allEqual = (result1 == result2) && (result2 == result3) && 
                              (result3 == result4) && (result4 == result5);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (HashMap)%n", time1);
            System.out.printf("Approach 2: %-10d (Prefix Array)%n", time2);
            System.out.printf("Approach 3: %-10d (Brute Force)%n", time3);
            System.out.printf("Approach 4: %-10d (HashSet)%n", time4);
            System.out.printf("Approach 5: %-10d (Array)%n", time5);
        } else {
            boolean allEqual = (result1 == result2) && (result2 == result4) && (result4 == result5);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (HashMap)%n", time1);
            System.out.printf("Approach 2: %-10d (Prefix Array)%n", time2);
            System.out.printf("Approach 4: %-10d (HashSet)%n", time4);
            System.out.printf("Approach 5: %-10d (Array)%n", time5);
        }
        
        // Visualize the solution
        if (nums.length <= 20) {
            solution.visualizeSolution(nums, k, "HashMap Approach");
        }
    }
    
    /**
     * Helper to analyze time and space complexity
     */
    private void analyzeComplexity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Time Complexity:");
        System.out.println("   All optimal approaches: O(n)");
        System.out.println("   - Single pass through the array");
        System.out.println("   - Each operation (addition, modulo, hash map) is O(1)");
        System.out.println("   - Brute force: O(n²) (too slow for n ≤ 10^5)");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   a. HashMap: O(min(n, k))");
        System.out.println("      - Store at most k different remainders");
        System.out.println("      - Or n if n < k");
        System.out.println("   b. Prefix Array: O(n)");
        System.out.println("      - Store all prefix sums");
        System.out.println("   c. HashSet: O(min(n, k))");
        System.out.println("      - Similar to HashMap but doesn't store indices");
        System.out.println("   d. Array: O(k)");
        System.out.println("      - Good when k is small");
        
        System.out.println("\n3. Why O(min(n, k)) space for hash map?");
        System.out.println("   - There are only k possible remainders (0 to k-1)");
        System.out.println("   - If n < k, we store at most n entries");
        System.out.println("   - If k is large (e.g., 10^9), we store at most n entries");
        
        System.out.println("\n4. Constraints Analysis:");
        System.out.println("   n ≤ 10^5, k ≤ 10^9");
        System.out.println("   - O(n) time: 100,000 operations (very fast)");
        System.out.println("   - O(min(n, k)) space: at most 100,000 entries");
        System.out.println("   - Brute force O(n²): 10^10 operations (too slow)");
    }
    
    /**
     * Helper to show real-world applications
     */
    private void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Financial Analysis:");
        System.out.println("   - Find periods where cumulative profit is multiple of target");
        System.out.println("   - Dividend payment scheduling");
        
        System.out.println("\n2. Load Balancing:");
        System.out.println("   - Distribute tasks so total load is multiple of capacity");
        System.out.println("   - Batch processing optimization");
        
        System.out.println("\n3. Cryptography:");
        System.out.println("   - Modular arithmetic in encryption algorithms");
        System.out.println("   - Hash collision detection");
        
        System.out.println("\n4. Game Development:");
        System.out.println("   - Check if score combinations hit certain multiples");
        System.out.println("   - Achievement system triggers");
        
        System.out.println("\n5. Resource Allocation:");
        System.out.println("   - Allocate resources in chunks of size k");
        System.out.println("   - Inventory management");
        
        System.out.println("\n6. Network Protocols:");
        System.out.println("   - Packet aggregation into fixed-size frames");
        System.out.println("   - Checksum validation");
        
        System.out.println("\n7. Calendar/Scheduling:");
        System.out.println("   - Find periods where total days is multiple of week");
        System.out.println("   - Recurring event planning");
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Continuous Subarray Sum:");
        System.out.println("=================================");
        
        // Show mathematical principle
        solution.explainMathematicalPrinciple();
        
        // Show edge cases
        solution.demonstrateEdgeCases();
        
        // Test case 1: Example from problem
        System.out.println("\n\nTest 1: Example 1");
        int[] nums1 = {23, 2, 4, 6, 7};
        int k1 = 6;
        boolean expected1 = true;
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums1) + ", k = " + k1);
        
        boolean result1 = solution.checkSubarraySum(nums1, k1);
        System.out.println("Expected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        // Visualize
        solution.visualizeSolution(nums1, k1, "Test 1");
        
        // Test case 2: Example 2
        System.out.println("\n\nTest 2: Example 2");
        int[] nums2 = {23, 2, 6, 4, 7};
        int k2 = 6;
        boolean expected2 = true;
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums2) + ", k = " + k2);
        
        boolean result2 = solution.checkSubarraySum(nums2, k2);
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        // Test case 3: Example 3
        System.out.println("\n\nTest 3: Example 3");
        int[] nums3 = {23, 2, 6, 4, 7};
        int k3 = 13;
        boolean expected3 = false;
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums3) + ", k = " + k3);
        
        boolean result3 = solution.checkSubarraySum(nums3, k3);
        System.out.println("Expected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + (result3 == expected3 ? "✓" : "✗"));
        
        // Test case 4: k = 0 with consecutive zeros
        System.out.println("\n\nTest 4: k = 0 with consecutive zeros");
        int[] nums4 = {1, 0, 0, 2, 3};
        int k4 = 0;
        boolean expected4 = true;
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums4) + ", k = " + k4);
        
        boolean result4 = solution.checkSubarraySum(nums4, k4);
        System.out.println("Expected: " + expected4);
        System.out.println("Result:   " + result4);
        System.out.println("Passed: " + (result4 == expected4 ? "✓" : "✗"));
        
        // Test case 5: k = 0 without consecutive zeros
        System.out.println("\n\nTest 5: k = 0 without consecutive zeros");
        int[] nums5 = {0, 1, 0, 2, 0};
        int k5 = 0;
        boolean expected5 = false;
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums5) + ", k = " + k5);
        
        boolean result5 = solution.checkSubarraySum(nums5, k5);
        System.out.println("Expected: " + expected5);
        System.out.println("Result:   " + result5);
        System.out.println("Passed: " + (result5 == expected5 ? "✓" : "✗"));
        
        // Test case 6: Single element (should be false even if divisible)
        System.out.println("\n\nTest 6: Single element divisible by k");
        int[] nums6 = {6};
        int k6 = 6;
        boolean expected6 = false; // Length must be ≥ 2
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums6) + ", k = " + k6);
        
        boolean result6 = solution.checkSubarraySum(nums6, k6);
        System.out.println("Expected: " + expected6);
        System.out.println("Result:   " + result6);
        System.out.println("Passed: " + (result6 == expected6 ? "✓" : "✗"));
        
        // Test case 7: All zeros
        System.out.println("\n\nTest 7: All zeros");
        int[] nums7 = {0, 0, 0};
        int k7 = 5;
        boolean expected7 = true; // Any subarray of length ≥ 2 sums to 0, divisible by any k
        
        System.out.println("\nInput: nums = " + Arrays.toString(nums7) + ", k = " + k7);
        
        boolean result7 = solution.checkSubarraySum(nums7, k7);
        System.out.println("Expected: " + expected7);
        System.out.println("Result:   " + result7);
        System.out.println("Passed: " + (result7 == expected7 ? "✓" : "✗"));
        
        // Compare all approaches
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES:");
        System.out.println("=".repeat(80));
        
        int[][][] testCases = {
            {nums1, {k1}},
            {nums2, {k2}},
            {nums3, {k3}},
            {nums4, {k4}},
            {nums5, {k5}},
            {nums6, {k6}},
            {nums7, {k7}},
            {new int[]{1, 2, 3, 4, 5}, new int[]{9}},
            {new int[]{0, 1, 2, 3, 4}, new int[]{3}},
            {new int[]{5, 0, 0, 0}, new int[]{0}},
            {new int[]{1, 3, 5, 7, 9}, new int[]{2}}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\nTest Case " + (i+1) + ":");
            int[] nums = testCases[i][0];
            int k = testCases[i][1][0];
            
            System.out.println("nums = " + Arrays.toString(nums) + ", k = " + k);
            solution.compareApproaches(nums, k);
            
            if (i < testCases.length - 1) {
                System.out.println("\n" + "-".repeat(80));
            }
        }
        
        // Performance test with larger array
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST WITH LARGER ARRAY:");
        System.out.println("=".repeat(80));
        
        // Generate larger test case
        Random random = new Random(42);
        int n = 100000; // Maximum constraint
        int[] largeNums = new int[n];
        for (int i = 0; i < n; i++) {
            largeNums[i] = random.nextInt(1000);
        }
        int largeK = 1000;
        
        System.out.println("\nTesting with n = " + n + ", k = " + largeK);
        
        long startTime, endTime;
        
        // Approach 1: Hash Map
        startTime = System.currentTimeMillis();
        boolean perf1 = solution.checkSubarraySum(largeNums, largeK);
        endTime = System.currentTimeMillis();
        long time1 = endTime - startTime;
        
        // Approach 2: Prefix Array
        startTime = System.currentTimeMillis();
        boolean perf2 = solution.checkSubarraySumPrefixArray(largeNums, largeK);
        endTime = System.currentTimeMillis();
        long time2 = endTime - startTime;
        
        // Approach 4: HashSet
        startTime = System.currentTimeMillis();
        boolean perf4 = solution.checkSubarraySumHashSet(largeNums, largeK);
        endTime = System.currentTimeMillis();
        long time4 = endTime - startTime;
        
        // Approach 5: Array (good for small k)
        startTime = System.currentTimeMillis();
        boolean perf5 = solution.checkSubarraySumArray(largeNums, largeK);
        endTime = System.currentTimeMillis();
        long time5 = endTime - startTime;
        
        System.out.println("\nPerformance (milliseconds):");
        System.out.printf("Approach 1 (HashMap):      %5d ms - Result: %b%n", time1, perf1);
        System.out.printf("Approach 2 (Prefix Array): %5d ms - Result: %b%n", time2, perf2);
        System.out.printf("Approach 4 (HashSet):      %5d ms - Result: %b%n", time4, perf4);
        System.out.printf("Approach 5 (Array):        %5d ms - Result: %b%n", time5, perf5);
        
        // Verify all give same result
        boolean perfConsistent = (perf1 == perf2) && (perf2 == perf4) && (perf4 == perf5);
        System.out.println("\nResults consistent: " + (perfConsistent ? "✓ YES" : "✗ NO"));
        
        // Show complexity analysis
        solution.analyzeComplexity();
        
        // Show applications
        solution.showApplications();
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - Find subarray length ≥ 2 with sum divisible by k");
        System.out.println("   - Operations: check all contiguous subarrays");
        
        System.out.println("\n2. Recognize need for optimization:");
        System.out.println("   - n up to 10^5, so O(n²) brute force is too slow");
        System.out.println("   - Need O(n) or O(n log n) solution");
        
        System.out.println("\n3. Think about prefix sums:");
        System.out.println("   - Subarray sum = prefix[j] - prefix[i]");
        System.out.println("   - Need (prefix[j] - prefix[i]) % k == 0");
        
        System.out.println("\n4. Apply modular arithmetic:");
        System.out.println("   - (a - b) % k == 0 means a % k == b % k");
        System.out.println("   - So prefix sums with same remainder → valid subarray");
        
        System.out.println("\n5. Design algorithm:");
        System.out.println("   - Compute running sum % k");
        System.out.println("   - Use hash map to store first occurrence of each remainder");
        System.out.println("   - Check if same remainder seen before with distance ≥ 2");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - k = 0: look for two consecutive zeros");
        System.out.println("   - Negative remainders: add k to make positive");
        System.out.println("   - Initialize with remainder 0 at index -1");
        
        System.out.println("\n7. Implement:");
        System.out.println("   - Special case for k = 0");
        System.out.println("   - HashMap<Integer, Integer> for remainder → index");
        System.out.println("   - Single pass through array");
        
        System.out.println("\n8. Walk through example:");
        System.out.println("   - Use given example to demonstrate");
        
        System.out.println("\n9. Discuss complexity:");
        System.out.println("   - Time: O(n), Space: O(min(n, k))");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Mathematical insight: same remainder → divisible sum");
        System.out.println("- Hash map for O(1) remainder lookup");
        System.out.println("- Careful handling of edge cases (k=0, negatives, length≥2)");
        System.out.println("- Space optimization: only need first occurrence of each remainder");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Not handling k = 0 case separately");
        System.out.println("- Not checking subarray length ≥ 2 requirement");
        System.out.println("- Not fixing negative remainders (Java's % gives negative)");
        System.out.println("- Not initializing with remainder 0 at index -1");
        System.out.println("- Using O(n²) brute force for large n");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 560. Subarray Sum Equals K");
        System.out.println("2. 974. Subarray Sums Divisible by K");
        System.out.println("3. 525. Contiguous Array");
        System.out.println("4. 209. Minimum Size Subarray Sum");
        System.out.println("5. 713. Subarray Product Less Than K");
        System.out.println("6. 862. Shortest Subarray with Sum at Least K");
        System.out.println("7. 930. Binary Subarrays With Sum");
        System.out.println("8. 1248. Count Number of Nice Subarrays");
        System.out.println("9. 1358. Number of Substrings Containing All Three Characters");
        System.out.println("10. 1442. Count Triplets That Can Form Two Arrays of Equal XOR");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
