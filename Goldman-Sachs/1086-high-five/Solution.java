
# Solution.java

```java
import java.util.*;

/**
 * 1086. High Five
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given list of [student_id, score], return average of top 5 scores for each student.
 * Average calculated using integer division (floor).
 * 
 * Key Insights:
 * 1. Group scores by student ID
 * 2. Keep only top 5 scores using min-heap
 * 3. Compute average and sort by ID
 */
class Solution {
    
    /**
     * Approach 1: HashMap + Min-Heap (PriorityQueue) - RECOMMENDED
     * Time: O(n log 5) = O(n), Space: O(m * 5) where m = number of students
     * 
     * Steps:
     * 1. Create map: student ID -> min-heap of scores
     * 2. For each score, add to student's heap
     * 3. If heap size > 5, remove smallest
     * 4. After processing, compute average of top 5 scores
     * 5. Sort results by ID
     */
    public int[][] highFive(int[][] items) {
        // Map student ID to min-heap of scores
        Map<Integer, PriorityQueue<Integer>> studentScores = new HashMap<>();
        
        // Process each score
        for (int[] item : items) {
            int id = item[0];
            int score = item[1];
            
            studentScores.putIfAbsent(id, new PriorityQueue<>());
            PriorityQueue<Integer> heap = studentScores.get(id);
            heap.offer(score);
            
            // Keep only top 5 scores
            if (heap.size() > 5) {
                heap.poll(); // Remove smallest
            }
        }
        
        // Build result list
        List<int[]> result = new ArrayList<>();
        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : studentScores.entrySet()) {
            int id = entry.getKey();
            PriorityQueue<Integer> heap = entry.getValue();
            
            // Calculate sum of top 5 scores
            int sum = 0;
            for (int score : heap) {
                sum += score;
            }
            
            // Average = sum / 5 (integer division)
            int average = sum / 5;
            result.add(new int[]{id, average});
        }
        
        // Sort by ID
        result.sort((a, b) -> Integer.compare(a[0], b[0]));
        
        // Convert to 2D array
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Approach 2: HashMap + List (Sort all scores)
     * Time: O(n log n), Space: O(n)
     * 
     * Simpler but less efficient for large datasets
     */
    public int[][] highFiveSortAll(int[][] items) {
        Map<Integer, List<Integer>> studentScores = new HashMap<>();
        
        // Group scores
        for (int[] item : items) {
            int id = item[0];
            int score = item[1];
            studentScores.putIfAbsent(id, new ArrayList<>());
            studentScores.get(id).add(score);
        }
        
        List<int[]> result = new ArrayList<>();
        
        // Process each student
        for (Map.Entry<Integer, List<Integer>> entry : studentScores.entrySet()) {
            int id = entry.getKey();
            List<Integer> scores = entry.getValue();
            
            // Sort in descending order
            scores.sort((a, b) -> Integer.compare(b, a));
            
            // Take top 5
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                sum += scores.get(i);
            }
            
            result.add(new int[]{id, sum / 5});
        }
        
        // Sort by ID
        result.sort((a, b) -> Integer.compare(a[0], b[0]));
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Approach 3: PriorityQueue for each student (More explicit)
     * Time: O(n log 5), Space: O(m * 5)
     * 
     * Using custom class for clarity
     */
    public int[][] highFivePQExplicit(int[][] items) {
        // Map student ID to min-heap
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        
        for (int[] item : items) {
            int id = item[0];
            int score = item[1];
            
            if (!map.containsKey(id)) {
                map.put(id, new PriorityQueue<>());
            }
            
            PriorityQueue<Integer> pq = map.get(id);
            pq.offer(score);
            
            if (pq.size() > 5) {
                pq.poll();
            }
        }
        
        int[][] result = new int[map.size()][2];
        int index = 0;
        
        // Sort IDs
        List<Integer> ids = new ArrayList<>(map.keySet());
        Collections.sort(ids);
        
        for (int id : ids) {
            PriorityQueue<Integer> pq = map.get(id);
            int sum = 0;
            while (!pq.isEmpty()) {
                sum += pq.poll();
            }
            result[index][0] = id;
            result[index][1] = sum / 5;
            index++;
        }
        
        return result;
    }
    
    /**
     * Approach 4: Array-based for fixed ID range
     * Time: O(n + M), Space: O(1000 * 5) = O(1)
     * 
     * Since IDs range from 1 to 1000, we can use array
     */
    public int[][] highFiveArray(int[][] items) {
        // Array of PriorityQueues for IDs 1-1000
        PriorityQueue<Integer>[] studentScores = new PriorityQueue[1001];
        
        // Initialize
        for (int i = 1; i <= 1000; i++) {
            studentScores[i] = new PriorityQueue<>();
        }
        
        // Process scores
        for (int[] item : items) {
            int id = item[0];
            int score = item[1];
            
            studentScores[id].offer(score);
            if (studentScores[id].size() > 5) {
                studentScores[id].poll();
            }
        }
        
        // Build result (only for IDs with scores)
        List<int[]> resultList = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            if (!studentScores[i].isEmpty()) {
                int sum = 0;
                for (int score : studentScores[i]) {
                    sum += score;
                }
                resultList.add(new int[]{i, sum / 5});
            }
        }
        
        return resultList.toArray(new int[resultList.size()][]);
    }
    
    /**
     * Approach 5: Using TreeMap for automatic sorting
     * Time: O(n log 5 + m log m), Space: O(m * 5)
     * 
     * TreeMap maintains sorted order by key
     */
    public int[][] highFiveTreeMap(int[][] items) {
        TreeMap<Integer, PriorityQueue<Integer>> map = new TreeMap<>();
        
        for (int[] item : items) {
            int id = item[0];
            int score = item[1];
            
            map.putIfAbsent(id, new PriorityQueue<>());
            PriorityQueue<Integer> pq = map.get(id);
            pq.offer(score);
            
            if (pq.size() > 5) {
                pq.poll();
            }
        }
        
        int[][] result = new int[map.size()][2];
        int index = 0;
        
        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet()) {
            int id = entry.getKey();
            PriorityQueue<Integer> pq = entry.getValue();
            int sum = 0;
            for (int score : pq) {
                sum += score;
            }
            result[index][0] = id;
            result[index][1] = sum / 5;
            index++;
        }
        
        return result;
    }
    
    /**
     * Helper: Visualize the process
     */
    public void visualizeHighFive(int[][] items) {
        System.out.println("\nHigh Five Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("\nInput items:");
        for (int[] item : items) {
            System.out.printf("  [%d, %d]%n", item[0], item[1]);
        }
        
        System.out.println("\nProcessing with Min-Heap:");
        Map<Integer, PriorityQueue<Integer>> studentScores = new HashMap<>();
        
        for (int[] item : items) {
            int id = item[0];
            int score = item[1];
            
            studentScores.putIfAbsent(id, new PriorityQueue<>());
            PriorityQueue<Integer> heap = studentScores.get(id);
            heap.offer(score);
            
            System.out.printf("\nStudent %d: added score %d → heap: %s", id, score, heap);
            
            if (heap.size() > 5) {
                int removed = heap.poll();
                System.out.printf(" (size > 5, removed %d)", removed);
            }
            System.out.println();
        }
        
        System.out.println("\nResults:");
        int[][] result = highFive(items);
        for (int[] r : result) {
            System.out.printf("  Student %d: average = %d%n", r[0], r[1]);
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[][][] generateTestCases() {
        return new int[][][] {
            // Example 1
            {{{1,91},{1,92},{2,93},{2,97},{1,60},{2,77},{1,65},{1,87},{1,100},{2,100},{2,76}}, 
             {{1,87},{2,88}}},
            // Example 2
            {{{1,100},{7,100},{1,100},{7,100},{1,100},{7,100},{1,100},{7,100},{1,100},{7,100}},
             {{1,100},{7,100}}},
            // Single student
            {{{1,90},{1,80},{1,70},{1,60},{1,50}}, {{1,70}}},
            // Multiple students
            {{{1,100},{1,99},{1,98},{1,97},{1,96},{2,90},{2,89},{2,88},{2,87},{2,86}},
             {{1,98},{2,88}}},
            // Unsorted IDs
            {{{3,100},{1,100},{2,100},{3,99},{1,99},{2,99},{3,98},{1,98},{2,98},{3,97},{1,97},{2,97}},
             {{1,98},{2,98},{3,98}}}
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int[][] items = testCases[i][0];
            int[][] expected = testCases[i][1];
            
            System.out.printf("\nTest %d:%n", i + 1);
            System.out.println("Items: " + items.length + " scores");
            
            int[][] result1 = highFive(items);
            int[][] result2 = highFiveSortAll(items);
            int[][] result3 = highFivePQExplicit(items);
            int[][] result4 = highFiveArray(items);
            int[][] result5 = highFiveTreeMap(items);
            
            boolean allMatch = Arrays.deepEquals(result1, expected) && 
                              Arrays.deepEquals(result2, expected) &&
                              Arrays.deepEquals(result3, expected) &&
                              Arrays.deepEquals(result4, expected) &&
                              Arrays.deepEquals(result5, expected);
            
            if (allMatch) {
                System.out.println("✓ PASS - Results: " + Arrays.deepToString(result1));
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + Arrays.deepToString(expected));
                System.out.println("  Method 1: " + Arrays.deepToString(result1));
                System.out.println("  Method 2: " + Arrays.deepToString(result2));
                System.out.println("  Method 3: " + Arrays.deepToString(result3));
                System.out.println("  Method 4: " + Arrays.deepToString(result4));
                System.out.println("  Method 5: " + Arrays.deepToString(result5));
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeHighFive(items);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=".repeat(50));
        
        // Generate large test case
        Random rand = new Random(42);
        int n = 10000;
        int[][] items = new int[n][2];
        for (int i = 0; i < n; i++) {
            items[i][0] = rand.nextInt(1000) + 1; // ID 1-1000
            items[i][1] = rand.nextInt(101);      // Score 0-100
        }
        
        System.out.println("Test Setup: " + n + " scores");
        
        long[] times = new long[5];
        
        // Method 1: HashMap + Min-Heap
        int[][] items1 = copyItems(items);
        long start = System.currentTimeMillis();
        highFive(items1);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Sort All
        int[][] items2 = copyItems(items);
        start = System.currentTimeMillis();
        highFiveSortAll(items2);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: PQ Explicit
        int[][] items3 = copyItems(items);
        start = System.currentTimeMillis();
        highFivePQExplicit(items3);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Array-based
        int[][] items4 = copyItems(items);
        start = System.currentTimeMillis();
        highFiveArray(items4);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: TreeMap
        int[][] items5 = copyItems(items);
        start = System.currentTimeMillis();
        highFiveTreeMap(items5);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. HashMap + Min-Heap     | %9d%n", times[0]);
        System.out.printf("2. Sort All               | %9d%n", times[1]);
        System.out.printf("3. PQ Explicit            | %9d%n", times[2]);
        System.out.printf("4. Array-based            | %9d%n", times[3]);
        System.out.printf("5. TreeMap                | %9d%n", times[4]);
        
        System.out.println("\nObservations:");
        System.out.println("1. Min-heap approach is most efficient for large datasets");
        System.out.println("2. Array-based is fastest due to fixed ID range");
        System.out.println("3. Sort all is slower for students with many scores");
        System.out.println("4. TreeMap has overhead for maintaining order");
    }
    
    private int[][] copyItems(int[][] items) {
        int[][] copy = new int[items.length][2];
        for (int i = 0; i < items.length; i++) {
            copy[i][0] = items[i][0];
            copy[i][1] = items[i][1];
        }
        return copy;
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Exactly 5 scores per student:");
        int[][] items1 = {{1,100},{1,90},{1,80},{1,70},{1,60},{2,95},{2,85},{2,75},{2,65},{2,55}};
        System.out.println("   Input: 10 scores (5 each)");
        System.out.println("   Output: " + Arrays.deepToString(highFive(items1)));
        
        System.out.println("\n2. All same scores:");
        int[][] items2 = {{1,50},{1,50},{1,50},{1,50},{1,50},{2,75},{2,75},{2,75},{2,75},{2,75}};
        System.out.println("   Input: All same scores");
        System.out.println("   Output: " + Arrays.deepToString(highFive(items2)));
        
        System.out.println("\n3. More than 5 scores per student:");
        int[][] items3 = {{1,100},{1,99},{1,98},{1,97},{1,96},{1,95},{1,94},{1,93},{1,92},{1,91}};
        System.out.println("   Input: 10 scores for student 1");
        System.out.println("   Output: " + Arrays.deepToString(highFive(items3)));
        
        System.out.println("\n4. Maximum ID range:");
        int[][] items4 = new int[5000][2];
        for (int i = 0; i < 5000; i++) {
            items4[i][0] = i % 1000 + 1;
            items4[i][1] = 100;
        }
        System.out.println("   Input: 5000 scores distributed across IDs 1-1000");
        System.out.println("   Output: " + highFive(items4).length + " students");
        
        System.out.println("\n5. Minimum score (0):");
        int[][] items5 = {{1,0},{1,0},{1,0},{1,0},{1,0},{1,0},{1,0},{1,0},{1,0},{1,0}};
        System.out.println("   Input: All zeros");
        System.out.println("   Output: " + Arrays.deepToString(highFive(items5)));
    }
    
    /**
     * Helper: Explain the algorithm
     */
    public void explainAlgorithm() {
        System.out.println("\nAlgorithm Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nProblem: Find average of top 5 scores for each student.");
        
        System.out.println("\nKey Insight:");
        System.out.println("We only need to keep the top 5 scores for each student.");
        System.out.println("A min-heap of size 5 is perfect for this.");
        
        System.out.println("\nWhy Min-Heap:");
        System.out.println("- Adding a score: O(log 5) = O(1)");
        System.out.println("- When heap size exceeds 5, remove smallest");
        System.out.println("- Ensures heap always contains the 5 largest scores");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Create map: student ID → min-heap");
        System.out.println("2. For each [id, score]:");
        System.out.println("   a. Add score to student's heap");
        System.out.println("   b. If heap size > 5, remove smallest");
        System.out.println("3. For each student:");
        System.out.println("   a. Sum all scores in heap");
        System.out.println("   b. average = sum / 5 (integer division)");
        System.out.println("4. Sort results by ID");
        System.out.println("5. Return results");
        
        System.out.println("\nExample: Student with scores [91,92,60,65,87,100]");
        System.out.println("  Add 91: heap=[91]");
        System.out.println("  Add 92: heap=[91,92]");
        System.out.println("  Add 60: heap=[60,91,92]");
        System.out.println("  Add 65: heap=[60,65,91,92]");
        System.out.println("  Add 87: heap=[60,65,87,91,92]");
        System.out.println("  Add 100: heap=[60,65,87,91,92,100] → size>5 → remove 60 → [65,87,91,92,100]");
        System.out.println("  Sum = 65+87+91+92+100 = 435");
        System.out.println("  Average = 435 / 5 = 87");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Is ID guaranteed to be in range? (1-1000)");
        System.out.println("   - Are there at least 5 scores per student? (Yes)");
        System.out.println("   - Should average use floor division? (Yes)");
        
        System.out.println("\n2. Start with grouping approach:");
        System.out.println("   - Mention HashMap to group scores by ID");
        System.out.println("   - Discuss keeping only top 5 scores");
        
        System.out.println("\n3. Optimize with min-heap:");
        System.out.println("   - Explain why min-heap is efficient");
        System.out.println("   - Show how it maintains only top K elements");
        
        System.out.println("\n4. Discuss alternatives:");
        System.out.println("   - Sort all scores (O(n log n) vs O(n))");
        System.out.println("   - Array-based for fixed ID range");
        
        System.out.println("\n5. Complexity analysis:");
        System.out.println("   - Time: O(n log 5) = O(n)");
        System.out.println("   - Space: O(m × 5) = O(m) where m = unique students");
        
        System.out.println("\n6. Edge cases:");
        System.out.println("   - Exactly 5 scores");
        System.out.println("   - All scores the same");
        System.out.println("   - More than 5 scores");
        System.out.println("   - IDs not sorted in input");
        
        System.out.println("\n7. Common mistakes:");
        System.out.println("   - Forgetting to remove smallest when heap exceeds size");
        System.out.println("   - Using max-heap instead of min-heap");
        System.out.println("   - Not sorting final result by ID");
        System.out.println("   - Using regular division instead of integer division");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("1086. High Five");
        System.out.println("===============");
        
        // Explain algorithm
        solution.explainAlgorithm();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
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
    public int[][] highFive(int[][] items) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        
        for (int[] item : items) {
            int id = item[0];
            int score = item[1];
            
            map.putIfAbsent(id, new PriorityQueue<>());
            PriorityQueue<Integer> heap = map.get(id);
            heap.offer(score);
            
            if (heap.size() > 5) {
                heap.poll();
            }
        }
        
        List<int[]> result = new ArrayList<>();
        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet()) {
            int id = entry.getKey();
            PriorityQueue<Integer> heap = entry.getValue();
            
            int sum = 0;
            for (int score : heap) {
                sum += score;
            }
            result.add(new int[]{id, sum / 5});
        }
        
        result.sort((a, b) -> Integer.compare(a[0], b[0]));
        return result.toArray(new int[result.size()][]);
    }
}
            """);
        
        System.out.println("\nAlternative (Array-based for fixed IDs):");
        System.out.println("""
class Solution {
    public int[][] highFive(int[][] items) {
        PriorityQueue<Integer>[] scores = new PriorityQueue[1001];
        for (int i = 1; i <= 1000; i++) {
            scores[i] = new PriorityQueue<>();
        }
        
        for (int[] item : items) {
            int id = item[0];
            int score = item[1];
            scores[id].offer(score);
            if (scores[id].size() > 5) {
                scores[id].poll();
            }
        }
        
        List<int[]> result = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            if (!scores[i].isEmpty()) {
                int sum = 0;
                for (int s : scores[i]) {
                    sum += s;
                }
                result.add(new int[]{i, sum / 5});
            }
        }
        
        return result.toArray(new int[result.size()][]);
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Min-heap of size 5 keeps only the top scores");
        System.out.println("2. Time complexity: O(n) - each score processed in O(1)");
        System.out.println("3. Space complexity: O(m) where m = number of students");
        System.out.println("4. Sort result by ID before returning");
        System.out.println("5. Use integer division for floor average");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) - each score processed once with O(log 5) heap ops");
        System.out.println("- Space: O(m × 5) - at most 5 scores per student");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. What if we needed top K scores instead of 5?");
        System.out.println("2. How would you handle missing scores?");
        System.out.println("3. What if IDs are not in a limited range?");
        System.out.println("4. How would you implement with streams?");
        System.out.println("5. What if we needed the actual top 5 scores, not just average?");
    }
}
