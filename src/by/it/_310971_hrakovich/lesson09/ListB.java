package by.it._310971_hrakovich.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {


    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    private Object[] elements; // Массив для хранения элементов
    private int size; // Текущий размер списка

    public ListB() {
        elements = new Object[10]; // Начальный размер массива
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        if (size == elements.length) {
            resize(); // Увеличиваем размер массива, если он заполнен
        }
        elements[size++] = e; // Добавляем элемент и увеличиваем размер
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E removedElement = (E) elements[index];
        // Сдвигаем элементы влево
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null; // Уменьшаем размер и очищаем последний элемент
        return removedElement;
    }

    @Override
    public int size() {
        return size; // Возвращаем текущий размер списка
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size == elements.length) {
            resize(); // Увеличиваем размер массива, если он заполнен
        }
        System.arraycopy(elements, index, elements, index + 1, size - index); // Сдвигаем элементы вправо
        elements[index] = element; // Вставляем новый элемент
        size++;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                remove(i); // Удаляем элемент по индексу
                return true;
            }
        }
        return false; // Элемент не найден
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E oldElement = (E) elements[index];
        elements[index] = element; // Заменяем элемент
        return oldElement; // Возвращаем старый элемент
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // Проверяем, пустой ли список
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null; // Очищаем массив
        }
        size = 0; // Устанавливаем размер в 0
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                return i; // Возвращаем индекс элемента
            }
        }
        return -1; // Элемент не найден
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) elements[index]; // Возвращаем элемент по индексу
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0; // Проверяем, содержится ли элемент в списке
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (o.equals(elements[i])) {
                return i; // Возвращаем последний индекс элемента
            }
        }
        return -1; // Элемент не найден
    }

    private void resize() {
        Object[] newArray = new Object[elements.length * 2]; // Увеличиваем размер массива вдвое
        System.arraycopy(elements, 0, newArray, 0, size); // Копируем старые элементы
        elements = newArray; // Устанавливаем новый массив
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return null;
    }

}
