package by.it.group310971.Isaichikova.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    private Object[] elements;
    private int size;
    private int front;
    private int rear;

    // Конструктор
    public MyArrayDeque() {
        elements = new Object[16];  // начальный размер массива
        size = 0;
        front = 0;
        rear = 0;
    }

    // Метод для увеличения размера массива
    private void resize(int capacity) {
        Object[] newArray = new Object[capacity];
        int j = front;
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[j];
            j = (j + 1) % elements.length;
        }
        elements = newArray;
        front = 0;
        rear = size;
    }

    // toString() для отображения содержимого очереди
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        int index = front;
        for (int i = 0; i < size; i++) {
            sb.append(elements[index]);
            if (i < size - 1) sb.append(", ");
            index = (index + 1) % elements.length;
        }
        sb.append("]");
        return sb.toString();
    }

    // size() возвращает количество элементов в очереди
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    // add(E element) добавляет элемент в конец очереди
    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    // addFirst(E element) добавляет элемент в начало очереди
    @Override
    public void addFirst(E e) {
        if (size == elements.length) resize(elements.length * 2); // Увеличиваем размер массива
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = e;
        size++;
    }

    // addLast(E element) добавляет элемент в конец очереди
    @Override
    public void addLast(E e) {
        if (size == elements.length) resize(elements.length * 2); // Увеличиваем размер массива
        elements[rear] = e;
        rear = (rear + 1) % elements.length;
        size++;
    }

    // element() возвращает первый элемент очереди, но не удаляет его
    @Override
    public E element() {
        if (size == 0) throw new java.util.NoSuchElementException();
        return (E) elements[front];
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
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
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    // getFirst() возвращает первый элемент очереди
    @Override
    public E getFirst() {
        if (size == 0) throw new java.util.NoSuchElementException();
        return (E) elements[front];
    }

    // getLast() возвращает последний элемент очереди
    @Override
    public E getLast() {
        if (size == 0) throw new java.util.NoSuchElementException();
        return (E) elements[(rear - 1 + elements.length) % elements.length];
    }

    // peekFirst() возвращает первый элемент очереди, но не удаляет его (новая реализация)
    @Override
    public E peekFirst() {
        if (size == 0) return null;
        return (E) elements[front];
    }

    @Override
    public E peekLast() {
        return null;
    }

    // poll() удаляет и возвращает первый элемент очереди
    @Override
    public E poll() {
        if (size == 0) return null;
        E removed = (E) elements[front];
        front = (front + 1) % elements.length;
        size--;
        if (size > 0 && size == elements.length / 4) resize(elements.length / 2); // Уменьшаем размер массива
        return removed;
    }

    // pollFirst() удаляет и возвращает первый элемент очереди
    @Override
    public E pollFirst() {
        return poll();
    }

    // pollLast() удаляет и возвращает последний элемент очереди
    @Override
    public E pollLast() {
        if (size == 0) return null;
        rear = (rear - 1 + elements.length) % elements.length;
        E removed = (E) elements[rear];
        size--;
        if (size > 0 && size == elements.length / 4) resize(elements.length / 2); // Уменьшаем размер массива
        return removed;
    }

    // Метод iterator() выбрасывает UnsupportedOperationException, так как не требуется
    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    // Остальные методы интерфейса можно оставить без реализации, если они не требуются
    @Override
    public boolean offerFirst(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offerLast(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E removeFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E removeLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
