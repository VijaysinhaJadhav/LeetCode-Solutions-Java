
## Solution.java

```java
/**
 * 300. Longest Increasing Subsequence
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 * 
 * Key Insights:
 * 1. Dynamic Programming: O(n²) - For each element, check all previous elements
 * 2. Binary Search: O(n log n) - Maintain active lists and use binary search
 * 3. Patience Sorting: The O(n log n) solution is based on patience sorting
 * 4. The subsequence doesn't need to be contiguous, just strictly increasing
 * 
 * Approach (Binary Search with Active Lists):
 * 1. Maintain an array 'tails' where tails[i] = smallest tail value for LIS of length i+1
 * 2. For each number, use binary search to find its position in tails array
 * 3. If number > all tails, append it (increasing LIS length)
 * 4. Otherwise, replace first tail that is >= current number
 * 
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 * 
 * Tags: Array, Binary Search, Dynamic Programming
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Binary Search with Active Lists (Recommended)
     * O(n log n) time, O(n) space
     * Based on patience sorting algorithm
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int[] tails = new int[nums.length];
        int size = 0;
        
        for (int num : nums) {
            int left = 0;
            int right = size;
            
            // Binary search to find the position to replace/insert
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (tails[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            
            tails[left] = num;
            if (left == size) {
                size++;
            }
        }
        
        return size;
    }
    
    /**
     * Approach 2: Dynamic Programming (Basic)
     * O(n²) time, O(n) space
     * For each element, check all previous elements
     */
    public int lengthOfLISDP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Each element is a subsequence of length 1
        
        int maxLength = 1;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }
        
        return maxLength;
    }
    
    /**
     * Approach 3: Dynamic Programming with Reconstruction
     * O(n²) time, O(n) space
     * Also reconstructs the actual LIS
     */
    public int lengthOfLISWithReconstruction(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        int[] dp = new int[n];
        int[] prev = new int[n]; // To reconstruct the sequence
        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1);
        
        int maxLength = 1;
        int maxIndex = 0;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > maxLength) {
                maxLength = dp[i];
                maxIndex = i;
            }
        }
        
        // Reconstruct the LIS
        List<Integer> lis = new ArrayList<>();
        int current = maxIndex;
        while (current != -1) {
            lis.add(nums[current]);
            current = prev[current];
        }
        Collections.reverse(lis);
        
        System.out.println("Longest Increasing Subsequence: " + lis);
        return maxLength;
    }
    
    /**
     * Approach 4: Binary Search with List (More Intuitive)
     * O(n log n) time, O(n) space
     * Uses ArrayList instead of array for clarity
     */
    public int lengthOfLISBinarySearchList(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        List<Integer> tails = new ArrayList<>();
        
        for (int num : nums) {
            if (tails.isEmpty() || num > tails.get(tails.size() - 1)) {
                tails.add(num);
            } else {
                // Binary search to find the position to replace
                int pos = binarySearch(tails, num);
                tails.set(pos, num);
            }
        }
        
        return tails.size();
    }
    
    private int binarySearch(List<Integer> list, int target) {
        int left = 0;
        int right = list.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) == target) {
                return mid;
            } else if (list.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return left;
    }
    
    /**
     * Approach 5: Segment Tree (Advanced)
     * O(n log n) time, O(n) space
     * Uses segment tree for range maximum queries
     */
    public int lengthOfLISSegmentTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // Coordinate compression for negative numbers and large ranges
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        Map<Integer, Integer> rank = new HashMap<>();
        int index = 0;
        for (int i = 0; i < sorted.length; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                rank.put(sorted[i], index++);
            }
        }
        
        SegmentTree st = new SegmentTree(index);
        int maxLen = 0;
        
        for (int num : nums) {
            int pos = rank.get(num);
            // Query maximum LIS length for numbers < current number
            int maxPrev = st.query(0, pos - 1);
            int currentLen = maxPrev + 1;
            maxLen = Math.max(maxLen, currentLen);
            st.update(pos, currentLen);
        }
        
        return maxLen;
    }
    
    class SegmentTree {
        private int[] tree;
        private int n;
        
        public SegmentTree(int size) {
            n = size;
            tree = new int[4 * n];
        }
        
        public void update(int index, int value) {
            update(0, 0, n - 1, index, value);
        }
        
        private void update(int treeIndex, int left, int right, int index, int value) {
            if (left == right) {
                tree[treeIndex] = Math.max(tree[treeIndex], value);
                return;
            }
            
            int mid = left + (right - left) / 2;
            if (index <= mid) {
                update(2 * treeIndex + 1, left, mid, index, value);
            } else {
                update(2 * treeIndex + 2, mid + 1, right, index, value);
            }
            
            tree[treeIndex] = Math.max(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
        }
        
        public int query(int queryLeft, int queryRight) {
            if (queryLeft > queryRight) return 0;
            return query(0, 0, n - 1, queryLeft, queryRight);
        }
        
        private int query(int treeIndex, int left, int right, int queryLeft, int queryRight) {
            if (queryLeft > right || queryRight < left) return 0;
            if (queryLeft <= left && queryRight >= right) return tree[treeIndex];
            
            int mid = left + (right - left) / 2;
            int leftMax = query(2 * treeIndex + 1, left, mid, queryLeft, queryRight);
            int rightMax = query(2 * treeIndex + 2, mid + 1, right, queryLeft, queryRight);
            
            return Math.max(leftMax, rightMax);
        }
    }
    
    /**
     * Approach 6: Recursive with Memoization
     * O(n²) time, O(n²) space
     * Top-down recursive approach
     */
    public int lengthOfLISRecursive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int[][] memo = new int[nums.length][nums.length + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        
        return lisHelper(nums, -1, 0, memo);
    }
    
    private int lisHelper(int[] nums, int prevIndex, int currIndex, int[][] memo) {
        if (currIndex == nums.length) {
            return 0;
        }
        
        if (memo[currIndex][prevIndex + 1] != -1) {
            return memo[currIndex][prevIndex + 1];
        }
        
        int taken = 0;
        if (prevIndex == -1 || nums[currIndex] > nums[prevIndex]) {
            taken = 1 + lisHelper(nums, currIndex, currIndex + 1, memo);
        }
        
        int notTaken = lisHelper(nums, prevIndex, currIndex + 1, memo);
        
        memo[currIndex][prevIndex + 1] = Math.max(taken, notTaken);
        return memo[currIndex][prevIndex + 1];
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    public void visualizeBinarySearchLIS(int[] nums, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Input: " + Arrays.toString(nums));
        
        if ("Binary Search".equals(approach)) {
            visualizeBinarySearchProcess(nums);
        } else if ("Dynamic Programming".equals(approach)) {
            visualizeDPProcess(nums);
        }
    }
    
    private void visualizeBinarySearchProcess(int[] nums) {
        System.out.println("\nBinary Search with Active Lists Process:");
        System.out.println("Step | Number | Tails Array | Action");
        System.out.println("-----|--------|-------------|--------");
        
        List<Integer> tails = new ArrayList<>();
        int step = 1;
        
        for (int num : nums) {
            System.out.printf("%4d | %6d | %11s | ", step++, num, tails.toString());
            
            if (tails.isEmpty() || num > tails.get(tails.size() - 1)) {
                tails.add(num);
                System.out.println("Append (new longest subsequence)");
            } else {
                int pos = binarySearch(tails, num);
                int oldVal = tails.get(pos);
                tails.set(pos, num);
                System.out.printf("Replace tails[%d] = %d with %d%n", pos, oldVal, num);
            }
        }
        
        System.out.println("Final LIS length: " + tails.size());
        System.out.println("Final tails array: " + tails);
    }
    
    private void visualizeDPProcess(int[] nums) {
        System.out.println("\nDynamic Programming Process:");
        System.out.println("Index | Value | DP Array | Explanation");
        System.out.println("------|-------|----------|------------");
        
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        
        for (int i = 0; i < n; i++) {
            System.out.printf("%5d | %5d | ", i, nums[i]);
            
            // Calculate DP value
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            
            // Print current DP array
            System.out.print(Arrays.toString(Arrays.copyOf(dp, i + 1)));
            System.out.print(" | ");
            
            // Explanation
            if (i == 0) {
                System.out.println("Base case: length = 1");
            } else {
                List<Integer> contributors = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    if (nums[i] > nums[j] && dp[j] + 1 == dp[i]) {
                        contributors.add(j);
                    }
                }
                if (contributors.isEmpty()) {
                    System.out.println("No smaller elements before, length = 1");
                } else {
                    System.out.printf("Can extend from indices %s, length = %d%n", 
                        contributors.toString(), dp[i]);
                }
            }
        }
        
        int maxLength = Arrays.stream(dp).max().getAsInt();
        System.out.println("Final maximum length: " + maxLength);
    }
    
    /**
     * Helper method to generate test cases
     */
    public int[] generateTestArray(int size, String type) {
        Random random = new Random(42);
        int[] arr = new int[size];
        
        switch (type) {
            case "random":
                for (int i = 0; i < size; i++) {
                    arr[i] = random.nextInt(100);
                }
                break;
            case "increasing":
                for (int i = 0; i < size; i++) {
                    arr[i] = i;
                }
                break;
            case "decreasing":
                for (int i = 0; i < size; i++) {
                    arr[i] = size - i;
                }
                break;
            case "all_same":
                Arrays.fill(arr, 5);
                break;
        }
        
        return arr;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Longest Increasing Subsequence:");
        System.out.println("======================================\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: Standard example [10,9,2,5,3,7,101,18]");
        int[] nums1 = {10,9,2,5,3,7,101,18};
        int expected1 = 4;
        testImplementation(solution, nums1, expected1, "Binary Search");
        
        // Test case 2: Another example
        System.out.println("\nTest 2: [0,1,0,3,2,3]");
        int[] nums2 = {0,1,0,3,2,3};
        int expected2 = 4;
        testImplementation(solution, nums2, expected2, "Binary Search");
        
        // Test case 3: All same elements
        System.out.println("\nTest 3: All same elements [7,7,7,7,7,7,7]");
        int[] nums3 = {7,7,7,7,7,7,7};
        int expected3 = 1;
        testImplementation(solution, nums3, expected3, "Binary Search");
        
        // Test case 4: Single element
        System.out.println("\nTest 4: Single element [5]");
        int[] nums4 = {5};
        int expected4 = 1;
        testImplementation(solution, nums4, expected4, "Binary Search");
        
        // Test case 5: Strictly increasing
        System.out.println("\nTest 5: Strictly increasing [1,2,3,4,5]");
        int[] nums5 = {1,2,3,4,5};
        int expected5 = 5;
        testImplementation(solution, nums5, expected5, "Binary Search");
        
        // Test case 6: Strictly decreasing
        System.out.println("\nTest 6: Strictly decreasing [5,4,3,2,1]");
        int[] nums6 = {5,4,3,2,1};
        int expected6 = 1;
        testImplementation(solution, nums6, expected6, "Binary Search");
        
        // Test case 7: Negative numbers
        System.out.println("\nTest 7: Negative numbers [-1,-2,-3,-4,-5]");
        int[] nums7 = {-1,-2,-3,-4,-5};
        int expected7 = 1;
        testImplementation(solution, nums7, expected7, "Binary Search");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: BINARY SEARCH APPROACH");
        System.out.println("=".repeat(70));
        
        explainBinarySearchApproach(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, int[] nums, 
                                         int expected, String approach) {
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
        
        long startTime = System.nanoTime();
        int result = 0;
        switch (approach) {
            case "Binary Search":
                result = solution.lengthOfLIS(numsCopy);
                break;
            case "Dynamic Programming":
                result = solution.lengthOfLISDP(numsCopy);
                break;
            case "Binary Search List":
                result = solution.lengthOfLISBinarySearchList(numsCopy);
                break;
            case "Recursive":
                result = solution.lengthOfLISRecursive(numsCopy);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        boolean passed = (result == expected);
        System.out.printf("%s: Expected=%d, Got=%d, Time=%,d ns - %s%n",
                approach, expected, result, time, (passed ? "PASSED" : "FAILED"));
        
        if (!passed) {
            System.out.println("  Input: " + Arrays.toString(nums));
        }
        
        // Visualization for interesting cases
        if (passed && nums.length <= 10) {
            solution.visualizeBinarySearchLIS(nums, approach);
        }
    }
    
    private static void comparePerformance(Solution solution) {
        int size = 1000;
        int[] largeArray = solution.generateTestArray(size, "random");
        
        System.out.println("Performance test with " + size + " elements:");
        
        // Test Binary Search
        int[] arr1 = Arrays.copyOf(largeArray, largeArray.length);
        long startTime = System.nanoTime();
        solution.lengthOfLIS(arr1);
        long time1 = System.nanoTime() - startTime;
        
        // Test Dynamic Programming
        int[] arr2 = Arrays.copyOf(largeArray, largeArray.length);
        startTime = System.nanoTime();
        solution.lengthOfLISDP(arr2);
        long time2 = System.nanoTime() - startTime;
        
        // Test Binary Search List
        int[] arr3 = Arrays.copyOf(largeArray, largeArray.length);
        startTime = System.nanoTime();
        solution.lengthOfLISBinarySearchList(arr3);
        long time3 = System.nanoTime() - startTime;
        
        // Test Segment Tree (skip for very large due to setup cost)
        long time4 = 0;
        if (size <= 1000) {
            int[] arr4 = Arrays.copyOf(largeArray, largeArray.length);
            startTime = System.nanoTime();
            solution.lengthOfLISSegmentTree(arr4);
            time4 = System.nanoTime() - startTime;
        }
        
        // Skip recursive for large arrays (too slow)
        
        System.out.printf("Binary Search:       %,12d ns%n", time1);
        System.out.printf("Dynamic Programming: %,12d ns%n", time2);
        System.out.printf("Binary Search List:  %,12d ns%n", time3);
        if (size <= 1000) {
            System.out.printf("Segment Tree:        %,12d ns%n", time4);
        } else {
            System.out.println("Segment Tree:        (skipped - setup cost)");
        }
        System.out.println("Recursive:           (skipped - too slow)");
    }
    
    private static void explainBinarySearchApproach(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("The binary search approach maintains an array 'tails' where:");
        System.out.println("  tails[i] = smallest tail value for all increasing subsequences of length i+1");
        System.out.println();
        System.out.println("This approach is based on the observation that we want the tail values");
        System.out.println("to be as small as possible to allow more numbers to extend the sequences.");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Initialize an empty tails array");
        System.out.println("2. For each number in the input array:");
        System.out.println("   a. If number > last element in tails, append it");
        System.out.println("   b. Else, use binary search to find the first element >= number");
        System.out.println("   c. Replace that element with the current number");
        System.out.println("3. The length of tails is the LIS length");
        
        System.out.println("\nWhy it works:");
        System.out.println("- By replacing larger tail values with smaller ones, we maintain");
        System.out.println("  the potential for longer subsequences in the future");
        System.out.println("- The tails array always remains sorted, enabling binary search");
        System.out.println("- The length of tails represents the maximum LIS length found so far");
        
        System.out.println("\nExample Walkthrough: [10,9,2,5,3,7,101,18]");
        int[] example = {10,9,2,5,3,7,101,18};
        solution.visualizeBinarySearchLIS(example, "Binary Search");
        
        System.out.println("\nTime Complexity: O(n log n)");
        System.out.println("  - n elements processed");
        System.out.println("  - Binary search on tails array: O(log n) per element");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - tails array storage");
    }
    
    private static void checkAllImplementations(Solution solution) {
        Object[][][] testCases = {
            {{10,9,2,5,3,7,101,18}, {4}},  // Standard
            {{0,1,0,3,2,3}, {4}},          // Another
            {{7,7,7,7,7,7,7}, {1}},        // All same
            {{5}, {1}},                    // Single
            {{1,2,3,4,5}, {5}},           // Increasing
            {{5,4,3,2,1}, {1}},           // Decreasing
            {{1,3,6,7,9,4,10,5,6}, {6}}  // Complex
        };
        
        int[] expected = {4, 4, 1, 1, 5, 1, 6};
        
        String[] methods = {
            "Binary Search",
            "Dynamic Programming", 
            "Binary Search List",
            "Segment Tree",
            "Recursive",
            "With Reconstruction"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = (int[]) testCases[i][0];
            int expectedVal = ((int[]) testCases[i][1])[0];
            
            System.out.printf("\nTest case %d: %s (expected: %d)%n",
                    i + 1, Arrays.toString(nums), expectedVal);
            
            int[] results = new int[methods.length];
            results[0] = solution.lengthOfLIS(nums.clone());
            results[1] = solution.lengthOfLISDP(nums.clone());
            results[2] = solution.lengthOfLISBinarySearchList(nums.clone());
            results[3] = solution.lengthOfLISSegmentTree(nums.clone());
            results[4] = solution.lengthOfLISRecursive(nums.clone());
            results[5] = solution.lengthOfLISWithReconstruction(nums.clone());
            
            boolean caseConsistent = true;
            for (int j = 0; j < methods.length; j++) {
                boolean correct = (results[j] == expectedVal);
                System.out.printf("  %-20s: %d %s%n", methods[j], results[j],
                        correct ? "✓" : "✗ (expected " + expectedVal + ")");
                if (!correct) {
                    caseConsistent = false;
                    allConsistent = false;
                }
            }
            
            if (!caseConsistent) {
                System.out.println("  INCONSISTENT RESULTS!");
            }
        }
        
        System.out.println("\nOverall consistency: " + (allConsistent ? "ALL PASSED ✓" : "SOME FAILED ✗"));
        
        // Algorithm comparison table
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON SUMMARY");
        System.out.println("=".repeat(70));
        
        printAlgorithmComparison();
    }
    
    private static void printAlgorithmComparison() {
        System.out.println("\n1. BINARY SEARCH WITH ACTIVE LISTS (RECOMMENDED):");
        System.out.println("   Time: O(n log n) - Optimal");
        System.out.println("   Space: O(n) - Tails array storage");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Elegant and efficient");
        System.out.println("     - Based on patience sorting");
        System.out.println("   Cons:");
        System.out.println("     - Doesn't reconstruct the actual sequence");
        System.out.println("     - Can be tricky to understand initially");
        System.out.println("   Use when: Optimal performance needed, only length required");
        
        System.out.println("\n2. DYNAMIC PROGRAMMING (BASIC):");
        System.out.println("   Time: O(n²) - Quadratic");
        System.out.println("   Space: O(n) - DP array");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and easy to understand");
        System.out.println("     - Can be extended to reconstruct sequence");
        System.out.println("     - Good for small inputs");
        System.out.println("   Cons:");
        System.out.println("     - Too slow for large inputs (n > 1000)");
        System.out.println("   Use when: Learning, small arrays, need sequence reconstruction");
        
        System.out.println("\n3. BINARY SEARCH WITH LIST:");
        System.out.println("   Time: O(n log n) - Optimal");
        System.out.println("   Space: O(n) - ArrayList storage");
        System.out.println("   Pros:");
        System.out.println("     - Same complexity as array version");
        System.out.println("     - More intuitive using ArrayList");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more overhead than array");
        System.out.println("   Use when: Prefer List interface over arrays");
        
        System.out.println("\n4. SEGMENT TREE:");
        System.out.println("   Time: O(n log n) - Optimal");
        System.out.println("   Space: O(n) - Segment tree storage");
        System.out.println("   Pros:");
        System.out.println("     - Handles complex constraints well");
        System.out.println("     - Good theoretical foundation");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Overkill for basic LIS");
        System.out.println("   Use when: Learning segment trees, complex variations");
        
        System.out.println("\n5. RECURSIVE WITH MEMOIZATION:");
        System.out.println("   Time: O(n²) - Quadratic");
        System.out.println("   Space: O(n²) - Memoization table");
        System.out.println("   Pros:");
        System.out.println("     - Top-down approach is natural");
        System.out.println("     - Easy to understand recurrence");
        System.out.println("   Cons:");
        System.out.println("     - Worst time and space complexity");
        System.out.println("     - Stack overflow risk for large n");
        System.out.println("   Use when: Teaching recursive thinking");
        
        System.out.println("\n6. WITH RECONSTRUCTION:");
        System.out.println("   Time: O(n²) - Quadratic");
        System.out.println("   Space: O(n) - Additional prev array");
        System.out.println("   Pros:");
        System.out.println("     - Returns actual LIS sequence");
        System.out.println("     - Useful for debugging and visualization");
        System.out.println("   Cons:");
        System.out.println("     - Slower than binary search");
        System.out.println("   Use when: Need the actual subsequence, not just length");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with DP solution to show understanding of the problem");
        System.out.println("2. Then optimize to binary search approach for O(n log n)");
        System.out.println("3. Explain the tails array concept clearly");
        System.out.println("4. Mention that binary search finds length but not sequence");
        System.out.println("5. Discuss time/space complexity trade-offs");
        System.out.println("6. Handle edge cases: all same, single element, duplicates");
        System.out.println("=".repeat(70));
    }
}
