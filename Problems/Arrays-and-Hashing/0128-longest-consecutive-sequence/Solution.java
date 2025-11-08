/**
 * 128. Longest Consecutive Sequence
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an unsorted array of integers nums, return the length of the 
 * longest consecutive elements sequence. Must run in O(n) time.
 * 
 * Key Insights:
 * 1. HashSet Approach: Store numbers in set, find sequence starts
 * 2. Union Find: Connect consecutive numbers, find largest component
 * 3. Sorting: Simple but O(n log n) - doesn't meet requirement
 * 4. Key optimization: Only start sequences from numbers where num-1 doesn't exist
 * 
 * Approach (HashSet):
 * 1. Add all numbers to a HashSet for O(1) lookups
 * 2. For each number, check if it's a sequence start (num-1 not in set)
 * 3. If sequence start, count consecutive numbers forward
 * 4. Track the maximum sequence length found
 * 
 * Time Complexity: O(n) - Each number processed at most twice
 * Space Complexity: O(n) - HashSet storage
 * 
 * Tags: Array, Hash Table, Union Find
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: HashSet with Sequence Starts (RECOMMENDED)
     * O(n) time, O(n) space - Optimal solution
     */
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        int longestStreak = 0;
        
        for (int num : numSet) {
            // Check if this number is the start of a sequence
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                
                // Count consecutive numbers forward
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }
                
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        
        return longestStreak;
    }
    
    /**
     * Approach 2: Union Find (Disjoint Set Union)
     * O(n) amortized time, O(n) space - Alternative approach
     */
    public int longestConsecutiveUnionFind(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        UnionFind uf = new UnionFind(nums.length);
        Map<Integer, Integer> numToIndex = new HashMap<>();
        
        // First pass: map numbers to indices
        for (int i = 0; i < nums.length; i++) {
            // Skip duplicates
            if (!numToIndex.containsKey(nums[i])) {
                numToIndex.put(nums[i], i);
            }
        }
        
        // Second pass: union consecutive numbers
        for (int num : nums) {
            if (numToIndex.containsKey(num + 1)) {
                uf.union(numToIndex.get(num), numToIndex.get(num + 1));
            }
            if (numToIndex.containsKey(num - 1)) {
                uf.union(numToIndex.get(num), numToIndex.get(num - 1));
            }
        }
        
        return uf.getMaxSize();
    }
    
    /**
     * Union Find data structure for Approach 2
     */
    class UnionFind {
        private int[] parent;
        private int[] size;
        private int maxSize;
        
        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            maxSize = 1;
            
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX != rootY) {
                // Union by size
                if (size[rootX] < size[rootY]) {
                    parent[rootX] = rootY;
                    size[rootY] += size[rootX];
                    maxSize = Math.max(maxSize, size[rootY]);
                } else {
                    parent[rootY] = rootX;
                    size[rootX] += size[rootY];
                    maxSize = Math.max(maxSize, size[rootX]);
                }
            }
        }
        
        public int getMaxSize() {
            return maxSize;
        }
    }
    
    /**
     * Approach 3: Sorting (For Comparison - Doesn't meet O(n) requirement)
     * O(n log n) time, O(1) space if sorting in-place
     */
    public int longestConsecutiveSorting(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        Arrays.sort(nums);
        
        int longestStreak = 1;
        int currentStreak = 1;
        
        for (int i = 1; i < nums.length; i++) {
            // Skip duplicates
            if (nums[i] == nums[i - 1]) {
                continue;
            }
            
            // Check if consecutive
            if (nums[i] == nums[i - 1] + 1) {
                currentStreak++;
                longestStreak = Math.max(longestStreak, currentStreak);
            } else {
                currentStreak = 1;
            }
        }
        
        return longestStreak;
    }
    
    /**
     * Approach 4: HashSet with Removal (Alternative Implementation)
     * O(n) time, O(n) space - Removes processed numbers from set
     */
    public int longestConsecutiveHashSetRemoval(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        int longestStreak = 0;
        
        while (!numSet.isEmpty()) {
            int num = numSet.iterator().next();
            numSet.remove(num);
            
            int currentStreak = 1;
            
            // Check left side
            int left = num - 1;
            while (numSet.contains(left)) {
                numSet.remove(left);
                currentStreak++;
                left--;
            }
            
            // Check right side
            int right = num + 1;
            while (numSet.contains(right)) {
                numSet.remove(right);
                currentStreak++;
                right++;
            }
            
            longestStreak = Math.max(longestStreak, currentStreak);
        }
        
        return longestStreak;
    }
    
    /**
     * Approach 5: Boundary Detection (Advanced)
     * O(n) time, O(n) space - Tracks sequence boundaries
     */
    public int longestConsecutiveBoundary(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        Map<Integer, Integer> boundaries = new HashMap<>();
        int maxLength = 0;
        
        for (int num : nums) {
            // Skip duplicates
            if (boundaries.containsKey(num)) {
                continue;
            }
            
            int left = boundaries.getOrDefault(num - 1, 0);
            int right = boundaries.getOrDefault(num + 1, 0);
            int totalLength = left + right + 1;
            
            // Update the sequence length
            boundaries.put(num, totalLength);
            
            // Update boundaries
            if (left > 0) {
                boundaries.put(num - left, totalLength);
            }
            if (right > 0) {
                boundaries.put(num + right, totalLength);
            }
            
            maxLength = Math.max(maxLength, totalLength);
        }
        
        return maxLength;
    }
    
    /**
     * Helper method to visualize the sequence finding process
     */
    private void visualizeSequenceFinding(int[] nums, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Input: " + Arrays.toString(nums));
        
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) numSet.add(num);
        
        System.out.println("Number Set: " + numSet);
        System.out.println("Finding sequence starts and counting sequences:");
        
        int longestStreak = 0;
        Set<Integer> processed = new HashSet<>();
        
        for (int num : numSet) {
            if (processed.contains(num)) continue;
            
            // Check if this is a sequence start
            if (!numSet.contains(num - 1)) {
                System.out.println("\nStarting sequence from: " + num);
                int currentNum = num;
                int currentStreak = 1;
                processed.add(num);
                
                System.out.print("Sequence: [" + num);
                
                // Count consecutive numbers forward
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                    processed.add(currentNum);
                    System.out.print(", " + currentNum);
                }
                
                System.out.println("]");
                System.out.println("Length: " + currentStreak);
                
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        
        System.out.println("\nLongest consecutive sequence length: " + longestStreak);
    }
    
    /**
     * Helper method to verify result
     */
    private boolean verifyResult(int[] nums, int result) {
        if (nums == null || nums.length == 0) {
            return result == 0;
        }
        
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) numSet.add(num);
        
        int expected = 0;
        for (int num : numSet) {
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }
                expected = Math.max(expected, currentStreak);
            }
        }
        
        return result == expected;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Longest Consecutive Sequence Solution:");
        System.out.println("===============================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] nums1 = {100, 4, 200, 1, 3, 2};
        int expected1 = 4;
        
        long startTime = System.nanoTime();
        int result1a = solution.longestConsecutive(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.longestConsecutiveUnionFind(nums1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.longestConsecutiveSorting(nums1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.longestConsecutiveHashSetRemoval(nums1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1e = solution.longestConsecutiveBoundary(nums1);
        long time1e = System.nanoTime() - startTime;
        
        System.out.println("HashSet (Recommended): " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Union Find: " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Sorting: " + result1c + " - " + 
                         (result1c == expected1 ? "PASSED" : "FAILED"));
        System.out.println("HashSet Removal: " + result1d + " - " + 
                         (result1d == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Boundary Detection: " + result1e + " - " + 
                         (result1e == expected1 ? "PASSED" : "FAILED"));
        
        // Visualize the sequence finding process
        solution.visualizeSequenceFinding(nums1, "HashSet Approach");
        
        // Test case 2: Longer sequence with duplicates
        System.out.println("\nTest 2: Longer sequence with duplicates");
        int[] nums2 = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        int expected2 = 9;
        
        int result2a = solution.longestConsecutive(nums2);
        System.out.println("Long sequence: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Empty array
        System.out.println("\nTest 3: Empty array");
        int[] nums3 = {};
        int expected3 = 0;
        
        int result3a = solution.longestConsecutive(nums3);
        System.out.println("Empty array: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single element
        System.out.println("\nTest 4: Single element");
        int[] nums4 = {5};
        int expected4 = 1;
        
        int result4a = solution.longestConsecutive(nums4);
        System.out.println("Single element: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: All duplicates
        System.out.println("\nTest 5: All duplicates");
        int[] nums5 = {1, 1, 1, 1};
        int expected5 = 1;
        
        int result5a = solution.longestConsecutive(nums5);
        System.out.println("All duplicates: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: No consecutive numbers
        System.out.println("\nTest 6: No consecutive numbers");
        int[] nums6 = {1, 3, 5, 7, 9};
        int expected6 = 1;
        
        int result6a = solution.longestConsecutive(nums6);
        System.out.println("No consecutive: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Negative numbers
        System.out.println("\nTest 7: Negative numbers");
        int[] nums7 = {-1, -2, -3, 1, 2, 3, 0};
        int expected7 = 7;
        
        int result7a = solution.longestConsecutive(nums7);
        System.out.println("Negative numbers: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Large numbers
        System.out.println("\nTest 8: Large numbers");
        int[] nums8 = {2147483646, -2147483647, 2147483645, 2147483647};
        int result8a = solution.longestConsecutive(nums8);
        boolean valid8 = solution.verifyResult(nums8, result8a);
        System.out.println("Large numbers: " + result8a + " - " + 
                         (valid8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  HashSet: " + time1a + " ns");
        System.out.println("  Union Find: " + time1b + " ns");
        System.out.println("  Sorting: " + time1c + " ns");
        System.out.println("  HashSet Removal: " + time1d + " ns");
        System.out.println("  Boundary Detection: " + time1e + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[] largeNums = new int[100000];
        Random random = new Random(42);
        for (int i = 0; i < largeNums.length; i++) {
            largeNums[i] = random.nextInt(1000000);
        }
        
        startTime = System.nanoTime();
        int result10a = solution.longestConsecutive(largeNums);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result10b = solution.longestConsecutiveSorting(largeNums);
        long time10b = System.nanoTime() - startTime;
        
        System.out.println("Large input (100,000 elements):");
        System.out.println("  HashSet: " + time10a + " ns, Result: " + result10a);
        System.out.println("  Sorting: " + time10b + " ns, Result: " + result10b);
        System.out.println("  Results match: " + (result10a == result10b));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("A number is the start of a sequence if (num - 1) is not in the set.");
        System.out.println("This ensures we only process each sequence once.");
        
        System.out.println("\nExample: [100, 4, 200, 1, 3, 2]");
        System.out.println("Number Set: {1, 2, 3, 4, 100, 200}");
        System.out.println("Sequence starts:");
        System.out.println("  - 1 is start (0 not in set) → Sequence: 1,2,3,4 → Length: 4");
        System.out.println("  - 100 is start (99 not in set) → Sequence: 100 → Length: 1");
        System.out.println("  - 200 is start (199 not in set) → Sequence: 200 → Length: 1");
        System.out.println("Longest sequence: 4");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. HashSet with Sequence Starts (RECOMMENDED):");
        System.out.println("   Time: O(n) - Each number processed at most twice");
        System.out.println("   Space: O(n) - HashSet storage");
        System.out.println("   How it works:");
        System.out.println("     - Store all numbers in HashSet for O(1) lookups");
        System.out.println("     - For each number, check if it's a sequence start");
        System.out.println("     - Only count sequences from start numbers");
        System.out.println("     - Track maximum sequence length");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time complexity");
        System.out.println("     - Simple and elegant");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Requires O(n) extra space");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Union Find (Disjoint Set Union):");
        System.out.println("   Time: O(n α(n)) - Amortized nearly linear");
        System.out.println("   Space: O(n) - Parent and size arrays");
        System.out.println("   How it works:");
        System.out.println("     - Create Union-Find data structure");
        System.out.println("     - Union consecutive numbers");
        System.out.println("     - Find largest connected component");
        System.out.println("   Pros:");
        System.out.println("     - Good for dynamic connectivity problems");
        System.out.println("     - Demonstrates advanced data structure knowledge");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Overkill for this problem");
        System.out.println("   Best for: Learning Union-Find, dynamic scenarios");
        
        System.out.println("\n3. Sorting:");
        System.out.println("   Time: O(n log n) - Dominated by sorting");
        System.out.println("   Space: O(1) or O(n) - Depending on sort implementation");
        System.out.println("   How it works:");
        System.out.println("     - Sort the array");
        System.out.println("     - Iterate through sorted array");
        System.out.println("     - Count consecutive sequences");
        System.out.println("   Pros:");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - No extra space if sorting in-place");
        System.out.println("   Cons:");
        System.out.println("     - O(n log n) doesn't meet O(n) requirement");
        System.out.println("     - Not optimal for large inputs");
        System.out.println("   Best for: Small inputs, when simplicity is priority");
        
        System.out.println("\n4. HashSet with Removal:");
        System.out.println("   Time: O(n) - Each number processed once");
        System.out.println("   Space: O(n) - HashSet storage");
        System.out.println("   How it works:");
        System.out.println("     - Store numbers in HashSet");
        System.out.println("     - Remove numbers as they are processed");
        System.out.println("     - Expand sequences in both directions");
        System.out.println("   Pros:");
        System.out.println("     - Processes each number exactly once");
        System.out.println("     - Clean implementation");
        System.out.println("   Cons:");
        System.out.println("     - Modifies the set during iteration");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: Alternative O(n) approach");
        
        System.out.println("\n5. Boundary Detection:");
        System.out.println("   Time: O(n) - Single pass with HashMap");
        System.out.println("   Space: O(n) - HashMap storage");
        System.out.println("   How it works:");
        System.out.println("     - Track sequence boundaries in HashMap");
        System.out.println("     - Update boundaries when connecting sequences");
        System.out.println("     - Track maximum sequence length");
        System.out.println("   Pros:");
        System.out.println("     - Single pass algorithm");
        System.out.println("     - Elegant boundary management");
        System.out.println("   Cons:");
        System.out.println("     - More complex logic");
        System.out.println("     - Harder to understand");
        System.out.println("   Best for: Advanced algorithm practice");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY THE HASHSET APPROACH IS O(n):");
        System.out.println("1. Adding n numbers to HashSet: O(n)");
        System.out.println("2. Checking sequence starts: O(n)");
        System.out.println("3. Counting sequences: Each number visited at most twice");
        System.out.println("4. Total: O(n) + O(n) + O(n) = O(n)");
        System.out.println("The key is that we only process sequences from their starts,");
        System.out.println("ensuring each number is part of exactly one sequence count.");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with HashSet approach - it's the expected solution");
        System.out.println("2. Explain the sequence start concept clearly");
        System.out.println("3. Draw examples to demonstrate the algorithm");
        System.out.println("4. Mention why sorting doesn't meet O(n) requirement");
        System.out.println("5. Discuss time/space complexity trade-offs");
        System.out.println("6. Handle edge cases: empty array, duplicates, single element");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
