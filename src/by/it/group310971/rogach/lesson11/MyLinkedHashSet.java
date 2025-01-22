package by.it.group310971.rogach.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prevAdded; // Previous node in the addition order
        Node<E> nextAdded; // Next node in the addition order

        Node(E data, Node<E> next, Node<E> prevAdded, Node<E> nextAdded) {
            this.data = data;
            this.next = next;
            this.prevAdded = prevAdded;
            this.nextAdded = nextAdded;
        }
    }

    private Node<E>[] table; // Хеш-таблица
    private Node<E> head; // Первый добавленный элемент
    private Node<E> tail; // Последний добавленный элемент
    private int size; // Количество элементов в множестве
    private static final int INITIAL_CAPACITY = 16; // Начальная вместимость

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

        // Проверка на существование элемента
        while (current != null) {
            if ((element == null && current.data == null) || (element != null && element.equals(current.data))) {
                return false; // Элемент уже существует
            }
            current = current.next;
        }

        // Создание нового узла
        Node<E> newNode = new Node<>(element, table[index], tail, null);
        table[index] = newNode;

        if (tail != null) {
            tail.nextAdded = newNode; // Подключение к предыдущему узлу по порядку
        }
        tail = newNode; // Новый узел становится последним

        if (head == null) {
            head = newNode; // Если это первый элемент, он также становится первым
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
            // Проверка на совпадение элементов
            if ((element == null && current.data == null) || (element != null && element.equals(current.data))) {
                // Убрать из хеш-таблицы
                if (prev == null) {
                    table[index] = current.next; // Удаление первого узла
                } else {
                    prev.next = current.next; // Удаление узла в середине или конце
                }

                // Удаление из порядка добавления
                if (current.prevAdded != null) {
                    current.prevAdded.nextAdded = current.nextAdded;

                } else {
                    head = current.nextAdded; // Если удаляем первый элемент
                }

                if (current.nextAdded != null) {
                    current.nextAdded.prevAdded = current.prevAdded;
                } else {
                    tail = current.prevAdded; // Если удаляем последний элемент
                }

                size--;
                return true; // Элемент был удален
            }
            prev = current;
            current = current.next;
        }

        return false; // Элемент не найден
    }

    @Override
    public boolean contains(Object element) {
        int index = hash(element);
        Node<E> current = table[index];

        while (current != null) {
            if ((element == null && current.data == null) || (element != null && element.equals(current.data))) {
                return true; // Элемент найден
            }
            current = current.next;
        }

        return false; // Элемент не найден
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head; // Начнем с головы
        boolean first = true;

        while (current != null) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(current.data); // Добавляем данные узла в строку
            first = false;
            current = current.nextAdded; // Переход к следующему узлу
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
        Object[] result = new Object[size];
        Node<E> current = head;
        for (int i = 0; current != null; i++) {
            result[i] = current.data;
            current = current.nextAdded; // Переход к следующему узлу
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // Реализация метода toArray(T[] a)
        if (a.length < size) {
            return (T[]) toArray(); // Создаем новый массив, если предоставленный меньше
        }

        Node<E> current = head;
        for (int i = 0; current != null; i++) {
            a[i] = (T) current.data; // Копируем данные в предоставленный массив
            current = current.nextAdded; // Переход к следующему узлу
        }

        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false; // Если хотя бы один элемент отсутствует
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E element : c) {
            changed |= add(element); // Добавляем элементы и отмечаем изменение
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<E> current = head;
        while (current != null) {
            if (!c.contains(current.data)) {
                remove(current.data); // Удаляем, если элемент не содержится в коллекции
                modified = true;
            }
            current = current.nextAdded; // Переход к следующему узлу
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            modified |= remove(element); // Удаляем элементы и отмечаем изменение
        }

        return modified;
    }
}

