package by.it._310971_hrakovich.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

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
        int index = getIndex(value);
        if (contains(value)) {
            return false; // элемент уже существует
        }
        Node<E> newNode = new Node<>(value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object value) { // Изменено на Object
        int index = getIndex(value);
        Node<E> current = table[index];
        Node<E> previous = null;

        while (current != null) {
            if (current.value.equals(value)) {
                if (previous == null) {
                    table[index] = current.next; // удаляем первый элемент
                } else {
                    previous.next = current.next; // удаляем элемент из середины или конца
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false; // элемент не найден
    }

    @Override
    public boolean contains(Object value) { // Изменено на Object
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

    private int getIndex(Object value) {
        return Objects.hashCode(value) % table.length;
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
            sb.setLength(sb.length() - 2); // удаляем последнюю запятую и пробел
        }
        sb.append("]");
        return sb.toString();
    }
}
