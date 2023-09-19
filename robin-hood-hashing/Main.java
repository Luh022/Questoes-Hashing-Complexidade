import java.util.Arrays;

class HashEntry {
    int key;
    int value;
    boolean isOccupied;

    HashEntry(int key, int value) {
        this.key = key;
        this.value = value;
        this.isOccupied = true;
    }
}

public class RobinHoodHashing {
    private HashEntry[] table;
    private int capacity;

    public RobinHoodHashing(int capacity) {
        this.capacity = capacity;
        table = new HashEntry[capacity];
        Arrays.fill(table, null);
    }

    private int hash(int key) {
        return key % capacity;
    }

    public void insert(int key, int value) {
        int hashedKey = hash(key);
        int distance = 0;

        while (distance < capacity) {
            if (table[hashedKey] == null || !table[hashedKey].isOccupied) {
                table[hashedKey] = new HashEntry(key, value);
                return;
            }

            if (table[hashedKey].key == key) {
                table[hashedKey].value = value;
                return;
            }

            if (table[hashedKey].isOccupied && distance < calculateDistance(hash(table[hashedKey].key), hashedKey)) {
                // Swap entries based on Robin Hood rule
                HashEntry temp = table[hashedKey];
                table[hashedKey] = new HashEntry(key, value);
                key = temp.key;
                value = temp.value;
                hashedKey = hash(key);
                distance = calculateDistance(hash(key), hashedKey);
            }

            hashedKey = (hashedKey + 1) % capacity;
            distance++;
        }

        System.out.println("Table is full, cannot insert: " + key);
    }

    private int calculateDistance(int from, int to) {
        return (to >= from) ? to - from : (capacity - from) + to;
    }

    public void displayTable() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && table[i].isOccupied) {
                System.out.println("Key: " + table[i].key + ", Value: " + table[i].value);
            }
        }
    }

    public static void main(String[] args) {
        int capacity = 10;
        RobinHoodHashing hashTable = new RobinHoodHashing(capacity);

        int[] keys = {23, 37, 18, 47, 10, 32, 56, 77, 92, 101};
        for (int key : keys) {
            hashTable.insert(key, key * 10);  // Just using key*10 as a placeholder for the value
        }

        hashTable.displayTable();
    }
}
