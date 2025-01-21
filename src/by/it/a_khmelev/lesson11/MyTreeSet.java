package by.it.a_khmelev.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {
    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public MyTreeSet() {
        elements = (E[]) new Comparable[0]; // Initial empty array
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
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
    public void clear() {
        elements = (E[]) new Comparable[0];
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) {
            return false; // Element already exists
        }
        ensureCapacity(size + 1);

        int insertIndex = findInsertIndex(element);
        System.arraycopy(elements, insertIndex, elements, insertIndex + 1, size - insertIndex);
        elements[insertIndex] = element;
        size++;

        return true;
    }

    @Override
    public boolean remove(Object obj) {
        @SuppressWarnings("unchecked")
        E element = (E) obj;

        int index = findIndex(element);
        if (index < 0) {
            return false; // Element not found
        }

        // Shift elements left to fill the gap after removal
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;

        elements[size] = null; // Nullify the last reference
        return true;
    }

    @Override
    public boolean contains(Object obj) {
        @SuppressWarnings("unchecked")
        E element = (E) obj;
        return findIndex(element) >= 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E element : c) {
            modified |= add(element);
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object obj : c) {
            modified |= remove(obj);
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                i--; // Adjust index after removal
                modified = true;
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
                return elements[currentIndex++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(elements, 0, result, 0, size);
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) java.util.Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    // Helper Methods

    /**
     * Finds the index where the element should be inserted (using binary search).
     * @param element The element to insert
     * @return The insert index
     */
    private int findInsertIndex(E element) {
        int low = 0, high = size - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = element.compareTo(elements[mid]);
            if (cmp > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    /**
     * Finds the index of the element (using binary search).
     * @param element The element to find
     * @return The index if found, or -1 if not found
     */
    private int findIndex(E element) {
        int low = 0, high = size - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = element.compareTo(elements[mid]);
            if (cmp == 0) {
                return mid;
            } else if (cmp > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Ensures that the array has sufficient capacity to hold the specified number of elements.
     * @param capacity The required capacity
     */
    @SuppressWarnings("unchecked")
    private void ensureCapacity(int capacity) {
        if (elements.length < capacity) {
            E[] newArray = (E[]) new Comparable[capacity * 2];
            System.arraycopy(elements, 0, newArray, 0, size);
            elements = newArray;
        }
    }
}
