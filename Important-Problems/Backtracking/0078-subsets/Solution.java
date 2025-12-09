
## Problems/Arrays-Hashing/0078-subsets/Solution.java

```java
/**
 * 78. Subsets
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an integer array nums of unique elements, return all possible subsets (the power set).
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 * 
 * Key Insights:
 * 1. There are 2^n possible subsets for n elements
 * 2. Each element can be either included or excluded
 * 3. Backtracking builds subsets incrementally
 * 4. Bit manipulation can represent subsets using bitmask
 * 
 * Approach (Backtracking):
 * 1. Start with empty subset
 * 2. For each element, make two recursive calls:
 *    - Include the current element
 *    - Exclude the current element
 * 3. Add current subset to result when all elements processed
 * 
 * Time Complexity: O(n * 2^n) - 2^n subsets, each taking O(n) to build
 * Space Complexity: O(n) - Recursion stack depth
 * 
 * Tags: Array, Backtracking, Bit Manipulation, DFS
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Backtracking (DFS) - RECOMMENDED
     * O(n * 2^n) time, O(n) space
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        // Add the current subset to result
        result.add(new ArrayList<>(current));
        
        // Generate subsets by including each element from start to end
        for (int i = start; i < nums.length; i++) {
            // Include nums[i]
            current.add(nums[i]);
            // Explore further with this inclusion
            backtrack(nums, i + 1, current, result);
            // Backtrack - exclude nums[i]
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Approach 2: Iterative (Cascading)
     * O(n * 2^n) time, O(n * 2^n) space for output
     */
    public List<List<Integer>> subsetsIterative(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>()); // Start with empty subset
        
        for (int num : nums) {
            int size = result.size();
            for (int i = 0; i < size; i++) {
                List<Integer> newSubset = new ArrayList<>(result.get(i));
                newSubset.add(num);
                result.add(newSubset);
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Bit Manipulation
     * O(n * 2^n) time, O(n * 2^n) space for output
     */
    public List<List<Integer>> subsetsBitmask(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        int totalSubsets = 1 << n; // 2^n
        
        for (int mask = 0; mask < totalSubsets; mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            result.add(subset);
        }
        
        return result;
    }
    
    /**
     * Approach 4: Backtracking with Path Tracking
     * O(n * 2^n) time, O(n) space - Alternative backtracking
     */
    public List<List<Integer>> subsetsBacktracking2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void dfs(int[] nums, int index, List<Integer> path, List<List<Integer>> result) {
        if (index == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        // Exclude current element
        dfs(nums, index + 1, path, result);
        
        // Include current element
        path.add(nums[index]);
        dfs(nums, index + 1, path, result);
        path.remove(path.size() - 1);
    }
    
    /**
     * Approach 5: Lexicographic (Binary Sorted) Subsets
     * O(n * 2^n) time, O(n * 2^n) space - Generate in lex order
     */
    public List<List<Integer>> subsetsLexicographic(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        Arrays.sort(nums); // Ensure lexicographic order
        
        for (int i = 0; i < (1 << n); i++) {
            List<Integer> subset = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subset.add(nums[j]);
                }
            }
            result.add(subset);
        }
        
        // Sort result lexicographically (optional)
        result.sort((a, b) -> {
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                int cmp = Integer.compare(a.get(i), b.get(i));
                if (cmp != 0) return cmp;
            }
            return Integer.compare(a.size(), b.size());
        });
        
        return result;
    }
    
    /**
     * Approach 6: Iterative with Queue (BFS)
     * O(n * 2^n) time, O(n * 2^n) space - Breadth-first approach
     */
    public List<List<Integer>> subsetsBFS(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.offer(new ArrayList<>());
        
        for (int num : nums) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                List<Integer> current = queue.poll();
                result.add(current);
                
                // Create new subset by adding current number
                List<Integer> newSubset = new ArrayList<>(current);
                newSubset.add(num);
                queue.offer(newSubset);
                
                // Also keep the original subset
                queue.offer(new ArrayList<>(current));
            }
        }
        
        // Add remaining subsets from queue
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }
        
        return result;
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    private void visualizeBacktracking(int[] nums) {
        System.out.println("\nSubsets Backtracking Visualization:");
        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("\nBacktracking Tree:");
        System.out.println("Depth | Current Subset | Action");
        System.out.println("------|----------------|--------");
        
        visualizeDFS(nums, 0, new ArrayList<>(), 0);
    }
    
    private void visualizeDFS(int[] nums, int index, List<Integer> current, int depth) {
        String indent = "  ".repeat(depth);
        System.out.printf("%s%d | %-14s | Add to result%n", indent, depth, current.toString());
        
        for (int i = index; i < nums.length; i++) {
            System.out.printf("%s%d | %-14s | Include %d%n", indent, depth, current.toString(), nums[i]);
            current.add(nums[i]);
            visualizeDFS(nums, i + 1, current, depth + 1);
            System.out.printf("%s%d | %-14s | Backtrack (remove %d)%n", indent, depth, current.toString(), nums[i]);
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * Helper method to show iterative process
     */
    private void visualizeIterative(int[] nums) {
        System.out.println("\nIterative Subset Generation:");
        System.out.println("Input: " + Arrays.toString(nums));
        
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        
        System.out.println("\nStep | New Element | Current Result");
        System.out.println("-----|-------------|----------------");
        System.out.printf("%4d | %11s | %s%n", 0, "Start", result.toString());
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int size = result.size();
            
            for (int j = 0; j < size; j++) {
                List<Integer> newSubset = new ArrayList<>(result.get(j));
                newSubset.add(num);
                result.add(newSubset);
            }
            
            System.out.printf("%4d | %11d | %s%n", i + 1, num, result.toString());
        }
    }
    
    /**
     * Helper method to show bitmask representation
     */
    private void visualizeBitmask(int[] nums) {
        System.out.println("\nBitmask Subset Generation:");
        System.out.println("Input: " + Arrays.toString(nums));
        
        int n = nums.length;
        int totalSubsets = 1 << n;
        
        System.out.println("\nBitmask | Binary | Subset");
        System.out.println("--------|--------|--------");
        
        for (int mask = 0; mask < totalSubsets; mask++) {
            List<Integer> subset = new ArrayList<>();
            String binary = String.format("%" + n + "s", Integer.toBinaryString(mask))
                           .replace(' ', '0');
            
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            
            System.out.printf("%7d | %6s | %s%n", mask, binary, subset.toString());
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Subsets Solution:");
        System.out.println("=========================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] nums1 = {1, 2, 3};
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(),
            Arrays.asList(1),
            Arrays.asList(2),
            Arrays.asList(1, 2),
            Arrays.asList(3),
            Arrays.asList(1, 3),
            Arrays.asList(2, 3),
            Arrays.asList(1, 2, 3)
        );
        
        long startTime = System.nanoTime();
        List<List<Integer>> result1a = solution.subsets(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1b = solution.subsetsIterative(nums1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result1c = solution.subsetsBitmask(nums1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = areSubsetsEqual(result1a, expected1);
        boolean test1b = areSubsetsEqual(result1b, expected1);
        boolean test1c = areSubsetsEqual(result1c, expected1);
        
        System.out.println("Backtracking: " + result1a.size() + " subsets - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Iterative: " + result1b.size() + " subsets - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Bitmask: " + result1c.size() + " subsets - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the processes
        solution.visualizeBacktracking(nums1);
        solution.visualizeIterative(nums1);
        solution.visualizeBitmask(nums1);
        
        // Test case 2: Single element
        System.out.println("\nTest 2: Single element");
        int[] nums2 = {0};
        List<List<Integer>> expected2 = Arrays.asList(
            Arrays.asList(),
            Arrays.asList(0)
        );
        
        List<List<Integer>> result2a = solution.subsets(nums2);
        System.out.println("Single element: " + result2a.size() + " subsets - " + 
                         (areSubsetsEqual(result2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Empty array (edge case)
        System.out.println("\nTest 3: Empty array");
        int[] nums3 = {};
        List<List<Integer>> expected3 = Arrays.asList(Arrays.asList());
        
        List<List<Integer>> result3a = solution.subsets(nums3);
        System.out.println("Empty array: " + result3a.size() + " subsets - " + 
                         (areSubsetsEqual(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Two elements
        System.out.println("\nTest 4: Two elements");
        int[] nums4 = {1, 2};
        List<List<Integer>> result4a = solution.subsets(nums4);
        System.out.println("Two elements: " + result4a.size() + " subsets");
        System.out.println("Subsets: " + result4a);
        
        // Test case 5: Four elements
        System.out.println("\nTest 5: Four elements");
        int[] nums5 = {1, 2, 3, 4};
        List<List<Integer>> result5a = solution.subsets(nums5);
        System.out.println("Four elements: " + result5a.size() + " subsets (expected: 16)");
        
        // Performance comparison
        System.out.println("\nTest 6: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Backtracking: " + time1a + " ns");
        System.out.println("  Iterative: " + time1b + " ns");
        System.out.println("  Bitmask: " + time1c + " ns");
        
        // Performance test with maximum input size
        System.out.println("\nTest 7: Maximum input performance");
        int[] nums7 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Maximum size
        startTime = System.nanoTime();
        List<List<Integer>> result7a = solution.subsets(nums7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result7b = solution.subsetsIterative(nums7);
        long time7b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<Integer>> result7c = solution.subsetsBitmask(nums7);
        long time7c = System.nanoTime() - startTime;
        
        System.out.println("Maximum input (10 elements):");
        System.out.println("  Backtracking: " + time7a + " ns, Subsets: " + result7a.size());
        System.out.println("  Iterative: " + time7b + " ns, Subsets: " + result7b.size());
        System.out.println("  Bitmask: " + time7c + " ns, Subsets: " + result7c.size());
        
        // Compare all approaches for consistency
        System.out.println("\nTest 8: Consistency Check");
        int[] testNums = {1, 2, 3};
        List<List<Integer>> r1 = solution.subsets(testNums);
        List<List<Integer>> r2 = solution.subsetsIterative(testNums);
        List<List<Integer>> r3 = solution.subsetsBitmask(testNums);
        List<List<Integer>> r4 = solution.subsetsBacktracking2(testNums);
        List<List<Integer>> r5 = solution.subsetsBFS(testNums);
        
        boolean consistent = areSubsetsEqual(r1, r2) && areSubsetsEqual(r1, r3) && 
                           areSubsetsEqual(r1, r4) && areSubsetsEqual(r1, r5);
        System.out.println("All approaches consistent: " + consistent);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BACKTRACKING ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("Each element has two choices: include or exclude.");
        System.out.println("We build subsets incrementally using DFS.");
        System.out.println("At each step, we:");
        System.out.println("  1. Add current subset to result");
        System.out.println("  2. For each remaining element:");
        System.out.println("     - Include it in the subset");
        System.out.println("     - Recursively process remaining elements");
        System.out.println("     - Backtrack (remove the element)");
        
        System.out.println("\nWhy Backtracking works:");
        System.out.println("1. Completeness: Generates all 2^n subsets");
        System.out.println("2. No Duplicates: Since we process elements in order");
        System.out.println("3. Efficiency: O(n * 2^n) is optimal for output size");
        System.out.println("4. Natural Order: Generates subsets by size");
        
        System.out.println("\nVisual Example (nums = [1,2,3]):");
        System.out.println("Start: []");
        System.out.println("  Include 1: [1]");
        System.out.println("    Include 2: [1,2]");
        System.out.println("      Include 3: [1,2,3]");
        System.out.println("    Exclude 3: [1,2] -> backtrack");
        System.out.println("  Exclude 2: [1] -> backtrack");
        System.out.println("    Include 3: [1,3]");
        System.out.println("  Exclude 1: [] -> backtrack");
        System.out.println("  Include 2: [2]");
        System.out.println("    Include 3: [2,3]");
        System.out.println("  Exclude 2: [] -> backtrack");
        System.out.println("  Include 3: [3]");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Backtracking (DFS) - RECOMMENDED:");
        System.out.println("   Time: O(n * 2^n) - Optimal for output size");
        System.out.println("   Space: O(n) - Recursion stack depth");
        System.out.println("   How it works:");
        System.out.println("     - Build subsets incrementally using DFS");
        System.out.println("     - At each step, include/exclude elements");
        System.out.println("     - Add completed subsets to result");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive and widely used");
        System.out.println("     - Easy to understand and implement");
        System.out.println("     - Natural extension to related problems");
        System.out.println("   Cons:");
        System.out.println("     - Recursion overhead for large n");
        System.out.println("   Best for: Interview settings, general use");
        
        System.out.println("\n2. Iterative (Cascading):");
        System.out.println("   Time: O(n * 2^n) - Same as backtracking");
        System.out.println("   Space: O(n * 2^n) - Output storage");
        System.out.println("   How it works:");
        System.out.println("     - Start with empty set");
        System.out.println("     - For each element, duplicate all existing subsets");
        System.out.println("     - Add current element to duplicated subsets");
        System.out.println("   Pros:");
        System.out.println("     - No recursion overhead");
        System.out.println("     - Very clear and straightforward");
        System.out.println("   Cons:");
        System.out.println("     - Uses more explicit memory");
        System.out.println("   Best for: When avoiding recursion");
        
        System.out.println("\n3. Bit Manipulation:");
        System.out.println("   Time: O(n * 2^n) - Enumerate all bitmasks");
        System.out.println("   Space: O(n * 2^n) - Output storage");
        System.out.println("   How it works:");
        System.out.println("     - Use integers as bitmasks (0 to 2^n-1)");
        System.out.println("     - Each bit represents inclusion of element");
        System.out.println("     - Convert bitmask to subset");
        System.out.println("   Pros:");
        System.out.println("     - Elegant mathematical approach");
        System.out.println("     - No recursion or complex logic");
        System.out.println("   Cons:");
        System.out.println("     - Limited to n ≤ 31 (for 32-bit integers)");
        System.out.println("     - Less intuitive for some");
        System.out.println("   Best for: Bit manipulation practice");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Number of subsets: 2^n (power set size)");
        System.out.println("2. Each subset corresponds to a binary number 0 to 2^n-1");
        System.out.println("3. The empty set is always included");
        System.out.println("4. For n elements, there are:");
        System.out.println("   - C(n,0) = 1 subset of size 0");
        System.out.println("   - C(n,1) = n subsets of size 1");
        System.out.println("   - C(n,2) = n(n-1)/2 subsets of size 2");
        System.out.println("   - ...");
        System.out.println("   - C(n,n) = 1 subset of size n");
        System.out.println("5. Total subsets = Σ C(n,k) for k=0 to n = 2^n");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Backtracking - it's the most expected solution");
        System.out.println("2. Clearly explain the include/exclude decision at each step");
        System.out.println("3. Mention the time/space complexity (O(n * 2^n))");
        System.out.println("4. Discuss alternative approaches (iterative, bitmask)");
        System.out.println("5. Handle edge cases: empty array, single element");
        System.out.println("6. Connect to related problems (Subsets II, Combinations)");
        System.out.println("7. Write clean, well-commented code");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Helper method to check if two lists of subsets are equal (order doesn't matter)
     */
    private static boolean areSubsetsEqual(List<List<Integer>> list1, List<List<Integer>> list2) {
        if (list1.size() != list2.size()) return false;
        
        // Convert to set of sets for comparison
        Set<Set<Integer>> set1 = new HashSet<>();
        Set<Set<Integer>> set2 = new HashSet<>();
        
        for (List<Integer> subset : list1) {
            set1.add(new HashSet<>(subset));
        }
        
        for (List<Integer> subset : list2) {
            set2.add(new HashSet<>(subset));
        }
        
        return set1.equals(set2);
    }
}
