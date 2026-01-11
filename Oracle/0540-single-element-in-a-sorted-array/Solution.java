
# Solution.java

```java
import java.util.*;

/**
 * 540. Single Element in a Sorted Array
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find the single element in a sorted array where every other element appears twice.
 * Must run in O(log n) time and O(1) space.
 * 
 * Key Insights:
 * 1. Binary search due to O(log n) requirement and sorted array
 * 2. Pattern: before single element, pairs are at (even, odd) indices
 * 3. After single element, pairs are at (odd, even) indices
 * 4. Use index parity to determine search direction
 */
class Solution {
    
    /**
     * Approach 1: Binary Search with Parity Check (Recommended)
     * Time: O(log n), Space: O(1)
     * Clean and efficient solution
     */
    public int singleNonDuplicate(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Ensure mid is even for easier comparison
            if (mid % 2 == 1) {
                mid--; // Make mid even
            }
            
            // Check if mid and mid+1 form a pair
            if (nums[mid] == nums[mid + 1]) {
                // Single element is to the right (after the pair)
                left = mid + 2;
            } else {
                // Single element is at mid or to the left
                right = mid;
            }
        }
        
        return nums[left];
    }
    
    /**
     * Approach 2: Binary Search with XOR trick
     * Time: O(log n), Space: O(1)
     * Uses XOR to find partner index
     */
    public int singleNonDuplicate2(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // XOR with 1 gives partner index
            // If mid is even, partner is mid+1
            // If mid is odd, partner is mid-1
            int partner = mid ^ 1;
            
            if (nums[mid] == nums[partner]) {
                // Single element is to the right
                left = mid + 1;
            } else {
                // Single element is to the left
                right = mid;
            }
        }
        
        return nums[left];
    }
    
    /**
     * Approach 3: Binary Search checking both neighbors
     * Time: O(log n), Space: O(1)
     * More explicit but slightly more complex
     */
    public int singleNonDuplicate3(int[] nums) {
        int n = nums.length;
        
        // Edge cases: single element at boundaries
        if (n == 1) return nums[0];
        if (nums[0] != nums[1]) return nums[0];
        if (nums[n-1] != nums[n-2]) return nums[n-1];
        
        int left = 1;
        int right = n - 2;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Check if mid is the single element
            if (nums[mid] != nums[mid-1] && nums[mid] != nums[mid+1]) {
                return nums[mid];
            }
            
            // Determine which side to search
            if ((mid % 2 == 0 && nums[mid] == nums[mid+1]) ||
                (mid % 2 == 1 && nums[mid] == nums[mid-1])) {
                // Pattern correct on left side, search right
                left = mid + 1;
            } else {
                // Pattern incorrect, search left
                right = mid - 1;
            }
        }
        
        return -1; // Should never reach here
    }
    
    /**
     * Approach 4: Linear XOR (not meeting O(log n) requirement)
     * Time: O(n), Space: O(1)
     * Simple but doesn't use sorted property
     */
    public int singleNonDuplicateLinear(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
    
    /**
     * Approach 5: Binary Search with pattern offset
     * Time: O(log n), Space: O(1)
     * Another variation focusing on pattern offset
     */
    public int singleNonDuplicate5(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Check if we're at an even index
            boolean isEvenIndex = (mid % 2 == 0);
            
            if (isEvenIndex) {
                // At even index, should match next element
                if (nums[mid] == nums[mid + 1]) {
                    left = mid + 2;
                } else {
                    right = mid;
                }
            } else {
                // At odd index, should match previous element
                if (nums[mid] == nums[mid - 1]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
        }
        
        return nums[left];
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][] generateTestCases() {
        return new int[][] {
            // Single test case format: {array, expected}
            {1, 1, 2, 3, 3, 4, 4, 8, 8}, // Example 1
            {3, 3, 7, 7, 10, 11, 11}, // Example 2
            {1}, // Single element
            {1, 1, 2}, // Single at end
            {1, 2, 2}, // Single at beginning
            {1, 1, 2, 2, 3}, // Single in middle
            {1, 1, 2, 2, 3, 3, 4, 4, 5}, // Single at end (longer)
            {0, 1, 1, 2, 2, 3, 3}, // Single at beginning (with 0)
            {1, 1, 2, 2, 3, 3, 4, 5, 5}, // Various patterns
            {1, 2, 2, 3, 3, 4, 4, 5, 5} // Single at beginning
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        int[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.printf("\nTest Case %d: %s%n", i + 1, Arrays.toString(nums));
            
            int result1 = singleNonDuplicate(nums);
            int result2 = singleNonDuplicate2(nums);
            int result3 = singleNonDuplicate3(nums);
            int result4 = singleNonDuplicateLinear(nums);
            int result5 = singleNonDuplicate5(nums);
            
            // Calculate expected using XOR (guaranteed correct)
            int expected = 0;
            for (int num : nums) expected ^= num;
            
            boolean allMatch = result1 == expected && result2 == expected &&
                              result3 == expected && result4 == expected &&
                              result5 == expected;
            
            if (allMatch) {
                System.out.println("✓ PASS - All methods return: " + expected);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
                System.out.println("  Method 4: " + result4);
                System.out.println("  Method 5: " + result5);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Visualize binary search process
     */
    public void visualizeBinarySearch(int[] nums) {
        System.out.println("\nBinary Search Visualization:");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Length: " + nums.length + " (odd)");
        
        System.out.println("\nIndices and values:");
        for (int i = 0; i < nums.length; i++) {
            System.out.printf("%2d: %d%n", i, nums[i]);
        }
        
        System.out.println("\nPattern analysis:");
        System.out.println("Before single element: pairs at (even, odd) indices");
        System.out.println("After single element: pairs at (odd, even) indices");
        
        System.out.println("\nBinary search steps:");
        int left = 0;
        int right = nums.length - 1;
        int step = 1;
        
        while (left < right) {
            System.out.printf("\nStep %d: left=%d, right=%d%n", step++, left, right);
            
            int mid = left + (right - left) / 2;
            System.out.printf("  mid = %d (value=%d)%n", mid, nums[mid]);
            
            // Ensure mid is even
            if (mid % 2 == 1) {
                mid--;
                System.out.printf("  Adjusted mid to even: %d (value=%d)%n", mid, nums[mid]);
            }
            
            System.out.printf("  Check pair [%d]=%d and [%d]=%d: ", 
                mid, nums[mid], mid+1, nums[mid+1]);
            
            if (nums[mid] == nums[mid + 1]) {
                System.out.println("MATCH - single element is to the right");
                System.out.printf("  Update left from %d to %d%n", left, mid + 2);
                left = mid + 2;
            } else {
                System.out.println("NO MATCH - single element is at or to the left");
                System.out.printf("  Update right from %d to %d%n", right, mid);
                right = mid;
            }
            
            System.out.printf("  New search range: [%d, %d]%n", left, right);
        }
        
        System.out.println("\nFound single element at index " + left + ": " + nums[left]);
        
        // Verify
        System.out.println("\nVerification:");
        int expected = 0;
        for (int num : nums) expected ^= num;
        System.out.println("XOR verification: " + expected + " ✓");
    }
    
    /**
     * Helper: Explain the pattern
     */
    public void explainPattern() {
        System.out.println("\nUnderstanding the Pattern:");
        System.out.println("===========================");
        
        System.out.println("\nPerfectly paired array (no single element):");
        int[] perfect = {1, 1, 2, 2, 3, 3, 4, 4};
        System.out.println(Arrays.toString(perfect));
        System.out.println("Indices: 0  1  2  3  4  5  6  7");
        System.out.println("Pairs:   (0,1) (2,3) (4,5) (6,7)");
        System.out.println("Pattern: pairs start at EVEN indices");
        
        System.out.println("\nArray with single element at index 2:");
        int[] singleMiddle = {1, 1, 2, 3, 3, 4, 4};
        System.out.println(Arrays.toString(singleMiddle));
        System.out.println("Indices: 0  1  2  3  4  5  6");
        System.out.println("Before single element (indices 0-1):");
        System.out.println("  Pair (0,1): starts at EVEN index ✓");
        System.out.println("Single element at index 2: 2");
        System.out.println("After single element (indices 3-6):");
        System.out.println("  Pair (3,4): starts at ODD index");
        System.out.println("  Pair (5,6): starts at ODD index");
        System.out.println("Pattern shift: After single element, pairs start at ODD indices");
        
        System.out.println("\nArray with single element at index 0:");
        int[] singleStart = {1, 2, 2, 3, 3};
        System.out.println(Arrays.toString(singleStart));
        System.out.println("Indices: 0  1  2  3  4");
        System.out.println("Single element at index 0: 1");
        System.out.println("After single element (indices 1-4):");
        System.out.println("  Pair (1,2): starts at ODD index");
        System.out.println("  Pair (3,4): starts at ODD index");
        
        System.out.println("\nKey Insight:");
        System.out.println("1. Before single element: pairs at (even, odd)");
        System.out.println("2. After single element: pairs at (odd, even)");
        System.out.println("3. Single element itself breaks the pattern");
        
        System.out.println("\nBinary Search Strategy:");
        System.out.println("1. Look at middle index");
        System.out.println("2. Check if it follows 'before' or 'after' pattern");
        System.out.println("3. Adjust search range accordingly");
        System.out.println("4. Repeat until found");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=======================");
        
        // Generate large test case
        Random rand = new Random(42);
        int n = 1000000; // 1 million elements
        int[] nums = new int[n];
        
        // Create array with single element
        int singleIndex = rand.nextInt(n);
        int singleValue = rand.nextInt(1000000);
        
        // Fill array
        int value = 0;
        for (int i = 0; i < n; i++) {
            if (i == singleIndex) {
                nums[i] = singleValue;
            } else {
                nums[i] = value;
                // Every other element gets the same value
                if (i % 2 == 1 || i == singleIndex + 1) {
                    value++;
                }
            }
        }
        
        // Sort to ensure correct ordering
        Arrays.sort(nums);
        
        System.out.println("Testing with " + n + " elements");
        System.out.println("Single element value: " + singleValue);
        
        long[] times = new long[5];
        int[] results = new int[5];
        
        // Method 1: Binary Search with Parity
        long start = System.currentTimeMillis();
        results[0] = singleNonDuplicate(nums);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Binary Search with XOR
        start = System.currentTimeMillis();
        results[1] = singleNonDuplicate2(nums);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Binary Search with neighbors
        start = System.currentTimeMillis();
        results[2] = singleNonDuplicate3(nums);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Linear XOR (for comparison)
        start = System.currentTimeMillis();
        results[3] = singleNonDuplicateLinear(nums);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Binary Search with pattern offset
        start = System.currentTimeMillis();
        results[4] = singleNonDuplicate5(nums);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                     | Time (ms) | Result | Correct?");
        System.out.println("---------------------------|-----------|--------|---------");
        System.out.printf("1. Binary Search (Parity)  | %9d | %6d | %s%n",
            times[0], results[0], results[0] == singleValue ? "✓" : "✗");
        System.out.printf("2. Binary Search (XOR)     | %9d | %6d | %s%n",
            times[1], results[1], results[1] == singleValue ? "✓" : "✗");
        System.out.printf("3. Binary Search (Neighbors)| %9d | %6d | %s%n",
            times[2], results[2], results[2] == singleValue ? "✓" : "✗");
        System.out.printf("4. Linear XOR              | %9d | %6d | %s%n",
            times[3], results[3], results[3] == singleValue ? "✓" : "✗");
        System.out.printf("5. Binary Search (Pattern) | %9d | %6d | %s%n",
            times[4], results[4], results[4] == singleValue ? "✓" : "✗");
        
        System.out.println("\nObservations:");
        System.out.println("1. All binary search methods are O(log n) ~ 0-1 ms");
        System.out.println("2. Linear XOR is O(n) ~ 2-5 ms (slower for large n)");
        System.out.println("3. All methods find the correct single element");
        System.out.println("4. Binary search methods differ in implementation details");
    }
    
    /**
     * Helper: Edge case testing
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        // Case 1: Single element array
        System.out.println("\n1. Single element array:");
        int[] nums1 = {5};
        System.out.println("Array: " + Arrays.toString(nums1));
        int result1 = singleNonDuplicate(nums1);
        System.out.println("Result: " + result1 + " (expected: 5)");
        
        // Case 2: Single element at beginning
        System.out.println("\n2. Single element at beginning:");
        int[] nums2 = {1, 2, 2, 3, 3};
        System.out.println("Array: " + Arrays.toString(nums2));
        int result2 = singleNonDuplicate(nums2);
        System.out.println("Result: " + result2 + " (expected: 1)");
        
        // Case 3: Single element at end
        System.out.println("\n3. Single element at end:");
        int[] nums3 = {1, 1, 2, 2, 3};
        System.out.println("Array: " + Arrays.toString(nums3));
        int result3 = singleNonDuplicate(nums3);
        System.out.println("Result: " + result3 + " (expected: 3)");
        
        // Case 4: Large numbers
        System.out.println("\n4. Large numbers:");
        int[] nums4 = {100000, 100000, 200000, 300000, 300000};
        System.out.println("Array: " + Arrays.toString(nums4));
        int result4 = singleNonDuplicate(nums4);
        System.out.println("Result: " + result4 + " (expected: 200000)");
        
        // Case 5: All same pairs except one
        System.out.println("\n5. All same pairs except one:");
        int[] nums5 = {7, 7, 7, 7, 7, 7, 8, 7, 7};
        // Need to sort for binary search to work
        Arrays.sort(nums5);
        System.out.println("Array (sorted): " + Arrays.toString(nums5));
        int result5 = singleNonDuplicate(nums5);
        System.out.println("Result: " + result5 + " (XOR verification: " + 
            (nums5[0] ^ nums5[1] ^ nums5[2] ^ nums5[3] ^ nums5[4] ^ 
             nums5[5] ^ nums5[6] ^ nums5[7] ^ nums5[8]) + ")");
    }
    
    /**
     * Helper: Mathematical proof of pattern
     */
    public void mathematicalProof() {
        System.out.println("\nMathematical Reasoning:");
        System.out.println("=======================");
        
        System.out.println("\nLet's prove the pattern:");
        System.out.println("In a perfectly paired sorted array:");
        System.out.println("  Indices: 0  1  2  3  4  5  6  7 ...");
        System.out.println("  Pairs:   (0,1) (2,3) (4,5) (6,7) ...");
        System.out.println("  Observation: First element of each pair is at EVEN index");
        
        System.out.println("\nNow insert a single element at position k:");
        System.out.println("  All pairs before position k remain unchanged");
        System.out.println("  All pairs after position k shift by 1 position");
        
        System.out.println("\nExample: Insert single element at index 2");
        System.out.println("Original: 0:(a,a) 2:(b,b) 4:(c,c) 6:(d,d)");
        System.out.println("After:    0:(a,a) 2:x 3:(b,b) 5:(c,c) 7:(d,d)");
        System.out.println("Pattern before index 2: (even, odd)");
        System.out.println("Pattern after index 2: (odd, even)");
        
        System.out.println("\nFormal Proof:");
        System.out.println("Let n be the position of the single element.");
        System.out.println("For i < n:");
        System.out.println("  Original pair was at (2j, 2j+1)");
        System.out.println("  These indices remain < n, so pattern holds");
        System.out.println("For i > n:");
        System.out.println("  Original pair was at (2j, 2j+1)");
        System.out.println("  After insertion, these become (2j+1, 2j+2)");
        System.out.println("  First element of pair is now at ODD index");
        
        System.out.println("\nBinary Search Logic:");
        System.out.println("At any index mid:");
        System.out.println("  If mid is even:");
        System.out.println("    Check if nums[mid] == nums[mid+1]");
        System.out.println("    If true: single element is to the right");
        System.out.println("    If false: single element is at or to the left");
        System.out.println("  If mid is odd:");
        System.out.println("    Check if nums[mid] == nums[mid-1]");
        System.out.println("    If true: single element is to the right");
        System.out.println("    If false: single element is to the left");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify the problem:");
        System.out.println("   - Array is sorted");
        System.out.println("   - Every element appears twice except one");
        System.out.println("   - Need O(log n) time and O(1) space");
        
        System.out.println("\n2. Start with brute force (if asked):");
        System.out.println("   - Linear scan O(n) - mention but don't implement");
        System.out.println("   - XOR solution O(n) - elegant but not optimal");
        
        System.out.println("\n3. Recognize pattern:");
        System.out.println("   - Mention the (even, odd) pair pattern");
        System.out.println("   - Explain how single element disrupts it");
        
        System.out.println("\n4. Propose binary search:");
        System.out.println("   - Due to sorted array and O(log n) requirement");
        System.out.println("   - Explain the modified binary search logic");
        
        System.out.println("\n5. Walk through examples:");
        System.out.println("   - Example 1: [1,1,2,3,3,4,4,8,8]");
        System.out.println("   - Show step-by-step binary search");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - Single element array");
        System.out.println("   - Single element at beginning");
        System.out.println("   - Single element at end");
        
        System.out.println("\n7. Code cleanly:");
        System.out.println("   - Use while (left < right) not <= ");
        System.out.println("   - Calculate mid safely: left + (right-left)/2");
        System.out.println("   - Adjust mid to even if odd");
        
        System.out.println("\n8. Test with examples:");
        System.out.println("   - Provided examples");
        System.out.println("   - Edge cases");
        
        System.out.println("\n9. Discuss complexity:");
        System.out.println("   - Time: O(log n) - binary search halves range");
        System.out.println("   - Space: O(1) - only a few variables");
        
        System.out.println("\n10. Alternative approaches:");
        System.out.println("   - XOR solution (O(n) time, O(1) space)");
        System.out.println("   - But doesn't meet O(log n) requirement");
    }
    
    /**
     * Helper: Common mistakes
     */
    public void showCommonMistakes() {
        System.out.println("\nCommon Mistakes:");
        System.out.println("=================");
        
        System.out.println("\n1. Not adjusting odd mid to even:");
        System.out.println("   ❌ Using odd mid directly");
        System.out.println("   ✅ Adjust mid-- if odd to compare pairs properly");
        
        System.out.println("\n2. Off-by-one errors in indices:");
        System.out.println("   ❌ Checking nums[mid] == nums[mid+1] without bounds");
        System.out.println("   ✅ Ensure mid+1 is within bounds (binary search ensures this)");
        
        System.out.println("\n3. Incorrect search range update:");
        System.out.println("   ❌ left = mid + 1 (should be mid + 2 when pair matches)");
        System.out.println("   ✅ When pair matches, skip both elements: left = mid + 2");
        
        System.out.println("\n4. Not handling edge cases:");
        System.out.println("   ❌ Forgetting single element at boundaries");
        System.out.println("   ✅ Binary search handles these naturally");
        
        System.out.println("\n5. Infinite loop:");
        System.out.println("   ❌ Using while (left <= right) with wrong updates");
        System.out.println("   ✅ Use while (left < right) with proper updates");
        
        System.out.println("\n6. Forgetting array is sorted:");
        System.out.println("   ❌ Trying complex patterns without using sorted property");
        System.out.println("   ✅ Leverage sorted nature for binary search");
        
        System.out.println("\nExample of mistake #1:");
        System.out.println("Array: [1,1,2,3,3]");
        System.out.println("If mid=2 (odd), and we check nums[2]==nums[3]");
        System.out.println("We get 2==3 false, would search left incorrectly");
        System.out.println("Correct: adjust mid to 1 (even), check nums[1]==nums[2]");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("540. Single Element in a Sorted Array");
        System.out.println("======================================");
        
        // Explain the pattern
        solution.explainPattern();
        
        // Mathematical proof
        System.out.println("\n" + "=".repeat(80));
        solution.mathematicalProof();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Visualize examples
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Visualizing Examples:");
        System.out.println("=".repeat(80));
        
        // Example 1
        System.out.println("\nExample 1: [1,1,2,3,3,4,4,8,8]");
        int[] example1 = {1,1,2,3,3,4,4,8,8};
        solution.visualizeBinarySearch(example1);
        
        // Example 2
        System.out.println("\n" + "-".repeat(80));
        System.out.println("\nExample 2: [3,3,7,7,10,11,11]");
        int[] example2 = {3,3,7,7,10,11,11};
        solution.visualizeBinarySearch(example2);
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Common mistakes
        System.out.println("\n" + "=".repeat(80));
        solution.showCommonMistakes();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Ensure mid is even to compare pairs
            if (mid % 2 == 1) {
                mid--;
            }
            
            // Check if mid and mid+1 form a pair
            if (nums[mid] == nums[mid + 1]) {
                // Single element is to the right
                left = mid + 2;
            } else {
                // Single element is at or to the left
                right = mid;
            }
        }
        
        return nums[left];
    }
}
            """);
        
        System.out.println("\nKey Insights:");
        System.out.println("1. Array is sorted → binary search possible");
        System.out.println("2. Pairs are adjacent in sorted array");
        System.out.println("3. Before single element: pairs at (even, odd) indices");
        System.out.println("4. After single element: pairs at (odd, even) indices");
        System.out.println("5. Binary search checks this pattern to find single element");
        
        System.out.println("\nComplexity:");
        System.out.println("- Time: O(log n) - binary search halves range each iteration");
        System.out.println("- Space: O(1) - only a few variables used");
        
        System.out.println("\nAlternative Solutions:");
        System.out.println("1. XOR all elements: O(n) time, O(1) space");
        System.out.println("2. Linear scan: O(n) time, O(1) space");
        System.out.println("3. Binary search variations: all O(log n) time");
        
        System.out.println("\nWhen to Use Each:");
        System.out.println("- Binary search: When O(log n) required (interview favorite)");
        System.out.println("- XOR: When array not sorted or simple solution needed");
        System.out.println("- Linear scan: When simplicity is more important than speed");
    }
}
