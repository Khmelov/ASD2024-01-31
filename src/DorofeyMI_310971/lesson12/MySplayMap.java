package DorofeyMI_310971.lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {
    private Node root;
    private int size;

    private class Node {
        Integer key;
        String value;
        Node left, right;

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public MySplayMap() {
        root = null;
        size = 0;
    }

    private void splay(Integer key) {
        // Реализация splay-операции
        if (root == null) return;

        Node header = new Node(null, null);
        Node leftTreeMax = header;
        Node rightTreeMin = header;
        Node t = root;

        while (true) {
            if (key < t.key) {
                if (t.left == null) break;
                if (key < t.left.key) {
                    Node y = t.left;
                    t.left = y.right;
                    y.right = t;
                    t = y;
                    if (t.left == null) break;
                }
                rightTreeMin.left = t;
                rightTreeMin = t;
                t = t.left;
            } else if (key > t.key) {
                if (t.right == null) break;
                if (key > t.right.key) {
                    Node y = t.right;
                    t.right = y.left;
                    y.left = t;
                    t = y;
                    if (t.right == null) break;
                }
                leftTreeMax.right = t;
                leftTreeMax = t;
                t = t.right;
            } else {
                break;
            }
        }

        leftTreeMax.right = t.left;
        rightTreeMin.left = t.right;
        t.left = header.right;
        t.right = header.left;
        root = t;
    }

    @Override
    public String put(Integer key, String value) {
        if (root == null) {
            root = new Node(key, value);
            size = 1;
            return null;
        }

        splay(key);

        if (key.equals(root.key)) {
            String oldValue = root.value;
            root.value = value;
            return oldValue;
        }

        Node newNode = new Node(key, value);
        if (key < root.key) {
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

    @Override
    public String remove(Object key) {
        if (root == null) return null;

        splay((Integer) key);

        if (!key.equals(root.key)) return null;

        String removedValue = root.value;
        if (root.left == null) {
            root = root.right;
        } else {
            Node rightSubTree = root.right;
            root = root.left;
            splay((Integer) key);
            root.right = rightSubTree;
        }
        size--;
        return removedValue;
    }


    @Override
    public String get(Object key) {
        if (root == null) return null;

        splay((Integer) key);

        if (key.equals(root.key)) {
            return root.value;
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }


    private boolean containsValue(Node node, String value) {
        if (node == null) return false;
        if (node.value.equals(value)) return true;
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    @Override
    public boolean containsValue(Object value) {
        if (!(value instanceof String)) {
            return false;
        }
        return containsValue(root, (String) value);
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        toString(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private void toString(Node node, StringBuilder sb) {
        if (node != null) {
            toString(node.left, sb);
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(node.key).append("=").append(node.value);
            toString(node.right, sb);
        }
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        MySplayMap headMap = new MySplayMap();
        headMapHelper(root, headMap, toKey, inclusive);
        return headMap;
    }

    private void headMapHelper(Node node, MySplayMap headMap, Integer toKey, boolean inclusive) {
        if (node == null) return;
        if (node.key < toKey || (inclusive && node.key.equals(toKey))) {
            headMap.put(node.key, node.value);
            headMapHelper(node.left, headMap, toKey, inclusive);
            headMapHelper(node.right, headMap, toKey, inclusive);
        } else {
            headMapHelper(node.left, headMap, toKey, inclusive);
        }
    }



    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        MySplayMap tailMap = new MySplayMap();
        tailMap(root, tailMap, fromKey, inclusive);
        return tailMap;
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }


    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        for (Map.Entry<? extends Integer, ? extends String> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }


    private void tailMap(Node node, MySplayMap tailMap, Integer fromKey, boolean inclusive) {
        if (node == null) return;
        if (node.key > fromKey || (inclusive && node.key.equals(fromKey))) {
            tailMap.put(node.key, node.value);
            tailMap(node.left, tailMap, fromKey, inclusive);
            tailMap(node.right, tailMap, fromKey, inclusive);
        } else {
            tailMap(node.right, tailMap, fromKey, inclusive);
        }
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        return tailMap(fromKey, true);
    }


    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        MySplayMap subMap = new MySplayMap();
        subMapHelper(root, subMap, fromKey, fromInclusive, toKey, toInclusive);
        return subMap;
    }

    private void subMapHelper(Node node, MySplayMap subMap, Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        if (node == null) return;
        if ((fromInclusive ? node.key >= fromKey : node.key > fromKey) && (toInclusive ? node.key <= toKey : node.key < toKey)) {
            subMap.put(node.key, node.value);
        }
        if (node.key > fromKey) {
            subMapHelper(node.left, subMap, fromKey, fromInclusive, toKey, toInclusive);
        }
        if (node.key < toKey) {
            subMapHelper(node.right, subMap, fromKey, fromInclusive, toKey, toInclusive);
        }
    }


    @Override
    public Integer firstKey() {
        if (root == null) throw new NoSuchElementException();
        Node node = root;
        while (node.left != null) {
            node = node.left;
        }
        return node.key;
    }

    @Override
    public Integer lastKey() {
        if (root == null) throw new NoSuchElementException();
        Node node = root;
        while (node.right != null) {
            node = node.right;
        }
        return node.key;
    }

    @Override
    public Set<Integer> keySet() {
        return Set.of();
    }

    @Override
    public Collection<String> values() {
        return List.of();
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return Set.of();
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        return null;
    }

    @Override
    public Integer lowerKey(Integer key) {
        return lowerKey(root, key, null);
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        return null;
    }

    private Integer lowerKey(Node node, Integer key, Integer best) {
        if (node == null) return best;
        if (node.key < key) {
            best = node.key;
            return lowerKey(node.right, key, best);
        } else {
            return lowerKey(node.left, key, best);
        }
    }

    @Override
    public Integer floorKey(Integer key) {
        return floorKey(root, key, null);
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        return null;
    }

    private Integer floorKey(Node node, Integer key, Integer best) {
        if (node == null) return best;
        if (node.key <= key) {
            best = node.key;
            return floorKey(node.right, key, best);
        } else {
            return floorKey(node.left, key, best);
        }
    }

    @Override
    public Integer ceilingKey(Integer key) {
        return ceilingKey(root, key, null);
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        return headMap(toKey, false);
    }


    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        return null;
    }

    private Integer ceilingKey(Node node, Integer key, Integer best) {
        if (node == null) return best;
        if (node.key >= key) {
            best = node.key;
            return ceilingKey(node.left, key, best);
        } else {
            return ceilingKey(node.right, key, best);
        }
    }

    @Override
    public Integer higherKey(Integer key) {
        return higherKey(root, key, null);
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


    private Integer higherKey(Node node, Integer key, Integer best) {
        if (node == null) return best;
        if (node.key > key) {
            best = node.key;
            return higherKey(node.left, key, best);
        } else {
            return higherKey(node.right, key, best);
        }
    }

}
