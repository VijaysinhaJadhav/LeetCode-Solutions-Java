
## Solution.java

```java
/**
 * 621. Task Scheduler
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given a list of tasks and a cooldown period n between same tasks,
 * find the minimum time needed to complete all tasks.
 * 
 * Key Insights:
 * 1. The most frequent task determines the schedule framework
 * 2. Create "frames" of size (n+1) with most frequent task at start
 * 3. Fill remaining slots with other tasks
 * 4. Idle time = empty slots if not enough tasks to fill
 * 5. Mathematical formula: (maxFreq-1)*(n+1) + countOfMaxFreq
 * 
 * Approach (Mathematical Formula):
 * 1. Count frequency of each task
 * 2. Find maximum frequency (maxFreq)
 * 3. Count how many tasks have maxFreq
 * 4. Calculate using formula
 * 5. Handle case when tasks fill all slots (no idle time)
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1) - 26 element array
 * 
 * Tags: Array, Hash Table, Greedy, Sorting, Heap, Counting
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Mathematical Formula (RECOMMENDED)
     * O(n) time, O(1) space
     */
    public int leastInterval(char[] tasks, int n) {
        if (n == 0) return tasks.length;
        
        // Count frequency of each task
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        
        // Find the maximum frequency
        int maxFreq = 0;
        for (int count : freq) {
            maxFreq = Math.max(maxFreq, count);
        }
        
        // Count how many tasks have maximum frequency
        int maxCount = 0;
        for (int count : freq) {
            if (count == maxFreq) {
                maxCount++;
            }
        }
        
        // Calculate using the formula
        // (maxFreq - 1) * (n + 1) gives the framework
        // + maxCount accounts for tasks with max frequency
        int result = (maxFreq - 1) * (n + 1) + maxCount;
        
        // If tasks can fill all slots without idle time
        return Math.max(result, tasks.length);
    }
    
    /**
     * Approach 2: Max Heap Simulation
     * O(n log 26) time, O(1) space
     */
    public int leastIntervalHeap(char[] tasks, int n) {
        if (n == 0) return tasks.length;
        
        // Count frequency of each task
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        
        // Create max heap based on frequency
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int count : freq) {
            if (count > 0) {
                maxHeap.offer(count);
            }
        }
        
        int time = 0;
        // Queue to store tasks that are in cooldown
        Queue<int[]> cooldownQueue = new LinkedList<>(); // [remainingTime, taskCount]
        
        while (!maxHeap.isEmpty() || !cooldownQueue.isEmpty()) {
            time++;
            
            // If there's a task available, schedule it
            if (!maxHeap.isEmpty()) {
                int taskCount = maxHeap.poll();
                taskCount--;
                if (taskCount > 0) {
                    // Task needs to go to cooldown
                    cooldownQueue.offer(new int[]{time + n, taskCount});
                }
            }
            
            // Check if any task is out of cooldown
            while (!cooldownQueue.isEmpty() && cooldownQueue.peek()[0] == time) {
                int[] task = cooldownQueue.poll();
                maxHeap.offer(task[1]);
            }
        }
        
        return time;
    }
    
    /**
     * Approach 3: Greedy with Array Sorting
     * O(n log 26) time, O(1) space
     */
    public int leastIntervalSorting(char[] tasks, int n) {
        if (n == 0) return tasks.length;
        
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        
        // Sort frequencies in descending order
        Arrays.sort(freq);
        
        int maxFreq = freq[25];
        int idleSlots = (maxFreq - 1) * n;
        
        // Try to fill idle slots with other tasks
        for (int i = 24; i >= 0 && freq[i] > 0 && idleSlots > 0; i--) {
            // We can fill min(maxFreq-1, freq[i]) slots
            idleSlots -= Math.min(maxFreq - 1, freq[i]);
        }
        
        idleSlots = Math.max(0, idleSlots);
        return tasks.length + idleSlots;
    }
    
    /**
     * Approach 4: Frame-based Mathematical Approach
     * More intuitive explanation of the formula
     */
    public int leastIntervalFrameBased(char[] tasks, int n) {
        if (n == 0) return tasks.length;
        
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        
        // Find max frequency
        int maxFreq = 0;
        for (int count : freq) {
            maxFreq = Math.max(maxFreq, count);
        }
        
        // Create frames: each frame has n+1 slots
        // First frame: maxFreq tasks at position 0, 1, ..., maxFreq-1
        int frameCount = maxFreq - 1;
        int frameSize = n + 1;
        int totalSlots = frameCount * frameSize;
        
        // Fill frames with other tasks
        // Each task can fill at most (maxFreq-1) slots (except in last frame)
        for (int count : freq) {
            if (count == maxFreq) {
                // Already accounted for in the formula
                continue;
            }
            totalSlots -= Math.min(count, frameCount);
        }
        
        // If totalSlots < 0, it means tasks can fill all slots without idle
        if (totalSlots < 0) {
            return tasks.length;
        }
        
        // Remaining slots are idle time
        return tasks.length + totalSlots;
    }
    
    /**
     * Approach 5: Simulation with Time Slots
     * Most intuitive but less efficient
     */
    public int leastIntervalSimulation(char[] tasks, int n) {
        if (n == 0) return tasks.length;
        
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        
        // Last time each task was scheduled
        int[] lastTime = new int[26];
        Arrays.fill(lastTime, -n - 1); // Initialize so first schedule is always valid
        
        int time = 0;
        int remainingTasks = tasks.length;
        
        while (remainingTasks > 0) {
            // Find the task with highest frequency that's not in cooldown
            int bestTask = -1;
            int bestFreq = -1;
            
            for (int i = 0; i < 26; i++) {
                if (freq[i] > 0 && time - lastTime[i] > n) {
                    if (freq[i] > bestFreq) {
                        bestFreq = freq[i];
                        bestTask = i;
                    }
                }
            }
            
            if (bestTask != -1) {
                // Schedule this task
                freq[bestTask]--;
                lastTime[bestTask] = time;
                remainingTasks--;
            }
            // else: idle cycle
            
            time++;
        }
        
        return time;
    }
    
    /**
     * Helper method to visualize the schedule
     */
    private void visualizeSchedule(char[] tasks, int n, String approach) {
        System.out.println("\n" + approach + " - Schedule Visualization:");
        System.out.println("Tasks: " + Arrays.toString(tasks));
        System.out.println("Cooldown (n): " + n);
        
        int result;
        switch (approach) {
            case "Mathematical":
                result = leastInterval(tasks, n);
                break;
            case "Heap":
                result = leastIntervalHeap(tasks, n);
                break;
            case "Sorting":
                result = leastIntervalSorting(tasks, n);
                break;
            case "Frame":
                result = leastIntervalFrameBased(tasks, n);
                break;
            default:
                result = leastInterval(tasks, n);
        }
        
        System.out.println("Minimum time: " + result);
        
        // Show frequency distribution
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        
        System.out.println("\nTask Frequencies:");
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                System.out.printf("  %c: %d times%n", (char)('A' + i), freq[i]);
            }
        }
        
        // Show the mathematical calculation
        if (approach.equals("Mathematical") || approach.equals("Frame")) {
            int maxFreq = 0;
            for (int count : freq) {
                maxFreq = Math.max(maxFreq, count);
            }
            
            int maxCount = 0;
            for (int count : freq) {
                if (count == maxFreq) {
                    maxCount++;
                }
            }
            
            System.out.println("\nMathematical Calculation:");
            System.out.println("  Maximum frequency: " + maxFreq);
            System.out.println("  Tasks with max frequency: " + maxCount);
            System.out.println("  Formula: (maxFreq-1)*(n+1) + maxCount");
            System.out.println("          = (" + maxFreq + "-1)*(" + n + "+1) + " + maxCount);
            System.out.println("          = " + ((maxFreq-1)*(n+1) + maxCount));
            System.out.println("  Max with tasks.length: max(" + ((maxFreq-1)*(n+1) + maxCount) + 
                             ", " + tasks.length + ") = " + result);
        }
        
        // Try to generate a sample schedule
        System.out.println("\nSample Schedule Pattern:");
        generateSampleSchedule(tasks, n);
    }
    
    /**
     * Generate a sample schedule (may not be optimal but valid)
     */
    private void generateSampleSchedule(char[] tasks, int n) {
        if (tasks.length == 0) return;
        
        // Count frequencies
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        
        // Find most frequent task
        int maxFreq = 0;
        char maxTask = 'A';
        for (int i = 0; i < 26; i++) {
            if (freq[i] > maxFreq) {
                maxFreq = freq[i];
                maxTask = (char)('A' + i);
            }
        }
        
        // Create a simple schedule pattern
        StringBuilder schedule = new StringBuilder();
        int time = 0;
        Map<Character, Integer> lastUsed = new HashMap<>();
        
        // Try to schedule tasks
        for (char task : tasks) {
            // Find next available time for this task
            int lastTime = lastUsed.getOrDefault(task, -n - 1);
            int nextAvailable = lastTime + n + 1;
            
            // Add idle time if needed
            while (time < nextAvailable) {
                schedule.append("idle ");
                time++;
            }
            
            // Schedule the task
            schedule.append(task).append(" ");
            lastUsed.put(task, time);
            time++;
        }
        
        System.out.println("  " + schedule.toString().trim());
        System.out.println("  Total time: " + time);
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Task Scheduler Solution:");
        System.out.println("================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        char[] tasks1 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n1 = 2;
        int expected1 = 8;
        
        solution.visualizeSchedule(tasks1, n1, "Mathematical");
        
        long startTime = System.nanoTime();
        int result1a = solution.leastInterval(tasks1, n1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1b = solution.leastIntervalHeap(tasks1, n1);
        long time1b = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1c = solution.leastIntervalSorting(tasks1, n1);
        long time1c = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Mathematical: " + result1a + " - " + 
                         (result1a == expected1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1a + " ns)");
        System.out.println("Heap:         " + result1b + " - " + 
                         (result1b == expected1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1b + " ns)");
        System.out.println("Sorting:      " + result1c + " - " + 
                         (result1c == expected1 ? "PASSED" : "FAILED") + 
                         " (Time: " + time1c + " ns)");
        
        // Test case 2: n = 0
        System.out.println("\nTest 2: n = 0");
        char[] tasks2 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n2 = 0;
        int expected2 = 6;
        
        solution.visualizeSchedule(tasks2, n2, "Mathematical");
        
        int result2a = solution.leastInterval(tasks2, n2);
        System.out.println("Result: " + result2a + " - " + 
                         (result2a == expected2 ? "PASSED" : "FAILED"));
        
        // Test case 3: Large example from problem
        System.out.println("\nTest 3: Large example");
        char[] tasks3 = {'A','A','A','A','A','B','B','B','B','B','C','C','C','C','C','D','D','D'};
        int n3 = 2;
        int expected3 = 16;
        
        solution.visualizeSchedule(tasks3, n3, "Mathematical");
        
        startTime = System.nanoTime();
        int result3a = solution.leastInterval(tasks3, n3);
        long time3a = System.nanoTime() - startTime;
        System.out.println("Result: " + result3a + " - " + 
                         (result3a == expected3 ? "PASSED" : "FAILED") + 
                         " (Time: " + time3a + " ns)");
        
        // Test case 4: Single task type
        System.out.println("\nTest 4: Single task type");
        char[] tasks4 = {'A', 'A', 'A', 'A'};
        int n4 = 2;
        int expected4 = 10; // A idle idle A idle idle A idle idle A
        
        solution.visualizeSchedule(tasks4, n4, "Mathematical");
        
        int result4a = solution.leastInterval(tasks4, n4);
        System.out.println("Result: " + result4a + " - " + 
                         (result4a == expected4 ? "PASSED" : "FAILED"));
        
        // Test case 5: No idle time needed
        System.out.println("\nTest 5: No idle time needed");
        char[] tasks5 = {'A', 'B', 'C', 'D', 'E', 'F'};
        int n5 = 2;
        int expected5 = 6; // All different, no cooldown needed
        
        solution.visualizeSchedule(tasks5, n5, "Mathematical");
        
        int result5a = solution.leastInterval(tasks5, n5);
        System.out.println("Result: " + result5a + " - " + 
                         (result5a == expected5 ? "PASSED" : "FAILED"));
        
        // Test case 6: More complex example
        System.out.println("\nTest 6: Complex example");
        char[] tasks6 = {'A', 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E'};
        int n6 = 2;
        
        solution.visualizeSchedule(tasks6, n6, "Mathematical");
        
        startTime = System.nanoTime();
        int result6a = solution.leastInterval(tasks6, n6);
        long time6a = System.nanoTime() - startTime;
        System.out.println("Result: " + result6a + " (Time: " + time6a + " ns)");
        
        // Test case 7: Edge case - empty tasks
        System.out.println("\nTest 7: Empty tasks");
        char[] tasks7 = {};
        int n7 = 3;
        int expected7 = 0;
        
        int result7a = solution.leastInterval(tasks7, n7);
        System.out.println("Result: " + result7a + " - " + 
                         (result7a == expected7 ? "PASSED" : "FAILED"));
        
        // Test case 8: n larger than needed
        System.out.println("\nTest 8: Large n, small tasks");
        char[] tasks8 = {'A', 'B', 'A'};
        int n8 = 5;
        int expected8 = 9; // A idle idle idle idle idle B idle idle A
        
        solution.visualizeSchedule(tasks8, n8, "Mathematical");
        
        int result8a = solution.leastInterval(tasks8, n8);
        System.out.println("Result: " + result8a + " - " + 
                         (result8a == expected8 ? "PASSED" : "FAILED"));
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE COMPARISON:");
        System.out.println("=".repeat(70));
        
        // Create a large test case
        char[] largeTasks = new char[10000];
        Random random = new Random(42);
        for (int i = 0; i < largeTasks.length; i++) {
            largeTasks[i] = (char)('A' + random.nextInt(4)); // A, B, C, D
        }
        int largeN = 5;
        
        System.out.println("\nTesting with " + largeTasks.length + " tasks, n = " + largeN);
        
        // Test Mathematical
        startTime = System.nanoTime();
        int resultMath = solution.leastInterval(largeTasks, largeN);
        long timeMath = System.nanoTime() - startTime;
        
        // Test Heap
        startTime = System.nanoTime();
        int resultHeap = solution.leastIntervalHeap(largeTasks, largeN);
        long timeHeap = System.nanoTime() - startTime;
        
        // Test Sorting
        startTime = System.nanoTime();
        int resultSort = solution.leastIntervalSorting(largeTasks, largeN);
        long timeSort = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Mathematical: " + resultMath + " (Time: " + (timeMath / 1_000_000) + " ms)");
        System.out.println("Heap:         " + resultHeap + " (Time: " + (timeHeap / 1_000_000) + " ms)");
        System.out.println("Sorting:      " + resultSort + " (Time: " + (timeSort / 1_000_000) + " ms)");
        System.out.println("All results match: " + 
                         (resultMath == resultHeap && resultMath == resultSort));
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nMathematical Formula Intuition:");
        System.out.println("Let maxFreq = frequency of most frequent task");
        System.out.println("Let maxCount = number of tasks with frequency maxFreq");
        System.out.println("Example: tasks = [A,A,A,B,B,B], n = 2");
        System.out.println("  maxFreq = 3, maxCount = 2 (A and B both appear 3 times)");
        
        System.out.println("\nStep 1: Arrange the most frequent tasks:");
        System.out.println("  A _ _ A _ _ A");
        System.out.println("  This creates (maxFreq-1) = 2 gaps of size n = 2");
        System.out.println("  Total framework size: (maxFreq-1)*(n+1) = 2*3 = 6");
        
        System.out.println("\nStep 2: Add other maxFreq tasks:");
        System.out.println("  We have maxCount = 2 tasks with maxFreq");
        System.out.println("  Add B to the end: A _ _ A _ _ A B");
        System.out.println("  Now place remaining B's in the gaps");
        System.out.println("  A B _ A B _ A B");
        
        System.out.println("\nStep 3: Fill remaining gaps with other tasks:");
        System.out.println("  If there are more tasks, fill the gaps");
        System.out.println("  If not, gaps remain idle");
        
        System.out.println("\nStep 4: Handle case when tasks fill all slots:");
        System.out.println("  If tasks can fill all slots without idle time,");
        System.out.println("  then total time = tasks.length");
        System.out.println("  So result = max(formula, tasks.length)");
        
        // Visual example
        System.out.println("\nVisual Example (tasks = [A,A,A,B,B,B,C], n = 2):");
        System.out.println("1. Count frequencies: A=3, B=3, C=1");
        System.out.println("2. maxFreq = 3, maxCount = 2 (A and B)");
        System.out.println("3. Framework: (3-1)*(2+1) = 2*3 = 6 slots");
        System.out.println("4. Add maxCount: 6 + 2 = 8");
        System.out.println("5. Fill with C: A B C A B _ A B");
        System.out.println("6. Total time = 8");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Mathematical Formula (RECOMMENDED):");
        System.out.println("   Time: O(n) - Single pass to count frequencies");
        System.out.println("   Space: O(1) - 26-element frequency array");
        System.out.println("   Pros:");
        System.out.println("     - Most efficient");
        System.out.println("     - Elegant mathematical solution");
        System.out.println("     - No simulation overhead");
        System.out.println("     - Easy to implement");
        System.out.println("   Cons:");
        System.out.println("     - Less intuitive than simulation");
        System.out.println("     - Harder to derive in interview");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. Max Heap Simulation:");
        System.out.println("   Time: O(n log 26) - Heap operations for each task");
        System.out.println("   Space: O(1) - Constant space for heap and queue");
        System.out.println("   Pros:");
        System.out.println("     - Intuitive simulation approach");
        System.out.println("     - Easy to understand and explain");
        System.out.println("     - Naturally greedy (always pick highest frequency)");
        System.out.println("   Cons:");
        System.out.println("     - Slower than mathematical approach");
        System.out.println("     - More complex implementation");
        System.out.println("     - Heap operations overhead");
        System.out.println("   Best for: Interviews if asked for simulation");
        
        System.out.println("\n3. Greedy with Sorting:");
        System.out.println("   Time: O(n + 26 log 26) - Count + sort frequencies");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Simple to implement");
        System.out.println("     - Intuitive idle slot calculation");
        System.out.println("     - Good balance of simplicity and efficiency");
        System.out.println("   Cons:");
        System.out.println("     - Slightly slower than mathematical");
        System.out.println("     - Still requires mathematical insight");
        System.out.println("   Best for: Learning, interviews");
        
        System.out.println("\n4. Frame-based Mathematical:");
        System.out.println("   Time: O(n) - Single pass to count");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - More intuitive explanation of formula");
        System.out.println("     - Frame visualization helps understanding");
        System.out.println("     - Same efficiency as mathematical");
        System.out.println("   Cons:");
        System.out.println("     - Same complexity as mathematical");
        System.out.println("     - Slightly different perspective");
        System.out.println("   Best for: Understanding the formula");
        
        System.out.println("\n5. Simulation with Time Slots:");
        System.out.println("   Time: O(n * 26) - For each time slot, check 26 tasks");
        System.out.println("   Space: O(1) - Constant space");
        System.out.println("   Pros:");
        System.out.println("     - Most intuitive simulation");
        System.out.println("     - Directly models the problem");
        System.out.println("     - Easy to understand");
        System.out.println("   Cons:");
        System.out.println("     - Least efficient");
        System.out.println("     - O(n*26) can be large");
        System.out.println("   Best for: Conceptual understanding, small n");
        
        // Derivation of formula
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DERIVATION OF THE FORMULA:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nLet:");
        System.out.println("  maxFreq = frequency of most frequent task");
        System.out.println("  n = cooldown period");
        System.out.println("  maxCount = number of tasks with frequency maxFreq");
        
        System.out.println("\n1. Arrange the most frequent task:");
        System.out.println("   We need to place maxFreq instances of the task");
        System.out.println("   With n cooldown between each, we get:");
        System.out.println("   Task _ ... _ Task _ ... _ Task");
        System.out.println("   ^     n      ^     n      ^");
        System.out.println("   This creates (maxFreq-1) gaps");
        System.out.println("   Each gap needs n idle slots");
        
        System.out.println("\n2. Framework size:");
        System.out.println("   Total slots in framework = (maxFreq-1)*(n+1)");
        System.out.println("   Why (n+1)? Because each 'Task + n idle' is n+1 slots");
        
        System.out.println("\n3. Add other maxFreq tasks:");
        System.out.println("   For each additional task with same maxFreq,");
        System.out.println("   we add one more slot at the end");
        System.out.println("   So add maxCount");
        
        System.out.println("\n4. Final formula:");
        System.out.println("   (maxFreq-1)*(n+1) + maxCount");
        
        System.out.println("\n5. Handle overflow:");
        System.out.println("   If tasks can fill all slots without idle,");
        System.out.println("   then total time = tasks.length");
        System.out.println("   So result = max(formula, tasks.length)");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify requirements:");
        System.out.println("   - Each task takes 1 unit time");
        System.out.println("   - Cooldown n between same tasks");
        System.out.println("   - Minimize total time");
        
        System.out.println("\n2. Start with simple example:");
        System.out.println("   - [A,A,A,B,B,B], n=2");
        System.out.println("   - Draw schedule to understand pattern");
        
        System.out.println("\n3. Identify key insight:");
        System.out.println("   - Most frequent task determines framework");
        System.out.println("   - Need to place other tasks in gaps");
        
        System.out.println("\n4. Propose mathematical solution:");
        System.out.println("   - Count frequencies");
        System.out.println("   - Find max frequency and count");
        System.out.println("   - Use formula: (maxFreq-1)*(n+1) + maxCount");
        System.out.println("   - Handle case when tasks fill all gaps");
        
        System.out.println("\n5. Discuss alternative approaches:");
        System.out.println("   - Max heap simulation");
        System.out.println("   - Greedy with sorting");
        System.out.println("   - Compare time/space complexity");
        
        System.out.println("\n6. Walk through examples:");
        System.out.println("   - n=0 case");
        System.out.println("   - Single task type");
        System.out.println("   - No idle time case");
        System.out.println("   - Edge cases");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- The framework created by most frequent task");
        System.out.println("- Idle slots = empty gaps in framework");
        System.out.println("- Formula handles multiple tasks with same max frequency");
        System.out.println("- Max with tasks.length handles no-idle case");
        System.out.println("- O(n) time, O(1) space optimal solution");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting to handle case when tasks fill all slots");
        System.out.println("- Not counting multiple tasks with same max frequency");
        System.out.println("- Incorrect formula derivation");
        System.out.println("- Not considering n=0 edge case");
        System.out.println("- Overcomplicating with simulation when math works");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with n=0 (should return tasks.length)");
        System.out.println("2. Test with single task type");
        System.out.println("3. Test with all different tasks (no idle)");
        System.out.println("4. Test with provided examples");
        System.out.println("5. Test edge cases (empty tasks, n > tasks)");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
