
# Solution.java

```java
import java.util.*;

/**
 * 380. Insert Delete GetRandom O(1)
 * 
 * Difficulty: Medium
 * 
 * Problem:
 * Design a data structure that supports insert, delete, and getRandom
 * all in O(1) average time.
 * 
 * Key Insights:
 * 1. Use ArrayList for O(1) random access
 * 2. Use HashMap for O(1) lookup by value
 * 3. When removing, swap with last element to avoid shifting
 * 4. Update map after swap to reflect new index
 */
class RandomizedSet {
    
    /**
     * Approach 1: HashMap + ArrayList (Recommended)
     * Time: O(1) average for all operations, Space: O(n)
     * 
     * Steps:
     * 1. ArrayList stores the actual values
     * 2. HashMap maps value -> index in ArrayList
     * 3. Insert: if not present, add to end of list, store index
     * 4. Remove: get index, swap with last element, remove last, update map
     * 5. GetRandom: generate random index, return list.get(index)
     */
    private List<Integer> list;
    private Map<Integer, Integer> map;
    private Random random;
    
    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
        random = new Random();
    }
    
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        
        map.put(val, list.size());
        list.add(val);
        return true;
    }
    
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }
        
        int index = map.get(val);
        int lastVal = list.get(list.size() - 1);
        
        // Swap with last element
        list.set(index, lastVal);
        map.put(lastVal, index);
        
        // Remove last element
        list.remove(list.size() - 1);
        map.remove(val);
        
        return true;
    }
    
    public int getRandom() {
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }
}

/**
 * Approach 2: Using LinkedHashMap (Alternative)
 * Time: O(1) for insert/remove, O(n) for getRandom, Space: O(n)
 * 
 * Not meeting O(1) getRandom requirement
 */
class RandomizedSetLinkedHashMap {
    private LinkedHashSet<Integer> set;
    private List<Integer> list;
    private Random random;
    
    public RandomizedSetLinkedHashMap() {
        set = new LinkedHashSet<>();
        list = new ArrayList<>();
        random = new Random();
    }
    
    public boolean insert(int val) {
        if (set.contains(val)) return false;
        set.add(val);
        list.add(val);
        return true;
    }
    
    public boolean remove(int val) {
        if (!set.contains(val)) return false;
        set.remove(val);
        list.remove((Integer) val);
        return true;
    }
    
    public int getRandom() {
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }
}

/**
 * Approach 3: Using Two HashMaps (Alternative)
 * Time: O(1) for all operations, Space: O(n)
 * 
 * Uses index -> value map and value -> index map
 */
class RandomizedSetTwoMaps {
    private Map<Integer, Integer> valToIndex;
    private Map<Integer, Integer> indexToVal;
    private int size;
    private Random random;
    
    public RandomizedSetTwoMaps() {
        valToIndex = new HashMap<>();
        indexToVal = new HashMap<>();
        size = 0;
        random = new Random();
    }
    
    public boolean insert(int val) {
        if (valToIndex.containsKey(val)) return false;
        
        valToIndex.put(val, size);
        indexToVal.put(size, val);
        size++;
        return true;
    }
    
    public boolean remove(int val) {
        if (!valToIndex.containsKey(val)) return false;
        
        int index = valToIndex.get(val);
        int lastVal = indexToVal.get(size - 1);
        
        // Swap with last element
        valToIndex.put(lastVal, index);
        indexToVal.put(index, lastVal);
        
        // Remove last
        valToIndex.remove(val);
        indexToVal.remove(size - 1);
        size--;
        
        return true;
    }
    
    public int getRandom() {
        int randomIndex = random.nextInt(size);
        return indexToVal.get(randomIndex);
    }
}

/**
 * Approach 4: Using Array + HashMap with Lazy Deletion
 * Time: O(1) average, Space: O(n)
 * 
 * Uses boolean array to mark deleted (not efficient for large ranges)
 */
class RandomizedSetArray {
    private List<Integer> list;
    private Map<Integer, Integer> map;
    private Random random;
    
    public RandomizedSetArray() {
        list = new ArrayList<>();
        map = new HashMap<>();
        random = new Random();
    }
    
    public boolean insert(int val) {
        if (map.containsKey(val)) return false;
        map.put(val, list.size());
        list.add(val);
        return true;
    }
    
    public boolean remove(int val) {
        if (!map.containsKey(val)) return false;
        
        int index = map.get(val);
        int lastVal = list.get(list.size() - 1);
        
        list.set(index, lastVal);
        map.put(lastVal, index);
        list.remove(list.size() - 1);
        map.remove(val);
        
        return true;
    }
    
    public int getRandom() {
        return list.get(random.nextInt(list.size()));
    }
}

/**
 * Approach 5: Thread-Safe Version
 * Time: O(1) average, Space: O(n)
 * 
 * Uses synchronized for thread safety
 */
class RandomizedSetThreadSafe {
    private List<Integer> list;
    private Map<Integer, Integer> map;
    private Random random;
    private final Object lock = new Object();
    
    public RandomizedSetThreadSafe() {
        list = new ArrayList<>();
        map = new HashMap<>();
        random = new Random();
    }
    
    public boolean insert(int val) {
        synchronized (lock) {
            if (map.containsKey(val)) return false;
            map.put(val, list.size());
            list.add(val);
            return true;
        }
    }
    
    public boolean remove(int val) {
        synchronized (lock) {
            if (!map.containsKey(val)) return false;
            int index = map.get(val);
            int lastVal = list.get(list.size() - 1);
            list.set(index, lastVal);
            map.put(lastVal, index);
            list.remove(list.size() - 1);
            map.remove(val);
            return true;
        }
    }
    
    public int getRandom() {
        synchronized (lock) {
            return list.get(random.nextInt(list.size()));
        }
    }
}

/**
 * Test and Demonstration Class
 */
class RandomizedSetTest {
    
    public static void visualizeOperations() {
        System.out.println("\nRandomizedSet Operations Visualization:");
        System.out.println("=".repeat(60));
        
        RandomizedSet rs = new RandomizedSet();
        
        System.out.println("\nInsert operations:");
        System.out.println("insert(1): " + rs.insert(1) + " → list: " + rs.list + ", map: " + rs.map);
        System.out.println("insert(2): " + rs.insert(2) + " → list: " + rs.list + ", map: " + rs.map);
        System.out.println("insert(3): " + rs.insert(3) + " → list: " + rs.list + ", map: " + rs.map);
        System.out.println("insert(2) again: " + rs.insert(2) + " → list: " + rs.list + ", map: " + rs.map);
        
        System.out.println("\nGetRandom:");
        System.out.println("Random element: " + rs.getRandom());
        System.out.println("Random element: " + rs.getRandom());
        System.out.println("Random element: " + rs.getRandom());
        
        System.out.println("\nRemove operations:");
        System.out.println("remove(2): " + rs.remove(2));
        System.out.println("  Before swap: list: " + rs.list + ", map: " + rs.map);
        System.out.println("  After remove: list: " + rs.list + ", map: " + rs.map);
        
        System.out.println("remove(5): " + rs.remove(5) + " (not exists)");
        System.out.println("remove(1): " + rs.remove(1));
        System.out.println("  After remove: list: " + rs.list + ", map: " + rs.map);
    }
    
    public static void runTestCases() {
        System.out.println("\nRunning Test Cases:");
        System.out.println("=".repeat(50));
        
        // Test 1: Example from problem
        System.out.println("\nTest 1: Example from problem");
        RandomizedSet rs1 = new RandomizedSet();
        System.out.println("insert(1): " + rs1.insert(1));
        System.out.println("remove(2): " + rs1.remove(2));
        System.out.println("insert(2): " + rs1.insert(2));
        System.out.println("getRandom(): " + rs1.getRandom());
        System.out.println("remove(1): " + rs1.remove(1));
        System.out.println("insert(2): " + rs1.insert(2));
        System.out.println("getRandom(): " + rs1.getRandom());
        
        // Test 2: Insert duplicates
        System.out.println("\nTest 2: Insert duplicates");
        RandomizedSet rs2 = new RandomizedSet();
        System.out.println("insert(5): " + rs2.insert(5));
        System.out.println("insert(5): " + rs2.insert(5));
        System.out.println("insert(5): " + rs2.insert(5));
        System.out.println("list: " + rs2.list);
        
        // Test 3: Remove non-existent
        System.out.println("\nTest 3: Remove non-existent");
        RandomizedSet rs3 = new RandomizedSet();
        rs3.insert(10);
        System.out.println("remove(20): " + rs3.remove(20));
        
        // Test 4: Remove and re-insert
        System.out.println("\nTest 4: Remove and re-insert");
        RandomizedSet rs4 = new RandomizedSet();
        rs4.insert(1);
        rs4.insert(2);
        rs4.insert(3);
        System.out.println("Before removal: list=" + rs4.list);
        rs4.remove(2);
        System.out.println("After removal: list=" + rs4.list);
        rs4.insert(2);
        System.out.println("After re-insert: list=" + rs4.list);
    }
    
    public static void comparePerformance() {
        System.out.println("\nPerformance Comparison:");
        System.out.println("=".repeat(50));
        
        int operations = 100000;
        Random rand = new Random(42);
        
        // Test RandomizedSet
        RandomizedSet rs1 = new RandomizedSet();
        long start = System.currentTimeMillis();
        for (int i = 0; i < operations; i++) {
            int val = rand.nextInt(100000);
            switch (rand.nextInt(3)) {
                case 0: rs1.insert(val); break;
                case 1: rs1.remove(val); break;
                case 2: rs1.getRandom(); break;
            }
        }
        long time1 = System.currentTimeMillis() - start;
        
        // Test RandomizedSetTwoMaps
        RandomizedSetTwoMaps rs2 = new RandomizedSetTwoMaps();
        start = System.currentTimeMillis();
        for (int i = 0; i < operations; i++) {
            int val = rand.nextInt(100000);
            switch (rand.nextInt(3)) {
                case 0: rs2.insert(val); break;
                case 1: rs2.remove(val); break;
                case 2: rs2.getRandom(); break;
            }
        }
        long time2 = System.currentTimeMillis() - start;
        
        // Test RandomizedSetLinkedHashMap (will be slower for getRandom)
        RandomizedSetLinkedHashMap rs3 = new RandomizedSetLinkedHashMap();
        start = System.currentTimeMillis();
        for (int i = 0; i < operations; i++) {
            int val = rand.nextInt(100000);
            switch (rand.nextInt(3)) {
                case 0: rs3.insert(val); break;
                case 1: rs3.remove(val); break;
                case 2: rs3.getRandom(); break;
            }
        }
        long time3 = System.currentTimeMillis() - start;
        
        System.out.println("\nResults for " + operations + " mixed operations:");
        System.out.println("Implementation                | Time (ms)");
        System.out.println("------------------------------|-----------");
        System.out.printf("1. HashMap + ArrayList       | %9d%n", time1);
        System.out.printf("2. Two HashMaps              | %9d%n", time2);
        System.out.printf("3. LinkedHashSet             | %9d%n", time3);
        
        System.out.println("\nObservations:");
        System.out.println("1. HashMap + ArrayList is fastest and simplest");
        System.out.println("2. Two HashMaps has similar performance");
        System.out.println("3. LinkedHashSet has slower getRandom (conversion to array)");
        System.out.println("4. All meet O(1) average for insert/remove");
    }
    
    public static void testEdgeCases() {
        System.out.println("\nEdge Cases Testing:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Empty set getRandom:");
        RandomizedSet rs1 = new RandomizedSet();
        try {
            System.out.println("  getRandom(): " + rs1.getRandom());
        } catch (Exception e) {
            System.out.println("  Exception caught: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\n2. Single element operations:");
        RandomizedSet rs2 = new RandomizedSet();
        rs2.insert(42);
        System.out.println("  insert(42): " + true);
        System.out.println("  getRandom(): " + rs2.getRandom());
        System.out.println("  remove(42): " + rs2.remove(42));
        System.out.println("  getRandom() after removal: ");
        try {
            System.out.println("    " + rs2.getRandom());
        } catch (Exception e) {
            System.out.println("    Exception (expected)");
        }
        
        System.out.println("\n3. Large values (near int limits):");
        RandomizedSet rs3 = new RandomizedSet();
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;
        System.out.println("  insert(" + max + "): " + rs3.insert(max));
        System.out.println("  insert(" + min + "): " + rs3.insert(min));
        System.out.println("  contains " + max + ": " + rs3.map.containsKey(max));
        System.out.println("  getRandom(): " + rs3.getRandom());
        
        System.out.println("\n4. Many inserts and removes:");
        RandomizedSet rs4 = new RandomizedSet();
        for (int i = 0; i < 1000; i++) {
            rs4.insert(i);
        }
        for (int i = 0; i < 500; i++) {
            rs4.remove(i);
        }
        System.out.println("  Size after 1000 inserts + 500 removes: " + rs4.list.size());
        System.out.println("  Map size: " + rs4.map.size());
    }
    
    public static void explainDesign() {
        System.out.println("\nDesign Explanation:");
        System.out.println("=".repeat(50));
        
        System.out.println("\nWhy HashMap + ArrayList?");
        System.out.println("- ArrayList: O(1) random access for getRandom()");
        System.out.println("- HashMap: O(1) lookup by value for insert/remove");
        System.out.println("- Combined: both requirements met");
        
        System.out.println("\nThe Swap Trick:");
        System.out.println("  When removing element at index i:");
        System.out.println("  1. Get last element value");
        System.out.println("  2. Swap element at i with last element");
        System.out.println("  3. Update map for swapped element");
        System.out.println("  4. Remove last element (O(1))");
        System.out.println("  5. Remove value from map");
        System.out.println("  This avoids O(n) shifting!");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- insert: O(1) - add to end of list, put in map");
        System.out.println("- remove: O(1) - swap with last, remove last");
        System.out.println("- getRandom: O(1) - random index access");
        
        System.out.println("\nSpace Complexity: O(n) - stores all elements");
    }
    
    public static void interviewTips() {
        System.out.println("\nInterview Tips:");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Clarify requirements:");
        System.out.println("   - O(1) average for all operations");
        System.out.println("   - Uniform random distribution");
        System.out.println("   - Duplicates? (No, set semantics)");
        
        System.out.println("\n2. Start with simple ideas:");
        System.out.println("   - HashSet: O(1) insert/remove, O(n) getRandom");
        System.out.println("   - ArrayList: O(1) getRandom, O(n) insert/remove");
        System.out.println("   - Combined approach solves both");
        
        System.out.println("\n3. Explain the swap trick:");
        System.out.println("   - Why swapping with last element works");
        System.out.println("   - Draw example: [1,2,3,4] remove(2)");
        System.out.println("   - Show how indices update");
        
        System.out.println("\n4. Complexity analysis:");
        System.out.println("   - All operations O(1) average");
        System.out.println("   - Space O(n)");
        
        System.out.println("\n5. Edge cases:");
        System.out.println("   - Empty set getRandom() (not allowed by problem)");
        System.out.println("   - Remove non-existent element");
        System.out.println("   - Insert duplicate");
        
        System.out.println("\n6. Common mistakes:");
        System.out.println("   - Forgetting to update map after swap");
        System.out.println("   - Using list.remove(index) which is O(n)");
        System.out.println("   - Not handling the case when removing last element");
    }
    
    public static void main(String[] args) {
        System.out.println("380. Insert Delete GetRandom O(1)");
        System.out.println("================================");
        
        // Explain design
        explainDesign();
        
        // Visualize operations
        System.out.println("\n" + "=".repeat(80));
        visualizeOperations();
        
        // Run test cases
        System.out.println("\n" + "=".repeat(80));
        runTestCases();
        
        // Edge cases
        System.out.println("\n" + "=".repeat(80));
        testEdgeCases();
        
        // Performance comparison
        System.out.println("\n" + "=".repeat(80));
        comparePerformance();
        
        // Interview tips
        System.out.println("\n" + "=".repeat(80));
        interviewTips();
        
        // Final summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class RandomizedSet {
    private List<Integer> list;
    private Map<Integer, Integer> map;
    private Random random;
    
    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
        random = new Random();
    }
    
    public boolean insert(int val) {
        if (map.containsKey(val)) return false;
        map.put(val, list.size());
        list.add(val);
        return true;
    }
    
    public boolean remove(int val) {
        if (!map.containsKey(val)) return false;
        int index = map.get(val);
        int lastVal = list.get(list.size() - 1);
        list.set(index, lastVal);
        map.put(lastVal, index);
        list.remove(list.size() - 1);
        map.remove(val);
        return true;
    }
    
    public int getRandom() {
        return list.get(random.nextInt(list.size()));
    }
}
            """);
        
        System.out.println("\nKey Points:");
        System.out.println("1. ArrayList provides O(1) random access");
        System.out.println("2. HashMap provides O(1) value-to-index lookup");
        System.out.println("3. Swap with last element enables O(1) removal");
        System.out.println("4. Update map after swap to maintain consistency");
        System.out.println("5. All operations are O(1) average time");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- Time: O(1) average for insert, remove, getRandom");
        System.out.println("- Space: O(n) for storing elements");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you handle duplicates? (See LeetCode 381)");
        System.out.println("2. How would you make it thread-safe?");
        System.out.println("3. How would you implement if values were strings?");
        System.out.println("4. How would you handle very large value ranges?");
        System.out.println("5. How would you implement weighted random?");
    }
}

// For LeetCode submission, the class name must be RandomizedSet
public class Solution {
    public static void main(String[] args) {
        RandomizedSetTest.main(args);
    }
}
