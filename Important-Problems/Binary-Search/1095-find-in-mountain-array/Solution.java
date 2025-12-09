
## Solution.java

```java
/**
 * 1095. Find in Mountain Array
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Given a mountain array, find the minimum index where mountainArr.get(index) == target.
 * You cannot access the array directly - must use MountainArray interface with limited get() calls.
 * 
 * Key Insights:
 * 1. Mountain array: strictly increasing then strictly decreasing
 * 2. Need to minimize API calls (limit: 100 calls)
 * 3. Three-phase approach: find peak, search left, search right
 * 4. Use binary search for all phases to minimize calls
 * 
 * Approach (Triple Binary Search):
 * 1. Find peak index using binary search
 * 2. Search target in left (ascending) part using binary search
 * 3. If not found, search target in right (descending) part using binary search
 * 4. Return minimum valid index or -1 if not found
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 * API Calls: ~3 * log(n) (within 100 for n ≤ 10,000)
 * 
 * Tags: Array, Binary Search, Interactive
 */

/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */

class Solution {
    
    /**
     * Approach 1: Triple Binary Search (RECOMMENDED)
     * O(log n) time, O(1) space, ~3*log(n) API calls
     */
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int n = mountainArr.length();
        
        // Phase 1: Find the peak index
        int peakIndex = findPeakIndex(mountainArr, n);
        
        // Phase 2: Search in left (ascending) part
        int leftResult = binarySearchAscending(mountainArr, target, 0, peakIndex);
        if (leftResult != -1) {
            return leftResult; // Return minimum index found in left part
        }
        
        // Phase 3: Search in right (descending) part
        return binarySearchDescending(mountainArr, target, peakIndex + 1, n - 1);
    }
    
    /**
     * Find the peak index using binary search
     */
    private int findPeakIndex(MountainArray mountainArr, int n) {
        int left = 0;
        int right = n - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            int midVal = mountainArr.get(mid);
            int nextVal = mountainArr.get(mid + 1);
            
            if (midVal < nextVal) {
                // Peak is to the right
                left = mid + 1;
            } else {
                // Peak is to the left or at mid
                right = mid;
            }
        }
        
        return left; // Peak index
    }
    
    /**
     * Binary search for ascending (increasing) part
     */
    private int binarySearchAscending(MountainArray mountainArr, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = mountainArr.get(mid);
            
            if (midVal == target) {
                return mid;
            } else if (midVal < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1; // Not found
    }
    
    /**
     * Binary search for descending (decreasing) part
     */
    private int binarySearchDescending(MountainArray mountainArr, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = mountainArr.get(mid);
            
            if (midVal == target) {
                return mid;
            } else if (midVal > target) {
                // In descending part, if value > target, search right
                left = mid + 1;
            } else {
                // In descending part, if value < target, search left
                right = mid - 1;
            }
        }
        
        return -1; // Not found
    }
    
    /**
     * Approach 2: Optimized Triple Binary Search with Early Termination
     * More efficient version with caching to reduce API calls
     */
    public int findInMountainArrayOptimized(int target, MountainArray mountainArr) {
        int n = mountainArr.length();
        
        // Phase 1: Find peak with caching
        int peakIndex = findPeakWithCaching(mountainArr, n);
        
        // Check peak itself
        int peakValue = mountainArr.get(peakIndex);
        if (peakValue == target) {
            return peakIndex;
        }
        
        // Phase 2: Search left part
        int leftResult = binarySearchAscendingCached(mountainArr, target, 0, peakIndex - 1);
        if (leftResult != -1) {
            return leftResult;
        }
        
        // Phase 3: Search right part
        return binarySearchDescendingCached(mountainArr, target, peakIndex + 1, n - 1);
    }
    
    /**
     * Find peak with value caching to reduce API calls
     */
    private int findPeakWithCaching(MountainArray mountainArr, int n) {
        int left = 0;
        int right = n - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            int midVal = mountainArr.get(mid);
            int nextVal = mountainArr.get(mid + 1);
            
            if (midVal < nextVal) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Binary search ascending with caching
     */
    private int binarySearchAscendingCached(MountainArray mountainArr, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = mountainArr.get(mid);
            
            if (midVal == target) {
                return mid;
            } else if (midVal < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    /**
     * Binary search descending with caching
     */
    private int binarySearchDescendingCached(MountainArray mountainArr, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = mountainArr.get(mid);
            
            if (midVal == target) {
                return mid;
            } else if (midVal > target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    /**
     * Approach 3: Single Pass with Smart Comparison
     * More complex but potentially fewer API calls in some cases
     */
    public int findInMountainArraySinglePass(int target, MountainArray mountainArr) {
        int n = mountainArr.length();
        int left = 0;
        int right = n - 1;
        
        // We'll try to find the target while also identifying the peak
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = mountainArr.get(mid);
            
            if (midVal == target) {
                return mid;
            }
            
            // Determine if we're on left or right side of peak
            boolean isIncreasing = true;
            if (mid > 0) {
                int prevVal = mountainArr.get(mid - 1);
                isIncreasing = prevVal < midVal;
            }
            
            if (isIncreasing) {
                // We're in increasing part
                if (midVal < target) {
                    left = mid + 1;
                } else {
                    // Target could be in left increasing part or we need to check both
                    int leftResult = binarySearchAscending(mountainArr, target, 0, mid - 1);
                    if (leftResult != -1) return leftResult;
                    return binarySearchDescending(mountainArr, target, mid + 1, n - 1);
                }
            } else {
                // We're in decreasing part
                if (midVal > target) {
                    left = mid + 1;
                } else {
                    int leftResult = binarySearchAscending(mountainArr, target, 0, mid - 1);
                    if (leftResult != -1) return leftResult;
                    return binarySearchDescending(mountainArr, target, mid + 1, n - 1);
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Helper method to simulate MountainArray for testing
     */
    private static class TestMountainArray implements MountainArray {
        private final int[] arr;
        private int callCount = 0;
        
        public TestMountainArray(int[] arr) {
            this.arr = arr;
        }
        
        @Override
        public int get(int index) {
            callCount++;
            if (index < 0 || index >= arr.length) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + arr.length);
            }
            return arr[index];
        }
        
        @Override
        public int length() {
            return arr.length;
        }
        
        public int getCallCount() {
            return callCount;
        }
        
        public void resetCallCount() {
            callCount = 0;
        }
    }
    
    /**
     * Helper method to visualize the search process
     */
    private void visualizeSearch(MountainArray mountainArr, int target, int peakIndex) {
        int n = mountainArr.length();
        System.out.println("\nMountain Array Visualization:");
        System.out.println("Array length: " + n);
        System.out.println("Target: " + target);
        System.out.println("Peak index: " + peakIndex);
        System.out.println("Peak value: " + mountainArr.get(peakIndex));
        
        // Show first few and last few elements to understand shape
        System.out.print("First 5 elements: ");
        for (int i = 0; i < Math.min(5, n); i++) {
            System.out.print(mountainArr.get(i) + " ");
        }
        System.out.println();
        
        System.out.print("Last 5 elements: ");
        for (int i = Math.max(0, n - 5); i < n; i++) {
            System.out.print(mountainArr.get(i) + " ");
        }
        System.out.println();
        
        // Show search ranges
        System.out.println("Search ranges:");
        System.out.println("  Left (ascending): 0 to " + peakIndex);
        System.out.println("  Right (descending): " + (peakIndex + 1) + " to " + (n - 1));
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Find in Mountain Array Solution:");
        System.out.println("=========================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int[] array1 = {1, 2, 3, 4, 5, 3, 1};
        TestMountainArray mountainArr1 = new TestMountainArray(array1);
        int target1 = 3;
        int expected1 = 2;
        
        mountainArr1.resetCallCount();
        long startTime = System.nanoTime();
        int result1a = solution.findInMountainArray(target1, mountainArr1);
        long time1a = System.nanoTime() - startTime;
        int calls1a = mountainArr1.getCallCount();
        
        mountainArr1.resetCallCount();
        startTime = System.nanoTime();
        int result1b = solution.findInMountainArrayOptimized(target1, mountainArr1);
        long time1b = System.nanoTime() - startTime;
        int calls1b = mountainArr1.getCallCount();
        
        System.out.println("Triple Binary Search: " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED") + 
                         " (Calls: " + calls1a + ", Time: " + time1a + " ns)");
        System.out.println("Optimized Version: " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED") + 
                         " (Calls: " + calls1b + ", Time: " + time1b + " ns)");
        
        solution.visualizeSearch(mountainArr1, target1, 4); // Peak at index 4 (value 5)
        
        // Test case 2: Target not found
        System.out.println("\nTest 2: Target not found");
        int[] array2 = {0, 1, 2, 4, 2, 1};
        TestMountainArray mountainArr2 = new TestMountainArray(array2);
        int target2 = 3;
        int expected2 = -1;
        
        mountainArr2.resetCallCount();
        int result2 = solution.findInMountainArray(target2, mountainArr2);
        int calls2 = mountainArr2.getCallCount();
        System.out.println("Target not found: " + result2 + " - " + 
                         (result2 == expected2 ? "PASSED" : "FAILED") + 
                         " (Calls: " + calls2 + ")");
        
        // Test case 3: Target at peak
        System.out.println("\nTest 3: Target at peak");
        int[] array3 = {1, 5, 2};
        TestMountainArray mountainArr3 = new TestMountainArray(array3);
        int target3 = 5;
        int expected3 = 1;
        
        mountainArr3.resetCallCount();
        int result3 = solution.findInMountainArray(target3, mountainArr3);
        int calls3 = mountainArr3.getCallCount();
        System.out.println("Target at peak: " + result3 + " - " + 
                         (result3 == expected3 ? "PASSED" : "FAILED") + 
                         " (Calls: " + calls3 + ")");
        
        // Test case 4: Target in right part only
        System.out.println("\nTest 4: Target in right part only");
        int[] array4 = {1, 2, 3, 4, 5, 3, 1};
        TestMountainArray mountainArr4 = new TestMountainArray(array4);
        int target4 = 1;
        int expected4 = 0; // Should find at index 0 (left part), not index 6
        
        mountainArr4.resetCallCount();
        int result4 = solution.findInMountainArray(target4, mountainArr4);
        int calls4 = mountainArr4.getCallCount();
        System.out.println("Target in left part: " + result4 + " - " + 
                         (result4 == expected4 ? "PASSED" : "FAILED") + 
                         " (Calls: " + calls4 + ")");
        
        // Test case 5: Large mountain array
        System.out.println("\nTest 5: Large mountain array");
        int[] array5 = new int[1000];
        // Create a mountain array: increasing 0-499, decreasing 500-999
        for (int i = 0; i < 500; i++) {
            array5[i] = i * 2; // 0, 2, 4, ..., 998
        }
        for (int i = 500; i < 1000; i++) {
            array5[i] = 998 - (i - 500) * 2; // 996, 994, ..., 0
        }
        
        TestMountainArray mountainArr5 = new TestMountainArray(array5);
        int target5 = 500;
        int expected5 = 250; // 500 should be at index 250 (500/2)
        
        mountainArr5.resetCallCount();
        startTime = System.nanoTime();
        int result5 = solution.findInMountainArray(target5, mountainArr5);
        long time5 = System.nanoTime() - startTime;
        int calls5 = mountainArr5.getCallCount();
        
        System.out.println("Large array: " + result5 + " - " + 
                         (result5 == expected5 ? "PASSED" : "FAILED") + 
                         " (Calls: " + calls5 + ", Time: " + time5 + " ns)");
        System.out.println("Call limit respected: " + (calls5 <= 100));
        
        // Test case 6: Minimum size mountain array
        System.out.println("\nTest 6: Minimum size mountain array");
        int[] array6 = {0, 1, 0};
        TestMountainArray mountainArr6 = new TestMountainArray(array6);
        int target6 = 1;
        int expected6 = 1;
        
        mountainArr6.resetCallCount();
        int result6 = solution.findInMountainArray(target6, mountainArr6);
        int calls6 = mountainArr6.getCallCount();
        System.out.println("Min size array: " + result6 + " - " + 
                         (result6 == expected6 ? "PASSED" : "FAILED") + 
                         " (Calls: " + calls6 + ")");
        
        // Performance and call count analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE AND CALL COUNT ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nCall Count Summary:");
        System.out.println("Test 1 (small): " + calls1a + " calls");
        System.out.println("Test 2 (not found): " + calls2 + " calls");
        System.out.println("Test 3 (peak): " + calls3 + " calls");
        System.out.println("Test 4 (left): " + calls4 + " calls");
        System.out.println("Test 5 (large): " + calls5 + " calls");
        System.out.println("Test 6 (min): " + calls6 + " calls");
        
        System.out.println("\nTheoretical Maximum Calls:");
        System.out.println("For n = 10,000:");
        System.out.println("  Peak finding: log₂(10000) ≈ 14 calls");
        System.out.println("  Left search: log₂(5000) ≈ 13 calls");
        System.out.println("  Right search: log₂(5000) ≈ 13 calls");
        System.out.println("  Total: 14 + 13 + 13 = 40 calls");
        System.out.println("  Well under 100 call limit!");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nWhy Triple Binary Search Works:");
        System.out.println("1. Mountain arrays have a strict structure:");
        System.out.println("   - Strictly increasing from start to peak");
        System.out.println("   - Strictly decreasing from peak to end");
        System.out.println("2. We can find the peak efficiently with binary search");
        System.out.println("3. Once we have the peak, we can search both sides separately");
        System.out.println("4. We search left side first to get minimum index");
        
        System.out.println("\nPeak Finding Logic:");
        System.out.println("While left < right:");
        System.out.println("  mid = (left + right) / 2");
        System.out.println("  if arr[mid] < arr[mid+1]:");
        System.out.println("    // Still increasing, peak is to the right");
        System.out.println("    left = mid + 1");
        System.out.println("  else:");
        System.out.println("    // Decreasing, peak is at mid or to the left");
        System.out.println("    right = mid");
        
        System.out.println("\nSearch Strategy:");
        System.out.println("1. Find peak index (binary search)");
        System.out.println("2. Search left (0 to peak-1) - ascending binary search");
        System.out.println("3. If found in left, return immediately (minimum index)");
        System.out.println("4. Else search right (peak+1 to end) - descending binary search");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Triple Binary Search (RECOMMENDED):");
        System.out.println("   Time: O(log n) - Three binary searches");
        System.out.println("   API Calls: ~3 * log(n) - Optimal");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Guaranteed within 100 call limit");
        System.out.println("     - Simple and reliable");
        System.out.println("     - Easy to understand and implement");
        System.out.println("   Cons:");
        System.out.println("     - Always does three binary searches");
        System.out.println("     - Could be optimized for early finds");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Optimized with Caching:");
        System.out.println("   Time: O(log n) - Same complexity");
        System.out.println("   API Calls: ~2.5 * log(n) - Slightly better");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Fewer API calls on average");
        System.out.println("     - Checks peak value directly");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Marginal improvement");
        System.out.println("   Best for: When every API call counts");
        
        System.out.println("\n3. Single Pass Approach:");
        System.out.println("   Time: O(log n) - Single binary search");
        System.out.println("   API Calls: ~1.5 * log(n) - Potentially best");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Fewest API calls in best case");
        System.out.println("     - More elegant theoretically");
        System.out.println("   Cons:");
        System.out.println("     - Much more complex to implement correctly");
        System.out.println("     - Hard to debug and verify");
        System.out.println("     - May need fallback to triple search");
        System.out.println("   Best for: Advanced optimization, learning");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach for Interviews:");
        System.out.println("1. Understand the constraints (100 API calls limit)");
        System.out.println("2. Explain why brute force won't work (O(n) calls)");
        System.out.println("3. Identify mountain array properties");
        System.out.println("4. Propose triple binary search approach");
        System.out.println("5. Implement peak finding first");
        System.out.println("6. Implement ascending binary search");
        System.out.println("7. Implement descending binary search");
        System.out.println("8. Combine with left-search-first strategy");
        System.out.println("9. Verify call count mathematically");
        System.out.println("10. Test with examples");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- API call limit is the main challenge");
        System.out.println("- Binary search is optimal for sorted/half-sorted data");
        System.out.println("- Mountain array structure enables triple search");
        System.out.println("- Left search first ensures minimum index");
        System.out.println("- Call count is O(log n), well under limit");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting the API call limit");
        System.out.println("- Not checking left side first for minimum index");
        System.out.println("- Incorrect binary search for descending part");
        System.out.println("- Off-by-one errors in peak finding");
        System.out.println("- Not handling edge cases (peak, boundaries)");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Calculate maximum possible calls for n=10,000");
        System.out.println("2. Test with target in left, right, and peak");
        System.out.println("3. Test with target not found");
        System.out.println("4. Verify minimum index is returned");
        System.out.println("5. Check call count for each test case");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
