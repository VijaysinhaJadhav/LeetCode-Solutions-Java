/**
 * 169. Majority Element
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given an array nums of size n, return the majority element.
 * The majority element is the element that appears more than ⌊n / 2⌋ times.
 * You may assume that the majority element always exists in the array.
 * 
 * Key Insights:
 * 1. Boyer-Moore Voting Algorithm: O(n) time, O(1) space
 * 2. HashMap counting: O(n) time, O(n) space  
 * 3. Sorting: O(n log n) time, O(1) space - middle element is majority
 * 4. Divide and Conquer: O(n log n) time, O(log n) space
 * 
 * Approach (Boyer-Moore):
 * 1. Initialize candidate and count = 0
 * 2. For each number, if count == 0, set candidate to current number
 * 3. If current number == candidate, increment count, else decrement count
 * 4. Return candidate (guaranteed to be majority element)
 * 
 * Time Complexity: O(n) - Single pass through array
 * Space Complexity: O(1) - Constant extra space
 * 
 * Tags: Array, Hash Table, Divide and Conquer, Sorting, Counting
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Boyer-Moore Voting Algorithm (OPTIMAL)
     * O(n) time, O(1) space - Solves the follow-up challenge
     */
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;
        
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }
        
        // Since majority element always exists, we don't need to verify
        return candidate;
    }
    
    /**
     * Approach 2: HashMap Frequency Counting
     * O(n) time, O(n) space - Simple but uses extra space
     */
    public int majorityElementHashMap(int[] nums) {
        Map<Integer, Integer> frequency = new HashMap<>();
        int majorityThreshold = nums.length / 2;
        
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
            if (frequency.get(num) > majorityThreshold) {
                return num;
            }
        }
        
        // Should never reach here given the constraint
        return -1;
    }
    
    /**
     * Approach 3: Sorting
     * O(n log n) time, O(1) space - Simple but not optimal time
     */
    public int majorityElementSorting(int[] nums) {
        Arrays.sort(nums);
        // The majority element must be at the middle index since it appears more than n/2 times
        return nums[nums.length / 2];
    }
    
    /**
     * Approach 4: Divide and Conquer
     * O(n log n) time, O(log n) space - Recursive stack space
     */
    public int majorityElementDivideConquer(int[] nums) {
        return majorityElementRecursive(nums, 0, nums.length - 1);
    }
    
    private int majorityElementRecursive(int[] nums, int left, int right) {
        // Base case: single element
        if (left == right) {
            return nums[left];
        }
        
        // Recursively find majority in left and right halves
        int mid = left + (right - left) / 2;
        int leftMajority = majorityElementRecursive(nums, left, mid);
        int rightMajority = majorityElementRecursive(nums, mid + 1, right);
        
        // If both halves agree on majority, return it
        if (leftMajority == rightMajority) {
            return leftMajority;
        }
        
        // Otherwise, count which one appears more in this range
        int leftCount = countInRange(nums, leftMajority, left, right);
        int rightCount = countInRange(nums, rightMajority, left, right);
        
        return leftCount > rightCount ? leftMajority : rightMajority;
    }
    
    private int countInRange(int[] nums, int num, int left, int right) {
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (nums[i] == num) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Approach 5: Bit Manipulation
     * O(32n) time, O(1) space - Works for 32-bit integers
     */
    public int majorityElementBitManipulation(int[] nums) {
        int majority = 0;
        int n = nums.length;
        
        // Check each bit position (32 bits for integers)
        for (int bit = 0; bit < 32; bit++) {
            int count = 0;
            
            // Count numbers with this bit set
            for (int num : nums) {
                if (((num >> bit) & 1) == 1) {
                    count++;
                }
            }
            
            // If more than half have this bit set, set it in result
            if (count > n / 2) {
                majority |= (1 << bit);
            }
        }
        
        return majority;
    }
    
    /**
     * Helper method to verify if a number is actually majority element
     */
    private boolean verifyMajority(int[] nums, int candidate) {
        int count = 0;
        for (int num : nums) {
            if (num == candidate) {
                count++;
            }
        }
        return count > nums.length / 2;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Majority Element Solution:");
        System.out.println("===================================");
        
        // Test case 1: Simple case
        System.out.println("\nTest 1: Simple case");
        int[] nums1 = {3, 2, 3};
        int expected1 = 3;
        
        long startTime = System.nanoTime();
        int result1a = solution.majorityElement(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.majorityElementHashMap(nums1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.majorityElementSorting(nums1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.majorityElementDivideConquer(nums1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.majorityElementBitManipulation(nums1);
        long time1e = System.nanoTime() - startTime;
        
        System.out.println("Boyer-Moore: " + (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Sorting: " + (result1c == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Divide & Conquer: " + (result1d == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Bit Manipulation: " + (result1e == expected1 ? "PASSED" : "FAILED"));
        
        // Test case 2: Larger array
        System.out.println("\nTest 2: Larger array");
        int[] nums2 = {2, 2, 1, 1, 1, 2, 2};
        int expected2 = 2;
        
        int result2a = solution.majorityElement(nums2);
        int result2b = solution.majorityElementHashMap(nums2);
        int result2c = solution.majorityElementSorting(nums2);
        int result2d = solution.majorityElementDivideConquer(nums2);
        int result2e = solution.majorityElementBitManipulation(nums2);
        
        System.out.println("Boyer-Moore: " + (result2a == expected2 ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + (result2b == expected2 ? "PASSED" : "FAILED"));
        System.out.println("Sorting: " + (result2c == expected2 ? "PASSED" : "FAILED"));
        System.out.println("Divide & Conquer: " + (result2d == expected2 ? "PASSED" : "FAILED"));
        System.out.println("Bit Manipulation: " + (result2e == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single element
        System.out.println("\nTest 3: Single element");
        int[] nums3 = {5};
        int expected3 = 5;
        
        int result3a = solution.majorityElement(nums3);
        int result3b = solution.majorityElementHashMap(nums3);
        int result3c = solution.majorityElementSorting(nums3);
        int result3d = solution.majorityElementDivideConquer(nums3);
        int result3e = solution.majorityElementBitManipulation(nums3);
        
        System.out.println("Boyer-Moore: " + (result3a == expected3 ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + (result3b == expected3 ? "PASSED" : "FAILED"));
        System.out.println("Sorting: " + (result3c == expected3 ? "PASSED" : "FAILED"));
        System.out.println("Divide & Conquer: " + (result3d == expected3 ? "PASSED" : "FAILED"));
        System.out.println("Bit Manipulation: " + (result3e == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: All same elements
        System.out.println("\nTest 4: All same elements");
        int[] nums4 = {7, 7, 7, 7, 7};
        int expected4 = 7;
        
        int result4a = solution.majorityElement(nums4);
        int result4b = solution.majorityElementHashMap(nums4);
        int result4c = solution.majorityElementSorting(nums4);
        int result4d = solution.majorityElementDivideConquer(nums4);
        int result4e = solution.majorityElementBitManipulation(nums4);
        
        System.out.println("Boyer-Moore: " + (result4a == expected4 ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + (result4b == expected4 ? "PASSED" : "FAILED"));
        System.out.println("Sorting: " + (result4c == expected4 ? "PASSED" : "FAILED"));
        System.out.println("Divide & Conquer: " + (result4d == expected4 ? "PASSED" : "FAILED"));
        System.out.println("Bit Manipulation: " + (result4e == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Negative numbers
        System.out.println("\nTest 5: Negative numbers");
        int[] nums5 = {-1, -1, -1, 2, 2, -1, -1};
        int expected5 = -1;
        
        int result5a = solution.majorityElement(nums5);
        int result5b = solution.majorityElementHashMap(nums5);
        int result5c = solution.majorityElementSorting(nums5);
        int result5d = solution.majorityElementDivideConquer(nums5);
        int result5e = solution.majorityElementBitManipulation(nums5);
        
        System.out.println("Boyer-Moore: " + (result5a == expected5 ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + (result5b == expected5 ? "PASSED" : "FAILED"));
        System.out.println("Sorting: " + (result5c == expected5 ? "PASSED" : "FAILED"));
        System.out.println("Divide & Conquer: " + (result5d == expected5 ? "PASSED" : "FAILED"));
        System.out.println("Bit Manipulation: " + (result5e == expected5 ? "PASSED" : "FAILED"));
        
        // Performance comparison for large array
        System.out.println("\nTest 6: Performance comparison (large array)");
        int[] nums6 = new int[10000];
        Arrays.fill(nums6, 5); // All elements are 5 (majority)
        // Add some variety
        for (int i = 0; i < 4000; i++) {
            nums6[i] = i % 10; // 40% different elements
        }
        
        startTime = System.nanoTime();
        int result6a = solution.majorityElement(nums6);
        long time6a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result6b = solution.majorityElementHashMap(nums6);
        long time6b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result6c = solution.majorityElementSorting(nums6);
        long time6c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result6d = solution.majorityElementDivideConquer(nums6);
        long time6d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result6e = solution.majorityElementBitManipulation(nums6);
        long time6e = System.nanoTime() - startTime;
        
        System.out.println("Large array (10000 elements):");
        System.out.println("  Boyer-Moore: " + time6a + " ns");
        System.out.println("  HashMap: " + time6b + " ns");
        System.out.println("  Sorting: " + time6c + " ns");
        System.out.println("  Divide & Conquer: " + time6d + " ns");
        System.out.println("  Bit Manipulation: " + time6e + " ns");
        
        // Verify all approaches found the correct majority element
        boolean allCorrect = solution.verifyMajority(nums6, result6a) &&
                           solution.verifyMajority(nums6, result6b) &&
                           solution.verifyMajority(nums6, result6c) &&
                           solution.verifyMajority(nums6, result6d) &&
                           solution.verifyMajority(nums6, result6e);
        System.out.println("  All approaches correct: " + allCorrect);
        
        // Boyer-Moore algorithm visualization for understanding
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BOYER-MOORE VOTING ALGORITHM VISUALIZATION:");
        System.out.println("=".repeat(70));
        
        int[] demoNums = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("Array: " + Arrays.toString(demoNums));
        System.out.println("\nStep-by-step execution:");
        
        int candidate = 0;
        int count = 0;
        for (int i = 0; i < demoNums.length; i++) {
            int num = demoNums[i];
            if (count == 0) {
                candidate = num;
                System.out.println("Step " + (i + 1) + ": count=0, set candidate=" + candidate);
            }
            if (num == candidate) {
                count++;
                System.out.println("Step " + (i + 1) + ": num=" + num + " == candidate, count++ -> " + count);
            } else {
                count--;
                System.out.println("Step " + (i + 1) + ": num=" + num + " != candidate, count-- -> " + count);
            }
        }
        System.out.println("\nFinal candidate: " + candidate);
        System.out.println("Verification: " + candidate + " appears " + 
                         solution.countInRange(demoNums, candidate, 0, demoNums.length - 1) + 
                         " times (needs > " + demoNums.length / 2 + ")");
        
        // Complexity analysis and approach comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE APPROACH ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Boyer-Moore Voting Algorithm (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(1) - Only two variables (candidate, count)");
        System.out.println("   How it works:");
        System.out.println("     - Maintain a candidate and count");
        System.out.println("     - When count == 0, choose new candidate");
        System.out.println("     - Increment count for matching elements, decrement for others");
        System.out.println("     - Majority element survives the cancellation process");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time and space complexity");
        System.out.println("     - Elegant mathematical solution");
        System.out.println("     - Solves the follow-up challenge");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of why it works");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. HashMap Approach:");
        System.out.println("   Time: O(n) - Single pass for counting");
        System.out.println("   Space: O(n) - Store frequency counts");
        System.out.println("   How it works:");
        System.out.println("     - Count frequency of each element");
        System.out.println("     - Return element with count > n/2");
        System.out.println("   Pros:");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("   Best for: When simplicity is prioritized over space");
        
        System.out.println("\n3. Sorting Approach:");
        System.out.println("   Time: O(n log n) - Sorting dominates");
        System.out.println("   Space: O(1) or O(n) - Depending on sort implementation");
        System.out.println("   How it works:");
        System.out.println("     - Sort the array");
        System.out.println("     - Middle element must be majority (appears > n/2 times)");
        System.out.println("   Pros:");
        System.out.println("     - Very simple one-liner implementation");
        System.out.println("     - Easy to remember");
        System.out.println("   Cons:");
        System.out.println("     - Not optimal time complexity");
        System.out.println("     - Modifies original array");
        System.out.println("   Best for: Quick implementation, small arrays");
        
        System.out.println("\n4. Divide and Conquer:");
        System.out.println("   Time: O(n log n) - Recursive splitting");
        System.out.println("   Space: O(log n) - Recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Split array into halves recursively");
        System.out.println("     - Find majority in each half");
        System.out.println("     - Count which candidate wins in combined range");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates divide and conquer thinking");
        System.out.println("     - Good for parallel processing");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Not optimal time complexity");
        System.out.println("   Best for: Learning divide and conquer, interviews");
        
        System.out.println("\n5. Bit Manipulation:");
        System.out.println("   Time: O(32n) = O(n) - 32 passes for 32-bit integers");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - For each bit position, count majority");
        System.out.println("     - Reconstruct number from majority bits");
        System.out.println("   Pros:");
        System.out.println("     - Different and interesting approach");
        System.out.println("     - Works for integer constraints");
        System.out.println("   Cons:");
        System.out.println("     - Limited to fixed-bit integers");
        System.out.println("     - More complex than Boyer-Moore");
        System.out.println("   Best for: Demonstrating bit manipulation skills");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Boyer-Moore - it's the optimal solution");
        System.out.println("2. Explain WHY it works (majority survives cancellation)");
        System.out.println("3. Mention HashMap as simpler alternative");
        System.out.println("4. Discuss sorting approach for its simplicity");
        System.out.println("5. Be prepared to implement any approach");
        System.out.println("6. Practice the Boyer-Moore algorithm until it's intuitive");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
