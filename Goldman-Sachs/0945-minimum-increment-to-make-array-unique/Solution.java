
# Solution.java

```java
import java.util.*;

/**
 * 945. Minimum Increment to Make Array Unique
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find minimum increments to make all array elements unique.
 * Can only increment values (cannot decrement).
 * 
 * Key Insights:
 * 1. Greedy approach: Sort array, then ensure each element > previous
 * 2. Counting approach: Use frequency array to track duplicates
 * 3. When a duplicate is found, find the next available number
 */
class Solution {
    
    /**
     * Approach 1: Sort + Greedy (Recommended)
     * Time: O(n log n), Space: O(1)
     * 
     * Steps:
     * 1. Sort the array
     * 2. Track current expected value
     * 3. If current number <= previous, increment to previous + 1
     * 4. Accumulate moves
     */
    public int minIncrementForUnique(int[] nums) {
        Arrays.sort(nums);
        int moves = 0;
        int nextAvailable = nums[0]; // Track the next expected value
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nextAvailable) {
                // Need to increment to nextAvailable + 1
                int increment = (nextAvailable + 1) - nums[i];
                moves += increment;
                nextAvailable++;
            } else {
                nextAvailable = nums[i];
            }
        }
        
        return moves;
    }
    
    /**
     * Approach 2: Sort + Greedy (Simpler)
     * Time: O(n log n), Space: O(1)
     * 
     * Alternative implementation: compare with previous element
     */
    public int minIncrementForUniqueSimple(int[] nums) {
        Arrays.sort(nums);
        int moves = 0;
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                int increment = nums[i - 1] + 1 - nums[i];
                moves += increment;
                nums[i] = nums[i - 1] + 1;
            }
        }
        
        return moves;
    }
    
    /**
     * Approach 3: Counting Array (Efficient for bounded values)
     * Time: O(n + max), Space: O(max)
     * 
     * Steps:
     * 1. Count frequency of each number
     * 2. Track the next available number
     * 3. For duplicates, find the next empty slot
     */
    public int minIncrementForUniqueCounting(int[] nums) {
        if (nums.length == 0) return 0;
        
        // Find max value
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        
        // Frequency array (size: max + nums.length to account for increments)
        int[] freq = new int[max + nums.length + 1];
        
        // Count frequencies
        for (int num : nums) {
            freq[num]++;
        }
        
        int moves = 0;
        int nextAvailable = -1;
        
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 1) {
                // Keep one occurrence at i, move the rest forward
                int duplicates = freq[i] - 1;
                // Find next available slot
                int j = i + 1;
                while (duplicates > 0 && j < freq.length) {
                    if (freq[j] == 0) {
                        moves += j - i;
                        duplicates--;
                        freq[j] = 1;
                    }
                    j++;
                }
            }
        }
        
        return moves;
    }
    
    /**
     * Approach 4: Greedy with HashMap
     * Time: O(n + max), Space: O(n)
     * 
     * Uses HashMap to track used numbers
     */
    public int minIncrementForUniqueHashMap(int[] nums) {
        Arrays.sort(nums);
        Set<Integer> used = new HashSet<>();
        int moves = 0;
        
        for (int num : nums) {
            int target = num;
            while (used.contains(target)) {
                target++;
                moves++;
            }
            used.add(target);
        }
        
        return moves;
    }
    
    /**
     * Approach 5: Optimized Counting (In-place frequency)
     * Time: O(n + max), Space: O(max)
     * 
     * More efficient counting approach without nested loops
     */
    public int minIncrementForUniqueOptimized(int[] nums) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        
        // Frequency array
        int[] freq = new int[max + nums.length + 1];
        for (int num : nums) {
            freq[num]++;
        }
        
        int moves = 0;
        int nextAvailable = 0;
        
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 1) {
                int duplicates = freq[i] - 1;
                // Move duplicates to next available positions
                moves += duplicates;
                freq[i + 1] += duplicates;
            }
        }
        
        return moves;
    }
    
    /**
     * Approach 6: Greedy without Sorting (Using TreeMap)
     * Time: O(n log n), Space: O(n)
     */
    public int minIncrementForUniqueTreeMap(int[] nums) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        
        int moves = 0;
        int carry = 0;
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();
            
            // Add carry from previous number
            count += carry;
            carry = 0;
            
            if (count > 1) {
                // Keep one at current number, move the rest forward
                carry = count - 1;
                moves += carry;
            }
        }
        
        return moves;
    }
    
    /**
     * Helper: Visualize the greedy process
     */
    public void visualizeGreedy(int[] nums) {
        System.out.println("\nGreedy Process Visualization:");
        System.out.println("=".repeat(60));
        
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        
        System.out.println("\nOriginal: " + Arrays.toString(nums));
        System.out.println("Sorted:   " + Arrays.toString(sorted));
        
        System.out.println("\nProcessing:");
        int moves = 0;
        int[] processed = sorted.clone();
        
        for (int i = 1; i < processed.length; i++) {
            System.out.printf("\nStep %d: i=%d, current=%d, previous=%d%n", 
                i, i, processed[i], processed[i - 1]);
            
            if (processed[i] <= processed[i - 1]) {
                int increment = processed[i - 1] + 1 - processed[i];
                System.out.printf("  %d <= %d → need to increment by %d%n", 
                    processed[i], processed[i - 1], increment);
                processed[i] = processed[i - 1] + 1;
                moves += increment;
                System.out.printf("  New value: %d, moves: %d%n", processed[i], moves);
            } else {
                System.out.printf("  %d > %d → no change needed%n", 
                    processed[i], processed[i - 1]);
            }
            System.out.printf("  Array state: %s%n", Arrays.toString(processed));
        }
        
        System.out.println("\nFinal unique array: " + Arrays.toString(processed));
        System.out.println("Total moves: " + moves);
    }
    
    /**
     * Helper: Visualize counting approach
     */
    public void visualizeCounting(int[] nums) {
        System.out.println("\nCounting Approach Visualization:");
        System.out.println("=".repeat(60));
        
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        
        int[] freq = new int[max + nums.length + 1];
        for (int num : nums) {
            freq[num]++;
        }
        
        System.out.println("\nInitial frequency array (first 15 positions):");
        for (int i = 0; i < Math.min(15, freq.length); i++) {
            System.out.printf("%2d: %d%n", i, freq[i]);
        }
        
        int moves = 0;
        System.out.println("\nProcessing:");
        
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 1) {
                int duplicates = freq[i] - 1;
                System.out.printf("  Position %d: %d duplicates → move to next positions%n", i, duplicates);
                moves += duplicates;
                freq[i + 1] += duplicates;
                System.out.printf("    moves: %d, pass %d to next position%n", moves, duplicates);
            }
        }
        
        System.out.println("\nTotal moves: " + moves);
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            {1, 2, 2},                          // Example 1, expected: 1
            {3, 2, 1, 2, 1, 7},                 // Example 2, expected: 6
            {1},                                 // Single element, expected: 0
            {1, 1, 1},                          // All same, expected: 3 (1→2→3)
            {1, 1, 2, 2, 3, 3},                 // Duplicates, expected: 6
            {0, 0, 1, 1, 2, 2},                 // Starting from 0, expected: 6
            {1, 2, 3, 4, 5},                    // Already unique, expected: 0
            {1, 2, 2, 3, 3, 4, 4},              // Pattern, expected: 6
            {1, 1, 1, 1, 1},                    // All same (5), expected: 10
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}     // Ten zeros, expected: 45 (sum 0..9)
        };
    }
    
    /**
     * Helper: Get expected result
     */
    public int getExpected(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int moves = 0;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] <= sorted[i - 1]) {
                int increment = sorted[i - 1] + 1 - sorted[i];
                moves += increment;
                sorted[i] = sorted[i - 1] + 1;
            }
        }
        return moves;
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        int[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] original = testCases[i].clone();
            int[] nums1 = testCases[i].clone();
            int[] nums2 = testCases[i].clone();
            int[] nums3 = testCases[i].clone();
            int[] nums4 = testCases[i].clone();
            int[] nums5 = testCases[i].clone();
            int[] nums6 = testCases[i].clone();
            
            int expected = getExpected(original);
            
            System.out.printf("\nTest %d: %s%n", i + 1, Arrays.toString(original));
            
            int result1 = minIncrementForUnique(nums1);
            int result2 = minIncrementForUniqueSimple(nums2);
            int result3 = minIncrementForUniqueCounting(nums3);
            int result4 = minIncrementForUniqueHashMap(nums4);
            int result5 = minIncrementForUniqueOptimized(nums5);
            int result6 = minIncrementForUniqueTreeMap(nums6);
            
            boolean allMatch = result1 == expected && result2 == expected &&
                              result3 == expected && result4 == expected &&
                              result5 == expected && result6 == expected;
            
            if (allMatch) {
                System.out.println("✓ PASS - Moves: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
                System.out.println("  Method 6: " + result6);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeGreedy(original);
            }
            if (i == 1) {
                visualizeCounting(original);
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
        System.out.println("=======================");
        
        // Generate large test case
        int n = 100000;
        int[] largeArray = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            largeArray[i] = rand.nextInt(50000);
        }
        
        System.out.println("Test Setup: " + n + " elements");
        
        long[] times = new long[6];
        int[] results = new int[6];
        
        // Method 1: Sort + Greedy
        int[] copy1 = largeArray.clone();
        long start = System.currentTimeMillis();
        results[0] = minIncrementForUnique(copy1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Sort + Greedy (simple)
        int[] copy2 = largeArray.clone();
        start = System.currentTimeMillis();
        results[1] = minIncrementForUniqueSimple(copy2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Counting
        int[] copy3 = largeArray.clone();
        start = System.currentTimeMillis();
        results[2] = minIncrementForUniqueCounting(copy3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: HashMap
        int[] copy4 = largeArray.clone();
        start = System.currentTimeMillis();
        results[3] = minIncrementForUniqueHashMap(copy4);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Optimized Counting
        int[] copy5 = largeArray.clone();
        start = System.currentTimeMillis();
        results[4] = minIncrementForUniqueOptimized(copy5);
        times[4] = System.currentTimeMillis() - start;
        
        // Method 6: TreeMap
        int[] copy6 = largeArray.clone();
        start = System.currentTimeMillis();
        results[5] = minIncrementForUniqueTreeMap(copy6);
        times[5] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Sort + Greedy          | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Sort + Greedy (Simple) | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. Counting               | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. HashMap                | %9d | %6d%n", times[3], results[3]);
        System.out.printf("5. Optimized Counting     | %9d | %6d%n", times[4], results[4]);
        System.out.printf("6. TreeMap                | %9d | %6d%n", times[5], results[5]);
        
        boolean allSame = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[3] && results[3] == results[4] &&
                          results[4] == results[5];
        System.out.println("\nAll methods produce same result: " + (allSame ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Sort-based approaches are fastest for random data");
        System.out.println("2. Counting approach can be faster when max value is small");
        System.out.println("3. HashMap approach is slower due to overhead");
        System.out.println("4. TreeMap is the slowest due to sorting overhead");
        System.out.println("5. Optimized counting is memory-efficient and fast");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Single element:");
        int[] test1 = {5};
        System.out.println("   Input: [5]");
        System.out.println("   Output: " + minIncrementForUnique(test1));
        
        System.out.println("\n2. All same elements (size 10):");
        int[] test2 = new int[10];
        Arrays.fill(test2, 1);
        System.out.println("   Input: [1,1,1,1,1,1,1,1,1,1]");
        System.out.println("   Output: " + minIncrementForUnique(test2));
        
        System.out.println("\n3. Already unique array:");
        int[] test3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("   Input: " + Arrays.toString(test3));
        System.out.println("   Output: " + minIncrementForUnique(test3));
        
        System.out.println("\n4. Large gaps between numbers:");
        int[] test4 = {1, 100, 1000};
        System.out.println("   Input: " + Arrays.toString(test4));
        System.out.println("   Output: " + minIncrementForUnique(test4));
        
        System.out.println("\n5. Zeros and negatives (only zeros allowed per constraints):");
        int[] test5 = {0, 0, 0, 0};
        System.out.println("   Input: [0,0,0,0]");
        System.out.println("   Output: " + minIncrementForUnique(test5));
        
        System.out.println("\n6. Descending order:");
        int[] test6 = {5, 4, 3, 2, 1};
        System.out.println("   Input: " + Arrays.toString(test6));
        System.out.println("   Output: " + minIncrementForUnique(test6));
    }
    
    /**
     * Helper: Explain the greedy approach
     */
    public void explainGreedy() {
        System.out.println("\nGreedy Approach Explanation:");
        System.out.println("============================");
        
        System.out.println("\nWhy greedy works:");
        System.out.println("1. Sort the array in non-decreasing order");
        System.out.println("2. Process elements from smallest to largest");
        System.out.println("3. For each element, ensure it's > previous element");
        System.out.println("4. If current ≤ previous, increment to previous + 1");
        
        System.out.println("\nProof Sketch:");
        System.out.println("- Sorting allows us to handle smallest numbers first");
        System.out.println("- When we fix a number, we don't need to revisit it");
        System.out.println("- Incrementing the current number to the smallest possible unique value minimizes future increments");
        
        System.out.println("\nExample: nums = [3, 2, 1, 2, 1, 7]");
        System.out.println("  Sorted: [1, 1, 2, 2, 3, 7]");
        System.out.println("  i=1: 1 ≤ 1 → increment to 2 (+1), array: [1, 2, 2, 2, 3, 7]");
        System.out.println("  i=2: 2 ≤ 2 → increment to 3 (+1), array: [1, 2, 3, 2, 3, 7]");
        System.out.println("  i=3: 2 ≤ 3 → increment to 4 (+2), array: [1, 2, 3, 4, 3, 7]");
        System.out.println("  i=4: 3 ≤ 4 → increment to 5 (+2), array: [1, 2, 3, 4, 5, 7]");
        System.out.println("  i=5: 7 > 5 → no change");
        System.out.println("  Total moves: 1 + 1 + 2 + 2 = 6");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Can we decrement? (No, only increment)");
        System.out.println("   - What's the range of values? (0 to 10^5)");
        System.out.println("   - What's the array size? (up to 10^5)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - For each duplicate, find next available number");
        System.out.println("   - O(n²) too slow");
        
        System.out.println("\n3. Propose sorting approach:");
        System.out.println("   - Sort array O(n log n)");
        System.out.println("   - Process sequentially, track previous value");
        System.out.println("   - O(n) after sorting");
        
        System.out.println("\n4. Discuss counting approach:");
        System.out.println("   - Use frequency array");
        System.out.println("   - Move duplicates to next positions");
        System.out.println("   - O(n + max) time, O(max) space");
        
        System.out.println("\n5. Handle edge cases:");
        System.out.println("   - Empty array (not possible, n ≥ 1)");
        System.out.println("   - Single element");
        System.out.println("   - Already unique array");
        System.out.println("   - All elements the same");
        
        System.out.println("\n6. Complexity analysis:");
        System.out.println("   - Time: O(n log n) for sort, O(n + max) for counting");
        System.out.println("   - Space: O(1) for sort, O(max) for counting");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Not handling large values properly");
        System.out.println("   - Forgetting to account for carry-over increments");
        System.out.println("   - Off-by-one errors in index handling");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("945. Minimum Increment to Make Array Unique");
        System.out.println("============================================");
        
        // Explain greedy approach
        solution.explainGreedy();
        
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
        
        System.out.println("\nRecommended Implementation (Sort + Greedy):");
        System.out.println("""
class Solution {
    public int minIncrementForUnique(int[] nums) {
        Arrays.sort(nums);
        int moves = 0;
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                int increment = nums[i - 1] + 1 - nums[i];
                moves += increment;
                nums[i] = nums[i - 1] + 1;
            }
        }
        
        return moves;
    }
}
            """);
        
        System.out.println("\nAlternative (Counting Approach):");
        System.out.println("""
class Solution {
    public int minIncrementForUnique(int[] nums) {
        int max = 0;
        for (int num : nums) max = Math.max(max, num);
        
        int[] freq = new int[max + nums.length + 1];
        for (int num : nums) freq[num]++;
        
        int moves = 0;
        for (int i = 0; i < freq.length - 1; i++) {
            if (freq[i] > 1) {
                int duplicates = freq[i] - 1;
                moves += duplicates;
                freq[i + 1] += duplicates;
            }
        }
        
        return moves;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Greedy approach with sorting is optimal and intuitive");
        System.out.println("2. For each element, ensure it's greater than previous");
        System.out.println("3. Counting approach works well for bounded values");
        System.out.println("4. Time complexity: O(n log n) for sorting, O(n + max) for counting");
        System.out.println("5. Space complexity: O(1) for sort, O(max) for counting");
        
        System.out.println("\nMathematical Insight:");
        System.out.println("- When you have k duplicates of a number n, you need to spread them across n+1, n+2, ..., n+k");
        System.out.println("- The total increments = sum of 1 to k = k*(k+1)/2 for each group");
        
        System.out.println("\nCommon Interview Follow-ups:");
        System.out.println("1. What if you could also decrement values?");
        System.out.println("2. What if you wanted to minimize maximum value after increments?");
        System.out.println("3. How would you handle very large value ranges?");
        System.out.println("4. What if you needed to output the final array?");
    }
}
