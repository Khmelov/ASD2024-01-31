package by.it.a_khmelev.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyAvlMap implements Map<Integer, String> {

    private static class Node {
        Integer key;
        String value;
        int height;
        Node left;
        Node right;

        Node(Integer key, String value) {
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

    // isEmpty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // put
    @Override
    public String put(Integer key, String value) {
        if (key == null || value == null) {
            throw new NullPointerException("Key and value cannot be null");
        }
        Node[] result = insert(root, key, value);
        root = result[0];
        return String.valueOf(result[1]); // возвращаем старое значение (если оно было)
    }

    // get
    @Override
    public String get(Object key) {
        Node node = getNode(root, (Integer) key);
        return node != null ? node.value : null;
    }

    // remove
    @Override
    public String remove(Object key) {
        if (key == null) {
            return null;
        }
        Node[] result = delete(root, (Integer) key);
        root = result[0];
        return String.valueOf(result[1]); // возвращаем удаленное значение (если оно было)
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    // containsKey
    @Override
    public boolean containsKey(Object key) {
        return getNode(root, (Integer) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        inorderTraversal(root, sb);
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // удал€ем последнюю зап€тую и пробел
        }
        sb.append("}");
        return sb.toString();
    }

    // ¬спомогательные методы

    private Node[] insert(Node node, Integer key, String value) {
        if (node == null) {
            size++;
            return new Node[]{new Node(key, value), null};
        }

        String oldValue = null;
        if (key < node.key) {
            Node[] result = insert(node.left, key, value);
            node.left = result[0];
            oldValue = String.valueOf(result[1]);
        } else if (key > node.key) {
            Node[] result = insert(node.right, key, value);
            node.right = result[0];
            oldValue = String.valueOf(result[1]);
        } else { // обновл€ем значение, если ключ уже существует
            oldValue = node.value;
            node.value = value;
        }

        updateHeight(node);
        return new Node[]{balance(node)};
    }

    private Node[] delete(Node node, Integer key) {
        if (node == null) {
            return new Node[]{null, null};
        }

        String oldValue = null;
        if (key < node.key) {
            Node[] result = delete(node.left, key);
            node.left = result[0];
            oldValue = String.valueOf(result[1]);
        } else if (key > node.key) {
            Node[] result = delete(node.right, key);
            node.right = result[0];
            oldValue = String.valueOf(result[1]);
        } else { // ключ найден
            oldValue = node.value;
            size--;

            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node successor = findMin(node.right);
                node.key = successor.key;
                node.value = successor.value;
                node.right = delete(node.right, successor.key)[0];
            }
        }

        if (node != null) {
            updateHeight(node);
            return new Node[]{balance(node)};
        }

        return new Node[]{null};
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

    private void inorderTraversal(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left, sb);
        sb.append(node.key).append("=").append(node.value).append(", ");
        inorderTraversal(node.right, sb);
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int height(Node node) {
        return node != null ? node.height : 0;
    }

    private int getBalance(Node node) {
        return height(node.left) - height(node.right);
    }

    private Node balance(Node node) {
        int balance = getBalance(node);

        if (balance > 1) { // левое поддерево выше
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left); // большой поворот
            }
            return rotateRight(node);
        }

        if (balance < -1) { // правое поддерево выше
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right); // большой поворот
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }
}