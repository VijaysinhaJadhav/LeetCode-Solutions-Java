
# Solution.java

```java
import java.util.*;

/**
 * 162. Find Peak Element
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find any peak element (greater than neighbors) in an array.
 * Use O(log n) time algorithm.
 * 
 * Key Insights:
 * 1. Binary search works because if nums[mid] < nums[mid+1], a peak exists to the right
 * 2. If nums[mid] > nums[mid+1], a peak exists to the left (including mid)
 * 3. Boundaries are considered -∞, so ends can be peaks
 */
class Solution {
    
    /**
     * Approach 1: Iterative Binary Search (Recommended)
     * Time: O(log n), Space: O(1)
     * 
     * Steps:
     * 1. Initialize left = 0, right = n - 1
     * 2. While left < right:
     *    - mid = left + (right - left) / 2
     *    - If nums[mid] < nums[mid + 1], peak is on the right
     *    - Else, peak is on the left (including mid)
     * 3. Return left (or right)
     */
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < nums[mid + 1]) {
                // Peak is on the right side
                left = mid + 1;
            } else {
                // Peak is on the left side (including mid)
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Approach 2: Recursive Binary Search
     * Time: O(log n), Space: O(log n) recursion stack
     * 
     * Same logic but recursive
     */
    public int findPeakElementRecursive(int[] nums) {
        return binarySearch(nums, 0, nums.length - 1);
    }
    
    private int binarySearch(int[] nums, int left, int right) {
        if (left == right) {
            return left;
        }
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] < nums[mid + 1]) {
            return binarySearch(nums, mid + 1, right);
        } else {
            return binarySearch(nums, left, mid);
        }
    }
    
    /**
     * Approach 3: Linear Scan (for comparison)
     * Time: O(n), Space: O(1)
     * 
     * Simple but doesn't meet O(log n) requirement
     */
    public int findPeakElementLinear(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                return i;
            }
        }
        return nums.length - 1;
    }
    
    /**
     * Approach 4: Binary Search with Mid Comparison (Alternative)
     * Time: O(log n), Space: O(1)
     * 
     * Compare both neighbors for clarity
     */
    public int findPeakElementDetailed(int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Check if mid is a peak
            boolean leftGreater = (mid == 0) || (nums[mid] > nums[mid - 1]);
            boolean rightGreater = (mid == n - 1) || (nums[mid] > nums[mid + 1]);
            
            if (leftGreater && rightGreater) {
                return mid;
            }
            
            // If right neighbor is greater, peak is to the right
            if (mid < n - 1 && nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                // Peak is to the left
                right = mid - 1;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 5: Using IntStream (Java 8+)
     * Time: O(n), Space: O(1)
     * 
     * Functional approach (not O(log n))
     */
    public int findPeakElementStream(int[] nums) {
        return java.util.stream.IntStream.range(0, nums.length)
            .filter(i -> (i == 0 || nums[i] > nums[i - 1]) && 
                         (i == nums.length - 1 || nums[i] > nums[i + 1]))
            .findFirst()
            .orElse(-1);
    }
    
    /**
     * Helper: Visualize the binary search process
     */
    public void visualizeSearch(int[] nums) {
        System.out.println("\nFind Peak Element Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nArray: " + Arrays.toString(nums));
        System.out.println("Length: " + nums.length);
        System.out.println("(Boundaries: -∞ at both ends)");
        
        int left = 0;
        int right = nums.length - 1;
        int step = 1;
        
        System.out.println("\nBinary Search Process:");
        System.out.println("Step | left | right | mid | nums[mid] | nums[mid+1] | Decision");
        System.out.println("-----|------|-------|-----|-----------|-------------|---------");
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            String decision;
            
            if (nums[mid] < nums[mid + 1]) {
                decision = "nums[mid] < nums[mid+1] → go RIGHT";
                left = mid + 1;
            } else {
                decision = "nums[mid] > nums[mid+1] → go LEFT";
                right = mid;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %9d | %11d | %s%n",
                step++, left - (nums[mid] < nums[mid + 1] ? 1 : 0), 
                right + (nums[mid] < nums[mid + 1] ? 0 : 1),
                mid, nums[mid], nums[mid + 1], decision);
        }
        
        System.out.printf("\nPeak found at index %d, value = %d%n", left, nums[left]);
        
        // Verify
        boolean isPeak = true;
        if (left > 0 && nums[left] <= nums[left - 1]) isPeak = false;
        if (left < nums.length - 1 && nums[left] <= nums[left + 1]) isPeak = false;
        System.out.println("Verification: " + (isPeak ? "✓ Valid peak" : "✗ Not a valid peak"));
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            {1, 2, 3, 1},                 // Example 1 → 2
            {1, 2, 1, 3, 5, 6, 4},       // Example 2 → 1 or 5
            {1},                          // Single element → 0
            {1, 2},                       // Increasing → 1
            {2, 1},                       // Decreasing → 0
            {1, 2, 3, 4, 5},              // Strictly increasing → 4
            {5, 4, 3, 2, 1},              // Strictly decreasing → 0
            {1, 3, 2, 4, 3, 5, 4},        // Multiple peaks
            {1, 2, 3, 4, 3, 2, 1},        // Single peak at center
            {1, 2, 1, 2, 1, 2, 1}         // Alternating peaks
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.printf("\nTest %d: %s%n", i + 1, Arrays.toString(nums));
            
            int result1 = findPeakElement(nums.clone());
            int result2 = findPeakElementRecursive(nums.clone());
            int result3 = findPeakElementLinear(nums.clone());
            int result4 = findPeakElementDetailed(nums.clone());
            
            // Verify results are valid peaks
            boolean valid1 = isValidPeak(nums, result1);
            boolean valid2 = isValidPeak(nums, result2);
            boolean valid3 = isValidPeak(nums, result3);
            boolean valid4 = isValidPeak(nums, result4);
            
            boolean allValid = valid1 && valid2 && valid3 && valid4;
            
            if (allValid) {
                System.out.println("✓ PASS - Peak index: " + result1 + " (value: " + nums[result1] + ")");
                passed++;
            } else {
                System.out.println("✗ FAIL - Invalid peak returned");
                if (!valid1) System.out.println("  Method 1: index " + result1);
                if (!valid2) System.out.println("  Method 2: index " + result2);
                if (!valid3) System.out.println("  Method 3: index " + result3);
                if (!valid4) System.out.println("  Method 4: index " + result4);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeSearch(nums);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    private boolean isValidPeak(int[] nums, int index) {
        int n = nums.length;
        if (index < 0 || index >= n) return false;
        
        boolean leftValid = (index == 0) || (nums[index] > nums[index - 1]);
        boolean rightValid = (index == n - 1) || (nums[index] > nums[index + 1]);
        
        return leftValid && rightValid;
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=".repeat(50));
        
        // Generate large test case
        int n = 10000000; // 10 million
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i; // Strictly increasing
        }
        
        System.out.println("Test Setup: " + n + " elements (strictly increasing)");
        
        long[] times = new long[4];
        
        // Method 1: Iterative Binary Search
        int[] nums1 = nums.clone();
        long start = System.currentTimeMillis();
        findPeakElement(nums1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Recursive Binary Search
        int[] nums2 = nums.clone();
        start = System.currentTimeMillis();
        findPeakElementRecursive(nums2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Linear Scan
        int[] nums3 = nums.clone();
        start = System.currentTimeMillis();
        findPeakElementLinear(nums3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Detailed Binary Search
        int[] nums4 = nums.clone();
        start = System.currentTimeMillis();
        findPeakElementDetailed(nums4);
        times[3] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. Iterative Binary       | %9d%n", times[0]);
        System.out.printf("2. Recursive Binary       | %9d%n", times[1]);
        System.out.printf("3. Linear Scan            | %9d%n", times[2]);
        System.out.printf("4. Detailed Binary        | %9d%n", times[3]);
        
        System.out.println("\nObservations:");
        System.out.println("1. Binary search is O(log n) → extremely fast");
        System.out.println("2. Linear scan is O(n) → much slower for large inputs");
        System.out.println("3. Recursive approach has overhead but similar speed");
        System.out.println("4. All binary search methods produce valid peaks");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single element:");
        int[] nums1 = {42};
        System.out.println("   Input: [42]");
        System.out.println("   Output: index " + findPeakElement(nums1));
        
        System.out.println("\n2. Two elements increasing:");
        int[] nums2 = {1, 2};
        System.out.println("   Input: [1,2]");
        System.out.println("   Output: index " + findPeakElement(nums2));
        
        System.out.println("\n3. Two elements decreasing:");
        int[] nums3 = {2, 1};
        System.out.println("   Input: [2,1]");
        System.out.println("   Output: index " + findPeakElement(nums3));
        
        System.out.println("\n4. Strictly increasing:");
        int[] nums4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("   Input: [1..10]");
        System.out.println("   Output: index " + findPeakElement(nums4));
        
        System.out.println("\n5. Strictly decreasing:");
        int[] nums5 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("   Input: [10..1]");
        System.out.println("   Output: index " + findPeakElement(nums5));
        
        System.out.println("\n6. Large array (peak at end):");
        int[] nums6 = new int[100000];
        for (int i = 0; i < 100000; i++) nums6[i] = i;
        long start = System.currentTimeMillis();
        int result = findPeakElement(nums6);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 100k increasing elements");
        System.out.println("   Output: index " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Helper: Explain binary search intuition
     */
    public void explainIntuition() {
        System.out.println("\nBinary Search Intuition:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nWhy does binary search work on an unsorted array?");
        System.out.println("Because of the peak property:");
        System.out.println("- If nums[mid] < nums[mid+1], the sequence is increasing at mid");
        System.out.println("  → A peak must exist to the right (since eventually it must decrease or hit boundary)");
        System.out.println("- If nums[mid] > nums[mid+1], the sequence is decreasing at mid");
        System.out.println("  → A peak must exist to the left (including mid)");
        
        System.out.println("\nVisualization:");
        System.out.println("  Array: [1, 2, 3, 4, 5, 4, 3, 2, 1]");
        System.out.println("  mid = 4, nums[4] = 5, nums[5] = 4");
        System.out.println("  5 > 4 → peak is to the left (including mid)");
        System.out.println("  → Search left half: [1, 2, 3, 4, 5]");
        
        System.out.println("\nWhy boundaries work:");
        System.out.println("  nums[-1] = -∞, nums[n] = -∞");
        System.out.println("  So ends can be peaks if they are greater than their only neighbor");
        System.out.println("  Example: [1, 2] → index 1 is peak (2 > 1 and 2 > -∞)");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - What defines a peak? (Greater than neighbors)");
        System.out.println("   - What about boundaries? (Considered -∞)");
        System.out.println("   - Need O(log n) time? (Yes)");
        
        System.out.println("\n2. Start with linear scan:");
        System.out.println("   - O(n) solution: find first element greater than next");
        System.out.println("   - Mention it's simple but doesn't meet requirements");
        
        System.out.println("\n3. Propose binary search:");
        System.out.println("   - Explain why binary search works on unsorted array");
        System.out.println("   - Draw the reasoning: if mid < mid+1, peak on right");
        System.out.println("   - If mid > mid+1, peak on left");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - Time: O(log n)");
        System.out.println("   - Space: O(1) iterative, O(log n) recursive");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - Single element");
        System.out.println("   - Two elements");
        System.out.println("   - Strictly increasing/decreasing");
        System.out.println("   - Multiple peaks");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Off-by-one errors in mid calculation");
        System.out.println("   - Not handling boundaries correctly");
        System.out.println("   - Using <= instead of < for comparisons");
        System.out.println("   - Stack overflow in recursive solution for large arrays");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("162. Find Peak Element");
        System.out.println("======================");
        
        // Explain intuition
        solution.explainIntuition();
        
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
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
}
            """);
        
        System.out.println("\nAlternative (Recursive):");
        System.out.println("""
class Solution {
    public int findPeakElement(int[] nums) {
        return search(nums, 0, nums.length - 1);
    }
    
    private int search(int[] nums, int left, int right) {
        if (left == right) return left;
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] < nums[mid + 1]) {
            return search(nums, mid + 1, right);
        } else {
            return search(nums, left, mid);
        }
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Binary search works because of the peak property");
        System.out.println("2. Compare mid with mid+1 to decide search direction");
        System.out.println("3. Time complexity: O(log n)");
        System.out.println("4. Space complexity: O(1) iterative");
        System.out.println("5. Always returns a valid peak due to boundary conditions");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(log n) - binary search halves the search space");
        System.out.println("- Space: O(1) - constant extra space");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you find all peaks?");
        System.out.println("2. How would you find the maximum peak?");
        System.out.println("3. How would you handle a 2D array? (Find Peak Element II)");
        System.out.println("4. What if adjacent elements could be equal?");
        System.out.println("5. How would you find the leftmost peak?");
    }
}
