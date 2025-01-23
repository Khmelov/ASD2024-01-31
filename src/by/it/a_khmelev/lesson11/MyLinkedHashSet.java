package by.it.a_khmelev.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet <E> implements Set<E> {

    private static class Node<E> {
        E value;
        Node<E> next;
        Node<E> prevOrder; // предыдущий узел в пор€дке добавлени€
        Node<E> nextOrder; // следующий узел в пор€дке добавлени€

        Node(E value) {
            this.value = value;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<E>[] table;
    private Node<E> head; // начало св€зного списка дл€ пор€дка добавлени€
    private Node<E> tail; // конец св€зного списка дл€ пор€дка добавлени€
    private int size;
    private int threshold;

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
        this.table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        this.threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
        this.size = 0;
    }

    // size
    @Override
    public int size() {
        return size;
    }

    // clear
    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        head = tail = null;
        size = 0;
    }

    // isEmpty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // add
    @Override
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Null values are not supported");
        }

        int index = indexFor(hash(element));
        Node<E> current = table[index];

        while (current != null) {
            if (current.value.equals(element)) {
                return false; // элемент уже существует
            }
            current = current.next;
        }

        Node<E> newNode = new Node<>(element);
        newNode.next = table[index];
        table[index] = newNode;

        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.nextOrder = newNode;
            newNode.prevOrder = tail;
            tail = newNode;
        }

        size++;

        if (size > threshold) {
            resize();
        }
        return true;
    }

    // remove
    @Override
    public boolean remove(Object element) {
        if (element == null) {
            return false;
        }

        int index = indexFor(hash(element));
        Node<E> current = table[index];
        Node<E> prev = null;

        while (current != null) {
            if (current.value.equals(element)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }

                // ”дал€ем из св€зного списка пор€дка добавлени€
                if (current.prevOrder != null) {
                    current.prevOrder.nextOrder = current.nextOrder;
                } else {
                    head = current.nextOrder;
                }

                if (current.nextOrder != null) {
                    current.nextOrder.prevOrder = current.prevOrder;
                } else {
                    tail = current.prevOrder;
                }

                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    // contains
    @Override
    public boolean contains(Object element) {
        if (element == null) {
            return false;
        }

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
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        boolean first = true;

        while (current != null) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(current.value);
            first = false;
            current = current.nextOrder;
        }

        sb.append("]");
        return sb.toString();
    }

    // containsAll
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    // addAll
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

    // removeAll
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

    // retainAll
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<E> current = head;

        while (current != null) {
            Node<E> next = current.nextOrder;
            if (!c.contains(current.value)) {
                remove(current.value);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    // ¬спомогательные методы

    private int hash(Object element) {
        int h = element.hashCode();
        return h ^ (h >>> 16); // улучшенное вычисление хеша
    }

    private int indexFor(int hash) {
        return hash & (table.length - 1); // прив€зка хеша к размеру массива
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<E>[] oldTable = table;
        table = (Node<E>[]) new Node[oldTable.length * 2];
        threshold = (int) (table.length * LOAD_FACTOR);

        for (Node<E> bucket : oldTable) {
            Node<E> current = bucket;
            while (current != null) {
                Node<E> next = current.next;
                int index = indexFor(hash(current.value));
                current.next = table[index];
                table[index] = current;
                current = next;
            }
        }
    }
}