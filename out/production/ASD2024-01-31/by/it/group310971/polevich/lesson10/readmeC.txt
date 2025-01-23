package by.it.group310971.polevich.lesson09;

import java.util.Collection;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class MyPriorityQueue<E extends Comparable<E>> {

    private E[] heap;    // Массив для хранения элементов кучи
    private int size;    // Текущий размер кучи

    // Конструктор по умолчанию с начальной емкостью
    public MyPriorityQueue() {
        heap = (E[]) new Comparable[10];
        size = 0;
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }

    public boolean add(E element) {
        return offer(element);
    }

    public boolean offer(E element) {
        if (size == heap.length) {
            resize();
        }
        heap[size] = element;
        siftUp(size);
        size++;
        return true;
    }

    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E result = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        siftDown(0);
        return result;
    }

    public E peek() {
        return isEmpty() ? null : heap[0];
    }

    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return heap[0];
    }

    public E remove() {
        E result = poll();
        if (result == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        return result;
    }

    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(heap[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsAll(Collection<E> c) {
        for (E element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection<E> c) {
        for (E element : c) {
            add(element);
        }
        return true;
    }

    public boolean removeAll(Collection<E> c) {
        boolean modified = false;
        for (E element : c) {
            while (contains(element)) {
                removeElement(element);
                modified = true;
            }
        }
        return modified;
    }

    public boolean retainAll(Collection<E> c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(heap[i])) {
                removeElement(heap[i]);
                i--; // Корректируем индекс после удаления
                modified = true;
            }
        }
        return modified;
    }

    /////////////////////////////////////////////////////////////////////////
    //////               Вспомогательные методы                        ///////
    /////////////////////////////////////////////////////////////////////////

    // Увеличение размера массива в 2 раза
    private void resize() {
        E[] newHeap = (E[]) new Comparable[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }

    // Просеивание вверх для поддержания свойства кучи
    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index].compareTo(heap[parentIndex]) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    // Просеивание вниз для поддержания свойства кучи
    private void siftDown(int index) {
        while (index < size / 2) { // Пока есть хотя бы один потомок
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallerChild = leftChild;

            if (rightChild < size && heap[rightChild].compareTo(heap[leftChild]) < 0) {
                smallerChild = rightChild;
            }

            if (heap[index].compareTo(heap[smallerChild]) <= 0) {
                break;
            }

            swap(index, smallerChild);
            index = smallerChild;
        }
    }

    // Удаление элемента из кучи
    private void removeElement(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(heap[i])) {
                heap[i] = heap[size - 1];
                heap[size - 1] = null;
                size--;
                siftDown(i);
                break;
            }
        }
    }

    // Обмен элементов массива
    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
