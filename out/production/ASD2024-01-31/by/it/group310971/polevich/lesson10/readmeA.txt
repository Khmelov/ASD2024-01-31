package by.it.group310971.polevich.lesson09;

import java.util.NoSuchElementException;

public class MyArrayDeque<E> {

    // Внутренний массив для хранения элементов
    private E[] elements;

    // Индекс первого и последнего элементов
    private int head;
    private int tail;

    // Текущее количество элементов
    private int size;

    // Конструктор по умолчанию
    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        elements = (E[]) new Object[10]; // Начальная емкость - 10
        head = 0;
        tail = 0;
        size = 0;
    }

    // Увеличение емкости массива при необходимости
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length * 2;
            E[] newArray = (E[]) new Object[newCapacity];
            int currentIndex = head;

            for (int i = 0; i < size; i++) {
                newArray[i] = elements[currentIndex];
                currentIndex = (currentIndex + 1) % elements.length;
            }

            elements = newArray;
            head = 0;
            tail = size;
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        int currentIndex = head;

        for (int i = 0; i < size; i++) {
            sb.append(elements[currentIndex]);
            if (i < size - 1) sb.append(", ");
            currentIndex = (currentIndex + 1) % elements.length;
        }

        sb.append("]");
        return sb.toString();
    }

    public int size() {
        return size;
    }

    public void add(E element) {
        addLast(element); // Добавление в конец
    }

    public void addFirst(E element) {
        ensureCapacity();
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
        size++;
    }

    public void addLast(E element) {
        ensureCapacity();
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        size++;
    }

    public E element() {
        return getFirst();
    }

    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        return elements[head];
    }

    public E getLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        int lastIndex = (tail - 1 + elements.length) % elements.length;
        return elements[lastIndex];
    }

    public E poll() {
        return pollFirst();
    }

    public E pollFirst() {
        if (size == 0) {
            return null;
        }
        E firstElement = elements[head];
        elements[head] = null; // Освобождаем память
        head = (head + 1) % elements.length;
        size--;
        return firstElement;
    }

    public E pollLast() {
        if (size == 0) {
            return null;
        }
        tail = (tail - 1 + elements.length) % elements.length;
        E lastElement = elements[tail];
        elements[tail] = null; // Освобождаем память
        size--;
        return lastElement;
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Дополнительные методы для удобства            ///////
    /////////////////////////////////////////////////////////////////////////

    // Проверка, пуст ли дек
    public boolean isEmpty() {
        return size == 0;
    }

    // Очистка всех элементов
    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
        head = 0;
        tail = 0;
        size = 0;
    }
}
