package by.it.group310971.katsuba.lesson11;

import java.util.*;

public class MyHashSet<E> implements Set<E> {
    private static final int INITIAL_CAPACITY = 16;
    private Node<E>[] table;
    private int size;

    public MyHashSet() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
    }

    private static class Node<E> {
        E value;
        Node<E> next;

        Node(E value) {
            this.value = value;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E value) {
        if (value == null) {
            throw new NullPointerException("Null values are not allowed");
        }
        int index = getIndex(value);
        if (contains(value)) {
            return false; // Element already exists
        }
        Node<E> newNode = new Node<>(value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object value) {
        if (value == null) {
            return false; // Handle null value
        }
        int index = getIndex(value);
        Node<E> current = table[index];
        Node<E> previous = null;

        while (current != null) {
            if (current.value.equals(value)) {
                if (previous == null) {
                    table[index] = current.next; // Remove first element
                } else {
                    previous.next = current.next; // Remove from middle or end
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false; // Element not found
    }

    @Override
    public boolean contains(Object value) {
        if (value == null) {
            return false; // Handle null value
        }
        int index = getIndex(value);
        Node<E> current = table[index];
        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object value : c) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E value : c) {
            if (add(value)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < table.length; i++) {
            Node<E> current = table[i];
            Node<E> previous = null;
            while (current != null) {
                if (!c.contains(current.value)) {
                    if (previous == null) {
                        table[i] = current.next; // Remove head
                    } else {
                        previous.next = current.next; // Remove from middle/end
                    }
                    size--;
                    modified = true;
                } else {
                    previous = current;
                }
                current = current.next;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object value : c) {
            if (remove(value)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            private Node<E> current = getNextNode();

            private Node<E> getNextNode() {
                while (index < table.length) {
                    if (table[index] != null) {
                        return table[index];
                    }
                    index++;
                }
                return null;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E value = current.value;
                current = current.next;
                if (current == null) {
                    index++;
                    current = getNextNode();
                }
                return value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (Node<E> node : table) {
            Node<E> current = node;
            while (current != null) {
                array[index++] = current.value;
                current = current.next;
            }
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) toArray(); // Use the toArray method
        }
        int index = 0;
        for (Node<E> node : table) {
            Node<E> current = node;
            while (current != null) {
                a[index++] = (T) current.value;
                current = current.next;
            }
        }
        if (a.length > size) {
            a[size] = null; // Null-terminate the array
        }
        return a;
    }

    private int getIndex(Object value) {
        return Math.abs(Objects.hashCode(value)) % table.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Node<E> node : table) {
            Node<E> current = node;
            while (current != null) {
                sb.append(current.value).append(", ");
                current = current.next;
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Remove last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
}