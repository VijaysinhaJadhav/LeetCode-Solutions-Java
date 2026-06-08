
## Solution.java

```java
/**
 * 1832. Check if the Sentence Is Pangram
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Determine if a sentence contains every letter of the English alphabet.
 * 
 * Key Insights:
 * 1. Use a boolean array of size 26 to mark seen letters.
 * 2. Alternatively, use a bitmask (int) to track letters.
 * 3. Return true if all 26 letters are present.
 */
class Solution {
    
    /**
     * Approach 1: Boolean Array (Recommended)
     * Time: O(n), Space: O(1)
     */
    public boolean checkIfPangram(String sentence) {
        boolean[] seen = new boolean[26];
        for (char c : sentence.toCharArray()) {
            seen[c - 'a'] = true;
        }
        for (boolean b : seen) {
            if (!b) return false;
        }
        return true;
    }
    
    /**
     * Approach 2: Bitmask (Most Space-Efficient)
     * Time: O(n), Space: O(1)
     */
    public boolean checkIfPangramBitmask(String sentence) {
        int mask = 0;
        for (char c : sentence.toCharArray()) {
            mask |= 1 << (c - 'a');
        }
        return mask == (1 << 26) - 1;
    }
    
    /**
     * Approach 3: HashSet (Simplest)
     * Time: O(n), Space: O(26) ≈ O(1)
     */
    public boolean checkIfPangramSet(String sentence) {
        java.util.Set<Character> set = new java.util.HashSet<>();
        for (char c : sentence.toCharArray()) {
            set.add(c);
        }
        return set.size() == 26;
    }
    
    /**
     * Helper: Visualize the process
     */
    public void visualizePangram(String sentence) {
        System.out.println("\nPangram Check Visualization:");
        System.out.println("=".repeat(60));
        System.out.println("Sentence: \"" + sentence + "\"");
        
        boolean[] seen = new boolean[26];
        int count = 0;
        for (char c : sentence.toCharArray()) {
            int idx = c - 'a';
            if (!seen[idx]) {
                seen[idx] = true;
                count++;
                System.out.printf("Found '%c' → %d letters seen so far%n", c, count);
                if (count == 26) {
                    System.out.println("All 26 letters found! → true");
                    return;
                }
            }
        }
        System.out.println("Only " + count + " unique letters found → false");
    }
    
    /**
     * Helper: Run test cases
     */
    public void runTestCases() {
        System.out.println("\nRunning Test Cases:");
        System.out.println("=".repeat(50));
        
        String[] tests = {
            "thequickbrownfoxjumpsoverthelazydog",
            "leetcode",
            "a",
            "abcdefghijklmnopqrstuvwxyz",
            "packmyboxwithfivedozenliquorjugs"
        };
        boolean[] expected = {true, false, false, true, true};
        
        for (int i = 0; i < tests.length; i++) {
            boolean res = checkIfPangram(tests[i]);
            System.out.printf("Test %d: \"%s\" → %b (expected %b) %s%n",
                i+1, tests[i], res, expected[i], res == expected[i] ? "✓" : "✗");
        }
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println("1832. Check if the Sentence Is Pangram");
        System.out.println("=====================================");
        sol.visualizePangram("thequickbrownfoxjumpsoverthelazydog");
        sol.runTestCases();
    }
}
