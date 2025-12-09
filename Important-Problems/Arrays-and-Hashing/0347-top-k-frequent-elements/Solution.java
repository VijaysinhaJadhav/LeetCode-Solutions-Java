/**
 * 347. Top K Frequent Elements
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an integer array nums and an integer k, return the k most frequent elements.
 * You may return the answer in any order.
 * 
 * Key Insights:
 * 1. HashMap + Bucket Sort: O(n) time, O(n) space - optimal
 * 2. HashMap + Min-Heap: O(n log k) time, O(n) space - good for large n, small k
 * 3. HashMap + QuickSelect: O(n) average time, O(n) space
 * 4. HashMap + Sorting: O(n log n) time - simple but doesn't meet follow-up
 * 
 * Approach (Bucket Sort):
 * 1. Count frequencies using HashMap
 * 2. Create buckets where index = frequency, value = list of numbers with that frequency
 * 3. Iterate from highest frequency bucket to collect top k elements
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 * 
 * Tags: Hash Table, Heap, Sorting, Bucket Sort, Counting
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Bucket Sort (RECOMMENDED)
     * O(n) time, O(n) space - Meets follow-up requirement
     */
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Count frequencies
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Step 2: Create buckets - index = frequency, value = list of numbers
        // Using List of Lists instead of array to avoid generic array creation
        List<List<Integer>> buckets = new ArrayList<>(nums.length + 1);
        for (int i = 0; i <= nums.length; i++) {
            buckets.add(new ArrayList<>());
        }
        
        for (int num : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(num);
            buckets.get(frequency).add(num);
        }
        
        // Step 3: Collect top k frequent elements
        int[] result = new int[k];
        int index = 0;
        
        // Iterate from highest frequency to lowest
        for (int i = buckets.size() - 1; i >= 0 && index < k; i--) {
            List<Integer> bucket = buckets.get(i);
            if (!bucket.isEmpty()) {
                for (int num : bucket) {
                    result[index++] = num;
                    if (index == k) {
                        break;
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: Min-Heap (Priority Queue)
     * O(n log k) time, O(n) space - Good when k is small
     */
    public int[] topKFrequentMinHeap(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Min-heap based on frequency (keep smallest frequencies at top to remove)
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = 
            new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        
        // Maintain heap size k
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll(); // Remove the least frequent element
            }
        }
        
        // Extract results from heap
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll().getKey();
        }
        
        return result;
    }
    
    /**
     * Approach 3: QuickSelect (Lomuto Partition)
     * O(n) average time, O(n) worst case, O(n) space
     */
    public int[] topKFrequentQuickSelect(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Create array of unique numbers
        int n = frequencyMap.size();
        int[] unique = new int[n];
        int i = 0;
        for (int num : frequencyMap.keySet()) {
            unique[i++] = num;
        }
        
        // Use QuickSelect to find kth most frequent element
        int left = 0, right = n - 1;
        int targetIndex = n - k; // We want elements from targetIndex to end
        
        while (left <= right) {
            int pivotIndex = partition(unique, left, right, frequencyMap);
            
            if (pivotIndex == targetIndex) {
                break;
            } else if (pivotIndex < targetIndex) {
                left = pivotIndex + 1;
            } else {
                right = pivotIndex - 1;
            }
        }
        
        // Return top k frequent elements
        return Arrays.copyOfRange(unique, targetIndex, n);
    }
    
    private int partition(int[] unique, int left, int right, Map<Integer, Integer> frequencyMap) {
        int pivotFrequency = frequencyMap.get(unique[right]);
        int i = left;
        
        for (int j = left; j < right; j++) {
            if (frequencyMap.get(unique[j]) <= pivotFrequency) {
                swap(unique, i, j);
                i++;
            }
        }
        swap(unique, i, right);
        return i;
    }
    
    /**
     * Approach 4: Max-Heap (Simpler but less efficient)
     * O(n log n) time, O(n) space - Simple but doesn't meet follow-up
     */
    public int[] topKFrequentMaxHeap(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Max-heap based on frequency
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = 
            new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        
        // Add all entries to heap
        maxHeap.addAll(frequencyMap.entrySet());
        
        // Extract top k
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll().getKey();
        }
        
        return result;
    }
    
    /**
     * Approach 5: TreeMap with reverse frequency ordering
     * O(n log n) time, O(n) space - Clean but not optimal
     */
    public int[] topKFrequentTreeMap(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // TreeMap with reverse order (highest frequency first)
        TreeMap<Integer, List<Integer>> sortedMap = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int num = entry.getKey();
            int frequency = entry.getValue();
            sortedMap.putIfAbsent(frequency, new ArrayList<>());
            sortedMap.get(frequency).add(num);
        }
        
        // Collect top k
        int[] result = new int[k];
        int index = 0;
        for (List<Integer> numbers : sortedMap.values()) {
            for (int num : numbers) {
                result[index++] = num;
                if (index == k) {
                    return result;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Utility method to swap elements in array
     */
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * Helper method to verify result contains correct elements (order doesn't matter)
     */
    private boolean verifyTopK(int[] result, int[] expected) {
        if (result.length != expected.length) {
            return false;
        }
        
        // Since order doesn't matter, we can use sets
        Set<Integer> resultSet = new HashSet<>();
        for (int num : result) resultSet.add(num);
        Set<Integer> expectedSet = new HashSet<>();
        for (int num : expected) expectedSet.add(num);
        
        return resultSet.equals(expectedSet);
    }
    
    /**
     * Helper method to print frequency analysis
     */
    private void printFrequencyAnalysis(int[] nums, Map<Integer, Integer> frequencyMap) {
        System.out.println("Frequency Analysis:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Frequencies: " + frequencyMap);
        
        // Sort by frequency for display
        List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>(frequencyMap.entrySet());
        sortedEntries.sort((a, b) -> b.getValue() - a.getValue());
        
        System.out.print("Sorted by frequency: [");
        for (int i = 0; i < sortedEntries.size(); i++) {
            Map.Entry<Integer, Integer> entry = sortedEntries.get(i);
            System.out.print(entry.getKey() + "(" + entry.getValue() + ")");
            if (i < sortedEntries.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Top K Frequent Elements Solution:");
        System.out.println("==========================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int k1 = 2;
        int[] expected1 = {1, 2};
        
        Map<Integer, Integer> freq1 = new HashMap<>();
        for (int num : nums1) freq1.put(num, freq1.getOrDefault(num, 0) + 1);
        solution.printFrequencyAnalysis(nums1, freq1);
        
        long startTime = System.nanoTime();
        int[] result1a = solution.topKFrequent(nums1, k1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1b = solution.topKFrequentMinHeap(nums1, k1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1c = solution.topKFrequentQuickSelect(nums1, k1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1d = solution.topKFrequentMaxHeap(nums1, k1);
        long time1d = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1e = solution.topKFrequentTreeMap(nums1, k1);
        long time1e = System.nanoTime() - startTime;
        
        boolean test1a = solution.verifyTopK(result1a, expected1);
        boolean test1b = solution.verifyTopK(result1b, expected1);
        boolean test1c = solution.verifyTopK(result1c, expected1);
        boolean test1d = solution.verifyTopK(result1d, expected1);
        boolean test1e = solution.verifyTopK(result1e, expected1);
        
        System.out.println("Bucket Sort: " + (test1a ? "PASSED" : "FAILED") + " - " + Arrays.toString(result1a));
        System.out.println("Min-Heap: " + (test1b ? "PASSED" : "FAILED") + " - " + Arrays.toString(result1b));
        System.out.println("QuickSelect: " + (test1c ? "PASSED" : "FAILED") + " - " + Arrays.toString(result1c));
        System.out.println("Max-Heap: " + (test1d ? "PASSED" : "FAILED") + " - " + Arrays.toString(result1d));
        System.out.println("TreeMap: " + (test1e ? "PASSED" : "FAILED") + " - " + Arrays.toString(result1e));
        
        // Test case 2: Single element
        System.out.println("\nTest 2: Single element");
        int[] nums2 = {1};
        int k2 = 1;
        int[] expected2 = {1};
        
        int[] result2a = solution.topKFrequent(nums2, k2);
        boolean test2a = Arrays.equals(result2a, expected2);
        System.out.println("Single element - Bucket Sort: " + (test2a ? "PASSED" : "FAILED"));
        
        // Test case 3: All same elements
        System.out.println("\nTest 3: All same elements");
        int[] nums3 = {1, 1, 1, 1};
        int k3 = 1;
        int[] expected3 = {1};
        
        int[] result3a = solution.topKFrequent(nums3, k3);
        boolean test3a = Arrays.equals(result3a, expected3);
        System.out.println("All same - Bucket Sort: " + (test3a ? "PASSED" : "FAILED"));
        
        // Test case 4: k equals array length
        System.out.println("\nTest 4: k equals number of unique elements");
        int[] nums4 = {1, 2, 3};
        int k4 = 3;
        int[] result4a = solution.topKFrequent(nums4, k4);
        Arrays.sort(result4a);
        int[] expected4 = {1, 2, 3};
        boolean test4a = Arrays.equals(result4a, expected4);
        System.out.println("k = n_unique - Bucket Sort: " + (test4a ? "PASSED" : "FAILED"));
        
        // Test case 5: Negative numbers
        System.out.println("\nTest 5: Negative numbers");
        int[] nums5 = {-1, -1, -2, -2, -2, -3};
        int k5 = 2;
        int[] expected5 = {-2, -1};
        
        int[] result5a = solution.topKFrequent(nums5, k5);
        boolean test5a = solution.verifyTopK(result5a, expected5);
        System.out.println("Negative numbers - Bucket Sort: " + (test5a ? "PASSED" : "FAILED"));
        
        // Test case 6: Multiple elements with same frequency
        System.out.println("\nTest 6: Multiple elements with same frequency");
        int[] nums6 = {1, 1, 2, 2, 3, 3, 4};
        int k6 = 2;
        // Any two of {1, 2, 3} are acceptable since they have same frequency
        int[] result6a = solution.topKFrequent(nums6, k6);
        System.out.println("Same frequency - Bucket Sort: " + Arrays.toString(result6a) + " (any 2 of {1,2,3})");
        
        // Performance comparison for large array
        System.out.println("\nTest 7: Performance comparison (large array)");
        int[] nums7 = new int[1000]; // Reduced size for faster testing
        Random random = new Random(42);
        for (int i = 0; i < nums7.length; i++) {
            nums7[i] = random.nextInt(100); // Limited range to create frequencies
        }
        int k7 = 10;
        
        startTime = System.nanoTime();
        int[] result7a = solution.topKFrequent(nums7, k7);
        long time7a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7b = solution.topKFrequentMinHeap(nums7, k7);
        long time7b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7c = solution.topKFrequentQuickSelect(nums7, k7);
        long time7c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result7d = solution.topKFrequentMaxHeap(nums7, k7);
        long time7d = System.nanoTime() - startTime;
        
        System.out.println("Large array (1000 elements, k=10):");
        System.out.println("  Bucket Sort: " + time7a + " ns");
        System.out.println("  Min-Heap: " + time7b + " ns");
        System.out.println("  QuickSelect: " + time7c + " ns");
        System.out.println("  Max-Heap: " + time7d + " ns");
        
        // Verify all approaches produce valid results (same set of elements)
        Set<Integer> set7a = new HashSet<>();
        for (int num : result7a) set7a.add(num);
        Set<Integer> set7b = new HashSet<>();
        for (int num : result7b) set7b.add(num);
        Set<Integer> set7c = new HashSet<>();
        for (int num : result7c) set7c.add(num);
        Set<Integer> set7d = new HashSet<>();
        for (int num : result7d) set7d.add(num);
        
        boolean allValid = set7a.equals(set7b) && set7a.equals(set7c) && set7a.equals(set7d);
        System.out.println("  All approaches produce same set: " + allValid);
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Bucket Sort (RECOMMENDED):");
        System.out.println("   Time: O(n) - Two passes: counting + bucket collection");
        System.out.println("   Space: O(n) - Frequency map + buckets array");
        System.out.println("   How it works:");
        System.out.println("     - Count frequencies using HashMap");
        System.out.println("     - Create buckets where index = frequency");
        System.out.println("     - Iterate from highest frequency bucket to collect top k");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time complexity");
        System.out.println("     - Meets the follow-up requirement");
        System.out.println("     - Elegant and efficient");
        System.out.println("   Cons:");
        System.out.println("     - Requires O(n) extra space for buckets");
        System.out.println("   Best for: Most cases, especially when optimal time is required");
        
        System.out.println("\n2. Min-Heap (Priority Queue):");
        System.out.println("   Time: O(n log k) - n insertions with heap size k");
        System.out.println("   Space: O(n) - Frequency map + heap");
        System.out.println("   How it works:");
        System.out.println("     - Count frequencies using HashMap");
        System.out.println("     - Use min-heap to maintain top k elements");
        System.out.println("     - Remove smallest when heap exceeds size k");
        System.out.println("   Pros:");
        System.out.println("     - Efficient when k is small compared to n");
        System.out.println("     - Only requires O(k) extra space for heap");
        System.out.println("     - Good practical performance");
        System.out.println("   Cons:");
        System.out.println("     - O(n log k) doesn't meet strict O(n) follow-up");
        System.out.println("   Best for: When k is small, practical applications");
        
        System.out.println("\n3. QuickSelect:");
        System.out.println("   Time: O(n) average, O(n²) worst case");
        System.out.println("   Space: O(n) - Frequency map + unique array");
        System.out.println("   How it works:");
        System.out.println("     - Count frequencies using HashMap");
        System.out.println("     - Use QuickSelect to find kth most frequent element");
        System.out.println("     - All elements from kth to end are top k");
        System.out.println("   Pros:");
        System.out.println("     - O(n) average case performance");
        System.out.println("     - In-place partitioning");
        System.out.println("   Cons:");
        System.out.println("     - Worst case O(n²) time");
        System.out.println("     - More complex implementation");
        System.out.println("   Best for: When average case performance is acceptable");
        
        System.out.println("\n4. Max-Heap:");
        System.out.println("   Time: O(n log n) - Building heap with all elements");
        System.out.println("   Space: O(n) - Frequency map + heap");
        System.out.println("   How it works:");
        System.out.println("     - Count frequencies using HashMap");
        System.out.println("     - Build max-heap with all frequency entries");
        System.out.println("     - Extract top k elements");
        System.out.println("   Pros:");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to implement");
        System.out.println("   Cons:");
        System.out.println("     - O(n log n) doesn't meet follow-up");
        System.out.println("     - Less efficient for large n");
        System.out.println("   Best for: Small inputs, simplicity over performance");
        
        System.out.println("\n5. TreeMap:");
        System.out.println("   Time: O(n log n) - TreeMap operations are O(log n)");
        System.out.println("   Space: O(n) - Frequency map + TreeMap");
        System.out.println("   How it works:");
        System.out.println("     - Count frequencies using HashMap");
        System.out.println("     - Use TreeMap with reverse ordering by frequency");
        System.out.println("     - Collect top k from highest frequency buckets");
        System.out.println("   Pros:");
        System.out.println("     - Clean and readable code");
        System.out.println("     - Built-in sorting");
        System.out.println("   Cons:");
        System.out.println("     - O(n log n) doesn't meet follow-up");
        System.out.println("     - Overhead of TreeMap");
        System.out.println("   Best for: Code readability, small to medium inputs");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("WHY BUCKET SORT WORKS FOR THIS PROBLEM:");
        System.out.println("1. Frequencies are bounded: 1 <= frequency <= n");
        System.out.println("2. We can create an array of size n+1 for buckets");
        System.out.println("3. Each bucket contains numbers with that frequency");
        System.out.println("4. We can iterate from highest frequency to get top k");
        System.out.println("5. This avoids the O(n log n) sorting bottleneck");
        System.out.println("=".repeat(80));
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Bucket Sort - it's optimal and meets follow-up");
        System.out.println("2. Explain why frequencies are bounded by array length");
        System.out.println("3. Mention Min-Heap as good alternative for small k");
        System.out.println("4. Discuss trade-offs between different approaches");
        System.out.println("5. Handle edge cases: k=1, k=n_unique, all same elements");
        System.out.println("6. Practice implementing Bucket Sort until it's intuitive");
        System.out.println("=".repeat(80));
        
        System.out.println("\nAll tests completed!");
    }
}
