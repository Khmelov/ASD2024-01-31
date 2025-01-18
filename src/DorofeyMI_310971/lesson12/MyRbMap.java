package DorofeyMI_310971.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Integer key;
        String value;
        Node left, right;
        boolean color;

        Node(Integer key, String value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }

    private Node root;
    private int size = 0;

    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color == RED;
    }

    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right) && isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        if (h.left != null) h.left.color = BLACK;
        if (h.right != null) h.right.color = BLACK;
    }

    private Node put(Node h, Integer key, String value) {
        if (h == null) {
            size++;
            return new Node(key, value, RED);
        }

        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, value);
        else if (cmp > 0) h.right = put(h.right, key, value);
        else h.value = value;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MyRbMap subMap = new MyRbMap();
        for (Map.Entry<Integer, String> entry : entrySet()) {
            if (entry.getKey().compareTo(toKey) < 0) {
                subMap.put(entry.getKey(), entry.getValue());
            }
        }
        return subMap;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MyRbMap subMap = new MyRbMap();
        for (Map.Entry<Integer, String> entry : entrySet()) {
            if (entry.getKey().compareTo(fromKey) >= 0) {
                subMap.put(entry.getKey(), entry.getValue());
            }
        }
        return subMap;
    }

    @Override
    public String put(Integer key, String value) {
        String oldValue = get(key);
        root = put(root, key, value);
        root.color = BLACK;
        return oldValue;
    }



    private Node min(Node h) {
        if (h.left == null) return h;
        else return min(h.left);
    }

    private Node deleteMin(Node h) {
        if (h.left == null) return null;
        if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);
    }


    private Node balance(Node h) {
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        return h;
    }

    private Node moveRedRight(Node h) {
        flipColors(h);
        if (h.left != null && isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }


    private Node delete(Node h, Integer key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null)) return null;
            if (!isRed(h.right) && !isRed(h.right.left)) h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.value = x.value;
                h.right = deleteMin(h.right);
            } else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    @Override
    public String remove(Object key) {
        if (!containsKey(key)) return null;
        String oldValue = get(key);
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, (Integer) key);
        if (!isEmpty()) root.color = BLACK;
        size--;
        return oldValue;
    }


    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    private Node getNode(Node h, Integer key) {
        if (h == null) return null;
        int cmp = key.compareTo(h.key);
        if (cmp < 0) return getNode(h.left, key);
        else if (cmp > 0) return getNode(h.right, key);
        else return h;
    }

    @Override
    public String get(Object key) {
        Node node = getNode(root, (Integer) key);
        return node == null ? null : node.value;
    }

    @Override
    public boolean containsKey(Object key) {
        return getNode(root, (Integer) key) != null;
    }

    private boolean containsValue(Node h, String value) {
        if (h == null) return false;
        if (h.value.equals(value)) return true;
        return containsValue(h.left, value) || containsValue(h.right, value);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void inOrderTraversal(Node h, StringBuilder sb) {
        if (h == null) return;
        inOrderTraversal(h.left, sb);
        sb.append(h.key).append("=").append(h.value).append(", ");
        inOrderTraversal(h.right, sb);
    }

    @Override
    public String toString() {
        if (isEmpty()) return "{}";
        StringBuilder sb = new StringBuilder("{");
        inOrderTraversal(root, sb);
        sb.setLength(sb.length() - 2); // Remove the last ", "
        sb.append("}");
        return sb.toString();
    }


    @Override
    public Integer firstKey() {
        if (isEmpty()) throw new NoSuchElementException();
        return min(root).key;
    }

    private Node max(Node h) {
        if (h.right == null) return h;
        else return max(h.right);
    }

    @Override
    public Integer lastKey() {
        if (isEmpty()) throw new NoSuchElementException();
        return max(root).key;
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }


    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(Object value) {
        if (!(value instanceof String)) {
            return false;
        }
        return containsValue(root, (String) value);
    }

    @Override
    public Set<Integer> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<Integer, String>> entrySet() {
        Set<Map.Entry<Integer, String>> entrySet = new TreeSet<>(Map.Entry.comparingByKey());
        inOrderTraversal(root, entrySet);
        return entrySet;
    }

    private void inOrderTraversal(Node h, Set<Map.Entry<Integer, String>> entrySet) {
        if (h == null) return;
        inOrderTraversal(h.left, entrySet);
        entrySet.add(new AbstractMap.SimpleEntry<>(h.key, h.value));
        inOrderTraversal(h.right, entrySet);
    }

    @Override
    public Collection<String> values() {
        throw new UnsupportedOperationException();
    }
}
