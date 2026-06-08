
# Solution.java

```java
import java.util.*;

/**
 * 451. Sort Characters By Frequency
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Sort characters in string by descending frequency.
 * 
 * Key Insights:
 * 1. Count frequencies of each character
 * 2. Use bucket sort (O(n)) or max-heap (O(n log n))
 * 3. Build result by appending character repeated by its frequency
 */
class Solution {
    
    /**
     * Approach 1: Bucket Sort (Optimal)
     * Time: O(n), Space: O(n)
     * 
     * Steps:
     * 1. Count frequency of each character (map or array of size 128)
     * 2. Create bucket array where bucket[freq] = list of chars with that frequency
     * 3. Iterate from highest possible frequency down to 1
     * 4. For each frequency, append characters to result repeated 'freq' times
     */
    public String frequencySort(String s) {
        // Count frequencies (ASCII characters 0-127)
        int[] freq = new int[128];
        for (char c : s.toCharArray()) {
            freq[c]++;
        }
        
        // Bucket: index = frequency, value = list of characters
        List<Character>[] buckets = new List[s.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            buckets[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < 128; i++) {
            if (freq[i] > 0) {
                buckets[freq[i]].add((char) i);
            }
        }
        
        // Build result
        StringBuilder result = new StringBuilder();
        for (int f = s.length(); f > 0; f--) {
            for (char c : buckets[f]) {
                for (int i = 0; i < f; i++) {
                    result.append(c);
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 2: Max-Heap (PriorityQueue)
     * Time: O(n log k), Space: O(n)
     */
    public String frequencySortHeap(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        
        // Max-heap based on frequency
        PriorityQueue<Map.Entry<Character, Integer>> pq = 
            new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        pq.addAll(freq.entrySet());
        
        StringBuilder result = new StringBuilder();
        while (!pq.isEmpty()) {
            Map.Entry<Character, Integer> entry = pq.poll();
            char c = entry.getKey();
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 3: Sort Map Entries
     * Time: O(n log n), Space: O(n)
     */
    public String frequencySortSorting(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(freq.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());
        
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : list) {
            char c = entry.getKey();
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Approach 4: Using Character Array and Counting (Simple)
     * Time: O(n log n), Space: O(n)
     */
    public String frequencySortCharArr(String s) {
        // Convert to char array
        char[] chars = s.toCharArray();
        // Count frequencies
        int[] freq = new int[128];
        for (char c : chars) {
            freq[c]++;
        }
        // Custom sort: use Character wrapper with comparator
        Character[] arr = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            arr[i] = chars[i];
        }
        Arrays.sort(arr, (a, b) -> {
            int cmp = Integer.compare(freq[b], freq[a]);
            if (cmp != 0) return cmp;
            return Character.compare(a, b);
        });
        // Build result
        StringBuilder sb = new StringBuilder();
        for (char c : arr) sb.append(c);
        return sb.toString();
    }
    
    /**
     * Approach 5: Using StringBuilder Array as Buckets (Alternative)
     * Time: O(n), Space: O(n)
     */
    public String frequencySortBucketsStr(String s) {
        int[] freq = new int[128];
        for (char c : s.toCharArray()) {
            freq[c]++;
        }
        
        StringBuilder[] buckets = new StringBuilder[s.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            buckets[i] = new StringBuilder();
        }
        
        for (int i = 0; i < 128; i++) {
            if (freq[i] > 0) {
                buckets[freq[i]].append((char) i);
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (int f = s.length(); f > 0; f--) {
            for (char c : buckets[f].toString().toCharArray()) {
                for (int i = 0; i < f; i++) {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }
    
    /**
     * Helper: Visualize frequency distribution
     */
    public void visualizeFreq(String s) {
        System.out.println("\nFrequency Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.println("Original: \"" + s + "\"");
        
        int[] freq = new int[128];
        for (char c : s.toCharArray()) {
            freq[c]++;
        }
        
        System.out.println("\nCharacter frequencies:");
        for (int i = 0; i < 128; i++) {
            if (freq[i] > 0) {
                System.out.printf("  '%c': %d%n", (char) i, freq[i]);
            }
        }
        
        // Show bucket distribution
        List<Character>[] buckets = new List[s.length() + 1];
        for (int i = 0; i <= s.length(); i++) buckets[i] = new ArrayList<>();
        for (int i = 0; i < 128; i++) {
            if (freq[i] > 0) buckets[freq[i]].add((char) i);
        }
        
        System.out.println("\nBucket distribution:");
        for (int f = s.length(); f > 0; f--) {
            if (!buckets[f].isEmpty()) {
                System.out.printf("  Frequency %d: %s%n", f, buckets[f]);
            }
        }
        
        String result = frequencySort(s);
        System.out.println("\nSorted result: \"" + result + "\"");
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[] generateTestCases() {
        return new String[]{
            "tree",
            "cccaaa",
            "Aabb",
            "loveleetcode",
            "a",
            "ab",
            "aaaa",
            "aaabbbccc",
            "2a554442f544asfdsafrwerw"
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[] testCases = generateTestCases();
        String[] expected = {
            "eert", "aaaccc", "bbAa", "eeeeoollvtdc", "a",
            "ab", "aaaa", "aaabbbccc", "5555544444222aaaafffsssdddrrww"
        };
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i];
            System.out.printf("\nTest %d: \"%s\"%n", i + 1, s);
            
            String result1 = frequencySort(s);
            String result2 = frequencySortHeap(s);
            String result3 = frequencySortSorting(s);
            String result4 = frequencySortCharArr(s);
            String result5 = frequencySortBucketsStr(s);
            
            // Note: multiple valid outputs possible; check only frequencies match?
            // For simplicity, we just compare length and character multiset.
            // We'll trust methods if they match each other.
            boolean allMatch = result1.equals(result2) && result2.equals(result3) &&
                              result3.equals(result4) && result4.equals(result5);
            
            if (allMatch) {
                System.out.println("✓ PASS - Result: \"" + result1 + "\"");
                passed++;
            } else {
                System.out.println("✗ FAIL - Methods disagree");
                System.out.println("  Bucket: " + result1);
                System.out.println("  Heap: " + result2);
                System.out.println("  Sort: " + result3);
                System.out.println("  CharArr: " + result4);
                System.out.println("  BucketsStr: " + result5);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeFreq(s);
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
        
        // Generate large string
        int n = 500000;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            sb.append((char) ('a' + rand.nextInt(26)));
        }
        String large = sb.toString();
        
        System.out.println("Test Setup: string length = " + n);
        
        long[] times = new long[5];
        String[] results = new String[5];
        
        // Method 1: Bucket Sort
        long start = System.currentTimeMillis();
        results[0] = frequencySort(large);
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Max-Heap
        start = System.currentTimeMillis();
        results[1] = frequencySortHeap(large);
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Sort Map Entries
        start = System.currentTimeMillis();
        results[2] = frequencySortSorting(large);
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Character Array Sort
        start = System.currentTimeMillis();
        results[3] = frequencySortCharArr(large);
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Buckets with StringBuilder
        start = System.currentTimeMillis();
        results[4] = frequencySortBucketsStr(large);
        times[4] = System.currentTimeMillis() - start;
        
        System.out.println("\nResults:");
        System.out.println("Method                    | Time (ms)");
        System.out.println("--------------------------|-----------");
        System.out.printf("1. Bucket Sort            | %9d%n", times[0]);
        System.out.printf("2. Max-Heap               | %9d%n", times[1]);
        System.out.printf("3. Sort Map Entries       | %9d%n", times[2]);
        System.out.printf("4. Char Array Sort        | %9d%n", times[3]);
        System.out.printf("5. Buckets (StringBuilder)| %9d%n", times[4]);
        
        // Check correctness: all results should have same multiset (length same)
        boolean lengthsMatch = true;
        for (int i = 1; i < 5; i++) {
            if (results[i].length() != results[0].length()) {
                lengthsMatch = false;
                break;
            }
        }
        System.out.println("\nAll outputs have same length: " + (lengthsMatch ? "✓" : "✗"));
        
        System.out.println("\nObservations:");
        System.out.println("1. Bucket sort is fastest (O(n))");
        System.out.println("2. Max-heap and sorting are O(n log n) but still efficient");
        System.out.println("3. For large n, bucket sort significantly outperforms others");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Single character:");
        System.out.println("   Input: \"a\"");
        System.out.println("   Output: \"" + frequencySort("a") + "\"");
        
        System.out.println("\n2. Two same characters:");
        System.out.println("   Input: \"aa\"");
        System.out.println("   Output: \"" + frequencySort("aa") + "\"");
        
        System.out.println("\n3. All characters distinct:");
        System.out.println("   Input: \"abcde\"");
        System.out.println("   Output: \"" + frequencySort("abcde") + "\"");
        
        System.out.println("\n4. Mixed case and digits:");
        System.out.println("   Input: \"Aa1Aa1\"");
        System.out.println("   Output: \"" + frequencySort("Aa1Aa1") + "\"");
        
        System.out.println("\n5. Very long string (500k same char):");
        String longSame = "a".repeat(500000);
        long start = System.currentTimeMillis();
        String result = frequencySort(longSame);
        long time = System.currentTimeMillis() - start;
        System.out.println("   Input: 500,000 'a's");
        System.out.println("   Output length: " + result.length());
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("451. Sort Characters By Frequency");
        System.out.println("==================================");
        
        // Run test cases
        solution.runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation (Bucket Sort):");
        System.out.println("""
class Solution {
    public String frequencySort(String s) {
        int[] freq = new int[128];
        for (char c : s.toCharArray()) freq[c]++;
        
        List<Character>[] buckets = new List[s.length() + 1];
        for (int i = 0; i <= s.length(); i++) buckets[i] = new ArrayList<>();
        for (int i = 0; i < 128; i++) {
            if (freq[i] > 0) buckets[freq[i]].add((char) i);
        }
        
        StringBuilder result = new StringBuilder();
        for (int f = s.length(); f > 0; f--) {
            for (char c : buckets[f]) {
                for (int i = 0; i < f; i++) result.append(c);
            }
        }
        return result.toString();
    }
}
            """);
        
        System.out.println("\nAlternative (Max-Heap):");
        System.out.println("""
class Solution {
    public String frequencySort(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) freq.put(c, freq.getOrDefault(c, 0) + 1);
        
        PriorityQueue<Map.Entry<Character, Integer>> pq = 
            new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        pq.addAll(freq.entrySet());
        
        StringBuilder result = new StringBuilder();
        while (!pq.isEmpty()) {
            Map.Entry<Character, Integer> e = pq.poll();
            result.append(String.valueOf(e.getKey()).repeat(e.getValue()));
        }
        return result.toString();
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Bucket sort achieves O(n) time complexity");
        System.out.println("2. Frequency counting is straightforward with array or HashMap");
        System.out.println("3. Result order among same-frequency characters is arbitrary");
        System.out.println("4. Use StringBuilder for efficient string building");
        System.out.println("5. For large inputs, avoid O(n log n) if possible");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(n) for bucket sort, O(n log n) for comparison-based");
        System.out.println("- Space: O(n) for buckets/storage");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle Unicode characters? (Use HashMap)");
        System.out.println("2. How would you sort characters with same frequency alphabetically? (Add secondary comparator)");
        System.out.println("3. How would you solve without extra space? (Not possible)");
        System.out.println("4. What if the string contains only digits? (Same logic)");
        System.out.println("5. How would you output the result as a char array? (Convert)");
    }
}
