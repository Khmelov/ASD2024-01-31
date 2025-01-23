package by.it.a_khmelev.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet <E> implements Set<E> {

    private static class Node<E> {
        E value;
        Node<E> next;

        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<E>[] table;
    private int size;
    private int threshold;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        this.table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        this.threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
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

        table[index] = new Node<>(element, table[index]);
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
                    table[index] = current.next; // удаляем голову списка
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
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
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
        boolean first = true;

        for (Node<E> bucket : table) {
            Node<E> current = bucket;
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.value);
                first = false;
                current = current.next;
            }
        }

        sb.append("]");
        return sb.toString();
    }

    // Вспомогательные методы

    private int hash(Object element) {
        int h = element.hashCode();
        return h ^ (h >>> 16); // улучшенное вычисление хеша
    }

    private int indexFor(int hash) {
        return hash & (table.length - 1); // привязка хеша к размеру массива
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