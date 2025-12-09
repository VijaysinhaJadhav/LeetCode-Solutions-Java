
## Problems/Arrays-Hashing/0046-permutations/Solution.java

```java
/**
 * 46. Permutations
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array nums of distinct integers, return all the possible permutations.
 * You can return the answer in any order.
 * 
 * Key Insights:
 * 1. Need to generate all possible arrangements of distinct elements
 * 2. There are n! permutations for n distinct elements
 * 3. Backtracking builds permutations incrementally
 * 4. Use a used array to track which elements are already in current permutation
 * 5. At each position, try all unused elements
 * 
 * Approach (Backtracking with Used Array):
 * 1. Use boolean array to track used elements
 * 2. Build permutation incrementally
 * 3. For each position, try all unused elements
 * 4. Backtrack after trying each element
 * 5. Add to result when permutation is complete
 * 
 * Time Complexity: O(n * n!) - n! permutations, each taking O(n) to build
 * Space Complexity: O(n) - Recursion stack and used array
 * 
 * Tags: Array, Backtracking, DFS, Permutations
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Backtracking with Used Array - RECOMMENDED
     * O(n * n!) time, O(n) space
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrack(nums, used, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, boolean[] used, 
                          List<Integer> current, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(nums[i]);
                backtrack(nums, used, current, result);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }
    
    /**
     * Approach 2: Swap-based Backtracking
     * O(n * n!) time, O(n) space - In-place swapping, no extra used array
     */
    public List<List<Integer>> permuteSwap(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackSwap(nums, 0, result);
        return result;
    }
    
    private void backtrackSwap(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }
        
        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            backtrackSwap(nums, start + 1, result);
            swap(nums, start, i); // backtrack
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Approach 3: Iterative (BFS-like)
     * O(n * n!) time, O(n * n!) space - Build permutations level by level
     */
    public List<List<Integer>> permuteIterative(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        
        for (int num : nums) {
            List<List<Integer>> newResult = new ArrayList<>();
            for (List<Integer> permutation : result) {
                for (int i = 0; i <= permutation.size(); i++) {
                    List<Integer> newPermutation = new ArrayList<>(permutation);
                    newPermutation.add(i, num);
                    newResult.add(newPermutation);
                }
            }
            result = newResult;
        }
        
        return result;
    }
    
    /**
     * Approach 4: Using Deque for efficient insertion
     * O(n * n!) time, O(n) space - Alternative iterative approach
     */
    public List<List<Integer>> permuteDeque(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length == 0) return result;
        
        Deque<List<Integer>> queue = new LinkedList<>();
        queue.offer(new ArrayList<>());
        
        for (int num : nums) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                List<Integer> current = queue.poll();
                for (int j = 0; j <= current.size(); j++) {
                    List<Integer> newPermutation = new ArrayList<>(current);
                    newPermutation.add(j, num);
                    if (newPermutation.size() == nums.length) {
                        result.add(newPermutation);
                    } else {
                        queue.offer(newPermutation);
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Recursive with List Removal
     * O(n * n!) time, O(n) space - Alternative recursive approach
     */
    public List<List<Integer>> permuteListRemoval(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (int num : nums) numList.add(num);
        backtrackListRemoval(numList, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrackListRemoval(List<Integer> remaining, 
                                     List<Integer> current, 
                                     List<List<Integer>> result) {
        if (remaining.isEmpty()) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = 0; i < remaining.size(); i++) {
            int num = remaining.get(i);
            current.add(num);
            remaining.remove(i);
            backtrackListRemoval(remaining, current, result);
            remaining.add(i, num);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Approach 6: Using Next Permutation (Lexicographic)
     * O(n * n!) time, O(1) extra space - Generate in lexicographic order
     */
    public List<List<Integer>> permuteLexicographic(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // Start with sorted array for lexicographic order
        
        do {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
        } while (nextPermutation(nums));
        
        return result;
    }
    
    private boolean nextPermutation(int[] nums) {
        // Find first decreasing element from right
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        
        if (i < 0) {
            return false; // No next permutation
        }
        
        // Find element just larger than nums[i]
        int j = nums.length - 1;
        while (nums[j] <= nums[i]) {
            j--;
        }
        
        // Swap and reverse
        swap(nums, i, j);
        reverse(nums, i + 1, nums.length - 1);
        return true;
    }
    
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    private void visualizeBacktracking(int[] nums) {
        System.out.println("\nPermutations Backtracking Visualization:");
        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("Total permutations: " + factorial(nums.length));
        
        System.out.println("\nBacktracking Tree:");
        System.out.println("Level | Current Permutation | Used Array | Action");
        System.out.println("------|---------------------|------------|--------");
        
        visualizeDFS(nums, new boolean[nums.length], new ArrayList<>(), 0);
    }
    
    private void visualizeDFS(int[] nums, boolean[] used, 
                            List<Integer> current, int depth) {
        String indent = "  ".repeat(depth);
        
        if (current.size() == nums.length) {
            System.out.printf("%s%5d | %-19s | %-10s | FOUND PERMUTATION%n", 
                            indent, depth, current.toString(), 
                            Arrays.toString(used));
            return;
        }
        
        System.out.printf("%s%5d | %-19s | %-10s | Start%n", 
                        indent, depth, current.toString(), 
                        Arrays.toString(used));
        
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                System.out.printf("%s%5d | %-19s | %-10s | Use nums[%d] = %d%n", 
                                indent, depth, current.toString(), 
                                Arrays.toString(used), i, nums[i]);
                used[i] = true;
                current.add(nums[i]);
                visualizeDFS(nums, used, current, depth + 1);
                System.out.printf("%s%5d | %-19s | %-10s | Backtrack (remove %d)%n", 
                                indent, depth, current.toString(), 
                                Arrays.toString(used), nums[i]);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }
    
    /**
     * Helper method to visualize swap-based approach
     */
    private void visualizeSwapBacktracking(int[] nums) {
        System.out.println("\nSwap-based Backtracking Visualization:");
        System.out.println("Input: " + Arrays.toString(nums));
        
        System.out.println("\nSwap Process:");
        System.out.println("Start | Array State | Action");
        System.out.println("------|-------------|--------");
        
        visualizeSwapDFS(nums, 0);
    }
    
    private void visualizeSwapDFS(int[] nums, int start) {
        String indent = "  ".repeat(start);
        
        if (start == nums.length) {
            System.out.printf("%s%5d | %-11s | FOUND: %s%n", 
                            indent, start, Arrays.toString(nums), 
                            Arrays.toString(nums));
            return;
        }
        
        System.out.printf("%s%5d | %-11s | Start at index %d%n", 
                        indent, start, Arrays.toString(nums), start);
        
        for (int i = start; i < nums.length; i++) {
            System.out.printf("%s%5d | %-11s | Swap indices %d and %d%n", 
                            indent, start, Arrays.toString(nums), start, i);
            swap(nums, start, i);
            visualizeSwapDFS(nums, start + 1);
            System.out.printf("%s%5d | %-11s | Backtrack swap %d and %d%n", 
                            indent, start, Arrays.toString(nums), start, i);
            swap(nums, start, i);
        }
    }
    
    /**
     * Helper method to calculate factorial
     */
    private int factorial(int n) {
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    /**
     * Helper method to demonstrate mathematical properties
     */
    private void demonstrateMathematicalProperties(int[] nums) {
        System.out.println("\nMathematical Properties of Permutations:");
        int n = nums.length;
        System.out.println("Number of elements (n): " + n);
        System.out.println("Number of permutations (n!): " + factorial(n));
        
        System.out.println("\nKey Properties:");
        System.out.println("1. For n distinct elements: n! permutations");
        System.out.println("2. Each element appears in each position exactly (n-1)! times");
        System.out.println("3. Total permutations grows very fast:");
        for (int i = 1; i <= 6; i++) {
            System.out.println("   n=" + i + ": " + factorial(i) + " permutations");
        }
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("P(n) = n × P(n-1)");
        System.out.println("P(0) = 1 (empty permutation)");
    }
    
    /**
     * Helper method to compare with combinations
     */
    private void compareWithCombinations(int[] nums) {
        System.out.println("\nPermutations vs Combinations:");
        System.out.println("Feature        | Permutations | Combinations");
        System.out.println("---------------|--------------|-------------");
        System.out.println("Order matters  | Yes          | No");
        System.out.println("Formula        | P(n,k)=n!/(n-k)! | C(n,k)=n!/(k!(n-k)!)");
        System.out.println("All elements   | P(n,n)=n!    | C(n,n)=1");
        System.out.println("Count for n=3  | 6            | C(3,2)=3");
        
        System.out.println("\nExample: [1,2,3]");
        System.out.println("Permutations: [1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]");
        System.out.println("Combinations (k=2): [1,2],[1,3],[2,3]");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Permutations Solution:");
        System.out.println("===============================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] nums1 = {1, 2, 3};
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(1, 3, 2),
            Arrays.asList(2, 1, 3),
            Arrays.asList(2, 3, 1),
            Arrays.asList(3, 1, 2),
            Arrays.asList(3, 2, 1)
        );
        
        long startTime = System.nanoTime();
        List<List<Integer>> result1a = solution.permute(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1b = solution.permuteSwap(nums1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1c = solution.permuteIterative(nums1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = arePermutationsEqual(result1a, expected1);
        boolean test1b = arePermutationsEqual(result1b, expected1);
        boolean test1c = arePermutationsEqual(result1c, expected1);
        
        System.out.println("Backtracking: " + result1a.size() + " permutations - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Swap-based: " + result1b.size() + " permutations - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Iterative: " + result1c.size() + " permutations - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the processes
        solution.visualizeBacktracking(nums1);
        solution.visualizeSwapBacktracking(nums1);
        solution.demonstrateMathematicalProperties(nums1);
        solution.compareWithCombinations(nums1);
        
        // Test case 2: Two elements
        System.out.println("\nTest 2: Two elements");
        int[] nums2 = {0, 1};
        List<List<Integer>> expected2 = Arrays.asList(
            Arrays.asList(0, 1),
            Arrays.asList(1, 0)
        );
        
        List<List<Integer>> result2a = solution.permute(nums2);
        System.out.println("Two elements: " + result2a.size() + " permutations - " + 
                         (arePermutationsEqual(result2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Single element
        System.out.println("\nTest 3: Single element");
        int[] nums3 = {1};
        List<List<Integer>> expected3 = Arrays.asList(Arrays.asList(1));
        
        List<List<Integer>> result3a = solution.permute(nums3);
        System.out.println("Single element: " + result3a.size() + " permutations - " + 
                         (arePermutationsEqual(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Four elements
        System.out.println("\nTest 4: Four elements");
        int[] nums4 = {1, 2, 3, 4};
        List<List<Integer>> result4a = solution.permute(nums4);
        System.out.println("Four elements: " + result4a.size() + " permutations (expected: 24)");
        
        // Test case 5: Empty array (edge case)
        System.out.println("\nTest 5: Empty array");
        int[] nums5 = {};
        List<List<Integer>> result5a = solution.permute(nums5);
        System.out.println("Empty array: " + result5a.size() + " permutations (should be 1 for empty permutation)");
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Backtracking: " + time1a + " ns");
        System.out.println("  Swap-based: " + time1b + " ns");
        System.out.println("  Iterative: " + time1c + " ns");
        
        // Performance test with larger input
        System.out.println("\nTest 7: Larger input performance");
        int[] nums7 = {1, 2, 3, 4, 5, 6}; // Maximum size for constraints
        startTime = System.nanoTime();
        List<List<Integer>> result7a = solution.permute(nums7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result7b = solution.permuteSwap(nums7);
        long time7b = System.nanoTime() - startTime;
        
        System.out.println("Larger input (6 elements, 720 permutations):");
        System.out.println("  Backtracking: " + time7a + " ns, Permutations: " + result7a.size());
        System.out.println("  Swap-based: " + time7b + " ns, Permutations: " + result7b.size());
        
        // Compare all approaches for consistency
        System.out.println("\nTest 8: Consistency Check");
        int[] testNums = {1, 2, 3};
        List<List<Integer>> r1 = solution.permute(testNums);
        List<List<Integer>> r2 = solution.permuteSwap(testNums);
        List<List<Integer>> r3 = solution.permuteIterative(testNums);
        List<List<Integer>> r4 = solution.permuteDeque(testNums);
        List<List<Integer>> r5 = solution.permuteListRemoval(testNums);
        List<List<Integer>> r6 = solution.permuteLexicographic(testNums);
        
        boolean consistent = arePermutationsEqual(r1, r2) && arePermutationsEqual(r1, r3) && 
                           arePermutationsEqual(r1, r4) && arePermutationsEqual(r1, r5) && 
                           arePermutationsEqual(r1, r6);
        System.out.println("All approaches consistent: " + consistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BACKTRACKING ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We build permutations incrementally by trying all unused");
        System.out.println("elements at each position. The used array ensures we don't");
        System.out.println("reuse elements. At each step:");
        System.out.println("  1. Try each unused element in current position");
        System.out.println("  2. Mark it as used and recursively build the rest");
        System.out.println("  3. Backtrack (unmark and remove)");
        
        System.out.println("\nWhy Backtracking works for permutations:");
        System.out.println("1. Completeness: Generates all n! permutations");
        System.out.println("2. No Duplicates: Used array prevents element reuse");
        System.out.println("3. Efficiency: O(n * n!) is optimal for output size");
        System.out.println("4. Simplicity: Easy to understand and implement");
        
        System.out.println("\nVisual Example (nums = [1,2,3]):");
        System.out.println("Start: [], used=[false,false,false]");
        System.out.println("  Use 1: [1], used=[true,false,false]");
        System.out.println("    Use 2: [1,2], used=[true,true,false]");
        System.out.println("      Use 3: [1,2,3] -> FOUND");
        System.out.println("    Use 3: [1,3], used=[true,false,true]");
        System.out.println("      Use 2: [1,3,2] -> FOUND");
        System.out.println("  Use 2: [2], used=[false,true,false]");
        System.out.println("    ... and so on");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking with Used Array (RECOMMENDED):");
        System.out.println("   Time: O(n * n!) - Optimal for output size");
        System.out.println("   Space: O(n) - Recursion stack and used array");
        System.out.println("   How it works:");
        System.out.println("     - Track used elements with boolean array");
        System.out.println("     - Build permutation incrementally");
        System.out.println("     - Try all unused elements at each position");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and widely used");
        System.out.println("     - Easy to understand and implement");
        System.out.println("     - Handles distinct elements perfectly");
        System.out.println("   Cons:");
        System.out.println("     - Extra O(n) space for used array");
        System.out.println("   Best for: Interview settings, general use");
        
        System.out.println("\n2. Swap-based Backtracking:");
        System.out.println("   Time: O(n * n!) - Same as backtracking");
        System.out.println("   Space: O(n) - Recursion stack only");
        System.out.println("   How it works:");
        System.out.println("     - Generate permutations by swapping elements");
        System.out.println("     - First n positions are fixed, recurse on rest");
        System.out.println("     - No extra used array needed");
        System.out.println("   Pros:");
        System.out.println("     - No extra space for used array");
        System.out.println("     - In-place modification");
        System.out.println("   Cons:");
        System.out.println("     - Modifies input array (need to backtrack swaps)");
        System.out.println("     - Less intuitive than used array approach");
        System.out.println("   Best for: When memory optimization is important");
        
        System.out.println("\n3. Iterative (BFS-like):");
        System.out.println("   Time: O(n * n!) - Same as backtracking");
        System.out.println("   Space: O(n * n!) - Stores all intermediate permutations");
        System.out.println("   How it works:");
        System.out.println("     - Build permutations level by level");
        System.out.println("     - Insert new element in all possible positions");
        System.out.println("     - No recursion needed");
        System.out.println("   Pros:");
        System.out.println("     - No recursion depth limits");
        System.out.println("     - Clear level-by-level construction");
        System.out.println("   Cons:");
        System.out.println("     - High memory usage");
        System.out.println("     - Less efficient for large n");
        System.out.println("   Best for: Small n, educational purposes");
        
        System.out.println("\n4. Lexicographic (Next Permutation):");
        System.out.println("   Time: O(n * n!) - Generate in sorted order");
        System.out.println("   Space: O(1) extra space - In-place generation");
        System.out.println("   How it works:");
        System.out.println("     - Start with sorted array");
        System.out.println("     - Repeatedly generate next permutation");
        System.out.println("     - Based on specific swapping pattern");
        System.out.println("   Pros:");
        System.out.println("     - Minimal extra space");
        System.out.println("     - Generates in lexicographic order");
        System.out.println("   Cons:");
        System.out.println("     - More complex algorithm");
        System.out.println("     - Requires understanding of next permutation");
        System.out.println("   Best for: When lexicographic order is required");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("OPTIMIZATION STRATEGIES:");
        System.out.println("1. Early Termination: Return when permutation complete");
        System.out.println("2. Efficient Data Structures: Use arrays instead of lists when possible");
        System.out.println("3. In-place Modification: Swap-based avoids extra storage");
        System.out.println("4. Memory Reuse: Reuse data structures when possible");
        System.out.println("5. Algorithm Choice: Choose based on constraints and requirements");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Backtracking + Used Array - most expected solution");
        System.out.println("2. Explain the used array concept clearly");
        System.out.println("3. Mention time/space complexity in terms of n!");
        System.out.println("4. Discuss alternative approaches (swap-based, iterative)");
        System.out.println("5. Handle edge cases: single element, empty array");
        System.out.println("6. Connect to related problems (Permutations II, Combinations)");
        System.out.println("7. Write clean, well-commented backtracking code");
        System.out.println("8. If time permits, implement swap-based approach");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to check if two lists of permutations are equal (order doesn't matter)
     */
    private static boolean arePermutationsEqual(List<List<Integer>> list1, List<List<Integer>> list2) {
        if (list1.size() != list2.size()) return false;
        
        // Convert to set of lists for comparison (order matters in permutations)
        Set<List<Integer>> set1 = new HashSet<>(list1);
        Set<List<Integer>> set2 = new HashSet<>(list2);
        
        return set1.equals(set2);
    }
}
