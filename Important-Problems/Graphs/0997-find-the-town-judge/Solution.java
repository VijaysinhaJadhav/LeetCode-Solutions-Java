
## Solution.java

```java
/**
 * 997. Find the Town Judge
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * In a town of n people, find the town judge who:
 * 1. Trusts nobody
 * 2. Is trusted by everybody else
 * 3. Exactly one person satisfies both conditions
 * 
 * Key Insights:
 * 1. Model trust relationships as directed graph edges
 * 2. Judge has in-degree = n-1 and out-degree = 0
 * 3. Use arrays to track in-degree and out-degree
 * 4. Only one person can satisfy both conditions
 * 
 * Approach (Degree Counting):
 * 1. Create arrays for in-degree and out-degree
 * 2. Process each trust relationship to update degrees
 * 3. Find person with in-degree = n-1 and out-degree = 0
 * 4. Return that person or -1 if not found
 * 
 * Time Complexity: O(n + t) where t is trust array length
 * Space Complexity: O(n)
 * 
 * Tags: Array, Graph, Hash Table
 */

import java.util.*;

class Solution {
    /**
     * Approach 1: Degree Counting with Arrays - RECOMMENDED
     * O(n + t) time, O(n) space
     */
    public int findJudge(int n, int[][] trust) {
        // Arrays to track in-degree (trusted by) and out-degree (trusts others)
        int[] trustedBy = new int[n + 1];  // trustedBy[i] = how many people trust person i
        int[] trustsOthers = new int[n + 1]; // trustsOthers[i] = how many people person i trusts
        
        // Process each trust relationship
        for (int[] relation : trust) {
            int truster = relation[0];
            int trusted = relation[1];
            
            trustsOthers[truster]++;  // truster trusts someone
            trustedBy[trusted]++;     // trusted is trusted by someone
        }
        
        // Find the person who is trusted by n-1 people and trusts nobody
        for (int i = 1; i <= n; i++) {
            if (trustedBy[i] == n - 1 && trustsOthers[i] == 0) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 2: Single Array Optimization
     * O(n + t) time, O(n) space
     * Uses net trust score concept
     */
    public int findJudgeSingleArray(int n, int[][] trust) {
        int[] trustScore = new int[n + 1];
        
        for (int[] relation : trust) {
            int truster = relation[0];
            int trusted = relation[1];
            
            // When you trust someone, your score decreases
            trustScore[truster]--;
            // When you are trusted by someone, your score increases
            trustScore[trusted]++;
        }
        
        // The judge should have trust score = n-1
        // (trusted by n-1 people, trusts nobody)
        for (int i = 1; i <= n; i++) {
            if (trustScore[i] == n - 1) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 3: HashMap Implementation
     * O(n + t) time, O(n) space
     * Alternative using HashMaps
     */
    public int findJudgeHashMap(int n, int[][] trust) {
        if (n == 1 && trust.length == 0) return 1;
        
        Map<Integer, Integer> trustedBy = new HashMap<>();
        Set<Integer> trustsSomeone = new HashSet<>();
        
        for (int[] relation : trust) {
            int truster = relation[0];
            int trusted = relation[1];
            
            trustsSomeone.add(truster);
            trustedBy.put(trusted, trustedBy.getOrDefault(trusted, 0) + 1);
        }
        
        // The judge should be trusted by n-1 people and not trust anyone
        for (int i = 1; i <= n; i++) {
            if (trustedBy.getOrDefault(i, 0) == n - 1 && !trustsSomeone.contains(i)) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 4: Graph Adjacency List
     * O(n + t) time, O(n + t) space
     * More complex but demonstrates graph approach
     */
    public int findJudgeGraph(int n, int[][] trust) {
        if (n == 1) return 1;
        
        // Create adjacency list for trust relationships
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // Build the graph
        for (int[] relation : trust) {
            int truster = relation[0];
            int trusted = relation[1];
            graph.get(truster).add(trusted);
        }
        
        // Find potential judge (person with no outgoing edges)
        List<Integer> potentialJudges = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (graph.get(i).isEmpty()) {
                potentialJudges.add(i);
            }
        }
        
        // If there's not exactly one potential judge, return -1
        if (potentialJudges.size() != 1) {
            return -1;
        }
        
        int judgeCandidate = potentialJudges.get(0);
        
        // Check if everyone else trusts the candidate
        for (int i = 1; i <= n; i++) {
            if (i != judgeCandidate) {
                if (!graph.get(i).contains(judgeCandidate)) {
                    return -1;
                }
            }
        }
        
        return judgeCandidate;
    }
    
    /**
     * Approach 5: Early Termination with Debug Info
     * O(n + t) time, O(n) space
     * Includes detailed debugging information
     */
    public int findJudgeDebug(int n, int[][] trust) {
        int[] trustedBy = new int[n + 1];
        int[] trustsOthers = new int[n + 1];
        
        System.out.println("Processing trust relationships:");
        for (int[] relation : trust) {
            int truster = relation[0];
            int trusted = relation[1];
            
            trustsOthers[truster]++;
            trustedBy[trusted]++;
            
            System.out.printf("  %d trusts %d -> trustsOthers[%d]=%d, trustedBy[%d]=%d%n",
                            truster, trusted, truster, trustsOthers[truster], trusted, trustedBy[trusted]);
        }
        
        System.out.println("\nChecking candidates:");
        for (int i = 1; i <= n; i++) {
            System.out.printf("  Person %d: trustedBy=%d, trustsOthers=%d",
                            i, trustedBy[i], trustsOthers[i]);
            
            if (trustedBy[i] == n - 1 && trustsOthers[i] == 0) {
                System.out.println(" -> JUDGE FOUND");
                return i;
            } else {
                System.out.println(" -> not judge");
            }
        }
        
        System.out.println("No judge found");
        return -1;
    }
    
    /**
     * Helper method to visualize trust relationships
     */
    private void visualizeTrust(int n, int[][] trust) {
        System.out.println("Trust Relationships Visualization:");
        System.out.println("People: 1 to " + n);
        
        // Create adjacency list for display
        Map<Integer, List<Integer>> trustMap = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            trustMap.put(i, new ArrayList<>());
        }
        
        for (int[] relation : trust) {
            int truster = relation[0];
            int trusted = relation[1];
            trustMap.get(truster).add(trusted);
        }
        
        // Display who trusts whom
        for (int i = 1; i <= n; i++) {
            List<Integer> trusts = trustMap.get(i);
            if (!trusts.isEmpty()) {
                System.out.printf("  Person %d trusts: %s%n", i, trusts);
            } else {
                System.out.printf("  Person %d trusts: nobody%n", i);
            }
        }
        
        // Calculate and display who is trusted by whom
        Map<Integer, List<Integer>> trustedByMap = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            trustedByMap.put(i, new ArrayList<>());
        }
        
        for (int[] relation : trust) {
            int truster = relation[0];
            int trusted = relation[1];
            trustedByMap.get(trusted).add(truster);
        }
        
        System.out.println("\nTrusted By Relationships:");
        for (int i = 1; i <= n; i++) {
            List<Integer> trustedBy = trustedByMap.get(i);
            System.out.printf("  Person %d is trusted by: %s (count: %d)%n", 
                            i, trustedBy, trustedBy.size());
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Find the Town Judge:");
        System.out.println("============================");
        
        // Test case 1: Example 1
        System.out.println("\nTest 1: Example from problem");
        int n1 = 2;
        int[][] trust1 = {{1, 2}};
        int expected1 = 2;
        
        long startTime = System.nanoTime();
        int result1a = solution.findJudge(n1, trust1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.findJudgeSingleArray(n1, trust1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.findJudgeHashMap(n1, trust1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1d = solution.findJudgeGraph(n1, trust1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = result1a == expected1;
        boolean test1b = result1b == expected1;
        boolean test1c = result1c == expected1;
        boolean test1d = result1d == expected1;
        
        System.out.println("Degree Counting: " + result1a + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Single Array: " + result1b + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("HashMap: " + result1c + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("Graph: " + result1d + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the trust relationships
        solution.visualizeTrust(n1, trust1);
        
        // Test case 2: Example 2
        System.out.println("\nTest 2: Example 2");
        int n2 = 3;
        int[][] trust2 = {{1, 3}, {2, 3}};
        int result2 = solution.findJudge(n2, trust2);
        int expected2 = 3;
        System.out.println("Example 2: " + result2 + " - " + 
                         (result2 == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Example 3 (no judge)
        System.out.println("\nTest 3: Example 3 (no judge)");
        int n3 = 3;
        int[][] trust3 = {{1, 3}, {2, 3}, {3, 1}};
        int result3 = solution.findJudge(n3, trust3);
        int expected3 = -1;
        System.out.println("Example 3: " + result3 + " - " + 
                         (result3 == expected3 ? "PASSED" : "FAILED"));
        
        // Test case 4: Single person (edge case)
        System.out.println("\nTest 4: Single person");
        int n4 = 1;
        int[][] trust4 = {};
        int result4 = solution.findJudge(n4, trust4);
        System.out.println("Single person: " + result4 + " - " + 
                         (result4 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 5: Empty trust array but n > 1
        System.out.println("\nTest 5: Empty trust array, n=3");
        int n5 = 3;
        int[][] trust5 = {};
        int result5 = solution.findJudge(n5, trust5);
        System.out.println("Empty trust, n=3: " + result5 + " - " + 
                         (result5 == -1 ? "PASSED" : "FAILED"));
        
        // Test case 6: Multiple potential judges
        System.out.println("\nTest 6: Multiple potential judges");
        int n6 = 4;
        int[][] trust6 = {{1, 3}, {2, 3}, {4, 3}, {1, 4}};
        int result6 = solution.findJudge(n6, trust6);
        System.out.println("Multiple candidates: " + result6 + " - " + 
                         (result6 == -1 ? "PASSED" : "FAILED"));
        
        // Test case 7: Judge trusts someone
        System.out.println("\nTest 7: Judge trusts someone");
        int n7 = 3;
        int[][] trust7 = {{1, 3}, {2, 3}, {3, 2}};
        int result7 = solution.findJudge(n7, trust7);
        System.out.println("Judge trusts someone: " + result7 + " - " + 
                         (result7 == -1 ? "PASSED" : "FAILED"));
        
        // Test case 8: Large n with judge
        System.out.println("\nTest 8: Large n with judge");
        int n8 = 5;
        int[][] trust8 = {{1, 3}, {2, 3}, {4, 3}, {5, 3}, {1, 2}, {4, 5}};
        int result8 = solution.findJudge(n8, trust8);
        System.out.println("Large n with judge: " + result8 + " - " + 
                         (result8 == 3 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        System.out.println("  Degree Counting: " + time1a + " ns");
        System.out.println("  Single Array: " + time1b + " ns");
        System.out.println("  HashMap: " + time1c + " ns");
        System.out.println("  Graph: " + time1d + " ns");
        
        // Performance test with larger input
        System.out.println("\nPerformance Test with n=1000, trust=5000:");
        int n9 = 1000;
        int[][] trust9 = generateLargeTrustArray(1000, 5000);
        
        startTime = System.nanoTime();
        int result9 = solution.findJudge(n9, trust9);
        long largeTime = System.nanoTime() - startTime;
        System.out.println("Large input result: " + result9 + ", time: " + largeTime + " ns");
        
        // Debug version with detailed output
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DEBUG VERSION OUTPUT:");
        System.out.println("=".repeat(70));
        solution.findJudgeDebug(n2, trust2);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        solution.explainAlgorithm();
        
        System.out.println("\nAll tests completed!");
    }
    
    /**
     * Generate a large trust array for performance testing
     */
    private static int[][] generateLargeTrustArray(int n, int trustCount) {
        int[][] trust = new int[trustCount][2];
        Random random = new Random(42);
        
        // Make person n the judge (trusted by everyone, trusts nobody)
        for (int i = 0; i < trustCount; i++) {
            int truster = random.nextInt(n - 1) + 1; // 1 to n-1
            int trusted = n; // Always trust the judge
            trust[i][0] = truster;
            trust[i][1] = trusted;
        }
        
        return trust;
    }
    
    /**
     * Detailed explanation of the algorithm
     */
    private void explainAlgorithm() {
        System.out.println("\nKey Insights:");
        System.out.println("1. The town judge must satisfy two conditions:");
        System.out.println("   - Trusts nobody (out-degree = 0)");
        System.out.println("   - Trusted by everyone else (in-degree = n-1)");
        System.out.println("2. We can model this as a directed graph problem");
        System.out.println("3. Only one person can satisfy both conditions");
        
        System.out.println("\nDegree Counting Approach:");
        System.out.println("1. Use two arrays:");
        System.out.println("   - trustedBy[i] = how many people trust person i");
        System.out.println("   - trustsOthers[i] = how many people person i trusts");
        
        System.out.println("2. Process each trust relationship [a, b]:");
        System.out.println("   - Increment trustsOthers[a] (a trusts someone)");
        System.out.println("   - Increment trustedBy[b] (b is trusted by someone)");
        
        System.out.println("3. Find person i where:");
        System.out.println("   - trustedBy[i] == n - 1");
        System.out.println("   - trustsOthers[i] == 0");
        
        System.out.println("\nSingle Array Optimization:");
        System.out.println("1. Use net trust score:");
        System.out.println("   - When you trust someone: score--");
        System.out.println("   - When you are trusted: score++");
        System.out.println("2. Judge has score = n-1 (trusted by n-1, trusts 0)");
        
        System.out.println("\nExample Walkthrough:");
        System.out.println("n = 3, trust = [[1,3], [2,3]]");
        System.out.println("Initial arrays: trustedBy = [0,0,0,0], trustsOthers = [0,0,0,0]");
        System.out.println("Process [1,3]:");
        System.out.println("  trustsOthers[1]++ -> 1");
        System.out.println("  trustedBy[3]++ -> 1");
        System.out.println("Process [2,3]:");
        System.out.println("  trustsOthers[2]++ -> 1");
        System.out.println("  trustedBy[3]++ -> 2");
        System.out.println("Check candidates:");
        System.out.println("  Person 1: trustedBy=0, trustsOthers=1 -> not judge");
        System.out.println("  Person 2: trustedBy=0, trustsOthers=1 -> not judge");
        System.out.println("  Person 3: trustedBy=2, trustsOthers=0 -> JUDGE (2 = n-1)");
        
        System.out.println("\nTime Complexity: O(n + t)");
        System.out.println("- Process n people: O(n)");
        System.out.println("- Process t trust relationships: O(t)");
        System.out.println("- Total: O(n + t)");
        
        System.out.println("\nSpace Complexity: O(n)");
        System.out.println("- Two arrays of size n+1: O(n)");
    }
}

/**
 * Additional utility class for graph operations
 */
class GraphUtils {
    /**
     * Calculate in-degree and out-degree for each node
     */
    public static int[][] calculateDegrees(int n, int[][] edges) {
        int[] inDegree = new int[n + 1];
        int[] outDegree = new int[n + 1];
        
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            outDegree[from]++;
            inDegree[to]++;
        }
        
        return new int[][]{inDegree, outDegree};
    }
    
    /**
     * Find nodes with zero out-degree
     */
    public static List<Integer> findZeroOutDegree(int n, int[][] edges) {
        int[] outDegree = new int[n + 1];
        for (int[] edge : edges) {
            outDegree[edge[0]]++;
        }
        
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (outDegree[i] == 0) {
                result.add(i);
            }
        }
        return result;
    }
    
    /**
     * Find nodes with maximum in-degree
     */
    public static List<Integer> findMaxInDegree(int n, int[][] edges) {
        int[] inDegree = new int[n + 1];
        for (int[] edge : edges) {
            inDegree[edge[1]]++;
        }
        
        int maxDegree = 0;
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] > maxDegree) {
                maxDegree = inDegree[i];
                result.clear();
                result.add(i);
            } else if (inDegree[i] == maxDegree) {
                result.add(i);
            }
        }
        return result;
    }
}
