package by.it.group310971.kush.lesson10;
import java.util.*;


public class MyArrayDeque<E> implements Deque<E> {
    final static int InitialSize = 8;
    int Front;
    int Rear;
    int Size;
    E[] Elements;

    MyArrayDeque() {
        this(InitialSize);
    }

    MyArrayDeque(int size) {
        Front = size / 2;
        Rear = Front - 1;
        Size = 0;
        Elements = (E[]) new Object[size];
    }

    private void Resize(int size) {
        E[] temp = (E[]) new Object[size];
        int k = (size - Size) / 2;

        for (int i = 0; i < Size; i++) {
            temp[i + k] = Elements[Front + i];
        }

        Front = k;
        Rear = k + Size - 1;
        Elements = temp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('[');
        for (int i = 0; i < Size; i++) {
            sb.append(Elements[Front + i]);
            if (i < Size - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');

        return sb.toString();
    }

    @Override
    public int size() {
        return Size;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        if (Front == 0) {
            Resize(Elements.length * 2);
        }

        Front--;
        Size++;
        Elements[Front] = e;
    }

    @Override
    public void addLast(E e) {
        if (Rear == Elements.length - 1) {
            Resize(Elements.length * 2);
        }

        Rear++;
        Size++;
        Elements[Rear] = e;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (Size == 0) {
            return null;
        }

        return Elements[Front];
    }

    @Override
    public E getLast() {
        if (Size == 0) {
            return null;
        }

        return Elements[Rear];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (Size == 0) {
            return null;
        }

        Front++;
        Size--;
        return Elements[Front - 1];
    }

    @Override
    public E pollLast() {
        if (Size == 0) {
            return null;
        }

        Rear--;
        Size--;
        return Elements[Rear + 1];
    }



    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
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
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean isEmpty() {
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

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
}
