package by.it.group310971.stankevich.lesson11;

import java.util.Set;
import java.util.Iterator;
import java.util.Collection;

public class MyHashSet<E> implements Set<E> {

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<E>[] table;
    private int size;
    private static final int INITIAL_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        table = (Node<E>[]) new Node[INITIAL_CAPACITY];
        size = 0;
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
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E element) {
        int index = hash(element);
        Node<E> current = table[index];

        while (current != null) {
            if ((element == null && current.data == null) || (element != null && element.equals(current.data))) {
                return false; // Element already exists
            }
            current = current.next;
        }

        table[index] = new Node<>(element, table[index]);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object element) {
        int index = hash(element);
        Node<E> current = table[index];
        Node<E> prev = null;

        while (current != null) {
            if ((element == null && current.data == null) || (element != null && element.equals(current.data))) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean contains(Object element) {
        int index = hash(element);
        Node<E> current = table[index];

        while (current != null) {
            if ((element == null && current.data == null) || (element != null && element.equals(current.data))) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;

        for (Node<E> node : table) {
            Node<E> current = node;
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.data);
                first = false;
                current = current.next;
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("iterator() is not implemented");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("toArray() is not implemented");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("toArray(T[] a) is not implemented");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("addAll(Collection<? extends E> c) is not implemented");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll(Collection<?> c) is not implemented");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll(Collection<?> c) is not implemented");
    }
}
