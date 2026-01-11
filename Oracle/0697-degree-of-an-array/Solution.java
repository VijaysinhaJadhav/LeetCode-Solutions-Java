
# Solution.java

```java
import java.util.*;

/**
 * 697. Degree of an Array
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Find smallest contiguous subarray that has same degree as original array.
 * Degree = maximum frequency of any element.
 * 
 * Key Insights:
 * 1. Track frequency of each element
 * 2. Track first and last occurrence of each element
 * 3. For elements with max frequency, calculate subarray length
 * 4. Return minimum length among such elements
 */
class Solution {
    
    /**
     * Approach 1: Three HashMaps (Recommended)
     * Time: O(n), Space: O(n)
     * Most readable and straightforward
     */
    public int findShortestSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        // Maps to store frequency, first occurrence, and last occurrence
        Map<Integer, Integer> frequency = new HashMap<>();
        Map<Integer, Integer> firstOccurrence = new HashMap<>();
        Map<Integer, Integer> lastOccurrence = new HashMap<>();
        
        int maxDegree = 0;
        
        // Single pass through array
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            
            // Update frequency
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
            maxDegree = Math.max(maxDegree, frequency.get(num));
            
            // Update first occurrence (only if first time)
            if (!firstOccurrence.containsKey(num)) {
                firstOccurrence.put(num, i);
            }
            
            // Always update last occurrence
            lastOccurrence.put(num, i);
        }
        
        // Find minimum length for elements with max degree
        int minLength = Integer.MAX_VALUE;
        for (int num : frequency.keySet()) {
            if (frequency.get(num) == maxDegree) {
                int length = lastOccurrence.get(num) - firstOccurrence.get(num) + 1;
                minLength = Math.min(minLength, length);
            }
        }
        
        return minLength;
    }
    
    /**
     * Approach 2: Single HashMap with custom object
     * Time: O(n), Space: O(n)
     * More organized, less readable
     */
    public int findShortestSubArray2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        Map<Integer, ElementInfo> map = new HashMap<>();
        int maxDegree = 0;
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!map.containsKey(num)) {
                map.put(num, new ElementInfo(i));
            }
            ElementInfo info = map.get(num);
            info.count++;
            info.lastIndex = i;
            maxDegree = Math.max(maxDegree, info.count);
        }
        
        int minLength = Integer.MAX_VALUE;
        for (ElementInfo info : map.values()) {
            if (info.count == maxDegree) {
                minLength = Math.min(minLength, info.lastIndex - info.firstIndex + 1);
            }
        }
        
        return minLength;
    }
    
    /**
     * Helper class for Approach 2
     */
    class ElementInfo {
        int count;
        int firstIndex;
        int lastIndex;
        
        ElementInfo(int firstIndex) {
            this.firstIndex = firstIndex;
            this.lastIndex = firstIndex;
            this.count = 0;
        }
    }
    
    /**
     * Approach 3: Using arrays (optimized for constraints)
     * Time: O(n), Space: O(50000) = O(1) since fixed
     * Best performance for given constraints (0 <= nums[i] <= 49999)
     */
    public int findShortestSubArray3(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        // Based on constraints: nums[i] will be an integer between 0 and 49,999
        int MAX_VALUE = 50000;
        int[] frequency = new int[MAX_VALUE];
        int[] firstOccurrence = new int[MAX_VALUE];
        int[] lastOccurrence = new int[MAX_VALUE];
        
        // Initialize firstOccurrence with -1 (not found)
        Arrays.fill(firstOccurrence, -1);
        
        int maxDegree = 0;
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            frequency[num]++;
            maxDegree = Math.max(maxDegree, frequency[num]);
            
            if (firstOccurrence[num] == -1) {
                firstOccurrence[num] = i;
            }
            lastOccurrence[num] = i;
        }
        
        int minLength = Integer.MAX_VALUE;
        for (int num = 0; num < MAX_VALUE; num++) {
            if (frequency[num] == maxDegree) {
                int length = lastOccurrence[num] - firstOccurrence[num] + 1;
                minLength = Math.min(minLength, length);
            }
        }
        
        return minLength;
    }
    
    /**
     * Approach 4: Two-pass solution
     * Time: O(n), Space: O(n)
     * First pass: calculate frequencies and find max degree
     * Second pass: find min subarray length for max degree elements
     */
    public int findShortestSubArray4(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        // First pass: calculate frequencies and max degree
        Map<Integer, Integer> frequency = new HashMap<>();
        int maxDegree = 0;
        
        for (int num : nums) {
            int freq = frequency.getOrDefault(num, 0) + 1;
            frequency.put(num, freq);
            maxDegree = Math.max(maxDegree, freq);
        }
        
        // Collect elements with max degree
        Set<Integer> maxDegreeElements = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() == maxDegree) {
                maxDegreeElements.add(entry.getKey());
            }
        }
        
        // Second pass: find first and last occurrence for max degree elements
        Map<Integer, Integer> firstOccurrence = new HashMap<>();
        Map<Integer, Integer> lastOccurrence = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (maxDegreeElements.contains(num)) {
                if (!firstOccurrence.containsKey(num)) {
                    firstOccurrence.put(num, i);
                }
                lastOccurrence.put(num, i);
            }
        }
        
        // Calculate minimum length
        int minLength = Integer.MAX_VALUE;
        for (int num : maxDegreeElements) {
            int length = lastOccurrence.get(num) - firstOccurrence.get(num) + 1;
            minLength = Math.min(minLength, length);
        }
        
        return minLength;
    }
    
    /**
     * Approach 5: Compact one-pass with array of objects
     * Time: O(n), Space: O(n)
     */
    public int findShortestSubArray5(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        Map<Integer, int[]> map = new HashMap<>(); // [count, first, last]
        int maxDegree = 0;
        int minLength = Integer.MAX_VALUE;
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!map.containsKey(num)) {
                map.put(num, new int[]{0, i, i});
            }
            int[] info = map.get(num);
            info[0]++; // increment count
            info[2] = i; // update last occurrence
            
            if (info[0] > maxDegree) {
                maxDegree = info[0];
                minLength = info[2] - info[1] + 1;
            } else if (info[0] == maxDegree) {
                minLength = Math.min(minLength, info[2] - info[1] + 1);
            }
        }
        
        return minLength;
    }
    
    /**
     * Helper: Visualize the solution
     */
    public void visualizeSolution(int[] nums) {
        System.out.println("\nProblem Visualization:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Length: " + nums.length);
        
        // Calculate degree
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }
        
        int degree = 0;
        List<Integer> degreeElements = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() > degree) {
                degree = entry.getValue();
                degreeElements.clear();
                degreeElements.add(entry.getKey());
            } else if (entry.getValue() == degree) {
                degreeElements.add(entry.getKey());
            }
        }
        
        System.out.println("\nDegree: " + degree);
        System.out.println("Elements with degree " + degree + ": " + degreeElements);
        
        // Find first and last occurrence for each element
        Map<Integer, Integer> first = new HashMap<>();
        Map<Integer, Integer> last = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!first.containsKey(num)) {
                first.put(num, i);
            }
            last.put(num, i);
        }
        
        System.out.println("\nFor each degree element:");
        int minLength = Integer.MAX_VALUE;
        for (int element : degreeElements) {
            int length = last.get(element) - first.get(element) + 1;
            System.out.printf("  Element %d: first at %d, last at %d, subarray length = %d%n",
                element, first.get(element), last.get(element), length);
            minLength = Math.min(minLength, length);
        }
        
        System.out.println("\nMinimum subarray length: " + minLength);
        
        // Show the actual subarray
        for (int element : degreeElements) {
            if (last.get(element) - first.get(element) + 1 == minLength) {
                System.out.print("One shortest subarray: [");
                for (int i = first.get(element); i <= last.get(element); i++) {
                    System.out.print(nums[i]);
                    if (i < last.get(element)) System.out.print(", ");
                }
                System.out.println("]");
                break;
            }
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            // Example 1
            {1, 2, 2, 3, 1},
            // Example 2
            {1, 2, 2, 3, 1, 4, 2},
            // Edge cases
            {1},
            {1, 1},
            {1, 2, 1},
            {1, 2, 2, 1},
            // Multiple elements with same degree
            {1, 2, 3, 4, 5},
            {1, 1, 2, 2, 3, 3},
            // Random cases
            {1, 2, 2, 3, 1, 4, 2, 3, 3},
            {1, 3, 2, 2, 3, 1, 4, 2, 1}
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        int[][] testCases = generateTestCases();
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\nTest Case " + (i + 1) + ":");
            System.out.println("Input: " + Arrays.toString(testCases[i]));
            
            int result1 = findShortestSubArray(testCases[i]);
            int result2 = findShortestSubArray2(testCases[i]);
            int result3 = findShortestSubArray3(testCases[i]);
            int result4 = findShortestSubArray4(testCases[i]);
            int result5 = findShortestSubArray5(testCases[i]);
            
            System.out.println("Result (Approach 1): " + result1);
            
            if (result1 == result2 && result2 == result3 && result3 == result4 && result4 == result5) {
                System.out.println("✓ All approaches agree");
            } else {
                System.out.println("✗ Discrepancy found!");
                System.out.println("  Approach 2: " + result2);
                System.out.println("  Approach 3: " + result3);
                System.out.println("  Approach 4: " + result4);
                System.out.println("  Approach 5: " + result5);
            }
            
            // Visualize for smaller arrays
            if (testCases[i].length <= 20) {
                visualizeSolution(testCases[i]);
            }
        }
    }
    
    /**
     * Helper: Explain the algorithm step by step
     */
    public void explainAlgorithm(int[] nums) {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("======================");
        System.out.println("Input array: " + Arrays.toString(nums));
        
        System.out.println("\nStep 1: Initialize data structures");
        System.out.println("  We'll use three HashMaps:");
        System.out.println("    frequency: to count occurrences of each number");
        System.out.println("    firstOccurrence: to track first index of each number");
        System.out.println("    lastOccurrence: to track last index of each number");
        
        System.out.println("\nStep 2: Process each element in the array");
        Map<Integer, Integer> frequency = new HashMap<>();
        Map<Integer, Integer> firstOccurrence = new HashMap<>();
        Map<Integer, Integer> lastOccurrence = new HashMap<>();
        int maxDegree = 0;
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            System.out.printf("\n  Processing index %d: value = %d%n", i, num);
            
            // Update frequency
            int newFreq = frequency.getOrDefault(num, 0) + 1;
            frequency.put(num, newFreq);
            System.out.printf("    Frequency of %d: %d%n", num, newFreq);
            
            if (newFreq > maxDegree) {
                maxDegree = newFreq;
                System.out.printf("    New max degree: %d%n", maxDegree);
            }
            
            // Update first occurrence
            if (!firstOccurrence.containsKey(num)) {
                firstOccurrence.put(num, i);
                System.out.printf("    First occurrence of %d at index %d%n", num, i);
            }
            
            // Update last occurrence
            lastOccurrence.put(num, i);
            System.out.printf("    Last occurrence of %d at index %d%n", num, i);
        }
        
        System.out.println("\nStep 3: Find maximum degree");
        System.out.println("  Maximum degree: " + maxDegree);
        
        System.out.println("\nStep 4: Find elements with maximum degree");
        List<Integer> maxDegreeElements = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() == maxDegree) {
                maxDegreeElements.add(entry.getKey());
            }
        }
        System.out.println("  Elements with degree " + maxDegree + ": " + maxDegreeElements);
        
        System.out.println("\nStep 5: Calculate subarray lengths for these elements");
        int minLength = Integer.MAX_VALUE;
        for (int num : maxDegreeElements) {
            int first = firstOccurrence.get(num);
            int last = lastOccurrence.get(num);
            int length = last - first + 1;
            System.out.printf("  Element %d: first=%d, last=%d, length=%d%n", 
                num, first, last, length);
            minLength = Math.min(minLength, length);
        }
        
        System.out.println("\nStep 6: Return minimum length");
        System.out.println("  Minimum subarray length: " + minLength);
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=======================");
        
        // Generate large test case
        Random rand = new Random(42);
        int n = 100000;
        int[] largeArray = new int[n];
        for (int i = 0; i < n; i++) {
            largeArray[i] = rand.nextInt(50000); // within constraints
        }
        
        // Test each approach
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Approach 1
        long start = System.currentTimeMillis();
        results[0] = findShortestSubArray(largeArray);
        times[0] = System.currentTimeMillis() - start;
        
        // Approach 2
        start = System.currentTimeMillis();
        results[1] = findShortestSubArray2(largeArray);
        times[1] = System.currentTimeMillis() - start;
        
        // Approach 3 (array-based - should be fastest)
        start = System.currentTimeMillis();
        results[2] = findShortestSubArray3(largeArray);
        times[2] = System.currentTimeMillis() - start;
        
        // Approach 4
        start = System.currentTimeMillis();
        results[3] = findShortestSubArray4(largeArray);
        times[3] = System.currentTimeMillis() - start;
        
        // Approach 5
        start = System.currentTimeMillis();
        results[4] = findShortestSubArray5(largeArray);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("Results for array of size " + n + ":");
        for (int i = 0; i < 5; i++) {
            System.out.printf("Approach %d: %d (Time: %d ms)%n", i + 1, results[i], times[i]);
        }
        
        // Verify all results are the same
        boolean allMatch = true;
        for (int i = 1; i < 5; i++) {
            if (results[i] != results[0]) {
                allMatch = false;
                break;
            }
        }
        System.out.println("\nAll results match: " + (allMatch ? "✓" : "✗"));
        
        System.out.println("\nRecommendation:");
        System.out.println("- For readability: Approach 1 (Three HashMaps)");
        System.out.println("- For performance: Approach 3 (Array-based, given constraints)");
        System.out.println("- For interviews: Approach 1 or Approach 5 (compact one-pass)");
    }
    
    /**
     * Helper: Common mistakes to avoid
     */
    public void showCommonMistakes() {
        System.out.println("\nCommon Mistakes to Avoid:");
        System.out.println("==========================");
        
        System.out.println("\n1. Confusing degree with subarray length:");
        System.out.println("   ❌ Degree is maximum frequency, NOT subarray length");
        System.out.println("   ✅ Subarray length = last occurrence - first occurrence + 1");
        
        System.out.println("\n2. Not handling multiple elements with same degree:");
        System.out.println("   ❌ Only checking first element with max frequency");
        System.out.println("   ✅ Must check ALL elements with max frequency");
        System.out.println("   ✅ Take minimum subarray length among them");
        
        System.out.println("\n3. Using two passes when one pass is enough:");
        System.out.println("   ❌ First pass to find degree, second to find subarray");
        System.out.println("   ✅ Can do in one pass by tracking first/last occurrence");
        
        System.out.println("\n4. Not considering edge cases:");
        System.out.println("   ❌ Empty array (not allowed per constraints but good to check)");
        System.out.println("   ❌ Single element array");
        System.out.println("   ❌ All elements unique");
        
        System.out.println("\n5. Memory inefficiency:");
        System.out.println("   ❌ Storing all subarrays or positions");
        System.out.println("   ✅ Only need frequency, first index, last index");
        
        System.out.println("\nExample of mistake #2:");
        int[] nums = {1, 2, 2, 3, 1};
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Both 1 and 2 have degree 2");
        System.out.println("For element 1: positions [0,4], length = 5");
        System.out.println("For element 2: positions [1,2], length = 2");
        System.out.println("Correct answer: min(5, 2) = 2");
        System.out.println("Mistake: returning 5 because only checked first max element");
    }
    
    /**
     * Helper: Solve similar variations
     */
    public void solveVariations() {
        System.out.println("\nSimilar Problems and Variations:");
        System.out.println("================================");
        
        System.out.println("\n1. Find the degree itself (simpler):");
        System.out.println("   Just need to find maximum frequency");
        int[] nums = {1, 2, 2, 3, 1};
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) freq.put(num, freq.getOrDefault(num, 0) + 1);
        int degree = Collections.max(freq.values());
        System.out.println("   Array: " + Arrays.toString(nums));
        System.out.println("   Degree: " + degree);
        
        System.out.println("\n2. Find all elements with maximum degree:");
        System.out.println("   Return list of elements, not subarray length");
        List<Integer> maxElements = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == degree) {
                maxElements.add(entry.getKey());
            }
        }
        System.out.println("   Elements with degree " + degree + ": " + maxElements);
        
        System.out.println("\n3. Find longest subarray with same degree:");
        System.out.println("   Similar but take maximum length instead of minimum");
        Map<Integer, Integer> first = new HashMap<>();
        Map<Integer, Integer> last = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!first.containsKey(num)) first.put(num, i);
            last.put(num, i);
        }
        int maxLength = 0;
        for (int num : maxElements) {
            maxLength = Math.max(maxLength, last.get(num) - first.get(num) + 1);
        }
        System.out.println("   Longest subarray length: " + maxLength);
        
        System.out.println("\n4. Degree of 2D array (extension):");
        System.out.println("   Find degree of 2D array (max frequency in entire matrix)");
        System.out.println("   Then find smallest submatrix containing that frequency");
        System.out.println("   Much more complex - would need 2D prefix sums or similar");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("697. Degree of an Array");
        System.out.println("========================");
        
        // Run test cases
        solution.runTestCases();
        
        // Detailed explanation
        int[] example = {1, 2, 2, 3, 1, 4, 2};
        System.out.println("\n" + "=".repeat(80));
        System.out.println("DETAILED EXPLANATION FOR EXAMPLE:");
        System.out.println("=".repeat(80));
        solution.explainAlgorithm(example);
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Common mistakes
        System.out.println("\n" + "=".repeat(80));
        solution.showCommonMistakes();
        
        // Variations
        System.out.println("\n" + "=".repeat(80));
        solution.solveVariations();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nKey Takeaways:");
        System.out.println("1. Degree = maximum frequency in the array");
        System.out.println("2. Need to find smallest subarray containing all occurrences");
        System.out.println("   of at least one element with maximum frequency");
        System.out.println("3. Track: frequency, first occurrence, last occurrence");
        System.out.println("4. One-pass O(n) solution is optimal");
        System.out.println("5. For constraints (0-49,999), array-based is most efficient");
        
        System.out.println("\nInterview Tips:");
        System.out.println("1. Start with clarifying questions:");
        System.out.println("   - Can array be empty? (No, per constraints)");
        System.out.println("   - What's the value range? (0-49,999)");
        System.out.println("   - What about negative numbers? (Not allowed)");
        System.out.println("2. Explain your approach before coding");
        System.out.println("3. Consider edge cases:");
        System.out.println("   - Single element array");
        System.out.println("   - All elements unique");
        System.out.println("   - Multiple elements with same degree");
        System.out.println("4. Discuss time/space complexity");
        System.out.println("5. Suggest optimizations (array vs HashMap)");
        
        System.out.println("\nSample Implementation (for interviews):");
        System.out.println("""
class Solution {
    public int findShortestSubArray(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        Map<Integer, Integer> first = new HashMap<>();
        Map<Integer, Integer> last = new HashMap<>();
        int maxDegree = 0;
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            freq.put(num, freq.getOrDefault(num, 0) + 1);
            maxDegree = Math.max(maxDegree, freq.get(num));
            
            if (!first.containsKey(num)) first.put(num, i);
            last.put(num, i);
        }
        
        int minLength = Integer.MAX_VALUE;
        for (int num : freq.keySet()) {
            if (freq.get(num) == maxDegree) {
                minLength = Math.min(minLength, 
                    last.get(num) - first.get(num) + 1);
            }
        }
        
        return minLength;
    }
}
            """);
    }
}
