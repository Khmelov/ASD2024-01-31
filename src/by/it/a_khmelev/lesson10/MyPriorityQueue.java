package by.it.a_khmelev.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyPriorityQueue <E extends Comparable<E>> implements Queue<E> {
    private E[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public MyPriorityQueue(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.heap = (E[]) new Comparable[initialCapacity];
        this.size = 0;
    }

    public MyPriorityQueue() {
        this(10); // стандартная начальная емкость
    }

    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // size
    @Override
    public int size() {
        return size;
    }

    // clear
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }

    // add
    @Override
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Null elements are not allowed");
        }
        return offer(element);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    // offer
    @Override
    public boolean offer(E element) {
        if (size == heap.length) {
            resize();
        }
        heap[size] = element;
        siftUp(size);
        size++;
        return true;
    }

    // remove
    @Override
    public E remove() {
        E element = poll();
        if (element == null) {
            throw new NoSuchElementException();
        }
        return element;
    }

    // poll
    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E result = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        siftDown(0);
        return result;
    }

    // peek
    @Override
    public E peek() {
        return isEmpty() ? null : heap[0];
    }

    // element
    @Override
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap[0];
    }

    // isEmpty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // contains
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(o)) {
                return true;
            }
        }
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
            modified |= add(element);
        }
        return modified;
    }

    // removeAll
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            while (removeElement((E) element)) {
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
            if (!c.contains(heap[i])) {
                removeElement(heap[i]);
                modified = true;
            }
        }
        return modified;
    }

    // Вспомогательный метод: удаление элемента по значению
    private boolean removeElement(E element) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(element)) {
                heap[i] = heap[size - 1];
                heap[size - 1] = null;
                size--;
                siftDown(i);
                return true;
            }
        }
        return false;
    }

    // Вспомогательный метод: увеличение размера массива
    @SuppressWarnings("unchecked")
    private void resize() {
        E[] newHeap = (E[]) new Comparable[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    // Вспомогательный метод: просеивание вверх (heapify up)
    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].compareTo(heap[parent]) >= 0) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    // Вспомогательный метод: просеивание вниз (heapify down)
    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap[left].compareTo(heap[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
                smallest = right;
            }
            if (smallest == index) {
                break;
            }
            swap(index, smallest);
            index = smallest;
        }
    }

    // Вспомогательный метод: обмен элементов
    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}