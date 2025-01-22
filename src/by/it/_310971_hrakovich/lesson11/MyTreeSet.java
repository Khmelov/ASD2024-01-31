package by.it._310971_hrakovich.lesson11;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {
    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public MyTreeSet() {
        elements = (E[]) new Comparable[10]; // Начальный размер массива
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) sb.append(", ");
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
        elements = (E[]) new Comparable[10]; // Сброс массива
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) return false; // Элемент уже существует
        if (size == elements.length) resize(); // Увеличение массива при необходимости
        int index = Arrays.binarySearch(elements, 0, size, element);
        index = index < 0 ? -index - 1 : index; // Получаем индекс для вставки
        System.arraycopy(elements, index, elements, index + 1, size - index); // Сдвиг элементов
        elements[index] = element; // Вставка элемента
        size++;
        return true;
    }

    @Override
    public boolean remove(Object element) {
        int index = Arrays.binarySearch(elements, 0, size, (E) element);
        if (index < 0) return false; // Элемент не найден
        System.arraycopy(elements, index + 1, elements, index, size - index - 1); // Сдвиг элементов
        size--;
        return true;
    }

    @Override
    public boolean contains(Object element) {
        return Arrays.binarySearch(elements, 0, size, (E) element) >= 0; // Поиск элемента
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object element : collection) {
            if (!contains(element)) return false; // Проверка каждого элемента
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean modified = false;
        for (E element : collection) {
            if (add(element)) modified = true; // Добавление элементов
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (Object element : collection) {
            if (remove(element)) modified = true; // Удаление элементов
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!collection.contains(elements[i])) {
                remove(elements[i]); // Удаление элементов, не входящих в коллекцию
                modified = true;
                i--; // Корректировка индекса после удаления
            }
        }
        return modified;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        elements = Arrays.copyOf(elements, elements.length * 2); // Увеличение размера массива
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
