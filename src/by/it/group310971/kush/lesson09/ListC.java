package by.it.group310971.kush.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    E[] elements;
    int indexCur = 0;
    static int size = 8;

    public ListC() {
        this(size);
    }

    public ListC(int size) {
        elements = (E[]) new Object[size];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < indexCur; i++) {
            sb.append(elements[i]);

            if (i < indexCur - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        if (indexCur == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }

            elements = tempElements;
        }

        elements[indexCur] = e;
        indexCur++;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= indexCur) {
            return null;
        }

        E deletedElem = elements[index];
        for (int i = index; i < indexCur - 1; i++) {
            elements[i] = elements[i + 1];
        }

        indexCur--;
        return deletedElem;
    }

    @Override
    public int size() {
        return indexCur;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > indexCur) {
            return;
        }

        if (indexCur == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }

            elements = tempElements;
        }

        for (int i = indexCur; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        indexCur++;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < indexCur; i++) {
            if (o.equals(elements[i])) {
                E deletedItem = elements[i];

                for (int j = i; j < indexCur; j++) {
                    elements[j] = elements[j + 1];
                }

                indexCur--;
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= indexCur) {
            return null;
        }

        E oldElem = elements[index];
        elements[index] = element;
        return oldElem;
    }


    @Override
    public boolean isEmpty() {
        return indexCur == 0;
    }


    @Override
    public void clear() {
        elements = (E[]) new Object[size];
        indexCur = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < indexCur; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= indexCur) {
            return null;
        }

        return elements[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < indexCur; i++) {
            if (o.equals(elements[i])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = indexCur - 1; i >= 0; i--) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem: c) {
            if (!contains(elem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object elem: c) {
            add((E) elem);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (Object elem: c) {
            add(index, (E) elem);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        boolean deletedElem = false;

        for (int i = 0; i < indexCur; i++) {
            if (c.contains(elements[i])) {
                remove(i);
                i--;
                deletedElem = true;
            }
        }
        return deletedElem;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        boolean deletedElem = false;

        for (int i = 0; i < indexCur; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
                deletedElem = true;
            }
        }
        return deletedElem;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > indexCur || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }

        ListC<E> sublist = new ListC<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            sublist.add(elements[i]);
        }
        return sublist;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < indexCur) {
            return (T[]) java.util.Arrays.copyOf(elements, indexCur, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, indexCur);
        if (a.length > indexCur) {
            a[indexCur] = null;
        }
        return a;
    }

    @Override
    public Object[] toArray() {
        return java.util.Arrays.copyOf(elements, indexCur);
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < indexCur;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException();
                }
                return elements[currentIndex++];
            }
        };
    }

}
