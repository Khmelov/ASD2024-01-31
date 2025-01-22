package by.it.group310971.fedorenko.lesson11;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private Node<E>[] table;
    private int size;

    public MyHashSet() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Node<E> bucket : table) {
            for (Node<E> current = bucket; current != null; current = current.next) {
                if (!first) sb.append(", ");
                sb.append(current.value);
                first = false;
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
        table = new Node[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) {
            return false;
        }
        if (size >= LOAD_FACTOR * table.length) {
            resize();
        }
        int index = indexFor(hash(element));
        Node<E> newNode = new Node<>(element, table[index]);
        table[index] = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object element) {
        int index = indexFor(hash(element));
        Node<E> current = table[index];
        Node<E> previous = null;

        while (current != null) {
            if (current.value.equals(element)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean contains(Object element) {
        int index = indexFor(hash(element));
        Node<E> current = table[index];
        while (current != null) {
            if (current.value.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
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
        boolean changed = false;
        for (E element : c) {
            if (add(element)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E element = it.next();
            if (!c.contains(element)) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object e : c) {
            if (remove(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int bucketIndex = 0;
            private Node<E> current = null;
            private Node<E> lastReturned = null;

            {
                moveToNextBucket();
            }

            private void moveToNextBucket() {
                while (bucketIndex < table.length && (current == null || table[bucketIndex] == null)) {
                    current = table[bucketIndex++];
                }
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastReturned = current;
                current = current.next;
                if (current == null) {
                    bucketIndex++;
                    moveToNextBucket();
                }
                return lastReturned.value;
            }

            @Override
            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException();
                }
                MyHashSet.this.remove(lastReturned.value);
                lastReturned = null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node<E> bucket : table) {
            for (Node<E> current = bucket; current != null; current = current.next) {
                array[i++] = current.value;
            }
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        for (Node<E> bucket : table) {
            for (Node<E> current = bucket; current != null; current = current.next) {
                a[i++] = (T) current.value;
            }
        }
        return a;
    }

    private int hash(Object element) {
        return (element == null) ? 0 : element.hashCode();
    }

    private int indexFor(int hash) {
        return (hash & 0x7FFFFFFF) % table.length;
    }

    private void resize() {
        Node<E>[] oldTable = table;
        table = new Node[oldTable.length * 2];
        size = 0;
        for (Node<E> bucket : oldTable) {
            for (Node<E> current = bucket; current != null; current = current.next) {
                add(current.value);
            }
        }
    }

    private static class Node<E> {
        final E value;
        Node<E> next;

        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }
}