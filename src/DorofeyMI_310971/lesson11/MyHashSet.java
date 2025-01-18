package DorofeyMI_310971.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private Node<E>[] table;
    private int size;

    public MyHashSet() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private static class Node<E> {
        E key;
        Node<E> next;

        Node(E key, Node<E> next) {
            this.key = key;
            this.next = next;
        }
    }

    private int hash(Object key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % table.length);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E key) {
        int index = hash(key);
        Node<E> node = table[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return false;
            }
            node = node.next;
        }
        table[index] = new Node<>(key, table[index]);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object key) {
        int index = hash(key);
        Node<E> node = table[index];
        Node<E> prev = null;
        while (node != null) {
            if (node.key.equals(key)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        return false;
    }



    @Override
    public boolean contains(Object key) {
        int index = hash(key);
        Node<E> node = table[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            private Node<E> current = null;

            @Override
            public boolean hasNext() {
                while (current == null && index < table.length) {
                    current = table[index++];
                }
                return current != null;
            }

            @Override
            public E next() {
                E key = current.key;
                current = current.next;
                return key;
            }
        };
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (Node<E> node : table) {
            while (node != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(node.key);
                first = false;
                node = node.next;
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
