
## Solution.java

```java
/**
 * 287. Find the Duplicate Number
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of n+1 integers where each integer is between 1 and n inclusive,
 * find the duplicate number without modifying the array and using constant space.
 * 
 * Key Insights:
 * 1. Floyd's Cycle Detection: Treat array as linked list, duplicate creates cycle
 * 2. Binary Search: Count numbers <= mid to find which half contains duplicate
 * 3. Bit Manipulation: Use XOR properties to find duplicate
 * 4. Array as Graph: nums[i] points to nums[nums[i]], creating a cycle at duplicate
 * 
 * Approach (Floyd's Cycle Detection - RECOMMENDED):
 * 1. Use slow and fast pointers to detect cycle
 * 2. Find the meeting point inside cycle
 * 3. Use second slow pointer from start to find cycle entrance (duplicate)
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Two Pointers, Binary Search
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Floyd's Tortoise and Hare (Cycle Detection) - RECOMMENDED
     * O(n) time, O(1) space - Most efficient and elegant
     */
    public int findDuplicate(int[] nums) {
        // Phase 1: Detect cycle using slow and fast pointers
        int slow = nums[0];
        int fast = nums[0];
        
        do {
            slow = nums[slow];          // Move one step
            fast = nums[nums[fast]];    // Move two steps
        } while (slow != fast);
        
        // Phase 2: Find the entrance to the cycle (duplicate number)
        int slow2 = nums[0];
        while (slow != slow2) {
            slow = nums[slow];
            slow2 = nums[slow2];
        }
        
        return slow;
    }
    
    /**
     * Approach 2: Binary Search on Range [1, n]
     * O(n log n) time, O(1) space - Slower but intuitive
     */
    public int findDuplicateBinarySearch(int[] nums) {
        int left = 1;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            int count = 0;
            
            // Count numbers <= mid
            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }
            
            // If count > mid, duplicate is in left half
            if (count > mid) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    /**
     * Approach 3: Bit Manipulation
     * O(n log n) time, O(1) space - Interesting but not optimal
     */
    public int findDuplicateBitManipulation(int[] nums) {
        int duplicate = 0;
        int n = nums.length - 1;
        int maxBit = 31; // Since n <= 10^5, we need at most 17 bits
        
        // Check each bit position
        for (int bit = 0; bit <= maxBit; bit++) {
            int mask = 1 << bit;
            int baseCount = 0;
            int numsCount = 0;
            
            // Count how many numbers from 1 to n have this bit set
            for (int i = 1; i <= n; i++) {
                if ((i & mask) != 0) {
                    baseCount++;
                }
            }
            
            // Count how many numbers in nums have this bit set
            for (int num : nums) {
                if ((num & mask) != 0) {
                    numsCount++;
                }
            }
            
            // If numsCount > baseCount, this bit should be set in duplicate
            if (numsCount > baseCount) {
                duplicate |= mask;
            }
        }
        
        return duplicate;
    }
    
    /**
     * Approach 4: Negative Marking (Modifies Array)
     * O(n) time, O(1) space - Violates "without modifying array" constraint
     * Included for completeness
     */
    public int findDuplicateNegativeMarking(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]);
            if (nums[index] < 0) {
                return index;
            }
            nums[index] = -nums[index];
        }
        return -1;
    }
    
    /**
     * Approach 5: Array as HashMap (Modifies Array)
     * O(n) time, O(1) space - Violates "without modifying array" constraint
     * Included for completeness
     */
    public int findDuplicateArrayAsMap(int[] nums) {
        while (nums[0] != nums[nums[0]]) {
            int temp = nums[nums[0]];
            nums[nums[0]] = nums[0];
            nums[0] = temp;
        }
        return nums[0];
    }
    
    /**
     * Approach 6: Floyd's with Visualization
     * Same as Approach 1 but with step-by-step visualization
     */
    public int findDuplicateVisual(int[] nums) {
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Treating array as linked list:");
        System.out.println("Index -> Value -> Index...");
        
        // Phase 1: Detect cycle
        System.out.println("\nPhase 1: Detect cycle");
        int slow = nums[0];
        int fast = nums[0];
        int step = 1;
        
        System.out.println("Initial: slow = nums[0] = " + slow + ", fast = nums[0] = " + fast);
        
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
            
            System.out.println("Step " + step + ": slow = nums[" + slow + "] = " + 
                             (step == 1 ? "nums[" + nums[0] + "]" : "nums[slow]") + 
                             " = " + slow + ", fast = nums[nums[fast]] = " + fast);
            step++;
        } while (slow != fast);
        
        System.out.println("Cycle detected! slow == fast = " + slow);
        
        // Phase 2: Find cycle entrance (duplicate)
        System.out.println("\nPhase 2: Find cycle entrance (duplicate)");
        int slow2 = nums[0];
        step = 1;
        
        System.out.println("Initial: slow = " + slow + ", slow2 = nums[0] = " + slow2);
        
        while (slow != slow2) {
            slow = nums[slow];
            slow2 = nums[slow2];
            
            System.out.println("Step " + step + ": slow = nums[slow] = " + slow + 
                             ", slow2 = nums[slow2] = " + slow2);
            step++;
        }
        
        System.out.println("Found duplicate: " + slow);
        return slow;
    }
    
    /**
     * Helper method to visualize the linked list representation
     */
    private void visualizeLinkedList(int[] nums) {
        System.out.println("\nLinked List Visualization:");
        System.out.println("Index: 0 -> " + nums[0]);
        
        Set<Integer> visited = new HashSet<>();
        int current = 0;
        
        while (!visited.contains(current)) {
            visited.add(current);
            int next = nums[current];
            System.out.println("  " + current + " -> " + next);
            current = next;
            
            if (visited.size() > 10) break; // Prevent infinite loops
        }
        
        System.out.println("Cycle detected at: " + current);
    }
    
    /**
     * Helper method to verify the solution
     */
    private boolean verifyDuplicate(int[] nums, int duplicate) {
        int count = 0;
        for (int num : nums) {
            if (num == duplicate) {
                count++;
            }
        }
        return count >= 2;
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int[] nums) {
        System.out.println("\nPerformance Comparison:");
        System.out.println("Array length: " + nums.length);
        System.out.println("n: " + (nums.length - 1));
        System.out.println("=================================");
        
        long startTime, endTime;
        int result;
        
        // Floyd's Cycle Detection
        startTime = System.nanoTime();
        result = findDuplicate(nums);
        endTime = System.nanoTime();
        System.out.printf("Floyd's Cycle:    %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Binary Search
        startTime = System.nanoTime();
        result = findDuplicateBinarySearch(nums);
        endTime = System.nanoTime();
        System.out.printf("Binary Search:    %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Bit Manipulation
        startTime = System.nanoTime();
        result = findDuplicateBitManipulation(nums);
        endTime = System.nanoTime();
        System.out.printf("Bit Manipulation: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Verify all produce same result
        boolean allEqual = (findDuplicate(nums) == findDuplicateBinarySearch(nums)) && 
                          (findDuplicate(nums) == findDuplicateBitManipulation(nums));
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Find the Duplicate Number:");
        System.out.println("==================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: [1,3,4,2,2]");
        int[] test1 = {1, 3, 4, 2, 2};
        int result1 = solution.findDuplicate(test1);
        System.out.println("Expected: 2, Actual: " + result1);
        boolean test1Pass = (result1 == 2) && solution.verifyDuplicate(test1, result1);
        System.out.println("Test 1: " + (test1Pass ? "PASSED" : "FAILED"));
        solution.visualizeLinkedList(test1);
        
        // Test case 2: Another example
        System.out.println("\nTest 2: [3,1,3,4,2]");
        int[] test2 = {3, 1, 3, 4, 2};
        int result2 = solution.findDuplicate(test2);
        System.out.println("Expected: 3, Actual: " + result2);
        boolean test2Pass = (result2 == 3) && solution.verifyDuplicate(test2, result2);
        System.out.println("Test 2: " + (test2Pass ? "PASSED" : "FAILED"));
        
        // Test case 3: Duplicate at beginning
        System.out.println("\nTest 3: [2,2,1,3]");
        int[] test3 = {2, 2, 1, 3}; // n=3, array length=4
        int result3 = solution.findDuplicate(test3);
        System.out.println("Expected: 2, Actual: " + result3);
        boolean test3Pass = (result3 == 2) && solution.verifyDuplicate(test3, result3);
        System.out.println("Test 3: " + (test3Pass ? "PASSED" : "FAILED"));
        
        // Test case 4: Large array
        System.out.println("\nTest 4: Large array with duplicate at end");
        int[] test4 = new int[10001];
        for (int i = 0; i < 10000; i++) {
            test4[i] = i + 1;
        }
        test4[10000] = 10000; // Duplicate the last number
        int result4 = solution.findDuplicate(test4);
        System.out.println("Expected: 10000, Actual: " + result4);
        boolean test4Pass = (result4 == 10000) && solution.verifyDuplicate(test4, result4);
        System.out.println("Test 4: " + (test4Pass ? "PASSED" : "FAILED"));
        
        // Test case 5: Compare all approaches
        System.out.println("\nTest 5: Verify all approaches produce same result");
        int[] test5 = {1, 4, 3, 2, 4};
        int result5a = solution.findDuplicate(test5);
        int result5b = solution.findDuplicateBinarySearch(test5);
        int result5c = solution.findDuplicateBitManipulation(test5);
        
        boolean allEqual = (result5a == result5b) && (result5a == result5c);
        System.out.println("All approaches produce same result: " + (allEqual ? "PASSED" : "FAILED"));
        
        // Visualization test
        System.out.println("\nTest 6: Step-by-step visualization");
        int[] test6 = {1, 3, 4, 2, 2};
        solution.findDuplicateVisual(test6);
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison - Small Array");
        int[] smallTest = new int[101]; // n=100
        for (int i = 0; i < 100; i++) {
            smallTest[i] = i + 1;
        }
        smallTest[100] = 50; // Duplicate in middle
        solution.compareApproaches(smallTest);
        
        System.out.println("\nTest 8: Performance Comparison - Large Array");
        int[] largeTest = new int[10001]; // n=10000
        for (int i = 0; i < 10000; i++) {
            largeTest[i] = i + 1;
        }
        largeTest[10000] = 5000; // Duplicate in middle
        solution.compareApproaches(largeTest);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nFloyd's Cycle Detection (RECOMMENDED):");
        System.out.println("Key Insight: Treat the array as a linked list where");
        System.out.println("each element points to the next index (nums[i] -> nums[nums[i]]).");
        System.out.println("The duplicate creates a cycle in this linked list.");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. Since values are in [1, n] and array length is n+1,");
        System.out.println("   we can use values as indices without going out of bounds");
        System.out.println("2. The duplicate creates multiple pointers to the same index");
        System.out.println("3. This creates a cycle in the implicit linked list");
        System.out.println("4. We can use Floyd's algorithm to find cycle entrance");
        
        System.out.println("\nMathematical Proof:");
        System.out.println("Let:");
        System.out.println("  x = distance from start to cycle entrance");
        System.out.println("  y = distance from cycle entrance to meeting point");
        System.out.println("  z = remaining cycle distance");
        System.out.println("When slow and fast meet:");
        System.out.println("  Slow distance = x + y");
        System.out.println("  Fast distance = x + y + k*(y + z)  where k >= 1");
        System.out.println("Since fast moves twice as fast:");
        System.out.println("  2(x + y) = x + y + k(y + z)");
        System.out.println("  x + y = k(y + z)");
        System.out.println("  x = (k-1)(y + z) + z");
        System.out.println("This shows that moving from start and meeting point");
        System.out.println("at same speed will meet at cycle entrance.");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nFloyd's Cycle Detection:");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Two passes through the list");
        System.out.println("  - Linear time complexity");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only constant extra space for pointers");
        
        System.out.println("\nBinary Search:");
        System.out.println("Time Complexity: O(n log n)");
        System.out.println("  - Binary search: O(log n) iterations");
        System.out.println("  - Each iteration scans entire array: O(n)");
        System.out.println("  - Total: O(n log n)");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only constant extra space");
        
        System.out.println("\nBit Manipulation:");
        System.out.println("Time Complexity: O(n log n)");
        System.out.println("  - For each bit (log n bits), scan array: O(n)");
        System.out.println("  - Total: O(n log n)");
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only constant extra space");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("1. Start with Floyd's cycle detection (most efficient)");
        System.out.println("2. Explain the linked list transformation clearly:");
        System.out.println("   - Array indices as nodes");
        System.out.println("   - Array values as pointers to next nodes");
        System.out.println("   - Duplicate creates cycle");
        System.out.println("3. Describe the two-phase algorithm:");
        System.out.println("   - Phase 1: Detect cycle with slow/fast pointers");
        System.out.println("   - Phase 2: Find cycle entrance (duplicate)");
        System.out.println("4. Mention alternative approaches:");
        System.out.println("   - Binary search (intuitive but slower)");
        System.out.println("   - Bit manipulation (interesting but slower)");
        System.out.println("5. Discuss time and space complexity");
        
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("- Not understanding the linked list transformation");
        System.out.println("- Incorrect pointer movement in cycle detection");
        System.out.println("- Forgetting the constraints (no modification, constant space)");
        System.out.println("- Not handling edge cases (small arrays, duplicate at ends)");
        System.out.println("- Confusing this with other duplicate finding problems");
        
        System.out.println("\nAll tests completed!");
    }
}
