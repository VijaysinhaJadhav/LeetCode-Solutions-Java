
## Solution.java

```java
/**
 * 35. Search Insert Position
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given a sorted array of distinct integers and a target value, return the index 
 * if the target is found. If not, return the index where it would be if it were inserted in order.
 * 
 * Key Insights:
 * 1. Array is sorted and contains distinct integers - perfect for binary search
 * 2. Insertion position is the first index where element >= target
 * 3. When target not found, left pointer in binary search indicates insertion position
 * 4. O(log n) runtime complexity required
 * 
 * Approach (Binary Search):
 * 1. Initialize left and right pointers
 * 2. While left <= right, calculate mid
 * 3. If nums[mid] == target, return mid
 * 4. If nums[mid] < target, search right half
 * 5. If nums[mid] > target, search left half
 * 6. When loop ends, left indicates insertion position
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Binary Search
 */

class Solution {
    /**
     * Approach 1: Standard Binary Search - RECOMMENDED
     * O(log n) time, O(1) space - Meets requirements
     */
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // When while loop ends, left is the insertion position
        return left;
    }
    
    /**
     * Approach 2: Binary Search with Early Returns
     * Same complexity but with additional early checks
     */
    public int searchInsertEarlyReturn(int[] nums, int target) {
        // Early return for edge cases
        if (target < nums[0]) {
            return 0;
        }
        if (target > nums[nums.length - 1]) {
            return nums.length;
        }
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return left;
    }
    
    /**
     * Approach 3: Linear Search (Not Recommended - for comparison only)
     * O(n) time, O(1) space - Doesn't meet O(log n) requirement
     */
    public int searchInsertLinear(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return nums.length;
    }
    
    /**
     * Approach 4: Binary Search with Insertion Position Tracking
     * Alternative implementation that explicitly tracks insertion position
     */
    public int searchInsertExplicit(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int insertPos = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
                insertPos = left; // Insert after current position
            } else {
                right = mid - 1;
                insertPos = mid; // Insert at current position
            }
        }
        
        return insertPos;
    }
    
    /**
     * Approach 5: Recursive Binary Search
     * O(log n) time, O(log n) space due to recursion stack
     */
    public int searchInsertRecursive(int[] nums, int target) {
        return binarySearchRecursive(nums, target, 0, nums.length - 1);
    }
    
    private int binarySearchRecursive(int[] nums, int target, int left, int right) {
        if (left > right) {
            return left;
        }
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return binarySearchRecursive(nums, target, mid + 1, right);
        } else {
            return binarySearchRecursive(nums, target, left, mid - 1);
        }
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    private void visualizeBinarySearch(int[] nums, int target) {
        System.out.println("\nBinary Search Visualization:");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Target: " + target);
        System.out.println("n = " + nums.length);
        
        int left = 0;
        int right = nums.length - 1;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Mid | nums[Mid] | Action");
        System.out.println("-----|------|-------|-----|-----------|--------");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String action;
            
            if (nums[mid] == target) {
                action = "FOUND at index " + mid;
                System.out.printf("%4d | %4d | %5d | %3d | %9d | %s%n", 
                                step, left, right, mid, nums[mid], action);
                System.out.println("\nResult: " + mid);
                return;
            } else if (nums[mid] < target) {
                action = "Go RIGHT (nums[mid] < target)";
                left = mid + 1;
            } else {
                action = "Go LEFT (nums[mid] > target)";
                right = mid - 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %9d | %s%n", 
                            step, left, right, mid, nums[mid], action);
            step++;
        }
        
        System.out.println("\nTarget not found. Insertion position: " + left);
        System.out.println("Result: " + left);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Search Insert Position:");
        System.out.println("================================");
        
        // Test case 1: Target exists in middle
        System.out.println("\nTest 1: Target exists in middle");
        int[] nums1 = {1, 3, 5, 6};
        int target1 = 5;
        int expected1 = 2;
        
        long startTime = System.nanoTime();
        int result1a = solution.searchInsert(nums1, target1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.searchInsertEarlyReturn(nums1, target1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.searchInsertLinear(nums1, target1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.searchInsertRecursive(nums1, target1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        
        System.out.println("Binary Search: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Early Return: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Linear: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Recursive: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the binary search process
        solution.visualizeBinarySearch(nums1, target1);
        
        // Test case 2: Target doesn't exist, insert in middle
        System.out.println("\nTest 2: Target doesn't exist, insert in middle");
        int[] nums2 = {1, 3, 5, 6};
        int target2 = 2;
        int expected2 = 1;
        
        int result2a = solution.searchInsert(nums2, target2);
        System.out.println("Insert middle: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Target larger than all elements
        System.out.println("\nTest 3: Target larger than all elements");
        int[] nums3 = {1, 3, 5, 6};
        int target3 = 7;
        int expected3 = 4;
        
        int result3a = solution.searchInsert(nums3, target3);
        System.out.println("Insert end: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Target smaller than all elements
        System.out.println("\nTest 4: Target smaller than all elements");
        int[] nums4 = {1, 3, 5, 6};
        int target4 = 0;
        int expected4 = 0;
        
        int result4a = solution.searchInsert(nums4, target4);
        System.out.println("Insert beginning: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single element array, target exists
        System.out.println("\nTest 5: Single element array, target exists");
        int[] nums5 = {5};
        int target5 = 5;
        int expected5 = 0;
        
        int result5a = solution.searchInsert(nums5, target5);
        System.out.println("Single element found: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Single element array, target doesn't exist
        System.out.println("\nTest 6: Single element array, target doesn't exist");
        int[] nums6 = {5};
        int target6 = 2;
        int expected6 = 0;
        
        int result6a = solution.searchInsert(nums6, target6);
        System.out.println("Single element insert: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Empty array (though constraints say length >= 1)
        System.out.println("\nTest 7: Two elements, insert between");
        int[] nums7 = {1, 3};
        int target7 = 2;
        int expected7 = 1;
        
        int result7a = solution.searchInsert(nums7, target7);
        System.out.println("Two elements insert: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Large array performance
        System.out.println("\nTest 8: Large array performance");
        int[] nums8 = new int[10000];
        for (int i = 0; i < nums8.length; i++) {
            nums8[i] = i * 2; // Even numbers: 0, 2, 4, 6, ...
        }
        int target8 = 9999; // Doesn't exist, should insert at 5000
        
        startTime = System.nanoTime();
        int result8a = solution.searchInsert(nums8, target8);
        long time8a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result8b = solution.searchInsertLinear(nums8, target8);
        long time8b = System.nanoTime() - startTime;
        
        System.out.println("Large array binary search: " + result8a + " - " + time8a + " ns");
        System.out.println("Large array linear search: " + result8b + " - " + time8b + " ns");
        System.out.println("Performance ratio: " + (time8b / time8a) + "x faster");
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Binary Search: " + time1a + " ns");
        System.out.println("  Early Return: " + time1b + " ns");
        System.out.println("  Linear: " + time1c + " ns");
        System.out.println("  Recursive: " + time1d + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BINARY SEARCH ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("When binary search ends (left > right), the left pointer");
        System.out.println("always indicates the insertion position.");
        
        System.out.println("\nWhy left pointer indicates insertion position:");
        System.out.println("1. Binary search maintains the invariant: all elements to the");
        System.out.println("   left of 'left' are < target, all elements to the right");
        System.out.println("   of 'right' are > target");
        System.out.println("2. When the loop ends, left = right + 1");
        System.out.println("3. The position 'left' is where target should be inserted");
        System.out.println("   to maintain sorted order");
        
        System.out.println("\nStep-by-step example:");
        System.out.println("Array: [1, 3, 5, 6], Target: 2");
        System.out.println("Step 1: left=0, right=3, mid=1 → nums[1]=3 > 2 → right=0");
        System.out.println("Step 2: left=0, right=0, mid=0 → nums[0]=1 < 2 → left=1");
        System.out.println("Step 3: left=1, right=0 → loop ends");
        System.out.println("Insertion position = left = 1");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Standard Binary Search (RECOMMENDED):");
        System.out.println("   Time: O(log n) - Halves search space each iteration");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Initialize left=0, right=n-1");
        System.out.println("     - While left <= right, calculate mid");
        System.out.println("     - Compare nums[mid] with target");
        System.out.println("     - Adjust left or right based on comparison");
        System.out.println("     - Return left when loop ends");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(log n) time complexity");
        System.out.println("     - O(1) space complexity");
        System.out.println("     - Simple and elegant implementation");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of insertion position logic");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Binary Search with Early Returns:");
        System.out.println("   Time: O(log n) - Same as standard");
        System.out.println("   Space: O(1) - Same as standard");
        System.out.println("   How it works:");
        System.out.println("     - Check edge cases first (target < first, target > last)");
        System.out.println("     - Then perform standard binary search");
        System.out.println("   Pros:");
        System.out.println("     - Handles edge cases efficiently");
        System.out.println("     - May have better average performance");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("     - Edge cases are rare in practice");
        System.out.println("   Best for: When edge cases are common");
        
        System.out.println("\n3. Linear Search (NOT RECOMMENDED):");
        System.out.println("   Time: O(n) - Worst case scans entire array");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Iterate through array from start to end");
        System.out.println("     - Return first index where element >= target");
        System.out.println("   Pros:");
        System.out.println("     - Very simple to implement and understand");
        System.out.println("     - No complex logic");
        System.out.println("   Cons:");
        System.out.println("     - O(n) time doesn't meet O(log n) requirement");
        System.out.println("     - Inefficient for large arrays");
        System.out.println("   Best for: Small arrays, when simplicity is key");
        
        System.out.println("\n4. Recursive Binary Search:");
        System.out.println("   Time: O(log n) - Same as iterative");
        System.out.println("   Space: O(log n) - Recursion stack depth");
        System.out.println("   How it works:");
        System.out.println("     - Recursively search left or right half");
        System.out.println("     - Base case: left > right, return left");
        System.out.println("   Pros:");
        System.out.println("     - Elegant recursive implementation");
        System.out.println("     - Natural expression of divide-and-conquer");
        System.out.println("   Cons:");
        System.out.println("     - O(log n) space due to recursion stack");
        System.out.println("     - Risk of stack overflow for very large arrays");
        System.out.println("   Best for: Learning recursion, small to medium arrays");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Binary search reduces search space by half each iteration");
        System.out.println("2. Maximum iterations: ⌊log₂(n)⌋ + 1");
        System.out.println("3. For n=10,000: maximum 14 iterations");
        System.out.println("4. Linear search would require up to 10,000 comparisons");
        System.out.println("5. Binary search is exponentially faster for large n");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with binary search - it's the expected solution");
        System.out.println("2. Explain why left pointer indicates insertion position");
        System.out.println("3. Handle edge cases: empty array, single element, etc.");
        System.out.println("4. Discuss time/space complexity trade-offs");
        System.out.println("5. Mention alternative approaches and why you chose binary search");
        System.out.println("6. Write clean code with proper variable names");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
