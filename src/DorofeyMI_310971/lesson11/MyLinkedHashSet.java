package DorofeyMI_310971.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private Node<E>[] table;
    private int size;
    private Node<E> head, tail;

    private static class Node<E> {
        E key;
        Node<E> next;
        Node<E> prev;
        Node<E> nextInTable;

        Node(E key) {
            this.key = key;
        }
    }

    public MyLinkedHashSet() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
        head = tail = null;
    }

    private int hash(Object key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % table.length);
    }

    @Override
    public boolean add(E key) {
        int index = hash(key);
        Node<E> newNode = new Node<>(key);

        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node<E> current = table[index];
            while (current != null) {
                if (current.key.equals(key)) {
                    return false; // элемент уже существует
                }
                if (current.nextInTable == null) {
                    current.nextInTable = newNode;
                    break;
                }
                current = current.nextInTable;
            }
        }

        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
        return true;
    }

    @Override
    public boolean remove(Object key) {
        int index = hash(key);
        Node<E> current = table[index];
        Node<E> prevInTable = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prevInTable == null) {
                    table[index] = current.nextInTable;
                } else {
                    prevInTable.nextInTable = current.nextInTable;
                }

                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }

                size--;
                return true;
            }
            prevInTable = current;
            current = current.nextInTable;
        }
        return false;
    }

    @Override
    public boolean contains(Object key) {
        int index = hash(key);
        Node<E> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.nextInTable;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
        head = tail = null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.key);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
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
        for (Object e : c) {
            if (remove(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<E> current = head;
        while (current != null) {
            if (!c.contains(current.key)) {
                Node<E> next = current.next;
                remove(current.key);
                current = next;
                modified = true;
            } else {
                current = current.next;
            }
        }
        return modified;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E key = current.key;
                current = current.next;
                return key;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }
}
