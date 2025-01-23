package by.it.group310971.polevich.lesson09;

import java.util.NoSuchElementException;

public class MyLinkedList<E> {

    // Узел связного списка
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    // Поля связного списка
    private Node<E> head; // Первый элемент
    private Node<E> tail; // Последний элемент
    private int size;     // Количество элементов

    // Конструктор по умолчанию
    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    public int size() {
        return size;
    }

    public boolean add(E element) {
        addLast(element);
        return true;
    }

    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element, null, head);
        if (head != null) {
            head.prev = newNode;
        } else {
            tail = newNode; // Если список пуст, голова и хвост совпадают
        }
        head = newNode;
        size++;
    }

    public void addLast(E element) {
        Node<E> newNode = new Node<>(element, tail, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode; // Если список пуст, голова и хвост совпадают
        }
        tail = newNode;
        size++;
    }

    public E element() {
        return getFirst();
    }

    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }

    public E getLast() {
        if (tail == null) {
            throw new NoSuchElementException("List is empty");
        }
        return tail.data;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return unlink(current);
    }

    public boolean remove(Object element) {
        Node<E> current = head;
        while (current != null) {
            if (element == null ? current.data == null : element.equals(current.data)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public E poll() {
        return pollFirst();
    }

    public E pollFirst() {
        if (head == null) {
            return null;
        }
        return unlink(head);
    }

    public E pollLast() {
        if (tail == null) {
            return null;
        }
        return unlink(tail);
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Вспомогательные методы                        ///////
    /////////////////////////////////////////////////////////////////////////

    private E unlink(Node<E> node) {
        E element = node.data;
        Node<E> next = node.next;
        Node<E> prev = node.prev;

        if (prev == null) {
            head = next; // Если удаляемый элемент — первый
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev; // Если удаляемый элемент — последний
        } else {
            next.prev = prev;
        }

        node.data = null;
        node.next = null;
        node.prev = null;
        size--;

        return element;
    }
}
