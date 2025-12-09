
# Solution.java

```java
/**
 * 71. Simplify Path
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an absolute path for a Unix-style file system, simplify it to canonical path.
 * Rules: 
 * - Single slash between directories
 * - No trailing slash
 * - Handle '.' (current) and '..' (parent)
 * - Multiple slashes become single slash
 * 
 * Key Insights:
 * 1. Use stack to track directory hierarchy
 * 2. Split path by '/' to get components
 * 3. Ignore empty strings and '.' 
 * 4. For '..', pop from stack if not empty
 * 5. For valid directories, push to stack
 * 6. Join stack with '/' for final path
 * 
 * Approach (Stack with Split):
 * 1. Split path by '/'
 * 2. Initialize stack for directories
 * 3. Process each component:
 *    - Skip empty and '.' 
 *    - For '..': pop from stack if not empty
 *    - For valid dir: push to stack
 * 4. Build result from stack with '/'
 * 
 * Time Complexity: O(n) - Process each component once
 * Space Complexity: O(n) - Stack storage
 * 
 * Tags: String, Stack
 */

import java.util.*;

public class Solution {
    
    /**
     * Approach 1: Stack with Split - RECOMMENDED
     * O(n) time, O(n) space - Clean and efficient
     */
    public String simplifyPath(String path) {
        // Split path by slash
        String[] components = path.split("/");
        Stack<String> stack = new Stack<>();
        
        for (String component : components) {
            // Skip empty strings and current directory '.'
            if (component.isEmpty() || component.equals(".")) {
                continue;
            }
            
            // Handle parent directory '..'
            if (component.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                // Valid directory name
                stack.push(component);
            }
        }
        
        // Build canonical path from stack
        StringBuilder result = new StringBuilder();
        for (String dir : stack) {
            result.append("/").append(dir);
        }
        
        // Handle empty stack case (root directory)
        return result.length() > 0 ? result.toString() : "/";
    }
    
    /**
     * Approach 2: Deque for Efficient Building
     * O(n) time, O(n) space - Uses Deque for efficient building
     */
    public String simplifyPathDeque(String path) {
        Deque<String> deque = new LinkedList<>();
        String[] components = path.split("/");
        
        for (String component : components) {
            if (component.isEmpty() || component.equals(".")) {
                continue;
            }
            
            if (component.equals("..")) {
                if (!deque.isEmpty()) {
                    deque.removeLast();
                }
            } else {
                deque.addLast(component);
            }
        }
        
        // Build result efficiently
        if (deque.isEmpty()) {
            return "/";
        }
        
        StringBuilder result = new StringBuilder();
        for (String dir : deque) {
            result.append("/").append(dir);
        }
        
        return result.toString();
    }
    
    /**
     * Approach 3: ArrayList as Stack
     * O(n) time, O(n) space - Uses ArrayList for stack operations
     */
    public String simplifyPathArrayList(String path) {
        List<String> list = new ArrayList<>();
        String[] components = path.split("/");
        
        for (String component : components) {
            if (component.isEmpty() || component.equals(".")) {
                continue;
            }
            
            if (component.equals("..")) {
                if (!list.isEmpty()) {
                    list.remove(list.size() - 1);
                }
            } else {
                list.add(component);
            }
        }
        
        // Build result
        if (list.isEmpty()) {
            return "/";
        }
        
        StringBuilder result = new StringBuilder();
        for (String dir : list) {
            result.append("/").append(dir);
        }
        
        return result.toString();
    }
    
    /**
     * Approach 4: Manual Processing (No Split)
     * O(n) time, O(n) space - Processes path character by character
     */
    public String simplifyPathManual(String path) {
        Stack<String> stack = new Stack<>();
        int n = path.length();
        int i = 0;
        
        while (i < n) {
            // Skip consecutive slashes
            while (i < n && path.charAt(i) == '/') {
                i++;
            }
            
            // Extract current component
            int start = i;
            while (i < n && path.charAt(i) != '/') {
                i++;
            }
            
            String component = path.substring(start, i);
            
            // Process component
            if (component.isEmpty() || component.equals(".")) {
                continue;
            }
            
            if (component.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(component);
            }
        }
        
        // Build result
        if (stack.isEmpty()) {
            return "/";
        }
        
        StringBuilder result = new StringBuilder();
        for (String dir : stack) {
            result.append("/").append(dir);
        }
        
        return result.toString();
    }
    
    /**
     * Approach 5: Array-based Stack (Optimized)
     * O(n) time, O(n) space - Uses array instead of Stack class
     */
    public String simplifyPathArrayStack(String path) {
        String[] components = path.split("/");
        String[] stack = new String[components.length];
        int pointer = 0; // Stack pointer
        
        for (String component : components) {
            if (component.isEmpty() || component.equals(".")) {
                continue;
            }
            
            if (component.equals("..")) {
                if (pointer > 0) {
                    pointer--;
                }
            } else {
                stack[pointer++] = component;
            }
        }
        
        // Build result
        if (pointer == 0) {
            return "/";
        }
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < pointer; i++) {
            result.append("/").append(stack[i]);
        }
        
        return result.toString();
    }
    
    /**
     * Helper method to visualize the stack process
     */
    private void visualizeStackProcess(String path) {
        System.out.println("\nStack Process Visualization:");
        System.out.println("Input path: \"" + path + "\"");
        
        String[] components = path.split("/");
        Stack<String> stack = new Stack<>();
        
        System.out.println("\nComponents: " + Arrays.toString(components));
        System.out.println("\nStep | Component | Stack State | Action");
        System.out.println("-----|-----------|-------------|--------");
        
        for (int i = 0; i < components.length; i++) {
            String component = components[i];
            String stackStateBefore = stack.toString();
            String action = "";
            
            if (component.isEmpty() || component.equals(".")) {
                action = "Skip (empty or current dir)";
            } else if (component.equals("..")) {
                if (!stack.isEmpty()) {
                    String popped = stack.pop();
                    action = "Pop '" + popped + "' (go up one level)";
                } else {
                    action = "No op (already at root)";
                }
            } else {
                stack.push(component);
                action = "Push '" + component + "'";
            }
            
            String stackStateAfter = stack.toString();
            System.out.printf("%4d | %9s | %11s | %s%n", 
                            i + 1, component.isEmpty() ? "\"\"" : component, 
                            stackStateAfter, action);
        }
        
        // Build result
        StringBuilder result = new StringBuilder();
        for (String dir : stack) {
            result.append("/").append(dir);
        }
        String finalPath = result.length() > 0 ? result.toString() : "/";
        
        System.out.println("\nFinal Stack: " + stack);
        System.out.println("Canonical Path: \"" + finalPath + "\"");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Simplify Path:");
        System.out.println("======================");
        
        // Test case 1: Simple path with trailing slash
        System.out.println("\nTest 1: Simple path with trailing slash");
        String path1 = "/home/";
        String expected1 = "/home";
        
        long startTime = System.nanoTime();
        String result1a = solution.simplifyPath(path1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result1b = solution.simplifyPathDeque(path1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result1c = solution.simplifyPathManual(path1);
        long time1c = System.nanoTime() - startTime;
        
        boolean test1a = result1a.equals(expected1);
        boolean test1b = result1b.equals(expected1);
        boolean test1c = result1c.equals(expected1);
        
        System.out.println("Stack: \"" + result1a + "\" - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Deque: \"" + result1b + "\" - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Manual: \"" + result1c + "\" - " + (test1c ? "PASSED" : "FAILED"));
        
        // Visualize the stack process
        solution.visualizeStackProcess(path1);
        
        // Test case 2: Parent directory operations
        System.out.println("\nTest 2: Parent directory operations");
        String path2 = "/../";
        String expected2 = "/";
        
        String result2a = solution.simplifyPath(path2);
        System.out.println("Parent dir: \"" + result2a + "\" - " + 
                         (result2a.equals(expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Multiple slashes
        System.out.println("\nTest 3: Multiple slashes");
        String path3 = "/home//foo/";
        String expected3 = "/home/foo";
        
        String result3a = solution.simplifyPath(path3);
        System.out.println("Multiple slashes: \"" + result3a + "\" - " + 
                         (result3a.equals(expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Complex path with mixed operations
        System.out.println("\nTest 4: Complex path with mixed operations");
        String path4 = "/a/./b/../../c/";
        String expected4 = "/c";
        
        String result4a = solution.simplifyPath(path4);
        System.out.println("Complex path: \"" + result4a + "\" - " + 
                         (result4a.equals(expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: Multiple parent directories
        System.out.println("\nTest 5: Multiple parent directories");
        String path5 = "/a/../../b/";
        String expected5 = "/b";
        
        String result5a = solution.simplifyPath(path5);
        System.out.println("Multiple parents: \"" + result5a + "\" - " + 
                         (result5a.equals(expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Root directory
        System.out.println("\nTest 6: Root directory");
        String path6 = "/";
        String expected6 = "/";
        
        String result6a = solution.simplifyPath(path6);
        System.out.println("Root: \"" + result6a + "\" - " + 
                         (result6a.equals(expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Path with three dots (treated as directory)
        System.out.println("\nTest 7: Three dots (directory name)");
        String path7 = "/.../a/";
        String expected7 = "/.../a";
        
        String result7a = solution.simplifyPath(path7);
        System.out.println("Three dots: \"" + result7a + "\" - " + 
                         (result7a.equals(expected7) ? "PASSED" : "FAILED"));
        
        // Test case 8: Mixed with valid directories
        System.out.println("\nTest 8: Mixed with valid directories");
        String path8 = "/a/b/c/../././../x/";
        String expected8 = "/a/x";
        
        String result8a = solution.simplifyPath(path8);
        System.out.println("Mixed: \"" + result8a + "\" - " + 
                         (result8a.equals(expected8) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Simple path performance:");
        System.out.println("  Stack: " + time1a + " ns");
        System.out.println("  Deque: " + time1b + " ns");
        System.out.println("  Manual: " + time1c + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        String largePath = generateLargePath(1000);
        
        startTime = System.nanoTime();
        String result10a = solution.simplifyPath(largePath);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result10b = solution.simplifyPathArrayStack(largePath);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        String result10c = solution.simplifyPathDeque(largePath);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large path (1000 components):");
        System.out.println("  Stack: " + time10a + " ns");
        System.out.println("  Array Stack: " + time10b + " ns");
        System.out.println("  Deque: " + time10c + " ns");
        
        // Verify all approaches produce the same result
        boolean allEqual = result10a.equals(result10b) && result10a.equals(result10c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Edge case: Empty path (constraint says length >= 1)
        System.out.println("\nTest 11: Complex edge case");
        String path11 = "/a/./b/./c/./d/../../../../";
        String expected11 = "/";
        
        String result11a = solution.simplifyPath(path11);
        System.out.println("Complex edge: \"" + result11a + "\" - " + 
                         (result11a.equals(expected11) ? "PASSED" : "FAILED"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        
        System.out.println("\nUnix Path Rules:");
        System.out.println("- '/' separates directories");
        System.out.println("- '.' means current directory");
        System.out.println("- '..' means parent directory");
        System.out.println("- Multiple '/' are equivalent to single '/'");
        System.out.println("- Path always starts with '/' (absolute path)");
        
        System.out.println("\nStack Approach:");
        System.out.println("1. Split path by '/' to get components");
        System.out.println("2. Initialize empty stack");
        System.out.println("3. For each component:");
        System.out.println("   - Skip if empty or '.'");
        System.out.println("   - If '..': pop from stack (if not empty)");
        System.out.println("   - Else: push to stack (valid directory)");
        System.out.println("4. Build result by joining stack with '/'");
        System.out.println("5. Handle empty stack case (return '/')");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStack with Split Approach:");
        System.out.println("┌────────────┬────────────┬──────────────┐");
        System.out.println("│ Operation  │ Time       │ Space        │");
        System.out.println("├────────────┼────────────┼──────────────┤");
        System.out.println("│ Split      │ O(n)       │ O(n)         │");
        System.out.println("│ Stack Ops  │ O(n)       │ O(n)         │");
        System.out.println("│ Join       │ O(n)       │ O(n)         │");
        System.out.println("│ Total      │ O(n)       │ O(n)         │");
        System.out.println("└────────────┴────────────┴──────────────┘");
        
        System.out.println("\nComparison of Approaches:");
        System.out.println("┌──────────────────┬────────────┬─────────────────┐");
        System.out.println("│ Approach         │ Time       │ Space           │");
        System.out.println("├──────────────────┼────────────┼─────────────────┤");
        System.out.println("│ Stack + Split    │ O(n)       │ O(n)            │");
        System.out.println("│ Deque            │ O(n)       │ O(n)            │");
        System.out.println("│ ArrayList        │ O(n)       │ O(n)            │");
        System.out.println("│ Manual Processing│ O(n)       │ O(n)            │");
        System.out.println("│ Array Stack      │ O(n)       │ O(n)            │");
        System.out.println("└──────────────────┴────────────┴─────────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with Stack + Split Approach:");
        System.out.println("   - Most intuitive and easy to explain");
        System.out.println("   - Handles all edge cases correctly");
        System.out.println("   - Clean and readable code");
        
        System.out.println("\n2. Key Points to Explain:");
        System.out.println("   - Why stack is appropriate (directory hierarchy)");
        System.out.println("   - How split handles multiple slashes");
        System.out.println("   - Special cases: '.', '..', empty components");
        System.out.println("   - Time and space complexity analysis");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - Root directory '/'");
        System.out.println("   - Multiple consecutive slashes");
        System.out.println("   - Multiple '..' operations");
        System.out.println("   - Three dots '...' (valid directory name)");
        System.out.println("   - Trailing slashes");
        
        System.out.println("\n4. Discuss Alternative Approaches:");
        System.out.println("   - Manual character processing");
        System.out.println("   - Different data structures (Deque, ArrayList)");
        System.out.println("   - Space optimizations");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - File system path normalization");
        System.out.println("   - Web URL path resolution");
        System.out.println("   - Command line tool development");
        System.out.println("   - Operating system internals");
        
        System.out.println("\nAll tests completed successfully!");
    }
    
    /**
     * Helper method to generate large path for performance testing
     */
    private static String generateLargePath(int numComponents) {
        StringBuilder path = new StringBuilder("/");
        Random random = new Random(42);
        String[] operations = {"dir", ".", ".."};
        
        for (int i = 0; i < numComponents; i++) {
            int op = random.nextInt(10);
            if (op < 6) {
                // Regular directory (60% probability)
                path.append("dir").append(i).append("/");
            } else if (op < 8) {
                // Current directory (20% probability)
                path.append("./");
            } else {
                // Parent directory (20% probability)
                path.append("../");
            }
        }
        
        return path.toString();
    }
}
