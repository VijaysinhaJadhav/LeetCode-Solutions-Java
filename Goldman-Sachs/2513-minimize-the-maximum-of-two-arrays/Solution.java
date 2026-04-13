
# Solution.java

```java
/**
 * 2513. Minimize the Maximum of Two Arrays
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Find minimum maximum integer such that we can select uniqueCnt1 numbers
 * not divisible by divisor1 and uniqueCnt2 numbers not divisible by divisor2,
 * with no overlap between the two sets.
 * 
 * Key Insights:
 * 1. Binary search on the maximum value
 * 2. For a value x, count numbers <= x not divisible by a divisor
 * 3. Subtract numbers divisible by both divisors (LCM)
 * 4. Check if we can pick required counts
 */
class Solution {
    
    /**
     * Approach 1: Binary Search with Mathematical Counting (Recommended)
     * Time: O(log(answer)), Space: O(1)
     * 
     * Steps:
     * 1. Binary search on maximum value from 2 to 2e9
     * 2. For each mid, compute:
     *    - count1 = numbers <= mid not divisible by divisor1
     *    - count2 = numbers <= mid not divisible by divisor2
     *    - overlap = numbers divisible by both (lcm)
     * 3. Numbers available for arr1 = count1 - overlap/?
     *    Actually, numbers divisible by both cannot be used by either
     * 4. Check if we can select uniqueCnt1 and uniqueCnt2
     */
    public int minimizeSet(int divisor1, int divisor2, int uniqueCnt1, int uniqueCnt2) {
        long left = 1;
        long right = (long) 2e9;
        long lcm = lcm(divisor1, divisor2);
        
        while (left < right) {
            long mid = left + (right - left) / 2;
            
            // Count numbers not divisible by divisor1
            long notDiv1 = mid - mid / divisor1;
            // Count numbers not divisible by divisor2
            long notDiv2 = mid - mid / divisor2;
            // Count numbers divisible by both (cannot be used by either)
            long divisibleByBoth = mid / lcm;
            // Numbers available for both (not divisible by either)
            long availableForBoth = mid - (mid / divisor1 + mid / divisor2 - mid / lcm);
            
            // We need to assign numbers to arr1 and arr2
            // First, give numbers that are only good for one array
            long onlyForArr1 = notDiv1 - availableForBoth;
            long onlyForArr2 = notDiv2 - availableForBoth;
            
            // After giving only-for-one numbers, use shared numbers for remaining
            long remaining1 = Math.max(0, uniqueCnt1 - onlyForArr1);
            long remaining2 = Math.max(0, uniqueCnt2 - onlyForArr2);
            
            if (remaining1 + remaining2 <= availableForBoth) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return (int) left;
    }
    
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
    
    /**
     * Approach 2: Binary Search with Inclusion-Exclusion (Simpler)
     * Time: O(log(answer)), Space: O(1)
     */
    public int minimizeSetSimpler(int divisor1, int divisor2, int uniqueCnt1, int uniqueCnt2) {
        long left = 1;
        long right = (long) 2e9;
        long lcm = lcm(divisor1, divisor2);
        
        while (left < right) {
            long mid = left + (right - left) / 2;
            
            // Numbers that can go to arr1 (not divisible by divisor1)
            long count1 = mid - mid / divisor1;
            // Numbers that can go to arr2 (not divisible by divisor2)
            long count2 = mid - mid / divisor2;
            // Numbers divisible by both (cannot be used by either)
            long common = mid / lcm;
            
            // Total available numbers (not divisible by divisor1 OR not divisible by divisor2)
            // Actually, we need: numbers that can be assigned to either array
            // After subtracting common numbers that can't be used by anyone
            long totalAvailable = count1 + count2 - common;
            
            // We need to select uniqueCnt1 + uniqueCnt2 numbers total
            if (count1 >= uniqueCnt1 && count2 >= uniqueCnt2 && 
                count1 + count2 - common >= uniqueCnt1 + uniqueCnt2) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return (int) left;
    }
    
    /**
     * Approach 3: Binary Search with Direct Counting
     * Time: O(log(answer)), Space: O(1)
     */
    public int minimizeSetDirect(int divisor1, int divisor2, int uniqueCnt1, int uniqueCnt2) {
        long left = 1;
        long right = (long) 2e9;
        long lcm = lcm(divisor1, divisor2);
        
        while (left < right) {
            long mid = left + (right - left) / 2;
            
            // Numbers that can go to arr1
            long canTake1 = mid - mid / divisor1;
            // Numbers that can go to arr2
            long canTake2 = mid - mid / divisor2;
            // Numbers that can go to both (not divisible by either)
            long canTakeBoth = mid - mid / divisor1 - mid / divisor2 + mid / lcm;
            
            // We need at least uniqueCnt1 from canTake1
            // and at least uniqueCnt2 from canTake2
            // But numbers in canTakeBoth can be used for either
            if (canTake1 >= uniqueCnt1 && canTake2 >= uniqueCnt2) {
                // Check if we have enough total
                long neededTotal = uniqueCnt1 + uniqueCnt2;
                long availableTotal = canTake1 + canTake2 - canTakeBoth;
                
                if (availableTotal >= neededTotal) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else {
                left = mid + 1;
            }
        }
        
        return (int) left;
    }
    
    /**
     * Helper: Brute force verification for small values
     */
    public int bruteForce(int divisor1, int divisor2, int uniqueCnt1, int uniqueCnt2) {
        for (int maxVal = 1; maxVal <= 1000; maxVal++) {
            long count1 = 0;
            long count2 = 0;
            
            for (int i = 1; i <= maxVal; i++) {
                if (i % divisor1 != 0) count1++;
                if (i % divisor2 != 0) count2++;
            }
            
            if (count1 >= uniqueCnt1 && count2 >= uniqueCnt2) {
                // Check if we can assign uniquely
                long bothNotDivisible = 0;
                for (int i = 1; i <= maxVal; i++) {
                    if (i % divisor1 != 0 && i % divisor2 != 0) {
                        bothNotDivisible++;
                    }
                }
                
                long only1 = count1 - bothNotDivisible;
                long only2 = count2 - bothNotDivisible;
                
                long need1 = Math.max(0, uniqueCnt1 - only1);
                long need2 = Math.max(0, uniqueCnt2 - only2);
                
                if (need1 + need2 <= bothNotDivisible) {
                    return maxVal;
                }
            }
        }
        return -1;
    }
    
    /**
     * Helper: Visualize the binary search process
     */
    public void visualizeSearch(int divisor1, int divisor2, int uniqueCnt1, int uniqueCnt2) {
        System.out.println("\nMinimize the Maximum of Two Arrays Visualization:");
        System.out.println("=".repeat(70));
        
        System.out.printf("\nInput: divisor1=%d, divisor2=%d, uniqueCnt1=%d, uniqueCnt2=%d%n",
            divisor1, divisor2, uniqueCnt1, uniqueCnt2);
        
        long left = 1;
        long right = (long) 2e9;
        long lcm = lcm(divisor1, divisor2);
        
        System.out.println("\nBinary Search Process:");
        System.out.println("Step | Left | Right | Mid | Count1 | Count2 | Available | Can Assign?");
        System.out.println("-----|------|-------|-----|--------|--------|-----------|-------------");
        
        int step = 1;
        while (left < right) {
            long mid = left + (right - left) / 2;
            
            long count1 = mid - mid / divisor1;
            long count2 = mid - mid / divisor2;
            long bothDivisible = mid / lcm;
            long canTakeBoth = mid - mid / divisor1 - mid / divisor2 + bothDivisible;
            long availableTotal = count1 + count2 - canTakeBoth;
            
            boolean canAssign = count1 >= uniqueCnt1 && count2 >= uniqueCnt2 && 
                               availableTotal >= uniqueCnt1 + uniqueCnt2;
            
            System.out.printf("%4d | %4d | %5d | %3d | %6d | %6d | %9d | %s%n",
                step++, left, right, mid, count1, count2, availableTotal, canAssign ? "YES" : "NO");
            
            if (canAssign) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        System.out.println("\nResult: " + left);
    }
    
    /**
     * Helper: Run test cases
     */
    public void runTestCases() {
        System.out.println("Running Test Cases:");
        System.out.println("=".repeat(50));
        
        int[][][] testCases = {
            {{2, 3, 1, 2}, {4}},
            {{2, 4, 3, 2}, {6}},
            {{3, 5, 2, 3}, {7}},
            {{5, 7, 3, 2}, {9}},
            {{2, 2, 3, 3}, {8}},
            {{3, 3, 5, 5}, {15}}
        };
        
        int passed = 0;
        
        for (int i = 0; i < testCases.length; i++) {
            int d1 = testCases[i][0][0];
            int d2 = testCases[i][0][1];
            int cnt1 = testCases[i][0][2];
            int cnt2 = testCases[i][0][3];
            int expected = testCases[i][1][0];
            
            System.out.printf("\nTest %d: divisor1=%d, divisor2=%d, cnt1=%d, cnt2=%d%n",
                i + 1, d1, d2, cnt1, cnt2);
            
            int result1 = minimizeSet(d1, d2, cnt1, cnt2);
            int result2 = minimizeSetSimpler(d1, d2, cnt1, cnt2);
            int result3 = minimizeSetDirect(d1, d2, cnt1, cnt2);
            
            boolean allMatch = result1 == expected && result2 == expected && result3 == expected;
            
            if (allMatch) {
                System.out.println("✓ PASS - Result: " + result1);
                passed++;
            } else {
                System.out.println("✗ FAIL - Expected: " + expected);
                System.out.println("  Method 1: " + result1);
                System.out.println("  Method 2: " + result2);
                System.out.println("  Method 3: " + result3);
            }
            
            // Visualize first test case
            if (i == 0) {
                visualizeSearch(d1, d2, cnt1, cnt2);
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Summary: " + passed + "/" + testCases.length + " tests passed");
    }
    
    /**
     * Helper: Test edge cases
     */
    public void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Equal divisors:");
        System.out.println("   minimizeSet(2,2,3,3) = " + minimizeSet(2, 2, 3, 3));
        
        System.out.println("\n2. One divisor divides the other:");
        System.out.println("   minimizeSet(2,4,5,5) = " + minimizeSet(2, 4, 5, 5));
        
        System.out.println("\n3. Large counts:");
        System.out.println("   minimizeSet(2,3,1000000,1000000) = " + 
            minimizeSet(2, 3, 1000000, 1000000));
        
        System.out.println("\n4. Minimum values:");
        System.out.println("   minimizeSet(2,3,1,1) = " + minimizeSet(2, 3, 1, 1));
        
        System.out.println("\n5. Maximum divisors:");
        long start = System.currentTimeMillis();
        int result = minimizeSet(100000, 99991, 1000000, 1000000);
        long time = System.currentTimeMillis() - start;
        System.out.println("   minimizeSet(100000,99991,1e6,1e6) = " + result);
        System.out.println("   Time: " + time + "ms");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("2513. Minimize the Maximum of Two Arrays");
        System.out.println("=======================================");
        
        // Run test cases
        solution.runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        solution.testEdgeCases();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class Solution {
    public int minimizeSet(int divisor1, int divisor2, int uniqueCnt1, int uniqueCnt2) {
        long left = 1;
        long right = (long) 2e9;
        long lcm = lcm(divisor1, divisor2);
        
        while (left < right) {
            long mid = left + (right - left) / 2;
            
            long notDiv1 = mid - mid / divisor1;
            long notDiv2 = mid - mid / divisor2;
            long divisibleByBoth = mid / lcm;
            long availableForBoth = mid - (mid / divisor1 + mid / divisor2 - mid / lcm);
            
            long onlyForArr1 = notDiv1 - availableForBoth;
            long onlyForArr2 = notDiv2 - availableForBoth;
            
            long remaining1 = Math.max(0, uniqueCnt1 - onlyForArr1);
            long remaining2 = Math.max(0, uniqueCnt2 - onlyForArr2);
            
            if (remaining1 + remaining2 <= availableForBoth) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return (int) left;
    }
    
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. Use binary search on the maximum value");
        System.out.println("2. Count numbers not divisible by each divisor");
        System.out.println("3. Use inclusion-exclusion to handle overlap");
        System.out.println("4. Numbers divisible by both divisors cannot be used");
        System.out.println("5. Check if we can assign required counts");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(log(answer)) ≈ 31 iterations");
        System.out.println("- Space: O(1)");
    }
}
