package by.it.a_khmelev.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Node<E>[] table;
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public MyLinkedHashSet() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.value);
            if (current.nextInOrder != null) {
                sb.append(", ");
            }
            current = current.nextInOrder;
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
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) {
            return false;
        }

        if (size >= LOAD_FACTOR * table.length) {
            resize();
        }

        int index = indexFor(hash(element));
        Node<E> newNode = new Node<>(element, table[index]);
        table[index] = newNode;

        // Linking to maintain order
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.nextInOrder = newNode;
            newNode.prevInOrder = tail;
            tail = newNode;
        }

        size++;
        return true;
    }

    @Override
    public boolean remove(Object element) {
        int index = indexFor(hash(element));
        Node<E> current = table[index];
        Node<E> previous = null;

        while (current != null) {
            if (current.value.equals(element)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                unlink(current); // Unlink from order list
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean contains(Object element) {
        int index = indexFor(hash(element));
        Node<E> current = table[index];

        while (current != null) {
            if (current.value.equals(element)) {
                return true;
            }
            current = current.next;
        }

        return false;
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
            modified |= add(element);
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            modified |= remove(element);
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            E element = iterator.next();
            if (!c.contains(element)) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;
            private Node<E> lastReturned = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                lastReturned = current;
                current = current.nextInOrder;
                return lastReturned.value;
            }

            @Override
            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException();
                }
                MyLinkedHashSet.this.remove(lastReturned.value);
                lastReturned = null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (Node<E> current = head; current != null; current = current.nextInOrder) {
            array[index++] = current.value;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }

        int index = 0;
        for (Node<E> current = head; current != null; current = current.nextInOrder) {
            a[index++] = (T) current.value;
        }

        return a;
    }

    private int hash(Object element) {
        return (element == null) ? 0 : element.hashCode();
    }

    private int indexFor(int hash) {
        return (hash & 0x7FFFFFFF) % table.length;
    }

    private void resize() {
        Node<E>[] oldTable = table;
        table = new Node[oldTable.length * 2];
        size = 0;
        Node<E> current = head;
        head = tail = null;
        while (current != null) {
            add(current.value);
            current = current.nextInOrder;
        }
    }

    private void unlink(Node<E> node) {
        if (node.prevInOrder != null) {
            node.prevInOrder.nextInOrder = node.nextInOrder;
        } else {
            head = node.nextInOrder;
        }

        if (node.nextInOrder != null) {
            node.nextInOrder.prevInOrder = node.prevInOrder;
        } else {
            tail = node.prevInOrder;
        }
    }

    private static class Node<E> {
        final E value;
        Node<E> next;
        Node<E> nextInOrder;
        Node<E> prevInOrder;

        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }
}
