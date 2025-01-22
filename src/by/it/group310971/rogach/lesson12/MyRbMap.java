package by.it.group310971.rogach.lesson12;

import java.util.*;
import java.util.function.Consumer;

public class MyRbMap implements SortedMap<Integer, String> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        int key; // Ключ узла
        String value; // Значение узла
        Node left, right, parent; // Левый, правый дочерние узлы и родительский узел
        boolean color;

        Node(int key, String value, boolean color, Node parent) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.parent = parent;
        }
    }

    private Node root; // Корень дерева
    private int size; // Количество узлов

    public MyRbMap() {
        this.root = null;
        this.size = 0;
    }

    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    private Node rotateLeft(Node node) {
        Node rightChild = node.right; // Правый дочерний узел
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }
        rightChild.left = node;
        node.parent = rightChild;
        return rightChild;
    }

    private Node rotateRight(Node node) {
        Node leftChild = node.left; // Левый дочерний узел
        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
        return leftChild;
    }

    private void fixAfterInsertion(Node node) {
        node.color = RED;
        while (node != null && node != root && isRed(node.parent)) {
            if (node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
                if (isRed(uncle)) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateRight(node.parent.parent);
                }
            } else {
                Node uncle = node.parent.parent.left;
                if (isRed(uncle)) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    @Override
    public String put(Integer key, String value) {
        Node parent = null, current = root;
        int cmp = 0;
        while (current != null) {
            parent = current;
            cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                String oldValue = current.value;
                current.value = value;
                return oldValue;
            }
        }
        Node newNode = new Node(key, value, RED, parent);
        if (parent == null) {
            root = newNode;
        } else if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        fixAfterInsertion(newNode);
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        Node node = getNode(root, (Integer) key);
        if (node == null) {
            return null;
        }
        String oldValue = node.value;
        deleteNode(node);
        size--;
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    private void deleteNode(Node node) {
        if (node.left == null && node.right == null) {
            if (node == root) {
                root = null;
            } else if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        } else if (node.left == null) {
            if (node == root) {
                root = node.right;
            } else if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
        } else if (node.right == null) {
            if (node == root) {
                root = node.left;
            } else if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }
            node.left.parent = node.parent;
        } else {
            Node successor = getSuccessor(node);
            node.key = successor.key;
            node.value = successor.value;
            deleteNode(successor);
        }
    }

    private Node getSuccessor(Node node) {
        Node successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        return successor;
    }

    private Node getNode(Node node, Integer key) {
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
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

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public Integer firstKey() {
        Node node = root;
        if (node == null) throw new NoSuchElementException();
        while (node.left != null) {
            node = node.left;
        }
        return node.key;
    }

    @Override
    public Integer lastKey() {
        Node node = root;
        if (node == null) throw new NoSuchElementException();
        while (node.right != null) {
            node = node.right;
        }
        return node.key;
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> keys = new TreeSet<>();
        inOrderTraversal(root, keys::add);
        return keys;
    }

    @Override
    public Collection<String> values() {
        return List.of();
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return Set.of();
    }

    private void inOrderTraversal(Node node, Consumer<Integer> consumer) {
        if (node != null) {
            inOrderTraversal(node.left, consumer);
            consumer.accept(node.key);
            inOrderTraversal(node.right, consumer);
        }
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MyRbMap subMap = new MyRbMap();
        fillSubMap(root, subMap, null, toKey);
        return subMap;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MyRbMap subMap = new MyRbMap();
        fillSubMap(root, subMap, fromKey, null);
        return subMap;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        MyRbMap subMap = new MyRbMap();
        fillSubMap(root, subMap, fromKey, toKey);
        return subMap;
    }

    private void fillSubMap(Node node, MyRbMap subMap, Integer fromKey, Integer toKey) {
        if (node == null) return;
        if ((fromKey == null || node.key >= fromKey) && (toKey == null || node.key < toKey)) {
            subMap.put(node.key, node.value);
        }
        if (fromKey == null || node.key >= fromKey) {
            fillSubMap(node.left, subMap, fromKey, toKey);
        }
        if (toKey == null || node.key < toKey) {
            fillSubMap(node.right, subMap, fromKey, toKey);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        inOrderTraversalToString(root, sb);
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    private void inOrderTraversalToString(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderTraversalToString(node.left, sb);
            sb.append(node.key).append("=").append(node.value).append(", ");
            inOrderTraversalToString(node.right, sb);
        }
    }
}
