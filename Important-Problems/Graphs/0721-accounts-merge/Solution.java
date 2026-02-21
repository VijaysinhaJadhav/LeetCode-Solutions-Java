
# Solution.java

```java
import java.util.*;

/**
 * 721. Accounts Merge
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Merge accounts that share common emails. Each account has a name and multiple emails.
 * Two accounts belong to the same person if they share at least one email.
 * 
 * Key Insights:
 * 1. This is a graph connectivity problem - use Union-Find [citation:4][citation:5]
 * 2. Map emails to account indices for union operations
 * 3. After union, group emails by their root account
 * 4. Sort emails and format output with name
 */
class Solution {
    
    /**
     * Approach 1: Union-Find on Account Indices (Recommended)
     * Time: O(NK log(NK)), Space: O(NK)
     * 
     * Steps:
     * 1. Map each email to its first seen account index
     * 2. Union current account with the previously seen account for each email
     * 3. Group emails by root account index
     * 4. Sort emails and add name to create final result [citation:4]
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // Map email to the first account index where it was seen
        Map<String, Integer> emailToFirstAccountIndex = new HashMap<>();
        
        int n = accounts.size();
        UnionFind uf = new UnionFind(n);
        
        // Step 1 & 2: Process each account and union accounts sharing emails
        for (int i = 0; i < n; i++) {
            List<String> account = accounts.get(i);
            // Start from index 1 to skip the name
            for (int j = 1; j < account.size(); j++) {
                String email = account.get(j);
                
                // If email already seen, union current account with the previous one
                if (emailToFirstAccountIndex.containsKey(email)) {
                    int previousAccountIndex = emailToFirstAccountIndex.get(email);
                    uf.union(i, previousAccountIndex);
                } else {
                    emailToFirstAccountIndex.put(email, i);
                }
            }
        }
        
        // Step 3: Group emails by root account index
        Map<Integer, List<String>> rootToEmails = new HashMap<>();
        
        for (Map.Entry<String, Integer> entry : emailToFirstAccountIndex.entrySet()) {
            String email = entry.getKey();
            int accountIndex = entry.getValue();
            
            int root = uf.find(accountIndex);
            
            if (!rootToEmails.containsKey(root)) {
                rootToEmails.put(root, new ArrayList<>());
            }
            rootToEmails.get(root).add(email);
        }
        
        // Step 4: Build result with name and sorted emails
        List<List<String>> result = new ArrayList<>();
        
        for (Map.Entry<Integer, List<String>> entry : rootToEmails.entrySet()) {
            int rootAccountIndex = entry.getKey();
            List<String> emails = entry.getValue();
            
            // Sort emails
            Collections.sort(emails);
            
            // Get the name from the original account
            String name = accounts.get(rootAccountIndex).get(0);
            
            // Create merged account
            List<String> mergedAccount = new ArrayList<>();
            mergedAccount.add(name);
            mergedAccount.addAll(emails);
            
            result.add(mergedAccount);
        }
        
        return result;
    }
    
    /**
     * Approach 2: Union-Find on Emails Directly
     * Time: O(NK log(NK)), Space: O(NK)
     * 
     * Alternative: Union emails directly and track owner separately [citation:9]
     */
    public List<List<String>> accountsMergeEmailUnion(List<List<String>> accounts) {
        Map<String, String> emailToOwner = new HashMap<>();
        Map<String, String> emailToParent = new HashMap<>();
        
        // Initialize each email as its own parent and record owner
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                emailToParent.put(email, email);
                emailToOwner.put(email, name);
            }
        }
        
        // Union emails within each account
        for (List<String> account : accounts) {
            String firstEmail = account.get(1);
            String firstRoot = find(firstEmail, emailToParent);
            
            for (int i = 2; i < account.size(); i++) {
                String email = account.get(i);
                String emailRoot = find(email, emailToParent);
                emailToParent.put(emailRoot, firstRoot);
            }
        }
        
        // Group emails by root
        Map<String, TreeSet<String>> rootToEmails = new HashMap<>();
        for (Map.Entry<String, String> entry : emailToParent.entrySet()) {
            String email = entry.getKey();
            String root = find(email, emailToParent);
            
            rootToEmails.computeIfAbsent(root, k -> new TreeSet<>()).add(email);
        }
        
        // Build result
        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<String, TreeSet<String>> entry : rootToEmails.entrySet()) {
            String rootEmail = entry.getKey();
            TreeSet<String> emails = entry.getValue();
            
            List<String> mergedAccount = new ArrayList<>();
            mergedAccount.add(emailToOwner.get(rootEmail));
            mergedAccount.addAll(emails);
            
            result.add(mergedAccount);
        }
        
        return result;
    }
    
    private String find(String email, Map<String, String> parent) {
        if (!parent.get(email).equals(email)) {
            parent.put(email, find(parent.get(email), parent));
        }
        return parent.get(email);
    }
    
    /**
     * Approach 3: DFS Graph Traversal
     * Time: O(NK log(NK)), Space: O(NK)
     * 
     * Build graph of emails and traverse connected components [citation:7]
     */
    public List<List<String>> accountsMergeDFS(List<List<String>> accounts) {
        // Build graph: email -> list of connected emails (including from same account)
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                emailToName.put(email, name);
                
                // Connect all emails in this account
                if (i == 1) continue;
                
                String prevEmail = account.get(i - 1);
                
                graph.computeIfAbsent(prevEmail, k -> new ArrayList<>()).add(email);
                graph.computeIfAbsent(email, k -> new ArrayList<>()).add(prevEmail);
            }
        }
        
        // DFS to find connected components
        Set<String> visited = new HashSet<>();
        List<List<String>> result = new ArrayList<>();
        
        for (String email : emailToName.keySet()) {
            if (!visited.contains(email)) {
                List<String> component = new ArrayList<>();
                dfs(email, graph, visited, component);
                
                Collections.sort(component);
                component.add(0, emailToName.get(email));
                
                result.add(component);
            }
        }
        
        return result;
    }
    
    private void dfs(String email, Map<String, List<String>> graph, 
                     Set<String> visited, List<String> component) {
        visited.add(email);
        component.add(email);
        
        if (graph.containsKey(email)) {
            for (String neighbor : graph.get(email)) {
                if (!visited.contains(neighbor)) {
                    dfs(neighbor, graph, visited, component);
                }
            }
        }
    }
    
    /**
     * Union-Find helper class
     */
    class UnionFind {
        int[] parent;
        int[] rank;
        
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }
        
        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX == rootY) return;
            
            // Union by rank
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
    
    /**
     * Helper: Visualize the merging process
     */
    public void visualizeMerge(List<List<String>> accounts) {
        System.out.println("\nAccounts Merge Visualization:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nOriginal accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("  Account %d: %s%n", i, accounts.get(i));
        }
        
        System.out.println("\nStep 1: Map emails to account indices");
        Map<String, List<Integer>> emailToAccounts = new HashMap<>();
        for (int i = 0; i < accounts.size(); i++) {
            List<String> account = accounts.get(i);
            String name = account.get(0);
            System.out.printf("  Account %d (%s): ", i, name);
            for (int j = 1; j < account.size(); j++) {
                String email = account.get(j);
                emailToAccounts.computeIfAbsent(email, k -> new ArrayList<>()).add(i);
                System.out.print(email);
                if (j < account.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
        
        System.out.println("\nStep 2: Find emails that connect multiple accounts:");
        for (Map.Entry<String, List<Integer>> entry : emailToAccounts.entrySet()) {
            if (entry.getValue().size() > 1) {
                System.out.printf("  Email '%s' connects accounts %s%n", 
                    entry.getKey(), entry.getValue());
            }
        }
        
        List<List<String>> result = accountsMerge(accounts);
        
        System.out.println("\nStep 3: Merged accounts:");
        for (int i = 0; i < result.size(); i++) {
            List<String> account = result.get(i);
            System.out.printf("  Person %d: %s%n", i + 1, account);
        }
    }
    
    /**
     * Helper: Generate test cases
     */
    public Object[][] generateTestCases() {
        return new Object[][] {
            // Example 1
            {
                Arrays.asList(
                    Arrays.asList("John","johnsmith@mail.com","john_newyork@mail.com"),
                    Arrays.asList("John","johnsmith@mail.com","john00@mail.com"),
                    Arrays.asList("Mary","mary@mail.com"),
                    Arrays.asList("John","johnnybravo@mail.com")
                ),
                Arrays.asList(
                    Arrays.asList("John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"),
                    Arrays.asList("Mary","mary@mail.com"),
                    Arrays.asList("John","johnnybravo@mail.com")
                )
            },
            // Example 2
            {
                Arrays.asList(
                    Arrays.asList("Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"),
                    Arrays.asList("Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"),
                    Arrays.asList("Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"),
                    Arrays.asList("Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"),
                    Arrays.asList("Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co")
                ),
                Arrays.asList(
                    Arrays.asList("Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"),
                    Arrays.asList("Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"),
                    Arrays.asList("Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"),
                    Arrays.asList("Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"),
                    Arrays.asList("Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co")
                )
            },
            // Single account
            {
                Arrays.asList(
                    Arrays.asList("Alex","alex@mail.com","alex2@mail.com")
                ),
                Arrays.asList(
                    Arrays.asList("Alex","alex@mail.com","alex2@mail.com")
                )
            },
            // Two accounts with no common emails
            {
                Arrays.asList(
                    Arrays.asList("John","john1@mail.com"),
                    Arrays.asList("John","john2@mail.com")
                ),
                Arrays.asList(
                    Arrays.asList("John","john1@mail.com"),
                    Arrays.asList("John","john2@mail.com")
                )
            }
        };
    }
    
    /**
     * Helper: Compare two account lists (order-insensitive)
     */
    private boolean accountsEqual(List<List<String>> result, List<List<String>> expected) {
        if (result.size() != expected.size()) return false;
        
        // Convert to sets for comparison
        Set<Set<String>> resultSet = new HashSet<>();
        Set<Set<String>> expectedSet = new HashSet<>();
        
        for (List<String> account : result) {
            resultSet.add(new HashSet<>(account));
        }
        for (List<String> account : expected) {
            expectedSet.add(new HashSet<>(account));
        }
        
        return resultSet.equals(expectedSet);
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        Object[][] testCases = generateTestCases();
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            @SuppressWarnings("unchecked")
            List<List<String>> accounts = (List<List<String>>) testCases[i][0];
            @SuppressWarnings("unchecked")
            List<List<String>> expected = (List<List<String>>) testCases[i][1];
            
            System.out.printf("\nTest %d:%n", i + 1);
            System.out.println("Input accounts: " + accounts);
            
            List<List<String>> result1 = accountsMerge(accounts);
            List<List<String>> result2 = accountsMergeEmailUnion(accounts);
            List<List<String>> result3 = accountsMergeDFS(accounts);
            
            boolean allMatch = accountsEqual(result1, expected) && 
                              accountsEqual(result2, expected) &&
                              accountsEqual(result3, expected);
            
            if (allMatch) {
                System.out.println("✓ PASS");
                passed++;
            } else {
                System.out.println("✗ FAIL");
                System.out.println("  Expected: " + expected);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
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
        System.out.println("=======================");
        
        // Create a test case with 500 accounts
        List<List<String>> accounts = new ArrayList<>();
        Random rand = new Random(42);
        
        for (int i = 0; i < 500; i++) {
            List<String> account = new ArrayList<>();
            account.add("User" + i);
            
            // Add 3-5 random emails
            int numEmails = rand.nextInt(3) + 3;
            for (int j = 0; j < numEmails; j++) {
                account.add("user" + i + "_email" + j + "@test.com");
            }
            accounts.add(account);
        }
        
        // Create some connections (10% of accounts share emails)
        for (int i = 0; i < 50; i++) {
            int idx1 = rand.nextInt(500);
            int idx2 = rand.nextInt(500);
            if (idx1 != idx2) {
                String sharedEmail = "shared" + i + "@test.com";
                accounts.get(idx1).add(sharedEmail);
                accounts.get(idx2).add(sharedEmail);
            }
        }
        
        System.out.println("Test Setup:");
        System.out.println("  Accounts: " + accounts.size());
        System.out.println("  Total emails: ~" + (accounts.size() * 4));
        
        long[] times = new long[3];
        
        // Method 1: Union-Find on account indices
        long start = System.currentTimeMillis();
        List<List<String>> result1 = accountsMerge(accounts);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Union-Find on emails
        start = System.currentTimeMillis();
        List<List<String>> result2 = accountsMergeEmailUnion(accounts);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: DFS
        start = System.currentTimeMillis();
        List<List<String>> result3 = accountsMergeDFS(accounts);
        times[2] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                      | Time (ms) | Accounts Found");
        System.out.println("----------------------------|-----------|---------------");
        System.out.printf("1. Union-Find (account idx) | %9d | %13d%n", times[0], result1.size());
        System.out.printf("2. Union-Find (emails)      | %9d | %13d%n", times[1], result2.size());
        System.out.printf("3. DFS                       | %9d | %13d%n", times[2], result3.size());
        
        System.out.println("\nAll methods produce same result: " + 
            (result1.size() == result2.size() && result2.size() == result3.size()));
    }
    
    /**
     * Helper: Edge cases testing
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        Solution solution = new Solution();
        
        System.out.println("\n1. Single account with multiple emails:");
        List<List<String>> accounts1 = Arrays.asList(
            Arrays.asList("Alex", "alex1@mail.com", "alex2@mail.com")
        );
        System.out.println("  Input: " + accounts1);
        System.out.println("  Output: " + solution.accountsMerge(accounts1));
        
        System.out.println("\n2. Two accounts with same name but no common emails:");
        List<List<String>> accounts2 = Arrays.asList(
            Arrays.asList("John", "john1@mail.com"),
            Arrays.asList("John", "john2@mail.com")
        );
        System.out.println("  Input: " + accounts2);
        System.out.println("  Output: " + solution.accountsMerge(accounts2));
        
        System.out.println("\n3. Three accounts where two share email:");
        List<List<String>> accounts3 = Arrays.asList(
            Arrays.asList("Jane", "jane@mail.com", "jane-work@mail.com"),
            Arrays.asList("Jane", "jane@mail.com"),
            Arrays.asList("Jane", "jane-personal@mail.com")
        );
        System.out.println("  Input: " + accounts3);
        System.out.println("  Output: " + solution.accountsMerge(accounts3));
        
        System.out.println("\n4. Empty accounts (should handle gracefully):");
        List<List<String>> accounts4 = Arrays.asList();
        System.out.println("  Input: " + accounts4);
        System.out.println("  Output: " + solution.accountsMerge(accounts4));
    }
    
    /**
     * Helper: Explain Union-Find solution
     */
    public void explainUnionFind() {
        System.out.println("\nUnion-Find Solution Explanation:");
        System.out.println("================================");
        
        System.out.println("\nWhat is Union-Find?");
        System.out.println("- A data structure that tracks elements partitioned into disjoint sets");
        System.out.println("- Supports two main operations: find (which set?) and union (merge sets)");
        System.out.println("- With path compression and union by rank, operations are nearly O(1)");
        
        System.out.println("\nHow it applies to Accounts Merge:");
        System.out.println("1. Each account is initially its own set");
        System.out.println("2. When an email appears in multiple accounts, those accounts must be merged");
        System.out.println("3. Union operations connect accounts that share emails");
        System.out.println("4. After all unions, each set represents one person");
        
        System.out.println("\nStep-by-step for Example 1:");
        System.out.println("Accounts:");
        System.out.println("  0: [John, johnsmith@mail.com, john_newyork@mail.com]");
        System.out.println("  1: [John, johnsmith@mail.com, john00@mail.com]");
        System.out.println("  2: [Mary, mary@mail.com]");
        System.out.println("  3: [John, johnnybravo@mail.com]");
        
        System.out.println("\nProcess:");
        System.out.println("  - Email 'johnsmith@mail.com' appears in accounts 0 and 1 → union(0,1)");
        System.out.println("  - Email 'mary@mail.com' only in account 2 → no union");
        System.out.println("  - Email 'johnnybravo@mail.com' only in account 3 → no union");
        System.out.println("  - Result sets: {0,1}, {2}, {3}");
        
        System.out.println("\nFinal mapping:");
        System.out.println("  - Set {0,1} → combine all emails from accounts 0 and 1 → [john00@..., john_newyork@..., johnsmith@...]");
        System.out.println("  - Set {2} → [mary@mail.com]");
        System.out.println("  - Set {3} → [johnnybravo@mail.com]");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify the problem:");
        System.out.println("   - When do accounts belong to the same person? (shared email)");
        System.out.println("   - Can same name be different people? (yes)");
        System.out.println("   - What about duplicate emails? (handle gracefully)");
        
        System.out.println("\n2. Identify the pattern:");
        System.out.println("   - This is a graph connectivity problem");
        System.out.println("   - Two standard approaches: Union-Find and DFS");
        
        System.out.println("\n3. Propose Union-Find:");
        System.out.println("   - Explain why it's efficient (near O(1) operations)");
        System.out.println("   - Walk through the example");
        System.out.println("   - Mention path compression and union by rank");
        
        System.out.println("\n4. Implementation steps:");
        System.out.println("   - Initialize Union-Find with account indices");
        System.out.println("   - Map emails to account indices");
        System.out.println("   - Union accounts that share emails");
        System.out.println("   - Group emails by root account");
        System.out.println("   - Sort and format output");
        
        System.out.println("\n5. Handle edge cases:");
        System.out.println("   - Empty input");
        System.out.println("   - Single account");
        System.out.println("   - No connections between accounts");
        System.out.println("   - Multiple accounts for same person");
        
        System.out.println("\n6. Complexity analysis:");
        System.out.println("   - Time: O(NK log(NK)) due to sorting");
        System.out.println("   - Space: O(NK) for storing emails");
        
        System.out.println("\n7. Alternative approaches:");
        System.out.println("   - DFS/BFS on email graph");
        System.out.println("   - Pros and cons of each");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("721. Accounts Merge");
        System.out.println("===================");
        
        // Explain Union-Find
        solution.explainUnionFind();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Visualize example
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Example 1 Visualization:");
        System.out.println("=".repeat(80));
        
        List<List<String>> example = Arrays.asList(
            Arrays.asList("John","johnsmith@mail.com","john_newyork@mail.com"),
            Arrays.asList("John","johnsmith@mail.com","john00@mail.com"),
            Arrays.asList("Mary","mary@mail.com"),
            Arrays.asList("John","johnnybravo@mail.com")
        );
        solution.visualizeMerge(example);
        
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
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        Map<String, Integer> emailToAccountIndex = new HashMap<>();
        UnionFind uf = new UnionFind(n);
        
        // Step 1: Union accounts that share emails
        for (int i = 0; i < n; i++) {
            List<String> account = accounts.get(i);
            for (int j = 1; j < account.size(); j++) {
                String email = account.get(j);
                if (emailToAccountIndex.containsKey(email)) {
                    uf.union(i, emailToAccountIndex.get(email));
                } else {
                    emailToAccountIndex.put(email, i);
                }
            }
        }
        
        // Step 2: Group emails by root account
        Map<Integer, List<String>> rootToEmails = new HashMap<>();
        for (Map.Entry<String, Integer> entry : emailToAccountIndex.entrySet()) {
            String email = entry.getKey();
            int accountIndex = entry.getValue();
            int root = uf.find(accountIndex);
            rootToEmails.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
        }
        
        // Step 3: Build result with sorted emails
        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> entry : rootToEmails.entrySet()) {
            List<String> emails = entry.getValue();
            Collections.sort(emails);
            List<String> mergedAccount = new ArrayList<>();
            mergedAccount.add(accounts.get(entry.getKey()).get(0));
            mergedAccount.addAll(emails);
            result.add(mergedAccount);
        }
        
        return result;
    }
    
    class UnionFind {
        int[] parent;
        int[] rank;
        
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) return;
            
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Union-Find is ideal for connectivity problems [citation:4][citation:8]");
        System.out.println("2. Map emails to account indices for efficient lookup");
        System.out.println("3. Path compression makes find() operations near O(1)");
        System.out.println("4. Sort emails after grouping for final output");
        System.out.println("5. Time complexity: O(NK log(NK))");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle very large number of accounts?");
        System.out.println("2. What if you need to return accounts in specific order?");
        System.out.println("3. How would you modify for real-time merging?");
        System.out.println("4. What if accounts could have multiple names?");
        System.out.println("5. How to detect if a person has multiple accounts with different names?");
    }
}
