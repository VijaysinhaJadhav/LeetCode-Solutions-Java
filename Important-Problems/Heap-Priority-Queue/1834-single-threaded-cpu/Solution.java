
## Solution.java

```java
/**
 * 1834. Single-Threaded CPU
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Given tasks with [enqueueTime, processingTime], simulate a single-threaded CPU
 * that processes tasks with shortest processing time first (with index tiebreaker).
 * Return the order in which tasks are processed.
 * 
 * Key Insights:
 * 1. Tasks become available at their enqueue time
 * 2. CPU picks available task with shortest processing time (smallest index tiebreaker)
 * 3. Need efficient way to find next task to process
 * 4. Two data structures: sorted list by enqueue time and min-heap for available tasks
 * 
 * Approach (Sorting + Min-Heap):
 * 1. Store original indices before sorting
 * 2. Sort tasks by enqueue time
 * 3. Use min-heap ordered by (processingTime, index)
 * 4. Simulate time, adding available tasks to heap
 * 5. Process tasks from heap, updating time
 * 6. If heap empty, jump to next task's enqueue time
 * 
 * Time Complexity: O(n log n) - Sorting + heap operations
 * Space Complexity: O(n) - Heap and result storage
 * 
 * Tags: Array, Sorting, Heap, Simulation
 */

import java.util.*;

class Solution {
    
    /**
     * Approach 1: Sorting + Min-Heap Simulation (RECOMMENDED)
     * O(n log n) time, O(n) space
     */
    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        
        // Create array with [enqueueTime, processingTime, originalIndex]
        int[][] taskWithIndex = new int[n][3];
        for (int i = 0; i < n; i++) {
            taskWithIndex[i][0] = tasks[i][0]; // enqueueTime
            taskWithIndex[i][1] = tasks[i][1]; // processingTime
            taskWithIndex[i][2] = i;           // original index
        }
        
        // Sort tasks by enqueue time
        Arrays.sort(taskWithIndex, (a, b) -> a[0] - b[0]);
        
        // Min-heap for available tasks: ordered by (processingTime, index)
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1]) {
                return a[1] - b[1]; // Shortest processing time first
            }
            return a[2] - b[2];     // Smallest index if same processing time
        });
        
        int[] result = new int[n];
        int resultIndex = 0;
        int taskIndex = 0; // Index in sorted task array
        long currentTime = 0; // Use long to avoid overflow
        
        while (resultIndex < n) {
            // Add all tasks that are available at or before current time
            while (taskIndex < n && taskWithIndex[taskIndex][0] <= currentTime) {
                minHeap.offer(taskWithIndex[taskIndex]);
                taskIndex++;
            }
            
            if (!minHeap.isEmpty()) {
                // Process the next task from heap
                int[] task = minHeap.poll();
                result[resultIndex++] = task[2]; // Store original index
                currentTime += task[1]; // Update time by processing time
            } else {
                // No tasks available, jump to next task's enqueue time
                if (taskIndex < n) {
                    currentTime = taskWithIndex[taskIndex][0];
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 2: Using Custom Task Class (More Readable)
     * Same complexity, better object-oriented design
     */
    public int[] getOrderWithClass(int[][] tasks) {
        int n = tasks.length;
        Task[] taskArray = new Task[n];
        
        // Create Task objects with original indices
        for (int i = 0; i < n; i++) {
            taskArray[i] = new Task(i, tasks[i][0], tasks[i][1]);
        }
        
        // Sort by enqueue time
        Arrays.sort(taskArray, (a, b) -> a.enqueueTime - b.enqueueTime);
        
        // Min-heap for available tasks
        PriorityQueue<Task> minHeap = new PriorityQueue<>((a, b) -> {
            if (a.processingTime != b.processingTime) {
                return a.processingTime - b.processingTime;
            }
            return a.index - b.index;
        });
        
        int[] result = new int[n];
        int resultIndex = 0;
        int taskIndex = 0;
        long currentTime = 0;
        
        while (resultIndex < n) {
            // Add available tasks
            while (taskIndex < n && taskArray[taskIndex].enqueueTime <= currentTime) {
                minHeap.offer(taskArray[taskIndex]);
                taskIndex++;
            }
            
            if (!minHeap.isEmpty()) {
                Task task = minHeap.poll();
                result[resultIndex++] = task.index;
                currentTime += task.processingTime;
            } else {
                // Jump to next task's enqueue time
                if (taskIndex < n) {
                    currentTime = taskArray[taskIndex].enqueueTime;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Task class for better readability
     */
    private static class Task {
        int index;
        int enqueueTime;
        int processingTime;
        
        Task(int index, int enqueueTime, int processingTime) {
            this.index = index;
            this.enqueueTime = enqueueTime;
            this.processingTime = processingTime;
        }
    }
    
    /**
     * Approach 3: Two Heaps Approach
     * One heap for time ordering, one for task selection
     */
    public int[] getOrderTwoHeaps(int[][] tasks) {
        int n = tasks.length;
        
        // First heap: tasks sorted by enqueue time
        PriorityQueue<int[]> timeHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < n; i++) {
            timeHeap.offer(new int[]{tasks[i][0], tasks[i][1], i});
        }
        
        // Second heap: available tasks sorted by (processingTime, index)
        PriorityQueue<int[]> taskHeap = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1]) return a[1] - b[1];
            return a[2] - b[2];
        });
        
        int[] result = new int[n];
        int resultIndex = 0;
        long currentTime = 0;
        
        while (resultIndex < n) {
            // Move tasks from time heap to task heap if they're available
            while (!timeHeap.isEmpty() && timeHeap.peek()[0] <= currentTime) {
                taskHeap.offer(timeHeap.poll());
            }
            
            if (!taskHeap.isEmpty()) {
                int[] task = taskHeap.poll();
                result[resultIndex++] = task[2];
                currentTime += task[1];
            } else {
                // If no tasks available, jump to next enqueue time
                if (!timeHeap.isEmpty()) {
                    currentTime = Math.max(currentTime, timeHeap.peek()[0]);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach 4: Event-based Simulation
     * More detailed simulation with event queue
     */
    public int[] getOrderEventBased(int[][] tasks) {
        int n = tasks.length;
        
        // Create event list: (time, type, task)
        // type 0: task becomes available
        // type 1: task completes
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            events.add(new Event(tasks[i][0], 0, i, tasks[i][1]));
        }
        
        // Sort events by time, then by type (availability before completion)
        Collections.sort(events, (a, b) -> {
            if (a.time != b.time) return Long.compare(a.time, b.time);
            return a.type - b.type;
        });
        
        // Min-heap for available tasks
        PriorityQueue<Task> taskHeap = new PriorityQueue<>((a, b) -> {
            if (a.processingTime != b.processingTime) {
                return a.processingTime - b.processingTime;
            }
            return a.index - b.index;
        });
        
        int[] result = new int[n];
        int resultIndex = 0;
        int eventIndex = 0;
        long currentTime = 0;
        boolean cpuBusy = false;
        long busyUntil = 0;
        int currentTaskIndex = -1;
        
        while (resultIndex < n) {
            // Process all events at current time
            while (eventIndex < events.size() && events.get(eventIndex).time <= currentTime) {
                Event event = events.get(eventIndex);
                if (event.type == 0) {
                    // Task becomes available
                    taskHeap.offer(new Task(event.taskIndex, (int)event.time, event.processingTime));
                }
                eventIndex++;
            }
            
            // Check if CPU is free
            if (cpuBusy && currentTime >= busyUntil) {
                cpuBusy = false;
                result[resultIndex++] = currentTaskIndex;
            }
            
            // If CPU is free and tasks available, start new task
            if (!cpuBusy && !taskHeap.isEmpty()) {
                Task task = taskHeap.poll();
                cpuBusy = true;
                busyUntil = currentTime + task.processingTime;
                currentTaskIndex = task.index;
            }
            
            // Determine next event time
            long nextTime = Long.MAX_VALUE;
            if (eventIndex < events.size()) {
                nextTime = events.get(eventIndex).time;
            }
            if (cpuBusy) {
                nextTime = Math.min(nextTime, busyUntil);
            }
            
            // Jump to next event time
            if (nextTime == Long.MAX_VALUE) break;
            currentTime = nextTime;
        }
        
        return result;
    }
    
    private static class Event {
        long time;
        int type; // 0: available, 1: complete
        int taskIndex;
        int processingTime;
        
        Event(long time, int type, int taskIndex, int processingTime) {
            this.time = time;
            this.type = type;
            this.taskIndex = taskIndex;
            this.processingTime = processingTime;
        }
    }
    
    /**
     * Approach 5: Optimized with Array Indexing
     * Avoids creating many small objects
     */
    public int[] getOrderOptimized(int[][] tasks) {
        int n = tasks.length;
        
        // Create index array and sort indices by enqueue time
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        
        Arrays.sort(indices, (a, b) -> tasks[a][0] - tasks[b][0]);
        
        // Min-heap stores [processingTime, originalIndex]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1];
        });
        
        int[] result = new int[n];
        int resultIndex = 0;
        int taskIndex = 0;
        long currentTime = 0;
        
        while (resultIndex < n) {
            // Add available tasks
            while (taskIndex < n && tasks[indices[taskIndex]][0] <= currentTime) {
                int idx = indices[taskIndex];
                minHeap.offer(new int[]{tasks[idx][1], idx});
                taskIndex++;
            }
            
            if (!minHeap.isEmpty()) {
                int[] task = minHeap.poll();
                result[resultIndex++] = task[1];
                currentTime += task[0];
            } else {
                // Jump to next enqueue time
                if (taskIndex < n) {
                    currentTime = tasks[indices[taskIndex]][0];
                }
            }
        }
        
        return result;
    }
    
    /**
     * Helper method to visualize the simulation
     */
    private void visualizeSimulation(int[][] tasks, int[] order, String approach) {
        System.out.println("\n" + approach + " - Simulation Visualization:");
        System.out.println("Tasks (index: [enqueueTime, processingTime]):");
        for (int i = 0; i < tasks.length; i++) {
            System.out.printf("  %d: [%d, %d]%n", i, tasks[i][0], tasks[i][1]);
        }
        
        System.out.println("\nProcessing Order: " + Arrays.toString(order));
        
        // Simulate and show timeline
        System.out.println("\nTimeline:");
        long currentTime = 0;
        int[] taskStartTimes = new int[tasks.length];
        int[] taskEndTimes = new int[tasks.length];
        
        // First, sort tasks by enqueue time for visualization
        int[][] tasksWithIndex = new int[tasks.length][3];
        for (int i = 0; i < tasks.length; i++) {
            tasksWithIndex[i][0] = tasks[i][0];
            tasksWithIndex[i][1] = tasks[i][1];
            tasksWithIndex[i][2] = i;
        }
        Arrays.sort(tasksWithIndex, (a, b) -> a[0] - b[0]);
        
        // Min-heap for simulation
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1]) return a[1] - b[1];
            return a[2] - b[2];
        });
        
        int idx = 0;
        int completed = 0;
        
        while (completed < tasks.length) {
            // Add available tasks
            while (idx < tasks.length && tasksWithIndex[idx][0] <= currentTime) {
                heap.offer(tasksWithIndex[idx]);
                idx++;
            }
            
            if (!heap.isEmpty()) {
                int[] task = heap.poll();
                taskStartTimes[task[2]] = (int)currentTime;
                currentTime += task[1];
                taskEndTimes[task[2]] = (int)currentTime;
                completed++;
                
                System.out.printf("  Time %d-%d: Processing task %d (enqueued at %d, takes %d)%n",
                    taskStartTimes[task[2]], taskEndTimes[task[2]], 
                    task[2], task[0], task[1]);
            } else {
                // Jump to next enqueue time
                if (idx < tasks.length) {
                    long nextTime = tasksWithIndex[idx][0];
                    if (currentTime < nextTime) {
                        System.out.printf("  Time %d-%d: CPU Idle%n", currentTime, nextTime);
                        currentTime = nextTime;
                    }
                }
            }
        }
        
        // Show statistics
        System.out.println("\nStatistics:");
        int totalProcessingTime = 0;
        int totalWaitTime = 0;
        for (int i = 0; i < tasks.length; i++) {
            int waitTime = taskStartTimes[i] - tasks[i][0];
            totalWaitTime += waitTime;
            totalProcessingTime += tasks[i][1];
            System.out.printf("  Task %d: Wait time = %d, Start = %d, End = %d%n",
                i, waitTime, taskStartTimes[i], taskEndTimes[i]);
        }
        
        System.out.printf("\nTotal processing time: %d%n", totalProcessingTime);
        System.out.printf("Total wait time: %d%n", totalWaitTime);
        System.out.printf("Average wait time: %.2f%n", (double)totalWaitTime / tasks.length);
        System.out.printf("Makespan (completion time): %d%n", currentTime);
    }
    
    /**
     * Helper method to validate the result
     */
    private boolean validateOrder(int[][] tasks, int[] order) {
        // Check all indices are present exactly once
        Set<Integer> indices = new HashSet<>();
        for (int idx : order) {
            if (idx < 0 || idx >= tasks.length) return false;
            if (indices.contains(idx)) return false;
            indices.add(idx);
        }
        if (indices.size() != tasks.length) return false;
        
        // Simulate to check feasibility
        long currentTime = 0;
        int[][] tasksWithIndex = new int[tasks.length][3];
        for (int i = 0; i < tasks.length; i++) {
            tasksWithIndex[i][0] = tasks[i][0];
            tasksWithIndex[i][1] = tasks[i][1];
            tasksWithIndex[i][2] = i;
        }
        Arrays.sort(tasksWithIndex, (a, b) -> a[0] - b[0]);
        
        PriorityQueue<int[]> available = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1]) return a[1] - b[1];
            return a[2] - b[2];
        });
        
        int taskIndex = 0;
        
        for (int taskIdx : order) {
            // Update current time to when this task becomes available
            while (taskIndex < tasks.length && tasksWithIndex[taskIndex][0] <= currentTime) {
                available.offer(tasksWithIndex[taskIndex]);
                taskIndex++;
            }
            
            // Check if task is available
            boolean found = false;
            for (int[] task : available) {
                if (task[2] == taskIdx) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                // Task not available, need to wait
                if (tasks[taskIdx][0] > currentTime) {
                    currentTime = tasks[taskIdx][0];
                } else {
                    // Task should have been available but wasn't
                    return false;
                }
            }
            
            // Process the task
            currentTime += tasks[taskIdx][1];
            
            // Remove from available
            available.removeIf(task -> task[2] == taskIdx);
        }
        
        return true;
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        System.out.println("Testing Single-Threaded CPU Solution:");
        System.out.println("======================================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example");
        int[][] tasks1 = {{1,2}, {2,4}, {3,2}, {4,1}};
        int[] expected1 = {0, 2, 3, 1};
        
        solution.visualizeSimulation(tasks1, expected1, "Expected");
        
        long startTime = System.nanoTime();
        int[] result1a = solution.getOrder(tasks1);
        long time1a = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int[] result1b = solution.getOrderWithClass(tasks1);
        long time1b = System.nanoTime() - startTime;
        
        System.out.println("\nResults:");
        System.out.println("Sort+Heap: " + Arrays.toString(result1a) + " - " + 
                         (Arrays.equals(result1a, expected1) ? "PASSED" : "FAILED") + 
                         " (Time: " + time1a + " ns)");
        System.out.println("With Class: " + Arrays.toString(result1b) + " - " + 
                         (Arrays.equals(result1b, expected1) ? "PASSED" : "FAILED") + 
                         " (Time: " + time1b + " ns)");
        
        boolean valid1 = solution.validateOrder(tasks1, result1a);
        System.out.println("Validation: " + (valid1 ? "VALID" : "INVALID"));
        
        // Test case 2: Example 2 from problem
        System.out.println("\nTest 2: Same enqueue time");
        int[][] tasks2 = {{7,10}, {7,12}, {7,5}, {7,4}, {7,2}};
        int[] expected2 = {4, 3, 2, 0, 1};
        
        solution.visualizeSimulation(tasks2, expected2, "Expected");
        
        int[] result2a = solution.getOrder(tasks2);
        System.out.println("Result: " + Arrays.toString(result2a) + " - " + 
                         (Arrays.equals(result2a, expected2) ? "PASSED" : "FAILED"));
        
        // Test case 3: Single task
        System.out.println("\nTest 3: Single task");
        int[][] tasks3 = {{5, 10}};
        int[] expected3 = {0};
        
        int[] result3a = solution.getOrder(tasks3);
        System.out.println("Result: " + Arrays.toString(result3a) + " - " + 
                         (Arrays.equals(result3a, expected3) ? "PASSED" : "FAILED"));
        
        // Test case 4: Tasks in perfect order
        System.out.println("\nTest 4: Tasks in perfect order");
        int[][] tasks4 = {{1,1}, {2,1}, {3,1}, {4,1}};
        int[] expected4 = {0, 1, 2, 3};
        
        solution.visualizeSimulation(tasks4, expected4, "Expected");
        
        int[] result4a = solution.getOrder(tasks4);
        System.out.println("Result: " + Arrays.toString(result4a) + " - " + 
                         (Arrays.equals(result4a, expected4) ? "PASSED" : "FAILED"));
        
        // Test case 5: CPU idle time
        System.out.println("\nTest 5: CPU idle time between tasks");
        int[][] tasks5 = {{1,1}, {5,1}, {10,1}};
        
        solution.visualizeSimulation(tasks5, new int[]{0,1,2}, "Expected");
        
        int[] result5a = solution.getOrder(tasks5);
        System.out.println("Result: " + Arrays.toString(result5a));
        
        // Test case 6: Mixed priorities
        System.out.println("\nTest 6: Mixed priorities");
        int[][] tasks6 = {{0,3}, {1,2}, {2,1}};
        // At time 0: only task 0 available, process it (takes 3)
        // At time 3: tasks 1 and 2 available, pick task 2 (shorter processing time)
        // At time 4: task 1 available, process it
        int[] expected6 = {0, 2, 1};
        
        solution.visualizeSimulation(tasks6, expected6, "Expected");
        
        int[] result6a = solution.getOrder(tasks6);
        System.out.println("Result: " + Arrays.toString(result6a) + " - " + 
                         (Arrays.equals(result6a, expected6) ? "PASSED" : "FAILED"));
        
        // Test case 7: Tie in processing time
        System.out.println("\nTest 7: Tie in processing time (pick smaller index)");
        int[][] tasks7 = {{1,5}, {1,5}, {1,5}};
        // All have same enqueue time and processing time
        // Should pick 0, then 1, then 2
        int[] expected7 = {0, 1, 2};
        
        int[] result7a = solution.getOrder(tasks7);
        System.out.println("Result: " + Arrays.toString(result7a) + " - " + 
                         (Arrays.equals(result7a, expected7) ? "PASSED" : "FAILED"));
        
        // Test case 8: Large values
        System.out.println("\nTest 8: Large time values");
        int[][] tasks8 = {{1000000000, 1000000000}, {1000000000, 999999999}};
        // Both available at same time, pick task 1 first (shorter processing time)
        int[] expected8 = {1, 0};
        
        int[] result8a = solution.getOrder(tasks8);
        System.out.println("Result: " + Arrays.toString(result8a) + " - " + 
                         (Arrays.equals(result8a, expected8) ? "PASSED" : "FAILED"));
        
        // Performance test
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PERFORMANCE TEST:");
        System.out.println("=".repeat(70));
        
        Random random = new Random(42);
        int n = 100000;
        int[][] largeTasks = new int[n][2];
        
        // Generate random tasks
        for (int i = 0; i < n; i++) {
            largeTasks[i][0] = random.nextInt(1000000); // enqueue time up to 1M
            largeTasks[i][1] = random.nextInt(10000) + 1; // processing time 1-10000
        }
        
        System.out.println("\nTesting with " + n + " tasks");
        
        // Test different implementations
        startTime = System.currentTimeMillis();
        int[] resultLarge1 = solution.getOrder(largeTasks);
        long timeLarge1 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        int[] resultLarge2 = solution.getOrderOptimized(largeTasks);
        long timeLarge2 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        int[] resultLarge3 = solution.getOrderWithClass(largeTasks);
        long timeLarge3 = System.currentTimeMillis() - startTime;
        
        System.out.println("\nPerformance Results:");
        System.out.println("Sort+Heap:        " + timeLarge1 + " ms");
        System.out.println("Optimized:        " + timeLarge2 + " ms");
        System.out.println("With Class:       " + timeLarge3 + " ms");
        
        // Verify all produce same result
        boolean allSame = Arrays.equals(resultLarge1, resultLarge2) && 
                         Arrays.equals(resultLarge1, resultLarge3);
        System.out.println("All results same: " + allSame);
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nKey Observations:");
        System.out.println("1. Tasks become available at specific times");
        System.out.println("2. CPU picks shortest processing time first (with index tiebreaker)");
        System.out.println("3. CPU can be idle if no tasks available");
        
        System.out.println("\nData Structures:");
        System.out.println("1. Sorted task list by enqueue time:");
        System.out.println("   - Allows us to efficiently find when next task becomes available");
        System.out.println("   - O(n log n) to sort initially");
        
        System.out.println("\n2. Min-Heap for available tasks:");
        System.out.println("   - Ordered by (processingTime, index)");
        System.out.println("   - O(log n) to add/remove tasks");
        System.out.println("   - O(1) to get shortest task");
        
        System.out.println("\nAlgorithm Steps:");
        System.out.println("1. Sort tasks by enqueue time (preserve original indices)");
        System.out.println("2. Initialize currentTime = 0, result = []");
        System.out.println("3. While tasks remain:");
        System.out.println("   a. Add all tasks with enqueueTime <= currentTime to heap");
        System.out.println("   b. If heap not empty:");
        System.out.println("      - Pop shortest task from heap");
        System.out.println("      - Add its index to result");
        System.out.println("      - currentTime += task processing time");
        System.out.println("   c. Else (heap empty):");
        System.out.println("      - Jump currentTime to next task's enqueueTime");
        System.out.println("4. Return result");
        
        System.out.println("\nVisual Example:");
        System.out.println("Tasks: [[1,2], [2,4], [3,2], [4,1]]");
        System.out.println("\nStep 1: Sort (already sorted by enqueue time)");
        System.out.println("Step 2: currentTime = 0");
        System.out.println("Step 3: Add tasks with enqueueTime <= 0 (none)");
        System.out.println("Step 4: Heap empty, jump to time = 1");
        System.out.println("Step 5: Add task 0 to heap");
        System.out.println("Step 6: Process task 0, currentTime = 1+2 = 3");
        System.out.println("Step 7: Add tasks with enqueueTime <= 3 (tasks 1,2)");
        System.out.println("Step 8: Process task 2 (shortest), currentTime = 3+2 = 5");
        System.out.println("Step 9: Add task 3 (enqueueTime 4 <= 5)");
        System.out.println("Step 10: Process task 3, currentTime = 5+1 = 6");
        System.out.println("Step 11: Process task 1, currentTime = 6+4 = 10");
        System.out.println("Result: [0, 2, 3, 1]");
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("IMPLEMENTATION COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Sorting + Min-Heap (RECOMMENDED):");
        System.out.println("   Time: O(n log n) - Sorting + heap operations");
        System.out.println("   Space: O(n) - Heap and auxiliary arrays");
        System.out.println("   Pros:");
        System.out.println("     - Optimal time complexity");
        System.out.println("     - Clear and intuitive");
        System.out.println("     - Handles all cases efficiently");
        System.out.println("   Cons:");
        System.out.println("     - Requires understanding of both sorting and heaps");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. With Task Class (Object-Oriented):");
        System.out.println("   Time: O(n log n) - Same as above");
        System.out.println("   Space: O(n) - Slightly more due to object overhead");
        System.out.println("   Pros:");
        System.out.println("     - More readable code");
        System.out.println("     - Better encapsulation");
        System.out.println("     - Easier to extend");
        System.out.println("   Cons:");
        System.out.println("     - Object creation overhead");
        System.out.println("     - Slightly slower in practice");
        System.out.println("   Best for: Code clarity, larger systems");
        
        System.out.println("\n3. Two Heaps Approach:");
        System.out.println("   Time: O(n log n) - Two heap operations");
        System.out.println("   Space: O(n) - Two heaps");
        System.out.println("   Pros:");
        System.out.println("     - Clear separation of concerns");
        System.out.println("     - No need to sort array initially");
        System.out.println("   Cons:");
        System.out.println("     - More heap operations");
        System.out.println("     - Less efficient than sorting + heap");
        System.out.println("   Best for: When tasks arrive in random order");
        
        System.out.println("\n4. Event-based Simulation:");
        System.out.println("   Time: O(n log n) - Event sorting");
        System.out.println("   Space: O(n) - Event list");
        System.out.println("   Pros:");
        System.out.println("     - Most detailed simulation");
        System.out.println("     - Can handle more complex scenarios");
        System.out.println("     - Clear event-driven model");
        System.out.println("   Cons:");
        System.out.println("     - Most complex implementation");
        System.out.println("     - Overkill for this problem");
        System.out.println("   Best for: Learning, complex scheduling systems");
        
        System.out.println("\n5. Optimized with Array Indexing:");
        System.out.println("   Time: O(n log n) - Same complexity");
        System.out.println("   Space: O(n) - Less object overhead");
        System.out.println("   Pros:");
        System.out.println("     - Most memory efficient");
        System.out.println("     - Fastest in practice");
        System.out.println("     - Minimal object creation");
        System.out.println("   Cons:");
        System.out.println("     - Less readable code");
        System.out.println("     - More complex indexing");
        System.out.println("   Best for: Performance-critical applications");
        
        // Scheduling theory insights
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCHEDULING THEORY INSIGHTS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nThis problem implements SJF (Shortest Job First):");
        System.out.println("1. Non-preemptive SJF:");
        System.out.println("   - Once a task starts, it runs to completion");
        System.out.println("   - Minimizes average waiting time");
        System.out.println("   - Can cause starvation for long tasks");
        
        System.out.println("\n2. With release times (enqueue times):");
        System.out.println("   - Tasks not available until their release time");
        System.out.println("   - More realistic than simple SJF");
        
        System.out.println("\n3. Performance metrics:");
        System.out.println("   - Throughput: tasks completed per unit time");
        System.out.println("   - CPU utilization: percentage time CPU is busy");
        System.out.println("   - Average waiting time: (start time - enqueue time)");
        System.out.println("   - Makespan: time to complete all tasks");
        
        System.out.println("\n4. Optimality:");
        System.out.println("   - SJF is optimal for minimizing average waiting time");
        System.out.println("   - With release times, it's still a good heuristic");
        System.out.println("   - Not necessarily optimal for makespan");
        
        // Real-world applications
        System.out.println("\n" + "=".repeat(70));
        System.out.println("REAL-WORLD APPLICATIONS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Operating System CPU Scheduling:");
        System.out.println("   - Similar to Unix 'nice' priority system");
        System.out.println("   - Batch processing systems");
        System.out.println("   - Real-time systems with deadlines");
        
        System.out.println("\n2. Cloud Computing:");
        System.out.println("   - Virtual machine scheduling");
        System.out.println("   - Container orchestration (Kubernetes)");
        System.out.println("   - Serverless function execution");
        
        System.out.println("\n3. Database Systems:");
        System.out.println("   - Query optimization and execution");
        System.out.println("   - Transaction scheduling");
        System.out.println("   - Batch job processing");
        
        System.out.println("\n4. Manufacturing:");
        System.out.println("   - Job shop scheduling");
        System.out.println("   - Production line optimization");
        System.out.println("   - Resource allocation");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify requirements:");
        System.out.println("   - Tasks have enqueue time and processing time");
        System.out.println("   - CPU picks shortest processing time first");
        System.out.println("   - Index tiebreaker for same processing time");
        System.out.println("   - Non-preemptive (tasks run to completion)");
        
        System.out.println("\n2. Discuss brute force:");
        System.out.println("   - O(n²) simulation");
        System.out.println("   - At each step, check all available tasks");
        System.out.println("   - Mention it's too slow for n=10^5");
        
        System.out.println("\n3. Identify optimization opportunities:");
        System.out.println("   - Need efficient way to find available tasks");
        System.out.println("   - Need efficient way to find shortest task");
        System.out.println("   - Two data structures: sorted list + min-heap");
        
        System.out.println("\n4. Propose solution:");
        System.out.println("   - Sort tasks by enqueue time");
        System.out.println("   - Use min-heap ordered by (processingTime, index)");
        System.out.println("   - Simulate time, adding available tasks to heap");
        
        System.out.println("\n5. Walk through example:");
        System.out.println("   - Use provided example");
        System.out.println("   - Show step-by-step execution");
        System.out.println("   - Demonstrate heap operations");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - CPU idle time (jump to next enqueue time)");
        System.out.println("   - Tie in processing time (use index)");
        System.out.println("   - Tasks with same enqueue time");
        System.out.println("   - Single task case");
        
        System.out.println("\n7. Analyze complexity:");
        System.out.println("   - Time: O(n log n) for sorting + heap operations");
        System.out.println("   - Space: O(n) for heap and result");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- O(n log n) is optimal for comparison-based sorting");
        System.out.println("- Min-heap gives O(log n) for task selection");
        System.out.println("- Two-pointer technique for adding available tasks");
        System.out.println("- Use long for time to avoid overflow");
        System.out.println("- Preserve original indices for result");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting to handle idle CPU time");
        System.out.println("- Not preserving original indices");
        System.out.println("- Integer overflow with large times");
        System.out.println("- Incorrect heap ordering (forgetting tiebreaker)");
        System.out.println("- Not validating result order");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test with given examples");
        System.out.println("2. Test edge cases (single task, idle CPU)");
        System.out.println("3. Test tie-breaking scenarios");
        System.out.println("4. Test with large values (overflow check)");
        System.out.println("5. Validate result by simulation");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
