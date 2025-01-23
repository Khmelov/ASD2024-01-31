package by.it.group310971.katsuba.lesson12;

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
        this.root = null;
        this.size = 0;
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
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, value);
    }

    private boolean containsValue(Node node, Object value) {
        if (node == null) return false;
        if (Objects.equals(node.value, value)) return true;
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    @Override
    public String get(Object key) {
        root = splay(root, (Integer) key);
        return root != null && root.key.equals(key) ? root.value : null;
    }

    @Override
    public String put(Integer key, String value) {
        if (root == null) {
            root = new Node(key, value);
            size++;
            return null;
        }

        root = splay(root, key);

        if (key.compareTo(root.key) == 0) {
            String oldValue = root.value;
            root.value = value;
            return oldValue;
        } else {
            Node newNode = new Node(key, value);
            if (key.compareTo(root.key) < 0) {
                newNode.left = root.left;
                newNode.right = root;
                root.left = null;
            } else {
                newNode.right = root.right;
                newNode.left = root;
                root.right = null;
            }
            root = newNode;
            size++;
            return null;
        }
    }

    @Override
    public String remove(Object key) {
        if (root == null) return null;

        root = splay(root, (Integer) key);

        if (!root.key.equals(key)) return null;

        String removedValue = root.value;

        if (root.left == null) {
            root = root.right;
        } else {
            Node rightSubtree = root.right;
            root = splay(root.left, (Integer) key);
            root.right = rightSubtree;
        }

        size--;
        return removedValue;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        toString(root, result);
        if (result.length() > 1) {
            result.setLength(result.length() - 2); // Удаляем последнюю запятую и пробел
        }
        result.append("}");
        return result.toString();
    }

    private void toString(Node node, StringBuilder result) {
        if (node != null) {
            toString(node.left, result);
            result.append(node.key).append("=").append(node.value).append(", ");
            toString(node.right, result);
        }
    }

    // Методы для splay-дерева
    private Node splay(Node node, Integer key) {
        if (node == null) return null;

        if (key.compareTo(node.key) < 0) {
            if (node.left == null) return node;
            if (key.compareTo(node.left.key) < 0) {
                node.left.left = splay(node.left.left, key);
                node = rotateRight(node);
            } else if (key.compareTo(node.left.key) > 0) {
                node.left.right = splay(node.left.right, key);
                if (node.left.right != null) {
                    node.left = rotateLeft(node.left);
                }
            }
            return node.left == null ? node : rotateRight(node);
        } else if (key.compareTo(node.key) > 0) {
            if (node.right == null) return node;
            if (key.compareTo(node.right.key) < 0) {
                node.right.left = splay(node.right.left, key);
                if (node.right.left != null) {
                    node.right = rotateRight(node.right);
                }
            } else if (key.compareTo(node.right.key) > 0) {
                node.right.right = splay(node.right.right, key);
                node = rotateLeft(node);
            }
            return node.right == null ? node : rotateLeft(node);
        } else {
            return node;
        }
    }

    private Node rotateRight(Node root) {
        Node temp = root.left;
        root.left = temp.right;
        temp.right = root;
        return temp;
    }

    private Node rotateLeft(Node root) {
        Node temp = root.right;
        root.right = temp.left;
        temp.left = root;
        return temp;
    }

    // Методы NavigableMap
    @Override
    public Integer firstKey() {
        if (root == null) return null;
        Node node = root;
        while (node.left != null)
            node = node.left;

        return node.key;
    }

    @Override
    public Integer lastKey() {
        if (root == null) return null;
        Node node = root;
        while (node.right != null)
            node = node.right;

        return node.key;
    }

    @Override
    public Integer lowerKey(Integer key) {
        Node node = lower(root, key);
        return node == null ? null : node.key;
    }

    private Node lower(Node node, Integer key) {
        if (node == null) return null;
        if (key.compareTo(node.key) <= 0) {
            return lower(node.left, key);
        } else {
            Node right = lower(node.right, key);
            return right != null ? right : node;
        }
    }

    @Override
    public Integer floorKey(Integer key) {
        Node node = floor(root, key);
        return node == null ? null : node.key;
    }

    private Node floor(Node node, Integer key) {
        if (node == null) return null;
        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return floor(node.left, key);
        } else {
            Node right = floor(node.right, key);
            return right != null ? right : node;
        }
    }

    @Override
    public Integer ceilingKey(Integer key) {
        Node node = ceiling(root, key);
        return node == null ? null : node.key;
    }

    private Node ceiling(Node node, Integer key) {
        if (node == null) return null;
        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) > 0) {
            return ceiling(node.right, key);
        } else {
            Node left = ceiling(node.left, key);
            return left != null ? left : node;
        }
    }

    @Override
    public Integer higherKey(Integer key) {
        Node node = higher(root, key);
        return node == null ? null : node.key;
    }

    private Node higher(Node node, Integer key) {
        if (node == null) return null;
        if (key.compareTo(node.key) >= 0) {
            return higher(node.right, key);
        } else {
            Node left = higher(node.left, key);
            return left != null ? left : node;
        }
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey) {
        MySplayMap result = new MySplayMap();
        headMap(root, toKey, result);
        return result;
    }

    private void headMap(Node node, Integer toKey, MySplayMap result) {
        if (node == null) return;
        headMap(node.left, toKey, result);
        if (node.key.compareTo(toKey) < 0) {
            result.put(node.key, node.value);
        }
        headMap(node.right, toKey, result);
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey) {
        MySplayMap result = new MySplayMap();
        tailMap(root, fromKey, result);
        return result;
    }

    private void tailMap(Node node, Integer fromKey, MySplayMap result) {
        if (node == null) return;
        tailMap(node.left, fromKey, result);
        if (node.key.compareTo(fromKey) >= 0) {
            result.put(node.key, node.value);
        }
        tailMap(node.right, fromKey, result);
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.util.Set<Integer> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.util.Collection<String> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.util.Set<Entry<Integer, String>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparator<? super Integer> comparator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'comparator'");
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'navigableKeySet'");
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'descendingKeySet'");
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subMap'");
    }
}