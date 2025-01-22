package by.it.group310971.kush.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {

    private enum COLOR{RED, BLACK};

    private static class NodeMyRb {
        Integer key;
        String value;
        COLOR color;
        NodeMyRb left, right;
        public NodeMyRb(Integer key, String value, COLOR color) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.left = this.right = null;
        }
    }

    int _size = 0;
    NodeMyRb head = null;

    private boolean isRed(NodeMyRb n) {
        return n != null && n.color == COLOR.RED;
    }

    private void swapColors(NodeMyRb n) {
        COLOR tmp = n.left.color;
        n.left.color = n.color;
        n.right.color = n.color;
        n.color = tmp;
    }
    private NodeMyRb leftRotate(NodeMyRb n) {
        NodeMyRb child = n.right;
        n.right = child.left;
        child.left = n;
        child.color = n.color;
        n.color = COLOR.RED;
        return child;
    }

    private NodeMyRb rightRotate(NodeMyRb n) {
        NodeMyRb child = n.left;
        n.left = child.right;
        child.right = n;
        child.color = n.color;
        n.color = COLOR.RED;
        return child;
    }

    private NodeMyRb balanceNode(NodeMyRb n) {
        if (isRed(n.right) && !isRed(n.left)) {
            n = leftRotate(n);
        }
        if (isRed(n.left) && isRed(n.left.left)) {
            n = rightRotate(n);
        }
        if (isRed(n.right) && isRed(n.left)) {
            swapColors(n);
        }

        return n;
    }


    private NodeMyRb search(NodeMyRb n, Integer key) {
        while(n != null) {
            if ((int)key < (int)n.key) {
                n = n.left;
            } else if ((int)key > (int)n.key) {
                n = n.right;
            } else {
                return n;
            }
        }

        return null;
    }

    private NodeMyRb put(NodeMyRb n, Integer key, String value) {
        if (n == null) {
            return new NodeMyRb(key, value, COLOR.RED);
        }

        if ((int)key < (int)n.key) {
            n.left = put(n.left, key, value);
        } else if ((int)key > (int)n.key) {
            n.right = put(n.right, key, value);
        }

        return balanceNode(n);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        String delimiter = "";
        Stack<NodeMyRb> s = new Stack<NodeMyRb>();
        NodeMyRb curNode = head;
        while (!s.isEmpty() || curNode != null) {
            if (curNode != null) {
                s.push(curNode);
                curNode = curNode.left;
            } else {
                curNode = s.pop();
                sb.append(delimiter).append(curNode.key).append("=").append(curNode.value);
                delimiter = ", ";
                curNode = curNode.right;
            }
        }

        sb.append("}");
        return sb.toString();
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    private void headToKey(SortedMap<Integer, String> newMap, Integer toKey, NodeMyRb n) {
        if (n == null) {
            return;
        }

        headToKey(newMap, toKey, n.left);
        if (toKey > n.key) {
            newMap.put(n.key, n.value);
            headToKey(newMap, toKey, n.right);
        }
    }

    private void tailFromKey(SortedMap<Integer, String> newMap, Integer fromKey, NodeMyRb n) {
        if (n == null) {
            return;
        }

        tailFromKey(newMap, fromKey, n.right);
        if (fromKey <= n.key) {
            newMap.put(n.key, n.value);
            tailFromKey(newMap, fromKey, n.left);
        }
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        SortedMap<Integer, String> newMap = new MyRbMap();
        headToKey(newMap, toKey, head);
        return newMap;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        SortedMap<Integer, String> newMap = new MyRbMap();
        tailFromKey(newMap, fromKey, head);
        return newMap;
    }

    @Override
    public Integer firstKey() {
        if (head == null) {
            return null;
        }

        NodeMyRb n = head;
        while (n.left != null) {
            n = n.left;
        }

        return n.key;
    }

    @Override
    public Integer lastKey() {
        if (head == null) {
            return null;
        }

        NodeMyRb n = head;
        while (n.right != null) {
            n = n.right;
        }

        return n.key;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return search(head, (int)key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Stack<NodeMyRb> s = new Stack<NodeMyRb>();
        NodeMyRb curNode = head;
        while (!s.isEmpty() || curNode != null) {
            if (curNode != null) {
                s.push(curNode);
                curNode = curNode.left;
            } else {
                curNode = s.pop();
                if (value.equals(curNode.value)) {
                    return true;
                }
                curNode = curNode.right;
            }
        }
        return false;
    }

    @Override
    public String get(Object key) {
        NodeMyRb n = search(head, (int)key);
        return n == null ? null : n.value;
    }

    @Override
    public String put(Integer key, String value) {
        NodeMyRb n = search(head, (int)key);
        if (n == null) {
            _size++;
            head = put(head, key, value);
            if (head.color == COLOR.RED) {
                head.color = COLOR.BLACK;
            }
            return null;
        }

        String oldValue = n.value;
        n.value = value;
        return oldValue;
    }


    private NodeMyRb findMin(NodeMyRb n) {
        while (n.left != null) {
            n = n.left;
        }

        return n;
    }

    private NodeMyRb removeMin(NodeMyRb n) {
        if (n.left == null) {
            return n.right;
        }

        n.left = removeMin(n.left);
        return balanceNode(n);
    }


    private NodeMyRb remove(NodeMyRb n, Integer key) {
        if (key < n.key) {
            n.left = remove(n.left, key);
        } else {
            if (isRed(n.left)) {
                n = rightRotate(n);
            }
            if (n.key.equals(key) && n.right == null) {
                return null;
            }

            if (n.key.equals(key)) {
                NodeMyRb minNode = findMin(n.right);
                n.key = minNode.key;
                n.value = minNode.value;
                n.right = removeMin(n.right);
            } else {
                n.right = remove(n.right, key);
            }
        }
        return balanceNode(n);
    }
    @Override
    public String remove(Object key) {
        String oldValue = get(key);
        if (oldValue != null) {
            _size--;
            head = remove(head, (int)key);
            if (head.color == COLOR.RED) {
                head.color = COLOR.BLACK;
            }
        }

        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public void clear() {
        Stack<NodeMyRb> s = new Stack<NodeMyRb>();
        NodeMyRb lastVisited = null, curNode = head;
        while (!s.isEmpty() || curNode != null) {
            if (curNode != null) {
                s.push(curNode);
                lastVisited = curNode;
                curNode = curNode.left;
            } else {
                curNode = s.pop();
                if (lastVisited != null && lastVisited != curNode) {
                    lastVisited.right = null;
                }
                lastVisited = curNode;
                curNode.left = null;
                curNode = curNode.right;
            }
        }
        head.left = null;
        head.right = null;
        head = null;
        _size = 0;
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
}