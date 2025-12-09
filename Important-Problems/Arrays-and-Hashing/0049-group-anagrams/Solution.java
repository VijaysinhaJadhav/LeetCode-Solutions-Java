/**
 * 49. Group Anagrams
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, 
 * typically using all the original letters exactly once.
 * 
 * Key Insights:
 * 1. Anagrams have the same characters when sorted
 * 2. We can use sorted string as key in HashMap to group anagrams
 * 3. Alternative: use character frequency counts as key
 * 4. Alternative: use prime number product as key (unique for each anagram group)
 * 
 * Approach:
 * 1. Create HashMap with key as sorted string and value as list of anagrams
 * 2. For each string, sort it to get the key
 * 3. Add original string to the list for that key
 * 4. Return all values from the HashMap
 * 
 * Time Complexity: O(n * k log k) where n is strings count, k is max string length
 * Space Complexity: O(n * k) for storing the result
 * 
 * Tags: Hash Table, String, Sorting
 */

import java.util.*;
import java.util.stream.Collectors;

class Solution {
    /**
     * Approach 1: Sorted String Key (Most Common)
     * Sort each string and use as key in HashMap
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        Map<String, List<String>> anagramMap = new HashMap<>();
        
        for (String str : strs) {
            // Convert string to char array, sort, and convert back to string
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String sortedKey = new String(charArray);
            
            // Add to appropriate group
            anagramMap.putIfAbsent(sortedKey, new ArrayList<>());
            anagramMap.get(sortedKey).add(str);
        }
        
        return new ArrayList<>(anagramMap.values());
    }
    
    /**
     * Approach 2: Frequency Count Key
     * Use character frequency counts as key instead of sorting
     * Better for long strings where sorting is expensive
     */
    public List<List<String>> groupAnagramsFrequency(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        Map<String, List<String>> anagramMap = new HashMap<>();
        
        for (String str : strs) {
            int[] frequency = new int[26];
            
            // Count character frequencies
            for (char c : str.toCharArray()) {
                frequency[c - 'a']++;
            }
            
            // Build frequency key like "#1#2#0#..." 
            StringBuilder keyBuilder = new StringBuilder();
            for (int count : frequency) {
                keyBuilder.append('#').append(count);
            }
            String frequencyKey = keyBuilder.toString();
            
            // Add to appropriate group
            anagramMap.putIfAbsent(frequencyKey, new ArrayList<>());
            anagramMap.get(frequencyKey).add(str);
        }
        
        return new ArrayList<>(anagramMap.values());
    }
    
    /**
     * Approach 3: Prime Product Key (Advanced)
     * Assign prime numbers to each letter and multiply
     * Creates unique product for each anagram group
     * Risk of integer overflow for very long strings
     */
    public List<List<String>> groupAnagramsPrime(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        // First 26 prime numbers for a-z
        int[] primes = {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 
            43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101
        };
        
        Map<Long, List<String>> anagramMap = new HashMap<>();
        
        for (String str : strs) {
            long product = 1;
            
            // Calculate product of primes for each character
            for (char c : str.toCharArray()) {
                product *= primes[c - 'a'];
            }
            
            // Add to appropriate group
            anagramMap.putIfAbsent(product, new ArrayList<>());
            anagramMap.get(product).add(str);
        }
        
        return new ArrayList<>(anagramMap.values());
    }
    
    /**
     * Approach 4: Using Streams (Concise but less efficient)
     * Functional programming approach using Java Streams
     */
    public List<List<String>> groupAnagramsStreams(String[] strs) {
        return new ArrayList<>(
            Arrays.stream(strs)
                .collect(Collectors.groupingBy(str -> {
                    char[] chars = str.toCharArray();
                    Arrays.sort(chars);
                    return new String(chars);
                }))
                .values()
        );
    }
    
    /**
     * Helper method to sort lists for consistent testing
     */
    private List<List<String>> sortResult(List<List<String>> result) {
        for (List<String> group : result) {
            Collections.sort(group);
        }
        result.sort((a, b) -> {
            if (a.isEmpty() && b.isEmpty()) return 0;
            if (a.isEmpty()) return -1;
            if (b.isEmpty()) return 1;
            return a.get(0).compareTo(b.get(0));
        });
        return result;
    }
    
    /**
     * Helper method to compare two results ignoring order
     */
    private boolean areResultsEqual(List<List<String>> result1, List<List<String>> result2) {
        if (result1.size() != result2.size()) return false;
        
        List<List<String>> sorted1 = sortResult(new ArrayList<>(result1));
        List<List<String>> sorted2 = sortResult(new ArrayList<>(result2));
        
        for (int i = 0; i < sorted1.size(); i++) {
            List<String> group1 = sorted1.get(i);
            List<String> group2 = sorted2.get(i);
            
            if (group1.size() != group2.size()) return false;
            for (int j = 0; j < group1.size(); j++) {
                if (!group1.get(j).equals(group2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Helper method to print test results
     */
    private static void printTestResult(String testName, boolean passed) {
        System.out.println(testName + ": " + (passed ? "PASSED" : "FAILED"));
    }
    
    /**
     * Helper method to print performance results
     */
    private static void printPerformanceResult(String approach, long timeNs, int groupCount) {
        System.out.println("  " + approach + ": " + timeNs + " ns, Groups: " + groupCount);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Group Anagrams Solution:");
        System.out.println("=================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example");
        String[] strs1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> expected1 = Arrays.asList(
            Arrays.asList("bat"),
            Arrays.asList("nat", "tan"),
            Arrays.asList("ate", "eat", "tea")
        );
        
        long startTime = System.nanoTime();
        List<List<String>> result1a = solution.groupAnagrams(strs1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1b = solution.groupAnagramsFrequency(strs1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1c = solution.groupAnagramsPrime(strs1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result1d = solution.groupAnagramsStreams(strs1);
        long time1d = System.nanoTime() - startTime;
        
        printTestResult("Sorted key approach", solution.areResultsEqual(expected1, result1a));
        printTestResult("Frequency key approach", solution.areResultsEqual(expected1, result1b));
        printTestResult("Prime product approach", solution.areResultsEqual(expected1, result1c));
        printTestResult("Streams approach", solution.areResultsEqual(expected1, result1d));
        
        System.out.println("Performance for Test 1:");
        printPerformanceResult("Sorted key", time1a, result1a.size());
        printPerformanceResult("Frequency key", time1b, result1b.size());
        printPerformanceResult("Prime product", time1c, result1c.size());
        printPerformanceResult("Streams", time1d, result1d.size());
        
        // Test case 2: Empty string
        System.out.println("\nTest 2: Empty string");
        String[] strs2 = {""};
        List<List<String>> expected2 = Arrays.asList(Arrays.asList(""));
        
        List<List<String>> result2a = solution.groupAnagrams(strs2);
        List<List<String>> result2b = solution.groupAnagramsFrequency(strs2);
        List<List<String>> result2c = solution.groupAnagramsPrime(strs2);
        List<List<String>> result2d = solution.groupAnagramsStreams(strs2);
        
        printTestResult("Sorted key approach", solution.areResultsEqual(expected2, result2a));
        printTestResult("Frequency key approach", solution.areResultsEqual(expected2, result2b));
        printTestResult("Prime product approach", solution.areResultsEqual(expected2, result2c));
        printTestResult("Streams approach", solution.areResultsEqual(expected2, result2d));
        
        // Test case 3: Single character
        System.out.println("\nTest 3: Single character");
        String[] strs3 = {"a"};
        List<List<String>> expected3 = Arrays.asList(Arrays.asList("a"));
        
        List<List<String>> result3a = solution.groupAnagrams(strs3);
        List<List<String>> result3b = solution.groupAnagramsFrequency(strs3);
        List<List<String>> result3c = solution.groupAnagramsPrime(strs3);
        List<List<String>> result3d = solution.groupAnagramsStreams(strs3);
        
        printTestResult("Sorted key approach", solution.areResultsEqual(expected3, result3a));
        printTestResult("Frequency key approach", solution.areResultsEqual(expected3, result3b));
        printTestResult("Prime product approach", solution.areResultsEqual(expected3, result3c));
        printTestResult("Streams approach", solution.areResultsEqual(expected3, result3d));
        
        // Test case 4: Multiple empty strings
        System.out.println("\nTest 4: Multiple empty strings");
        String[] strs4 = {"", "", ""};
        List<List<String>> expected4 = Arrays.asList(Arrays.asList("", "", ""));
        
        List<List<String>> result4a = solution.groupAnagrams(strs4);
        List<List<String>> result4b = solution.groupAnagramsFrequency(strs4);
        List<List<String>> result4c = solution.groupAnagramsPrime(strs4);
        List<List<String>> result4d = solution.groupAnagramsStreams(strs4);
        
        printTestResult("Sorted key approach", solution.areResultsEqual(expected4, result4a));
        printTestResult("Frequency key approach", solution.areResultsEqual(expected4, result4b));
        printTestResult("Prime product approach", solution.areResultsEqual(expected4, result4c));
        printTestResult("Streams approach", solution.areResultsEqual(expected4, result4d));
        
        // Test case 5: No anagrams
        System.out.println("\nTest 5: No anagrams");
        String[] strs5 = {"abc", "def", "ghi"};
        List<List<String>> expected5 = Arrays.asList(
            Arrays.asList("abc"),
            Arrays.asList("def"),
            Arrays.asList("ghi")
        );
        
        List<List<String>> result5a = solution.groupAnagrams(strs5);
        List<List<String>> result5b = solution.groupAnagramsFrequency(strs5);
        List<List<String>> result5c = solution.groupAnagramsPrime(strs5);
        List<List<String>> result5d = solution.groupAnagramsStreams(strs5);
        
        printTestResult("Sorted key approach", solution.areResultsEqual(expected5, result5a));
        printTestResult("Frequency key approach", solution.areResultsEqual(expected5, result5b));
        printTestResult("Prime product approach", solution.areResultsEqual(expected5, result5c));
        printTestResult("Streams approach", solution.areResultsEqual(expected5, result5d));
        
        // Test case 6: Large input performance test
        System.out.println("\nTest 6: Large input performance comparison");
        String[] strs6 = new String[1000];
        Random random = new Random(42); // Fixed seed for reproducible results
        
        // Generate random strings of length 10-20
        for (int i = 0; i < strs6.length; i++) {
            int length = 10 + random.nextInt(11);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < length; j++) {
                sb.append((char)('a' + random.nextInt(26)));
            }
            strs6[i] = sb.toString();
        }
        
        // Add some anagrams by shuffling existing strings
        for (int i = 0; i < 100; i++) {
            char[] chars = strs6[i].toCharArray();
            // Shuffle the characters to create anagrams
            for (int j = chars.length - 1; j > 0; j--) {
                int index = random.nextInt(j + 1);
                char temp = chars[index];
                chars[index] = chars[j];
                chars[j] = temp;
            }
            strs6[100 + i] = new String(chars);
        }
        
        startTime = System.nanoTime();
        List<List<String>> result6a = solution.groupAnagrams(strs6);
        long largeTime1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result6b = solution.groupAnagramsFrequency(strs6);
        long largeTime2 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        List<List<String>> result6c = solution.groupAnagramsPrime(strs6);
        long largeTime3 = System.nanoTime() - startTime;
        
        System.out.println("Large input (1000 strings, length 10-20):");
        printPerformanceResult("Sorted key", largeTime1, result6a.size());
        printPerformanceResult("Frequency key", largeTime2, result6b.size());
        printPerformanceResult("Prime product", largeTime3, result6c.size());
        
        // Verify all approaches produce same results
        boolean consistent1 = solution.areResultsEqual(result6a, result6b);
        boolean consistent2 = solution.areResultsEqual(result6a, result6c);
        System.out.println("  Results consistent: " + (consistent1 && consistent2));
        
        // Complexity analysis and approach comparison
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE APPROACH ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Sorted String Key Approach (RECOMMENDED for interviews):");
        System.out.println("   Time: O(n * k log k) - n strings, k max length (sorting each string)");
        System.out.println("   Space: O(n * k) - storing all strings in result");
        System.out.println("   Pros:");
        System.out.println("     - Simple and intuitive");
        System.out.println("     - Easy to implement under pressure");
        System.out.println("     - Works reliably for all cases");
        System.out.println("   Cons:");
        System.out.println("     - Sorting can be expensive for long strings");
        System.out.println("   Best for: Interview settings, general use cases");
        
        System.out.println("\n2. Frequency Count Key Approach:");
        System.out.println("   Time: O(n * k) - counting frequencies is O(k) per string");
        System.out.println("   Space: O(n * k) - same as sorted approach");
        System.out.println("   Pros:");
        System.out.println("     - Better for long strings (avoids sorting)");
        System.out.println("     - Consistent performance regardless of string length");
        System.out.println("   Cons:");
        System.out.println("     - More complex key generation");
        System.out.println("     - Key string can be long for large alphabets");
        System.out.println("   Best for: Long strings, performance-critical applications");
        
        System.out.println("\n3. Prime Product Key Approach (ADVANCED):");
        System.out.println("   Time: O(n * k) - similar to frequency counting");
        System.out.println("   Space: O(n * k) - same as others");
        System.out.println("   Pros:");
        System.out.println("     - Mathematical and elegant solution");
        System.out.println("     - Fast key computation (multiplication)");
        System.out.println("   Cons:");
        System.out.println("     - Risk of integer overflow for very long strings");
        System.out.println("     - Requires knowledge of prime number properties");
        System.out.println("     - Limited to certain character sets");
        System.out.println("   Best for: Demonstrating advanced knowledge, constrained scenarios");
        
        System.out.println("\n4. Streams Approach (CONCISE):");
        System.out.println("   Time: O(n * k log k) - same as sorted approach");
        System.out.println("   Space: O(n * k) - same as others");
        System.out.println("   Pros:");
        System.out.println("     - Very concise code (functional programming)");
        System.out.println("     - Readable for those familiar with streams");
        System.out.println("   Cons:");
        System.out.println("     - Slightly slower due to stream overhead");
        System.out.println("     - Less familiar to some interviewers");
        System.out.println("   Best for: Code golf, functional programming contexts");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Sorted Key approach - it's the most expected solution");
        System.out.println("2. Mention Frequency Count approach as optimization for long strings");
        System.out.println("3. Discuss Prime Product approach to show depth of knowledge");
        System.out.println("4. Handle edge cases: empty array, empty strings, single characters");
        System.out.println("5. Discuss time/space complexity trade-offs");
        System.out.println("=".repeat(80));
        
        System.out.println("\nAll tests completed!");
    }
}
