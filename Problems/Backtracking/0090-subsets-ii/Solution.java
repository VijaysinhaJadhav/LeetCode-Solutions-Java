
## Solution.java

```java
/**
 * 90. Subsets II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an integer array nums that may contain duplicates, return all possible subsets.
 * The solution set must not contain duplicate subsets.
 * 
 * Key Insights:
 * 1. Sort the array first to handle duplicates easily
 * 2. Use backtracking to generate all subsets
 * 3. Skip duplicates at the same decision level: i > start && nums[i] == nums[i-1]
 * 4. Each element has two choices: include or exclude, but skip duplicate choices
 * 
 * Approach (Backtracking with Sorting):
 * 1. Sort the input array
 * 2. Use backtracking to generate subsets
 * 3. At each step, add current subset to result
 * 4. For each position, include current element and recurse
 * 5. Skip duplicates to avoid duplicate subsets
 * 
 * Time Complexity: O(n × 2^n)
 * Space Complexity: O(n) for recursion stack
 * 
 * Tags: Array, Backtracking, Bit Manipulation
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Backtracking with Sorting (Recommended)
     * O(n × 2^n) time, O(n) space
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // Sort to handle duplicates
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }
    
    private void backtrack(List<List<Integer>> result, List<Integer> temp, 
                          int[] nums, int start) {
        // Add current subset to result
        result.add(new ArrayList<>(temp));
        
        for (int i = start; i < nums.length; i++) {
            // Skip duplicates at the same decision level
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // Include current element
            temp.add(nums[i]);
            backtrack(result, temp, nums, i + 1);
            temp.remove(temp.size() - 1); // Backtrack
        }
    }
    
    /**
     * Approach 2: Iterative (BFS-like)
     * O(n × 2^n) time, O(n × 2^n) space
     * Build subsets level by level
     */
    public List<List<Integer>> subsetsWithDupIterative(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>()); // Start with empty subset
        
        int startIndex = 0;
        int subsetSize = 0;
        
        for (int i = 0; i < nums.length; i++) {
            // If current number is same as previous, only add to subsets created in previous step
            if (i > 0 && nums[i] == nums[i - 1]) {
                startIndex = subsetSize;
            } else {
                startIndex = 0;
            }
            
            subsetSize = result.size();
            for (int j = startIndex; j < subsetSize; j++) {
                List<Integer> newSubset = new ArrayList<>(result.get(j));
                newSubset.add(nums[i]);
                result.add(newSubset);
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Bit Manipulation with Set
     * O(n × 2^n) time, O(n × 2^n) space
     * Uses bitmasks but needs set to handle duplicates
     */
    public List<List<Integer>> subsetsWithDupBitmask(int[] nums) {
        Arrays.sort(nums);
        Set<List<Integer>> resultSet = new HashSet<>();
        int n = nums.length;
        
        // Generate all bitmasks from 0 to 2^n - 1
        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            resultSet.add(subset);
        }
        
        return new ArrayList<>(resultSet);
    }
    
    /**
     * Approach 4: Backtracking with Detailed Duplicate Handling
     * O(n × 2^n) time, O(n) space
     * More explicit duplicate handling
     */
    public List<List<Integer>> subsetsWithDupDetailed(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        backtrackDetailed(result, new ArrayList<>(), nums, 0, new boolean[nums.length]);
        return result;
    }
    
    private void backtrackDetailed(List<List<Integer>> result, List<Integer> temp,
                                 int[] nums, int start, boolean[] used) {
        result.add(new ArrayList<>(temp));
        
        for (int i = start; i < nums.length; i++) {
            // Skip duplicates: if same as previous and previous wasn't used at this level
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            
            used[i] = true;
            temp.add(nums[i]);
            backtrackDetailed(result, temp, nums, i + 1, used);
            temp.remove(temp.size() - 1);
            used[i] = false;
        }
    }
    
    /**
     * Approach 5: Iterative with Duplicate Count
     * O(n × 2^n) time, O(n × 2^n) space
     * Handles duplicates by counting occurrences
     */
    public List<List<Integer>> subsetsWithDupCount(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        
        for (int i = 0; i < nums.length; ) {
            // Count duplicates
            int count = 0;
            int current = nums[i];
            while (i < nums.length && nums[i] == current) {
                count++;
                i++;
            }
            
            // For each existing subset, create new subsets with 1 to count duplicates
            int size = result.size();
            for (int j = 0; j < size; j++) {
                List<Integer> existing = result.get(j);
                for (int k = 1; k <= count; k++) {
                    List<Integer> newSubset = new ArrayList<>(existing);
                    // Add k copies of current number
                    for (int c = 0; c < k; c++) {
                        newSubset.add(current);
                    }
                    result.add(newSubset);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 6: Lexicographic Backtracking
     * O(n × 2^n) time, O(n) space
     * Generates subsets in lexicographic order
     */
    public List<List<Integer>> subsetsWithDupLexico(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        generateLexico(result, new ArrayList<>(), nums, 0);
        return result;
    }
    
    private void generateLexico(List<List<Integer>> result, List<Integer> temp,
                              int[] nums, int index) {
        if (index == nums.length) {
            result.add(new ArrayList<>(temp));
            return;
        }
        
        // Count how many duplicates we have
        int count = 1;
        while (index + count < nums.length && nums[index + count] == nums[index]) {
            count++;
        }
        
        // For each possible number of duplicates to include (0 to count)
        for (int i = 0; i <= count; i++) {
            // Add i copies of current number
            for (int j = 0; j < i; j++) {
                temp.add(nums[index]);
            }
            
            generateLexico(result, temp, nums, index + count);
            
            // Remove i copies
            for (int j = 0; j < i; j++) {
                temp.remove(temp.size() - 1);
            }
        }
    }
    
    /**
     * Helper method to visualize the backtracking process
     */
    public void visualizeBacktracking(int[] nums, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Input: " + Arrays.toString(nums));
        Arrays.sort(nums);
        System.out.println("Sorted: " + Arrays.toString(nums));
        
        if ("Backtracking".equals(approach)) {
            visualizeBacktrackingProcess(nums);
        } else if ("Iterative".equals(approach)) {
            visualizeIterativeProcess(nums);
        }
    }
    
    private void visualizeBacktrackingProcess(int[] nums) {
        System.out.println("\nBacktracking Process:");
        System.out.println("Level | Current Subset | Action | New Subsets");
        System.out.println("------|----------------|--------|------------");
        
        List<List<Integer>> result = new ArrayList<>();
        backtrackWithVisualization(result, new ArrayList<>(), nums, 0, 0);
        
        System.out.println("\nFinal Result (" + result.size() + " subsets):");
        for (int i = 0; i < result.size(); i++) {
            System.out.printf("%2d: %s%n", i, result.get(i));
        }
    }
    
    private void backtrackWithVisualization(List<List<Integer>> result, 
                                          List<Integer> temp, int[] nums, 
                                          int start, int level) {
        String indent = "  ".repeat(level);
        System.out.printf("%5d | %14s | ADD    | %s%n", level, temp, temp);
        result.add(new ArrayList<>(temp));
        
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                System.out.printf("%5d | %14s | SKIP   | nums[%d] = %d (duplicate)%n", 
                    level, temp, i, nums[i]);
                continue;
            }
            
            System.out.printf("%5d | %14s | ADD    | nums[%d] = %d%n", 
                level, temp, i, nums[i]);
            temp.add(nums[i]);
            backtrackWithVisualization(result, temp, nums, i + 1, level + 1);
            temp.remove(temp.size() - 1);
            System.out.printf("%5d | %14s | REMOVE | nums[%d] = %d%n", 
                level, temp, i, nums[i]);
        }
    }
    
    private void visualizeIterativeProcess(int[] nums) {
        System.out.println("\nIterative Process:");
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        
        System.out.println("Step | Current Number | Start | Result Size | New Subsets");
        System.out.println("-----|----------------|-------|-------------|------------");
        
        int step = 1;
        for (int i = 0; i < nums.length; i++) {
            int startIndex = 0;
            if (i > 0 && nums[i] == nums[i - 1]) {
                startIndex = result.size() / 2; // Simplified for visualization
            }
            
            int currentSize = result.size();
            System.out.printf("%4d | %14d | %5d | %11d | ", 
                step++, nums[i], startIndex, currentSize);
            
            List<String> newSubsets = new ArrayList<>();
            for (int j = startIndex; j < currentSize; j++) {
                List<Integer> newSubset = new ArrayList<>(result.get(j));
                newSubset.add(nums[i]);
                result.add(newSubset);
                newSubsets.add(newSubset.toString());
            }
            System.out.println(newSubsets);
        }
        
        System.out.println("\nFinal Result (" + result.size() + " subsets):");
        for (int i = 0; i < result.size(); i++) {
            System.out.printf("%2d: %s%n", i, result.get(i));
        }
    }
    
    /**
     * Helper method to generate test cases
     */
    public int[] generateTestCase(String type) {
        switch (type) {
            case "simple":
                return new int[]{1, 2, 2};
            case "single":
                return new int[]{0};
            case "all_same":
                return new int[]{1, 1, 1};
            case "no_duplicates":
                return new int[]{1, 2, 3};
            case "mixed":
                return new int[]{4, 4, 4, 1, 4};
            case "negative":
                return new int[]{-1, 0, 1, -1};
            default:
                return new int[]{1, 2, 2};
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Subsets II:");
        System.out.println("===================\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: [1,2,2]");
        int[] nums1 = {1, 2, 2};
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(),
            Arrays.asList(1),
            Arrays.asList(1, 2),
            Arrays.asList(1, 2, 2),
            Arrays.asList(2),
            Arrays.asList(2, 2)
        );
        testImplementation(solution, nums1, expected1, "Backtracking");
        
        // Test case 2: Single element
        System.out.println("\nTest 2: [0]");
        int[] nums2 = {0};
        List<List<Integer>> expected2 = Arrays.asList(
            Arrays.asList(),
            Arrays.asList(0)
        );
        testImplementation(solution, nums2, expected2, "Backtracking");
        
        // Test case 3: All same elements
        System.out.println("\nTest 3: [1,1,1]");
        int[] nums3 = {1, 1, 1};
        List<List<Integer>> expected3 = Arrays.asList(
            Arrays.asList(),
            Arrays.asList(1),
            Arrays.asList(1, 1),
            Arrays.asList(1, 1, 1)
        );
        testImplementation(solution, nums3, expected3, "Backtracking");
        
        // Test case 4: No duplicates
        System.out.println("\nTest 4: [1,2,3]");
        int[] nums4 = {1, 2, 3};
        // Should have 8 subsets (2^3)
        testImplementation(solution, nums4, null, "Backtracking");
        
        // Test case 5: Mixed duplicates
        System.out.println("\nTest 5: [4,4,4,1,4]");
        int[] nums5 = {4, 4, 4, 1, 4};
        testImplementation(solution, nums5, null, "Backtracking");
        
        // Test case 6: Negative numbers with duplicates
        System.out.println("\nTest 6: [-1,0,1,-1]");
        int[] nums6 = {-1, 0, 1, -1};
        testImplementation(solution, nums6, null, "Backtracking");
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON");
        System.out.println("=".repeat(70));
        
        comparePerformance(solution);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION: BACKTRACKING WITH SORTING");
        System.out.println("=".repeat(70));
        
        explainBacktrackingApproach(solution);
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK ACROSS ALL IMPLEMENTATIONS");
        System.out.println("=".repeat(70));
        
        checkAllImplementations(solution);
    }
    
    private static void testImplementation(Solution solution, int[] nums, 
                                         List<List<Integer>> expected, String approach) {
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
        
        long startTime = System.nanoTime();
        List<List<Integer>> result = null;
        switch (approach) {
            case "Backtracking":
                result = solution.subsetsWithDup(numsCopy);
                break;
            case "Iterative":
                result = solution.subsetsWithDupIterative(numsCopy);
                break;
            case "Bitmask":
                result = solution.subsetsWithDupBitmask(numsCopy);
                break;
            case "Detailed":
                result = solution.subsetsWithDupDetailed(numsCopy);
                break;
        }
        long time = System.nanoTime() - startTime;
        
        // Sort both result and expected for comparison
        sortSubsets(result);
        if (expected != null) {
            sortSubsets(expected);
        }
        
        boolean passed = true;
        if (expected != null) {
            passed = result.size() == expected.size() && 
                    new HashSet<>(result).equals(new HashSet<>(expected));
        } else {
            // Just check that there are no duplicates
            passed = new HashSet<>(result).size() == result.size();
        }
        
        System.out.printf("%s: Got %d subsets, Time=%,d ns - %s%n",
                approach, result.size(), time, (passed ? "PASSED" : "FAILED"));
        
        if (!passed && expected != null) {
            System.out.println("  Expected: " + expected.size() + " subsets");
            System.out.println("  Got: " + result.size() + " subsets");
        }
        
        // Visualization for small inputs
        if (passed && nums.length <= 5) {
            solution.visualizeBacktracking(nums, approach);
        }
    }
    
    private static void sortSubsets(List<List<Integer>> subsets) {
        for (List<Integer> subset : subsets) {
            Collections.sort(subset);
        }
        subsets.sort((a, b) -> {
            if (a.size() != b.size()) {
                return a.size() - b.size();
            }
            for (int i = 0; i < a.size(); i++) {
                if (!a.get(i).equals(b.get(i))) {
                    return a.get(i) - b.get(i);
                }
            }
            return 0;
        });
    }
    
    private static void comparePerformance(Solution solution) {
        int[] testArray = solution.generateTestCase("mixed"); // [4,4,4,1,4]
        
        System.out.println("Performance test with array: " + Arrays.toString(testArray));
        
        // Test Backtracking
        long startTime = System.nanoTime();
        solution.subsetsWithDup(testArray);
        long time1 = System.nanoTime() - startTime;
        
        // Test Iterative
        startTime = System.nanoTime();
        solution.subsetsWithDupIterative(testArray);
        long time2 = System.nanoTime() - startTime;
        
        // Test Bitmask
        startTime = System.nanoTime();
        solution.subsetsWithDupBitmask(testArray);
        long time3 = System.nanoTime() - startTime;
        
        // Test Detailed
        startTime = System.nanoTime();
        solution.subsetsWithDupDetailed(testArray);
        long time4 = System.nanoTime() - startTime;
        
        System.out.printf("Backtracking:        %,12d ns%n", time1);
        System.out.printf("Iterative:           %,12d ns%n", time2);
        System.out.printf("Bitmask:             %,12d ns%n", time3);
        System.out.printf("Detailed Backtracking: %,12d ns%n", time4);
    }
    
    private static void explainBacktrackingApproach(Solution solution) {
        System.out.println("\nKey Insight:");
        System.out.println("To handle duplicates in subsets, we need to ensure that we don't");
        System.out.println("generate the same subset multiple times. The key is to sort the");
        System.out.println("array first and then skip duplicates at the same decision level.");
        
        System.out.println("\nWhy Sorting Helps:");
        System.out.println("Sorting groups duplicate elements together, making it easy to");
        System.out.println("identify and skip them during the backtracking process.");
        
        System.out.println("\nBacktracking Algorithm:");
        System.out.println("1. Sort the input array");
        System.out.println("2. Start with an empty subset");
        System.out.println("3. At each step:");
        System.out.println("   - Add current subset to result");
        System.out.println("   - For each element from start index to end:");
        System.out.println("     * Skip if it's a duplicate at same level (i > start && nums[i] == nums[i-1])");
        System.out.println("     * Include current element in subset");
        System.out.println("     * Recursively generate subsets from next index");
        System.out.println("     * Remove current element (backtrack)");
        
        System.out.println("\nDuplicate Skipping Condition:");
        System.out.println("The condition 'i > start && nums[i] == nums[i-1]' ensures that:");
        System.out.println("- We only skip duplicates at the same decision level");
        System.out.println("- We don't skip the first occurrence of a duplicate");
        System.out.println("- This prevents subsets like [2] from being generated multiple times");
        
        System.out.println("\nExample: [1,2,2]");
        System.out.println("Sorted: [1,2,2]");
        System.out.println("Decision Tree:");
        System.out.println("Start: []");
        System.out.println("  Include 1: [1]");
        System.out.println("    Include first 2: [1,2]");
        System.out.println("      Include second 2: [1,2,2]");
        System.out.println("    Skip second 2 (duplicate at same level)");
        System.out.println("  Skip 1 (not applicable)");
        System.out.println("  Start from first 2: [2]");
        System.out.println("    Include second 2: [2,2]");
        System.out.println("  Skip second 2 (duplicate at same level)");
        
        System.out.println("\nTime Complexity: O(n × 2^n)");
        System.out.println("  - There are 2^n possible subsets in worst case");
        System.out.println("  - Each subset takes O(n) to generate and add");
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Recursion stack depth: O(n)");
        System.out.println("  - Temporary list: O(n)");
        
        System.out.println("\nVisual Example Walkthrough:");
        int[] example = {1, 2, 2};
        solution.visualizeBacktracking(example, "Backtracking");
    }
    
    private static void checkAllImplementations(Solution solution) {
        int[][] testCases = {
            {1, 2, 2},          // Standard
            {0},                 // Single
            {1, 1, 1},          // All same
            {1, 2, 3},          // No duplicates
            {4, 4, 4, 1, 4},    // Mixed
            {-1, 0, 1, -1}      // Negative
        };
        
        String[] methods = {
            "Backtracking",
            "Iterative", 
            "Bitmask",
            "Detailed",
            "Count",
            "Lexico"
        };
        
        boolean allConsistent = true;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            
            System.out.printf("\nTest case %d: %s%n",
                    i + 1, Arrays.toString(nums));
            
            List<List<Integer>>[] results = new List[methods.length];
            results[0] = solution.subsetsWithDup(nums.clone());
            results[1] = solution.subsetsWithDupIterative(nums.clone());
            results[2] = solution.subsetsWithDupBitmask(nums.clone());
            results[3] = solution.subsetsWithDupDetailed(nums.clone());
            results[4] = solution.subsetsWithDupCount(nums.clone());
            results[5] = solution.subsetsWithDupLexico(nums.clone());
            
            // Sort all results for comparison
            for (List<List<Integer>> result : results) {
                sortSubsets(result);
            }
            
            boolean caseConsistent = true;
            int expectedSize = results[0].size();
            
            for (int j = 0; j < methods.length; j++) {
                boolean consistent = results[j].size() == expectedSize && 
                                   new HashSet<>(results[j]).size() == results[j].size();
                
                System.out.printf("  %-12s: %2d subsets, no duplicates: %s%n", 
                    methods[j], results[j].size(), consistent ? "✓" : "✗");
                
                if (!consistent) {
                    caseConsistent = false;
                    allConsistent = false;
                }
                
                // Check against first implementation
                if (j > 0 && !results[j].equals(results[0])) {
                    System.out.printf("    WARNING: Different result than Backtracking%n");
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
        System.out.println("\n1. BACKTRACKING WITH SORTING (RECOMMENDED):");
        System.out.println("   Time: O(n × 2^n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Elegant and intuitive");
        System.out.println("     - Handles duplicates naturally");
        System.out.println("     - Easy to understand and implement");
        System.out.println("     - Optimal time complexity");
        System.out.println("   Cons:");
        System.out.println("     - Recursive (stack depth for large n)");
        System.out.println("   Use when: Interviews, general case");
        
        System.out.println("\n2. ITERATIVE (BFS-LIKE):");
        System.out.println("   Time: O(n × 2^n)");
        System.out.println("   Space: O(n × 2^n)");
        System.out.println("   Pros:");
        System.out.println("     - No recursion stack");
        System.out.println("     - Builds subsets level by level");
        System.out.println("     - Good for understanding the process");
        System.out.println("   Cons:");
        System.out.println("     - Uses more memory");
        System.out.println("     - Slightly more complex logic");
        System.out.println("   Use when: Prefer iterative over recursive");
        
        System.out.println("\n3. BIT MANIPULATION WITH SET:");
        System.out.println("   Time: O(n × 2^n)");
        System.out.println("   Space: O(n × 2^n)");
        System.out.println("   Pros:");
        System.out.println("     - Simple concept (all bitmasks)");
        System.out.println("     - No recursion");
        System.out.println("   Cons:");
        System.out.println("     - Uses HashSet to remove duplicates");
        System.out.println("     - Less efficient due to set operations");
        System.out.println("     - Doesn't handle duplicates naturally");
        System.out.println("   Use when: Educational purposes, small inputs");
        
        System.out.println("\n4. DETAILED BACKTRACKING:");
        System.out.println("   Time: O(n × 2^n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - More explicit duplicate handling");
        System.out.println("     - Uses used array for clarity");
        System.out.println("   Cons:");
        System.out.println("     - More complex than standard backtracking");
        System.out.println("     - Extra space for used array");
        System.out.println("   Use when: Need very explicit duplicate handling");
        
        System.out.println("\n5. COUNT-BASED ITERATIVE:");
        System.out.println("   Time: O(n × 2^n)");
        System.out.println("   Space: O(n × 2^n)");
        System.out.println("   Pros:");
        System.out.println("     - Handles duplicates by counting");
        System.out.println("     - Clear mathematical approach");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Uses more memory");
        System.out.println("   Use when: Many duplicates, mathematical approach preferred");
        
        System.out.println("\n6. LEXICOGRAPHIC BACKTRACKING:");
        System.out.println("   Time: O(n × 2^n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Generates subsets in order");
        System.out.println("     - Handles duplicates by counting");
        System.out.println("   Cons:");
        System.out.println("     - Most complex implementation");
        System.out.println("     - Harder to understand");
        System.out.println("   Use when: Need subsets in specific order");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with explaining the duplicate handling challenge");
        System.out.println("2. Propose sorting as the first step");
        System.out.println("3. Implement standard backtracking with duplicate skipping");
        System.out.println("4. Explain the 'i > start && nums[i] == nums[i-1]' condition clearly");
        System.out.println("5. Discuss time/space complexity");
        System.out.println("6. Mention alternative approaches if time permits");
        System.out.println("7. Handle edge cases: single element, all duplicates, no duplicates");
        System.out.println("=".repeat(70));
    }
}
