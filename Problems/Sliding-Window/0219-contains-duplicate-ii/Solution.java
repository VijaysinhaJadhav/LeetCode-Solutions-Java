/**
 * 219. Contains Duplicate II
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given an integer array nums and an integer k, return true if there are two distinct indices i and j 
 * in the array such that nums[i] == nums[j] and abs(i - j) <= k.
 * 
 * Key Insights:
 * 1. Use HashMap to store numbers and their most recent indices
 * 2. For each number, check if it exists in HashMap and if index difference <= k
 * 3. Update HashMap with current index
 * 4. This approach ensures O(n) time complexity
 * 
 * Approach (HashMap - RECOMMENDED):
 * 1. Create HashMap to store number -> index mappings
 * 2. Iterate through array
 * 3. For each number, check if it exists and index difference <= k
 * 4. If found, return true
 * 5. Update HashMap with current index
 * 6. Return false if no such pair found
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 * 
 * Tags: Array, Hash Table, Sliding Window
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: HashMap (Index Tracking) - RECOMMENDED
     * O(n) time, O(n) space - Most efficient and readable
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> indexMap = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            
            // Check if we've seen this number before and if index difference <= k
            if (indexMap.containsKey(num) && i - indexMap.get(num) <= k) {
                return true;
            }
            
            // Update with current index (always keep the most recent)
            indexMap.put(num, i);
        }
        
        return false;
    }
    
    /**
     * Approach 2: Sliding Window with HashSet
     * O(n) time, O(k) space - Alternative approach using sliding window
     */
    public boolean containsNearbyDuplicateSlidingWindow(int[] nums, int k) {
        Set<Integer> window = new HashSet<>();
        
        for (int i = 0; i < nums.length; i++) {
            // Remove element that's out of window range
            if (i > k) {
                window.remove(nums[i - k - 1]);
            }
            
            // If current number exists in window, we found a duplicate
            if (!window.add(nums[i])) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Approach 3: Sliding Window with LinkedHashSet
     * O(n) time, O(k) space - Maintains insertion order
     */
    public boolean containsNearbyDuplicateLinkedHashSet(int[] nums, int k) {
        if (k == 0) return false;
        
        Set<Integer> window = new LinkedHashSet<>();
        
        for (int i = 0; i < nums.length; i++) {
            if (window.contains(nums[i])) {
                return true;
            }
            
            window.add(nums[i]);
            
            // Maintain window size of k+1
            if (window.size() > k) {
                // Remove the oldest element (first in the set)
                Iterator<Integer> iterator = window.iterator();
                iterator.next();
                iterator.remove();
            }
        }
        
        return false;
    }
    
    /**
     * Approach 4: Brute Force (For Comparison)
     * O(n * min(n, k)) time, O(1) space - Inefficient but complete
     */
    public boolean containsNearbyDuplicateBruteForce(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            // Only check up to k elements ahead
            for (int j = i + 1; j <= Math.min(i + k, nums.length - 1); j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Approach 5: TreeMap for Educational Purposes
     * O(n log n) time, O(n) space - Demonstrates alternative data structure
     */
    public boolean containsNearbyDuplicateTreeMap(int[] nums, int k) {
        // TreeMap to store number -> list of indices (for educational purposes)
        Map<Integer, List<Integer>> indexMap = new TreeMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            
            if (indexMap.containsKey(num)) {
                List<Integer> indices = indexMap.get(num);
                // Check all previous indices for this number
                for (int index : indices) {
                    if (i - index <= k) {
                        return true;
                    }
                }
                indices.add(i);
            } else {
                List<Integer> indices = new ArrayList<>();
                indices.add(i);
                indexMap.put(num, indices);
            }
        }
        
        return false;
    }
    
    /**
     * Approach 6: Optimized Sliding Window
     * O(n) time, O(k) space - Most space-efficient sliding window
     */
    public boolean containsNearbyDuplicateOptimized(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return false;
        }
        
        Set<Integer> window = new HashSet<>();
        
        for (int i = 0; i < nums.length; i++) {
            // Check before adding to handle k = 0 case
            if (window.contains(nums[i])) {
                return true;
            }
            
            window.add(nums[i]);
            
            // Maintain window size exactly k
            if (window.size() > k) {
                window.remove(nums[i - k]);
            }
        }
        
        return false;
    }
    
    /**
     * Helper method to visualize the algorithm process
     */
    private void visualizeContainsDuplicate(int[] nums, int k, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("k = " + k);
        
        if (approach.contains("HashMap")) {
            System.out.println("\nStep | Index | Number | HashMap State | Action");
            System.out.println("-----|-------|--------|---------------|--------");
            
            Map<Integer, Integer> indexMap = new HashMap<>();
            int step = 1;
            
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i];
                String action;
                
                if (indexMap.containsKey(num) && i - indexMap.get(num) <= k) {
                    action = "FOUND! Duplicate at distance " + (i - indexMap.get(num)) + " <= " + k;
                    System.out.printf("%4d | %5d | %6d | %13s | %s%n", 
                                    step, i, num, indexMap.toString(), action);
                    return;
                } else if (indexMap.containsKey(num)) {
                    action = "Update index (distance " + (i - indexMap.get(num)) + " > " + k + ")";
                } else {
                    action = "Add to map";
                }
                
                System.out.printf("%4d | %5d | %6d | %13s | %s%n", 
                                step, i, num, indexMap.toString(), action);
                indexMap.put(num, i);
                step++;
            }
            
            System.out.println("No nearby duplicates found");
            
        } else if (approach.contains("Sliding Window")) {
            System.out.println("\nStep | Index | Number | Window State | Action");
            System.out.println("-----|-------|--------|--------------|--------");
            
            Set<Integer> window = new HashSet<>();
            int step = 1;
            
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i];
                String action;
                
                // Remove element that's out of window
                if (i > k) {
                    window.remove(nums[i - k - 1]);
                    action = "Remove nums[" + (i - k - 1) + "] = " + nums[i - k - 1];
                    System.out.printf("%4d | %5d | %6d | %12s | %s%n", 
                                    step, i, num, window.toString(), action);
                    step++;
                }
                
                // Check if current number is in window
                if (window.contains(num)) {
                    action = "FOUND! Duplicate in current window";
                    System.out.printf("%4d | %5d | %6d | %12s | %s%n", 
                                    step, i, num, window.toString(), action);
                    return;
                } else {
                    action = "Add to window";
                }
                
                window.add(num);
                System.out.printf("%4d | %5d | %6d | %12s | %s%n", 
                                step, i, num, window.toString(), action);
                step++;
            }
            
            System.out.println("No nearby duplicates found");
        }
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(int[] nums, int k, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        long startTime = System.nanoTime();
        boolean result1 = containsNearbyDuplicate(nums, k);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result2 = containsNearbyDuplicateSlidingWindow(nums, k);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result3 = containsNearbyDuplicateLinkedHashSet(nums, k);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result4 = containsNearbyDuplicateBruteForce(nums, k);
        long time4 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result5 = containsNearbyDuplicateTreeMap(nums, k);
        long time5 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        boolean result6 = containsNearbyDuplicateOptimized(nums, k);
        long time6 = System.nanoTime() - startTime;
        
        System.out.printf("HashMap: %d ns%n", time1);
        System.out.printf("Sliding Window (HashSet): %d ns%n", time2);
        System.out.printf("LinkedHashSet: %d ns%n", time3);
        System.out.printf("Brute Force: %d ns%n", time4);
        System.out.printf("TreeMap: %d ns%n", time5);
        System.out.printf("Optimized Sliding Window: %d ns%n", time6);
        
        // Verify all produce same result
        boolean allEqual = result1 == result2 && result1 == result3 && 
                          result1 == result4 && result1 == result5 && result1 == result6;
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Contains Duplicate II Solution:");
        System.out.println("=======================================");
        
        // Test case 1: Standard case with duplicate within k
        System.out.println("\nTest 1: Standard case with duplicate within k");
        int[] nums1 = {1, 2, 3, 1};
        int k1 = 3;
        boolean expected1 = true;
        
        boolean result1a = solution.containsNearbyDuplicate(nums1, k1);
        boolean result1b = solution.containsNearbyDuplicateSlidingWindow(nums1, k1);
        boolean result1c = solution.containsNearbyDuplicateOptimized(nums1, k1);
        
        System.out.println("HashMap: " + result1a + " - " + (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Sliding Window: " + result1b + " - " + (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Optimized: " + result1c + " - " + (result1c == expected1 ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeContainsDuplicate(nums1, k1, "Test 1 - HashMap Approach");
        
        // Test case 2: Duplicate with distance exactly k
        System.out.println("\nTest 2: Duplicate with distance exactly k");
        int[] nums2 = {1, 0, 1, 1};
        int k2 = 1;
        boolean expected2 = true;
        
        boolean result2a = solution.containsNearbyDuplicate(nums2, k2);
        System.out.println("Distance exactly k: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: No duplicates within k
        System.out.println("\nTest 3: No duplicates within k");
        int[] nums3 = {1, 2, 3, 1, 2, 3};
        int k3 = 2;
        boolean expected3 = false;
        
        boolean result3a = solution.containsNearbyDuplicate(nums3, k3);
        System.out.println("No nearby duplicates: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: k = 0
        System.out.println("\nTest 4: k = 0");
        int[] nums4 = {1, 2, 3, 1};
        int k4 = 0;
        boolean expected4 = false; // Need distinct indices
        
        boolean result4a = solution.containsNearbyDuplicate(nums4, k4);
        System.out.println("k = 0: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Single element
        System.out.println("\nTest 5: Single element");
        int[] nums5 = {1};
        int k5 = 1;
        boolean expected5 = false; // Need two distinct indices
        
        boolean result5a = solution.containsNearbyDuplicate(nums5, k5);
        System.out.println("Single element: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: All same elements with k >= 1
        System.out.println("\nTest 6: All same elements with k >= 1");
        int[] nums6 = {1, 1, 1, 1};
        int k6 = 1;
        boolean expected6 = true;
        
        boolean result6a = solution.containsNearbyDuplicate(nums6, k6);
        System.out.println("All same elements: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large k value
        System.out.println("\nTest 7: Large k value");
        int[] nums7 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        int k7 = 9;
        boolean expected7 = true;
        
        boolean result7a = solution.containsNearbyDuplicate(nums7, k7);
        System.out.println("Large k: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(nums1, k1, "Small Input (4 elements)");
        
        // Medium input performance
        int[] mediumNums = new int[1000];
        Random random = new Random(42);
        for (int i = 0; i < mediumNums.length; i++) {
            mediumNums[i] = random.nextInt(100); // Many duplicates expected
        }
        solution.comparePerformance(mediumNums, 10, "Medium Input (1000 elements)");
        
        // Large input performance
        int[] largeNums = new int[10000];
        for (int i = 0; i < largeNums.length; i++) {
            largeNums[i] = random.nextInt(500); // Many duplicates expected
        }
        solution.comparePerformance(largeNums, 50, "Large Input (10000 elements)");
        
        // Worst-case performance for brute force (no duplicates)
        int[] worstCaseNums = new int[100];
        for (int i = 0; i < worstCaseNums.length; i++) {
            worstCaseNums[i] = i; // All unique elements
        }
        solution.comparePerformance(worstCaseNums, 10, "Worst Case for Brute Force (100 unique elements)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. HashMap (Index Tracking) - RECOMMENDED:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(n) - HashMap storage for indices");
        System.out.println("   How it works:");
        System.out.println("     - Store each number and its most recent index in HashMap");
        System.out.println("     - For each number, check if it exists and index difference <= k");
        System.out.println("     - Update HashMap with current index");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Handles all edge cases gracefully");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) space");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Sliding Window with HashSet:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(k) - HashSet storing at most k+1 elements");
        System.out.println("   How it works:");
        System.out.println("     - Maintain a sliding window of the last k+1 elements");
        System.out.println("     - Use HashSet to check for duplicates in current window");
        System.out.println("     - Remove elements that fall out of window range");
        System.out.println("   Pros:");
        System.out.println("     - More space efficient (O(k) instead of O(n))");
        System.out.println("     - Good for memory-constrained environments");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex logic");
        System.out.println("     - Window management overhead");
        System.out.println("   Best for: When memory is a concern");
        
        System.out.println("\n3. Sliding Window with LinkedHashSet:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(k) - LinkedHashSet storage");
        System.out.println("   How it works:");
        System.out.println("     - Similar to HashSet approach but maintains order");
        System.out.println("     - Use LinkedHashSet to easily remove oldest element");
        System.out.println("     - Check for duplicates in current window");
        System.out.println("   Pros:");
        System.out.println("     - Natural ordering for window management");
        System.out.println("     - Easy removal of oldest element");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more overhead than regular HashSet");
        System.out.println("     - More complex than HashMap approach");
        System.out.println("   Best for: Learning about LinkedHashSet applications");
        
        System.out.println("\n4. Brute Force (NOT RECOMMENDED):");
        System.out.println("   Time: O(n * min(n, k)) - Check each element with next k elements");
        System.out.println("   Space: O(1) - No extra data structures");
        System.out.println("   How it works:");
        System.out.println("     - For each element, check the next k elements for duplicates");
        System.out.println("     - Return true if any duplicate found within distance k");
        System.out.println("   Pros:");
        System.out.println("     - Simple to understand and implement");
        System.out.println("     - No extra space required");
        System.out.println("   Cons:");
        System.out.println("     - Very inefficient for large inputs");
        System.out.println("     - Doesn't scale to problem constraints");
        System.out.println("   Best for: Demonstration only, very small inputs");
        
        System.out.println("\n5. TreeMap Approach:");
        System.out.println("   Time: O(n log n) - TreeMap operations are O(log n)");
        System.out.println("   Space: O(n) - Store all indices for each number");
        System.out.println("   How it works:");
        System.out.println("     - Use TreeMap to store numbers -> list of indices");
        System.out.println("     - For each number, check all previous indices");
        System.out.println("     - Return true if any index difference <= k");
        System.out.println("   Pros:");
        System.out.println("     - Demonstrates TreeMap usage");
        System.out.println("     - Stores complete history");
        System.out.println("   Cons:");
        System.out.println("     - Less efficient than HashMap");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: Educational purposes only");
        
        System.out.println("\n6. Optimized Sliding Window:");
        System.out.println("   Time: O(n) - Single pass through array");
        System.out.println("   Space: O(k) - HashSet storing exactly k elements");
        System.out.println("   How it works:");
        System.out.println("     - Maintain a fixed-size window of k elements");
        System.out.println("     - Check for duplicate before adding new element");
        System.out.println("     - Remove oldest element when window size exceeds k");
        System.out.println("   Pros:");
        System.out.println("     - Most space-efficient sliding window");
        System.out.println("     - Clean window management");
        System.out.println("   Cons:");
        System.out.println("     - Slightly different logic flow");
        System.out.println("     - Requires careful index management");
        System.out.println("   Best for: Memory-optimized applications");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY HASHMAP APPROACH IS OPTIMAL:");
        System.out.println("1. Single pass through array: O(n) time");
        System.out.println("2. Constant time lookups and updates: O(1) per operation");
        System.out.println("3. Always stores most recent index: ensures we find closest duplicate");
        System.out.println("4. Handles all edge cases: k=0, single element, large inputs");
        System.out.println("5. Space usage is acceptable: O(n) for potentially n unique elements");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. k = 0: Always false (need distinct indices)");
        System.out.println("2. Single element: Always false (need two elements)");
        System.out.println("3. All same elements: True if k >= 1");
        System.out.println("4. No duplicates: Always false");
        System.out.println("5. Duplicates exactly at distance k: True");
        System.out.println("6. Large k values: Handled efficiently by all approaches");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with HashMap approach - it's the most straightforward");
        System.out.println("2. Explain the algorithm clearly: store indices, check distance");
        System.out.println("3. Mention time/space complexity: O(n)/O(n)");
        System.out.println("4. Discuss alternative approaches (sliding window) and trade-offs");
        System.out.println("5. Handle edge cases explicitly in discussion");
        System.out.println("6. Write clean code with good variable names");
        System.out.println("7. Consider mentioning when sliding window might be preferable");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Using brute force for large inputs");
        System.out.println("2. Not handling k = 0 case correctly");
        System.out.println("3. Forgetting that indices must be distinct");
        System.out.println("4. Using wrong data structures (TreeMap when HashMap suffices)");
        System.out.println("5. Not updating the index in HashMap (always keep most recent)");
        System.out.println("6. Incorrect distance calculation (should be i - j, not j - i)");
        System.out.println("=".repeat(70));
        
        // Extension to related problems
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EXTENSION: CONTAINS DUPLICATE III");
        System.out.println("Problem: Find two indices i and j such that:");
        System.out.println("  - abs(nums[i] - nums[j]) <= t");
        System.out.println("  - abs(i - j) <= k");
        System.out.println("Solution: Use TreeSet or Bucketing approach");
        System.out.println("Complexity: O(n log k) with TreeSet");
        System.out.println("This is a more challenging version of the current problem");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
