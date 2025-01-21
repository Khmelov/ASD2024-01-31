package by.it.group310971.Shabalinsky.lesson10;

import java.util.*;

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private E[] heap;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public MyPriorityQueue() {
        heap = (E[]) new Comparable[DEFAULT_CAPACITY];
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].compareTo(heap[parent]) >= 0) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        while (index < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size && heap[leftChild].compareTo(heap[smallest]) < 0) {
                smallest = leftChild;
            }

            if (rightChild < size && heap[rightChild].compareTo(heap[smallest]) < 0) {
                smallest = rightChild;
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public boolean add(E e) {
        ensureCapacity();
        heap[size] = e;
        heapifyUp(size);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(o)) {
                heap[i] = heap[--size];
                heapifyDown(i);
                heapifyUp(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public E remove() {
        if (size == 0) throw new NoSuchElementException();
        E root = heap[0];
        heap[0] = heap[--size];
        heapifyDown(0);
        return root;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E poll() {
        return remove();
    }

    @Override
    public E peek() {
        if (size == 0) return null;
        return heap[0];
    }

    @Override
    public E element() {
        return peek();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        heap = (E[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }

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
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Set<Object> toRemove = new HashSet<>(c);

        E[] newHeap = (E[]) new Comparable[heap.length];
        int newSize = 0;

        for (int i = 0; i < size; i++) {
            if (!toRemove.contains(heap[i])) {
                newHeap[newSize++] = heap[i];
            } else {
                modified = true;
            }
        }

        heap = newHeap;
        size = newSize;
        heapify();

        return modified;
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Set<Object> toRetain = new HashSet<>(c);
        int newSize = 0;

        for (int i = 0; i < size; i++) {
            if (toRetain.contains(heap[i])) {
                heap[newSize++] = heap[i];
            } else {
                modified = true;
            }
        }

        size = newSize;

        heapify();

        return modified;
    }




    private void heapify() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return heap[current++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(heap, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(heap, size, a.getClass());
        }
        System.arraycopy(heap, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
