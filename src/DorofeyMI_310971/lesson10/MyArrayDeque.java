package DorofeyMI_310971.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    private E[] array;
    private int size;
    private int head;
    private int tail;

    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        array = (E[]) new Object[10];
        size = 0;
        head = 0;
        tail = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[(head + i) % array.length]);
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
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    @Override
    public void addFirst(E element) {
        if (size == array.length) {
            resize();
        }
        head = (head - 1 + array.length) % array.length;
        array[head] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        if (size == array.length) {
            resize();
        }
        array[tail] = element;
        tail = (tail + 1) % array.length;
        size++;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        return array[head];
    }

    @Override
    public E getLast() {
        if (size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        return array[(tail - 1 + array.length) % array.length];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (size == 0) {
            return null;
        }
        E element = array[head];
        head = (head + 1) % array.length;
        size--;
        return element;
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }
        tail = (tail - 1 + array.length) % array.length;
        E element = array[tail];
        size--;
        return element;
    }

    @Override
    public boolean offerFirst(E element) {
        addFirst(element);
        return true;
    }

    @Override
    public boolean offerLast(E element) {
        addLast(element);
        return true;
    }

    @Override
    public E removeFirst() {
        if (size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        E element = array[head];
        head = (head + 1) % array.length;
        size--;
        return element;
    }

    @Override
    public E removeLast() {
        if (size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        tail = (tail - 1 + array.length) % array.length;
        E element = array[tail];
        size--;
        return element;
    }

    @Override
    public E peekFirst() {
        if (size == 0) {
            return null;
        }
        return array[head];
    }

    @Override
    public E peekLast() {
        if (size == 0) {
            return null;
        }
        return array[(tail - 1 + array.length) % array.length];
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        for (int i = 0; i < size; i++) {
            int index = (head + i) % array.length;
            if (array[index].equals(o)) {
                for (int j = i; j < size - 1; j++) {
                    array[(head + j) % array.length] = array[(head + j + 1) % array.length];
                }
                array[(head + size - 1) % array.length] = null;
                size--;
                tail = (tail - 1 + array.length) % array.length;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            int index = (head + i) % array.length;
            if (array[index].equals(o)) {
                for (int j = i; j < size - 1; j++) {
                    array[(head + j) % array.length] = array[(head + j + 1) % array.length];
                }
                array[(head + size - 1) % array.length] = null;
                size--;
                tail = (tail - 1 + array.length) % array.length;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean offer(E element) {
        return add(element);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
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

    // Остальные методы интерфейса Deque<E> можно реализовать аналогично

    private void resize() {
        @SuppressWarnings("unchecked")
        E[] newArray = (E[]) new Object[array.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(head + i) % array.length];
        }
        array = newArray;
        head = 0;
        tail = size;
    }
}
