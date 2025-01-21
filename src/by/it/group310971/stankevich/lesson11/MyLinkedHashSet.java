package by.it.group310971.stankevich.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prevAdded;
        Node<E> nextAdded;

        Node(E data, Node<E> next, Node<E> prevAdded, Node<E> nextAdded) {
            this.data = data;
            this.next = next;
            this.prevAdded = prevAdded;
            this.nextAdded = nextAdded;
        }
    }

    private Node<E>[] table;
    private Node<E> head;
    private Node<E> tail;
    private int size;
    private static final int INITIAL_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
        table = (Node<E>[]) new Node[INITIAL_CAPACITY];
        head = null;
        tail = null;
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
        head = null;
        tail = null;
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

        Node<E> newNode = new Node<>(element, table[index], tail, null);
        table[index] = newNode;
        if (tail != null) {
            tail.nextAdded = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
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

                if (current.prevAdded != null) {
                    current.prevAdded.nextAdded = current.nextAdded;
                } else {
                    head = current.nextAdded;
                }

                if (current.nextAdded != null) {
                    current.nextAdded.prevAdded = current.prevAdded;
                } else {
                    tail = current.prevAdded;
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
        Node<E> current = head;
        boolean first = true;
        while (current != null) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(current.data);
            first = false;
            current = current.nextAdded;
        }
        sb.append("]");
        return sb.toString();
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
        boolean modified = false;
        for (E element : c) {
            if (add(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            if (remove(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<E> current = head;
        while (current != null) {
            if (!c.contains(current.data)) {
                remove(current.data);
                modified = true;
            }
            current = current.nextAdded;
        }
        return modified;
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
}
