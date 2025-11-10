
## Solution.java

```java
/**
 * 146. LRU Cache
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * 
 * Key Insights:
 * 1. Need O(1) access - HashMap for key to node mapping
 * 2. Need O(1) rearrangement - Doubly linked list for order maintenance
 * 3. When element accessed: move to head (most recent)
 * 4. When capacity exceeded: remove tail (least recent)
 * 5. Use dummy head and tail to simplify edge cases
 * 
 * Approach:
 * 1. HashMap stores key -> Node mapping for O(1) access
 * 2. Doubly linked list maintains usage order
 * 3. Head is most recently used, tail is least recently used
 * 4. On get/put: move node to head
 * 5. On put when full: remove tail node
 * 
 * Time Complexity: O(1) for both get and put
 * Space Complexity: O(capacity)
 * 
 * Tags: Hash Table, Linked List, Design, Doubly-Linked List
 */

import java.util.*;

class LRUCache {
    
    /**
     * Doubly Linked List Node class
     */
    class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
        
        Node() {
            this(0, 0);
        }
    }
    
    // Cache capacity
    private int capacity;
    
    // HashMap for O(1) access to nodes
    private Map<Integer, Node> cache;
    
    // Doubly linked list pointers
    private Node head;
    private Node tail;
    
    /**
     * Initialize the LRU Cache with positive size capacity
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Initialize dummy head and tail nodes
        this.head = new Node();
        this.tail = new Node();
        
        // Connect head and tail
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * Get the value of the key if exists, otherwise return -1
     * Time: O(1)
     */
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        
        Node node = cache.get(key);
        // Move accessed node to head (most recently used)
        moveToHead(node);
        return node.value;
    }
    
    /**
     * Update or add the key-value pair
     * Time: O(1)
     */
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            // Key exists - update value and move to head
            Node node = cache.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            // Key doesn't exist - create new node
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNode(newNode);
            
            // Check capacity and evict if necessary
            if (cache.size() > capacity) {
                Node tailNode = popTail();
                cache.remove(tailNode.key);
            }
        }
    }
    
    /**
     * Add a new node right after head
     */
    private void addNode(Node node) {
        // Always add new node at head (most recent)
        node.prev = head;
        node.next = head.next;
        
        head.next.prev = node;
        head.next = node;
    }
    
    /**
     * Remove an existing node from the linked list
     */
    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        
        prev.next = next;
        next.prev = prev;
    }
    
    /**
     * Move certain node to head (most recent)
     */
    private void moveToHead(Node node) {
        removeNode(node);
        addNode(node);
    }
    
    /**
     * Pop the current tail (least recent)
     */
    private Node popTail() {
        Node res = tail.prev;
        removeNode(res);
        return res;
    }
    
    /**
     * Helper method to visualize the current cache state
     */
    public void visualizeCache() {
        System.out.println("\nCurrent Cache State (Capacity: " + capacity + ")");
        System.out.println("Most Recent -> Least Recent");
        
        Node current = head.next;
        List<String> elements = new ArrayList<>();
        
        while (current != tail) {
            elements.add(current.key + "=" + current.value);
            current = current.next;
        }
        
        System.out.println("Cache: " + elements);
        System.out.println("HashMap keys: " + cache.keySet());
    }
}

/**
 * Alternative implementation using LinkedHashMap (built-in Java)
 * Simpler but less educational for understanding the underlying mechanics
 */
class LRUCacheLinkedHashMap extends LinkedHashMap<Integer, Integer> {
    private int capacity;
    
    public LRUCacheLinkedHashMap(int capacity) {
        // accessOrder = true for LRU behavior
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }
    
    public int get(int key) {
        return super.getOrDefault(key, -1);
    }
    
    public void put(int key, int value) {
        super.put(key, value);
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}

/**
 * Comprehensive test class with multiple implementations and test cases
 */
public class Solution {
    
    /**
     * Test the LRU Cache implementation
     */
    public static void main(String[] args) {
        System.out.println("Testing LRU Cache Implementation");
        System.out.println("================================\n");
        
        // Test Case 1: Basic operations from example
        System.out.println("Test 1: Basic operations from problem example");
        LRUCache cache1 = new LRUCache(2);
        
        cache1.put(1, 1);
        cache1.put(2, 2);
        System.out.println("get(1): " + cache1.get(1) + " (expected: 1)"); // returns 1
        
        cache1.put(3, 3); // evicts key 2
        System.out.println("get(2): " + cache1.get(2) + " (expected: -1)"); // returns -1 (not found)
        
        cache1.put(4, 4); // evicts key 1
        System.out.println("get(1): " + cache1.get(1) + " (expected: -1)"); // returns -1 (not found)
        System.out.println("get(3): " + cache1.get(3) + " (expected: 3)"); // returns 3
        System.out.println("get(4): " + cache1.get(4) + " (expected: 4)"); // returns 4
        
        cache1.visualizeCache();
        
        // Test Case 2: Capacity 1
        System.out.println("\nTest 2: Capacity 1");
        LRUCache cache2 = new LRUCache(1);
        cache2.put(1, 1);
        System.out.println("get(1): " + cache2.get(1) + " (expected: 1)");
        cache2.put(2, 2); // evicts key 1
        System.out.println("get(1): " + cache2.get(1) + " (expected: -1)");
        System.out.println("get(2): " + cache2.get(2) + " (expected: 2)");
        
        // Test Case 3: Update existing key
        System.out.println("\nTest 3: Update existing key");
        LRUCache cache3 = new LRUCache(2);
        cache3.put(1, 1);
        cache3.put(2, 2);
        cache3.put(1, 10); // update key 1
        System.out.println("get(1): " + cache3.get(1) + " (expected: 10)");
        cache3.put(3, 3); // should evict key 2, not key 1
        System.out.println("get(2): " + cache3.get(2) + " (expected: -1)");
        System.out.println("get(1): " + cache3.get(1) + " (expected: 10)");
        
        // Test Case 4: Large capacity
        System.out.println("\nTest 4: Large capacity operations");
        LRUCache cache4 = new LRUCache(3);
        for (int i = 1; i <= 5; i++) {
            cache4.put(i, i * 10);
            if (i >= 3) {
                cache4.visualizeCache();
            }
        }
        
        // Test get operations to change order
        System.out.println("Accessing key 3 to make it most recent");
        cache4.get(3);
        cache4.visualizeCache();
        
        // Test Case 5: Complex access pattern
        System.out.println("\nTest 5: Complex access pattern");
        LRUCache cache5 = new LRUCache(3);
        int[] operations = {1, 2, 3, 4, 2, 5, 1, 6};
        for (int key : operations) {
            if (cache5.get(key) == -1) {
                cache5.put(key, key * 100);
                System.out.println("Put: " + key + " -> " + (key * 100));
            } else {
                System.out.println("Get: " + key + " -> " + cache5.get(key));
            }
            cache5.visualizeCache();
        }
        
        // Performance comparison
        System.out.println("\nPerformance Comparison");
        comparePerformance();
        
        // Algorithm explanation
        System.out.println("\n" + "=".repeat(70));
        System.out.println("LRU CACHE ALGORITHM EXPLANATION");
        System.out.println("=".repeat(70));
        
        explainAlgorithm();
    }
    
    /**
     * Compare performance of different implementations
     */
    private static void comparePerformance() {
        System.out.println("Performance Test with 10,000 operations:");
        
        // Custom implementation
        long startTime = System.nanoTime();
        LRUCache customCache = new LRUCache(1000);
        for (int i = 0; i < 10000; i++) {
            if (i % 3 == 0) {
                customCache.get(i % 500);
            } else {
                customCache.put(i % 500, i);
            }
        }
        long customTime = System.nanoTime() - startTime;
        
        // LinkedHashMap implementation
        startTime = System.nanoTime();
        LRUCacheLinkedHashMap builtinCache = new LRUCacheLinkedHashMap(1000);
        for (int i = 0; i < 10000; i++) {
            if (i % 3 == 0) {
                builtinCache.get(i % 500);
            } else {
                builtinCache.put(i % 500, i);
            }
        }
        long builtinTime = System.nanoTime() - startTime;
        
        System.out.println("Custom Implementation: " + customTime + " ns");
        System.out.println("LinkedHashMap Implementation: " + builtinTime + " ns");
        System.out.println("Ratio: " + (double) customTime / builtinTime);
    }
    
    /**
     * Explain the algorithm in detail
     */
    private static void explainAlgorithm() {
        System.out.println("\nHOW LRU CACHE WORKS:");
        System.out.println("1. Data Structures:");
        System.out.println("   - HashMap: Provides O(1) access to cache entries");
        System.out.println("   - Doubly Linked List: Maintains usage order");
        System.out.println("   - Head: Most recently used element");
        System.out.println("   - Tail: Least recently used element");
        
        System.out.println("\n2. Key Operations:");
        System.out.println("   GET(key):");
        System.out.println("     - If key not in cache: return -1");
        System.out.println("     - If key in cache:");
        System.out.println("       * Get node from HashMap");
        System.out.println("       * Move node to head (most recent)");
        System.out.println("       * Return value");
        
        System.out.println("\n   PUT(key, value):");
        System.out.println("     - If key in cache:");
        System.out.println("       * Update value");
        System.out.println("       * Move node to head");
        System.out.println("     - If key not in cache:");
        System.out.println("       * Create new node");
        System.out.println("       * Add to HashMap");
        System.out.println("       * Add to head of linked list");
        System.out.println("       * If capacity exceeded:");
        System.out.println("         - Remove tail node (LRU)");
        System.out.println("         - Remove from HashMap");
        
        System.out.println("\n3. Why This Design?");
        System.out.println("   - HashMap: O(1) access by key");
        System.out.println("   - Doubly Linked List: O(1) insertion/deletion");
        System.out.println("   - Combined: O(1) for all operations");
        
        System.out.println("\n4. Complexity Analysis:");
        System.out.println("   - Time: O(1) for get and put");
        System.out.println("   - Space: O(capacity) for storage");
        
        System.out.println("\n5. Edge Cases Handled:");
        System.out.println("   - Capacity 1: Single element cache");
        System.out.println("   - Update existing key: Maintains position");
        System.out.println("   - Duplicate operations: Proper ordering");
        System.out.println("   - Empty cache: Proper error handling");
        
        System.out.println("\n6. Real-world Applications:");
        System.out.println("   - CPU cache management");
        System.out.println("   - Database query caching");
        System.out.println("   - Web page caching");
        System.out.println("   - Memory management in operating systems");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY FOR LRU CACHE");
        System.out.println("=".repeat(70));
        
        System.out.println("\n1. Key Points to Mention:");
        System.out.println("   - Need O(1) time complexity for both operations");
        System.out.println("   - HashMap for O(1) access, Linked List for O(1) rearrangement");
        System.out.println("   - Explain why singly linked list won't work (need O(1) removal)");
        System.out.println("   - Mention dummy head/tail to simplify edge cases");
        
        System.out.println("\n2. Step-by-step Implementation:");
        System.out.println("   a) Define Node class with key, value, prev, next");
        System.out.println("   b) Initialize HashMap, capacity, dummy head/tail");
        System.out.println("   c) Implement helper methods:");
        System.out.println("      - addNode(), removeNode(), moveToHead(), popTail()");
        System.out.println("   d) Implement get() and put() using helpers");
        
        System.out.println("\n3. Common Mistakes to Avoid:");
        System.out.println("   - Forgetting to update HashMap when removing nodes");
        System.out.println("   - Not handling the case when updating existing key");
        System.out.println("   - Incorrect pointer manipulation in linked list");
        System.out.println("   - Not checking capacity before adding new elements");
        
        System.out.println("\n4. Alternative Approaches:");
        System.out.println("   - LinkedHashMap (built-in Java) - simpler but less educational");
        System.out.println("   - Two Stacks - O(n) time complexity");
        System.out.println("   - Array with timestamps - O(n) for eviction");
        
        System.out.println("\n5. Follow-up Questions:");
        System.out.println("   - How would you implement LFU Cache?");
        System.out.println("   - What about thread-safe implementation?");
        System.out.println("   - How to handle persistence?");
        System.out.println("   - What if we need distributed caching?");
    }
}
