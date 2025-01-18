package DorofeyMI_310971.lesson10;

import java.util.*;

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {
    private E[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public MyPriorityQueue() {
        heap = (E[]) new Comparable[10];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            @SuppressWarnings("unchecked")
            E[] newHeap = (E[]) new Comparable[heap.length * 2];
            System.arraycopy(heap, 0, newHeap, 0, heap.length);
            heap = newHeap;
        }
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index].compareTo(heap[parentIndex]) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void siftDown(int index) {
        while (index * 2 + 1 < size) {
            int leftChild = index * 2 + 1;
            int rightChild = index * 2 + 2;
            int smallest = leftChild;

            if (rightChild < size && heap[rightChild].compareTo(heap[leftChild]) < 0) {
                smallest = rightChild;
            }

            if (heap[index].compareTo(heap[smallest]) <= 0) {
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
    public boolean add(E element) {
        ensureCapacity();
        heap[size] = element;
        siftUp(size);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(o)) {
                heap[i] = heap[--size];
                heap[size] = null;
                siftDown(i);
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
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E result = heap[0];
        heap[0] = heap[--size];
        heap[size] = null;
        siftDown(0);
        return result;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        return remove();
    }

    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }
        return heap[0];
    }

    @Override
    public E element() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return heap[0];
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
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        List<E> retainedElements = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (!c.contains(heap[i])) {
                retainedElements.add(heap[i]);
            }
        }
        if (retainedElements.size() != size) {
            modified = true;
            size = retainedElements.size();
            heap = (E[]) new Comparable[heap.length];
            for (int i = 0; i < size; i++) {
                heap[i] = retainedElements.get(i);
            }
            for (int i = size / 2 - 1; i >= 0; i--) {
                siftDown(i);
            }
        }
        return modified;
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        List<E> retainedElements = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (c.contains(heap[i])) {
                retainedElements.add(heap[i]);
            }
        }
        if (retainedElements.size() != size) {
            modified = true;
            size = retainedElements.size();
            heap = (E[]) new Comparable[heap.length];
            for (int i = 0; i < size; i++) {
                heap[i] = retainedElements.get(i);
            }
            for (int i = size / 2 - 1; i >= 0; i--) {
                siftDown(i);
            }
        }
        return modified;
    }



    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return heap[currentIndex++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(heap, size);
    }

    @Override
    @SuppressWarnings("unchecked")
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
}
