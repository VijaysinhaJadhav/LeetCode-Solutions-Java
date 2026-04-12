
# Solution.java

```java
import java.util.*;

/**
 * 268. Missing Number
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Find the missing number in an array containing distinct numbers from 0 to n.
 * 
 * Key Insights:
 * 1. XOR: x ^ x = 0, so XOR all indices and values
 * 2. Sum: expected sum - actual sum
 * 3. Sorting: find where index != value
 * 4. All numbers are unique and in range [0, n]
 */
class Solution {
    
    /**
     * Approach 1: XOR (Bit Manipulation) - RECOMMENDED
     * Time: O(n), Space: O(1)
     * 
     * Steps:
     * 1. Initialize result = n
     * 2. XOR result with i and nums[i] for all i
     * 3. The missing number remains
     */
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int result = n;
        
        for (int i = 0; i < n; i++) {
            result ^= i ^ nums[i];
        }
        
        return result;
    }
    
    /**
     * Approach 2: Sum Formula
     * Time: O(n), Space: O(1)
     * 
     * Steps:
     * 1. Compute expected sum = n * (n + 1) / 2
     * 2. Compute actual sum of array
     * 3. Missing = expected - actual
     */
    public int missingNumberSum(int[] nums) {
        int n = nums.length;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;
        
        for (int num : nums) {
            actualSum += num;
        }
        
        return expectedSum - actualSum;
    }
    
    /**
     * Approach 3: Sorting
     * Time: O(n log n), Space: O(1)
     * 
     * Steps:
     * 1. Sort the array
     * 2. Find first index where nums[i] != i
     * 3. If none, return n
     */
    public int missingNumberSorting(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        
        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                return i;
            }
        }
        
        return n;
    }
    
    /**
     * Approach 4: Hash Set
     * Time: O(n), Space: O(n)
     * 
     * Steps:
     * 1. Add all numbers to set
     * 2. Check each number from 0 to n
     * 3. Return the first missing
     */
    public int missingNumberHashSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        
        int n = nums.length;
        for (int i = 0; i <= n; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 5: Boolean Array
     * Time: O(n), Space: O(n)
     * 
     * Steps:
     * 1. Create boolean array of size n+1
     * 2. Mark present numbers
     * 3. Find unmarked number
     */
    public int missingNumberBooleanArray(int[] nums) {
        int n = nums.length;
        boolean[] present = new boolean[n + 1];
        
        for (int num : nums) {
            present[num] = true;
        }
        
        for (int i = 0; i <= n; i++) {
            if (!present[i]) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 6: Gauss Formula with Long (Avoid Overflow)
     * Time: O(n), Space: O(1)
     * 
     * Use long to prevent overflow
     */
    public int missingNumberLong(int[] nums) {
        int n = nums.length;
        long expectedSum = (long) n * (n + 1) / 2;
        long actualSum = 0;
        
        for (int num : nums) {
            actualSum += num;
        }
        
        return (int) (expectedSum - actualSum);
    }
    
    /**
     * Helper: Visualize the XOR process
     */
    public void visualizeXOR(int[] nums) {
        System.out.println("\nMissing Number Visualization (XOR Method):");
        System.out.println("=".repeat(60));
        
        System.out.println("\nArray: " + Arrays.toString(nums));
        int n = nums.length;
        System.out.println("n = " + n);
        
        System.out.println("\nXOR Process:");
        System.out.println("result = n = " + n);
        
        int result = n;
        for (int i = 0; i < n; i++) {
            System.out.printf("  i=%d, nums[%d]=%d → result ^= %d ^ %d = %d%n", 
                i, i, nums[i], i, nums[i], result ^ i ^ nums[i]);
            result ^= i ^ nums[i];
        }
        
        System.out.println("\nMissing number: " + result);
        
        // Verification
        System.out.println("\nVerification:");
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);
        for (int i = 0; i <= n; i++) {
            if (!set.contains(i)) {
                System.out.println("  Number " + i + " is missing ✓");
                break;
            }
        }
    }
    
    /**
     * Helper: Visualize Sum approach
     */
    public void visualizeSum(int[] nums) {
        System.out.println("\nMissing Number Visualization (Sum Method):");
        System.out.println("=".repeat(60));
        
        System.out.println("\nArray: " + Arrays.toString(nums));
        int n = nums.length;
        
        long expectedSum = (long) n * (n + 1) / 2;
        long actualSum = 0;
        
        System.out.println("\nExpected sum (0 to " + n + "): " + expectedSum);
        
        for (int num : nums) {
            actualSum += num;
            System.out.printf("  Add %d → actual sum = %d%n", num, actualSum);
        }
        
        long missing = expectedSum - actualSum;
        System.out.println("\nMissing number: " + expectedSum + " - " + actualSum + " = " + missing);
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            {3, 0, 1},           // → 2
            {0, 1},              // → 2
            {9, 6, 4, 2, 3, 5, 7, 0, 1}, // → 8
            {0},                 // → 1
            {1},                 // → 0
            {0, 2, 3},           // → 1
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 0}  // → 10
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][] testCases = generateTestCases();
        int[] expected = {2, 2, 8, 1, 0, 1, 10};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.printf("\nTest %d: %s%n", i + 1, Arrays.toString(nums));
            
            int result1 = missingNumber(nums.clone());
            int result2 = missingNumberSum(nums.clone());
            int result3 = missingNumberSorting(nums.clone());
            int result4 = missingNumberHashSet(nums.clone());
            int result5 = missingNumberBooleanArray(nums.clone());
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Missing number: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1 (XOR): " + result1);
                System.out.println("  Method 2 (Sum): " + result2);
                System.out.println("  Method 3 (Sort): " + result3);
                System.out.println("  Method 4 (HashSet): " + result4);
                System.out.println("  Method 5 (Boolean): " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeXOR(nums);
                visualizeSum(nums);
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
        
        int n = 10000;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i;
        }
        nums[n - 1] = n; // Replace last element with n, missing n-1
        
        System.out.println("Test Setup: " + n + " elements");
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: XOR
        int[] nums1 = nums.clone();
        long start = System.currentTimeMillis();
        results[0] = missingNumber(nums1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Sum
        int[] nums2 = nums.clone();
        start = System.currentTimeMillis();
        results[1] = missingNumberSum(nums2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Sorting
        int[] nums3 = nums.clone();
        start = System.currentTimeMillis();
        results[2] = missingNumberSorting(nums3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: HashSet
        int[] nums4 = nums.clone();
        start = System.currentTimeMillis();
        results[3] = missingNumberHashSet(nums4);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Boolean Array
        int[] nums5 = nums.clone();
        start = System.currentTimeMillis();
        results[4] = missingNumberBooleanArray(nums5);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. XOR                    | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Sum                    | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. Sorting                | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. HashSet                | %9d | %6d%n", times[3], results[3]);
        System.out.printf("5. Boolean Array          | %9d | %6d%n", times[4], results[4]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3] && results[3] == results[4];
        System.out.println("\nAll methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. XOR and Sum are fastest (O(n), no extra allocations)");
        System.out.println("2. Sorting is slower due to O(n log n)");
        System.out.println("3. HashSet and Boolean Array use O(n) extra space");
        System.out.println("4. XOR avoids overflow issues that Sum might have");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. n = 1, missing 0:");
        int[] nums1 = {1};
        System.out.println("   Input: [1]");
        System.out.println("   Output: " + missingNumber(nums1));
        
        System.out.println("\n2. n = 1, missing 1:");
        int[] nums2 = {0};
        System.out.println("   Input: [0]");
        System.out.println("   Output: " + missingNumber(nums2));
        
        System.out.println("\n3. n = 2, missing middle:");
        int[] nums3 = {0, 2};
        System.out.println("   Input: [0,2]");
        System.out.println("   Output: " + missingNumber(nums3));
        
        System.out.println("\n4. Maximum n (10,000):");
        int[] nums4 = new int[10000];
        for (int i = 0; i < 9999; i++) nums4[i] = i;
        nums4[9999] = 10000;
        long start = System.currentTimeMillis();
        int result = missingNumber(nums4);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 10000 elements, missing 9999");
        System.out.println("   Output: " + result);
        System.out.println("   Time: " + time + "ms");
        
        System.out.println("\n5. Missing at beginning:");
        int[] nums5 = {1, 2, 3, 4, 5};
        System.out.println("   Input: [1,2,3,4,5]");
        System.out.println("   Output: " + missingNumber(nums5));
        
        System.out.println("\n6. Missing at end:");
        int[] nums6 = {0, 1, 2, 3, 4};
        System.out.println("   Input: [0,1,2,3,4]");
        System.out.println("   Output: " + missingNumber(nums6));
    }
    
    /**
     * Helper: Explain XOR approach
     */
    public void explainXOR() {
        System.out.println("\nXOR Approach Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nProperties of XOR:");
        System.out.println("  - a ^ a = 0 (a number XORed with itself is 0)");
        System.out.println("  - a ^ 0 = a (a number XORed with 0 is itself)");
        System.out.println("  - XOR is commutative and associative");
        
        System.out.println("\nHow it works:");
        System.out.println("  result = n ^ 0 ^ 1 ^ 2 ^ ... ^ n ^ nums[0] ^ nums[1] ^ ... ^ nums[n-1]");
        System.out.println("  Since the array contains all numbers from 0 to n except one,");
        System.out.println("  every number appears twice except the missing one.");
        System.out.println("  XOR cancels out pairs, leaving the missing number.");
        
        System.out.println("\nExample: nums = [3, 0, 1], n = 3");
        System.out.println("  result = 3 ^ 0 ^ 1 ^ 2 ^ 3 ^ 3 ^ 0 ^ 1");
        System.out.println("  = (3 ^ 3) ^ (0 ^ 0) ^ (1 ^ 1) ^ 2");
        System.out.println("  = 0 ^ 0 ^ 0 ^ 2");
        System.out.println("  = 2");
        
        System.out.println("\nWhy it's better than sum:");
        System.out.println("  - No overflow risk");
        System.out.println("  - Works for very large numbers");
        System.out.println("  - Same O(n) time, O(1) space");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What is the range of numbers? (0 to n)");
        System.out.println("   - Are all numbers unique? (Yes)");
        System.out.println("   - What's the maximum n? (10^4)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - Sort and check (O(n log n))");
        System.out.println("   - Hash set (O(n) time, O(n) space)");
        
        System.out.println("\n3. Propose optimal solutions:");
        System.out.println("   - Sum formula: O(n) time, O(1) space");
        System.out.println("   - XOR: O(n) time, O(1) space, no overflow");
        
        System.out.println("\n4. Explain XOR reasoning:");
        System.out.println("   - Discuss XOR properties");
        System.out.println("   - Show how pairs cancel out");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(n)");
        System.out.println("   - Space: O(1)");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - n = 1, missing 0 or 1");
        System.out.println("   - Missing number at beginning or end");
        System.out.println("   - Large n (10,000)");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Integer overflow in sum formula (use long)");
        System.out.println("   - Forgetting to include n in XOR");
        System.out.println("   - Off-by-one in loop bounds");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("268. Missing Number");
        System.out.println("==================");
        
        // Explain XOR approach
        solution.explainXOR();
        
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
        
        System.out.println("\nRecommended Implementation (XOR):");
        System.out.println("""
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int result = n;
        
        for (int i = 0; i < n; i++) {
            result ^= i ^ nums[i];
        }
        
        return result;
    }
}
            """);
        
        System.out.println("\nAlternative (Sum Formula):");
        System.out.println("""
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;
        
        for (int num : nums) {
            actualSum += num;
        }
        
        return expectedSum - actualSum;
    }
}
            """);
        
        System.out.println("\nAlternative (Sum with Long for Safety):");
        System.out.println("""
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        long expectedSum = (long) n * (n + 1) / 2;
        long actualSum = 0;
        
        for (int num : nums) {
            actualSum += num;
        }
        
        return (int) (expectedSum - actualSum);
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. XOR is the most elegant solution (no overflow)");
        System.out.println("2. Sum formula is simple but may overflow for large n");
        System.out.println("3. Time complexity: O(n) for all optimal solutions");
        System.out.println("4. Space complexity: O(1) for XOR and Sum");
        System.out.println("5. All numbers are unique and in range [0, n]");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - single pass through array");
        System.out.println("- Space: O(1) - constant extra space");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. What if there were two missing numbers?");
        System.out.println("2. What if numbers weren't guaranteed to be in range?");
        System.out.println("3. How would you handle duplicates?");
        System.out.println("4. What if the array was sorted? (Use binary search)");
        System.out.println("5. How would you find the missing number in a stream?");
    }
}
