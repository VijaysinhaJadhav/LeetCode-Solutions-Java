
# Solution.java

```java
import java.util.*;

/**
 * 31. Next Permutation
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find the next lexicographically greater permutation of the array.
 * If no greater permutation exists, return the smallest (sorted ascending).
 * 
 * Key Insights:
 * 1. Find the rightmost index where nums[i] < nums[i+1] (pivot)
 * 2. Find the smallest number to the right of pivot that is greater than pivot
 * 3. Swap them
 * 4. Reverse the suffix after pivot
 */
class Solution {
    
    /**
     * Approach 1: Standard Next Permutation Algorithm (Recommended)
     * Time: O(n), Space: O(1)
     * 
     * Steps:
     * 1. Find the largest index i where nums[i] < nums[i+1]
     * 2. If no such index, reverse entire array and return
     * 3. Find largest index j > i where nums[j] > nums[i]
     * 4. Swap nums[i] and nums[j]
     * 5. Reverse the suffix from i+1 to end
     */
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) return;
        
        int n = nums.length;
        
        // Step 1: Find the pivot (first decreasing element from right)
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        
        // Step 2: If no pivot found, array is in descending order
        if (i < 0) {
            reverse(nums, 0, n - 1);
            return;
        }
        
        // Step 3: Find the smallest number to the right of i that is greater than nums[i]
        int j = n - 1;
        while (j > i && nums[j] <= nums[i]) {
            j--;
        }
        
        // Step 4: Swap nums[i] and nums[j]
        swap(nums, i, j);
        
        // Step 5: Reverse the suffix starting from i+1
        reverse(nums, i + 1, n - 1);
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }
    
    /**
     * Approach 2: More explicit with comments
     * Time: O(n), Space: O(1)
     * 
     * Same algorithm with more detailed variable names
     */
    public void nextPermutationExplicit(int[] nums) {
        int n = nums.length;
        
        // Find the first decreasing element from the right
        int pivot = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                pivot = i;
                break;
            }
        }
        
        // If no pivot found, array is in descending order
        if (pivot == -1) {
            reverse(nums, 0, n - 1);
            return;
        }
        
        // Find the smallest number greater than nums[pivot] to the right
        int successor = n - 1;
        for (int i = n - 1; i > pivot; i--) {
            if (nums[i] > nums[pivot]) {
                successor = i;
                break;
            }
        }
        
        // Swap pivot and successor
        swap(nums, pivot, successor);
        
        // Reverse the suffix after pivot
        reverse(nums, pivot + 1, n - 1);
    }
    
    /**
     * Approach 3: Using while loops for clarity
     * Time: O(n), Space: O(1)
     */
    public void nextPermutationWhile(int[] nums) {
        int n = nums.length;
        
        // Find pivot
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        
        if (i >= 0) {
            // Find successor
            int j = n - 1;
            while (j > i && nums[j] <= nums[i]) {
                j--;
            }
            // Swap
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        
        // Reverse suffix
        int left = i + 1;
        int right = n - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
    
    /**
     * Approach 4: Next permutation for string (adapted)
     * Time: O(n), Space: O(n)
     * 
     * Returns the next permutation as a string
     */
    public String nextPermutationString(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        
        // Find pivot
        int i = n - 2;
        while (i >= 0 && chars[i] >= chars[i + 1]) {
            i--;
        }
        
        if (i >= 0) {
            // Find successor
            int j = n - 1;
            while (j > i && chars[j] <= chars[i]) {
                j--;
            }
            // Swap
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        
        // Reverse suffix
        int left = i + 1;
        int right = n - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        return new String(chars);
    }
    
    /**
     * Approach 5: Using Collections (for Integer List)
     * Time: O(n), Space: O(1)
     * 
     * Works with List<Integer> instead of int[]
     */
    public void nextPermutationList(List<Integer> nums) {
        int n = nums.size();
        
        // Find pivot
        int i = n - 2;
        while (i >= 0 && nums.get(i) >= nums.get(i + 1)) {
            i--;
        }
        
        if (i >= 0) {
            // Find successor
            int j = n - 1;
            while (j > i && nums.get(j) <= nums.get(i)) {
                j--;
            }
            // Swap
            Collections.swap(nums, i, j);
        }
        
        // Reverse suffix
        Collections.reverse(nums.subList(i + 1, n));
    }
    
    /**
     * Helper: Visualize the process
     */
    public void visualizeNextPermutation(int[] nums) {
        System.out.println("\nNext Permutation Visualization:");
        System.out.println("=".repeat(60));
        
        int[] original = nums.clone();
        System.out.println("\nOriginal array: " + Arrays.toString(original));
        
        int n = nums.length;
        
        // Step 1: Find pivot
        System.out.println("\nStep 1: Find pivot (first decreasing element from right)");
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            System.out.printf("  nums[%d]=%d >= nums[%d]=%d → continue%n", 
                i, nums[i], i + 1, nums[i + 1]);
            i--;
        }
        
        if (i < 0) {
            System.out.println("  No pivot found → array is in descending order");
            System.out.println("  Reverse entire array → " + Arrays.toString(nums));
            reverse(nums, 0, n - 1);
            System.out.println("\nResult: " + Arrays.toString(nums));
            return;
        }
        
        System.out.printf("  Pivot found at index %d, value = %d%n", i, nums[i]);
        
        // Step 2: Find successor
        System.out.println("\nStep 2: Find successor (smallest number greater than pivot to the right)");
        int j = n - 1;
        while (j > i && nums[j] <= nums[i]) {
            System.out.printf("  nums[%d]=%d <= pivot %d → continue%n", j, nums[j], nums[i]);
            j--;
        }
        System.out.printf("  Successor found at index %d, value = %d%n", j, nums[j]);
        
        // Step 3: Swap
        System.out.println("\nStep 3: Swap pivot and successor");
        System.out.printf("  Before swap: %s%n", Arrays.toString(nums));
        swap(nums, i, j);
        System.out.printf("  After swap:  %s%n", Arrays.toString(nums));
        
        // Step 4: Reverse suffix
        System.out.println("\nStep 4: Reverse suffix from index " + (i + 1) + " to end");
        System.out.printf("  Before reverse: %s%n", Arrays.toString(nums));
        reverse(nums, i + 1, n - 1);
        System.out.printf("  After reverse:  %s%n", Arrays.toString(nums));
        
        System.out.println("\nResult: " + Arrays.toString(nums));
        System.out.println("Original → Next: " + Arrays.toString(original) + " → " + Arrays.toString(nums));
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            {1, 2, 3},           // → [1,3,2]
            {3, 2, 1},           // → [1,2,3]
            {1, 1, 5},           // → [1,5,1]
            {1, 3, 2},           // → [2,1,3]
            {2, 3, 1},           // → [3,1,2]
            {1, 2, 3, 4},        // → [1,2,4,3]
            {4, 3, 2, 1},        // → [1,2,3,4]
            {1, 1, 1},           // → [1,1,1]
            {1, 2},              // → [2,1]
            {2, 1},              // → [1,2]
            {1},                 // → [1]
            {1, 5, 8, 4, 7, 6, 5, 3, 1}  // Complex case
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][] testCases = generateTestCases();
        int[][] expected = {
            {1, 3, 2}, {1, 2, 3}, {1, 5, 1}, {2, 1, 3}, {3, 1, 2},
            {1, 2, 4, 3}, {1, 2, 3, 4}, {1, 1, 1}, {2, 1}, {1, 2}, {1},
            {1, 5, 8, 5, 1, 3, 4, 6, 7}
        };
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] original = testCases[i].clone();
            int[] nums1 = testCases[i].clone();
            int[] nums2 = testCases[i].clone();
            int[] nums3 = testCases[i].clone();
            int[] nums4 = testCases[i].clone();
            
            System.out.printf("\nTest %d: %s%n", i + 1, Arrays.toString(original));
            
            nextPermutation(nums1);
            nextPermutationExplicit(nums2);
            nextPermutationWhile(nums3);
            
            // Convert array to list for list method
            List<Integer> list = new ArrayList<>();
            for (int num : testCases[i]) list.add(num);
            nextPermutationList(list);
            int[] nums4Result = list.stream().mapToInt(Integer::intValue).toArray();
            
            boolean allMatch = Arrays.equals(nums1, expected[i]) && 
                              Arrays.equals(nums2, expected[i]) &&
                              Arrays.equals(nums3, expected[i]) &&
                              Arrays.equals(nums4Result, expected[i]);
            
            if (allMatch) {
                System.out.println("✓ PASS - Result: " + Arrays.toString(nums1));
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + Arrays.toString(expected[i]));
                System.out.println("  Method 1: " + Arrays.toString(nums1));
                System.out.println("  Method 2: " + Arrays.toString(nums2));
                System.out.println("  Method 3: " + Arrays.toString(nums3));
                System.out.println("  Method 4: " + Arrays.toString(nums4Result));
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeNextPermutation(original);
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
        for (int i = 0; i < n; i++) {
            nums[i] = n - i;  // Descending order (worst case for finding pivot)
        }
        
        System.out.println("Test Setup: " + n + " elements");
        
        long[] times = new long[4];
        
        // Method 1: Standard algorithm
        int[] nums1 = nums.clone();
        long start = System.currentTimeMillis();
        nextPermutation(nums1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Explicit version
        int[] nums2 = nums.clone();
        start = System.currentTimeMillis();
        nextPermutationExplicit(nums2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: While loop version
        int[] nums3 = nums.clone();
        start = System.currentTimeMillis();
        nextPermutationWhile(nums3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: List version
        List<Integer> list = new ArrayList<>();
        for (int num : nums) list.add(num);
        start = System.currentTimeMillis();
        nextPermutationList(list);
        times[3] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. Standard               | %9d%n", times[0]);
        System.out.printf("2. Explicit               | %9d%n", times[1]);
        System.out.printf("3. While Loop             | %9d%n", times[2]);
        System.out.printf("4. List                   | %9d%n", times[3]);
        
        System.out.println("\nObservations:");
        System.out.println("1. All O(n) methods have similar performance");
        System.out.println("2. Array-based methods are slightly faster than List-based");
        System.out.println("3. The algorithm is very efficient even for large arrays");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single element:");
        int[] nums1 = {5};
        nextPermutation(nums1);
        System.out.println("   Input: [5] → " + Arrays.toString(nums1));
        
        System.out.println("\n2. Two elements ascending:");
        int[] nums2 = {1, 2};
        nextPermutation(nums2);
        System.out.println("   Input: [1,2] → " + Arrays.toString(nums2));
        
        System.out.println("\n3. Two elements descending:");
        int[] nums3 = {2, 1};
        nextPermutation(nums3);
        System.out.println("   Input: [2,1] → " + Arrays.toString(nums3));
        
        System.out.println("\n4. All equal:");
        int[] nums4 = {3, 3, 3};
        nextPermutation(nums4);
        System.out.println("   Input: [3,3,3] → " + Arrays.toString(nums4));
        
        System.out.println("\n5. Already at last permutation:");
        int[] nums5 = {3, 2, 1};
        nextPermutation(nums5);
        System.out.println("   Input: [3,2,1] → " + Arrays.toString(nums5));
        
        System.out.println("\n6. With duplicates:");
        int[] nums6 = {1, 2, 2, 3};
        nextPermutation(nums6);
        System.out.println("   Input: [1,2,2,3] → " + Arrays.toString(nums6));
        
        System.out.println("\n7. Large values:");
        int[] nums7 = {100, 99, 98, 97};
        nextPermutation(nums7);
        System.out.println("   Input: [100,99,98,97] → " + Arrays.toString(nums7));
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nNext Permutation Algorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nWhat is the next permutation?");
        System.out.println("The next lexicographically greater arrangement of numbers.");
        System.out.println("Example: [1,2,3] → [1,3,2] → [2,1,3] → [2,3,1] → [3,1,2] → [3,2,1] → back to [1,2,3]");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Find the largest index i such that nums[i] < nums[i+1] (the 'pivot')");
        System.out.println("   This is where the sequence stops decreasing from the right");
        System.out.println("2. If no such index exists, reverse the entire array (last permutation)");
        System.out.println("3. Find the largest index j > i such that nums[j] > nums[i] (the 'successor')");
        System.out.println("4. Swap nums[i] and nums[j]");
        System.out.println("5. Reverse the suffix from i+1 to the end");
        
        System.out.println("\nExample: [1, 2, 3]");
        System.out.println("  Step 1: i=1 (nums[1]=2 < nums[2]=3)");
        System.out.println("  Step 2: (skip)");
        System.out.println("  Step 3: j=2 (nums[2]=3 > 2)");
        System.out.println("  Step 4: swap → [1, 3, 2]");
        System.out.println("  Step 5: reverse suffix from i+1=2 → already in order");
        System.out.println("  Result: [1, 3, 2]");
        
        System.out.println("\nWhy it works:");
        System.out.println("- The pivot is where we can make a larger number by swapping with a larger digit to its right");
        System.out.println("- We want the smallest possible increase, so we pick the smallest larger digit to the right");
        System.out.println("- After swapping, the suffix is in descending order, so we reverse it to get ascending order (smallest arrangement)");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What should happen for the last permutation? (Wrap to first)");
        System.out.println("   - Does the array contain duplicates? (Yes)");
        System.out.println("   - Is in-place modification required? (Yes)");
        
        System.out.println("\n2. Understand the problem:");
        System.out.println("   - This is about lexicographic ordering of sequences");
        System.out.println("   - Can be visualized as generating permutations in order");
        
        System.out.println("\n3. Explain the algorithm steps:");
        System.out.println("   - Draw an example to illustrate");
        System.out.println("   - Show why we find the pivot and successor");
        System.out.println("   - Explain why we reverse the suffix");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - Time: O(n) - single pass through array");
        System.out.println("   - Space: O(1) - in-place, constant extra space");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - Single element");
        System.out.println("   - Two elements");
        System.out.println("   - Descending array (last permutation)");
        System.out.println("   - Array with duplicates");
        System.out.println("   - All equal elements");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Off-by-one errors in indices");
        System.out.println("   - Forgetting to handle the case when no pivot exists");
        System.out.println("   - Using > instead of >= for duplicate handling");
        System.out.println("   - Not reversing the entire suffix correctly");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("31. Next Permutation");
        System.out.println("====================");
        
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
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) return;
        
        int i = nums.length - 2;
        
        // Step 1: Find pivot
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        
        // Step 2: If no pivot, reverse entire array
        if (i < 0) {
            reverse(nums, 0, nums.length - 1);
            return;
        }
        
        // Step 3: Find successor
        int j = nums.length - 1;
        while (j > i && nums[j] <= nums[i]) {
            j--;
        }
        
        // Step 4: Swap pivot and successor
        swap(nums, i, j);
        
        // Step 5: Reverse suffix
        reverse(nums, i + 1, nums.length - 1);
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Find the rightmost increasing pair (pivot)");
        System.out.println("2. Find the smallest number greater than pivot to its right");
        System.out.println("3. Swap pivot with that number");
        System.out.println("4. Reverse the suffix to get the smallest possible arrangement");
        System.out.println("5. Time: O(n), Space: O(1)");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - single pass through array");
        System.out.println("- Space: O(1) - in-place modification");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you find the previous permutation?");
        System.out.println("2. How would you find the kth permutation?");
        System.out.println("3. How would you generate all permutations using this algorithm?");
        System.out.println("4. What if the array contained negative numbers?");
        System.out.println("5. How would you handle very large arrays?");
    }
}
