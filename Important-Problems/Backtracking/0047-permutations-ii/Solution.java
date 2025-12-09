
## Solution.java

```java
/**
 * 47. Permutations II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a collection of numbers, nums, that might contain duplicates, 
 * return all possible unique permutations in any order.
 * 
 * Key Insights:
 * 1. Sorting helps identify duplicates easily
 * 2. Use backtracking with careful pruning to avoid duplicate permutations
 * 3. Skip duplicates at the same recursion level
 * 4. Track used elements to avoid reusing the same element
 * 
 * Approach (Backtracking with Pruning):
 * 1. Sort the array to group duplicates together
 * 2. Use backtracking to generate permutations
 * 3. Skip duplicates when they appear at the same level
 * 4. Use boolean array to track used elements
 * 5. Add complete permutations to result
 * 
 * Time Complexity: O(n × n!)
 * Space Complexity: O(n) for recursion stack
 * 
 * Tags: Array, Backtracking, Recursion
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Backtracking with Sorting and Pruning - RECOMMENDED
     * O(n × n!) time, O(n) space
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // Sort the array to handle duplicates
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        backtrack(nums, used, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        // Base case: if current permutation is complete
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            // Skip if element is already used
            if (used[i]) {
                continue;
            }
            
            // Skip duplicates: if current element is same as previous AND previous wasn't used
            // This ensures we skip duplicates at the same level of recursion
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            
            // Choose the current element
            used[i] = true;
            current.add(nums[i]);
            
            // Recursively build the rest of the permutation
            backtrack(nums, used, current, result);
            
            // Unchoose the current element (backtrack)
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
    
    /**
     * Approach 2: Backtracking with HashSet (Alternative)
     * O(n × n!) time, O(n) space - Uses HashSet to avoid duplicates
     */
    public List<List<Integer>> permuteUniqueHashSet(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        backtrackHashSet(nums, 0, result);
        return result;
    }
    
    private void backtrackHashSet(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }
        
        Set<Integer> used = new HashSet<>();
        for (int i = start; i < nums.length; i++) {
            // Skip if we've already used this value at this position
            if (used.contains(nums[i])) {
                continue;
            }
            
            used.add(nums[i]);
            swap(nums, start, i);
            backtrackHashSet(nums, start + 1, result);
            swap(nums, start, i); // backtrack
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Approach 3: Using Next Permutation
     * O(n × n!) time, O(1) extra space
     */
    public List<List<Integer>> permuteUniqueNextPermutation(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        Arrays.sort(nums);
        
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
            return false; // no next permutation
        }
        
        // Find element just larger than nums[i]
        int j = nums.length - 1;
        while (j > i && nums[j] <= nums[i]) {
            j--;
        }
        
        // Swap and reverse
        swap(nums, i, j);
        reverse(nums, i + 1, nums.length - 1);
        return true;
    }
    
    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
    
    /**
     * Approach 4: Using Frequency Map (Counting Backtrack)
     * O(n × n!) time, O(n) space
     */
    public List<List<Integer>> permuteUniqueFrequency(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // Count frequency of each number
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }
        
        backtrackFrequency(frequency, nums.length, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrackFrequency(Map<Integer, Integer> frequency, int remaining, 
                                   List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int num : frequency.keySet()) {
            int count = frequency.get(num);
            if (count > 0) {
                // Choose this number
                frequency.put(num, count - 1);
                current.add(num);
                
                // Recurse
                backtrackFrequency(frequency, remaining - 1, current, result);
                
                // Backtrack
                current.remove(current.size() - 1);
                frequency.put(num, count);
            }
        }
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    private void visualizeBacktracking(int[] nums) {
        System.out.println("\nBacktracking Algorithm Visualization:");
        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("Sorted: " + Arrays.toString(Arrays.copyOf(nums, nums.length)));
        
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        List<Integer> current = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        
        System.out.println("\nBacktracking Steps:");
        System.out.println("Depth | Current State | Used Array | Action");
        System.out.println("------|---------------|------------|--------");
        
        visualizeBacktrackHelper(nums, used, current, result, 0);
        
        System.out.println("\nFinal Result: " + result);
    }
    
    private void visualizeBacktrackHelper(int[] nums, boolean[] used, List<Integer> current, 
                                        List<List<Integer>> result, int depth) {
        if (current.size() == nums.length) {
            System.out.printf("%5d | %13s | %10s | ADD TO RESULT: %s%n", 
                            depth, current, Arrays.toString(used), current);
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                System.out.printf("%5d | %13s | %10s | SKIP (used): nums[%d]=%d%n", 
                                depth, current, Arrays.toString(used), i, nums[i]);
                continue;
            }
            
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                System.out.printf("%5d | %13s | %10s | SKIP (duplicate): nums[%d]=%d, nums[%d]=%d%n", 
                                depth, current, Arrays.toString(used), i, nums[i], i-1, nums[i-1]);
                continue;
            }
            
            System.out.printf("%5d | %13s | %10s | CHOOSE: nums[%d]=%d%n", 
                            depth, current, Arrays.toString(used), i, nums[i]);
            
            used[i] = true;
            current.add(nums[i]);
            
            visualizeBacktrackHelper(nums, used, current, result, depth + 1);
            
            System.out.printf("%5d | %13s | %10s | BACKTRACK: remove %d%n", 
                            depth, current, Arrays.toString(used), nums[i]);
            
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Permutations II Solution:");
        System.out.println("=================================");
        
        // Test case 1: Standard example with duplicates
        System.out.println("\nTest 1: Standard example with duplicates");
        int[] nums1 = {1, 1, 2};
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(1, 1, 2),
            Arrays.asList(1, 2, 1),
            Arrays.asList(2, 1, 1)
        );
        
        long startTime = System.nanoTime();
        List<List<Integer>> result1a = solution.permuteUnique(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1b = solution.permuteUniqueHashSet(nums1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1c = solution.permuteUniqueNextPermutation(nums1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1d = solution.permuteUniqueFrequency(nums1);
        long time1d = System.nanoTime() - startTime;
        
        // Sort results for comparison
        sortResult(result1a);
        sortResult(result1b);
        sortResult(result1c);
        sortResult(result1d);
        sortResult(expected1);
        
        boolean test1a = result1a.equals(expected1);
        boolean test1b = result1b.equals(expected1);
        boolean test1c = result1c.equals(expected1);
        boolean test1d = result1d.equals(expected1);
        
        System.out.println("Backtracking: " + result1a.size() + " permutations - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("HashSet: " + result1b.size() + " permutations - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Next Permutation: " + result1c.size() + " permutations - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Frequency: " + result1d.size() + " permutations - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the backtracking process
        solution.visualizeBacktracking(nums1);
        
        // Test case 2: No duplicates
        System.out.println("\nTest 2: No duplicates");
        int[] nums2 = {1, 2, 3};
        List<List<Integer>> result2a = solution.permuteUnique(nums2);
        System.out.println("No duplicates: " + result2a.size() + " permutations - " + 
                         (result2a.size() == 6 ? "PASSED" : "FAILED"));
        
        // Test case 3: All duplicates
        System.out.println("\nTest 3: All duplicates");
        int[] nums3 = {1, 1, 1};
        List<List<Integer>> result3a = solution.permuteUnique(nums3);
        System.out.println("All duplicates: " + result3a.size() + " permutations - " + 
                         (result3a.size() == 1 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single element
        System.out.println("\nTest 4: Single element");
        int[] nums4 = {1};
        List<List<Integer>> result4a = solution.permuteUnique(nums4);
        System.out.println("Single element: " + result4a.size() + " permutations - " + 
                         (result4a.size() == 1 ? "PASSED" : "FAILED"));
        
        // Test case 5: Two elements with duplicates
        System.out.println("\nTest 5: Two elements with duplicates");
        int[] nums5 = {1, 1};
        List<List<Integer>> result5a = solution.permuteUnique(nums5);
        System.out.println("Two duplicates: " + result5a.size() + " permutations - " + 
                         (result5a.size() == 1 ? "PASSED" : "FAILED"));
        
        // Test case 6: Mixed numbers with duplicates
        System.out.println("\nTest 6: Mixed numbers with duplicates");
        int[] nums6 = {2, 2, 1, 1};
        List<List<Integer>> result6a = solution.permuteUnique(nums6);
        System.out.println("Mixed duplicates: " + result6a.size() + " permutations - " + 
                         (result6a.size() == 6 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 7: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Backtracking: " + time1a + " ns");
        System.out.println("  HashSet: " + time1b + " ns");
        System.out.println("  Next Permutation: " + time1c + " ns");
        System.out.println("  Frequency: " + time1d + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BACKTRACKING WITH PRUNING EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("When we have duplicates, we need to ensure we don't generate");
        System.out.println("the same permutation multiple times.");
        
        System.out.println("\nThe Pruning Condition:");
        System.out.println("if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) { continue; }");
        System.out.println("This means: Skip the current element if:");
        System.out.println("1. It's not the first element (i > 0)");
        System.out.println("2. It's the same as the previous element (nums[i] == nums[i - 1])");
        System.out.println("3. The previous element wasn't used (!used[i - 1])");
        
        System.out.println("\nWhy this works:");
        System.out.println("- We only skip duplicates at the same level of recursion");
        System.out.println("- If the previous duplicate wasn't used, it means we're at the same level");
        System.out.println("- This ensures we generate each unique permutation exactly once");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking with Pruning (RECOMMENDED):");
        System.out.println("   Time: O(n × n!) - Generate n! permutations, each takes O(n)");
        System.out.println("   Space: O(n) - Recursion stack and used array");
        System.out.println("   How it works:");
        System.out.println("     - Sort array to group duplicates");
        System.out.println("     - Use backtracking with pruning condition");
        System.out.println("     - Skip duplicates at same recursion level");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and easy to understand");
        System.out.println("     - Efficient duplicate handling");
        System.out.println("     - Standard approach for permutation problems");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of pruning condition");
        System.out.println("     - O(n!) time complexity (inherent to problem)");
        System.out.println("   Best for: Interview settings, most use cases");
        
        System.out.println("\n2. HashSet Backtracking:");
        System.out.println("   Time: O(n × n!) - Same as above with HashSet operations");
        System.out.println("   Space: O(n) - Recursion stack and HashSet");
        System.out.println("   How it works:");
        System.out.println("     - Use HashSet to track used values at each position");
        System.out.println("     - Swap elements in-place");
        System.out.println("     - Generate permutations without sorting");
        System.out.println("   Pros:");
        System.out.println("     - Doesn't require sorting");
        System.out.println("     - In-place swapping saves space");
        System.out.println("   Cons:");
        System.out.println("     - HashSet operations add overhead");
        System.out.println("     - Less intuitive than standard backtracking");
        System.out.println("   Best for: When you want to avoid sorting");
        
        System.out.println("\n3. Next Permutation:");
        System.out.println("   Time: O(n × n!) - Generate each next permutation");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Sort array and generate permutations in lex order");
        System.out.println("     - Use next permutation algorithm repeatedly");
        System.out.println("     - Stop when no more permutations");
        System.out.println("   Pros:");
        System.out.println("     - O(1) extra space (excluding output)");
        System.out.println("     - Generates permutations in sorted order");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Harder to understand and explain");
        System.out.println("   Best for: When space is critical");
        
        System.out.println("\n4. Frequency Map Backtracking:");
        System.out.println("   Time: O(n × n!) - Same complexity");
        System.out.println("   Space: O(n) - Frequency map and recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Count frequency of each number");
        System.out.println("     - Use backtracking with frequency map");
        System.out.println("     - Decrement count when using a number");
        System.out.println("   Pros:");
        System.out.println("     - Very clear duplicate handling");
        System.out.println("     - Easy to extend for other problems");
        System.out.println("   Cons:");
        System.out.println("     - Extra overhead of frequency map");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: Learning, combinatorial problems");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Number of unique permutations with duplicates:");
        System.out.println("   n! / (f1! × f2! × ... × fk!)");
        System.out.println("   where fi is frequency of i-th distinct element");
        System.out.println("2. For [1,1,2]: 3! / (2! × 1!) = 6 / 2 = 3 permutations");
        System.out.println("3. For [1,1,1]: 3! / 3! = 6 / 6 = 1 permutation");
        System.out.println("4. The pruning condition ensures we generate exactly this many");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with backtracking with pruning - it's the expected solution");
        System.out.println("2. Explain why sorting is necessary for duplicate handling");
        System.out.println("3. Clearly explain the pruning condition and why it works");
        System.out.println("4. Discuss time/space complexity honestly");
        System.out.println("5. Mention alternative approaches and their trade-offs");
        System.out.println("6. Handle edge cases: all duplicates, single element, etc.");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    // Helper method to sort result for comparison
    private static void sortResult(List<List<Integer>> result) {
        for (List<Integer> list : result) {
            Collections.sort(list);
        }
        result.sort((a, b) -> {
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                int cmp = Integer.compare(a.get(i), b.get(i));
                if (cmp != 0) return cmp;
            }
            return Integer.compare(a.size(), b.size());
        });
    }
}
