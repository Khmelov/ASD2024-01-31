package by.it.a_khmelev.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayDeque<E> implements Deque<E> {

    private Object[] elements;
    private int size;
    private int front;
    private int rear;

    public MyArrayDeque() {
        elements = new Object[10]; // начальный размер
        size = 0;
        front = 0;
        rear = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
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
    public boolean add(E element) {
        addLast(element);
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

    @Override
    public void addFirst(E element) {
        ensureCapacity();
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        ensureCapacity();
        elements[rear] = element;
        rear = (rear + 1) % elements.length;
        size++;
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

    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] newArray = new Object[size * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = elements[(front + i) % elements.length];
            }
            elements = newArray;
            front = 0;
            rear = size;
        }
    }

    @Override
    public E element() {
        return getFirst();
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
    public E getFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        return (E) elements[front];
    }

    @Override
    public E getLast() {
        if (isEmpty()) throw new NoSuchElementException();
        return (E) elements[(rear - 1 + elements.length) % elements.length];
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

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) return null;
        E element = (E) elements[front];
        front = (front + 1) % elements.length;
        size--;
        return element;
    }

    @Override
    public E pollLast() {
        if (isEmpty()) return null;
        rear = (rear - 1 + elements.length) % elements.length;
        E element = (E) elements[rear];
        size--;
        return element;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

}