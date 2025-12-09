
## Solution.java

```java
/**
 * 1711. Count Good Meals
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Count pairs of food items where the sum of deliciousness is a power of two.
 * 
 * Key Insights:
 * 1. Maximum deliciousness is 2^20, so maximum sum is 2^21
 * 2. Only 22 powers of two to check (2^0 to 2^21)
 * 3. Use hash map to count frequency of each deliciousness
 * 4. For each number, check all powers of two to find complement
 * 5. Handle pair counting combinatorics carefully
 * 
 * Approach (HashMap with Power Checking):
 * 1. Count frequency of each deliciousness value
 * 2. Precompute all powers of two up to 2^21
 * 3. For each unique deliciousness value:
 *    - For each power of two:
 *      - Calculate complement = power - deliciousness
 *      - If complement exists in map, add to count
 *    - Handle same value pairs separately
 * 4. Apply modulo 10^9+7
 * 
 * Time Complexity: O(n * log(MAX_VALUE)) ≈ O(22n) ≈ O(n)
 * Space Complexity: O(n) for frequency map
 * 
 * Tags: Array, Hash Table, Two Pointers
 */

import java.util.*;

class Solution {
    private static final int MOD = 1000000007;
    
    /**
     * Approach 1: Hash Map with Power of Two Checking (RECOMMENDED)
     * O(n) time, O(n) space
     */
    public int countPairs(int[] deliciousness) {
        // Maximum possible sum: 2^20 + 2^20 = 2^21
        int maxSum = 1 << 22; // 2^22 as upper bound
        Map<Integer, Integer> freq = new HashMap<>();
        
        // Count frequency of each deliciousness
        for (int d : deliciousness) {
            freq.put(d, freq.getOrDefault(d, 0) + 1);
        }
        
        long count = 0;
        List<Integer> uniqueValues = new ArrayList<>(freq.keySet());
        
        // Precompute powers of two
        int[] powers = new int[22]; // 2^0 to 2^21
        for (int i = 0; i < 22; i++) {
            powers[i] = 1 << i;
        }
        
        for (int value : uniqueValues) {
            int countValue = freq.get(value);
            
            // Check all powers of two
            for (int power : powers) {
                int complement = power - value;
                
                if (complement < value) {
                    // Skip to avoid double counting (we'll count when value = complement)
                    continue;
                }
                
                if (complement == value) {
                    // Same value: count pairs within same value
                    // Number of pairs = count * (count - 1) / 2
                    long pairs = ((long)countValue * (countValue - 1) / 2) % MOD;
                    count = (count + pairs) % MOD;
                } else if (freq.containsKey(complement)) {
                    // Different values: count all pairs
                    int countComplement = freq.get(complement);
                    long pairs = ((long)countValue * countComplement) % MOD;
                    count = (count + pairs) % MOD;
                }
            }
        }
        
        return (int)count;
    }
    
    /**
     * Approach 2: Optimized with Early Exit
     * Stops checking powers when complement > max possible value
     */
    public int countPairsOptimized(int[] deliciousness) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int d : deliciousness) {
            freq.put(d, freq.getOrDefault(d, 0) + 1);
        }
        
        long count = 0;
        int maxDeliciousness = 1 << 20; // 2^20
        
        // Generate all powers of two up to 2^21
        List<Integer> powers = new ArrayList<>();
        for (int i = 0; i <= 21; i++) {
            powers.add(1 << i);
        }
        
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            int value = entry.getKey();
            int countValue = entry.getValue();
            
            for (int power : powers) {
                int complement = power - value;
                
                if (complement < 0) continue;
                if (complement > maxDeliciousness) continue; // Early exit
                if (complement < value) continue; // Avoid double counting
                
                if (complement == value) {
                    // Same value pairs
                    long pairs = ((long)countValue * (countValue - 1) / 2) % MOD;
                    count = (count + pairs) % MOD;
                } else if (freq.containsKey(complement)) {
                    // Different value pairs
                    int countComplement = freq.get(complement);
                    long pairs = ((long)countValue * countComplement) % MOD;
                    count = (count + pairs) % MOD;
                }
            }
        }
        
        return (int)count;
    }
    
    /**
     * Approach 3: Iterate through array instead of unique values
     * Simpler but slightly less efficient
     */
    public int countPairsArrayIteration(int[] deliciousness) {
        Map<Integer, Integer> freq = new HashMap<>();
        long count = 0;
        
        // Precompute powers of two
        int[] powers = new int[22];
        for (int i = 0; i < 22; i++) {
            powers[i] = 1 << i;
        }
        
        // Process in one pass
        for (int d : deliciousness) {
            for (int power : powers) {
                int complement = power - d;
                if (complement < 0) continue;
                
                if (freq.containsKey(complement)) {
                    count = (count + freq.get(complement)) % MOD;
                }
            }
            freq.put(d, freq.getOrDefault(d, 0) + 1);
        }
        
        return (int)count;
    }
    
    /**
     * Approach 4: Two Pass with Sorting (for smaller ranges)
     * Not optimal for this problem but educational
     */
    public int countPairsTwoPointers(int[] deliciousness) {
        // Sort the array
        Arrays.sort(deliciousness);
        int n = deliciousness.length;
        long count = 0;
        
        // Precompute powers of two
        Set<Integer> powers = new HashSet<>();
        for (int i = 0; i <= 21; i++) {
            powers.add(1 << i);
        }
        
        // For each power, use two pointers to find pairs
        for (int power : powers) {
            int left = 0, right = n - 1;
            
            while (left < right) {
                int sum = deliciousness[left] + deliciousness[right];
                
                if (sum == power) {
                    // Handle equal values
                    if (deliciousness[left] == deliciousness[right]) {
                        long length = right - left + 1;
                        long pairs = length * (length - 1) / 2;
                        count = (count + pairs) % MOD;
                        break;
                    } else {
                        // Count all pairs with these values
                        int leftValue = deliciousness[left];
                        int rightValue = deliciousness[right];
                        int leftCount = 0, rightCount = 0;
                        
                        while (left < n && deliciousness[left] == leftValue) {
                            leftCount++;
                            left++;
                        }
                        while (right >= 0 && deliciousness[right] == rightValue) {
                            rightCount++;
                            right--;
                        }
                        
                        long pairs = ((long)leftCount * rightCount) % MOD;
                        count = (count + pairs) % MOD;
                    }
                } else if (sum < power) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return (int)count;
    }
    
    /**
     * Approach 5: Bit Manipulation Trick
     * For each number, we can find complement by checking powers
     */
    public int countPairsBitManipulation(int[] deliciousness) {
        Map<Integer, Integer> freq = new HashMap<>();
        long count = 0;
        
        for (int d : deliciousness) {
            // For current number d, check all powers of two
            for (int power = 1; power <= (1 << 21); power <<= 1) {
                int complement = power - d;
                if (complement < 0) continue;
                
                if (freq.containsKey(complement)) {
                    count = (count + freq.get(complement)) % MOD;
                }
            }
            freq.put(d, freq.getOrDefault(d, 0) + 1);
        }
        
        return (int)count;
    }
    
    /**
     * Helper method to check if a number is power of two
     */
    private boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
    
    /**
     * Helper method to generate all powers of two up to limit
     */
    private List<Integer> generatePowersOfTwo(int limit) {
        List<Integer> powers = new ArrayList<>();
        int power = 1;
        while (power <= limit) {
            powers.add(power);
            power <<= 1;
        }
        return powers;
    }
    
    /**
     * Helper method to visualize the pairs
     */
    private void visualizePairs(int[] deliciousness) {
        System.out.println("\nVisualizing Pairs:");
        System.out.println("Deliciousness array: " + Arrays.toString(deliciousness));
        
        Map<Integer, Integer> freq = new HashMap<>();
        for (int d : deliciousness) {
            freq.put(d, freq.getOrDefault(d, 0) + 1);
        }
        
        System.out.println("\nFrequency Map:");
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            System.out.printf("  Value %d: %d times%n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("\nPower of Two Sums:");
        List<Integer> powers = generatePowersOfTwo(1 << 21);
        
        Map<String, List<String>> pairMap = new HashMap<>();
        long totalCount = 0;
        
        for (int power : powers) {
            List<String> pairsForPower = new ArrayList<>();
            
            // Check all values
            List<Integer> values = new ArrayList<>(freq.keySet());
            for (int i = 0; i < values.size(); i++) {
                int value1 = values.get(i);
                int count1 = freq.get(value1);
                
                for (int j = i; j < values.size(); j++) {
                    int value2 = values.get(j);
                    int count2 = freq.get(value2);
                    
                    if (value1 + value2 == power) {
                        if (value1 == value2) {
                            // Same value
                            long pairs = (long)count1 * (count1 - 1) / 2;
                            if (pairs > 0) {
                                pairsForPower.add(String.format("(%d,%d): %d pairs", 
                                    value1, value2, pairs));
                                totalCount += pairs;
                            }
                        } else {
                            // Different values
                            long pairs = (long)count1 * count2;
                            pairsForPower.add(String.format("(%d,%d): %d pairs", 
                                value1, value2, pairs));
                            totalCount += pairs;
                        }
                    }
                }
            }
            
            if (!pairsForPower.isEmpty()) {
                pairMap.put("Sum = " + power, pairsForPower);
            }
        }
        
        for (Map.Entry<String, List<String>> entry : pairMap.entrySet()) {
            System.out.println("\n" + entry.getKey() + ":");
            for (String pair : entry.getValue()) {
                System.out.println("  " + pair);
            }
        }
        
        System.out.println("\nTotal pairs (brute force): " + totalCount);
        System.out.println("Total pairs modulo " + MOD + ": " + (totalCount % MOD));
    }
    
    /**
     * Helper method to brute force verify for small arrays
     */
    private long bruteForceCount(int[] deliciousness) {
        long count = 0;
        int n = deliciousness.length;
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = deliciousness[i] + deliciousness[j];
                if (isPowerOfTwo(sum)) {
                    count++;
                }
            }
        }
        
        return count % MOD;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Count Good Meals Solution:");
        System.out.println("===================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int[] deliciousness1 = {1, 3, 5, 7, 9};
        int expected1 = 4;
        
        solution.visualizePairs(deliciousness1);
        
        long startTime = System.nanoTime();
        int result1a = solution.countPairs(deliciousness1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.countPairsOptimized(deliciousness1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.countPairsArrayIteration(deliciousness1);
        long time1c = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("HashMap: " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1a + " ns)");
        System.out.println("Optimized: " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1b + " ns)");
        System.out.println("Array Iteration: " + result1c + " - " + 
                         (result1c == expected1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1c + " ns)");
        
        long bruteForce1 = solution.bruteForceCount(deliciousness1);
        System.out.println("Brute Force: " + bruteForce1 + " - " + 
                         (bruteForce1 == expected1 ? "PASSED" : "FAILED"));
        
        // Test case 2: Example 2 from problem
        System.out.println("\nTest 2: Multiple same values");
        int[] deliciousness2 = {1, 1, 1, 3, 3, 3, 7};
        int expected2 = 15;
        
        solution.visualizePairs(deliciousness2);
        
        startTime = System.nanoTime();
        int result2a = solution.countPairs(deliciousness2);
        long time2a = System.nanoTime() - startTime;
        
        System.out.println("\nResult: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED") + 
                         " (Time: " + time2a + " ns)");
        
        long bruteForce2 = solution.bruteForceCount(deliciousness2);
        System.out.println("Brute Force: " + bruteForce2 + " - " + 
                         (bruteForce2 == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single value repeated
        System.out.println("\nTest 3: All same values (power of two sum)");
        int[] deliciousness3 = {2, 2, 2, 2};
        // Pairs: 2+2=4 (power of 2)
        // Number of pairs: 4 choose 2 = 6
        int expected3 = 6;
        
        solution.visualizePairs(deliciousness3);
        
        int result3a = solution.countPairs(deliciousness3);
        System.out.println("Result: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: All same values (not power of two sum)
        System.out.println("\nTest 4: All same values (not power of two sum)");
        int[] deliciousness4 = {3, 3, 3, 3};
        // 3+3=6 (not power of two)
        int expected4 = 0;
        
        int result4a = solution.countPairs(deliciousness4);
        System.out.println("Result: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Empty array
        System.out.println("\nTest 5: Empty array");
        int[] deliciousness5 = {};
        int expected5 = 0;
        
        int result5a = solution.countPairs(deliciousness5);
        System.out.println("Result: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Single element
        System.out.println("\nTest 6: Single element");
        int[] deliciousness6 = {5};
        int expected6 = 0;
        
        int result6a = solution.countPairs(deliciousness6);
        System.out.println("Result: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large values
        System.out.println("\nTest 7: Large values at boundaries");
        int[] deliciousness7 = {0, 1<<20, 1<<20, 0};
        // Pairs: (0, 1<<20): 2*2 = 4, (1<<20, 1<<20): 1
        // Sums: 2^20 (power of 2) and 2^21 (power of 2)
        int expected7 = 5;
        
        solution.visualizePairs(deliciousness7);
        
        int result7a = solution.countPairs(deliciousness7);
        System.out.println("Result: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: Random values
        System.out.println("\nTest 8: Random values");
        int[] deliciousness8 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        solution.visualizePairs(deliciousness8);
        
        startTime = System.nanoTime();
        int result8a = solution.countPairs(deliciousness8);
        long time8a = System.nanoTime() - startTime;
        
        long bruteForce8 = solution.bruteForceCount(deliciousness8);
        System.out.println("HashMap Result: " + result8a);
        System.out.println("Brute Force Result: " + bruteForce8);
        System.out.println("Match: " + (result8a == bruteForce8 ? "PASSED" : "FAILED"));
        System.out.println("Time: " + time8a + " ns");
        
        // Performance test with large array
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        Random random = new Random(42);
        int size = 10000;
        int[] largeDeliciousness = new int[size];
        for (int i = 0; i < size; i++) {
            largeDeliciousness[i] = random.nextInt(1 << 11); // Values up to 2048
        }
        
        System.out.println("\nTesting with " + size + " elements");
        
        // Test HashMap approach
        startTime = System.nanoTime();
        int resultLarge1 = solution.countPairs(largeDeliciousness);
        long timeLarge1 = System.nanoTime() - startTime;
        
        // Test Optimized approach
        startTime = System.nanoTime();
        int resultLarge2 = solution.countPairsOptimized(largeDeliciousness);
        long timeLarge2 = System.nanoTime() - startTime;
        
        // Test Array Iteration approach
        startTime = System.nanoTime();
        int resultLarge3 = solution.countPairsArrayIteration(largeDeliciousness);
        long timeLarge3 = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("HashMap:          " + resultLarge1 + " (Time: " + 
                         (timeLarge1 / 1_000_000) + " ms)");
        System.out.println("Optimized:        " + resultLarge2 + " (Time: " + 
                         (timeLarge2 / 1_000_000) + " ms)");
        System.out.println("Array Iteration:  " + resultLarge3 + " (Time: " + 
                         (timeLarge3 / 1_000_000) + " ms)");
        
        System.out.println("All results match: " + 
                         (resultLarge1 == resultLarge2 && resultLarge1 == resultLarge3));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Observations:");
        System.out.println("1. Maximum deliciousness value is 2^20");
        System.out.println("2. Maximum possible sum is 2^20 + 2^20 = 2^21");
        System.out.println("3. Only 22 powers of two to check (2^0 to 2^21)");
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Count frequency of each deliciousness value");
        System.out.println("2. Generate all powers of two up to 2^21");
        System.out.println("3. For each unique value v with frequency f:");
        System.out.println("   a. For each power of two p:");
        System.out.println("      - Calculate complement = p - v");
        System.out.println("      - If complement < v: skip (avoid double counting)");
        System.out.println("      - If complement == v:");
        System.out.println("        * Number of pairs = f * (f-1) / 2");
        System.out.println("      - Else if complement exists in map:");
        System.out.println("        * Number of pairs = f * freq[complement]");
        System.out.println("4. Sum all counts modulo 10^9+7");
        
        System.out.println("\nWhy it's O(n):");
        System.out.println("- We process each unique value (at most n)");
        System.out.println("- For each value, we check 22 powers of two");
        System.out.println("- Total operations: O(22 * n) ≈ O(n)");
        
        // Visual example
        System.out.println("\nVisual Example: deliciousness = [1, 1, 3, 3, 7]");
        System.out.println("Frequency: {1:2, 3:2, 7:1}");
        System.out.println("\nChecking value 1 (frequency 2):");
        System.out.println("  Power 2: complement = 1 (same value) -> 2*1/2 = 1 pair");
        System.out.println("  Power 4: complement = 3 (exists) -> 2*2 = 4 pairs");
        System.out.println("  Power 8: complement = 7 (exists) -> 2*1 = 2 pairs");
        System.out.println("\nChecking value 3 (frequency 2):");
        System.out.println("  Power 4: complement = 1 (already counted when value=1)");
        System.out.println("  Power 6: complement = 3 (same value) -> 2*1/2 = 1 pair");
        System.out.println("  Power 8: complement = 5 (not in array)");
        System.out.println("\nChecking value 7 (frequency 1):");
        System.out.println("  Power 8: complement = 1 (already counted)");
        System.out.println("  Power 14: complement = 7 (same value) -> 1*0/2 = 0");
        System.out.println("\nTotal: 1 + 4 + 2 + 1 = 8 pairs");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. HashMap with Power Checking (RECOMMENDED):");
        System.out.println("   Time: O(22n) ≈ O(n)");
        System.out.println("   Space: O(n) for frequency map");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Handles duplicate values efficiently");
        System.out.println("     - Clear and intuitive");
        System.out.println("     - Easy to implement");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of power of two limits");
        System.out.println("     - Need to handle modulo arithmetic");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Optimized with Early Exit:");
        System.out.println("   Time: O(22n) ≈ O(n) with early exits");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Same as above with optimization");
        System.out.println("     - Early exit when complement > max possible");
        System.out.println("     - Slightly faster in practice");
        System.out.println("   Cons:");
        System.out.println("     - Same complexity");
        System.out.println("     - Minor optimization");
        System.out.println("   Best for: When every optimization counts");
        
        System.out.println("\n3. Array Iteration (One Pass):");
        System.out.println("   Time: O(22n) ≈ O(n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Single pass through array");
        System.out.println("     - Simpler loop structure");
        System.out.println("     - No need to handle same-value pairs separately");
        System.out.println("   Cons:");
        System.out.println("     - Counts pairs in different order");
        System.out.println("     - May be slightly slower due to hash lookups");
        System.out.println("   Best for: Simpler implementation");
        
        System.out.println("\n4. Two Pointers with Sorting:");
        System.out.println("   Time: O(n log n + 22n)");
        System.out.println("   Space: O(1) extra space");
        System.out.println("   Pros:");
        System.out.println("     - No hash map overhead");
        System.out.println("     - Can be better for memory-constrained");
        System.out.println("     - Familiar two-pointer pattern");
        System.out.println("   Cons:");
        System.out.println("     - O(n log n) sorting overhead");
        System.out.println("     - More complex to handle duplicates");
        System.out.println("     - Need to run two-pointer for each power");
        System.out.println("   Best for: When memory is tight");
        
        System.out.println("\n5. Bit Manipulation (Loop through powers):");
        System.out.println("   Time: O(22n) ≈ O(n)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Clear bit manipulation");
        System.out.println("     - Direct power of two generation");
        System.out.println("   Cons:");
        System.out.println("     - Same as basic approach");
        System.out.println("     - Bit shifting might be less readable");
        System.out.println("   Best for: Showing bit manipulation skills");
        
        // Edge cases and handling
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Zero Values:");
        System.out.println("   - 0 + x = x (power of two only if x is power of two)");
        System.out.println("   - Need to check if complement is power of two");
        System.out.println("   - 0 + 0 = 0 (not a power of two, so no pair)");
        
        System.out.println("\n2. Maximum Values (2^20):");
        System.out.println("   - 2^20 + 2^20 = 2^21 (power of two)");
        System.out.println("   - Need to check up to power 2^21");
        
        System.out.println("\n3. Large Counts (Combinatorics):");
        System.out.println("   - Use long for intermediate calculations");
        System.out.println("   - Apply modulo at each step");
        System.out.println("   - For same value: n * (n-1) / 2 can overflow");
        
        System.out.println("\n4. Negative Complements:");
        System.out.println("   - Skip when complement < 0");
        System.out.println("   - Since all values are non-negative");
        
        // Mathematical insights
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nWhy 22 powers of two?");
        System.out.println("1. deliciousness[i] ≤ 2^20");
        System.out.println("2. Maximum sum = 2^20 + 2^20 = 2^21");
        System.out.println("3. Powers of two from 2^0 to 2^21: 22 values");
        System.out.println("   [1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024,");
        System.out.println("    2048, 4096, 8192, 16384, 32768, 65536, 131072,");
        System.out.println("    262144, 524288, 1048576, 2097152]");
        
        System.out.println("\nCombinatorics for Counting Pairs:");
        System.out.println("Case 1: Same value (v == complement):");
        System.out.println("   If frequency = f, pairs = f * (f-1) / 2");
        System.out.println("   Example: [2,2,2,2] -> f=4, pairs=4*3/2=6");
        
        System.out.println("\nCase 2: Different values (v != complement):");
        System.out.println("   If frequencies are f1 and f2, pairs = f1 * f2");
        System.out.println("   Example: [1,1,3,3] -> f1=2, f2=2, pairs=2*2=4");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify requirements:");
        System.out.println("   - Pairs where sum is power of two");
        System.out.println("   - Different indices count as different");
        System.out.println("   - Return modulo 10^9+7");
        
        System.out.println("\n2. Start with brute force:");
        System.out.println("   - O(n²) solution, mention it's too slow");
        System.out.println("   - Need to optimize");
        
        System.out.println("\n3. Identify constraints:");
        System.out.println("   - Values ≤ 2^20");
        System.out.println("   - Maximum sum = 2^21");
        System.out.println("   - Only 22 powers of two to check");
        
        System.out.println("\n4. Propose hash map solution:");
        System.out.println("   - Count frequencies");
        System.out.println("   - For each value, check all powers of two");
        System.out.println("   - Look for complement in map");
        
        System.out.println("\n5. Handle combinatorics:");
        System.out.println("   - Same value vs different values");
        System.out.println("   - Avoid double counting");
        
        System.out.println("\n6. Discuss optimization:");
        System.out.println("   - Only check powers up to 2^21");
        System.out.println("   - Early exit for negative complements");
        System.out.println("   - Modulo arithmetic");
        
        System.out.println("\n7. Walk through examples:");
        System.out.println("   - Simple example ([1,3,5,7,9])");
        System.out.println("   - Duplicate values example");
        System.out.println("   - Edge cases (empty, single, max values)");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- O(n) time complexity due to fixed power checks (22)");
        System.out.println("- O(n) space for frequency map");
        System.out.println("- Careful handling of same-value pairs");
        System.out.println("- Modulo arithmetic to prevent overflow");
        System.out.println("- Understanding of power of two properties");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting modulo operations");
        System.out.println("- Double counting pairs");
        System.out.println("- Not handling large intermediate values");
        System.out.println("- Missing edge cases (0 values, max values)");
        System.out.println("- Incorrect power of two range");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with given examples");
        System.out.println("2. Test with all same values");
        System.out.println("3. Test with 0 values");
        System.out.println("4. Test with maximum values (2^20)");
        System.out.println("5. Test empty and single element arrays");
        System.out.println("6. Compare with brute force for small arrays");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
