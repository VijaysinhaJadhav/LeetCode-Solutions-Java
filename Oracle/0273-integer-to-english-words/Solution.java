
# Solution.java

```java
import java.util.*;

/**
 * 273. Integer to English Words
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Convert non-negative integer to English words representation.
 * 
 * Key Insights:
 * 1. Break number into chunks of 3 digits (thousands, millions, billions)
 * 2. Process each chunk similarly
 * 3. Special handling for numbers 0-19 and tens
 * 4. Add scale words (Thousand, Million, Billion) appropriately
 */
class Solution {
    
    // Arrays for number words
    private final String[] LESS_THAN_20 = {
        "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
        "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
        "Seventeen", "Eighteen", "Nineteen"
    };
    
    private final String[] TENS = {
        "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", 
        "Eighty", "Ninety"
    };
    
    private final String[] THOUSANDS = {
        "", "Thousand", "Million", "Billion"
    };
    
    /**
     * Approach 1: Recursive Solution (Recommended)
     * Time: O(log₁₀ n), Space: O(log₁₀ n) recursion depth
     * Clean and elegant recursive approach
     */
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        
        return helper(num).trim();
    }
    
    private String helper(int num) {
        if (num == 0) {
            return "";
        } else if (num < 20) {
            return LESS_THAN_20[num] + " ";
        } else if (num < 100) {
            return TENS[num / 10] + " " + helper(num % 10);
        } else if (num < 1000) {
            return LESS_THAN_20[num / 100] + " Hundred " + helper(num % 100);
        } else {
            // Find the appropriate scale
            for (int i = 3; i >= 0; i--) {
                int scale = (int) Math.pow(1000, i);
                if (num >= scale) {
                    return helper(num / scale) + THOUSANDS[i] + " " + helper(num % scale);
                }
            }
        }
        return "";
    }
    
    /**
     * Approach 2: Iterative Solution
     * Time: O(log₁₀ n), Space: O(log₁₀ n)
     * Process number in chunks of 3 digits from right to left
     */
    public String numberToWordsIterative(int num) {
        if (num == 0) {
            return "Zero";
        }
        
        StringBuilder result = new StringBuilder();
        int i = 0; // Index for THOUSANDS array
        
        while (num > 0) {
            if (num % 1000 != 0) {
                // Process current 3-digit chunk
                StringBuilder chunkBuilder = new StringBuilder();
                convertThreeDigits(num % 1000, chunkBuilder);
                
                // Add scale word if needed
                if (i > 0) {
                    chunkBuilder.append(THOUSANDS[i]).append(" ");
                }
                
                // Prepend to result (since we're processing from right to left)
                result.insert(0, chunkBuilder.toString());
            }
            
            num /= 1000;
            i++;
        }
        
        return result.toString().trim();
    }
    
    private void convertThreeDigits(int num, StringBuilder sb) {
        if (num == 0) {
            return;
        }
        
        if (num >= 100) {
            sb.append(LESS_THAN_20[num / 100]).append(" Hundred ");
            num %= 100;
        }
        
        if (num >= 20) {
            sb.append(TENS[num / 10]).append(" ");
            num %= 10;
            if (num > 0) {
                sb.append(LESS_THAN_20[num]).append(" ");
            }
        } else if (num > 0) {
            sb.append(LESS_THAN_20[num]).append(" ");
        }
    }
    
    /**
     * Approach 3: Array-based without recursion
     * Time: O(log₁₀ n), Space: O(log₁₀ n)
     * Uses arrays for all possible number combinations
     */
    public String numberToWordsArray(int num) {
        if (num == 0) return "Zero";
        
        String[] units = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String[] teens = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String[] thousands = {"", "Thousand", "Million", "Billion"};
        
        StringBuilder result = new StringBuilder();
        int thousandIndex = 0;
        
        while (num > 0) {
            if (num % 1000 != 0) {
                StringBuilder chunk = new StringBuilder();
                int chunkNum = num % 1000;
                
                // Hundreds place
                if (chunkNum >= 100) {
                    chunk.append(units[chunkNum / 100]).append(" Hundred ");
                    chunkNum %= 100;
                }
                
                // Tens and ones place
                if (chunkNum >= 20) {
                    chunk.append(tens[chunkNum / 10]).append(" ");
                    chunkNum %= 10;
                    if (chunkNum > 0) {
                        chunk.append(units[chunkNum]).append(" ");
                    }
                } else if (chunkNum >= 10) {
                    chunk.append(teens[chunkNum - 10]).append(" ");
                    chunkNum = 0;
                } else if (chunkNum > 0) {
                    chunk.append(units[chunkNum]).append(" ");
                }
                
                // Add scale
                if (thousandIndex > 0) {
                    chunk.append(thousands[thousandIndex]).append(" ");
                }
                
                result.insert(0, chunk.toString());
            }
            
            num /= 1000;
            thousandIndex++;
        }
        
        return result.toString().trim();
    }
    
    /**
     * Approach 4: Divide and Conquer
     * Time: O(log₁₀ n), Space: O(log₁₀ n)
     * More explicit handling of different number ranges
     */
    public String numberToWordsDivide(int num) {
        if (num == 0) return "Zero";
        
        return convert(num).trim();
    }
    
    private String convert(int num) {
        if (num >= 1000000000) {
            return convert(num / 1000000000) + " Billion " + convert(num % 1000000000);
        } else if (num >= 1000000) {
            return convert(num / 1000000) + " Million " + convert(num % 1000000);
        } else if (num >= 1000) {
            return convert(num / 1000) + " Thousand " + convert(num % 1000);
        } else if (num >= 100) {
            return convert(num / 100) + " Hundred " + convert(num % 100);
        } else if (num >= 20) {
            return TENS[num / 10] + " " + convert(num % 10);
        } else {
            return LESS_THAN_20[num];
        }
    }
    
    /**
     * Approach 5: Complete word mapping
     * Time: O(log₁₀ n), Space: O(log₁₀ n)
     * Maps every possible number directly (not recommended for production)
     */
    public String numberToWordsMapping(int num) {
        if (num == 0) return "Zero";
        
        Map<Integer, String> map = new HashMap<>();
        initializeNumberMap(map);
        
        StringBuilder result = new StringBuilder();
        int billion = num / 1000000000;
        int million = (num % 1000000000) / 1000000;
        int thousand = (num % 1000000) / 1000;
        int remainder = num % 1000;
        
        if (billion > 0) {
            result.append(convertThreeDigitsMapping(billion, map)).append(" Billion ");
        }
        if (million > 0) {
            result.append(convertThreeDigitsMapping(million, map)).append(" Million ");
        }
        if (thousand > 0) {
            result.append(convertThreeDigitsMapping(thousand, map)).append(" Thousand ");
        }
        if (remainder > 0) {
            result.append(convertThreeDigitsMapping(remainder, map));
        }
        
        return result.toString().trim();
    }
    
    private void initializeNumberMap(Map<Integer, String> map) {
        // 1-19
        String[] ones = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
                         "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
                         "Seventeen", "Eighteen", "Nineteen"};
        for (int i = 0; i <= 19; i++) {
            map.put(i, ones[i]);
        }
        
        // Tens
        map.put(20, "Twenty");
        map.put(30, "Thirty");
        map.put(40, "Forty");
        map.put(50, "Fifty");
        map.put(60, "Sixty");
        map.put(70, "Seventy");
        map.put(80, "Eighty");
        map.put(90, "Ninety");
    }
    
    private String convertThreeDigitsMapping(int num, Map<Integer, String> map) {
        StringBuilder sb = new StringBuilder();
        if (num >= 100) {
            sb.append(map.get(num / 100)).append(" Hundred ");
            num %= 100;
        }
        if (num > 0) {
            if (num < 20) {
                sb.append(map.get(num));
            } else {
                sb.append(map.get((num / 10) * 10));
                if (num % 10 > 0) {
                    sb.append(" ").append(map.get(num % 10));
                }
            }
        }
        return sb.toString().trim();
    }
    
    /**
     * Helper: Visualize the conversion process
     */
    public void visualizeConversion(int num) {
        System.out.println("\nVisualizing conversion for: " + num);
        System.out.println("=".repeat(50));
        
        if (num == 0) {
            System.out.println("Number is 0 → Return 'Zero'");
            return;
        }
        
        System.out.println("\nStep 1: Break into 3-digit chunks");
        List<Integer> chunks = new ArrayList<>();
        List<String> scales = new ArrayList<>();
        
        int temp = num;
        int scaleIndex = 0;
        while (temp > 0) {
            int chunk = temp % 1000;
            if (chunk != 0) {
                chunks.add(chunk);
                scales.add(THOUSANDS[scaleIndex]);
                System.out.printf("  Chunk: %d (%s)%n", chunk, THOUSANDS[scaleIndex]);
            }
            temp /= 1000;
            scaleIndex++;
        }
        
        System.out.println("\nStep 2: Convert each chunk to words");
        for (int i = chunks.size() - 1; i >= 0; i--) {
            int chunk = chunks.get(i);
            String scale = scales.get(i);
            System.out.printf("\n  Processing %d:", chunk);
            
            StringBuilder chunkWords = new StringBuilder();
            
            // Hundreds
            if (chunk >= 100) {
                int hundreds = chunk / 100;
                System.out.printf(" %d hundred(s)", hundreds);
                chunkWords.append(LESS_THAN_20[hundreds]).append(" Hundred ");
                chunk %= 100;
            }
            
            // Tens and ones
            if (chunk >= 20) {
                int tensDigit = chunk / 10;
                int onesDigit = chunk % 10;
                System.out.printf(" + %d tens + %d ones", tensDigit, onesDigit);
                chunkWords.append(TENS[tensDigit]).append(" ");
                if (onesDigit > 0) {
                    chunkWords.append(LESS_THAN_20[onesDigit]).append(" ");
                }
            } else if (chunk > 0) {
                System.out.printf(" + special number %d", chunk);
                chunkWords.append(LESS_THAN_20[chunk]).append(" ");
            }
            
            // Add scale
            if (!scale.isEmpty()) {
                chunkWords.append(scale).append(" ");
            }
            
            System.out.printf(" → \"%s\"", chunkWords.toString().trim());
        }
        
        System.out.println("\n\nStep 3: Combine all chunks");
        System.out.println("Result: \"" + numberToWords(num) + "\"");
    }
    
    /**
     * Helper: Generate test cases
     */
    public int[] generateTestCases() {
        return new int[] {
            0,          // Zero
            1,          // One
            10,         // Ten
            11,         // Eleven
            20,         // Twenty
            21,         // Twenty One
            100,        // One Hundred
            101,        // One Hundred One
            110,        // One Hundred Ten
            111,        // One Hundred Eleven
            123,        // One Hundred Twenty Three
            1000,       // One Thousand
            1001,       // One Thousand One
            1010,       // One Thousand Ten
            1100,       // One Thousand One Hundred
            1111,       // One Thousand One Hundred Eleven
            12345,      // Twelve Thousand Three Hundred Forty Five
            100000,     // One Hundred Thousand
            100001,     // One Hundred Thousand One
            123456,     // One Hundred Twenty Three Thousand Four Hundred Fifty Six
            1000000,    // One Million
            1234567,    // One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven
            10000000,   // Ten Million
            12345678,   // Twelve Million Three Hundred Forty Five Thousand Six Hundred Seventy Eight
            100000000,  // One Hundred Million
            123456789,  // One Hundred Twenty Three Million Four Hundred Fifty Six Thousand Seven Hundred Eighty Nine
            1000000000, // One Billion
            1234567890, // One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety
            2147483647  // Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Seven (Max int)
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("===================");
        
        int[] testCases = generateTestCases();
        String[] expected = {
            "Zero",
            "One",
            "Ten",
            "Eleven",
            "Twenty",
            "Twenty One",
            "One Hundred",
            "One Hundred One",
            "One Hundred Ten",
            "One Hundred Eleven",
            "One Hundred Twenty Three",
            "One Thousand",
            "One Thousand One",
            "One Thousand Ten",
            "One Thousand One Hundred",
            "One Thousand One Hundred Eleven",
            "Twelve Thousand Three Hundred Forty Five",
            "One Hundred Thousand",
            "One Hundred Thousand One",
            "One Hundred Twenty Three Thousand Four Hundred Fifty Six",
            "One Million",
            "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven",
            "Ten Million",
            "Twelve Million Three Hundred Forty Five Thousand Six Hundred Seventy Eight",
            "One Hundred Million",
            "One Hundred Twenty Three Million Four Hundred Fifty Six Thousand Seven Hundred Eighty Nine",
            "One Billion",
            "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety",
            "Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Seven"
        };
        
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.printf("\nTest %d: %,d%n", i + 1, testCases[i]);
            
            String result1 = numberToWords(testCases[i]);
            String result2 = numberToWordsIterative(testCases[i]);
            String result3 = numberToWordsArray(testCases[i]);
            String result4 = numberToWordsDivide(testCases[i]);
            
            boolean allMatch = result1.equals(expected[i]) && 
                              result2.equals(expected[i]) &&
                              result3.equals(expected[i]) &&
                              result4.equals(expected[i]);
            
            if (allMatch) {
                System.out.println("✓ PASS - All methods return: \"" + expected[i] + "\"");
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: \"" + expected[i] + "\"");
                System.out.println("  Method 1: \"" + result1 + "\"");
                System.out.println("  Method 2: \"" + result2 + "\"");
                System.out.println("  Method 3: \"" + result3 + "\"");
                System.out.println("  Method 4: \"" + result4 + "\"");
            }
            
            // Visualize for interesting cases
            if (testCases[i] == 1234567 || testCases[i] == 1234567890) {
                visualizeConversion(testCases[i]);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Explain English number naming rules
     */
    public void explainNumberRules() {
        System.out.println("\nEnglish Number Naming Rules:");
        System.out.println("============================");
        
        System.out.println("\n1. Basic numbers (0-19):");
        System.out.println("   - 0: Zero");
        System.out.println("   - 1-9: One, Two, ..., Nine");
        System.out.println("   - 10-19: Ten, Eleven, ..., Nineteen");
        
        System.out.println("\n2. Tens (20-90):");
        System.out.println("   - 20: Twenty");
        System.out.println("   - 30: Thirty");
        System.out.println("   - 40: Forty (not Fourty)");
        System.out.println("   - 50: Fifty");
        System.out.println("   - 60: Sixty");
        System.out.println("   - 70: Seventy");
        System.out.println("   - 80: Eighty");
        System.out.println("   - 90: Ninety");
        
        System.out.println("\n3. Compound numbers (21-99):");
        System.out.println("   - Format: Tens + Ones");
        System.out.println("   - Example: 21 = Twenty One");
        System.out.println("   - Example: 99 = Ninety Nine");
        
        System.out.println("\n4. Hundreds (100-999):");
        System.out.println("   - Format: Hundreds + Rest");
        System.out.println("   - Example: 100 = One Hundred");
        System.out.println("   - Example: 123 = One Hundred Twenty Three");
        System.out.println("   - Example: 101 = One Hundred One (not One Hundred And One)");
        
        System.out.println("\n5. Thousands (1,000-999,999):");
        System.out.println("   - Format: Thousands + Hundreds + Rest");
        System.out.println("   - Example: 1,000 = One Thousand");
        System.out.println("   - Example: 1,001 = One Thousand One");
        System.out.println("   - Example: 123,456 = One Hundred Twenty Three Thousand Four Hundred Fifty Six");
        
        System.out.println("\n6. Millions (1,000,000-999,999,999):");
        System.out.println("   - Format: Millions + Thousands + Hundreds + Rest");
        System.out.println("   - Example: 1,000,000 = One Million");
        System.out.println("   - Example: 1,234,567 = One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven");
        
        System.out.println("\n7. Billions (1,000,000,000-2,147,483,647):");
        System.out.println("   - Format: Billions + Millions + Thousands + Hundreds + Rest");
        System.out.println("   - Example: 1,000,000,000 = One Billion");
        System.out.println("   - Example: 1,234,567,890 = One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety");
        
        System.out.println("\n8. Special rules:");
        System.out.println("   - No 'and' in American English (One Hundred One, not One Hundred and One)");
        System.out.println("   - Hyphens for 21-99 (Twenty-One, but we use space for simplicity)");
        System.out.println("   - No plural for hundred, thousand, million, billion");
        System.out.println("   - Spaces between words, no trailing spaces");
    }
    
    /**
     * Helper: Performance comparison
     */
    public void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=======================");
        
        // Generate large test cases
        Random rand = new Random(42);
        int[] testNumbers = new int[10000];
        for (int i = 0; i < testNumbers.length; i++) {
            testNumbers[i] = rand.nextInt(Integer.MAX_VALUE);
        }
        
        System.out.println("Testing with " + testNumbers.length + " random numbers");
        
        long[] times = new long[5];
        
        // Method 1: Recursive
        long start = System.currentTimeMillis();
        for (int num : testNumbers) {
            numberToWords(num);
        }
        times[0] = System.currentTimeMillis() - start;
        
        // Method 2: Iterative
        start = System.currentTimeMillis();
        for (int num : testNumbers) {
            numberToWordsIterative(num);
        }
        times[1] = System.currentTimeMillis() - start;
        
        // Method 3: Array-based
        start = System.currentTimeMillis();
        for (int num : testNumbers) {
            numberToWordsArray(num);
        }
        times[2] = System.currentTimeMillis() - start;
        
        // Method 4: Divide and Conquer
        start = System.currentTimeMillis();
        for (int num : testNumbers) {
            numberToWordsDivide(num);
        }
        times[3] = System.currentTimeMillis() - start;
        
        // Method 5: Mapping (skip for large test)
        times[4] = -1;
        
        System.out.println("\nResults:");
        System.out.println("Method                     | Time (ms)");
        System.out.println("---------------------------|-----------");
        System.out.printf("1. Recursive              | %9d%n", times[0]);
        System.out.printf("2. Iterative              | %9d%n", times[1]);
        System.out.printf("3. Array-based            | %9d%n", times[2]);
        System.out.printf("4. Divide and Conquer     | %9d%n", times[3]);
        System.out.printf("5. Complete Mapping       | %9s%n", "N/A (too slow)");
        
        System.out.println("\nObservations:");
        System.out.println("1. All methods are O(log n) time complexity");
        System.out.println("2. Iterative method is usually fastest");
        System.out.println("3. Recursive method is most elegant");
        System.out.println("4. Array-based method is most explicit");
        System.out.println("5. Complete mapping is impractical for large ranges");
    }
    
    /**
     * Helper: Edge cases testing
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("===================");
        
        System.out.println("\n1. Zero:");
        System.out.println("   Input: 0");
        System.out.println("   Output: \"" + numberToWords(0) + "\"");
        
        System.out.println("\n2. Maximum integer (2^31 - 1):");
        System.out.println("   Input: 2,147,483,647");
        System.out.println("   Output: \"" + numberToWords(Integer.MAX_VALUE) + "\"");
        
        System.out.println("\n3. Round numbers:");
        System.out.println("   100: \"" + numberToWords(100) + "\"");
        System.out.println("   1000: \"" + numberToWords(1000) + "\"");
        System.out.println("   1000000: \"" + numberToWords(1000000) + "\"");
        System.out.println("   1000000000: \"" + numberToWords(1000000000) + "\"");
        
        System.out.println("\n4. Numbers with zeros in middle:");
        System.out.println("   101: \"" + numberToWords(101) + "\"");
        System.out.println("   1001: \"" + numberToWords(1001) + "\"");
        System.out.println("   10001: \"" + numberToWords(10001) + "\"");
        System.out.println("   100001: \"" + numberToWords(100001) + "\"");
        
        System.out.println("\n5. Special tens:");
        System.out.println("   40: \"" + numberToWords(40) + "\" (Forty, not Fourty)");
        System.out.println("   50: \"" + numberToWords(50) + "\"");
        System.out.println("   80: \"" + numberToWords(80) + "\"");
        
        System.out.println("\n6. Teen numbers:");
        System.out.println("   11: \"" + numberToWords(11) + "\"");
        System.out.println("   12: \"" + numberToWords(12) + "\"");
        System.out.println("   13: \"" + numberToWords(13) + "\"");
        System.out.println("   15: \"" + numberToWords(15) + "\" (Fifteen, not Fiveteen)");
    }
    
    /**
     * Helper: International variations
     */
    public void explainInternationalVariations() {
        System.out.println("\nInternational Variations:");
        System.out.println("=========================");
        
        System.out.println("\n1. British English vs American English:");
        System.out.println("   - British: One Hundred and One");
        System.out.println("   - American: One Hundred One");
        System.out.println("   - This problem uses American English");
        
        System.out.println("\n2. Long Scale vs Short Scale:");
        System.out.println("   - Short Scale (US, modern British):");
        System.out.println("     Billion = 10^9 (1,000,000,000)");
        System.out.println("     Trillion = 10^12");
        System.out.println("   - Long Scale (some European countries):");
        System.out.println("     Billion = 10^12 (1,000,000,000,000)");
        System.out.println("     Trillion = 10^18");
        
        System.out.println("\n3. Indian Numbering System:");
        System.out.println("   - Lakh = 100,000");
        System.out.println("   - Crore = 10,000,000");
        System.out.println("   - Example: 1,23,45,678 = One Crore Twenty Three Lakh Forty Five Thousand Six Hundred Seventy Eight");
        
        System.out.println("\n4. Hyphen Usage:");
        System.out.println("   - US/UK: Twenty-one (with hyphen)");
        System.out.println("   - Some style guides: Twenty one (without hyphen)");
        System.out.println("   - This problem uses spaces without hyphens");
        
        System.out.println("\n5. Decimal and Fraction Handling:");
        System.out.println("   - Not covered in this problem");
        System.out.println("   - Example: 123.45 = One Hundred Twenty Three Point Four Five");
    }
    
    /**
     * Helper: Interview tips
     */
    public void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("===============");
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - Range of numbers (0 to 2^31-1)");
        System.out.println("   - Hyphen or space for compound numbers?");
        System.out.println("   - 'And' in hundreds? (American vs British)");
        System.out.println("   - Output format (spaces, capitalization)");
        
        System.out.println("\n2. Identify patterns:");
        System.out.println("   - Numbers repeat every 3 digits (thousands)");
        System.out.println("   - Need special handling for 0-19");
        System.out.println("   - Tens (20, 30, ..., 90) have special names");
        
        System.out.println("\n3. Design approach:");
        System.out.println("   - Use arrays for number words");
        System.out.println("   - Process number in chunks of 3");
        System.out.println("   - Handle each chunk recursively/iteratively");
        System.out.println("   - Add scale words (Thousand, Million, Billion)");
        
        System.out.println("\n4. Handle edge cases:");
        System.out.println("   - Zero");
        System.out.println("   - Numbers with zeros in middle (101, 1001)");
        System.out.println("   - Maximum integer");
        System.out.println("   - Round numbers (100, 1000, 1000000)");
        
        System.out.println("\n5. Implementation details:");
        System.out.println("   - Use StringBuilder for efficient string building");
        System.out.println("   - Trim trailing spaces");
        System.out.println("   - Proper spacing between words");
        System.out.println("   - Handle 'Forty' vs 'Fourty'");
        
        System.out.println("\n6. Testing strategy:");
        System.out.println("   - Test provided examples");
        System.out.println("   - Test edge cases");
        System.out.println("   - Test random numbers");
        System.out.println("   - Verify capitalization and spacing");
        
        System.out.println("\n7. Optimize and refactor:");
        System.out.println("   - Consider iterative vs recursive");
        System.out.println("   - Precompute arrays for efficiency");
        System.out.println("   - Handle early exits (e.g., return 'Zero' immediately)");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("273. Integer to English Words");
        System.out.println("=============================");
        
        // Explain number rules
        solution.explainNumberRules();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        solution.runTestCases();
        
        // Visualize conversions
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Detailed Conversion Visualization:");
        System.out.println("=".repeat(80));
        
        // Example 1
        System.out.println("\nExample 1: 123");
        solution.visualizeConversion(123);
        
        // Example 2
        System.out.println("\n" + "-".repeat(80));
        System.out.println("\nExample 2: 12345");
        solution.visualizeConversion(12345);
        
        // Example 3
        System.out.println("\n" + "-".repeat(80));
        System.out.println("\nExample 3: 1234567");
        solution.visualizeConversion(1234567);
        
        // Complex example
        System.out.println("\n" + "-".repeat(80));
        System.out.println("\nComplex Example: 2,147,483,647 (Max int)");
        solution.visualizeConversion(Integer.MAX_VALUE);
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        solution.comparePerformance();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // International variations
        System.out.println("\n" = "=".repeat(80));
        solution.explainInternationalVariations();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        solution.interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation (Recursive):");
        System.out.println("""
class Solution {
    private final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", 
                                           "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", 
                                           "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", 
                                           "Nineteen"};
    private final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", 
                                   "Eighty", "Ninety"};
    private final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};
    
    public String numberToWords(int num) {
        if (num == 0) return "Zero";
        return helper(num).trim();
    }
    
    private String helper(int num) {
        if (num == 0) return "";
        else if (num < 20) return LESS_THAN_20[num] + " ";
        else if (num < 100) return TENS[num / 10] + " " + helper(num % 10);
        else if (num < 1000) return LESS_THAN_20[num / 100] + " Hundred " + helper(num % 100);
        else {
            for (int i = 3; i >= 0;
