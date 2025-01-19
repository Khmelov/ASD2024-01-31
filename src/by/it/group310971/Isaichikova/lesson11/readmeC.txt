import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {

    private Object[] elements;   // Массив для хранения элементов
    private int size;            // Текущий размер множества

    public MyTreeSet() {
        elements = new Object[10];
        size = 0;
    }

    // Реализация метода toString()
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            sb.append(elements[i]).append(", ");
        }
        sb.append(elements[size - 1]).append("]");
        return sb.toString();
    }

    // Реализация метода size()
    @Override
    public int size() {
        return size;
    }

    // Реализация метода clear()
    @Override
    public void clear() {
        elements = new Object[10];
        size = 0;
    }

    // Реализация метода isEmpty()
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Реализация метода add(Object)
    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException();
        }

        if (contains(e)) {
            return false;
        }

        ensureCapacity();
        elements[size] = e;
        size++;
        Arrays.sort(elements, 0, size);  // Сортировка после добавления
        return true;
    }

    // Реализация метода remove(Object)
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }

        int index = indexOf(o);
        if (index == -1) {
            return false;
        }

        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[size - 1] = null;
        size--;
        return true;
    }

    // Реализация метода contains(Object)
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    // Реализация метода containsAll(Collection)
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    // Реализация метода addAll(Collection)
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

    // Реализация метода removeAll(Collection)
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    // Реализация метода retainAll(Collection)
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                modified = true;
            }
        }
        return modified;
    }

    // Метод для нахождения индекса элемента
    private int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    // Метод для обеспечения достаточной емкости массива
    private void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }

    // Итератор для перебора элементов
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return (E) elements[index++];
            }
        };
    }

    // Остальные методы Set, которые не используются, могут быть оставлены без реализации:
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }
}
