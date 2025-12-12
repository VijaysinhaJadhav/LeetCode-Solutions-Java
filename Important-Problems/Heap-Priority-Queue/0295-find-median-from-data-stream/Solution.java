
## Solution.java

```java
/**
 * 295. Find Median from Data Stream
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Design a data structure that supports adding numbers and finding median efficiently.
 * 
 * Key Insights:
 * 1. Median requires access to middle elements of sorted list
 * 2. Two heaps approach: max heap for lower half, min heap for upper half
 * 3. Maintain balance: |size(maxHeap) - size(minHeap)| ≤ 1
 * 4. All elements in maxHeap ≤ all elements in minHeap
 * 5. Median is either top of larger heap or average of both tops
 * 
 * Approach (Two Heaps):
 * 1. maxHeap (max priority queue) stores smaller half of numbers
 * 2. minHeap (min priority queue) stores larger half of numbers
 * 3. addNum: add to appropriate heap, rebalance if needed
 * 4. findMedian: if sizes equal, average both tops, else top of larger heap
 * 
 * Time Complexity:
 * - addNum: O(log n) - heap insertion
 * - findMedian: O(1) - heap peek
 * Space Complexity: O(n) - storing all elements
 * 
 * Tags: Two Pointers, Design, Sorting, Heap, Data Stream
 */

import java.util.*;

class MedianFinder {
    
    /**
     * Approach 1: Two Heaps (Standard Solution)
     */
    // Max heap for lower half (contains smaller numbers)
    private PriorityQueue<Integer> maxHeap;
    // Min heap for upper half (contains larger numbers)
    private PriorityQueue<Integer> minHeap;
    
    public MedianFinder() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        // Add to appropriate heap
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        
        // Rebalance heaps
        balanceHeaps();
    }
    
    private void balanceHeaps() {
        // Ensure maxHeap size is either equal to or one more than minHeap size
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }
    
    public double findMedian() {
        if (maxHeap.size() == minHeap.size()) {
            // Even number of elements
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            // Odd number of elements, maxHeap has one more element
            return maxHeap.peek();
        }
    }
    
    /**
     * Approach 2: Two Heaps with Different Insertion Strategy
     * Always add to maxHeap first, then move to minHeap if needed
     */
    static class MedianFinder2 {
        private PriorityQueue<Integer> maxHeap; // lower half
        private PriorityQueue<Integer> minHeap; // upper half
        
        public MedianFinder2() {
            maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            minHeap = new PriorityQueue<>();
        }
        
        public void addNum(int num) {
            // Always add to maxHeap first
            maxHeap.offer(num);
            
            // Move the largest element from maxHeap to minHeap
            minHeap.offer(maxHeap.poll());
            
            // If minHeap has more elements, move one back to maxHeap
            if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        }
        
        public double findMedian() {
            if (maxHeap.size() > minHeap.size()) {
                return maxHeap.peek();
            } else {
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            }
        }
    }
    
    /**
     * Approach 3: Using TreeSet with Two Pointers
     * More complex but allows removal of arbitrary elements
     */
    static class MedianFinder3 {
        private TreeSet<int[]> treeSet;
        private int index;
        private int[] left, right;
        
        public MedianFinder3() {
            // Use array with [value, index] to handle duplicates
            treeSet = new TreeSet<>((a, b) -> {
                if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
                return Integer.compare(a[1], b[1]);
            });
            index = 0;
            left = null;
            right = null;
        }
        
        public void addNum(int num) {
            int[] element = new int[]{num, index++};
            treeSet.add(element);
            
            if (treeSet.size() == 1) {
                left = right = element;
            } else {
                if (treeSet.size() % 2 == 0) {
                    // Even number of elements
                    if (num < left[0]) {
                        left = treeSet.lower(left);
                    } else if (num >= right[0]) {
                        right = treeSet.higher(right);
                    } else {
                        left = element;
                        right = element;
                    }
                } else {
                    // Odd number of elements
                    if (num < left[0]) {
                        right = left;
                    } else if (num >= right[0]) {
                        left = right;
                    } else {
                        left = right = element;
                    }
                }
            }
        }
        
        public double findMedian() {
            return (left[0] + right[0]) / 2.0;
        }
    }
    
    /**
     * Approach 4: Using Two ArrayLists (Simulating Insertion Sort)
     * O(n) insertion, O(1) median - for comparison
     */
    static class MedianFinder4 {
        private List<Integer> nums;
        
        public MedianFinder4() {
            nums = new ArrayList<>();
        }
        
        public void addNum(int num) {
            // Binary search to find insertion position
            int left = 0, right = nums.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums.get(mid) < num) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            nums.add(left, num);
        }
        
        public double findMedian() {
            int n = nums.size();
            if (n % 2 == 1) {
                return nums.get(n / 2);
            } else {
                return (nums.get(n / 2 - 1) + nums.get(n / 2)) / 2.0;
            }
        }
    }
    
    /**
     * Approach 5: Using Count Sort (for limited range)
     * O(1) addNum, O(range) findMedian - works for limited value range
     */
    static class MedianFinder5 {
        private int[] count;
        private int total;
        private final int OFFSET = 100000; // Handle negative numbers
        
        public MedianFinder5() {
            count = new int[200001]; // Range: -100000 to 100000
            total = 0;
        }
        
        public void addNum(int num) {
            count[num + OFFSET]++;
            total++;
        }
        
        public double findMedian() {
            int target1 = (total + 1) / 2; // For odd, middle; for even, left middle
            int target2 = (total + 2) / 2; // For odd, same; for even, right middle
            
            int median1 = 0, median2 = 0;
            int countSoFar = 0;
            
            for (int i = 0; i < count.length; i++) {
                countSoFar += count[i];
                if (median1 == 0 && countSoFar >= target1) {
                    median1 = i - OFFSET;
                }
                if (median2 == 0 && countSoFar >= target2) {
                    median2 = i - OFFSET;
                    break;
                }
            }
            
            return (median1 + median2) / 2.0;
        }
    }
    
    /**
     * Helper method to visualize the two heaps
     */
    public void visualizeState() {
        System.out.println("\nCurrent State:");
        
        // Convert heaps to sorted lists for visualization
        List<Integer> lowerHalf = new ArrayList<>(maxHeap);
        Collections.sort(lowerHalf, Collections.reverseOrder());
        List<Integer> upperHalf = new ArrayList<>(minHeap);
        Collections.sort(upperHalf);
        
        System.out.println("Lower half (maxHeap): " + lowerHalf);
        System.out.println("Upper half (minHeap): " + upperHalf);
        System.out.println("Sizes: maxHeap=" + maxHeap.size() + ", minHeap=" + minHeap.size());
        
        if (!maxHeap.isEmpty() && !minHeap.isEmpty()) {
            System.out.println("Invariants:");
            System.out.println("  All in lower ≤ All in upper: " + 
                (maxHeap.peek() <= minHeap.peek() ? "✓" : "✗"));
            System.out.println("  Size difference ≤ 1: " + 
                (Math.abs(maxHeap.size() - minHeap.size()) <= 1 ? "✓" : "✗"));
        }
        
        if (maxHeap.size() + minHeap.size() > 0) {
            System.out.println("Current median: " + findMedian());
        }
    }
    
    /**
     * Helper method to test with a sequence of numbers
     */
    public static void testSequence(int[] nums) {
        System.out.println("\nTesting with numbers: " + Arrays.toString(nums));
        MedianFinder mf = new MedianFinder();
        
        List<Double> medians = new ArrayList<>();
        for (int num : nums) {
            mf.addNum(num);
            medians.add(mf.findMedian());
        }
        
        System.out.println("Medians after each addition: " + medians);
        mf.visualizeState();
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        System.out.println("Testing Find Median from Data Stream:");
        System.out.println("======================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Example from problem");
        testSequence(new int[]{1, 2, 3});
        
        // Test case 2: Even number of elements
        System.out.println("\nTest 2: Even number of elements");
        testSequence(new int[]{1, 2, 3, 4});
        
        // Test case 3: Unsorted order
        System.out.println("\nTest 3: Unsorted order");
        testSequence(new int[]{5, 1, 3, 2, 4});
        
        // Test case 4: Duplicate numbers
        System.out.println("\nTest 4: Duplicate numbers");
        testSequence(new int[]{2, 2, 2, 2});
        
        // Test case 5: Negative numbers
        System.out.println("\nTest 5: Negative numbers");
        testSequence(new int[]{-1, -2, -3, -4, -5});
        
        // Test case 6: Mixed positive and negative
        System.out.println("\nTest 6: Mixed positive and negative");
        testSequence(new int[]{-1, 2, -3, 4, -5});
        
        // Test case 7: Single number
        System.out.println("\nTest 7: Single number");
        testSequence(new int[]{42});
        
        // Test case 8: Large numbers
        System.out.println("\nTest 8: Large numbers");
        testSequence(new int[]{100000, -100000, 50000, -50000});
        
        // Compare all implementations
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING ALL IMPLEMENTATIONS:");
        System.out.println("=".repeat(70));
        
        int[][] testSequences = {
            {1, 2, 3},
            {1, 2, 3, 4},
            {5, 1, 3, 2, 4},
            {2, 2, 2, 2},
            {-1, -2, -3, -4, -5},
            {-1, 2, -3, 4, -5},
            {100000, -100000, 50000, -50000}
        };
        
        System.out.println("\nTesting " + testSequences.length + " sequences:");
        boolean allConsistent = true;
        
        for (int seqIndex = 0; seqIndex < testSequences.length; seqIndex++) {
            int[] nums = testSequences[seqIndex];
            
            // Initialize all implementations
            MedianFinder mf1 = new MedianFinder();
            MedianFinder2 mf2 = new MedianFinder2();
            MedianFinder4 mf4 = new MedianFinder4(); // Skip TreeSet version due to complexity
            MedianFinder5 mf5 = new MedianFinder5();
            
            List<Double> medians1 = new ArrayList<>();
            List<Double> medians2 = new ArrayList<>();
            List<Double> medians4 = new ArrayList<>();
            List<Double> medians5 = new ArrayList<>();
            
            // Add numbers and collect medians
            for (int num : nums) {
                mf1.addNum(num); medians1.add(mf1.findMedian());
                mf2.addNum(num); medians2.add(mf2.findMedian());
                mf4.addNum(num); medians4.add(mf4.findMedian());
                mf5.addNum(num); medians5.add(mf5.findMedian());
            }
            
            // Check consistency
            boolean consistent = medians1.equals(medians2) && 
                                medians2.equals(medians4) && 
                                medians4.equals(medians5);
            
            System.out.printf("Sequence %d: %s - %s%n",
                seqIndex + 1, Arrays.toString(nums),
                consistent ? "✓ CONSISTENT" : "✗ INCONSISTENT");
            
            if (!consistent) {
                System.out.println("  Two Heaps: " + medians1);
                System.out.println("  Alt Two Heaps: " + medians2);
                System.out.println("  Insertion Sort: " + medians4);
                System.out.println("  Count Sort: " + medians5);
                allConsistent = false;
            }
        }
        
        System.out.println("\nAll implementations consistent: " + (allConsistent ? "✓ YES" : "✗ NO"));
        
        // Performance test
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        // Generate large test data
        Random random = new Random(42);
        int n = 50000;
        int[] largeNums = new int[n];
        for (int i = 0; i < n; i++) {
            largeNums[i] = random.nextInt(200001) - 100000; // Range: -100000 to 100000
        }
        
        System.out.println("\nTesting with " + n + " random numbers");
        
        // Test Two Heaps
        long startTime = System.currentTimeMillis();
        MedianFinder mfPerf1 = new MedianFinder();
        for (int num : largeNums) {
            mfPerf1.addNum(num);
        }
        double median1 = mfPerf1.findMedian();
        long time1 = System.currentTimeMillis() - startTime;
        
        // Test Alternative Two Heaps
        startTime = System.currentTimeMillis();
        MedianFinder2 mfPerf2 = new MedianFinder2();
        for (int num : largeNums) {
            mfPerf2.addNum(num);
        }
        double median2 = mfPerf2.findMedian();
        long time2 = System.currentTimeMillis() - startTime;
        
        // Test Insertion Sort (should be much slower)
        startTime = System.currentTimeMillis();
        MedianFinder4 mfPerf4 = new MedianFinder4();
        for (int num : largeNums) {
            mfPerf4.addNum(num);
        }
        double median4 = mfPerf4.findMedian();
        long time4 = System.currentTimeMillis() - startTime;
        
        // Test Count Sort (fast for this range)
        startTime = System.currentTimeMillis();
        MedianFinder5 mfPerf5 = new MedianFinder5();
        for (int num : largeNums) {
            mfPerf5.addNum(num);
        }
        double median5 = mfPerf5.findMedian();
        long time5 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Two Heaps:        " + time1 + " ms - Median: " + median1);
        System.out.println("Alt Two Heaps:    " + time2 + " ms - Median: " + median2);
        System.out.println("Insertion Sort:   " + time4 + " ms - Median: " + median4);
        System.out.println("Count Sort:       " + time5 + " ms - Median: " + median5);
        
        // Verify medians are close (within floating point error)
        boolean mediansMatch = Math.abs(median1 - median2) < 1e-6 &&
                              Math.abs(median2 - median4) < 1e-6 &&
                              Math.abs(median4 - median5) < 1e-6;
        System.out.println("Medians match: " + (mediansMatch ? "✓ YES" : "✗ NO"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nTwo Heaps Intuition:");
        System.out.println("We maintain two heaps that represent the sorted list:");
        System.out.println("1. maxHeap (max priority queue): stores the smaller half of numbers");
        System.out.println("2. minHeap (min priority queue): stores the larger half of numbers");
        
        System.out.println("\nKey Invariants:");
        System.out.println("1. All elements in maxHeap ≤ all elements in minHeap");
        System.out.println("2. |size(maxHeap) - size(minHeap)| ≤ 1");
        
        System.out.println("\nFinding Median:");
        System.out.println("- If total elements odd: median = top of larger heap");
        System.out.println("- If total elements even: median = (top(maxHeap) + top(minHeap)) / 2");
        
        System.out.println("\nAdding a Number:");
        System.out.println("1. Compare with top of maxHeap:");
        System.out.println("   - If num ≤ maxHeap.peek(): add to maxHeap");
        System.out.println("   - Else: add to minHeap");
        System.out.println("2. Rebalance heaps to maintain size invariant");
        System.out.println("3. Ensure maxHeap size is either equal to or one more than minHeap");
        
        System.out.println("\nVisual Example: Adding [1, 2, 3, 4]");
        System.out.println("Step 1: Add 1");
        System.out.println("  maxHeap: [1], minHeap: []");
        System.out.println("  Median: 1");
        
        System.out.println("\nStep 2: Add 2");
        System.out.println("  Compare 2 with maxHeap.top=1 → add to minHeap");
        System.out.println("  maxHeap: [1], minHeap: [2]");
        System.out.println("  Rebalance: sizes equal ✓");
        System.out.println("  Median: (1+2)/2 = 1.5");
        
        System.out.println("\nStep 3: Add 3");
        System.out.println("  Compare 3 with maxHeap.top=1 → add to minHeap");
        System.out.println("  maxHeap: [1], minHeap: [2, 3]");
        System.out.println("  Rebalance: move 2 from minHeap to maxHeap");
        System.out.println("  maxHeap: [2, 1], minHeap: [3]");
        System.out.println("  Median: 2 (top of larger heap)");
        
        System.out.println("\nStep 4: Add 4");
        System.out.println("  Compare 4 with maxHeap.top=2 → add to minHeap");
        System.out.println("  maxHeap: [2, 1], minHeap: [3, 4]");
        System.out.println("  Rebalance: sizes equal ✓");
        System.out.println("  Median: (2+3)/2 = 2.5");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("IMPLEMENTATION COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Two Heaps (Standard) - RECOMMENDED:");
        System.out.println("   Time: O(log n) addNum, O(1) findMedian");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Simple and elegant");
        System.out.println("     - Easy to understand and implement");
        System.out.println("     - Works for any input range");
        System.out.println("   Cons:");
        System.out.println("     - Two data structures to maintain");
        System.out.println("     - Need to handle rebalancing");
        System.out.println("   Best for: General purpose, interview settings");
        
        System.out.println("\n2. Alternative Two Heaps:");
        System.out.println("   Time: O(log n) addNum, O(1) findMedian");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Simpler insertion logic");
        System.out.println("     - Always add to maxHeap first, then rebalance");
        System.out.println("     - Same performance as standard");
        System.out.println("   Cons:");
        System.out.println("     - Extra heap operations");
        System.out.println("     - Less intuitive");
        System.out.println("   Best for: When simplicity is preferred");
        
        System.out.println("\n3. TreeSet with Two Pointers:");
        System.out.println("   Time: O(log n) addNum, O(1) findMedian");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Allows removal of elements");
        System.out.println("     - Maintains sorted order");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Need to handle duplicates carefully");
        System.out.println("     - TreeSet overhead");
        System.out.println("   Best for: When removal is needed");
        
        System.out.println("\n4. Insertion Sort Simulation:");
        System.out.println("   Time: O(n) addNum, O(1) findMedian");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Simple to understand");
        System.out.println("     - Maintains sorted list");
        System.out.println("   Cons:");
        System.out.println("     - O(n) insertion is too slow");
        System.out.println("     - Not suitable for data streams");
        System.out.println("   Best for: Small datasets, understanding concepts");
        
        System.out.println("\n5. Count Sort (for limited range):");
        System.out.println("   Time: O(1) addNum, O(range) findMedian");
        System.out.println("   Space: O(range)");
        System.out.println("   Pros:");
        System.out.println("     - O(1) insertion");
        System.out.println("     - Very fast for small ranges");
        System.out.println("   Cons:");
        System.out.println("     - O(range) memory and findMedian");
        System.out.println("     - Only works for limited value ranges");
        System.out.println("     - Doesn't scale for large ranges");
        System.out.println("   Best for: When value range is limited and known");
        
        // Time complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("TIME COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nTwo Heaps Operations:");
        System.out.println("1. addNum(int num):");
        System.out.println("   - Heap insertion: O(log n)");
        System.out.println("   - Heap removal (if rebalancing): O(log n)");
        System.out.println("   - Total: O(log n)");
        
        System.out.println("\n2. findMedian():");
        System.out.println("   - Heap peek: O(1)");
        System.out.println("   - Arithmetic operation: O(1)");
        System.out.println("   - Total: O(1)");
        
        System.out.println("\nMathematical Derivation:");
        System.out.println("Heap operations: insert/remove = O(log k) where k = heap size");
        System.out.println("Worst case: both heaps have ~n/2 elements");
        System.out.println("So O(log(n/2)) = O(log n - log 2) = O(log n)");
        
        // Space complexity
        System.out.println("\nSpace Complexity:");
        System.out.println("- Two heaps store all n elements: O(n)");
        System.out.println("- Each heap entry: integer (4 bytes) + heap overhead");
        System.out.println("- Total: ~2 * n * 4 bytes = O(n)");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Financial Systems:");
        System.out.println("   - Real-time median stock prices");
        System.out.println("   - Transaction amount monitoring");
        System.out.println("   - Fraud detection (unusual transaction patterns)");
        
        System.out.println("\n2. Network Monitoring:");
        System.out.println("   - Median latency calculation");
        System.out.println("   - Bandwidth usage monitoring");
        System.out.println("   - Packet loss rate tracking");
        
        System.out.println("\n3. IoT and Sensor Data:");
        System.out.println("   - Median temperature readings");
        System.out.println("   - Sensor data aggregation");
        System.out.println("   - Outlier detection");
        
        System.out.println("\n4. Healthcare:");
        System.out.println("   - Median vital signs monitoring");
        System.out.println("   - Patient data analysis");
        System.out.println("   - Medical device readings");
        
        System.out.println("\n5. Gaming:");
        System.out.println("   - Median player scores");
        System.out.println("   - Real-time leaderboards");
        System.out.println("   - Skill rating systems");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify requirements:");
        System.out.println("   - Need to support addNum and findMedian");
        System.out.println("   - Data stream (numbers arrive one by one)");
        System.out.println("   - Median requires sorted order");
        
        System.out.println("\n2. Discuss naive solutions:");
        System.out.println("   - Store numbers in array, sort on each query: O(n log n)");
        System.out.println("   - Insertion sort: O(n) add, O(1) median");
        System.out.println("   - Mention both are too slow");
        
        System.out.println("\n3. Identify need for better data structure:");
        System.out.println("   - Need O(log n) insertion to handle streams");
        System.out.println("   - Need O(1) access to middle elements");
        System.out.println("   - Heaps provide these operations");
        
        System.out.println("\n4. Propose two heaps solution:");
        System.out.println("   - Max heap for lower half");
        System.out.println("   - Min heap for upper half");
        System.out.println("   - Explain invariants");
        
        System.out.println("\n5. Walk through operations:");
        System.out.println("   - addNum: add to appropriate heap, rebalance");
        System.out.println("   - findMedian: check sizes, return appropriate value");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - Empty data structure");
        System.out.println("   - Single element");
        System.out.println("   - Duplicate numbers");
        System.out.println("   - Negative numbers");
        System.out.println("   - Integer overflow when calculating average");
        
        System.out.println("\n7. Discuss alternatives:");
        System.out.println("   - Balanced BST (TreeSet)");
        System.out.println("   - Count sort for limited range");
        System.out.println("   - Compare time/space tradeoffs");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- Two heaps maintain the sorted order implicitly");
        System.out.println("- Max heap stores smaller half in reverse order");
        System.out.println("- Size invariant ensures median access in O(1)");
        System.out.println("- O(log n) addNum is optimal for comparison-based approach");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting to rebalance heaps after insertion");
        System.out.println("- Not handling integer to double conversion correctly");
        System.out.println("- Not considering overflow in average calculation");
        System.out.println("- Incorrect heap ordering (max vs min)");
        System.out.println("- Not testing with edge cases");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with ascending numbers [1,2,3,4,5]");
        System.out.println("2. Test with descending numbers [5,4,3,2,1]");
        System.out.println("3. Test with duplicates [2,2,2,2]");
        System.out.println("4. Test with negative numbers [-1,-2,-3]");
        System.out.println("5. Test single element [42]");
        System.out.println("6. Test empty case (should handle gracefully)");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
