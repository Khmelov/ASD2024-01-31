package by.it.group310971.a_kokhan.lesson10;

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
        elements = new Object[10];
        size = 0;
        front = 0;
        rear = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[(front + i) % elements.length]);
            if (i < size - 1) {
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
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    @Override
    public void addFirst(E element) {
        if (size == elements.length) {
            resize();
        }
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        if (size == elements.length) {
            resize();
        }
        elements[rear] = element;
        rear = (rear + 1) % elements.length;
        size++;
    }

    private void resize() {
        Object[] newArray = new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[(front + i) % elements.length];
        }
        elements = newArray;
        front = 0;
        rear = size;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return (E) elements[front];
    }

    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return (E) elements[(rear - 1 + elements.length) % elements.length];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }
        E element = (E) elements[front];
        front = (front + 1) % elements.length;
        size--;
        return element;
    }

    @Override
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        rear = (rear - 1 + elements.length) % elements.length;
        E element = (E) elements[rear];
        size--;
        return element;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Остальные методы интерфейса Deque<E> могут быть добавлены по необходимости

    public static void main(String[] args) {
        MyArrayDeque<Integer> deque = new MyArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addFirst(0);
        System.out.println(deque); // [0, 1, 2]
        System.out.println(deque.pollFirst()); // 0
        System.out.println(deque); // [1, 2]
        System.out.println(deque.getLast()); // 2
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public boolean offerFirst(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offerFirst'");
    }

    @Override
    public boolean offerLast(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offerLast'");
    }

    @Override
    public E removeFirst() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFirst'");
    }

    @Override
    public E removeLast() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeLast'");
    }

    @Override
    public E peekFirst() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peekFirst'");
    }

    @Override
    public E peekLast() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peekLast'");
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFirstOccurrence'");
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeLastOccurrence'");
    }

    @Override
    public boolean offer(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offer'");
    }

    @Override
    public E remove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public E peek() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peek'");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public void push(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'push'");
    }

    @Override
    public E pop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pop'");
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public Iterator<E> descendingIterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'descendingIterator'");
    }
}
