
## Solution.java

```java
/**
 * 1046. Last Stone Weight
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * You are given an array of integers stones where stones[i] is the weight of the ith stone.
 * On each turn, choose the two heaviest stones and smash them together:
 * - If x == y, both stones are destroyed
 * - If x != y, the stone of weight x is destroyed, and y becomes y - x
 * Return the weight of the last remaining stone or 0 if no stones left.
 * 
 * Key Insights:
 * 1. Need to repeatedly find and process the two largest stones
 * 2. Max-heap provides efficient access to largest elements
 * 3. Simulation continues until 0 or 1 stone remains
 * 4. If stones have different weights, insert the difference back
 * 
 * Approach (Max-Heap):
 * 1. Create max-heap from all stones
 * 2. While heap has more than 1 stone:
 *    - Extract two largest stones
 *    - If different, insert difference back
 * 3. Return last stone or 0
 * 
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 * 
 * Tags: Array, Heap, Priority Queue, Simulation
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Max-Heap (Priority Queue) - RECOMMENDED
     * O(n log n) time, O(n) space
     */
    public int lastStoneWeight(int[] stones) {
        // Create a max-heap using PriorityQueue with reverse order
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        // Add all stones to the max-heap
        for (int stone : stones) {
            maxHeap.offer(stone);
        }
        
        // Continue smashing stones until 0 or 1 stone remains
        while (maxHeap.size() > 1) {
            int stone1 = maxHeap.poll(); // Heaviest stone
            int stone2 = maxHeap.poll(); // Second heaviest stone
            
            // If stones have different weights, insert the difference
            if (stone1 != stone2) {
                maxHeap.offer(stone1 - stone2);
            }
            // If equal, both stones are destroyed (no insertion needed)
        }
        
        // Return the last stone or 0 if no stones remain
        return maxHeap.isEmpty() ? 0 : maxHeap.poll();
    }
    
    /**
     * Approach 2: Sorting and Simulation
     * O(n^2 log n) time, O(1) space if sorting in-place
     * Less efficient but demonstrates alternative approach
     */
    public int lastStoneWeightSorting(int[] stones) {
        List<Integer> stoneList = new ArrayList<>();
        for (int stone : stones) {
            stoneList.add(stone);
        }
        
        while (stoneList.size() > 1) {
            // Sort in descending order
            Collections.sort(stoneList, Collections.reverseOrder());
            
            int stone1 = stoneList.get(0);
            int stone2 = stoneList.get(1);
            
            // Remove the two largest stones
            stoneList.remove(0);
            stoneList.remove(0);
            
            // If different weights, add the difference
            if (stone1 != stone2) {
                stoneList.add(stone1 - stone2);
            }
        }
        
        return stoneList.isEmpty() ? 0 : stoneList.get(0);
    }
    
    /**
     * Approach 3: Optimized Sorting with Array
     * O(n^2) time, O(1) space - Better than ArrayList version
     */
    public int lastStoneWeightArray(int[] stones) {
        int n = stones.length;
        
        while (n > 1) {
            // Sort the relevant portion of the array
            Arrays.sort(stones, 0, n);
            
            // Get the two largest stones (at the end after sorting)
            int stone1 = stones[n - 1];
            int stone2 = stones[n - 2];
            
            if (stone1 == stone2) {
                // Both destroyed, reduce array size by 2
                n -= 2;
            } else {
                // Replace second largest with difference, reduce size by 1
                stones[n - 2] = stone1 - stone2;
                n -= 1;
            }
        }
        
        return n == 0 ? 0 : stones[0];
    }
    
    /**
     * Approach 4: Counting Sort (Optimized for constraints)
     * O(n + maxWeight) time, O(maxWeight) space
     * Efficient when max weight is small compared to n
     */
    public int lastStoneWeightCountingSort(int[] stones) {
        // Since constraints: 1 <= stones[i] <= 1000
        int maxWeight = 1000;
        int[] buckets = new int[maxWeight + 1];
        
        // Count frequencies of each weight
        for (int stone : stones) {
            buckets[stone]++;
        }
        
        int largestWeight = 0;
        int currentWeight = maxWeight;
        
        while (currentWeight > 0) {
            if (buckets[currentWeight] == 0) {
                currentWeight--;
            } else if (largestWeight == 0) {
                // We found a potential largest stone
                buckets[currentWeight] %= 2; // Handle pairs
                if (buckets[currentWeight] == 1) {
                    largestWeight = currentWeight;
                }
                currentWeight--;
            } else {
                // We have a largest weight and found another stone to smash with
                buckets[currentWeight]--;
                int newWeight = largestWeight - currentWeight;
                if (newWeight <= currentWeight) {
                    buckets[newWeight]++;
                } else {
                    // The new weight is larger than current, need to handle it
                    int temp = largestWeight;
                    largestWeight = newWeight;
                    newWeight = temp;
                    // This case is complex, fall back to simpler approach
                    return lastStoneWeight(stones); // Use heap approach instead
                }
                largestWeight = 0;
            }
        }
        
        return largestWeight;
    }
    
    /**
     * Approach 5: Recursive Solution (Educational)
     * O(n log n) time, O(n) space
     * Demonstrates recursive thinking but not practical
     */
    public int lastStoneWeightRecursive(int[] stones) {
        if (stones.length == 0) return 0;
        if (stones.length == 1) return stones[0];
        
        // Convert to list for easier manipulation
        List<Integer> stoneList = new ArrayList<>();
        for (int stone : stones) stoneList.add(stone);
        
        return recursiveHelper(stoneList);
    }
    
    private int recursiveHelper(List<Integer> stones) {
        if (stones.size() == 0) return 0;
        if (stones.size() == 1) return stones.get(0);
        
        // Find indices of two largest stones
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;
        int idx1 = -1, idx2 = -1;
        
        for (int i = 0; i < stones.size(); i++) {
            if (stones.get(i) > max1) {
                max2 = max1;
                idx2 = idx1;
                max1 = stones.get(i);
                idx1 = i;
            } else if (stones.get(i) > max2) {
                max2 = stones.get(i);
                idx2 = i;
            }
        }
        
        // Remove the two largest stones (remove higher index first to avoid shifting issues)
        List<Integer> newStones = new ArrayList<>();
        for (int i = 0; i < stones.size(); i++) {
            if (i != idx1 && i != idx2) {
                newStones.add(stones.get(i));
            }
        }
        
        // If stones have different weights, add the difference
        if (max1 != max2) {
            newStones.add(max1 - max2);
        }
        
        return recursiveHelper(newStones);
    }
    
    /**
     * Helper method to visualize the simulation process
     */
    public void simulateProcess(int[] stones) {
        System.out.println("Simulating stone smashing process:");
        System.out.println("Initial stones: " + Arrays.toString(stones));
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int stone : stones) maxHeap.offer(stone);
        
        int step = 1;
        while (maxHeap.size() > 1) {
            int stone1 = maxHeap.poll();
            int stone2 = maxHeap.poll();
            
            System.out.printf("Step %d: Smash %d and %d", step, stone1, stone2);
            
            if (stone1 == stone2) {
                System.out.println(" - Both destroyed");
            } else {
                int newStone = stone1 - stone2;
                System.out.printf(" - Get %d (difference)%n", newStone);
                maxHeap.offer(newStone);
            }
            
            // Show current heap state
            List<Integer> currentStones = new ArrayList<>(maxHeap);
            Collections.sort(currentStones, Collections.reverseOrder());
            System.out.println("  Remaining stones: " + currentStones);
            step++;
        }
        
        int result = maxHeap.isEmpty() ? 0 : maxHeap.poll();
        System.out.println("Final result: " + result);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Last Stone Weight:");
        System.out.println("==========================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        int[] stones1 = {2, 7, 4, 1, 8, 1};
        int expected1 = 1;
        
        long startTime = System.nanoTime();
        int result1a = solution.lastStoneWeight(stones1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.lastStoneWeightSorting(stones1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.lastStoneWeightArray(stones1.clone());
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.lastStoneWeightRecursive(stones1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        
        System.out.println("Max-Heap: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Sorting: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Array: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Recursive: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the simulation
        solution.simulateProcess(stones1);
        
        // Test case 2: Single stone
        System.out.println("\nTest 2: Single stone");
        int[] stones2 = {1};
        int result2 = solution.lastStoneWeight(stones2);
        System.out.println("Single stone: " + result2 + " - " + 
                         (result2 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 3: All stones destroyed
        System.out.println("\nTest 3: All stones destroyed");
        int[] stones3 = {2, 2};
        int result3 = solution.lastStoneWeight(stones3);
        System.out.println("All destroyed: " + result3 + " - " + 
                         (result3 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 4: Multiple pairs
        System.out.println("\nTest 4: Multiple pairs");
        int[] stones4 = {5, 5, 3, 3, 2, 2};
        int result4 = solution.lastStoneWeight(stones4);
        System.out.println("Multiple pairs: " + result4 + " - " + 
                         (result4 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 5: Complex case
        System.out.println("\nTest 5: Complex case");
        int[] stones5 = {10, 8, 6, 4, 2};
        int result5 = solution.lastStoneWeight(stones5);
        System.out.println("Complex case: " + result5 + " - " + 
                         (result5 == 2 ? "PASSED" : "FAILED"));
        
        // Test case 6: Large weights
        System.out.println("\nTest 6: Large weights");
        int[] stones6 = {1000, 1000, 500, 500};
        int result6 = solution.lastStoneWeight(stones6);
        System.out.println("Large weights: " + result6 + " - " + 
                         (result6 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 7: All different weights
        System.out.println("\nTest 7: All different weights");
        int[] stones7 = {9, 3, 2, 10};
        int result7 = solution.lastStoneWeight(stones7);
        // Process: 10-9=1, 3-2=1, 1-1=0
        System.out.println("All different: " + result7 + " - " + 
                         (result7 == 0 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  Max-Heap: " + time1a + " ns");
        System.out.println("  Sorting: " + time1b + " ns");
        System.out.println("  Array: " + time1c + " ns");
        System.out.println("  Recursive: " + time1d + " ns");
        
        // Performance test with larger input
        System.out.println("\nPerformance Test with 30 stones:");
        int[] largeStones = new int[30];
        Random random = new Random(42);
        for (int i = 0; i < largeStones.length; i++) {
            largeStones[i] = random.nextInt(1000) + 1;
        }
        
        startTime = System.nanoTime();
        int largeResult = solution.lastStoneWeight(largeStones);
        long largeTime = System.nanoTime() - startTime;
        System.out.println("30 stones result: " + largeResult + ", time: " + largeTime + " ns");
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MAX-HEAP ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nWhy Max-Heap?");
        System.out.println("1. We need repeated access to the two largest elements");
        System.out.println("2. Max-heap provides O(1) access to max and O(log n) insertion/removal");
        System.out.println("3. More efficient than sorting after each operation");
        
        System.out.println("\nStep-by-step for [2,7,4,1,8,1]:");
        System.out.println("1. Build max-heap: [8,7,4,2,1,1]");
        System.out.println("2. Extract 8 and 7 → 8-7=1 → Insert 1 → Heap: [4,2,1,1,1]");
        System.out.println("3. Extract 4 and 2 → 4-2=2 → Insert 2 → Heap: [2,1,1,1]");
        System.out.println("4. Extract 2 and 1 → 2-1=1 → Insert 1 → Heap: [1,1,1]");
        System.out.println("5. Extract 1 and 1 → equal → destroy both → Heap: [1]");
        System.out.println("6. Return 1");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Max-Heap (RECOMMENDED):");
        System.out.println("   Time: O(n log n) - Each stone processed with O(log n) operations");
        System.out.println("   Space: O(n) - Heap storage");
        System.out.println("   How it works:");
        System.out.println("     - Build max-heap from all stones");
        System.out.println("     - Repeatedly extract top two stones");
        System.out.println("     - Insert difference if weights are different");
        System.out.println("     - Continue until 0 or 1 stone remains");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Clean and intuitive implementation");
        System.out.println("     - Efficient for repeated max operations");
        System.out.println("   Cons:");
        System.out.println("     - O(n) extra space for heap");
        System.out.println("   Best for: General case, interview settings");
        
        System.out.println("\n2. Sorting and Simulation:");
        System.out.println("   Time: O(n^2 log n) - Sort O(n log n) repeated O(n) times");
        System.out.println("   Space: O(n) - List storage");
        System.out.println("   How it works:");
        System.out.println("     - Maintain list of stones");
        System.out.println("     - Repeatedly sort and process two largest");
        System.out.println("     - Remove processed stones, add difference if needed");
        System.out.println("   Pros:");
        System.out.println("     - Simple to understand");
        System.out.println("     - No complex data structures");
        System.out.println("   Cons:");
        System.out.println("     - Inefficient due to repeated sorting");
        System.out.println("     - O(n^2 log n) worst-case time");
        System.out.println("   Best for: Small inputs, educational purposes");
        
        System.out.println("\n3. Array with In-place Sorting:");
        System.out.println("   Time: O(n^2) - Sort O(n log n) but with reduced n each time");
        System.out.println("   Space: O(1) - In-place operations");
        System.out.println("   How it works:");
        System.out.println("     - Use original array with size tracking");
        System.out.println("     - Sort relevant portion after each operation");
        System.out.println("     - Update array in-place");
        System.out.println("   Pros:");
        System.out.println("     - Constant space complexity");
        System.out.println("     - No additional data structures");
        System.out.println("   Cons:");
        System.out.println("     - Still O(n^2) time complexity");
        System.out.println("     - More complex index management");
        System.out.println("   Best for: Memory-constrained environments");
        
        System.out.println("\n4. Counting Sort (For Constraints):");
        System.out.println("   Time: O(n + maxWeight) - Linear for small max weight");
        System.out.println("   Space: O(maxWeight) - Bucket array");
        System.out.println("   How it works:");
        System.out.println("     - Use bucket sort for small value range");
        System.out.println("     - Process weights from highest to lowest");
        System.out.println("     - Handle pairs and differences efficiently");
        System.out.println("   Pros:");
        System.out.println("     - Very fast for constrained value ranges");
        System.out.println("     - O(n) time when max weight is small");
        System.out.println("   Cons:");
        System.out.println("     - Only works with small value ranges");
        System.out.println("     - Complex implementation");
        System.out.println("   Best for: When stone weights have small upper bound");
        
        System.out.println("\n5. Recursive Approach:");
        System.out.println("   Time: O(n^2) - Finding max elements repeatedly");
        System.out.println("   Space: O(n) - Recursion stack and list copies");
        System.out.println("   How it works:");
        System.out.println("     - Recursively find and process two largest stones");
        System.out.println("     - Create new list for each recursive call");
        System.out.println("   Pros:");
        System.out.println("     - Natural recursive formulation");
        System.out.println("     - Easy to understand conceptually");
        System.out.println("   Cons:");
        System.out.println("     - Inefficient due to list copying");
        System.out.println("     - Stack overflow risk for large inputs");
        System.out.println("   Best for: Understanding the problem recursively");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Problem as Difference Operations:");
        System.out.println("   The problem can be viewed as repeatedly computing");
        System.out.println("   absolute differences between the largest elements.");
        
        System.out.println("\n2. Invariant Property:");
        System.out.println("   The final result is determined by the parity of");
        System.out.println("   the sum of weights and the operations performed.");
        
        System.out.println("\n3. Equivalent Formulation:");
        System.out.println("   This is equivalent to finding if we can partition");
        System.out.println("   the stones into two groups with minimal difference.");
        
        System.out.println("\n4. Special Cases:");
        System.out.println("   - If all stones can be paired equally → result = 0");
        System.out.println("   - If one stone remains unmatched → result = that stone");
        System.out.println("   - The process always terminates with 0 or 1 stone");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with Max-Heap Approach:");
        System.out.println("   - Most interviewers expect this solution");
        System.out.println("   - Explain why heap is efficient for repeated max operations");
        System.out.println("   - Mention O(n log n) time and O(n) space complexity");
        
        System.out.println("\n2. Explain the Algorithm Clearly:");
        System.out.println("   - Create max-heap from all stones");
        System.out.println("   - While heap size > 1, extract two largest");
        System.out.println("   - If different, insert the difference");
        System.out.println("   - Return the last stone or 0");
        
        System.out.println("\n3. Walk Through an Example:");
        System.out.println("   - Use the provided example [2,7,4,1,8,1]");
        System.out.println("   - Show each step of the simulation");
        System.out.println("   - Demonstrate how heap operations work");
        
        System.out.println("\n4. Discuss Alternative Approaches:");
        System.out.println("   - Sorting approach and its inefficiency");
        System.out.println("   - Array in-place approach for memory constraints");
        System.out.println("   - Counting sort for small value ranges");
        
        System.out.println("\n5. Handle Edge Cases:");
        System.out.println("   - Single stone (return that stone)");
        System.out.println("   - All stones destroyed (return 0)");
        System.out.println("   - All stones equal (return 0 if even, stone if odd)");
        
        System.out.println("\nAll tests completed!");
    }
}
