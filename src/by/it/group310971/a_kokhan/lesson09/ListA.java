package by.it.group310971.a_kokhan.lesson09;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ListA<E> implements List<E> {
    
    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    private static class Node<E>{
        E val;
        Node<E> next;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder(size * 2);
        stringBuilder.append('[');
        for (var node : this) {
            stringBuilder.append(node.toString()).append(',').append(' ');
        }
        if (size > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public boolean add(E e) {
        var newNode = new Node<E>();
        newNode.val = e;
        if (last == null) {
            last = newNode;
            first = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();
        E removedVal;
        if (index == 0) {
            removedVal = first.val;
            first = first.next;
            if (size == 1)
                last = null;
        } else {
            var curNode = first;
            index--;
            for (int i = 0; i < index; i++)
                curNode = curNode.next;
            removedVal = curNode.next.val;
            if (index == size - 2) {
                last = curNode;
                curNode.next = null;
            } else {
                curNode.next = curNode.next.next;
            }
        }
        size--;
        return removedVal;
    }

    @Override
    public int size() {
        return size;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
        if (index > size - 1)
            throw new ArrayIndexOutOfBoundsException();
        var newNode = new Node<E>();
        newNode.val = element;
        if (index == 0) {
            newNode.next = first;
            first = newNode;
            return;
        }
        var curNode = first;
        for (var i = 0; i < index; i++)
            curNode = curNode.next;
        newNode.next = curNode.next;
        curNode.next = newNode;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public void clear() {

    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
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
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(toArray(), size, a.getClass());
        }
        System.arraycopy(toArray(), 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public Object[] toArray() {
        return toArray(new Object[0]);
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return new kListIterator();
    }

    private class kListIterator implements Iterator<E> {
        private Node<E> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E data = current.val;
            current = current.next;
            return data;
        }
    }
}
