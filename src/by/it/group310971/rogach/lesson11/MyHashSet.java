package by.it.group310971.rogach.lesson11;

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

    private Node<E>[] table; // Массив для хранения элементов
    private int size; // Количество элементов в множестве
    private static final int INITIAL_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        table = (Node<E>[]) new Node[INITIAL_CAPACITY];
        size = 0;
    }

    // Хеш-функция
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
        int index = hash(element); // Получаем индекс для добавления
        Node<E> current = table[index];

        // Проверка на дубликаты
        while (current != null) {
            if ((element == null && current.data == null) || (element != null && element.equals(current.data))) {
                return false;
            }
            current = current.next;
        }

        // Добавление нового элемента
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
                    table[index] = current.next; // Удаляем первый узел
                } else {
                    prev.next = current.next; // Удаляем узел в середине или конце
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
        return new Iterator<E>() {
            int index = 0; // Индекс текущего списка
            Node<E> current = null;

            @Override
            public boolean hasNext() {
                if (current != null && current.next != null) {
                    return true; // Если в текущем узле есть следующий элемент
                }

                // Поиск следующего узла
                while (index < table.length) {
                    if (table[index] != null) {
                        current = table[index];
                        index++;
                        return true;
                    }
                    index++;
                }

                return false;
            }

            @Override
            public E next() {
                if (current == null || current.next == null) {
                    hasNext(); // Ищем следующий узел
                }
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;

        for (Node<E> node : table) {
            Node<E> current = node;
            while (current != null) {
                result[i++] = current.data;
                current = current.next;
            }
        }

        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) toArray(); // Создаём новый массив
        }

        int i = 0;
        for (Node<E> node : table) {
            Node<E> current = node;
            while (current != null) {
                a[i++] = (T) current.data; // Копируем элементы в массив
                current = current.next;
            }
        }

        return a;
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
        boolean changed = false;
        for (E element : c) {
            changed |= add(element); // Добавляем элементы
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;

        for (int i = 0; i < table.length; i++) {
            Node<E> current = table[i];
            Node<E> prev = null;

            while (current != null) {
                if (!c.contains(current.data)) {
                    if (prev == null) {
                        table[i] = current.next;
                    } else {
                        prev.next = current.next; // Удаляем узел
                    }
                    size--;
                    modified = true; // Удален элемент
                } else {
                    prev = current;
                }
                current = current.next;
            }
        }

        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;

        for (Object element : c) {
            modified |= remove(element); // Удаляем элементы из множества
        }
        return modified;
    }
}

