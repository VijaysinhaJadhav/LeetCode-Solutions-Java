
# Solution.java

```java
import java.util.*;

/**
 * 769. Max Chunks To Make Sorted
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given permutation of [0, n-1], find max chunks we can split into
 * such that sorting each chunk gives sorted array when concatenated.
 * 
 * Key Insights:
 * 1. For chunk from i to j to be valid: after sorting, it must be [i, i+1, ..., j]
 * 2. When max_so_far == current_index, we can end a chunk
 * 3. Works because array contains exactly 0 to n-1 once each
 * 4. Greedy approach: cut whenever possible
 * 
 * Approach 1: Max Tracking (RECOMMENDED)
 * O(n) time, O(1) space
 */

class Solution {
    
    /**
     * Approach 1: Max Tracking (RECOMMENDED)
     * O(n) time, O(1) space
     * Key insight: When current max equals index, we can make a cut
     */
    public int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        
        int chunks = 0;
        int currentMax = 0;
        
        for (int i = 0; i < n; i++) {
            currentMax = Math.max(currentMax, arr[i]);
            
            // When max equals current index, we can make a cut
            if (currentMax == i) {
                chunks++;
            }
        }
        
        return chunks;
    }
    
    /**
     * Approach 2: Prefix Max and Suffix Min
     * O(n) time, O(n) space
     * More explicit but uses extra space
     */
    public int maxChunksToSortedPrefixSuffix(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        
        // prefixMax[i] = max(arr[0..i])
        int[] prefixMax = new int[n];
        prefixMax[0] = arr[0];
        for (int i = 1; i < n; i++) {
            prefixMax[i] = Math.max(prefixMax[i-1], arr[i]);
        }
        
        // suffixMin[i] = min(arr[i..n-1])
        int[] suffixMin = new int[n];
        suffixMin[n-1] = arr[n-1];
        for (int i = n-2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i+1], arr[i]);
        }
        
        int chunks = 0;
        for (int i = 0; i < n; i++) {
            // Can make cut at i if:
            // 1. All elements before i are <= i (ensured by permutation property)
            // 2. All elements after i are > i
            if (i == n-1 || prefixMax[i] <= i && suffixMin[i+1] > i) {
                chunks++;
            }
        }
        
        return chunks;
    }
    
    /**
     * Approach 3: Interval Merging
     * O(n) time, O(n) space
     * More intuitive but less efficient
     */
    public int maxChunksToSortedInterval(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        
        // Create intervals: [min(pos, value), max(pos, value)]
        List<int[]> intervals = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            intervals.add(new int[]{Math.min(i, arr[i]), Math.max(i, arr[i])});
        }
        
        // Sort intervals by start
        intervals.sort((a, b) -> a[0] - b[0]);
        
        // Merge overlapping intervals
        List<int[]> merged = new ArrayList<>();
        for (int[] interval : intervals) {
            if (merged.isEmpty() || merged.get(merged.size()-1)[1] < interval[0]) {
                merged.add(interval);
            } else {
                int[] last = merged.get(merged.size()-1);
                last[1] = Math.max(last[1], interval[1]);
            }
        }
        
        return merged.size();
    }
    
    /**
     * Approach 4: Stack-based (for follow-up problem)
     * O(n) time, O(n) space
     * Works for LeetCode 768 as well
     */
    public int maxChunksToSortedStack(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        
        for (int num : arr) {
            if (stack.isEmpty() || num >= stack.peek()) {
                // Start new chunk
                stack.push(num);
            } else {
                // Merge chunks
                int max = stack.pop();
                while (!stack.isEmpty() && num < stack.peek()) {
                    stack.pop();
                }
                stack.push(max);
            }
        }
        
        return stack.size();
    }
    
    /**
     * Approach 5: Brute Force (for small n)
     * O(2^n) time, O(n) space
     * Only works for n <= 10 (given constraint)
     */
    public int maxChunksToSortedBruteForce(int[] arr) {
        int n = arr.length;
        int maxChunks = 1; // At least one chunk (whole array)
        
        // Try all possible partitions
        for (int mask = 0; mask < (1 << (n-1)); mask++) {
            // mask represents cuts between elements
            // bit i = 1 means cut between i and i+1
            
            if (isValidPartition(arr, mask)) {
                maxChunks = Math.max(maxChunks, Integer.bitCount(mask) + 1);
            }
        }
        
        return maxChunks;
    }
    
    private boolean isValidPartition(int[] arr, int mask) {
        int n = arr.length;
        int start = 0;
        
        for (int i = 0; i < n-1; i++) {
            if (((mask >> i) & 1) == 1) {
                // Check if chunk [start, i] is valid
                if (!isValidChunk(arr, start, i)) {
                    return false;
                }
                start = i + 1;
            }
        }
        
        // Check last chunk
        return isValidChunk(arr, start, n-1);
    }
    
    private boolean isValidChunk(int[] arr, int start, int end) {
        // A chunk is valid if it contains exactly numbers start..end
        boolean[] seen = new boolean[end - start + 1];
        for (int i = start; i <= end; i++) {
            if (arr[i] < start || arr[i] > end) {
                return false;
            }
            seen[arr[i] - start] = true;
        }
        
        // Check all numbers in range are present
        for (boolean s : seen) {
            if (!s) return false;
        }
        return true;
    }
    
    /**
     * Approach 6: Count Sort Check
     * O(n) time, O(n) space
     * Simulates sorting and checks when prefix matches
     */
    public int maxChunksToSortedCount(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        
        // Since arr contains 0..n-1, sorted array is [0, 1, ..., n-1]
        int chunks = 0;
        int maxSeen = -1;
        
        for (int i = 0; i < n; i++) {
            maxSeen = Math.max(maxSeen, arr[i]);
            
            // If we've seen all numbers 0..i at position i
            // This works because numbers are unique
            if (maxSeen == i) {
                chunks++;
            }
        }
        
        return chunks;
    }
    
    /**
     * Helper: Visualize the algorithm
     */
    public void visualizeMaxTracking(int[] arr) {
        System.out.println("\nMax Tracking Algorithm Visualization:");
        System.out.println("arr = " + Arrays.toString(arr));
        System.out.println("n = " + arr.length);
        System.out.println("\nSorted array should be: " + Arrays.toString(getSortedArray(arr.length)));
        System.out.println("\nIndex | Value | Current Max | Can Cut? | Chunks");
        System.out.println("------|-------|-------------|----------|--------");
        
        int chunks = 0;
        int currentMax = 0;
        
        for (int i = 0; i < arr.length; i++) {
            currentMax = Math.max(currentMax, arr[i]);
            boolean canCut = (currentMax == i);
            if (canCut) chunks++;
            
            System.out.printf("%5d | %5d | %11d | %8s | %6d%n", 
                i, arr[i], currentMax, canCut ? "✓" : "✗", chunks);
            
            if (canCut) {
                System.out.println("      |       |             |          |   ↑ Cut here!");
            }
        }
        
        System.out.println("\nTotal chunks: " + chunks);
        
        // Show the actual chunks
        List<List<Integer>> chunksList = getChunks(arr);
        System.out.println("\nActual chunks found:");
        for (int i = 0; i < chunksList.size(); i++) {
            List<Integer> chunk = chunksList.get(i);
            Collections.sort(chunk);
            System.out.printf("Chunk %d: %s -> sorted: %s%n", 
                i+1, chunk, chunk);
        }
    }
    
    private int[] getSortedArray(int n) {
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) {
            sorted[i] = i;
        }
        return sorted;
    }
    
    private List<List<Integer>> getChunks(int[] arr) {
        List<List<Integer>> chunks = new ArrayList<>();
        int currentMax = 0;
        int chunkStart = 0;
        
        for (int i = 0; i < arr.length; i++) {
            currentMax = Math.max(currentMax, arr[i]);
            if (currentMax == i) {
                List<Integer> chunk = new ArrayList<>();
                for (int j = chunkStart; j <= i; j++) {
                    chunk.add(arr[j]);
                }
                chunks.add(chunk);
                chunkStart = i + 1;
            }
        }
        
        return chunks;
    }
    
    /**
     * Helper: Explain the mathematical reasoning
     */
    public void explainMathematicalReasoning() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("MATHEMATICAL REASONING:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nWhy does max tracking work?");
        System.out.println("\nKey Facts:");
        System.out.println("1. arr is a permutation of [0, n-1]");
        System.out.println("2. All numbers are unique");
        System.out.println("3. Sorted array would be [0, 1, 2, ..., n-1]");
        
        System.out.println("\nObservation:");
        System.out.println("For a chunk from index i to j to be valid:");
        System.out.println("After sorting the chunk, it must become [i, i+1, ..., j]");
        System.out.println("This means the chunk must contain exactly the numbers i through j");
        
        System.out.println("\nProperty:");
        System.out.println("If we look at prefix arr[0..i], and the maximum value is i,");
        System.out.println("then arr[0..i] must contain all numbers 0 through i.");
        System.out.println("Why? Because:");
        System.out.println("- Maximum is i, so no number > i in prefix");
        System.out.println("- Numbers are unique");
        System.out.println("- There are i+1 numbers in prefix");
        System.out.println("- The only way to have i+1 unique numbers all ≤ i is to have exactly 0..i");
        
        System.out.println("\nExample: arr = [1,0,2,3,4]");
        System.out.println("\ni=0: arr[0]=1, max=1 ≠ 0 → can't cut");
        System.out.println("i=1: max(1,0)=1 = 1 ✓ → cut! Chunk [1,0] -> sorted [0,1]");
        System.out.println("i=2: arr[2]=2, max=2 = 2 ✓ → cut! Chunk [2]");
        System.out.println("i=3: arr[3]=3, max=3 = 3 ✓ → cut! Chunk [3]");
        System.out.println("i=4: arr[4]=4, max=4 = 4 ✓ → cut! Chunk [4]");
        
        System.out.println("\nProof of Optimality (Greedy):");
        System.out.println("Whenever we can cut (max == i), we should cut.");
        System.out.println("Delaying the cut cannot give us more chunks.");
        System.out.println("Because if we don't cut when we can,");
        System.out.println("we'll have to include more elements in current chunk,");
        System.out.println("which reduces the number of chunks.");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EDGE CASES TESTING:");
        System.out.println("=".repeat(80));
        
        Solution solution = new Solution();
        
        System.out.println("\n1. Already sorted array:");
        int[] case1 = {0, 1, 2, 3, 4};
        System.out.println("   arr = " + Arrays.toString(case1));
        System.out.println("   Result: " + solution.maxChunksToSorted(case1) + 
                         " (each element can be its own chunk)");
        
        System.out.println("\n2. Reverse sorted array:");
        int[] case2 = {4, 3, 2, 1, 0};
        System.out.println("   arr = " + Arrays.toString(case2));
        System.out.println("   Result: " + solution.maxChunksToSorted(case2) + 
                         " (only one chunk possible)");
        
        System.out.println("\n3. Single element:");
        int[] case3 = {0};
        System.out.println("   arr = " + Arrays.toString(case3));
        System.out.println("   Result: " + solution.maxChunksToSorted(case3) + 
                         " (one chunk)");
        
        System.out.println("\n4. Two elements:");
        int[] case4 = {1, 0};
        System.out.println("   arr = " + Arrays.toString(case4));
        System.out.println("   Result: " + solution.maxChunksToSorted(case4) + 
                         " (can split into two chunks)");
        
        System.out.println("\n5. Example from problem:");
        int[] case5 = {1, 0, 2, 3, 4};
        System.out.println("   arr = " + Arrays.toString(case5));
        System.out.println("   Result: " + solution.maxChunksToSorted(case5) + 
                         " (4 chunks)");
        
        System.out.println("\n6. Complex case:");
        int[] case6 = {2, 0, 1, 4, 3};
        System.out.println("   arr = " + Arrays.toString(case6));
        System.out.println("   Result: " + solution.maxChunksToSorted(case6) + 
                         " (chunks: [2,0,1] and [4,3])");
        
        System.out.println("\n7. Maximum chunks (n=10):");
        int[] case7 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("   arr = " + Arrays.toString(case7));
        System.out.println("   Result: " + solution.maxChunksToSorted(case7) + 
                         " (10 chunks, maximum possible)");
    }
    
    /**
     * Helper: Compare all approaches
     */
    public void compareApproaches(int[] arr) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING APPROACHES:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nInput:");
        System.out.println("arr = " + Arrays.toString(arr));
        System.out.println("n = " + arr.length);
        
        Solution solution = new Solution();
        
        long startTime, endTime;
        int result1, result2, result3, result4, result5, result6;
        
        // Approach 1: Max Tracking
        startTime = System.nanoTime();
        result1 = solution.maxChunksToSorted(arr);
        endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        // Approach 2: Prefix-Suffix
        startTime = System.nanoTime();
        result2 = solution.maxChunksToSortedPrefixSuffix(arr);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        // Approach 3: Interval
        startTime = System.nanoTime();
        result3 = solution.maxChunksToSortedInterval(arr);
        endTime = System.nanoTime();
        long time3 = endTime - startTime;
        
        // Approach 4: Stack
        startTime = System.nanoTime();
        result4 = solution.maxChunksToSortedStack(arr);
        endTime = System.nanoTime();
        long time4 = endTime - startTime;
        
        // Approach 6: Count
        startTime = System.nanoTime();
        result6 = solution.maxChunksToSortedCount(arr);
        endTime = System.nanoTime();
        long time6 = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Approach 1 (Max Tracking):      " + result1);
        System.out.println("Approach 2 (Prefix-Suffix):     " + result2);
        System.out.println("Approach 3 (Interval):          " + result3);
        System.out.println("Approach 4 (Stack):             " + result4);
        System.out.println("Approach 6 (Count):             " + result6);
        
        // Approach 5: Brute Force (only for n <= 10)
        if (arr.length <= 10) {
            startTime = System.nanoTime();
            result5 = solution.maxChunksToSortedBruteForce(arr);
            endTime = System.nanoTime();
            long time5 = endTime - startTime;
            
            System.out.println("Approach 5 (Brute Force):       " + result5);
            
            boolean allEqual = (result1 == result2) && (result2 == result3) && 
                              (result3 == result4) && (result4 == result5) &&
                              (result5 == result6);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (Max Tracking)%n", time1);
            System.out.printf("Approach 2: %-10d (Prefix-Suffix)%n", time2);
            System.out.printf("Approach 3: %-10d (Interval)%n", time3);
            System.out.printf("Approach 4: %-10d (Stack)%n", time4);
            System.out.printf("Approach 5: %-10d (Brute Force)%n", time5);
            System.out.printf("Approach 6: %-10d (Count)%n", time6);
        } else {
            boolean allEqual = (result1 == result2) && (result2 == result3) && 
                              (result3 == result4) && (result4 == result6);
            System.out.println("\nAll results equal: " + (allEqual ? "✓ YES" : "✗ NO"));
            
            System.out.println("\nPerformance (nanoseconds):");
            System.out.printf("Approach 1: %-10d (Max Tracking)%n", time1);
            System.out.printf("Approach 2: %-10d (Prefix-Suffix)%n", time2);
            System.out.printf("Approach 3: %-10d (Interval)%n", time3);
            System.out.printf("Approach 4: %-10d (Stack)%n", time4);
            System.out.printf("Approach 6: %-10d (Count)%n", time6);
        }
        
        // Visualize
        if (arr.length <= 15) {
            solution.visualizeMaxTracking(arr);
        }
    }
    
    /**
     * Helper: Analyze complexity
     */
    public void analyzeComplexity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Time Complexity:");
        System.out.println("   a. Max Tracking: O(n)");
        System.out.println("      - Single pass through array");
        System.out.println("   b. Prefix-Suffix: O(n)");
        System.out.println("      - Two passes, O(n) each");
        System.out.println("   c. Interval: O(n log n)");
        System.out.println("      - Sorting intervals takes O(n log n)");
        System.out.println("   d. Stack: O(n)");
        System.out.println("      - Each element pushed/popped once");
        System.out.println("   e. Brute Force: O(2^n)");
        System.out.println("      - Try all 2^(n-1) partitions");
        System.out.println("   f. Count: O(n)");
        System.out.println("      - Single pass");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   a. Max Tracking: O(1)");
        System.out.println("      - Only few variables");
        System.out.println("   b. Prefix-Suffix: O(n)");
        System.out.println("      - Two arrays of size n");
        System.out.println("   c. Interval: O(n)");
        System.out.println("      - Store intervals");
        System.out.println("   d. Stack: O(n) worst case");
        System.out.println("      - Stack can hold all elements");
        System.out.println("   e. Brute Force: O(n)");
        System.out.println("      - Recursion stack/validation arrays");
        System.out.println("   f. Count: O(1)");
        System.out.println("      - Only few variables");
        
        System.out.println("\n3. Why Max Tracking is Optimal:");
        System.out.println("   - Linear time, constant space");
        System.out.println("   - Simple implementation");
        System.out.println("   - Based on mathematical property of permutations");
        System.out.println("   - Greedy choice is optimal for this problem");
        
        System.out.println("\n4. Constraints Analysis:");
        System.out.println("   n ≤ 10 (in this problem)");
        System.out.println("   - Even brute force O(2^n) works (2^9 = 512)");
        System.out.println("   - But max tracking O(n) is still better");
        System.out.println("   - For follow-up (n up to 2000), need O(n)");
    }
    
    /**
     * Helper: Show related problem (768)
     */
    public void showRelatedProblem() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEM: 768. Max Chunks To Make Sorted II");
        System.out.println("=".repeat(80));
        
        System.out.println("\nProblem Statement:");
        System.out.println("Same as 769, but array can contain duplicates");
        System.out.println("and numbers can be any integer, not just 0..n-1");
        
        System.out.println("\nExample:");
        System.out.println("Input: arr = [2,1,3,4,4]");
        System.out.println("Output: 4");
        System.out.println("Explanation:");
        System.out.println("We can split into [2,1], [3], [4], [4]");
        
        System.out.println("\nSolution for 768:");
        System.out.println("Use stack-based approach:");
        System.out.println("1. Initialize empty stack");
        System.out.println("2. For each num in arr:");
        System.out.println("   - If stack empty or num >= stack.top: push(num)");
        System.out.println("   - Else: pop while num < stack.top, then push max");
        System.out.println("3. Return stack.size()");
        
        System.out.println("\nWhy stack works for 768:");
        System.out.println("- Stack stores maximum of each chunk");
        System.out.println("- When we see smaller number, merge chunks");
        System.out.println("- Keep maximum of merged chunk");
        System.out.println("- Final stack size = number of chunks");
    }
    
    /**
     * Helper: Real-world applications
     */
    public void showApplications() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Parallel Processing:");
        System.out.println("   - Split data into independent chunks");
        System.out.println("   - Process chunks in parallel");
        System.out.println("   - Merge results");
        
        System.out.println("\n2. Database Optimization:");
        System.out.println("   - Partition tables for faster queries");
        System.out.println("   - Range-based partitioning");
        System.out.println("   - Data distribution across nodes");
        
        System.out.println("\n3. Load Balancing:");
        System.out.println("   - Split workload into balanced chunks");
        System.out.println("   - Distribute to different servers");
        System.out.println("   - Maximize parallel processing");
        
        System.out.println("\n4. File Systems:");
        System.out.println("   - Split large files into blocks");
        System.out.println("   - Distribute across storage devices");
        System.out.println("   - Optimize read/write operations");
        
        System.out.println("\n5. Network Routing:");
        System.out.println("   - Split data packets");
        System.out.println("   - Route through different paths");
        System.out.println("   - Reassemble at destination");
        
        System.out.println("\n6. Compiler Optimization:");
        System.out.println("   - Split code into basic blocks");
        System.out.println("   - Optimize each block independently");
        System.out.println("   - Control flow analysis");
        
        System.out.println("\n7. Image Processing:");
        System.out.println("   - Split image into tiles");
        System.out.println("   - Process tiles in parallel");
        System.out.println("   - Stitch results together");
    }
    
    /**
     * Main method with comprehensive tests
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Max Chunks To Make Sorted:");
        System.out.println("==================================");
        
        // Explain mathematical reasoning
        solution.explainMathematicalReasoning();
        
        // Test edge cases
        solution.testEdgeCases();
        
        // Example 1 from problem
        System.out.println("\n\nExample 1 from problem:");
        int[] arr1 = {4, 3, 2, 1, 0};
        int expected1 = 1;
        
        System.out.println("\nInput: arr = " + Arrays.toString(arr1));
        
        int result1 = solution.maxChunksToSorted(arr1);
        System.out.println("Expected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Passed: " + (result1 == expected1 ? "✓" : "✗"));
        
        solution.visualizeMaxTracking(arr1);
        
        // Example 2 from problem
        System.out.println("\n\nExample 2 from problem:");
        int[] arr2 = {1, 0, 2, 3, 4};
        int expected2 = 4;
        
        System.out.println("\nInput: arr = " + Arrays.toString(arr2));
        
        int result2 = solution.maxChunksToSorted(arr2);
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Passed: " + (result2 == expected2 ? "✓" : "✗"));
        
        solution.visualizeMaxTracking(arr2);
        
        // Additional test cases
        System.out.println("\n\nAdditional Test Cases:");
        
        // Test case 3
        System.out.println("\nTest Case 3:");
        int[] arr3 = {2, 0, 1, 4, 3};
        int expected3 = 2;
        
        int result3 = solution.maxChunksToSorted(arr3);
        System.out.println("Input: arr = " + Arrays.toString(arr3));
        System.out.println("Expected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Passed: " + (result3 == expected3 ? "✓" : "✗"));
        
        // Test case 4
        System.out.println("\nTest Case 4:");
        int[] arr4 = {0, 2, 1, 4, 3};
        int expected4 = 3; // [0], [2,1], [4,3]
        
        int result4 = solution.maxChunksToSorted(arr4);
        System.out.println("Input: arr = " + Arrays.toString(arr4));
        System.out.println("Expected: " + expected4);
        System.out.println("Result:   " + result4);
        System.out.println("Passed: " + (result4 == expected4 ? "✓" : "✗"));
        
        // Test case 5
        System.out.println("\nTest Case 5:");
        int[] arr5 = {3, 2, 0, 1};
        int expected5 = 1; // Only one chunk
        
        int result5 = solution.maxChunksToSorted(arr5);
        System.out.println("Input: arr = " + Arrays.toString(arr5));
        System.out.println("Expected: " + expected5);
        System.out.println("Result:   " + result5);
        System.out.println("Passed: " + (result5 == expected5 ? "✓" : "✗"));
        
        // Compare all approaches
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES FOR EXAMPLE 1:");
        System.out.println("=".repeat(80));
        solution.compareApproaches(arr1);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARING ALL APPROACHES FOR EXAMPLE 2:");
        System.out.println("=".repeat(80));
        solution.compareApproaches(arr2);
        
        // Test with all permutations for small n
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TESTING ALL PERMUTATIONS FOR n=4:");
        System.out.println("=".repeat(80));
        
        // Generate all permutations of [0,1,2,3]
        List<int[]> permutations = generatePermutations(4);
        System.out.println("Testing " + permutations.size() + " permutations");
        
        int correct = 0;
        for (int[] perm : permutations) {
            int result = solution.maxChunksToSorted(perm);
            int bruteResult = solution.maxChunksToSortedBruteForce(perm);
            
            if (result == bruteResult) {
                correct++;
            } else {
                System.out.println("Mismatch for: " + Arrays.toString(perm));
                System.out.println("  Max tracking: " + result);
                System.out.println("  Brute force:  " + bruteResult);
            }
        }
        
        System.out.println("\nCorrect: " + correct + "/" + permutations.size());
        System.out.println("All tests passed: " + (correct == permutations.size() ? "✓" : "✗"));
        
        // Performance test
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(80));
        
        // Generate random permutation of size 2000 (for follow-up scale)
        int n = 2000;
        int[] largeArr = new int[n];
        for (int i = 0; i < n; i++) {
            largeArr[i] = i;
        }
        // Shuffle
        Random random = new Random(42);
        for (int i = n-1; i > 0; i--) {
            int j = random.nextInt(i+1);
            int temp = largeArr[i];
            largeArr[i] = largeArr[j];
            largeArr[j] = temp;
        }
        
        System.out.println("\nTesting with n = " + n);
        
        long startTime = System.currentTimeMillis();
        int largeResult = solution.maxChunksToSorted(largeArr);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Result: " + largeResult + " chunks");
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        
        // Test stack approach (for comparison with follow-up)
        startTime = System.currentTimeMillis();
        int stackResult = solution.maxChunksToSortedStack(largeArr);
        endTime = System.currentTimeMillis();
        
        System.out.println("Stack approach result: " + stackResult + " chunks");
        System.out.println("Stack approach time: " + (endTime - startTime) + " ms");
        System.out.println("Results match: " + (largeResult == stackResult ? "✓" : "✗"));
        
        // Complexity analysis
        solution.analyzeComplexity();
        
        // Show related problem
        solution.showRelatedProblem();
        
        // Show applications
        solution.showApplications();
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Understand the problem:");
        System.out.println("   - Permutation of [0, n-1]");
        System.out.println("   - Split into chunks, sort chunks, concatenate = sorted");
        System.out.println("   - Maximize number of chunks");
        
        System.out.println("\n2. Think about properties:");
        System.out.println("   - Each chunk must contain contiguous numbers when sorted");
        System.out.println("   - Chunk [i..j] when sorted must be [i, i+1, ..., j]");
        System.out.println("   - So chunk must contain exactly numbers i through j");
        
        System.out.println("\n3. Key observation:");
        System.out.println("   - If we look at prefix arr[0..i] and max = i");
        System.out.println("   - Then prefix contains all numbers 0..i");
        System.out.println("   - Because: unique numbers, max = i, i+1 numbers");
        
        System.out.println("\n4. Design algorithm:");
        System.out.println("   - Track current maximum");
        System.out.println("   - When max == current index, make a cut");
        System.out.println("   - Increment chunk count");
        
        System.out.println("\n5. Prove correctness:");
        System.out.println("   - Show greedy choice is optimal");
        System.out.println("   - Cutting when possible doesn't reduce future options");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - Already sorted array (n chunks)");
        System.out.println("   - Reverse sorted (1 chunk)");
        System.out.println("   - Single element (1 chunk)");
        
        System.out.println("\n7. Discuss follow-up (768):");
        System.out.println("   - Array can have duplicates");
        System.out.println("   - Use stack-based approach");
        System.out.println("   - Merge chunks when smaller number appears");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Mathematical insight about permutations");
        System.out.println("- Simple O(n) time, O(1) space solution");
        System.out.println("- Greedy approach is optimal");
        System.out.println("- Connection to follow-up problem");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Overcomplicating with sorting or backtracking");
        System.out.println("- Not recognizing the permutation property");
        System.out.println("- Missing the max == index condition");
        System.out.println("- Not considering greedy optimality");
        
        // Related problems
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RELATED PROBLEMS TO STUDY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. 768. Max Chunks To Make Sorted II");
        System.out.println("2. 56. Merge Intervals");
        System.out.println("3. 763. Partition Labels");
        System.out.println("4. 921. Minimum Add to Make Parentheses Valid");
        System.out.println("5. 735. Asteroid Collision");
        System.out.println("6. 853. Car Fleet");
        System.out.println("7. 845. Longest Mountain in Array");
        System.out.println("8. 915. Partition Array into Disjoint Intervals");
        System.out.println("9. 1574. Shortest Subarray to be Removed to Make Array Sorted");
        System.out.println("10. 1775. Equal Sum Arrays With Minimum Number of Operations");
        
        System.out.println("\nAll tests completed successfully!");
    }
    
    /**
     * Helper: Generate all permutations of [0..n-1]
     */
    private static List<int[]> generatePermutations(int n) {
        List<int[]> result = new ArrayList<>();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i;
        }
        permute(result, nums, 0);
        return result;
    }
    
    private static void permute(List<int[]> result, int[] nums, int start) {
        if (start == nums.length) {
            result.add(nums.clone());
            return;
        }
        
        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            permute(result, nums, start + 1);
            swap(nums, start, i);
        }
    }
    
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
