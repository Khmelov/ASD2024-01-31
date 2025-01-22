package by.it.group310971.kush.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyAvlMap implements Map<Integer, String> {

    class NodeAvi {
        int key;
        String value;
        int Height;
        NodeAvi Left, Right;

        NodeAvi(int key, String value) {
            this.key = key;
            this.value = value;
            this.Height = 1;
        }
    }

    NodeAvi Root;
    StringBuilder result;

    @Override
    public int size() {
        return size(Root);
    }

    private int size(NodeAvi node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.Left) + size(node.Right);
    }

    @Override
    public boolean isEmpty() {
        return Root == null;
    }

    @Override
    public String toString() {
        if (Root == null)
            return "{}";
        StringBuilder sb = new StringBuilder().append('{');
        InOrderTraversal(Root, sb);
        sb.replace(sb.length() - 2, sb.length(), "}");
        return sb.toString();
    }

    private void InOrderTraversal(NodeAvi node, StringBuilder sb) {
        if (node != null) {
            InOrderTraversal(node.Left, sb);
            sb.append(node.key + "=" + node.value + ", ");
            InOrderTraversal(node.Right, sb);
        }
    }


    @Override
    public boolean containsKey(Object key) {
        return Search((Integer) key, Root) != null;
    }

    NodeAvi Search(Integer key, NodeAvi node) {
        if (node == null)
            return null;
        int comparison = key.compareTo(node.key);
        if (comparison == 0)
            return node;

        return Search(key, comparison < 0 ? node.Left : node.Right);
    }

    @Override
    public String get(Object key) {

        NodeAvi result = Search((Integer) key, Root);

        return result == null ? null : result.value;
    }

    @Override
    public String put(Integer key, String value) {
        result = new StringBuilder();
        Root = put(Root, key, value);
        return result.isEmpty() ? null : result.toString();
    }

    NodeAvi put(NodeAvi node, Integer key, String value) {
        if (node == null) {
            return new NodeAvi(key, value);
        }
        int comparison = key.compareTo(node.key);
        if (comparison < 0)
            node.Left = put(node.Left, key, value);
        else if (comparison > 0)
            node.Right = put(node.Right, key, value);
        else {
            if (!node.value.equalsIgnoreCase(value)) {
                node.value = value;
                result.append("generate" + key);
            }
        }
        return Balance(node);
    }

    int Height(NodeAvi node) {
        return node == null ? 0 : node.Height;
    }

    int BalanceFactor(NodeAvi node) {
        return node == null ? 0 : Height(node.Left) - Height(node.Right);
    }

    NodeAvi RotateRight(NodeAvi node)
    {
        NodeAvi newRoot = node.Left;
        node.Left = newRoot.Right;
        newRoot.Right = node;
        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        newRoot.Height = 1 + Math.max(Height(newRoot.Left), Height(newRoot.Right));
        return newRoot;
    }

    NodeAvi RotateLeft(NodeAvi node)
    {
        NodeAvi newRoot = node.Right;
        node.Right = newRoot.Left;
        newRoot.Left = node;
        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        newRoot.Height = 1 + Math.max(Height(newRoot.Left), Height(newRoot.Right));
        return newRoot;
    }

    NodeAvi Balance(NodeAvi node)
    {
        if (node == null)
            return node;

        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        int balanceFactor = BalanceFactor(node);

        if (balanceFactor > 1)
        {
            if (BalanceFactor(node.Left) < 0)
                node.Left = RotateLeft(node.Left);
            return RotateRight(node);
        }

        if (balanceFactor < -1)
        {
            if (BalanceFactor(node.Right) > 0)
                node.Right = RotateRight(node.Right);
            return RotateLeft(node);
        }

        return node;
    }

    @Override
    public String remove(Object key) {
        result = new StringBuilder();
        Root = remove(Root, (Integer) key);
        return result.isEmpty() ? null : result.toString();
    }

    NodeAvi remove(NodeAvi node, Integer key)
    {
        if (node == null)
            return node;
        int comparison = key.compareTo(node.key);
        if (comparison < 0)
            node.Left = remove(node.Left, key);
        else if (comparison > 0)
            node.Right = remove(node.Right, key);
        else
        {
            result.append("generate" + key);
            if (node.Left == null)
                return node.Right;
            if (node.Right == null)
                return node.Left;

            NodeAvi minNode = minValueNode(node.Right);
            node.value = minNode.value;
            node.Right = RemoveMinNode(node.Right);
        }

        return Balance(node);
    }

    NodeAvi RemoveMinNode(NodeAvi node)
    {
        if (node.Left == null)
            return node.Right;

        node.Left = RemoveMinNode(node.Left);
        return Balance(node);
    }

    NodeAvi minValueNode(NodeAvi node) {
        return node.Left == null ? node : minValueNode(node.Left);
    }
    @Override
    public void clear() {
        Root = clear(Root);
    }

    NodeAvi clear(NodeAvi node) {
        if (node == null)
            return null;
        node.Left = clear(node.Left);
        node.Right = clear(node.Right);
        return null;
    }

    //////////////////////////////////////////////////////

    @Override
    public boolean containsValue(Object value) {
        return false;
    }


    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

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

