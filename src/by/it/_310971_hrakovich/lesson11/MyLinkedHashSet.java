package by.it._310971_hrakovich.lesson11;

import java.util.Iterator;
import java.util.Set;
import java.util.Collection;

public class MyLinkedHashSet<E> implements Set<E> {
    private static final int INITIAL_CAPACITY = 16; // Начальная емкость
    private Node<E>[] table; // Массив для хранения элементов
    private int size; // Количество элементов в наборе
    private Node<E> head; // Голова связного списка для порядка добавления
    private Node<E> tail; // Хвост связного списка для порядка добавления

    // Внутренний класс для узлов связного списка
    private static class Node<E> {
        E value;
        Node<E> next; // Ссылка на следующий узел
        Node<E> prev; // Ссылка на предыдущий узел

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
        StringBuilder sb = new StringBuilder();
        sb.append("[");
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
        int index = getIndex(value);
        if (contains(value)) {
            return false; // Элемент уже существует
        }
        Node<E> newNode = new Node<>(value);
        // Добавление в массив
        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node<E> current = table[index];
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode; // Добавление в конец списка
        }
        // Добавление в связный список
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
                if (prev == null) {
                    table[index] = current.next; // Удаление первого узла
                } else {
                    prev.next = current.next; // Удаление узла из списка
                }
                // Удаление из связного списка
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next; // Если удаляем голову
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev; // Если удаляем хвост
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false; // Элемент не найден
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
}
