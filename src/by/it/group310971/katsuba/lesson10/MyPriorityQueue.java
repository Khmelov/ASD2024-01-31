package by.it.group310971.katsuba.lesson10;

import java.util.*;

public class MyPriorityQueue<E> implements Queue<E> {
    private int size = 0;
    @SuppressWarnings("unchecked")
    private E[] heap = (E[]) new Object[0];
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object element) {
        for (int i = 0; i < size; i++)
            if (heap[i].equals(element))
                return true;
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
    public boolean add(E element) {
        return offer(element);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        @SuppressWarnings("unchecked")
        E []cArray =(E[]) c.toArray();
        if(cArray.length == 0)
            return false;
        for(int i = 0; i < cArray.length; i++)
            offer(cArray[i]);
        return true;
    }

    public void heapify(){
        for(int i = size/2-1;i>=0;i--)
            heapifyDown(i);
    }
    @Override
    public boolean removeAll(Collection<?> c) {
        int i = 0;
        for(;i<size && !c.contains(heap[i]);i++);
        if(i==size)
            return false;
        int end = size;
        int begin = i;
        int[] forSaving = new int[end-begin];
        for(i = begin+1; i<end;i++)
            forSaving[i-begin] = (c.contains(heap[i]))?0:1;
        int w = begin;
        for(i = begin; i < end; i++)
            if(forSaving[i-begin]==1)
                heap[w++]=heap[i];
        size = w;
        for(i = size; i < end; i++)
            heap[i]=null;
        heapify();
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int i = 0;
        for(;i<size && c.contains(heap[i]);i++);
        if(i==size)
            return false;
        int end = size;
        int begin = i;
        int[] forSaving = new int[end-begin];
        for(i = begin+1; i<end;i++)
            forSaving[i-begin] = (!c.contains(heap[i]))?0:1;
        int w = begin;
        for(i = begin; i < end; i++)
            if(forSaving[i-begin]==1)
                heap[w++]=heap[i];
        size = w;
        for(i = size; i < end; i++)
            heap[i]=null;
        heapify();
        return true;
    }

    @Override
    public void clear() {
        for(int i = 0; i < size; i++)
            heap[i]=null;
        size = 0;
    }

    @Override
    public boolean offer(E element) {
        if(size == heap.length){
            @SuppressWarnings("unchecked")
            E []temp = (E[])new Object[size*3/2+1];
            System.arraycopy(heap, 0, temp, 0, size);
            heap = temp;
        }
        heap[size]=element;
        heapifyUp(size);
        size++;
        return true;
    }

    @Override
    public E remove() {
        if(isEmpty())
            throw new IllegalStateException("Queue is empty");
        return poll();
    }

    private void swap(int i, int j){
        E temp = heap[i];
        heap[i]=heap[j];
        heap[j]=temp;
    }

    @SuppressWarnings("unchecked")
    private void heapifyUp(int index){
        while(((Comparable<? super E>) heap[index]).compareTo(heap[(index-1)/2])<0){
            swap(index, (index-1)/2);
            index = (index-1)/2;
        }
    }

    @SuppressWarnings("unchecked")
    private void heapifyDown(int index){
        boolean isinplace = false;
        while(2*index+1<size && !isinplace){
            int left = 2*index+1;
            int right = left+1;
            int child = left;
            if(right < size && ((Comparable<? super E>) heap[right]).compareTo(heap[left])<0)
                child = right;
            if(((Comparable<? super E>) heap[index]).compareTo(heap[child])<0)
                isinplace = true;
            if (!isinplace)
                swap(index, child);
            index = child;
        }
    }
    @Override
    public E poll() {
        if(size == 0)
            return null;
        E element = heap[0];
        heap[0] = heap[size-1];
        heap[--size] = null;
        heapifyDown(0);
        return element;
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return heap[0];
    }

    @Override
    public E peek() {
        return isEmpty() ? null : heap[0];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < size; i++){
            sb.append(heap[i]);
            if(i < size-1){
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}