package by.it.a_khmelev.lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {

    private class Node {
        Integer key;
        String value;
        Node left, right;

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;

    public MySplayMap() {
        root = null;
        size = 0;
    }

    // Вспомогательный метод для выполнения splay-операции
    private Node splay(Node node, Integer key) {
        if (node == null || node.key.equals(key)) {
            return node;
        }

        if (key < node.key) {
            if (node.left == null) {
                return node;
            }
            if (key < node.left.key) {
                node.left.left = splay(node.left.left, key);
                node = rotateRight(node);
            } else if (key > node.left.key) {
                node.left.right = splay(node.left.right, key);
                if (node.left.right != null) {
                    node.left = rotateLeft(node.left);
                }
            }
            return node.left == null ? node : rotateRight(node);
        } else {
            if (node.right == null) {
                return node;
            }
            if (key > node.right.key) {
                node.right.right = splay(node.right.right, key);
                node = rotateLeft(node);
            } else if (key < node.right.key) {
                node.right.left = splay(node.right.left, key);
                if (node.right.left != null) {
                    node.right = rotateRight(node.right);
                }
            }
            return node.right == null ? node : rotateLeft(node);
        }
    }

    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        return newRoot;
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        return newRoot;
    }

    // put
    @Override
    public String put(Integer key, String value) {
        if (key == null || value == null) {
            throw new NullPointerException("Key or value cannot be null");
        }

        if (root == null) {
            root = new Node(key, value);
            size++;
            return null;
        }

        root = splay(root, key);

        if (key.equals(root.key)) {
            String oldValue = root.value;
            root.value = value;
            return oldValue;
        }

        Node newNode = new Node(key, value);
        if (key < root.key) {
            newNode.right = root;
            newNode.left = root.left;
            root.left = null;
        } else {
            newNode.left = root;
            newNode.right = root.right;
            root.right = null;
        }
        root = newNode;
        size++;
        return null;
    }

    // get
    @Override
    public String get(Object key) {
        if (key == null) {
            return null;
        }
        root = splay(root, (Integer) key);
        return root != null && root.key.equals(key) ? root.value : null;
    }

    // remove
    @Override
    public String remove(Object key) {
        if (key == null || root == null) {
            return null;
        }

        root = splay(root, (Integer) key);

        if (!root.key.equals(key)) {
            return null;
        }

        String oldValue = root.value;
        if (root.left == null) {
            root = root.right;
        } else {
            Node temp = root.right;
            root = root.left;
            root = splay(root, (Integer) key);
            root.right = temp;
        }
        size--;
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    // containsKey
    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    // containsValue
    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, (String) value);
    }

    private boolean containsValue(Node node, String value) {
        if (node == null) {
            return false;
        }
        if (value.equals(node.value)) {
            return true;
        }
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    // size
    @Override
    public int size() {
        return size;
    }

    // clear
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    // isEmpty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // firstKey
    @Override
    public Integer firstKey() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty");
        }
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.key;
    }

    // lastKey
    @Override
    public Integer lastKey() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty");
        }
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.key;
    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<String> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null;
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        return null;
    }

    // lowerKey
    @Override
    public Integer lowerKey(Integer key) {
        return findBoundKey(key, false, false);
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        return null;
    }

    // floorKey
    @Override
    public Integer floorKey(Integer key) {
        return findBoundKey(key, true, false);
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        return null;
    }

    // ceilingKey
    @Override
    public Integer ceilingKey(Integer key) {
        return findBoundKey(key, true, true);
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        return null;
    }

    // higherKey
    @Override
    public Integer higherKey(Integer key) {
        return findBoundKey(key, false, true);
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        return null;
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        return null;
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        return null;
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    private Integer findBoundKey(Integer key, boolean inclusive, boolean higher) {
        if (root == null) {
            return null;
        }
        root = splay(root, key);
        if ((inclusive && key.equals(root.key)) || (higher && key > root.key) || (!higher && key < root.key)) {
            return root.key;
        }
        Node candidate = higher ? root.right : root.left;
        while (candidate != null) {
            if ((higher && key < candidate.key) || (!higher && key > candidate.key)) {
                return candidate.key;
            }
            candidate = higher ? candidate.right : candidate.left;
        }
        return null;
    }

    // headMap
    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey) {
        MySplayMap subMap = new MySplayMap();
        fillSubMap(root, subMap, null, toKey);
        return subMap;
    }

    // tailMap
    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey) {
        MySplayMap subMap = new MySplayMap();
        fillSubMap(root, subMap, fromKey, null);
        return subMap;
    }

    private void fillSubMap(Node node, MySplayMap subMap, Integer fromKey, Integer toKey) {
        if (node == null) {
            return;
        }
        if ((fromKey == null || node.key >= fromKey) && (toKey == null || node.key < toKey)) {
            subMap.put(node.key, node.value);
        }
        fillSubMap(node.left, subMap, fromKey, toKey);
        fillSubMap(node.right, subMap, fromKey, toKey);
    }

    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        inorderTraversal(root, sb);
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Удаляем последнюю запятую
        }
        sb.append("}");
        return sb.toString();
    }

    private void inorderTraversal(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left, sb);
        sb.append(node.key).append("=").append(node.value).append(", ");
        inorderTraversal(node.right, sb);
    }
}