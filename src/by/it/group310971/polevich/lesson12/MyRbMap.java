package by.it.group310971.polevich.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        int key;
        String value;
        Node left, right, parent;
        boolean color;

        Node(int key, String value, boolean color, Node parent) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.parent = parent;
        }
    }

    private Node root;
    private int size;

    public MyRbMap() {
        this.root = null;
        this.size = 0;
    }

    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    private void rotateLeft(Node node) {
        Node rightChild = node.right;
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
    }

    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.left) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
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
        Node t = root;
        if (t == null) {
            root = new Node(key, value, BLACK, null);
            size = 1;
            return null;
        }

        Node parent = null;
        int cmp = 0;
        while (t != null) {
            parent = t;
            cmp = key.compareTo(t.key);
            if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            } else {
                String oldValue = t.value;
                t.value = value;
                return oldValue;
            }
        }

        Node newNode = new Node(key, value, RED, parent);
        if (cmp < 0) {
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
        Node node = getNode(root, (Integer) key); // Находим узел с ключом
        if (node == null) {
            return null; // Если ключ не найден, возвращаем null
        }

        String oldValue = node.value; // Сохраняем значение, которое будем удалять

        // Удаляем узел
        deleteNode(node);

        size--; // Уменьшаем размер карты
        return oldValue; // Возвращаем старое значение
    }

    private void deleteNode(Node node) {
        // Если у узла нет детей
        if (node.left == null && node.right == null) {
            if (node == root) {
                root = null;
            } else if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
        // Если у узла только один правый ребенок
        else if (node.left == null) {
            if (node == root) {
                root = node.right;
            } else if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
        }
        // Если у узла только один левый ребенок
        else if (node.right == null) {
            if (node == root) {
                root = node.left;
            } else if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }
            node.left.parent = node.parent;
        }
        // Если у узла два ребенка
        else {
            Node successor = getSuccessor(node);
            node.key = successor.key;
            node.value = successor.value;
            deleteNode(successor); // Рекурсивно удаляем преемника
        }
    }

    private Node getSuccessor(Node node) {
        Node successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        return successor;
    }


    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        for (Map.Entry<? extends Integer, ? extends String> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
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
        return containsValue(root, value);
    }

    private boolean containsValue(Node node, Object value) {
        if (node == null) {
            return false;
        }
        // Сравниваем значение текущего узла с искомым
        if (node.value.equals(value)) {
            return true;
        }
        // Рекурсивно ищем в левом и правом поддереве
        return containsValue(node.left, value) || containsValue(node.right, value);
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
        inOrderKeys(root, keys);
        return keys;
    }

    private void inOrderKeys(Node node, Set<Integer> keys) {
        if (node != null) {
            inOrderKeys(node.left, keys);
            keys.add(node.key);
            inOrderKeys(node.right, keys);
        }
    }

    @Override
    public Collection<String> values() {
        Collection<String> values = new ArrayList<>();
        inOrderValues(root, values);
        return values;
    }

    private void inOrderValues(Node node, Collection<String> values) {
        if (node != null) {
            inOrderValues(node.left, values);
            values.add(node.value);
            inOrderValues(node.right, values);
        }
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        Set<Entry<Integer, String>> entries = new TreeSet<>(Comparator.comparingInt(Entry::getKey));
        inOrderEntries(root, entries);
        return entries;
    }

    private void inOrderEntries(Node node, Set<Entry<Integer, String>> entries) {
        if (node != null) {
            inOrderEntries(node.left, entries);
            entries.add(new AbstractMap.SimpleEntry<>(node.key, node.value));
            inOrderEntries(node.right, entries);
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
    public Comparator<? super Integer> comparator() {
        return null;
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
    public int size() {
        return size;
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
}

