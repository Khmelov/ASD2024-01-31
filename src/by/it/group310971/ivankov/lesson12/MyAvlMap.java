package by.it.group310971.ivankov.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
public class MyAvlMap implements Map<Integer, String> {
    private class Node {
        int key;
        String value;
        int height;
        Node left, right;
        Node(int key, String value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }
    private Node root;
    private int size;
    public MyAvlMap() {
        this.root = null;
        this.size = 0;
    }
    // Helper methods for AVL tree operations
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }
    private int balanceFactor(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T = x.right;
        x.right = y;
        y.left = T;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T = y.left;
        y.left = x;
        x.right = T;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }
    private Node balance(Node node) {
        if (balanceFactor(node) > 1) {
            if (balanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balanceFactor(node) < -1) {
            if (balanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }
    // Public methods implementation
    @Override
    public String put(Integer key, String value) {
        String[] oldValue = new String[1];
        root = put(root, key, value, oldValue);
        return oldValue[0];
    }
    private Node put(Node node, Integer key, String value, String[] oldValue) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key < node.key) {
            node.left = put(node.left, key, value, oldValue);
        } else if (key > node.key) {
            node.right = put(node.right, key, value, oldValue);
        } else {
            oldValue[0] = node.value;
            node.value = value;
            return node;
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return balance(node);
    }
    @Override
    public String get(Object key) {
        Node node = getNode(root, (Integer) key);
        return node == null ? null : node.value;
    }
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
    @Override
    public boolean containsKey(Object key) {
        return getNode(root, (Integer) key) != null;
    }
    @Override
    public boolean containsValue(Object value) {
        return false;
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
    @Override
    public String remove(Object key) {
        String[] removedValue = new String[1];
        root = remove(root, (Integer) key, removedValue);
        return removedValue[0];
    }
    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
    }
    private Node remove(Node node, Integer key, String[] removedValue) {
        if (node == null) return null;
        if (key < node.key) {
            node.left = remove(node.left, key, removedValue);
        } else if (key > node.key) {
            node.right = remove(node.right, key, removedValue);
        } else {
            removedValue[0] = node.value;
            size--;
            if (node.left == null || node.right == null) {
                return (node.left != null) ? node.left : node.right;
            }
            Node minNode = getMin(node.right);
            node.key = minNode.key;
            node.value = minNode.value;
            node.right = remove(node.right, minNode.key, new String[1]);
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return balance(node);
    }
    private Node getMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        inOrderTraversal(root, sb);
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }
    private void inOrderTraversal(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderTraversal(node.left, sb);
            sb.append(node.key).append("=").append(node.value).append(", ");
            inOrderTraversal(node.right, sb);
        }
    }
    // Unsupported operations
    @Override
    public Set<Entry<Integer, String>> entrySet() {
        throw new UnsupportedOperationException();
    }
    @Override
    public Set<Integer> keySet() {
        throw new UnsupportedOperationException();
    }
    @Override
    public Collection<String> values() {
        throw new UnsupportedOperationException();
    }
}
