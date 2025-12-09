
## Solution.java

```java
/**
 * 374. Guess Number Higher or Lower
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * We are playing the Guess Game. The game is as follows:
 * I pick a number from 1 to n. You have to guess which number I picked.
 * Every time you guess wrong, I will tell you whether the number I picked 
 * is higher or lower than your guess.
 * 
 * Key Insights:
 * 1. The problem is essentially binary search in range [1, n]
 * 2. The guess API acts as the comparison function
 * 3. Need to handle large n (up to 2^31-1) efficiently
 * 4. Use proper mid calculation to avoid integer overflow
 * 
 * Approach (Binary Search):
 * 1. Initialize left = 1, right = n
 * 2. While left <= right, calculate mid
 * 3. Call guess(mid) to get direction
 * 4. If guess(mid) == 0, return mid (found the number)
 * 5. If guess(mid) == -1, search left half (right = mid - 1)
 * 6. If guess(mid) == 1, search right half (left = mid + 1)
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 * 
 * Tags: Binary Search, Interactive
 */

/** 
 * Forward declaration of guess API.
 * @param  num   your guess
 * @return 	     -1 if num is higher than the picked number
 *			      1 if num is lower than the picked number
 *               otherwise return 0
 * int guess(int num);
 */

public class Solution extends GuessGame {
    /**
     * Approach 1: Standard Binary Search - RECOMMENDED
     * O(log n) time, O(1) space - Optimal solution
     */
    public int guessNumber(int n) {
        int left = 1;
        int right = n;
        
        while (left <= right) {
            int mid = left + (right - left) / 2; // Prevent overflow
            int result = guess(mid);
            
            if (result == 0) {
                return mid; // Found the number
            } else if (result == -1) {
                right = mid - 1; // Number is lower, search left
            } else {
                left = mid + 1; // Number is higher, search right
            }
        }
        
        return -1; // Should never reach here if pick is in [1, n]
    }
    
    /**
     * Approach 2: Binary Search with Early Return
     * Same complexity with additional early check
     */
    public int guessNumberEarlyReturn(int n) {
        // Early return for single element
        if (n == 1) {
            return 1;
        }
        
        int left = 1;
        int right = n;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int result = guess(mid);
            
            if (result == 0) {
                return mid;
            } else if (result == -1) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 3: Ternary Search (Theoretical)
     * O(log₃ n) time, but worse constant factors in practice
     */
    public int guessNumberTernary(int n) {
        int left = 1;
        int right = n;
        
        while (left <= right) {
            int mid1 = left + (right - left) / 3;
            int mid2 = right - (right - left) / 3;
            
            int res1 = guess(mid1);
            if (res1 == 0) return mid1;
            
            int res2 = guess(mid2);
            if (res2 == 0) return mid2;
            
            if (res1 == -1) {
                right = mid1 - 1;
            } else if (res2 == 1) {
                left = mid2 + 1;
            } else {
                left = mid1 + 1;
                right = mid2 - 1;
            }
        }
        
        return -1;
    }
    
    /**
     * Approach 4: Linear Search (Not Recommended - for comparison only)
     * O(n) time, O(1) space - Too slow for large n
     */
    public int guessNumberLinear(int n) {
        for (int i = 1; i <= n; i++) {
            if (guess(i) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Approach 5: Recursive Binary Search
     * O(log n) time, O(log n) space due to recursion stack
     */
    public int guessNumberRecursive(int n) {
        return binarySearchRecursive(1, n);
    }
    
    private int binarySearchRecursive(int left, int right) {
        if (left > right) {
            return -1;
        }
        
        int mid = left + (right - left) / 2;
        int result = guess(mid);
        
        if (result == 0) {
            return mid;
        } else if (result == -1) {
            return binarySearchRecursive(left, mid - 1);
        } else {
            return binarySearchRecursive(mid + 1, right);
        }
    }
    
    /**
     * Helper method to visualize the binary search process
     */
    private void visualizeBinarySearch(int n, int pick) {
        System.out.println("\nBinary Search Visualization:");
        System.out.println("Range: 1 to " + n);
        System.out.println("Actual pick: " + pick);
        
        int left = 1;
        int right = n;
        int step = 1;
        
        System.out.println("\nStep | Left | Right | Mid | Guess Result | Action");
        System.out.println("-----|------|-------|-----|--------------|--------");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int result = guess(mid);
            String action;
            
            if (result == 0) {
                action = "FOUND! Number is " + mid;
                System.out.printf("%4d | %4d | %5d | %3d | %12d | %s%n", 
                                step, left, right, mid, result, action);
                System.out.println("\nResult: " + mid);
                return;
            } else if (result == -1) {
                action = "Too high, search LEFT";
                right = mid - 1;
            } else {
                action = "Too low, search RIGHT";
                left = mid + 1;
            }
            
            System.out.printf("%4d | %4d | %5d | %3d | %12d | %s%n", 
                            step, left, right, mid, result, action);
            step++;
        }
        
        System.out.println("Number not found (should not happen)");
    }
    
    /**
     * Performance comparison for different approaches
     */
    public void compareApproaches(int n, int pick) {
        System.out.println("\nPerformance Comparison for n = " + n + ", pick = " + pick);
        System.out.println("=============================================");
        
        long startTime, endTime;
        int result;
        
        // Binary Search
        startTime = System.nanoTime();
        result = guessNumber(n);
        endTime = System.nanoTime();
        System.out.printf("Binary Search: %d ns, Result: %d%n", (endTime - startTime), result);
        
        // Early Return
        startTime = System.nanoTime();
        result = guessNumberEarlyReturn(n);
        endTime = System.nanoTime();
        System.out.printf("Early Return:  %d ns, Result: %d%n", (endTime - startTime), result);
        
        // Recursive
        startTime = System.nanoTime();
        result = guessNumberRecursive(n);
        endTime = System.nanoTime();
        System.out.printf("Recursive:     %d ns, Result: %d%n", (endTime - startTime), result);
        
        // Ternary Search
        startTime = System.nanoTime();
        result = guessNumberTernary(n);
        endTime = System.nanoTime();
        System.out.printf("Ternary Search:%d ns, Result: %d%n", (endTime - startTime), result);
        
        // Linear Search (only for small n)
        if (n <= 1000) {
            startTime = System.nanoTime();
            result = guessNumberLinear(n);
            endTime = System.nanoTime();
            System.out.printf("Linear Search: %d ns, Result: %d%n", (endTime - startTime), result);
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        // Create test instance
        Solution solution = new Solution();
        
        System.out.println("Testing Guess Number Higher or Lower:");
        System.out.println("=====================================");
        
        // Test case 1: Standard example
        System.out.println("\nTest 1: Standard example (n=10, pick=6)");
        solution.setPick(6);
        int result1 = solution.guessNumber(10);
        System.out.println("Result: " + result1 + " - " + (result1 == 6 ? "PASSED" : "FAILED"));
        solution.visualizeBinarySearch(10, 6);
        
        // Test case 2: Single element
        System.out.println("\nTest 2: Single element (n=1, pick=1)");
        solution.setPick(1);
        int result2 = solution.guessNumber(1);
        System.out.println("Result: " + result2 + " - " + (result2 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 3: Two elements, pick first
        System.out.println("\nTest 3: Two elements, pick first (n=2, pick=1)");
        solution.setPick(1);
        int result3 = solution.guessNumber(2);
        System.out.println("Result: " + result3 + " - " + (result3 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 4: Two elements, pick second
        System.out.println("\nTest 4: Two elements, pick second (n=2, pick=2)");
        solution.setPick(2);
        int result4 = solution.guessNumber(2);
        System.out.println("Result: " + result4 + " - " + (result4 == 2 ? "PASSED" : "FAILED"));
        
        // Test case 5: Large n, pick in middle
        System.out.println("\nTest 5: Large n, pick in middle (n=1000000, pick=500000)");
        solution.setPick(500000);
        int result5 = solution.guessNumber(1000000);
        System.out.println("Result: " + result5 + " - " + (result5 == 500000 ? "PASSED" : "FAILED"));
        
        // Test case 6: Large n, pick at beginning
        System.out.println("\nTest 6: Large n, pick at beginning (n=1000000, pick=1)");
        solution.setPick(1);
        int result6 = solution.guessNumber(1000000);
        System.out.println("Result: " + result6 + " - " + (result6 == 1 ? "PASSED" : "FAILED"));
        
        // Test case 7: Large n, pick at end
        System.out.println("\nTest 7: Large n, pick at end (n=1000000, pick=1000000)");
        solution.setPick(1000000);
        int result7 = solution.guessNumber(1000000);
        System.out.println("Result: " + result7 + " - " + (result7 == 1000000 ? "PASSED" : "FAILED"));
        
        // Test case 8: Maximum constraint test
        System.out.println("\nTest 8: Maximum constraint (n=Integer.MAX_VALUE, pick=Integer.MAX_VALUE/2)");
        solution.setPick(Integer.MAX_VALUE / 2);
        int result8 = solution.guessNumber(Integer.MAX_VALUE);
        System.out.println("Result: " + result8 + " - " + (result8 == Integer.MAX_VALUE / 2 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        solution.setPick(42);
        solution.compareApproaches(100, 42);
        
        System.out.println("\nTest 10: Large Scale Performance");
        solution.setPick(123456);
        solution.compareApproaches(1000000, 123456);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BINARY SEARCH ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Insight:");
        System.out.println("The guess API provides the comparison function for binary search.");
        System.out.println("We search in the range [1, n] and use the API response to");
        System.out.println("determine whether to search left or right.");
        
        System.out.println("\nWhy binary search works:");
        System.out.println("1. The numbers are naturally sorted from 1 to n");
        System.out.println("2. The guess API tells us if we're too high or too low");
        System.out.println("3. Each guess eliminates half of the remaining possibilities");
        System.out.println("4. For n=1,000,000: maximum 20 guesses vs 1,000,000 for linear");
        
        System.out.println("\nImportant Implementation Details:");
        System.out.println("1. Use 'left + (right - left) / 2' to prevent integer overflow");
        System.out.println("2. The loop condition is 'left <= right' to handle all cases");
        System.out.println("3. Return immediately when guess(mid) == 0");
        System.out.println("4. Update bounds based on API response");
        
        // Algorithm comparison and analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPREHENSIVE ALGORITHM ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Standard Binary Search (RECOMMENDED):");
        System.out.println("   Time: O(log n) - Halves search space each iteration");
        System.out.println("   Space: O(1) - Only constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Initialize left=1, right=n");
        System.out.println("     - While left <= right, calculate mid");
        System.out.println("     - Call guess(mid) to get direction");
        System.out.println("     - Adjust bounds based on API response");
        System.out.println("     - Return when guess(mid) == 0");
        System.out.println("   Pros:");
        System.out.println("     - Optimal O(log n) time complexity");
        System.out.println("     - O(1) space complexity");
        System.out.println("     - Simple and efficient");
        System.out.println("   Cons:");
        System.out.println("     - None for this problem");
        System.out.println("   Best for: All cases, interview settings");
        
        System.out.println("\n2. Binary Search with Early Return:");
        System.out.println("   Time: O(log n) - Same as standard");
        System.out.println("   Space: O(1) - Same as standard");
        System.out.println("   How it works:");
        System.out.println("     - Check edge case n=1 first");
        System.out.println("     - Then perform standard binary search");
        System.out.println("   Pros:");
        System.out.println("     - Handles single element case efficiently");
        System.out.println("     - Same performance as standard");
        System.out.println("   Cons:");
        System.out.println("     - Minor complexity increase");
        System.out.println("   Best for: When single element case is common");
        
        System.out.println("\n3. Ternary Search (THEORETICAL):");
        System.out.println("   Time: O(log₃ n) - Faster theoretical bound");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Divide search space into three parts");
        System.out.println("     - Make two API calls per iteration");
        System.out.println("     - Eliminate one third of search space");
        System.out.println("   Pros:");
        System.out.println("     - Better theoretical time complexity");
        System.out.println("     - Demonstrates alternative approach");
        System.out.println("   Cons:");
        System.out.println("     - Worse constant factors in practice");
        System.out.println("     - More API calls per iteration");
        System.out.println("   Best for: Learning, theoretical discussion");
        
        System.out.println("\n4. Linear Search (NOT RECOMMENDED):");
        System.out.println("   Time: O(n) - Worst case scans entire range");
        System.out.println("   Space: O(1) - Constant extra space");
        System.out.println("   How it works:");
        System.out.println("     - Iterate from 1 to n");
        System.out.println("     - Call guess(i) for each number");
        System.out.println("     - Return when guess(i) == 0");
        System.out.println("   Pros:");
        System.out.println("     - Extremely simple to implement");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - O(n) time doesn't scale");
        System.out.println("     - Fails for large n (2^31-1)");
        System.out.println("   Best for: Very small n, educational purposes");
        
        System.out.println("\n5. Recursive Binary Search:");
        System.out.println("   Time: O(log n) - Same as iterative");
        System.out.println("   Space: O(log n) - Recursion stack depth");
        System.out.println("   How it works:");
        System.out.println("     - Recursively search left or right half");
        System.out.println("     - Base case: left > right");
        System.out.println("   Pros:");
        System.out.println("     - Elegant recursive implementation");
        System.out.println("     - Natural divide-and-conquer expression");
        System.out.println("   Cons:");
        System.out.println("     - O(log n) space due to recursion");
        System.out.println("     - Risk of stack overflow for very large n");
        System.out.println("   Best for: Small to medium n, learning recursion");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MATHEMATICAL INSIGHTS:");
        System.out.println("1. Binary search iterations: ⌊log₂(n)⌋ + 1");
        System.out.println("2. For n=1,000,000: maximum 20 iterations");
        System.out.println("3. For n=2^31-1: maximum 31 iterations");
        System.out.println("4. Linear search would require up to 2^31-1 iterations");
        System.out.println("5. Binary search is exponentially more efficient");
        System.out.println("=".repeat(70));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Immediately identify this as a binary search problem");
        System.out.println("2. Explain why binary search is appropriate (sorted range, API guidance)");
        System.out.println("3. Implement standard binary search with proper bounds");
        System.out.println("4. Mention integer overflow prevention in mid calculation");
        System.out.println("5. Discuss time/space complexity trade-offs");
        System.out.println("6. Handle edge cases: n=1, pick at boundaries");
        System.out.println("=".repeat(70));
        
        System.out.println("\nAll tests completed!");
    }
}

/**
 * Mock GuessGame class for testing purposes
 * In the actual LeetCode problem, this class is provided
 */
class GuessGame {
    private int pick;
    
    public void setPick(int pick) {
        this.pick = pick;
    }
    
    public int guess(int num) {
        if (num > pick) {
            return -1;
        } else if (num < pick) {
            return 1;
        } else {
            return 0;
        }
    }
}
