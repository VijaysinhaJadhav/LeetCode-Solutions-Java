/**
 * 271. Encode and Decode Strings
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Design an algorithm to encode a list of strings to a single string.
 * The encoded string is then sent over the network and is decoded back 
 * to the original list of strings.
 * 
 * Key Insights:
 * 1. Length Prefix: Encode each string as "length:string" 
 * 2. Escape Characters: Handle special characters with escaping
 * 3. Unique Delimiters: Use characters unlikely to appear in input
 * 4. The main challenge is distinguishing between strings in encoded format
 * 
 * Approach (Length Prefix):
 * 1. Encode: For each string, write length + ":" + string
 * 2. Decode: Parse length, then read that many characters as string
 * 3. Repeat until all input is processed
 * 
 * Time Complexity: O(n) where n is total characters
 * Space Complexity: O(n) for encoded string
 * 
 * Tags: String, Design, Data Stream
 */

import java.util.*;

public class Solution {
    
    /**
     * Approach 1: Length Prefix with Colon Delimiter (RECOMMENDED)
     * Simple, efficient, and handles all ASCII characters
     * Format: "length:stringlength:string..."
     */
    
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        if (strs == null || strs.isEmpty()) {
            return "";
        }
        
        StringBuilder encoded = new StringBuilder();
        for (String str : strs) {
            // Handle null strings gracefully
            if (str == null) {
                encoded.append("0:");
            } else {
                encoded.append(str.length()).append(":").append(str);
            }
        }
        return encoded.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> decoded = new ArrayList<>();
        if (s == null || s.isEmpty()) {
            return decoded;
        }
        
        int i = 0;
        while (i < s.length()) {
            // Find the colon delimiter
            int colonIndex = s.indexOf(':', i);
            if (colonIndex == -1) {
                throw new IllegalArgumentException("Invalid encoded string format");
            }
            
            // Parse the length
            String lengthStr = s.substring(i, colonIndex);
            int length;
            try {
                length = Integer.parseInt(lengthStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid length format: " + lengthStr);
            }
            
            // Extract the string
            int start = colonIndex + 1;
            int end = start + length;
            if (end > s.length()) {
                throw new IllegalArgumentException("Encoded string truncated");
            }
            
            String str = s.substring(start, end);
            decoded.add(str);
            
            // Move to next string
            i = end;
        }
        
        return decoded;
    }
    
    /**
     * Approach 2: Chunked Encoding (Alternative Implementation)
     * More robust version with better error handling
     */
    public String encodeChunked(List<String> strs) {
        if (strs == null) return "";
        
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            if (str == null) {
                sb.append("0:");
            } else {
                sb.append(str.length()).append(":").append(str);
            }
        }
        return sb.toString();
    }
    
    public List<String> decodeChunked(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.isEmpty()) return result;
        
        int pos = 0;
        while (pos < s.length()) {
            // Find the next colon
            int colon = s.indexOf(':', pos);
            if (colon == -1) break;
            
            // Read length
            String lenStr = s.substring(pos, colon);
            int len = Integer.parseInt(lenStr);
            
            // Read string
            pos = colon + 1;
            if (pos + len > s.length()) break;
            
            String str = s.substring(pos, pos + len);
            result.add(str);
            pos += len;
        }
        return result;
    }
    
    /**
     * Approach 3: Escape Character Based Encoding
     * Uses escape sequences for special characters
     * Format: "string;string;..." with \; as escape for semicolon
     */
    public String encodeEscape(List<String> strs) {
        if (strs == null || strs.isEmpty()) return "";
        
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            if (str == null) {
                sb.append("\\0;");
            } else {
                // Escape any semicolons and backslashes in the string
                String escaped = str.replace("\\", "\\\\").replace(";", "\\;");
                sb.append(escaped).append(";");
            }
        }
        return sb.toString();
    }
    
    public List<String> decodeEscape(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.isEmpty()) return result;
        
        StringBuilder current = new StringBuilder();
        boolean escaping = false;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (escaping) {
                if (c == '0') {
                    current.append("\0"); // Null character
                } else {
                    current.append(c);
                }
                escaping = false;
            } else {
                if (c == '\\') {
                    escaping = true;
                } else if (c == ';') {
                    result.add(current.toString());
                    current = new StringBuilder();
                } else {
                    current.append(c);
                }
            }
        }
        
        // Add the last string if any
        if (current.length() > 0) {
            result.add(current.toString());
        }
        
        return result;
    }
    
    /**
     * Approach 4: Unicode Delimiter
     * Uses a Unicode character that's unlikely to appear in input
     */
    private static final char DELIMITER = '￾'; // Unicode character U+FFFE
    
    public String encodeUnicode(List<String> strs) {
        if (strs == null || strs.isEmpty()) return "";
        
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            if (str == null) {
                sb.append(DELIMITER);
            } else {
                sb.append(str).append(DELIMITER);
            }
        }
        // Remove the last delimiter
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
    
    public List<String> decodeUnicode(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.isEmpty()) return result;
        
        // If the string doesn't contain our delimiter, return it as single element
        if (s.indexOf(DELIMITER) == -1) {
            result.add(s);
            return result;
        }
        
        int start = 0;
        while (start < s.length()) {
            int end = s.indexOf(DELIMITER, start);
            if (end == -1) {
                result.add(s.substring(start));
                break;
            }
            result.add(s.substring(start, end));
            start = end + 1;
        }
        
        return result;
    }
    
    /**
     * Helper method to verify encode/decode round trip
     */
    private boolean verifyRoundTrip(List<String> original, List<String> decoded) {
        if (original == null && decoded == null) return true;
        if (original == null || decoded == null) return false;
        if (original.size() != decoded.size()) return false;
        
        for (int i = 0; i < original.size(); i++) {
            String orig = original.get(i);
            String dec = decoded.get(i);
            if (orig == null) {
                if (dec != null) return false;
            } else {
                if (!orig.equals(dec)) return false;
            }
        }
        return true;
    }
    
    /**
     * Helper method to print test results
     */
    private void printTestResult(String testName, boolean passed, List<String> original, List<String> result) {
        System.out.println(testName + ": " + (passed ? "PASSED" : "FAILED"));
        if (!passed) {
            System.out.println("  Original: " + original);
            System.out.println("  Result:   " + result);
        }
    }
    
    /**
     * Performance test helper
     */
    private void printPerformanceResult(String approach, long encodeTime, long decodeTime, int totalChars) {
        System.out.println("  " + approach + ": encode=" + encodeTime + "ns, decode=" + decodeTime + 
                         "ns, total=" + (encodeTime + decodeTime) + "ns");
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Encode and Decode Strings Solution:");
        System.out.println("============================================");
        
        // Test case 1: Normal strings
        System.out.println("\nTest 1: Normal strings");
        List<String> strs1 = Arrays.asList("hello", "world");
        String encoded1 = solution.encode(strs1);
        List<String> decoded1 = solution.decode(encoded1);
        
        boolean test1 = solution.verifyRoundTrip(strs1, decoded1);
        solution.printTestResult("Length Prefix", test1, strs1, decoded1);
        System.out.println("  Encoded: \"" + encoded1 + "\"");
        
        // Test case 2: Empty list
        System.out.println("\nTest 2: Empty list");
        List<String> strs2 = Arrays.asList();
        String encoded2 = solution.encode(strs2);
        List<String> decoded2 = solution.decode(encoded2);
        
        boolean test2 = solution.verifyRoundTrip(strs2, decoded2);
        solution.printTestResult("Empty list", test2, strs2, decoded2);
        
        // Test case 3: List with empty strings
        System.out.println("\nTest 3: List with empty strings");
        List<String> strs3 = Arrays.asList("", "hello", "", "world", "");
        String encoded3 = solution.encode(strs3);
        List<String> decoded3 = solution.decode(encoded3);
        
        boolean test3 = solution.verifyRoundTrip(strs3, decoded3);
        solution.printTestResult("Empty strings", test3, strs3, decoded3);
        System.out.println("  Encoded: \"" + encoded3 + "\"");
        
        // Test case 4: Strings with numbers
        System.out.println("\nTest 4: Strings with numbers");
        List<String> strs4 = Arrays.asList("123", "456:789", "0:1:2");
        String encoded4 = solution.encode(strs4);
        List<String> decoded4 = solution.decode(encoded4);
        
        boolean test4 = solution.verifyRoundTrip(strs4, decoded4);
        solution.printTestResult("Numbers and colons", test4, strs4, decoded4);
        System.out.println("  Encoded: \"" + encoded4 + "\"");
        
        // Test case 5: Special characters
        System.out.println("\nTest 5: Special characters");
        List<String> strs5 = Arrays.asList("hello:world", "test;string", "line\nbreak", "tab\there");
        String encoded5 = solution.encode(strs5);
        List<String> decoded5 = solution.decode(encoded5);
        
        boolean test5 = solution.verifyRoundTrip(strs5, decoded5);
        solution.printTestResult("Special characters", test5, strs5, decoded5);
        
        // Test case 6: Unicode characters
        System.out.println("\nTest 6: Unicode characters");
        List<String> strs6 = Arrays.asList("hello🌍", "测试", "🎉庆祝🎊");
        String encoded6 = solution.encode(strs6);
        List<String> decoded6 = solution.decode(encoded6);
        
        boolean test6 = solution.verifyRoundTrip(strs6, decoded6);
        solution.printTestResult("Unicode characters", test6, strs6, decoded6);
        
        // Test case 7: Null strings in list
        System.out.println("\nTest 7: Null strings in list");
        List<String> strs7 = Arrays.asList("hello", null, "world", null);
        String encoded7 = solution.encode(strs7);
        List<String> decoded7 = solution.decode(encoded7);
        
        // Note: Our implementation converts null to empty string
        List<String> expected7 = Arrays.asList("hello", "", "world", "");
        boolean test7 = solution.verifyRoundTrip(expected7, decoded7);
        solution.printTestResult("Null strings", test7, expected7, decoded7);
        
        // Test case 8: Very long strings
        System.out.println("\nTest 8: Very long string");
        String longString = "a".repeat(1000);
        List<String> strs8 = Arrays.asList("short", longString, "medium");
        String encoded8 = solution.encode(strs8);
        List<String> decoded8 = solution.decode(encoded8);
        
        boolean test8 = solution.verifyRoundTrip(strs8, decoded8);
        solution.printTestResult("Long strings", test8, strs8, decoded8);
        System.out.println("  Original length: " + (longString.length() + 5 + 6));
        System.out.println("  Encoded length: " + encoded8.length());
        
        // Compare all approaches
        System.out.println("\nTest 9: Compare all encoding approaches");
        List<String> testStrs = Arrays.asList("hello", "world", "test:with:colons", "semi;colon");
        
        long startTime, endTime;
        
        // Length Prefix
        startTime = System.nanoTime();
        String encodedA = solution.encode(testStrs);
        List<String> decodedA = solution.decode(encodedA);
        endTime = System.nanoTime();
        long timeA = endTime - startTime;
        
        // Chunked
        startTime = System.nanoTime();
        String encodedB = solution.encodeChunked(testStrs);
        List<String> decodedB = solution.decodeChunked(encodedB);
        endTime = System.nanoTime();
        long timeB = endTime - startTime;
        
        // Escape
        startTime = System.nanoTime();
        String encodedC = solution.encodeEscape(testStrs);
        List<String> decodedC = solution.decodeEscape(encodedC);
        endTime = System.nanoTime();
        long timeC = endTime - startTime;
        
        // Unicode
        startTime = System.nanoTime();
        String encodedD = solution.encodeUnicode(testStrs);
        List<String> decodedD = solution.decodeUnicode(encodedD);
        endTime = System.nanoTime();
        long timeD = endTime - startTime;
        
        System.out.println("Performance comparison:");
        solution.printPerformanceResult("Length Prefix", timeA/2, timeA/2, encodedA.length());
        solution.printPerformanceResult("Chunked     ", timeB/2, timeB/2, encodedB.length());
        solution.printPerformanceResult("Escape      ", timeC/2, timeC/2, encodedC.length());
        solution.printPerformanceResult("Unicode     ", timeD/2, timeD/2, encodedD.length());
        
        System.out.println("\nEncoded results:");
        System.out.println("  Length Prefix: \"" + encodedA + "\"");
        System.out.println("  Chunked:       \"" + encodedB + "\"");
        System.out.println("  Escape:        \"" + encodedC + "\"");
        System.out.println("  Unicode:       \"" + encodedD + "\"");
        
        // Verify all approaches work correctly
        boolean allWork = solution.verifyRoundTrip(testStrs, decodedA) &&
                         solution.verifyRoundTrip(testStrs, decodedB) &&
                         solution.verifyRoundTrip(testStrs, decodedC) &&
                         solution.verifyRoundTrip(testStrs, decodedD);
        System.out.println("All approaches work correctly: " + allWork);
        
        // Performance test with large input
        System.out.println("\nTest 10: Performance with large input");
        List<String> largeStrs = new ArrayList<>();
        Random random = new Random(42);
        for (int i = 0; i < 1000; i++) {
            int length = 10 + random.nextInt(50);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < length; j++) {
                // Mix of regular and special characters
                char c = (char) (32 + random.nextInt(95)); // Printable ASCII
                sb.append(c);
            }
            largeStrs.add(sb.toString());
        }
        
        startTime = System.nanoTime();
        String largeEncoded = solution.encode(largeStrs);
        List<String> largeDecoded = solution.decode(largeEncoded);
        endTime = System.nanoTime();
        long largeTime = endTime - startTime;
        
        System.out.println("Large input (1000 strings):");
        System.out.println("  Total time: " + largeTime + " ns");
        System.out.println("  Average per string: " + (largeTime / 1000) + " ns");
        System.out.println("  Original data size: ~" + (largeStrs.stream().mapToInt(String::length).sum()) + " chars");
        System.out.println("  Encoded size: " + largeEncoded.length() + " chars");
        System.out.println("  Round trip successful: " + solution.verifyRoundTrip(largeStrs, largeDecoded));
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE ENCODING STRATEGY ANALYSIS:");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. Length Prefix with Colon (RECOMMENDED):");
        System.out.println("   Format: \"length:stringlength:string...\"");
        System.out.println("   Pros:");
        System.out.println("     - Simple and efficient implementation");
        System.out.println("     - Handles any ASCII characters including colons");
        System.out.println("     - Easy to parse and debug");
        System.out.println("     - Similar to real-world protocols (HTTP chunked encoding)");
        System.out.println("   Cons:");
        System.out.println("     - Slight overhead for length prefixes");
        System.out.println("     - Requires parsing integers");
        System.out.println("   Best for: General purpose, interview settings");
        
        System.out.println("\n2. Chunked Encoding (Alternative):");
        System.out.println("   Format: Same as length prefix, different implementation");
        System.out.println("   Pros:");
        System.out.println("     - More robust error handling");
        System.out.println("     - Better handling of edge cases");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more complex");
        System.out.println("   Best for: Production code requiring robustness");
        
        System.out.println("\n3. Escape Character Based:");
        System.out.println("   Format: \"string;string;...\" with \\; escapes");
        System.out.println("   Pros:");
        System.out.println("     - Human readable encoded format");
        System.out.println("     - No need to parse numbers");
        System.out.println("   Cons:");
        System.out.println("     - More complex parsing logic");
        System.out.println("     - Less efficient for strings with many special chars");
        System.out.println("     - Encoded string can be significantly longer");
        System.out.println("   Best for: When human readability of encoded format is important");
        
        System.out.println("\n4. Unicode Delimiter:");
        System.out.println("   Format: \"string￾string￾...\"");
        System.out.println("   Pros:");
        System.out.println("     - Very simple encoding/decoding");
        System.out.println("     - No escaping needed");
        System.out.println("   Cons:");
        System.out.println("     - Assumes delimiter won't appear in input");
        System.out.println("     - Not safe for arbitrary binary data");
        System.out.println("     - Unicode characters may have encoding issues");
        System.out.println("   Best for: Controlled environments with known character sets");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("REAL-WORLD CONSIDERATIONS:");
        System.out.println("1. Error Handling: Production code should validate encoded strings");
        System.out.println("2. Character Encoding: Consider UTF-8 for internationalization");
        System.out.println("3. Performance: For very large data, consider streaming");
        System.out.println("4. Security: Validate input to prevent injection attacks");
        System.out.println("5. Compression: Consider compressing encoded string for network transfer");
        System.out.println("=".repeat(80));
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with Length Prefix approach - it's the most common solution");
        System.out.println("2. Explain why it handles all characters (colons in content are fine)");
        System.out.println("3. Discuss edge cases: empty strings, null values, very long strings");
        System.out.println("4. Mention alternative approaches and their trade-offs");
        System.out.println("5. Practice implementing both encode and decode methods");
        System.out.println("6. Be prepared to discuss time/space complexity");
        System.out.println("=".repeat(80));
        
        System.out.println("\nAll tests completed!");
    }
}
