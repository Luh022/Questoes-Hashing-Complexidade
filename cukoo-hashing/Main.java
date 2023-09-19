import java.util.Arrays;

public class CuckooHashing {
    private int[] table1;
    private int[] table2;
    private int size;
    private int maxRehashes;

    public CuckooHashing(int size, int maxRehashes) {
        this.size = size;
        this.maxRehashes = maxRehashes;
        table1 = new int[size];
        table2 = new int[size];
        Arrays.fill(table1, -1);
        Arrays.fill(table2, -1);
    }

    private int hash1(int key) {
        return key % size;
    }

    private int hash2(int key) {
        // Choose a prime number smaller than size for a good hash function
        return (key % (size - 1)) + 1;
    }

    public void insert(int key) {
        if (!insertKey(key, 0)) {
            System.out.println("Failed to insert key: " + key);
        }
    }

    private boolean insertKey(int key, int rehashCount) {
        if (rehashCount >= maxRehashes) {
            return false;  // Failed to insert within rehash limit
        }

        int hash1 = hash1(key);
        int hash2 = hash2(key);

        if (table1[hash1] == -1) {
            table1[hash1] = key;
            return true;
        }

        int displacedKey = table1[hash1];
        table1[hash1] = key;

        if (!insertKey(displacedKey, rehashCount + 1)) {
            int hash2Value = table2[hash2];
            table2[hash2] = displacedKey;
            if (hash2Value == -1) {
                return true;
            } else {
                return insertKey(hash2Value, rehashCount + 1);
            }
        }

        return true;
    }

    public void displayTables() {
        System.out.println("Table 1: " + Arrays.toString(table1));
        System.out.println("Table 2: " + Arrays.toString(table2));
    }

    public static void main(String[] args) {
        int size = 10;
        int maxRehashes = 5;
        CuckooHashing cuckooHashing = new CuckooHashing(size, maxRehashes);

        int[] keys = {23, 37, 18, 47, 10, 32, 56, 77, 92, 101};
        for (int key : keys) {
            cuckooHashing.insert(key);
        }

        cuckooHashing.displayTables();
    }
}
