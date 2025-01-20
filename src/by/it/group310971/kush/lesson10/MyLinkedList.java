package by.it.group310971.kush.lesson10;
import java.util.*;

public class MyLinkedList<E> implements Deque<E> {
    class MyLinkedListNode<E> {
        public E Data;
        public MyLinkedListNode<E> Previous;
        public MyLinkedListNode<E> Next;

        public MyLinkedListNode(E data) {
            Data = data;
        }
    }

    MyLinkedListNode<E> Head;
    MyLinkedListNode<E> Tail;
    int Size;

    MyLinkedList() {
        Head = null;
        Tail = null;
        Size = 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        MyLinkedListNode<E> temp = Head;
        for (int i = 0; i < Size; i++) {
            sb.append(temp.Data);
            if (i < Size - 1) {
                sb.append(", ");
            }
            temp = temp.Next;
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    public E remove(int index) {
        if (index < 0 || index >= Size) {
            return null;
        }

        MyLinkedListNode<E> temp = Head;
        for (int i = 0; i < index; i++) {
            temp = temp.Next;
        }
        E e = temp.Data;

        if (temp.Previous != null) {
            temp.Previous.Next = temp.Next;
        } else {
            Head = temp.Next;
        }

        if (temp.Next != null) {
            temp.Next.Previous = temp.Previous;
        } else {
            Tail = temp.Previous;
        }

        Size--;

        return e;
    }

    @Override
    public boolean remove(Object o) {
        MyLinkedListNode<E> temp = Head;
        int index = 0;
        while (temp != null) {
            if (temp.Data.equals(o)) {
                remove(index);
                return true;
            }
            index++;
            temp = temp.Next;
        }
        return false;
    }

    @Override
    public int size() {
        return Size;
    }

    @Override
    public void addFirst(E e) {
        MyLinkedListNode<E> node = new MyLinkedListNode<>(e);
        if (Head != null) {
            node.Next = Head;
            Head.Previous = node;
        }
        Head = node;

        if (Tail == null) {
            Tail = node;
        }

        Size++;
    }

    @Override
    public void addLast(E e) {
        MyLinkedListNode<E> node = new MyLinkedListNode<>(e);
        if (Tail != null) {
            Tail.Next = node;
            node.Previous = Tail;
        }
        Tail = node;

        if (Head == null) {
            Head = node;
        }

        Size++;
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
        return Head.Data;
    }

    @Override
    public E getLast() {
        if (Size == 0) {
            return null;
        }
        return Tail.Data;
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
        E e = Head.Data;
        Head = Head.Next;

        if (Head != null) {
            Head.Previous = null;
        }
        else {
            Tail = null;
        }

        Size--;
        return e;
    }

    @Override
    public E pollLast() {
        if (Size == 0) {
            return null;
        }
        E e = Tail.Data;
        Tail = Tail.Previous;

        if (Tail != null) {
            Tail.Next = null;
        }
        else {
            Head = null;
        }

        Size--;
        return e;
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
