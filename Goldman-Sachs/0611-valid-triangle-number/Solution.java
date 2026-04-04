
# Solution.java

```java
import java.util.*;

/**
 * 611. Valid Triangle Number
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Count number of triplets that can form a valid triangle.
 * Triangle condition: a + b > c for sorted sides a ≤ b ≤ c.
 * 
 * Key Insights:
 * 1. Sort array to fix largest side as the rightmost element
 * 2. Use two pointers to find valid pairs for each largest side
 * 3. When a + b > c, all pairs between left and right are valid
 */
class Solution {
    
    /**
     * Approach 1: Sort + Two Pointers (Recommended)
     * Time: O(n²), Space: O(1)
     * 
     * Steps:
     * 1. Sort the array
     * 2. Fix the largest side from right to left
     * 3. Use two pointers to find pairs that satisfy a + b > c
     * 4. Count all valid pairs
     */
    public int triangleNumber(int[] nums) {
        if (nums == null || nums.length < 3) return 0;
        
        Arrays.sort(nums);
        int count = 0;
        int n = nums.length;
        
        // Fix the largest side
        for (int i = n - 1; i >= 2; i--) {
            int left = 0;
            int right = i - 1;
            
            while (left < right) {
                if (nums[left] + nums[right] > nums[i]) {
                    // All pairs between left and right are valid
                    count += (right - left);
                    right--;
                } else {
                    left++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 2: Sort + Two Pointers (Left to Right)
     * Time: O(n²), Space: O(1)
     * 
     * Fix middle side instead of largest side
     */
    public int triangleNumberMiddle(int[] nums) {
        if (nums == null || nums.length < 3) return 0;
        
        Arrays.sort(nums);
        int count = 0;
        int n = nums.length;
        
        // Fix the middle side
        for (int i = 1; i < n - 1; i++) {
            int left = 0;
            int right = n - 1;
            
            while (left < i && i < right) {
                if (nums[left] + nums[i] > nums[right]) {
                    count += (i - left);
                    right--;
                } else {
                    left++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 3: Sort + Binary Search
     * Time: O(n² log n), Space: O(1)
     * 
     * For each pair (a, b), binary search for the largest c
     */
    public int triangleNumberBinarySearch(int[] nums) {
        if (nums == null || nums.length < 3) return 0;
        
        Arrays.sort(nums);
        int count = 0;
        int n = nums.length;
        
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                int sum = nums[i] + nums[j];
                // Binary search for the largest index where nums[k] < sum
                int left = j + 1;
                int right = n - 1;
                int k = j;
                
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (nums[mid] < sum) {
                        k = mid;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                count += (k - j);
            }
        }
        
        return count;
    }
    
    /**
     * Approach 4: Brute Force (for small arrays)
     * Time: O(n³), Space: O(1)
     * 
     * Check all triplets
     */
    public int triangleNumberBruteForce(int[] nums) {
        if (nums == null || nums.length < 3) return 0;
        
        int count = 0;
        int n = nums.length;
        
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] > nums[k] &&
                        nums[i] + nums[k] > nums[j] &&
                        nums[j] + nums[k] > nums[i]) {
                        count++;
                    }
                }
            }
        }
        
        return count;
    }
    
    /**
     * Approach 5: Counting Sort (since nums[i] ≤ 1000)
     * Time: O(n + M²), Space: O(M) where M = 1001
     * 
     * Use frequency array to count combinations
     */
    public int triangleNumberCounting(int[] nums) {
        if (nums == null || nums.length < 3) return 0;
        
        int maxVal = 1000;
        int[] freq = new int[maxVal + 1];
        for (int num : nums) {
            freq[num]++;
        }
        
        // Prefix sum for quick count of numbers <= x
        int[] prefix = new int[maxVal + 2];
        for (int i = 1; i <= maxVal + 1; i++) {
            prefix[i] = prefix[i - 1] + freq[i - 1];
        }
        
        int count = 0;
        
        // Iterate over all possible side lengths
        for (int a = 1; a <= maxVal; a++) {
            if (freq[a] == 0) continue;
            
            for (int b = a; b <= maxVal; b++) {
                if (freq[b] == 0) continue;
                
                int minC = b;
                int maxC = Math.min(maxVal, a + b - 1);
                
                if (minC > maxC) continue;
                
                int cCount = prefix[maxC + 1] - prefix[minC];
                
                if (a == b) {
                    // Choose 2 from freq[a] for a and b
                    int choose2 = freq[a] * (freq[a] - 1) / 2;
                    count += choose2 * cCount;
                    
                    // Choose 3 from freq[a] (a = b = c)
                    if (a <= maxC) {
                        int choose3 = freq[a] * (freq[a] - 1) * (freq[a] - 2) / 6;
                        count += choose3;
                    }
                } else {
                    // a < b
                    count += freq[a] * freq[b] * cCount;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Helper: Visualize the two-pointer process
     */
    public void visualizeTwoPointer(int[] nums) {
        System.out.println("\nValid Triangle Number Visualization:");
        System.out.println("=".repeat(60));
        
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        System.out.println("\nSorted array: " + Arrays.toString(sorted));
        
        int n = sorted.length;
        int count = 0;
        
        System.out.println("\nProcessing largest sides from right to left:");
        
        for (int i = n - 1; i >= 2; i--) {
            int left = 0;
            int right = i - 1;
            int c = sorted[i];
            
            System.out.printf("\nLargest side = %d (index %d)%n", c, i);
            System.out.printf("  left = %d, right = %d%n", left, right);
            
            while (left < right) {
                int a = sorted[left];
                int b = sorted[right];
                System.out.printf("  Checking (%d, %d, %d): ", a, b, c);
                
                if (a + b > c) {
                    int validPairs = right - left;
                    count += validPairs;
                    System.out.printf("%d + %d = %d > %d ✓ → all %d pairs between indices %d and %d are valid%n",
                        a, b, a + b, c, validPairs, left, right);
                    right--;
                } else {
                    System.out.printf("%d + %d = %d ≤ %d ✗ → move left up%n", a, b, a + b, c);
                    left++;
                }
            }
        }
        
        System.out.println("\nTotal valid triangles: " + count);
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            {2, 2, 3, 4},           // Example 1 → 3
            {4, 2, 3, 4},           // Example 2 → 4
            {1, 2, 3},              // No triangle → 0
            {5, 5, 5},              // Equilateral → 1
            {1, 1, 1, 1},           // Four 1's → 4
            {0, 0, 0, 0},           // All zeros → 0
            {1, 2, 3, 4, 5, 6},     // Increasing → count
            {10, 10, 10, 10, 10},   // All same → 10
            {2, 2, 2, 3, 3, 4}      // Mixed duplicates
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][] testCases = generateTestCases();
        int[] expected = {3, 4, 0, 1, 4, 0, 7, 10, 12};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.printf("\nTest %d: %s%n", i + 1, Arrays.toString(nums));
            
            int result1 = triangleNumber(nums);
            int result2 = triangleNumberMiddle(nums);
            int result3 = triangleNumberBinarySearch(nums);
            int result4 = triangleNumberBruteForce(nums);
            int result5 = triangleNumberCounting(nums);
            
            boolean allMatch = result1 == expected[i] && result2 == expected[i] &&
                              result3 == expected[i] && result4 == expected[i] &&
                              result5 == expected[i];
            
            if (allMatch) {
                System.out.println("✓ PASS - Count: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeTwoPointer(nums);
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
        Random rand = new Random(42);
        int n = 1000;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = rand.nextInt(1000) + 1;
        }
        
        System.out.println("Test Setup: " + n + " elements");
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: Two Pointers
        int[] nums1 = nums.clone();
        long start = System.currentTimeMillis();
        results[0] = triangleNumber(nums1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Two Pointers (Middle)
        int[] nums2 = nums.clone();
        start = System.currentTimeMillis();
        results[1] = triangleNumberMiddle(nums2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Binary Search
        int[] nums3 = nums.clone();
        start = System.currentTimeMillis();
        results[2] = triangleNumberBinarySearch(nums3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Brute Force (skip for large n)
        times[3] = -1;
        results[3] = -1;
        
        // Method 5: Counting Sort
        int[] nums5 = nums.clone();
        start = System.currentTimeMillis();
        results[4] = triangleNumberCounting(nums5);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms) | Result");
        System.out.println("--------------------------|-----------|--------");
        System.out.printf("1. Two Pointers           | %9d | %6d%n", times[0], results[0]);
        System.out.printf("2. Two Pointers (Middle)  | %9d | %6d%n", times[1], results[1]);
        System.out.printf("3. Binary Search          | %9d | %6d%n", times[2], results[2]);
        System.out.printf("4. Brute Force            | %9s | %6s%n", "N/A", "N/A");
        System.out.printf("5. Counting Sort          | %9d | %6d%n", times[4], results[4]);
        
        boolean allMatch = results[0] == results[1] && results[1] == results[2] &&
                          results[2] == results[4];
        System.out.println("\nAll efficient methods produce same result: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Two-pointer approach is fastest O(n²)");
        System.out.println("2. Binary search adds log n factor (slightly slower)");
        System.out.println("3. Counting sort is efficient when value range is small");
        System.out.println("4. Brute force O(n³) is infeasible for n=1000");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Empty array:");
        int[] nums1 = {};
        System.out.println("   Input: []");
        System.out.println("   Output: " + triangleNumber(nums1));
        
        System.out.println("\n2. Array with less than 3 elements:");
        int[] nums2 = {1, 2};
        System.out.println("   Input: [1,2]");
        System.out.println("   Output: " + triangleNumber(nums2));
        
        System.out.println("\n3. All zeros:");
        int[] nums3 = {0, 0, 0, 0};
        System.out.println("   Input: [0,0,0,0]");
        System.out.println("   Output: " + triangleNumber(nums3));
        
        System.out.println("\n4. Including zeros:");
        int[] nums4 = {0, 1, 1, 1};
        System.out.println("   Input: [0,1,1,1]");
        System.out.println("   Output: " + triangleNumber(nums4));
        
        System.out.println("\n5. Large numbers:");
        int[] nums5 = {1000, 1000, 1000};
        System.out.println("   Input: [1000,1000,1000]");
        System.out.println("   Output: " + triangleNumber(nums5));
        
        System.out.println("\n6. Already sorted:");
        int[] nums6 = {1, 2, 3, 4, 5, 6};
        System.out.println("   Input: [1,2,3,4,5,6]");
        System.out.println("   Output: " + triangleNumber(nums6));
    }
    
    /**
     * Helper: Explain triangle inequality
     */
    public void explainTriangleInequality() {
        System.out.println("\nTriangle Inequality Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nTriangle Inequality Theorem:");
        System.out.println("For any triangle with sides a, b, c:");
        System.out.println("  a + b > c");
        System.out.println("  a + c > b");
        System.out.println("  b + c > a");
        
        System.out.println("\nAfter sorting (a ≤ b ≤ c):");
        System.out.println("  Only need to check: a + b > c");
        System.out.println("  Because a + c > b and b + c > a are automatically true");
        
        System.out.println("\nWhy sorting helps:");
        System.out.println("1. Fix the largest side as the rightmost element");
        System.out.println("2. Use two pointers to find all pairs (a, b) where a + b > c");
        System.out.println("3. When a + b > c, all pairs between a and b are also valid");
        
        System.out.println("\nExample: [2, 2, 3, 4]");
        System.out.println("  Sorted: [2, 2, 3, 4]");
        System.out.println("  Fix c = 4 (index 3):");
        System.out.println("    left=0 (2), right=2 (3): 2+3=5 > 4 → valid");
        System.out.println("    Count += (right - left) = 2 - 0 = 2 (pairs: (2,3) and (2,3))");
        System.out.println("    right-- → 1 (2)");
        System.out.println("    left=0 (2), right=1 (2): 2+2=4 > 4? No → left++");
        System.out.println("  Fix c = 3 (index 2):");
        System.out.println("    left=0 (2), right=1 (2): 2+2=4 > 3 → valid");
        System.out.println("    Count += 1");
        System.out.println("  Total: 2 + 1 = 3");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Can sides be zero? (Yes, but invalid for triangle)");
        System.out.println("   - What about duplicate values? (Count all valid triplets)");
        System.out.println("   - What's the maximum array length? (1000)");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - O(n³) solution checking all triplets");
        System.out.println("   - Mention it's too slow for n=1000");
        
        System.out.println("\n3. Optimize with sorting:");
        System.out.println("   - Sort array: O(n log n)");
        System.out.println("   - Triangle condition simplifies to a + b > c");
        
        System.out.println("\n4. Two-pointer approach:");
        System.out.println("   - Fix largest side c");
        System.out.println("   - Use two pointers to find valid a and b");
        System.out.println("   - When a + b > c, all pairs between left and right are valid");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(n²)");
        System.out.println("   - Space: O(1)");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - Less than 3 elements");
        System.out.println("   - All zeros");
        System.out.println("   - Large numbers (up to 1000)");
        System.out.println("   - Duplicate values");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Forgetting to sort");
        System.out.println("   - Not skipping zeros");
        System.out.println("   - Off-by-one errors in two pointers");
        System.out.println("   - Integer overflow in a + b");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("611. Valid Triangle Number");
        System.out.println("==========================");
        
        // Explain triangle inequality
        solution.explainTriangleInequality();
        
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
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public int triangleNumber(int[] nums) {
        if (nums == null || nums.length < 3) return 0;
        
        Arrays.sort(nums);
        int count = 0;
        int n = nums.length;
        
        for (int i = n - 1; i >= 2; i--) {
            int left = 0;
            int right = i - 1;
            
            while (left < right) {
                if (nums[left] + nums[right] > nums[i]) {
                    count += (right - left);
                    right--;
                } else {
                    left++;
                }
            }
        }
        
        return count;
    }
}
            """);
        
        System.out.println("\nAlternative (Binary Search):");
        System.out.println("""
class Solution {
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        int n = nums.length;
        
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                int sum = nums[i] + nums[j];
                int left = j + 1;
                int right = n - 1;
                int k = j;
                
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (nums[mid] < sum) {
                        k = mid;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                count += (k - j);
            }
        }
        
        return count;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Sorting simplifies the triangle condition");
        System.out.println("2. Two-pointer technique finds all valid pairs in O(n) for each c");
        System.out.println("3. When a + b > c, all pairs between left and right are valid");
        System.out.println("4. Time complexity: O(n²), Space: O(1)");
        System.out.println("5. Handle zeros carefully (can't form triangles)");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n²) with two pointers, O(n² log n) with binary search");
        System.out.println("- Space: O(1) extra space");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle floating-point sides?");
        System.out.println("2. What if the array is not sorted?");
        System.out.println("3. How would you return the actual triplets, not just count?");
        System.out.println("4. How would you handle very large numbers (overflow)?");
        System.out.println("5. How would you modify for 4 sides?");
    }
}
