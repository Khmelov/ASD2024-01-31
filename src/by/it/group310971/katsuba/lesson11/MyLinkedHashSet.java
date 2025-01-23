package by.it.group310971.katsuba.lesson11;

import java.util.*;

public class MyLinkedHashSet<E> implements Set<E> {
    private static final int INITIAL_CAPACITY = 16; // Initial capacity
    private Node<E>[] table; // Array to store elements
    private int size; // Number of elements in the set
    private Node<E> head; // Head of the linked list for insertion order
    private Node<E> tail; // Tail of the linked list for insertion order

    // Inner class for linked list nodes
    private static class Node<E> {
        E value;
        Node<E> next; // Pointer to the next node
        Node<E> prev; // Pointer to the previous node

        Node(E value) {
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.value);
            current = current.next;
            if (current != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
        head = null;
        tail = null;
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
        // Add to array
        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node<E> current = table[index];
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode; // Add to the end of the linked list in the bucket
        }
        // Add to linked list for insertion order
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object value) {
        int index = getIndex(value);
        Node<E> current = table[index];
        Node<E> prev = null;

        while (current != null) {
            if (current.value.equals(value)) {
                // Remove from the array
                if (prev == null) {
                    table[index] = current.next; // Remove first node
                } else {
                    prev.next = current.next; // Remove node from the list
                }
                // Remove from linked list
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next; // If removing head
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev; // If removing tail
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false; // Element not found
    }

    @Override
    public boolean contains(Object value) {
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
    public boolean containsAll(Collection<?> collection) {
        for (Object value : collection) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean modified = false;
        for (E value : collection) {
            if (add(value)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (Object value : collection) {
            if (remove(value)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean modified = false;
        Node<E> current = head;
        while (current != null) {
            if (!collection.contains(current.value)) {
                remove(current.value);
                modified = true;
            }
            current = current.next;
        }
        return modified;
    }

    private int getIndex(Object value) {
        return value == null ? 0 : Math.abs(value.hashCode() % table.length);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

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
                return value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.value;
            current = current.next;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) toArray(); // Use the toArray method
        }
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            a[i] = (T) current.value;
            current = current.next;
        }
        if (a.length > size) {
            a[size] = null; // Null-terminate the array
        }
        return a;
    }
}