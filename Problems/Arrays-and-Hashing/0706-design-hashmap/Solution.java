
## Solution.java

```java
/**
 * 706. Design HashMap
 * 
 * Difficulty: Easy
 * 
 * Problem:
 * Design a HashMap without using any built-in hash table libraries.
 * Implement the MyHashMap class with put, get, and remove operations.
 * 
 * Key Insights:
 * 1. Use separate chaining with linked lists to handle collisions
 * 2. Choose a prime number for bucket size to reduce collisions
 * 3. Simple hash function: key % bucket_size
 * 4. Each bucket contains a linked list of key-value pairs
 * 5. Need to handle key updates and removals properly
 * 
 * Approach (Separate Chaining):
 * 1. Create an array of linked lists (buckets)
 * 2. Use hash function to determine bucket index
 * 3. For put: traverse linked list, update if key exists, else add new node
 * 4. For get: traverse linked list, return value if key found, else -1
 * 5. For remove: traverse linked list, remove node if key found
 * 
 * Time Complexity: O(n) worst case, O(1) average case
 * Space Complexity: O(n + m) where n is elements, m is buckets
 * 
 * Tags: Array, Hash Table, Linked List, Design, Hash Function
 */

import java.util.*;

class MyHashMap {
    /**
     * Node class for linked list implementation
     */
    private static class Node {
        int key;
        int value;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    // Constants
    private static final int BUCKET_SIZE = 2069; // Prime number for better distribution
    private Node[] buckets;
    
    /**
     * Initialize your data structure here.
     */
    public MyHashMap() {
        buckets = new Node[BUCKET_SIZE];
    }
    
    /**
     * Hash function: key % bucket_size
     */
    private int hash(int key) {
        return key % BUCKET_SIZE;
    }
    
    /**
     * value will always be non-negative.
     */
    public void put(int key, int value) {
        int index = hash(key);
        Node head = buckets[index];
        
        // Check if key already exists
        Node current = head;
        while (current != null) {
            if (current.key == key) {
                current.value = value; // Update existing key
                return;
            }
            current = current.next;
        }
        
        // Key doesn't exist, add new node at beginning
        Node newNode = new Node(key, value);
        newNode.next = head;
        buckets[index] = newNode;
    }
    
    /**
     * Returns the value to which the specified key is mapped, 
     * or -1 if this map contains no mapping for the key.
     */
    public int get(int key) {
        int index = hash(key);
        Node current = buckets[index];
        
        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }
        
        return -1; // Key not found
    }
    
    /**
     * Removes the mapping of the specified value key 
     * if this map contains a mapping for the key.
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
     * Alternative implementation with ArrayList for buckets (Simpler but less efficient)
     */
    static class MyHashMapArrayListVersion {
        private static final int BUCKET_SIZE = 2069;
        private List<int[]>[] buckets; // Each bucket contains list of [key, value] pairs
        
        public MyHashMapArrayListVersion() {
            buckets = new ArrayList[BUCKET_SIZE];
            for (int i = 0; i < BUCKET_SIZE; i++) {
                buckets[i] = new ArrayList<>();
            }
        }
        
        private int hash(int key) {
            return key % BUCKET_SIZE;
        }
        
        public void put(int key, int value) {
            int index = hash(key);
            List<int[]> bucket = buckets[index];
            
            // Check if key exists
            for (int[] pair : bucket) {
                if (pair[0] == key) {
                    pair[1] = value; // Update value
                    return;
                }
            }
            
            // Key doesn't exist, add new pair
            bucket.add(new int[]{key, value});
        }
        
        public int get(int key) {
            int index = hash(key);
            List<int[]> bucket = buckets[index];
            
            for (int[] pair : bucket) {
                if (pair[0] == key) {
                    return pair[1];
                }
            }
            
            return -1;
        }
        
        public void remove(int key) {
            int index = hash(key);
            List<int[]> bucket = buckets[index];
            
            for (int i = 0; i < bucket.size(); i++) {
                if (bucket.get(i)[0] == key) {
                    bucket.remove(i);
                    return;
                }
            }
        }
    }
    
    /**
     * Implementation with resize capability (Advanced version)
     */
    static class MyHashMapWithResize {
        private static final int INITIAL_CAPACITY = 16;
        private static final double LOAD_FACTOR = 0.75;
        
        private Node[] table;
        private int size;
        
        public MyHashMapWithResize() {
            table = new Node[INITIAL_CAPACITY];
            size = 0;
        }
        
        private int hash(int key) {
            return key % table.length;
        }
        
        public void put(int key, int value) {
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
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            
            // Add new node
            Node newNode = new Node(key, value);
            newNode.next = head;
            table[index] = newNode;
            size++;
        }
        
        public int get(int key) {
            int index = hash(key);
            Node current = table[index];
            
            while (current != null) {
                if (current.key == key) {
                    return current.value;
                }
                current = current.next;
            }
            
            return -1;
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
                    put(current.key, current.value);
                    current = current.next;
                }
            }
        }
    }
    
    /**
     * Implementation using TreeMap for buckets (Balanced BST approach)
     */
    static class MyHashMapTreeVersion {
        private static final int BUCKET_SIZE = 2069;
        private TreeMap<Integer, Integer>[] buckets;
        
        @SuppressWarnings("unchecked")
        public MyHashMapTreeVersion() {
            buckets = new TreeMap[BUCKET_SIZE];
            for (int i = 0; i < BUCKET_SIZE; i++) {
                buckets[i] = new TreeMap<>();
            }
        }
        
        private int hash(int key) {
            return key % BUCKET_SIZE;
        }
        
        public void put(int key, int value) {
            int index = hash(key);
            buckets[index].put(key, value);
        }
        
        public int get(int key) {
            int index = hash(key);
            return buckets[index].getOrDefault(key, -1);
        }
        
        public void remove(int key) {
            int index = hash(key);
            buckets[index].remove(key);
        }
    }
}

/**
 * Test class to verify all implementations
 */
class TestMyHashMap {
    public static void main(String[] args) {
        System.out.println("Testing MyHashMap Implementations");
        System.out.println("=================================");
        
        // Test 1: Basic operations
        System.out.println("\nTest 1: Basic Operations");
        testBasicOperations(new MyHashMap());
        testBasicOperations(new MyHashMap.MyHashMapArrayListVersion());
        testBasicOperations(new MyHashMap.MyHashMapWithResize());
        testBasicOperations(new MyHashMap.MyHashMapTreeVersion());
        
        // Test 2: Collision handling
        System.out.println("\nTest 2: Collision Handling");
        testCollisions(new MyHashMap());
        
        // Test 3: Large dataset
        System.out.println("\nTest 3: Large Dataset Performance");
        testPerformance(new MyHashMap());
        
        // Test 4: Edge cases
        System.out.println("\nTest 4: Edge Cases");
        testEdgeCases(new MyHashMap());
        
        // Algorithm comparison
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON AND ANALYSIS");
        System.out.println("=".repeat(70));
        
        printAlgorithmAnalysis();
    }
    
    private static void testBasicOperations(MyHashMap map) {
        System.out.println("Testing " + map.getClass().getSimpleName());
        
        map.put(1, 1);
        map.put(2, 2);
        
        // Test get operations
        int result1 = map.get(1);
        int result2 = map.get(2);
        int result3 = map.get(3);
        
        // Test update
        map.put(2, 1);
        int result4 = map.get(2);
        
        // Test remove
        map.remove(2);
        int result5 = map.get(2);
        
        boolean passed = (result1 == 1) && (result2 == 2) && 
                        (result3 == -1) && (result4 == 1) && 
                        (result5 == -1);
        
        System.out.println("Basic operations: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testBasicOperations(MyHashMap.MyHashMapArrayListVersion map) {
        System.out.println("Testing MyHashMapArrayListVersion");
        
        map.put(1, 1);
        map.put(2, 2);
        
        int result1 = map.get(1);
        int result2 = map.get(2);
        int result3 = map.get(3);
        
        map.put(2, 1);
        int result4 = map.get(2);
        
        map.remove(2);
        int result5 = map.get(2);
        
        boolean passed = (result1 == 1) && (result2 == 2) && 
                        (result3 == -1) && (result4 == 1) && 
                        (result5 == -1);
        
        System.out.println("Basic operations: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testBasicOperations(MyHashMap.MyHashMapWithResize map) {
        System.out.println("Testing MyHashMapWithResize");
        
        map.put(1, 1);
        map.put(2, 2);
        
        int result1 = map.get(1);
        int result2 = map.get(2);
        int result3 = map.get(3);
        
        map.put(2, 1);
        int result4 = map.get(2);
        
        map.remove(2);
        int result5 = map.get(2);
        
        boolean passed = (result1 == 1) && (result2 == 2) && 
                        (result3 == -1) && (result4 == 1) && 
                        (result5 == -1);
        
        System.out.println("Basic operations: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testBasicOperations(MyHashMap.MyHashMapTreeVersion map) {
        System.out.println("Testing MyHashMapTreeVersion");
        
        map.put(1, 1);
        map.put(2, 2);
        
        int result1 = map.get(1);
        int result2 = map.get(2);
        int result3 = map.get(3);
        
        map.put(2, 1);
        int result4 = map.get(2);
        
        map.remove(2);
        int result5 = map.get(2);
        
        boolean passed = (result1 == 1) && (result2 == 2) && 
                        (result3 == -1) && (result4 == 1) && 
                        (result5 == -1);
        
        System.out.println("Basic operations: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testCollisions(MyHashMap map) {
        // These keys will likely collide with bucket size 2069
        int baseKey = 2069;
        boolean passed = true;
        
        // Add multiple keys that might collide
        for (int i = 0; i < 10; i++) {
            map.put(baseKey * i, i * 10);
        }
        
        // Verify all values can be retrieved correctly
        for (int i = 0; i < 10; i++) {
            int value = map.get(baseKey * i);
            if (value != i * 10) {
                passed = false;
                break;
            }
        }
        
        System.out.println("Collision handling: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void testPerformance(MyHashMap map) {
        int numOperations = 10000;
        long startTime = System.nanoTime();
        
        // Put operations
        for (int i = 0; i < numOperations; i++) {
            map.put(i, i * 2);
        }
        
        // Get operations
        for (int i = 0; i < numOperations; i++) {
            map.get(i);
        }
        
        // Remove operations
        for (int i = 0; i < numOperations; i++) {
            map.remove(i);
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds
        
        System.out.println("Performance test (" + numOperations + " operations): " + 
                          duration + " ms");
    }
    
    private static void testEdgeCases(MyHashMap map) {
        boolean passed = true;
        
        // Test with key 0
        map.put(0, 100);
        if (map.get(0) != 100) passed = false;
        
        // Test with maximum constraint value
        map.put(1000000, 999);
        if (map.get(1000000) != 999) passed = false;
        
        // Test remove non-existent key
        map.remove(999999);
        if (map.get(999999) != -1) passed = false;
        
        // Test update existing key
        map.put(0, 200);
        if (map.get(0) != 200) passed = false;
        
        System.out.println("Edge cases: " + (passed ? "PASSED" : "FAILED"));
    }
    
    private static void printAlgorithmAnalysis() {
        System.out.println("\n1. SEPARATE CHAINING WITH LINKED LISTS (MAIN IMPLEMENTATION):");
        System.out.println("   Time Complexity:");
        System.out.println("     - Average case: O(1) for put, get, remove");
        System.out.println("     - Worst case: O(n) when all keys hash to same bucket");
        System.out.println("   Space Complexity: O(n + m) where n=elements, m=buckets");
        System.out.println("   Pros:");
        System.out.println("     - Simple implementation");
        System.out.println("     - Handles collisions gracefully");
        System.out.println("     - No need for resize in basic version");
        System.out.println("   Cons:");
        System.out.println("     - Worst case performance can be poor");
        System.out.println("     - Memory overhead for linked list nodes");
        
        System.out.println("\n2. SEPARATE CHAINING WITH ARRAYLISTS:");
        System.out.println("   Time Complexity:");
        System.out.println("     - Average case: O(1) for put, get");
        System.out.println("     - Worst case: O(k) where k=bucket size");
        System.out.println("   Space Complexity: O(n + m)");
        System.out.println("   Pros:");
        System.out.println("     - Very simple to implement");
        System.out.println("     - Good for small bucket sizes");
        System.out.println("   Cons:");
        System.out.println("     - Poor performance with large bucket sizes");
        System.out.println("     - ArrayList resizing overhead");
        
        System.out.println("\n3. WITH RESIZE CAPABILITY (ADVANCED):");
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
        
        System.out.println("\n4. WITH TREEMAP BUCKETS (BALANCED BST):");
        System.out.println("   Time Complexity:");
        System.out.println("     - Average case: O(log k) where k=bucket size");
        System.out.println("     - Worst case: O(log k)");
        System.out.println("   Space Complexity: O(n + m)");
        System.out.println("   Pros:");
        System.out.println("     - Guaranteed O(log n) performance");
        System.out.println("     - Keys in each bucket are sorted");
        System.out.println("   Cons:");
        System.out.println("     - More memory overhead");
        System.out.println("     - Slower than linked lists for small buckets");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("HASH FUNCTION ANALYSIS:");
        System.out.println("1. Simple modulo: key % bucket_size");
        System.out.println("2. Bucket size should be prime to reduce collisions");
        System.out.println("3. 2069 is prime and provides good distribution");
        System.out.println("4. For resize version, usually double the size");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COLLISION RESOLUTION STRATEGIES:");
        System.out.println("1. Separate Chaining: Store collisions in data structure");
        System.out.println("   - Linked lists: Simple, good for small collisions");
        System.out.println("   - Balanced BST: Better worst-case, more memory");
        System.out.println("   - Arrays: Simple but poor for large collisions");
        System.out.println("2. Open Addressing: Find next available slot");
        System.out.println("   - Linear probing: Check next slot");
        System.out.println("   - Quadratic probing: Check i^2 slots away");
        System.out.println("   - Double hashing: Use second hash function");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTERVIEW STRATEGY:");
        System.out.println("1. Start with separate chaining (linked lists)");
        System.out.println("2. Explain hash function and collision handling");
        System.out.println("3. Discuss time/space complexity trade-offs");
        System.out.println("4. Mention alternatives and their pros/cons");
        System.out.println("5. Consider discussing resize strategy if time permits");
        System.out.println("=".repeat(70));
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
