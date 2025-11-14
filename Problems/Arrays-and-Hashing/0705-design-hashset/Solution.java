
## Solution.java

```java
/**
 * 705. Design HashSet
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Design a HashSet without using any built-in hash table libraries.
 * Implement the MyHashSet class with add, contains, and remove operations.
 * 
 * Key Insights:
 * 1. Use separate chaining with linked lists to handle collisions
 * 2. Choose a prime number for bucket size to reduce collisions
 * 3. Simple hash function: key % bucket_size
 * 4. Each bucket contains a linked list of keys
 * 5. Need to handle duplicate keys in add operation
 * 
 * Approach (Separate Chaining):
 * 1. Create an array of linked lists (buckets)
 * 2. Use hash function to determine bucket index
 * 3. For add: traverse linked list, add only if key doesn't exist
 * 4. For contains: traverse linked list, return true if key found
 * 5. For remove: traverse linked list, remove node if key found
 * 
 * Time Complexity: O(n) worst case, O(1) average case
 * Space Complexity: O(n + m) where n is elements, m is buckets
 * 
 * Tags: Array, Hash Table, Linked List, Design, Hash Function
 */

import java.util.*;

class MyHashSet {
    /**
     * Node class for linked list implementation
     */
    private static class Node {
        int key;
        Node next;
        
        Node(int key) {
            this.key = key;
        }
    }
    
    // Constants
    private static final int BUCKET_SIZE = 2069; // Prime number for better distribution
    private Node[] buckets;
    
    /**
     * Initialize your data structure here.
     */
    public MyHashSet() {
        buckets = new Node[BUCKET_SIZE];
    }
    
    /**
     * Hash function: key % bucket_size
     */
    private int hash(int key) {
        return key % BUCKET_SIZE;
    }
    
    /**
     * Add key to the HashSet
     */
    public void add(int key) {
        int index = hash(key);
        Node head = buckets[index];
        
        // Check if key already exists
        Node current = head;
        while (current != null) {
            if (current.key == key) {
                return; // Key already exists, no duplicate needed
            }
            current = current.next;
        }
        
        // Key doesn't exist, add new node at beginning
        Node newNode = new Node(key);
        newNode.next = head;
        buckets[index] = newNode;
    }
    
    /**
     * Returns true if this set contains the specified element
     */
    public boolean contains(int key) {
        int index = hash(key);
        Node current = buckets[index];
        
        while (current != null) {
            if (current.key == key) {
                return true;
            }
            current = current.next;
        }
        
        return false; // Key not found
    }
    
    /**
     * Remove the key from the HashSet if it exists
     */
    public void remove(int key) {
        int index = hash(key);
        Node current = buckets[index];
        Node prev = null;
        
        while (current != null) {
            if (current.key == key) {
                if (prev == null) {
                    // Remove head node
                    buckets[index] = current.next;
                } else {
                    // Remove middle or tail node
                    prev.next = current.next;
                }
                return;
            }
            prev = current;
            current = current.next;
        }
    }
    
    /**
     * Get all keys in the HashSet (for testing purposes)
     */
    public List<Integer> getAllKeys() {
        List<Integer> keys = new ArrayList<>();
        for (Node head : buckets) {
            Node current = head;
            while (current != null) {
                keys.add(current.key);
                current = current.next;
            }
        }
        return keys;
    }
    
    /**
     * Get the size of the HashSet (for testing purposes)
     */
    public int size() {
        int count = 0;
        for (Node head : buckets) {
            Node current = head;
            while (current != null) {
                count++;
                current = current.next;
            }
        }
        return count;
    }
    
    /**
     * Alternative implementation with boolean array (Simplest but limited)
     * Only works when key range is known and manageable
     */
    static class MyHashSetBooleanArray {
        private boolean[] set;
        
        public MyHashSetBooleanArray() {
            set = new boolean[1000001]; // Constraints: 0 <= key <= 10^6
        }
        
        public void add(int key) {
            set[key] = true;
        }
        
        public void remove(int key) {
            set[key] = false;
        }
        
        public boolean contains(int key) {
            return set[key];
        }
    }
    
    /**
     * Alternative implementation with ArrayList for buckets
     */
    static class MyHashSetArrayListVersion {
        private static final int BUCKET_SIZE = 2069;
        private List<Integer>[] buckets;
        
        @SuppressWarnings("unchecked")
        public MyHashSetArrayListVersion() {
            buckets = new ArrayList[BUCKET_SIZE];
            for (int i = 0; i < BUCKET_SIZE; i++) {
                buckets[i] = new ArrayList<>();
            }
        }
        
        private int hash(int key) {
            return key % BUCKET_SIZE;
        }
        
        public void add(int key) {
            int index = hash(key);
            List<Integer> bucket = buckets[index];
            
            if (!bucket.contains(key)) {
                bucket.add(key);
            }
        }
        
        public boolean contains(int key) {
            int index = hash(key);
            return buckets[index].contains(key);
        }
        
        public void remove(int key) {
            int index = hash(key);
            List<Integer> bucket = buckets[index];
            
            // Manual removal to avoid boxing issues
            for (int i = 0; i < bucket.size(); i++) {
                if (bucket.get(i) == key) {
                    bucket.remove(i);
                    return;
                }
            }
        }
    }
    
    /**
     * Implementation with resize capability (Advanced version)
     */
    static class MyHashSetWithResize {
        private static final int INITIAL_CAPACITY = 16;
        private static final double LOAD_FACTOR = 0.75;
        
        private Node[] table;
        private int size;
        
        public MyHashSetWithResize() {
            table = new Node[INITIAL_CAPACITY];
            size = 0;
        }
        
        private int hash(int key) {
            return key % table.length;
        }
        
        public void add(int key) {
            // Check if resize is needed
            if (size >= table.length * LOAD_FACTOR) {
                resize();
            }
            
            int index = hash(key);
            Node head = table[index];
            
            // Check if key exists
            Node current = head;
            while (current != null) {
                if (current.key == key) {
                    return; // Key already exists
                }
                current = current.next;
            }
            
            // Add new node
            Node newNode = new Node(key);
            newNode.next = head;
            table[index] = newNode;
            size++;
        }
        
        public boolean contains(int key) {
            int index = hash(key);
            Node current = table[index];
            
            while (current != null) {
                if (current.key == key) {
                    return true;
                }
                current = current.next;
            }
            
            return false;
        }
        
        public void remove(int key) {
            int index = hash(key);
            Node current = table[index];
            Node prev = null;
            
            while (current != null) {
                if (current.key == key) {
                    if (prev == null) {
                        table[index] = current.next;
                    } else {
                        prev.next = current.next;
                    }
                    size--;
                    return;
                }
                prev = current;
                current = current.next;
            }
        }
        
        private void resize() {
            Node[] oldTable = table;
            table = new Node[oldTable.length * 2];
            size = 0;
            
            // Rehash all elements
            for (Node head : oldTable) {
                Node current = head;
                while (current != null) {
                    add(current.key); // This will rehash with new table size
                    current = current.next;
                }
            }
        }
    }
    
    /**
     * Implementation using BitSet (Memory efficient for integer keys)
     */
    static class MyHashSetBitSet {
        private BitSet bitSet;
        
        public MyHashSetBitSet() {
            bitSet = new BitSet(1000001); // Constraints: 0 <= key <= 10^6
        }
        
        public void add(int key) {
            bitSet.set(key);
        }
        
        public void remove(int key) {
            bitSet.clear(key);
        }
        
        public boolean contains(int key) {
            return bitSet.get(key);
        }
    }
}

/**
 * Test class to verify all implementations
 */
class TestMyHashSet {
    public static void main(String[] args) {
        System.out.println("Testing MyHashSet Implementations");
        System.out.println("=================================");
        
        // Test 1: Basic operations
        System.out.println("\nTest 1: Basic Operations");
        testBasicOperations(new MyHashSet());
        testBasicOperations(new MyHashSet.MyHashSetBooleanArray());
        testBasicOperations(new MyHashSet.MyHashSetArrayListVersion());
        testBasicOperations(new MyHashSet.MyHashSetWithResize());
        testBasicOperations(new MyHashSet.MyHashSetBitSet());
        
        // Test 2: Duplicate handling
        System.out.println("\nTest 2: Duplicate Handling");
        testDuplicateHandling(new MyHashSet());
        
        // Test 3: Collision handling
        System.out.println("\nTest 3: Collision Handling");
        testCollisions(new MyHashSet());
        
        // Test 4: Large dataset
        System.out.println("\nTest 4: Large Dataset Performance");
        testPerformance(new MyHashSet());
        
        // Test 5: Edge cases
        System.out.println("\nTest 5: Edge Cases");
        testEdgeCases(new MyHashSet());
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON AND ANALYSIS");
        System.out.println("=".repeat(70));
        
        printAlgorithmAnalysis();
        
        // Memory usage comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MEMORY USAGE COMPARISON");
        System.out.println("=".repeat(70));
        
        compareMemoryUsage();
    }
    
    private static void testBasicOperations(MyHashSet set) {
        System.out.println("Testing " + set.getClass().getSimpleName());
        
        set.add(1);
        set.add(2);
        
        // Test contains operations
        boolean result1 = set.contains(1);
        boolean result2 = set.contains(2);
        boolean result3 = set.contains(3);
        
        // Test duplicate add
        set.add(2);
        
        // Test remove
        set.remove(2);
        boolean result4 = set.contains(2);
        
        boolean passed = result1 && result2 && !result3 && !result4;
        
        System.out.println("Basic operations: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testBasicOperations(MyHashSet.MyHashSetBooleanArray set) {
        System.out.println("Testing MyHashSetBooleanArray");
        
        set.add(1);
        set.add(2);
        
        boolean result1 = set.contains(1);
        boolean result2 = set.contains(2);
        boolean result3 = set.contains(3);
        
        set.add(2); // Duplicate
        
        set.remove(2);
        boolean result4 = set.contains(2);
        
        boolean passed = result1 && result2 && !result3 && !result4;
        
        System.out.println("Basic operations: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testBasicOperations(MyHashSet.MyHashSetArrayListVersion set) {
        System.out.println("Testing MyHashSetArrayListVersion");
        
        set.add(1);
        set.add(2);
        
        boolean result1 = set.contains(1);
        boolean result2 = set.contains(2);
        boolean result3 = set.contains(3);
        
        set.add(2); // Duplicate
        
        set.remove(2);
        boolean result4 = set.contains(2);
        
        boolean passed = result1 && result2 && !result3 && !result4;
        
        System.out.println("Basic operations: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testBasicOperations(MyHashSet.MyHashSetWithResize set) {
        System.out.println("Testing MyHashSetWithResize");
        
        set.add(1);
        set.add(2);
        
        boolean result1 = set.contains(1);
        boolean result2 = set.contains(2);
        boolean result3 = set.contains(3);
        
        set.add(2); // Duplicate
        
        set.remove(2);
        boolean result4 = set.contains(2);
        
        boolean passed = result1 && result2 && !result3 && !result4;
        
        System.out.println("Basic operations: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testBasicOperations(MyHashSet.MyHashSetBitSet set) {
        System.out.println("Testing MyHashSetBitSet");
        
        set.add(1);
        set.add(2);
        
        boolean result1 = set.contains(1);
        boolean result2 = set.contains(2);
        boolean result3 = set.contains(3);
        
        set.add(2); // Duplicate
        
        set.remove(2);
        boolean result4 = set.contains(2);
        
        boolean passed = result1 && result2 && !result3 && !result4;
        
        System.out.println("Basic operations: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testDuplicateHandling(MyHashSet set) {
        set.add(1);
        set.add(1); // Duplicate
        set.add(1); // Another duplicate
        
        int size = set.size();
        boolean passed = (size == 1) && set.contains(1);
        
        System.out.println("Duplicate handling: " + (passed ? "PASSED" : "FAILED"));
        System.out.println("  Size after adding duplicates: " + size);
    }
    
    private static void testCollisions(MyHashSet set) {
        // These keys will likely collide with bucket size 2069
        int baseKey = 2069;
        boolean passed = true;
        
        // Add multiple keys that might collide
        for (int i = 0; i < 10; i++) {
            set.add(baseKey * i);
        }
        
        // Verify all keys can be found
        for (int i = 0; i < 10; i++) {
            if (!set.contains(baseKey * i)) {
                passed = false;
                break;
            }
        }
        
        System.out.println("Collision handling: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testPerformance(MyHashSet set) {
        int numOperations = 10000;
        long startTime = System.nanoTime();
        
        // Add operations
        for (int i = 0; i < numOperations; i++) {
            set.add(i);
        }
        
        // Contains operations
        for (int i = 0; i < numOperations; i++) {
            set.contains(i);
        }
        
        // Remove operations
        for (int i = 0; i < numOperations; i++) {
            set.remove(i);
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds
        
        System.out.println("Performance test (" + numOperations + " operations): " + 
                          duration + " ms");
    }
    
    private static void testEdgeCases(MyHashSet set) {
        boolean passed = true;
        
        // Test with key 0
        set.add(0);
        if (!set.contains(0)) passed = false;
        set.remove(0);
        if (set.contains(0)) passed = false;
        
        // Test with maximum constraint value
        set.add(1000000);
        if (!set.contains(1000000)) passed = false;
        
        // Test remove non-existent key
        set.remove(999999); // Should not throw exception
        
        // Test contains non-existent key
        if (set.contains(555555)) passed = false;
        
        System.out.println("Edge cases: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void printAlgorithmAnalysis() {
        System.out.println("\n1. SEPARATE CHAINING WITH LINKED LISTS (MAIN IMPLEMENTATION):");
        System.out.println("   Time Complexity:");
        System.out.println("     - Average case: O(1) for add, contains, remove");
        System.out.println("     - Worst case: O(n) when all keys hash to same bucket");
        System.out.println("   Space Complexity: O(n + m) where n=elements, m=buckets");
        System.out.println("   Pros:");
        System.out.println("     - Simple implementation");
        System.out.println("     - Handles collisions gracefully");
        System.out.println("     - No need for resize in basic version");
        System.out.println("   Cons:");
        System.out.println("     - Worst case performance can be poor");
        System.out.println("     - Memory overhead for linked list nodes");
        
        System.out.println("\n2. BOOLEAN ARRAY (SIMPLEST):");
        System.out.println("   Time Complexity: O(1) for all operations");
        System.out.println("   Space Complexity: O(1) but fixed large size");
        System.out.println("   Pros:");
        System.out.println("     - Extremely fast operations");
        System.out.println("     - Very simple implementation");
        System.out.println("   Cons:");
        System.out.println("     - Wastes memory for sparse data");
        System.out.println("     - Only works when key range is known and limited");
        
        System.out.println("\n3. SEPARATE CHAINING WITH ARRAYLISTS:");
        System.out.println("   Time Complexity:");
        System.out.println("     - Average case: O(1) for add, contains");
        System.out.println("     - Worst case: O(k) where k=bucket size");
        System.out.println("   Space Complexity: O(n + m)");
        System.out.println("   Pros:");
        System.out.println("     - Very simple to implement");
        System.out.println("     - Good for small bucket sizes");
        System.out.println("   Cons:");
        System.out.println("     - Poor performance with large bucket sizes");
        System.out.println("     - ArrayList resizing overhead");
        
        System.out.println("\n4. WITH RESIZE CAPABILITY (ADVANCED):");
        System.out.println("   Time Complexity:");
        System.out.println("     - Average case: O(1) amortized");
        System.out.println("     - Resize: O(n) but amortized over many operations");
        System.out.println("   Space Complexity: O(n)");
        System.out.println("   Pros:");
        System.out.println("     - Maintains good load factor");
        System.out.println("     - Better memory utilization");
        System.out.println("   Cons:");
        System.out.println("     - More complex implementation");
        System.out.println("     - Resize operation is expensive");
        
        System.out.println("\n5. BITSET (MEMORY EFFICIENT):");
        System.out.println("   Time Complexity: O(1) for all operations");
        System.out.println("   Space Complexity: O(1) fixed size");
        System.out.println("   Pros:");
        System.out.println("     - Most memory efficient for integer keys");
        System.out.println("     - Very fast operations");
        System.out.println("   Cons:");
        System.out.println("     - Only works for non-negative integer keys");
        System.out.println("     - Fixed size, cannot handle arbitrary key ranges");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CHOOSING THE RIGHT IMPLEMENTATION:");
        System.out.println("1. For interviews: Linked List separate chaining");
        System.out.println("2. For known key range: Boolean array or BitSet");
        System.out.println("3. For production with resize: Advanced version");
        System.out.println("4. For simplicity: ArrayList version");
    }
    
    private static void compareMemoryUsage() {
        int numElements = 1000;
        
        // Estimate memory usage for each implementation
        System.out.println("\nMemory Usage Comparison (Estimated for " + numElements + " elements):");
        
        // Linked List version
        // Each node: 12 bytes (object header) + 4 bytes (key) + 4 bytes (next reference) = 20 bytes
        // Array: 2069 * 4 bytes (references) ≈ 8KB
        long linkedListMemory = numElements * 20L + 2069 * 4L;
        
        // Boolean Array version
        // Fixed: 1000001 bytes ≈ 1MB
        long booleanArrayMemory = 1000001L;
        
        // ArrayList version
        // Each ArrayList: 12 bytes header + fields ≈ 20 bytes per bucket
        // Each Integer: 16 bytes
        long arrayListMemory = 2069 * 20L + numElements * 16L;
        
        // BitSet version
        // Fixed: 1000001/8 bytes ≈ 125KB
        long bitSetMemory = 1000001L / 8;
        
        System.out.println("Linked List Version: " + linkedListMemory + " bytes");
        System.out.println("Boolean Array Version: " + booleanArrayMemory + " bytes");
        System.out.println("ArrayList Version: " + arrayListMemory + " bytes");
        System.out.println("BitSet Version: " + bitSetMemory + " bytes");
        
        System.out.println("\nRecommendations:");
        System.out.println("- For small datasets: All implementations work well");
        System.out.println("- For memory efficiency: BitSet (if keys are integers)");
        System.out.println("- For general purpose: Linked List separate chaining");
        System.out.println("- For known key range: Boolean array");
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * boolean param_2 = obj.contains(key);
 * obj.remove(key);
 */
