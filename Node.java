public class Node {
    String key;
    char value;
    boolean deleted; // Marcador lógico para eliminación (tombstone)

    public Node(String key, char value) {
        this.key = key;
        this.value = value;
        this.deleted = false;
    }

    @Override
    public String toString() {
        return deleted ? "[DELETED]" : "(" + key + "," + value + ")";
    }
}