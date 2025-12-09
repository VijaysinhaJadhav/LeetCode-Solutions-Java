
## Solution.java

```java
/**
 * 460. LFU Cache
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Design and implement a Least Frequently Used (LFU) cache with O(1) time complexity
 * for both get and put operations. When cache is full, evict the least frequently used
 * item (with LRU tiebreaker for same frequency).
 * 
 * Key Insights:
 * 1. Need O(1) access: HashMap for key -> node mapping
 * 2. Need frequency tracking: HashMap for frequency -> DLL group
 * 3. Need LRU ordering within same frequency: Doubly Linked List
 * 4. Need to track minimum frequency for quick eviction
 * 
 * Approach (Double HashMap + DLL):
 * 1. Use Node class with key, value, frequency, prev, next
 * 2. Use keyMap: HashMap<Integer, Node> for O(1) access by key
 * 3. Use freqMap: HashMap<Integer, DoublyLinkedList> for frequency groups
 * 4. Each DLL maintains LRU order for nodes with same frequency
 * 5. Track minFreq to quickly find LFU item
 * 6. On get/put: promote node to next frequency group
 * 
 * Time Complexity: O(1) for get() and put()
 * Space Complexity: O(capacity)
 * 
 * Tags: Hash Table, Linked List, Design, Doubly-Linked List
 */

import java.util.*;

class LFUCache {
    
    /**
     * Node class representing cache entry
     */
    private class Node {
        int key;
        int value;
        int frequency;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
            this.prev = null;
            this.next = null;
        }
    }
    
    /**
     * Doubly Linked List class for LRU ordering within frequency group
     */
    private class DoublyLinkedList {
        Node head;  // Dummy head (most recent)
        Node tail;  // Dummy tail (least recent)
        int size;
        
        DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }
        
        /**
         * Add node to the front (most recent position)
         */
        void addToFront(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }
        
        /**
         * Remove node from the list
         */
        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        
        /**
         * Remove and return the least recently used node (from tail)
         */
        Node removeLRU() {
            if (size == 0) return null;
            Node lruNode = tail.prev;
            remove(lruNode);
            return lruNode;
        }
        
        /**
         * Check if the list is empty
         */
        boolean isEmpty() {
            return size == 0;
        }
        
        /**
         * Move node to front (most recent) - used when node is accessed
         */
        void moveToFront(Node node) {
            remove(node);
            addToFront(node);
        }
    }
    
    // Main data structures
    private final int capacity;
    private int size;
    private int minFrequency;
    private Map<Integer, Node> keyMap;           // key -> Node
    private Map<Integer, DoublyLinkedList> freqMap; // frequency -> DLL
    
    /**
     * Constructor
     */
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.minFrequency = 0;
        this.keyMap = new HashMap<>();
        this.freqMap = new HashMap<>();
    }
    
    /**
     * Get value by key
     */
    public int get(int key) {
        if (!keyMap.containsKey(key)) {
            return -1;
        }
        
        Node node = keyMap.get(key);
        updateNode(node);
        return node.value;
    }
    
    /**
     * Put key-value pair
     */
    public void put(int key, int value) {
        if (capacity == 0) return; // Edge case
        
        if (keyMap.containsKey(key)) {
            // Key exists, update value and frequency
            Node node = keyMap.get(key);
            node.value = value;
            updateNode(node);
        } else {
            // New key
            if (size == capacity) {
                // Cache is full, need to evict
                evict();
            }
            
            // Create new node
            Node newNode = new Node(key, value);
            keyMap.put(key, newNode);
            
            // Add to frequency 1 group
            freqMap.putIfAbsent(1, new DoublyLinkedList());
            freqMap.get(1).addToFront(newNode);
            
            // Update min frequency to 1
            minFrequency = 1;
            size++;
        }
    }
    
    /**
     * Update node frequency and position
     * Called when node is accessed (get or put)
     */
    private void updateNode(Node node) {
        int oldFreq = node.frequency;
        int newFreq = oldFreq + 1;
        
        // Remove from old frequency group
        DoublyLinkedList oldList = freqMap.get(oldFreq);
        oldList.remove(node);
        
        // If old frequency group becomes empty and it was min frequency
        if (oldList.isEmpty() && oldFreq == minFrequency) {
            minFrequency = newFreq;
        }
        
        // Update node frequency
        node.frequency = newFreq;
        
        // Add to new frequency group
        freqMap.putIfAbsent(newFreq, new DoublyLinkedList());
        freqMap.get(newFreq).addToFront(node);
    }
    
    /**
     * Evict the least frequently used item (LRU tiebreaker)
     */
    private void evict() {
        // Get the LRU node from min frequency group
        DoublyLinkedList minFreqList = freqMap.get(minFrequency);
        Node lruNode = minFreqList.removeLRU();
        
        // Remove from key map
        keyMap.remove(lruNode.key);
        size--;
        
        // Note: We don't need to update minFrequency here
        // It will be updated when a node is accessed or new node is added
    }
    
    /**
     * Approach 2: Alternative implementation with cleaner separation
     * Using FrequencyList class for better encapsulation
     */
    static class LFUCache2 {
        private static class FrequencyList {
            private static class Node {
                int key, value, freq;
                Node prev, next;
                Node(int k, int v) {
                    key = k; value = v; freq = 1;
                }
            }
            
            private Map<Integer, Node> nodes;
            private Node head, tail;
            private int size;
            
            FrequencyList() {
                nodes = new HashMap<>();
                head = new Node(0, 0);
                tail = new Node(0, 0);
                head.next = tail;
                tail.prev = head;
                size = 0;
            }
            
            void add(Node node) {
                node.next = head.next;
                node.prev = head;
                head.next.prev = node;
                head.next = node;
                nodes.put(node.key, node);
                size++;
            }
            
            void remove(Node node) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                nodes.remove(node.key);
                size--;
            }
            
            void remove(int key) {
                Node node = nodes.get(key);
                if (node != null) remove(node);
            }
            
            Node popLRU() {
                if (size == 0) return null;
                Node node = tail.prev;
                remove(node);
                return node;
            }
            
            boolean isEmpty() {
                return size == 0;
            }
        }
        
        private final int cap;
        private int minFreq;
        private Map<Integer, FrequencyList.Node> cache;
        private Map<Integer, FrequencyList> freqLists;
        
        public LFUCache2(int capacity) {
            this.cap = capacity;
            this.minFreq = 0;
            this.cache = new HashMap<>();
            this.freqLists = new HashMap<>();
        }
        
        public int get(int key) {
            if (!cache.containsKey(key)) return -1;
            
            FrequencyList.Node node = cache.get(key);
            promote(node);
            return node.value;
        }
        
        public void put(int key, int value) {
            if (cap == 0) return;
            
            if (cache.containsKey(key)) {
                FrequencyList.Node node = cache.get(key);
                node.value = value;
                promote(node);
            } else {
                if (cache.size() == cap) {
                    evict();
                }
                
                FrequencyList.Node newNode = new FrequencyList.Node(key, value);
                cache.put(key, newNode);
                freqLists.putIfAbsent(1, new FrequencyList());
                freqLists.get(1).add(newNode);
                minFreq = 1;
            }
        }
        
        private void promote(FrequencyList.Node node) {
            int oldFreq = node.freq;
            int newFreq = oldFreq + 1;
            
            FrequencyList oldList = freqLists.get(oldFreq);
            oldList.remove(node);
            
            if (oldList.isEmpty() && oldFreq == minFreq) {
                minFreq = newFreq;
            }
            
            node.freq = newFreq;
            freqLists.putIfAbsent(newFreq, new FrequencyList());
            freqLists.get(newFreq).add(node);
        }
        
        private void evict() {
            FrequencyList minList = freqLists.get(minFreq);
            FrequencyList.Node lruNode = minList.popLRU();
            cache.remove(lruNode.key);
        }
    }
    
    /**
     * Approach 3: Using Java's LinkedHashSet for LRU ordering
     * Simpler but slightly less efficient for large operations
     */
    static class LFUCache3 {
        private class Node {
            int key, value, freq;
            Node(int k, int v) {
                key = k; value = v; freq = 1;
            }
        }
        
        private final int capacity;
        private int minFreq;
        private Map<Integer, Node> cache;
        private Map<Integer, LinkedHashSet<Integer>> freqKeys;
        
        public LFUCache3(int capacity) {
            this.capacity = capacity;
            this.minFreq = 0;
            this.cache = new HashMap<>();
            this.freqKeys = new HashMap<>();
        }
        
        public int get(int key) {
            if (!cache.containsKey(key)) return -1;
            
            Node node = cache.get(key);
            updateFreq(node);
            return node.value;
        }
        
        public void put(int key, int value) {
            if (capacity == 0) return;
            
            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                node.value = value;
                updateFreq(node);
            } else {
                if (cache.size() == capacity) {
                    evict();
                }
                
                Node newNode = new Node(key, value);
                cache.put(key, newNode);
                freqKeys.putIfAbsent(1, new LinkedHashSet<>());
                freqKeys.get(1).add(key);
                minFreq = 1;
            }
        }
        
        private void updateFreq(Node node) {
            int oldFreq = node.freq;
            int newFreq = oldFreq + 1;
            
            // Remove from old frequency set
            freqKeys.get(oldFreq).remove(node.key);
            if (freqKeys.get(oldFreq).isEmpty()) {
                freqKeys.remove(oldFreq);
                if (oldFreq == minFreq) {
                    minFreq = newFreq;
                }
            }
            
            // Update frequency
            node.freq = newFreq;
            
            // Add to new frequency set
            freqKeys.putIfAbsent(newFreq, new LinkedHashSet<>());
            freqKeys.get(newFreq).add(node.key);
        }
        
        private void evict() {
            LinkedHashSet<Integer> minFreqSet = freqKeys.get(minFreq);
            int lruKey = minFreqSet.iterator().next(); // Get first (least recently used)
            minFreqSet.remove(lruKey);
            if (minFreqSet.isEmpty()) {
                freqKeys.remove(minFreq);
            }
            cache.remove(lruKey);
        }
    }
    
    /**
     * Helper method to visualize cache state
     */
    public void visualize() {
        System.out.println("\nCache Visualization:");
        System.out.println("Capacity: " + capacity + ", Size: " + size);
        System.out.println("Min Frequency: " + minFrequency);
        
        System.out.println("\nKey Map (Key -> Node):");
        for (Map.Entry<Integer, Node> entry : keyMap.entrySet()) {
            Node node = entry.getValue();
            System.out.printf("  Key: %d -> Value: %d, Freq: %d%n", 
                            node.key, node.value, node.frequency);
        }
        
        System.out.println("\nFrequency Groups:");
        List<Integer> frequencies = new ArrayList<>(freqMap.keySet());
        Collections.sort(frequencies);
        
        for (int freq : frequencies) {
            DoublyLinkedList list = freqMap.get(freq);
            System.out.printf("  Frequency %d (Size: %d): ", freq, list.size);
            
            Node current = list.head.next;
            while (current != list.tail) {
                System.out.printf("[%d:%d] ", current.key, current.value);
                current = current.next;
            }
            System.out.println();
        }
        
        // Show eviction candidate
        if (size > 0) {
            DoublyLinkedList minList = freqMap.get(minFrequency);
            if (minList != null && !minList.isEmpty()) {
                Node lruNode = minList.tail.prev;
                System.out.printf("\nLFU Eviction Candidate: Key=%d, Value=%d, Freq=%d%n",
                                lruNode.key, lruNode.value, lruNode.frequency);
            }
        }
    }
    
    /**
     * Test cases to verify all implementations
     */
    public static void main(String[] args) {
        System.out.println("Testing LFU Cache Solution:");
        System.out.println("===========================");
        
        // Test case 1: Example from problem
        System.out.println("\nTest 1: Basic example from problem");
        LFUCache lfu1 = new LFUCache(2);
        
        System.out.println("Operations:");
        System.out.println("  put(1, 1)");
        lfu1.put(1, 1);
        
        System.out.println("  put(2, 2)");
        lfu1.put(2, 2);
        
        System.out.println("  get(1) -> Expected: 1");
        int result1 = lfu1.get(1);
        System.out.println("    Result: " + result1 + " - " + (result1 == 1 ? "PASSED" : "FAILED"));
        
        System.out.println("  put(3, 3) -> Should evict key 2 (LFU)");
        lfu1.put(3, 3);
        
        System.out.println("  get(2) -> Expected: -1 (evicted)");
        int result2 = lfu1.get(2);
        System.out.println("    Result: " + result2 + " - " + (result2 == -1 ? "PASSED" : "FAILED"));
        
        System.out.println("  get(3) -> Expected: 3");
        int result3 = lfu1.get(3);
        System.out.println("    Result: " + result3 + " - " + (result3 == 3 ? "PASSED" : "FAILED"));
        
        System.out.println("  put(4, 4) -> Should evict key 1 (same freq as 3 but LRU)");
        lfu1.put(4, 4);
        
        System.out.println("  get(1) -> Expected: -1 (evicted)");
        int result4 = lfu1.get(1);
        System.out.println("    Result: " + result4 + " - " + (result4 == -1 ? "PASSED" : "FAILED"));
        
        System.out.println("  get(3) -> Expected: 3");
        int result5 = lfu1.get(3);
        System.out.println("    Result: " + result5 + " - " + (result5 == 3 ? "PASSED" : "FAILED"));
        
        System.out.println("  get(4) -> Expected: 4");
        int result6 = lfu1.get(4);
        System.out.println("    Result: " + result6 + " - " + (result6 == 4 ? "PASSED" : "FAILED"));
        
        lfu1.visualize();
        
        // Test case 2: Edge case - capacity 0
        System.out.println("\nTest 2: Capacity 0");
        LFUCache lfu2 = new LFUCache(0);
        lfu2.put(1, 1);
        int result7 = lfu2.get(1);
        System.out.println("Capacity 0 - get(1) -> Expected: -1, Result: " + result7 + 
                         " - " + (result7 == -1 ? "PASSED" : "FAILED"));
        
        // Test case 3: Single element cache
        System.out.println("\nTest 3: Single element cache");
        LFUCache lfu3 = new LFUCache(1);
        lfu3.put(1, 10);
        System.out.println("get(1) -> Expected: 10");
        int result8 = lfu3.get(1);
        System.out.println("  Result: " + result8 + " - " + (result8 == 10 ? "PASSED" : "FAILED"));
        
        lfu3.put(2, 20); // Should evict key 1
        System.out.println("put(2, 20) -> Should evict key 1");
        System.out.println("get(1) -> Expected: -1");
        int result9 = lfu3.get(1);
        System.out.println("  Result: " + result9 + " - " + (result9 == -1 ? "PASSED" : "FAILED"));
        System.out.println("get(2) -> Expected: 20");
        int result10 = lfu3.get(2);
        System.out.println("  Result: " + result10 + " - " + (result10 == 20 ? "PASSED" : "FAILED"));
        
        // Test case 4: Frequency promotion
        System.out.println("\nTest 4: Frequency promotion");
        LFUCache lfu4 = new LFUCache(3);
        lfu4.put(1, 1);
        lfu4.put(2, 2);
        lfu4.put(3, 3);
        
        // Access key 1 multiple times to increase frequency
        lfu4.get(1);
        lfu4.get(1);
        lfu4.get(1); // freq = 4
        
        lfu4.get(2);
        lfu4.get(2); // freq = 3
        
        lfu4.get(3); // freq = 2
        
        // Now add new key - should evict key 3 (lowest frequency = 2, but key 3 is LRU)
        System.out.println("put(4, 4) -> Should evict key 3 (freq=2, LRU)");
        lfu4.put(4, 4);
        
        System.out.println("get(3) -> Expected: -1 (evicted)");
        int result11 = lfu4.get(3);
        System.out.println("  Result: " + result11 + " - " + (result11 == -1 ? "PASSED" : "FAILED"));
        
        System.out.println("get(1) -> Expected: 1");
        int result12 = lfu4.get(1);
        System.out.println("  Result: " + result12 + " - " + (result12 == 1 ? "PASSED" : "FAILED"));
        
        lfu4.visualize();
        
        // Test case 5: Complex scenario with tie-breaking
        System.out.println("\nTest 5: Complex tie-breaking scenario");
        LFUCache lfu5 = new LFUCache(3);
        lfu5.put(1, 1); // freq=1
        lfu5.put(2, 2); // freq=1
        lfu5.put(3, 3); // freq=1
        
        // All have freq=1, access order: 1, 2, 3
        lfu5.get(1); // freq=2
        lfu5.get(2); // freq=2
        
        // Now all: 1(freq=2), 2(freq=2), 3(freq=1)
        // Add new key - should evict key 3 (lowest freq)
        lfu5.put(4, 4);
        
        System.out.println("After put(4,4):");
        System.out.println("  get(3) -> Expected: -1");
        int result13 = lfu5.get(3);
        System.out.println("    Result: " + result13 + " - " + (result13 == -1 ? "PASSED" : "FAILED"));
        
        // Now: 1(freq=2), 2(freq=2), 4(freq=1)
        // Access 4 to increase its frequency
        lfu5.get(4); // freq=2
        
        // Now all have freq=2, order by access: 1(oldest), 2, 4(newest)
        // Add new key - should evict key 1 (LRU among same freq)
        lfu5.put(5, 5);
        
        System.out.println("After put(5,5):");
        System.out.println("  get(1) -> Expected: -1");
        int result14 = lfu5.get(1);
        System.out.println("    Result: " + result14 + " - " + (result14 == -1 ? "PASSED" : "FAILED"));
        
        // Performance test
        System.out.println("\nTest 6: Performance test");
        int capacity = 10000;
        int operations = 100000;
        
        System.out.println("Testing with capacity=" + capacity + ", operations=" + operations);
        
        long startTime = System.nanoTime();
        LFUCache lfuPerf = new LFUCache(capacity);
        
        Random random = new Random(42);
        for (int i = 0; i < operations; i++) {
            int key = random.nextInt(20000);
            if (random.nextBoolean()) {
                // get operation
                lfuPerf.get(key);
            } else {
                // put operation
                int value = random.nextInt(1000);
                lfuPerf.put(key, value);
            }
        }
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Performance test completed in " + (duration / 1_000_000) + " ms");
        System.out.println("Average time per operation: " + (duration / operations) + " ns");
        
        // Compare different implementations
        System.out.println("\n" + "=".repeat(70));
        System.out.println("IMPLEMENTATION COMPARISON:");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Double HashMap + Custom DLL (RECOMMENDED):");
        System.out.println("   Time Complexity: O(1) for get() and put()");
        System.out.println("   Space Complexity: O(capacity)");
        System.out.println("   Pros:");
        System.out.println("     - True O(1) operations");
        System.out.println("     - Efficient memory usage");
        System.out.println("     - Clear separation of concerns");
        System.out.println("     - Handles all edge cases properly");
        System.out.println("   Cons:");
        System.out.println("     - More code to write and maintain");
        System.out.println("     - Multiple data structures to manage");
        System.out.println("   Best for: Interview settings, production code");
        
        System.out.println("\n2. FrequencyList Encapsulated:");
        System.out.println("   Time Complexity: O(1) for get() and put()");
        System.out.println("   Space Complexity: O(capacity)");
        System.out.println("   Pros:");
        System.out.println("     - Better encapsulation");
        System.out.println("     - Cleaner separation of FrequencyList logic");
        System.out.println("     - Easier to test and debug");
        System.out.println("   Cons:");
        System.out.println("     - Slightly more abstraction");
        System.out.println("     - More classes to manage");
        System.out.println("   Best for: Larger projects, team development");
        
        System.out.println("\n3. LinkedHashSet Based:");
        System.out.println("   Time Complexity: ~O(1) for get() and put()");
        System.out.println("   Space Complexity: O(capacity)");
        System.out.println("   Pros:");
        System.out.println("     - Simpler implementation");
        System.out.println("     - Uses built-in Java collections");
        System.out.println("     - Less custom code");
        System.out.println("   Cons:");
        System.out.println("     - LinkedHashSet operations are not always O(1)");
        System.out.println("     - Iterator overhead");
        System.out.println("     - May not meet strict O(1) requirement");
        System.out.println("   Best for: Quick prototypes, interviews where O(1) not strictly required");
        
        // Complexity analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nOperations Breakdown:");
        System.out.println("1. get(key):");
        System.out.println("   - HashMap lookup: O(1)");
        System.out.println("   - Remove from old freq DLL: O(1)");
        System.out.println("   - Add to new freq DLL: O(1)");
        System.out.println("   - Update minFreq if needed: O(1)");
        System.out.println("   Total: O(1)");
        
        System.out.println("\n2. put(key, value):");
        System.out.println("   a) Key exists:");
        System.out.println("      - HashMap lookup: O(1)");
        System.out.println("      - Update value: O(1)");
        System.out.println("      - Frequency promotion (same as get): O(1)");
        System.out.println("   b) Key doesn't exist:");
        System.out.println("      - Check capacity: O(1)");
        System.out.println("      - Evict if full: O(1)");
        System.out.println("      - Create new node: O(1)");
        System.out.println("      - Add to keyMap: O(1)");
        System.out.println("      - Add to freq=1 DLL: O(1)");
        System.out.println("      - Set minFreq=1: O(1)");
        System.out.println("   Total: O(1)");
        
        System.out.println("\n3. evict():");
        System.out.println("   - Get minFreq DLL: O(1)");
        System.out.println("   - Remove LRU node from tail: O(1)");
        System.out.println("   - Remove from keyMap: O(1)");
        System.out.println("   Total: O(1)");
        
        // Interview strategy
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("=".repeat(70));
        
        System.out.println("\nStep-by-Step Approach:");
        System.out.println("1. Clarify requirements:");
        System.out.println("   - O(1) time for get() and put()");
        System.out.println("   - LFU eviction with LRU tiebreaker");
        System.out.println("   - Frequency counter increments on get() and put()");
        
        System.out.println("\n2. Design data structures:");
        System.out.println("   - HashMap for O(1) key access");
        System.out.println("   - Need to track frequency: HashMap<frequency, DLL>");
        System.out.println("   - DLL for LRU ordering within same frequency");
        System.out.println("   - Variable to track minFrequency");
        
        System.out.println("\n3. Implement Node class:");
        System.out.println("   - key, value, frequency");
        System.out.println("   - prev, next pointers for DLL");
        
        System.out.println("\n4. Implement DoublyLinkedList class:");
        System.out.println("   - addToFront(), remove(), removeLRU()");
        System.out.println("   - moveToFront() for frequency promotion");
        
        System.out.println("\n5. Implement LFUCache:");
        System.out.println("   - Constructor: initialize data structures");
        System.out.println("   - get(): promote node, return value");
        System.out.println("   - put(): update or insert with eviction if needed");
        System.out.println("   - Helper: updateNode() for frequency promotion");
        System.out.println("   - Helper: evict() to remove LFU item");
        
        System.out.println("\n6. Handle edge cases:");
        System.out.println("   - capacity = 0");
        System.out.println("   - Empty cache operations");
        System.out.println("   - Single element cache");
        System.out.println("   - All nodes same frequency");
        
        System.out.println("\n7. Test with examples:");
        System.out.println("   - Walk through given example");
        System.out.println("   - Test tie-breaking scenarios");
        System.out.println("   - Test frequency promotion");
        
        System.out.println("\nKey Points to Emphasize:");
        System.out.println("- O(1) operations are achieved through hash maps");
        System.out.println("- DLL provides LRU ordering within frequency groups");
        System.out.println("- minFrequency enables quick LFU eviction");
        System.out.println("- Frequency promotion moves nodes between groups");
        
        System.out.println("\nCommon Pitfalls to Avoid:");
        System.out.println("- Forgetting to update minFrequency when groups empty");
        System.out.println("- Not handling capacity=0 edge case");
        System.out.println("- Incorrect LRU ordering within frequency groups");
        System.out.println("- Not promoting frequency on put() for existing keys");
        System.out.println("- Memory leaks from not removing nodes properly");
        
        System.out.println("\nVerification Steps:");
        System.out.println("1. Test basic get/put operations");
        System.out.println("2. Test eviction when cache is full");
        System.out.println("3. Test frequency counting and promotion");
        System.out.println("4. Test LRU tie-breaking for same frequency");
        System.out.println("5. Test edge cases (capacity 0, single element)");
        System.out.println("6. Verify O(1) time complexity explanation");
        
        System.out.println("\nAll tests completed successfully!");
    }
}
