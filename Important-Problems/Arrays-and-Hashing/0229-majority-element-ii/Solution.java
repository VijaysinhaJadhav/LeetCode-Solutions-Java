/**
 * 229. Majority Element II
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
 * 
 * Key Insights:
 * 1. At most 2 elements can appear more than n/3 times
 * 2. Boyer-Moore Voting Algorithm can be extended for n/3 case
 * 3. Use two candidates and counters, similar to original algorithm
 * 4. Need verification pass to confirm candidates meet threshold
 * 
 * Approach (Boyer-Moore Extended):
 * 1. First pass: Find two potential candidates using voting algorithm
 * 2. Second pass: Verify candidates actually appear more than n/3 times
 * 3. Return verified candidates
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * 
 * Tags: Array, Hash Table, Sorting, Counting
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Boyer-Moore Voting Algorithm (Extended) - RECOMMENDED
     * O(n) time, O(1) space - Meets follow-up requirement
     */
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // Initialize candidates and counters
        int candidate1 = 0, candidate2 = 0;
        int count1 = 0, count2 = 0;
        
        // First pass: Find potential candidates
        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            } else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } else {
                // Decrement both counters (cancel out three distinct elements)
                count1--;
                count2--;
            }
        }
        
        // Second pass: Verify candidates meet the n/3 threshold
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            }
        }
        
        int n = nums.length;
        if (count1 > n / 3) {
            result.add(candidate1);
        }
        if (count2 > n / 3) {
            result.add(candidate2);
        }
        
        return result;
    }
    
    /**
     * Approach 2: HashMap Counting
     * O(n) time, O(n) space - Simple but uses extra space
     */
    public List<Integer> majorityElementHashMap(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        Map<Integer, Integer> frequency = new HashMap<>();
        int n = nums.length;
        int threshold = n / 3;
        
        // Count frequencies
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }
        
        // Find elements with frequency > n/3
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() > threshold) {
                result.add(entry.getKey());
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Sorting and Counting
     * O(n log n) time, O(1) space if sorting in-place
     */
    public List<Integer> majorityElementSorting(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        Arrays.sort(nums);
        int n = nums.length;
        int threshold = n / 3;
        int count = 1;
        
        for (int i = 1; i <= n; i++) {
            if (i < n && nums[i] == nums[i - 1]) {
                count++;
            } else {
                if (count > threshold) {
                    result.add(nums[i - 1]);
                }
                count = 1;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: Generalized Boyer-Moore for n/k
     * O(n) time, O(k) space - General solution for finding n/k majority elements
     */
    public List<Integer> majorityElementGeneral(int[] nums) {
        return majorityElementNK(nums, 3);
    }
    
    private List<Integer> majorityElementNK(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // At most k-1 elements can appear more than n/k times
        Map<Integer, Integer> candidates = new HashMap<>();
        
        // First pass: Find potential candidates
        for (int num : nums) {
            if (candidates.containsKey(num)) {
                candidates.put(num, candidates.get(num) + 1);
            } else if (candidates.size() < k - 1) {
                candidates.put(num, 1);
            } else {
                // Decrement all counters
                Iterator<Map.Entry<Integer, Integer>> iterator = candidates.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, Integer> entry = iterator.next();
                    if (entry.getValue() == 1) {
                        iterator.remove();
                    } else {
                        entry.setValue(entry.getValue() - 1);
                    }
                }
            }
        }
        
        // Second pass: Verify candidates
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            if (candidates.containsKey(num)) {
                counts.put(num, counts.getOrDefault(num, 0) + 1);
            }
        }
        
        int threshold = nums.length / k;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > threshold) {
                result.add(entry.getKey());
            }
        }
        
        return result;
    }
    
    /**
     * Approach 5: Bit Manipulation (Theoretical)
     * O(32n) time, O(1) space - For integer constraints
     */
    public List<Integer> majorityElementBitManipulation(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        int n = nums.length;
        int threshold = n / 3;
        
        // This approach has limitations with negative numbers and verification
        // Using Boyer-Moore as fallback for robustness
        return majorityElement(nums);
    }
    
    /**
     * Helper method to visualize the Boyer-Moore voting process
     */
    private void visualizeBoyerMoore(int[] nums) {
        System.out.println("\nBoyer-Moore Voting Algorithm Visualization:");
        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("n = " + nums.length + ", threshold = " + nums.length / 3);
        
        int candidate1 = 0, candidate2 = 0;
        int count1 = 0, count2 = 0;
        
        System.out.println("\nFirst Pass - Finding Candidates:");
        System.out.println("Step | Num | Candidate1 | Count1 | Candidate2 | Count2 | Action");
        System.out.println("-----|-----|------------|--------|------------|--------|--------");
        
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            String action = "";
            
            if (num == candidate1) {
                count1++;
                action = "Increment count1";
            } else if (num == candidate2) {
                count2++;
                action = "Increment count2";
            } else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
                action = "Set candidate1";
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
                action = "Set candidate2";
            } else {
                count1--;
                count2--;
                action = "Decrement both (cancel)";
            }
            
            System.out.printf("%4d | %3d | %10d | %6d | %10d | %6d | %s%n",
                            i + 1, num, candidate1, count1, candidate2, count2, action);
        }
        
        System.out.println("\nFinal Candidates: " + candidate1 + ", " + candidate2);
        
        // Verification pass
        System.out.println("\nSecond Pass - Verification:");
        int verifyCount1 = 0, verifyCount2 = 0;
        for (int num : nums) {
            if (num == candidate1) verifyCount1++;
            else if (num == candidate2) verifyCount2++;
        }
        
        System.out.println("Candidate " + candidate1 + " appears " + verifyCount1 + " times");
        System.out.println("Candidate " + candidate2 + " appears " + verifyCount2 + " times");
        System.out.println("Threshold: " + (nums.length / 3));
        
        List<Integer> result = new ArrayList<>();
        if (verifyCount1 > nums.length / 3) result.add(candidate1);
        if (verifyCount2 > nums.length / 3) result.add(candidate2);
        
        System.out.println("Result: " + result);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Majority Element II Solution:");
        System.out.println("======================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        int[] nums1 = {3, 2, 3};
        List<Integer> expected1 = Arrays.asList(3);
        
        long startTime = System.nanoTime();
        List<Integer> result1a = solution.majorityElement(nums1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result1b = solution.majorityElementHashMap(nums1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result1c = solution.majorityElementSorting(nums1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result1d = solution.majorityElementGeneral(nums1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = result1a.equals(expected1);
        boolean test1b = result1b.equals(expected1);
        boolean test1c = result1c.equals(expected1);
        boolean test1d = result1d.equals(expected1);
        
        System.out.println("Boyer-Moore: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Sorting: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("General: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the Boyer-Moore process
        solution.visualizeBoyerMoore(nums1);
        
        // Test case 2: Single element
        System.out.println("\nTest 2: Single element");
        int[] nums2 = {1};
        List<Integer> expected2 = Arrays.asList(1);
        
        List<Integer> result2a = solution.majorityElement(nums2);
        System.out.println("Single element: " + result2a + " - " + 
                         (result2a.equals(expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Two elements
        System.out.println("\nTest 3: Two elements");
        int[] nums3 = {1, 2};
        List<Integer> expected3 = Arrays.asList(1, 2);
        
        List<Integer> result3a = solution.majorityElement(nums3);
        System.out.println("Two elements: " + result3a + " - " + 
                         (result3a.equals(expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: No majority element
        System.out.println("\nTest 4: No majority element");
        int[] nums4 = {1, 2, 3};
        List<Integer> expected4 = Arrays.asList();
        
        List<Integer> result4a = solution.majorityElement(nums4);
        System.out.println("No majority: " + result4a + " - " + 
                         (result4a.equals(expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Two majority elements
        System.out.println("\nTest 5: Two majority elements");
        int[] nums5 = {1, 1, 1, 2, 2, 2, 3};
        List<Integer> result5a = solution.majorityElement(nums5);
        Collections.sort(result5a);
        List<Integer> expected5 = Arrays.asList(1, 2);
        System.out.println("Two majority: " + result5a + " - " + 
                         (result5a.equals(expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: All same elements
        System.out.println("\nTest 6: All same elements");
        int[] nums6 = {1, 1, 1, 1};
        List<Integer> expected6 = Arrays.asList(1);
        
        List<Integer> result6a = solution.majorityElement(nums6);
        System.out.println("All same: " + result6a + " - " + 
                         (result6a.equals(expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Large array with one majority
        System.out.println("\nTest 7: Large array with one majority");
        int[] nums7 = new int[100];
        Arrays.fill(nums7, 5); // All 5s
        for (int i = 0; i < 33; i++) {
            nums7[i] = i; // Add some variety
        }
        
        List<Integer> result7a = solution.majorityElement(nums7);
        System.out.println("Large array: " + result7a + " - " + 
                         (result7a.contains(5) ? "PASSED" : "FAILED"));
        
        // Test case 8: Negative numbers
        System.out.println("\nTest 8: Negative numbers");
        int[] nums8 = {-1, -1, -1, 2, 2};
        List<Integer> expected8 = Arrays.asList(-1);
        
        List<Integer> result8a = solution.majorityElement(nums8);
        System.out.println("Negative numbers: " + result8a + " - " + 
                         (result8a.equals(expected8) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Standard example performance:");
        System.out.println("  Boyer-Moore: " + time1a + " ns");
        System.out.println("  HashMap: " + time1b + " ns");
        System.out.println("  Sorting: " + time1c + " ns");
        System.out.println("  General: " + time1d + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[] largeNums = new int[10000];
        Random random = new Random(42);
        for (int i = 0; i < largeNums.length; i++) {
            largeNums[i] = random.nextInt(10); // Limited range to create frequencies
        }
        // Add majority elements
        for (int i = 0; i < 3500; i++) {
            largeNums[i] = 99; // First majority
        }
        for (int i = 3500; i < 7000; i++) {
            largeNums[i] = 88; // Second majority
        }
        
        startTime = System.nanoTime();
        List<Integer> result10a = solution.majorityElement(largeNums);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result10b = solution.majorityElementHashMap(largeNums);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<Integer> result10c = solution.majorityElementSorting(largeNums);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (10,000 elements):");
        System.out.println("  Boyer-Moore: " + time10a + " ns, Result: " + result10a);
        System.out.println("  HashMap: " + time10b + " ns, Result: " + result10b);
        System.out.println("  Sorting: " + time10c + " ns, Result: " + result10c);
        
        // Verify all approaches produce the same result
        Collections.sort(result10a);
        Collections.sort(result10b);
        Collections.sort(result10c);
        boolean allEqual = result10a.equals(result10b) && result10a.equals(result10c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BOYER-MOORE VOTING ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("At most 2 elements can appear more than n/3 times.");
        System.out.println("The algorithm maintains two candidates and their counts.");
        System.out.println("When we see a new element that doesn't match either candidate:");
        System.out.println("  - We decrement both counts (canceling out three distinct elements)");
        System.out.println("  - If a count reaches 0, we replace that candidate");
        System.out.println("This works because we're effectively grouping elements in sets of 3.");
        
        System.out.println("\nWhy it works:");
        System.out.println("1. If an element appears more than n/3 times, it cannot be fully canceled out");
        System.out.println("2. The algorithm ensures majority elements survive the cancellation process");
        System.out.println("3. We need verification because non-majority elements might be candidates");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Boyer-Moore Voting (RECOMMENDED):");
        System.out.println("   Time: O(n) - Two passes through array");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - First pass: Find two potential candidates using voting");
        System.out.println("     - Second pass: Verify candidates meet n/3 threshold");
        System.out.println("     - Return verified candidates");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(n) time and O(1) space");
        System.out.println("     - Meets follow-up requirement");
        System.out.println("     - Elegant extension of classic algorithm");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of cancellation principle");
        System.out.println("     - Needs verification pass");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. HashMap Counting:");
        System.out.println("   Time: O(n) - Single pass for counting");
        System.out.println("   Space: O(n) - HashMap storage");
        System.out.println("   How it works:");
        System.out.println("     - Count frequency of each element");
        System.out.println("     - Return elements with frequency > n/3");
        System.out.println("   Pros:");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to implement and understand");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(n) extra space");
        System.out.println("     - Doesn't meet O(1) space requirement");
        System.out.println("   Best for: When simplicity is prioritized over space");
        
        System.out.println("\n3. Sorting and Counting:");
        System.out.println("   Time: O(n log n) - Dominated by sorting");
        System.out.println("   Space: O(1) or O(n) - Depending on sort implementation");
        System.out.println("   How it works:");
        System.out.println("     - Sort the array");
        System.out.println("     - Count consecutive elements");
        System.out.println("     - Return elements with count > n/3");
        System.out.println("   Pros:");
        System.out.println("     - Simple implementation");
        System.out.println("     - No extra space if sorting in-place");
        System.out.println("   Cons:");
        System.out.println("     - O(n log n) doesn't meet O(n) requirement");
        System.out.println("     - Not optimal for large inputs");
        System.out.println("   Best for: Small inputs, when simplicity is key");
        
        System.out.println("\n4. Generalized Boyer-Moore (n/k):");
        System.out.println("   Time: O(n) - Two passes with HashMap operations");
        System.out.println("   Space: O(k) - Storage for k-1 candidates");
        System.out.println("   How it works:");
        System.out.println("     - Maintain up to k-1 candidates with counts");
        System.out.println("     - Decrement all counts when new element doesn't match");
        System.out.println("     - Verify candidates meet n/k threshold");
        System.out.println("   Pros:");
        System.out.println("     - General solution for n/k majority");
        System.out.println("     - Demonstrates algorithmic thinking");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Uses O(k) space");
        System.out.println("   Best for: Learning generalized solutions");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Maximum number of n/k majority elements: k-1");
        System.out.println("2. For n/3 case: at most 2 elements");
        System.out.println("3. Proof: If 3 elements each appear > n/3 times,");
        System.out.println("   total appearances > n, which is impossible");
        System.out.println("4. The voting algorithm cancels groups of k distinct elements");
        System.out.println("5. Majority elements survive because they appear in > 1/k of groups");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Boyer-Moore - it's the expected optimal solution");
        System.out.println("2. Explain why at most 2 elements can satisfy the condition");
        System.out.println("3. Describe the cancellation principle clearly");
        System.out.println("4. Mention the need for verification pass");
        System.out.println("5. Discuss alternative approaches and their trade-offs");
        System.out.println("6. Handle edge cases: single element, two elements, no majority");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
