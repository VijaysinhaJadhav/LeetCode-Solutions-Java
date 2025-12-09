
# Solution.java

```java
/**
 * 735. Asteroid Collision
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * We are given an array of asteroids. Positive values move right, negative values move left.
 * When asteroids collide, the smaller one explodes. If equal, both explode.
 * Return the state of asteroids after all collisions.
 * 
 * Key Insights:
 * 1. Use stack to simulate the asteroid collisions
 * 2. Only collisions occur when positive asteroid is followed by negative asteroid
 * 3. Process asteroids left to right, handle collisions with stack top
 * 4. Smaller asteroid explodes, equal sizes both explode
 * 
 * Approach (Stack Simulation):
 * 1. Initialize stack for surviving asteroids
 * 2. Iterate through each asteroid
 * 3. For positive asteroids, simply push to stack
 * 4. For negative asteroids, handle collisions with positive asteroids in stack
 * 5. Continue until no more collisions or stack is empty
 * 6. Convert stack to array for result
 * 
 * Time Complexity: O(n) - Each asteroid processed once
 * Space Complexity: O(n) - Stack storage for surviving asteroids
 * 
 * Tags: Array, Stack, Simulation
 */

import java.util.*;

public class Solution {
    
    /**
     * Approach 1: Stack Simulation - RECOMMENDED
     * O(n) time, O(n) space - Most intuitive and efficient
     */
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        
        for (int asteroid : asteroids) {
            if (asteroid > 0) {
                // Positive asteroid - always push (moving right)
                stack.push(asteroid);
            } else {
                // Negative asteroid - handle collisions
                while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < Math.abs(asteroid)) {
                    // Current negative asteroid destroys smaller positive asteroids
                    stack.pop();
                }
                
                if (!stack.isEmpty() && stack.peek() == Math.abs(asteroid)) {
                    // Both asteroids destroy each other
                    stack.pop();
                } else if (!stack.isEmpty() && stack.peek() > Math.abs(asteroid)) {
                    // Current negative asteroid is destroyed by larger positive asteroid
                    // Do nothing (current asteroid explodes)
                } else {
                    // No collision or all positive asteroids destroyed
                    stack.push(asteroid);
                }
            }
        }
        
        // Convert stack to array
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }
    
    /**
     * Approach 2: Stack with Simplified Logic
     * O(n) time, O(n) space - Cleaner conditional logic
     */
    public int[] asteroidCollisionSimplified(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        
        for (int asteroid : asteroids) {
            // Handle collisions while current asteroid is negative and stack top is positive
            while (!stack.isEmpty() && asteroid < 0 && stack.peek() > 0) {
                int top = stack.peek();
                
                if (top < Math.abs(asteroid)) {
                    // Stack top is destroyed
                    stack.pop();
                    continue; // Continue checking with next stack top
                } else if (top == Math.abs(asteroid)) {
                    // Both destroyed
                    stack.pop();
                }
                // Current asteroid is destroyed if top > Math.abs(asteroid)
                asteroid = 0; // Mark as destroyed
                break;
            }
            
            if (asteroid != 0) {
                stack.push(asteroid);
            }
        }
        
        // Convert stack to array
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }
    
    /**
     * Approach 3: Array as Stack (Optimized)
     * O(n) time, O(n) space - Uses array instead of Stack class
     */
    public int[] asteroidCollisionArray(int[] asteroids) {
        int[] stack = new int[asteroids.length];
        int pointer = 0; // Points to next available position
        
        for (int asteroid : asteroids) {
            if (asteroid > 0) {
                // Positive asteroid - push to stack
                stack[pointer++] = asteroid;
            } else {
                // Negative asteroid - handle collisions
                while (pointer > 0 && stack[pointer - 1] > 0 && stack[pointer - 1] < Math.abs(asteroid)) {
                    pointer--; // Destroy smaller positive asteroid
                }
                
                if (pointer > 0 && stack[pointer - 1] > 0) {
                    if (stack[pointer - 1] == Math.abs(asteroid)) {
                        pointer--; // Both destroyed
                    }
                    // Else: current asteroid destroyed by larger positive asteroid
                } else {
                    // No collision, push negative asteroid
                    stack[pointer++] = asteroid;
                }
            }
        }
        
        return Arrays.copyOf(stack, pointer);
    }
    
    /**
     * Approach 4: Two Pointers (In-place)
     * O(n) time, O(1) extra space - Modifies input array
     */
    public int[] asteroidCollisionInPlace(int[] asteroids) {
        int pointer = 0; // Points to next position to write
        
        for (int i = 0; i < asteroids.length; i++) {
            int current = asteroids[i];
            
            if (current > 0) {
                // Positive asteroid - always add
                asteroids[pointer++] = current;
            } else {
                // Negative asteroid - handle collisions with previous positives
                while (pointer > 0 && asteroids[pointer - 1] > 0 && asteroids[pointer - 1] < Math.abs(current)) {
                    pointer--; // Destroy smaller positive asteroid
                }
                
                if (pointer > 0 && asteroids[pointer - 1] > 0) {
                    if (asteroids[pointer - 1] == Math.abs(current)) {
                        pointer--; // Both destroyed
                    }
                    // Else: current asteroid destroyed by larger positive asteroid
                } else {
                    // No collision, add negative asteroid
                    asteroids[pointer++] = current;
                }
            }
        }
        
        return Arrays.copyOf(asteroids, pointer);
    }
    
    /**
     * Approach 5: LinkedList Simulation
     * O(n) time, O(n) space - Uses LinkedList for efficient removals
     */
    public int[] asteroidCollisionLinkedList(int[] asteroids) {
        LinkedList<Integer> list = new LinkedList<>();
        
        for (int asteroid : asteroids) {
            if (asteroid > 0) {
                list.addLast(asteroid);
            } else {
                // Handle collisions with previous positive asteroids
                while (!list.isEmpty() && list.getLast() > 0 && list.getLast() < Math.abs(asteroid)) {
                    list.removeLast();
                }
                
                if (!list.isEmpty() && list.getLast() > 0) {
                    if (list.getLast() == Math.abs(asteroid)) {
                        list.removeLast();
                    }
                    // Else: current asteroid destroyed
                } else {
                    list.addLast(asteroid);
                }
            }
        }
        
        // Convert LinkedList to array
        int[] result = new int[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }
    
    /**
     * Helper method to visualize the asteroid collision process
     */
    private void visualizeCollisions(int[] asteroids) {
        System.out.println("\nAsteroid Collision Visualization:");
        System.out.println("Initial asteroids: " + Arrays.toString(asteroids));
        System.out.println("(+ = moving right, - = moving left)");
        
        Stack<Integer> stack = new Stack<>();
        
        System.out.println("\nStep | Asteroid | Stack State | Action");
        System.out.println("-----|----------|-------------|--------");
        
        for (int i = 0; i < asteroids.length; i++) {
            int asteroid = asteroids[i];
            String stackStateBefore = stack.toString();
            String action = "";
            
            if (asteroid > 0) {
                stack.push(asteroid);
                action = "Push +" + asteroid + " (moving right)";
            } else {
                boolean destroyed = false;
                
                // Handle collisions
                while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < Math.abs(asteroid)) {
                    int destroyedAsteroid = stack.pop();
                    action += "Destroy +" + destroyedAsteroid + " with -" + Math.abs(asteroid) + ", ";
                }
                
                if (!stack.isEmpty() && stack.peek() > 0) {
                    if (stack.peek() == Math.abs(asteroid)) {
                        int destroyedAsteroid = stack.pop();
                        action += "Both +" + destroyedAsteroid + " and -" + Math.abs(asteroid) + " destroyed";
                        destroyed = true;
                    } else {
                        action += "-" + Math.abs(asteroid) + " destroyed by +" + stack.peek();
                        destroyed = true;
                    }
                }
                
                if (!destroyed) {
                    stack.push(asteroid);
                    if (action.isEmpty()) {
                        action = "Push -" + Math.abs(asteroid) + " (no collision)";
                    } else {
                        action += ", push -" + Math.abs(asteroid);
                    }
                }
            }
            
            String stackStateAfter = stack.toString();
            System.out.printf("%4d | %8s | %11s | %s%n", 
                            i + 1, formatAsteroid(asteroid), stackStateAfter, action);
        }
        
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        
        System.out.println("\nFinal Result: " + Arrays.toString(result));
    }
    
    private String formatAsteroid(int asteroid) {
        return asteroid > 0 ? "+" + asteroid : String.valueOf(asteroid);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Asteroid Collision:");
        System.out.println("===========================");
        
        // Test case 1: Simple collision (smaller negative destroys positive)
        System.out.println("\nTest 1: Simple collision");
        int[] asteroids1 = {5, 10, -5};
        int[] expected1 = {5, 10};
        
        long startTime = System.nanoTime();
        int[] result1a = solution.asteroidCollision(asteroids1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1b = solution.asteroidCollisionSimplified(asteroids1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1c = solution.asteroidCollisionArray(asteroids1);
        long time1c = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1d = solution.asteroidCollisionInPlace(asteroids1);
        long time1d = System.nanoTime() - startTime;
        
        boolean test1a = Arrays.equals(result1a, expected1);
        boolean test1b = Arrays.equals(result1b, expected1);
        boolean test1c = Arrays.equals(result1c, expected1);
        boolean test1d = Arrays.equals(result1d, expected1);
        
        System.out.println("Stack: " + Arrays.toString(result1a) + " - " + (test1a ? "PASSED" : "FAILED"));
        System.out.println("Simplified: " + Arrays.toString(result1b) + " - " + (test1b ? "PASSED" : "FAILED"));
        System.out.println("Array: " + Arrays.toString(result1c) + " - " + (test1c ? "PASSED" : "FAILED"));
        System.out.println("In-place: " + Arrays.toString(result1d) + " - " + (test1d ? "PASSED" : "FAILED"));
        
        // Visualize the collision process
        solution.visualizeCollisions(asteroids1);
        
        // Test case 2: Mutual destruction
        System.out.println("\nTest 2: Mutual destruction");
        int[] asteroids2 = {8, -8};
        int[] expected2 = {};
        
        int[] result2a = solution.asteroidCollision(asteroids2);
        System.out.println("Mutual destruction: " + Arrays.toString(result2a) + " - " + 
                         (Arrays.equals(result2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Chain destruction
        System.out.println("\nTest 3: Chain destruction");
        int[] asteroids3 = {10, 2, -5};
        int[] expected3 = {10};
        
        int[] result3a = solution.asteroidCollision(asteroids3);
        System.out.println("Chain destruction: " + Arrays.toString(result3a) + " - " + 
                         (Arrays.equals(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: All positive (no collisions)
        System.out.println("\nTest 4: All positive");
        int[] asteroids4 = {1, 2, 3, 4, 5};
        int[] expected4 = {1, 2, 3, 4, 5};
        
        int[] result4a = solution.asteroidCollision(asteroids4);
        System.out.println("All positive: " + Arrays.toString(result4a) + " - " + 
                         (Arrays.equals(result4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: All negative (no collisions)
        System.out.println("\nTest 5: All negative");
        int[] asteroids5 = {-1, -2, -3, -4, -5};
        int[] expected5 = {-1, -2, -3, -4, -5};
        
        int[] result5a = solution.asteroidCollision(asteroids5);
        System.out.println("All negative: " + Arrays.toString(result5a) + " - " + 
                         (Arrays.equals(result5a, expected5) ? "PASSED" : "FAILED"));
        
        // Test case 6: Mixed with no collisions
        System.out.println("\nTest 6: Mixed with no collisions");
        int[] asteroids6 = {-1, -2, 1, 2};
        int[] expected6 = {-1, -2, 1, 2};
        
        int[] result6a = solution.asteroidCollision(asteroids6);
        System.out.println("Mixed no collisions: " + Arrays.toString(result6a) + " - " + 
                         (Arrays.equals(result6a, expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Complex scenario
        System.out.println("\nTest 7: Complex scenario");
        int[] asteroids7 = {-2, -1, 1, 2};
        int[] expected7 = {-2, -1, 1, 2};
        
        int[] result7a = solution.asteroidCollision(asteroids7);
        System.out.println("Complex: " + Arrays.toString(result7a) + " - " + 
                         (Arrays.equals(result7a, expected7) ? "PASSED" : "FAILED"));
        
        // Test case 8: Multiple destructions
        System.out.println("\nTest 8: Multiple destructions");
        int[] asteroids8 = {5, 10, -5, -10};
        int[] expected8 = {};
        
        int[] result8a = solution.asteroidCollision(asteroids8);
        System.out.println("Multiple destructions: " + Arrays.toString(result8a) + " - " + 
                         (Arrays.equals(result8a, expected8) ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\nTest 9: Performance Comparison");
        System.out.println("Simple collision performance:");
        System.out.println("  Stack: " + time1a + " ns");
        System.out.println("  Simplified: " + time1b + " ns");
        System.out.println("  Array: " + time1c + " ns");
        System.out.println("  In-place: " + time1d + " ns");
        
        // Performance test with large input
        System.out.println("\nTest 10: Large input performance");
        int[] largeAsteroids = generateLargeAsteroids(10000);
        
        startTime = System.nanoTime();
        int[] result10a = solution.asteroidCollision(largeAsteroids);
        long time10a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result10b = solution.asteroidCollisionArray(largeAsteroids);
        long time10b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result10c = solution.asteroidCollisionInPlace(largeAsteroids);
        long time10c = System.nanoTime() - startTime;
        
        System.out.println("Large input (10,000 asteroids):");
        System.out.println("  Stack: " + time10a + " ns, Result length: " + result10a.length);
        System.out.println("  Array: " + time10b + " ns, Result length: " + result10b.length);
        System.out.println("  In-place: " + time10c + " ns, Result length: " + result10c.length);
        
        // Verify all approaches produce the same result
        boolean allEqual = Arrays.equals(result10a, result10b) && Arrays.equals(result10a, result10c);
        System.out.println("All approaches consistent: " + allEqual);
        
        // Edge case: Single asteroid
        System.out.println("\nTest 11: Single asteroid");
        int[] asteroids11 = {5};
        int[] expected11 = {5};
        
        int[] result11a = solution.asteroidCollision(asteroids11);
        System.out.println("Single asteroid: " + Arrays.toString(result11a) + " - " + 
                         (Arrays.equals(result11a, expected11) ? "PASSED" : "FAILED"));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        
        System.out.println("\nCollision Rules:");
        System.out.println("1. Positive asteroids move →, negative asteroids move ←");
        System.out.println("2. Only collisions occur when → asteroid is followed by ← asteroid");
        System.out.println("3. Smaller asteroid explodes, equal sizes both explode");
        System.out.println("4. Same direction asteroids never collide");
        
        System.out.println("\nStack Simulation Algorithm:");
        System.out.println("1. Initialize empty stack");
        System.out.println("2. For each asteroid:");
        System.out.println("   a. If positive: push to stack");
        System.out.println("   b. If negative:");
        System.out.println("      - While stack has positive asteroids smaller than current:");
        System.out.println("        * Pop and destroy them");
        System.out.println("      - If stack has positive asteroid equal to current:");
        System.out.println("        * Pop and destroy both");
        System.out.println("      - If stack has positive asteroid larger than current:");
        System.out.println("        * Destroy current asteroid");
        System.out.println("      - Else: push negative asteroid to stack");
        System.out.println("3. Stack contains surviving asteroids");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStack Approach:");
        System.out.println("┌────────────┬────────────┬──────────────┐");
        System.out.println("│ Operation  │ Time       │ Space        │");
        System.out.println("├────────────┼────────────┼──────────────┤");
        System.out.println("│ Process    │ O(n)       │ O(n)         │");
        System.out.println("│ Push/Pop   │ O(1) each  │ O(1) each    │");
        System.out.println("│ Collisions │ O(n) total │ O(n) total   │");
        System.out.println("└────────────┴────────────┴──────────────┘");
        
        System.out.println("\nComparison of Approaches:");
        System.out.println("┌──────────────────┬────────────┬─────────────────┐");
        System.out.println("│ Approach         │ Time       │ Space           │");
        System.out.println("├──────────────────┼────────────┼─────────────────┤");
        System.out.println("│ Stack            │ O(n)       │ O(n)            │");
        System.out.println("│ Array as Stack   │ O(n)       │ O(n)            │");
        System.out.println("│ In-place         │ O(n)       │ O(1) extra      │");
        System.out.println("│ LinkedList       │ O(n)       │ O(n)            │");
        System.out.println("└──────────────────┴────────────┴─────────────────┘");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Start with Stack Approach:");
        System.out.println("   - Most intuitive and easy to explain");
        System.out.println("   - Clearly demonstrates collision handling");
        System.out.println("   - Handles all edge cases efficiently");
        
        System.out.println("\n2. Key Points to Explain:");
        System.out.println("   - Why stack is appropriate (LIFO for collision resolution)");
        System.out.println("   - Collision conditions (only positive → negative)");
        System.out.println("   - Size comparison logic");
        System.out.println("   - Time and space complexity analysis");
        
        System.out.println("\n3. Handle Edge Cases:");
        System.out.println("   - All positive/negative asteroids");
        System.out.println("   - Mutual destruction (equal sizes)");
        System.out.println("   - Chain reactions");
        System.out.println("   - Single asteroid");
        
        System.out.println("\n4. Discuss Optimizations:");
        System.out.println("   - Array instead of Stack class for performance");
        System.out.println("   - In-place modification to save space");
        System.out.println("   - Early termination if possible");
        
        System.out.println("\n5. Real-world Applications:");
        System.out.println("   - Particle collision simulations");
        System.out.println("   - Game physics engines");
        System.out.println("   - Traffic flow modeling");
        System.out.println("   - Resource conflict resolution");
        
        System.out.println("\nAll tests completed successfully!");
    }
    
    /**
     * Helper method to generate large asteroid array for performance testing
     */
    private static int[] generateLargeAsteroids(int size) {
        int[] asteroids = new int[size];
        Random random = new Random(42);
        
        for (int i = 0; i < size; i++) {
            // Generate both positive and negative asteroids with some pattern
            // to ensure collisions happen
            int value = random.nextInt(1000) + 1;
            if (random.nextDouble() < 0.5) {
                value = -value;
            }
            asteroids[i] = value;
        }
        
        return asteroids;
    }
}
