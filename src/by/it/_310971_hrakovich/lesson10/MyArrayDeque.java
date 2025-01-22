package by.it._310971_hrakovich.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Deque;

public class MyArrayDeque<E> implements Deque<E> {
    private E[] elements;
    private int size;
    private int front;
    private int rear;

    // Конструктор по умолчанию
    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        this(10); // Устанавливаем начальную емкость по умолчанию
    }

    @SuppressWarnings("unchecked")
    public MyArrayDeque(int capacity) {
        elements = (E[]) new Object[capacity];
        size = 0;
        front = 0;
        rear = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[(front + i) % elements.length]);
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
    public boolean add(E element) {
        if (size == elements.length) resize();
        addLast(element);
        return true; // Возвращаем true после успешного добавления элемента
    }

    @Override
    public void addFirst(E element) {
        if (size == elements.length) resize();
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        if (size == elements.length) resize();
        elements[rear] = element;
        rear = (rear + 1) % elements.length;
        size++;
    }

    @Override
    public E element() {
        if (size == 0) throw new NoSuchElementException();
        return elements[front];
    }

    @Override
    public E getFirst() {
        return element();
    }

    @Override
    public E getLast() {
        if (size == 0) throw new NoSuchElementException();
        return elements[(rear - 1 + elements.length) % elements.length];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (size == 0) return null;
        E element = elements[front];
        front = (front + 1) % elements.length;
        size--;
        return element;
    }

    @Override
    public E pollLast() {
        if (size == 0) return null;
        rear = (rear - 1 + elements.length) % elements.length;
        E element = elements[rear];
        size--;
        return element;
    }

    private void resize() {
        int newCapacity = elements.length * 2;
        E[] newArray = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[(front + i) % elements.length];
        }
        elements = newArray;
        front = 0;
        rear = size;
    }
    @Override
    public boolean isEmpty() {
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

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }
    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
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
    public void clear() {

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
    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }
}
