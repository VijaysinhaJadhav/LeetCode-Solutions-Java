
## Solution.java

```java
/**
 * 895. Maximum Frequency Stack
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Design a stack-like data structure that supports push and pop operations and can efficiently 
 * return the most frequent element. If there is a tie for the most frequent element, return 
 * the element that was pushed most recently.
 * 
 * Key Insights:
 * 1. Use two HashMaps: one for frequency counts, one for stacks at each frequency level
 * 2. Maintain a maxFrequency variable to track current maximum frequency
 * 3. Each frequency level has its own stack (LIFO behavior for tie-breaking)
 * 4. When an element's frequency increases, it moves to the next frequency stack
 * 5. Pop always removes from the highest frequency stack
 * 
 * Approach (Two HashMaps with Frequency Stacks):
 * 1. frequencyMap: val -> frequency count
 * 2. groupMap: frequency -> stack of elements with that frequency
 * 3. maxFrequency: current maximum frequency in the stack
 * 
 * Time Complexity: O(1) for push and pop
 * Space Complexity: O(n) where n is number of elements
 * 
 * Tags: Hash Table, Stack, Design, Ordered Set
 */

import java.util.*;

class FreqStack {
    // Map to store frequency of each element
    private Map<Integer, Integer> frequencyMap;
    // Map to store stacks of elements for each frequency level
    private Map<Integer, Deque<Integer>> groupMap;
    // Track current maximum frequency
    private int maxFrequency;

    /**
     * Initialize the frequency stack
     */
    public FreqStack() {
        frequencyMap = new HashMap<>();
        groupMap = new HashMap<>();
        maxFrequency = 0;
    }
    
    /**
     * Push an element onto the stack
     */
    public void push(int val) {
        // Update frequency
        int freq = frequencyMap.getOrDefault(val, 0) + 1;
        frequencyMap.put(val, freq);
        
        // Update max frequency
        if (freq > maxFrequency) {
            maxFrequency = freq;
        }
        
        // Add to corresponding frequency group stack
        groupMap.putIfAbsent(freq, new ArrayDeque<>());
        groupMap.get(freq).push(val);
    }
    
    /**
     * Pop and return the most frequent element
     * In case of tie, return the most recently pushed element
     */
    public int pop() {
        // Get the stack of elements with max frequency
        Deque<Integer> stack = groupMap.get(maxFrequency);
        
        // Pop the top element (most recent at this frequency)
        int val = stack.pop();
        
        // Update frequency map
        frequencyMap.put(val, frequencyMap.get(val) - 1);
        
        // If the stack for current max frequency becomes empty, decrement maxFrequency
        if (stack.isEmpty()) {
            groupMap.remove(maxFrequency);
            maxFrequency--;
        }
        
        return val;
    }
    
    /**
     * Get the current maximum frequency in the stack
     * (Helper method for testing and debugging)
     */
    public int getMaxFrequency() {
        return maxFrequency;
    }
    
    /**
     * Get the frequency of a specific value
     * (Helper method for testing and debugging)
     */
    public int getFrequency(int val) {
        return frequencyMap.getOrDefault(val, 0);
    }
    
    /**
     * Get all elements in the stack (for debugging)
     */
    public List<Integer> getAllElements() {
        List<Integer> elements = new ArrayList<>();
        // This is simplified - actual order depends on frequency groups
        for (int freq = maxFrequency; freq >= 1; freq--) {
            if (groupMap.containsKey(freq)) {
                // Create a copy to avoid modifying the original stack
                Deque<Integer> stackCopy = new ArrayDeque<>(groupMap.get(freq));
                while (!stackCopy.isEmpty()) {
                    elements.add(stackCopy.removeLast()); // Add in push order
                }
            }
        }
        return elements;
    }
}

/**
 * Alternative Implementation 1: Using Priority Queue
 * O(log n) for push and pop, but more intuitive for some
 */
class FreqStackPriorityQueue {
    class Element {
        int val;
        int frequency;
        int pushOrder;
        
        Element(int val, int frequency, int pushOrder) {
            this.val = val;
            this.frequency = frequency;
            this.pushOrder = pushOrder;
        }
    }
    
    private Map<Integer, Integer> frequencyMap;
    private PriorityQueue<Element> maxHeap;
    private int pushCounter;
    
    public FreqStackPriorityQueue() {
        frequencyMap = new HashMap<>();
        maxHeap = new PriorityQueue<>((a, b) -> {
            if (a.frequency != b.frequency) {
                return b.frequency - a.frequency; // Higher frequency first
            }
            return b.pushOrder - a.pushOrder; // More recent push first
        });
        pushCounter = 0;
    }
    
    public void push(int val) {
        int freq = frequencyMap.getOrDefault(val, 0) + 1;
        frequencyMap.put(val, freq);
        maxHeap.offer(new Element(val, freq, pushCounter++));
    }
    
    public int pop() {
        Element element = maxHeap.poll();
        frequencyMap.put(element.val, element.frequency - 1);
        
        // Re-add remaining occurrences if any (simplified approach)
        // In practice, we'd need a more complex implementation
        
        return element.val;
    }
}

/**
 * Alternative Implementation 2: Using TreeMap and Stacks
 * More explicit frequency group management
 */
class FreqStackTreeMap {
    private Map<Integer, Integer> frequencyMap;
    private TreeMap<Integer, Deque<Integer>> frequencyGroups; // frequency -> stack
    private int maxFrequency;
    
    public FreqStackTreeMap() {
        frequencyMap = new HashMap<>();
        frequencyGroups = new TreeMap<>(Collections.reverseOrder()); // Descending order
        maxFrequency = 0;
    }
    
    public void push(int val) {
        int freq = frequencyMap.getOrDefault(val, 0) + 1;
        frequencyMap.put(val, freq);
        
        frequencyGroups.putIfAbsent(freq, new ArrayDeque<>());
        frequencyGroups.get(freq).push(val);
        
        if (freq > maxFrequency) {
            maxFrequency = freq;
        }
    }
    
    public int pop() {
        // Get the highest frequency group
        Deque<Integer> stack = frequencyGroups.get(maxFrequency);
        int val = stack.pop();
        
        frequencyMap.put(val, frequencyMap.get(val) - 1);
        
        if (stack.isEmpty()) {
            frequencyGroups.remove(maxFrequency);
            // Update maxFrequency by finding next highest
            maxFrequency = frequencyGroups.isEmpty() ? 0 : frequencyGroups.firstKey();
        }
        
        return val;
    }
}

/**
 * Alternative Implementation 3: Using LinkedHashSet for each frequency
 * Maintains insertion order within each frequency group
 */
class FreqStackLinkedHashSet {
    private Map<Integer, Integer> frequencyMap;
    private Map<Integer, LinkedHashSet<Integer>> groupMap;
    private int maxFrequency;
    
    public FreqStackLinkedHashSet() {
        frequencyMap = new HashMap<>();
        groupMap = new HashMap<>();
        maxFrequency = 0;
    }
    
    public void push(int val) {
        int freq = frequencyMap.getOrDefault(val, 0) + 1;
        frequencyMap.put(val, freq);
        
        groupMap.putIfAbsent(freq, new LinkedHashSet<>());
        groupMap.get(freq).add(val);
        
        if (freq > maxFrequency) {
            maxFrequency = freq;
        }
    }
    
    public int pop() {
        LinkedHashSet<Integer> set = groupMap.get(maxFrequency);
        
        // Get the most recently added element (last in insertion order)
        int val = -1;
        for (int element : set) {
            val = element; // This will give us the last element in insertion order
        }
        set.remove(val);
        
        frequencyMap.put(val, frequencyMap.get(val) - 1);
        
        if (set.isEmpty()) {
            groupMap.remove(maxFrequency);
            maxFrequency--;
        }
        
        return val;
    }
}

/**
 * Test class to verify all implementations
 */
class TestFreqStack {
    public static void main(String[] args) {
        System.out.println("Testing Maximum Frequency Stack:");
        System.out.println("================================\n");
        
        // Test 1: Basic example from problem statement
        System.out.println("Test 1: Basic Example");
        testBasicExample();
        
        // Test 2: Tie-breaking behavior
        System.out.println("\nTest 2: Tie-breaking Behavior");
        testTieBreaking();
        
        // Test 3: Single element
        System.out.println("\nTest 3: Single Element");
        testSingleElement();
        
        // Test 4: All same elements
        System.out.println("\nTest 4: All Same Elements");
        testAllSameElements();
        
        // Test 5: Mixed frequencies
        System.out.println("\nTest 5: Mixed Frequencies");
        testMixedFrequencies();
        
        // Test 6: Performance test
        System.out.println("\nTest 6: Performance Comparison");
        testPerformance();
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        explainAlgorithm();
        
        // All implementations consistency check
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONSISTENCY CHECK");
        System.out.println("=".repeat(70));
        checkAllImplementations();
    }
    
    private static void testBasicExample() {
        FreqStack freqStack = new FreqStack();
        int[] pushes = {5, 7, 5, 7, 4, 5};
        int[] expectedPops = {5, 7, 5, 4};
        
        System.out.println("Pushing: " + Arrays.toString(pushes));
        for (int val : pushes) {
            freqStack.push(val);
        }
        
        System.out.print("Popping: ");
        for (int expected : expectedPops) {
            int result = freqStack.pop();
            System.out.print(result + " ");
            if (result != expected) {
                System.out.print("(Expected: " + expected + ") ");
            }
        }
        System.out.println();
        
        // Visualization
        visualizeOperation(freqStack, "Basic Example");
    }
    
    private static void testTieBreaking() {
        FreqStack freqStack = new FreqStack();
        
        // Push sequence: A, B, A, B, C, A, B
        // Frequencies: A=3, B=3, C=1
        // Pop should return: B (most recent at frequency 3), then A, then B, then C, then A, then A
        freqStack.push(1); // A
        freqStack.push(2); // B
        freqStack.push(1); // A
        freqStack.push(2); // B
        freqStack.push(3); // C
        freqStack.push(1); // A
        freqStack.push(2); // B
        
        System.out.println("Push sequence: A, B, A, B, C, A, B");
        System.out.print("Pop sequence: ");
        
        // Expected: B, A, B, C, A, A (based on frequency and recency)
        int[] expected = {2, 1, 2, 3, 1, 1};
        for (int i = 0; i < expected.length; i++) {
            int result = freqStack.pop();
            System.out.print(result + " ");
            if (result != expected[i]) {
                System.out.print("(Expected: " + expected[i] + ") ");
            }
        }
        System.out.println();
    }
    
    private static void testSingleElement() {
        FreqStack freqStack = new FreqStack();
        
        freqStack.push(10);
        freqStack.push(10);
        freqStack.push(10);
        
        System.out.println("Pushed 10 three times");
        System.out.print("Pops: ");
        for (int i = 0; i < 3; i++) {
            System.out.print(freqStack.pop() + " ");
        }
        System.out.println();
    }
    
    private static void testAllSameElements() {
        FreqStack freqStack = new FreqStack();
        
        for (int i = 0; i < 5; i++) {
            freqStack.push(99);
        }
        
        System.out.println("Pushed 99 five times");
        System.out.print("Pops: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(freqStack.pop() + " ");
        }
        System.out.println();
    }
    
    private static void testMixedFrequencies() {
        FreqStack freqStack = new FreqStack();
        
        // Complex pattern to test various scenarios
        int[] pushes = {1, 2, 1, 3, 2, 1, 4, 2, 3, 1, 4, 4};
        System.out.println("Push sequence: " + Arrays.toString(pushes));
        
        for (int val : pushes) {
            freqStack.push(val);
        }
        
        System.out.print("Pop sequence: ");
        while (freqStack.getMaxFrequency() > 0) {
            System.out.print(freqStack.pop() + " ");
        }
        System.out.println();
    }
    
    private static void testPerformance() {
        int numOperations = 10000;
        System.out.println("Performance test with " + numOperations + " operations:");
        
        // Test main implementation
        long startTime = System.nanoTime();
        FreqStack stack1 = new FreqStack();
        for (int i = 0; i < numOperations; i++) {
            stack1.push(i % 100); // Push numbers 0-99 repeatedly
            if (i % 3 == 0) { // Pop every 3rd operation
                stack1.pop();
            }
        }
        long time1 = System.nanoTime() - startTime;
        
        // Test TreeMap implementation
        startTime = System.nanoTime();
        FreqStackTreeMap stack2 = new FreqStackTreeMap();
        for (int i = 0; i < numOperations; i++) {
            stack2.push(i % 100);
            if (i % 3 == 0) {
                stack2.pop();
            }
        }
        long time2 = System.nanoTime() - startTime;
        
        System.out.printf("HashMap + Stacks: %,12d ns%n", time1);
        System.out.printf("TreeMap:          %,12d ns%n", time2);
    }
    
    private static void visualizeOperation(FreqStack freqStack, String title) {
        System.out.println("\n" + title + " Visualization:");
        System.out.println("Current max frequency: " + freqStack.getMaxFrequency());
        
        List<Integer> elements = freqStack.getAllElements();
        System.out.println("All elements (in frequency groups, most recent first): " + elements);
        
        // Show frequency distribution
        System.out.println("Frequency distribution:");
        for (int element : elements) {
            System.out.printf("Element %d: frequency %d%n", element, freqStack.getFrequency(element));
        }
    }
    
    private static void explainAlgorithm() {
        System.out.println("\nMAIN IMPLEMENTATION (Two HashMaps with Frequency Stacks):");
        System.out.println("==========================================================");
        
        System.out.println("\nData Structures:");
        System.out.println("1. frequencyMap: HashMap<Integer, Integer>");
        System.out.println("   - Key: element value");
        System.out.println("   - Value: current frequency count");
        System.out.println("2. groupMap: HashMap<Integer, Deque<Integer>>");
        System.out.println("   - Key: frequency level");
        System.out.println("   - Value: stack of elements with that frequency");
        System.out.println("3. maxFrequency: int");
        System.out.println("   - Tracks the current maximum frequency in the stack");
        
        System.out.println("\nPush Operation:");
        System.out.println("1. Get current frequency of val from frequencyMap");
        System.out.println("2. Increment frequency by 1");
        System.out.println("3. Update frequencyMap with new frequency");
        System.out.println("4. If new frequency > maxFrequency, update maxFrequency");
        System.out.println("5. Add val to groupMap[frequency] stack");
        
        System.out.println("\nPop Operation:");
        System.out.println("1. Get the stack from groupMap[maxFrequency]");
        System.out.println("2. Pop the top element from the stack (most recent)");
        System.out.println("3. Decrement frequency of popped element in frequencyMap");
        System.out.println("4. If stack becomes empty:");
        System.out.println("   - Remove groupMap[maxFrequency]");
        System.out.println("   - Decrement maxFrequency");
        
        System.out.println("\nWhy This Works:");
        System.out.println("- Each frequency level maintains its own LIFO stack");
        System.out.println("- When frequencies tie, the most recently pushed element is at the top");
        System.out.println("- Moving elements between frequency stacks preserves recency ordering");
        System.out.println("- maxFrequency ensures we always know where to pop from");
        
        System.out.println("\nExample Walkthrough:");
        System.out.println("Push 5: freq=1, maxFreq=1, groupMap[1] = [5]");
        System.out.println("Push 7: freq=1, maxFreq=1, groupMap[1] = [5, 7]");
        System.out.println("Push 5: freq=2, maxFreq=2, groupMap[2] = [5]");
        System.out.println("Pop: from groupMap[2] -> 5, freq=1, maxFreq=1");
        System.out.println("Pop: from groupMap[1] -> 7, freq=0, maxFreq=1");
    }
    
    private static void checkAllImplementations() {
        int[][] testCases = {
            {5, 7, 5, 7, 4, 5}, // Basic example
            {1, 2, 1, 2, 3, 1, 2}, // Tie-breaking
            {10, 10, 10}, // Single element repeated
            {1, 2, 3, 4, 5} // All unique
        };
        
        String[] testNames = {
            "Basic Example",
            "Tie-breaking",
            "Single Element Repeated", 
            "All Unique Elements"
        };
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\nTest: " + testNames[i]);
            System.out.println("Input: " + Arrays.toString(testCases[i]));
            
            // Test all implementations
            FreqStack stack1 = new FreqStack();
            FreqStackTreeMap stack2 = new FreqStackTreeMap();
            FreqStackLinkedHashSet stack3 = new FreqStackLinkedHashSet();
            
            // Push all elements
            for (int val : testCases[i]) {
                stack1.push(val);
                stack2.push(val);
                stack3.push(val);
            }
            
            // Pop all elements and compare
            boolean consistent = true;
            System.out.print("Pops - Main: ");
            while (stack1.getMaxFrequency() > 0) {
                int pop1 = stack1.pop();
                int pop2 = stack2.pop();
                int pop3 = stack3.pop();
                
                System.out.print(pop1 + " ");
                
                if (pop1 != pop2 || pop1 != pop3) {
                    consistent = false;
                    System.out.printf("(INCONSISTENT: %d vs %d vs %d) ", pop1, pop2, pop3);
                }
            }
            System.out.println();
            System.out.println("Consistent across implementations: " + (consistent ? "YES" : "NO"));
        }
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. MAIN IMPLEMENTATION (Two HashMaps with Stacks):");
        System.out.println("   Time: O(1) for push and pop");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros: Most efficient, elegant solution");
        System.out.println("   Cons: Requires understanding of two-map approach");
        
        System.out.println("\n2. PRIORITY QUEUE APPROACH:");
        System.out.println("   Time: O(log n) for push and pop");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros: Intuitive priority-based approach");
        System.out.println("   Cons: Slower due to heap operations");
        
        System.out.println("\n3. TREEMAP APPROACH:");
        System.out.println("   Time: O(log k) for operations (k = distinct frequencies)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros: Explicit frequency ordering");
        System.out.println("   Cons: Slightly slower than HashMap approach");
        
        System.out.println("\n4. LINKEDHASHSET APPROACH:");
        System.out.println("   Time: O(1) for push, O(n) for pop (to find last element)");
        System.out.println("   Space: O(n)");
        System.out.println("   Pros: Maintains insertion order explicitly");
        System.out.println("   Cons: Inefficient pop operation");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY");
        System.out.println("=".repeat(70));
        System.out.println("1. Start by clarifying requirements: most frequent, tie-break by recency");
        System.out.println("2. Discuss brute force approach first (sorting by frequency and timestamp)");
        System.out.println("3. Then optimize to the two HashMap approach");
        System.out.println("4. Draw diagrams to explain the frequency group stacks");
        System.out.println("5. Walk through an example to demonstrate tie-breaking");
        System.out.println("6. Discuss time/space complexity trade-offs");
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */
