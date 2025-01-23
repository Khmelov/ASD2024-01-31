package by.it.a_khmelev.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet <E extends Comparable<E>> implements Set<E> {
    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public MyTreeSet() {
        this.elements = (E[]) new Comparable[10]; // начальная емкость
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
        elements = (E[]) new Comparable[10];
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

        int index = binarySearch(element);
        if (index >= 0) {
            return false; // элемент уже существует
        }

        index = -(index + 1); // преобразуем отрицательный результат в индекс вставки

        if (size == elements.length) {
            resize();
        }

        // Сдвигаем элементы вправо для вставки
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
        return true;
    }

    // remove
    @Override
    public boolean remove(Object element) {
        if (element == null) {
            return false;
        }

        int index = binarySearch((E) element);
        if (index < 0) {
            return false; // элемент не найден
        }

        // Сдвигаем элементы влево для удаления
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null; // удаляем последний элемент
        return true;
    }



    // contains
    @Override
    public boolean contains(Object element) {
        if (element == null) {
            return false;
        }
        return binarySearch((E) element) >= 0;
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
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
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
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                i--; // компенсируем сдвиг после удаления
                modified = true;
            }
        }
        return modified;
    }

    // Вспомогательные методы

    private void resize() {
        @SuppressWarnings("unchecked")
        E[] newElements = (E[]) new Comparable[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    private int binarySearch(E element) {
        int left = 0;
        int right = size - 1;

        while (left <= right) {
            int mid = (left + right) >>> 1;
            int cmp = element.compareTo(elements[mid]);
            if (cmp < 0) {
                right = mid - 1;
            } else if (cmp > 0) {
                left = mid + 1;
            } else {
                return mid; // элемент найден
            }
        }

        return -(left + 1); // элемент не найден, возвращаем позицию для вставки
    }
}