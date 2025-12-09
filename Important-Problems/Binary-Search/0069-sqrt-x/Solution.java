
## Solution.java

```java
/**
 * 69. Sqrt(x)
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Given a non-negative integer x, return the square root of x rounded down 
 * to the nearest integer. The returned integer should be non-negative as well.
 * 
 * Key Insights:
 * 1. Need to find the largest integer ans such that ans² ≤ x
 * 2. Binary search is perfect for searching in range [0, x]
 * 3. Must handle integer overflow when calculating mid * mid
 * 4. Edge cases: x = 0, x = 1, and large x values
 * 
 * Approach (Binary Search):
 * 1. Initialize left = 0, right = x
 * 2. While left <= right, calculate mid
 * 3. If mid² == x, return mid
 * 4. If mid² < x, search right half (ans could be larger)
 * 5. If mid² > x, search left half
 * 6. When loop ends, right is the answer (largest integer with square ≤ x)
 * 
 * Time Complexity: O(log x)
 * Space Complexity: O(1)
 * 
 * Tags: Math, Binary Search
 */

class Solution {
    /**
     * Approach 1: Standard Binary Search - RECOMMENDED
     * O(log x) time, O(1) space - Optimal solution
     */
    public int mySqrt(int x) {
        if (x == 0) return 0;
        if (x == 1) return 1;
        
        int left = 1;
        int right = x;
        int result = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Use long to prevent integer overflow
            long square = (long) mid * mid;
            
            if (square == x) {
                return mid;
            } else if (square < x) {
                result = mid; // Potential answer
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: Binary Search with Optimized Range
     * Same complexity but with smaller initial range
     */
    public int mySqrtOptimized(int x) {
        if (x == 0) return 0;
        if (x == 1) return 1;
        
        // For x ≥ 2, sqrt(x) ≤ x/2
        int left = 1;
        int right = x / 2;
        int result = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;
            
            if (square == x) {
                return mid;
            } else if (square < x) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 3: Binary Search without Long (Overflow Safe)
     * Uses division instead of multiplication to avoid overflow
     */
    public int mySqrtNoLong(int x) {
        if (x == 0) return 0;
        if (x == 1) return 1;
        
        int left = 1;
        int right = x;
        int result = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Use division to avoid overflow
            if (mid <= x / mid) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: Newton's Method (Mathematical Approach)
     * Faster convergence but similar O(log x) time
     */
    public int mySqrtNewton(int x) {
        if (x == 0) return 0;
        
        long guess = x; // Use long to prevent overflow
        
        while (guess * guess > x) {
            guess = (guess + x / guess) / 2;
        }
        
        return (int) guess;
    }
    
    /**
     * Approach 5: Linear Search (Not Recommended - for comparison only)
     * O(√x) time, O(1) space - Too slow for large x
     */
    public int mySqrtLinear(int x) {
        if (x == 0) return 0;
        
        int i = 1;
        while (i <= x / i) {
            i++;
        }
        return i - 1;
    }
    
    /**
     * Approach 6: Bit Manipulation (Advanced)
     * O(32) time for 32-bit integers
     */
    public int mySqrtBitManipulation(int x) {
        if (x == 0) return 0;
        
        int result = 0;
        int bit = 1 << 15; // Start from the highest possible bit
        
        while (bit > 0) {
            result |= bit;
            if (result > x / result) {
                result ^= bit; // Reset if too large
            }
            bit >>= 1;
        }
        
        return result;
    }
    
    /**
     * Approach 7: Binary Search with Exact Implementation
     * Clear implementation showing the exact logic
     */
    public int mySqrtExact(int x) {
        if (x < 2) return x;
        
        int left = 2;
        int right = x / 2;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long num = (long) mid * mid;
            
            if (num > x) {
                right = mid - 1;
            } else if (num < x) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        
        return right;
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    private void visualizeBinarySearch(int x) {
        System.out.println("\nBinary Search Visualization for sqrt(" + x + "):");
        System.out.println("Search range: 0 to " + x);
        
        if (x == 0) {
            System.out.println("Result: 0");
            return;
        }
        if (x == 1) {
            System.out.println("Result: 1");
            return;
        }
        
        int left = 1;
        int right = x;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Mid | Mid² | Comparison | Action");
        System.out.println("-----|------|-------|-----|------|------------|--------");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;
            String comparison = square + " ? " + x;
            String action;
            
            if (square == x) {
                action = "EXACT MATCH! sqrt(" + x + ") = " + mid;
                System.out.printf("%4d | %4d | %5d | %3d | %4d | %10s | %s%n", 
                                step, left, right, mid, square, comparison, action);
                return;
            } else if (square < x) {
                action = "Mid² < x, search RIGHT (ans could be " + mid + " or larger)";
                left = mid + 1;
            } else {
                action = "Mid² > x, search LEFT";
                right = mid - 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %4d | %10s | %s%n", 
                            step, left, right, mid, square, comparison, action);
            step++;
        }
        
        System.out.println("\nFinal Result: " + right + " (largest integer where " + right + "² ≤ " + x + ")");
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int x) {
        System.out.println("\nPerformance Comparison for sqrt(" + x + ")");
        System.out.println("=========================================");
        
        long startTime, endTime;
        int result;
        
        // Standard Binary Search
        startTime = System.nanoTime();
        result = mySqrt(x);
        endTime = System.nanoTime();
        System.out.printf("Standard Binary: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Optimized Range
        startTime = System.nanoTime();
        result = mySqrtOptimized(x);
        endTime = System.nanoTime();
        System.out.printf("Optimized Range: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // No Long (Division)
        startTime = System.nanoTime();
        result = mySqrtNoLong(x);
        endTime = System.nanoTime();
        System.out.printf("Division Method: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Newton's Method
        startTime = System.nanoTime();
        result = mySqrtNewton(x);
        endTime = System.nanoTime();
        System.out.printf("Newton's Method: %8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Bit Manipulation
        startTime = System.nanoTime();
        result = mySqrtBitManipulation(x);
        endTime = System.nanoTime();
        System.out.printf("Bit Manipulation:%8d ns, Result: %d%n", (endTime - startTime), result);
        
        // Linear Search (only for reasonable x)
        if (x <= 100000) {
            startTime = System.nanoTime();
            result = mySqrtLinear(x);
            endTime = System.nanoTime();
            System.out.printf("Linear Search:   %8d ns, Result: %d%n", (endTime - startTime), result);
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Sqrt(x):");
        System.out.println("=================");
        
        // Test case 1: Perfect square
        System.out.println("\nTest 1: Perfect square (x=4)");
        int result1 = solution.mySqrt(4);
        System.out.println("Result: " + result1 + " - " + (result1 == 2 ? "PASSED" : "FAILED"));
        solution.visualizeBinarySearch(4);
        
        // Test case 2: Non-perfect square
        System.out.println("\nTest 2: Non-perfect square (x=8)");
        int result2 = solution.mySqrt(8);
        System.out.println("Result: " + result2 + " - " + (result2 == 2 ? "PASSED" : "FAILED"));
        solution.visualizeBinarySearch(8);
        
        // Test case 3: Zero
        System.out.println("\nTest 3: Zero (x=0)");
        int result3 = solution.mySqrt(0);
        System.out.println("Result: " + result3 + " - " + (result3 == 0 ? "PASSED" : "FAILED"));
        
        // Test case 4: One
        System.out.println("\nTest 4: One (x=1)");
        int result4 = solution.mySqrt(1);
        System.out.println("Result: " + result4 + " - " + (result4 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 5: Large perfect square
        System.out.println("\nTest 5: Large perfect square (x=1000000)");
        int result5 = solution.mySqrt(1000000);
        System.out.println("Result: " + result5 + " - " + (result5 == 1000 ? "PASSED" : "FAILED"));
        
        // Test case 6: Large non-perfect square
        System.out.println("\nTest 6: Large non-perfect square (x=2147395599)");
        int result6 = solution.mySqrt(2147395599);
        System.out.println("Result: " + result6 + " - " + (result6 == 46339 ? "PASSED" : "FAILED"));
        
        // Test case 7: Maximum integer
        System.out.println("\nTest 7: Maximum integer (x=2147483647)");
        int result7 = solution.mySqrt(Integer.MAX_VALUE);
        System.out.println("Result: " + result7 + " - " + (result7 == 46340 ? "PASSED" : "FAILED"));
        
        // Test case 8: Small numbers
        System.out.println("\nTest 8: Small numbers");
        for (int i = 0; i <= 10; i++) {
            int result = solution.mySqrt(i);
            int expected = (int) Math.sqrt(i);
            System.out.println("sqrt(" + i + ") = " + result + " - " + 
                             (result == expected ? "PASSED" : "FAILED"));
        }
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison - Small Number");
        solution.compareApproaches(100);
        
        System.out.println("\nTest 10: Performance Comparison - Large Number");
        solution.compareApproaches(2147483647);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BINARY SEARCH ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("We're searching for the largest integer 'ans' such that ans² ≤ x");
        System.out.println("This is essentially binary search in the range [0, x]");
        
        System.out.println("\nWhy binary search works:");
        System.out.println("1. The function f(n) = n² is monotonically increasing for n ≥ 0");
        System.out.println("2. We can use binary search to find where n² transitions from ≤ x to > x");
        System.out.println("3. When the search ends, 'right' points to the largest valid integer");
        
        System.out.println("\nImportant Implementation Details:");
        System.out.println("1. Use long for mid² calculation to prevent integer overflow");
        System.out.println("2. Alternative: use mid <= x / mid to avoid overflow");
        System.out.println("3. Handle edge cases x=0 and x=1 separately for clarity");
        System.out.println("4. The search range can be optimized to [1, x/2] for x ≥ 2");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Standard Binary Search (RECOMMENDED):");
        System.out.println("   Time: O(log x) - Halves search space each iteration");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Search in range [1, x]");
        System.out.println("     - Calculate mid² using long to prevent overflow");
        System.out.println("     - Track the largest valid mid found");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(log x) time complexity");
        System.out.println("     - O(1) space complexity");
        System.out.println("     - Simple and reliable");
        System.out.println("   Cons:");
        System.out.println("     - Requires long conversion for large x");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Binary Search with Optimized Range:");
        System.out.println("   Time: O(log x) - Same as standard");
        System.out.println("   Space: O(1) - Same as standard");
        System.out.println("   How it works:");
        System.out.println("     - For x ≥ 2, sqrt(x) ≤ x/2");
        System.out.println("     - Search in range [1, x/2]");
        System.out.println("     - Reduces search space by half initially");
        System.out.println("   Pros:");
        System.out.println("     - Smaller initial search range");
        System.out.println("     - Slightly faster for large x");
        System.out.println("   Cons:");
        System.out.println("     - Need to handle x=0 and x=1 separately");
        System.out.println("   Best for: Large x values");
        
        System.out.println("\n3. Binary Search without Long (Division Method):");
        System.out.println("   Time: O(log x) - Same as standard");
        System.out.println("   Space: O(1) - Same as standard");
        System.out.println("   How it works:");
        System.out.println("     - Use mid <= x / mid instead of mid * mid <= x");
        System.out.println("     - Avoids integer overflow completely");
        System.out.println("   Pros:");
        System.out.println("     - No risk of integer overflow");
        System.out.println("     - Clean implementation");
        System.out.println("   Cons:");
        System.out.println("     - Division might be slightly slower than multiplication");
        System.out.println("   Best for: Maximum safety against overflow");
        
        System.out.println("\n4. Newton's Method (Mathematical Approach):");
        System.out.println("   Time: O(log x) - Quadratic convergence");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Use iterative formula: guess = (guess + x/guess) / 2");
        System.out.println("     - Converges very quickly to the solution");
        System.out.println("   Pros:");
        System.out.println("     - Very fast convergence");
        System.out.println("     - Elegant mathematical solution");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of calculus");
        System.out.println("     - May need long for large x");
        System.out.println("   Best for: Mathematical applications, when speed is critical");
        
        System.out.println("\n5. Bit Manipulation (Advanced):");
        System.out.println("   Time: O(32) - Constant for 32-bit integers");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Set bits from highest to lowest");
        System.out.println("     - Check if setting the bit makes result too large");
        System.out.println("   Pros:");
        System.out.println("     - Constant time complexity");
        System.out.println("     - No risk of overflow");
        System.out.println("   Cons:");
        System.out.println("     - More complex to understand");
        System.out.println("     - Limited to integer square roots");
        System.out.println("   Best for: Embedded systems, performance-critical code");
        
        System.out.println("\n6. Linear Search (NOT RECOMMENDED):");
        System.out.println("   Time: O(√x) - Iterate up to sqrt(x)");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Iterate i from 1 until i² > x");
        System.out.println("     - Return i-1");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(√x) time is too slow for large x");
        System.out.println("     - Fails for x near 2^31-1");
        System.out.println("   Best for: Very small x, educational purposes");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. For x ≥ 2: sqrt(x) ≤ x/2");
        System.out.println("2. Maximum iterations for binary search: ⌊log₂(x)⌋ + 1");
        System.out.println("3. For x=2^31-1: maximum 31 iterations");
        System.out.println("4. Linear search would require up to 46340 iterations");
        System.out.println("5. Newton's method typically converges in 5-10 iterations");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Immediately identify this as a binary search problem");
        System.out.println("2. Explain the search space and the condition we're looking for");
        System.out.println("3. Implement binary search with overflow prevention");
        System.out.println("4. Discuss alternative approaches and their trade-offs");
        System.out.println("5. Handle edge cases: x=0, x=1, large x");
        System.out.println("6. Mention the mathematical property: for x≥2, sqrt(x)≤x/2");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}
