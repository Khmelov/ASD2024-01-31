package by.it.a_khmelev.lesson12;

import java.util.*;

public class MyRbMap  implements SortedMap<Integer, String> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Integer key;
        String value;
        Node left;
        Node right;
        Node parent;
        boolean color;

        Node(Integer key, String value, boolean color, Node parent) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.parent = parent;
        }
    }

    private Node root;
    private int size;

    public MyRbMap() {
        root = null;
        size = 0;
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

    // get
    @Override
    public String get(Object key) {
        Node node = getNode(root, (Integer) key);
        return node != null ? node.value : null;
    }

    // containsKey
    @Override
    public boolean containsKey(Object key) {
        return getNode(root, (Integer) key) != null;
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

    // put
    @Override
    public String put(Integer key, String value) {
        if (key == null || value == null) {
            throw new NullPointerException("Key or value cannot be null");
        }
        Node existing = getNode(root, key);
        if (existing != null) {
            String oldValue = existing.value;
            existing.value = value;
            return oldValue;
        }

        root = insert(root, key, value);
        root.color = BLACK; // корень всегда черный
        size++;
        return null;
    }

    // remove
    @Override
    public String remove(Object key) {
        if (key == null) {
            return null;
        }
        Node node = getNode(root, (Integer) key);
        if (node == null) {
            return null;
        }
        String oldValue = node.value;
        root = delete(root, (Integer) key);
        if (root != null) {
            root.color = BLACK;
        }
        size--;
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    // firstKey
    @Override
    public Integer firstKey() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty");
        }
        return findMin(root).key;
    }

    // lastKey
    @Override
    public Integer lastKey() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty");
        }
        return findMax(root).key;
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
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    // headMap
    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MyRbMap subMap = new MyRbMap();
        fillSubMap(root, subMap, null, toKey);
        return subMap;
    }

    // tailMap
    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MyRbMap subMap = new MyRbMap();
        fillSubMap(root, subMap, fromKey, null);
        return subMap;
    }

    private void fillSubMap(Node node, MyRbMap subMap, Integer fromKey, Integer toKey) {
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
            sb.setLength(sb.length() - 2); // удаляем последнюю запятую и пробел
        }
        sb.append("}");
        return sb.toString();
    }

    // Вспомогательные методы

    private Node getNode(Node node, Integer key) {
        while (node != null) {
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    private Node insert(Node node, Integer key, String value) {
        if (node == null) {
            return new Node(key, value, RED, null);
        }

        if (key < node.key) {
            node.left = insert(node.left, key, value);
            node.left.parent = node;
        } else if (key > node.key) {
            node.right = insert(node.right, key, value);
            node.right.parent = node;
        }

        return balanceAfterInsert(node);
    }

    private Node delete(Node node, Integer key) {
        // Удаление и балансировка дерева
        if (node == null) {
            return null;
        }

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                Node replacement = (node.left != null) ? node.left : node.right;
                if (replacement == null) {
                    return null;
                } else {
                    replacement.color = node.color;
                    return replacement;
                }
            } else {
                Node successor = findMin(node.right);
                node.key = successor.key;
                node.value = successor.value;
                node.right = delete(node.right, successor.key);
            }
        }

        return balanceAfterDelete(node);
    }

    private Node balanceAfterInsert(Node node) {
        // Балансировка после вставки
        return node;
    }

    private Node balanceAfterDelete(Node node) {
        // Балансировка после удаления
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
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