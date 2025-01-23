package by.it.group310971.katsuba.lesson11;
import java.util.*;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {
    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public MyTreeSet() {
        elements = (E[]) new Comparable[10]; // Initial array size
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
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
    public void clear() {
        elements = (E[]) new Comparable[10]; // Resetting the array
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E element) {
        if (element == null) throw new NullPointerException("Cannot add null elements");
        if (contains(element)) return false; // Element already exists
        if (size == elements.length) resize(); // Resize array if necessary

        int index = Arrays.binarySearch(elements, 0, size, element);
        index = index < 0 ? -index - 1 : index; // Get insertion index
        System.arraycopy(elements, index, elements, index + 1, size - index); // Shift elements
        elements[index] = element; // Insert element
        size++;
        return true;
    }

    @Override
    public boolean remove(Object element) {
        if (element == null) return false; // Handle null safely
        int index = Arrays.binarySearch(elements, 0, size, (E) element);
        if (index < 0) return false; // Element not found
        System.arraycopy(elements, index + 1, elements, index, size - index - 1); // Shift elements
        size--;
        return true;
    }

    @Override
    public boolean contains(Object element) {
        if (element == null) return false; // Handle null safely
        return Arrays.binarySearch(elements, 0, size, (E) element) >= 0; // Search for element
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object element : collection) {
            if (!contains(element)) return false; // Check each element
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean modified = false;
        for (E element : collection) {
            if (add(element)) modified = true; // Add elements
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (Object element : collection) {
            if (remove(element)) modified = true; // Remove elements
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!collection.contains(elements[i])) {
                remove(elements[i]); // Remove elements not in collection
                modified = true;
                i--; // Adjust index after removal
            }
        }
        return modified;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        elements = Arrays.copyOf(elements, elements.length * 2); // Increase array size
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
                if (!hasNext()) throw new NoSuchElementException();
                return elements[currentIndex++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size); // Return a new array containing the elements
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elements, size, a.getClass()); // Use provided array if it's big enough
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null; // Null-terminate the array
        }
        return a;
    }
}
