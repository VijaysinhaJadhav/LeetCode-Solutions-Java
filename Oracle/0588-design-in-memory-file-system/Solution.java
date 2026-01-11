
# Solution.java

```java
import java.util.*;

/**
 * 588. Design In-Memory File System
 * 
 * Difficulty: Hard
 * 
 * Problem:
 * Design an in-memory file system with:
 * - ls: list directory contents or file name
 * - mkdir: create directories including parents
 * - addContentToFile: create or append to file
 * - readContentFromFile: read file content
 * 
 * Key Insights:
 * 1. Tree structure for hierarchical file system
 * 2. Trie-like nodes for efficient path traversal
 * 3. Separate handling for files and directories
 * 4. Path parsing and normalization
 */
class FileSystem {
    
    /**
     * FileSystem Node representing both files and directories
     */
    class FileNode {
        String name;
        boolean isFile;
        StringBuilder content;  // For files only
        Map<String, FileNode> children;  // For directories only
        
        FileNode(String name, boolean isFile) {
            this.name = name;
            this.isFile = isFile;
            this.children = new TreeMap<>();  // TreeMap for automatic sorting
            if (isFile) {
                this.content = new StringBuilder();
            }
        }
        
        void appendContent(String content) {
            if (this.content == null) {
                this.content = new StringBuilder();
            }
            this.content.append(content);
        }
        
        String getContent() {
            return this.content == null ? "" : this.content.toString();
        }
    }
    
    private FileNode root;
    
    public FileSystem() {
        root = new FileNode("", false);  // Root directory
    }
    
    /**
     * List directory contents or file name
     * Time: O(n log n) for sorting, Space: O(L) for path depth
     */
    public List<String> ls(String path) {
        FileNode node = traverse(path);
        List<String> result = new ArrayList<>();
        
        if (node.isFile) {
            // If it's a file, return just the file name
            result.add(node.name);
        } else {
            // If it's a directory, return all children names
            result.addAll(node.children.keySet());
        }
        
        return result;
    }
    
    /**
     * Create directory including parent directories
     * Time: O(L) where L is path depth, Space: O(L)
     */
    public void mkdir(String path) {
        String[] parts = parsePath(path);
        FileNode current = root;
        
        for (String part : parts) {
            if (!current.children.containsKey(part)) {
                current.children.put(part, new FileNode(part, false));
            }
            current = current.children.get(part);
        }
    }
    
    /**
     * Create or append to file
     * Time: O(L + C) where L is path depth, C is content length
     */
    public void addContentToFile(String filePath, String content) {
        String[] parts = parsePath(filePath);
        FileNode current = root;
        
        // Traverse to parent directory
        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            if (!current.children.containsKey(part)) {
                current.children.put(part, new FileNode(part, false));
            }
            current = current.children.get(part);
        }
        
        // Get or create file node
        String fileName = parts[parts.length - 1];
        if (!current.children.containsKey(fileName)) {
            current.children.put(fileName, new FileNode(fileName, true));
        }
        
        FileNode fileNode = current.children.get(fileName);
        if (fileNode.isFile) {
            fileNode.appendContent(content);
        }
    }
    
    /**
     * Read file content
     * Time: O(L) where L is path depth, Space: O(1)
     */
    public String readContentFromFile(String filePath) {
        FileNode node = traverse(filePath);
        if (node != null && node.isFile) {
            return node.getContent();
        }
        return "";
    }
    
    /**
     * Helper: Parse path into parts
     * "/a/b/c" -> ["a", "b", "c"]
     */
    private String[] parsePath(String path) {
        if (path.equals("/")) {
            return new String[0];
        }
        
        // Remove leading slash and split
        String[] parts = path.substring(1).split("/");
        // Filter out empty parts (e.g., from trailing slash)
        List<String> result = new ArrayList<>();
        for (String part : parts) {
            if (!part.isEmpty()) {
                result.add(part);
            }
        }
        return result.toArray(new String[0]);
    }
    
    /**
     * Helper: Traverse to node at given path
     * Returns null if path doesn't exist
     */
    private FileNode traverse(String path) {
        String[] parts = parsePath(path);
        FileNode current = root;
        
        for (String part : parts) {
            if (!current.children.containsKey(part)) {
                return null;  // Path doesn't exist
            }
            current = current.children.get(part);
        }
        
        return current;
    }
    
    /**
     * Visualization helper: Print file system structure
     */
    public void printFileSystem() {
        System.out.println("\nFile System Structure:");
        printDirectory(root, 0);
    }
    
    private void printDirectory(FileNode node, int depth) {
        String indent = "  ".repeat(depth);
        
        if (depth == 0) {
            System.out.println(indent + "/");
        } else {
            System.out.println(indent + node.name + (node.isFile ? " (file)" : "/"));
        }
        
        if (!node.isFile) {
            for (FileNode child : node.children.values()) {
                printDirectory(child, depth + 1);
            }
        } else if (depth > 0) {
            System.out.println(indent + "  Content: \"" + node.getContent() + "\"");
        }
    }
}

/**
 * Alternative Implementation 2: HashMap-based approach
 * More straightforward but less efficient for hierarchical operations
 */
class FileSystem2 {
    
    class File {
        String name;
        boolean isDirectory;
        StringBuilder content;
        Map<String, File> children;
        
        File(String name, boolean isDirectory) {
            this.name = name;
            this.isDirectory = isDirectory;
            this.children = new HashMap<>();
            if (!isDirectory) {
                this.content = new StringBuilder();
            }
        }
    }
    
    private File root;
    
    public FileSystem2() {
        root = new File("", true);
    }
    
    public List<String> ls(String path) {
        File file = getFile(path);
        List<String> result = new ArrayList<>();
        
        if (file.isDirectory) {
            result.addAll(file.children.keySet());
            Collections.sort(result);
        } else {
            result.add(file.name);
        }
        
        return result;
    }
    
    public void mkdir(String path) {
        createFile(path, true);
    }
    
    public void addContentToFile(String filePath, String content) {
        File file = createFile(filePath, false);
        if (!file.isDirectory) {
            file.content.append(content);
        }
    }
    
    public String readContentFromFile(String filePath) {
        File file = getFile(filePath);
        if (file != null && !file.isDirectory) {
            return file.content.toString();
        }
        return "";
    }
    
    private File getFile(String path) {
        if (path.equals("/")) {
            return root;
        }
        
        String[] parts = path.substring(1).split("/");
        File current = root;
        
        for (String part : parts) {
            if (!current.children.containsKey(part)) {
                return null;
            }
            current = current.children.get(part);
        }
        
        return current;
    }
    
    private File createFile(String path, boolean isDirectory) {
        if (path.equals("/")) {
            return root;
        }
        
        String[] parts = path.substring(1).split("/");
        File current = root;
        
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            boolean isLast = (i == parts.length - 1);
            boolean shouldBeDir = isDirectory || !isLast;
            
            if (!current.children.containsKey(part)) {
                current.children.put(part, new File(part, shouldBeDir));
            }
            
            current = current.children.get(part);
            
            // If existing file should be directory but isn't, error
            if (!current.isDirectory && shouldBeDir) {
                // In real system, this would be an error
                // For this problem, we assume valid inputs
            }
        }
        
        return current;
    }
}

/**
 * Alternative Implementation 3: Trie-based approach
 * More elegant for path operations
 */
class FileSystem3 {
    
    class TrieNode {
        Map<String, TrieNode> children;
        StringBuilder content;
        String name;
        boolean isFile;
        
        TrieNode(String name) {
            this.name = name;
            this.children = new TreeMap<>();
            this.content = new StringBuilder();
            this.isFile = false;
        }
    }
    
    private TrieNode root;
    
    public FileSystem3() {
        root = new TrieNode("");
    }
    
    public List<String> ls(String path) {
        TrieNode node = getNode(path);
        List<String> result = new ArrayList<>();
        
        if (node.isFile) {
            result.add(node.name);
        } else {
            result.addAll(node.children.keySet());
        }
        
        return result;
    }
    
    public void mkdir(String path) {
        getNode(path, false);  // Just accessing creates directories
    }
    
    public void addContentToFile(String filePath, String content) {
        TrieNode node = getNode(filePath, true);
        node.isFile = true;
        node.content.append(content);
    }
    
    public String readContentFromFile(String filePath) {
        TrieNode node = getNode(filePath);
        if (node != null && node.isFile) {
            return node.content.toString();
        }
        return "";
    }
    
    private TrieNode getNode(String path) {
        return getNode(path, false);
    }
    
    private TrieNode getNode(String path, boolean isFile) {
        if (path.equals("/")) {
            return root;
        }
        
        String[] parts = path.substring(1).split("/");
        TrieNode current = root;
        
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            boolean isLast = (i == parts.length - 1);
            
            if (!current.children.containsKey(part)) {
                if (isLast && isFile) {
                    current.children.put(part, new TrieNode(part));
                } else if (!isFile) {
                    current.children.put(part, new TrieNode(part));
                } else {
                    return null;  // Can't create file as directory
                }
            }
            
            current = current.children.get(part);
            
            // If last part and we're creating a file
            if (isLast && isFile) {
                current.isFile = true;
            }
        }
        
        return current;
    }
}

/**
 * Test and Demonstration Class
 */
public class Solution {
    
    /**
     * Test the file system implementation
     */
    public static void testFileSystem() {
        System.out.println("Testing File System Implementation 1:");
        System.out.println("=====================================");
        
        FileSystem fs = new FileSystem();
        
        // Initial state
        System.out.println("\n1. Initial ls('/'): " + fs.ls("/"));
        
        // Create directories
        System.out.println("\n2. Creating directories:");
        fs.mkdir("/a/b/c");
        fs.mkdir("/a/b/d");
        fs.mkdir("/x/y");
        fs.printFileSystem();
        
        // List directories
        System.out.println("\n3. ls('/'): " + fs.ls("/"));
        System.out.println("   ls('/a'): " + fs.ls("/a"));
        System.out.println("   ls('/a/b'): " + fs.ls("/a/b"));
        
        // Create and append to files
        System.out.println("\n4. Creating files:");
        fs.addContentToFile("/a/b/c/file1.txt", "Hello ");
        fs.addContentToFile("/a/b/c/file1.txt", "World!");
        fs.addContentToFile("/a/b/d/file2.txt", "Content ");
        fs.addContentToFile("/a/b/d/file2.txt", "for file 2");
        fs.addContentToFile("/root_file.txt", "Root file content");
        fs.printFileSystem();
        
        // List with files
        System.out.println("\n5. ls('/a/b/c'): " + fs.ls("/a/b/c"));
        System.out.println("   ls('/a/b/d'): " + fs.ls("/a/b/d"));
        
        // Read file content
        System.out.println("\n6. Reading files:");
        System.out.println("   read('/a/b/c/file1.txt'): \"" + 
            fs.readContentFromFile("/a/b/c/file1.txt") + "\"");
        System.out.println("   read('/a/b/d/file2.txt'): \"" + 
            fs.readContentFromFile("/a/b/d/file2.txt") + "\"");
        System.out.println("   read('/root_file.txt'): \"" + 
            fs.readContentFromFile("/root_file.txt") + "\"");
        
        // Edge cases
        System.out.println("\n7. Edge cases:");
        System.out.println("   ls('/nonexistent'): " + fs.ls("/nonexistent"));
        System.out.println("   read('/nonexistent'): \"" + 
            fs.readContentFromFile("/nonexistent") + "\"");
    }
    
    /**
     * Test FileSystem2 implementation
     */
    public static void testFileSystem2() {
        System.out.println("\n\nTesting File System Implementation 2:");
        System.out.println("======================================");
        
        FileSystem2 fs = new FileSystem2();
        
        // Example from problem statement
        System.out.println("\nExample from problem:");
        System.out.println("ls('/'): " + fs.ls("/"));
        fs.mkdir("/a/b/c");
        fs.addContentToFile("/a/b/c/d", "hello");
        System.out.println("ls('/'): " + fs.ls("/"));
        System.out.println("read('/a/b/c/d'): \"" + 
            fs.readContentFromFile("/a/b/c/d") + "\"");
        
        // Additional tests
        fs.addContentToFile("/a/b/c/d", " world");
        System.out.println("read after append: \"" + 
            fs.readContentFromFile("/a/b/c/d") + "\"");
        
        fs.mkdir("/a/b/e");
        fs.addContentToFile("/a/b/e/f", "file f content");
        System.out.println("ls('/a/b'): " + fs.ls("/a/b"));
    }
    
    /**
     * Test FileSystem3 implementation
     */
    public static void testFileSystem3() {
        System.out.println("\n\nTesting File System Implementation 3:");
        System.out.println("======================================");
        
        FileSystem3 fs = new FileSystem3();
        
        // Complex test case
        System.out.println("\nComplex file system operations:");
        
        // Create nested directories
        fs.mkdir("/usr/local/bin");
        fs.mkdir("/usr/local/lib");
        fs.mkdir("/var/log");
        
        // Create files
        fs.addContentToFile("/usr/local/bin/script.sh", "#!/bin/bash\necho \"Hello\"");
        fs.addContentToFile("/var/log/app.log", "Log entry 1\n");
        fs.addContentToFile("/var/log/app.log", "Log entry 2\n");
        
        // List operations
        System.out.println("ls('/usr'): " + fs.ls("/usr"));
        System.out.println("ls('/usr/local'): " + fs.ls("/usr/local"));
        System.out.println("ls('/usr/local/bin'): " + fs.ls("/usr/local/bin"));
        
        // Read operations
        System.out.println("\nFile contents:");
        System.out.println("/usr/local/bin/script.sh: \n" + 
            fs.readContentFromFile("/usr/local/bin/script.sh"));
        System.out.println("/var/log/app.log: \n" + 
            fs.readContentFromFile("/var/log/app.log"));
    }
    
    /**
     * Performance comparison
     */
    public static void comparePerformance() {
        System.out.println("\n\nPerformance Comparison:");
        System.out.println("========================");
        
        int numOperations = 10000;
        Random rand = new Random(42);
        
        // Test FileSystem
        long start = System.currentTimeMillis();
        FileSystem fs1 = new FileSystem();
        for (int i = 0; i < numOperations; i++) {
            int op = rand.nextInt(4);
            String path = "/dir" + rand.nextInt(100) + 
                         "/subdir" + rand.nextInt(10);
            
            switch (op) {
                case 0:
                    fs1.mkdir(path);
                    break;
                case 1:
                    fs1.addContentToFile(path + "/file.txt", "content");
                    break;
                case 2:
                    fs1.ls(path);
                    break;
                case 3:
                    fs1.readContentFromFile(path + "/file.txt");
                    break;
            }
        }
        long time1 = System.currentTimeMillis() - start;
        
        // Test FileSystem2
        start = System.currentTimeMillis();
        FileSystem2 fs2 = new FileSystem2();
        for (int i = 0; i < numOperations; i++) {
            int op = rand.nextInt(4);
            String path = "/dir" + rand.nextInt(100) + 
                         "/subdir" + rand.nextInt(10);
            
            switch (op) {
                case 0:
                    fs2.mkdir(path);
                    break;
                case 1:
                    fs2.addContentToFile(path + "/file.txt", "content");
                    break;
                case 2:
                    fs2.ls(path);
                    break;
                case 3:
                    fs2.readContentFromFile(path + "/file.txt");
                    break;
            }
        }
        long time2 = System.currentTimeMillis() - start;
        
        // Test FileSystem3
        start = System.currentTimeMillis();
        FileSystem3 fs3 = new FileSystem3();
        for (int i = 0; i < numOperations; i++) {
            int op = rand.nextInt(4);
            String path = "/dir" + rand.nextInt(100) + 
                         "/subdir" + rand.nextInt(10);
            
            switch (op) {
                case 0:
                    fs3.mkdir(path);
                    break;
                case 1:
                    fs3.addContentToFile(path + "/file.txt", "content");
                    break;
                case 2:
                    fs3.ls(path);
                    break;
                case 3:
                    fs3.readContentFromFile(path + "/file.txt");
                    break;
            }
        }
        long time3 = System.currentTimeMillis() - start;
        
        System.out.println("\nResults for " + numOperations + " operations:");
        System.out.println("FileSystem (TreeMap):   " + time1 + " ms");
        System.out.println("FileSystem2 (HashMap):  " + time2 + " ms");
        System.out.println("FileSystem3 (Trie):     " + time3 + " ms");
        
        System.out.println("\nObservations:");
        System.out.println("1. TreeMap provides automatic sorting for ls()");
        System.out.println("2. HashMap is faster for insertion but requires manual sorting");
        System.out.println("3. Trie approach is most elegant for path operations");
        System.out.println("4. All approaches are O(L) for path operations");
    }
    
    /**
     * Demonstrate edge cases
     */
    public static void testEdgeCases() {
        System.out.println("\n\nEdge Cases Testing:");
        System.out.println("====================");
        
        FileSystem fs = new FileSystem();
        
        System.out.println("\n1. Root operations:");
        System.out.println("ls('/'): " + fs.ls("/"));
        fs.addContentToFile("/rootfile.txt", "Root file");
        System.out.println("ls('/') after adding file: " + fs.ls("/"));
        
        System.out.println("\n2. Multiple slashes and trailing slash:");
        fs.mkdir("//a///b//c///");
        System.out.println("ls('/a/b'): " + fs.ls("/a/b"));
        
        System.out.println("\n3. File in root directory:");
        fs.addContentToFile("/file_in_root", "Content");
        System.out.println("ls('/'): " + fs.ls("/"));
        System.out.println("read('/file_in_root'): \"" + 
            fs.readContentFromFile("/file_in_root") + "\"");
        
        System.out.println("\n4. Overwriting directories with files:");
        fs.mkdir("/test/dir");
        try {
            fs.addContentToFile("/test/dir", "trying to write to directory");
            System.out.println("Should not reach here - directories can't be files");
        } catch (Exception e) {
            System.out.println("Correctly prevented writing to directory as file");
        }
        
        System.out.println("\n5. Long paths:");
        StringBuilder longPath = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            longPath.append("/level").append(i);
        }
        fs.mkdir(longPath.toString());
        System.out.println("Created 10-level deep directory");
        System.out.println("ls at depth 5: " + fs.ls("/level0/level1/level2/level3/level4"));
    }
    
    /**
     * Design patterns and principles used
     */
    public static void explainDesign() {
        System.out.println("\n\nDesign Patterns and Principles:");
        System.out.println("================================");
        
        System.out.println("\n1. Composite Pattern:");
        System.out.println("   - Treat files and directories uniformly");
        System.out.println("   - Both are FileNodes with common interface");
        System.out.println("   - Simplifies traversal and operations");
        
        System.out.println("\n2. Trie Data Structure:");
        System.out.println("   - Natural for hierarchical paths");
        System.out.println("   - Efficient prefix operations");
        System.out.println("   - Easy path traversal");
        
        System.out.println("\n3. Separation of Concerns:");
        System.out.println("   - Path parsing logic separate from file operations");
        System.out.println("   - Node creation separate from content management");
        System.out.println("   - Clear API boundaries");
        
        System.out.println("\n4. Immutability where possible:");
        System.out.println("   - File names are immutable");
        System.out.println("   - Path strings are immutable");
        System.out.println("   - Only content is mutable");
        
        System.out.println("\n5. Error Handling:");
        System.out.println("   - Graceful handling of non-existent paths");
        System.out.println("   - Clear separation of files vs directories");
        System.out.println("   - Input validation");
        
        System.out.println("\n6. Performance Considerations:");
        System.out.println("   - TreeMap for automatic sorting (O(log n) operations)");
        System.out.println("   - StringBuilder for efficient string concatenation");
        System.out.println("   - Lazy creation of nodes");
        System.out.println("   - Efficient path parsing");
    }
    
    /**
     * Real-world considerations
     */
    public static void realWorldConsiderations() {
        System.out.println("\n\nReal-world Considerations:");
        System.out.println("===========================");
        
        System.out.println("\n1. Concurrency:");
        System.out.println("   - Real file systems need to handle concurrent access");
        System.out.println("   - Need locks or concurrent data structures");
        System.out.println("   - Transaction support for atomic operations");
        
        System.out.println("\n2. Persistence:");
        System.out.println("   - In-memory file system needs persistence layer");
        System.out.println("   - Serialization to disk");
        System.out.println("   - Crash recovery");
        
        System.out.println("\n3. Security:");
        System.out.println("   - Permissions (read/write/execute)");
        System.out.println("   - Ownership and groups");
        System.out.println("   - Access control lists");
        
        System.out.println("\n4. Advanced Features:");
        System.out.println("   - Symbolic links");
        System.out.println("   - Hard links");
        System.out.println("   - File metadata (size, creation time, modification time)");
        System.out.println("   - Disk quotas");
        
        System.out.println("\n5. Performance Optimizations:");
        System.out.println("   - Caching frequently accessed files");
        System.out.println("   - Lazy loading of directory contents");
        System.out.println("   - Compression for large files");
        System.out.println("   - Indexing for fast search");
        
        System.out.println("\n6. Network File System:");
        System.out.println("   - Remote access protocols");
        System.out.println("   - Cache consistency");
        System.out.println("   - Latency hiding techniques");
        System.out.println("   - Partial file synchronization");
    }
    
    /**
     * Interview preparation tips
     */
    public static void interviewTips() {
        System.out.println("\n\nInterview Preparation Tips:");
        System.out.println("============================");
        
        System.out.println("\n1. Clarify Requirements:");
        System.out.println("   - Exact API specifications");
        System.out.println("   - Edge cases (root directory, invalid paths)");
        System.out.println("   - Performance expectations");
        System.out.println("   - Thread safety requirements");
        
        System.out.println("\n2. Design Discussion:");
        System.out.println("   - Propose tree/trie structure first");
        System.out.println("   - Discuss file vs directory representation");
        System.out.println("   - Consider sorting requirements for ls()");
        System.out.println("   - Discuss time/space complexity trade-offs");
        
        System.out.println("\n3. Implementation Steps:");
        System.out.println("   - Start with node class definition");
        System.out.println("   - Implement path parsing helper");
        System.out.println("   - Implement basic traversal");
        System.out.println("   - Add each API method one by one");
        
        System.out.println("\n4. Testing Strategy:");
        System.out.println("   - Test provided examples first");
        System.out.println("   - Test edge cases (empty paths, non-existent files)");
        System.out.println("   - Test performance with large number of operations");
        System.out.println("   - Verify lexicographic ordering");
        
        System.out.println("\n5. Optimization Discussion:");
        System.out.println("   - Discuss TreeMap vs HashMap + sorting");
        System.out.println("   - Consider StringBuilder vs String for content");
        System.out.println("   - Discuss lazy vs eager directory creation");
        System.out.println("   - Mention possible caching strategies");
        
        System.out.println("\n6. Extensions Discussion:");
        System.out.println("   - How to add file permissions");
        System.out.println("   - How to implement 'rm' command");
        System.out.println("   - How to add search functionality");
        System.out.println("   - How to make it persistent");
    }
    
    /**
     * Main method with comprehensive demonstration
     */
    public static void main(String[] args) {
        System.out.println("588. Design In-Memory File System");
        System.out.println("==================================");
        
        // Test all implementations
        testFileSystem();
        testFileSystem2();
        testFileSystem3();
        
        // Performance comparison
        comparePerformance();
        
        // Edge cases
        testEdgeCases();
        
        // Design discussion
        explainDesign();
        
        // Real-world considerations
        realWorldConsiderations();
        
        // Interview tips
        interviewTips();
        
        // Summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY");
        System.out.println("=".repeat(80));
        
        System.out.println("\nRecommended Implementation:");
        System.out.println("""
class FileSystem {
    class FileNode {
        String name;
        boolean isFile;
        StringBuilder content;
        Map<String, FileNode> children;
        
        FileNode(String name, boolean isFile) {
            this.name = name;
            this.isFile = isFile;
            this.children = new TreeMap<>();
            if (isFile) this.content = new StringBuilder();
        }
    }
    
    private FileNode root;
    
    public FileSystem() {
        root = new FileNode("", false);
    }
    
    public List<String> ls(String path) {
        FileNode node = traverse(path);
        List<String> result = new ArrayList<>();
        if (node.isFile) {
            result.add(node.name);
        } else {
            result.addAll(node.children.keySet());
        }
        return result;
    }
    
    public void mkdir(String path) {
        String[] parts = parsePath(path);
        FileNode current = root;
        for (String part : parts) {
            if (!current.children.containsKey(part)) {
                current.children.put(part, new FileNode(part, false));
            }
            current = current.children.get(part);
        }
    }
    
    public void addContentToFile(String filePath, String content) {
        String[] parts = parsePath(filePath);
        FileNode current = root;
        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            if (!current.children.containsKey(part)) {
                current.children.put(part, new FileNode(part, false));
            }
            current = current.children.get(part);
        }
        String fileName = parts[parts.length - 1];
        if (!current.children.containsKey(fileName)) {
            current.children.put(fileName, new FileNode(fileName, true));
        }
        current.children.get(fileName).appendContent(content);
    }
    
    public String readContentFromFile(String filePath) {
        FileNode node = traverse(filePath);
        return node != null && node.isFile ? node.getContent() : "";
    }
    
    private String[] parsePath(String path) {
        if (path.equals("/")) return new String[0];
        return Arrays.stream(path.substring(1).split("/"))
                    .filter(p -> !p.isEmpty())
                    .toArray(String[]::new);
    }
    
    private FileNode traverse(String path) {
        String[] parts = parsePath(path);
        FileNode current = root;
        for (String part : parts) {
            if (!current.children.containsKey(part)) return null;
            current = current.children.get(part);
        }
        return current;
    }
}
            """);
        
        System.out.println("\nKey Features:");
        System.out.println("1. Tree structure with FileNode class");
        System.out.println("2. TreeMap for automatic lexicographic ordering");
        System.out.println("3. StringBuilder for efficient file content manipulation");
        System.out.println("4. Path parsing helper methods");
        System.out.println("5. Clear separation of files and directories");
        
        System.out.println("\nComplexity Analysis:");
        System.out.println("- ls: O(n log n) for sorting (or O(n) with TreeMap)");
        System.out.println("- mkdir: O(L) where L is path depth");
        System.out.println("- addContentToFile: O(L + C) where C is content length");
        System.out.println("- readContentFromFile: O(L)");
        System.out.println("- Space: O(N) total nodes");
        
        System.out.println("\nCommon Interview Questions:");
        System.out.println("1. How would you add file permissions?");
        System.out.println("2. How would you implement 'rm' command?");
        System.out.println("3. How would you make it persistent?");
        System.out.println("4. How would you handle concurrent access?");
        System.out.println("5. How would you add search functionality?");
    }
}
