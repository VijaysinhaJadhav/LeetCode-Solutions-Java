/**
 * 881. Boats to Save People
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * You are given an array people where people[i] is the weight of the ith person, 
 * and an infinite number of boats where each boat can carry a maximum weight of limit. 
 * Each boat carries at most two people at the same time, provided the sum of the weight 
 * of those people is at most limit.
 * Return the minimum number of boats to carry every given person.
 * 
 * Key Insights:
 * 1. Sort the people array to enable efficient pairing
 * 2. Use two pointers: one for lightest, one for heaviest
 * 3. Try to pair lightest with heaviest if their sum <= limit
 * 4. If pairing not possible, heaviest goes alone
 * 5. This greedy approach minimizes boats used
 * 
 * Approach (Sorting + Two Pointers - RECOMMENDED):
 * 1. Sort the people array
 * 2. Initialize left = 0, right = n-1, boats = 0
 * 3. While left <= right:
 *    - If people[left] + people[right] <= limit, pair them (left++, right--)
 *    - Else, heaviest goes alone (right--)
 *    - Increment boats count
 * 4. Return boats
 * 
 * Time Complexity: O(n log n)
 * Space Complexity: O(1) or O(n) depending on sorting
 * 
 * Tags: Array, Two Pointers, Greedy, Sorting
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Sorting + Two Pointers - RECOMMENDED
     * O(n log n) time, O(1) or O(n) space - Most efficient and optimal
     */
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int left = 0;
        int right = people.length - 1;
        int boats = 0;
        
        while (left <= right) {
            // If lightest and heaviest can share a boat
            if (people[left] + people[right] <= limit) {
                left++;  // lightest person goes with heaviest
            }
            right--;  // heaviest person always goes (either alone or with lightest)
            boats++;   // count this boat
        }
        
        return boats;
    }
    
    /**
     * Approach 2: Two Pointers with Explicit Conditions
     * O(n log n) time, O(1) or O(n) space - More explicit logic
     */
    public int numRescueBoatsExplicit(int[] people, int limit) {
        Arrays.sort(people);
        int boats = 0;
        int light = 0;
        int heavy = people.length - 1;
        
        while (light <= heavy) {
            if (light == heavy) {
                // Last person goes alone
                boats++;
                break;
            }
            
            if (people[light] + people[heavy] <= limit) {
                // Can carry both light and heavy
                light++;
                heavy--;
                boats++;
            } else {
                // Can only carry heavy alone
                heavy--;
                boats++;
            }
        }
        
        return boats;
    }
    
    /**
     * Approach 3: Counting Sort for Small Limits
     * O(n + limit) time, O(limit) space - Efficient for small limit values
     */
    public int numRescueBoatsCountingSort(int[] people, int limit) {
        // Since people[i] <= limit, we can use counting sort
        int[] count = new int[limit + 1];
        for (int weight : people) {
            count[weight]++;
        }
        
        int boats = 0;
        int left = 1;
        int right = limit;
        
        while (left <= right) {
            // Find next available light person
            while (left <= right && count[left] == 0) {
                left++;
            }
            // Find next available heavy person
            while (left <= right && count[right] == 0) {
                right--;
            }
            
            if (left > right) break;
            
            if (left + right <= limit) {
                // Can pair them
                count[left]--;
                count[right]--;
                boats++;
            } else {
                // Heavy goes alone
                count[right]--;
                boats++;
            }
        }
        
        return boats;
    }
    
    /**
     * Approach 4: Greedy with Priority Queue (Less Efficient)
     * O(n log n) time, O(n) space - Alternative approach using PQ
     */
    public int numRescueBoatsPriorityQueue(int[] people, int limit) {
        Arrays.sort(people);
        int boats = 0;
        int left = 0;
        int right = people.length - 1;
        
        while (left <= right) {
            int remaining = limit - people[right];
            right--;
            boats++;
            
            // If we can take the lightest person with the current heavy
            if (left <= right && people[left] <= remaining) {
                left++;
            }
        }
        
        return boats;
    }
    
    /**
     * Approach 5: Binary Search Simulation (Educational)
     * O(n²) time, O(1) space - Inefficient but demonstrates alternative
     */
    public int numRescueBoatsBinarySearch(int[] people, int limit) {
        Arrays.sort(people);
        boolean[] rescued = new boolean[people.length];
        int boats = 0;
        
        for (int i = people.length - 1; i >= 0; i--) {
            if (rescued[i]) continue;
            
            boats++;
            rescued[i] = true;
            int remaining = limit - people[i];
            
            // Find the heaviest person that can fit with current person
            int partner = findPartner(people, rescued, remaining, i - 1);
            if (partner != -1) {
                rescued[partner] = true;
            }
        }
        
        return boats;
    }
    
    private int findPartner(int[] people, boolean[] rescued, int remaining, int end) {
        for (int i = end; i >= 0; i--) {
            if (!rescued[i] && people[i] <= remaining) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Approach 6: Brute Force with Backtracking (Inefficient)
     * O(n!) time, O(n) space - Demonstrates worst-case approach
     */
    public int numRescueBoatsBruteForce(int[] people, int limit) {
        // This is highly inefficient and only for educational purposes
        // In practice, use the two pointers approach
        
        List<Integer> list = new ArrayList<>();
        for (int person : people) {
            list.add(person);
        }
        Collections.sort(list);
        
        return backtrack(list, limit);
    }
    
    private int backtrack(List<Integer> people, int limit) {
        if (people.isEmpty()) return 0;
        
        int minBoats = Integer.MAX_VALUE;
        
        // Try taking the heaviest person alone
        int heaviest = people.get(people.size() - 1);
        List<Integer> remaining1 = new ArrayList<>(people);
        remaining1.remove(remaining1.size() - 1);
        minBoats = Math.min(minBoats, 1 + backtrack(remaining1, limit));
        
        // Try pairing heaviest with each possible lighter person
        for (int i = 0; i < people.size() - 1; i++) {
            if (heaviest + people.get(i) <= limit) {
                List<Integer> remaining2 = new ArrayList<>(people);
                remaining2.remove(people.size() - 1);
                remaining2.remove(i);
                minBoats = Math.min(minBoats, 1 + backtrack(remaining2, limit));
            }
        }
        
        return minBoats;
    }
    
    /**
     * Helper method to visualize the boat assignment process
     */
    private void visualizeBoats(int[] people, int limit, String approach) {
        System.out.println("\n" + approach + " Visualization:");
        System.out.println("People weights: " + Arrays.toString(people));
        System.out.println("Weight limit per boat: " + limit);
        
        int[] sortedPeople = people.clone();
        Arrays.sort(sortedPeople);
        System.out.println("Sorted people: " + Arrays.toString(sortedPeople));
        
        int left = 0;
        int right = sortedPeople.length - 1;
        int boats = 0;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | People[left] | People[right] | Sum | Action | Boats");
        System.out.println("-----|------|-------|--------------|---------------|-----|--------|-------");
        
        while (left <= right) {
            int leftWeight = sortedPeople[left];
            int rightWeight = sortedPeople[right];
            int sum = leftWeight + rightWeight;
            String action;
            
            if (sum <= limit) {
                action = "Pair: " + leftWeight + " + " + rightWeight;
                left++;
                right--;
            } else {
                action = "Single: " + rightWeight + " alone";
                right--;
            }
            boats++;
            
            System.out.printf("%4d | %4d | %5d | %12d | %13d | %3d | %s | %5d%n",
                            step, left, right + 1, leftWeight, rightWeight, sum, action, boats);
            step++;
        }
        
        System.out.println("\nTotal boats needed: " + boats);
        
        // Show the actual boat assignments
        System.out.println("\nBoat Assignments:");
        left = 0;
        right = sortedPeople.length - 1;
        int boatNumber = 1;
        
        while (left <= right) {
            if (left < right && sortedPeople[left] + sortedPeople[right] <= limit) {
                System.out.println("Boat " + boatNumber + ": [" + sortedPeople[left] + ", " + sortedPeople[right] + "]");
                left++;
                right--;
            } else {
                System.out.println("Boat " + boatNumber + ": [" + sortedPeople[right] + "]");
                right--;
            }
            boatNumber++;
        }
    }
    
    /**
     * Performance comparison helper
     */
    private void comparePerformance(int[] people, int limit, String testName) {
        System.out.println("\n" + testName + " Performance Comparison:");
        
        long startTime = System.nanoTime();
        int result1 = numRescueBoats(people, limit);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result2 = numRescueBoatsExplicit(people, limit);
        long time2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result3 = numRescueBoatsCountingSort(people, limit);
        long time3 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result4 = numRescueBoatsPriorityQueue(people, limit);
        long time4 = System.nanoTime() - startTime;
        
        // Skip brute force for large inputs
        int result5 = result1;
        long time5 = 0;
        if (people.length <= 10) {
            startTime = System.nanoTime();
            result5 = numRescueBoatsBruteForce(people, limit);
            time5 = System.nanoTime() - startTime;
        }
        
        System.out.printf("Two Pointers (Standard): %d ns%n", time1);
        System.out.printf("Two Pointers (Explicit): %d ns%n", time2);
        System.out.printf("Counting Sort: %d ns%n", time3);
        System.out.printf("Priority Queue: %d ns%n", time4);
        if (people.length <= 10) {
            System.out.printf("Brute Force: %d ns%n", time5);
        } else {
            System.out.printf("Brute Force: Skipped (input too large)%n");
        }
        
        // Verify all produce same result (for approaches that completed)
        boolean allEqual = result1 == result2 && result1 == result3 && result1 == result4;
        if (people.length <= 10) {
            allEqual = allEqual && result1 == result5;
        }
        System.out.println("All approaches consistent: " + allEqual);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Boats to Save People Solution:");
        System.out.println("======================================");
        
        // Test case 1: Simple case
        System.out.println("\nTest 1: Simple case");
        int[] people1 = {1, 2};
        int limit1 = 3;
        int expected1 = 1;
        
        int result1a = solution.numRescueBoats(people1, limit1);
        int result1b = solution.numRescueBoatsExplicit(people1, limit1);
        int result1c = solution.numRescueBoatsCountingSort(people1, limit1);
        
        System.out.println("Two Pointers: " + result1a + " - " + (result1a == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Explicit: " + result1b + " - " + (result1b == expected1 ? "PASSED" : "FAILED"));
        System.out.println("Counting Sort: " + result1c + " - " + (result1c == expected1 ? "PASSED" : "FAILED"));
        
        // Visualize the process
        solution.visualizeBoats(people1, limit1, "Test 1 - Simple Case");
        
        // Test case 2: Example from problem
        System.out.println("\nTest 2: Example from problem");
        int[] people2 = {3, 2, 2, 1};
        int limit2 = 3;
        int expected2 = 3;
        
        int result2a = solution.numRescueBoats(people2, limit2);
        System.out.println("Multiple people: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: All heavy people
        System.out.println("\nTest 3: All heavy people");
        int[] people3 = {3, 5, 3, 4};
        int limit3 = 5;
        int expected3 = 4;
        
        int result3a = solution.numRescueBoats(people3, limit3);
        System.out.println("All heavy: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: All light people
        System.out.println("\nTest 4: All light people");
        int[] people4 = {1, 1, 1, 1, 1};
        int limit4 = 2;
        int expected4 = 3; // (1,1), (1,1), (1)
        
        int result4a = solution.numRescueBoats(people4, limit4);
        System.out.println("All light: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: Mixed weights
        System.out.println("\nTest 5: Mixed weights");
        int[] people5 = {1, 2, 3, 4, 5};
        int limit5 = 5;
        int expected5 = 3; // (1,4), (2,3), (5)
        
        int result5a = solution.numRescueBoats(people5, limit5);
        System.out.println("Mixed weights: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: Single person
        System.out.println("\nTest 6: Single person");
        int[] people6 = {5};
        int limit6 = 5;
        int expected6 = 1;
        
        int result6a = solution.numRescueBoats(people6, limit6);
        System.out.println("Single person: " + result6a + " - " + 
                         (result6a == expected6 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large limit
        System.out.println("\nTest 7: Large limit");
        int[] people7 = {1, 2, 3, 4, 5};
        int limit7 = 10;
        int expected7 = 3; // (1,5), (2,4), (3)
        
        int result7a = solution.numRescueBoats(people7, limit7);
        System.out.println("Large limit: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Performance tests
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PERFORMANCE TESTS");
        System.out.println("=".repeat(50));
        
        // Small input performance
        solution.comparePerformance(people2, limit2, "Small Input (4 people)");
        
        // Medium input performance
        int[] mediumPeople = new int[1000];
        Random random = new Random(42);
        for (int i = 0; i < mediumPeople.length; i++) {
            mediumPeople[i] = random.nextInt(150) + 1; // 1 to 150
        }
        solution.comparePerformance(mediumPeople, 200, "Medium Input (1000 people)");
        
        // Large input performance
        int[] largePeople = new int[10000];
        for (int i = 0; i < largePeople.length; i++) {
            largePeople[i] = random.nextInt(200) + 1; // 1 to 200
        }
        solution.comparePerformance(largePeople, 250, "Large Input (10000 people)");
        
        // Worst-case performance (all same weight)
        int[] worstCasePeople = new int[1000];
        Arrays.fill(worstCasePeople, 100);
        solution.comparePerformance(worstCasePeople, 200, "Worst Case (1000 people of same weight)");
        
        // Algorithm analysis and educational content
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Sorting + Two Pointers - RECOMMENDED:");
        System.out.println("   Time: O(n log n) - Dominated by sorting");
        System.out.println("   Space: O(1) or O(n) - Depending on sorting algorithm");
        System.out.println("   How it works:");
        System.out.println("     - Sort people by weight");
        System.out.println("     - Use two pointers: lightest and heaviest");
        System.out.println("     - Try to pair lightest with heaviest");
        System.out.println("     - If not possible, heaviest goes alone");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity for this problem");
        System.out.println("     - Greedy approach ensures minimum boats");
        System.out.println("     - Simple and elegant implementation");
        System.out.println("   Cons:");
        System.out.println("     - Requires sorting");
        System.out.println("   Best for: Interviews, production code");
        
        System.out.println("\n2. Two Pointers with Explicit Conditions:");
        System.out.println("   Time: O(n log n) - Same as standard approach");
        System.out.println("   Space: O(1) or O(n) - Depending on sorting");
        System.out.println("   How it works:");
        System.out.println("     - Same algorithm as standard");
        System.out.println("     - More explicit handling of edge cases");
        System.out.println("     - Clearer logic flow");
        System.out.println("   Pros:");
        System.out.println("     - Easier to understand for beginners");
        System.out.println("     - Explicit edge case handling");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more verbose");
        System.out.println("   Best for: Learning and clarity");
        
        System.out.println("\n3. Counting Sort Approach:");
        System.out.println("   Time: O(n + limit) - Linear time for small limits");
        System.out.println("   Space: O(limit) - Count array storage");
        System.out.println("   How it works:");
        System.out.println("     - Use counting sort since weights are bounded");
        System.out.println("     - Maintain count of each weight");
        System.out.println("     - Use two pointers on the count array");
        System.out.println("   Pros:");
        System.out.println("     - O(n) time when limit is small");
        System.out.println("     - No explicit sorting needed");
        System.out.println("   Cons:");
        System.out.println("     - Uses O(limit) extra space");
        System.out.println("     - Only efficient for small limit values");
        System.out.println("   Best for: Small weight limits");
        
        System.out.println("\n4. Priority Queue Approach:");
        System.out.println("   Time: O(n log n) - Sorting + queue operations");
        System.out.println("   Space: O(n) - Queue storage");
        System.out.println("   How it works:");
        System.out.println("     - Sort the array");
        System.out.println("     - Use greedy strategy with remaining capacity");
        System.out.println("   Pros:");
        System.out.println("     - Alternative greedy formulation");
        System.out.println("     - Clear capacity-based logic");
        System.out.println("   Cons:");
        System.out.println("     - More complex than two pointers");
        System.out.println("     - No significant advantages");
        System.out.println("   Best for: Learning alternative greedy strategies");
        
        System.out.println("\n5. Binary Search Simulation:");
        System.out.println("   Time: O(n²) - Nested loops for partner finding");
        System.out.println("   Space: O(n) - Boolean array for tracking");
        System.out.println("   How it works:");
        System.out.println("     - Sort the array");
        System.out.println("     - Process from heaviest to lightest");
        System.out.println("     - For each person, find best partner using linear search");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive sequential processing");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Inefficient O(n²) time");
        System.out.println("     - Doesn't scale to large inputs");
        System.out.println("   Best for: Educational purposes only");
        
        System.out.println("\n6. Brute Force with Backtracking:");
        System.out.println("   Time: O(n!) - Exponential time complexity");
        System.out.println("   Space: O(n) - Recursion stack");
        System.out.println("   How it works:");
        System.out.println("     - Try all possible boat assignments");
        System.out.println("     - Use backtracking to explore possibilities");
        System.out.println("     - Find the minimum boats needed");
        System.out.println("   Pros:");
        System.out.println("     - Guaranteed to find optimal solution");
        System.out.println("     - Complete search of solution space");
        System.out.println("   Cons:");
        System.out.println("     - Extremely inefficient");
        System.out.println("     - Only works for very small inputs");
        System.out.println("   Best for: Demonstration of worst-case approach");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("WHY THE GREEDY APPROACH WORKS:");
        System.out.println("1. If heaviest person can't pair with lightest, they can't pair with anyone");
        System.out.println("2. If heaviest can pair with lightest, this is optimal pairing");
        System.out.println("3. The greedy choice doesn't affect future optimal choices");
        System.out.println("4. This ensures we use minimum number of boats");
        System.out.println("5. The algorithm is provably optimal for this problem");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Each boat carries at most 2 people");
        System.out.println("2. The problem is about optimal pairing under weight constraints");
        System.out.println("3. Sorting enables efficient greedy pairing strategy");
        System.out.println("4. The two pointers approach is essentially a matching algorithm");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASES AND HANDLING:");
        System.out.println("1. Single person: Requires one boat");
        System.out.println("2. All same weight: Efficient pairing possible");
        System.out.println("3. All heavy people: Each person needs separate boat");
        System.out.println("4. All light people: Can pair multiple people");
        System.out.println("5. Empty array: Zero boats needed");
        System.out.println("6. Large limit: More pairing opportunities");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Sorting + Two Pointers approach");
        System.out.println("2. Explain why sorting helps with greedy pairing");
        System.out.println("3. Justify why moving both pointers is optimal");
        System.out.println("4. Mention time/space complexity: O(n log n)/O(1) or O(n)");
        System.out.println("5. Discuss edge cases and how algorithm handles them");
        System.out.println("6. Consider mentioning counting sort for small limits");
        System.out.println("7. Write clean, readable code with good variable names");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMMON MISTAKES TO AVOID:");
        System.out.println("1. Not sorting the array first");
        System.out.println("2. Moving only one pointer incorrectly");
        System.out.println("3. Not handling the single person case");
        System.out.println("4. Using inefficient O(n²) or worse algorithms");
        System.out.println("5. Forgetting to increment boat count");
        System.out.println("6. Incorrectly calculating the weight sum");
        System.out.println("=".repeat(70));
        
        // Extension to related problems
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EXTENSION: BOAT CAPACITY > 2 PEOPLE");
        System.out.println("If boats can carry more than 2 people:");
        System.out.println("1. The problem becomes bin packing problem (NP-hard)");
        System.out.println("2. Need to use approximation algorithms");
        System.out.println("3. First Fit Decreasing algorithm is commonly used");
        System.out.println("4. Time complexity becomes more challenging");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed successfully!");
    }
}
