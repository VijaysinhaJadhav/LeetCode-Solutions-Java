
# Solution.java

```java
import java.util.*;

/**
 * 2006. Count Number of Pairs With Absolute Difference K
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Count pairs (i, j) with i < j such that |nums[i] - nums[j]| == k.
 * 
 * Key Insights:
 * 1. Use frequency map to count occurrences
 * 2. For each number, check if num + k and num - k exist
 * 3. Multiply frequencies to count all pairs
 */
class Solution {
    
    /**
     * Approach 1: Hash Map (Frequency Counting) - RECOMMENDED
     * Time: O(n), Space: O(n)
     * 
     * Steps:
     * 1. Count frequency of each number
     * 2. For each unique number, check if num + k exists
     * 3. Add frequency[num] * frequency[num + k] to result
     * 4. No need to check num - k separately to avoid double counting
     */
    public int countKDifference(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        
        // Count frequencies
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        int count = 0;
        
        // Count pairs with difference k
        for (int num : freq.keySet()) {
            if (freq.containsKey(num + k)) {
                count += freq.get(num) * freq.get(num + k);
            }
        }
        
        return count;
    }
    
    /**
     * Approach 2: Hash Map with Single Pass
     * Time: O(n), Space: O(n)
     * 
     * Count pairs as we traverse (avoids double counting)
     */
    public int countKDifferenceSinglePass(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        int count = 0;
        
        for (int num : nums) {
            // Check for num + k and num - k in previously seen numbers
            count += freq.getOrDefault(num + k, 0);
            count += freq.getOrDefault(num - k, 0);
            
            // Add current number to frequency map
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * Approach 3: Brute Force
     * Time: O(n²), Space: O(1)
     * 
     * Check all pairs directly
     */
    public int countKDifferenceBruteForce(int[] nums, int k) {
        int count = 0;
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(nums[i] - nums[j]) == k) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 4: Sorting + Two Pointers
     * Time: O(n log n), Space: O(1) (or O(n) for sorting)
     * 
     * Sort array, then use two pointers to find pairs with difference k
     */
    public int countKDifferenceSorting(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            // For each i, find all j > i where nums[j] = nums[i] + k
            int target = nums[i] + k;
            int left = i + 1;
            int right = n - 1;
            
            // Binary search for first occurrence of target
            int first = -1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] == target) {
                    first = mid;
                    right = mid - 1;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            if (first != -1) {
                // Find last occurrence of target
                left = first;
                right = n - 1;
                int last = first;
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (nums[mid] == target) {
                        last = mid;
                        left = mid + 1;
                    } else if (nums[mid] < target) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                count += (last - first + 1);
            }
        }
        
        return count;
    }
    
    /**
     * Approach 5: Frequency Array (Since values are bounded 1-100)
     * Time: O(n + maxVal), Space: O(maxVal)
     * 
     * Most efficient when value range is small
     */
    public int countKDifferenceFreqArray(int[] nums, int k) {
        int[] freq = new int[101]; // nums[i] between 1 and 100
        int count = 0;
        
        // Single pass: count pairs with previously seen numbers
        for (int num : nums) {
            if (num + k <= 100) {
                count += freq[num + k];
            }
            if (num - k >= 1) {
                count += freq[num - k];
            }
            freq[num]++;
        }
        
        return count;
    }
    
    /**
     * Approach 6: Two-Pass Frequency Array
     * Time: O(n + maxVal), Space: O(maxVal)
     * 
     * Count frequencies first, then compute pairs
     */
    public int countKDifferenceTwoPassFreq(int[] nums, int k) {
        int[] freq = new int[101];
        
        for (int num : nums) {
            freq[num]++;
        }
        
        int count = 0;
        for (int i = 1; i + k <= 100; i++) {
            count += freq[i] * freq[i + k];
        }
        
        return count;
    }
    
    /**
     * Helper: Visualize the counting process
     */
    public void visualizeCount(int[] nums, int k) {
        System.out.println("\nCount Pairs with Absolute Difference K Visualization:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nArray: " + Arrays.toString(nums));
        System.out.println("k = " + k);
        
        System.out.println("\nHash Map Approach:");
        Map<Integer, Integer> freq = new HashMap<>();
        
        System.out.println("\nStep 1: Count frequencies");
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
            System.out.printf("  %d → frequency: %d%n", num, freq.get(num));
        }
        
        System.out.println("\nFrequency map: " + freq);
        
        System.out.println("\nStep 2: Count pairs");
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            int num = entry.getKey();
            int target = num + k;
            if (freq.containsKey(target)) {
                int pairs = entry.getValue() * freq.get(target);
                System.out.printf("  %d and %d: %d * %d = %d pairs%n", 
                    num, target, entry.getValue(), freq.get(target), pairs);
                count += pairs;
            }
        }
        
        System.out.println("\nTotal pairs: " + count);
        
        // Show brute force verification for small arrays
        if (nums.length <= 10) {
            System.out.println("\nBrute force verification:");
            int bruteCount = 0;
            for (int i = 0; i < nums.length; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (Math.abs(nums[i] - nums[j]) == k) {
                        System.out.printf("  (%d, %d) → |%d - %d| = %d%n", 
                            i, j, nums[i], nums[j], Math.abs(nums[i] - nums[j]));
                        bruteCount++;
                    }
                }
            }
            System.out.println("Brute force count: " + bruteCount);
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            {new int[]{1, 2, 2, 1}, 1, 4},      // Example 1
            {new int[]{1, 3}, 3, 0},              // Example 2
            {new int[]{3, 2, 1, 5, 4}, 2, 3},     // Example 3
            {new int[]{1, 1, 1, 1}, 0, 6},        // All same, k=0
            {new int[]{1, 2, 3, 4, 5}, 1, 4},     // Consecutive
            {new int[]{1, 2, 3, 4, 5}, 2, 3},     // Difference 2
            {new int[]{1}, 1, 0},                 // Single element
            {new int[]{1, 2, 2, 2, 3}, 1, 7},     // Duplicates
            {new int[]{1, 100, 2, 99, 3, 98}, 97, 3}, // Large difference
            {new int[]{5, 5, 5, 5, 5}, 0, 10}     // All same, k=0
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        Object[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = (int[]) testCases[i][0];
            int k = (int) testCases[i][1];
            int expected = (int) testCases[i][2];
            
            System.out.printf("\nTest %d: nums=%s, k=%d%n", i + 1, Arrays.toString(nums), k);
            
            int result1 = countKDifference(nums.clone(), k);
            int result2 = countKDifferenceSinglePass(nums.clone(), k);
            int result3 = countKDifferenceBruteForce(nums.clone(), k);
            int result4 = countKDifferenceSorting(nums.clone(), k);
            int result5 = countKDifferenceFreqArray(nums.clone(), k);
            int result6 = countKDifferenceTwoPassFreq(nums.clone(), k);
            
            boolean allMatch = result1 == expected && result2 == expected &&
                              result3 == expected && result4 == expected &&
                              result5 == expected && result6 == expected;
            
            if (allMatch) {
                System.out.println("✓ PASS - Count: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1 (HashMap): " + result1);
                System.out.println("  Method 2 (Single Pass): " + result2);
                System.out.println("  Method 3 (Brute Force): " + result3);
                System.out.println("  Method 4 (Sorting): " + result4);
                System.out.println("  Method 5 (Freq Array): " + result5);
                System.out.println("  Method 6 (Two-Pass Freq): " + result6);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeCount(nums, k);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=".repeat(50));
        
        // Generate large test case
        int n = 100000;
        int[] nums = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            nums[i] = rand.nextInt(100) + 1;
        }
        int k = 5;
        
        System.out.println("Test Setup: " + n + " elements");
        
        long[] times = new long[6];
        int[] results = new int[6];
        
        // Method 1: Hash Map
        int[] nums1 = nums.clone();
        long start = System.currentTimeMillis();
        results[0] = countKDifference(nums1, k);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Single Pass Hash Map
        int[] nums2 = nums.clone();
        start = System.currentTimeMillis();
        results[1] = countKDifferenceSinglePass(nums2, k);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Brute Force (skip for large n)
        times[2] = -1;
        results[2] = -1;
        
        // Method 4: Sorting
        int[] nums4 = nums.clone();
        start = System.currentTimeMillis();
        results[3] = countKDifferenceSorting(nums4, k);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Frequency Array (Single Pass)
        int[] nums5 = nums.clone();
        start = System.currentTimeMillis();
        results[4] = countKDifferenceFreqArray(nums5, k);
        times[4] = System.currentTimeMillis() - start;
        
        // Method 6: Frequency Array (Two Pass)
        int[] nums6 = nums.clone();
        start = System.currentTimeMillis();
        results[5] = countKDifferenceTwoPassFreq(nums6, k);
        times[5] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Hash Map               | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Single Pass Hash       | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. Brute Force            | %9s | %6s%n", "N/A", "N/A");
        System.out.printf("4. Sorting + Binary Search| %9d | %6d%n", times[3], results[3]);
        System.out.printf("5. Freq Array (Single)    | %9d | %6d%n", times[4], results[4]);
        System.out.printf("6. Freq Array (Two Pass)  | %9d | %6d%n", times[5], results[5]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[3] &&
                          results[3] == results[4] && results[4] == results[5];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Frequency array is fastest due to direct indexing");
        System.out.println("2. Hash map approaches are also O(n) and efficient");
        System.out.println("3. Sorting + binary search is slower due to O(n log n)");
        System.out.println("4. Brute force O(n²) is infeasible for large n");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single element:");
        int[] nums1 = {5};
        System.out.println("   Input: [5], k=1");
        System.out.println("   Output: " + countKDifference(nums1, 1));
        
        System.out.println("\n2. k = 0 (same values):");
        int[] nums2 = {1, 1, 1, 1};
        System.out.println("   Input: [1,1,1,1], k=0");
        System.out.println("   Output: " + countKDifference(nums2, 0));
        
        System.out.println("\n3. No pairs:");
        int[] nums3 = {1, 2, 3, 4};
        System.out.println("   Input: [1,2,3,4], k=10");
        System.out.println("   Output: " + countKDifference(nums3, 10));
        
        System.out.println("\n4. Maximum k (99):");
        int[] nums4 = {1, 100};
        System.out.println("   Input: [1,100], k=99");
        System.out.println("   Output: " + countKDifference(nums4, 99));
        
        System.out.println("\n5. All different values:");
        int[] nums5 = {10, 20, 30, 40, 50};
        System.out.println("   Input: [10,20,30,40,50], k=10");
        System.out.println("   Output: " + countKDifference(nums5, 10));
        
        System.out.println("\n6. Large array (1000 elements):");
        int[] nums6 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            nums6[i] = i % 50 + 1;
        }
        long start = System.currentTimeMillis();
        int result = countKDifference(nums6, 5);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 1000 elements");
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nProblem: Count pairs with absolute difference = k.");
        
        System.out.println("\nKey Insight:");
        System.out.println("For each number x, we need to find numbers x + k and x - k.");
        
        System.out.println("\nHash Map Approach:");
        System.out.println("1. Count frequency of each number");
        System.out.println("2. For each unique number x, check if x + k exists");
        System.out.println("3. Add freq[x] * freq[x + k] to result");
        
        System.out.println("\nExample: nums = [1,2,2,1], k = 1");
        System.out.println("  Frequencies: 1→2, 2→2");
        System.out.println("  Check 1: 1+1=2 exists → add 2 * 2 = 4");
        System.out.println("  Check 2: 2+1=3 does not exist → skip");
        System.out.println("  Total = 4");
        
        System.out.println("\nComplexity:");
        System.out.println("- Time: O(n) - single pass to build map, then iterate over unique keys");
        System.out.println("- Space: O(n) - store frequencies");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What is the range of values? (1 to 100)");
        System.out.println("   - Can k be 0? (Yes)");
        System.out.println("   - What about i < j constraint? (Count each pair once)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - O(n²) solution checking all pairs");
        System.out.println("   - Acceptable for small n, but can we do better?");
        
        System.out.println("\n3. Propose hash map solution:");
        System.out.println("   - Count frequencies first");
        System.out.println("   - For each number, check for num + k");
        System.out.println("   - Multiply frequencies to count all pairs");
        
        System.out.println("\n4. Discuss single-pass optimization:");
        System.out.println("   - Update count while building frequency map");
        System.out.println("   - Avoids second iteration");
        
        System.out.println("\n5. Mention frequency array optimization:");
        System.out.println("   - Since values are bounded (1-100), use array instead of map");
        System.out.println("   - O(n) time, O(1) space (fixed array size)");
        
        System.out.println("\n6. Complexity analysis:");
        System.out.println("   - Time: O(n) - single pass");
        System.out.println("   - Space: O(n) for map, O(1) for frequency array");
        
        System.out.println("\n7. Edge cases:");
        System.out.println("   - k = 0 (need to handle carefully)");
        System.out.println("   - Single element");
        System.out.println("   - No valid pairs");
        System.out.println("   - Duplicate values");
        
        System.out.println("\n8. Common mistakes:");
        System.out.println("   - Double counting when checking both num+k and num-k");
        System.out.println("   - Not handling k=0 correctly");
        System.out.println("   - Using nested loops when O(n) solution exists");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("2006. Count Number of Pairs With Absolute Difference K");
        System.out.println("======================================================");
        
        // Explain algorithm
        solution.explainAlgorithm();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation (Frequency Array):");
        System.out.println("""
class Solution {
    public int countKDifference(int[] nums, int k) {
        int[] freq = new int[101]; // values 1-100
        int count = 0;
        
        for (int num : nums) {
            if (num + k <= 100) count += freq[num + k];
            if (num - k >= 1) count += freq[num - k];
            freq[num]++;
        }
        
        return count;
    }
}
            """);
        
        System.out.println("\nAlternative (Hash Map):");
        System.out.println("""
class Solution {
    public int countKDifference(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        int count = 0;
        for (int num : freq.keySet()) {
            if (freq.containsKey(num + k)) {
                count += freq.get(num) * freq.get(num + k);
            }
        }
        
        return count;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Frequency counting is the key optimization");
        System.out.println("2. Use frequency array for bounded values (1-100)");
        System.out.println("3. Single pass avoids separate frequency map building");
        System.out.println("4. For k=0, formula becomes nC2 = n*(n-1)/2");
        System.out.println("5. Time: O(n), Space: O(1) with frequency array");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - single pass through array");
        System.out.println("- Space: O(1) - fixed size array of 101 elements");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle negative numbers? (Use map)");
        System.out.println("2. What if the array is sorted? (Use two pointers)");
        System.out.println("3. How would you return the actual pairs, not just count?");
        System.out.println("4. What if k is very large (beyond value range)?");
        System.out.println("5. How would you count pairs with difference ≤ k?");
    }
}
