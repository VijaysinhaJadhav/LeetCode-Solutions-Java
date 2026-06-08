
## Solution.java

```java
/**
 * 415. Add Strings
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Add two non-negative integers represented as strings without converting to integers.
 * 
 * Key Insights:
 * 1. Use two pointers starting from the end of each string.
 * 2. Maintain a carry variable.
 * 3. Build result in reverse order, then reverse.
 */
class Solution {
    
    /**
     * Approach 1: Two Pointers with StringBuilder (Recommended)
     * Time: O(max(len1, len2)), Space: O(max(len1, len2))
     * 
     * Steps:
     * 1. Initialize i = len1 - 1, j = len2 - 1, carry = 0
     * 2. While i >= 0 or j >= 0 or carry > 0:
     *    - digit1 = (i >= 0) ? num1.charAt(i) - '0' : 0
     *    - digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0
     *    - sum = digit1 + digit2 + carry
     *    - result.append(sum % 10)
     *    - carry = sum / 10
     *    - i--; j--
     * 3. Reverse result and return as string.
     */
    public String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;
        
        while (i >= 0 || j >= 0 || carry > 0) {
            int digit1 = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0;
            int sum = digit1 + digit2 + carry;
            result.append(sum % 10);
            carry = sum / 10;
            i--;
            j--;
        }
        
        return result.reverse().toString();
    }
    
    /**
     * Approach 2: Using char array (alternative)
     * Time: O(max(len1, len2)), Space: O(max(len1, len2))
     */
    public String addStringsCharArray(String num1, String num2) {
        char[] res = new char[Math.max(num1.length(), num2.length()) + 1];
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int k = res.length - 1;
        int carry = 0;
        
        while (i >= 0 || j >= 0 || carry > 0) {
            int d1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int d2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            int sum = d1 + d2 + carry;
            res[k--] = (char) (sum % 10 + '0');
            carry = sum / 10;
            i--;
            j--;
        }
        
        return new String(res, k + 1, res.length - k - 1);
    }
    
    /**
     * Helper: Visualize the addition process
     */
    public void visualizeAddition(String num1, String num2) {
        System.out.println("\nAdd Strings Visualization:");
        System.out.println("=".repeat(60));
        
        System.out.printf("  %s%n", num1);
        System.out.printf("+ %s%n", num2);
        System.out.println("-".repeat(Math.max(num1.length(), num2.length()) + 2));
        
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;
        StringBuilder steps = new StringBuilder();
        int step = 1;
        
        while (i >= 0 || j >= 0 || carry > 0) {
            int d1 = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int d2 = (j >= 0) ? num2.charAt(j) - '0' : 0;
            int sum = d1 + d2 + carry;
            int digit = sum % 10;
            carry = sum / 10;
            
            System.out.printf("Step %d: %d + %d + carry(%d) = %d → digit = %d, new carry = %d%n", 
                step++, d1, d2, carry == 0 ? (carry) : (carry == 1 ? carry : carry), sum, digit, carry);
            
            steps.insert(0, digit);
            i--;
            j--;
        }
        
        System.out.println("\nResult: " + steps.toString());
    }
    
    /**
     * Helper: Generate test cases
     */
    public String[][] generateTestCases() {
        return new String[][]{
            {"11", "123"},       // "134"
            {"456", "77"},       // "533"
            {"0", "0"},          // "0"
            {"999", "1"},        // "1000"
            {"1", "999"},        // "1000"
            {"123456789", "987654321"}, // "1111111110"
            {"0", "5"}           // "5"
        };
    }
    
    /**
     * Helper: Run all test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        String[][] testCases = generateTestCases();
        String[] expected = {"134", "533", "0", "1000", "1000", "1111111110", "5"};
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            String num1 = testCases[i][0];
            String num2 = testCases[i][1];
            System.out.printf("\nTest %d: \"%s\" + \"%s\"%n", i + 1, num1, num2);
            
            String r1 = addStrings(num1, num2);
            String r2 = addStringsCharArray(num1, num2);
            
            if (r1.equals(expected[i]) && r2.equals(expected[i])) {
                System.out.println("✓ PASS - Result: " + r1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected[i]);
                System.out.println("  Method 1: " + r1);
                System.out.println("  Method 2: " + r2);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("415. Add Strings");
        System.out.println("===============");
        
        // Visualize a sample addition
        solution.visualizeAddition("456", "77");
        
        // Run test cases
        solution.runTestCases();
    }
}
