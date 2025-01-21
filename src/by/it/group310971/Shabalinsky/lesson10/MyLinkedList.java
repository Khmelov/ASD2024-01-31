package by.it.group310971.Shabalinsky.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Deque<E> {

    // Внутренний класс для узла
    private static class Node<E> {
        E value;
        Node<E> next;
        Node<E> prev;

        Node(E value) {
            this.value = value;
        }
    }

    private Node<E> head; // Начало списка
    private Node<E> tail; // Конец списка
    private int size; // Размер списка

    // Конструктор
    public MyLinkedList() {
        head = tail = null;
        size = 0;
    }

    // toString() для отображения содержимого списка
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    // size() возвращает количество элементов в списке
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    // add(E element) добавляет элемент в конец списка
    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    // remove(int index) удаляет элемент по индексу
    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> current = getNodeAt(index);
        removeNode(current);
        return current.value;
    }

    // remove(E element) удаляет первое вхождение элемента
    @Override
    public boolean remove(Object o) {
        Node<E> current = head;
        while (current != null) {
            if (current.value.equals(o)) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }
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

    // Добавить элемент в начало списка
    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Добавить элемент в конец списка
    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // Возвращает первый элемент списка, но не удаляет его
    @Override
    public E element() {
        if (size == 0) throw new NoSuchElementException();
        return head.value;
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
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    // Возвращает первый элемент списка
    @Override
    public E getFirst() {
        if (size == 0) throw new NoSuchElementException();
        return head.value;
    }

    // Возвращает последний элемент списка
    @Override
    public E getLast() {
        if (size == 0) throw new NoSuchElementException();
        return tail.value;
    }

    // Удаляет и возвращает первый элемент списка
    @Override
    public E poll() {
        if (size == 0) return null;
        return pollFirst();
    }

    // Удаляет и возвращает первый элемент списка
    @Override
    public E pollFirst() {
        if (size == 0) return null;
        E value = head.value;
        removeNode(head);
        return value;
    }

    // Удаляет и возвращает последний элемент списка
    @Override
    public E pollLast() {
        if (size == 0) return null;
        E value = tail.value;
        removeNode(tail);
        return value;
    }

    // Удаление узла
    private void removeNode(Node<E> node) {
        if (node == head) {
            head = node.next;
            if (head != null) head.prev = null;
        } else if (node == tail) {
            tail = node.prev;
            if (tail != null) tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    // Получение узла по индексу
    private Node<E> getNodeAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    // Реализация метода peekFirst()
    @Override
    public E peekFirst() {
        if (size == 0) return null;
        return head.value;
    }

    @Override
    public E peekLast() {
        return null;
    }

    // Остальные методы интерфейса Deque могут быть реализованы аналогично, если необходимо:
    @Override
    public boolean offerFirst(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offerLast(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E removeFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E removeLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
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
